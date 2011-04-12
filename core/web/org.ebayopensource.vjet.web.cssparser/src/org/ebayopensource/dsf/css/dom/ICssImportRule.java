/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom;

import org.ebayopensource.dsf.dom.stylesheets.IMediaList;

/**
 *  The <code>CSSImportRule</code> interface represents a @import rule within 
 * a CSS style sheet. The <code>@import</code> rule is used to import style 
 * rules from other style sheets. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICssImportRule extends ICssRule {
    /**
     *  The location of the style sheet to be imported. The attribute will not 
     * contain the <code>"url(...)"</code> specifier around the URI. 
     */
    String getHref();

    /**
     *  A list of media types for which this style sheet may be used. 
     */
    IMediaList getMedia();

    /**
     * The style sheet referred to by this rule, if it has been loaded. The 
     * value of this attribute is <code>null</code> if the style sheet has 
     * not yet been loaded or if it will not be loaded (e.g. if the style 
     * sheet is for a media type not supported by the user agent). 
     */
    ICssStyleSheet getStyleSheet();
}
