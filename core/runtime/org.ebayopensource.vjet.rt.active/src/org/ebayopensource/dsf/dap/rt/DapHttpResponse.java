/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.Map;

import org.ebayopensource.dsf.active.client.AXmlParser;
import org.ebayopensource.dsf.active.dom.html.ADocument;

public class DapHttpResponse {
	private String m_rawData;
	private String m_responseText;
	private ADocument m_responseXml;
	private Map<String,String> m_responseHeaders;
	private short m_statusCode;
	private String m_statusText;
	public String getRawData() {
		return m_rawData;
	}
	public void setRawData(String rawData) {
		m_rawData = rawData;
	}
	public String getResponseText() {
		return m_responseText;
	}
	public void setResponseText(String responseText) {
		m_responseText = responseText;
	}
	public ADocument getResponseXML() {
		if (m_responseXml == null && m_responseText != null){
			m_responseXml = AXmlParser.parse(m_responseText.getBytes());
		}
		return m_responseXml;
	}
	public Map<String, String> getResponseHeaders() {
		return m_responseHeaders;
	}
	public void setResponseHeaders(Map<String, String> responseHeaders) {
		m_responseHeaders = responseHeaders;
	}
	
	public String getResponseHeadersText() {
		if (m_responseHeaders == null || m_responseHeaders.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String key : m_responseHeaders.keySet()) {
			sb.append(key).append(": ").append(m_responseHeaders.get(key)).append('\n');
		}
		return sb.toString();
	}
	public short getStatusCode() {
		return m_statusCode;
	}
	public void setStatusCode(short code) {
		m_statusCode = code;
	}
	public String getStatusText() {
		return m_statusText;
	}
	public void setStatusText(String text) {
		m_statusText = text;
	}
}
