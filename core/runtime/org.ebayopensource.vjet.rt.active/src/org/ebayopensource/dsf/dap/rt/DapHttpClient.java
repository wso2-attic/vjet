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

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

@SuppressWarnings("serial")
public class DapHttpClient implements IDapHttpClient {
	
	/*
	 * A default timeout value for socket connection, 
	 * which helps to improve system performance. 
	 */
	public static final int SOCKET_TIMEOUT = 50000;
	
	private static final String UTF8 = "utf-8";
	
	private List<IDapHttpListener> m_listeners = new ArrayList<IDapHttpListener>();

	//Below are helping to provide meaning full response on error.
	public static final String HTTP_ERR = "HTTP ERROR: ";
	
	public static final String PAGE_LOAD_ERR = "Page Load Error: ";
	
	private static final String ERR_CONN = PAGE_LOAD_ERR+"DapHttp can't establish a connection to remote server";
	
	private static final String ERR_TIMEOUT = HTTP_ERR+"408 Request Timeout";
	
	public static final String ACTIVE_JS_MODE_HEADER = "ACTIVE_JS_MODE";
	
	

	//
	// Satisfy IDapHttpClient
	//
	/**
	 * @see IDapHttpClient#send(DapHttpRequest)
	 * @throws DsfRuntimeException for socket timeout
	 * Send the request in sync mode
	 */
	public DapHttpResponse send(DapHttpRequest request){
		return execute(request);
	}
	
	/**
	 * @see IDapHttpClient#send(DapHttpRequest, IDapCallback)
	 * Send the request in async mode. When done, notify result via callback.
	 */
	public void send(DapHttpRequest request, IDapCallback callback){
		AsyncRunner runner = new AsyncRunner(this, request, callback);
		Thread t = new Thread(runner);
		t.setDaemon(true);
		t.start();
		if (request.getTimeout() > 0){
			AsyncTimer timer = new AsyncTimer(runner, request.getTimeout());
			timer.start();
		}
	}
	
	/**
	 * @see IDapHttpClient#addListener(IDapHttpListener)
	 */
	public void addListener(IDapHttpListener listener){
		if (listener != null){
			m_listeners.add(listener);
		}
	}
	
	//
	// Private
	//
	
	private String removeProtocol(String url){
		int idx = url.indexOf("://");
		idx = url.indexOf("/", idx+3);
		if(idx==-1){
			return url;
		}
		return url.substring(idx);
	}

	/*
	 * 
	 */
	private DapHttpResponse execute(DapHttpRequest dapRequest){
		
//		for (IDapHttpListener listener: m_listeners){
//			listener.onHttpRequest(dapRequest);
//		}
//		
//		Request request = new Request(dapRequest.getHost(), removeProtocol(dapRequest.getUrl()));
//		
//		SvcInvocationConfig svcConfig = new SvcInvocationConfig("DapAjax");
//		svcConfig.setSvcHost(dapRequest.getHost());
//		svcConfig.createConnectionConfig(4, 8, (int)dapRequest.getTimeout(), 2, false, false, false);
//		svcConfig.createSocketConfig
//			(Integer.valueOf(SOCKET_TIMEOUT), null, null, null, null, null);
//		svcConfig.createHttpConfig(false, false, false);
//		HttpClient client = new HttpClient(svcConfig, null);
//		
//		if ("GET".equalsIgnoreCase(dapRequest.getMethod())){
//			request.setMethod(Request.GET);
//		}
//		else {
//			if(dapRequest.getRawData() != null){
//				try {//TODO: utf-8?
//					request.setRawData(dapRequest.getRawData().getBytes(UTF8));
//				} catch (UnsupportedEncodingException e) {
//					//TODO: handle error
//					e.printStackTrace();
//				}
//			}
//			request.setMethod(Request.POST);
//		}
//		
//		request.setParamEncoding(UTF8);
//		for (Entry<String, String> header : dapRequest.getRequestHeaders().entrySet()) {
//			request.addHeader(header.getKey(), header.getValue());
//		}
//		request.addHeader(ACTIVE_JS_MODE_HEADER, "true");
//		
//		Response response = null;
		DapHttpResponse dapResponse = null;
//		try {
//			response = client.invoke(request);			
//			dapResponse = buildDapResponse(response);
//		}catch(Exception ex){
//			dapResponse = buildDapResponse(ex);
//		}
//		
//		for (IDapHttpListener listener: m_listeners){
//			listener.onHttpResponse(dapRequest, dapResponse);
//		}
		
		return dapResponse;
	}
	
