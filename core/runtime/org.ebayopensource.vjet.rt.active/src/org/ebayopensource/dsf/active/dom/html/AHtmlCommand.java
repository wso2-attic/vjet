/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DCommand;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlCommand;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlCommand extends AHtmlElement implements HtmlCommand {

	private static final long serialVersionUID = 1L;
	
	//
	// Constructor(s)
	//
	protected AHtmlCommand(AHtmlDocument doc, DCommand command) {
		super(doc, command);
		populateScriptable(AHtmlCommand.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	// 
	// Satisfy HtmlCommand
	//
	public String getType() {
		return getDCommand().getHtmlType();
	}
	public void setType(String type) {
		getDCommand().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, type);
	}

	public String getLabel() {
		return getDCommand().getHtmlLabel();
	}
	public void setLabel(String label) {
		getDCommand().setHtmlType(label);
		onAttrChange(EHtmlAttr.label, label);
	}
	
	public String getIcon() {
		return getDCommand().getHtmlIcon();
	}
	public void setIcon(String iconUrl) {
		getDCommand().setHtmlType(iconUrl);
		onAttrChange(EHtmlAttr.icon, iconUrl);
	}	
	
	public boolean getDisabled() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.disabled,getHtmlAttribute(EHtmlAttr.disabled));
	}
	public void setDisabled(boolean disabled) {
		getDCommand().setHtmlDisabled(disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
	}
	
	public boolean getChecked() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.checked,getHtmlAttribute(EHtmlAttr.checked));
	}
	public void setChecked(boolean checked) {
		setHtmlAttribute(EHtmlAttr.checked, checked);
		onAttrChange(EHtmlAttr.checked, checked);
	}
	
	public String getRadioGroup() {
		return getDCommand().getHtmlRadioGroup();
	}
	public void setRadioGroup(String radiogroup) {
		getDCommand().setHtmlRadioGroup(radiogroup);
		onAttrChange(EHtmlAttr.radiogroup, radiogroup);
	}
	
	//
	// Events
	//
	// Since property name is 'onabort', Rhino invokes this method.
	public Object getOnabort() {
		return getOnAbort();
	}
	
	// For Rhino
	public void setOnabort(Object functionRef) {
		setOnAbort(functionRef);
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
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	// Since property name is 'onunload', Rhino invokes this method.
	public Object getOnunload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnunload(Object functionRef) {
		setOnLoad(functionRef);
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

	//
	// Private
	//
	private DCommand getDCommand() {
		return (DCommand) getDNode();
	}
}
