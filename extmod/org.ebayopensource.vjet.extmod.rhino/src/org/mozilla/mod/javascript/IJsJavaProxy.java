package org.mozilla.mod.javascript;

/**
 * Java Proxy for Native JavaScript Object
 * 
 * an eBay extension to Rhino - EBAY MOD
 */
public interface IJsJavaProxy {
	
	String JS_JAVA_PROXY = "_js_java_proxy";
	
	Scriptable getJsNative();

}
