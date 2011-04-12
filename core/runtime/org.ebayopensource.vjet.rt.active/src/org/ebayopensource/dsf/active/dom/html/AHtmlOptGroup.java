/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DOptGroup;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlOptGroup;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlOptGroup extends AHtmlElement implements HtmlOptGroup {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlOptGroup(AHtmlDocument doc, DOptGroup node) {
		super(doc, node);
		populateScriptable(AHtmlOptGroup.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}	

	public boolean getDisabled() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.disabled,getHtmlAttribute(EHtmlAttr.disabled));
	}

	public String getLabel() {
		return getDOptGroup().getHtmlLabel();
	}

	public void setDisabled(boolean disabled) {
		setHtmlAttribute(EHtmlAttr.disabled, disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
	}

	public void setLabel(String label) {
		getDOptGroup().setHtmlLabel(label);
		onAttrChange(EHtmlAttr.label, label);
	}
	
	private DOptGroup getDOptGroup() {
		return (DOptGroup) getDNode();
	}

}
