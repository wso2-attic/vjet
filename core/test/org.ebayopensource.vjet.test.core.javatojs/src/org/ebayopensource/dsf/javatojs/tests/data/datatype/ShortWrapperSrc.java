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
 * Class uses Short - wrapper for short
 * Wrapper test Areas - Short Class fields,methods,constructor 
 */
public class ShortWrapperSrc {

	public void shortConstructors() {

		short v_short = 123;
		// --Unitialised
		Short v_shortObj;
		// --NULL
		Short v_shortObj0 = new java.lang.Short(v_short);

		// --public java.lang.Short(short)
		Short v_shortObj1 = new java.lang.Short(v_short);
		// --public java.lang.Short(java.lang.String) throws
		// java.lang.NumberFormatException
		Short v_shortObj2 = new java.lang.Short("677");// throws
		// java.lang.NumberFormatException;
	}

	public void shortWrapperFields() {
		// --MIN_VALUE
		short v_short0 = Short.MIN_VALUE;
		// --MAX_VALUE
		short v_short1 = Short.MAX_VALUE;
		// --TYPE
		Class<java.lang.Short> v_short2 = Short.TYPE;
		// --SIZE
		int v_short3 = Short.SIZE;
	}

	public void shortWrapperMthd() throws Exception

	{

		Short v_shortObj1 = new Short("120");
		Short v_shortObj2 = new Short("125");
		Object o = new Integer("89");
		long v_long = 900;
		short v_short = 25;
		String v_str = "766";
		int v_int = 900;

		// --equals
		boolean v_equals = v_shortObj1.equals(o);
		// --hashCode
		int v_hashCode = v_shortObj1.hashCode();
		// --toString
		String v_toString = v_shortObj1.toString();
		// --intValue
		int v_intValue = v_shortObj1.intValue();
		// --longValue
		long v_longValue = v_shortObj1.longValue();
		// --floatValue
		float v_floatValue = v_shortObj1.floatValue();
		// --doubleValue
		double v_doubleValue = v_shortObj1.doubleValue();
		// --byteValue
		byte v_byteValue = v_shortObj1.byteValue();
		// --shortValue
		short v_shortValue = v_shortObj1.shortValue();
		// --compareTo
		int v_compareTo = v_shortObj1.compareTo(v_shortObj2);
		// --getClass
		Class v_getClass = v_shortObj1.getClass();
		// --notify
		v_shortObj1.notify();
		// --notifyAll
		v_shortObj1.notifyAll();
		// --wait
		v_shortObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_shortObj1.wait(v_long);// throws java.lang.InterruptedException;
		// --wait
		v_shortObj1.wait(v_long, v_int);// throws
		// java.lang.InterruptedException;
		// --toString (static)
		String v_toString1 = Short.toString(v_short);
		// --parseShort (static)
		short v_parseShort = Short.parseShort(v_str);// throws
		// java.lang.NumberFormatException;
		// --parseShort (static)
		short v_parseShort1 = Short.parseShort(v_str, v_int);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Short v_valueOf = Short.valueOf(v_str, v_int);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Short v_valueOf1 = Short.valueOf(v_str);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Short v_valueOf2 = Short.valueOf(v_short);
		// --decode (static)
		Short v_decode = Short.decode(v_str);// throws
		// java.lang.NumberFormatException;
		// --reverseBytes (static)
		short v_reverseBytes = Short.reverseBytes(v_short);
	}

}
