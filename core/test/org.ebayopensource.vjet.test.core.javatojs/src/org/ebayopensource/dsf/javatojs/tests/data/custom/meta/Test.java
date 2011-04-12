/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom.meta;

public class Test {

	public void doit(){
		A a = new A();
		a.foo();
		a.foo(1);
		a.foo(2,"A");
		a.foo(3);
		a.foo(4,"C","D");
	}
}
