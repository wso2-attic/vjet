/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

/**
 * Interface for HTTP client in active mode
 */
public interface IDapHttpClient {
	
	/**
	 * Send http request synchronously
	 * @param request DapHttpRequest
	 * @return DapHttpResponse
	 */
	DapHttpResponse send(DapHttpRequest request);
	
	/**
	 * Send http request asynchronously
	 * @param request DapHttpRequest
	 * @param callback IDapCallback
	 */
	void send(DapHttpRequest request, IDapCallback callback);
	
	/**
	 * Add listeners which will be invoked when http requests
	 * are sent and when http responses are received, and when
	 * http requests are timed out
	 * @param listener IHttpListener
	 */
	void addListener(IDapHttpListener listener);

	/**
	 * Interface for Ajax callback
	 */
	interface IDapCallback {
		
		/**
		 * A method which will be called when http call
		 * is completed 
		 * @param resp DapHttpResponse
		 */
		void onComplete(DapHttpResponse resp);
		
		/**
		 * A method which will be called when http call
		 * is timed out
		 */
		void onTimedOut();
	}
	
	/**
	 * Interface for Ajax listeners
	 */
	public interface IDapHttpListener {
		
		/**
		 * Called when a HTTP request is sent
		 * @param req DapHttpRequest
		 */
		void onHttpRequest(final DapHttpRequest req);
		
		/**
		 * Called when a HTTP response is back
		 * @param req DapHttpRequest
		 * @param resp DapHttpResponse
		 */
		void onHttpResponse(final DapHttpRequest req, final DapHttpResponse resp);
		
		/**
		 * Called when a HTTP request is timed out
		 * @param req DapHttpRequest
		 */
		void onHttpTimedOut(final DapHttpRequest req);
	}
}
