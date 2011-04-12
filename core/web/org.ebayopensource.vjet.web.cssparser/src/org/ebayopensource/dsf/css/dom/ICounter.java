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
 *  The <code>Counter</code> interface is used to represent any counter or 
 * counters function value. This interface reflects the values in the 
 * underlying style property. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICounter {
    /**
     *  This attribute is used for the identifier of the counter. 
     */
    String getIdentifier();

    /**
     *  This attribute is used for the style of the list. 
     */
    String getListStyle();

    /**
     *  This attribute is used for the separator of the nested counters. 
     */
    String getSeparator();
}
