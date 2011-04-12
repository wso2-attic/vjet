/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.api.util;

import org.ebayopensource.dsf.dap.proxy.Global;

public class DapJsHelper {
	
	//
	// Singleton
	//
	private static final DapJsHelper s_instance = new DapJsHelper();
	private DapJsHelper(){}
	public static DapJsHelper getInstance(){
		return s_instance;
	}
	
	//
	// API
	//
	/*
	 * JS Global functions
	 */
	public String encodeURI(String s){
		return Global.encodeURI(s);
	}
	
	public String encodeURIComponent(String s){
		return Global.encodeURIComponent(s);
	}
	
	public String decodeURI(String str){
		return Global.decodeURI(str);
	}
	
	public String decodeURIComponent(String s){
		return Global.decodeURIComponent(s);
	}
	
	public String escape(String s){
		return Global.escape(s);
	}
	
	public String unescape(String s){
		return Global.unescape(s);
	}
	
	public int parseInt(String s){
		return Global.parseInt(s);
	}
	
	public int parseInt(String s, int radix){
		return Global.parseInt(s, radix);
	}
	
	public float parseFloat(String s){
		return Global.parseFloat(s);
	}
	
	public boolean isFinite(Number n){
		return Global.isFinite(n);
	}
	
	public boolean isNaN(Object o){
		return Global.isNaN(o);
	}
	
	public Object eval(String s){
		return Global.eval(s);
	}
	
	//
	// VJIT extensions
	//
	
	/**
	 * In JavaScript you often will see code like the following:
	 * 
	 * if (window.focus) { ... }
	 * 
	 * What is going on is that JavaScript will interpret this as
	 * a check for undefined which if the result of window.focus is
	 * undefined it is treated as a false.  This paradigm does not
	 * translate well to Java.  First the focus() method is actually
	 * a method not a property.  To get around this we have a utility
	 * method to represent this check:
	 * VJ.js.isDefined(window, "focus")  --> window.focus
	 */
	public boolean isDefined(Object obj, String propName) {
		return Global.isDefined(obj, propName);
	}	
}
