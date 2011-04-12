/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.global;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.Static;

/**
 * 
 * Represents JavaScript native Math object
 *
 */
@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ZERO, JsVersion.JSCRIPT_ONE_DOT_ZERO})
public interface Math extends Object {
	
	/**
	 * Euler's constant and the base of natural logarithms, approximately 2.718. 
	 */
	@Property @Static Number getE();
	
	/**
	 * The natural logarithm of 10, approximately 2.302.
	 */
	@Property @Static Number getLN10();
	
	/**
	 * The natural logarithm of 2, approximately 0.693.
	 */
	@Property @Static Number getLN2();
	
	/**
	 * The base 10 logarithm of E (approximately 0.434).
	 */
	@Property @Static Number getLOG10E();
	
	/**
	 * The base 2 logarithm of E (approximately 1.442).
	 */
	@Property @Static Number getLOG2E();
	
	/**
	 * The ratio of the circumference of a circle to its diameter, approximately 3.14159. 
	 */
	@Property @Static Number getPI();
	
	/**
	 * The square root of 1/2; equivalently, 1 over the square root of 2, approximately 0.707.  
	 */
	@Property @Static Number getSQRT1_2();
	
	/**
	 * The square root of 2, approximately 1.414. 
	 */
	@Property @Static Number getSQRT2();
	
	/**
	 * Returns the absolute value of a number.
	 */
	@OverLoadFunc @Static Number abs();
	
	/**
	 * Returns the absolute value of a number.
	 */
	@OverLoadFunc @Static Number abs(Number x);
	
	/**
	 * Returns the absolute value of a number.
	 */
	@OverLoadFunc @Static Number abs(String x);
	
	/**
	 * Returns the arccosine (in radians) of a number.
	 */
	@OverLoadFunc @Static Number acos();
	
	/**
	 * Returns the arccosine (in radians) of a number.
	 */
	@OverLoadFunc @Static Number acos(Number x);
	
	/**
	 * Returns the arccosine (in radians) of a number.
	 */
	@OverLoadFunc @Static Number acos(String x);
	
	/**
	 * Returns the arcsine (in radians) of a number.
	 */
	@OverLoadFunc @Static Number asin();
	
	/**
	 * Returns the arcsine (in radians) of a number.
	 */
	@OverLoadFunc @Static Number asin(Number x);
	
	/**
	 * Returns the arcsine (in radians) of a number.
	 */
	@OverLoadFunc @Static Number asin(String x);
	
	/**
	 * Returns the arctangent (in radians) of a number.
	 */
	@OverLoadFunc @Static Number atan();
	
	/**
	 * Returns the arctangent (in radians) of a number.
	 */
	@OverLoadFunc @Static Number atan(Number x);
	
	/**
	 * Returns the arctangent (in radians) of a number.
	 */
	@OverLoadFunc @Static Number atan(String x);
	
	/**
	 * Returns the arctangent of the quotient of its arguments. 
	 */
	@Function @Static Number atan2(Object y, Object x);
	
	/**
	 * Returns the smallest integer greater than or equal to a number. 
	 */
	@OverLoadFunc @Static Number ceil();
	
	/**
	 * Returns the smallest integer greater than or equal to a number. 
	 */
	@OverLoadFunc @Static Number ceil(Number x);
	
	/**
	 * Returns the smallest integer greater than or equal to a number. 
	 */
	@OverLoadFunc @Static Number ceil(String x);
	
	/**
	 * Returns the cosine of a number.
	 */
	@OverLoadFunc @Static Number cos();
	
	/**
	 * Returns the cosine of a number.
	 */
	@OverLoadFunc @Static Number cos(Number x);
	
	/**
	 * Returns the cosine of a number.
	 */
	@OverLoadFunc @Static Number cos(String x);
	
	/**
	 * Returns Ex, where x is the argument, and E is Euler's constant, the base of the natural logarithms. 
	 */
	@OverLoadFunc @Static Number exp();
	
