/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom;

import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.AMappedToJS;
import org.ebayopensource.dsf.javatojs.anno.AMappedToVJO;

public class OverloadedMethods {
	@AMappedToJS(name = "xyx")
	public void foo() {

	}
	
	@AExclude
	public void foo(Boolean v) {

	}
	
	@AJavaOnly
	public void foo(String arg) {

	}

	@AMappedToVJO(name = "a.b.c.Y")
	public void foo(Integer arg) {

	}

	public static void main(String[] args) {
		OverloadedMethods test = new OverloadedMethods();
		test.foo();
		test.foo(Boolean.TRUE);
		test.foo("booyah");
		test.foo(10);
	}
}
