/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;


import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.html.dom.BaseTableSection;
import org.ebayopensource.dsf.html.dom.DTBody;
import org.ebayopensource.dsf.html.dom.DTable;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlTable;
import org.ebayopensource.dsf.jsnative.HtmlTableCaption;
import org.ebayopensource.dsf.jsnative.HtmlTableRow;
import org.ebayopensource.dsf.jsnative.HtmlTableSection;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlTable extends AHtmlElement implements HtmlTable {

	private static final long serialVersionUID = 1L;
	
	private AHtmlCollection m_rows;
	private AHtmlCollection m_bodies;
	
	protected AHtmlTable(AHtmlDocument doc, DTable node) {
		super(doc, node);
		populateScriptable(AHtmlTable.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
    /**
     * Explicit implementation of cloneNode() to ensure that cache used
     * for getRows() and getTBodies() gets cleared.
     */
    public Node cloneNode( boolean deep ) {
        AHtmlTable clonedNode = (AHtmlTable)super.cloneNode( deep );
        clonedNode.m_rows = null;
        clonedNode.m_bodies = null;
        return clonedNode;
    }

	public HtmlTableCaption createCaption() {
		AHtmlTableCaption caption = (AHtmlTableCaption) getCaption();
        if ( caption != null ) {
            return caption;
        }
        caption = new AHtmlTableCaption(
        	getOwnerDocument(), getDTable().htmlCreateCaption());
        appendChild(caption, false);
        onAppendChild(caption);
        return caption;
	}

	public HtmlTableSection createTFoot() {        
		AHtmlTableSection section = (AHtmlTableSection) getTFoot();
        if ( section != null )
            return section;
        section = new AHtmlTableSection(
        	getOwnerDocument(), getDTable().htmlCreateTFoot());
        appendChild( section, false );
        onAppendChild(section);
        return section;
	}

	public HtmlTableSection createTHead() {
		AHtmlTableSection section = (AHtmlTableSection) getTHead();
        if ( section != null )
            return section;
        section = new AHtmlTableSection(
        	getOwnerDocument(), getDTable().htmlCreateTHead());
        appendChild( section, false );
        onAppendChild(section);
        return section;
	}

	public void deleteCaption() {        
		HtmlTableCaption old = getCaption();
        if ( old != null ) {
            removeChild ( old );
        }
	}

	public void deleteRow(int index) {
		Node child = getFirstChild();
        while ( child != null )
        {
            if ( child instanceof AHtmlTableRow )
            {
                if ( index == 0 )
                {
                    removeChild ( child );
                    return;
                }
                --index;
            }
            else
            if ( child instanceof AHtmlTableSection )
            {
                index = ( (AHtmlTableSection) child ).deleteRowX( index );
                if ( index < 0 )
                    return;
            }
            child = child.getNextSibling();
        }

	}

	public void deleteTBody() {
		HtmlTableSection old = getTBody();
		 if ( old != null ) {
			 removeChild ( old );
		 }
	}
	
	public void deleteTFoot() {
		HtmlTableSection old = getTFoot();
		 if ( old != null ) {
			 removeChild ( old );
		 }
	}

	public void deleteTHead() {
		HtmlTableSection old = getTHead();
		 if ( old != null ) {
			 removeChild ( old );
		 }
	}

	public String getAlign() {
		return getDTable().getHtmlAlign();
	}

	public String getBgColor() {
		return getDTable().getHtmlBgColor();
	}

	public String getBorder() {
		return getDTable().getHtmlBorder();
	}

	public HtmlTableCaption getCaption() {
		Node child = getFirstChild();
        while ( child != null )
        {
            if ( child instanceof AHtmlTableCaption &&
                 child.getNodeName().equalsIgnoreCase("caption") )
                return (HtmlTableCaption) child;
            child = child.getNextSibling();
        }
        return null;
	}

	public String getCellPadding() {
		return getDTable().getHtmlCellPadding();
	}

	public String getCellSpacing() {
		return getDTable().getHtmlCellSpacing();
	}

	public String getFrame() {
		return getDTable().getHtmlFrame();
	}

	public HtmlCollection getRows() {
		 if (m_rows == null) {
	            m_rows = new AHtmlCollection( this, AHtmlCollection.ROW );
		 }
		 return m_rows;
	}

	public String getRules() {
		return getDTable().getHtmlRules();
	}

	public String getSummary() {
		return getDTable().getHtmlSummary();
	}

	public HtmlCollection getTBodies() {
		if (m_bodies == null) {
            m_bodies = new AHtmlCollection( this, AHtmlCollection.TBODY );
		}
        return m_bodies;
	}

	public HtmlTableSection getTFoot() {
        Node child = getFirstChild();
        while ( child != null )
        {
            if ( child instanceof AHtmlTableSection &&
                 child.getNodeName().equalsIgnoreCase("tfoot") )
                return (HtmlTableSection) child;
            child = child.getNextSibling();
        }
        return null;
	}

	public HtmlTableSection getTHead() {
		Node child = getFirstChild();
        while ( child != null )
        {
            if ( child instanceof AHtmlTableSection &&
                 child.getNodeName().equalsIgnoreCase("thead") )
                return (HtmlTableSection) child;
            child = child.getNextSibling();
        }
        return null;
	}

	public String getWidth() {
		return getDTable().getHtmlWidth();
	}

	public HtmlTableRow insertRow(int index) {
		 AHtmlTableRow    newRow;
		 newRow = new AHtmlTableRow(
			getOwnerDocument(), getDTable().htmlInsertRow(index));
        insertRowX( index, newRow );
        return newRow;
	}

	public void setAlign(String align) {
		getDTable().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setBgColor(String bgColor) {
		getDTable().setHtmlBgColor(bgColor);
		onAttrChange(EHtmlAttr.bgcolor, bgColor);
	}

//	public void setBorder(int border) {
//		setBorder("" + border) ;
//	}
	public void setBorder(String border) {
		getDTable().setHtmlBorder(border);
		onAttrChange(EHtmlAttr.border, border);
	}

	public void setCaption(HtmlTableCaption caption) {
		if (caption != null && ! caption.getTagName().equalsIgnoreCase("caption")) {
            throw new IllegalArgumentException( 
            	"Argument 'caption' is not an element of type <CAPTION>" );
		}
        deleteCaption();
        if ( caption != null ) {
            appendChild( caption );
        }
	}

	public void setCellPadding(String cellPadding) {
		getDTable().setHtmlCellPadding(cellPadding);
		onAttrChange(EHtmlAttr.cellpadding, cellPadding);
	}

	public void setCellSpacing(String spacing) {
		getDTable().setHtmlCellSpacing(spacing);
		onAttrChange(EHtmlAttr.cellspacing, spacing);
	}

	public void setFrame(String frame) {
		getDTable().setHtmlFrame(frame);
		onAttrChange(EHtmlAttr.frame, frame);
	}

	public void setRules(String rules) {
		getDTable().setHtmlRules(rules);
		onAttrChange(EHtmlAttr.rules, rules);
	}

	public void setSummary(String summary) {
		getDTable().setHtmlSummary(summary);
		onAttrChange(EHtmlAttr.border, summary);
	}

	public void setTFoot(HtmlTableSection tFoot) {
		if (tFoot != null && ! tFoot.getTagName().equalsIgnoreCase("tfoot")) {
            throw new IllegalArgumentException(
            	"Argument 'tFoot' is not an element of type <TFOOT>" );
		}
        deleteTFoot();
        if ( tFoot != null ) {
            appendChild( tFoot );
        }

	}

	public void setTHead(HtmlTableSection tHead) {
		if ( tHead != null && ! tHead.getTagName().equalsIgnoreCase("thead")) {
            throw new IllegalArgumentException(
            	"Argument 'tHead' is not an element of type <THEAD>" );
		}
        deleteTHead();
        if (tHead != null) {
            appendChild( tHead );
        }
	}

	public void setWidth(String width) {
		getDTable().setHtmlWidth(width);
		onAttrChange(EHtmlAttr.width, width);
	}
	
	@Override
	public Node appendChild(Node newChild) throws DOMException {
		if (newChild instanceof AHtmlTableRow) {
			HtmlTableSection tBody =  getTBody();
			if (tBody == null) {
				tBody =  createTBody();
			}
			tBody.appendChild(newChild);
		} else {
			super.appendChild(newChild);
		}
		return newChild;
	}
	
	
	
	@Override
	void appendChild(ANode newChild, boolean deep) throws DOMException {
		if (newChild instanceof AHtmlTableRow) {
			HtmlTableSection tBody =  getTBody();
			if (tBody == null) {
				tBody =  createTBody();
			}
			tBody.insertRow(-1);
		} else {
			super.appendChild(newChild, deep);
		}
	}

	void insertRowX( int index, AHtmlTableRow newRow )
    {
        ANode    child;
        ANode    lastSection = null;  
        child = (ANode) getFirstChild();
        
        while ( child != null )
        {
            if ( child instanceof AHtmlTableRow )
            {
                if ( index == 0 )
                {
                    insertBefore( newRow, child, false );
                    return;
                }
                --index;
            }
            else if ( child instanceof AHtmlTableSection && 
            		((BaseTableSection) child.getDNode()).isHtmlTableBody())
            {
                lastSection = child;
                index = ( (AHtmlTableSection) child ).insertRowX( index, newRow );
                if ( index < 0 )
                    return;
            }
            child = (ANode) child.getNextSibling();
        }
        if ( lastSection != null ) {
        	lastSection.appendChild(newRow, false);
        
        } else {
            appendChild( newRow, false );
        }
        onAppendChild(newRow);
    }
	
	private HtmlTableSection getTBody() {
		Node child = getFirstChild();
        while ( child != null )
        {
            if ( child instanceof AHtmlTableSection &&
                 child.getNodeName().equalsIgnoreCase("tbody") )
                return (HtmlTableSection) child;
            child = child.getNextSibling();
        }
        return null;
	}
	
	public HtmlTableSection createTBody() {
		DTBody tb = new DTBody();
		getDTable().add(tb);
		AHtmlTableSection tBody = new AHtmlTableSection(
        	getOwnerDocument(), tb);
        super.appendChild(tBody);
        onAppendChild(tBody);
        return tBody;
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
	
	private DTable getDTable() {
		return (DTable) getDNode();
	}

}
