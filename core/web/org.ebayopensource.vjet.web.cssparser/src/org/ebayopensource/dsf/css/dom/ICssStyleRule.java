/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom;

import org.ebayopensource.dsf.css.sac.ISelectorList;

import org.w3c.dom.DOMException;

/**
 *  The <code>CSSStyleRule</code> interface represents a single rule set in a 
 * CSS style sheet. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICssStyleRule extends ICssRule {
    /**
     *  The textual representation of the selector for the rule set. The 
     * implementation may have stripped out insignificant whitespace while 
     * parsing the selector. 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the specified CSS string value has a syntax 
     *   error and is unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this rule is readonly.
     */
    String getSelectorText();
	ICssStyleRule setSelectorText(String selectorText) throws DOMException;

    /**
     *  The declaration-block of this rule set. 
     */
    ICssStyleDeclaration getStyle();
    
    //
    // eBay extensions
    //
	ISelectorList getSelectors() ;
	ICssStyleSheet getParentStyleSheet() ;
	ICssRule getParentRule() ;
	String getCssText() ;
//	void setCssText(String cssText) throws DOMException ;
	ICssStyleRule setStyle(ICssStyleDeclaration style) ;
}
