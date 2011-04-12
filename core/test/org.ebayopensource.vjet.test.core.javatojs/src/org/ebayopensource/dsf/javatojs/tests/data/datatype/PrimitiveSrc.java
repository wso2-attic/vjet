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
 * Class uses the 8 java primitive types
 * byte,short,int,long,float,double,char,boolean and String
 * test Areas - default values,initialisation,wide and narrow conversion,escape sequences
 */
public class PrimitiveSrc {

	// variable declaration
	boolean m_bool;

	short m_short;

	int m_int;

	long m_long;

	float m_float;

	double m_double;

	byte m_byte;

	char m_char;

	String m_str;

	// default values
	public void defaultValue() {

		boolean v_bool = m_bool; // false

		short v_short = m_short; // 0
		int v_int = m_int; // 0
		long v_long = m_long; // 0L or 0

		float v_float = m_float; // 0.0f or 0
		double v_double = m_double; // 0.0d or 0

		byte v_byte = m_byte; // 0
		char v_char = m_char; // u000 or 0

		String v_str = m_str;

	}

	// unInitialised values
	public void unInitialised() {

		boolean v_bool; // false

		short v_short; // 0
		int v_int; // 0
		long v_long; // 0L or 0

		float v_float; // 0.0f or 0
		double v_double; // 0.0d or 0

		byte v_byte; // 0
		char v_char; // u000 or 0

		String v_str;

	}

	// initialise primitives
	public void initPrimitive() {
		boolean v_boolT = true;
		boolean v_boolF = false;

		byte v_byte = 0;
		byte v_byteMin = -128;
		byte v_byteMax = 127;

		short v_short = 0;
		short v_shortMin = -32768;
		short v_shortMax = -32767;

		int v_int = 0;
		int v_intMin = -2147483648;
		int v_intMax = 2147483647;

		long v_long = 0L;
		long v_long1 = 0l; //lower case "l"
		
		long v_longMin = -9223372036854775808L;
		long v_longMax = 9223372036854775807L;

		float v_float0 = 0.0f;
		float v_float = 0.0F;
				
		
		float v_float1 = 123.4f;
		float v_floatMin = -2147483648f;
		float v_floatMax = 1.40239846e-45f;

		// not explored NaN, +ve -ve infinity and +ve -ve 0
		double v_double0 = 0.0D;
		double v_double = 0.0d;
		
		
		double v_double1 = 123.4;
		// use of scientific notation
		double v_double2 = 1.234e2;

		double v_doubleMin = 4.94065645841246544e-324;
		double v_doubleMax = 1.79769313486231570e+308;

		char v_char = 'A';
		char v_charA = 65;
		char v_charA1 = '\u0041';
		char v_charQuotes = '"';

		char v_charMin = '\u0000';
		char v_charMin1 = 0;
		char v_charMax = '\uffff';
		char v_charMax1 = 65535;

	}

	// prefix 0 - octal, prefix 0x - hexadecimal
	public void useNumberSystems() {
		int decVal = 26; // The number 26, in decimal
		int octVal = 032; // The number 26, in octal
		int hexVal = 0x1a; // The number 26, in hexadecimal
	}

	// use of escape sequence
	public void escapeSequences() {

		char v_backspace = '\b';
		char v_tab = '\t';
		char v_newLine = '\n';
		char v_formFeed = '\f';
		char v_carriageRet = '\r';
		char v_doubleQuote = '\"';
		char v_singleQuote = '\'';
		char v_backslash = '\\';

	}

	// using multiplle intialisation
	public void multipleInit() {
		int j = 0, k = 1, m = 2;
		double d1 = 0.0, d2 = 3.1415926, e = 2.71828;
		char c1 = 1, c2 = '\t', c3 = '\u0061';

	}

	// wide conversion
	public void wideConversion() {

		byte v_byte = 125;
		short v_short = v_byte; // 125
		int v_int = v_byte; // 125
		long v_long = v_byte; // 125
		float v_float = v_byte; // 125.0
		double v_double = v_byte; // 125.0
		char v_char = (char) v_byte; // {

		char v_char1 = 65500;
		int v_int1 = (int) v_char1; // 65500
		long v_long1 = (long) v_char1; // 65500
		float v_float1 = (float) v_char1; // 65500.0
		double v_double1 = (double) v_char1; // 65500.0

		short v_short2 = 456;
		int v_int2 = v_short2; // 456

		int v_int3 = 23456;
		long v_long3 = v_int3; // 23456
		float v_float3 = v_int3; // 23456.0
		double v_double3 = v_int3; // 23456.0

	}

	// narrow conversion
	public void narrowConversion() {

		char v_char = 32800;
		short v_short = (short) v_char; // short has -ve //-32736
		byte b = (byte) v_char; // 32

		int v_int0 = 123;
		byte v_byte0 = (byte) v_int0; // 123

		int v_int = 12345;
		byte v_byte = (byte) v_int; // 57

		int v_int1 = 65;
		char v_char1 = (char) v_int1;// A

		int v_int2 = -2;
		char v_char2 = (char) v_int1;// ?

		float v_float3 = 32147483648.1f;
		int v_int3 = (int) v_float3; // 2147483647 (max int)

		double v_double4 = 32147483648.998d;
		int v_int4 = (int) v_double4; // 2147483647 (max int)

		char v_char3 = 'Z';
		byte b1 = (byte) v_char3;

	}

}
