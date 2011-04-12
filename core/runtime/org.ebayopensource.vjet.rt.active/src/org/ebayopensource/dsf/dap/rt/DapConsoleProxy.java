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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.cnr.DapConsoleReplayHandler;
import org.ebayopensource.dsf.dap.cnr.IDapCapture;
import org.ebayopensource.dsf.json.JsonArray;
import org.ebayopensource.dsf.json.JsonObject;
import org.ebayopensource.dsf.json.JsonTokener;
import org.ebayopensource.dsf.util.JavaSourceLocator;

/**
 * This class will be serving as a communication client to Plugin Socket Server.
 * Basically, the thread model will be:
 * 1. DapPanelProxy runnable thread
 * 		-- keep reading msg from socket server
 * 		-- dispatch msg to corresponding cmd
 * 		-- send response back to server
 * 2. DapConsole thread
 * 		-- send onload/onunload msg to server
 * 
 * To wrap it up, 
 * -- DapPanelProxy is the only thread trying to read data from server
 * -- A couple of threads will try to send data to server
 * 
 */

public class DapConsoleProxy implements IDapConsoleProxy{
	
	private DapBrowserEmulator m_emulator;

	private Socket m_socket;
	private PrintWriter m_out;
	private BufferedReader m_in;
	private static final String REMOTE_HOST = "localhost";
	private static final int REMOTE_PORT = 10062;

	private Map<String, ICmdHandler> m_handlers = new HashMap<String, ICmdHandler>();
	
	
	public DapConsoleProxy() {
        try {
            m_socket = new Socket(REMOTE_HOST, REMOTE_PORT);
         } catch (IOException e) {
            System.out.println("Can not establish connection to " + REMOTE_HOST + ":" + REMOTE_PORT);
         }
	}

	//
	// Satisfy IDapConsoelProxy
	//
	
	@Override
	public void onLoad(DapSession session) {
		if(m_socket == null){
			return;
		}
		sendMsg(buildOnloadMsg(session));
	}

	@Override
	public void onUnload(DapSession session) {
		if(m_socket == null){
			return;
		}
		sendMsg(buildOnunloadMsg(session));
	}

	@Override
	public void setBrowserEmulator(DapBrowserEmulator emulator) {
		m_emulator = emulator;		
	}
	
	@Override
	public void run() {
		if(m_socket == null){
			return;
		}
        initCmdHandlers();
		try{
	        m_in = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
	        m_out = new PrintWriter(new OutputStreamWriter(m_socket.getOutputStream()));
	        System.out.println("Connected to server " + REMOTE_HOST + ":" + REMOTE_PORT);
	        
	        String msg;
			while ((msg = m_in.readLine()) != null) {
				receiveMsg(msg);
			}
		}catch(Exception e){
			//Got exception, which means this proxy will not work properly any more
			//As this proxy is only a plug-in, we won't impact other functions.
			//Throwing a runtime exception will be OK
			throw new DsfRuntimeException(e);
		}
        
	}
	
	//
	// Private methods
	//
	
	private String buildOnloadMsg(DapSession session) {
		
		//It's not a good idea to parse dervlet name here
		//Maybe we should change DsfDervlet to put the dervletname into ctx
		String url = session.getCurrentView().getUrl();
		String dervletName = "DsfDervlet";
		int start = url.indexOf("class=");
		if(start > 0){
			dervletName = url.substring(start + "class=".length());
			int end = dervletName.indexOf("&");
			if(end > 0){
				dervletName = dervletName.substring(0, end);
			}
		}
		String path = "";
		try {
			Class<?> c = Class.forName(dervletName);
			//path = c.getProtectionDomain().getCodeSource().getLocation().toString();
			String testPath = JavaSourceLocator.getInstance().getSourceUrl(c).toExternalForm();
			path = new File(testPath).getParent();
			if(path == null) path = "";
		} catch (ClassNotFoundException e) {
			//Just could not find the location, no need to propagate the exception
		}
		
		JsonObject jo = new JsonObject();
		jo.accumulate("cmd", "newsession");
		jo.accumulate("sessionid", session.getSessionId());
		jo.accumulate("dervletname", dervletName);
		jo.accumulate("useragent", session.getUserAgent());
		jo.accumulate("status", getSessionStatus(session));
		jo.accumulate("url", url);
		jo.accumulate("testcasepath", path);
		start = url.indexOf("name=");
		if(start > 0){
			String name = url.substring(start + "name=".length());
			int end = dervletName.indexOf("&");
			if(end > 0){
				name = name.substring(0, end);
			}
			jo.accumulate("name", name);	
		}
		jo.accumulate("timestamp", new Date().getTime()+"");
		return jo.toString();
	}

