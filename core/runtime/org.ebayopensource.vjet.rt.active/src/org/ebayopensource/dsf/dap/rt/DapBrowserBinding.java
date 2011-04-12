/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.dap.util.DapDomHelper;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.Window;
import org.ebayopensource.dsf.json.JsonObject;
import org.ebayopensource.dsf.liveconnect.IDLCDispatcher;

public class DapBrowserBinding implements IBrowserBinding {
	private final IDLCDispatcher m_dlcDispatcher;
	public DapBrowserBinding(final IDLCDispatcher dlcDispatcher){
		m_dlcDispatcher = dlcDispatcher;
	}

	public String getDocumentProperty(String name) {
		return m_dlcDispatcher.request("document." + name, 0);
	}

	public String getDomAttributeValue(BaseHtmlElement elem, EHtmlAttr attr) {
		return m_dlcDispatcher.request
			(DapDomHelper.getPath(elem)+"."+attr.getAttributeName(), 0);
	}

	public String getNavigatorProperty(String name) {
		return m_dlcDispatcher.request("navigator." + name, 0);
	}

	public String getScreenProperty(String name) {
		return m_dlcDispatcher.request("screen." + name, 0);
	}
	
	public int getWindowHeight() {
		int val = 0;
		String value = m_dlcDispatcher
			.request("(window.innerHeight || document.body.offsetHeight)", 0);
		if (value != null && value.length() > 0) {
			try{
				val = Integer.parseInt(value);
			}catch(Exception e){
				//return 0 incase of exception
				val = 0;
			}
		}
		return val;
	}

	public int getWindowWidth() {
		int val = 0;
		String value = m_dlcDispatcher
			.request("(window.innerWidth || document.body.offsetWidth)", 0);
		if (value != null && value.length() > 0) {
			try{
				val = Integer.parseInt(value);
			}catch(Exception e){
				//return 0 incase of exception
				val = 0 ;
			}
		}
		return val;
	}

	public void setDocumentProperty(String name, String value) {
		m_dlcDispatcher.send("document." + name + "=" + value);
	}

	public void setDomAttributeValue(BaseHtmlElement elem, EHtmlAttr attr, String value) {
		m_dlcDispatcher.send
			(DapDomHelper.getPath(elem)+"."+attr.getAttributeName() + "=" + value);
	}

	public void setNavigatorProperty(String name, String value) {
		m_dlcDispatcher.send("navigator." + name + "=" + value);
	}

	public void setScreenProperty(String name, String value) {
		m_dlcDispatcher.send("screen." + name + "=" + value);
	}

	public void setWindowHeight(int heigth) {
	}

	public void setWindowWidth(int width) {
	}
	
	public void alert(String message) {
		message = message.replace("\n", " ");
		m_dlcDispatcher.request("alert(" + JsonObject.quote(message) + ")", 0);
	}
	
	public void blur() {
		m_dlcDispatcher.send("window.blur()");
	}
	
	public void focus() {
		m_dlcDispatcher.send("window.focus()");
	}
	
	public void moveBy(int x, int y) {
		m_dlcDispatcher.send("window.moveBy(" + x + "," + y + ")");
	}
	

	public void moveTo(int x, int y) {
		m_dlcDispatcher.send("window.moveTo(" + x + "," + y + ")");
	}

	public int getPageXOffset() {
		int val = 0;
		String value = m_dlcDispatcher
		.request("(window.pageXOffset || document.body.scrollLeft)", 0);
		if (value != null && value.length() > 0) {
			try{
				val = Integer.parseInt(value);
			}catch(Exception e){
				//return 0 incase of exception
				val = 0;
			}
		}
		return val;
	}

	public int getPageYOffset() {
		int val = 0;
		String value = m_dlcDispatcher
		.request("(window.pageYOffset || document.body.scrollTop)", 0);
		if (value != null && value.length() > 0) {
			try{
				val = Integer.parseInt(value);
			}catch(Exception e){
				//return 0 incase of exception
				val = 0;
			}
		}
		return val;
	}

	public int getScreenLeft() {
		int val = 0;
		String value = m_dlcDispatcher
		.request("(window.screenLeft || window.screenX)", 0);
		if (value != null && value.length() > 0) {
			try{
				val = Integer.parseInt(value);
			}catch(Exception e){
				//return 0 incase of exception
				val = 0;
			}
		}
		return val;
	}

	public int getScreenTop() {
		int val = 0;
		String value = m_dlcDispatcher
		.request("(window.screenTop || window.screenY)", 0);
		if (value != null && value.length() > 0) {
			try{
				val = Integer.parseInt(value);
			}catch(Exception e){
				//return 0 incase of exception
				val = 0;
			}
		}
		return val;
	}

	public boolean confirm(String message) {
		String ret = m_dlcDispatcher.request("window.confirm('"+message+"')", 0); 
		return ret.equalsIgnoreCase("true")?true:false;
	}

