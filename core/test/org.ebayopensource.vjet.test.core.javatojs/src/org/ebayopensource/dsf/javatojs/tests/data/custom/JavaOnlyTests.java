/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;

public class JavaOnlyTests {
	public void doIt() {

		String p = A.prototype;
		int q = A.prototype.indexOf("a");
		
		A a = new A();
		B b = new B();
		
		p = a.foo();
		q = a.foo().indexOf("b");
		
		p = B.sNAME;
		p = B.sfoo();
		
		p = b.sNAME;
		p = b.sfoo();
		p = b.iNAME;
		p = b.ifoo();
	}
	
	static public class A { 

		@AJavaOnly
		public final static String prototype = null;
		
		@AJavaOnly
		public String foo(){return null;}
	}
	
	@AJavaOnly
	static public class B {

		public static String sNAME;
		public static String sfoo(){return null;};
		
		public String iNAME;
		public String ifoo(){return null;};
	}
}
