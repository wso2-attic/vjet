/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.dom.DBody;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.liveconnect.DLCDispatcher;
import org.ebayopensource.dsf.liveconnect.DLCHttpRequest;
import org.ebayopensource.dsf.liveconnect.DLCHttpResponse;
import org.ebayopensource.dsf.liveconnect.IDLCReceiver;
import org.ebayopensource.dsf.liveconnect.DLCServer;
import org.ebayopensource.dsf.liveconnect.client.DLCClientHelper;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;

/**
 * A console based DLC driver for controling the remove
 * browser(s). One can send any javascript from server console
 * to the connected remove browsers.
 * 
 * With optional IDLCClient, one can configure the web page
 * with additional behaviors and capabilities.
 */
public class SimpleConsoleDriver implements IDLCReceiver {
	protected DLCDispatcher m_dlcDispatcher;
	protected int m_dlcPort;
	protected DLCServer m_server;
	protected IDLCClient m_dlcClient;
	private SysInThread m_sysInThread;
	
	public SimpleConsoleDriver(IDLCClient dlcClient) {
		try {
			m_dlcClient = dlcClient;
			m_server = new DLCServer(this, m_dlcClient);
			m_dlcPort = m_server.getPort();
			m_dlcDispatcher = new DLCDispatcher(m_server);
			startSysInHandler();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * helper method to inject dlc into DDom
	 */
	public DHtmlDocument createDlcEnabledDoc(DNode node, String host) {
		DHtmlDocument doc;
		if (node instanceof DHtmlDocument) {
			doc = (DHtmlDocument)node;
		}
		else {
			doc = new DHtmlDocument();
			if (node instanceof DBody) {
				doc.add(node);
			}
			else {
				doc.getBody().add(node);
			}
		}
		
		DLCClientHelper.enableDLC(host, m_dlcPort, doc, m_dlcClient);
		return doc;
	}
	
	public static final String INSERT_DLC_SCRIPT = 
		"function insertDlc(host,port){var script=document.createElement('script');" +
		"script.type='text/javascript';script.text=\"var DLC_HOST='\"+host+\"';var DLC_PORT=\"+port+';';" +
		"document.getElementsByTagName('head')[0].appendChild(script);" +
		"script=document.createElement('script');script.type='text/javascript';script.src='http://'+host+':'+port+'/DLC_JS.js';" +
		"div=document.createElement('div');div.id='DLC_OBJ';" +
		"document.body.appendChild(div);" +
		"div.appendChild(script);};";
	
	/**
	 * helper method to create bookmarklet for adding DLC into a loaded page
	 */
	public String getDlcInjectionScriptlet(String host) {
		return "javascript:" + INSERT_DLC_SCRIPT + "insertDlc('" + host + "'," + m_dlcPort + ");";
	}
	
	public int getDlcPort() {
		return m_dlcPort;
	}
	
	public void closed(SocketChannel channel) {
		m_dlcDispatcher.removeChannel(channel);
		System.out.println(channel.socket().getRemoteSocketAddress()
			+ " closed");		
	}

	public void connected(SocketChannel channel) {
		m_dlcDispatcher.addChannel(channel);
		System.out.println("connected from " 
			+ channel.socket().getRemoteSocketAddress());
	}
	
	public void shutdown() {
		if (m_sysInThread != null) {
			m_sysInThread.m_shutdown = true;
			m_sysInThread.interrupt();
		}
		m_server.shutdown();
	}

	private static SyncHttpResponse m_syncHttpResponse = new SyncHttpResponse();
	
	public DLCHttpResponse get(SocketChannel channel, DLCHttpRequest request) {
		String requestData = request.getMessage();		
		String responseData = "acknowledged";
		if (requestData != null && requestData.trim().endsWith("?")) {
			if (m_syncHttpResponse.setRequest(request.getMessage(), 30000)) {;
				responseData = m_syncHttpResponse.getResponse();
			}
			else {
				responseData = m_syncHttpResponse.getResponse();
				if (responseData == null) {
					responseData = "timeout";
					System.out.println("timeout");
				}
			}
		}
		else {
			System.out.println("IN(REQ/AUTO-RESPONSE)-> " + requestData);
		}
		DLCHttpResponse response = new DLCHttpResponse(responseData);
		response.setContentType("text/plain");
		return response;		
	}

	public void received(SocketChannel channel, String message) {
		System.out.println("IN-> " + message);
	}

	
	private void startSysInHandler() {
		m_sysInThread = new SysInThread(m_dlcDispatcher, m_dlcPort);
		m_sysInThread.start();
	}
	
	private static final String REQUEST_TOKEN = "REQUEST:";
	private static final String DLC_PORT = "DLC_PORT";
	
	static class SysInThread extends Thread {
		DLCDispatcher m_dispatcher;
		int m_dlcPort;
		boolean m_shutdown = false;
		
		public SysInThread(DLCDispatcher dispatcher, int dlcPort) {
			m_dispatcher = dispatcher;
			m_dlcPort = dlcPort;
			setDaemon(true);
		}

		public void run(  ) {
			String line;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    	while (!m_shutdown) {
	    		try {
					line = in.readLine();
					if (line.startsWith(REQUEST_TOKEN)) {
						String msg = line.substring(REQUEST_TOKEN.length());
						Iterator<SocketChannel> itr = m_dispatcher.getChannels();
						while (itr.hasNext()) {
							String response = m_dispatcher
								.request(itr.next(), msg, 1000);
							System.err.println(response);
						}
					}
					else if (line.startsWith(DLC_PORT)) {
						System.err.println(m_dlcPort);
					}
					else {
						if (m_syncHttpResponse.waitForResponse()) {
							m_syncHttpResponse.setResponse(line);
						}
						else {
							m_dispatcher.broadcast(line, null);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}	    		
	    	}   
		}
	}

	private static class SyncHttpResponse {
		private String m_request = null;
		private String m_response = null;
		
		synchronized String getResponse() {
			m_request = null;
			return m_response;
		}
		
		synchronized boolean waitForResponse() {
			return m_request != null;
		}
		
		synchronized void setResponse(String response) {
			m_response = response;
			notifyAll();
		}
		
		/**
		 * return false if it timed out before getting the response
		 */
		synchronized boolean setRequest(String request, long timeout) {
			m_response = null;
			m_request = request;
			System.out.println("IN(REQ)-> " + request);
			System.out.print("OUT(RESP)-> ");
			try {
				wait(timeout);
			} catch (InterruptedException e) {
				// DO NOTHING
			}
			return m_response != null;
		}
	}
}
