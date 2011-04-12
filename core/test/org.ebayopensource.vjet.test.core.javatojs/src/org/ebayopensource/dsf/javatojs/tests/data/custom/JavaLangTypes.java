/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

public class JavaLangTypes {
	public int testString(){
		String text = " ABC ";
		int index = text.indexOf("C");
		int length = text.length();
		String name = text.trim();
		String value = name.toString();
		byte[] bytes = value.getBytes();
		return text.compareTo(value);
	}
	
	// fix bugzilla 4676:
	// http://quickbugstage.arch.ebay.com/show_bug.cgi?id=4676
	public void testStringHashCode() {
		LinkedList<Object> v_linkObj1 = new java.util.LinkedList<Object>();
		assertEquals(true,v_linkObj1.add("ABC".hashCode()));
	}
	public void testBoolean(){
		Boolean isOk = Boolean.TRUE;
		boolean isFalse = Boolean.FALSE.booleanValue();
	}
}
