/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/REC-html40/present/styles.html#edef-STYLE
 *
 */
@Alias("HTMLStyleElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlStyle extends HtmlElement {

	@Property boolean getDisabled();
	@Property void setDisabled(boolean disabled);

	@Property String getMedia();
	@Property void setMedia(String media);

	@Property String getType();
	@Property void setType(String type);
	
	/**
	 * Retrieves an interface pointer that provides access to the style sheet 
	 * object's properties and methods
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property CssStyleSheet getStyleSheet();
	
	/**
	 * Retrieves an interface pointer that provides access to the style sheet 
	 * object's properties and methods
	 */
	@DOMSupport(DomLevel.TWO)
	@Property CssStyleSheet getSheet();

}
