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
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.html.ctx.HtmlContextHelper;
import org.ebayopensource.dsf.html.ctx.HtmlCtx;
import org.ebayopensource.dsf.html.events.EventHandlerContainer;
import org.ebayopensource.dsf.liveconnect.DLCDispatcher;
import org.ebayopensource.dsf.liveconnect.client.DLCJsAggregator;
import org.ebayopensource.dsf.liveconnect.client.DLCResourceHolder;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;

/**
 * DapBrowserEmulator emulates browser capability for DAP runtime.
 * It supports capture and replay.
 * 
 * TODO: multi-session/history support
 */
public final class DapBrowserEmulator {

	private static final Pattern nsPattern = Pattern.compile("^\\[(\\w+)\\]"); 

	private DapConfig m_dapConfig;
	private DapBrowserBridge m_browserBridge;
	
	private WeakHashMap<SocketChannel, DapSession> m_channelSessions
		= new WeakHashMap<SocketChannel, DapSession>();
	
	private WeakHashMap<String, DapSession> m_sessions
		= new WeakHashMap<String, DapSession>();
	
	private String m_curReqId;

	private DapConsole m_console;
	
	private List<IBrowserEmulatorListener> m_listeners = new ArrayList<IBrowserEmulatorListener>(1);
	
	//
	// Constructor
	//
	DapBrowserEmulator() throws IOException {
		this(new DapConfig());
	}
	
	DapBrowserEmulator(DapConfig config) throws IOException {
		m_dapConfig = (config == null) ? new DapConfig() : config;
		
		m_browserBridge = new DapBrowserBridge(this);

		m_console = new DapConsole(this);
		Thread ct = new Thread(m_console);
		ct.setDaemon(true);
		ct.start();
	}
	
	//
	// API
	//
	public DapConfig getDapConfig(){
		return m_dapConfig;
	}
	
	public void addListener(IBrowserEmulatorListener listener) {
		m_listeners.add(listener);
	}

	//
	// Package protected
	//
	DapSession getSession(String sessionId){
		return getSession(sessionId, false);
	}
	
	Map<String,DapSession> getSessions(){
		return Collections.unmodifiableMap(m_sessions);
	}
	
	void addHtml(String sessionId, String reqId, String url, String html){
		DapCtx ctx = DapCtx.ctx();
		m_curReqId = reqId;
		synchronized(this){
			getSession(sessionId, true)
				.addView(reqId, url, html, ctx, DsfCtx.ctx());
		}
	}
	
	IDLCClient getDlcClient(){
		return m_dapConfig.getDlcClient();
	}

	DLCJsAggregator getJsAggregator(){
		return m_dapConfig.getJsAggregator();
	}

	DLCResourceHolder getResourceHolder(){
		return m_dapConfig.getResourceHolder();
	}
	
	DapBrowserBridge getBrowserBridge(){
		return m_browserBridge;
	}
	
	DapCaptureReplay getCaptureReplay(){
		DapSession session = DapCtx.ctx().getSession();
		return session == null ? null : session.getCaptureReplay();
	}

	void onConnect(final SocketChannel channel){
		
	}

	void onReceive(final SocketChannel channel, final String msg){
		DapSession session = m_channelSessions.get(channel);
		DLCDispatcher dispatcher = null;
		boolean onload = (session == null);
		if(onload) {
			try {
				onload(msg, channel);
				session = m_channelSessions.get(channel);
			}
			catch(Exception e){
				throw new DsfRuntimeException("Failure during load event: " + msg, e);
			}
		}
		dispatcher = session.getDLCDispatcher();

		DLCMsgHandlerRegistry registry = m_dapConfig.getDlcMsgHandlerRegistry();

		Matcher matcher = nsPattern.matcher(msg);
		if(matcher.find()) {
			String name = matcher.group(1);
			IDLCMsgHandler handler = registry.get(name);
			if(handler != null) try {
				handler.handle(msg, session, dispatcher);
			} catch(Exception e) {
				e.printStackTrace();
			}		
		} else {
			DapEventMsgHandler.getInstance().handle(msg, session, dispatcher);
		}
		if (onload) {
			for (IBrowserEmulatorListener listener : m_listeners) {
				listener.windowOnload();
			}
		}
	}

	void onClose(SocketChannel channel){		
		DapSession session = m_channelSessions.remove(channel);
		if (session != null) {
			m_console.onUnload(session);
			session.onUnload();
		}
		resetThreadLocalContext();
	}
	
	public void shutdown() {
		if (m_browserBridge != null) {
			m_browserBridge.shoudown();
			m_browserBridge = null;
		}
	}
	
	void resetThreadLocalContext(){
		DapCtx.setCtx(null);
		DsfCtx.setCtx(null);
	}
	
	//
	// Private
	//
	private synchronized DapSession getSession(String sessionId, boolean create){
		DapSession session = m_sessions.get(sessionId);
		if (session == null && create){
			session = new DapSession(this, sessionId);
			m_sessions.put(sessionId, session);
		}
		return session;
	}
	
	private void onload(final String payload, final SocketChannel channel){
		
		String sessionId = getDlcClient().getSessionId(payload);
		if (sessionId == null){
			throw new DsfRuntimeException("sessionId is null for event:" + payload);
		}
		
		String reqId = getDlcClient().getReqId(payload);
		if (reqId == null){
			reqId = m_curReqId;
		}
		
		DapSession session = getSession(sessionId);
		session.onLoad(reqId, channel);
		
		synchronized (this) {	
			m_channelSessions.put(channel, session);
		}
		
		DapCaptureReplay captureReplay = getCaptureReplay();
		if (captureReplay != null){
			captureReplay.connected();
		}
		
		m_console.onload(session);
	}

	//
	// Embedded
	//
	static class HtmlCtxHelper extends ContextHelper {
		static void setContainer(EventHandlerContainer eventHandlerContainer){
			HtmlContextHelper.setEventHandlerContainer(HtmlCtx.ctx(), eventHandlerContainer);
		}
	}	
}
