/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DSamp;
import org.ebayopensource.dsf.jsnative.HtmlSamp;

public class AHtmlSamp extends AHtmlElement implements HtmlSamp {

	private static final long serialVersionUID = 1L;

	protected AHtmlSamp(DSamp node) {
		this(null, node);
	}
	
	protected AHtmlSamp(AHtmlDocument doc, DSamp node) {
		super(doc, node);
	}
}
