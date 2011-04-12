/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.build;


public class Expressions {
	
	private long m_a = 5; 
	private String m_s = "ready";
	private static String[] s_as = {"a", "b", "c"};
	
	public long arithmetic(int start){
		
		start++;
		m_a++;
		
		--start;
		--m_a;
		
		long a = m_a;
		a = start + 1;
		a += m_a + 25;
		
		m_a = m_a + 15;
		m_a += start + start * 12 + start * (-(1 + 2));
		
		int index = 0;
		s_as[0] = "first";
		s_as[index + 1] = "middle";
		m_s = s_as[0] + s_as[index+1];
		
		int y = 0 + 1 + 2 + 3*3;
		String value = s_as.toString() +" is " + m_s.toString();
		
		return (index > (start + 1)) ? (2+1) : ((m_a) * 10);
	}
}
