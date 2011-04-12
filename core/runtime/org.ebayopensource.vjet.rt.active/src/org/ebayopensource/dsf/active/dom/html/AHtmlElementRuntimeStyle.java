/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.ECssAttr;

public class AHtmlElementRuntimeStyle extends AHtmlElementStyle {

	private static final long serialVersionUID = 1L;
	
	AHtmlElementRuntimeStyle(AHtmlElement elem) {
		super(elem);
	}
	
	@Override
	protected void update(ECssAttr attr, String value, boolean isString) {
		String name = attr.domName();
		if (isString) {
			value = "'" + value + "'";
		}
		IBrowserBinding browserBinding = getBrowserBinding();
		if(browserBinding != null){
			browserBinding.setElementRuntimeStyleValue((BaseHtmlElement)m_elem.getDNode(), name, value);			
		}
	}
	
	@Override
	protected String getCssValue(ECssAttr attr) {
		IBrowserBinding browserBinding = getBrowserBinding();
		if(browserBinding != null){
			return browserBinding.getElementRuntimeStyleValue((BaseHtmlElement)m_elem.getDNode(), attr.domName());			
		}
		return "";
	}
	
	@Override
	public Object getPropertyValue(String name) {
		ECssAttr attr = ECssAttr.findByCssName(name);
		if (attr != null) {
			return getCssValue(attr);
		}
		return null;
	}
	
	private IBrowserBinding getBrowserBinding() {
		AHtmlDocument doc = m_elem.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		return doc.getBrowserBinding();
	}
}
