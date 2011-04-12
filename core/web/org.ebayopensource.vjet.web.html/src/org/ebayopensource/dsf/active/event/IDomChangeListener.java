/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.event;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;

public interface IDomChangeListener {
	
	void onElementChange(BaseHtmlElement elem);
	
	void onAppendChild(Node child);
	
	void onValueChange(BaseHtmlElement elem, final String value);
	
	void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, boolean value);
	void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, int value);
	void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, double value);
	void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, String value);
	
	void onStyleChange(BaseHtmlElement elem, String name, String value);
	
	void onWidthChange(BaseHtmlElement node, int width);
	
	void onHeightChange(BaseHtmlElement node, int height);
	
	void onClassNameChange(BaseHtmlElement elem, String className);
	
	void onInsert(Node newNode, final Node refNode, boolean insertBefore);
	
	void onRemove(Node node);
}
