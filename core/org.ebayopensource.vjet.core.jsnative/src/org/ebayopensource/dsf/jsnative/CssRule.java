package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

@Alias("CSSRule")
public interface CssRule extends IWillBeScriptable {

	/*
	 * Prototype Object CSSRule

    The CSSRule class has the following constants:

        CSSRule.UNKNOWN_RULE
            This constant is of type Number and its value is 0.
        CSSRule.STYLE_RULE
            This constant is of type Number and its value is 1.
        CSSRule.CHARSET_RULE
            This constant is of type Number and its value is 2.
        CSSRule.IMPORT_RULE
            This constant is of type Number and its value is 3.
        CSSRule.MEDIA_RULE
            This constant is of type Number and its value is 4.
        CSSRule.FONT_FACE_RULE
            This constant is of type Number and its value is 5.
        CSSRule.PAGE_RULE
            This constant is of type Number and its value is 6.

Object CSSRule

    The CSSRule object has the following properties:

        type
            This read-only property is of type Number.
        cssText
            This property is of type String and can raise a DOMException object on setting.
        parentStyleSheet
            This read-only property is a CSSStyleSheet object.
        parentRule
            This read-only property is a CSSRule object.


	 */
	
	/** "UNKNOWN_RULE" */
	@AJavaOnly
	@ARename(name = "'UNKNOWN_RULE'")
	short UNKNOWN_RULE = 0;
	
	@AJavaOnly
	short STYLE_RULE = 1;
	
	@AJavaOnly
	short CHARSET_RULE = 2;
	
	@AJavaOnly
	short IMPORT_RULE = 3;
	
	
	@AJavaOnly
	short MEDIA_RULE = 4;
	
	@AJavaOnly
	short FONT_FACE_RULE = 5;
	
	@AJavaOnly
	short PAGE_RULE = 6;
	
	
	@Property int getType();
	@Property String getCssText();
	@Property CssStyleSheet getParentStyleSheet();
	@Property CssRule getParentRule();
	
	
	
	
	
	
}
