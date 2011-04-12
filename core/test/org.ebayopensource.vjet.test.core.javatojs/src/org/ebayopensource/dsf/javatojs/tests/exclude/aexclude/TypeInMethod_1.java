/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.aexclude;


public class TypeInMethod_1 {
	
	public void m_m1(){
		A1 a1;
		A2 a2 = new A2();
		new A3();
		{
			A4 a4;
			A5 a5 = new A5();
			
		}
		Object o = new A6();
		return;
	}
	
	
	
}
