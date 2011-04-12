/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DCaption;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlTableCaption;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlTableCaption extends AHtmlElement implements HtmlTableCaption {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlTableCaption(AHtmlDocument doc, DCaption node) {
		super(doc, node);
		populateScriptable(AHtmlTableCaption.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAlign() {
		return getDCaption().getHtmlAlign();
	}

	public void setAlign(String align) {
		getDCaption().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}
	
	private DCaption getDCaption() {
		return (DCaption) getDNode();
	}

}
