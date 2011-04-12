/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.anno.field;



public class TestClass{
	
	private void testExcludeField(){
		ExcludeField tobj = new ExcludeField();
		
		// field
		String s1 = tobj.m_excludedField1;
		
		String s2 = tobj.getExcludedField2();
		
		String s3 = ExcludeField.s_excludedField3;
		
		String s4 = ExcludeField.getExcludedField4();
	}
	
}
