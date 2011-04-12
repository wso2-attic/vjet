/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DDel;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.jsnative.HtmlMod;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlMod extends AHtmlElement implements HtmlMod {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlMod(AHtmlDocument doc, DDel node) {
		super(doc, node);
		populateScriptable(AHtmlMod.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getCite() {
		return getDDel().getHtmlCite();
	}

	public String getDateTime() {
		return getDDel().getHtmlDateTime();
	}

	@Override
	public String getNodeName() {
		// We cannot deligate to DDel since it
		// has different node name
		return HtmlTypeEnum.MOD.getName().toUpperCase();
	}

	public void setCite(String cite) {
		getDDel().setHtmlCite(cite);
		onAttrChange(EHtmlAttr.cite, cite);
	}

	public void setDateTime(String dateTime) {
		getDDel().setHtmlDateTime(dateTime);
		onAttrChange(EHtmlAttr.datetime, dateTime);
	}
	
	private DDel getDDel() {
		return (DDel) getDNode();
	}

}
