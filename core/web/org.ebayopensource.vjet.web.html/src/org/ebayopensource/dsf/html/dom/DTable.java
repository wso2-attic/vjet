/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.common.node.IDNodeRelationshipVerifier;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.css.CssClassConstant;
import org.ebayopensource.dsf.css.CssIdConstant;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.common.Z;

/**
 * http://www.w3.org/TR/REC-html40/struct/tables.html#edef-TABLE
 */
public class DTable extends BaseAttrsHtmlElement
	implements IDBlock //, IDStrict, IAttrs
{
	private static final long serialVersionUID = 3977585787963651891L;
	
	/** "left" */
	public static final String ALIGN_LEFT = "left" ;
	/** "center" */
	public static final String ALIGN_CENTER = "center" ; // ignoreHtmlKeyword
	/** "right" */
	public static final String ALIGN_RIGHT = "right" ;
	
	/** "void" */
	public static final String FRAME_VOID = "void" ;
	/** "above" */
	public static final String FRAME_ABOVE = "above" ;
	/** "below" */
	public static final String FRAME_BELOW = "below" ;
	/** "hsides" */
	public static final String FRAME_HSIDES = "hsides" ;
	/** "lhs" */
	public static final String FRAME_LHS = "lhs" ;
	/** "rhs" */
	public static final String FRAME_RHS = "rhs" ;
	/** "vsides" */
	public static final String FRAME_VSIDES = "vsides" ;
	/** "box" */
	public static final String FRAME_BOX = "box" ;
	/** "border" */
	public static final String FRAME_BORDER = "border" ;
	
	/** "none" */
	public static final String RULES_NONE = "none" ;
	/** "groups" */
	public static final String RULES_GROUPS = "groups" ;
	/** "rows" */
	public static final String RULES_ROWS = "rows" ;
	/** "cols" */
	public static final String RULES_COLS = "cols" ;
	/** "all" */
	public static final String RULES_ALL = "all" ;
	
	private DHtmlCollection m_rows;
	private DHtmlCollection m_bodies;
	
	
	public DTable() {
		super(HtmlTypeEnum.TABLE);
	}
	
	public DTable(final DHtmlDocument doc) {
		super(doc, HtmlTypeEnum.TABLE);
	}
	
	public DTable(final String jif) {
		this() ;
		jif(jif) ;
	}

	public DTable(BaseHtmlElement... elems) {
		this() ;
		add(elems) ;
	}
	
	//
	// Framework
	//
	@Override
	public HtmlTypeEnum htmlType() {
		return HtmlTypeEnum.TABLE ;
	}
		
	//
	// HTML Attributes
	//
	public DCaption getHtmlCaption() {
		Node child = getFirstChild();
		while (child != null) {
			if (child instanceof DCaption) {
				return (DCaption) child;
			}
			child = child.getNextSibling();
		}
		return null;
	}

	public DTable setHtmlCaption(final DCaption caption) {
		htmlDeleteCaption();
		if (caption != null) {
			add(caption);
		}
		return this ;
	}
	public DCaption htmlCreateCaption() {
		DCaption section = getHtmlCaption();
		if (section != null) {
			return section;
		}
		section = new DCaption();
		add(section);
		return section;
	}

	public DTable htmlDeleteCaption() {
		final Node old = getHtmlCaption();
		if (old != null) {
			removeChild(old);
		}
		return this ;
	}

	public DTHead getHtmlTHead() {
		Node child = getFirstChild();
		while (child != null) {
			if (child instanceof DTHead) {
				return (DTHead) child;
			}
			child = child.getNextSibling();
		}
		return null;
	}

	public DTable setHtmlTHead(final DTHead tHead) {
		htmlDeleteTHead();
		if (tHead != null) {
			add(tHead);
		}
		return this ;
	}

	public DTHead htmlCreateTHead() {
		DTHead section = getHtmlTHead();
		if (section != null) {
			return section;
		}
		section = new DTHead();
		add(section);
		return section;
	}

	public DTable htmlDeleteTHead() {
		final Node old = getHtmlTHead();
		if (old != null) {
			removeChild(old);
		}
		return this ;
	}

	public DTFoot getHtmlTFoot() {
		Node child = getFirstChild();
		while (child != null) {
			if (child instanceof DTFoot) {
				return (DTFoot) child;
			}
			child = child.getNextSibling();
		}
		return null;
	}

	public DTable setHtmlTFoot(final DTFoot tFoot) {
		htmlDeleteTFoot();
		if (tFoot != null) {
			add(tFoot);
		}
		return this ;
	}

	public DTFoot htmlCreateTFoot() {
		DTFoot section = getHtmlTFoot();
		if (section != null) {
			return section;
		}
		section = new DTFoot();
		add(section);
		return section;
	}

	public DTable htmlDeleteTFoot() {
		final Node old = getHtmlTFoot();
		if (old != null) {
			removeChild(old);
		}
		return this ;
	}

	public DHtmlCollection getHtmlRows() {
		if (m_rows == null) {
			m_rows = new DHtmlCollection(this, DHtmlCollection.ROW);
		}
		return m_rows;
	}

	public DHtmlCollection getHtmlTBodies() {
		if (m_bodies == null) {
			m_bodies = new DHtmlCollection(this, DHtmlCollection.TBODY);
		}
		return m_bodies;
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
	public DTable setHtmlAlign(final String align) {
		setHtmlAttribute(EHtmlAttr.align, align);
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
	public DTable setHtmlBgColor(final String bgColor) {
		setHtmlAttribute(EHtmlAttr.bgcolor, bgColor);
		return this ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlBorder() {
		return getHtmlAttribute(EHtmlAttr.border);
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public DTable setHtmlBorder(final String border) {
		setHtmlAttribute(EHtmlAttr.border, border);
		return this ;
	}
	
	public DTable setHtmlBorder(final int border) {
		return setHtmlBorder(Integer.toString(border));
	}	

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 *
	 * @return String -- nn for pixels or nn% for percentage length
	 */
	public String getHtmlCellPadding() {
		return getHtmlAttribute(EHtmlAttr.cellpadding);
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 * 
	 * @param cellPadding -- nn for pixels or nn% for percentage length
	 * @return DTable -- answers this instance
	 */
	public DTable setHtmlCellPadding(final String cellPadding) {
		setHtmlAttribute(EHtmlAttr.cellpadding, cellPadding);
		return this ;
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public DTable setHtmlCellPadding(final int cellPadding) {
		return setHtmlCellPadding(Integer.toString(cellPadding)) ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 *
	 * @return String -- nn for pixels or nn% for percentage length
	 */
	public String getHtmlCellSpacing() {
		return getHtmlAttribute(EHtmlAttr.cellspacing);
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 *
	 * @param cellPadding -- nn for pixels or nn% for percentage length
	 * @return DTable -- answers this instance
	 */
	public DTable setHtmlCellSpacing(final String cellSpacing) {
		setHtmlAttribute(EHtmlAttr.cellspacing, cellSpacing);
		return this ;
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */	
	public DTable setHtmlCellSpacing(final int spacing) {
		return setHtmlCellSpacing(String.valueOf(spacing)) ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlFrame() {
		return capitalize(getHtmlAttribute(EHtmlAttr.frame));
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public DTable setHtmlFrame(final String frame) {
		setHtmlAttribute(EHtmlAttr.frame, frame);
		return this ;
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public String getHtmlRules() {
		return capitalize(getHtmlAttribute(EHtmlAttr.rules));
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public DTable setHtmlRules(final String rules) {
		setHtmlAttribute(EHtmlAttr.rules, rules);
		return this ;
	}

	public String getHtmlSummary() {
		return getHtmlAttribute(EHtmlAttr.summary);
	}

	public DTable setHtmlSummary(final String summary) {
		setHtmlAttribute(EHtmlAttr.summary, summary);
		return this ;
	}

	public String getHtmlWidth() {
		return getHtmlAttribute(EHtmlAttr.width);
	}

	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */
	public DTable setHtmlWidth(final String width) {
		setHtmlAttribute(EHtmlAttr.width, width);
		return this ;
	}
	/**
	 * @deprecated - Not supported in HTML 5.0, better handled by CSS
	 */	
	public DTable setHtmlWidth(final int width) {
		return setHtmlWidth(String.valueOf(width)) ;
	}

	public DTr htmlInsertRow(int index) {
		final DTr newRow = new DTr();
		insertRowX(index, newRow);
		return newRow;
	}

	void insertRowX(int index, DTr newRow) {
		Node lastSection = null;
		Node child = getFirstChild();

		while (child != null) {
			if (child instanceof DTr) {
				if (index == 0) {
					insertBefore(newRow, child);
					return;
				}
				--index;
			}
			else if (child instanceof BaseTableSection
					&& ((BaseTableSection) child).isHtmlTableBody())
			{
				lastSection = child;
				index =
					((BaseTableSection)child).insertRowX(
						index,
						newRow);
				if (index < 0) {
					return;
				}
			}
			child = child.getNextSibling();
		}
		
		if (lastSection != null) {
			lastSection.appendChild(newRow);
		}
		else {
			appendChild(newRow);
		}
	}

	public DTable htmlDeleteRow(int index) {
		Node child = getFirstChild();
		while (child != null) {
			if (child instanceof DTr) {
				if (index == 0) {
					removeChild(child);
					return this;
				}
				--index;
			}
			else if (child instanceof BaseTableSection) {
				index = ((BaseTableSection) child).deleteRowX(index);
				if (index < 0) {
					return this;
				}
			}
			child = child.getNextSibling();
		}
		
		return this ;
	}

	//
	// Assembly Helpers
	//
	public DColGroup htmlAddColGroup() {
		final DColGroup colGroup = new DColGroup() ;
		add(colGroup) ;
		return colGroup ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt(EHtmlAttr.align.getAttributeName(), getHtmlAlign()) +
		Z.fmt(EHtmlAttr.bgcolor.getAttributeName(), getHtmlBgColor()) +
		Z.fmt(EHtmlAttr.border.getAttributeName(), "" + getHtmlBorder()) +
		Z.fmt(HtmlTypeEnum.CAPTION.getName(), "" + getHtmlCaption()) +
		Z.fmt(EHtmlAttr.cellpadding.getAttributeName(), "" + getHtmlCellPadding()) +
		Z.fmt(EHtmlAttr.cellspacing.getAttributeName(), "" + getHtmlCellSpacing()) +
		Z.fmt(EHtmlAttr.frame.getAttributeName(), "" + getHtmlFrame()) +
//		Z.fmt("rows", "" + getHtmlRows()) +
		Z.fmt(EHtmlAttr.rules.getAttributeName(), "" + getHtmlRules()) +
		Z.fmt(EHtmlAttr.summary.getAttributeName(), "" + getHtmlSummary()) +
		Z.fmt(EHtmlAttr.width.getAttributeName(), getHtmlWidth()) ;	
	}
	
	/**
	 * Explicit implementation of clone() to ensure that cache used
	 * for getRows() and getTBodies() gets cleared.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		final DTable clonedNode = (DTable) super.clone();
		clonedNode.m_rows = null;
		clonedNode.m_bodies = null;
		return clonedNode;
	}

	//
	// Overrides from DElement
	//
	/**
	 * Shorthand of appendChild(Node) but takes a DNode arg.
	 * Returns "this" DNode vs. the added child - this is nice for
	 * cascade style programming. 
	 * <code>
	 * node.add(anotherNode).addRaw("&nbsp;") ;
	 * vs.
	 * node.add(anotherNode);
	 * node.addRaw("&nbsp;");
	 * @param newChild node to be appended.  Throws DOMException if value is null.
	 * @return this
	 * @throws DOMException
	 */
	@Override
	public DTable add(final DNode newChild) throws DOMException {
		super.add(newChild) ;
		return this ;
	}
	
	@Override
	public DTable add(BaseHtmlElement... elems) throws DOMException {
		super.add(elems) ;
		return this ;
	}
	
	/**
	 * Shorthand for add(new DText(value))
	 * <br><code>
	 * ex: node.add("Address")
	 * </code>
	 * @param value to be added as a DText node.  Throws DOMException if value is null.
	 * @return this
	 * @throws DOMException
	 */
	@Override
	public DTable add(final String value) throws DOMException {
		super.add(value) ;
		return this ;
	}
	
	/**
	 * Shorthand for add(new DRawString(value))
	 * <br>
	 * The value will be emitted as is without any escaping.
	 * <br>
	 * ex: node.addRaw("&npbsp;")
	 * @param  value to be added without any escaping. Throws DOMException if value is null.
	 */
	@Override
	public DTable addRaw(final String value) throws DOMException {
		super.addRaw(value) ;
		return this ;
	}
	
	/**
	 * This double dispatch approach provides the control point for the node
	 * to have customized behavior.
	 */
	@Override
	public DTable dsfAccept(final IDNodeVisitor visitor) {
		super.dsfAccept(visitor) ;
		return this;
	}
	
	/**
	 * Broadcasts the event to any registered IDsfEventListner's.
	 * The listeners are broadcast to in the order they were maintained.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DTable dsfBroadcast(final DsfEvent event) // must not be null
		throws AbortDsfEventProcessingException
	{
		super.dsfBroadcast(event) ;
		return this;
	}
	
	/**
	 * Set the relationship verifier for this instance
	 * <br>
	 * The verifier can be used to assert a newly added attribute, child, facet 
	 * or parent.
	 */
	@Override
	public DTable setDsfRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
		super.setDsfRelationshipVerifier(relationshipVerifier) ;
		return this;
	}
	
	@Override
	public DTable cloned() {
		return (DTable)super.cloned() ;
	}
	
    /**
     * set namespace for this node.
     * update the nodename based on the given namespace
     * @param namespace
     * @return
     */
    @Override
    public DTable setDsfNamespace(DNamespace namespace){
    	super.setDsfNamespace(namespace) ;
    	return this ;
    }
	
	//
	// Overrides from BaseHtmlElement
	//
	/**
	 * The accesskey attribute's value is used by the user agent as a guide for 
	 * creating a keyboard shortcut that activates or focuses the element.
	 */
	@Override
	public DTable setHtmlAccessKey(final String accessKey) {
		super.setHtmlAccessKey(accessKey) ;
		return this ;
	}
		
	/**
	 * set class name, overwrite current class(es)
	 */
	@Override
	public DTable setHtmlClassName(final String className) {
		super.setHtmlClassName(className) ;
		return this ;
	}
	@Override
	public DTable setHtmlClassName(final CssClassConstant ccc) {
		super.setHtmlClassName(ccc) ;
		return this ;
	}

	/**
	 * The contenteditable  attribute is an enumerated attribute whose keywords 
	 * are the empty string, true, and false. The empty string and the true keyword 
	 * map to the true state. The false keyword maps to the false state. In 
	 * addition, there is a third state, the inherit state, which is the missing 
	 * value default (and the invalid value default).
	 */
	@Override
	public DTable setHtmlContentEditable(final String editable) {
		super.setHtmlContentEditable(editable) ;
		return this ;
	}
	
	/**
	 * The contextmenu  attribute gives the element's context menu. The value 
	 * must be the ID of a menu element in the DOM. If the node that would be 
	 * obtained by the invoking the getElementById() method using the attribute's 
	 * value as the only argument is null or not a menu element, then the element 
	 * has no assigned context menu. Otherwise, the element's assigned context 
	 * menu is the element so identified.
	 */
	@Override
	public DTable setHtmlContextMenu(final String contextMenu) {
		super.setHtmlContextMenu(contextMenu) ;
		return this ;
	}
	
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	@Override
	public DTable setHtmlDir(final String dir) {
		super.setHtmlDir(dir) ;
		return this ;
	}
	
	/**
	 * The draggable attribute is an enumerated attribute. It has three states. 
	 * The first state is true and it has the keyword true. The second state is 
	 * false and it has the keyword false. The third state is auto; it has no 
	 * keywords but it is the missing value default.
	 */
	@Override
	public DTable setHtmlDraggable(final String draggable) {  // true, false, auto
		super.setHtmlDraggable(draggable) ;
		return this ;
	}
	/**
	 * The draggable attribute is an enumerated attribute. It has three states. 
	 * The first state is true and it has the keyword true. The second state is 
	 * false and it has the keyword false. The third state is auto; it has no 
	 * keywords but it is the missing value default.
	 */
	@Override
	public DTable setHtmlDraggable(final boolean draggable) {  // true, false
		super.setHtmlDraggable(draggable) ;
		return this ;
	}
	
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DTable setHtmlHidden(final String hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DTable setHtmlHidden(final boolean hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	@Override
	public DTable setHtmlId(String id) {
		super.setHtmlId(id) ;
		return this ;
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */	
	@Override
	public DTable setHtmlId(CssIdConstant id) {
		super.setHtmlId(id) ;
		return this ;
	}
	
	/**
	 * An element with the item attribute specified creates a new item, a group 
	 * of name-value pairs.  The attribute, if specified, must have a value that 
	 * is an unordered set of unique space-separated tokens representing the 
	 * types (if any) of the item.
	 */
	@Override
	public DTable setHtmlItem(final String item) {
		super.setHtmlItem(item) ;
		return this ;
	}
	
	/**
	 * An element with the itemprop  attribute specified adds one or more name-value 
	 * pairs to its corresponding item. The itemprop attribute, if specified, must 
	 * have a value that is an unordered set of unique space-separated tokens 
	 * representing the names of the name-value pairs that it adds. The attribute's 
	 * value must have at least one token.
	 */
	@Override
	public DTable setHtmlItemProp(final String itemProp) {
		super.setHtmlItemProp(itemProp) ;
		return this ;
	}	
	
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	@Override
	public DTable setHtmlLang(final String lang) {
		super.setHtmlLang(lang) ;
		return this ;
	}
	
	/**
	 * The spellcheck  attribute is an enumerated attribute whose keywords are 
	 * the empty string, true and false. The empty string and the true keyword 
	 * map to the true state. The false keyword maps to the false state. In 
	 * addition, there is a third state, the default state, which is the missing 
	 * value default (and the invalid value default).
	 */
	@Override
	public DTable setHtmlSpellCheck(final String spellCheck) {
		super.setHtmlSpellCheck(spellCheck);
		return this ;
	}
	/**
	 * The spellcheck  attribute is an enumerated attribute whose keywords are 
	 * the empty string, true and false. The empty string and the true keyword 
	 * map to the true state. The false keyword maps to the false state. In 
	 * addition, there is a third state, the default state, which is the missing 
	 * value default (and the invalid value default).
	 */
	@Override
	public DTable setHtmlSpellCheck(final boolean spellCheck) {
		super.setHtmlSpellCheck(spellCheck) ;
		return this ;
	}	

	@Override
	public DTable setHtmlStyleAsString(final String styleString) {
		super.setHtmlStyleAsString(styleString) ;
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 */
	@Override
	public DTable setHtmlStyle(final ICssStyleDeclaration style) {
		super.setHtmlStyle(style) ;
		return this;
	}	
	
	/**
	 * The subject  attribute may be specified on any HTML element to associate 
	 * the element with an element with an item attribute. If the subject 
	 * attribute is specified, the attribute's value must be the ID of an element 
	 * with an item attribute, in the same Document as the element with the 
	 * subject attribute.
	 */
	@Override
	public DTable setHtmlSubject(final String subject) {
		super.setHtmlSubject(subject) ;
		return this ;
	}	
	
	/**
	 * The tabindex content attribute specifies whether the element is focusable, 
	 * whether it can be reached using sequential focus navigation, and the relative 
	 * order of the element for the purposes of sequential focus navigation. The 
	 * name "tab index" comes from the common use of the "tab" key to navigate 
	 * through the focusable elements. The term "tabbing" refers to moving forward 
	 * through the focusable elements that can be reached using sequential focus 
	 * navigation.
	 */
	@Override
	public DTable setHtmlTabIndex(final String tabIndex) {  // HTML 5.0
		super.setHtmlTabIndex(tabIndex) ;
		return this ;
	}
	/**
	 * The tabindex content attribute specifies whether the element is focusable, 
	 * whether it can be reached using sequential focus navigation, and the relative 
	 * order of the element for the purposes of sequential focus navigation. The 
	 * name "tab index" comes from the common use of the "tab" key to navigate 
	 * through the focusable elements. The term "tabbing" refers to moving forward 
	 * through the focusable elements that can be reached using sequential focus 
	 * navigation.
	 */
	@Override
	public DTable setHtmlTabIndex(final int tabIndex) {  // HTML 5.0
		super.setHtmlTabIndex(tabIndex) ;
		return this ;
	}
	
	/**
	 * The title attribute represents advisory information for the element, such 
	 * as would be appropriate for a tooltip. On a link, this could be the title 
	 * or a description of the target resource; on an image, it could be the image 
	 * credit or a description of the image; on a paragraph, it could be a footnote 
	 * or commentary on the text; on a citation, it could be further information 
	 * about the source; and so forth. The value is text.
	 * <p>If this attribute is omitted from an element, then it implies that the 
	 * title attribute of the nearest ancestor HTML element with a title attribute 
	 * set is also relevant to this element.
	 */
	@Override
	public DTable setHtmlTitle(final String title) {
		super.setHtmlTitle(title) ;
		return this ;
	}
	
	//
	// HTML 5.0 API - Events
	//
	/**
	 * The user agent stops fetching the media data before it is completely downloaded.
	 */
	@Override
	public DTable setHtmlOnAbort(final String script) {
		super.setHtmlOnAbort(script) ;
		return this ;
	}
	
	/**
	 * The onblur event occurs when an object loses focus.
	 * not supported on BODY
	 */
	@Override
	public DTable setHtmlOnBlur(final String onBlur) {
		super.setHtmlOnBlur(onBlur) ;
		return this ;
	}
	
	/**
	 * The user agent can resume playback of the media data, but estimates that 
	 * if playback were to be started now, the media resource could not be rendered 
	 * at the current playback rate up to its end without having to stop for 
	 * further buffering of content.
	 */
	@Override
	public DTable setHtmlOnCanPlay(final String script) {
		super.setHtmlOnCanPlay(script) ;
		return this ;
	}
	
	/**
	 *  The user agent estimates that if playback were to be started now, the 
	 *  media resource could be rendered at the current playback rate all the way 
	 *  to its end without having to stop for further buffering. 
	 */
	@Override
	public DTable setHtmlOnCanPlayThrough(final String script) {
		super.setHtmlOnCanPlayThrough(script) ;
		return this ;
	}
	
	// onchange
	@Override
	public DTable setHtmlOnChange(final String script) {
		super.setHtmlOnChange(script) ;
		return this ;
	}
	
	// onclick
	@Override
	public DTable setHtmlOnClick(final String script) {
		super.setHtmlOnClick(script) ;
		return this ;
	}

	// oncontextmenu
	@Override
	public DTable setHtmlOnContextMenu(final String script) {
		super.setHtmlOnContextMenu(script) ;
		return this ;
	}
	
	// ondblclick
	@Override
	public DTable setHtmlOnDblClick(final String script) {
		super.setHtmlOnDblClick(script) ;
		return this ;
	}
	
	// ondrag
	@Override
	public DTable setHtmlOnDrag(final String script) {
		super.setHtmlOnDrag(script) ;
		return this ;
	}
	
	// ondragend
	@Override
	public DTable setHtmlOnDragEnd(final String script) {
		super.setHtmlOnDragEnd(script) ;
		return this ;
	}
	
	// ondragenter
	@Override
	public DTable setHtmlOnDragEnter(final String script) {
		super.setHtmlOnDragEnter(script) ;
		return this ;
	}
	
	// ondragleave
	@Override
	public DTable setHtmlOnDragLeave(final String script) {
		super.setHtmlOnDragLeave(script) ;
		return this ;
	}
	
	// ondragover
	@Override
	public DTable setHtmlOnDragOver(final String script) {
		super.setHtmlOnDragOver(script) ;
		return this ;
	}
	
	// ondragstart
	@Override
	public DTable setHtmlOnDragStart(final String script) {
		super.setHtmlOnDragStart(script) ;
		return this ;
	}
	
	// ondrop
	@Override
	public DTable setHtmlOnDrop(final String script) {
		super.setHtmlOnDrop(script) ;
		return this ;
	}
	
	// ondurationchange
	@Override
	public DTable setHtmlOnDurationChange(final String script) {
		super.setHtmlOnDurationChange(script) ;
		return this ;
	}
	
	/**
	 *  A media element whose networkState was previously not in the 
	 *  NETWORK_EMPTY state has just switched to that state (either because of a
	 *  fatal error during load that's about to be reported, or because the 
	 *  load() method was invoked while the resource selection algorithm was 
	 *  already running, in which case it is fired synchronously during the 
	 *  load() method call). 
	 */
	@Override
	public DTable setHtmlOnEmptied(final String script) {
		super.setHtmlOnEmptied(script) ;
		return this ;
	}
	
	/**
	 * Playback has stopped because the end of the media resource was reached. 
	 */
	@Override
	public DTable setHtmlOnEnded(final String script) {
		super.setHtmlOnEnded(script) ;
		return this ;
	}
	
	/**
	 * An error occurs while fetching the media data. 
	 * not supported on BODY
	 */
	@Override
	public DTable setHtmlOnError(final String script) {
		super.setHtmlOnError(script) ;
		return this ;
	}

	/**
	 * onfocus - not supported on BODY
	 */
	@Override
	public DTable setHtmlOnFocus(final String script) {
		super.setHtmlOnFocus(script) ;
		return this ;
	}
	
	/**
	 * onformchange
	 */
	@Override
	public DTable setHtmlOnFormChange(final String script) {
		super.setHtmlOnFormChange(script) ;
		return this ;
	}
	
	/**
	 * onforminput
	 */
	@Override
	public DTable setHtmlOnFormInput(final String script) {
		super.setHtmlOnFormInput(script) ;
		return this ;
	}
	
	/**
	 * oninput
	 */
	@Override
	public DTable setHtmlOnInput(final String script) {
		super.setHtmlOnInput(script) ;
		return this ;
	}
	
	/**
	 * oninvalid
	 */
	@Override
	public DTable setHtmlOnInvalid(final String script) {
		super.setHtmlOnInvalid(script) ;
		return this ;
	}
	
	/**
	 * onkeydown
	 */
	@Override
	public DTable setHtmlOnKeyDown(final String script) {
		super.setHtmlOnKeyDown(script) ;
		return this ;
	}

	/**
	 * onkeypress
	 */
	@Override
	public DTable setHtmlOnKeyPress(final String script) {
		super.setHtmlOnKeyPress(script) ;
		return this ;
	}
	
	/**
	 * onkeyup
	 */
	@Override
    public DTable setHtmlOnKeyUp(final String script) {
    	super.setHtmlOnKeyUp(script);
    	return this ;
	}
    
	/**
	 * onload
	 */
	@Override
    public DTable setHtmlOnLoad(final String script) {
    	super.setHtmlOnLoad(script) ;
    	return this ;
	}
    
	/**
	 * onloadeddata
	 */
	@Override
    public DTable setHtmlOnLoadedData(final String script) {
    	super.setHtmlOnLoadedData(script) ;
    	return this ;
	}   
    
	/**
	 * onloadedmetadata
	 */
	@Override
    public DTable setHtmlOnLoadedMetadata(final String script) {
    	super.setHtmlOnLoadedMetadata(script) ;
    	return this ;
	}  
	
	/**
	 * onloadstart
	 */
	@Override
    public DTable setHtmlOnLoadStart(final String script) {
    	super.setHtmlOnLoadStart(script) ;
    	return this ;
	} 
    
	/**
	 * onmousedown
	 */
	@Override
	public DTable setHtmlOnMouseDown(final String script){
		super.setHtmlOnMouseDown(script) ;
		return this ;
	}
	
	/**
	 * onmousemove
	 */
	@Override
	public DTable setHtmlOnMouseMove(final String script) {
		super.setHtmlOnMouseMove(script) ;
		return this ;
	}
	
	/**
	 * onmouseout
	 */
	@Override
	public DTable setHtmlOnMouseOut(final String script) {
		super.setHtmlOnMouseOut(script) ;
		return this ;
	}
	
	/**
	 * onmouseover
	 */
	@Override
	public DTable setHtmlOnMouseOver(final String script) {
		super.setHtmlOnMouseOver(script) ;
		return this ;
	}
	
	/**
	 * onmouseup
	 */
	@Override
	public DTable setHtmlOnMouseUp(final String script) {
		super.setHtmlOnMouseUp(script) ;
		return this ;
	}
    
	/**
	 * onmousewheel
	 */
	@Override
	public DTable setHtmlOnMouseWheel(final String script) {
		super.setHtmlOnMouseWheel(script) ;
		return this ;
	}
    
	/**
	 * onpause
	 */
	@Override
	public DTable setHtmlOnPause(final String script) {
		super.setHtmlOnPause(script) ;
		return this ;
	}  
    
	/**
	 * onplay
	 */
	@Override
	public DTable setHtmlOnPlay(final String script) {
		super.setHtmlOnPlay(script) ;
		return this ;
	}
    
	/**
	 * onplaying
	 */
	@Override
	public DTable setHtmlOnPlaying(final String script) {
		super.setHtmlOnPlaying(script) ;
		return this ;
	}
	
	/**
	 * onprogress
	 */
	@Override
	public DTable setHtmlOnProgress(final String script) {
		super.setHtmlOnProgress(script) ;
		return this ;
	}
    
	/**
	 * onratechange
	 */
	@Override
	public DTable setHtmlOnRateChange(final String script) {
		super.setHtmlOnRateChange(script) ;
		return this ;
	}
    
	/**
	 * onreadystatechange
	 */
	@Override
	public DTable setHtmlOnReadyStateChange(final String script) {
		super.setHtmlOnReadyStateChange(script) ;
		return this ;
	}

	/**
	 * onscroll
	 */
	@Override
	public DTable setHtmlOnScroll(final String script) {
		super.setHtmlOnScroll(script) ;
		return this ;
	}
	
	/**
	 * onseeked
	 */
	@Override
	public DTable setHtmlOnSeeked(final String script) {
		super.setHtmlOnSeeked(script) ;
		return this ;
	}
    
	/**
	 * onseeking
	 */
	@Override
	public DTable setHtmlOnSeeking(final String script) {
		super.setHtmlOnSeeking(script) ;
		return this ;
	}
	
	/**
	 * onselect
	 */
	@Override
	public DTable setHtmlOnSelect(final String script) {
		super.setHtmlOnSelect(script) ;
		return this ;
	}
	
	/**
	 * onshow
	 */
	@Override
	public DTable setHtmlOnShow(final String script) {
		super.setHtmlOnShow(script) ;
		return this ;
	}
	
	/**
	 * onstalled
	 */
	@Override
	public DTable setHtmlOnStalled(final String script) {
		super.setHtmlOnStalled(script) ;
		return this ;
	}
	
	/**
	 * onsubmit
	 */
	@Override
	public DTable setHtmlOnSubmit(final String script) {
		super.setHtmlOnSubmit(script) ;
		return this ;
	}
	
	/**
	 * onsuspend
	 */
	@Override
	public DTable setHtmlOnSuspend(final String script) {
		super.setHtmlOnSuspend(script) ;
		return this ;
	}
	
	/**
	 * ontimeupdate
	 */
	@Override
	public DTable setHtmlOnTimeUpdate(final String script) {
		super.setHtmlOnTimeUpdate(script) ;
		return this ;
	}
	
	/**
	 * onvolumechange
	 */
	@Override
	public DTable setHtmlOnVolumeChange(final String script) {
		super.setHtmlOnVolumeChange(script) ;
		return this ;
	}
	
	/**
	 * onwaiting
	 */
	@Override
	public DTable setHtmlOnWaiting(final String script) {
		super.setHtmlOnWaiting(script) ;
		return this ;
	}
	
	//
	// Framework - Event Wiring
	//
	@Override
	public DTable add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		super.add(eventType, handler) ;
		return this ;
	}
	
	@Override
	public DTable add(
		final EventType eventType, 
		final IJsFunc func)
	{
		super.add(eventType, func) ;
		return this ;
	}
	
	@Override
	public DTable add(
		final EventType eventType, 
		final String jsText)
	{
		super.add(eventType, jsText) ;
		return this ;
	}
	
//	@Override
//	public DTable add(final IDomActiveListener listener){
//		super.add(listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DTable add(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.add(eventType, listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DTable removeListener(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.removeListener(eventType, listener) ;
//		return this ;
//	}
	
	//
	// Helpers
	//
	@Override
	public DTable addBr() {
		super.addBr() ;
		return this ;
	}
	
	@Override
	public DTable addBr(final int howMany){
		super.addBr(howMany) ;
		return this ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	@Override
	public DTable addHtmlClassName(final String className) {
		super.addHtmlClassName(className) ;
		return this ;
	}
	
	@Override
	public DTable addHtmlClassName(final CssClassConstant ccc) {
		super.addHtmlClassName(ccc) ;
		return this ;
	}
	
	@Override
	public DTable jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Child Hooks
	//
	public DCaption _caption() {
		 return _caption(-1) ;
	}
	public DCaption _caption(final int count) {
		 return (DCaption)getOrCreate(DCaption.class, count) ;
	}
	public DCaption _caption(final String textValue) {
		return _caption().setHtmlExtTextValue(textValue) ;
	}

	public DColGroup _colgroup() {
		 return _colgroup(-1) ;
	}
	public DColGroup _colgroup(final int count) {
		 return (DColGroup)getOrCreate(DColGroup.class, count) ;
	}

	public DTBody _tbody() {
		 return _tbody(-1) ;
	}
	public DTBody _tbody(final int count) {
		 return (DTBody)getOrCreate(DTBody.class, count) ;
	}

	public DTFoot _tfoot() {
		 return _tfoot(-1) ;
	}
	public DTFoot _tfoot(final int count) {
		 return (DTFoot)getOrCreate(DTFoot.class, count) ;
	}

	public DTHead _thead() {
		 return _thead(-1) ;
	}
	public DTHead _thead(final int count) {
		 return (DTHead)getOrCreate(DTHead.class, count) ;
	}

	public DTr _tr() {
		 return _tr(-1) ;
	}
	public DTr _tr(final int count) {
		 return (DTr)getOrCreate(DTr.class, count) ;
	}
	public DTr _tr(DTd... cells) {
		DTr answer = _tr() ;
		for(DTd cell: cells) {
			answer.add(cell) ;
		}
		return answer ;
	}

}
