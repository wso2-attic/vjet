/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.datatype;


/**
 * This is a sample test for Java2JsAstParser
 *
 */
public class TestEnum {
	
	private MyEnum m_enumField1 = MyEnum.one;
	private CoinEnum m_enumField2 = CoinEnum.DIME;
	
	public MyEnum getEnumField1() {
		return m_enumField1;
	}
	
	public CoinEnum getEnumField2() {
		return m_enumField2;
	}

	public void setEnumField1(MyEnum field) {
		m_enumField1 = field;
	};
	
	public void setEnumField2(CoinEnum field) {
		m_enumField2 = field;
	};
	
	enum MyEnum {one, two }

}
