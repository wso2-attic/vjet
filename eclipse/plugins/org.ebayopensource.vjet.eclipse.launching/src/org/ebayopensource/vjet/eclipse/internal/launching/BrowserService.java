/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class BrowserService {
	
	public static final String PARM_WEB_URL = "webUrl";
	public static final String PARM_BROWSER_TYPE = "bType";
	
	private int m_port = 1000;
	private Server m_webServer = null;
	
	private static final BrowserService s_instance = new BrowserService();

	private BrowserService() {
		try {
			startWebServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BrowserService getInstance() {
		return s_instance;
	}
	
	public int getPort() {
		return m_port;
	}
	
	void startWebServer() throws Exception { 
		for (m_port = 10090; m_port <= 65535; m_port++) {
			try {
				m_webServer = new Server(m_port);
				m_webServer.setHandler(new WebHandler());
				m_webServer.start();
				break;
			}
			catch (IOException e) {
				if (m_webServer != null) {
					m_webServer.stop();
				}
				m_webServer = null;
			}
		}
	}
	
	void stopWebServer() {
		if (m_webServer != null) {
			try {
				m_webServer.stop();
			} catch (Exception e) {//DO NOTHING;
				e.printStackTrace();
			}
			try {
				m_webServer.destroy();
			} catch (Exception e) {//DO NOTHING;
				e.printStackTrace();
			}
			m_webServer = null;
		}
	}
	
	@Override
	public void finalize() {
		stopWebServer();
	}
	
	private class WebHandler extends AbstractHandler {
		

		@Override
		public void handle(String arg0, Request arg1, HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {
			request.setCharacterEncoding("UTF8");
			String webUrl = request.getParameter(PARM_WEB_URL);
			// TODO use the browser location and name to let ADOM to know which dom to emulate
			String bType = request.getParameter(PARM_BROWSER_TYPE); 
			if (webUrl == null || webUrl.isEmpty()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
			try {
				IWorkbenchBrowserSupport wbSupport = PlatformUI.getWorkbench().getBrowserSupport();
				IWebBrowser browser = wbSupport.getExternalBrowser();
				if (browser == null) {
					browser = wbSupport.createBrowser("internal");
				}
				browser.openURL(new URL(webUrl));
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			response.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(1);
			outputStream.close();
			
		}
	}
}
