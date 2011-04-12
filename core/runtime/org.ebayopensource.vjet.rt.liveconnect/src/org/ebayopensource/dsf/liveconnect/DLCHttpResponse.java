/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple class to hold a http response data to be sent by DLC server
 * in response to the DLC client HTTP request.
 */
public class DLCHttpResponse {
	private static final String HEADER_TOKEN = "\r\n";
	private static final String FIRST_LINE = "HTTP/1.1 200 OK" + HEADER_TOKEN;
	
	private final byte[] m_data;
	private Map<String, String> m_headers = new LinkedHashMap<String, String>();
	
	public DLCHttpResponse(String data) {
		this(data.getBytes());
	}
	
	public DLCHttpResponse(byte[] data) {
		m_data = data;
		m_headers.put("Content-Length", String.valueOf(m_data.length));
		m_headers.put("Cache-Control", "no-cache");
	}
	
	public DLCHttpResponse(boolean boolReturn) {
		this(String.valueOf(boolReturn));
	}
	
	public void addHeader(String name, String value) {
		m_headers.put(name, value);
	}
	
	public void setContentType(String type) {
		m_headers.put("Content-Type", type);
	}
	
	public byte[] getHttpPayload() {
		StringBuilder sb = new StringBuilder();
		sb.append(FIRST_LINE);
		processHeaders(sb);
		sb.append(HEADER_TOKEN);
		byte[] headerbytes = sb.toString().getBytes();
		byte[] payload = new byte[headerbytes.length + m_data.length];
		System.arraycopy(headerbytes, 0, payload, 0, headerbytes.length);
		System.arraycopy(m_data, 0, payload, headerbytes.length, m_data.length);
		return payload;
	}
	
	private void processHeaders(StringBuilder sb) {
		for (Map.Entry<String, String> entry : m_headers.entrySet()) {
			sb.append(entry.getKey())
			  .append(": ")
			  .append(entry.getValue())
			  .append(HEADER_TOKEN);
		}
	}
}
