/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data;

public class DefaultHandler implements IHandler {
	private boolean m_reverse = true;
	public DefaultHandler(boolean reverse, boolean log){
		m_reverse = reverse;
	}
	public boolean handle(boolean debug){
		return m_reverse;
	};
	
	public void log(){
		// No op
	}
}
