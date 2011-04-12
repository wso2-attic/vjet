/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.client.ScriptExecutor;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.svc.IDapSvcCallback;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.service.IServiceSpec;
import org.ebayopensource.dsf.service.ServiceIdHelper;
import org.ebayopensource.dsf.service.client.IClientServiceHandlerRegistry;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ScriptableObject;

/**
 * DAP service engine that provides API for:
 * 1. Subscribe/register service handles/callbacks 
 * 2. Publish/handle service requests
 * 
 * 
 */
public final class DapServiceEngine {
	
	private Map<String,List<IDapSvcCallback>> m_respHandlers
		= new LinkedHashMap<String,List<IDapSvcCallback>>();
	
	//
	// API
	//
	/**
	 * Subscribe/register given handler to service with given spec.
	 * @param svcSpec IServiceSpec
	 * @param handler IDapSvcCallback
	 */
	public static void subscribe(final IServiceSpec svcSpec, final IDapSvcCallback handler){
		DapCtx dapCtx = DapCtx.ctx();
		dapCtx.getDsfSvcRegistry().registerSvcReqHandler(svcSpec);
		dapCtx.getServiceEngine().register(svcSpec.getServiceName(), handler);
	}
	
	/**
	 * Subscribe/register given handler to service operation with given service name and op name.
	 * @param svcSpec IServiceSpec
	 * @param opName String
	 * @param handler IDapSvcCallback
	 */
	public static void subscribe(final IServiceSpec svcSpec, final String opName, final IDapSvcCallback handler){
		DapCtx dapCtx = DapCtx.ctx();
		dapCtx.getDsfSvcRegistry().registerSvcReqHandler(svcSpec, opName);
		dapCtx.getServiceEngine().register(svcSpec.getServiceName(), opName, handler);
	}
	
	/**
	 * Publish/handle given request on service operation with given service name and op name.
	 * @param svcName String
	 * param opName String
	 * @param request Object
	 */
	public static void publish(
			final String svcName, 
			final String opName, 
			final Object request){
		
		DapCtx.ctx().getServiceEngine().handleRequest(
				ServiceIdHelper.createServiceId(svcName, opName),
				request, 
				-1);
	}
	
	/**
	 * Publish/handle given request on service operation with given service name and op name.
	 * @param svcSpec IServiceSpec
	 * param opName String
	 * @param request Object
	 */
	public static void publish(
			final IServiceSpec svcSpec, 
			final Object request){
		
		DapCtx.ctx().getServiceEngine().handleRequest(
				svcSpec.getServiceName(), 
				request, 
				-1);
	}
	
	/**
	 * Publish/handle given request on service operation with given service name and op name.
	 * @param svcSpec IServiceSpec
	 * param opName String
	 * @param request Object
	 */
	public static void publish(
			final IServiceSpec svcSpec, 
			final String opName, 
			final Object request){
		
		DapCtx.ctx().getServiceEngine().handleRequest(
				ServiceIdHelper.createServiceId(svcSpec.getServiceName(), opName), 
				request, 
				-1);
	}
	
	/**
	 * Publish/handle given request on service operation with given service name and op name.
	 * @param svcSpec IServiceSpec
	 * @param opName String
	 * @param request Object
	 * @param callback IDapSvcCallback
	 */
	public static void publish(
			final IServiceSpec svcSpec, 
			final String opName, 
			final Object request, 
			final IDapSvcCallback callback){
		
		DapCtx dapCtx = DapCtx.ctx();
		String svcId = ServiceIdHelper.createServiceId(svcSpec.getServiceName(), opName);
		int handlerIndex = dapCtx.getServiceEngine().register(svcId, callback);
		dapCtx.getServiceEngine().handleRequest(
				svcId, 
				request, 
				handlerIndex);
	}
	
	//
	// Private
	//
	private int register(String svcName, String opName, IDapSvcCallback handler){
		return register(ServiceIdHelper.createServiceId(svcName, opName), handler);
	}
	
	private int register(String svcId, IDapSvcCallback handler){
		
		DapCtx dapCtx = DapCtx.ctx();
		IClientServiceHandlerRegistry registry = dapCtx.getDsfSvcRegistry();
		
		// Active Mode
		if (DapCtx.ctx().isActiveMode()){
			List<IDapSvcCallback> callbacks = getResponseHandlers(svcId, true);
			for (int i=0; i<callbacks.size(); i++){
				if (callbacks.get(i) == handler){
					return i;
				}
			}
			int index = add(svcId, handler);
			if (index >= 0){
				if (dapCtx.getDapHost() == null){
					registry.registerSvcRespHandler(svcId, handler.getSvcCallbackAdapter(svcId, index));
				}
				else {
					dapCtx.getDapHost().registerSvcHandler(svcId, handler);
				}
			}
			return index;
		}

		// Translate|Web Mode
		IJsFunc callback = null;
		if (handler.getProxySvcCallbacks() != null){
			callback = handler.getProxySvcCallbacks().get(svcId);
		}
		
		if (callback != null){
			registry.registerSvcRespHandler(svcId, callback);
			return -1;
		}

		throw new DsfRuntimeException("js proxy handlers are required for non-active mode: " +
				"mode=" + DapCtx.ctx().getExeMode() +
				", svcId=" + svcId +
				", handlerType=" + handler.getClass().getName());
	}
	
//	private void handleRequest(IServiceSpec svcSpec, String opName, Object request){
//		String svcId = ServiceIdHelper.createServiceId(svcSpec.getServiceName(), opName);
//		DapHostMessage hostMsg = new DapHostMessage();
//		hostMsg.setSvcId(svcId);
//		hostMsg.setRequest(request);
//		
//		executeJsPublish(hostMsg, svcId, -1);
//	}
	
