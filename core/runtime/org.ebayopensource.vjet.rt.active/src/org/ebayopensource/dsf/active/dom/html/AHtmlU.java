/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DU;
import org.ebayopensource.dsf.jsnative.HtmlU;

public class AHtmlU extends AHtmlElement implements HtmlU {

	private static final long serialVersionUID = 1L;

	protected AHtmlU(DU br) {
		this(null, br);
	}
	
	protected AHtmlU(AHtmlDocument doc, DU br) {
		super(doc, br);
	}
}
