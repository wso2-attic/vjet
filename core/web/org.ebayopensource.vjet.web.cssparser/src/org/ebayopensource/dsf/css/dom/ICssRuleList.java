/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom;

/**
 *  The <code>CSSRuleList</code> interface provides the abstraction of an 
 * ordered collection of CSS rules. 
 * <p> The items in the <code>CSSRuleList</code> are accessible via an 
 * integral index, starting from 0. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICssRuleList {
    /**
     *  The number of <code>CSSRules</code> in the list. The range of valid 
     * child rule indices is <code>0</code> to <code>length-1</code> 
     * inclusive. 
     */
    int getLength();

    /**
     *  Used to retrieve a CSS rule by ordinal index. The order in this 
     * collection represents the order of the rules in the CSS style sheet. 
     * If index is greater than or equal to the number of rules in the list, 
     * this returns <code>null</code>. 
     * @param indexIndex into the collection
     * @return The style rule at the <code>index</code> position in the 
     *   <code>CSSRuleList</code>, or <code>null</code> if that is not a 
     *   valid index. 
     */
    ICssRule item(int index);
}
