/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DTh;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlTh;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlTh extends AHtmlElement implements HtmlTh {
	
	private static final long serialVersionUID = 1L;

	protected AHtmlTh(AHtmlDocument doc, DTh node) {
		super(doc, node);
		populateScriptable(AHtmlTh.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAlign() {
		return getDTh().getHtmlAlign();
	}

	public String getCh() {
		return getDTh().getHtmlCh();
	}

	public String getChOff() {
		return getDTh().getHtmlChOff();
	}

	public String getVAlign() {
		return getDTh().getHtmlValign();
	}

	public void setAlign(String align) {
		getDTh().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setCh(String _char) {
		getDTh().setHtmlCh(_char);
		onAttrChange(EHtmlAttr._char, _char);
	}

	public void setChOff(String charoff) {
		getDTh().setHtmlChOff(charoff);
		onAttrChange(EHtmlAttr.charoff, charoff);
	}

	public void setVAlign(String valign) {
		getDTh().setHtmlValign(valign);
		onAttrChange(EHtmlAttr.valign, valign);
	}
	
	public String getAbbr() {
		return getDTh().getHtmlAbbr();
	}

	public String getAxis() {
		return getDTh().getHtmlAxis();
	}

	public String getColspan() {
		return getHtmlAttribute(EHtmlAttr.colspan);
	}

	public String getHeaders() {
		return getDTh().getHtmlHeaders();
	}

	public String getHeight() {
		return getDTh().getHtmlHeight();
	}

	public String getNowrap() {
		return String.valueOf(getDTh().getHtmlNoWrap());
	}

	public String getRowspan() {
		return String.valueOf(getDTh().getHtmlRowSpan());
	}

	public String getScope() {
		return getDTh().getHtmlScope();
	}

	public void setAbbr(String abbr) {
		getDTh().setHtmlAbbr(abbr);
		onAttrChange(EHtmlAttr.abbr, abbr);
	}

	public void setAxis(String axis) {
		getDTh().setHtmlAxis(axis);
		onAttrChange(EHtmlAttr.axis, axis);
	}

	public void setColspan(String colspan) {
		getDTh().setHtmlColSpan(colspan);
		onAttrChange(EHtmlAttr.colspan, colspan);
	}

	public void setHeaders(String headers) {
		getDTh().setHtmlHeaders(headers);
		onAttrChange(EHtmlAttr.headers, headers);
	}

	public void setHeight(String height) {
		getDTh().setHtmlHeight(height);
		onAttrChange(EHtmlAttr.height, height);
	}

	public void setNowrap(String noWrap) {
		getDTh().setHtmlNoWrap(noWrap);
		onAttrChange(EHtmlAttr.nowrap, noWrap);
	}

	public void setRowspan(String rowspan) {
		getDTh().setHtmlRowSpan(rowspan);
		onAttrChange(EHtmlAttr.rowspan, rowspan);
	}

	public void setScope(String scope) {
		getDTh().setHtmlScope(scope);
		onAttrChange(EHtmlAttr.scope, scope);
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
	
	private DTh getDTh() {
		return (DTh) getDNode();
	}
}
