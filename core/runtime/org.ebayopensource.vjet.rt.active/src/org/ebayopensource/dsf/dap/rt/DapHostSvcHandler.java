/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.lang.reflect.Method;
import java.util.List;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcErrorHandler;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcOnAll;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcOnFailure;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcOnSuccess;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcSuccessHandler;
import org.ebayopensource.dsf.dap.svc.IDapHostSvcCallback;
import org.ebayopensource.dsf.dap.svc.IDapSvcCallback;
import org.ebayopensource.dsf.services.ServiceError;
import org.ebayopensource.dsf.services.ServiceResponse;
import org.mozilla.mod.javascript.ScriptableObject;

@SuppressWarnings("serial")
public class DapHostSvcHandler extends BaseScriptable 
	implements IDapHostSvcCallback {
	
	private IDapSvcCallback m_respHandler;
	
	public static final String[] MTD_NAMES = {
		"onResponse",
	};
	
	public DapHostSvcHandler(){}
	
	public ScriptableObject onResponse(ScriptableObject src){
		if (src instanceof DapHostMessage){
			
			DapHostMessage msg = (DapHostMessage)src;

			msg.setResponse(DapCtx.ctx().getXmlHttpReq().getResponseText());
			
			handle(msg);
		}
		return null;
	}
	
	protected void handle(DapHostMessage msg){
		
		ServiceResponse resp = msg.getSvcResponse();
		if (resp == null){
			throw new DsfRuntimeException("resp is null");
		}
		
		String svcName = msg.getSvcId();
		String opName = msg.getOpName();
		List<ServiceError> error = resp.getErrors();
		// Single entry
		Class cls = m_respHandler.getClass();
		DapCtx dapCtx = DapCtx.ctx();
		for (Class itf: cls.getInterfaces()){
			if (!IDapSvcCallback.class.isAssignableFrom(itf)){
				continue;
			}
			for (Method m : itf.getMethods()) {
				if (m.getAnnotation(ADapSvcOnAll.class) != null){
					try {
						m.invoke(m_respHandler, resp.getData(), error);
					} catch (Exception e) {
						dapCtx.getDapConfig().getInfoCollector().log("fail to invoke " + m.getName(), e);
					}
					return;
				}
			}
		}
		// Two entries
		if (error != null && error.size() > 0){
			handleError(msg.getSvcId(), opName, error);
		}
		else {
			handleResponse(svcName, opName, resp.getData());
		}
	}
	
	//
	// Package protected
	//
	DapHostSvcHandler(IDapSvcCallback respHandler){
		
		m_respHandler = respHandler;

		defineFunctionProperties(MTD_NAMES);
	}
	
	private void handleResponse(String svcName, String opName, Object data){
		ADapSvcSuccessHandler callback;
		Class cls = m_respHandler.getClass();
		DapCtx dapCtx = DapCtx.ctx();
		
		// Annotation-based callback
		for (Method m : cls.getMethods()) {
			callback = m.getAnnotation(ADapSvcSuccessHandler.class);
			if (callback != null && callback.toString().contains("=" + opName)){
				try {
					m.invoke(m_respHandler, data);
				} catch (Exception e) {
					dapCtx.getDapConfig().getInfoCollector().log("fail to invoke " + m.getName(), e);
				}
				return;
			}
		}
		
		// Interface-based callback
		for (Class itf: cls.getInterfaces()){
			if (!IDapSvcCallback.class.isAssignableFrom(itf)){
				continue;
			}
			for (Method m : itf.getMethods()) {
				if (m.getAnnotation(ADapSvcOnSuccess.class) != null){
					try {
						m.invoke(m_respHandler, data);
					} catch (Exception e) {
						dapCtx.getDapConfig().getInfoCollector().log("fail to invoke " + m.getName(), e);
					}
					return;
				}
			}
		}
	}
	
	private void handleError(final String svcName, final String opName, final List<ServiceError> error){

		DapCtx dapCtx = DapCtx.ctx();
		
		Class cls = m_respHandler.getClass();
		
		// Interface-based callback
		for (Class itf: cls.getInterfaces()){
			if (!IDapSvcCallback.class.isAssignableFrom(itf)){
				continue;
			}
			for (Method m : itf.getMethods()) {
				if (m.getAnnotation(ADapSvcOnFailure.class) != null){
					try {
						m.invoke(m_respHandler, error);
					} catch (Exception e) {
						dapCtx.getDapConfig().getInfoCollector().log("fail to invoke " + m.getName(), e);
					}
					return;
				}
			}
		}
		
		// Annotation-based callback
		for (Method m : cls.getMethods()) {
			ADapSvcErrorHandler a = m.getAnnotation(ADapSvcErrorHandler.class);
			if (a == null){
				continue;
			}
			try {
				m.invoke(m_respHandler, error);
			} catch (Exception e) {
				dapCtx.getDapConfig().getInfoCollector().log("fail to invoke " + m.getName(), e);
			}
		}
	}
}
