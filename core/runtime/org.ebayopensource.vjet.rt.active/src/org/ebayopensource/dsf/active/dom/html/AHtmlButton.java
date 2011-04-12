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
import org.ebayopensource.dsf.html.dom.DButton;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.dom.IDFormControl;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.HtmlButton;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlButton extends AHtmlElement implements HtmlButton, IDFormControl {
	
	private static final long serialVersionUID = 1L;
	private static final String FOCUS_JS_METHOD = "focus()";
	
	protected AHtmlButton(AHtmlDocument doc, DButton button) {
		super(doc, button);
		populateScriptable(AHtmlButton.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
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
				browserBinding.executeDomMethod(getDButton(), FOCUS_JS_METHOD);
			}
		}
	}
	public String getAccessKey() {
		return getDButton().getHtmlAccessKey();
	}

	public boolean getDisabled() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.disabled,getHtmlAttribute(EHtmlAttr.disabled));
	}

	public HtmlForm getForm() {
		return super.getFormInternal();
	}

	public String getName() {
		return getDButton().getHtmlName();
	}

	public int getTabIndex() {
		return getDButton().getHtmlTabIndex();
	}

	public String getType() {
		return getDButton().getHtmlType();
	}

	public void setType(String type) {
		getDButton().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, type);
	}
	
	public String getValue() {
		return getDButton().getHtmlValue();
	}

	public void setAccessKey(String accessKey) {
		getDButton().setHtmlAccessKey(accessKey);
		onAttrChange(EHtmlAttr.accesskey, accessKey);
	}

	public void setDisabled(boolean disabled) {
		setHtmlAttribute(EHtmlAttr.disabled, disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
	}

	public void setName(String name) {
		getDButton().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setTabIndex(int tabIndex) {
		getDButton().setHtmlTabIndex(tabIndex);
		onAttrChange(EHtmlAttr.tabindex, String.valueOf(tabIndex));
	}

	public void setValue(String value) {
		getDButton().setHtmlValue(value);
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
	
	private DButton getDButton() {
		return (DButton) getDNode();
	}

}
