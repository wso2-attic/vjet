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
import org.ebayopensource.dsf.html.dom.DTextArea;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.dom.IDFormControl;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.HtmlTextArea;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlTextArea extends AHtmlElement implements HtmlTextArea, IDFormControl {
	
	private static final long serialVersionUID = 1L;
	private static final String SELECT_JS_METHOD = "select()";
	private static final String FOCUS_JS_METHOD = "focus()";
	
	protected AHtmlTextArea(AHtmlDocument doc, DTextArea ta) {
		super(doc, ta);
		populateScriptable(AHtmlTextArea.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public void blur() {
		dispatchEvent(EventType.BLUR.getName(), this);		
	}

	public void focus() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				browserBinding.executeDomMethod(getElement(),FOCUS_JS_METHOD);
			}
		}
	}

	public String getAccessKey() {
		return getDTextArea().getHtmlAccessKey();
	}

	public int getCols() {
		return getDTextArea().getHtmlCols();
	}

	public String getDefaultValue() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				return browserBinding.getDomAttributeValue(getDTextArea(), EHtmlAttr.defaultValue);
			}
		}
		return null;
	}

	public boolean getDisabled() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.disabled,getHtmlAttribute(EHtmlAttr.disabled));
	}

	public HtmlForm getForm() {
		return super.getFormInternal();
	}

	public String getName() {
		return getDTextArea().getHtmlName();
	}

	public boolean getReadOnly() {
		Boolean out = AHtmlHelper.booleanValueOf(EHtmlAttr.readOnly,getHtmlAttribute(EHtmlAttr.readOnly));
		if(!out){
			out = AHtmlHelper.booleanValueOf(EHtmlAttr.readonly,getHtmlAttribute(EHtmlAttr.readonly));
		}
		return out;
	}

	public int getRows() {
		return getDTextArea().getHtmlRows();
	}

	public int getTabIndex() {
		return getDTextArea().getHtmlTabIndex();
	}

	public String getType() {
		return getDTextArea().getHtmlType();
	}

	public String getValue() {
		return getDTextArea().getHtmlExtValue();
	}

	public void select() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				browserBinding.executeDomMethod(getElement(), SELECT_JS_METHOD);
			}
		}
	}

	public void setAccessKey(String accessKey) {
		getDTextArea().setHtmlAccessKey(accessKey);
		onAttrChange(EHtmlAttr.accesskey, accessKey);
	}

	public void setCols(int cols) {
		getDTextArea().setHtmlCols(cols);
		onAttrChange(EHtmlAttr.cols, String.valueOf(cols));
	}

	public void setDefaultValue(String value) {
		getDTextArea().setAttribute(EHtmlAttr.defaultvalue,value);
	}

	public void setDisabled(boolean disabled) {
		setHtmlAttribute(EHtmlAttr.disabled, disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
	}

	public void setName(String name) {
		getDTextArea().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}
	
	@Override
	public void setInnerHTML(String html) {
		setValue(html==null?"":html);
	}
	
	@Override
	public String getInnerHTML() {
		return getValue();
	}

	public void setReadOnly(boolean readOnly) {
		setHtmlAttribute(EHtmlAttr.readOnly, readOnly);
		onAttrChange(EHtmlAttr.readOnly, readOnly);
	}

	public void setRows(int rows) {
		getDTextArea().setHtmlRows(rows);
		onAttrChange(EHtmlAttr.rows, String.valueOf(rows));
	}

	public void setTabIndex(int tabIndex) {
		getDTextArea().setHtmlTabIndex(tabIndex);
		onAttrChange(EHtmlAttr.tabindex, String.valueOf(tabIndex));
	}

	public void setValue(String value) {
		getDTextArea().setHtmlExtValue(value);
		onElementChange();
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
	
	// Since property name is 'onchange', Rhino invokes this method.
	public Object getOnchange() {
		return getOnChange();
	}
	
	// For Rhino
	public void setOnchange(Object functionRef) {
		setOnChange(functionRef);
	}
	
	// Since property name is 'onselect', Rhino invokes this method.
	public Object getOnselect() {
		return getOnSelect();
	}
	
	// For Rhino
	public void setOnselect(Object functionRef) {
		setOnSelect(functionRef);
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
	
	private DTextArea getDTextArea() {
		return (DTextArea) getDNode();
	}

	private BaseHtmlElement getElement() {
		return (BaseHtmlElement) getDNode();
	}

}
