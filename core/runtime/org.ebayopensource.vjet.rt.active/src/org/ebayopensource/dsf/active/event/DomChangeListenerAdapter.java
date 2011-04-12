/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.event;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;

public class DomChangeListenerAdapter implements IDomChangeListener {

	private List<IDomChangeListener> m_listeners = 
		new ArrayList<IDomChangeListener>(2);
	
	public void add(IDomChangeListener listner) {
		m_listeners.add(listner);
	}
	
	public void remove(IDomChangeListener listner) {
		m_listeners.remove(listner);
	}
	
	public void onAppendChild(Node child) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onAppendChild(child);
		}
	}

	public void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, boolean value) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onAttrChange(elem, attr, value);
		}
	}
	
	public void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, int value) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onAttrChange(elem, attr, value);
		}
	}
	
	public void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, double value) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onAttrChange(elem, attr, value);
		}
	}

	public void onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, String value) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onAttrChange(elem, attr, value);
		}
	}

	public void onClassNameChange(BaseHtmlElement elem, String className) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onClassNameChange(elem, className);
		}
	}

	public void onElementChange(BaseHtmlElement elem) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onElementChange(elem);
		}
	}

	public void onHeightChange(BaseHtmlElement node, int height) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onHeightChange(node, height);
		}
	}

	public void onInsert(Node newNode, Node refNode, boolean insertBefore) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onInsert(newNode, refNode, insertBefore);
		}
	}

	public void onRemove(Node node) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onRemove(node);
		}
	}

	public void onStyleChange(BaseHtmlElement elem, String name, String value) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onStyleChange(elem, name, value);
		}
	}

	public void onValueChange(BaseHtmlElement elem, String value) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onValueChange(elem, value);
		}
	}

	public void onWidthChange(BaseHtmlElement node, int width) {
		for (int i = 0; i < m_listeners.size(); i++) {
			m_listeners.get(i).onWidthChange(node, width);
		}
	}
}
