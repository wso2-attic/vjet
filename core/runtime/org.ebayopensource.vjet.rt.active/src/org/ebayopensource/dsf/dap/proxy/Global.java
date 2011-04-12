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
import org.mozilla.mod.javascript.Undefined;

public class Global{
    
	public static String encodeURI(String str) {
        return NativeJsHelper.invokeNativeFunc("encodeURI",String.class,str);
    }
    
    public static String encodeURIComponent(String str) {
        return NativeJsHelper.invokeNativeFunc("encodeURIComponent",String.class,str);
    }
    
    public static String decodeURI(String str) {
        return NativeJsHelper.invokeNativeFunc("decodeURI",String.class,str);
    }
    
    public static String decodeURIComponent(String str) {
        return NativeJsHelper.invokeNativeFunc("decodeURIComponent",String.class,str);
    }
    
    public static String escape(String s){
    	return NativeJsHelper.invokeNativeFunc("escape",String.class,s);
    }
    
    public static String unescape(String s){
    	return NativeJsHelper.invokeNativeFunc("unescape",String.class,s);
    }
    
    public static int parseInt(String str){
    	return NativeJsHelper.invokeNativeFunc("parseInt", int.class, str);
    }
    
    public static int parseInt(String str, int radix){
    	return NativeJsHelper.invokeNativeFunc("parseInt", int.class, str, radix);
    }
    
    public static float parseFloat(String s){
    	return NativeJsHelper.invokeNativeFunc("parseFloat", float.class, s);
    }
    
    public static boolean isFinite(Number n){
    	return NativeJsHelper.invokeNativeFunc("isFinite", Boolean.class, n);
    }
    
    public static boolean isNaN(Object obj){
    	if(obj instanceof Integer){
    		return ((Integer)obj).equals(NaN.intValue());
    	}
    	else if (obj instanceof Float){
    		return ((Float)obj).equals(NaN.floatValue());
    	}
    	else {
    		return NativeJsHelper.invokeNativeFunc("isNaN", boolean.class, obj);
    	}
    }
    
    public static Object eval(String s){
    	return NativeJsHelper.eval(s);
    }
    
    public static boolean boolExpr(Object obj) {
    	if (obj == null) {
    		return false;
    	}
    	if (obj instanceof Undefined){
    		return false;
    	} else if (obj instanceof String) {
    		return !"".equals(obj);
    	} else if (obj instanceof Boolean) {
    		return ((Boolean)obj).booleanValue();
    	} else if (obj instanceof Number){
    		return !(((Number)obj).doubleValue() == 0);
    	}
    	return true;
    }
    
    public static boolean boolExpr(Object obj, String propName) {
		if (isDefined(obj, propName)) {
			Scriptable s = getScriptable(obj);
			return boolExpr(NativeJsHelper.convert(Object.class, s.get(
					propName, s)));
		}
		return false;
	}
    
    public static boolean isDefined(Object obj, String propName) {
    	Scriptable s = getScriptable(obj);
    	if (s==null) {
    		return false;
    	}
    	return s.has(propName, s);
    }
    
    private static Scriptable getScriptable(Object obj) {
    	Scriptable s = null;
    	if (obj instanceof Scriptable) {
    		s = (Scriptable)obj;
    	}
    	else if (obj instanceof IJsJavaProxy) {
    		s = ((IJsJavaProxy)obj).getJsNative();
    	}
    	return s;
    }
}