/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.dom.deprecated.DRt_IE;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative_deprecated.HtmlRt_IE;

public class AHtmlRt_IE extends AHtmlElement implements HtmlRt_IE {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlRt_IE(AHtmlDocument doc, DRt_IE node) {
		super(doc, node);
		populateScriptable(AHtmlRt_IE.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAccessKey() {
		return getDRt().getHtmlAccessKey();
	}

	public String getAtomicSelection() {
		return getDRt().getHtmlAtomicSelection();
	}

	public String getContentEditable() {
		return getDRt().getHtmlContentEditable();
	}

	public String getHideFocus() {
		return getDRt().getHtmlHideFocus();
	}

	public String getLanguage() {
		return getDRt().getHtmlLanguage();
	}

	public String getName() {
		return getDRt().getHtmlName();
	}

	public int getTabIndex() {
		return getDRt().getHtmlTabIndex();
	}

	public String getUnselectable() {
		return getDRt().getHtmlUnselectable();
	}

	public void setAccessKey(String accessKey) {
		getDRt().setHtmlAccessKey(accessKey);
		onAttrChange(EHtmlAttr.accesskey, accessKey);
	}

	public void setAtomicSelection(String atomic) {
		getDRt().setHtmlAtomicSelection(atomic);
		onAttrChange(EHtmlAttr.atomicselection, atomic);
	}

	public void setContentEditable(String contenteditable) {
		getDRt().setHtmlContentEditable(contenteditable);
		onAttrChange(EHtmlAttr.contenteditable, contenteditable);
	}

	public void setHideFocus(String hidefocus) {
		getDRt().setHtmlHideFocus(hidefocus);
		onAttrChange(EHtmlAttr.hidefocus, hidefocus);
	}

	public void setLanguage(String language) {
		getDRt().setHtmlLanguage(language);
		onAttrChange(EHtmlAttr.language, language);
	}

	public void setName(String name) {
		getDRt().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setTabIndex(String tabindex) {
		getDRt().setHtmlTabIndex(tabindex);
		onAttrChange(EHtmlAttr.tabindex, tabindex);
	}

	public void setUnselectable(String unselectable) {
		getDRt().setHtmlUnselectable(unselectable);
		onAttrChange(EHtmlAttr.unselectable, unselectable);
	}
	
	private DRt_IE getDRt() {
		return (DRt_IE) getDNode();
	}
}
