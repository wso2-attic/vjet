/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DApplet;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlApplet;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlApplet extends AHtmlElement implements HtmlApplet {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlApplet(AHtmlDocument doc, DApplet node) {
		super(doc, node);
		populateScriptable(AHtmlApplet.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAlign() {
		return getDApplet().getHtmlAlign();
	}

	public String getAlt() {
		return getDApplet().getHtmlAlt();
	}

	public String getArchive() {
		return getDApplet().getHtmlArchive();
	}

	public String getCode() {
		return getDApplet().getHtmlCode();
	}

	public String getCodeBase() {
		return getDApplet().getHtmlCodeBase();
	}

	public String getHeight() {
		return getDApplet().getHtmlHeight();
	}

	public int getHspace() {
		return Integer.valueOf(getDApplet().getHtmlHspace());
	}

	public String getName() {
		return getDApplet().getHtmlName();
	}

	public String getObject() {
		return getDApplet().getHtmlObject();
	}

	public int getVspace() {
		return Integer.valueOf(getDApplet().getHtmlVspace());
	}

	public String getWidth() {
		return getDApplet().getHtmlWidth();
	}

	public void setAlign(String align) {
		getDApplet().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setAlt(String alt) {
		getDApplet().setHtmlAlt(alt);
		onAttrChange(EHtmlAttr.alt, alt);
	}

	public void setArchive(String archive) {
		getDApplet().setHtmlArchive(archive);
		onAttrChange(EHtmlAttr.archive, archive);
	}

	public void setCode(String code) {
		getDApplet().setHtmlCode(code);
		onAttrChange(EHtmlAttr.code, code);
	}

	public void setCodeBase(String codeBase) {
		getDApplet().setHtmlCodeBase(codeBase);
		onAttrChange(EHtmlAttr.codebase, codeBase);
	}

	public void setHeight(String height) {
		getDApplet().setHtmlHeight(height);
		onAttrChange(EHtmlAttr.height, height);
	}

	public void setHspace(int hspace) {
		getDApplet().setHtmlHspace(hspace);
		onAttrChange(EHtmlAttr.hspace, String.valueOf(hspace));
	}

	public void setName(String name) {
		getDApplet().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setObject(String object) {
		getDApplet().setHtmlObject(object);
		onAttrChange(EHtmlAttr.object, object);
	}

	public void setVspace(int vspace) {
		getDApplet().setHtmlVspace(vspace);
		onAttrChange(EHtmlAttr.vspace, String.valueOf(vspace));
	}

	public void setWidth(String width) {
		getDApplet().setHtmlWidth(width);
		onAttrChange(EHtmlAttr.width, width);
	}
	
	private DApplet getDApplet() {
		return (DApplet) getDNode();
	}

}
