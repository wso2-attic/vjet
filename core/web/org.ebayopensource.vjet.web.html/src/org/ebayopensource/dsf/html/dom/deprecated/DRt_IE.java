/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.deprecated;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.node.IDNodeRelationshipVerifier;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.css.CssClassConstant;
import org.ebayopensource.dsf.css.CssIdConstant;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.dom.util.TextChildOperationUtil;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.common.Z;

/**
 * IE Specific tag.  Designates the ruby text for use with the Ruby element; 
 * the text that appears as the annotation.
 * 
 * http://msdn2.microsoft.com/en-us/library/ms535885.aspx
 * 
 * @see DRuby_IE
 */
@Deprecated
public class DRt_IE extends BaseHtmlElement implements IEventAttributes{
	private static final long serialVersionUID = 1L;
	
	public static final String LANGUAGE_JSCRIPT = "Jscript" ;
	public static final String LANGUAGE_JAVASCRIPT = "javascript" ; 
	public static final String LANGUAGE_VBS = "vbs" ; 
	public static final String LANGUAGE_VBSCRIPT = "vbscript" ; 
	public static final String LANGUAGE_XML = "XML" ;
	
	public static final String UNSELECTABLE_ON = "on" ; 
	public static final String UNSELECTABLE_OFF = "off" ; 
	
	// Constructors
	public DRt_IE() {
		super(null) ; //HtmlTypeEnum.RT_IE);
		throw new DsfRuntimeException("Not implemented") ;
	}

	public DRt_IE(final String textValue) {
		this() ;
		setHtmlExtTextValue(textValue) ;
	}
	
	//
	// Framework
	//
	@Override
	public HtmlTypeEnum htmlType() {
		throw new DsfRuntimeException("Not implemented") ;
		//return HtmlTypeEnum.RT_IE ;
	}
	
	//
	// HTML Attributes
	//	
	
	/**
	 * Returns whether the element and its contents must be selected as a 
	 * whole unit 
	 */
	public String getHtmlAtomicSelection() { 
		return null;//getHtmlAttribute(EHtmlAttr.atomicselection) ;
	}
	public DRt_IE setHtmlAtomicSelection(final String atomic ) { 
//		setHtmlAttribute(EHtmlAttr.atomicselection, atomic) ;
		return this ; 
	}
	public DRt_IE setHtmlAtomicSelection(final boolean atomic) { 
		setHtmlAtomicSelection(String.valueOf(atomic)) ; 
		return this ; 
	}
	
	/**
	 * Returns whether the object visibly shows that it has focus 
	 */
	public String getHtmlHideFocus() { 
		return null;//getHtmlAttribute(EHtmlAttr.hidefocus) ;
	}	
	public DRt_IE setHtmlHideFocus(final String hidefocus) { 
//		setHtmlAttribute(EHtmlAttr.hidefocus, hidefocus) ;
		return this ; 
	}	
	public DRt_IE setHtmlHideFocus(final boolean hidefocus) { 
		return setHtmlHideFocus(String.valueOf(hidefocus)) ;
	}
	
	/**
	 * Returns the language in which the script is written
	 */
	public String getHtmlLanguage() { 
		return null;//getHtmlAttribute(EHtmlAttr.language) ;
	}	
	public DRt_IE setHtmlLanguage(final String language) { 
//		setHtmlAttribute(EHtmlAttr.language, language) ;
		return this ;
	}
	
	/**
	 * Returns the name of the object
	 */
	public String getHtmlName() { 
		return null;//getHtmlAttribute(EHtmlAttr.name) ;
	}
	public DRt_IE setHtmlName(final String name) { 
//		setHtmlAttribute(EHtmlAttr.name, name) ;
		return this ; 
	}
	
	/** 
	 * Returns whether the element can be selected
	 */
	public String getHtmlUnselectable() { 
		return null;//getHtmlAttribute(EHtmlAttr.unselectable) ;
	}	
	public DRt_IE setHtmlUnselectable(final String unselectable) { 
//		setHtmlAttribute(EHtmlAttr.unselectable, unselectable) ; 
		return this ;
	}
	
