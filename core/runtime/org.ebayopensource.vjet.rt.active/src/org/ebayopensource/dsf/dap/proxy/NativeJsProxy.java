/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

/**
 * Proxy class for Native JavaScript to be accessed and invoked directly by java code.
 */
@AExclude
public class NativeJsProxy implements IJsJavaProxy {
	
	private Scriptable m_nativeObj;
	
	/**
	 * for framework
	 */
	protected NativeJsProxy(Scriptable nativeObj) {
		init(nativeObj);
	}
	
	/**
	 * for inheritance
	 */
	protected NativeJsProxy(Object ...args) {
		Context ctx = NativeJsHelper.getContext();
		Scriptable scope = NativeJsHelper.getScope();
		String clzType = getClass().getName();
		Function nativeType = (Function)ctx.evaluateString(scope, clzType, clzType, 0, null);
		init(nativeType.construct(ctx, scope, NativeJsHelper.toNatives(args)));
	}
	
	/**
	 * for framework
	 */
	public Scriptable getJsNative() {
		return m_nativeObj;
	}
	
	/**
	 * proxying a method with void return type,
	 * passing method name explicitly if it is different from calling method
	 */
	protected void callWithName(String methodName, Object ...args) {
		execInstanceMethod(methodName, args);
	}
	
	/**
	 * proxying a method with a given return type,
	 * passing method name explicitly if it is different from the calling method name
	 */
	protected <T> T callWithName(String methodName, Class<T> rtnType, Object ...args) {
		Object result = execInstanceMethod(methodName, args);
		return NativeJsHelper.convert(rtnType, result);
	}

	/**
	 * proxying a property as getter,
	 * passing property name explicitly if it is different from the calling method name
	 */
	protected <T> T getProperty(String name, Class<T> rtnType) {
		Object result = ScriptableObject.getProperty(m_nativeObj, name);
		return NativeJsHelper.convert(rtnType, result);
	}
	
	/**
	 * proxying a property as setter,
	 * passing property name explicitly if it is different from the calling method name
	 */
	protected void setProperty(String name, Object value) {
		ScriptableObject.putProperty(m_nativeObj, name, NativeJsHelper.toNative(value));
	}
	
	/**
	 * proxying a static property as getter,
	 * passing property name explicitly if it is different from the calling method name
	 */
	protected static <T> T getStaticProperty(String clzName, String name, Class<T> rtnType) {
		Object result = ScriptableObject.getProperty
			(NativeJsHelper.getNativeClzType(clzName), name);
		return NativeJsHelper.convert(rtnType, result);
	}

	/**
	 * proxying a static property as setter,
	 * passing property name explicitly if it is different from the calling method name
	 */
	protected static void setStaticProperty(String clzName, String name, Object value) {
		ScriptableObject.putProperty(
			NativeJsHelper.getNativeClzType(clzName),
			name,
			NativeJsHelper.toNative(value));
	}
	
	/**
	 * proxying a static method with a void return type,
	 * passing method name explicitly if it is different from the calling method name
	 */
	protected static void callStaticWithName(String clzName, String name, Object ...args) {
		execStaticMethod(clzName, name, args);
	}
	
	/**
	 * proxying a static method with a given return type,
	 * passing method name explicitly if it is different from the calling method name
	 */
	protected static <T> T callStaticWithName(String clzName, String name, Class<T> rtnType, Object ...args) {
		Object result = execStaticMethod(clzName, name, args);
		return NativeJsHelper.convert(rtnType, result);
	}
	
	private void init(Scriptable nativeObj) {
		m_nativeObj = nativeObj;
		//save the proxy to the hidden property on native object, so we can
		//get the original proxy back during convertion from native to java
		nativeObj.put(JS_JAVA_PROXY, nativeObj, this);
	}
	
	private Object execInstanceMethod(String methodName, Object ...args) {
		Function nativeFunc = (Function)ScriptableObject.getProperty(m_nativeObj, methodName);
		return nativeFunc.call(
			NativeJsHelper.getContext(),
			NativeJsHelper.getScope(),
			m_nativeObj,
			NativeJsHelper.toNatives(args));
	}
	
	private static Object execStaticMethod(String clzName, String methodName, Object ...args) {
		Scriptable nativeType = NativeJsHelper.getNativeClzType(clzName);
		Function nativeFunc = (Function)ScriptableObject.getProperty
			(nativeType, methodName);
		return nativeFunc.call(
			NativeJsHelper.getContext(),
			NativeJsHelper.getScope(),
			nativeType,
			NativeJsHelper.toNatives(args));
	}

}
