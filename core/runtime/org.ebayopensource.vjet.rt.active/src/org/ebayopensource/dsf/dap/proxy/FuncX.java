/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.Scriptable;

/**
 * This utility class provides a set of method to create a new function
 * with closure.
 * 
 * hitch produces a function closure to bind a given scope to the function.
 * 
 * curry produces a function closure to bind a given set of arguments.
 */
@AJavaOnly
public class FuncX {
	
	/**
	 * The returned function will ensure that the func in enclosure will always be bound
	 * to ctx.
	 */
	public static <T, E> JFunction<T, E> hitch(T ctx, JFunction<T, E> func) {		
		return hitch(ctx, func, false);
	}
	
	/**
	 * If openCtx is false, the returned function will ensure that the func in closure
	 * will always be bound to ctx.
	 * If openCtx is true, the returned function will ensure that the func in closure
	 * will be bound to ctx if the calling function is bound to global context,
	 * otherwise, func will be rebound to new context associated with calling function.
	 */
	public static <T, E> JFunction<T, E> hitch(T ctx, JFunction<T, E> func, boolean openCtx) {		
		Function hFunc = getHitchedFunc(ctx, func.getJsNative(), openCtx);
		JFunction<T, E> newJFunc = new JFunction<T, E>(hFunc);
		newJFunc.setReturnType(func.getReturnType());
		return newJFunc;
	}
	
	/**
	 * The returned function will ensure that the func in enclosure will always be bound
	 * to ctx.
	 */
	public static <T> INativeJsFuncProxy<T> hitch(T ctx, INativeJsFuncProxy<T> func) {		
		return hitch(ctx, func, false);
	}
	
	/**
	 * If openCtx is false, the returned function will ensure that the func in closure
	 * will always be bound to ctx.
	 * If openCtx is true, the returned function will ensure that the func in closure
	 * will be bound to ctx if the calling function is bound to global context,
	 * otherwise, func will be rebound to new context associated with calling function.
	 */
	public static <T> INativeJsFuncProxy<T> hitch(T ctx, INativeJsFuncProxy<T> func, boolean openCtx) {		
		Function hFunc = getHitchedFunc(ctx, func.getJsNative(), openCtx);
		return new NativeJsFuncProxy<T>(hFunc);
	}
	
	/**
	 * The returned function closure (currying function)
	 * will combine the passed-in arguments with arguments at the time
	 * of function invocation.
	 */
	public static <T, E> JFunction<T, E> curry(JFunction<T, E> func, Object ...args) {		
		Function curriedFunc = getCurriedFunc(func.getJsNative(), args);
		JFunction<T, E> newJFunc = new JFunction<T, E>(curriedFunc);
		newJFunc.setReturnType(func.getReturnType());
		return newJFunc;
	}
	
	/**
	 * The returned function closure (currying function)
	 * will combine the passed-in arguments with arguments at the time
	 * of function invocation.
	 */
	public static <T> INativeJsFuncProxy<T> curry(INativeJsFuncProxy<T> func, Object ...args) {
		Function curriedFunc = getCurriedFunc(func.getJsNative(), args);
		return new NativeJsFuncProxy<T>(curriedFunc);
	}
	
	/**
	 * The returned function closure (bound function)
	 * will achieve both hitching and currying.
	 */
	public static <T, E> JFunction<T, E> bind(T obj, JFunction<T, E> func, Object ...args) {		
		Function boundFunc = getBoundFunc(obj, func.getJsNative(), args);
		JFunction<T, E> newJFunc = new JFunction<T, E>(boundFunc);
		newJFunc.setReturnType(func.getReturnType());
		return newJFunc;
	}
	
	/**
	 * The returned function closure (bound function)
	 * will achieve both hitching and currying.
	 */
	public static <T> INativeJsFuncProxy<T> bind(T obj, INativeJsFuncProxy<T> func, Object ...args) {
		Function boundFunc = getBoundFunc(obj, func.getJsNative(), args);
		return new NativeJsFuncProxy<T>(boundFunc);
	}
	
	private static final String VJO_HITCH = "vjo.hitch";
	private static Function getHitchedFunc(Object obj, Function func, boolean openCtx) {
		Context ctx = NativeJsHelper.getContext();
		Scriptable scope = NativeJsHelper.getScope();
		Function hitch = (Function)ctx.evaluateString(scope, VJO_HITCH, VJO_HITCH, 0, null);
		Object[] args;
		if (openCtx) {
			args = new Object[] {func, obj, openCtx};
		}
		else {
			args = new Object[] {func, obj};
		}
		return (Function)hitch.call(ctx, scope, null, NativeJsHelper.toNatives(args));
	}
	
	private static final String VJO_CURRY = "vjo.curry";
	private static Function getCurriedFunc(Function func, Object[] args) {
		Context ctx = NativeJsHelper.getContext();
		Scriptable scope = NativeJsHelper.getScope();
		Function curry = (Function)ctx.evaluateString(scope, VJO_CURRY, VJO_CURRY, 0, null);
		Object[] newArgs = new Object[args.length + 1];
		newArgs[0] = func;
		System.arraycopy(args, 0, newArgs, 1, args.length);
		return (Function)curry.call(ctx, scope, null, NativeJsHelper.toNatives(newArgs));
	}
	
	private static final String VJO_BIND = "vjo.bind";
	private static Function getBoundFunc(Object obj, Function func, Object[] args) {
		Context ctx = NativeJsHelper.getContext();
		Scriptable scope = NativeJsHelper.getScope();
		Function bind = (Function)ctx.evaluateString(scope, VJO_BIND, VJO_BIND, 0, null);
		Object[] newArgs = new Object[args.length + 2];
		newArgs[0] = obj;
		newArgs[1] = func;
		System.arraycopy(args, 0, newArgs, 2, args.length);
		return (Function)bind.call(ctx, scope, null, NativeJsHelper.toNatives(newArgs));
	}
}
