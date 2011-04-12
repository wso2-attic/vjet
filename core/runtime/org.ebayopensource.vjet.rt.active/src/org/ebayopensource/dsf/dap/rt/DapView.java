/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.active.event.IDomChangeListener;
//import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.dap.cnr.IDapCapture;
import org.ebayopensource.dsf.dap.rt.DapCtx.ExeMode;
//import org.ebayopensource.dsf.html.events.EventHandlerContainer;
import org.ebayopensource.dsf.liveconnect.DLCDispatcher;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;

public class DapView {
	private String m_reqId;
	private String m_url;
	private String m_html;
	private DapCtx m_dapCtx;
//	private DsfCtx m_dsfCtx;
	private DapBrowserEngine m_curBrowserEngine;
	
	public DapView(
			final DapSession session, 
			final String reqId, 
			final String url, 
			final String html, 
			final DapCtx dapCtx, 
			final DsfCtx dsfCtx){
		
		m_reqId = reqId;
		m_url = url;
		m_html = html;
		m_dapCtx = DapCtx.create()
			.setDapConfig(dapCtx.getDapConfig())
			.setSession(session)
			.setEventListenerRegistry(dapCtx.getEventListenerRegistry())
			.setDsfSvcRegistry(dapCtx.getDsfSvcRegistry())
			.setServiceEngine(dapCtx.getServiceEngine());
			
//		m_dsfCtx = DsfCtx.createCtx();
		DsfCtx.createCtx();
		//DsfCtxHelper.setContainer(m_dsfCtx, dsfCtx.getEventHandlerContainer());
		DsfCtx.setCtx(dsfCtx);
	}
	
	public String getId(){
		return m_reqId;
	}
	
	public String getUrl(){
		return m_url;
	}
	
	public String getHtml(){
		return m_html;
	}
	
	public DapBrowserEngine getEngine(){
		return m_curBrowserEngine;
	}
	
	public void onLoad(final DLCDispatcher dlcDispatcher, final IDapCapture dapCapture){
		
		setupCtx();
		
		List<IDomChangeListener> domChangeListeners = new ArrayList<IDomChangeListener>();
		DapConfig config = m_dapCtx.getDapConfig();
		if (config.isCaptureReplayEnabled()){
			domChangeListeners.add(dapCapture);
			dlcDispatcher.setInfoCollector(dapCapture);
			config.getHttpClient().addListener(dapCapture);
		}
		
		DapCtx.ctx().setWindow(null);
		
		m_curBrowserEngine = new DapBrowserEngine(
				getHtml(),
			new DapBrowserBinding(dlcDispatcher),
			dlcDispatcher,
			domChangeListeners,
			new DapDomEventBindingListener(dlcDispatcher, config.getDlcClient()));
	}
	
	public void onReceive(final DLCEvent event){
		if (m_curBrowserEngine != null){
			m_curBrowserEngine.onReceive(event);
		}
	}
	
	public DapCtx setupCtx(){
		
		// Setup DapCtx
		DapCtx dapCtx = DapCtx.ctx().reset();
		dapCtx.setExeMode(ExeMode.ACTIVE);
		dapCtx.setDapConfig(m_dapCtx.getDapConfig());
		
		dapCtx.setSession(m_dapCtx.getSession());
//		if (m_curBrowserEngine != null){
//			dapCtx.setWindow(m_curBrowserEngine.getWindow());
//		}
		
		dapCtx.setEventListenerRegistry(m_dapCtx.getEventListenerRegistry());
		dapCtx.setDsfSvcRegistry(m_dapCtx.getDsfSvcRegistry());
		dapCtx.setServiceEngine(m_dapCtx.getServiceEngine());

		// Setup DsfCtx
		//DsfCtxHelper.setContainer(DsfCtx.ctx(), m_dsfCtx.getEventHandlerContainer());
		
		return dapCtx;
	}
	
	//
	// Embedded
	//
    /*
	static class DsfCtxHelper extends ContextHelper {
		static void setContainer(final DsfCtx ctx, final EventHandlerContainer eventHandlerContainer){
			ContextHelper.setEventHandlerContainer(ctx, eventHandlerContainer);
		}
	}
    */
}
