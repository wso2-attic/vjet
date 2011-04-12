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
import org.ebayopensource.dsf.html.dom.DInput;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.dom.IDFormControl;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.HtmlInput;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlInput extends AHtmlElement implements HtmlInput, IDFormControl {

	private static final long serialVersionUID = 1L;
	private static final String SELECT_JS_METHOD = "select()";
	private static final String FOCUS_JS_METHOD = "focus()";
	
	protected AHtmlInput(AHtmlDocument doc, DInput input) {
		super(doc, input);
		populateScriptable(AHtmlInput.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public void blur() {
		dispatchEvent(EventType.BLUR.getName(), this);
	}

	public void click() {
		dispatchEvent(EventType.CLICK.getName(), this);
	}

	public void focus() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				browserBinding.executeDomMethod(getDInput(), FOCUS_JS_METHOD);
			}
		}
	}

	public String getAccept() {
		return getDInput().getHtmlAccept();
	}

	public String getAccessKey() {
		return getDInput().getHtmlAccessKey();
	}

	public String getAlign() {
		return getDInput().getHtmlAlign();
	}

	public String getAlt() {
		return getDInput().getHtmlAlt();
	}

	public boolean getChecked() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.checked,getHtmlAttribute(EHtmlAttr.checked));
	}

	public boolean getDefaultChecked() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.defaultchecked,getHtmlAttribute(EHtmlAttr.defaultchecked));
	}

	public String getDefaultValue() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				return browserBinding.getDomAttributeValue(getDInput(), EHtmlAttr.defaultValue);
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

	public int getMaxLength() {
		return getDInput().getHtmlMaxLength();
	}

	public String getName() {
		return getDInput().getHtmlName();
	}

	public boolean getReadOnly() {
		Boolean out = AHtmlHelper.booleanValueOf(EHtmlAttr.readOnly,getHtmlAttribute(EHtmlAttr.readOnly));
		if(!out){
			out = AHtmlHelper.booleanValueOf(EHtmlAttr.readonly,getHtmlAttribute(EHtmlAttr.readonly));
		}
		return out;
	}

	public int getSize() {
		int size = 0;
		try {
			size = Integer.valueOf(getDInput().getHtmlSize());
		} catch (NumberFormatException e) {
			// NOPMD
		}
		return size;
	}

	public String getSrc() {
		return getDInput().getHtmlSrc();
	}

	public int getTabIndex() {
		return getDInput().getHtmlTabIndex();
	}

	public String getType() {
		return getDInput().getHtmlType();
	}

	public String getUseMap() {
		return getDInput().getHtmlUseMap();
	}

	public String getValue() {
		return getDInput().getHtmlValue();
	}

	public void select() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				browserBinding.executeDomMethod(getDInput(), SELECT_JS_METHOD);
			}
		}
	}

	public void setAccept(String accept) {
		getDInput().setHtmlAccept(accept);
		onAttrChange(EHtmlAttr.accept, accept);
	}

	public void setAccessKey(String accessKey) {
		getDInput().setHtmlAccessKey(accessKey);
		onAttrChange(EHtmlAttr.accesskey, accessKey);
	}

	public void setAlign(String align) {
		getDInput().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setAlt(String alt) {
		getDInput().setHtmlAlt(alt);
		onAttrChange(EHtmlAttr.alt, alt);
	}

	public void setChecked(boolean checked) {
		setHtmlAttribute(EHtmlAttr.checked, checked);
		onAttrChange(EHtmlAttr.checked, checked);
	}

	public void setDefaultChecked(boolean defaultChecked) {
		setHtmlAttribute(EHtmlAttr.defaultchecked, defaultChecked);
		onAttrChange(EHtmlAttr.defaultchecked, defaultChecked);
	}

	public void setDefaultValue(String defaultValue) {
		getDInput().setHtmlDefaultValue(defaultValue);
		onAttrChange(EHtmlAttr.defaultvalue, defaultValue);
	}

	public void setDisabled(boolean disabled) {
		setHtmlAttribute(EHtmlAttr.disabled, disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
	}

	public void setMaxLength(int maxLength) {
		getDInput().setHtmlMaxLength(maxLength);
		onAttrChange(EHtmlAttr.maxlength, String.valueOf(maxLength));
	}

	public void setName(String name) {
		getDInput().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setReadOnly(boolean readOnly) {
		setHtmlAttribute(EHtmlAttr.readOnly, readOnly);
		onAttrChange(EHtmlAttr.readOnly, readOnly);
	}

	public void setSize(int size) {
		getDInput().setHtmlSize(size);
		onAttrChange(EHtmlAttr.size, String.valueOf(size));
	}

	public void setSrc(String src) {
		getDInput().setHtmlSrc(src);
		onAttrChange(EHtmlAttr.src, src);
	}

	public void setTabIndex(int tabIndex) {
		getDInput().setHtmlTabIndex(tabIndex);
		onAttrChange(EHtmlAttr.tabindex, String.valueOf(tabIndex));
	}
	
	public void setType(String type) {
		getDInput().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, String.valueOf(type));
	}

	public void setUseMap(String useMap) {
		getDInput().setHtmlUseMap(useMap);
		onAttrChange(EHtmlAttr.usemap, useMap);
	}

	public void setValue(String value) {
		getDInput().setHtmlValue(value);
		onValueChange(value);
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
	
	private DInput getDInput() {
		return (DInput) getDNode();
	}

}
