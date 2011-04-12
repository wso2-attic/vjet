/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.datatype;

import java.util.Locale;

/*
 * Class uses String 
 * Test Areas - String Class fields,methods,constructor 
 */
public class StringSrc {

	public void stringConstructors() throws Exception {

		byte[] v_byteArr = { 1, 2, 3 };
		char[] v_charArr = { 'A', 'B', 'C' };
		int v_int = 1;
		int[] v_intArr = { 12, 14, 16 };
		String v_str = "Hello World!";
		StringBuffer v_strBuffObj = new StringBuffer(new String("Hello World!"));
		StringBuilder v_strBuilderObj = new StringBuilder("Hello World!");

		// --Unitialised
		String v_stringObj;
		// --NULL
		String v_stringObj0 = null;
		// --public java.lang.String()
		String v_stringObj1 = new java.lang.String();
		// --public java.lang.String(byte[])
		String v_stringObj2 = new java.lang.String(v_byteArr);
		// --public java.lang.String(byte[],int)
		String v_stringObj3 = new java.lang.String(v_byteArr, v_int);
		// --public java.lang.String(byte[],int,int)
		String v_stringObj4 = new java.lang.String(v_byteArr, v_int, v_int);
		// --public java.lang.String(byte[],int,int,int)
		String v_stringObj5 = new java.lang.String(v_byteArr, v_int, v_int, v_int);
		// --public java.lang.String(byte[],int,int,java.lang.String) throws
		// java.io.UnsupportedEncodingException
		String v_stringObj6 = new java.lang.String(v_byteArr, v_int, v_int, v_str);// throws
		// java.io.UnsupportedEncodingException;
		// --public java.lang.String(byte[],java.lang.String) throws
		// java.io.UnsupportedEncodingException
		String v_stringObj7 = new java.lang.String(v_byteArr, v_str);// throwsjava.io.UnsupportedEncodingException;
		// --public java.lang.String(char[])
		String v_stringObj8 = new java.lang.String(v_charArr);
		// --public java.lang.String(char[],int,int)
		String v_stringObj9 = new java.lang.String(v_charArr, v_int, v_int);
		// --public java.lang.String(java.lang.String)
		String v_stringObj10 = new java.lang.String(v_str);
		// --public java.lang.String(java.lang.StringBuffer)
		String v_stringObj11 = new java.lang.String(v_strBuffObj);
		// --public java.lang.String(int[],int,int)
		String v_stringObj12 = new java.lang.String(v_intArr, v_int, v_int);
		// --public java.lang.String(java.lang.StringBuilder)
		String v_stringObj13 = new java.lang.String(v_strBuilderObj);
		// concat
		String v_stringObj14 = "Hello" + "  World";
		//escape characters in String
		String v_stringObj15 = "\t" +" Hello World \n";
		// escape chars
		String v_stringObj16 = "\\ ' \" \\ ";
//		 key	
		String v_stringObj17 = "~ ! ` # $ % ^ & * ( ) _ - + = { } [ ] | \\ @ % ";
	}

	public void stringFields() {
		// --CASE_INSENSITIVE_ORDER
		// java.util.Comparator<java.lang.String> v_string0 =
		// java.util.Comparator<java.lang.String>
		// java.lang.String.CASE_INSENSITIVE_ORDER;
	}

