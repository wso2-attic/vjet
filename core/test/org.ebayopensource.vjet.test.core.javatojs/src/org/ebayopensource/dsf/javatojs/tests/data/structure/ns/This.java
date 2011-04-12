/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.ns;


public class This {
	private static int s_total;
	private int m_amount;
	
	public void staticMtd(){
		s_total++;
		This.s_total++;
	}
	
	public void instanceMtd(){
		s_total++;
		This.s_total++;
		
		m_amount++;
		
		new This().m_amount++;
	}
}
