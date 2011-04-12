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
 * Class uses Byte - wrapper for byte
 * Wrapper test Areas - Byte Class fields,methods,constructor 
 */
public class ByteWrapperSrc {

	public void byteConstructors() {
		byte v_int = 120;
		// -- Unitialised
		Byte v_byteObj;
		// --NULL
		Byte v_byteObj0 = null;
		// --public java.lang.Byte(byte)
		Byte v_byteObj1 = new java.lang.Byte(v_int);
		// --public java.lang.Byte(java.lang.String) throws
		// java.lang.NumberFormatException
		Byte v_byteObj2 = new java.lang.Byte("120");// throwsjava.lang.NumberFormatException;
		Byte v_byteObj3 = new java.lang.Byte(new java.lang.String("100"));// throws
		// java.lang.NumberFormatException;
	}

	public void byteWrapperFields() {
		// -- MIN_VALUE
		byte v_byte0 = Byte.MIN_VALUE;
		// -- MAX_VALUE
		byte v_byte1 = Byte.MAX_VALUE;
		// -- TYPE
		Class<Byte> v_byte2 = Byte.TYPE;
		// -- SIZE
		int v_byte3 = Byte.SIZE;
	}

	public void byteWrapperMthd() throws Exception {

		Byte v_byteObj1 = new Byte("120");
		byte b = 127;
		Byte v_byteObj2 = new Byte(b);
		byte v_byte3 = 34;
		String v_str = "HelloWorld";
		int v_int = 7;
		long v_long = 500;

		// --equals
		boolean v_equals = v_byteObj1.equals(v_byteObj2);
		// --hashCode
		int v_hashCode = v_byteObj1.hashCode();
		// --toString
		String v_toString = v_byteObj1.toString();
		// --intValue
		int v_intValue = v_byteObj1.intValue();
		// --longValue
		long v_longValue = v_byteObj1.longValue();
		// --floatValue
		float v_floatValue = v_byteObj1.floatValue();
		// --doubleValue
		double v_doubleValue = v_byteObj1.doubleValue();
		// --byteValue
		byte v_byteValue = v_byteObj1.byteValue();
		// --shortValue
		short v_shortValue = v_byteObj1.shortValue();
		// --compareTo
		int v_compareTo = v_byteObj1.compareTo(v_byteObj2);
		// --compareTo
		int v_compareTo1 = v_byteObj1.compareTo(v_byteObj1);

		// --getClass
		Class v_getClass = v_byteObj1.getClass();
		// --notify
		v_byteObj1.notify();
		// --notifyAll
		v_byteObj1.notifyAll();
		// --wait
		v_byteObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_byteObj1.wait(v_long);// throws java.lang.InterruptedException;
		// --wait
		v_byteObj1.wait(v_long, v_int); // throws
		// java.lang.InterruptedException;

		// --toString (static)
		String v_toString1 = Byte.toString(v_byte3);
		// --valueOf (static)
		Byte v_valueOf = Byte.valueOf(v_byte3);
		// --parseByte (static)
		byte v_parseByte0 = Byte.parseByte(v_str); // throws
		// java.lang.NumberFormatException;
		// --parseByte (static)
		byte v_parseByte1 = Byte.parseByte(v_str, v_int);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Byte v_valueOf1 = Byte.valueOf(v_str, v_int);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Byte v_valueOf2 = Byte.valueOf(v_str);// throws
		// java.lang.NumberFormatException;
		// --decode (static)
		Byte v_decode = Byte.decode(v_str);// throws
		// java.lang.NumberFormatException;
	}
}
