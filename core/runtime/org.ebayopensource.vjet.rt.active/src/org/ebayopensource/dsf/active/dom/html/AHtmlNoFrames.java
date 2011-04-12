/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DNoFrames;
import org.ebayopensource.dsf.jsnative.HtmlNoFrames;

public class AHtmlNoFrames extends AHtmlElement implements HtmlNoFrames {

	private static final long serialVersionUID = 1L;

	protected AHtmlNoFrames(DNoFrames node) {
		this(null, node);
	}
	
	protected AHtmlNoFrames(AHtmlDocument doc, DNoFrames node) {
		super(doc, node);
	}
}
