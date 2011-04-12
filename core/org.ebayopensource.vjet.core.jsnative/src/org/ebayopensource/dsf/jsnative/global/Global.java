/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.global;

import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;


/**
 * 
 * Represents JavaScript native Global object
 * Note: Global is not a top-level object in JavaScript. 
 * This only serves as a placeholder for global properties and
 * functions. 
 *
 */
@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ZERO, JsVersion.JSCRIPT_FIVE_DOT_ZERO})
@JsMetatype
public interface Global extends Object {
	
	/**
	 * A numeric value representing infinity.
	 */
	@Property Number getInfinity();
	
	/**
	 * A value representing Not-a-Number.
	 */
	@Property Number getNaN();
	
	/**
	 * The value undefined.
	 */
	@Property Object getUndefined();
	
	/**
	 * The instanceof operator returns true if the specified object 
	 * is of the specified object type. 
	 */
	@JsSupport( {JsVersion.MOZILLA_ONE_DOT_FOUR})
	@Property boolean getInstanceof();
	
	/**
	 * The typeof operator returns a string indicating the type of the unevaluated operand.
	 * Used as: typeof operand  
	 */
	@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ONE})
	@Property boolean getTypeof();
	
	/**
	 * The void operator evaluates the given expression and then returns undefined. 
	 */
	@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ONE})
	@ARename(name="void")
	@Function void _void(String expression);
	
	/**
	 * Decodes a Uniform Resource Identifier (URI) previously 
	 * created by encodeURI or by a similar routine.
	 */
	@Function String decodeURI(String encodedURI);
	
	/**
	 * Decodes a Uniform Resource Identifier (URI) component previously created by 
	 * encodeURIComponent or by a similar routine.
	 */
	@Function String decodeURIComponent(String encodedURI);
	
	/**
	 * Encodes a Uniform Resource Identifier (URI) by replacing each instance 
	 * of certain characters by one, two, 
	 * or three escape sequences representing the UTF-8 encoding of the character.
	 */
	@Function String encodeURI(String URI);
	
	/**
	 * Encodes a Uniform Resource Identifier (URI) component by replacing each 
	 * instance of certain characters by one, two, or three escape sequences representing the UTF-8 encoding of the character. 
	 */
	@Function String encodeURIComponent(String String);
	
	/**
	 * Returns a URI-encoded version of a string.
	 */
	@Function String escape(String string);
	
	/**
	 *  Evaluates a string and executes it as if it was script code
	 */
	@Function Object eval(String string);
	
	/**
	 * Evaluates whether an argument is a finite number.
	 */
	@Function boolean isFinite(Number number);
	
	/**
	 * Evaluates whether an argument is NaN.
	 */
	@Function boolean isNaN(Object value);
	
	/**
	 * Parses a string and returns its value as a number.
	 */
	@OverLoadFunc Number parseFloat(String string);
	
	/**
	 * Parses a number and returns its value as a number.
	 * Browsers support this so do we
	 */
	@OverLoadFunc Number parseFloat(Number number);
	
	/**
	 * Parses a string argument and returns an integer.
	 * @param string 
	 */
	@OverLoadFunc Number parseInt(String string);
	
	/**
	 * Parses a number argument and returns the same Number.
	 * Browsers support this so do we
	 * @param Number 
	 */
	@OverLoadFunc Number parseInt(Number number);
	
	/**
	 * Parses a string argument and returns an integer of the specified radix or base.
	 * @param string 
	 * @param radix A number (from 2 to 36) that represents the numeral system to be used
	 */
	@OverLoadFunc Number parseInt(String string, Number radix);
	
	/**
	 * Returns a URI-decoded version of a string.
	 */
	@Function String unescape(String string);
	
	/**
	 * The typeof operator returns a string indicating the type of the unevaluated operand. 
	 * Used as: typeof (operand)
	 */
	@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ONE})
	@Function String typeof(Object operand);
}
