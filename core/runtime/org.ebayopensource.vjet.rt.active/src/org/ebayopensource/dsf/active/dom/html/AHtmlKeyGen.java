/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DKeyGen;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlKeyGen;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlKeyGen extends AHtmlElement implements HtmlKeyGen {

	private static final long serialVersionUID = 1L;
	
	//
	// Constructor(s)
	//
	protected AHtmlKeyGen(AHtmlDocument doc, DKeyGen keyGen) {
		super(doc, keyGen);
		populateScriptable(AHtmlKeyGen.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	// 
	// Satisfy HtmlCommand
	//
	public String getKeyType() {
		return getDKeyGen().getHtmlKeyType();
	}
	public void setKeyType(String keytype) {
		getDKeyGen().setHtmlKeyType(keytype);
		onAttrChange(EHtmlAttr.keytype, keytype);
	}

	public String getName() {
		return getDKeyGen().getHtmlName();
	}
	public void setName(String name) {
		getDKeyGen().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}	
	
	public boolean getAutoFocus() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.autofocus,getHtmlAttribute(EHtmlAttr.autofocus));
	}
	public void setAutoFocus(boolean autofocus) {
		getDKeyGen().setHtmlAutoFocus(autofocus);
		onAttrChange(EHtmlAttr.autofocus, autofocus);
	}
	
	public boolean getChallenge() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.challenge,getHtmlAttribute(EHtmlAttr.challenge));
	}
	public void setChallenge(boolean challenge) {
		getDKeyGen().setHtmlChallenge(challenge);
		onAttrChange(EHtmlAttr.challenge, challenge);
	}
	
	public boolean getDisabled() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.disabled,getHtmlAttribute(EHtmlAttr.disabled));
	}
	public void setDisabled(boolean disabled) {
		getDKeyGen().setHtmlDisabled(disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
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
	private DKeyGen getDKeyGen() {
		return (DKeyGen) getDNode();
	}
}
