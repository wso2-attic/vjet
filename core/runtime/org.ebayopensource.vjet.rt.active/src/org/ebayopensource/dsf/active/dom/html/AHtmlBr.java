/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DBr;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlBr;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlBr extends AHtmlElement implements HtmlBr {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlBr(AHtmlDocument doc, DBr br) {
		super(doc, br);
		populateScriptable(AHtmlBr.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getClear() {
		return getDBr().getHtmlClear();
	}

	public void setClear(String clear) {
		getDBr().setHtmlClear(clear);
		onAttrChange(EHtmlAttr.clear, clear);
	}
	
	private DBr getDBr() {
		return (DBr) getDNode();
	}

}
