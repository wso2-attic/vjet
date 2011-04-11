package org.mozilla.mod.javascript;

/**
 * A interface for custom data type converter to convert
 * native js object to typed java object
 * 
 * an eBay extension to Rhino - EBAY MOD
 */
public interface IJsJavaConverter {
	
	IJsJavaConvertible convert(Scriptable s);

}
