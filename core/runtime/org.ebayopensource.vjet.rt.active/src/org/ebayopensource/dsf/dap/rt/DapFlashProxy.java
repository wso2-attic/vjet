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
import org.ebayopensource.dsf.active.dom.html.ADocument;
import org.ebayopensource.dsf.active.dom.html.AHtmlDocument;
import org.ebayopensource.dsf.active.dom.html.AHtmlObject;
import org.ebayopensource.dsf.dap.rt.IDapHttpClient.IDapCallback;
import org.ebayopensource.dsf.html.dom.DObject;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.service.PayloadTypeEnum;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.NativeArray;
import org.mozilla.mod.javascript.NativeObject;
import org.mozilla.mod.javascript.Scriptable;

public class DapFlashProxy extends AHtmlObject implements FlashProxy, IDapCallback {

	private static final long serialVersionUID = 1L;
	
	private static final long DEFAULT_TIMEOUT = 2000;

	private String m_respMarshalling;
	private long m_timeout;
	
	//
	// Constructor
	//
	public DapFlashProxy(AHtmlDocument doc, DObject node) {
		super(doc, node);
		populateScriptable(DapFlashProxy.class, doc == null ? 
			BrowserType.IE_6P : doc.getBrowserType());
	}
	
	//
	// Satisfy FlashProxy
	//
	public void onRemoteCall(NativeObject msg){
		
		System.out.println("DapFlashProxy::onRemoteCall," + msg);
		
		NativeObject req = (NativeObject)msg.get("request", null);
		NativeObject cfg = (NativeObject)msg.get("svcConfig", null);
		
		String svcUrl = (String)req.get("serviceUrl", req);
		NativeArray headers = (NativeArray)req.get("headers", req);
//		String reqData = (String)req.get("data", req);
//		Object contentType = req.get("contentType", req);
		
//		Object async = cfg.get("async", cfg);
		String method = (String)cfg.get("method", cfg);
//		String reqtMarshalling = (String)cfg.get("reqtMarshalling", cfg);
		m_respMarshalling = (String)cfg.get("respMarshalling", cfg);
		try {
			m_timeout = Long.parseLong((String)cfg.get("timeout", cfg));
		}
		catch(Exception e){
			m_timeout = DEFAULT_TIMEOUT;
		}
		
		DapHttpRequest dhr = new DapHttpRequest(method, svcUrl);
		dhr.setTimeout(m_timeout);
		//TODO fix this based on new DapHttpRequest usage
//		dhr.setReqData(reqData);
//		dhr.setPayloadType(PayloadTypeEnum.get(reqtMarshalling));
		
		if (headers.getLength() > 0) {
			for (int i=0; i<headers.getLength(); i++){
				NativeObject h = (NativeObject)headers.get(i, null);
				String name = (String)h.get("name", null);
				String value = (String)h.get("value", null);
				dhr.setRequestHeader(name, value);
			}
		}

		DapCtx.ctx().getDapConfig().getHttpClient().send(dhr, this);
	}
	
	//
	// Satisfy IDapCallback
	//
	/**
	 * @see IDapCallback#onComplete(DapHttpClient.IDapCallback.DapHttpResponse)
	 */
	public void onComplete(DapHttpResponse resp){
		
		if (resp == null){
			System.out.println("DapFlashProxy::onComplete: null");
			// TODO
			return;
		}
		
		System.out.println("DapFlashProxy::onComplete," + resp.getResponseText());
		
		try {
			AWindow window = DapCtx.ctx().getWindow();
			
			Function callback = (Function)get("callback", null);

			if(!callback.equals(NOT_FOUND)){
				Object[] args = null;
				if (m_respMarshalling == null){
					args = new Object[]{};
				}
				else if (m_respMarshalling.equals(PayloadTypeEnum.XML.getName()) || 
								m_respMarshalling.equals(PayloadTypeEnum.SOAP.getName())){
					ADocument xmlDoc = resp.getResponseXML();
					args = new Object[]{xmlDoc};
				}
				else {
					String respStr = resp.getResponseText();
					args = new Object[]{respStr};
				}
				
				callback.call(
					window.getContext(), 
					callback.getParentScope(), 
					((Scriptable)callback).getParentScope(), 
					args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see IDapCallback#onTimedOut()
	 */
	public void onTimedOut(){
		// TODO
	}
}
