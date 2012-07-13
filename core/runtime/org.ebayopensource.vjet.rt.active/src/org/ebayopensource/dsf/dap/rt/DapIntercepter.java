/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dap.rt.DapCtx.ExeMode;
import org.ebayopensource.dsf.dom.DComment;
import org.ebayopensource.dsf.html.HtmlBuilderHelper;
import org.ebayopensource.dsf.html.HtmlWriterHelper;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.ebayopensource.dsf.html.sax.AHtmlSchema;
import org.ebayopensource.dsf.liveconnect.client.DLCClientHelper;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;
import org.ebayopensource.dsf.service.client.IClientServiceHandlerRegistry;
import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.dsf.logger.Logger;

import org.ebayopensource.dsf.common.xml.IIndenter;

/**
 * This class intercepts incoming request and outgoing response to
 * perform DAP specific setup/logic to enable DAP for web request.
 */
public final class DapIntercepter {
	
	public static final String DAP_MODE = "dapMode";
	
	private static int s_sessionCounter = 0;
	private static int s_reqCounter = 0;
	
	private String m_host;
	
	private ExeMode m_mode;
	private AHtmlSchema m_schema;
	private volatile DapBrowserEmulator m_emulator;
	
	private DapConfig m_dapConfig;
	
	private static Logger s_logger = Logger.getInstance(DapIntercepter.class);
	
	//
	// Constructors
	//
	/**
	 * Constructor
	 * @param host String
	 * @param mode ExeMode
	 */ 
	public DapIntercepter(final String host, final ExeMode mode) {
		this(host, mode, null);
	}
	
	/**
	 * Constructor
	 * @param host String
	 * @param mode ExeMode
	 * @param dapConfig DapConfig
	 */ 
	public DapIntercepter(final String host, final ExeMode mode, final DapConfig dapConfig) {
		m_host = host;
		m_mode = (mode == null) ? ExeMode.WEB : mode;
		m_dapConfig = dapConfig == null ? new DapConfig() : dapConfig;
	}
	
	//
	// API
	//
	public void setDapMode(ExeMode mode){
		m_mode = (mode == null) ? ExeMode.WEB : mode;
	}
	
	public DapConfig getDapConfig(){
		return m_dapConfig;
	}
	
	/**
	 * Setup DAP for request processing. It should be called before app processing starts.
	 * @param request HttpServletRequest
	 * @param svcHandlerRegistry IClientServiceHandlerRegistry
	 */
	public void handleRequest(final HttpServletRequest request, IClientServiceHandlerRegistry svcHandlerRegistry) {

		DapCtx dapCtx = DapCtx.ctx();
		dapCtx.setExeMode(getMode(request));
		dapCtx.setDapConfig(m_dapConfig);
		dapCtx.setDsfSvcRegistry(svcHandlerRegistry);
		
		if (!dapCtx.isWebMode()){
			ActiveJsExecutionControlCtx.ctx().setExecuteJavaScript(true);
		}
	}
	
	/**
	 * Perform DAP specific response processing. It should be called after app processing is complete.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param doc DHtmlDocument
	 * @param indent IIndenter.
	 * @throws IOException 
	 */
	public void handleResponse(
			final HttpServletRequest request, 
			final HttpServletResponse response, 
			DHtmlDocument doc, 
			IIndenter indent) throws IOException {
		

		if (!DapCtx.ctx().isWebMode()){
			
			doc = processDapMode(request, response, doc);
		}
		
		// We need to get the content first so we can figure out the
		// correct content type to set for the output, otherwise we could
		// write directly to the output stream...
		String content = HtmlWriterHelper.asString(doc, indent) ;
		String contentType = getContentType(content);
		response.setContentType(contentType + ";charset=utf-8");		
		response.setHeader("Cache-Control", "no-cache");		
		OutputStream os = response.getOutputStream();
		os.write(content.getBytes("utf-8")) ;
		os.flush();
		os.close();
	}

