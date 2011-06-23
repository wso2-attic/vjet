/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.anno;

public enum BrowserType {
	IE_6 ("IE" , 6),			// MSIE 6.x only
	IE_6P ("IE" , 6, true),		// MSIE 6.x and above
	IE_7 ("IE" , 7),			// MSIE 7.x only
	IE_7P ("IE" , 7, true),		// MSIE 7.x and above
	IE_8 ("IE" , 8),			// MSIE 8.x only
	IE_8P ("IE" , 8, true),		// MSIE 8.x and above
	IE_9("IE", 9), // MSIE 9.x
	IE_9P("IE", 9, true),// MSIE 9.x and above
	FIREFOX_1 ("FIREFOX" , 1),			// Firefox/1.x only
	FIREFOX_1P ("FIREFOX" , 1, true),	// Firefox/1.x and above
	FIREFOX_2 ("FIREFOX" , 2),			// Firefox/2.x only
	FIREFOX_2P ("FIREFOX" , 2, true),	// Firefox/2.x and above
	FIREFOX_3 ("FIREFOX" , 3),			// Firefox/3.x only
	FIREFOX_3P ("FIREFOX" , 3, true),	// Firefox/3.x and above
	FIREFOX_4P("FIREFOX", 4, true),					
	FIREFIX_4("FIREFOX", 4),
	FIREFOX_5P("FIREFOX", 5, true),					
	FIREFIX_5("FIREFOX", 5),
	OPERA_7 ("OPERA" , 7),				// Opera/7.x only
	OPERA_7P ("OPERA" , 7, true),		// Opera/7.x and above
	OPERA_8 ("OPERA" , 8),				// Opera/8.x only
	OPERA_8P ("OPERA" , 8, true),		// Opera/8.x and above
	OPERA_9 ("OPERA" , 9),				// Opera/9.x only
	OPERA_9P ("OPERA" , 9, true),		// Opera/9.x and above
	SAFARI_3 ("SAFARI" , 3),			// Safari Version/3.x only
	SAFARI_3P ("SAFARI" , 3, true),		// Safari Version/3.x and above
	RHINO_1 ("Rhino", 1),				// Mozilla Rhino 1.x (not a browser but JS engine)
	RHINO_1P ("Rhino", 1, true),		// Mozilla Rhino 1.x and above (not a browser but JS engine)
	NONE ("None", 0), 
	CHROME_1P("CHROME", 1, true),       // Chrome browser 1.x and above
	UNDEFINED("UNDEFINED", 0); // Not supported by any of the listed browsers

	
	
	private String m_name;
	private int m_version;
	private boolean m_plus = false;
	
	BrowserType (String name, int version) {
		m_name = name;
		m_version = version;
	}
	
	BrowserType (String name, int version, boolean plus) {
		m_name = name;
		m_version = version;
		m_plus = plus;
	}
	
	public String getName() {
		return  m_name;
	}
	
	public int getVersion() {
		return  m_version;
	}
	
	public boolean isPlus() {
		return  m_plus;
	}
	
	public boolean isIE() {
		switch(this) {
        	case IE_6: 
        	case IE_7:
        	case IE_8:
        	case IE_6P: 
        	case IE_7P:
        	case IE_8P:
        		return true;
        	default:
        		return false;
		}
	}
	
	public boolean isFireFox() {
		switch(this) {
        	case FIREFOX_1: 
        	case FIREFOX_2:
        	case FIREFOX_3:
        	case FIREFOX_1P: 
        	case FIREFOX_2P:
        	case FIREFOX_3P:
        		return true;
        	default:
        		return false;
		}
	}
	
	public boolean isOpera() {
		switch(this) {
        	case OPERA_7: 
        	case OPERA_8:
        	case OPERA_9:
        	case OPERA_7P: 
        	case OPERA_8P:
        	case OPERA_9P:
        		return true;
        	default:
        		return false;
		}
	}
	
	public boolean isSafari() {
		switch(this) {
        	case SAFARI_3:
        	case SAFARI_3P:
        		return true;
        	default:
        		return false;
		}
	}
}
