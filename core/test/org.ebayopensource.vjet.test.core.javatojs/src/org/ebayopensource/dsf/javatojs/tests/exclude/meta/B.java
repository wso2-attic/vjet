/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.meta;


public class B {
	// below all methods are excluded in meta
	public String m_m1(){
		return null;
	}
	public void m_m2(){
		
	}	
	public String m_m3(String s){
		return null;
	}	
	
	public void m_m4(String s){
		
	}	
	
	
	
	public static String s_m1(){
		return null;
	}
	public  static  void s_m2(){
		
	}	
	public  static String s_m3(String s){
		return null;
	}	
	
	public  static void s_m4(String s){
		
	}	
	

}
