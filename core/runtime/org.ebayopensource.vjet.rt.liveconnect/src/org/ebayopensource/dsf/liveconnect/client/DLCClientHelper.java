/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.html.dom.DDiv;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.liveconnect.DLCServer;
import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Util class to enable the DLC for a web page.
 * It also caches DLC Runtime JS & flash data.
 */
public class DLCClientHelper {
	
	public static final byte[] DLC_JS = getData("DSFLiveConnect.js");
	public static final byte[] DLC_SWF = getData("DSFLiveConnect.swf");
	public static final String DLC_OBJ_ID = "DLC_OBJ";
	
	private static final String DLC_SESSION_ID = "DLC_SID";
	
	public static void enableDLC(String dlcHost, int port, DHtmlDocument doc, IDLCClient dlcClient) {		
		String js = "var DLC_HOST='" + dlcHost +"'; "
			+ "var DLC_PORT=" + port + ";";
		doc.getHead().add(createScriptWithText(js));
		String dlcUrl = "http://" + dlcHost + ":" + port + "/";

		doc.getHead().add(createScriptWithSrc(dlcUrl + DLCServer.DLC_FILE_NAME));

		doc.getBody().insertBefore(new DDiv().setHtmlId(DLC_OBJ_ID)
			.setHtmlStyleAsString("width:1px;height:1px;position:absolute;left:-1000px;")
			.add(createScriptWithSrc(dlcUrl + DLCServer.DLC_CLIENT_FILE_NAME)),
			doc.getBody().getFirstChild());
	}

	public static String getSessionId(HttpServletRequest request){
		if (request.getCookies() == null){
			return null;
		}
		for (Cookie c: request.getCookies()){
			if (DLC_SESSION_ID.equals(c.getName())){
				return c.getValue();
			}
		}
		return null;
	}
	
	public static String getRequestId(HttpServletRequest request){
		// TODO
		return null;
	}
	
	public static void setSessionId(HttpServletResponse response, String sessionId){
		response.addCookie(new Cookie(DLC_SESSION_ID,sessionId));
	}
	
	public static String dlcClientEventEnablerById(String id, NativeEvent event, IDLCClient dlcClient) {
		return dlcClientEventEnablerViaPath("document.getElementById(" + id + ")", event, dlcClient);
	}
	
	public static String dlcClientEventEnablerViaPath(String path, NativeEvent event, IDLCClient dlcClient) {
		if(event.equals(NativeEvent.imageload)){
			return "DLC_bindImageLoad("+path+")";
		}else if(event.equals(NativeEvent.scriptLoad)){
				return "DLC_bindScriptLoad("+path+")";
		}else if(event.equals(NativeEvent.scriptReadyStateChange)){
			return "DLC_bindScriptReadyStateChange("+path+")";
		}else{
			String code = dlcClient.getDlcEventHandler(event);
			if(code != null){
				return path + ".on" + event.name() + "=" + code;
			}
		}
		return null;
	}
	
	/**
	 * get data in byte[] from specified resource
	 */
	private static byte[] getData(String resourceName) {
		byte[] buffer = new byte[1024];
		int numRead = 0;
		try {
			InputStream is = ResourceUtil
				.getResourceAsStream(DLCClientHelper.class, resourceName);
			ByteArrayOutputStream os = new ByteArrayOutputStream();			
			while ((numRead = is.read(buffer)) > 0) {
				os.write(buffer, 0, numRead);
			}
			is.close();
			return os.toByteArray();
		} catch (IOException e) {
			throw new DsfRuntimeException(e);
		}
	}
	
	private static DScript createScriptWithText(String js) {
		DScript elem = new DScript().setHtmlText(js);
		elem.setAttribute(IDLCClient.DLC_TOKEN, "true");
		return elem;		
	}
	
	private static DScript createScriptWithSrc(String src) {
		DScript elem = new DScript().setHtmlSrc(src);
		elem.setAttribute(IDLCClient.DLC_TOKEN, "true");
		return elem;		
	}
}
