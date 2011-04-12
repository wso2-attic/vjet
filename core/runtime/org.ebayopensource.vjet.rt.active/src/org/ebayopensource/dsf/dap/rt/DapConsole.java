/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;

public class DapConsole implements Runnable {
	
	public static final String OPT_SESSION = "-sid ";

	private DapBrowserEmulator m_emulator;
	private DapConsoleHelpHandler m_menuHelper;
	
	// Strings defined for DUMP_TEST_CAPTURE
	
//	private static final String REQUEST_TOKEN = "REQUEST:";
//	private static final String DUMP_TEST_CAPTURE_TOKEN = "DUMP_TEST_CAPTURE";

	//
	// Constructor
	//
	public DapConsole(DapBrowserEmulator emulator){
		m_emulator = emulator;
		m_menuHelper = new DapConsoleHelpHandler();
		
		Collection<IDapConsoleProxy> proxies = DapCtx.ctx().getDapConfig().getDapConsoleProxies();
		for (IDapConsoleProxy dapConsoleProxy : proxies) {
			dapConsoleProxy.setBrowserEmulator(m_emulator);
			Thread t = new Thread(dapConsoleProxy);
			t.setDaemon(true);
			t.start();
		}
	}

	public void run(  ) {
		String line;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		DapSession session;
    	while (true) {
    		try {
				line = in.readLine();
				
				if (DapConsoleHelpHandler.HELP.equalsIgnoreCase(line)){
					m_menuHelper.handle(line);
					continue;
				}
				
				String sessionId = getSessionId(line);
				if (sessionId != null){
					session = m_emulator.getSession(sessionId);
					if (session == null){
						System.out.println("Invalid session id: " + sessionId);
						continue;
					}
				}
				else {
					Map<String,DapSession> sessions = m_emulator.getSessions();
					
					if (sessions.size() == 1){
						session = sessions.values().iterator().next();
					}
					else {
						System.out.println("Please provide session id");
						continue;
					}
				}
				
				session.getCurrentView().setupCtx();
				
				for (IDapConsoleHandler h: m_emulator.getDapConfig().getConsoleHandlers()){
					h.handle(line);
				}
//					if(m_emulator.m_serverDapCtx.isWebMode()){
//						System.err.println("--- Invalid operation : " + line + " ---");
//						System.err.println("To invoke JUnit test related command, run this test in active mode.");
//						System.err.println("Either specify  -DdapMode=A in eclipse VM Argument");
//						System.err.println("Or append a parameter &dapMode=A in the URL");
//						continue;
//					}
//					else 
//				else if (line.startsWith(REQUEST_TOKEN)) {
//					String msg = line.substring(REQUEST_TOKEN.length());
//					for (SocketChannel channel : m_bridge.getChannels()) {
//						String response = m_bridge.request(channel, msg);
//						System.err.println(response);
//					}						
//				}
//				else {
//					for (SocketChannel channel : m_bridge.getChannels()) {
//						m_bridge.send(channel, line);
//					}						
//				}
			} catch (IOException e) {
				e.printStackTrace();
			}	    		
    	}   
	}
	
	private String getSessionId(String cmd){
		if (cmd == null){
			return null;
		}
		
		int start = cmd.indexOf(OPT_SESSION);
		if (start < 0){
			return null;
		}
		
		int end = cmd.indexOf(" ", start + OPT_SESSION.length());
		if (end < start){
			return cmd.substring(start + OPT_SESSION.length());
		}
		
		return cmd.substring(start + OPT_SESSION.length(), end);
	}
	
	public void onload(DapSession session){
		Collection<IDapConsoleProxy> proxies = DapCtx.ctx().getDapConfig().getDapConsoleProxies();
		for (IDapConsoleProxy dapConsoleProxy : proxies) {
			dapConsoleProxy.onLoad(session);
		}
	}
	
	public void onUnload(DapSession session){
		Collection<IDapConsoleProxy> proxies = DapCtx.ctx().getDapConfig().getDapConsoleProxies();
		for (IDapConsoleProxy dapConsoleProxy : proxies) {
			dapConsoleProxy.onUnload(session);
		}
	}
}