	private String buildOnunloadMsg(DapSession session) {
		JsonObject jo = new JsonObject();
		jo.accumulate("cmd", "endsession");
		jo.accumulate("sessiondid", session.getSessionId());
		return jo.toString();
	}
	
	//Make this method synchronized to keep msg send completed one by one
	private synchronized void sendMsg(String msg){
		if(m_socket == null){
			return;
		}
		m_out.println(msg);
		m_out.flush();
	}
	
	private void receiveMsg(String msg){
		// 1. parse msg into JSON obj
		// 2. run logic for this obj
		//3. send response back to server
		JsonTokener jt = new JsonTokener(msg);
		JsonObject jo = null;
		try {
			jo = new JsonObject(jt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(jo == null){
			return;
		}
		
		String cmdName = jo.getString("cmd");
		if(cmdName == null){
			return;
		}
		
		ICmdHandler handler = m_handlers.get(cmdName);
		if(handler == null){
			return;
		}
		
		sendMsg(handler.execute(jo).toString());
	}
	
	//
	// API
	//
	
	public void addCmdHandler(String cmd, ICmdHandler handler){
		handler.setDapConsoleProxy(this);
		m_handlers.put(cmd, handler);
	}
	
	public void removeCmdHandler(String cmd){
		m_handlers.remove(cmd).setDapConsoleProxy(null);
	}
	
	public DapSession getSession(String sessionId){
		return m_emulator.getSession(sessionId);
	}
	
	public DapConfig getDapConfig(){
		return m_emulator.getDapConfig();
	}
	
	//
	// Cmd handling
	//
	
	public static interface ICmdHandler{
		JsonObject execute(JsonObject jo);
		void setDapConsoleProxy(DapConsoleProxy proxy);
	}
	
	/**
	 * A base class for handling cmd from socket msg  
	 *
	 */
	public static abstract class DapConsoleProxyCmdHandler implements ICmdHandler{
		
		private DapConsoleProxy m_proxy;

		public void setDapConsoleProxy(DapConsoleProxy proxy){
			m_proxy = proxy;
		}
		
		protected DapConsoleProxy getProxy(){
			return m_proxy;
		}
		
		public JsonObject execute(JsonObject jo) {
			String sessionId = jo.getString("sessionid");
			if(sessionId == null){
				return buildResp(false, sessionId, "Invalid sessionid.");
			}else{
				DapSession session = m_proxy.getSession(sessionId);
				if(session == null){
					return buildResp(false, sessionId, "Invalid sessionid.");
				}
				session.getCurrentView().setupCtx();
				handleCmd(jo, session);
			}
			return buildResp(true, sessionId, null);
		}
		
		protected abstract void handleCmd(JsonObject jo, DapSession session);

		private JsonObject buildResp(
				boolean isSuccess,
				String sessionId,
				String errMsg){
			JsonObject jo = new JsonObject();
			jo.put("cmd", "response");
			jo.put("result", isSuccess ? 0:1);
			jo.put("sessionid", sessionId);
			if(sessionId != null){//invalid session id was handled outside
				DapSession session = m_proxy.getSession(sessionId);
				if(session != null){
					jo.put("status", m_proxy.getSessionStatus(session));
				}
			}
			JsonArray errs = new JsonArray();
			if(!isSuccess){
				errs.put(errMsg);
			}
			jo.accumulate("errormsg", errs);
			
			return jo;
		}
	}
	
	/**
	 * A base class for dispatch socket cmd to DAP console CLI handlers 
	 *
	 */
	public static abstract class DapConsoleProxyCmdCliHandler extends DapConsoleProxyCmdHandler{
		protected abstract String genCmdLine(JsonObject jo, DapSession session);
		
		protected void handleCmd(JsonObject jo, DapSession session) {
			String cmdLine = genCmdLine(jo, session);
			for (IDapConsoleHandler h: getProxy().getDapConfig().getConsoleHandlers()){
				executeCmdLine(cmdLine, h);
			}
		}

		protected void executeCmdLine(String cmdLine, IDapConsoleHandler h) {
			h.handle(cmdLine);
		}
	}
	
	private void initCmdHandlers() {
		//Below are standard cmd for DAP RT
		addCmdHandler("start", new StartCmdHandler());
		addCmdHandler("stop", new StopCmdHandler());
		addCmdHandler("pause", new PauseCmdHandler());
		addCmdHandler("resume", new ResumeCmdHandler());
		addCmdHandler("replay", new ReplayCmdHandler());
	}
	
	private String getSessionStatus(DapSession session){
		IDapCapture capture = session.getCaptureReplay().getCapture();
		IDapCapture.CaptureState state = capture.state();
		String status = "";
		if(IDapCapture.CaptureState.paused == state){
			status = "RECORD_PENDING";
		}else if(IDapCapture.CaptureState.resumed == state){
			status = "RECORDING";
		}else if(IDapCapture.CaptureState.started == state){
			status = "RECORDING";
		}else if(IDapCapture.CaptureState.stoped == state){
			status = "READY";
		}
		return status;
	}

	private class StartCmdHandler extends DapConsoleProxyCmdCliHandler{

		@Override
		protected String genCmdLine(JsonObject jo, DapSession session) {
			String cmdLine = DapConsoleCaptureHandler.START;
			if(jo.has("groupname")){
				String grpName = jo.getString("groupname");
				if(grpName != null){
					cmdLine = cmdLine + " " + grpName;
				}
			}
			return cmdLine;
		}

	}
	
	private class StopCmdHandler extends DapConsoleProxyCmdCliHandler{

		@Override
		protected String genCmdLine(JsonObject jo, DapSession session) {
			return DapConsoleCaptureHandler.STOP;
		}
	}
	
	private class PauseCmdHandler extends DapConsoleProxyCmdCliHandler{

		@Override
		protected String genCmdLine(JsonObject jo, DapSession session) {
			return DapConsoleCaptureHandler.PAUSE;
		}
	}
	
	private class ResumeCmdHandler extends DapConsoleProxyCmdCliHandler{

		@Override
		protected String genCmdLine(JsonObject jo, DapSession session) {
			return DapConsoleCaptureHandler.RESUME;
		}
	}
	
	private class ReplayCmdHandler extends DapConsoleProxyCmdCliHandler{

		@Override
		protected String genCmdLine(JsonObject jo, DapSession session) {
			String cmdLine=DapConsoleReplayHandler.REPLAY;
			if(jo.has("mode")){
				String mode = jo.getString("mode");
				if("FAST".equals(mode)){
					cmdLine = DapConsoleReplayHandler.FAST_REPLAY;
				}else if("NORMAL".equals(mode)){
					cmdLine = DapConsoleReplayHandler.REPLAY;
				}else if("SLOW".equals(mode)){
					cmdLine = DapConsoleReplayHandler.SLOW_REPLAY;
				}
			}
			return cmdLine;
		}
	}
	
}