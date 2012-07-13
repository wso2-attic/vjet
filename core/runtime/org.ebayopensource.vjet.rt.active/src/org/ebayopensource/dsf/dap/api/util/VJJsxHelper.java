/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.api.util;

import org.ebayopensource.dsf.dap.proxy.FuncX;
import org.ebayopensource.dsf.dap.proxy.Global;
import org.ebayopensource.dsf.dap.proxy.INativeJsFuncProxy;
import org.ebayopensource.dsf.dap.proxy.JFunction;
import org.ebayopensource.dsf.dap.proxy.JFunctionX;
import org.ebayopensource.dsf.common.CallerIntrospector;

public class VJJsxHelper {
	
	//
	// Singleton
	//
	private static final VJJsxHelper s_instance = new VJJsxHelper();
	private VJJsxHelper(){}
	public static VJJsxHelper getInstance(){
		return s_instance;
	}
	
	//
	// API for general VJIT extension used in common construction/use
	//
	
	//
	// References - answers unbound function references
	//
	
	/**
	 * Answer an unbound static function from the passed in clz.  The function
	 * must return the passed in type.
	 * Throws an exception if the static method of the specific return type
	 * is not found. 
	 * Note that methods that are overloaded can not be used since there is
	 * no JavaScript/Rhino way to directly overload a method.
	 */
	public JFunction<Class<?>, Void> ref(Class<?> clz, String funcName) {
		return ref(clz, void.class, funcName) ;
	}

	public <R> JFunction<Class<?>, R> ref(
		Class<?> clz, Class<R> returnType, String funcName)
	{
		return JFunctionX.def(clz, funcName, returnType) ;
	}
	
	/**
	 * define function from static method of the caller
	 */
	public JFunction<Class<?>, Void> ref(String funcName) {
		Class<?> callerClz = CallerIntrospector.getCallingClass();
		return JFunctionX.def(callerClz, funcName, void.class);
	}

	/**
	 * Answer an unbound instance function from the passed in thisObj that
	 * returns the passed in type.
	 * Throws an exception if the instance method of the specific return type
	 * is not found. 
	 * Note that methods that are overloaded can not be used since there is
	 * no JavaScript/Rhino way to directly overload a method.
	 */
	public <T> JFunction<T, Void> ref(T thisObj, String funcName) {
		return JFunctionX.def(thisObj, funcName, void.class);
	}
	
	public <T, R> JFunction<T, R> ref(
		T thisObj, Class<R> returnType, String funcName)
	{
		return JFunctionX.def(thisObj, funcName, returnType);
	}
	
	//
	// Binding - returns bound function references with optionally
	// curried arguments.
	/** 
	 * Answers a bound static function.  The static function is looked up
	 * from the passed in clz and then is bound to that clz.  If the optional
	 * args are passed in, they are treated as curry args.
	 */	
	public <R> JFunction<Class<?>, R> bind(
		Class<?> clz, Class<R> returnType, String funcName, Object... args)
	{
		JFunction<Class<?>, R> f = ref(clz, returnType, funcName) ;
		return FuncX.bind(clz, f, args);
	}
	
	public JFunction<Class<?>, Void> bind(
		Class<?> clz, String funcName, Object... args)
	{
		return bind(clz, void.class, funcName, args) ;
	}
	
	/**
	 * return bound function references for caller's static method
	 */
	public JFunction<Class<?>, Void> bind(String funcName) {
		Class<?> callerClz = CallerIntrospector.getCallingClass();
		return bind(callerClz, funcName) ;
	}

	/** 
	 * Answers a bound instance function.  The instance function is looked up
	 * from the passed in thisObj and then is bound to that thisObj.  If the optional
	 * args are passed in, they are treated as curry args.
	 */
	public <T, R> JFunction<T, R> bind(
		T thisObj, Class<R> returnType, String funcName, Object... args)
	{
		JFunction<T, R> f = ref(thisObj, returnType, funcName);
		return FuncX.bind(thisObj, f, args);
	}
	
	public <T> JFunction<T, Void> bind(
		T thisObj, String funcName, Object... args)
	{
		return bind(thisObj, void.class, funcName, args) ;
	}

	//
	// Binding - enable bindings of a ctx to a function
	//
	/**
	 * The returned function will ensure that the func in enclosure will always 
	 * be bound to ctx.
	 */
	public <T, E> JFunction<T, E> hitch(T ctx, JFunction<T, E> func) {		
		return FuncX.hitch(ctx, func) ;
	}
	
	/**
	 * If openCtx is false, the returned function will ensure that the func in 
	 * closure will always be bound to ctx.
	 * If openCtx is true, the returned function will ensure that the func in 
	 * closure will be bound to ctx if the calling function is bound to global 
	 * context, otherwise, func will be rebound to new context associated with 
	 * calling function.
	 */
	public <T, E> JFunction<T, E> hitch(
		T ctx, JFunction<T, E> func, boolean openCtx)
	{		
		return FuncX.hitch(ctx, func, openCtx) ;
	}
	
	/**
	 * The returned function will ensure that the func in enclosure will always 
	 * be bound to ctx.
	 */
	public <T> INativeJsFuncProxy<T> hitch(T ctx, INativeJsFuncProxy<T> func) {		
		return FuncX.hitch(ctx, func) ;
	}
	
	/**
	 * If openCtx is false, the returned function will ensure that the func in 
	 * closure will always be bound to ctx.
	 * If openCtx is true, the returned function will ensure that the func in 
	 * closure will be bound to ctx if the calling function is bound to global 
	 * context, otherwise, func will be rebound to new context associated with 
	 * calling function.
	 */
	public <T> INativeJsFuncProxy<T> hitch(
		T ctx, INativeJsFuncProxy<T> func, boolean openCtx)
	{		
		return FuncX.hitch(ctx, func, openCtx) ;
	}
	
	//
	// Currying - create closures to enable arg passing to functions
	//
	/**
	 * The returned function closure (currying function)
	 * will combine the passed-in arguments with arguments at the time
	 * of function invocation.
	 */
	public <T, E> JFunction<T, E> curry(JFunction<T, E> func, Object ...args) {		
		return FuncX.curry(func, args) ;
	}
	
	/**
	 * The returned function closure (currying function) will combine the 
	 * passed-in arguments with arguments at the time of function invocation.
	 */
	public <T> INativeJsFuncProxy<T> curry(INativeJsFuncProxy<T> func, Object ...args) {
		return FuncX.curry(func, args) ;
	}
	
	//
	// Other global-ish functions we want to expose
	//

	
	public boolean boolExpr(Object obj) {
		return Global.boolExpr(obj) ;
	}
	
	
	public boolean boolExpr(Object obj, String propName) {
		return Global.boolExpr(obj, propName) ;
	}
	
	/**
	 * In JavaScript you often will see code like the following:
	 * 
	 * if (obj.focus) { ... }
	 * 
	 * What is going on is that JavaScript will interpret this as
	 * a check for undefined which if the result of window.focus is
	 * undefined it is treated as a false.  This paradigm does not
	 * translate well to Java.  First the focus() method is actually
	 * a method not a property.  To get around this we have a utility
	 * method to represent this check:
	 * VJ.jsx.isDefined(obj, "focus") --> if (obj.focus) { ... }
	 * VJ.jsx.isDefined(obj, null)    --> if (obj) { ... }
	 */
	public boolean isDefined(Object obj, String propName) {
		return Global.isDefined(obj, propName);
	}	
}