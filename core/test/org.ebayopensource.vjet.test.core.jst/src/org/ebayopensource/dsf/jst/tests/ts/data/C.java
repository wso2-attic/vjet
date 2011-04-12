/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts.data;

public class C extends B {
	
	public String c_something = m_something;
	
	protected String getFirstName(){
		return getName() + getC() + COUNTRY + ADDRESS;
	}
	
	public String getAddress(){
		return COUNTRY + CITY;
	}
}
