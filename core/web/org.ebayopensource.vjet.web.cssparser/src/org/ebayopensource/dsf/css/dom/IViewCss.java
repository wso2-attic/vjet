/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom;

import org.w3c.dom.views.AbstractView;

import org.ebayopensource.dsf.dom.DElement;

/**
 *  This interface represents a CSS view. The <code>getComputedStyle</code> 
 * method provides a read only access to the computed values of an element. 
 * <p> The expectation is that an instance of the <code>ViewCSS</code> 
 * interface can be obtained by using binding-specific casting methods on an 
 * instance of the <code>AbstractView</code> interface. 
 * <p> Since a computed style is related to an <code>Element</code> node, if 
 * this element is removed from the document, the associated 
 * <code>CSSStyleDeclaration</code> and <code>CSSValue</code> related to 
 * this declaration are no longer valid. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface IViewCss extends AbstractView {
    /**
     *  This method is used to get the computed style as it is defined in . 
     * @param elt The element whose style is to be computed. This parameter 
     *   cannot be null. 
     * @param pseudoElt The pseudo-element or <code>null</code> if none. 
     * @return  The computed style. The <code>CSSStyleDeclaration</code> is 
     *   read-only and contains only absolute values. 
     */
    ICssStyleDeclaration getComputedStyle(DElement elt, String pseudoElt);
}
