/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.net.URL;
import java.util.Iterator;

import org.ebayopensource.dsf.active.util.HtmlBuilderHelper;
import org.ebayopensource.dsf.html.dom.DHtmlCollection;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DImg;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.IEEventType;
import org.ebayopensource.dsf.common.enums.BaseEnum;

/**
 * This class is here for controlling server side JS execution during
 * HTML parsing. It also holds the info about following detected JS operations,
 * (1) accessing (get/set) cookies (2) redirect the page via setting location 
 * (active script or using image src/onload JS) or doing internal form submit
 * (3) hacking using js and html misture to avoid detecton, such as following,
 * <script>document.write(\"<scri\")</script>pt src=\"http://d-sjc-gchoi-1.corp.ebay.com:88/ebay/v4/cbac/hack.js\"></script>
 * 
 * One can call detectJsHack to detect JS hacking mentioned above.
 */
public class JsHackDetectionCtx {
	
	private StringBuilder m_locationChange = null;
	private boolean m_cookieAccessing = false;
	private boolean m_scriptHacking = false;

	private static ThreadLocalContext s_context = new ThreadLocalContext();
	
	public static JsHackDetectionCtx ctx() {
		return s_context.get();
	}
	
	public static void setCtx(final JsHackDetectionCtx context) {
		s_context.set(context) ;
	}
	
	public void reset() {
		m_locationChange = null;
		m_cookieAccessing = false;
		m_scriptHacking = false;
		ActiveJsExecutionControlCtx jsControlCtx = ActiveJsExecutionControlCtx.ctx();
		jsControlCtx.setExecuteJavaScript(false);
		jsControlCtx.setParseGeneratedContent(false);
	}
	
	public String getLocationChange() {
		return m_locationChange == null ? null : m_locationChange.toString();
	}
	
	public boolean hasCookieAccessing() {
		return m_cookieAccessing;
	}
	
	public boolean hasScriptHacking() {
		return m_scriptHacking;
	}
	
	public void setLocationChange(String value) {
		if (m_locationChange == null) {
			m_locationChange = new StringBuilder(value.length() + 2);
		}
		m_locationChange.append(value).append("\n");
	}

	public void setCookieAccessing(boolean value) {
		m_cookieAccessing = value;
	}
	
	public void setScriptHacking(boolean value) {
		m_scriptHacking = value;
	}	
	
	public void enableJsHackDetection() {
		ActiveJsExecutionControlCtx jsControlCtx = ActiveJsExecutionControlCtx.ctx();
		jsControlCtx.setExecuteJavaScript(true);
		jsControlCtx.setParseGeneratedContent(true);
	}
	
	public static JsHackDetectionCtx detectJsHack(URL url) {
		JsHackDetectionCtx ctx = JsHackDetectionCtx.ctx();
		ctx.reset();
		ctx.enableJsHackDetection();
		JSWindow window = HtmlBuilderHelper.parse(url);
		analyzeOtherOnloadScripts(window);
		return ctx;
	}
	
	public static JsHackDetectionCtx detectJsHack(String src, URL baseUrl) {
		JsHackDetectionCtx ctx = JsHackDetectionCtx.ctx();
		ctx.reset();
		ctx.enableJsHackDetection();
		JSWindow window = HtmlBuilderHelper.parse(src, baseUrl);
		analyzeOtherOnloadScripts(window);
		return ctx;
	}
	
	private static void analyzeOtherOnloadScripts(JSWindow window) {
		DHtmlDocument doc = window.getHTMLDocument();
		DHtmlCollection images = doc.getImages();
		int size = images.getLength();
		for (int i = 0; i <size; i++) {
			DImg image = (DImg)images.item(i);
			String src = image.getHtmlSrc().toLowerCase().trim();
			if (src.startsWith("javascript:")) {
				String script = src.substring(11);
				try {
					window.executeScript(script);
				}
				catch (Exception e) {
					//ignore for now
				}
			}
			
			Iterator itr = EventType.getIterator(EventType.class);
			execEventJs(window, image, itr);
			
			Iterator itr2 = IEEventType.getIterator(IEEventType.class);
			execEventJs(window, image, itr2);
			
		}
	}

	private static void execEventJs(JSWindow window, DImg image, Iterator itr) {
		while(itr.hasNext()){
			BaseEnum evt = (BaseEnum)itr.next();
			String onerror = image.getAttribute("on" + evt.getName());
			if (onerror != null && onerror.length() > 0) {
				try {
					window.executeScript(onerror);
				}
				catch (Exception e) {
					//ignore for now
				}
			}
			
			
		}
	}
	
	private static class ThreadLocalContext extends ThreadLocal<JsHackDetectionCtx> {
		protected JsHackDetectionCtx initialValue() {
			return new JsHackDetectionCtx();
		}
	}
}
