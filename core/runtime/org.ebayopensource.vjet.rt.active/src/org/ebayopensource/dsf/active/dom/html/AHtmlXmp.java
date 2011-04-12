/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DXmp;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlXmp;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlXmp extends AHtmlElement implements HtmlXmp {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlXmp(AHtmlDocument doc, DXmp node) {
		super(doc, node);
		populateScriptable(AHtmlXmp.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAccessKey() {
		return getDXmp().getHtmlAccessKey();
	}

	public String getAtomicSelection() {
		return getDXmp().getHtmlAtomicSelection();
	}

	public String getContentEditable() {
		return getDXmp().getHtmlContentEditable();
	}

	public String getHideFocus() {
		return getDXmp().getHtmlHideFocus();
	}

	public String getLanguage() {
		return getDXmp().getHtmlLanguage();
	}

	public int getTabIndex() {
		return getDXmp().getHtmlTabIndex();
	}

	public String getUnselectable() {
		return getDXmp().getHtmlUnselectable();
	}

	public void setAccessKey(String accessKey) {
		getDXmp().setHtmlAccessKey(accessKey);
		onAttrChange(EHtmlAttr.accesskey, accessKey);
	}

	public void setAtomicSelection(String atomic) {
		getDXmp().setHtmlAtomicSelection(atomic);
		onAttrChange(EHtmlAttr.atomicselection, atomic);
	}

	public void setContentEditable(String contenteditable) {
		getDXmp().setHtmlContentEditable(contenteditable);
		onAttrChange(EHtmlAttr.contenteditable, contenteditable);
	}

	public void setHideFocus(String hidefocus) {
		getDXmp().setHtmlHideFocus(hidefocus);
		onAttrChange(EHtmlAttr.hidefocus, hidefocus);
	}

	public void setLanguage(String language) {
		getDXmp().setHtmlLanguage(language);
		onAttrChange(EHtmlAttr.language, language);
	}

	public void setTabIndex(String tabindex) {
		getDXmp().setHtmlTabIndex(tabindex);
		onAttrChange(EHtmlAttr.tabindex, tabindex);
	}

	public void setUnselectable(String unselectable) {
		getDXmp().setHtmlUnselectable(unselectable);
		onAttrChange(EHtmlAttr.unselectable, unselectable);
	}
	
	private DXmp getDXmp() {
		return (DXmp) getDNode();
	}
}
