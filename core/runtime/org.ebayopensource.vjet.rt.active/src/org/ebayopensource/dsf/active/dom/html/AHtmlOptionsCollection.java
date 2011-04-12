/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlOption;
import org.ebayopensource.dsf.jsnative.HtmlOptionsCollection;
import org.mozilla.mod.javascript.Scriptable;

public class AHtmlOptionsCollection extends AHtmlCollection implements
		HtmlOptionsCollection {
	
	private static final long serialVersionUID = 1L;

	AHtmlOptionsCollection(AHtmlElement topLevel, short lookingFor) {
		super(topLevel, lookingFor);
		populateScriptable(AHtmlOptionsCollection.class, topLevel.getOwnerDocument().getBrowserType());
	}
	
    //FIXME Rhino doesn't allow overloaded mothod so currently we have no way
	// to distinguish between the following two JS code:
	// select.options.add(new Option());
	// select.options.add(new Option(), 0);
	
//	public void add(HtmlOption element) {
//		AHtmlSelect select = (AHtmlSelect) getTopLevel();
//		if (select != null) {
//			select.add(element, null);
//		}
//	}
	
	public void add(HtmlOption element, int index) {
		AHtmlSelect select = (AHtmlSelect) getTopLevel();
		if (select != null) {
			HtmlElement before = null;
			if (index != 0) {
				before = (HtmlElement) item(index);
			}
			select.add(element, before);
		}
	}
	
	public void setLength(int length) {
		AHtmlSelect select = (AHtmlSelect) getTopLevel();
		if (select != null) {
			if (length == 0) {
				AHtmlHelper.removeAllOptions(select);
			} else if (length < getLength()) {
				int index = getLength() - 1;
				while (index > length) {
					removeNode(item(index--));
				}
			}
		}
		
	}
	
	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}
	
	@Override
	public void put(String name, Scriptable start, Object value) {
		if ("length".equals(name) && value instanceof Double) {
			Double length = (Double) value;
			setLength(length.intValue());
		}
		super.put(name, start, value);
		return;
	}

}
