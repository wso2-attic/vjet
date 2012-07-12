/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.ebayopensource.dsf.common.Z;

public abstract class DCommonMod extends BaseAttrsHtmlElement {

	private static final long serialVersionUID = 3905801976649625913L;

	//
	// Constructor(s)
	//
	DCommonMod(final HtmlTypeEnum type) {
		super(type);
	}
	
	DCommonMod(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
	
	//
	// API
	//
	public String getHtmlCite() {
		return getHtmlAttribute(EHtmlAttr.cite);
	}

	public DCommonMod setHtmlCite(final String cite) {
		setHtmlAttribute(EHtmlAttr.cite, cite);
		return this ;
	}

	public String getHtmlDateTime() {
		return getHtmlAttribute(EHtmlAttr.datetime);
	}

	public DCommonMod setHtmlDateTime(final String dateTime) {
		setHtmlAttribute(EHtmlAttr.datetime, dateTime);
		return this ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt(EHtmlAttr.cite.getAttributeName(), "" + getHtmlCite()) +
		Z.fmt(EHtmlAttr.datetime.getAttributeName(), getHtmlDateTime()) ;	
	}
}
