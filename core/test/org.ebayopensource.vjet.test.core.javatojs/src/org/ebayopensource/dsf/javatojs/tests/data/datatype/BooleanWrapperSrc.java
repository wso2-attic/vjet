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
 * Class uses Boolean - wrapper for boolean
 * Wrapper test Areas - Boolean Class fields,methods,constructor 
 */
public class BooleanWrapperSrc {

	public void booleanConstructors() {

		boolean v_boolean = true;
		// -- Unitialised
		Boolean v_booleanObj;
		// --NULL
		Boolean v_booleanObj0 = null;
		// --public java.lang.Boolean(boolean)
		Boolean v_booleanObj1 = new java.lang.Boolean(v_boolean);
		// --public java.lang.Boolean(java.lang.String)
		Boolean v_booleanObj2 = new java.lang.Boolean("true");
		Boolean v_booleanObj3 = new java.lang.Boolean(new java.lang.String("yes")); // false
	}

	public void booleanWrapperFields() {
		// --TRUE
		Boolean v_boolean0 = Boolean.TRUE;
		// --FALSE
		java.lang.Boolean v_boolean1 = Boolean.FALSE;
		// --TYPE
		Class<java.lang.Boolean> v_boolean2 = java.lang.Boolean.TYPE;
	}

	public void booleanWrapperMthd() throws Exception {

		Object o = new Integer("45");
		String v_str = "HelloWorld";
		int v_int = 7;
		long v_long = 500;
		boolean v_bool = true;

		Boolean v_boolObj1 = new Boolean("true");
		Boolean v_boolObj2 = new Boolean("false");

		// --equals
		boolean v_equals = v_boolObj1.equals(o);
		// --hashCode
		int v_hashCode = v_boolObj1.hashCode();
		// --toString
		java.lang.String v_toString = v_boolObj1.toString();
		// --booleanValue
		boolean v_booleanValue = v_boolObj1.booleanValue();
		// --compareTo
		int v_compareTo = v_boolObj1.compareTo(v_boolObj2);
		//				
		// --getClass
		Class v_getClass = v_boolObj1.getClass();
		// --notify
		v_boolObj1.notify();
		// --notifyAll
		v_boolObj1.notifyAll();
		// --wait
		v_boolObj1.wait();// throws java.lang.InterruptedException;
		// --wait
		v_boolObj1.wait(v_long);// throws java.lang.InterruptedException;
		// --wait
		v_boolObj1.wait(v_long, v_int);// throws
		// java.lang.InterruptedException;
		// --parseBoolean (static)
		boolean v_parseBoolean = Boolean.parseBoolean(v_str);
		// --valueOf (static)
		Boolean v_valueOf = Boolean.valueOf(v_bool);
		// --valueOf (static)
		Boolean v_valueOf1 = Boolean.valueOf(v_str);
		// --toString (static)
		String v_toString1 = Boolean.toString(v_bool);
		// --getBoolean (static)
		boolean v_getBoolean = Boolean.getBoolean(v_str);
	}

}
