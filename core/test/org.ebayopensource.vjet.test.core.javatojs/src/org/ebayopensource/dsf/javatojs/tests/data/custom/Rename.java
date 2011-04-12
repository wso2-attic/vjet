/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom;

import org.ebayopensource.dsf.javatojs.anno.ARename;

@ARename(name="typeX")
public class Rename {
	
	@ARename(name="fldA")
	private String m_field;
	
	@ARename(name="fldB")
	private static String s_field;

	@ARename(name="mtdA")
	public void foo(){
		
	}
	
	@ARename(name="mtdB")
	public static void bar(){
		
	}
	
	public void doit(){
		String name = m_field + s_field;
		foo();
		bar();
	}
}
