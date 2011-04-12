/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.datatype;

import java.util.List;


/**
 * This is a sample test for Java2JsAstParser
 *
 */
public class TestArray {
	
	private List<String> m_field1;
	private int[] m_field2;
	private String[] m_field3;
	
	public TestArray(List<String> field1, int[] field2, String[] field3) {
		m_field1 = field1;
		m_field2 = field2;
		m_field3 = field3;
	}

	public List<String> getField1() {
		return m_field1;
	}
	public void setField1(List<String> l) {
		m_field1 = l;
	}
	public int[] getField2() {
		return m_field2;
	}
	public void setField2(int[] arry) {
		m_field2 = arry;
	}
	public String[] getField3() {
		return m_field3;
	}
	public void setField3(String[] sa) {
		m_field3 = sa;
	}
	
	public void doWork () {
		m_field1.add("one");
		m_field1.add("two");
		int size = m_field1.size();
		m_field1.remove("one");
//		m_field1.remove(m_field1.size()-1);
	}
}
