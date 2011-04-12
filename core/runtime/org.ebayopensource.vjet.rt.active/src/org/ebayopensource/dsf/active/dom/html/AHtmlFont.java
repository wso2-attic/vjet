/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DFont;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlFont;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlFont extends AHtmlElement implements HtmlFont {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlFont(AHtmlDocument doc, DFont font) {
		super(doc, font);
		populateScriptable(AHtmlFont.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getColor() {
		return getDFont().getHtmlColor();
	}

	public String getFace() {
		return getDFont().getHtmlFace();
	}

	public String getSize() {
		return getDFont().getHtmlSize();
	}

	public void setColor(String color) {
		getDFont().setHtmlColor(color);
		onAttrChange(EHtmlAttr.color, color);
	}

	public void setFace(String face) {
		getDFont().setHtmlFace(face);
		onAttrChange(EHtmlAttr.face, face);
	}

	public void setSize(String size) {
		getDFont().setHtmlSize(size);
		onAttrChange(EHtmlAttr.size, size);
	}
	
	private DFont getDFont() {
		return (DFont) getDNode();
	}
}
