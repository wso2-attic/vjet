/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DTd;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlTableCell;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlTableCell extends AHtmlElement implements HtmlTableCell {
	
	private static final long serialVersionUID = 1L;
	
	protected AHtmlTableCell(AHtmlDocument doc, DTd node) {
		super(doc, node);
		populateScriptable(AHtmlTableCell.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAbbr() {
		return getDTd().getHtmlAbbr();
	}

	public String getAlign() {
		return getDTd().getHtmlAlign();
	}

	public String getAxis() {
		return getDTd().getHtmlAxis();
	}

	public String getBgColor() {
		return getDTd().getHtmlBgColor();
	}

	public int getCellIndex() {
		return getDTd().getHtmlCellIndex();
	}

	public String getCh() {
		return getDTd().getHtmlCh();
	}

	public String getChOff() {
		return getDTd().getHtmlChOff();
	}

	public int getColSpan() {
		return getDTd().getHtmlColSpan();
	}

	public String getHeaders() {
		return getDTd().getHtmlHeaders();
	}

	public String getHeight() {
		return getDTd().getHtmlHeight();
	}

	public boolean getNoWrap() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.nowrap,getHtmlAttribute(EHtmlAttr.nowrap));
	}

	public int getRowSpan() {
		return getDTd().getHtmlRowSpan();
	}

	public String getScope() {
		return getDTd().getHtmlScope();
	}

	public String getVAlign() {
		return getDTd().getHtmlValign();
	}

	public String getWidth() {
		return getDTd().getHtmlWidth();
	}

	public void setAbbr(String abbr) {
		getDTd().setHtmlAbbr(abbr);
		onAttrChange(EHtmlAttr.abbr, abbr);
	}

	public void setAlign(String align) {
		getDTd().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setAxis(String axis) {
		getDTd().setHtmlAxis(axis);
		onAttrChange(EHtmlAttr.axis, axis);
	}

	public void setBgColor(String bgColor) {
		getDTd().setHtmlBgColor(bgColor);
		onAttrChange(EHtmlAttr.bgcolor, bgColor);
	}

//	public void setCellIndex(int cellIndex) {
//		getDTd().setHtmlCellIndex(cellIndex);
//		// EHtmlAttr.cellindex
////		onAttrChange(EHtmlAttr.cellindex, border);
//	}

	public void setCh(String _char) {
		getDTd().setHtmlCh(_char);
		onAttrChange(EHtmlAttr._char, _char);
	}

	public void setChOff(String charoff) {
		getDTd().setHtmlChOff(charoff);
		onAttrChange(EHtmlAttr.charoff, charoff);
	}

	public void setColSpan(int colspan) {
		getDTd().setHtmlColSpan(colspan);
		onAttrChange(EHtmlAttr.colspan, String.valueOf(colspan));
	}

	public void setHeaders(String headers) {
		getDTd().setHtmlHeaders(headers);
		onAttrChange(EHtmlAttr.headers, headers);
	}

	public void setHeight(String height) {
		getDTd().setHtmlHeight(height);
		onAttrChange(EHtmlAttr.height, height);
	}

	public void setNoWrap(boolean noWrap) {
		setHtmlAttribute(EHtmlAttr.nowrap, noWrap);
		onAttrChange(EHtmlAttr.nowrap, noWrap);
	}

	public void setRowSpan(int rowspan) {
		getDTd().setHtmlRowSpan(rowspan);
		onAttrChange(EHtmlAttr.rowspan, String.valueOf(rowspan));
	}

	public void setScope(String scope) {
		getDTd().setHtmlScope(scope);
		onAttrChange(EHtmlAttr.scope, scope);
	}

	public void setVAlign(String valign) {
		getDTd().setHtmlValign(valign);
		onAttrChange(EHtmlAttr.valign, valign);
	}

	public void setWidth(String width) {
		getDTd().setHtmlWidth(width);
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
	
	private DTd getDTd() {
		return (DTd) getDNode();
	}

}
