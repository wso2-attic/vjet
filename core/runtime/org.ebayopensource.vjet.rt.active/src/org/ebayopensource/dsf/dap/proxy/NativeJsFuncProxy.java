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
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

/**
 * proxy for native JS function, with a generic "call" and "apply" methods
 */
@AJavaOnly
public class NativeJsFuncProxy<T> implements INativeJsFuncProxy<T> {

	private static final long serialVersionUID = 1L;
	
	private final Function m_func;
	
	/**
	 * for framework
	 */
	public NativeJsFuncProxy(Function func) {
		assert func != null : "Passed in Rhino function must not be null" ;
		m_func = func;
	}
	
	public NativeJsFuncProxy(T obj, String funcName) {
		Scriptable type ;
		if (obj instanceof Class) {
			type = NativeJsHelper.getNativeClzType(((Class<?>)obj).getName());
		}
		else {
			type = toScriptable(obj);
		}
		m_func = (Function)ScriptableObject.getProperty(type, funcName);
	}

	/**
	 * apply method is not well typed in terms of its argument types
	 */
	public Object apply(T thisObj, Object[] args) {
		Object ret = m_func.call(
			NativeJsHelper.getContext(),
			NativeJsHelper.getScope(),
			toScriptable(thisObj),
			NativeJsHelper.toNatives(args));
		return convert(ret);
	}
	
	/**
	 * this call method is not well typed in terms of its argument types
	 */
	public Object call(T thisObj, Object ...args) {
		return apply(thisObj, args);
	}

	public Function getJsNative() {
		return m_func;
	}
	
	public static <E extends IJsJavaProxy> NativeJsFuncProxy<E> create(
		E obj, String funcName)
	{
		return new NativeJsFuncProxy<E>(obj, funcName);
	}
	
	protected Object convert(Object nativeObj) {
		return NativeJsHelper.convert(Object.class, nativeObj);
	}
	
	private Scriptable toScriptable(T obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof IJsJavaProxy) {
			return((IJsJavaProxy)obj).getJsNative();
		}
		else {
			return (Scriptable)NativeJsHelper.toNative(obj);
		}
	}
}
