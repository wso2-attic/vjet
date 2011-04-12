/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.nested;


public class B {
	private static int s_outer;
	private String m_outer;
	
	public static void outerStatic(){
		
		Y y1 = new B().new Y();
		
		B bb = new B();
		Y y2 = bb.new Y();
	}
	
	public void outerInstance(){
		
		Y y1 = new Y();
		Y y2 = new B.Y();
		Y y3 = new B().new Y();
		
		B bb = new B();
		Y y4 = bb.new Y();
	}
	
	public class X extends A.X {
		
	}
	
	public class Y {

		private String m_inner;
		
		public void innerInstance(){
			s_outer++;
			outerStatic();
			B.s_outer++;
			B.outerStatic();
			
			m_outer = null;
			outerInstance();
			
			B bb = new B();
		}
	}
}