	public void stringMthd() throws Exception {

		String v_strObj1 = new String("Hello World");
		String v_strObj2 = new String("Hello World1");
		String v_str = "World";
		int v_int = 12, v_int1 = 14, v_int2 = 16;
		char[] v_charArr = { 'A', 'B' };
		byte[] v_byteArr = { 112, 101 };
		boolean v_bool = false;
		char v_char = 'A', v_char1 = 'B';
		StringBuffer v_strBufferObj = new StringBuffer();
		Locale v_localeObj = Locale.US;
		long v_long = 123;
		double v_double = 234;
		float v_float = 123;
		Object[] oArr = new Object[2];
		Object o = new Object();
		CharSequence v_charseq = new String("ABC");

		// --equals
		boolean v_equals = v_strObj1.equals(v_strObj2);
		// --hashCode
		int v_hashCode = v_strObj1.hashCode();
		// --toString
		String v_toString = v_strObj1.toString();
		// --charAt
		char v_charAt = v_strObj1.charAt(v_int);
		// --compareTo
		int v_compareTo = v_strObj1.compareTo(v_strObj1);
		// --compareToIgnoreCase
		int v_compareToIgnoreCase = v_strObj1.compareToIgnoreCase(v_strObj1);
		// --concat
		String v_concat = v_strObj1.concat(v_strObj1);
		// --endsWith
		boolean v_endsWith = v_strObj1.endsWith(v_strObj1);
		// --equalsIgnoreCase
		boolean v_equalsIgnoreCase = v_strObj1.equalsIgnoreCase(v_strObj1);
		// --getBytes
		byte[] v_getBytes = v_strObj1.getBytes();
		// --getBytes (deprecated)
		v_strObj1.getBytes(v_int, v_int1, v_byteArr, v_int2);
		// --getBytes
		byte[] v_getBytes1 = v_strObj1.getBytes(v_strObj1);// throws
		// java.io.UnsupportedEncodingException;
		// --getChars (test)
		v_strObj1.getChars(v_int, v_int1, v_charArr, v_int2);
		// --indexOf
		int v_indexOf = v_strObj1.indexOf(v_int);
		// --indexOf
		int v_indexOf1 = v_strObj1.indexOf(v_int, v_int1);
		// --indexOf
		int v_indexOf2 = v_strObj1.indexOf(v_strObj1);
		// --indexOf
		int v_indexOf3 = v_strObj1.indexOf(v_str, v_int);
		// --intern
		String v_intern = v_strObj1.intern();
		// --lastIndexOf
		int v_lastIndexOf = v_strObj1.lastIndexOf(v_int);
		// --lastIndexOf
		int v_lastIndexOf1 = v_strObj1.lastIndexOf(v_int, v_int1);
		// --lastIndexOf
		int v_lastIndexOf2 = v_strObj1.lastIndexOf(v_strObj1);
		// --lastIndexOf
		int v_lastIndexOf3 = v_strObj1.lastIndexOf(v_str, v_int);
		// --length
		int v_length = v_strObj1.length();
		// --regionMatches
		boolean v_regionMatches = v_strObj1.regionMatches(v_int, v_strObj1, v_int1, v_int2);
		// --regionMatches
		boolean v_regionMatches1 = v_strObj1.regionMatches(v_bool, v_int, v_str, v_int1, v_int2);
		// --replace
		String v_replace = v_strObj1.replace(v_char, v_char1);
		// --startsWith
		boolean v_startsWith = v_strObj1.startsWith(v_strObj1);
		// --startsWith
		boolean v_startsWith1 = v_strObj1.startsWith(v_str, v_int);
		// --substring
		String v_substring = v_strObj1.substring(v_int);
		// --substring
		String v_substring1 = v_strObj1.substring(v_int, v_int1);
		// --toCharArray
		char[] v_toCharArray = v_strObj1.toCharArray();
		// --toLowerCase
		String v_toLowerCase = v_strObj1.toLowerCase();
		// --toLowerCase
		String v_toLowerCase1 = v_strObj1.toLowerCase(v_localeObj);
		// --toUpperCase
		String v_toUpperCase = v_strObj1.toUpperCase();
		// --toUpperCase
		String v_toUpperCase1 = v_strObj1.toUpperCase(v_localeObj);
		// --trim
		String v_trim = v_strObj1.trim();
		// --contentEquals
		boolean v_contentEquals = v_strObj1.contentEquals(v_strBufferObj);
		// --matches
		boolean v_matches = v_strObj1.matches(v_strObj1);
		// --replaceAll
		String v_replaceAll = v_strObj1.replaceAll(v_strObj2, v_str);
		// --replaceFirst
		String v_replaceFirst = v_strObj1.replaceFirst(v_strObj2, v_str);
		// --split
		String[] v_split = v_strObj1.split("");
		// --split
		String[] v_split1 = v_strObj1.split("", v_int);
		// --subSequence
		CharSequence v_subSequence = v_strObj1.subSequence(v_int, v_int1);
		// --codePointAt
		int v_codePointAt = v_strObj1.codePointAt(v_int);
		// --codePointBefore
		int v_codePointBefore = v_strObj1.codePointBefore(v_int);
		// --codePointCount
		int v_codePointCount = v_strObj1.codePointCount(v_int, v_int1);
		// --offsetByCodePoints
		int v_offsetByCodePoints = v_strObj1.offsetByCodePoints(v_int, v_int1);
		// --contentEquals
		boolean v_contentEquals1 = v_strObj1.contentEquals(v_charseq);
		// --contains
		boolean v_contains = v_strObj1.contains(v_charseq);
		// --replace
		String v_replace1 = v_strObj1.replace(v_charseq, v_charseq);
		// --compareTo
		// int v_compareTo1 =v_strObj1.compareTo(o);
		// --getClass
		java.lang.Class v_getClass = v_strObj1.getClass();
		// --notify
		v_strObj1.notify();
		// --notifyAll
		v_strObj1.notifyAll();
		// --wait
		v_strObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_strObj1.wait(v_long);// throws java.lang.InterruptedException;
		// --wait
		v_strObj1.wait(v_long, v_int);// throws
		// java.lang.InterruptedException;
		// --copyValueOf (static)
		String v_copyValueOf = String.copyValueOf(v_charArr);
		// --copyValueOf (static)
		String v_copyValueOf1 = String.copyValueOf(v_charArr, v_int, v_int1);
		// --valueOf (static)
		String v_valueOf = String.valueOf(v_charArr);
		// --valueOf (static)
		String v_valueOf1 = String.valueOf(v_charArr, v_int, v_int1);
		// --valueOf (static)
		String v_valueOf2 = String.valueOf(v_char);
		// --valueOf (static)
		String v_valueOf3 = String.valueOf(v_double);
		// --valueOf (static)
		String v_valueOf4 = String.valueOf(v_float);
		// --valueOf (static)
		String v_valueOf5 = String.valueOf(v_int);
		// --valueOf (static)
		String v_valueOf6 = String.valueOf(v_long);
		// --valueOf (static)
		String v_valueOf7 = String.valueOf(o);
		// --valueOf (static)
		String v_valueOf8 = String.valueOf(v_bool);
		// --format (static)
		String v_format = String.format(v_str, oArr);
		// --format (static)
		String v_format1 = String.format(v_localeObj, v_str, oArr);
	}

}
