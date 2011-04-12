/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.derivedfield;


public class TestClass{	
	
	private void test(){
		TestDerived derived = new TestDerived();
		String s1 = derived.getExcludedFieldInBase1();
		String s2 = derived.m_excludedFieldInBase2;
		String s3 = TestDerived.getExcludedFieldInBase3();
		String s4 = TestDerived.s_excludedFieldInBase4;
		
	}	
}
