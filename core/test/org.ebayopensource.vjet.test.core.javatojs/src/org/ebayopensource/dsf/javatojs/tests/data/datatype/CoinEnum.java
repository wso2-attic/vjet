/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.datatype;

public enum CoinEnum {
	PENNY(1), NICKEL(5), DIME(10), QUARTER(25);
	private final int m_value;
	
	CoinEnum(int value) { m_value = value; }
	
	public int value() { return m_value; }

}
