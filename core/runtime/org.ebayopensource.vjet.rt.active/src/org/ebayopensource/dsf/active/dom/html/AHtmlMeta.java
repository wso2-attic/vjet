/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DMeta;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlMeta;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlMeta extends AHtmlElement implements HtmlMeta {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlMeta(AHtmlDocument doc, DMeta meta) {
		super(doc, meta);
		populateScriptable(AHtmlMeta.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getContent() {
		return getDMata().getHtmlContent();
	}

	public String getHttpEquiv() {
		return getDMata().getHtmlHttpEquiv();
	}

	public String getName() {
		return getDMata().getHtmlName();
	}

	public String getScheme() {
		return getDMata().getHtmlScheme();
	}

	public void setContent(String content) {
		getDMata().setHtmlContent(content);
		onAttrChange(EHtmlAttr.content, content);
	}

	public void setHttpEquiv(String httpEquiv) {
		getDMata().setHtmlHttpEquiv(httpEquiv);
		onAttrChange(EHtmlAttr.http_equiv, httpEquiv);
	}

	public void setName(String name) {
		getDMata().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setScheme(String scheme) {
		getDMata().setHtmlScheme(scheme);
		onAttrChange(EHtmlAttr.scheme, scheme);
	}
	
	private DMeta getDMata() {
		return (DMeta) getDNode();
	}

}
