/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.global;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.Static;


/**
 * 
 * Represents JavaScript native Function object
 *
 */
@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ONE, JsVersion.JSCRIPT_TWO_DOT_ZERO})
public interface Function extends Object {
	
	// Function constructor has the following form
	// new Function ([arg1[, arg2[, ... argN]],] functionBody)
	// but we can't use java vararg  for argument list 
	// since it must be the last parameter.
	
	@Constructor void Function();
	
	@Constructor void Function(String functionBody);
	
	@Constructor void Function(String arg1, String functionBody);
	
	@Constructor void Function(String arg1, String arg2, String functionBody);
	
	@Constructor void Function(String arg1, String arg2, String arg3, String functionBody);
	
	/**
	 * An array-like object corresponding to the arguments passed to a function. 
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_ONE)
	@Property Arguments getArguments();
	
	/**
	 * An array-like object corresponding to the arguments passed to a function. 
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_ONE)
	@Alias("arguments")
	@Property @Static Arguments getArguments_s();
	
	/**
	 * Represents the prototype for this class. You can use the prototype 
	 * to add properties or methods to all instances of a Function.
	 */
	@Property Object getPrototype();
	
	/**
	 * Represents the prototype for this class. You can use the prototype 
	 * to add properties or methods to all instances of a Function.
	 */
	@Alias("prototype")
	@Property @Static Object getPrototype_s();
	
	/**
	 * Specifies the function that invoked the currently executing function.
	 * This property is not part of ECMA-262 Edition 3 standard. 
	 */
	@Property Function getCaller();
	
	/**
	 * Specifies the function that invoked the currently executing function.
	 * This property is not part of ECMA-262 Edition 3 standard. 
	 */
	@Alias("caller")
	@Property @Static Function getCaller_s();
	
	/**
	 * Specifies the number of arguments expected by the function. 
	 */
	@Property Number getLength();
	
	/**
	 * Specifies the number of arguments expected by the function. 
	 */
	@Alias("length")
	@Property @Static Number getLength_s();
	
	/**
	 * The name of the function. 
	 */
	@Property String getName();
	
	/**
	 * The name of the function. 
	 */
	@Alias("name")
	@Property @Static String getName_s();
	
	/**
	 * Applies the method of another object in the context of a different object (the calling object); 
	 * arguments can be passed as an Array object. 
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@OverLoadFunc Object apply();
	
	/**
	 * Applies the method of another object in the context of a different object (the calling object); 
	 * arguments can be passed as an Array object. 
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@OverLoadFunc Object apply(Object thisArg);
	
	/**
	 * Applies the method of another object in the context of a different object (the calling object); 
	 * arguments can be passed as an Array object. 
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@OverLoadFunc Object apply(Object thisArg, Object ...argsArray);
	
	/**
	 * Calls (executes) a method of another object in the context of a different object 
	 * (the calling object); arguments can be passed as they are. 
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@OverLoadFunc Object call();
	
	/**
	 * Calls (executes) a method of another object in the context of a different object 
	 * (the calling object); arguments can be passed as they are. 
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@OverLoadFunc Object call(Object thisArg);
	
	/**
	 * Calls (executes) a method of another object in the context of a different object 
	 * (the calling object); arguments can be passed as they are. 
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@OverLoadFunc Object call(Object thisArg, Object ...argsArray);
	
	// Static versions of apply and call methods
	
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@Alias("apply")
	@OverLoadFunc @Static Object s_apply();
	
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@Alias("apply")
	@OverLoadFunc @Static Object s_apply(Object thisArg);
	
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@Alias("apply")
	@OverLoadFunc @Static Object s_apply(Object thisArg, Object ...argsArray);
	
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@Alias("call")
	@OverLoadFunc @Static Object s_call();
	
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@Alias("call")
	@OverLoadFunc @Static Object s_call(Object thisArg);
	
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@Alias("call")
	@OverLoadFunc @Static Object s_call(Object thisArg, Object ...argsArray);
	
}
