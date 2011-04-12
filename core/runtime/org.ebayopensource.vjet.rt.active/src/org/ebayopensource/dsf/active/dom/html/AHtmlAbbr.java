/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DAbbr;
import org.ebayopensource.dsf.jsnative.HtmlAbbr;

public class AHtmlAbbr extends AHtmlElement implements HtmlAbbr {

	private static final long serialVersionUID = 1L;

	protected AHtmlAbbr(DAbbr a) {
		this(null, a);
	}
	
	protected AHtmlAbbr(AHtmlDocument doc, DAbbr a) {
		super(doc, a);
	}
}
