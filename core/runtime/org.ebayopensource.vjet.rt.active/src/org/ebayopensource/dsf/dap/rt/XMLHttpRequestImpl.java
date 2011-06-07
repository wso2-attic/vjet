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
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.client.AXmlParser;
import org.ebayopensource.dsf.active.client.ActiveObject;
import org.ebayopensource.dsf.active.client.WindowFactory;
import org.ebayopensource.dsf.active.util.AsyncTask;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.proxy.INativeJsFuncProxy;
import org.ebayopensource.dsf.dap.proxy.JFunctionX;
import org.ebayopensource.dsf.dap.rt.IDapHttpClient.IDapCallback;
import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.jsnative.Document;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.Location;
import org.ebayopensource.dsf.jsnative.XMLHttpRequest;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.services.ConnectionProtocolEnum;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.Undefined;

@SuppressWarnings("serial")
/**
 * XMLHttpRequestImpl is an eBay implementation for XMLHttpRequest.
 * It caters DAP for AJAX capability. Any XMLHttpRequest features run in Rhino will be implemented here.
 */
public final class XMLHttpRequestImpl extends ActiveObject  
	implements XMLHttpRequest {
	
	private String m_method;
	private String m_url;
	private boolean m_async = true;
	private short m_readyState;
	private short m_status;
	private String m_responseText;
	private Map<String,String> m_requestHeaders;
	private Map<String,String> m_responseHeaders;
	private String m_user;
	private String m_password;
	private String m_statusText;
	private Function m_onreadystatechange;
	private AWindow m_window;
	private Function m_hitchFunc;

	private static final String[] InternalFuns = new String[] {"invokeCallback"};

	private IDapHttpClient m_client = DapCtx.ctx().getDapConfig().getHttpClient();
	
	private boolean m_sendFlag = false;
	
	private boolean m_errorFlag = false;
	
	private AsyncTask m_asyncTask;
	
	private IDapCallback m_callback = new IDapCallback() {

		public void onComplete(DapHttpResponse resp) {
			if(m_sendFlag){//for abort
				doResponse(resp);
			}
		}

		public void onTimedOut() {
			if(m_sendFlag){//for abort
				doResponse(null);	
			}
		}
	};
	
	public XMLHttpRequestImpl() {
		// Only for Rhino defineClass method
	}
		
	public XMLHttpRequestImpl(AWindow window) {
		m_window = (window == null ? (AWindow) WindowFactory.createWindow() : window);
		populateScriptable(XMLHttpRequestImpl.class, m_window.getBrowserType());
		defineFunctionProperties(InternalFuns, XMLHttpRequestImpl.class, ScriptableObject.EMPTY);
		
		DapCtx.ctx().setXmlHttpReq(this); 
		m_hitchFunc = (Function)m_window.getContext().evaluateString(
				m_window.getScope(),
				"function(ctx, fn) {return function() {return fn.call(ctx);}}",
				"hitch", 0, null);
	}
	
	//
	// Satisfy XMLHttpRequest
	//
	public void open(String method, String url, boolean async, String user,
			String password){
		validateOpen(method, url);
		checkCrossDomain(url);	
		m_url = url; //check URL 1st
		m_method = method;
		m_async = async;
		m_user = user == null? "" : user;
		m_password = password == null? "" : password;
		m_readyState = XMLHttpRequest.OPENED;
	}

	public void send(Object request){
		if(m_readyState != XMLHttpRequest.OPENED || m_sendFlag){
			throw new DsfRuntimeException("INVALID_STATE_ERR");
		}
		if(ConnectionProtocolEnum.GET.getName().equals(m_method)){
			if(request != null && !(request instanceof Undefined)){
				throw new DsfRuntimeException("Invalid data");
			}
		}
		m_sendFlag = true;
		DapHttpRequest dapReq = buildDapRequest(request);

		if(m_async){	
			m_asyncTask = new AsyncTask();
			m_window.addTask(m_asyncTask);
			m_client.send(dapReq, m_callback);
		}else{
			DapHttpResponse resp = m_client.send(dapReq);
			doResponse(resp);
		}
	}
	
	public void abort() {
		//1. abort the send alogrithm
		//2. set the resp entity body to null, err flg to true and remove req headers
		//3. cancel network activity
		//4. if state is UNSENT, OPENED and send flg is false, or DONE, go to 5,
		//   else set state to DONE, send flg to false, and dispatch a readstatechange event
		//5. sent state to UNSENT(Don't dispatch the readystatechange event)
		
		if(m_async){
				//For aborting the send algorithm, just leave the m_sendflag as false			
				m_errorFlag = true;
				
				// check for presence of request headers
				if (null!= m_requestHeaders)
				  m_requestHeaders.clear();
				
				//TODO: cancel network activity (Change DapHttpClient?)
				if(m_readyState == UNSENT
					|| (m_readyState == OPENED && m_sendFlag == false)
					|| m_readyState == DONE){
					m_readyState = UNSENT;
				}else{
					m_readyState = DONE;
					m_sendFlag = false;
					m_asyncTask.cancel();
				}	
		}
	}

	/**
	 * The request body as a DOM string.  
	 * This data is converted to a string of single-byte characters 
	 * by truncation (removing the high-order byte of each character).
	 */
	public void sendAsBinary(String body) {
		int length = body == null ? 0 : body.length();
		if(length > 0){
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < body.length(); i++) {
				char c = body.charAt(i);
				byte b = (byte)c;
				sb.append((char)b);
			}
			send(sb.toString());
		}else{
			send(null);
		}
	}
	
	public String getResponseHeader(String header) {
		if (m_responseHeaders == null) {
			return null;
		}
		return m_responseHeaders.get(header);
	}
	
	public void setRequestHeader(String name, String value) {
		if(m_readyState != XMLHttpRequest.OPENED || m_sendFlag){
			throw new DsfRuntimeException("INVALID_STATE_ERR");
		}
		if (m_requestHeaders == null) {
			m_requestHeaders = new HashMap<String, String>();
		}
		m_requestHeaders.put(name, value);
	}
	
	public short getReadyState() {
		return m_readyState;
	}

	public short getStatus() {		
		//follow W3C standard, 
		//if not available, raise an INVALID_STATE_ERR
//		if(m_status == 0 || m_statusText == null){
//			throw new DsfRuntimeException("INVALID_STATE_ERR");
//		}
		return m_status;
	}

	public String getResponseText() {		
		//follow W3C standard, 
		//if not available, raise an INVALID_STATE_ERR
//		if(m_status == 0 || m_statusText == null){
//			throw new DsfRuntimeException("INVALID_STATE_ERR");
//		}
		return m_responseText;
	}

	public String getAllResponseHeaders() {
		if (m_responseHeaders == null || m_responseHeaders.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String key : m_responseHeaders.keySet()) {
			sb.append(key).append(": ").append(m_responseHeaders.get(key)).append('\n');
		}
		return sb.toString();
	}

	public Document getResponseXML() {
		if(m_readyState != DONE){
			return null;
		}
		if(getResponseText() == null){
			return null;
		}
		Document doc = AXmlParser.parse(getResponseText().getBytes());
		return doc;
	}

	public String getStatusText() {
		return m_statusText;
	}

	public org.ebayopensource.dsf.jsnative.global.Function getUpload() {
		throw new DsfRuntimeException("To be implemented");
	}

	public void setUpload(
			org.ebayopensource.dsf.jsnative.global.Function nsIXMLHttpRequestUpload) {
		throw new DsfRuntimeException("To be implemented");
		
	}
	
	public boolean getWithCredentials() {
		throw new DsfRuntimeException("To be implemented");
	}

	public void setWithCredentials(boolean value) {
		throw new DsfRuntimeException("To be implemented");
		
	}

	public Object getOnreadystatechange() {
		return m_onreadystatechange;
	}

	@SuppressWarnings("unchecked")
	public void setOnreadystatechange(Object handler) {
		if (handler instanceof Function) {
			m_onreadystatechange = (Function)handler;			
		}
		else if (handler instanceof INativeJsFuncProxy) {
			m_onreadystatechange = ((INativeJsFuncProxy)handler).getJsNative();
		}	
	}

	//
	//API 
	//

	/**
	 * internal method to be invoked by window task executor
	 * (need to be public simply due to rhino limitation)
	 */
	public void invokeCallback() {
		if (m_onreadystatechange != null) {
			m_onreadystatechange.call(m_window.getContext(), m_window, this, new Object[]{this});
		}
	}
	
	//
	//Private
	//	
		
	//1. for HTTP methods: GET, POST, PUT, DELETE or HEAD
	//2. null or undefined URI/URL
	private void validateOpen(String method, String url) {
		if(!(
				ConnectionProtocolEnum.GET.getName().equals(method)
				|| ConnectionProtocolEnum.POST.getName().equals(method)
				//TODO: below are not supported yet in ConnectionProtocolEnum
//				|| ConnectionProtocolEnum.PUT.getName().equals(method)
//				|| ConnectionProtocolEnum.DELETE.getName().equals(method)
//				|| ConnectionProtocolEnum.HEAD.getName().equals(method)
			)){
			throw new DsfRuntimeException("SYNTAX_ERR");
		}
		if(url == null 
				|| "".equals(url.trim())
				|| "null".equals(url.trim())
				|| "undefined".equals(url.trim())
				){
			throw new DsfRuntimeException("SYNTAX_ERR");
		}
	}
	
	private void checkCrossDomain(String url) {
		String tgtUrl = url;
		if(tgtUrl == null || tgtUrl.startsWith("/")){//relative url, no need to check			
			return;
		}
		if(!(url.indexOf("://")>0)){//say: www.google.com
			tgtUrl = "http://" + tgtUrl;
		}
		String[] curDomain = new String[2];
		HtmlDocument doc = m_window.getDocument();
		if(doc != null){
			Location loc = doc.getLocation();
			String currentUrl = loc.getHref();
			if(currentUrl.startsWith("file:/")||currentUrl.startsWith("jar:file:/")){
				//TODO: Any better ideas to save test from failure?
				//An example is : file:/C:/ccviews/liger_dev/v4darwin/DSFAggregatorTests/bin/test/engine/req/json/rt/ErrHandle.html
				return;
			}
			parseDomain(currentUrl, curDomain);
		}else{
			//basically it's in tests
			return;
		}
		String[] urlDomain = new String[2];
		parseDomain(tgtUrl, urlDomain);
		boolean isValid = true;
		if (!urlDomain[0].equalsIgnoreCase(curDomain[0])){
			isValid = false;
		}else{
			BrowserType browser = DapCtx.ctx().getWindow().getBrowserType();
			if(browser.isIE()){
				//IE & Chrome only check host name
			}else if(browser.isFireFox()
					|| browser.isSafari()){
				//These browsers also check port
				if(!urlDomain[1].equalsIgnoreCase(curDomain[1])) { 
					isValid = false;
				}
			}else{
				//by default, only checks hosts
			}
		}
		if(!isValid){
			throw new DsfRuntimeException("Access to restricted URI denied");
		}
	}
	
	private void parseDomain(String url, String[] domain){
		int idx = url.indexOf("://") + 3;
		idx = idx >= 0 ? idx : 0;
		int idxS = url.indexOf("/", idx);
		idxS = idxS >= 0 ? idxS : url.length();
		int idxC = url.indexOf(":", idx);
		if(idxC > 0){
			domain[0] = url.substring(idx, idxC);
			domain[1] = url.substring(idxC+1, idxS);
		}else{
			domain[0] = url.substring(idx, idxS);
			domain[1] = "80"; //default http port
		}
	}

	private DapHttpRequest buildDapRequest(Object request) {
		DapHttpRequest dapReq = new DapHttpRequest(m_method, m_url, m_async);
		
		if(request != null){
			dapReq.setRawData(request.toString());
		}
		
		if(m_requestHeaders != null){
			for(Entry<String, String> entry : m_requestHeaders.entrySet()){
				dapReq.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		
		return dapReq;

	}
	
	private void doResponse(DapHttpResponse resp) {
		if(resp != null){
			//copy header
			m_responseHeaders = new HashMap<String, String>();
			if(resp.getResponseHeaders() != null){
				m_responseHeaders.putAll(resp.getResponseHeaders());
			}			

			m_responseText = resp.getResponseText();
			m_status = resp.getStatusCode();
			m_statusText = resp.getStatusText();
		}else{
			//Timeout, from client perspective, server failed to return anything.
			//So leave the fields null.
//			m_status = 408;
//			m_statusText = "408 Request Timeout";
//			m_responseText = "HTTP ERROR: 408 Request Timeout";
		}
		
		if(m_status == 0 || m_statusText == null){
			m_errorFlag = true;
		}
		
		m_readyState = XMLHttpRequest.DONE;
		
		//invoke callback by window's task executor to achieve "single-threaded js execution"
		if (m_onreadystatechange != null) {
			Function hitch = hitch("invokeCallback", void.class);
			if (m_async){
				//m_asyncTask = new AsyncTask();
				//m_asyncTask.setJsCode(hitch);
				m_window.addTask(m_asyncTask);
			}
			else {
				m_window.addTask(hitch);
			}
		}
		else if (m_async){
			m_asyncTask.cancel();
		}
	}
	
	/**
	 * create a hitched function which was bounded to "this" object
	 */
	private Function hitch(String methodName, Class<?> returnType) {

		return (Function)m_hitchFunc.call(
			m_window.getContext(),
			m_window.getScope(),
			null,
			new Object[]{this, JFunctionX.def(this, methodName, returnType)});
		
	}

	//
	// Package protect
	//
	
	void setReadyState(short readyState) {
		m_readyState = readyState;
	}
	
	void setStatus(short status) {
		m_status = status;
	}

	void setResponseText(String responseText) {
		m_responseText = responseText;
	}

	@Override
	@AExclude
	public void XMLHttpRequest() {
		// TODO Auto-generated method stub
		
	}
}