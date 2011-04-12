/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

public class Ex1 {
	
	private static int s_min = 3;
	private static int s_max = 8;
	
	private int m_counter = 3;
	
	static {
		s_min = 5;
	}
	
	public static void out(){
		System.out.println("out");
	}
	
	public static void out2(String psMsg){
		System.out.println(psMsg);
	}
	
	public int getCounter(){
		return m_counter;
	}
	
	public void setCounter(int picounter){
		m_counter = picounter;
	}
	
	public void showCounter() {
//		document.getElementById("counterText").textValue = this.getCounter() ;
	}
	
	public void warn(String psMsg, int piTimes) { 
//	    alert('number of times: ' + piTimes) ; 
		int i = 0 ;
		for(i =1; i <=piTimes; i++) {
//			alert(psMsg) ;
		}
	}
	
	public boolean thisGreaterThanThat(int piA, int piB) { 
		return piA > piB ? true : false ;
	}

	public String cat(String psValue) {
		return psValue + psValue ;
	}
}
