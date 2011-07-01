package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsVariantArray;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 * Object CSSStyleSheet

 CSSStyleSheet has the all the properties and methods of the StyleSheet object as well as the properties and methods defined below.
 The CSSStyleSheet object has the following properties:

 ownerRule
 This read-only property is a CSSRule object.
 cssRules
 This read-only property is a CSSRuleList object.

 The CSSStyleSheet object has the following methods:

 insertRule(rule, index)
 This method returns a Number.
 The rule parameter is of type String.
 The index parameter is of type Number.
 This method can raise a DOMException object.
 deleteRule(index)
 This method has no return value.
 The index parameter is of type Number.
 This method can raise a DOMException object.


 */
@Alias("CSSStyleSheet")
public interface CssStyleSheet extends StyleSheet {

	
	@Property 
	String getCssText();
	
	@Property
	CssRule getOwnerRule();

	@Property
	@JsVariantArray({CssRule.class, CssMediaRule.class,
		CssStyleRule.class, CssFontFaceRule.class, CssCharsetRule.class,
		CssImportRule.class, CssPageRule.class
		})
	CSSRuleList getCssRules();

	@Function
	Number insertRule(String rule, int index);

	/**
	 * Deletes a rule from the style sheet.
	 * 
	 * @param lIndex
	 *            Required. The index within the rule list for the style sheet
	 *            of the rule to remove.
	 */

	@Function
	void deleteRule(int index);

	/**
	 * 
	 @param sSelector
	 *            Required. String that specifies the selector for the new rule.
	 *            Single contextual selectors are valid. For example, "div p b"
	 *            is a valid contextual selector.
	 * @param sStyle
	 *            Required. String that specifies the style assignments for this
	 *            style rule. This style takes the same form as an inline style
	 *            specification. For example, "color:blue" is a valid style
	 *            parameter.
	 * @param iIndex
	 *            Optional. Integer that specifies the zero-based position in
	 *            the rules collection where the new style rule should be
	 *            placed. -1 Default. The rule is added to the end of the
	 *            collection.
	 */
	@BrowserSupport(BrowserType.IE_6P)
	@OverLoadFunc
	int addRule(String sSelector);

	@BrowserSupport(BrowserType.IE_6P)
	@OverLoadFunc
	int addRule(String sSelector, String sStyle);

	@BrowserSupport(BrowserType.IE_6P)
	@OverLoadFunc
	int addRule(String sSelector, String sStyle, int iIndex);

	/**
	 * Deletes an existing style rule for the styleSheet object, and adjusts the
	 * index of the rules collection accordingly.
	 * 
	 * @param iIndex
	 *            Optional. Integer that specifies the index value of the rule
	 *            to be deleted from the style sheet. If an index is not
	 *            provided, the first rule in the rules collection is removed.
	 */
	@BrowserSupport(BrowserType.IE_6P)
	@OverLoadFunc
	void removeRule();

	@OverLoadFunc
	void removeRule(int iIndex);

	/**
	 * Adds a style sheet to the imports collection for the specified style
	 * sheet.
	 * 
	 * @param sURL
	 *            Required. String that specifies the location of the source
	 *            file for the style sheet.
	 * @param iIndexRequest
	 *            Optional. Integer that specifies the requested position for
	 *            the style sheet in the collection. If this value is not given,
	 *            the style sheet is added to the end of the collection.
	 */
	@BrowserSupport(BrowserType.IE_6P)
	@OverLoadFunc
	void addImport(String sURL);

	@OverLoadFunc
	void addImport(String sURL, int iIndexRequest);

	/**
	 * Creates a new page object for a style sheet.
	 * 
	 * @param sSelector
	 *            Required. String that specifies the selector for the new page
	 *            object.
	 * @param sStyle
	 *            Required. String that specifies the style assignments for this
	 *            page object. This style takes the same form as an inline style
	 *            specification. For example, "color:blue" is a valid style
	 *            parameter.
	 * @param iIndex
	 *            Required. Integer that specifies the zero-based position in
	 *            the pages collection where the new page object should be
	 *            placed. -1 Default. The page object is added to the end of the
	 *            collection.
	 * @return Reserved. Always returns -1.
	 */
	@BrowserSupport(BrowserType.IE_6P)
	@Function
	int addPageRule(String sSelector, String sStyle, int iIndex);

	/**
	 * Removes the imported style sheet from the imports collection based on
	 * ordinal position.
	 * 
	 * @param iIndex
	 *            Required. Integer value that indicates which imported style
	 *            sheet to remove.
	 */
	@BrowserSupport(BrowserType.IE_6P)
	@Function void removeImport(int iIndex);
	

}
