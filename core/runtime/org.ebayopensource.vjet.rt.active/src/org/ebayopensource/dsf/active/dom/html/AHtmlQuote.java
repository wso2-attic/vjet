/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DQ;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlQuote;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlQuote extends AHtmlElement implements HtmlQuote {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlQuote(AHtmlDocument doc, DQ dp) {
		super(doc, dp);
		populateScriptable(AHtmlQuote.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	public String getCite() {
		return getDQ().getHtmlCite();
	}
	
	public void setCite(String cite) {
		getDQ().setHtmlCite(cite);
		onAttrChange(EHtmlAttr.cite, cite);
	}

	private DQ getDQ() {
		return (DQ) getDNode();
	}

}
