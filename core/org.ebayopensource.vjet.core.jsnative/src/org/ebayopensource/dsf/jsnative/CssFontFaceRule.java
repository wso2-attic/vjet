package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 *     CSSFontFaceRule has the all the properties and methods of the CSSRule object as well as the properties and methods defined below.
 The CSSFontFaceRule object has the following properties:

 style
 This read-only property is a CSSStyleDeclaration object.


 *
 */
@Alias("CSSFontFaceRule")
public interface CssFontFaceRule extends CssRule {

	@Property CssStyleDeclaration getStyle();
	
}
