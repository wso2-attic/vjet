/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import org.ebayopensource.dsf.javatojs.tests.data.Person;

public class InheritanceTest extends Person implements Interface {

	public String sex = "male";

	public void rex() {
		for (int i = 0; i < 10; i++) {
			// Some Code go here
		}
	}

	public Employee mix(
			org.ebayopensource.dsf.javatojs.tests.data.structure.dup.Employee employee) {
		for (int i = 0; i < 10; i++) {
			// Some Code go here
		}
		return null;
	}

	public boolean handle2() {
		// TODO Auto-generated method stub
		return false;
	}

	public int total() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean handle(boolean debug) {
		// TODO Auto-generated method stub
		return false;
	}

}
