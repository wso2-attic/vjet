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
import org.ebayopensource.dsf.dap.svc.IDapHostSvcMsg;
import org.ebayopensource.dsf.json.serializer.SerializationException;
import org.ebayopensource.dsf.service.ServiceIdHelper;
import org.ebayopensource.dsf.service.serializer.JsonSerializer;
import org.ebayopensource.dsf.services.ServiceResponse;

@SuppressWarnings("serial")
public final class DapHostMessage extends BaseScriptable 
	implements IDapHostSvcMsg {
	
	private String m_svcId;
	private String m_objType = "dsf_Message";
	private Object m_request;
	private Object m_response;
	private String m_rawRequest = "";
	private Object m_clientContext;
	private String m_trspType = "InProc";
	private String m_status;
	private Object m_svcConfig;
//	private boolean m_returnData = true;
	private String m_trace = "";
	
	private static final String[] PTY_NAMES = {
		"objType",
		"svcId",
		"request",
		"response",
		"rawRequest",
		"clientContext",
		"trspType",
		"status",
		"svcConfig",
		"trace",
	};
	
	private static final String[] MTD_NAMES = {
		"valueOf",
	};
	
	public DapHostMessage(){
		
		defineProperties(PTY_NAMES);
		defineFunctionProperties(MTD_NAMES);
	}

	public Object getClientContext() {
		return m_clientContext;
	}

	public void setClientContext(Object clientContext) {
		m_clientContext = clientContext;
	}

	public String getObjType() {
		return m_objType;
	}

	public void setObjType(String objType) {
		m_objType = objType;
	}

	public String getRawRequest() {
		return m_rawRequest;
	}

	public void setRawRequest(String rawRequest) {
		m_rawRequest = rawRequest;
	}

	public Object getRequest() {
		
		if (m_request == null || m_request instanceof String){
			return m_request;
		}

		JsonSerializer rpcSerializer = JsonSerializer.getInstance();
		String jsonReq = null;
		try {
			jsonReq = rpcSerializer.serialize(m_request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AWindow w = DapCtx.ctx().getWindow();
		return w.getContext().evaluateString(w, "(" + jsonReq + ")", "dap", 1, null);
	}

	public void setRequest(Object request) {
		m_request = request;
	}

	public Object getResponse() {
		return m_response;
	}
	
	public ServiceResponse getSvcResponse(){
		
		String resp = m_response.toString();
		if (resp != null){
			ServiceResponse svcResp = new ServiceResponse();
			JsonSerializer rpcSerializer = JsonSerializer.getInstance();
		
			try {
				svcResp.setData(rpcSerializer.deserialize(resp, ServiceResponse.class, null));
			} catch (SerializationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return (ServiceResponse)svcResp.getData();
		}
		
		// TODO: handle other cases
		
		return null;
	}

	public void setResponse(Object response) {
		m_response = response;
	}

//	public boolean isReturnData() {
//		return m_returnData;
//	}
//
//	public void setReturnData(boolean returnData) {
//		m_returnData = returnData;
//	}

	public String getStatus() {
		return m_status;
	}

	public void setStatus(String status) {
		m_status = status;
	}

	public Object getSvcConfig() {
		return m_svcConfig;
	}

	public void setSvcConfig(Object svcConfig) {
		m_svcConfig = svcConfig;
	}

	public String getSvcId() {
		return m_svcId;
	}

	public void setSvcId(String svcId) {
		m_svcId = svcId;
	}
	
	public String getOpName(){
		return ServiceIdHelper.getOperationName(m_svcId);
	}

	public String getTrace() {
		return m_trace;
	}

	public void setTrace(String trace) {
		m_trace = trace;
	}

	public String getTrspType() {
		return m_trspType;
	}

	public void setTrspType(String trspType) {
		m_trspType = trspType;
	}
	
	public Object valueOf(String type) {
		if (type.equals("boolean")){
			return Boolean.TRUE;
		}
		else if (type.equals("object")){
			return this;
		}
		else if (type.equals("number")){
			return "0";
		}
		
		return null;
	}
}
