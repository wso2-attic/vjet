/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.Scriptable;
/**
 * Base class for native proxy function reference
 * @param <T>
 */
public class FuncRef <T extends IJsJavaProxy> implements IJsJavaProxy{
	private INativeJsFuncProxy<T> m_proxy;
	protected FuncRef(INativeJsFuncProxy<T> proxy) {
		m_proxy = proxy;
	}

	public Object apply(T thisObj, Object[] args) {
		return m_proxy.apply(thisObj, args);
	}

	protected Object call(T thisObj, Object... args) {
		return m_proxy.call(thisObj,args);
	}

	public Scriptable getJsNative() {
		return m_proxy.getJsNative();
	}
}
