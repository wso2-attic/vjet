package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

@Alias("CSSCharsetRule")
public interface CssCharsetRule extends CssRule, IWillBeScriptable {

	/*
    encoding
    This property is of type String and can raise a DOMException object on setting.
*/
	@Property String getEncoding();

}
