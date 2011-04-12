/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DCenter;
import org.ebayopensource.dsf.jsnative.HtmlCenter;

public class AHtmlCenter extends AHtmlElement implements HtmlCenter {

	private static final long serialVersionUID = 1L;

	protected AHtmlCenter(DCenter c) {
		this(null, c);
	}
	
	protected AHtmlCenter(AHtmlDocument doc, DCenter c) {
		super(doc, c);
	}
}
