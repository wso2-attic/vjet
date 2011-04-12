/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DEmbed;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlEmbed;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlEmbed extends AHtmlElement implements HtmlEmbed {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlEmbed(AHtmlDocument doc, DEmbed node) {
		super(doc, node);
		populateScriptable(AHtmlEmbed.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAccessKey() {
		return getDEmbed().getHtmlAccessKey();
	}

	public String getAlign() {
		return getDEmbed().getHtmlAlign();
	}

	public String getAtomicSelection() {
		return getDEmbed().getHtmlAtomicSelection();
	}

	public String getHeight() {
		return getDEmbed().getHtmlHeight();
	}

	public String getHidden() {
		return getDEmbed().getHtmlHidden();
	}

	public String getHideFocus() {
		return getDEmbed().getHtmlHideFocus();
	}

	public String getLanguage() {
		return getDEmbed().getHtmlLanguage();
	}

	public String getName() {
		return getDEmbed().getHtmlName();
	}

	public String getPluginsPage() {
		return getDEmbed().getHtmlPluginsPage();
	}

	public String getSrc() {
		return getDEmbed().getHtmlSrc();
	}

	public String getType() {
		return getDEmbed().getHtmlType();
	}

	public String getUnits() {
		return getDEmbed().getHtmlUnits();
	}

	public String getUnselectable() {
		return getDEmbed().getHtmlUnselectable();
	}

	public String getWidth() {
		return getDEmbed().getHtmlWidth();
	}

	public void setAccessKey(String accessKey) {
		getDEmbed().setHtmlAccessKey(accessKey);
		onAttrChange(EHtmlAttr.accesskey, accessKey);
	}
	
	public void setAlign(String align) {
		getDEmbed().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setAtomicSelection(String atomic) {
		getDEmbed().setHtmlAtomicSelection(atomic);
		onAttrChange(EHtmlAttr.atomicselection, atomic);
	}

	public void setHeight(String height) {
		getDEmbed().setHtmlHeight(height);
		onAttrChange(EHtmlAttr.height, height);
	}

	public void setHidden(String hidden) {
		getDEmbed().setHtmlHidden(hidden);
		onAttrChange(EHtmlAttr.hidden, hidden);
	}

	public void setHideFocus(String hidefocus) {
		getDEmbed().setHtmlHideFocus(hidefocus);
	}

	public void setLanguage(String language) {
		getDEmbed().setHtmlLanguage(language);
		onAttrChange(EHtmlAttr.language, language);
	}

	public void setName(String name) {
		getDEmbed().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setPluginsPage(String pluginspage) {
		getDEmbed().setHtmlPluginsPage(pluginspage);
		onAttrChange(EHtmlAttr.pluginspage, pluginspage);
	}

	public void setSrc(String src) {
		getDEmbed().setHtmlSrc(src);
		onAttrChange(EHtmlAttr.src, src);
	}

	public void setType(String type) {
		getDEmbed().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, type);
	}

	public void setUnits(String units) {
		getDEmbed().setHtmlUnits(units);
		onAttrChange(EHtmlAttr.units, units);
	}

	public void setUnselectable(String unselectable) {
		getDEmbed().setHtmlUnselectable(unselectable);
		onAttrChange(EHtmlAttr.unselectable, unselectable);
	}

	public void setWidth(String width) {
		getDEmbed().setHtmlWidth(width);
		onAttrChange(EHtmlAttr.width, width);
	}
	
	private DEmbed getDEmbed() {
		return (DEmbed) getDNode();
	}
}