	// Events
	public DRt_IE setHtmlOnActivate(final String script){
////		setInlineEvent(EHtmlAttr.onactivate, script, EventType.ONACTIVATE);
		return this ;
	} 
	
	public DRt_IE setHtmlOnAfterUpdate(final String script) {
////		setInlineEvent(EHtmlAttr.onafterupdate, script, EventType.ONAFTERUPDATE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnBeforeActivate(final String script) {
////		setInlineEvent(EHtmlAttr.onbeforeactivate, script, EventType.ONBEFOREACTIVATE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnBeforeCut(final String script) {
////		setInlineEvent(EHtmlAttr.onbeforecut, script, EventType.ONBEFORECUT);
		return this ; 
	}
	
	public DRt_IE setHtmlOnBeforeDeactivate(final String script) {
////		setInlineEvent(EHtmlAttr.onbeforedeactivate, script, EventType.ONBEFOREDEACTIVATE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnBeforeEditFocus(final String script) {
////		setInlineEvent(EHtmlAttr.onbeforeeditfocus, script, EventType.ONBEFOREEDITFOCUS);
		return this ; 
	}
	
	public DRt_IE setHtmlOnBeforePaste(final String script) {
////		setInlineEvent(EHtmlAttr.onbeforepaste, script, EventType.ONBEFOREPASTE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnBeforeUpdate(final String script) {
////		setInlineEvent(EHtmlAttr.onbeforeupdate, script, EventType.ONBEFOREUPDATE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnControlSelect(final String script) {
////		setInlineEvent(EHtmlAttr.oncontrolselect, script, EventType.ONCONTROLSELECT);
		return this ; 
	}
	
	public DRt_IE setHtmlOnCut(final String script) {
////		setInlineEvent(EHtmlAttr.oncut, script, EventType.ONCUT);
		return this ; 
	}
	
	@Override
	public DRt_IE setHtmlOnDoubleClick(final String script) {
		super.setHtmlOnDoubleClick(script) ;
		return this ; 
	}
	
	public DRt_IE setHtmlOnDeactivate(final String script) {
////		setInlineEvent(EHtmlAttr.ondeactivate, script, EventType.ONDEACTIVATE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnErrorUpdate(final String script) {
////		setInlineEvent(EHtmlAttr.onerrorupdate, script, EventType.ONERRORUPDATE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnFilterChange(final String script) {
////		setInlineEvent(EHtmlAttr.onfilterchange, script, EventType.ONFILTERCHANGE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnFocusIn(final String script) {
////		setInlineEvent(EHtmlAttr.onfocusin, script, EventType.ONFOCUSIN);
		return this ; 
	}
	
	public DRt_IE setHtmlOnFocusOut(final String script) {
////		setInlineEvent(EHtmlAttr.onfocusout, script, EventType.ONFOCUSOUT);
		return this ; 
	}
	
	public DRt_IE setHtmlOnHelp(final String script) {
////		setInlineEvent(EHtmlAttr.onhelp, script, EventType.ONHELP);
		return this ; 
	}
	
	public DRt_IE setHtmlOnMouseEnter(final String script) {
////		setInlineEvent(EHtmlAttr.onmouseenter, script, EventType.ONMOUSEENTER);
		return this ; 
	}
	
	public DRt_IE setHtmlOnMouseLeave(final String script) {
////		setInlineEvent(EHtmlAttr.onmouseleave, script, EventType.ONMOUSELEAVE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnMove(final String script) {
////		setInlineEvent(EHtmlAttr.onmove, script, EventType.ONMOVE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnMoveEnd(final String script) {
////		setInlineEvent(EHtmlAttr.onmoveend, script, EventType.ONMOVEEND);
		return this ; 
	}
	
	public DRt_IE setHtmlOnMoveStart(final String script) {
////		setInlineEvent(EHtmlAttr.onmovestart, script, EventType.ONMOVESTART);
		return this ; 
	}
	
	public DRt_IE setHtmlOnPaste(final String script) {
////		setInlineEvent(EHtmlAttr.onpaste, script, EventType.ONPASTE);
		return this ; 
	}
	
	public DRt_IE setHtmlOnResizeEnd(final String script) {
////		setInlineEvent(EHtmlAttr.onresizeend, script, EventType.ONRESIZEEND);
		return this ; 
	}
	
