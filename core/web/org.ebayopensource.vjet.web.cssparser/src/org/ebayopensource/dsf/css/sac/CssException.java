/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

public class CssException extends RuntimeException {

	public static final short SAC_UNSPECIFIED_ERR = 0;
	public static final short SAC_NOT_SUPPORTED_ERR = 1;
	public static final short SAC_SYNTAX_ERR = 2;
	protected static final String S_SAC_UNSPECIFIED_ERR = "unknown error";
	protected static final String S_SAC_NOT_SUPPORTED_ERR = "not supported";
	protected static final String S_SAC_SYNTAX_ERR = "syntax error";

	protected String m_s;	
	protected Exception m_e;
	protected short m_code;
	
	public CssException() {
		/* empty */
	}
	
	public CssException(String string) {
		m_code = (short) 0;
		m_s = string;
	}
	
	public CssException(Exception exception) {
		m_code = (short) 0;
		m_e = exception;
	}
	
	public CssException(short i) {
		m_code = i;
	}
	
	public CssException(short i, String string, Exception exception) {
		m_code = i;
		m_s = string;
		m_e = exception;
	}
	
	public String getMessage() {
		if (m_s != null)
			return m_s;
		if (m_e != null)
			return m_e.getMessage();
		switch (m_code) {
			case 0 :
				return "unknown error";
			case 1 :
				return "not supported";
			case 2 :
				return "syntax error";
			default :
				return null;
		}
	}
	
	public short getCode() {
		return m_code;
	}
	
	public Exception getException() {
		return m_e;
	}
}
