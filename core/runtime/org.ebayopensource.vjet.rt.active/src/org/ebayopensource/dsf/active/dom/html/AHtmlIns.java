/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DIns;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlIns;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlIns extends AHtmlElement implements HtmlIns {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlIns(AHtmlDocument doc, DIns node) {
		super(doc, node);
		populateScriptable(AHtmlIns.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getCite() {
		return getDIns().getHtmlCite();
	}

	public String getDateTime() {
		return getDIns().getHtmlDateTime();
	}

	public void setCite(String cite) {
		getDIns().setHtmlCite(cite);
		onAttrChange(EHtmlAttr.cite, cite);
	}

	public void setDateTime(String dateTime) {
		getDIns().setHtmlDateTime(dateTime);
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
	
	private DIns getDIns() {
		return (DIns) getDNode();
	}

}
