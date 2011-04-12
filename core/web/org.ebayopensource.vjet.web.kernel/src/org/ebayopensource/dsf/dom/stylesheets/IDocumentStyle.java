/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.stylesheets;

/**
 *  The <code>DocumentStyle</code> interface provides a mechanism by which the 
 * style sheets embedded in a document can be retrieved. The expectation is 
 * that an instance of the <code>DocumentStyle</code> interface can be 
 * obtained by using binding-specific casting methods on an instance of the 
 * <code>Document</code> interface. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface IDocumentStyle {
    /**
     *  A list containing all the style sheets explicitly linked into or 
     * embedded in a document. For HTML documents, this includes external 
     * style sheets, included via the HTML  LINK element, and inline  STYLE 
     * elements. In XML, this includes external style sheets, included via 
     * style sheet processing instructions (see ). 
     */
    IStyleSheetList getStyleSheets();
}
