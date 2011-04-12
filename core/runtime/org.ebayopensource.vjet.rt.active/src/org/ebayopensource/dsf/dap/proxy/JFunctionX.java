/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import java.lang.reflect.Method;

import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.javatojs.anno.ASupportExternalThis;
import org.mozilla.mod.javascript.Function;

public class JFunctionX<T, E>  extends NativeJsFuncProxy<T>{
	
	
	// Exclude we do not want rhino Function in vjo
	@AExclude
	public JFunctionX(Function func) {
		super(func);
	}
	
	@SuppressWarnings("unchecked")
	public static <E> JFunction<Class<?>, E> def(Class<?> clz, String funcName, Class<E> returnType) {
		if (needNoScopeSupport(clz, funcName)) {
			return NoScopeFunc.def(JType.def(clz), funcName, returnType);
		}
		return new JFunction(JType.def(clz), funcName, returnType);
	}

	public static <T, E> JFunction<T, E> def(
		T thisObj, String funcName, Class<E> returnType)
	{
		return new JFunction<T, E>(thisObj, funcName, returnType);
	}
	
	private static boolean needNoScopeSupport(Class<?> clz, String funcName) {
		Method[] methods = clz.getDeclaredMethods();
		for (Method m : methods) {
			if (funcName.equals(m.getName())) {
				return m.getAnnotation(ASupportExternalThis.class) != null;
			}
		}
		return false;
	}
	
}
