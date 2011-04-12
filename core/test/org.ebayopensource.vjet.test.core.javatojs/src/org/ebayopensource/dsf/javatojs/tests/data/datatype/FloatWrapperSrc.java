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
 * Class uses Float - wrapper for float
 * Wrapper test Areas - Float Class fields,methods,constructor 
 */
public class FloatWrapperSrc {

	public void floatConstructors() {

		float v_float = 0.0f;
		double v_double = 0.0d;

		// -- Unitialised
		Float v_floatObj;
		// --NULL
		Float v_floatObj0 = null;
		// --public java.lang.Float(float)
		Float v_floatObj1 = new java.lang.Float(v_float);
		// --public java.lang.Float(double)
		Float v_floatObj2 = new java.lang.Float(v_double);
		// --public java.lang.Float(java.lang.String) throws
		// java.lang.NumberFormatException
		Float v_floatObj3 = new java.lang.Float("300.1f");// throwsjava.lang.NumberFormatException;
	}

	public void floatWrapperFields() {
		// --POSITIVE_INFINITY
		float v_float0 = Float.POSITIVE_INFINITY;
		// --NEGATIVE_INFINITY
		float v_float1 = Float.NEGATIVE_INFINITY;
		// --NaN
		float v_float2 = Float.NaN;
		// --MAX_VALUE
		float v_float3 = Float.MAX_VALUE;
		// --MIN_VALUE
		float v_float4 = Float.MIN_VALUE;
		// --SIZE
		int v_float5 = Float.SIZE;
		// --TYPE
		Class<Float> v_float6 = Float.TYPE;
	}

	public void floatWrapperMthd() throws Exception {
		int v_int = 900;
		long v_long = 900;
		String v_str = "766";
		float v_float = 900, v_float1 = 200;
		Float v_FloatObj1 = new Float(v_float);
		Float v_FloatObj2 = new Float(v_float1);
		// --equals
		boolean v_equals = v_FloatObj1.equals(v_FloatObj2);
		// --hashCode
		int v_hashCode = v_FloatObj1.hashCode();
		// --toString
		String v_toString = v_FloatObj1.toString();
		// --intValue
		int v_intValue = v_FloatObj1.intValue();
		// --longValue
		long v_longValue = v_FloatObj1.longValue();
		// --floatValue
		float v_floatValue = v_FloatObj1.floatValue();
		// --doubleValue
		double v_doubleValue = v_FloatObj1.doubleValue();
		// --byteValue
		byte v_byteValue = v_FloatObj1.byteValue();
		// --shortValue
		short v_shortValue = v_FloatObj1.shortValue();
		// --isNaN
		boolean v_isNaN = v_FloatObj1.isNaN();
		// --isInfinite
		boolean v_isInfinite = v_FloatObj1.isInfinite();
		// --compareTo
		int v_compareTo = v_FloatObj1.compareTo(v_FloatObj1);
		//		
		// --getClass
		Class v_getClass = v_FloatObj1.getClass();
		// --notify
		v_FloatObj1.notify();
		// --notifyAll
		v_FloatObj1.notifyAll();
		// --wait
		v_FloatObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_FloatObj1.wait(v_long);// throws java.lang.InterruptedException;
		// --wait
		v_FloatObj1.wait(v_long, v_int);// throws
		// java.lang.InterruptedException;
		
		// --toString (static)
		String v_toString1 = Float.toString(v_float);
		// --toHexString (static)
		String v_toHexString = Float.toHexString(v_float);
		// --valueOf (static)
		Float v_valueOf = Float.valueOf(v_str);// throws
		// java.lang.NumberFormatException;
		// --valueOf (static)
		Float v_valueOf1 = Float.valueOf(v_float);
		// --parseFloat (static)
		float v_parseFloat = Float.parseFloat(v_str);// throws
		// java.lang.NumberFormatException;
		// --isNaN (static)
		boolean v_isNaN1 = Float.isNaN(v_float);
		// --isInfinite (static)
		boolean v_isInfinite1 = Float.isInfinite(v_float);
		// --floatToIntBits (static)
		int v_floatToIntBits = Float.floatToIntBits(v_float);
		// --floatToRawIntBits (static)
		int v_floatToRawIntBits = Float.floatToRawIntBits(v_float);
		// --intBitsToFloat (static)
		float v_intBitsToFloat = Float.intBitsToFloat(v_int);
		// --compare (static)
		int v_compare = Float.compare(v_float, v_float1);
	}

}
