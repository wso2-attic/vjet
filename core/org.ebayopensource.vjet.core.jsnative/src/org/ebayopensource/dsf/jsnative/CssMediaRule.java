package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;

@Alias("CSSMediaRule")
public interface CssMediaRule extends CssRule {

	@Property
	MediaList getMedia();

	@Property
	// TODO add variant array
	CSSRuleList getCssRules();

	/**
	 * This method has no return value. The index parameter is of type Number.
	 * This method can raise a DOMException object.
	 * 
	 * @param index
	 */
	@Function
	void deleteRule(int index);

	/**
	 * This method returns a Number. The rule parameter is of type String. The
	 * index parameter is of type Number. This method can raise a DOMException
	 * object.
	 */
	@Function
	int insertRule(String rule, int index);

}
