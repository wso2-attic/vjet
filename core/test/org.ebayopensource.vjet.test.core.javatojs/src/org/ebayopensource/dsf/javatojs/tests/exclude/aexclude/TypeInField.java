/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.aexclude;

public class TypeInField {
	A1 a1;
	A2 a2 = new A2();
	static{
		A3 a3;
		A4 a4 = new A4();
		new A6();
	}
	A5 a5, a51 = new A5();
	static A7 a7;

}
