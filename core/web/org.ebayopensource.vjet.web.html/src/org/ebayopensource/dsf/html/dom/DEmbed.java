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

import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.common.node.IDNodeRelationshipVerifier;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.css.CssClassConstant;
import org.ebayopensource.dsf.css.CssIdConstant;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.dom.util.TextChildOperationUtil;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.common.Z;

/**
 * Allows documents of any type to be embedded.
 * <p>
 * http://msdn2.microsoft.com/en-us/library/ms535245.aspx
 */

public class DEmbed extends BaseAttrsHtmlElement 
//	implements IEventAttributes, /* HTML 5.0 */ IEmbeddedContent
{
	private static final long serialVersionUID = 1L;
	
	public static final String ALIGN_ABSBOTTOM = "absbottom" ;
	public static final String ALIGN_ABSMIDDLE = "absmiddle" ;
	public static final String ALIGN_BASELINE = "baseline" ;
	public static final String ALIGN_LEFT = "left" ;
	public static final String ALIGN_MIDDLE = "middle" ;
	public static final String ALIGN_RIGHT = "right" ; 
	public static final String ALIGN_TEXTTOP = "texttop" ; 
	public static final String ALIGN_TOP = "top" ; 
	
	public static final String HIDDEN_FALSE = "false" ;
	public static final String HIDDEN_TRUE = "true" ;
	
	public static final String LANGUAGE_JSCRIPT = "Jscript" ;
	public static final String LANGUAGE_JAVASCRIPT = "javascript" ; 
	public static final String LANGUAGE_VBS = "vbs" ; 
	public static final String LANGUAGE_VBSCRIPT = "vbscript" ; 
	public static final String LANGUAGE_XML = "XML" ;
	
	public static final String UNITS_PX = "px" ; // pixels
	public static final String UNITS_EM = "em" ; // relative to the element's font
	
	public static final String UNSELECTABLE_OFF = "off" ; 
	public static final String UNSELECTABLE_ON = "on" ; 

	// 
	// Constructor(s)
	//
	public DEmbed() {
		super(HtmlTypeEnum.EMBED) ; 
	}
	
	public DEmbed(final DHtmlDocument doc) {
		super(doc, HtmlTypeEnum.EMBED);
	}
	
//	public DEmbed(final String jif) {
//		this() ;
//		jif(jif) ;
//	}
	
	public DEmbed(final String src) {
		this() ; 
		setHtmlAttribute(EHtmlAttr.src, src);	
	}
	
	//
	// Framework
	//
	@Override
	public HtmlTypeEnum htmlType() {
		return HtmlTypeEnum.EMBED ;
	}
	
	//
	// HTML Attributes
	//
	/**
	 * Returns how the object is aligned with adjacent text
	 */
	public String getHtmlAlign() { 
		return getHtmlAttribute(EHtmlAttr.align) ;
	} 
	
	public DEmbed setHtmlAlign(final String align) { 
		setHtmlAttribute(EHtmlAttr.align, align) ;
		return this ; 
	}
	
	/**
	 * Returns whether the element must be selected as a whole unit
	 */
	public String getHtmlAtomicSelection() { 
		return getHtmlAttribute(EHtmlAttr.atomicselection) ;
	}
	
	public DEmbed setHtmlAtomicSelection(final String atomic ) { 
		setHtmlAttribute(EHtmlAttr.atomicselection, atomic) ;
		return this ; 
	}
	
	public DEmbed setHtmlAtomicSelection(final boolean atomic) { 
		setHtmlAtomicSelection(String.valueOf(atomic)) ; 
		return this ; 
	}
		
	/**
	 * Returns the height of the object.
	 * Can be an integer, or an integer followed by the percent (%) sign 
	 */
	public String getHtmlHeight() { 
		return getHtmlAttribute(EHtmlAttr.height) ;
	}
	
	public DEmbed setHtmlHeight(final String height) {
		setHtmlAttribute(EHtmlAttr.height, height) ;
		return this ; 
	}
	
	public DEmbed setHtmlHeight(final int height) { 
		setHtmlHeight(String.valueOf(height)) ; 
		return this ; 
	}
	
	/**
	 * Returns whether the object visibly shows that it has focus
	 */
	public String getHtmlHideFocus() { 
		return getHtmlAttribute(EHtmlAttr.hidefocus) ;
	}
	
	public DEmbed setHtmlHideFocus(final String hidefocus) { 
		setHtmlAttribute(EHtmlAttr.hidefocus, hidefocus) ;
		return this ; 
	}
	
	public DEmbed setHtmlHideFocus(final boolean hidefocus) { 
		return setHtmlHideFocus(String.valueOf(hidefocus)) ;
	}
		
	/**
	 * Returns the language in which the script is written
	 */ 
	public String getHtmlLanguage() { 
		return getHtmlAttribute(EHtmlAttr.language) ;
	}
	
	public DEmbed setHtmlLanguage(final String language) { 
		setHtmlAttribute(EHtmlAttr.language, language) ;
		return this ;
	}
	
	/**
	 * Returns the name of the object
	 */
	public String getHtmlName() { 
		return getHtmlAttribute(EHtmlAttr.name) ; 
	}
	
	public DEmbed setHtmlName(final String name) { 
		setHtmlAttribute(EHtmlAttr.name, name) ; 
		return this ; 
	}
	
	/**
	 * Returns the URL of the plug-in used to view an embedded document  
	 */
	public String getHtmlPluginsPage() { 
		return getHtmlAttribute(EHtmlAttr.pluginspage) ; 
	}
	
	public DEmbed setHtmlPluginsPage(final String pluginspage) { 
		setHtmlAttribute(EHtmlAttr.pluginspage, pluginspage) ; 
		return this ; 
	}
	
	/**
	 * Returns the URL that will be loaded by the object
	 */
	public String getHtmlSrc() { 
		return getHtmlAttribute(EHtmlAttr.src) ; 
	}
	
	public DEmbed setHtmlSrc(final String src) { 
		setHtmlAttribute(EHtmlAttr.src, src) ; 
		return this ; 
	}
	
	/**
	 * Returns the MIME type of the object
	 */
	public String getHtmlType() { 
		return getHtmlAttribute(EHtmlAttr.type) ;
	}
	
	public DEmbed setHtmlType(final String type) { 
		setHtmlAttribute(EHtmlAttr.type, type);
		return this; 
	}
	
	/**
	 * Returns the height and width units of the embed object
	 */
	public String getHtmlUnits() {
		return getHtmlAttribute(EHtmlAttr.units) ;
	}
	
	public DEmbed setHtmlUnits(final String units) { 
		setHtmlAttribute(EHtmlAttr.units, units) ; 
		return this ; 
	}
	
	/**
	 * Returns whether the element can be selected
	 */ 
	public String getHtmlUnselectable() { 
		return getHtmlAttribute(EHtmlAttr.unselectable) ;
	}
	
	public DEmbed setHtmlUnselectable(final String unselectable) { 
		setHtmlAttribute(EHtmlAttr.unselectable, unselectable) ; 
		return this ;
	}
	
	/**
	 * Returns the width of the object
	 */ 
	public String getHtmlWidth() { 
		return getHtmlAttribute(EHtmlAttr.width) ; 
	}
	
	public DEmbed setHtmlWidth(final String width) { 
		setHtmlAttribute(EHtmlAttr.width, width) ; 
		return this ; 	
	}
	
	public DEmbed setHtmlWidth(final int width) { 
		setHtmlWidth(String.valueOf(width)) ; 
		return this ; 
	}
	
	// Events
	public DEmbed setHtmlOnActivate(final String script) {
		super.setInlineEvent(EHtmlAttr.onactivate, script, EventType.ACTIVATE);
		return this ; 
	}
	
	public DEmbed setHtmlOnBeforeActivate(final String script) {
		super.setInlineEvent(EHtmlAttr.onbeforeactivate, script, EventType.BEFOREACTIVATE);
		return this ; 
	}
	
	public DEmbed setHtmlOnBeforeCut(final String script) {
		super.setInlineEvent(EHtmlAttr.onbeforecut, script, EventType.BEFORECUT);
		return this ; 
	}
	
	public DEmbed setHtmlOnBeforeDeactivate(final String script) {
		super.setInlineEvent(EHtmlAttr.onbeforedeactivate, script, EventType.BEFOREDEACTIVATE);
		return this ; 
	}
	
	public DEmbed setHtmlOnBeforePaste(final String script) {
		super.setInlineEvent(EHtmlAttr.onbeforepaste, script, EventType.BEFOREPASTE);
		return this ; 
	}
	
	public DEmbed setHtmlOnControlSelect(final String script) {
		super.setInlineEvent(EHtmlAttr.oncontrolselect, script, EventType.CONTROLSELECT);
		return this ; 
	}
	
	public DEmbed setHtmlOnCut(final String script) {
		super.setInlineEvent(EHtmlAttr.oncut, script, EventType.CUT);
		return this ; 
	}
	
	public DEmbed setHtmlOnDeactivate(final String script) {
		super.setInlineEvent(EHtmlAttr.ondeactivate, script, EventType.DEACTIVATE);
		return this ; 
	}
	
	public DEmbed setHtmlOnFocusIn(final String script) {
		super.setInlineEvent(EHtmlAttr.onfocusin, script, EventType.FOCUSIN);
		return this ; 
	}
	
	public DEmbed setHtmlOnFocusOut(final String script) {
		super.setInlineEvent(EHtmlAttr.onfocusout, script, EventType.FOCUSOUT);
		return this ; 
	}
	
	public DEmbed setHtmlOnHelp(final String script) {
		super.setInlineEvent(EHtmlAttr.onhelp, script, EventType.HELP);
		return this ; 
	}

	public DEmbed setHtmlOnLoseCapture(final String script) { 
		super.setInlineEvent(EHtmlAttr.onlosecapture, script, EventType.LOSECAPTURE);
		return this ; 
	}
	
	public DEmbed setHtmlOnMouseEnter(final String script) {
		super.setInlineEvent(EHtmlAttr.onmouseenter, script, EventType.MOUSEENTER);
		return this ; 
	}
	
	public DEmbed setHtmlOnMouseLeave(final String script) {
		super.setInlineEvent(EHtmlAttr.onmouseleave, script, EventType.MOUSELEAVE);
		return this ; 
	}
	
	public DEmbed setHtmlOnMove(final String script) {
		super.setInlineEvent(EHtmlAttr.onmove, script, EventType.MOVE);
		return this ; 
	}
	
	public DEmbed setHtmlOnMoveEnd(final String script) {
		super.setInlineEvent(EHtmlAttr.onmoveend, script, EventType.MOVEEND);
		return this ; 
	}
	
	public DEmbed setHtmlOnMoveStart(final String script) {
		super.setInlineEvent(EHtmlAttr.onmovestart, script, EventType.MOVESTART);
		return this ; 
	}
	
	public DEmbed setHtmlOnPaste(final String script) {
		super.setInlineEvent(EHtmlAttr.onpaste, script, EventType.PASTE);
		return this ; 
	}
	
	public DEmbed setHtmlOnPropertyChange(final String script) { 
		super.setInlineEvent(EHtmlAttr.onpropertychange, script, EventType.PROPERTYCHANGE);
		return this ; 
	}
	
	public DEmbed setHtmlOnResize(final String script) {
		super.setInlineEvent(EHtmlAttr.onresize, script, EventType.RESIZE);
		return this ; 
	}
	
	public DEmbed setHtmlOnResizeEnd(final String script) {
		super.setInlineEvent(EHtmlAttr.onresizeend, script, EventType.RESIZEEND);
		return this ; 
	}
	
	public DEmbed setHtmlOnResizeStart(final String script) {
		super.setInlineEvent(EHtmlAttr.onresizestart, script, EventType.RESIZESTART);
		return this ; 
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt("accesskey", getHtmlAccessKey()) + 
		Z.fmt("align", getHtmlAlign()) + 
		Z.fmt("atomicselection", getHtmlAtomicSelection()) +
		Z.fmt("height", getHtmlHeight()) +
		Z.fmt("hidden", getHtmlHidden())  +
		Z.fmt("hidefocus", getHtmlHideFocus()) +
		Z.fmt("language", getHtmlLanguage()) +
		Z.fmt("name", getHtmlName()) +
		Z.fmt("pluginspage", getHtmlPluginsPage()) +
		Z.fmt("src", getHtmlSrc()) +
		Z.fmt("type", getHtmlType()) + 
		Z.fmt("units", getHtmlUnits()) + 
		Z.fmt("unselectable", getHtmlUnselectable()) +
		Z.fmt("width", getHtmlWidth()) ;
	}
	
	//
	// Extensions
	//
	public DEmbed setHtmlExtTextValue(final String value) {
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
	public DEmbed add(final DNode newChild) throws DOMException {
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
	public DEmbed add(final String value) throws DOMException {
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
	public DEmbed addRaw(final String value) throws DOMException {
		super.addRaw(value) ;
		return this ;
	}
	
	/**
	 * This double dispatch approach provides the control point for the node
	 * to have customized behavior.
	 */
	@Override
	public DEmbed dsfAccept(final IDNodeVisitor visitor) {
		super.dsfAccept(visitor) ;
		return this;
	}
	
	/**
	 * Broadcasts the event to any registered IDsfEventListner's.
	 * The listeners are broadcast to in the order they were maintained.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DEmbed dsfBroadcast(final DsfEvent event) // must not be null
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
	public DEmbed setDsfRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
		super.setDsfRelationshipVerifier(relationshipVerifier) ;
		return this;
	}
	
	@Override
	public DEmbed cloned() {
		return (DEmbed)super.cloned() ;
	}
	
    /**
     * set namespace for this node.
     * update the nodename based on the given namespace
     * @param namespace
     * @return
     */
    @Override
    public DEmbed setDsfNamespace(DNamespace namespace){
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
	public DEmbed setHtmlAccessKey(final String accessKey) {
		super.setHtmlAccessKey(accessKey) ;
		return this ;
	}
		
	/**
	 * set class name, overwrite current class(es)
	 */
	@Override
	public DEmbed setHtmlClassName(final String className) {
		super.setHtmlClassName(className) ;
		return this ;
	}
	@Override
	public DEmbed setHtmlClassName(final CssClassConstant ccc) {
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
	public DEmbed setHtmlContentEditable(final String editable) {
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
	public DEmbed setHtmlContextMenu(final String contextMenu) {
		super.setHtmlContextMenu(contextMenu) ;
		return this ;
	}
	
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	@Override
	public DEmbed setHtmlDir(final String dir) {
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
	public DEmbed setHtmlDraggable(final String draggable) {  // true, false, auto
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
	public DEmbed setHtmlDraggable(final boolean draggable) {  // true, false
		super.setHtmlDraggable(draggable) ;
		return this ;
	}
	
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DEmbed setHtmlHidden(final String hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DEmbed setHtmlHidden(final boolean hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	@Override
	public DEmbed setHtmlId(String id) {
		super.setHtmlId(id) ;
		return this ;
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */	
	@Override
	public DEmbed setHtmlId(CssIdConstant id) {
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
	public DEmbed setHtmlItem(final String item) {
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
	public DEmbed setHtmlItemProp(final String itemProp) {
		super.setHtmlItemProp(itemProp) ;
		return this ;
	}	
	
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	@Override
	public DEmbed setHtmlLang(final String lang) {
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
	public DEmbed setHtmlSpellCheck(final String spellCheck) {
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
	public DEmbed setHtmlSpellCheck(final boolean spellCheck) {
		super.setHtmlSpellCheck(spellCheck) ;
		return this ;
	}	

	@Override
	public DEmbed setHtmlStyleAsString(final String styleString) {
		super.setHtmlStyleAsString(styleString) ;
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 */
	@Override
	public DEmbed setHtmlStyle(final ICssStyleDeclaration style) {
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
	public DEmbed setHtmlSubject(final String subject) {
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
	public DEmbed setHtmlTabIndex(final String tabIndex) {  // HTML 5.0
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
	public DEmbed setHtmlTabIndex(final int tabIndex) {  // HTML 5.0
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
	public DEmbed setHtmlTitle(final String title) {
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
	public DEmbed setHtmlOnAbort(final String script) {
		super.setHtmlOnAbort(script) ;
		return this ;
	}
	
	/**
	 * The onblur event occurs when an object loses focus.
	 * not supported on BODY
	 */
	@Override
	public DEmbed setHtmlOnBlur(final String onBlur) {
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
	public DEmbed setHtmlOnCanPlay(final String script) {
		super.setHtmlOnCanPlay(script) ;
		return this ;
	}
	
	/**
	 *  The user agent estimates that if playback were to be started now, the 
	 *  media resource could be rendered at the current playback rate all the way 
	 *  to its end without having to stop for further buffering. 
	 */
	@Override
	public DEmbed setHtmlOnCanPlayThrough(final String script) {
		super.setHtmlOnCanPlayThrough(script) ;
		return this ;
	}
	
	// onchange
	@Override
	public DEmbed setHtmlOnChange(final String script) {
		super.setHtmlOnChange(script) ;
		return this ;
	}
	
	// onclick
	@Override
	public DEmbed setHtmlOnClick(final String script) {
		super.setHtmlOnClick(script) ;
		return this ;
	}

	// oncontextmenu
	@Override
	public DEmbed setHtmlOnContextMenu(final String script) {
		super.setHtmlOnContextMenu(script) ;
		return this ;
	}
	
	// ondblclick
	@Override
	public DEmbed setHtmlOnDblClick(final String script) {
		super.setHtmlOnDblClick(script) ;
		return this ;
	}
	
	// ondrag
	@Override
	public DEmbed setHtmlOnDrag(final String script) {
		super.setHtmlOnDrag(script) ;
		return this ;
	}
	
	// ondragend
	@Override
	public DEmbed setHtmlOnDragEnd(final String script) {
		super.setHtmlOnDragEnd(script) ;
		return this ;
	}
	
	// ondragenter
	@Override
	public DEmbed setHtmlOnDragEnter(final String script) {
		super.setHtmlOnDragEnter(script) ;
		return this ;
	}
	
	// ondragleave
	@Override
	public DEmbed setHtmlOnDragLeave(final String script) {
		super.setHtmlOnDragLeave(script) ;
		return this ;
	}
	
	// ondragover
	@Override
	public DEmbed setHtmlOnDragOver(final String script) {
		super.setHtmlOnDragOver(script) ;
		return this ;
	}
	
	// ondragstart
	@Override
	public DEmbed setHtmlOnDragStart(final String script) {
		super.setHtmlOnDragStart(script) ;
		return this ;
	}
	
	// ondrop
	@Override
	public DEmbed setHtmlOnDrop(final String script) {
		super.setHtmlOnDrop(script) ;
		return this ;
	}
	
	// ondurationchange
	@Override
	public DEmbed setHtmlOnDurationChange(final String script) {
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
	public DEmbed setHtmlOnEmptied(final String script) {
		super.setHtmlOnEmptied(script) ;
		return this ;
	}
	
	/**
	 * Playback has stopped because the end of the media resource was reached. 
	 */
	@Override
	public DEmbed setHtmlOnEnded(final String script) {
		super.setHtmlOnEnded(script) ;
		return this ;
	}
	
	/**
	 * An error occurs while fetching the media data. 
	 * not supported on BODY
	 */
	@Override
	public DEmbed setHtmlOnError(final String script) {
		super.setHtmlOnError(script) ;
		return this ;
	}

	/**
	 * onfocus - not supported on BODY
	 */
	@Override
	public DEmbed setHtmlOnFocus(final String script) {
		super.setHtmlOnFocus(script) ;
		return this ;
	}
	
	/**
	 * onformchange
	 */
	@Override
	public DEmbed setHtmlOnFormChange(final String script) {
		super.setHtmlOnFormChange(script) ;
		return this ;
	}
	
	/**
	 * onforminput
	 */
	@Override
	public DEmbed setHtmlOnFormInput(final String script) {
		super.setHtmlOnFormInput(script) ;
		return this ;
	}
	
	/**
	 * oninput
	 */
	@Override
	public DEmbed setHtmlOnInput(final String script) {
		super.setHtmlOnInput(script) ;
		return this ;
	}
	
	/**
	 * oninvalid
	 */
	@Override
	public DEmbed setHtmlOnInvalid(final String script) {
		super.setHtmlOnInvalid(script) ;
		return this ;
	}
	
	/**
	 * onkeydown
	 */
	@Override
	public DEmbed setHtmlOnKeyDown(final String script) {
		super.setHtmlOnKeyDown(script) ;
		return this ;
	}

	/**
	 * onkeypress
	 */
	@Override
	public DEmbed setHtmlOnKeyPress(final String script) {
		super.setHtmlOnKeyPress(script) ;
		return this ;
	}
	
	/**
	 * onkeyup
	 */
	@Override
    public DEmbed setHtmlOnKeyUp(final String script) {
    	super.setHtmlOnKeyUp(script);
    	return this ;
	}
    
	/**
	 * onload
	 */
	@Override
    public DEmbed setHtmlOnLoad(final String script) {
    	super.setHtmlOnLoad(script) ;
    	return this ;
	}
    
	/**
	 * onloadeddata
	 */
	@Override
    public DEmbed setHtmlOnLoadedData(final String script) {
    	super.setHtmlOnLoadedData(script) ;
    	return this ;
	}   
    
	/**
	 * onloadedmetadata
	 */
	@Override
    public DEmbed setHtmlOnLoadedMetadata(final String script) {
    	super.setHtmlOnLoadedMetadata(script) ;
    	return this ;
	}  
	
	/**
	 * onloadstart
	 */
	@Override
    public DEmbed setHtmlOnLoadStart(final String script) {
    	super.setHtmlOnLoadStart(script) ;
    	return this ;
	} 
    
	/**
	 * onmousedown
	 */
	@Override
	public DEmbed setHtmlOnMouseDown(final String script){
		super.setHtmlOnMouseDown(script) ;
		return this ;
	}
	
	/**
	 * onmousemove
	 */
	@Override
	public DEmbed setHtmlOnMouseMove(final String script) {
		super.setHtmlOnMouseMove(script) ;
		return this ;
	}
	
	/**
	 * onmouseout
	 */
	@Override
	public DEmbed setHtmlOnMouseOut(final String script) {
		super.setHtmlOnMouseOut(script) ;
		return this ;
	}
	
	/**
	 * onmouseover
	 */
	@Override
	public DEmbed setHtmlOnMouseOver(final String script) {
		super.setHtmlOnMouseOver(script) ;
		return this ;
	}
	
	/**
	 * onmouseup
	 */
	@Override
	public DEmbed setHtmlOnMouseUp(final String script) {
		super.setHtmlOnMouseUp(script) ;
		return this ;
	}
    
	/**
	 * onmousewheel
	 */
	@Override
	public DEmbed setHtmlOnMouseWheel(final String script) {
		super.setHtmlOnMouseWheel(script) ;
		return this ;
	}
    
	/**
	 * onpause
	 */
	@Override
	public DEmbed setHtmlOnPause(final String script) {
		super.setHtmlOnPause(script) ;
		return this ;
	}  
    
	/**
	 * onplay
	 */
	@Override
	public DEmbed setHtmlOnPlay(final String script) {
		super.setHtmlOnPlay(script) ;
		return this ;
	}
    
	/**
	 * onplaying
	 */
	@Override
	public DEmbed setHtmlOnPlaying(final String script) {
		super.setHtmlOnPlaying(script) ;
		return this ;
	}
	
	/**
	 * onprogress
	 */
	@Override
	public DEmbed setHtmlOnProgress(final String script) {
		super.setHtmlOnProgress(script) ;
		return this ;
	}
    
	/**
	 * onratechange
	 */
	@Override
	public DEmbed setHtmlOnRateChange(final String script) {
		super.setHtmlOnRateChange(script) ;
		return this ;
	}
    
	/**
	 * onreadystatechange
	 */
	@Override
	public DEmbed setHtmlOnReadyStateChange(final String script) {
		super.setHtmlOnReadyStateChange(script) ;
		return this ;
	}

	/**
	 * onscroll
	 */
	@Override
	public DEmbed setHtmlOnScroll(final String script) {
		super.setHtmlOnScroll(script) ;
		return this ;
	}
	
	/**
	 * onseeked
	 */
	@Override
	public DEmbed setHtmlOnSeeked(final String script) {
		super.setHtmlOnSeeked(script) ;
		return this ;
	}
    
	/**
	 * onseeking
	 */
	@Override
	public DEmbed setHtmlOnSeeking(final String script) {
		super.setHtmlOnSeeking(script) ;
		return this ;
	}
	
	/**
	 * onselect
	 */
	@Override
	public DEmbed setHtmlOnSelect(final String script) {
		super.setHtmlOnSelect(script) ;
		return this ;
	}
	
	/**
	 * onshow
	 */
	@Override
	public DEmbed setHtmlOnShow(final String script) {
		super.setHtmlOnShow(script) ;
		return this ;
	}
	
	/**
	 * onstalled
	 */
	@Override
	public DEmbed setHtmlOnStalled(final String script) {
		super.setHtmlOnStalled(script) ;
		return this ;
	}
	
	/**
	 * onsubmit
	 */
	@Override
	public DEmbed setHtmlOnSubmit(final String script) {
		super.setHtmlOnSubmit(script) ;
		return this ;
	}
	
	/**
	 * onsuspend
	 */
	@Override
	public DEmbed setHtmlOnSuspend(final String script) {
		super.setHtmlOnSuspend(script) ;
		return this ;
	}
	
	/**
	 * ontimeupdate
	 */
	@Override
	public DEmbed setHtmlOnTimeUpdate(final String script) {
		super.setHtmlOnTimeUpdate(script) ;
		return this ;
	}
	
	/**
	 * onvolumechange
	 */
	@Override
	public DEmbed setHtmlOnVolumeChange(final String script) {
		super.setHtmlOnVolumeChange(script) ;
		return this ;
	}
	
	/**
	 * onwaiting
	 */
	@Override
	public DEmbed setHtmlOnWaiting(final String script) {
		super.setHtmlOnWaiting(script) ;
		return this ;
	}
	
	//
	// Framework - Event Wiring
	//
	@Override
	public DEmbed add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		super.add(eventType, handler) ;
		return this ;
	}
	
	@Override
	public DEmbed add(
		final EventType eventType, 
		final IJsFunc func)
	{
		super.add(eventType, func) ;
		return this ;
	}
	
	@Override
	public DEmbed add(
		final EventType eventType, 
		final String jsText)
	{
		super.add(eventType, jsText) ;
		return this ;
	}
	
//	@Override
//	public DEmbed add(final IDomActiveListener listener){
//		super.add(listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DEmbed add(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.add(eventType, listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DEmbed removeListener(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.removeListener(eventType, listener) ;
//		return this ;
//	}
	
	//
	// Helpers
	//
	@Override
	public DEmbed addBr() {
		super.addBr() ;
		return this ;
	}
	
	@Override
	public DEmbed addBr(final int howMany){
		super.addBr(howMany) ;
		return this ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	@Override
	public DEmbed addHtmlClassName(final String className) {
		super.addHtmlClassName(className) ;
		return this ;
	}
	
	@Override
	public DEmbed addHtmlClassName(final CssClassConstant ccc) {
		super.addHtmlClassName(ccc) ;
		return this ;
	}
	
	@Override
	public DEmbed jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Child Hooks (none)
	//
}
