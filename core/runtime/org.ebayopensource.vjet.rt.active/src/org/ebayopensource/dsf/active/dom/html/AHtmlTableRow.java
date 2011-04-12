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
import org.ebayopensource.dsf.html.dom.DTr;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlTableCell;
import org.ebayopensource.dsf.jsnative.HtmlTableRow;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlTableRow extends AHtmlElement implements HtmlTableRow {

	private static final long serialVersionUID = 1L;
	
	private AHtmlCollection m_cells;

	protected AHtmlTableRow(AHtmlDocument doc, DTr node) {
		super(doc, node);
		populateScriptable(AHtmlTableRow.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
    /**
     * Explicit implementation of cloneNode() to ensure that cache used
     * for getCells() gets cleared.
     */
    public ANode cloneNode( boolean deep ) {
    	AHtmlTableRow clonedNode = (AHtmlTableRow)super.cloneNode( deep );
        clonedNode.m_cells = null;
        return clonedNode;
    }

	public void deleteCell(int index) {        
        ANode child = (AElement) getFirstChild();
        while ( child != null ) {
            if ( child instanceof AHtmlTableCell ) {
                if ( index == 0 ) {
                    removeChild ( child );
                    return;
                }
                --index;
            }
            child = (ANode) child.getNextSibling();
        }
	}

	public String getAlign() {
		return getDTr().getHtmlAlign();
	}

	public String getBgColor() {
		return getDTr().getHtmlBgColor();
	}

	public HtmlCollection getCells() {
		if (m_cells == null) {
			m_cells = new AHtmlCollection(this, AHtmlCollection.CELL);
		}
		return m_cells;
	}

	public String getCh() {
		return getDTr().getHtmlCh();
	}

	public String getChOff() {
		return getDTr().getHtmlChOff();
	}

	public int getRowIndex() {
		return getDTr().getHtmlRowIndex();
	}

	public int getSectionRowIndex() {
		return getDTr().getHtmlSectionRowIndex();
	}

	public String getVAlign() {
		return getDTr().getHtmlValign();
	}

	public HtmlTableCell insertCell(int index) {
		ANode        child;
		AHtmlTableCell    newCell;
        DTd td = getDTr().htmlInsertCell(index);
        newCell = new AHtmlTableCell(getOwnerDocument(), td);
        child = (ANode) getFirstChild();
        while ( child != null ) {
            if ( child instanceof AHtmlTableCell ) {
                if ( index == 0 ) {
                    insertBefore( newCell, child, false );
                    return newCell;
                }
                --index;
            }
            child = (ANode) child.getNextSibling();
        }
        appendChild( newCell, false );
        return newCell;
	}

	public void setAlign(String align) {
		getDTr().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setBgColor(String bgColor) {
		getDTr().setHtmlBgColor(bgColor);
		onAttrChange(EHtmlAttr.bgcolor, bgColor);
	}

	public void setCh(String _char) {
		getDTr().setHtmlCh(_char);
		onAttrChange(EHtmlAttr._char, _char);

	}

	public void setChOff(String charoff) {
		getDTr().setHtmlChOff(charoff);
		onAttrChange(EHtmlAttr.charoff, charoff);
	}

	public void setRowIndex(int rowIndex) {
		getDTr().setHtmlRowIndex(rowIndex);
	}

	public void setSectionRowIndex(int sectionRowIndex) {
		getDTr().setHtmlSectionRowIndex(sectionRowIndex);
	}

	public void setVAlign(String valign) {
		getDTr().setHtmlValign(valign);
		onAttrChange(EHtmlAttr.valign, valign);
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
	
	private DTr getDTr() {
		return (DTr) getDNode();
	}

}
