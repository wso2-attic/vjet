/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DLink;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.CssStyleSheet;
import org.ebayopensource.dsf.jsnative.HtmlElementStyle;
import org.ebayopensource.dsf.jsnative.HtmlLink;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlLink extends AHtmlElement implements  HtmlLink {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlLink(AHtmlDocument doc, DLink link) {
		super(doc, link);
		populateScriptable(AHtmlLink.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getCharset() {
		return getDLink().getHtmlCharset();
	}

	public boolean getDisabled() {
		return getDLink().getHtmlDisabled();
	}

	public String getHref() {
		return getDLink().getHtmlHref();
	}

	public String getHreflang() {
		return getDLink().getHtmlHreflang();
	}

	public String getMedia() {
		return getDLink().getHtmlMedia();
	}

	public String getRel() {
		return getDLink().getHtmlRel();
	}

	public String getRev() {
		return getDLink().getHtmlRev();
	}

	public String getTarget() {
		return getDLink().getHtmlTarget();
	}

	public String getType() {
		return getDLink().getHtmlType();
	}

	public void setCharset(String charset) {
		getDLink().setHtmlCharset(charset);
		onAttrChange(EHtmlAttr.charset, charset);
	}

	public void setDisabled(boolean disabled) {
		setHtmlAttribute(EHtmlAttr.disabled, disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
	}

	public void setHref(String href) {
		getDLink().setHtmlHref(href);
		onAttrChange(EHtmlAttr.href, href);
	}

	public void setHreflang(String hreflang) {
		getDLink().setHtmlHreflang(hreflang);
		onAttrChange(EHtmlAttr.hreflang, hreflang);
	}

	public void setMedia(String media) {
		getDLink().setHtmlMedia(media);
		onAttrChange(EHtmlAttr.media, media);
	}

	public void setRel(String rel) {
		getDLink().setHtmlRel(rel);
		onAttrChange(EHtmlAttr.rel, rel);
	}

	public void setRev(String rev) {
		getDLink().setHtmlRev(rev);
		onAttrChange(EHtmlAttr.rev, rev);
	}

	public void setTarget(String target) {
		getDLink().setHtmlTarget(target);
		onAttrChange(EHtmlAttr.target, target);
	}

	public void setType(String type) {
		getDLink().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, type);
	}
	
	public CssStyleSheet getStyleSheet() {
//		return getStyle();
		//TODO implement
		return null;
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	// Since property name is 'onunload', Rhino invokes this method.
	public Object getOnunload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnunload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	private DLink getDLink() {
		return (DLink) getDNode();
	}

	@Override
	public HtmlElementStyle getSheet() {
		// TODO Auto-generated method stub
		return null;
	}

}
