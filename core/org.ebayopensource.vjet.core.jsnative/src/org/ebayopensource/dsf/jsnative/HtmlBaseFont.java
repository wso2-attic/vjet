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
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * http://www.w3.org/TR/REC-html40/present/graphics.html#edef-BASEFONT
 */
@Alias("HTMLBaseFontElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlBaseFont extends HtmlElement {
	
	@Property String getColor();
	@Property void setColor(String color);

	@Property String getFace();
	@Property void setFace(String face);

	@Property int getSize();
	@Property void setSize(int size);


}
