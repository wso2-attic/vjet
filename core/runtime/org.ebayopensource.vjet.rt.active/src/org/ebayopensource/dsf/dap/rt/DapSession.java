/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.active.client.ANavigator;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.liveconnect.DLCDispatcher;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;

/**
 * <P>DapSession starts with a URL request in a browser window or window tab (as in Firefox)
 * and ends with a new URL request. Any in-between page updates or partial updates 
 * via buttons and links are part of the session.<p>
 */
public final class DapSession {
	
	private static final String NAVIGATOR_USER_AGENT = "navigator." + ANavigator.USER_AGENT;

	private final String m_sessionId;
	private String m_userAgent;
	private Map<String,DapView> m_reqViews = new LinkedHashMap<String,DapView>();
	private List<DapView> m_viewList = new ArrayList<DapView>();
	private Iterator<DapView> m_replayIterator;
	
	private DapBrowserEmulator m_emulator;
	private DapCaptureReplay m_captureReplay;
	
	private DapView m_curView;
	private DLCDispatcher m_dlcDispatcher;

	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param emulator DapBrowserEmulator
	 * @param sessionId String
	 */
	DapSession(final DapBrowserEmulator emulator, final String sessionId){
		m_emulator = emulator;
		m_sessionId = sessionId;
		if (m_emulator.getDapConfig().isCaptureReplayEnabled()){
			m_captureReplay = new DapCaptureReplay(m_emulator, this);
		}
	}
	
	//
	// API
	//
	/**
	 * Answer the id of this session
	 * @return String
	 */
	public String getSessionId(){
		return m_sessionId;
	}
	
	/**
	 * Answer the userAgent of this session
	 * @return String
	 */
	public String getUserAgent(){
		return m_userAgent;
	}
	
	/**
	 * Answer the capture replay for this session
	 * @return DapCaptureReplay
	 */
	public DapCaptureReplay getCaptureReplay(){
		return m_captureReplay;
	}
	
	/**
	 * Get ready for replay
	 * @return boolean false if there is nothing to replay
	 */
	public boolean startReplay(){
		if (m_viewList.isEmpty()){
			return false;
		}
		m_curView = m_viewList.get(0);
		m_replayIterator = m_viewList.iterator();

		return true;
	}
	
	/**
	 * Cleanup after replay
	 */
	public void endReplay(){
		m_replayIterator = null;
	}
	
	public Map<String,DapView> getViews(){
		return Collections.unmodifiableMap(m_reqViews);
	}
	
	//
	// Package protected
	//
	/**
	 * Add view to the session and set it as current view.
	 */
	void addView(String id, String url, String html, DapCtx dapCtx, DsfCtx dsfCtx){
		if (m_captureReplay.isReplay()){
			m_curView = m_replayIterator.next();
		}
		else {
			m_curView = new DapView(this, id, url, html, dapCtx, dsfCtx);
			m_viewList.add(m_curView);
		}
		m_reqViews.put(id, m_curView);
	}
	
	DapView getCurrentView(){
		return m_curView;
	}
	
	void onLoad(final String reqId, final SocketChannel channel){
		m_dlcDispatcher = m_emulator.getBrowserBridge().getDLCDispatcher(channel);
		if (m_userAgent == null){
			m_userAgent = m_emulator.getBrowserBridge().request(channel, NAVIGATOR_USER_AGENT);
			m_captureReplay.getCapture().getCapturedData().setUserAgent(m_userAgent);
		}
		m_curView = m_reqViews.get(reqId);
		m_curView.onLoad(m_dlcDispatcher, m_captureReplay.getCapture());
		m_captureReplay.getCapture().beginView(m_curView);
	}
	
	void onUnload(){
//		m_curBrowserEngine.shutdown();
//		m_curBrowserEngine = null;
		m_dlcDispatcher = null;
		m_curView = null;
		m_captureReplay.getCapture().endView();
	}
	
	void onReceive(final DLCEvent event){
		if (m_curView != null){
			m_curView.onReceive(event);
		}
	}

	DLCDispatcher getDLCDispatcher(){
		return m_dlcDispatcher;
	}
}
