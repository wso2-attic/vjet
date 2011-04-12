/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

public class TranslateConfig {
	private boolean m_skiptImplementation = false;
	private boolean m_skipJsExtSyntaxArgs;
	private boolean m_allowPartialJST;

	
	public boolean isSkiptImplementation() {
		return m_skiptImplementation;
	}
	public void setSkiptImplementation(boolean implementation) {
		m_skiptImplementation = implementation;
	}
	public boolean isSkipJsExtSyntaxArgs() {
		return m_skipJsExtSyntaxArgs;
	}
	public void setSkipJsExtSyntaxArgs(boolean jsExtSyntaxArgs) {
		m_skipJsExtSyntaxArgs = jsExtSyntaxArgs;
	}
	
	public void setAllowPartialJST(boolean b) {
		m_allowPartialJST = b;

	}

	public boolean isAllowPartialJST() {
		return m_allowPartialJST;
	}

}
