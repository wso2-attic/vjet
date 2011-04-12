/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DMap;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlMap;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.mozilla.mod.javascript.Scriptable;

public class AHtmlMap extends AHtmlElement implements HtmlMap {

	private static final long serialVersionUID = 1L;
	
	private AHtmlCollection m_areas;
	
	protected AHtmlMap(AHtmlDocument doc, DMap node) {
		super(doc, node);
		populateScriptable(AHtmlMap.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public HtmlCollection getAreas() {
		if (m_areas == null) {
			m_areas = new AHtmlCollection(this, AHtmlCollection.AREA);
		}
		return m_areas;
	}

	public String getName() {
		return getDMap().getHtmlName();
	}

	public void setName(String name) {
		getDMap().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}
	
	@Override
	public Object get(int index, Scriptable start) {
		Object obj = null;
		if ((obj = findAreaObject(index)) != null)
			return obj;
		obj = super.get(index, start);
		return obj;
	}

	private Object findAreaObject(int index) {
		return getAreas().item(index);
	}
	
	@Override
	public Object get(String name, Scriptable start) {
		Object obj = null;
		if ((obj = findAreaObject(name)) != null)
			return obj;
		obj = super.get(name, start);
		return obj;
	}
	
	// Since property name is 'onkeydown', Rhino invokes this method.
	public Object getOnkeydown() {
		return getOnKeyDown();
	}
	
	// For Rhino
	public void setOnkeydown(Object functionRef) {
		setOnKeyDown(functionRef);
	}
	
	// Since property name is 'onkeypress', Rhino invokes this method.
	public Object getOnkeypress() {
		return getOnKeyPress();
	}
	
	// For Rhino
	public void setOnkeypress(Object functionRef) {
		setOnKeyPress(functionRef);
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnkeyup() {
		return getOnKeyUp();
	}
	
	// For Rhino
	public void setOnkeyup(Object functionRef) {
		setOnKeyUp(functionRef);
	}
	
	// Since property name is 'onclick', Rhino invokes this method.
	public Object getOnclick() {
		return getOnClick();
	}
	
	// For Rhino
	public void setOnclick(Object functionRef) {
		setOnClick(functionRef);
	}
	
	// Since property name is 'ondblclick', Rhino invokes this method.
	public Object getOndblclick() {
		return getOnDblClick();
	}
	
	// For Rhino
	public void setOndblclick(Object functionRef) {
		setOnDblClick(functionRef);
	}
	
	// Since property name is 'onmousedown', Rhino invokes this method.
	public Object getOnmousedown() {
		return getOnMouseDown();
	}
	
	// For Rhino
	public void setOnmousedown(Object functionRef) {
		setOnMouseDown(functionRef);
	}
	
	// Since property name is 'onmousemove', Rhino invokes this method.
	public Object getOnmousemove() {
		return getOnMouseMove();
	}
	
	// For Rhino
	public void setOnmousemove(Object functionRef) {
		setOnMouseMove(functionRef);
	}
	
	// Since property name is 'onmouseout', Rhino invokes this method.
	public Object getOnmouseout() {
		return getOnMouseOut();
	}
	
	// For Rhino
	public void setOnmouseout(Object functionRef) {
		setOnMouseOut(functionRef);
	}
	
	// Since property name is 'onmouseover', Rhino invokes this method.
	public Object getOnmouseover() {
		return getOnMouseOver();
	}
	
	// For Rhino
	public void setOnmouseover(Object functionRef) {
		setOnMouseOver(functionRef);
	}
	
	// Since property name is 'onmouseup', Rhino invokes this method.
	public Object getOnmouseup() {
		return getOnMouseUp();
	}
	
	// For Rhino
	public void setOnmouseup(Object functionRef) {
		setOnMouseUp(functionRef);
	}
	
	private Object findAreaObject(String name) {
		HtmlCollection elements = getAreas();
		for (int i = 0; i < elements.getLength(); i++) {
			AHtmlArea area = (AHtmlArea) elements.item(i);
			if (area.getNodeName().equalsIgnoreCase(name)) {
				return area;
			}
		}
		return null;
	}
	
	private DMap getDMap() {
		return (DMap) getDNode();
	}

}
