/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.common.Z;

public abstract class BaseTableCell extends BaseAttrsHtmlElement {
	/** "left" */
	public static final String ALIGN_LEFT =    "left" ;
	/** "center" */
	public static final String ALIGN_CENTER =  "center" ; // ignoreHtmlKeyword
	/** "right" */
	public static final String ALIGN_RIGHT =   "right" ;
	/** "justify */
	public static final String ALIGN_JUSTIFY = "justify" ;
	/** "char" */
	public static final String ALIGN_CHAR =    "char" ; // ignoreHtmlKeyword

	/** "top" */
	public static final String VALIGN_TOP =      "top" ;
	/** "middle" */
	public static final String VALIGN_MIDDLE =   "middle" ;
	/** "bottom" */
	public static final String VALIGN_BOTTOM =   "bottom" ;
	/** "baseline" */
	public static final String VALIGN_BASELINE = "baseline" ;
	
	/** "row" */
	public static final String SCOPE_ROW = "row" ;
	/** "col" */
	public static final String SCOPE_COL = "col" ; // ignoreHtmlKeyword
	/** "rowgroup" */
	public static final String SCOPE_ROWGROUP = "rowgroup" ;
	/** "colgroup" */
	public static final String SCOPE_COLGROUP = "colgroup" ; // ignoreHtmlKeyword
	
	private static final long serialVersionUID = 3256722862214820152L;

