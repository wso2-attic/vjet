/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DStyle;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.CssStyleSheet;
import org.ebayopensource.dsf.jsnative.HtmlStyle;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlStyle extends AHtmlElement implements HtmlStyle {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlStyle(AHtmlDocument doc, DStyle style) {
		super(doc, style);
		populateScriptable(AHtmlStyle.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public boolean getDisabled() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.disabled,getHtmlAttribute(EHtmlAttr.disabled));
	}

	public String getMedia() {
		return getDStyle().getHtmlMedia();
	}

	public String getType() {
		return getDStyle().getHtmlType();
	}

	public void setDisabled(boolean disabled) {
		setHtmlAttribute(EHtmlAttr.disabled, disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
	}

	public void setMedia(String media) {
		getDStyle().setHtmlMedia(media);
		onAttrChange(EHtmlAttr.media, media);
	}

	public void setType(String type) {
		getDStyle().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, type);
	}
	
	public CssStyleSheet getStyleSheet() {
		//return getStyle();
		return null;
		// TODO implement
	}
	
	private DStyle getDStyle() {
		return (DStyle) getDNode();
	}

	@Override
	public CssStyleSheet getSheet() {
		// TODO Auto-generated method stub
		return null;
	}

}
