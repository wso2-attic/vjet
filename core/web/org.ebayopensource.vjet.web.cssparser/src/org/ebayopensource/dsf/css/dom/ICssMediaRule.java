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

import org.ebayopensource.dsf.dom.stylesheets.IMediaList;

/**
 *  The <code>CSSMediaRule</code> interface represents a @media rule in a CSS 
 * style sheet. A <code>@media</code> rule can be used to delimit style 
 * rules for specific media types. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICssMediaRule extends ICssRule {
    /**
     *  A list of media types for this rule. 
     */
    IMediaList getMedia();

    /**
     *  A list of all CSS rules contained within the media block. 
     */
    ICssRuleList getCssRules();

    /**
     *  Used to insert a new rule into the media block. 
     * @param rule The parsable text representing the rule. For rule sets 
     *   this contains both the selector and the style declaration. For 
     *   at-rules, this specifies both the at-identifier and the rule 
     *   content. 
     * @param index The index within the media block's rule collection of the 
     *   rule before which to insert the specified rule. If the specified 
     *   index is equal to the length of the media blocks's rule collection, 
     *   the rule will be added to the end of the media block. 
     * @return  The index within the media block's rule collection of the 
     *   newly inserted rule. 
     * @exception DOMException
     *   HIERARCHY_REQUEST_ERR: Raised if the rule cannot be inserted at the 
     *   specified index, e.g., if an <code>@import</code> rule is inserted 
     *   after a standard rule set or other at-rule.
     *   <br>INDEX_SIZE_ERR: Raised if the specified index is not a valid 
     *   insertion point.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this media rule is 
     *   readonly.
     *   <br>SYNTAX_ERR: Raised if the specified rule has a syntax error and 
     *   is unparsable.
     */
    int insertRule(String rule, int index) throws DOMException;

    /**
     *  Used to delete a rule from the media block. 
     * @param index The index within the media block's rule collection of the 
     *   rule to remove. 
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified index does not correspond to 
     *   a rule in the media rule list.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this media rule is 
     *   readonly.
     */
    void deleteRule(int index) throws DOMException;
}
