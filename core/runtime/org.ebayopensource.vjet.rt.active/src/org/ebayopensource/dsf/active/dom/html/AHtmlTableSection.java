/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.w3c.dom.html.HTMLTableRowElement;

import org.ebayopensource.dsf.html.dom.BaseTableSection;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlTableSection;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlTableSection extends AHtmlElement implements HtmlTableSection {
	
	private static final long serialVersionUID = 1L;
	
	private AHtmlCollection m_rows;
	
	protected AHtmlTableSection(AHtmlDocument doc, BaseTableSection node) {
		super(doc, node);
		populateScriptable(AHtmlTableSection.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	 /**
     * Explicit implementation of cloneNode() to ensure that cache used
     * for getRows() gets cleared.
     */
    public ANode cloneNode( boolean deep ) {
    	AHtmlTableSection clonedNode = (AHtmlTableSection)super.cloneNode( deep );
        clonedNode.m_rows = null;
        return clonedNode;
    }

	public void deleteRow(int index) {
		if (index == -1) {
			Node child = getLastChild();
			if (child != null && child instanceof HTMLTableRowElement) {
				removeChild(child);
			}
		} else {
			deleteRowX(index);
		}
	}

	public String getAlign() {
		return getDTBody().getHtmlAlign();
	}

	public String getCh() {
		return getDTBody().getHtmlCh();
	}

	public String getChOff() {
		return getDTBody().getHtmlChOff();
	}

	public HtmlCollection getRows() {
		if ( m_rows == null )
			m_rows = new AHtmlCollection( this, AHtmlCollection.ROW );
        return m_rows;
	}

	public String getVAlign() {
		return getDTBody().getHtmlValign();
	}

	public HtmlElement insertRow(int index) {
		AHtmlTableRow    newRow;
        newRow = new AHtmlTableRow(
        		getOwnerDocument(), getDTBody().htmlInsertRow(index));
//        newRow.insertCell( 0 );
        // If index is -1, the new row is appended. 
        if (index == -1) {
        	appendChild( newRow, false );
        } else if ( insertRowX( index, newRow ) >= 0 ) {
            appendChild( newRow, false );
        }
        onAppendChild(newRow);
        return newRow;
	}

	public void setAlign(String align) {
		getDTBody().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setCh(String _char) {
		getDTBody().setHtmlCh(_char);
		onAttrChange(EHtmlAttr._char, _char);
	}

	public void setChOff(String charoff) {
		getDTBody().setHtmlChOff(charoff);
		onAttrChange(EHtmlAttr.charoff, charoff);
	}

	public void setVAlign(String valign) {
		getDTBody().setHtmlValign(valign);
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
	
	int deleteRowX(int index) {
        ANode    child;
        child = (ANode) getFirstChild();
        while ( child != null )
        {
            if ( child instanceof HTMLTableRowElement )
            {
                if ( index == 0 )
                {
                    removeChild ( child );
                    return -1;
                }
                --index;
            }
            child = (ANode) child.getNextSibling();
        }
        return index;
    }
	
    int insertRowX( int index, AHtmlTableRow newRow )
    {
        ANode    child;
        child = (ANode) getFirstChild();
        while ( child != null )
        {
            if ( child instanceof HTMLTableRowElement )
            {
                if ( index == 0 )
                {
                    insertBefore( newRow, child, false );
                    return -1;
                }
                --index;
            }
            child = (ANode) child.getNextSibling();
        }
        return index;
    }
	
	private BaseTableSection getDTBody() {
		return (BaseTableSection) getDNode();
	}

}
