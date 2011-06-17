package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

@Alias("CSSStyleRule")
public interface CssStyleRule extends CssRule, IWillBeScriptable {

	@Property String getSelectorText();
	@Property CssStyleDeclaration getStyle();	
}
