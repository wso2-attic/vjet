/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.global;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/**
 * 
 * Object is the primitive JavaScript object type. 
 * All JavaScript objects are descended from Object.
 *
 */
@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ONE, JsVersion.JSCRIPT_THREE_DOT_ZERO})
public interface Object extends IWillBeScriptable {
	
	@Constructor void Object(Object... value);
	
	/**
	 * Specifies the function that creates a constructor of an object.
	 */
	@Property Object getConstructor();
	
	/**
	 * Allows a function to be defined that will be executed 
	 * when an undefined object member is called as a method. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Property Object get__noSuchMethod__();
	
	/**
	 * Points to an object's context. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Property Object get__parent__();
	
	/**
	 * Points to the object which was used as prototype 
	 * when the object was instantiated. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Property Object get__proto__();
	
	/**
	 * Associates a function with a property that, when accessed, 
	 * executes that function and returns its return value. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Function void __defineGetter__(String pName, Object fun); 
	
	/**
	 * Associates a function with a property that, when set, 
	 * executes that function which modifies the property. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Function void __defineSetter__(String pName, Object fun); 
	
	/**
	 * Evaluates a string of JavaScript code in the context of an object.
	 * @deprecated in JavaScript 1.2
	 */
	@Deprecated
	@Function Object eval(String s); 
	
	/**
	 * Returns true if the object is a prototype of another.
	 */
	@Function boolean isPrototypeOf(Object obj);
	
	/**
	 * Checks whether a property is inherited.
	 */
	@Function boolean hasOwnProperty(String prop);
	
	/**
	 * Returns true if the property can be enumerated in a for/in loop.
	 */
	@Function boolean propertyIsEnumerable(String prop);
	
	/**
	 * Returns the function associated with 
	 * the specified property by the __defineSetter__ method. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Function Object __lookupGetter__(String prop);
	
	/**
	 * Returns the function associated with 
	 * the specified property by the __defineSetter__ method. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Function Object __lookupSetter__(String prop);
	
	/**
	 * Removes a watchpoint from a property of the object. 
	 */
	@JsSupport(JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Function Object unwatch(String prop);
	
	/**
	 * Adds a watchpoint to a property of the object.
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@Function Object watch(String prop, Object handler);
	
	/**
	 * Returns a localized string representing the specified object.
	 * @return
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@Function java.lang.String toLocalString();
	
	/**
	 * Returns a string representing the specified object.
	 * @return
	 */
	@Function java.lang.String toString();
	
	/**
	 * Returns a string representing the source code of the object.
	 * @return
	 */
	@JsSupport( JsVersion.MOZILLA_ONE_DOT_THREE)
	@Function java.lang.String toSource();

}
