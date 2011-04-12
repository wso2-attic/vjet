/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.js.dbgp.DBGPDebugger;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.IJsJavaProxy;
import org.mozilla.mod.javascript.IWillBeScriptable;
import org.mozilla.mod.javascript.NativeArray;
import org.mozilla.mod.javascript.NativeJavaObject;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.Undefined;

public class NativeJsHelper {

	private static Class<?>[] PARAM_TYPES = {Scriptable.class};

	/**
	 * load scriptable, from JS engine, which represents a given type
	 */
	public static Scriptable getNativeClzType(String clzType) {
		Context ctx = getContext();
		Scriptable scope = getScope();
		if (clzType.indexOf("$") > 0) { //for inner class type
			clzType = clzType.replace("$", ".");
		}
		return (Scriptable)ctx.evaluateString(scope, clzType, clzType, 0, null);
	}
	
	public static NativeArray createNativeArray(int size) {
		Context ctx = getContext();
		Scriptable scope = getScope();
		String js = "new Array(" + size + ")";
		return (NativeArray)ctx.evaluateString(scope, js, js, 0, null);
	}
	
	public static <T> T invokeNativeFunc(String funcName, Class<? extends T> type, Object ...args) {
		Context ctx = getContext();
		Scriptable scope = getScope();
		Function func = (Function)ctx.evaluateString(scope, funcName, funcName, 0, null);
		Object value = func.call(ctx, scope, null, toNatives(args));
		return convert(type, value);
	}
	
	public static Object eval(String js) {
		Context ctx = getContext();
		Scriptable scope = getScope();
		return ctx.evaluateString(scope, js, js, 0, null);
	}
		
	/**
	 * convert native JS value to proper java type
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(Class<? extends T> type, Object value) {
		if (value == null || value instanceof Undefined) {
			return null;
		}
		
		if(value.getClass().equals(s_primiNumTypes.get(type))){
			return (T)value;
		}
		
		if (IWillBeScriptable.class.isAssignableFrom(type) && value instanceof IWillBeScriptable) {
			return type.cast(value);
		}

		if (value instanceof NativeJavaObject) {
			value = ((NativeJavaObject)value).unwrap();
		}

		if (IJsJavaProxy.class.isAssignableFrom(type) && value instanceof Scriptable) {
			return getProxy(type, (Scriptable)value);
		}
		
		if (value instanceof NativeArray && (type == Array.class || type == Object.class)) {
			return (T) new Array((NativeArray)value);
		}

		if (value instanceof ScriptableObject) {
			ScriptableObject s = (ScriptableObject)value;
			String typeName = s.getClassName();
			if ("Number".equals(typeName)) {
				value = Double.valueOf(s.toString());
			}
			else {
				T o = getProxy(type, (Scriptable)value);
				if (o != null) {
					return o;
				}
				try {
					value = Context.jsToJava(value, type);
				} catch (Exception e) {
					throw new RuntimeException("can't covert value [" + value + "] to type of " + type.getName());
				}
			}
		}
		
		if (value instanceof Double) {
			if (type == Integer.class || type == int.class) {
				if(Double.isNaN((Double)value)){
					value = NaN.intValue();
				}
				else{
					value = new Integer(((Double)value).intValue());
				}
			}else if (type == Long.class || type == long.class) {
				value = new Long(((Double)value).longValue());
			}else if (type == Short.class || type == short.class) {
				value = new Short(((Double)value).shortValue());
			}else if (type == Byte.class || type == byte.class) {
				value = new Byte(((Double)value).byteValue());
			}else if (type == Float.class || type == float.class) {
				if(Double.isNaN((Double)value)){
					value = NaN.floatValue();
				}
				else{
					value = new Float(((Double)value).floatValue());
				}
			}
			Class<?> wrapperType = s_primiNumTypes.get(type);
			if (wrapperType != null) {
				return (T)value;
			}
		}
		if (type == boolean.class && value instanceof Boolean) {
			return (T)value;
		}

		if (type.isInstance(value)) {
			return type.cast(value);
		}
		
		if(value instanceof String){
			if (type == Integer.class || type == int.class) {
				value = new Integer((String)value);
			}else if (type == Long.class || type == long.class) {
				value = new Long((String)value);
			}else if (type == Short.class || type == short.class) {
				value = new Short((String)value);
			}else if (type == Byte.class || type == byte.class) {
				value = new Byte((String)value);
			}else if (type == Float.class || type == float.class) {				
				value = new Float((String)value);				
			}else if (type == Double.class || type == double.class) {
				value = new Double((String)value);
			}
			Class<?> wrapperType = s_primiNumTypes.get(type);
			if (wrapperType != null) {
				return (T)value;
			}
		}
		
		throw new RuntimeException("can't covert value [" + value + "] to type of " + type.getName());
	}
	
	/**
	 * converts a native object to string 
	 * @param value Object
	 * @return String
	 */
	public static String toString(Object value) {
		if (value instanceof NativeJavaObject) {
			value = ((NativeJavaObject)value).unwrap();
		} else if (value instanceof NativeArray) {
			value = new Array((NativeArray)value);
		} else if (value instanceof IJsJavaProxy) {
			value =  ((IJsJavaProxy) value).getJsNative();
		} 
		return value.toString();
	}
	
