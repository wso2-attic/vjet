/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DObject;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.Document;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.HtmlObject;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlObject extends AHtmlElement implements HtmlObject {

	private static final long serialVersionUID = 1L;
	
	private Document m_contentDocument;

	protected AHtmlObject(AHtmlDocument doc, DObject node) {
		super(doc, node);
		populateScriptable(AHtmlObject.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}


	public String getAlign() {
		return getDObject().getHtmlAlign();
	}

	public String getArchive() {
		return getDObject().getHtmlArchive();
	}

	public String getBorder() {
		return getDObject().getHtmlBorder();
	}

	public String getCode() {
		return getDObject().getHtmlCode();
	}

	public String getCodeBase() {
		return getDObject().getHtmlCodeBase();
	}

	public String getCodeType() {
		return getDObject().getHtmlCodeType();
	}
	
	public Document getContentDocument() {
		return m_contentDocument;
	}

	public String getData() {
		return getDObject().getHtmlData();
	}

	public boolean getDeclare() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.declare,getHtmlAttribute(EHtmlAttr.declare));
	}
	
//	public AHtmlDocument getContentDocument() {
//		return m_contentDocument;
//	}

	public HtmlForm getForm() {
		return super.getFormInternal();
	}

	public String getHeight() {
		return getDObject().getHtmlHeight();
	}

	public int getHspace() {
		try {
			return Integer.valueOf(getDObject().getHtmlHspace());
		}
		catch(Exception e){
			return 0;
		}
	}

	public String getName() {
		return getDObject().getHtmlName();
	}

	public String getStandby() {
		return getDObject().getHtmlStandby();
	}

	public int getTabIndex() {
		return getDObject().getHtmlTabIndex();
	}

	public String getType() {
		return getDObject().getHtmlType();
	}

	public String getUseMap() {
		return getDObject().getHtmlUseMap();
	}

	public int getVspace() {
		try {
			return Integer.valueOf(getDObject().getHtmlVspace());
		}
		catch(Exception e){
			return 0;
		}
	}

	public String getWidth() {
		return getDObject().getHtmlWidth();
	}

	public void setAlign(String align) {
		getDObject().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setArchive(String archive) {
		getDObject().setHtmlArchive(archive);
		onAttrChange(EHtmlAttr.archive, archive);
	}

	public void setBorder(String border) {
		getDObject().setHtmlBorder(border);
		onAttrChange(EHtmlAttr.border, border);
	}

	public void setCode(String code) {
		getDObject().setHtmlCode(code);
		onAttrChange(EHtmlAttr.code, code);
	}

	public void setCodeBase(String codeBase) {
		getDObject().setHtmlCodeBase(codeBase);
		onAttrChange(EHtmlAttr.codebase, codeBase);
	}

	public void setCodeType(String type) {
		getDObject().setHtmlCodeType(type);
		onAttrChange(EHtmlAttr.codetype, type);
	}

	public void setData(String data) {
		getDObject().setHtmlData(data);
		onAttrChange(EHtmlAttr.data, data);
	}

	public void setDeclare(boolean declare) {
		setHtmlAttribute(EHtmlAttr.declare, declare);
		onAttrChange(EHtmlAttr.declare, declare);
	}

	public void setHeight(String height) {
		getDObject().setHtmlHeight(height);
		onAttrChange(EHtmlAttr.height, height);
	}

	public void setHspace(int hspace) {
		getDObject().setHtmlHspace(hspace);
		onAttrChange(EHtmlAttr.hspace, String.valueOf(hspace));
	}

	public void setName(String name) {
		getDObject().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setStandby(String standby) {
		getDObject().setHtmlStandby(standby);
		onAttrChange(EHtmlAttr.standby, standby);
	}

	public void setTabIndex(int tabIndex) {
		getDObject().setHtmlTabIndex(tabIndex);
		onAttrChange(EHtmlAttr.tabindex, String.valueOf(tabIndex));
	}

	public void setType(String type) {
		getDObject().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, type);
	}

	public void setUseMap(String useMap) {
		getDObject().setHtmlUseMap(useMap);
		onAttrChange(EHtmlAttr.usemap, useMap);
	}

	public void setVspace(int vspace) {
		getDObject().setHtmlVspace(vspace);
		onAttrChange(EHtmlAttr.vspace, String.valueOf(vspace));
	}

	public void setWidth(String width) {
		getDObject().setHtmlWidth(width);
		onAttrChange(EHtmlAttr.width, width);
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
	
	// Since property name is 'onresize', Rhino invokes this method.
	public Object getOnresize() {
		return getOnResize();
	}
	
	// For Rhino
	public void setOnresize(Object functionRef) {
		setOnResize(functionRef);
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
	
	private DObject getDObject() {
		return (DObject) getDNode();
	}

	void setContentDocument(Document document) {
		m_contentDocument = document;
	}
}
