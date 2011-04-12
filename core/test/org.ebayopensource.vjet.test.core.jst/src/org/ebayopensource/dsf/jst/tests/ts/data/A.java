/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts.data;

public class A implements IA {

	public static final String COUNTRY = "USA";
	protected static final String CITY = "San Jose";
	int m_pupulation = 1000 * 1000;
	private String m_company = "Ebay";
	public static int s_avar1;
	protected static int[] s_aa = new int[10];
	public Object m_avar1;
	
	public String getName(){
		return m_company;
	}
	
	public static C getC(){
		return new C();
	}
	public static String getSomething(){
		return "something";
	}
}
