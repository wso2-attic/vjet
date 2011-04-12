/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * Created on Jan 11, 2006
 */
package org.ebayopensource.dsf.html.js;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.naming.IDsfName;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.DScript;

/**
 * 
 */
public class JSSimpleInitialization extends DScript {

	BaseHtmlElement m_htmlElement = null;
	String m_varName = "";
	String m_constructorName = "";
	// used to collect function calls
	final Map<JSFunctionCall,String> m_functionCalls =
		new HashMap<JSFunctionCall,String>();
	
	public String getInitilization() {
		return "";
	}
	
	public void addFunctionCalls(final JSFunctionCall f) {
		m_functionCalls.put(f,"");
	}
	
	public void createJSinit() {
		BaseHtmlElement x = m_htmlElement;
		IDsfName name = x.getDsfName();
		String fullName = name.getFullName();
		final StringBuffer text = new StringBuffer();
		text.append(
					"var "
						+ m_varName
						+ " = new "
						+ m_constructorName
						+ "('" 
						+ fullName
						+ "');");

		for (final JSFunctionCall jsf:m_functionCalls.keySet()) {
			text.append(jsf.createJSFunctionCall());			
		}
		this.setHtmlText(text.toString());
	}

	/**
	 * @param string
	 */
	public void setVarName(String string) {
		m_varName = string;
	}

	/**
	 * @param element
	 */
	public void setHtmlElement(BaseHtmlElement element) {
		m_htmlElement = element;
	}

	/**
	 * @param string
	 */
	public void setConstructorName(String string) {
		m_constructorName = string;		
	}

}
