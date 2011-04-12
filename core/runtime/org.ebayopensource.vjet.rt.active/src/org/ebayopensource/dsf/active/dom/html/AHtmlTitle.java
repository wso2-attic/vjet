/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DTitle;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlTitle;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlTitle extends AHtmlElement implements HtmlTitle {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlTitle(AHtmlDocument doc, DTitle title) {
		super(doc, title);
		populateScriptable(AHtmlTitle.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getText() {
		return getDTitle().getHtmlText();
	}

	public void setText(String text) {
		getDTitle().setHtmlText(text);
		onAttrChange(EHtmlAttr.text, text);
	}
	
	private DTitle getDTitle() {
		return (DTitle) getDNode();
	}

}
