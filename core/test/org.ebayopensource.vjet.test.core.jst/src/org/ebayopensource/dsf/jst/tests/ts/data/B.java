/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts.data;

public class B extends A{
	
	static final String ADDRESS = COUNTRY + CITY;
	public String m_something = A.getSomething();

	public String getTitle(){
		return getName() + getC().getFirstName();
	}
	
	public int getCount(){
		return m_pupulation * 10;
	}
	
	@Override
	public String getName(){
		String city = CITY;
		A.getSomething();
		return super.getName();
	}
}
