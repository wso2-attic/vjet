/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.translate;


public class B {
	
	public void m1(){
		A.s_field1 = 20;
		
	}
	
	public void m2(){
		A.s_m1();
	}
	
	public void m3(){
		new A().m_field2 = 20;
	}
	
	public void m4(){
		new A().m_m1();
	}
	
	
	
}
