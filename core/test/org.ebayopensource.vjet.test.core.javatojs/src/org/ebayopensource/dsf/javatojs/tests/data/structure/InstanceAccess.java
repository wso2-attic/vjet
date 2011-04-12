/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;


public class InstanceAccess {
	int m_x=0;
	int m_y=0;
	
	public void foo(InstanceAccess x, int a){
		m_x = x.m_x;
		m_x = m_y;
		x.foo(x, m_y);
		x.foo(x, x.m_y);
	}
	
	public void bar(Fields f, Methods m){
		Boolean b = f.m_Ba;
		m.varargs(b.toString());
	}
}