	private void handleRequest(String svcId, Object request, int index){
		
		DapHostMessage hostMsg = new DapHostMessage();
		hostMsg.setSvcId(svcId);
		hostMsg.setRequest(request);
		
		executeJsPublish(hostMsg, svcId, index);
	}
	
	//
	// Package protected
	//
	List<IDapSvcCallback> getResponseHandlers(final String id){
		List<IDapSvcCallback> list = getResponseHandlers(id, false);
		if (list == null){
			return Collections.emptyList();
		}
		else {
			return Collections.unmodifiableList(list);
		}
	}
	
	synchronized Map<String,List<IDapSvcCallback>> getAllResponseHandlers(){
		return Collections.unmodifiableMap(m_respHandlers);
	}
	
	//
	// Private
	//
	private synchronized int add(final String elemId, final IDapSvcCallback listener){
		
		if (elemId == null){
			return -1;
		}
		
		List<IDapSvcCallback> listeners = getResponseHandlers(elemId, true);
		if (!listeners.contains(listener)){
			listeners.add(listener);
			return listeners.size()-1;
		}
		else {
			return -1;
		}
	}
	
	private synchronized List<IDapSvcCallback> getResponseHandlers(final String id, boolean create){
		if (id == null){
			return Collections.emptyList();
		}
		List<IDapSvcCallback> list = m_respHandlers.get(id);
		if (list == null){
			if (!create){
				return Collections.emptyList();
			}
			else {
				list = new ArrayList<IDapSvcCallback>(2);
				m_respHandlers.put(id, list);
			}
		}
		return list;
	}
	
	private static final String PTY_DAP_HOST_MSG = "dapHostMsg";
	private static final String SVC_RESP_HANDLER_REG_0 = "var _s=vjo.dsf.ServiceEngine, $se=_s.register;var _r=vjo.Registry;";
	private static final String SVC_RESP_HANDLER_REG_01 = "$se(1,\"{0}\",function(message) ";
	private static final String SVC_RESP_HANDLER_REG_02 = "{message.trspType =\"Remote\"; message.v = \"1\"; message.svcConfig = new vjo.dsf.SvcConfig(\"GET\", \"/V4Ajax\"); message.svcConfig.reqtMarshalling = \"JSON\"; message.svcConfig.respMarshalling = \"JSON\";});";
	private static final String SVC_RESP_HANDLER_REG_1 = "$se(4,\"{0}\",function (message) ";
	private static final String SVC_RESP_HANDLER_REG_2 = "{";
	private static final String SVC_RESP_HANDLER_REG_3 = "dapHost.getSvcRespHandlers(\"{0}\")[{1}].onResponse(message); ";
	private static final String SVC_RESP_HANDLER_REG_4 = "});";
	private static final String SCRIPT_PUBLISH_MSG = "vjo.dsf.ServiceEngine.handleRequest(" + PTY_DAP_HOST_MSG + ");";

	private void executeJsPublish(final DapHostMessage msg, final String svcId, int index){
		try {
			AWindow window = DapCtx.ctx().getWindow();
			
			Object wrappedOut = Context.javaToJS(msg, window);
			ScriptableObject.putProperty(window, PTY_DAP_HOST_MSG, wrappedOut);

			StringBuilder sb = new StringBuilder();
			if (index >= 0){
				sb.append(SVC_RESP_HANDLER_REG_0);
				sb.append(MessageFormat.format(SVC_RESP_HANDLER_REG_01, new Object[]{svcId}));
				sb.append(SVC_RESP_HANDLER_REG_02);
				sb.append(MessageFormat.format(SVC_RESP_HANDLER_REG_1, new Object[]{svcId}));
				sb.append(SVC_RESP_HANDLER_REG_2);
				sb.append(MessageFormat.format(SVC_RESP_HANDLER_REG_3, new Object[]{svcId, String.valueOf(index)}));
				sb.append(SVC_RESP_HANDLER_REG_4);
			}
			sb.append(SCRIPT_PUBLISH_MSG);
			String script = sb.toString();

			System.out.println("Rhino => " + script);
			
			ScriptExecutor.executeScript(script, window);
		}
		catch(Exception e){
			DapCtx.ctx().getDapConfig().getInfoCollector().log("Fail to execute JS service request", e);
		}
	}
}