	/**
	 * Returns Ex, where x is the argument, and E is Euler's constant, the base of the natural logarithms. 
	 */
	@OverLoadFunc @Static Number exp(Number x);
	
	/**
	 * Returns Ex, where x is the argument, and E is Euler's constant, the base of the natural logarithms. 
	 */
	@OverLoadFunc @Static Number exp(String x);
	
	/**
	 * Returns the largest integer less than or equal to a number. 
	 */
	@OverLoadFunc @Static Number floor();
	
	/**
	 * Returns the largest integer less than or equal to a number. 
	 */
	@OverLoadFunc @Static Number floor(Number x);
	
	/**
	 * Returns the largest integer less than or equal to a number. 
	 */
	@OverLoadFunc @Static Number floor(String x);
	
	/**
	 * Returns the natural logarithm (base E) of a number. 
	 */
	@OverLoadFunc @Static Number log();
	
	/**
	 * Returns the natural logarithm (base E) of a number. 
	 */
	@OverLoadFunc @Static Number log(Number x);
	
	/**
	 * Returns the natural logarithm (base E) of a number. 
	 */
	@OverLoadFunc @Static Number log(String x);
	
	/**
	 * Returns the larger of two numbers.
	 */
	@OverLoadFunc @Static Number max();
	
	/**
	 * Returns the larger of two numbers.
	 */
	@OverLoadFunc @Static Number max(Object... values);
	
	/**
	 * Returns the larger of two numbers.
	 */
	@OverLoadFunc @Static Number min();
	
	/**
	 * Returns the larger of two numbers.
	 */
	@OverLoadFunc @Static Number min(Object... values);
	
	/**
	 * Returns base to the exponent power.
	 */
	@OverLoadFunc @Static Number pow();
	
	/**
	 * Returns base to the exponent power.
	 */
	@OverLoadFunc @Static Number pow(Number base, Number exponent);
	
	/**
	 * Returns base to the exponent power.
	 */
	@OverLoadFunc @Static Number pow(Number base, String exponent);
	
	/**
	 * Returns base to the exponent power.
	 */
	@OverLoadFunc @Static Number pow(String base, Number exponent);
	
	/**
	 * Returns base to the exponent power.
	 */
	@OverLoadFunc @Static Number pow(String base, String exponent);
	
	/**
	 * Returns a pseudo-random number in the range [0,1) -- that is, between 0 (inclusive) and 1 (exclusive). 
	 * The random number generator is seeded from the current time, as in Java. 
	 */
	@Function @Static Number random();
	
	/**
	 * Returns the value of a number rounded to the nearest integer. 
	 */
	@OverLoadFunc @Static Number round();
	
	/**
	 * Returns the value of a number rounded to the nearest integer. 
	 */
	@OverLoadFunc @Static Number round(Number x);
	
	/**
	 * Returns the value of a number rounded to the nearest integer. 
	 */
	@OverLoadFunc @Static Number round(String x);
	
	/**
	 * Returns the sine of a number.
	 */
	@OverLoadFunc @Static Number sin();
	
	/**
	 * Returns the sine of a number.
	 */
	@OverLoadFunc @Static Number sin(Number x);
	
	/**
	 * Returns the sine of a number.
	 */
	@OverLoadFunc @Static Number sin(String x);
	
	/**
	 * Returns the square root of a number.
	 */
	@OverLoadFunc @Static Number sqrt();
	
	/**
	 * Returns the square root of a number.
	 */
	@OverLoadFunc @Static Number sqrt(Number x);
	
	/**
	 * Returns the square root of a number.
	 */
	@OverLoadFunc @Static Number sqrt(String x);
	
	/**
	 * Returns the tangent of a number.
	 */
	@OverLoadFunc @Static Number tan();
	
	/**
	 * Returns the tangent of a number.
	 */
	@OverLoadFunc @Static Number tan(Number x);
	
	/**
	 * Returns the tangent of a number.
	 */
	@OverLoadFunc @Static Number tan(String x);
}
