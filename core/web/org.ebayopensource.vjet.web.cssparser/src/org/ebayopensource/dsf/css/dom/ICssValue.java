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
 *  The <code>CSSValue</code> interface represents a simple or a complex 
 * value. A <code>CSSValue</code> object only occurs in a context of a CSS 
 * property. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICssValue {
    // UnitTypes
    /**
     * The value is inherited and the <code>cssText</code> contains "inherit".
     */
    short CSS_INHERIT = 0;
    /**
     * The value is a primitive value and an instance of the 
     * <code>CSSPrimitiveValue</code> interface can be obtained by using 
     * binding-specific casting methods on this instance of the 
     * <code>CSSValue</code> interface.
     */
    short CSS_PRIMITIVE_VALUE = 1;
    /**
     * The value is a <code>CSSValue</code> list and an instance of the 
     * <code>CSSValueList</code> interface can be obtained by using 
     * binding-specific casting methods on this instance of the 
     * <code>CSSValue</code> interface.
     */
    short CSS_VALUE_LIST = 2;
    /**
     * The value is a custom value.
     */
    short CSS_CUSTOM = 3;

    /**
     *  A string representation of the current value. 
     * @exception DOMException
     *    SYNTAX_ERR: Raised if the specified CSS string value has a syntax 
     *   error (according to the attached property) or is unparsable. 
     *   <br>INVALID_MODIFICATION_ERR: Raised if the specified CSS string 
     *   value represents a different type of values than the values allowed 
     *   by the CSS property.
     *   <br> NO_MODIFICATION_ALLOWED_ERR: Raised if this value is readonly. 
     */
    String getCssText();
    ICssValue setCssText(String cssText) throws DOMException;

    /**
     *  A code defining the type of the value as defined above. 
     */
    short getCssValueType();
}