	public DRt_IE setHtmlOnResizeStart(final String script) {
//		setInlineEvent(EHtmlAttr.onresizestart, script, EventType.ONRESIZESTART);
		return this ; 
	}
	
	public DRt_IE setHtmlOnSelectStart(final String script) {
//		setInlineEvent(EHtmlAttr.onselectstart, script, EventType.ONSELECTSTART);
		return this ; 
	}

	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt("accesskey", getHtmlAccessKey()) + 
		Z.fmt("contenteditable", getHtmlContentEditable()) + 
		Z.fmt("hidefocus", getHtmlHideFocus()) + 
		Z.fmt("language", getHtmlLanguage()) + 
		Z.fmt("tabindex", getHtmlTabIndex()) + 
		Z.fmt("unselectable", getHtmlUnselectable()) ;		
	}
	
	//
	// Extensions
	//
	public DRt_IE setHtmlExtTextValue(final String value) {
		TextChildOperationUtil.setTextValue(this, value) ;
		return this ;		
	}
	public String getHtmlExtTextValue() {
		return TextChildOperationUtil.getTextValue(this) ;
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
	public DRt_IE add(final DNode newChild) throws DOMException {
		super.add(newChild) ;
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
	public DRt_IE add(final String value) throws DOMException {
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
	public DRt_IE addRaw(final String value) throws DOMException {
		super.addRaw(value) ;
		return this ;
	}
	
	/**
	 * This double dispatch approach provides the control point for the node
	 * to have customized behavior.
	 */
	@Override
	public DRt_IE dsfAccept(final IDNodeVisitor visitor) {
		super.dsfAccept(visitor) ;
		return this;
	}
	
	/**
	 * Broadcasts the event to any registered IDsfEventListner's.
	 * The listeners are broadcast to in the order they were maintained.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DRt_IE dsfBroadcast(final DsfEvent event) // must not be null
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
	public DRt_IE setDsfRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
		super.setDsfRelationshipVerifier(relationshipVerifier) ;
		return this;
	}
	
	@Override
	public DRt_IE cloned() {
		return (DRt_IE)super.cloned() ;
	}
	
    /**
     * set namespace for this node.
     * update the nodename based on the given namespace
     * @param namespace
     * @return
     */
    @Override
    public DRt_IE setDsfNamespace(DNamespace namespace){
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
	public DRt_IE setHtmlAccessKey(final String accessKey) {
		super.setHtmlAccessKey(accessKey) ;
		return this ;
	}
		
	/**
	 * set class name, overwrite current class(es)
	 */
	@Override
	public DRt_IE setHtmlClassName(final String className) {
		super.setHtmlClassName(className) ;
		return this ;
	}
	@Override
	public DRt_IE setHtmlClassName(final CssClassConstant ccc) {
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
	public DRt_IE setHtmlContentEditable(final String editable) {
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
	public DRt_IE setHtmlContextMenu(final String contextMenu) {
		super.setHtmlContextMenu(contextMenu) ;
		return this ;
	}
	
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	@Override
	public DRt_IE setHtmlDir(final String dir) {
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
	public DRt_IE setHtmlDraggable(final String draggable) {  // true, false, auto
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
	public DRt_IE setHtmlDraggable(final boolean draggable) {  // true, false
		super.setHtmlDraggable(draggable) ;
		return this ;
	}
	
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DRt_IE setHtmlHidden(final String hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DRt_IE setHtmlHidden(final boolean hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	@Override
	public DRt_IE setHtmlId(String id) {
		super.setHtmlId(id) ;
		return this ;
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */	
	@Override
	public DRt_IE setHtmlId(CssIdConstant id) {
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
	public DRt_IE setHtmlItem(final String item) {
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
	public DRt_IE setHtmlItemProp(final String itemProp) {
		super.setHtmlItemProp(itemProp) ;
		return this ;
	}	
	
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	@Override
	public DRt_IE setHtmlLang(final String lang) {
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
	public DRt_IE setHtmlSpellCheck(final String spellCheck) {
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
	public DRt_IE setHtmlSpellCheck(final boolean spellCheck) {
		super.setHtmlSpellCheck(spellCheck) ;
		return this ;
	}	

	@Override
	public DRt_IE setHtmlStyleAsString(final String styleString) {
		super.setHtmlStyleAsString(styleString) ;
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 */
	@Override
	public DRt_IE setHtmlStyle(final ICssStyleDeclaration style) {
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
	public DRt_IE setHtmlSubject(final String subject) {
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
	public DRt_IE setHtmlTabIndex(final String tabIndex) {  // HTML 5.0
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
	public DRt_IE setHtmlTabIndex(final int tabIndex) {  // HTML 5.0
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
	public DRt_IE setHtmlTitle(final String title) {
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
	public DRt_IE setHtmlOnAbort(final String script) {
		super.setHtmlOnAbort(script) ;
		return this ;
	}
	
	/**
	 * The onblur event occurs when an object loses focus.
	 * not supported on BODY
	 */
	@Override
	public DRt_IE setHtmlOnBlur(final String onBlur) {
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
	public DRt_IE setHtmlOnCanPlay(final String script) {
		super.setHtmlOnCanPlay(script) ;
		return this ;
	}
	
	/**
	 *  The user agent estimates that if playback were to be started now, the 
	 *  media resource could be rendered at the current playback rate all the way 
	 *  to its end without having to stop for further buffering. 
	 */
	@Override
	public DRt_IE setHtmlOnCanPlayThrough(final String script) {
		super.setHtmlOnCanPlayThrough(script) ;
		return this ;
	}
	
	// onchange
	@Override
	public DRt_IE setHtmlOnChange(final String script) {
		super.setHtmlOnChange(script) ;
		return this ;
	}
	
	// onclick
	@Override
	public DRt_IE setHtmlOnClick(final String script) {
		super.setHtmlOnClick(script) ;
		return this ;
	}

	// oncontextmenu
	@Override
	public DRt_IE setHtmlOnContextMenu(final String script) {
		super.setHtmlOnContextMenu(script) ;
		return this ;
	}
	
	// ondblclick
	@Override
	public DRt_IE setHtmlOnDblClick(final String script) {
		super.setHtmlOnDblClick(script) ;
		return this ;
	}
	
	// ondrag
	@Override
	public DRt_IE setHtmlOnDrag(final String script) {
		super.setHtmlOnDrag(script) ;
		return this ;
	}
	
	// ondragend
	@Override
	public DRt_IE setHtmlOnDragEnd(final String script) {
		super.setHtmlOnDragEnd(script) ;
		return this ;
	}
	
	// ondragenter
	@Override
	public DRt_IE setHtmlOnDragEnter(final String script) {
		super.setHtmlOnDragEnter(script) ;
		return this ;
	}
	
	// ondragleave
	@Override
	public DRt_IE setHtmlOnDragLeave(final String script) {
		super.setHtmlOnDragLeave(script) ;
		return this ;
	}
	
	// ondragover
	@Override
	public DRt_IE setHtmlOnDragOver(final String script) {
		super.setHtmlOnDragOver(script) ;
		return this ;
	}
	
	// ondragstart
	@Override
	public DRt_IE setHtmlOnDragStart(final String script) {
		super.setHtmlOnDragStart(script) ;
		return this ;
	}
	
	// ondrop
	@Override
	public DRt_IE setHtmlOnDrop(final String script) {
		super.setHtmlOnDrop(script) ;
		return this ;
	}
	
	// ondurationchange
	@Override
	public DRt_IE setHtmlOnDurationChange(final String script) {
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
	public DRt_IE setHtmlOnEmptied(final String script) {
		super.setHtmlOnEmptied(script) ;
		return this ;
	}
	
	/**
	 * Playback has stopped because the end of the media resource was reached. 
	 */
	@Override
	public DRt_IE setHtmlOnEnded(final String script) {
		super.setHtmlOnEnded(script) ;
		return this ;
	}
	
	/**
	 * An error occurs while fetching the media data. 
	 * not supported on BODY
	 */
	@Override
	public DRt_IE setHtmlOnError(final String script) {
		super.setHtmlOnError(script) ;
		return this ;
	}

	/**
	 * onfocus - not supported on BODY
	 */
	@Override
	public DRt_IE setHtmlOnFocus(final String script) {
		super.setHtmlOnFocus(script) ;
		return this ;
	}
	
	/**
	 * onformchange
	 */
	@Override
	public DRt_IE setHtmlOnFormChange(final String script) {
		super.setHtmlOnFormChange(script) ;
		return this ;
	}
	
	/**
	 * onforminput
	 */
	@Override
	public DRt_IE setHtmlOnFormInput(final String script) {
		super.setHtmlOnFormInput(script) ;
		return this ;
	}
	
	/**
	 * oninput
	 */
	@Override
	public DRt_IE setHtmlOnInput(final String script) {
		super.setHtmlOnInput(script) ;
		return this ;
	}
	
	/**
	 * oninvalid
	 */
	@Override
	public DRt_IE setHtmlOnInvalid(final String script) {
		super.setHtmlOnInvalid(script) ;
		return this ;
	}
	
	/**
	 * onkeydown
	 */
	@Override
	public DRt_IE setHtmlOnKeyDown(final String script) {
		super.setHtmlOnKeyDown(script) ;
		return this ;
	}

	/**
	 * onkeypress
	 */
	@Override
	public DRt_IE setHtmlOnKeyPress(final String script) {
		super.setHtmlOnKeyPress(script) ;
		return this ;
	}
	
	/**
	 * onkeyup
	 */
	@Override
    public DRt_IE setHtmlOnKeyUp(final String script) {
    	super.setHtmlOnKeyUp(script);
    	return this ;
	}
    
	/**
	 * onload
	 */
	@Override
    public DRt_IE setHtmlOnLoad(final String script) {
    	super.setHtmlOnLoad(script) ;
    	return this ;
	}
    
	/**
	 * onloadeddata
	 */
	@Override
    public DRt_IE setHtmlOnLoadedData(final String script) {
    	super.setHtmlOnLoadedData(script) ;
    	return this ;
	}   
    
	/**
	 * onloadedmetadata
	 */
	@Override
    public DRt_IE setHtmlOnLoadedMetadata(final String script) {
    	super.setHtmlOnLoadedMetadata(script) ;
    	return this ;
	}  
	
	/**
	 * onloadstart
	 */
	@Override
    public DRt_IE setHtmlOnLoadStart(final String script) {
    	super.setHtmlOnLoadStart(script) ;
    	return this ;
	} 
    
	/**
	 * onmousedown
	 */
	@Override
	public DRt_IE setHtmlOnMouseDown(final String script){
		super.setHtmlOnMouseDown(script) ;
		return this ;
	}
	
	/**
	 * onmousemove
	 */
	@Override
	public DRt_IE setHtmlOnMouseMove(final String script) {
		super.setHtmlOnMouseMove(script) ;
		return this ;
	}
	
	/**
	 * onmouseout
	 */
	@Override
	public DRt_IE setHtmlOnMouseOut(final String script) {
		super.setHtmlOnMouseOut(script) ;
		return this ;
	}
	
	/**
	 * onmouseover
	 */
	@Override
	public DRt_IE setHtmlOnMouseOver(final String script) {
		super.setHtmlOnMouseOver(script) ;
		return this ;
	}
	
	/**
	 * onmouseup
	 */
	@Override
	public DRt_IE setHtmlOnMouseUp(final String script) {
		super.setHtmlOnMouseUp(script) ;
		return this ;
	}
    
	/**
	 * onmousewheel
	 */
	@Override
	public DRt_IE setHtmlOnMouseWheel(final String script) {
		super.setHtmlOnMouseWheel(script) ;
		return this ;
	}
    
	/**
	 * onpause
	 */
	@Override
	public DRt_IE setHtmlOnPause(final String script) {
		super.setHtmlOnPause(script) ;
		return this ;
	}  
    
	/**
	 * onplay
	 */
	@Override
	public DRt_IE setHtmlOnPlay(final String script) {
		super.setHtmlOnPlay(script) ;
		return this ;
	}
    
	/**
	 * onplaying
	 */
	@Override
	public DRt_IE setHtmlOnPlaying(final String script) {
		super.setHtmlOnPlaying(script) ;
		return this ;
	}
	
	/**
	 * onprogress
	 */
	@Override
	public DRt_IE setHtmlOnProgress(final String script) {
		super.setHtmlOnProgress(script) ;
		return this ;
	}
    
	/**
	 * onratechange
	 */
	@Override
	public DRt_IE setHtmlOnRateChange(final String script) {
		super.setHtmlOnRateChange(script) ;
		return this ;
	}
    
	/**
	 * onreadystatechange
	 */
	@Override
	public DRt_IE setHtmlOnReadyStateChange(final String script) {
		super.setHtmlOnReadyStateChange(script) ;
		return this ;
	}

	/**
	 * onscroll
	 */
	@Override
	public DRt_IE setHtmlOnScroll(final String script) {
		super.setHtmlOnScroll(script) ;
		return this ;
	}
	
	/**
	 * onseeked
	 */
	@Override
	public DRt_IE setHtmlOnSeeked(final String script) {
		super.setHtmlOnSeeked(script) ;
		return this ;
	}
    
	/**
	 * onseeking
	 */
	@Override
	public DRt_IE setHtmlOnSeeking(final String script) {
		super.setHtmlOnSeeking(script) ;
		return this ;
	}
	
	/**
	 * onselect
	 */
	@Override
	public DRt_IE setHtmlOnSelect(final String script) {
		super.setHtmlOnSelect(script) ;
		return this ;
	}
	
	/**
	 * onshow
	 */
	@Override
	public DRt_IE setHtmlOnShow(final String script) {
		super.setHtmlOnShow(script) ;
		return this ;
	}
	
	/**
	 * onstalled
	 */
	@Override
	public DRt_IE setHtmlOnStalled(final String script) {
		super.setHtmlOnStalled(script) ;
		return this ;
	}
	
	/**
	 * onsubmit
	 */
	@Override
	public DRt_IE setHtmlOnSubmit(final String script) {
		super.setHtmlOnSubmit(script) ;
		return this ;
	}
	
	/**
	 * onsuspend
	 */
	@Override
	public DRt_IE setHtmlOnSuspend(final String script) {
		super.setHtmlOnSuspend(script) ;
		return this ;
	}
	
	/**
	 * ontimeupdate
	 */
	@Override
	public DRt_IE setHtmlOnTimeUpdate(final String script) {
		super.setHtmlOnTimeUpdate(script) ;
		return this ;
	}
	
	/**
	 * onvolumechange
	 */
	@Override
	public DRt_IE setHtmlOnVolumeChange(final String script) {
		super.setHtmlOnVolumeChange(script) ;
		return this ;
	}
	
	/**
	 * onwaiting
	 */
	@Override
	public DRt_IE setHtmlOnWaiting(final String script) {
		super.setHtmlOnWaiting(script) ;
		return this ;
	}
	
	//
	// Framework - Event Wiring
	//
	@Override
	public DRt_IE add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		super.add(eventType, handler) ;
		return this ;
	}
	
	@Override
	public DRt_IE add(
		final EventType eventType, 
		final IJsFunc func)
	{
		super.add(eventType, func) ;
		return this ;
	}
	
	@Override
	public DRt_IE add(
		final EventType eventType, 
		final String jsText)
	{
		super.add(eventType, jsText) ;
		return this ;
	}
	
//	@Override
//	public DRt_IE add(final IDapEventListener listener){
//		super.add(listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DRt_IE add(
//		final EventType eventType, final IDapEventListener listener)
//	{
//		super.add(eventType, listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DRt_IE removeListener(
//		final EventType eventType, final IDapEventListener listener)
//	{
//		super.removeListener(eventType, listener) ;
//		return this ;
//	}
	
	//
	// Helpers
	//
	@Override
	public DRt_IE addBr() {
		super.addBr() ;
		return this ;
	}
	
	@Override
	public DRt_IE addBr(final int howMany){
		super.addBr(howMany) ;
		return this ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	@Override
	public DRt_IE addHtmlClassName(final String className) {
		super.addHtmlClassName(className) ;
		return this ;
	}
	
	@Override
	public DRt_IE addHtmlClassName(final CssClassConstant ccc) {
		super.addHtmlClassName(ccc) ;
		return this ;
	}
}

