package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Property;

@Alias("CSSPageRule")
public interface CssPageRule extends CssRule {

/*    CSSPageRule has the all the properties and methods of the CSSRule object as well as the properties and methods defined below.
    The CSSPageRule object has the following properties:

        selectorText
            This property is of type String and can raise a DOMException object on setting.
        style
            This read-only property is a CSSStyleDeclaration object.
*/
	/**
	 *  This property is of type String and can raise a DOMException object on setting.
	 */
	@Property String getSelectorText();
	
	/**
	 * This read-only property is a CSSStyleDeclaration object.
	 */
	
	@Property CssStyleDeclaration getStyle();
	


}
