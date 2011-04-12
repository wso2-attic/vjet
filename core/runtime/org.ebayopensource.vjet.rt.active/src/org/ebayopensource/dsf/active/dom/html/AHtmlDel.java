/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DDel;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlDel;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlDel extends AHtmlElement implements HtmlDel {

	private static final long serialVersionUID = 1L;

	protected AHtmlDel(AHtmlDocument doc, DDel node) {
		super(doc, node);
		populateScriptable(AHtmlDel.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getCite() {
		return getDDel().getHtmlCite();
	}

	public String getDateTime() {
		return getDDel().getHtmlDateTime();
	}

	public void setCite(String cite) {
		getDDel().setHtmlCite(cite);
		onAttrChange(EHtmlAttr.cite, cite);
	}

	public void setDateTime(String dateTime) {
		getDDel().setHtmlDateTime(dateTime);
		onAttrChange(EHtmlAttr.datetime, dateTime);
	}
	
	// Since property name is 'onblur', Rhino invokes this method.
	public Object getOnblur() {
		return getOnBlur();
	}
	
	// Since property name is 'onfocus', Rhino invokes this method.
	public Object getOnfocus() {
		return getOnFocus();
	}
	
	// For Rhino
	public void setOnblur(Object functionRef) {
		setOnBlur(functionRef);
	}
	
	// For Rhino
	public void setOnfocus(Object functionRef) {
		setOnFocus(functionRef);
	}
	
	// Since property name is 'onkeydown', Rhino invokes this method.
	public Object getOnkeydown() {
		return getOnKeyDown();
	}
	
	// For Rhino
	public void setOnkeydown(Object functionRef) {
		setOnKeyDown(functionRef);
	}
	
	// Since property name is 'onkeypress', Rhino invokes this method.
	public Object getOnkeypress() {
		return getOnKeyPress();
	}
	
	// For Rhino
	public void setOnkeypress(Object functionRef) {
		setOnKeyPress(functionRef);
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnkeyup() {
		return getOnKeyUp();
	}
	
	// For Rhino
	public void setOnkeyup(Object functionRef) {
		setOnKeyUp(functionRef);
	}
	
	private DDel getDDel() {
		return (DDel) getDNode();
	}

}
