/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.nested;


public class DeepEmbededInstance {
	class Inner1 {
		class Inner2 {
			class Inner3 {
				public String foo3(){
					return "foo3";
				};
			}
			public String foo2(){
				return "foo2";
			};
		}
		public String foo1(){
			return "foo1";
		};
	}
	
	public static void test(){
		Inner1 inner1 = new DeepEmbededInstance().new Inner1();
		Inner1.Inner2 inner2 = inner1.new Inner2();
		Inner1.Inner2.Inner3 inner3 = inner2.new Inner3();
	}
}