	public void print() {
		m_dlcDispatcher.send("window.print()");
	}

	public void close() {
		m_dlcDispatcher.send("window.close()");
	}

	public String prompt(String message, String defaultReply) {
		return m_dlcDispatcher.request("window.prompt('"+message+"','"+defaultReply + "')",0);
	}

	public Window open(String url, String windowName, String features, boolean replace) {
		StringBuilder sb = new StringBuilder();
		sb.append("window.open(");
		if (url != null) {
			sb.append(url);
		}
		if (windowName != null) {
			sb.append(", ").append(windowName);
		}
		if (features != null) {
			sb.append(", ").append(features);
		}
		if (url != null && windowName != null && features != null) {
			sb.append(", ").append(replace);
		}
		sb.append(")");
		m_dlcDispatcher.send(sb.toString());
		//TODO: implementation of returning the Window object.
		return null;
	}

	public void resizeBy(int width, int height) {
		m_dlcDispatcher.send("window.resizeBy("+width+","+height + ")");
	}

	public void resizeTo(int width, int height) {
		m_dlcDispatcher.send("window.resizeTo("+width+","+height + ")");
	}

	public void scrollBy(int x, int y) {
		m_dlcDispatcher.send("window.scrollBy("+x+","+y + ")");
	}

	public void scrollTo(int x, int y) {
		m_dlcDispatcher.send("window.scrollTo("+x+","+y + ")");
	}

	public void locationAssign(String url) {
		m_dlcDispatcher.send("location.assign('"+url+"')");
	}

	public void locationReload(boolean forceGet) {
		m_dlcDispatcher.send("location.reload("+forceGet+")");
	}

	public void locationReplace(String url) {
		m_dlcDispatcher.send("location.replace('"+url+"')");
	}

	public void resetForm(String formIdOrIndex) {
		m_dlcDispatcher.send("document.forms['"+formIdOrIndex+"'].reset()");
	}

	public void clearInterval(int timerId) {
		m_dlcDispatcher.send("window.clearInterval("+timerId+");");
	}

	public void clearTimeout(int timerId) {
		m_dlcDispatcher.send("window.clearTimeout("+timerId+");");
	}

	public int setInterval(String code, int delay) {
		int timer =0;
		String ret = "";
		if(code != null && code.length() > 0){
			ret = m_dlcDispatcher.request("window.setInterval('"+code+"',"+delay+");", 0);
			try{
				timer = Integer.parseInt(ret);
			}catch(Exception e){
				//return 0 incase of exception
				timer =0;
			}
		}
		return timer;
	}

	public int setTimeout(String code, int delay) {
		int timer = 0;
		String ret = "";
		if(code != null && code.length() > 0){
			ret = m_dlcDispatcher.request("window.setTimeout('"+code+"',"+delay+");", 0);
			try{
				timer = Integer.parseInt(ret);
			}catch(Exception e){
				//return 0 incase of exception
				timer = 0 ;
			}
		}
		return timer;
	}

	public void executeDomMethod(BaseHtmlElement elem, String methodCall) {
		String js = DapDomHelper.getPath(elem) + "." + methodCall;
		m_dlcDispatcher.send(js);
	}

	public void back() {
		m_dlcDispatcher.send("window.history.back();");
	}

	public void forward() {
		m_dlcDispatcher.send("window.history.forward();");
	}

	public void go(Object o) {
		if(o instanceof Number){
			m_dlcDispatcher.send("window.history.go("+o+");");
		}else{
			m_dlcDispatcher.send("window.history.go('"+o+"');");
		}
	}

	public int historyLength() {
		String ret = m_dlcDispatcher.request("window.history.length", 0);
		try{
			return Integer.parseInt(ret);
		}catch(Exception e){
			//return 0 incase of exception
		}
		return 0;
	}

	public String executeJs(String code) {
		return m_dlcDispatcher.request(code, 0);
	}

	public String getDomAttributeValue(BaseHtmlElement elem, String attrName) {
		return m_dlcDispatcher.request
			(DapDomHelper.getPath(elem)+"."+attrName, 0);
	}

	public String getElementCurrentStyleValue(BaseHtmlElement elem, String name) {
		String path = DapDomHelper.getPath(elem);
		if (path == null) {
			return null;
		}
		return m_dlcDispatcher.request
			("DLC_getCurrentStyleValue(" + path + ", '" + name + "')", 0);
	}

	public String getElementRuntimeStyleValue(BaseHtmlElement elem, String name) {
		String path = DapDomHelper.getPath(elem);
		if (path == null) {
			return null;
		}
		return m_dlcDispatcher.request(path + ".runtimeStyle." + name, 0);
	}

	public void setElementRuntimeStyleValue(BaseHtmlElement elem, String name,
			String value) {
		String path = DapDomHelper.getPath(elem);
		if (path == null) {
			return;
		}
		m_dlcDispatcher.send(path + ".runtimeStyle." + name + "=" + value);
	}
}
