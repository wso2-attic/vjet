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
import org.mozilla.mod.javascript.Function;

/**
 * Proxy to Java Script Function
 */
public class JFunction<T, E> extends NativeJsFuncProxy<T> {
	
	private Class<E> m_returnType = null;
	
	/**
	 * Framework use only
	 */
	// Exclude we do not want rhino Function in vjo
	@AExclude
	public JFunction(Function func) {
		super(func);
	}
	
	JFunction(T obj, String funcName, Class<E> returnType) {
		super(obj, funcName);
		m_returnType = returnType;
	}
	
	public E call(T thisObj, Object ...args) {
		return apply(thisObj, args);
	}
	
	@SuppressWarnings("unchecked")
	public E apply(T thisObj, Object[] args) {
		Object rtn = super.apply(thisObj, args);
		return (E)rtn;
	}


	public String toString() {
		return getJsNative().toString();
	}
	
	protected Object convert(Object nativeObj) {
		return NativeJsHelper.convert
			(m_returnType == null ? Object.class : m_returnType, nativeObj);
	}
	
	Class<E> getReturnType() {
		return m_returnType;
	}
	
	void setReturnType(Class<E> type) {
		m_returnType = type;
	}
		

}
