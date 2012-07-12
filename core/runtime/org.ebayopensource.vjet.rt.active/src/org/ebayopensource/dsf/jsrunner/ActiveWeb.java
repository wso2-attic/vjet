/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsrunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.DapCtx.ExeMode;
import org.ebayopensource.dsf.dap.rt.DapIntercepter;
import org.ebayopensource.dsf.dap.rt.IBrowserEmulatorListener;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DHtmlDocumentBuilder;
import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.ebayopensource.dsf.common.xml.IIndenter;

public class ActiveWeb {
	
	public static final String WEB_EXIT_COMMAND = "/exit";
	public static final String DISPLAY_COMMAND = "/display";
	public static final String WEB_HOST = "localhost";
	
	private int m_port = 8090;
	private Server m_webServer = null;
	private final DHtmlDocument m_doc;
	private final DapIntercepter m_intercepter;
	private boolean m_windowLoaded = false;
	private boolean m_exit = false;
	private Process m_displayProc = null;
	private final IBrowserLauncher m_browserLauncher;
	private final ExeMode m_mode;
	
	public ActiveWeb(InputStream is, IBrowserLauncher browserLauncher, ExeMode mode) throws IOException {
		m_mode = mode;
		m_doc = DHtmlDocumentBuilder.getDocument(is);
		m_intercepter = new DapIntercepter(WEB_HOST, m_mode);
		m_browserLauncher = (browserLauncher != null)? browserLauncher: BrowserLauncher.getInstance();
	}
	
	public static void main(String[] args) throws Exception {
		ActiveWeb aWeb = new ActiveWeb(
			new URL("http://localhost/Html5Tests.html").openConnection().getInputStream(),
			null, ExeMode.TRANSLATE);
		aWeb.startWebServer();
		aWeb.addListener(new IBrowserEmulatorListener() {			
			@Override
			public void windowOnload() {
				AWindow window = DapCtx.ctx().getWindow();
				window.alert("hi");
			}
		});
		
		aWeb.displayUrlInBrowser(BrowserType.IE_7);
		
		aWeb.waitForWindowLoaded();
		
		aWeb.exit();
		
		aWeb.waitForExit();
		
		aWeb.destroy();
	}
	
	public void addListener(final IBrowserEmulatorListener listener) {
		m_intercepter.getEmulator().addListener(new IBrowserEmulatorListener() {
			@Override
			public void windowOnload() {
				listener.windowOnload();
				windowLoaded();
			}
		});
	}
	
	public void startWebServer() throws Exception { 
		for (; m_port <= 65535; m_port++) {
			try {
				m_webServer = new Server(m_port);
				m_webServer.setHandler(new WebHandler());
				//m_webServer.setStopAtShutdown(true);
				m_webServer.start();
				break;
			}
			catch (IOException e) {
				System.out.println("There is already an existing server on port " + m_port + ".");
				if (m_webServer != null) {
					m_webServer.stop();
				}
				m_webServer = null;
			}
		}
	}
	
	public void stopWebServer() {
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
	
	public void destroy() {
		stopWebServer();
		if (m_mode != ExeMode.WEB) {
			m_intercepter.getEmulator().shutdown();
		}
		if (m_displayProc != null) {
			m_displayProc.destroy();
		}
	}
	
	public void finalize() {
		destroy();
	}
	
	private synchronized void windowLoaded() {
		m_windowLoaded = true;
		notifyAll();
	}
	
	public synchronized void waitForWindowLoaded() {
		if (m_windowLoaded) {
			return;
		}
		else {
			while (!m_windowLoaded) {
				try {
					wait();
				} catch (InterruptedException e) {
					//do nothing
				}
			}
		}
	}
	
	public synchronized void exit() {
		m_exit = true;
		notifyAll();
	}
	
	public synchronized void waitForExit() {
		if (m_exit) {
			return;
		}
		else {
			while (!m_exit) {
				try {
					wait();
				} catch (InterruptedException e) {
					//do nothing
				}
			}
		}
	}
	
	public void displayUrlInBrowser(BrowserType type) {
		m_displayProc = m_browserLauncher.launch(getWebDisplayUrl(), type);
	}
	
	public int getWebPort() {
		return m_port;
	}
	
	public String getWebDisplayUrl() {
		return "http://" + WEB_HOST + ":" + m_port + DISPLAY_COMMAND;
	}
	
	public String getWebExitUrl() {
		return "http://" + WEB_HOST + ":" + m_port + WEB_EXIT_COMMAND;
	}
	
	public boolean isWebMode() {
		return m_mode == ExeMode.WEB;
	}
	
	public void loadJs(URL url) {
		DScript script = new DScript().setHtmlType("text/javascript");
		String urlStr = url.toExternalForm();
		if (urlStr.startsWith("http")) {
			script.setHtmlSrc(urlStr);			
		} else {
			script.add(getContent(url));
		}
		m_doc.getBody().add(script);
	}
	
	private String getContent(URL url) {
		try {
			Reader r = new InputStreamReader(url.openStream());
			StringBuilder sb = new StringBuilder(100);
			char[] buffer = new char[100];
			int i = 0;
			while (i != -1) {
				i = r.read(buffer);
				if (i > 0) {
					sb.append(buffer, 0, i);
				}
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private class WebHandler extends AbstractHandler {

		@Override
		public void handle(String arg0, Request arg1, HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {
			if (DISPLAY_COMMAND.equalsIgnoreCase(request.getPathInfo())) {
				m_intercepter.handleRequest(request, null);
				m_intercepter.handleResponse(request, response, m_doc, IIndenter.COMPACT);
				if (m_mode == ExeMode.WEB) {
					windowLoaded();
					exit();
				}
			}
			else if (WEB_EXIT_COMMAND.equalsIgnoreCase(request.getPathInfo())) {
				String content = "<html><body>exit</body><html>";	
				response.setHeader("Cache-Control", "no-cache");		
				OutputStream os = response.getOutputStream();
				os.write(content.getBytes("utf-8"));
				os.flush();
				os.close();
				exit();
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
			
		}
	}
}