	/**
	 * convert java values to proper native JS types
	 */
	public static Object[] toNatives(Object[] args) {
		Object[] nativeArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			nativeArgs[i] = toNative(args[i]);
		}
		return nativeArgs;
	}
	
	/**
	 * convert java value to proper native JS type
	 */
	public static Object toNative(Object arg) {
		if (arg == null) {
			return null;
		}
		if (arg instanceof Undefined) {
			return arg;
		}
		if (arg instanceof IJsJavaProxy) {
			return ((IJsJavaProxy)arg).getJsNative();
		}
		else if (arg instanceof String) {
			return arg;
		}
		else if (arg instanceof Boolean) {
			return arg;
		}
		else {
			return Context.toObject(arg, getScope());
		}
	}
	
	/**
	 * get static property
	 */
	public static <T> T getStaticProperty(Class<?> type, String name, Class<T> rtnType) {
		Object result = ScriptableObject.getProperty
			(NativeJsHelper.getNativeClzType(type.getName()), name);
		return NativeJsHelper.convert(rtnType, result);
	}
	
	public static Scriptable getScope() {
		Scriptable scope = ScriptEngineCtx.ctx().getScope();
		// add by patrick
		if ((scope == null) && (Thread.currentThread() instanceof DBGPDebugger)) {
			DBGPDebugger debugger = (DBGPDebugger) Thread.currentThread();
			scope = debugger.getCurrentScope();
			ScriptEngineCtx.ctx().setScope(scope);
		}
		// end add
		return scope;
	}

	public static Context getContext() {
		Context scriptContext = ScriptEngineCtx.ctx().getScriptContext();
		// add by patrick
		if ((scriptContext == null)
				&& (Thread.currentThread() instanceof DBGPDebugger)) {
			DBGPDebugger debugger = (DBGPDebugger) Thread.currentThread();
			scriptContext = debugger.getContext();
			ScriptEngineCtx.ctx().setScriptContext(scriptContext);
		}
		// end add
		return scriptContext;
	}
	
	private static final Object[] EMPTY_ARGS = new Object[]{};
	private static final String VJO_getClass = "getClass";
	private static final String VJO_CLASS_getName = "getName";
	
	/**
	 * get VJO type name from native VJO object via 
	 * 	s.getClass().getName() in JavaScript
	 */
	public static String getVjoTypeName(Scriptable s) {
		Object rtn = ScriptableObject.getProperty(s, VJO_getClass);
		if (!(rtn instanceof Function)) {
			return null;
		}
		Function getClassFunc = (Function)ScriptableObject.getProperty(s, VJO_getClass);
		if (getClassFunc != null) {
			Scriptable vjoClz = (Scriptable)getClassFunc.call(getContext(), getScope(), s, EMPTY_ARGS);
			if (vjoClz != null) {
				Function getNameFunc =(Function)ScriptableObject.getProperty(vjoClz, VJO_CLASS_getName);
				return (String)getNameFunc.call(getContext(), getScope(), vjoClz, EMPTY_ARGS);
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getProxy(Class<? extends T> type, Scriptable s) {
		Object proxy = s.get(IJsJavaProxy.JS_JAVA_PROXY, s);
		if (type.isInstance(proxy)) {
			return type.cast(proxy);
		}

		String typeName = getVjoTypeName(s); //find the real type from VJO instance
		if (typeName != null) {
			try {
				//using custom ClassLoader to load the type
				type = (Class<? extends T>)getContext().getApplicationClassLoader().loadClass(typeName);
			} catch (ClassNotFoundException e) {
				//do nothing
			}
		}
		if (!IJsJavaProxy.class.isAssignableFrom(type)) {
			return null;
		}
		try {
			Constructor<? extends T> constructor = type.getDeclaredConstructor(PARAM_TYPES);
			constructor.setAccessible(true);
			return constructor.newInstance(new Object[]{s});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	private static final Map<Class<?>, Class<?>> s_primiNumTypes = new HashMap<Class<?>, Class<?>>();
	static {
		s_primiNumTypes.put(int.class, Integer.class);
		s_primiNumTypes.put(long.class, Long.class);
		s_primiNumTypes.put(double.class, Double.class);
		s_primiNumTypes.put(float.class, Float.class);
		s_primiNumTypes.put(short.class, Short.class);
		s_primiNumTypes.put(byte.class, Byte.class);
	}
}
