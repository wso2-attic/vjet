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
import org.ebayopensource.dsf.dap.util.DapDomHelper;
import org.ebayopensource.dsf.html.dom.DForm;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.mozilla.mod.javascript.Scriptable;

public class AHtmlForm extends AHtmlElement implements HtmlForm {

	private static final long serialVersionUID = 1L;
	private static final String SUBMIT_JS_METHOD = "submit()";
	private static final String RESET_JS_METHOD = "reset()";
	
	private AHtmlCollection m_elements;
	
	protected AHtmlForm(AHtmlDocument doc, DForm form) {
		super(doc, form);
		populateScriptable(AHtmlForm.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAcceptCharset() {
		return getDForm().getHtmlAcceptCharset();
	}

	public String getAction() {
		return getDForm().getHtmlAction();
	}

	public HtmlCollection getElements() {
		if (m_elements == null) {
			m_elements = new AHtmlCollection(this, AHtmlCollection.ELEMENT);
		}
		return m_elements;
	}

	public String getEnctype() {
		return getDForm().getHtmlEnctype();
	}

	public String getMethod() {
		return getDForm().getHtmlMethod();
	}

	public String getName() {
		return getDForm().getHtmlName();
	}

	public String getTarget() {
		return getDForm().getHtmlTarget();
	}

	public void reset() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if (browserBinding != null) {
				browserBinding.executeDomMethod(getDForm(), RESET_JS_METHOD);
			}
		}
	}

	public void setAcceptCharset(String acceptCharset) {
		getDForm().setHtmlAcceptCharset(acceptCharset);
		onAttrChange(EHtmlAttr.accept_charset, acceptCharset);
	}

	public void setAction(String binding) {
		getDForm().setHtmlAction(binding);
		onAttrChange(EHtmlAttr.action, binding);
	}

	public void setEnctype(String enctype) {
		getDForm().setHtmlEnctype(enctype);
		onAttrChange(EHtmlAttr.enctype, enctype);
	}

	public void setMethod(String method) {
		getDForm().setHtmlMethod(method);
		onAttrChange(EHtmlAttr.method, method);
	}

	public void setName(String name) {
		getDForm().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setTarget(String target) {
		getDForm().setHtmlTarget(target);
		onAttrChange(EHtmlAttr.target, target);
	}

	public void submit() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if (browserBinding != null) {
				browserBinding.executeDomMethod(getDForm(), SUBMIT_JS_METHOD);
			}
		}
	}

	public int getLength() {
		return getDForm().getHtmlLength();
	}
	
	@Override
	public Object get(int index, Scriptable start) {
		Object obj = null;
		if ((obj = findElemObject(index)) != null)
			return obj;
		obj = super.get(index, start);
		return obj;
	}

	private Object findElemObject(int index) {
		return getElements().item(index);
	}

	public Object get(String name, Scriptable start) {
		Object obj = null;
		if ((obj = findElemObject(name)) != null)
			return obj;
		obj = super.get(name, start);
		return obj;
	}
	
	private Object findElemObject(String name) {
		HtmlCollection elements = getElements();
		for (int i = 0; i < elements.getLength(); i++) {
			AHtmlElement elem = (AHtmlElement) elements.item(i);
			if (elem instanceof AHtmlInput
					&& ((AHtmlInput) elem).getName().equals(name))
					return elem;
			else if (
				elem instanceof AHtmlSelect
					&& ((AHtmlSelect) elem).getName().equals(name))
				return elem;
			else if (
				elem instanceof AHtmlTextArea
					&& ((AHtmlTextArea) elem).getName().equals(name))
				return elem;
		}
		return null;
	}

	private DForm getDForm() {
		return (DForm) getDNode();
	}
	
	public String getReferenceAsJs(){
		return DapDomHelper.getPath(getDForm());
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
	
	// Since property name is 'onreset', Rhino invokes this method.
	public Object getOnreset() {
		return getOnReset();
	}
	
	// For Rhino
	public void setOnreset(Object functionRef) {
		setOnReset(functionRef);
	}
	
	// Since property name is 'onsubmit', Rhino invokes this method.
	public Object getOnsubmit() {
		return getOnSubmit();
	}
	
	// For Rhino
	public void setOnsubmit(Object functionRef) {
		setOnSubmit(functionRef);
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
	
}
