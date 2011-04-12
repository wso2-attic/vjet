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
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.Scriptable;

/**
 * Proxy class for native JS type itself, not its instance.
 */
@AExclude
public class NativeJsTypeRef<T extends IJsJavaProxy> implements ITypeRef {

	private Scriptable m_nativeObj;
	
	/**
	 * for framework
	 */
	public NativeJsTypeRef(Scriptable nativeObj) {
		init(nativeObj);
	}
	
	/**
	 * return a proxy instance for an given IJsJavaProxy type, create new one if it is
	 * not existed
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IJsJavaProxy> NativeJsTypeRef<T> get(Class<T> clzType) {
		Scriptable nativeType = NativeJsHelper.getNativeClzType(clzType.getName());
		Object proxy = nativeType.get(IJsJavaProxy.JS_JAVA_PROXY, nativeType);
		if (NativeJsTypeRef.class.isInstance(proxy)) {
			return (NativeJsTypeRef<T>)proxy;
		}
		return new NativeJsTypeRef<T>(nativeType);
	}
	
	public Scriptable getJsNative() {
		return m_nativeObj;
	}
	
	private void init(Scriptable nativeObj) {
		m_nativeObj = nativeObj;
		//save the proxy to the hidden property on native object, so we can
		//get the original proxy back during conversion from native to java
		nativeObj.put(JS_JAVA_PROXY, nativeObj, this);
	}
}
