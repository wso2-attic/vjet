/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.WeakHashMap;

import org.ebayopensource.dsf.liveconnect.DLCDispatcher;
import org.ebayopensource.dsf.liveconnect.DLCHttpRequest;
import org.ebayopensource.dsf.liveconnect.DLCHttpResponse;
import org.ebayopensource.dsf.liveconnect.DLCServer;
import org.ebayopensource.dsf.liveconnect.IDLCReceiver;

/**
 * DapBrowserBridge connects DapBrowserEmulator to real or another
 * emulated browser. 
 * The communication between real and/or emulated browsers is via DLC.
 */
public final class DapBrowserBridge implements IDLCReceiver {
	
	private DapBrowserEmulator m_browserEmulator;
	private DLCServer m_dlcServer;
	private WeakHashMap<SocketChannel, DLCDispatcher> m_dispatchers
		= new WeakHashMap<SocketChannel, DLCDispatcher>();
	private WeakReference<SocketChannel> m_latestChannel = null;
	
	//
	// Constructor
	//
	DapBrowserBridge(final DapBrowserEmulator browserEmulator) throws IOException {
		m_browserEmulator = browserEmulator;
		m_dlcServer = new DLCServer(this, browserEmulator.getDlcClient(), 
				browserEmulator.getJsAggregator(), browserEmulator.getResourceHolder());
	}
	
	//
	// Satisfy IDLCReceiver
	//
	public void connected(SocketChannel channel){		
		m_browserEmulator.onConnect(channel);
		DLCDispatcher dispatcher = new DLCDispatcher(m_dlcServer);
		dispatcher.addChannel(channel);
		dispatcher.setInfoCollector(m_browserEmulator.getDapConfig().getInfoCollector());
		m_dispatchers.put(channel, dispatcher);
		m_latestChannel = new WeakReference<SocketChannel>(channel);
	}
	
	public void received(SocketChannel channel, String data) {
		m_browserEmulator.onReceive(channel, data);
	}
	
	public DLCHttpResponse get(SocketChannel channel, DLCHttpRequest request){
		return null;
	}
	
	public void closed(SocketChannel channel) {
		m_browserEmulator.onClose(channel);
		m_dispatchers.remove(channel);
	}
	
	public void shoudown() {
		if (m_dlcServer != null) {
			m_dlcServer.shutdown();
			m_dlcServer = null;
		}
	}
	
	//
	// Package protected
	//
	synchronized DLCDispatcher getDLCDispatcher(SocketChannel channel){
		if (channel == null) {
			if (m_latestChannel != null) {
				channel = m_latestChannel.get();
			}
			else {
				return null;
			}
		}
		
		return m_dispatchers.get(channel);
	}
	
	int getPort(){
		return m_dlcServer.getPort();
	}
	
	Collection<SocketChannel> getChannels() {
		return m_dispatchers.keySet();
	}
	
	void send(SocketChannel channel, String script){
		if (script != null && script.length() > 0){
			getDLCDispatcher(channel).send(script);
		}
	}
	
	String request(SocketChannel channel, String script) {
		if (script != null && script.length() > 0){
			return getDLCDispatcher(channel).request(script, 0);
		}
		return null;
	}
}
