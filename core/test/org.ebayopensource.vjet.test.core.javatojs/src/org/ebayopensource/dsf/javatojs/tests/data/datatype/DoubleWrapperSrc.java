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
 * Class uses Double - wrapper for double
 * Wrapper test Areas - Double Class fields,methods,constructor 
 */
public class DoubleWrapperSrc {

	public void doubleConstructors() {

		double v_double = 120.0d;
		// -- Unitialised
		Double v_doubleObj;
		// --NULL
		Double v_doubleObj0 = null;
		// --public java.lang.Double(double)
		Double v_doubleObj1 = new java.lang.Double(v_double);
		// --public java.lang.Double(java.lang.String) throws
		// java.lang.NumberFormatException
		Double v_doubleObj2 = new java.lang.Double("100.05d");// throws
																// java.lang.NumberFormatException;
		Double v_doubleObj3 = new java.lang.Double("2000.00");// throws
																// java.lang.NumberFormatException;
	}

	public void doubleWrapperFields() {
		// --POSITIVE_INFINITY
		double v_double0 = Double.POSITIVE_INFINITY;
		// --NEGATIVE_INFINITY
		double v_double1 = Double.NEGATIVE_INFINITY;
		// --NaN
		double v_double2 = Double.NaN;
		// --MAX_VALUE
		double v_double3 = Double.MAX_VALUE;
		// --MIN_VALUE
		double v_double4 = Double.MIN_VALUE;
		// --SIZE
		int v_double5 = Double.SIZE;
		// --TYPE
		java.lang.Class<java.lang.Double> v_double6 = java.lang.Double.TYPE;
	}

	public void doubleWrapperMthd() throws Exception {

		int v_int = 900;
		long v_long = 900;
		String v_str = "766";
		double v_double = 900, v_double1 = 200;
		java.lang.Double v_DoubleObj1 = new Double(v_double);
		Double v_DoubleObj2 = new Double(v_double1);
		Object o = new Object();

		// --equals
		boolean v_equals = v_DoubleObj1.equals(o);
		// --hashCode
		int v_hashCode = v_DoubleObj1.hashCode();
		// --toString
		String v_toString = v_DoubleObj1.toString();
		// --intValue
		int v_intValue = v_DoubleObj1.intValue();
		// --longValue
		long v_longValue = v_DoubleObj1.longValue();
		// --floatValue
		float v_floatValue = v_DoubleObj1.floatValue();
		// --doubleValue
		double v_doubleValue = v_DoubleObj1.doubleValue();
		// --byteValue
		byte v_byteValue = v_DoubleObj1.byteValue();
		// --shortValue
		short v_shortValue = v_DoubleObj1.shortValue();
		// --isNaN
		boolean v_isNaN = v_DoubleObj1.isNaN();
		// --isInfinite
		boolean v_isInfinite = v_DoubleObj1.isInfinite();
		// --compareTo
		int v_compareTo = v_DoubleObj1.compareTo(v_DoubleObj2);
		//		
		// --getClass
		java.lang.Class v_getClass = v_DoubleObj1.getClass();
		// --notify
		v_DoubleObj1.notify();
		// --notifyAll
		v_DoubleObj1.notifyAll();
		// --wait
		v_DoubleObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_DoubleObj1.wait(v_long);// throws java.lang.InterruptedException;
		// --wait
		v_DoubleObj1.wait(v_long, v_int);// throws
		// java.lang.InterruptedException;
		// --toString (static)
		String v_toString1 = Double.toString(v_double);
		// --toHexString (static)
		String v_toHexString = Double.toHexString(v_double);
		// --valueOf (static)
		Double v_valueOf = Double.valueOf(v_str);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Double v_valueOf1 = Double.valueOf(v_double);
		// --parseDouble (static)
		double v_parseDouble = Double.parseDouble(v_str);// throws
		// java.lang.NumberFormatException;
		// --isNaN (static)
		boolean v_isNaN1 = Double.isNaN(v_double);
		// --isInfinite (static)
		boolean v_isInfinite1 = Double.isInfinite(v_double);
		// --doubleToLongBits (static)
		long v_doubleToLongBits = Double.doubleToLongBits(v_double);
		// --doubleToRawLongBits (static)
		long v_doubleToRawLongBits = Double.doubleToRawLongBits(v_double);
		// --longBitsToDouble (static)
		double v_longBitsToDouble = Double.longBitsToDouble(v_long);
		// --compare (static)
		int v_compare = Double.compare(v_double, v_double1);
	}

}