	private DHtmlDocument processDapMode(final HttpServletRequest request,
			final HttpServletResponse response, DHtmlDocument doc) {
		String sessionId = DLCClientHelper.getSessionId(request);
		if (sessionId == null || sessionId.trim().length() == 0){
			sessionId = String.valueOf(++s_sessionCounter);
		}
		String reqId = DLCClientHelper.getRequestId(request);
		if (reqId == null || reqId.trim().length() == 0){
			reqId = String.valueOf(++s_reqCounter);
		}
		String html = getDapHtml(doc);
		
		getEmulator().addHtml(sessionId, reqId, getUrl(request), html);
			
		doc = removeScripts(html);
		
		DLCClientHelper.setSessionId(response, sessionId);
		return doc;
	}

	private String getContentType(String s) {
		int doctypeStartIndex = s.indexOf("<!DOCTYPE") ;
		if (doctypeStartIndex == -1) {
			return "text/html" ;
		}

		// find end of doctype and look for specific doc types 
		int doctypeEndIndex = s.indexOf(">", doctypeStartIndex) ;
		if (doctypeEndIndex == -1) {
			// Weird - saw doctype start but not end - default to text/html
			return "text/html" ;
		}
		String docType = s.substring(doctypeStartIndex, doctypeEndIndex) ;
		if (docType.contains("text/xml")) {
			return "text/xml" ;
		}
		if (docType.contains("xhtml") || docType.contains("XHTML")) {
			return "application/xhtml+xml;" ;
		}
		// just HTML again...
		return "text/html" ;
	}
	
	public String getDapHtml(DHtmlDocument doc) {
		removeComments(doc);
		DLCClientHelper.enableDLC(m_host, getEmulator().getBrowserBridge().getPort(), doc, getEmulator().getDlcClient());
		return HtmlWriterHelper.asString(doc);
	}
	
	//
	// Private
	//
	private ExeMode getMode(final HttpServletRequest request){
		String dapMode = request.getParameter(DAP_MODE);
		if (dapMode != null){
			if (dapMode.startsWith(ExeMode.ACTIVE.name().substring(0,1))){
				m_mode = ExeMode.ACTIVE;
			}
			else if (dapMode.startsWith(ExeMode.TRANSLATE.name().substring(0,1))){
				m_mode = ExeMode.TRANSLATE;
			}
			else {
				m_mode = ExeMode.WEB;
			}
		}
		return m_mode;
	}
	
	private String getUrl(final HttpServletRequest request){
		StringBuffer sb = new StringBuffer(request.getRequestURL());
		String query = request.getQueryString();
		if (query != null && query.length() > 0){
			sb.append("?").append(query);
		}
		return sb.toString();
	}
	
	private AHtmlSchema getSchema() {
		if(m_schema==null){
			m_schema = new AHtmlSchema();
		}
		return m_schema;
	}
		
	private static final String EMPTY = "";
	private void removeComments(Node node){
		if (node == null || !node.hasChildNodes()){
			return;
		}
		NodeList children = node.getChildNodes();
		Node child;
		for (int i=children.getLength()-1; i>=0; i--){
			child = children.item(i);
			if (child instanceof DComment){
				node.removeChild(child);
			}
			else {
				removeComments(child);
			}
		}
	}
	
	public DHtmlDocument removeScripts(final String html){
		DHtmlDocument doc = HtmlBuilderHelper.parseHtmlFragment(html, true,getSchema());
		removeScripts(doc);
		return doc;
	}
	
	public void removeScripts(Node node){
		if (node == null || !node.hasChildNodes()){
			return;
		}
		NodeList children = node.getChildNodes();
		Node child;
		DScript script;
		String src;
		for (int i=children.getLength()-1; i>=0; i--){
			child = children.item(i);
			if (child instanceof DScript){
				script = (DScript)child;
				src = script.getHtmlSrc();
				if ((src == null || src.length() == 0) &&
					"".equals(script.getAttribute(IDLCClient.DLC_TOKEN))){
					script.setHtmlText(EMPTY);
				}
			}
			else {
				removeScripts(child);
			}
		}
	}
	
	public DapBrowserEmulator getEmulator() {
		if (m_emulator == null) {
			synchronized (this) {
				if (m_emulator == null) {
					try {
						m_emulator = new DapBrowserEmulator(m_dapConfig);
					}
					catch (IOException e){
						s_logger.log(LogLevel.ERROR, "Fail to instantiate browser bridge", e);
					}
				}
			}
		}	
		return m_emulator;
	}
}
