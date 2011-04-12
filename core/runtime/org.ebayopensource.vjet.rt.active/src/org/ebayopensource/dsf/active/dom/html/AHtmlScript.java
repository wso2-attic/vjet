/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlScript;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlScript extends AHtmlElement implements HtmlScript {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlScript(AHtmlDocument doc, DScript node) {
		super(doc, node);
		populateScriptable(AHtmlScript.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getCharset() {
		return getDScript().getHtmlCharset();
	}

	public boolean getDefer() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.defer,getHtmlAttribute(EHtmlAttr.defer));
	}

	public String getSrc() {
		return getDScript().getHtmlSrc();
	}

	public String getText() {
		return getDScript().getHtmlText();
	}

	public String getType() {
		return getDScript().getHtmlType();
	}

	public void setCharset(String charset) {
		getDScript().setHtmlCharset(charset);
		onAttrChange(EHtmlAttr.charset, charset);
	}

	public void setDefer(boolean defer) {
		setHtmlAttribute(EHtmlAttr.defer, defer);
		onAttrChange(EHtmlAttr.defer, defer);
	}


	public void setSrc(String src) {
		getDScript().setHtmlSrc(src);
		onAttrChange(EHtmlAttr.src, src);
	}

	public void setText(String text) {
		getDScript().setHtmlText(text);
		onAttrChange(EHtmlAttr.text, text);
	}

	public void setType(String type) {
		getDScript().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, type);
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	// Since property name is 'onunload', Rhino invokes this method.
	public Object getOnunload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnunload(Object functionRef) {
		setOnLoad(functionRef);
	}

	private DScript getDScript() {
		return (DScript) getDNode();
	}

}
