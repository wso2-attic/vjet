/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DMenu;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlMenu;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlMenu extends AHtmlElement implements HtmlMenu {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlMenu(AHtmlDocument doc, DMenu node) {
		super(doc, node);
		populateScriptable(AHtmlMenu.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public boolean getCompact() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.compact,getHtmlAttribute(EHtmlAttr.compact));
	}

	public void setCompact(boolean compact) {
		setHtmlAttribute(EHtmlAttr.compact, compact);
		onAttrChange(EHtmlAttr.compact, compact);
	}
}
