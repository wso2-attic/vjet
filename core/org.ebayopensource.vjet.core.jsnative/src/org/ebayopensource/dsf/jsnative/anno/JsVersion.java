/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.anno;

public enum JsVersion {
	NONE("none", "0"),
	MOZILLA_ONE_DOT_ZERO("Mozilla", "1.0"),
	MOZILLA_ONE_DOT_ONE("Mozilla", "1.1"),
	MOZILLA_ONE_DOT_TWO("Mozilla", "1.2"),
	MOZILLA_ONE_DOT_THREE("Mozilla", "1.3"),
	MOZILLA_ONE_DOT_FOUR("Mozilla", "1.4"),
	MOZILLA_ONE_DOT_FIVE("Mozilla", "1.5"),
	MOZILLA_ONE_DOT_SIX("Mozilla", "1.6"),
	MOZILLA_ONE_DOT_SEVEN("Mozilla", "1.7"),
	MOZILLA_ONE_DOT_EIGHT("Mozilla", "1.8"),
	JSCRIPT_ONE_DOT_ZERO("JScript", "1.0"),
	JSCRIPT_TWO_DOT_ZERO("JScript", "2.0"),
	JSCRIPT_THREE_DOT_ZERO("JScript", "3.0"),
	JSCRIPT_FOUR_DOT_ZERO("JScript", "4.0"),
	JSCRIPT_FIVE_DOT_ZERO("JScript", "5.0"),
	JSCRIPT_FIVE_DOT_ONE("JScript", "5.1"),
	JSCRIPT_FIVE_DOT_FIVE("JScript", "5.5"),
	JSCRIPT_FIVE_DOT_SIX("JScript", "5.6"),
	JSCRIPT_EIGHT_DOT_ZERO("JScript", "8.0"), 
	UNDEFINED("UnknownJSRuntime", "0.0");
	
	private String m_name;
	private String m_version;

	private JsVersion(String name, String version) {
		m_version = version;
		m_name = name;
	}

	public String getVersion() {
		return m_version;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		m_name = name;
	}
	
	
}
