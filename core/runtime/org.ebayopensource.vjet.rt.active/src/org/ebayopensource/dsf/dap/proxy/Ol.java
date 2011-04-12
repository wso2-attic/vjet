/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.dap.util.IterableJs;
import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.javatojs.anno.ASupportJsForEachStmt;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.NativeObject;
import org.mozilla.mod.javascript.Scriptable;
/**
 * Generic implementation of Native JS Object literal
 */
@Alias("ObjLiteral")
@ASupportJsForEachStmt
public class Ol implements IJsJavaProxy, IterableJs<String>{
	@AExclude
	private Scriptable m_nativeObj;
	
	/* FRAMEWORK USAGE ONLY */
	@AExclude
	public Ol(Scriptable nativeObj) {
		m_nativeObj = nativeObj;
		m_nativeObj.put(JS_JAVA_PROXY, m_nativeObj, this);
	}
	
	protected Ol(Object... args) {
		this(new  NativeOl());
		if (args == null || args.length == 0) {
			return;
		}
		if (args.length % 2 != 0) {
			DsfExceptionHelper.chuck("argument list consists of name value pairs");
		}
		
		for (int i=0; i<args.length; i+=2) {
			if (!(args[i] instanceof String)) {
				DsfExceptionHelper.chuck("key must be a string");
			}
			put((String)args[i],args[i+1]);
		}
	}
	
	public void put(String key, Object value) {
		m_nativeObj.put(key, m_nativeObj, NativeJsHelper.toNative(value));
	}
	
	public Object get(String key) {
		Object value = m_nativeObj.get(key, m_nativeObj);
		if (value == null || value == Scriptable.NOT_FOUND) {
			return value;
		}
		return NativeJsHelper.convert(Object.class, value);
	}
	
	public void remove(String key) {
		m_nativeObj.delete(key);
	}
	

	/**
	 * Return an Iterator for all keys.
	 * Should be only used by java runtime for handling for-each-statement.
	 */
	/* FRAMEWORK USAGE ONLY */
	@AExclude
	public Iterator<String> iterator() {
		Object[] keys = m_nativeObj.getIds();
		List<String> keyList = new ArrayList<String>(keys.length);
		for (Object key : keys) {
			keyList.add(key.toString());
		}
		return keyList.iterator();
	}
	
	/* FRAMEWORK USAGE ONLY */
	@AExclude
	public Scriptable getJsNative() {
		return m_nativeObj;
	}
	
	/**
	 * API to create a native object literal. ex: { x : 0, y : 1 }
	 * Ol.obj("x",0,"y",1);
	 * 
	 * @param nvs
	 *            list of string/object name value pairs, which are comma
	 *            delimited
	 * 
	 * @return Ol
	 */
	public static Ol obj(Object... nvs) {
		return new Ol(nvs);
	}
	
	@AExclude
	private static class NativeOl extends NativeObject {
		private static final long serialVersionUID = 1L;
		
		@SuppressWarnings("unchecked")
		@Override
	    public Object getDefaultValue(Class typeHint) {
	    	return getClass().getName();
	    }
	}
}
