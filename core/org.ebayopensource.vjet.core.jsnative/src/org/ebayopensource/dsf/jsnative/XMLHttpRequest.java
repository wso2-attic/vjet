/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * http://www.w3.org/TR/XMLHttpRequest/
 * https://developer.mozilla.org/en/XMLHttpRequest
 */
public interface XMLHttpRequest extends IWillBeScriptable {
	
	// readyState
	public static final short UNSENT = 0;
	public static final short OPENED = 1;
	public static final short HEADERS_RECEIVED = 2;
	public static final short LOADING = 3;
	public static final short DONE = 4;
	
	
	@Constructor void XMLHttpRequest();
	
	/**
	 * The state of the request
	 * @return short
	 */
	@Property short getReadyState();
	
	/**
	 * Get event handler for readyState change if it is set
	 * @return org.ebayopensource.dsf.jsnative.global.Function
	 */
	@Property Object getOnreadystatechange();
	
	/**
	 * Set event handler for readyState change
	 * @param handler org.ebayopensource.dsf.jsnative.global.Function
	 */
	@Property void setOnreadystatechange(Object handler);
	
	/**
	 * The response to the request as text, or null if the request 
	 * was unsucessful or has not yet been sent.  
	 * @return String
	 */
	@Property String getResponseText();

	/**
	 * The response to the request as a DOM Document object, or null if 
	 * the request was unsuccessful, has not yet been sent, or cannot be 
	 * parsed as XML.  The response is parsed as if it were a text/xml stream. 
	 * @return Document
	 */
	@Property Document getResponseXML();
	
	/**
	 * The status of the response to the request.
	 * This is the HTTP result code (for example, status is 200 for 
	 * a successful request). 
	 * @return short response status
	 */
	@Property short getStatus();
	
	/**
	 * The response string returned by the HTTP server.
	 * Unlike status, this includes the entire text of the response message 
	 * @return String
	 */
	@Property String getStatusText();
	
	/**
	 * The upload process can be tracked by adding an event listener to upload. 
	 * @return org.ebayopensource.dsf.jsnative.global.Function
	 */
	@BrowserSupport({BrowserType.FIREFOX_3P})
	@Property org.ebayopensource.dsf.jsnative.global.Function getUpload();
	
	/**
	 * The upload process can be tracked by adding an event listener to upload. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_3P})
	@Property void setUpload(org.ebayopensource.dsf.jsnative.global.Function nsIXMLHttpRequestUpload);
	
	
	/**
	 * Indicates whether or not cross-site Access-Control requests 
	 * should be made using credentials such as cookies or authorization headers.
	 * @return boolean
	 */
	@BrowserSupport({BrowserType.FIREFOX_3P})
	@Property boolean getWithCredentials();
	
	/**
	 * Indicates whether or not cross-site Access-Control requests 
	 * should be made using credentials such as cookies or authorization headers.
	 */
	@BrowserSupport({BrowserType.FIREFOX_3P})
	@Property void setWithCredentials(boolean value);
	
	
	/**
	 * Aborts the request if it has already been sent.
	 */
	@Function  void abort();

	/**
	 * Returns all the response headers as a string.
	 * @return String
	 */
	@Function  String getAllResponseHeaders();
	
	/**
	 * Returns the text of a specified header.
	 * @param header String header
	 * @return String 
	 */
	@Function  String getResponseHeader(String header);
	
	/**
	 * Initializes a request. This method is to be used from JavaScript code; 
	 * to initialize a request from native code, use openRequest() instead.
	 * @param method The HTTP method to use; either "POST" or "GET".  
	 * Ignored for non-HTTP URLs.
	 * @param url The URL to which to send the request.
	 * @param async An optional boolean parameter, defaulting to true, 
	 * indicating whether or not to perform the operation asynchronously.  
	 * If this value is false, the send() method does not return until the 
	 * response is received.  If true, notification of a completed 
	 * transaction is provided using event listeners.  
	 * This must be true if the multipart attribute is true, 
	 * or an exception will be thrown.
	 * @param user The optional user name to use for 
	 * authentication purposes; by default, this is an empty string.
	 * @param password The optional password to use for authentication purposes; 
	 * by default, this is an empty string.
	 */
	@Function  void open(String method, String url, boolean async, 
			String user, String password);
	
	/**
	 * Sets the value of an HTTP request header.
	 * @param header The name of the header whose value is to be set.
	 * @param value The value to set as the body of the header.
	 */
	@Function  void setRequestHeader(String header, String value);
	
	
	/**
	 * Sends the request. If the request is asynchronous (which is the default), 
	 * this method returns as soon as the request is sent.
	 * If the request is synchronous, this method doesn't return until the 
	 * response has arrived.
	 * If data is not null, Serialize data into a namespace 
	 * well-formed XML document and encoded using the encoding given by 
	 * data.inputEncoding, when not null, or UTF-8 otherwise. 
	 * Or, if this fails because the Document cannot be serialized act as if
	 * data  is null.
	 * @param data if not null use it for the entity body 
	 * as defined by section 7.2 of RFC 2616.
	 */
	@Function  void send(Object data);
	
	/**
	 * A variant of the send() method that sends binary data.
	 * @param body The request body as a DOM string.  
	 */
	@BrowserSupport({BrowserType.FIREFOX_3P})
	@Function void sendAsBinary(String body);
}
