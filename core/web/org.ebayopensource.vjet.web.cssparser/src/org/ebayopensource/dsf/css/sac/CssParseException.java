/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

public class CssParseException extends CssException {
	private String m_uri;
	private int m_lineNumber;
	private int m_columnNumber;
	
	public CssParseException(String string, ILocator locator) {
		super(string);
		m_code = (short) 2;
		m_uri = locator.getURI();
		m_lineNumber = locator.getLineNumber();
		m_columnNumber = locator.getColumnNumber();
	}
	
	public CssParseException(
		String string,
		ILocator locator,
		Exception exception)
	{
		super((short) 2, string, exception);
		m_uri = locator.getURI();
		m_lineNumber = locator.getLineNumber();
		m_columnNumber = locator.getColumnNumber();
	}
	
	public CssParseException(
		String string,
		String string_0_,
		int i,
		int i_1_)
	{
		super(string);
		m_code = (short) 2;
		m_uri = string_0_;
		m_lineNumber = i;
		m_columnNumber = i_1_;
	}
	
	public CssParseException(
		String string,
		String string_2_,
		int i,
		int i_3_,
		Exception exception)
	{
		super((short) 2, string, exception);
		m_uri = string_2_;
		m_lineNumber = i;
		m_columnNumber = i_3_;
	}
	
	public String getURI() {
		return m_uri;
	}
	
	public int getLineNumber() {
		return m_lineNumber;
	}
	
	public int getColumnNumber() {
		return m_columnNumber;
	}
}
