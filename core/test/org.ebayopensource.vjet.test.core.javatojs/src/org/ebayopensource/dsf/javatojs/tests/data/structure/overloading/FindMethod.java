/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.overloading;

/**
 * Autoboxing/unboxing should behavior same as in Java.
 * For example, if there are more than one mtd that need boxing/unboxing, 
 * the method selection order is based on the position of the first arg that need
 * boxing or unboxing.
 */
public class FindMethod {

	public void foo(Integer x, long y){
	}
	
	public void foo (int x, Long y){
	}
	
	public static void invoke(){
		FindMethod findMtd = new FindMethod();
		findMtd.foo(1, 2);
		findMtd.foo(1, new Long(2));
		findMtd.foo(new Integer(1), 2);
	}
}
