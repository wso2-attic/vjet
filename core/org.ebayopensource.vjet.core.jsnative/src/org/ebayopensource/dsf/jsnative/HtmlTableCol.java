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
 * http://www.w3.org/TR/REC-html40/struct/tables.html#edef-COL
 */
@Alias("HTMLTableColElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlTableCol extends HtmlElement {

	@Property String getAlign();
	@Property void setAlign(String align);

	// For DOM Level 2 HTMLTableColElement
	@Property String getCh();
	@Property void setCh(String ch);

	// For DOM Level 2 HTMLTableColElement
	@Property String getChOff();
	@Property void setChOff(String chOff);
	
	// For HTML 4.01
	@Property String getChar();
	@Property void setChar(String ch);

	// For HTML 4.01	
	@Property String getCharoff();
	@Property void setCharoff(String chOff);

	@Property int getSpan();
	@Property void setSpan(int span);

	@Property String getVAlign();
	@Property void setVAlign(String vAlign);

	@Property String getWidth();
	@Property void setWidth(String width);

}
