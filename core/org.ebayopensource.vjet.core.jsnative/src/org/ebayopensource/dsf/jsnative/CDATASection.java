/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;

/**
 * CDATA sections are used to escape blocks of text containing characters 
 * that would otherwise be regarded as markup. The only delimiter that is recognized 
 * in a CDATA section is the "]]>" string that ends the CDATA section. 
 * CDATA sections cannot be nested. Their primary purpose is for including material 
 * such as XML fragments, without needing to escape all the delimiters.
 *
 */

@DOMSupport(DomLevel.ONE)
@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
@JsMetatype
public interface CDATASection extends Text {

}
