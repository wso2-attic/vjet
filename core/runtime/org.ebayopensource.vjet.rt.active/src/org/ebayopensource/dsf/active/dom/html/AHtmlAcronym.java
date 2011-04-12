/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DAcronym;
import org.ebayopensource.dsf.jsnative.HtmlAcronym;

public class AHtmlAcronym extends AHtmlElement implements HtmlAcronym {

	private static final long serialVersionUID = 1L;

	protected AHtmlAcronym(DAcronym a) {
		this(null, a);
	}
	
	protected AHtmlAcronym(AHtmlDocument doc, DAcronym a) {
		super(doc, a);
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
}
