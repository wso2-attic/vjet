/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DBaseFont;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlBaseFont;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlBaseFont extends AHtmlElement implements HtmlBaseFont {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlBaseFont(AHtmlDocument doc, DBaseFont node) {
		super(doc, node);
		populateScriptable(AHtmlBaseFont.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getColor() {
		return getDBaseFont().getHtmlColor();
	}

	public String getFace() {
		return getDBaseFont().getHtmlFace();
	}

	public int getSize() {
		return Integer.valueOf(getDBaseFont().getHtmlSize());
	}

	public void setColor(String color) {
		getDBaseFont().setHtmlColor(color);
		onAttrChange(EHtmlAttr.color, color);
	}

	public void setFace(String face) {
		getDBaseFont().setHtmlFace(face);
		onAttrChange(EHtmlAttr.face, face);
	}

	public void setSize(int size) {
		getDBaseFont().setHtmlSize(size);
		onAttrChange(EHtmlAttr.size, String.valueOf(size));
	}
	
	private DBaseFont getDBaseFont() {
		return (DBaseFont) getDNode();
	}

}
