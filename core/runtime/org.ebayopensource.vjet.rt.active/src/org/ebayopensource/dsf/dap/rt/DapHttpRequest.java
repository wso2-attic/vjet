/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.Location;

public class DapHttpRequest {
	
	private static final String HTTP = "http";
//	private static final String HTTPS = "https";

	private String m_method;
	private String m_url;
	private String m_host;
	private boolean m_async = false;
	private long m_timeout = 0;
	private String m_rawData;
	private Map<String,String> m_requestHeaders;
	
	//disabled to remove v4 specific contents from DHR
//	private boolean m_forceSameOrigin = false;
//	private Object m_reqData;
//	private PayloadTypeEnum m_payloadType;
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param method String
	 * @param url String
	 * @param async boolean
	 */
	public DapHttpRequest(final String method, final String url, final boolean async){
		if (url == null){
			throw new RuntimeException("url is null");
		}
		String fullUrl = checkRelativeUrl(url);
		if (!fullUrl.toLowerCase().startsWith(HTTP) || !fullUrl.contains("://")){
			throw new RuntimeException("Invalid protocol: " + fullUrl);
		}
		
		m_method = method;
		m_url = fullUrl;
		
		int start = fullUrl.indexOf("://") + 3;
		int end = fullUrl.indexOf("/", start);
		if (end > start){
			m_host = fullUrl.substring(start, end);
//			m_uri = fullUrl.substring(end);
		}
		else {
			m_host = fullUrl.substring(start);
//			m_uri = "/";
		}
		
		//check X-domain in XmlHttpRequestImpl
//		if (m_forceSameOrigin){
//			String thisHost = DapCtx.ctx().getHost();
//			if (!m_host.toLowerCase().startsWith(thisHost.toLowerCase())) {
//				DsfExceptionHelper.chuck("Domain Mismatch Exception: " + thisHost + " cannot communicate with " + m_url);
//			}
//		}
		
		m_async = async;
	}
	
	/**
	 * Constructor 
	 * @param method String
	 * @param url String
	 * @param callback ICallback
	 */
	public DapHttpRequest(final String method, final String url){
		this(method, url, true);
	}

	/**
	 * Constructor 
	 * @param method String
	 * @param url String
	 * @param callback ICallback
	 * @param user String
	 * @param password String
	 */
	public DapHttpRequest(final String method, final String url, final boolean async, 
			final String user, final String password){
		
		this(method, url, async);

//		m_user = user == null? "" : user;
//		m_password = password == null? "" : password;
	}
	
	//
	// API
	//
	public String getHost(){
		return m_host;
	}
	
	public String getUrl(){
		return m_url;
	}
	
	public String getMethod(){
		return m_method;
	}
	
//	public void setReqData(Object reqData){
//		m_reqData = reqData;
//	}
//	
//	public Object getReqData(){
//		return m_reqData;
//	}
	
	public void setRawData(String rawData){
		m_rawData = rawData;
	}
	
	public String getRawData(){
		return m_rawData;
	}
	
	public void setTimeout(long timeout){
		m_timeout = timeout;
	}
	
	public long getTimeout(){
		return m_timeout;
	}
	
	public void setRequestHeader(String name, String value) {
		if (m_requestHeaders == null) {
			m_requestHeaders = new HashMap<String, String>();
		}
		m_requestHeaders.put(name, value);
	}
	
	public Map<String, String> getRequestHeaders(){
		if (m_requestHeaders == null){
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(m_requestHeaders);
	}

//	public PayloadTypeEnum getPayloadType() {
//		return m_payloadType;
//	}
//
//	public void setPayloadType(PayloadTypeEnum payloadType) {
//		m_payloadType = payloadType;
//	}
	
	public boolean isAsync(){
		return m_async;
	}
		
	private String checkRelativeUrl(String url){
		String fullUrl = url;
		if(url != null && !"".equals(url)){
			if(url.indexOf("://") > 0){
				//full URL, leave it as is
			}else{
				//relative URL, resolve it with baseURI
				String prefix = "";
				
				HtmlDocument doc = DapCtx.ctx().getWindow().getDocument();
				if(doc != null){
					Location loc = doc.getLocation();
					String currentUrl = loc.getHref();
					int idx = currentUrl.indexOf("://") + 3;
					idx = currentUrl.indexOf("/", idx);
					prefix = currentUrl.substring(0, idx);	
				}
				
				if(!url.startsWith("/")){
					fullUrl = "/" + url;
				}
				fullUrl = prefix + fullUrl;
			}		
		}
		return fullUrl;
	}
}
