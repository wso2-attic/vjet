/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.ns;

import org.ebayopensource.dsf.javatojs.tests.data.Super;

public class SuperInstance extends Super {

	private String m_this1 = m_super;
	private String m_this2 = super.m_super;
	private String s_this3 = getSuperInstanceMtd();
	private String s_this4 = super.getSuperInstanceMtd();
	
	public String foo(){
		return m_super + super.m_super 
			+ getSuperInstanceMtd() + super.getSuperInstanceMtd();
	}
}
