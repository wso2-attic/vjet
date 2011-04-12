/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.dap.event.listener.IDapEventListener;
import org.ebayopensource.dsf.dap.event.listener.IDapHostEventHandler;
import org.ebayopensource.dsf.dap.svc.IDapHostSvcCallback;
import org.ebayopensource.dsf.dap.svc.IDapSvcCallback;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

@SuppressWarnings("serial")
public final class DapHost extends BaseScriptable {
	
	public static final String DAP_HOST = "dapHost";
	public static final String DAP_HOSTED_EVENT_HANDLER = 
		DAP_HOST + ".getEvtHandlers(\"{0}\")[{1}]";
	public static final String DAP_HOSTED_SVC_HANDLER = 
		DAP_HOST + ".getSvcRespHandlers(\"{0}\")[{1}]";
	
	private Scriptable m_scope;
	private Map<String,DapHostHandlers<IDapHostEventHandler>> m_elements = 
		new HashMap<String,DapHostHandlers<IDapHostEventHandler>>();
	private Map<String,DapHostHandlers<IDapHostSvcCallback>> m_services = 
		new HashMap<String,DapHostHandlers<IDapHostSvcCallback>>();
	
	private static final String[] MTD_NAMES = {
		"getEvtHandlers",
		"getSvcRespHandlers",
	};
	
	public DapHost(){
	};
	
	public DapHostHandlers<IDapHostEventHandler> getEvtHandlers(String elemId) {
		return m_elements.get(elemId);
	}

	public DapHostHandlers<IDapHostSvcCallback> getSvcRespHandlers(String svcId) {
		return m_services.get(svcId);
	}
	
	//
	// Package protected
	//
	DapHost(Scriptable scope){
		m_scope = scope;
		defineFunctionProperties(MTD_NAMES);
	}
	
	void addEvtHandler(String id, DapHostHandlers<IDapHostEventHandler> handlers) {
		m_elements.put(id, handlers);
	}
	
	void addSvcRespHandler(String id, DapHostHandlers<IDapHostSvcCallback> handlers) {
		m_services.put(id, handlers);
	}

	void initialize(){
		
		try {
			ScriptableObject.defineClass(m_scope, DapHostMessage.class);
			ScriptableObject.defineClass(m_scope, DapHost.class);
		} 
		catch (Exception e) {
			throw new RuntimeException("Fail to define host objects.", e);
		}

		Map<String,List<IDapEventListener>> evtHandlers = DapCtx.ctx().getEventListenerRegistry().getAllListeners();
		for(Entry<String,List<IDapEventListener>> entry: evtHandlers.entrySet()){
			for (IDapEventListener listener: entry.getValue()){
				registerEventListener(entry.getKey(), listener);
			}
		}

		Map<String,List<IDapSvcCallback>> svcHandlers = DapCtx.ctx().getServiceEngine().getAllResponseHandlers();
		for(Entry<String,List<IDapSvcCallback>> entry: svcHandlers.entrySet()){
			for (IDapSvcCallback handler: entry.getValue()){
				registerSvcHandler(entry.getKey(), handler);
			}
		}

		Object wrappedOut = Context.javaToJS(this, m_scope);
		ScriptableObject.putProperty(m_scope, DAP_HOST, wrappedOut);
	}
	
	void registerEventListener(final String elemId, final IDapEventListener listener){
		DapHostHandlers<IDapHostEventHandler> hostEvtHandlers = getEvtHandlers(elemId);
		if (hostEvtHandlers == null){
			hostEvtHandlers = new DapHostHandlers<IDapHostEventHandler>();
			addEvtHandler(elemId, hostEvtHandlers);
		}
		hostEvtHandlers.add(listener.getHostEventHandler());
	}
	
	void registerSvcHandler(final String svcId, final IDapSvcCallback callback){
		DapHostHandlers<IDapHostSvcCallback> hostSvcHandlers = getSvcRespHandlers(svcId);
		if (hostSvcHandlers == null){
			hostSvcHandlers = new DapHostHandlers<IDapHostSvcCallback>();
			addSvcRespHandler(svcId, hostSvcHandlers);
		}
		hostSvcHandlers.add(callback.getHostSvcCallback());
	}
}
