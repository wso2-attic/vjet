/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

public class NaN {
		
	public static Integer intValue(){
		//return the smallest java integer (-2e31) as js NaN
		//Integers in JS are considered reliable (numbers without a period or exponent notation) to 15 digits (9e15)
		return Integer.MIN_VALUE;
	}
	
	public static Float floatValue(){
		return Float.NaN;
	}
}
