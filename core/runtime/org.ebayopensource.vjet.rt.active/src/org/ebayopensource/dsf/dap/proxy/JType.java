/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import org.mozilla.mod.javascript.Scriptable;

/**
 * A TypeRef for VJO class authored in Java
 */
public class JType implements ITypeRef {

	private Scriptable m_type;
	
	/**
	 * for framework only
	 */
	public JType(Scriptable type) {
		m_type = type;
	}
	
	public Scriptable getJsNative() {
		return m_type;
	}
	
	public static JType def(Class<?> jType) {
		Scriptable type = NativeJsHelper.getNativeClzType(jType.getName());
		return new JType(type);
	}
}
