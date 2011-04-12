/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * Created on Jan 12, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.ebayopensource.dsf.html.js;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.common.naming.IDsfName;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.DScript;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JSFunctionCall extends DScript {
	// create a function call for Js without using a Id - this is used by IDPreprocessor
	//expandcontent('CustSearchPage', 'OPTION', CustSearchPageTab.tabA['CustSearchPage.OPTION.a']);

	String m_functionName = "";
	Map m_params = new HashMap();
	String m_paramSeperator = ",";
	public void addParam(BaseHtmlElement param) {
		m_params.put(param, "");
	}

	public void addParam(String param) {
		m_params.put(param, "");
	}

	public String createJSFunctionCall() {

		final StringBuffer text = new StringBuffer();
		text.append(m_functionName + "(");

		Set s = m_params.keySet();
		Iterator i = s.iterator();
		String param = "";
		while (i.hasNext()) {
			final Object o = i.next();
			if (o instanceof BaseHtmlElement) {
				BaseHtmlElement x = (BaseHtmlElement) o;
				IDsfName name = x.getDsfName();
				param = name.getFullName();

			}
			if (!param.equals("")) {
				text.append("'" + param + "'");
			}

			if (i.hasNext()) {
				text.append(m_paramSeperator);
			}

			text.append(");");

			
		}
		
		return text.toString();
	}
	/**
	 * @return
	 */
	public String getFunctionName() {
		return m_functionName;
	}

	/**
	 * @param string
	 */
	public void setFunctionName(String string) {
		m_functionName = string;
	}

}
