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
import org.ebayopensource.dsf.html.dom.DA;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.HtmlAnchor;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlAnchor extends AHtmlElement implements HtmlAnchor {
	
	private static final long serialVersionUID = 1L;
	private static final String FOCUS_JS_METHOD = "focus()";
	
	protected AHtmlAnchor(AHtmlDocument doc, DA node) {
		super(doc, node);
		populateScriptable(AHtmlAnchor.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public void blur() {
		dispatchEvent(EventType.BLUR.getName(), this);
	}

	public void focus() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				browserBinding.executeDomMethod(getDa(), FOCUS_JS_METHOD);
			}
		}
	}

	public String getAccessKey() {
		return getDa().getHtmlAccessKey();
	}

	public String getCharset() {
		return getDa().getHtmlCharset();
	}

	public String getCoords() {
		return getDa().getHtmlCoords();
	}

	public String getHref() {
		return getDa().getHtmlHref();
	}

	public String getHreflang() {
		return getDa().getHtmlHreflang();
	}

	public String getName() {
		return getDa().getHtmlName();
	}

	public String getRel() {
		return getDa().getHtmlRel();
	}

	public String getRev() {
		return getDa().getHtmlRev();
	}

	public String getShape() {
		return getDa().getHtmlShape();
	}

	public int getTabIndex() {
		return getDa().getHtmlTabIndex();
	}

	public String getTarget() {
		return getDa().getHtmlTarget();
	}

	public String getType() {
		return getDa().getHtmlType();
	}

	public void setAccessKey(String accessKey) {
		getDa().setHtmlAccessKey(accessKey);
		onAttrChange(EHtmlAttr.accesskey, accessKey);
	}

	public void setCharset(String charset) {
		getDa().setHtmlCharset(charset);
		onAttrChange(EHtmlAttr.charset, charset);
	}

	public void setCoords(String coords) {
		getDa().setHtmlCoords(coords);
		onAttrChange(EHtmlAttr.coords, coords);
	}

	public void setHref(String href) {
		getDa().setHtmlHref(href);
		onAttrChange(EHtmlAttr.href, href);
	}

	public void setHreflang(String hreflang) {
		getDa().setHtmlHreflang(hreflang);
		onAttrChange(EHtmlAttr.hreflang, hreflang);
	}

	public void setName(String name) {
		getDa().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setRel(String rel) {
		getDa().setHtmlRel(rel);
		onAttrChange(EHtmlAttr.rel, rel);
	}

	public void setRev(String rev) {
		getDa().setHtmlRev(rev);
		onAttrChange(EHtmlAttr.rev, rev);
	}

	public void setShape(String shape) {
		getDa().setHtmlShape(shape);
		onAttrChange(EHtmlAttr.shape, shape);
	}

	public void setTabIndex(int tabIndex) {
		getDa().setHtmlTabIndex(tabIndex);
		onAttrChange(EHtmlAttr.tabindex, String.valueOf(tabIndex));
	}

	public void setTarget(String target) {
		getDa().setHtmlTarget(target);
		onAttrChange(EHtmlAttr.target, target);
	}

	public void setType(String type) {
		getDa().setHtmlType(type);
		onAttrChange(EHtmlAttr.type, type);
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
	
	private DA getDa() {
		return (DA) getDNode();
	}

}
