/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple class to hold a http request data received from DLC client
 */
public class DLCHttpRequest {
	private static final String GET = "GET /";
	private static final String HTTP = " HTTP/";
	private Map<String, String> m_headers = new LinkedHashMap<String, String>();
	private String m_message= null;
	private String m_uri = null;
	
	private static final String HEADER_TOKEN = "\r\n";
	private static final String END_HEADER_TOKEN = "\r\n\r\n";
	
	public String getHeader(String name) {
		return m_headers.get(name);
	}
	
	public Iterator<String> getHeaderNames() {
		return m_headers.keySet().iterator();
	}
	
	public String getMessage() {
		return m_message;
	}
	public String getUri() {
		return m_uri;
	}
	public DLCHttpRequest(String requestData) {
		if (requestData.startsWith(GET)) {
			m_uri = requestData.substring(GET.length(), requestData.indexOf(HTTP));
		}
		//skip the first HTTP line
		int start = requestData.indexOf(HEADER_TOKEN);
		int endOfHeader = requestData.indexOf(END_HEADER_TOKEN);
		if (start == -1) {
			throw new RuntimeException("Incorrect HTTP request");
		}
		start += HEADER_TOKEN.length();;
		int end = requestData.indexOf(HEADER_TOKEN, start);
		while (end > 0 && end <= endOfHeader) {
			String line = requestData.substring(start, end);
			processHeader(line);
			if (end < endOfHeader) {
			start = end + HEADER_TOKEN.length();
			end = requestData.indexOf(HEADER_TOKEN, start);
		}
			else {
				start = end + END_HEADER_TOKEN.length();
				end = -1;
			}
		}
		if (start < requestData.length()) {
			m_message = requestData.substring(start);
			//System.out.println(m_message);
		}
	}
	
	private void processHeader(String line) {
		int index = line.indexOf(":");
		if (index == -1) {
			return;
		}
		String name = line.substring(0, index).trim();
		String value = line.substring(index + 1).trim();
		m_headers.put(name, value);
		//System.out.println("[" + name + "][" + value + "]");
	}
}
