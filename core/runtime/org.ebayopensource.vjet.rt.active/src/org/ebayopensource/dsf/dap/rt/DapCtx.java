/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.dom.html.AHtmlDocument;
import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.dap.proxy.ScriptEngineCtx;
import org.ebayopensource.dsf.html.ctx.HtmlCtx;
import org.ebayopensource.dsf.service.client.IClientServiceHandlerRegistry;

public final class DapCtx extends BaseSubCtx {

	private ExeMode m_exeMode = ExeMode.WEB;
	private DapConfig m_dapConfig;
	
	private DapSession m_session;
	private DapHost m_dapHost;
	private AWindow m_window;
	private XMLHttpRequestImpl m_xmlHttpReq;
	
	private DapEventListenerRegistry m_evtListenerRegistry;
	private DapEventDispatcher m_eventDispatcher;
	private DapServiceEngine m_svcEngine;
	private IClientServiceHandlerRegistry m_dsfSvcRegistry;

	private String m_host = "localhost";
	
	//
	// Constructor
	//
	private DapCtx(){}
	
	//
	// API
	//
	public static DapCtx create(){
		return new DapCtx();
	}
	/**
	 * Gets a context associated with current thread
	 */
	public static DapCtx ctx() {
		DapCtx context = CtxAssociator.getCtx();
		if (context == null) {
			context = create();
			setCtx(context);
		}
		return context;
	}
	
	public DapConfig getDapConfig() {
		if (m_dapConfig == null){
			m_dapConfig = new DapConfig();
		}
		return m_dapConfig;
	}

	public DapCtx setDapConfig(DapConfig dapConfig) {
		m_dapConfig = dapConfig;
		return this;
	}
	
	public void setExeMode(ExeMode exeMode) {
		if (exeMode != ExeMode.WEB) {
			HtmlCtx.ctx().setDisableInlineHandler(true);
		}
		m_exeMode = exeMode;
	}
	
	public ExeMode getExeMode() {
		return m_exeMode;
	}

	public boolean isActiveMode(){
		return m_exeMode == ExeMode.ACTIVE;
	}
	
	public boolean isTranslateMode(){
		return m_exeMode == ExeMode.TRANSLATE;
	}
	
	public boolean isWebMode(){
		return m_exeMode == ExeMode.WEB;
	}
	
	public AWindow getWindow(){
		return m_window;
	}
	
	public DapEventListenerRegistry getEventListenerRegistry(){
		if (m_evtListenerRegistry == null){
			m_evtListenerRegistry = new DapEventListenerRegistry();
		}
		return m_evtListenerRegistry;
	}
	
	public DapServiceEngine getServiceEngine() {
		if (m_svcEngine == null){
			m_svcEngine = new DapServiceEngine();
		}
		return m_svcEngine;
	}
	
	public static AWindow window(){
		return DapCtx.ctx().getWindow();
	}
	
	public static AHtmlDocument document(){
		AWindow w = window();
		return w == null ? null : (AHtmlDocument)w.getDocument();
	}
	
	public DapSession getSession() {
		return m_session;
	}

	public DapCtx setSession(DapSession session) {
		m_session = session;
		return this;
	}
	
	public DapCtx reset() {
		m_exeMode = ExeMode.WEB;
		m_dapConfig = null;

		m_session = null;
		m_dapHost = null;
		m_window = null;
		m_xmlHttpReq = null;
		
		m_evtListenerRegistry = null;
		m_eventDispatcher = null;
		m_svcEngine = null;
		m_dsfSvcRegistry = null;

		ScriptEngineCtx.ctx().reset();
		
		return this;
	}
	
	//
	// Package protected
	//
	/**
	 * Sets the context to be associated with this thread.  The context
	 * can be null.  
	 */
	public static void setCtx(final DapCtx context) {
		if (context != null){
			context.setUpScriptEngineCtx();
		}
		CtxAssociator.setCtx(context) ;
	}
	
	public void setWindow(AWindow window){
		m_window = window;
		setUpScriptEngineCtx();
	}
	
	DapCtx setEventListenerRegistry(DapEventListenerRegistry listenerRegistry){
		m_evtListenerRegistry = listenerRegistry;
		return this;
	}
	
	DapCtx setServiceEngine(DapServiceEngine svcEngine) {
		m_svcEngine = svcEngine;
		return this;
	}
	
	DapCtx setEventDispatcher(DapEventDispatcher eventDispatcher){
		m_eventDispatcher = eventDispatcher;
		return this;
	}
	
	DapEventDispatcher getEventDispatcher(){
		return m_eventDispatcher;
	}
	
	IClientServiceHandlerRegistry getDsfSvcRegistry() {
		return m_dsfSvcRegistry;
	}

	public DapCtx setDsfSvcRegistry(IClientServiceHandlerRegistry dsfSvcRegistry) {
		m_dsfSvcRegistry = dsfSvcRegistry;
		return this;
	}
	
	public XMLHttpRequestImpl getXmlHttpReq() {
		return m_xmlHttpReq;
	}

	void setXmlHttpReq(XMLHttpRequestImpl xmlHttpReq) {
		m_xmlHttpReq = xmlHttpReq;
	}

	String getHost() {
		return m_host;
	}

	void setHost(String host) {
		m_host = host;
	}
	
	DapHost getDapHost() {
		return m_dapHost;
	}

	void setDapHost(DapHost dapHost) {
		m_dapHost = dapHost;
	}
	
	//
	// Private
	//
	private static class CtxAssociator extends ContextHelper {
		private static final String CTX_NAME = DapCtx.class.getSimpleName();
		protected static DapCtx getCtx() {
			return (DapCtx)getSubCtx(DsfCtx.ctx(), CTX_NAME);
		}
		
		protected static void setCtx(final DapCtx ctx) {
			setSubCtx(DsfCtx.ctx(), CTX_NAME, ctx);
		}
	}

	public enum ExeMode {
		ACTIVE,
		TRANSLATE,
		WEB
	}

	/**
	 * set DapCtx for current thread.
	 */
	public void setSameCxtForCurrentThread() {
		DapCtx.setCtx(this);
	}
	
	private void setUpScriptEngineCtx() {
		if (m_window != null) {
			ScriptEngineCtx scriptCtx = ScriptEngineCtx.ctx();
			scriptCtx.setScriptContext(m_window.getContext());
			scriptCtx.setScope(m_window);
		}
	}
	
	public abstract static class DapCtxHelper{
		protected static void setWindow(AWindow win){
			DapCtx.ctx().setWindow(win);
		}
	}
}
