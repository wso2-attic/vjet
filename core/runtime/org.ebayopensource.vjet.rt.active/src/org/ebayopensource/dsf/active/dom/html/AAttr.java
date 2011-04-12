/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.w3c.dom.DOMException;
import org.w3c.dom.TypeInfo;

import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.jsnative.Attr;
import org.ebayopensource.dsf.jsnative.Element;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AAttr extends ANode implements Attr {
	
	private static final long serialVersionUID = 1L;
	
	private Element m_ownerElement;

	public AAttr(AHtmlDocument doc, DAttr attr) {
		super(doc, attr);
		populateScriptable(AAttr.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	public AAttr(ADocument doc, DAttr attr) {
		super(doc, attr);
		populateScriptable(AAttr.class, BrowserType.NONE);
	}

	public String getName() {
		return getDAttr().getName();
	}

	public Element getOwnerElement() {
		return m_ownerElement;
	}

	public TypeInfo getSchemaTypeInfo() {
		return getDAttr().getSchemaTypeInfo();
	}

	public boolean getSpecified() {
		return getDAttr().getSpecified();
	}

	public String getValue() {
		return getDAttr().getValue();
	}

	public boolean getIsId() {
		return getDAttr().isId();
	}
	
	public void setValue(String value) {
		getDAttr().setValue(value);
	}

	public void setOwnerElement(Element element) {
		m_ownerElement = element;
	}

	@Override
	public String getNodeValue() throws DOMException {
		// same as Attr.value
		return getValue();
	}

	DAttr getDAttr() {
		return (DAttr) getDNode();
	}

	@Override
	public String getNodeName() {
		// same as Attr.name
		return getName();
	}

}
