/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import org.mozilla.mod.javascript.BaseFunction;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.NativeArray;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

/**
 * This class is to create function intercepter which can setup real "this" scope
 * for invoking "non-scoped" java static function.
 */
class NoScopeFunc extends BaseFunction {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	private final JFunction m_func;
	
	@SuppressWarnings("unchecked")
	private NoScopeFunc(JFunction func) {
		m_func = func;
		defineProperty("call", this,
			ScriptableObject.DONTENUM|ScriptableObject.PERMANENT|ScriptableObject.READONLY);
		defineProperty("apply", this,
			ScriptableObject.DONTENUM|ScriptableObject.PERMANENT|ScriptableObject.READONLY);
		defineProperty("toString", this,
			ScriptableObject.DONTENUM|ScriptableObject.PERMANENT|ScriptableObject.READONLY);
	}
	
	/**
	 * create a "non-scoped" function from a java static method
	 */
	@SuppressWarnings("unchecked")
	static JFunction def(JType type, String funcName, Class returnType) {
		JFunction func = JFunctionX.def(type, funcName, returnType);
		Function wrapper = new NoScopeFunc(func);
		return new JFunction(wrapper);
	}
	
	private static final Object[] EMPTY_ARGS = new Object[0];
    @SuppressWarnings("unchecked")
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
    	Object passedInScope = null;
    	if (args.length == 0) {
    		return toString();
    	}
		passedInScope = args[0];
    	try {
    		//setup current scope
    		ScriptEngineCtx.ctx().pushScopeToStack(passedInScope);
    		
    		//invoke delegated method - m_func   		
    		Object[] realArgs = EMPTY_ARGS;
    		if (args.length > 1) {
    			if (args.length==2 && args[1] instanceof NativeArray) {
    				NativeArray nArr = (NativeArray)args[1];
    				int len = (int)nArr.getLength();
    				realArgs = new Object[len];
    				for (int i=0; i < len; i++) {
    					realArgs[i] = nArr.get(i, nArr);
    				}
    			} else {
    				realArgs = new Object[args.length - 1];
    				for (int i = 1; i < args.length; i++) {
    					realArgs[i-1] = args[i];
    				}
    			}
    		}
    		return NativeJsHelper.toNative(m_func.apply(null, realArgs));
    	}
    	finally {
    		ScriptEngineCtx.ctx().popScopeFromStack();
    	}
    }
    
    @Override
	public String toString() {
		return m_func.toString();
	}
	
    @SuppressWarnings("unchecked")
    @Override
	public Object getDefaultValue(Class typeHint) {
    	if (typeHint == null || typeHint == String.class) {
    		return toString();
    	}
    	if (typeHint == Boolean.class || typeHint == boolean.class) {
    		return Boolean.TRUE;
    	}
        return getDefaultValue(this, typeHint);
    }
}
