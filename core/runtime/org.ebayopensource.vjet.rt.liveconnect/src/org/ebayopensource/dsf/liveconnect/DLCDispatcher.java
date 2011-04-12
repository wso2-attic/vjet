/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DLCDispatcher implements IDLCDispatcher {
	
	private DLCServer m_dlcServer;
	private Set<SocketChannel> m_channels = new LinkedHashSet<SocketChannel>();
	private Map<String, DLCPageHistory> m_histories = new HashMap<String, DLCPageHistory>();
	private IDLCDispatcherInfoCollector m_infoCollector;
	
	public DLCDispatcher(DLCServer dlcServer) {
		m_dlcServer = dlcServer;
	}
	
	public synchronized void send(SocketChannel channel, String data) {
		if (m_infoCollector != null) {
			m_infoCollector.send(data);
		}
		m_dlcServer.send(channel, data);
	}
	
	/**
	 * send data to the first available channel
	 */
	public synchronized void send(String data) {
		if (m_channels.isEmpty()) {
			return;
		}
		SocketChannel channel = m_channels.iterator().next();
		send(channel, data);
	}
	
	public synchronized void broadcast(String data, SocketChannel exclude) {
		if (m_channels.isEmpty()) {
			System.err.println("There is no established channel");
		}
		for (SocketChannel channel : m_channels) {
			if (channel != exclude) {
				m_dlcServer.send(channel, data);
			}
		}
	}
	
	/**
	 * request will be made via the first available channel
	 */
	public String request(String message, int timeoutInMilli) {
		if (m_channels.isEmpty()) {
			return null;
		}
		SocketChannel channel = m_channels.iterator().next();
		return request(channel, message, timeoutInMilli);
	}
	
	private static final String NULL = "null";
	public String request(SocketChannel channel, String message, int timeoutInMilli) {
		if (m_infoCollector != null) {
			m_infoCollector.request(message);
		}
		DLCFutureResult response = m_dlcServer.request(channel, message);
		String result = response.get(timeoutInMilli, TimeUnit.MILLISECONDS);
		if (m_infoCollector != null) {
			m_infoCollector.response(result);
		}
		if (NULL.equals(result)) {
			return null;
		}
		return result;
	}
	
	public synchronized void addChannel(SocketChannel channel) {
		m_channels.add(channel);
	}
	
	public synchronized boolean removeChannel(SocketChannel channel) {
		return m_channels.remove(channel);
	}
	
	public Iterator<SocketChannel> getChannels() {
		return m_channels.iterator();
	}
	
	public boolean hasChannel() {
		return !m_channels.isEmpty();
	}
	
	public void shutdownDlcServer() {
		m_channels.clear();
		m_dlcServer.shutdown();
	}
	
	public int getDlcServerPort() {
		return m_dlcServer.getPort();
	}
	
	public boolean isNewSession(String sessionId) {
		return !m_histories.containsKey(sessionId);
	}
	
	public synchronized String add(String sessionId, String location) {
		DLCPageHistory history = m_histories.get(sessionId);
		if (history == null) {
			history = new DLCPageHistory();
			m_histories.put(sessionId, history);
		}
		return history.add(location);
	}
	
	public synchronized String getCurrentLocation() {
		if (m_histories.isEmpty()) {
			return null;
		}
		return m_histories.values().iterator().next().getCurrentLocation();
	}
	
	public void setInfoCollector(IDLCDispatcherInfoCollector infoCollector) {
		m_infoCollector = infoCollector;
	}
}
