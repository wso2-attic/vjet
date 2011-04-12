/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.lang.reflect.Method;

import org.ebayopensource.dsf.jsnative.anno.BrowserType;
//import org.ebayopensource.dsf.jsnative.anno.SupportedBy;

/**
 * This helper class provides simple methods to
 * (1) determine the bworser type from userAgent string
 * (2) determine if a method/property was supported by a given browser type.
 */
public class BrowserSupport {
	
	public static boolean support(Method m, BrowserType type) {
		Class<?> c = m.getDeclaringClass();	
		org.ebayopensource.dsf.jsnative.anno.BrowserSupport supportedBy = m.getAnnotation(org.ebayopensource.dsf.jsnative.anno.BrowserSupport.class);
		if(supportedBy==null){
			supportedBy = c.getAnnotation(org.ebayopensource.dsf.jsnative.anno.BrowserSupport.class);
			if (supportedBy==null) {
				return true;
			}
		}	
		return support(type, supportedBy.value());
	}
	
	public static boolean support(BrowserType type, BrowserType[] supportedTypes) {
		for (BrowserType supportedType : supportedTypes) {
			// assume if browser is undefined that we support it
			
			if (supportedType.getName().equals(type.getName())) {
				int version = supportedType.getVersion();
				if (supportedType.isPlus()) {
					return type.getVersion() >= version;
					
				} else {
					return version == type.getVersion();
				}
			}
		}
		return false;
	}
	
	public static BrowserType getType(String userAgent) {
		if (userAgent.indexOf("Opera") != -1) {
			if (userAgent.contains("Opera/8")) {
				return BrowserType.OPERA_8P;
			}  else if (userAgent.contains("Opera/9")) {
				return BrowserType.OPERA_9P;
			} else {
				return BrowserType.OPERA_7P;
			}
			
		}
		if (userAgent.indexOf("Safari") != -1) {
			return BrowserType.SAFARI_3P;
		}
		//make sure that this if-statement is after those for Opera and Safari
		if (userAgent.indexOf("Firefox") != -1) {
			if (userAgent.contains("Firefox/2")) {
				return BrowserType.FIREFOX_2P;
			}  else if (userAgent.contains("Firefox/3")) {
				return BrowserType.FIREFOX_3P;
			} else {
				return BrowserType.FIREFOX_1P;
			}
		}
		if (userAgent.indexOf("MSIE") != -1) {
			if (userAgent.contains("MSIE 7")) {
				return BrowserType.IE_7P;
			}  else if (userAgent.contains("MSIE 8")) {
				return BrowserType.IE_8P;
			} else {
				return BrowserType.IE_6P;
			}
		}
		// If all fails, default to IE 6
		return BrowserType.IE_6P;
	}
}
