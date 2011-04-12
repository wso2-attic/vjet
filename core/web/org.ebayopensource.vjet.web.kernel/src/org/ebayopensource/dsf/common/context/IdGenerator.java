/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

public final class IdGenerator {
	// Most pages need a very small number of generated id's so 200
	// is a "reasonable" cache.  The caching lookup is 50% faster than
	// the toHexString(...) etc... that was being used.  Look at the various
	// toXyz(...) methods on Long, Integer, etc..., they create arrays and
	// do alot of work.
	private static final int MAX_CACHED = 200 ;
	private static final String[] s_ids = getCachedIds(MAX_CACHED) ;
	// Provide a max sequence number to prevent run off.
	public static final int MAX_SEQUENCE = 9999;
	
	private static final String JS_HAN_GEN = "jsEvt";
	private static final String JS_WIN = "jsWin";
	private static final String V4AD_HOC_CSS = "v4Css";
	private static final String V4 = "v4-";

	private String m_scope = "";
	private int m_jsSequence = 0;
	private int m_cssSequence = 0;
	private int m_htmlSequence = 0;
	private int m_jswindowSequence = 0;
	private int m_jsCompSequence = 0;

	//
	// API
	//
	public String nextJsHandlerId() {
		if (m_jsSequence == MAX_SEQUENCE) {
			resetJsHandlerId();
		}
		return JS_HAN_GEN + m_scope + getId(m_jsSequence++);
	}

	public void resetJsHandlerId() {
		m_jsSequence = 0;
	}

	public String nextHtmlId() {
		if (m_htmlSequence == MAX_SEQUENCE) {
			resetHtmlId();
		}
		return  V4 + m_scope + getId(m_htmlSequence++);
	}
	
	public String nextJsCompId() {
		if (m_jsCompSequence == MAX_SEQUENCE) {
			resetJsCompId();
		}
		return  m_scope + getId(m_jsCompSequence++);
	}

	public void resetHtmlId() {
		m_htmlSequence = 0;
	}

	public String nextAdhocCss() {
		if (m_cssSequence == MAX_SEQUENCE) {
			resetAdhocCss();
		}
		return V4AD_HOC_CSS + getId(m_cssSequence++);
	}

	public void resetAdhocCss() {
		m_cssSequence = 0;
	}

	public String nextJsWindowId() {
		if (m_jswindowSequence == MAX_SEQUENCE) {
			resetJsWindowId();
		}
		return  JS_WIN + m_scope + getId(m_jswindowSequence++);
	}

	public void resetJsWindowId() {
		m_jswindowSequence = 0;
	}
	
	public void resetJsCompId() {
		m_jsCompSequence = 0;
	}
	
	public void setScope(String scope) {
		if (scope==null) {
			return;
		}
		m_scope = scope.replaceAll("\\.", "_");
	}
	
	public void resetScope() {
		m_scope = "";
	}

	public void resetAllIds() {
		resetAdhocCss();
		resetHtmlId();
		resetJsHandlerId();
		resetJsWindowId();
		resetJsCompId();
		resetScope();
	}

	private static String getId(final int index) {
		if (index >= MAX_CACHED) {
			// toHexString(...) is pretty expensive...
			return Integer.toString(index) ;
		}
		return s_ids[index] ;
	}
	
	private static String[] getCachedIds(final int maxCached) {
		final String[] answer = new String[maxCached] ;
		for(int i = 0; i < maxCached; i++) answer[i] = Integer.toString(i) ;
		return answer ;
	}
}
