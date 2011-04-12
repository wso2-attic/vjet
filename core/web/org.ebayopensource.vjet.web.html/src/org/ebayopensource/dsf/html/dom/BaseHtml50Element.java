/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

/**
 * BaseHtml40Elements contains all the original HTML 4.0 Elements
 * The BaseHtml50Elements are purely new elements for 5.0
 */
abstract class BaseHtml50Element extends BaseAttrsHtmlElement { // BaseAttrsHtmlElement {
	//
	// Constructor(s)
	//
	BaseHtml50Element(final HtmlTypeEnum type) {
		this(null, type);
	}
	BaseHtml50Element(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
}
