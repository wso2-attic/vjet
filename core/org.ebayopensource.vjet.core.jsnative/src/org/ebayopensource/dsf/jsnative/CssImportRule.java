package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 *     CSSImportRule has the all the properties and methods of the CSSRule object as well as the properties and methods defined below.
    The CSSImportRule object has the following properties:

        href
            This read-only property is of type String.
        media
            This read-only property is a MediaList object.
        styleSheet
            This read-only property is a CSSStyleSheet object.


 */
@Alias("CSSImportRule")
public interface CssImportRule extends CssRule {

	@Property String getHref();
	
	@Property MediaList getMedia();
	
	@Property CssStyleSheet getStyleSheet();
	
	
	
}
