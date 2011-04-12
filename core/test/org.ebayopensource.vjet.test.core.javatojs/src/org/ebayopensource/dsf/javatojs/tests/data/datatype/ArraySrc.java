/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.datatype;

/*
 * Class uses arrays
 * byte,short,int,long,float,double,char,boolean,String type arrays
 * single and multi dimensional array
 */

public class ArraySrc {

	int i = 1;

	boolean[] m_boolArr;

	boolean m_boolArr1[];

	short[] m_shortArr;

	short m_shortArr1[];

	int[] m_intArr;

	int m_intArr1[];

	int[] s;

	long[] m_longArr;

	long m_longArr1[];

	float[] m_floatArr;

	float m_floatArr1[];

	double[] m_doubleArr;

	double m_doubleArr1[];

	byte[] m_byteArr;

	byte m_byteArr1[];

	char[] m_charArr;

	char m_charArr1[];

	String[] m_strArr;

	String m_strArr1[];

	// multidimentional array
	int[][] m_intArr3;

	int[][] m_intArr4;

	int[] m_intArrA, m_intArrB;

	int[][] m_intArrC, m_intArrD;

	// declaration
	public void declartion() {
		m_boolArr = new boolean[5];
		m_boolArr1 = new boolean[5];
		m_shortArr = new short[2];

		m_intArr = new int[0];

		m_longArr = new long[1];

		m_floatArr = new float[3];
		m_doubleArr = new double[5];

		m_charArr = new char[2];
		m_byteArr = new byte[2];
		m_strArr = new String[5 - i];
		m_strArr1 = new String[4 - 1];

		m_intArr3 = new int[6][];
		m_intArr4 = new int[6][7];

		m_intArrA = null;
		m_intArrC = null;

	}

	// initialisation
	public void initialisation() {

		// variation
		boolean[] v_boolArr;
		boolean[] v_boolArr1 = null;
		boolean[] v_boolArr2 = { true, false, true };
		boolean[] v_boolArr3 = new boolean[] { true, false, true };
		boolean[] v_boolArr4 = new boolean[4];
		boolean[][] v_boolArr5 = new boolean[4][4];
		boolean[][] v_boolArr6 = new boolean[4][4];

		int[] v_intArr;
		int[] v_intArr1 = null;
		int[] v_intArr2 = { 1, 2, 3 };
		int[] v_intArr3 = new int[] { 1, 2, 3 };
		int[] v_intArr4 = { 1 + 5, 2 - 7, 3 - 200 };
		
		byte i = 123;
		int[] v_intArr5 = new int[] { i - 5, 2 - 7, i - 200 };
		int[] v_intArr6 = { (int) Math.pow(2, 2), 1 };
		int[][][] v_intArr7 = new int[6][7][8];
		
		byte[] v_bytelArr = { 123, -123, 0 };
		byte[] v_bytelArr1 = {};
		short[] v_shortArr = { 123, -123, 0 };
		long[] v_longArr = { 1L, 20000, 30l };
		long[] v_longArr1 = { 1l, 20000, 300l };
		
		
		float[] v_floatArr = { 1, 20000f, -30.00f };
		float[] v_floatArr1 = { 1, 20000F, -30.00F };
		
		double[] v_doubleArr = { 1.0, 20000d, -30.099d };
		double[] v_doubleArr1 = { 1.0, 20000D, -30.099D };
		
		char[] v_charArr = { 'A', 65, '\u0006', '\n' };
		String[] v_str = { "J", "AVA", "\t", "\n", "TOJS" };
		String[] v_str1 = { "J" + "A", "VA" + "!", "\t", "\n", "TOJS" + 1, };

	}

	public void setValues() {
		int[] v_intArr = new int[5];
		int v_intValue = v_intArr[0];

		boolean[] v_boolArr = new boolean[5];
		boolean v_bValue = v_boolArr[0];

		char[] v_charArr = new char[5];
		char v_cValue = v_charArr[0];

		byte[] v_byteArr = new byte[5];
		byte v_byte = v_byteArr[5];

	}

	public void getValues() {
		int[] v_intArr = new int[] { 1 + 5, 2 - 7, 3 - 200 };
		int v_int = v_intArr[2];

		int[] v_intArr1 = { 1 + 5, 2 - 7, 3 - 200 };
		int v_int1 = v_intArr1[2];

		char[] v_charArr = { 'A', 65, '\u0006', '\n' };
		char v_char = v_charArr[2];

	}

	// two and more dimensional arrays
	public void multiArrInit() {
		m_intArr3 = new int[3][];
		m_intArr4 = new int[4][5];
		int[][] twoDimArray = { { 1, 2, 3 }, { 1, 1, 1 }, { 7, 8, 9 } };
		int[][] twoDimArray1 = { { 1, 2, 3 }, { 1, 1, 1 }, { 7, 8, 9 } };
		int[][] irregular = { { 1 }, { 2, 3 }, { 4, 5, 6, 7 }, { 0 } };

	}

	public void methodsAvail() {
		int[] v_intArr = { 1, 2, 3, 4 };
		int size = v_intArr.length;
		String v_strArr = v_intArr.toString();
		int[] v_intArr1 = { 1, 2, 3, 4 };
		boolean v_equals = v_intArr.equals(v_intArr1);
		String[] v_str = { "J", "AVA", "\t", "\n", "TOJS" };
		int v_strLenght = v_str.length;

	}
	
	public void length() {
        int[] array1 = { 2, 3, 4, 5, 8, 9 };
        for (int j = 0; j < array1.length; j++) {
        }
	}
}
