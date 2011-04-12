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
 * Class uses Integer - wrapper for int
 * Wrapper test Areas - Integer Class fields,methods,constructor 
 */
public class IntWrapperSrc {

	public void integerConstructors() {
		int v_int = 100;
		String v_str = "123";

		// -- Unitialised
		Integer v_integerObj;
		// --NULL
		Integer v_integerObj0 = null;
		// --public java.lang.Integer(int)
		Integer v_integerObj1 = new java.lang.Integer(v_int);
		// --public java.lang.Integer(java.lang.String) throws
		// java.lang.NumberFormatException
		Integer v_integerObj2 = new java.lang.Integer("234");// throws
		// java.lang.NumberFormatException;
		Integer v_integerObj3 = new java.lang.Integer(new String("234"));// throws
		// java.lang.NumberFormatException;
		Integer v_integerObj4 = new java.lang.Integer(v_str);
	}

	public void intWrapperFields() {
		// --MIN_VALUE
		int v_int0 = Integer.MIN_VALUE;
		// --MAX_VALUE
		int v_int1 = Integer.MAX_VALUE;
		// --TYPE
		Class<Integer> v_clzz = Integer.TYPE;
		// --SIZE
		int v_int3 = Integer.SIZE;
	}

	public void intWrapperMthd() throws Exception {
		Integer v_IntObj1 = new Integer(600);
		Integer v_IntObj2 = new Integer(900);
		int v_int = 900;
		long v_long = 900;
		String v_str = "766";

		// --equals
		boolean v_equals = v_IntObj1.equals(v_IntObj2);
		// --hashCode
		int v_hashCode = v_IntObj1.hashCode();
		// --toString
		String v_toString0 = v_IntObj1.toString();

		// --intValue
		int v_intValue = v_IntObj1.intValue();
		// --longValue
		long v_longValue = v_IntObj1.longValue();

		// --floatValue
		float v_floatValue = v_IntObj1.floatValue();

		// --doubleValue
		double v_doubleValue = v_IntObj1.doubleValue();
		// --byteValue
		byte v_byteValue = v_IntObj1.byteValue();
		// --shortValue
		short v_shortValue = v_IntObj1.shortValue();
		// --compareTo
		int v_compareTo = v_IntObj1.compareTo(v_IntObj2);

		// Object methods
		// --getClass
		Class v_getClass = v_IntObj1.getClass();
		// --notify
		v_IntObj1.notify();
		// --notifyAll
		v_IntObj1.notifyAll();
		// --wait
		v_IntObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_IntObj1.wait(v_long);// throws java.lang.InterruptedException;
		// --wait
		v_IntObj1.wait(v_long, v_int); // throws
		// java.lang.InterruptedException;
		// --toString (static)
		String v_toString1 = Integer.toString(v_int, v_int);
		// --toHexString (static)
		String v_toHexString = Integer.toHexString(v_int);
		// --toOctalString (static)
		String v_toOctalString = Integer.toOctalString(v_int);
		// --toBinaryString (static)
		String v_toBinaryString = Integer.toBinaryString(v_int);
		// --toString (static)
		String v_toString2 = Integer.toString(v_int);
		// --parseInt (static)
		int v_parseInt = Integer.parseInt(v_str, v_int); // throws
		// java.lang.NumberFormatException;
		// --parseInt (static)
		int v_parseInt1 = Integer.parseInt(v_str);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Integer v_valueOf = Integer.valueOf(v_str, v_int);// throws
		// java.lang.NumberFormatException;

		// --valueOf (static)
		Integer v_valueOf1 = Integer.valueOf(v_str);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Integer v_valueOf2 = Integer.valueOf(v_int);
		// --getInteger (static)
		Integer v_getInteger = Integer.getInteger(v_str);
		// --getInteger (static)
		Integer v_getInteger1 = Integer.getInteger(v_str, v_int);
		// --getInteger (static)
		Integer v_getInteger2 = Integer.getInteger(v_str, v_IntObj1);
		// --getInteger (static)
		Integer v_decode = Integer.decode(v_str);// throws
		// java.lang.NumberFormatException;
		// --highestOneBit (static)
		int v_highestOneBit = Integer.highestOneBit(v_int);
		// --lowestOneBit (static)
		int v_lowestOneBit = Integer.lowestOneBit(v_int);
		// --numberOfLeadingZeros (static)
		int v_numberOfLeadingZeros = Integer.numberOfLeadingZeros(v_int);
		// --numberOfTrailingZeros (static)
		int v_numberOfTrailingZeros = Integer.numberOfTrailingZeros(v_int);
		// --bitCount (static)
		int v_bitCount = Integer.bitCount(v_int);
		// --rotateLeft (static)
		int v_rotateLeft = Integer.rotateLeft(v_int, v_int);
		// --rotateRight (static)
		int v_rotateRight = Integer.rotateRight(v_int, v_int);
		// --reverse (static)
		int v_reverse = Integer.reverse(v_int);
		// --signum (static)
		int v_signum = Integer.signum(v_int);
		// --reverseBytes (static)
		int v_reverseBytes = Integer.reverseBytes(v_int);
	}

}
