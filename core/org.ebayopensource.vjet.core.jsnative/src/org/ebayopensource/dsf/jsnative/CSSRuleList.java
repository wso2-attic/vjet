package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/*
 *     The CSSRuleList object has the following properties:

        length
            This read-only property is of type Number.

    The CSSRuleList object has the following methods:

        item(index)
            This method returns a CSSRule object.
            The index parameter is of type Number.
            Note: This object can also be dereferenced using square bracket notation (e.g. obj[1]). Dereferencing with an integer index is equivalent to invoking the item method with that index.


 */

public interface CSSRuleList extends IWillBeScriptable {

	@Property int getLength();
	@Function CssRule item(int index);
	
}
