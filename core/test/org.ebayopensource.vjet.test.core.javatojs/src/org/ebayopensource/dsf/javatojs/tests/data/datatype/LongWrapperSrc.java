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
 * Class uses Long - wrapper for long
 * Wrapper test Areas - Long Class fields,methods,constructor 
 */
public class LongWrapperSrc {

	public void longConstructors() {
		long v_long = 120L;
		// -- Unitialised
		Long v_longObj;
		// --NULL
		Long v_longObj0 = null;
		// --public java.lang.Long(long)
		Long v_longObj1 = new java.lang.Long(v_long);
		// --public java.lang.Long(java.lang.String) throws
		// java.lang.NumberFormatException
		Long v_longObj2 = new java.lang.Long("120000L");// throws
														// java.lang.NumberFormatException;
	}

	public void longWrapperFields() {
		// --MIN_VALUE
		long v_long0 = Long.MIN_VALUE;
		// --MAX_VALUE
		long v_long1 = Long.MAX_VALUE;
		// --TYPE
		Class<Long> v_long2 = Long.TYPE;
		// --SIZE
		int v_long3 = Long.SIZE;
	}

	public void longWrapperMthd() throws Exception

	{
		Object o = new Integer("89");
		long v_long = 900;
		String v_str = "766";
		int v_int = 900;
		Long v_longObj1 = new Long("12000L");
		Long v_longObj2 = new Long("12500");

		// --equals
		boolean v_equals = v_longObj1.equals(o);
		// --hashCode
		int v_hashCode = v_longObj1.hashCode();
		// --toString
		String v_toString = v_longObj1.toString();
		// --intValue
		int v_intValue = v_longObj1.intValue();
		// --longValue
		long v_longValue = v_longObj1.longValue();
		// --floatValue
		float v_floatValue = v_longObj1.floatValue();
		// --doubleValue
		double v_doubleValue = v_longObj1.doubleValue();
		// --byteValue
		byte v_byteValue = v_longObj1.byteValue();
		// --shortValue
		short v_shortValue = v_longObj1.shortValue();
		// --compareTo
		int v_compareTo = v_longObj1.compareTo(v_longObj2);

		// --getClass
		Class v_getClass = v_longObj1.getClass();
		// --notify
		v_longObj1.notify();
		// --notifyAll
		v_longObj1.notifyAll();
		// --wait
		v_longObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_longObj1.wait(v_long);// throws java.lang.InterruptedException;
		// --wait
		v_longObj1.wait(v_long, v_int);// throws
		// java.lang.InterruptedException;
		// --toString (static)
		String v_toString1 = Long.toString(v_long, v_int);
		// --toHexString (static)
		String v_toHexString = Long.toHexString(v_long);
		// --toOctalString (static)
		String v_toOctalString = Long.toOctalString(v_long);
		// --toBinaryString (static)
		String v_toBinaryString = Long.toBinaryString(v_long);
		// --toString (static)
		String v_toString2 = Long.toString(v_long);
		// --parseLong (static)
		long v_parseLong = Long.parseLong(v_str, v_int);// throws
		// java.lang.NumberFormatException;
		// --parseLong (static)
		long v_parseLong1 = Long.parseLong(v_str);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		java.lang.Long v_valueOf = Long.valueOf(v_str, v_int);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Long v_valueOf1 = Long.valueOf(v_str);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Long v_valueOf2 = java.lang.Long.valueOf(v_long);
		// --decode (static)
		java.lang.Long v_decode = java.lang.Long.decode(v_str);// throws
		// java.lang.NumberFormatException;
		// --getLong (static)
		Long v_getLong0 = Long.getLong(v_str);
		// --getLong (static)
		Long v_getLong1 = Long.getLong(v_str, v_long);
		// --getLong (static)
		Long v_getLong2 = Long.getLong(v_str, v_longObj2);
		// --highestOneBit (static)
		long v_highestOneBit = Long.highestOneBit(v_long);
		// --lowestOneBit (static)
		long v_lowestOneBit = Long.lowestOneBit(v_long);
		// --numberOfLeadingZeros (static)
		int v_numberOfLeadingZeros = Long.numberOfLeadingZeros(v_long);
		// --numberOfTrailingZeros (static)
		int v_numberOfTrailingZeros = Long.numberOfTrailingZeros(v_long);
		// --bitCount (static)
		int v_bitCount = Long.bitCount(v_long);
		// --rotateLeft (static)
		long v_rotateLeft = Long.rotateLeft(v_long, v_int);
		// --rotateRight (static)
		long v_rotateRight = Long.rotateRight(v_long, v_int);
		// --reverse (static)
		long v_reverse = Long.reverse(v_long);
		// --signum (static)
		int v_signum = Long.signum(v_long);
		// --reverseBytes (static)
		long v_reverseBytes = Long.reverseBytes(v_long);
	}
}
