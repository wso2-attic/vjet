/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.datatype;

import org.ebayopensource.dsf.javatojs.tests.data.A;

public class ObjectMethods {
	public boolean foo() {
		A a = new A();
		A b = new A();
		return a.equals(b);
	}
	
	public boolean foo1(){
		A a = new A();
		int[] array = {1,2};
		return array.equals(a);
	}
	
	public boolean foo2() {
		A a = new A();
		A b = new A();
		return this.equals(b);
	}
	
	public boolean foo3() {
		A a = new A();
		A b = new A();
		return super.equals(b);
	}
}
