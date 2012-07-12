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

import org.ebayopensource.dsf.common.Z;

public abstract class BaseTableSection extends BaseAttrsHtmlElement {

	private static final long serialVersionUID = 3257001042984973618L;

	private DHtmlCollection m_rows;

	//
	// Constructor(s)
	//	
	BaseTableSection(final HtmlTypeEnum type) {
		super(type);
	}
	BaseTableSection(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
	//
	// HTML Attributes
	//	
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlAlign() {
		return capitalize(getHtmlAttribute(EHtmlAttr.align));
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public BaseTableSection setHtmlAlign(final String align) {
		setHtmlAttribute(EHtmlAttr.align, align);
		return this ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlCh() {
		return Util.getHtmlCh(this) ;
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public BaseTableSection setHtmlCh(final String _char) {
		Util.setHtmlCh(this, _char) ;
		return this;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlChOff() {
		return getHtmlAttribute(EHtmlAttr.charoff);
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public BaseTableSection setHtmlChOff(final String charoff) {
		setHtmlAttribute(EHtmlAttr.charoff, charoff);
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
	public BaseTableSection setHtmlValign(final String valign) {
		setHtmlAttribute(EHtmlAttr.valign, valign);
		return this ;
	}

	public DHtmlCollection getHtmlRows() {
		if (m_rows == null) {
			m_rows = new DHtmlCollection(this, DHtmlCollection.ROW);
		}
		return m_rows;
	}

	public DTr htmlInsertRow(final int index) {
		final DTr newRow = new DTr();
		newRow.htmlInsertCell(0);
		if (insertRowX(index, newRow) >= 0) {
			add(newRow);
		}
		return newRow;
	}

	int insertRowX(int index, DTr newRow) {
		Node child = getFirstChild();
		while (child != null) {
			if (child instanceof DTr) {
				if (index == 0) {
					insertBefore(newRow, child);
					return -1;
				}
				--index;
			}
			child = child.getNextSibling();
		}
		return index;
	}

	public void htmlDeleteRow(final int index) {
		deleteRowX(index);
	}

	int deleteRowX(int index) {
		Node child = getFirstChild();
		while (child != null) {
			if (child instanceof DTr) {
				if (index == 0) {
					removeChild(child);
					return -1;
				}
				--index;
			}
			child = child.getNextSibling();
		}
		return index;
	}

	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt("align", getHtmlAlign()) +
		Z.fmt("char", "" + getHtmlCh()) +
		Z.fmt("charoff", "" + getHtmlChOff()) +
//		Z.fmt("rows", "" + getHtmlRows()) +
		Z.fmt("isTableBody", "" + isHtmlTableBody()) +
		Z.fmt("isTableFooter", "" + isHtmlTableFooter()) +
		Z.fmt("isTableHeader", "" + isHtmlTableHeader()) +
		Z.fmt("valign", getHtmlValign()) ;	
	}
	
	/*
	 * Explicit implementation of clone() to ensure that cache used
	 * for getRows() gets cleared.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		final BaseTableSection clonedNode = 
			(BaseTableSection) super.clone();
		clonedNode.m_rows = null;
		return clonedNode;
	}

	//////////////////////////////////////////////
	///// Additional API                     /////
	//////////////////////////////////////////////
	public boolean isHtmlTableHeader() {
		return this instanceof DTHead;
	}

	public boolean isHtmlTableFooter() {
		return this instanceof DTFoot;
	}

	public boolean isHtmlTableBody() {
		return this instanceof DTBody;
	}
}
