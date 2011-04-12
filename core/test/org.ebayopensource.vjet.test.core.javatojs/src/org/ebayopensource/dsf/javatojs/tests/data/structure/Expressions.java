/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import java.util.Date;

public class Expressions {
	
	private long m_a = 5; 
	private String m_s = "ready";
	private static String[] s_as = {"a", "b", "c"};
	
	
	public long arithmetic(int start){
		
		float x = 1/2;
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
	
	public int type(Object obj){
		if (obj instanceof String){
			return 1;
		}
//		else if (obj instanceof java.util.Date){ //FIXME this statement gets removed from translation
		else if (obj instanceof Date){
			return 2;
		}
		return -1;
	}
	
	public void space(){
		int a = 3;
		int b = 7 + a++ + 5;
		int c = 7 + ++a + 5;
		b = a++ + 5;
		c = 7 + ++a;
		
		byte d = 3;
		byte e = 2;
		int x = ++a  - - --b / c - d * e;
		
		int y = - -a++ + 1;
	}
}
