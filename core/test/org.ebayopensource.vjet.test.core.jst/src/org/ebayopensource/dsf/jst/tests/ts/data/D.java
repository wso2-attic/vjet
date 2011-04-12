/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts.data;

public class D {
	public static String s_d_static_var = "";
	protected int m_d_instance_var = 2;
	protected int[] m_d_instance_array = new int[10];
	public static C s_d_c = new C();
	public C m_d_c = new C();
	
	public C getC(){
		return new C();
	}
	public static B getB(){
		return new B();
	}
	public static int getLength(String s){
		return s.length();
	}
	public static D getD(){
		return new D();
	}
	public String getStrD(){
		return "something";
	}
	public static String getSomethingD(){
		return "something";
	}
	public void method_usages(){
		String s = getB().getName();
		this.s_d_c.getName();
		A.getC().getName();
		B.getC().getName();
		new D().m_d_c.getName();
		
		B.getSomething();
		A.getSomething();
		
	}
	public void property_usages(){
		A.s_avar1 = 123; // assign to prop: static
		A.s_aa[1] = 1; // assign to prop in array
		this.m_d_instance_array[0] = 0; // assign to prop: instance
		
		int usage_array_prop = A.s_aa[0]+1; // usage of array prop
		int usage_static_prop = A.s_avar1+2;
		int usage_local_static_prop = s_d_static_var.length();
		int usage_instance_prop = this.m_d_instance_var+3;
		A a = new A();
		int usage_instance_prop1 = a.m_avar1.hashCode();
	}
}