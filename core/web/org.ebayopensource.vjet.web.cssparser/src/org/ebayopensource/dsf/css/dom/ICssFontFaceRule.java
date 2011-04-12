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
 *  The <code>CSSFontFaceRule</code> interface represents a @font-face rule in 
 * a CSS style sheet. The <code>@font-face</code> rule is used to hold a set 
 * of font descriptions. 
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * @since DOM Level 2
 */
public interface ICssFontFaceRule extends ICssRule {
    /**
     *  The declaration-block of this rule. 
     */
    ICssStyleDeclaration getStyle();
	ICssFontFaceRule setStyle(ICssStyleDeclaration style) ;
}