	private DapHttpResponse buildDapResponse(Exception ex) {
		DapHttpResponse dapResponse = new DapHttpResponse();
		
//		if(ex instanceof ReceivingTimeoutException){
//			//This is Socket timeout, could be considered as 408 from server
//			dapResponse.setResponseText(ERR_TIMEOUT);
//			dapResponse.setStatusCode((short)408);
//		}else{
//			//Could be below:
////			ConnectionException -- This happened when client cannot find server
////			BaseClientSideException -- Thrown by invoke()
////			all other exceptions
//			dapResponse.setResponseText(ERR_CONN);
//		}
		
		dapResponse.setStatusText(getHttpStatusText(dapResponse.getStatusCode()));
		return dapResponse;
		
	}

//	private DapHttpResponse buildDapResponse(Response response) {
//		DapHttpResponse dapResponse = new DapHttpResponse();
//		if(response != null){
//			dapResponse.setResponseText(response.getBody());
//			dapResponse.setStatusCode((short)response.getStatusCode());
//			dapResponse.setStatusText(response.getMessage());
//			
//			if (response.getHeaderNames().hasNext()){
//				Iterator<String> iter = response.getHeaderNames();
//				HashMap<String, String> responseHeaders = new HashMap<String, String>();
//				String name;
//				while (iter.hasNext()) {
//					name = iter.next();
//					if (response.getHeader(name) != null) {
//						responseHeaders.put(name, response.getHeader(name));
//					}
//				}
//				dapResponse.setResponseHeaders(responseHeaders);
//			}
//		}
//		return dapResponse;
//	}
		
	//Consider DapHttpClient as a browser between XHR & Server, 
	//it will manage this info
	//TODO: further enhancement could extract below to a helper class
	private String getHttpStatusText(short status) {
		String text = null;
		switch(status){
		case 200:
			text = "200 OK";
			break;
		case 400:
			text = "400 Bad Request";
			break;
		case 401:
			text = "401 Unauthorized";
			break;
		case 403:
			text = "403 Forbidden";
			break;
		case 404:
			text = "404 Not Found";
			break;
		case 408:
			text = "408 Request Timeout";
			break;
		case 500:
			text = "500 Internal Server Error";
			break;
		case 501:
			text = "501 Not Implemented";
			break;
		case 502:
			text = "502 Bad Gateway";
			break;
		case 503:
			text = "503 Service Unavailable";
			break;
		case 504:
			text = "504 Gateway Timeout";
			break;
		}
		return text;
	}
	
	/**
	 * for async execution 
	 *
	 */
	private static class AsyncRunner implements Runnable {
		private DapHttpClient m_httpClient;
		private DapHttpRequest m_dapRequest;
		private IDapCallback m_dapCallback;
		private boolean m_isComplete = false;
		private boolean m_isTimedOut = false;
		private AsyncRunner(
				DapHttpClient httpClient,
				DapHttpRequest dapRequest, 
				IDapCallback dapCallback) {
			m_dapRequest = dapRequest;
			m_httpClient = httpClient;
			m_dapCallback = dapCallback;
		}
		public void run() {
			
			DapHttpResponse dapResponse = null;
			
			m_isComplete = false;
				
			dapResponse = m_httpClient.execute(m_dapRequest);
			onComplete(dapResponse);
		}
		
		boolean isComplete(){
			synchronized (this){
				return m_isComplete;
			}
		}
		
		void onComplete(DapHttpResponse dapResponse){
			synchronized (this){
				m_isComplete = true;
				if (m_isTimedOut){
					return;
				}
			}
			if (m_dapCallback != null){
				m_dapCallback.onComplete(dapResponse);
			}
		}
		
		void onTimedOut(){
			synchronized (this){
				m_isTimedOut = true;
				if (m_isComplete){
					return;
				}
			}
			
			for (IDapHttpListener listener: m_httpClient.m_listeners){
				listener.onHttpTimedOut(m_dapRequest);
			}
			if (m_dapCallback != null){
				m_dapCallback.onTimedOut();
			}
		}
	}
	
	/**
	 * for timeout monitoring 
	 *
	 */
	private static class AsyncTimer extends Thread {
		private AsyncRunner m_runner;
		private long m_timeout;
		AsyncTimer(AsyncRunner runner, long timeout) {
			m_runner = runner;
			m_timeout = timeout > 0 ? timeout : 0;
		}
		public void run() {
			synchronized (this){
				long start = System.currentTimeMillis();
				try {
					wait(m_timeout);
				} catch (InterruptedException e) {
					// DO nothing
				}
				if (!m_runner.isComplete() && System.currentTimeMillis() - start >= m_timeout) {
					m_runner.onTimedOut();
				}
			}
		}
	}
}