	//
	// Constructor(s)
	//
	BaseTableCell(final HtmlTypeEnum type) {
		super(type);
	}
	BaseTableCell(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
	//
	// HTML Attributes
	//	
	public int getHtmlCellIndex() {
		final DNode parent = (DNode)getParentNode();
		int index = 0;
		if (parent instanceof DTr) {
			Node child = parent.getFirstChild();
			while (child != null) {
				if (child instanceof BaseTableCell) {
					if (child == this)
						return index;
					++index;
				}
				child = child.getNextSibling();
			}
		}
		return -1;
	}

	public BaseTableCell setHtmlCellIndex(final String cellIndex) {
		return setHtmlCellIndex(Integer.parseInt(cellIndex)) ;
	}
	
	public BaseTableCell setHtmlCellIndex(int cellIndex) {
		final DNode parent = (DNode)getParentNode();
		if (parent instanceof DTr) {
			Node child = parent.getFirstChild();
			while (child != null) {
				if (child instanceof BaseTableCell) {
					if (cellIndex == 0) {
						if (this != child)
							parent.insertBefore(this, child);
						return this;
					}
					--cellIndex;
				}
				child = child.getNextSibling();
			}
		}
		if (parent != null) {
			parent.add(this);
		}
		return this ;
	}

	public String getHtmlAbbr() {
		return getHtmlAttribute(EHtmlAttr.abbr);
	}

	public BaseTableCell setHtmlAbbr(final String abbr) {
		setHtmlAttribute(EHtmlAttr.abbr, abbr);
		return this ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlAlign() {
		return capitalize(getHtmlAttribute(EHtmlAttr.align));
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public BaseTableCell setHtmlAlign(final String align) {
		setHtmlAttribute(EHtmlAttr.align, align);
		return this ;
	}

	public String getHtmlAxis() {
		return getHtmlAttribute(EHtmlAttr.axis);
	}

	public BaseTableCell setHtmlAxis(final String axis) {
		setHtmlAttribute(EHtmlAttr.axis, axis);
		return this ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlBgColor() {
		return getHtmlAttribute(EHtmlAttr.bgcolor);
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public BaseTableCell setHtmlBgColor(final String bgColor) {
		setHtmlAttribute(EHtmlAttr.bgcolor, bgColor);
		return this ;
	}

	// Purposefully did not use full name "char" to avoid confusion
	public String getHtmlCh() {
		return Util.getHtmlCh(this) ;
	}

	// Purposefully did not use full name "char" to avoid confusion
	public BaseTableCell setHtmlCh(String _char) {
		Util.setHtmlCh(this, _char) ;
		return this;
	}

	// Purposefully did not use full name "char" to avoid confusion
	public String getHtmlChOff() {
		return getHtmlAttribute(EHtmlAttr.charoff);
	}

	// Purposefully did not use full name "char" to avoid confusion
	public BaseTableCell setHtmlChOff(final String charoff) {
		setHtmlAttribute(EHtmlAttr.charoff, charoff);
		return this ;
	}

	public int getHtmlColSpan() {
		return getHtmlAttributeInteger(EHtmlAttr.colspan);
	}

	public BaseTableCell setHtmlColSpan(final String colspan) {
		setHtmlAttribute(EHtmlAttr.colspan, colspan);
		return this ;
	}
	
	public BaseTableCell setHtmlColSpan(final int colspan) {
		return setHtmlColSpan(String.valueOf(colspan)) ;
	}

	public String getHtmlHeaders() {
		return getHtmlAttribute(EHtmlAttr.headers);
	}

	public BaseTableCell setHtmlHeaders(final String headers) {
		setHtmlAttribute(EHtmlAttr.headers, headers);
		return this ;
	}

	public String getHtmlHeight() {
		return getHtmlAttribute(EHtmlAttr.height);
	}

	public BaseTableCell setHtmlHeight(final String height) {
		setHtmlAttribute(EHtmlAttr.height, height);
		return this ;
	}
	
	public BaseTableCell setHtmlHeight(final int height) {
		return setHtmlHeight(String.valueOf(height)) ;
	}

	public boolean getHtmlNoWrap() {
		return getHtmlAttributeExists(EHtmlAttr.nowrap);
	}

	public BaseTableCell setHtmlNoWrap(final String noWrap) {
		return setHtmlNoWrap(toBoolean("nowrap", noWrap)) ;
	}
	
	public BaseTableCell setHtmlNoWrap(final boolean noWrap) {
		setHtmlAttribute(EHtmlAttr.nowrap, noWrap);
		return this ;
	}

	public int getHtmlRowSpan() {
		return getHtmlAttributeInteger(EHtmlAttr.rowspan);
	}

	public BaseTableCell setHtmlRowSpan(final String rowspan) {
		setHtmlAttribute(EHtmlAttr.rowspan, rowspan);
		return this ;
	}
	
	public BaseTableCell setHtmlRowSpan(final int rowspan) {
		return setHtmlRowSpan(String.valueOf(rowspan)) ;
	}

	public String getHtmlScope() {
		return getHtmlAttribute(EHtmlAttr.scope);
	}

	public BaseTableCell setHtmlScope(final String scope) {
		setHtmlAttribute(EHtmlAttr.scope, scope);
		return this ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlValign() {
		return capitalize(getHtmlAttribute(EHtmlAttr.valign));
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public BaseTableCell setHtmlValign(final String valign) {
		setHtmlAttribute(EHtmlAttr.valign, valign);
		return this ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlWidth() {
		return getHtmlAttribute(EHtmlAttr.width);
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public BaseTableCell setHtmlWidth(final String width) {
		setHtmlAttribute(EHtmlAttr.width, width);
		return this ;
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */	
	public BaseTableCell setHtmlWidth(final int width) {
		return setHtmlWidth(String.valueOf(width)) ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt(EHtmlAttr.abbr.getAttributeName(), getHtmlAbbr()) + 
		Z.fmt(EHtmlAttr.align.getAttributeName(), getHtmlAlign()) +
		Z.fmt(EHtmlAttr.axis.getAttributeName(), getHtmlAxis()) +
		Z.fmt(EHtmlAttr.bgcolor.getAttributeName(), getHtmlBgColor()) +
		Z.fmt("cellindex", "" + getHtmlCellIndex()) +
		Z.fmt(EHtmlAttr._char.getAttributeName(), "" + getHtmlCh()) +
		Z.fmt(EHtmlAttr.charoff.getAttributeName(), "" + getHtmlChOff()) +
		Z.fmt(EHtmlAttr.colspan.getAttributeName(), "" + getHtmlColSpan()) +
		Z.fmt(EHtmlAttr.headers.getAttributeName(), "" + getHtmlHeaders()) +
		Z.fmt(EHtmlAttr.height.getAttributeName(), "" + getHtmlHeight()) +
		Z.fmt(EHtmlAttr.nowrap.getAttributeName(), "" + getHtmlNoWrap()) +
		Z.fmt(EHtmlAttr.rowspan.getAttributeName(), "" + getHtmlRowSpan()) +
		Z.fmt(EHtmlAttr.scope.getAttributeName(), "" + getHtmlScope()) +
		Z.fmt(EHtmlAttr.valign.getAttributeName(), "" + getHtmlValign()) +
		Z.fmt(EHtmlAttr.width.getAttributeName(), getHtmlWidth()) ;	
	}
}
