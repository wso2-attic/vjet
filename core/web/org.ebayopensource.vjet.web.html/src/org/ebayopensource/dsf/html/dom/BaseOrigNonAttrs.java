/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

abstract class BaseOrigNonAttrs extends BaseAttrsHtmlElement {
	protected BaseOrigNonAttrs(final HtmlTypeEnum type){
		this(null, type);
	}
	protected BaseOrigNonAttrs(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
}
