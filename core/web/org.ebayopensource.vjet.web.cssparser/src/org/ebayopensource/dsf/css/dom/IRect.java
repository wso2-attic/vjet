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
 *  The <code>Rect</code> interface is used to represent any rect value. This 
 * interface reflects the values in the underlying style property. Hence, 
 * modifications made to the <code>CSSPrimitiveValue</code> objects modify 
 * the style property. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface IRect {
    /**
     *  This attribute is used for the top of the rect. 
     */
    ICssPrimitiveValue getTop();

    /**
     *  This attribute is used for the right of the rect. 
     */
    ICssPrimitiveValue getRight();

    /**
     *  This attribute is used for the bottom of the rect. 
     */
    ICssPrimitiveValue getBottom();

    /**
     *  This attribute is used for the left of the rect. 
     */
    ICssPrimitiveValue getLeft();
}
