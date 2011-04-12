/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

public class Array {
	
	private long m_avg = 5;
	private long[] m_la = {1+m_avg,2,3};
	private int[][] m_twoDimArray = {{1,2,3}, {4,5,6}};
	
	static private String[] s_sa = {"a","b","c"};
	
	boolean  m_boolArr1[] ={true,false};
	static Employee  s_boolArr2[][] ={{new Employee()}};
	
	public void initializer(){
		long avg = 10;
		long[] la = {1+avg,2,3};
		int[][] twoDimArray = {{1,2,3}, {4,5,6}};
		
		String prefix = "Pre";
		String[] sa = {prefix + "a", prefix + "b", prefix + "c"};
		
		String[] sa2 = new String[2];
		
		Employee[] sa3 = new Employee[2];
		
		boolean[]  boolArr1 = new boolean[]{true};
		boolean[][]  boolArr2 = new boolean[][]{{false}};
		
		boolean[]  boolArr11 = new boolean[twoDimArray[0][0]];
		boolean[][]  boolArr12 = new boolean[twoDimArray[0][0]][twoDimArray[0][1]];
	}
	
	public void creation(){
		int avg = 3;
		long[] la = new long[avg + 3];
		
		String[] sa = new String[avg + 3];
		
		long[][][] la3 = new long[avg][][];
		
		for (int i=0; i<avg; i++){
			la3[i] = new long[i+2][];
			for (int j=0; j<i+2; j++){
				la3[i][j] = new long[j+2];
				for (int k=0; k<j+2; k++){
					la3[i][j][k] = k;
				}
			}
		}
	}
	
	public void access(){
		int avg=2;
		String[][] sa2 = new String[avg][];
		
		for (int i=0; i<avg; i++){
			sa2[i] = new String[i+2];
			String line = "Row " + String.valueOf(i) + ": ";
			for (int j=0; j<i+2; j++){
				sa2[i][j] = String.valueOf(j);
				line += sa2[i][j] + ", ";
			}
		}
		
		process(new int[]{});
	}
	
	public void process(int arr[]) { //syntax int[] arr as arg works fine
		for (int i : arr){
		        i++;
		}
	}
}
