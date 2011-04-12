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

/**
 * This is a synthesized style type for browser dom element.
 * It is not a true node or element. It acts as a proxy to
 * set style on AHtmlElement.
 */
public class AHtmlElementCurrentStyle extends AHtmlElementStyle {

	private static final long serialVersionUID = 1L;
	
	AHtmlElementCurrentStyle(AHtmlElement elem) {
		super(elem);
	}
	
	@Override
	protected void update(ECssAttr attr, String value, boolean isString) {
		throw new RuntimeException("currentStyle should be read-only");
	}
	
	@Override
	protected String getCssValue(ECssAttr attr) {
		IBrowserBinding browserBinding = getBrowserBinding();
		if(browserBinding != null){
			return browserBinding.getElementCurrentStyleValue
				((BaseHtmlElement)m_elem.getDNode(), attr.domName());			
		}
		return super.getCssValue(attr);
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
