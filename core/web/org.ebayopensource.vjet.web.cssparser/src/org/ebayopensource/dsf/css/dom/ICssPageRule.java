/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom;

import org.w3c.dom.DOMException;

/**
 *  The <code>CSSPageRule</code> interface represents a @page rule within a 
 * CSS style sheet. The <code>@page</code> rule is used to specify the 
 * dimensions, orientation, margins, etc. of a page box for paged media. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICssPageRule extends ICssRule {
    /**
     *  The parsable textual representation of the page selector for the rule. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the specified CSS string value has a syntax 
     *   error and is unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this rule is readonly.
     */
    String getSelectorText();
    ICssPageRule setSelectorText(String selectorText) throws DOMException;

    /**
     *  The declaration-block of this rule. 
     */
    ICssStyleDeclaration getStyle();
}
