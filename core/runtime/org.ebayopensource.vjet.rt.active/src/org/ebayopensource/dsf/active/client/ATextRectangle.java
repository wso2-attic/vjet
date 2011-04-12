/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import org.ebayopensource.dsf.active.dom.html.AElement;
import org.ebayopensource.dsf.active.dom.html.AHtmlDocument;
import org.ebayopensource.dsf.active.dom.html.AHtmlElement;
import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.html.dom.ECssAttr;
import org.ebayopensource.dsf.jsnative.TextRectangle;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class ATextRectangle extends ActiveObject implements TextRectangle {

	private static final long serialVersionUID = 8944913938454122242L;
	private AHtmlDocument m_doc;
	private AElement m_elem;
	private IBrowserBinding m_browserBinding;

	private final static String GET_BOUNDING_CLIENT_RECT = "getBoundingClientRect()";

	public ATextRectangle(AHtmlDocument doc, AElement elem,
			IBrowserBinding browserBinding) {
		m_doc = doc;
		m_elem = elem;
		m_browserBinding = browserBinding;
		populateScriptable(ATextRectangle.class, doc == null ? BrowserType.IE_6P
				: doc.getBrowserType());
	}

	public float getBottom() {
		return getFloatBindingValue(ECssAttr.bottom);
	}

	public float getLeft() {
		return getFloatBindingValue(ECssAttr.left);
	}

	public float getRight() {
		return getFloatBindingValue(ECssAttr.right);
	}

	public float getTop() {
		return getFloatBindingValue(ECssAttr.top);
	}

	private float getFloatBindingValue(ECssAttr attr) {
		if (m_browserBinding != null && m_doc != null && m_elem != null) {
			String code = AHtmlHelper.getElementPath((AHtmlElement) m_elem);
			code += "." + GET_BOUNDING_CLIENT_RECT + ".";
			code += attr.cssName();
			String value = m_browserBinding.executeJs(code);
			if (value == null || value.length() == 0) {
				return 0;
			}
			try {
				return Float.parseFloat(value);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

}
