package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/*
 *     cssText
 This property is of type String and can raise a DOMException object on setting.
 length
 This read-only property is of type Number.
 parentRule
 This read-only property is a CSSRule object.

 The CSSStyleDeclaration object has the following methods:

 getPropertyValue(propertyName)
 This method returns a String.
 The propertyName parameter is of type String.
 getPropertyCSSValue(propertyName)
 This method returns a CSSValue object.
 The propertyName parameter is of type String.
 removeProperty(propertyName)
 This method returns a String.
 The propertyName parameter is of type String.
 This method can raise a DOMException object.
 getPropertyPriority(propertyName)
 This method returns a String.
 The propertyName parameter is of type String.
 setProperty(propertyName, value, priority)
 This method has no return value.
 The propertyName parameter is of type String.
 The value parameter is of type String.
 The priority parameter is of type String.
 This method can raise a DOMException object.
 item(index)
 This method returns a String.
 The index parameter is of type Number.
 Note: This object can also be dereferenced using square bracket notation (e.g. obj[1]). Dereferencing with an integer index is equivalent to invoking the item method with that index.


 */
@Alias("CSSStyleDeclaration")
public interface CssStyleDeclaration extends IWillBeScriptable {

	@Property
	String getCssText();

	@Property
	int getLength();

	@Property
	CssRule getParentRule();

	/**
	 * getPropertyValue(propertyName) This method returns a String. The
	 * propertyName parameter is of type String.
	 */
	@Function
	String getPropertyValue(String propertyName);

	/**
	 * getPropertyCSSValue(propertyName) This method returns a CSSValue object.
	 * The propertyName parameter is of type String.
	 */
	@Function
	CssValue getPropertyCSSValue(String propertyName);

	/**
	 * removeProperty(propertyName) This method returns a String. The
	 * propertyName parameter is of type String. This method can raise a
	 * DOMException object.
	 */
	@Function
	String removeProperty(String propertyName);

	/**
	 * getPropertyPriority(propertyName) This method returns a String. The
	 * propertyName parameter is of type String.
	 */
	@Function
	String getPropertyPriority(String propertyName);

	/**
	 * setProperty(propertyName, value, priority) This method has no return
	 * value. The propertyName parameter is of type String. The value parameter
	 * is of type String. The priority parameter is of type String. This method
	 * can raise a DOMException object.
	 * 
	 */
	@Function
	void setProperty(String propertyName, String value, String priority);

	/**
	 * This method returns a String. The index parameter is of type Number.
	 * Note: This object can also be dereferenced using square bracket notation
	 * (e.g. obj[1]). Dereferencing with an integer index is equivalent to
	 * invoking the item method with that index.
	 * 
	 * @param index
	 * @return
	 */
	// TODO look into array deferenced access and using jstarray
	@Function
	String item(int index);
	
	

}
