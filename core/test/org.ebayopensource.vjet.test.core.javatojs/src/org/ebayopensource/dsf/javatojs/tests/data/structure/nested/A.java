/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.nested;


public class A {

	private static int s_outer;
	private String m_outer;
	
	public static void outerStatic(){
		X.s_inner++;
		X.innerStatic();
		
		A.X.s_inner++;
		A.X.innerStatic();
		
		X x1 = new X();
		X x2 = new A.X();
	}
	
	public void outerInstance(){
		X.s_inner++;
		X.innerStatic();
		
		A.X.s_inner++;
		A.X.innerStatic();
		
		X x1 = new X();
		X x2 = new A.X();
	}
	
	public static class X {
		private static int s_inner;
		private String m_inner;
		
		public static void innerStatic(){
			s_outer++;
			outerStatic();
			A.s_outer++;
			A.outerStatic();
			
			A aa = new A();
		}
		
		public void innerInstance(){
			s_outer++;
			outerStatic();
			A.s_outer++;
			A.outerStatic();
			
			A aa = new A();
		}
	}
}
