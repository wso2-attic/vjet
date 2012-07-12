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
 * IE Specific tag.  Scrolling text.
 * 
 * http://msdn2.microsoft.com/en-us/library/ms535851.aspx
 * 
 */
public class DMarquee extends BaseAttrsHtmlElement /* implements IEventAttributes */{
	private static final long serialVersionUID = 1L;
	
	public static final String BEHAVIOR_SCROLL = "scroll" ; 
	public static final String BEHAVIOR_ALTERNATE = "alternate" ; 
	public static final String BEHAVIOR_SLIDE = "slide" ; 
	
	public static final String DATAFORMATAS_TEXT = "text" ; 
	public static final String DATAFORMATAS_HTML = "html" ;  
	
	public static final String DIRECTION_LEFT = "left" ; 
	public static final String DIRECTION_RIGHT = "right" ; 
	public static final String DIRECTION_DOWN = "down" ; 
	public static final String DIRECTION_UP = "up" ; 
	
	public static final String LANGUAGE_JSCRIPT = "Jscript" ;
	public static final String LANGUAGE_JAVASCRIPT = "javascript" ; 
	public static final String LANGUAGE_VBS = "vbs" ; 
	public static final String LANGUAGE_VBSCRIPT = "vbscript" ; 
	public static final String LANGUAGE_XML = "XML" ;
	
	public static final String UNSELECTABLE_OFF = "off" ; 
	public static final String UNSELECTABLE_ON = "on" ; 
	
	// Constructors
	public DMarquee() {
		super(HtmlTypeEnum.MARQUEE);
	}
	
	public DMarquee(final DHtmlDocument doc) {
		super(doc, HtmlTypeEnum.MARQUEE);
	}
	
//	public DMarquee(final String jif) {
//		this() ;
//		jif(jif) ;
//	}
	
	public DMarquee(final String textValue) {
		this() ;
		setHtmlExtTextValue(textValue) ;
	}
	
	//
	// Framework
	//
	@Override
	public HtmlTypeEnum htmlType() {
		return HtmlTypeEnum.MARQUEE ;
	}
	
	//
	// HTML Attributes
	//
	
	/**
	 * Returns whether the element must be selected as a whole unit
	 */
	public String getHtmlAtomicSelection() { 
		return getHtmlAttribute(EHtmlAttr.atomicselection) ;
	}	
	public DMarquee setHtmlAtomicSelection(final String atomic ) { 
		setHtmlAttribute(EHtmlAttr.atomicselection, atomic) ;
		return this ; 
	}
	public DMarquee setHtmlAtomicSelection(final boolean atomic) { 
		setHtmlAtomicSelection(String.valueOf(atomic)) ; 
		return this ; 
	}
	
	/**
	 * Returns how the text scrolls in the marquee
	 */
	public String getHtmlBehavior() { 
		return getHtmlAttribute(EHtmlAttr.behavior) ;
	}	
	public DMarquee setHtmlBehavior(final String behavior) {
		setHtmlAttribute(EHtmlAttr.behavior, behavior) ;
		return this ; 
	}

	/**
	 * Returns the background color behind the object
	 * Can specify a color name or an RGB value 
	 */
	public String getHtmlBgColor() { 
		return getHtmlAttribute(EHtmlAttr.bgcolor) ; 
	}	
	public DMarquee setHtmlBgColor(final String bgcolor) { 
		setHtmlAttribute(EHtmlAttr.bgcolor, bgcolor) ; 
		return this ; 
	}
	
	/**
	 * Returns which field of a given data source to bind to the specified 
	 * object
	 */ 
	public String getHtmlDataFld() { 
		return getHtmlAttribute(EHtmlAttr.datafld) ; 
	}	
	public DMarquee setHtmlDataFld(final String datafld) {
		setHtmlAttribute(EHtmlAttr.datafld, datafld) ; 
		return this ; 
	}
	
	/**
	 * Returns how to render the data supplied to the object
	 */
	 
	public String getHtmlDataFormatAs() { 
		return getHtmlAttribute(EHtmlAttr.dataformatas) ; 
	}
	public DMarquee setHtmlDataFormatas(final String dataformatas) {
		setHtmlAttribute(EHtmlAttr.dataformatas, dataformatas) ; 
		return this ; 
	}
	
	/**
	 * Returns the source of the data for the data binding
	 */ 
	public String getHtmlDataSrc() {
		return getHtmlAttribute(EHtmlAttr.datasrc) ; 
	}	
	public DMarquee setHtmlDataSrc(final String datasrc) { 
		setHtmlAttribute(EHtmlAttr.datasrc, datasrc) ; 
		return this ; 
	}
	
	/**
	 * Returns the direction in which the text should scroll 
	 */	 
	public String getHtmlDirection() { 
		return getHtmlAttribute(EHtmlAttr.direction) ; 
	}	
	public DMarquee setHtmlDirection(final String direction) { 
		setHtmlAttribute(EHtmlAttr.direction, direction) ; 
		return this ; 
	}
	
	/**
	 * Returns the height of the object.
	 * Can be an integer followed by the percent (%) sign 
	 */
	public String getHtmlHeight() { 
		return getHtmlAttribute(EHtmlAttr.height) ;
	}	
	public DMarquee setHtmlHeight(final String height) {
		setHtmlAttribute(EHtmlAttr.height, height) ;
		return this ; 
	}	
	public DMarquee setHtmlHeight(final int height) { 
		setHtmlHeight(String.valueOf(height)) ; 
		return this ; 
	}
	
	/**
	 * Returns whether the object visibly shows that it has focus
	 */
	public String getHtmlHideFocus() { 
		return getHtmlAttribute(EHtmlAttr.hidefocus) ;
	}	
	public DMarquee setHtmlHideFocus(final String hidefocus) { 
		setHtmlAttribute(EHtmlAttr.hidefocus, hidefocus) ;
		return this ; 
	}	
	public DMarquee setHtmlHideFocus(final boolean hidefocus) { 
		return setHtmlHideFocus(String.valueOf(hidefocus)) ;
	}
	
	/**
	 * Returns the horizontal margin for the object
	 */ 
	public String getHtmlHSpace() { 
		return getHtmlAttribute(EHtmlAttr.hspace) ; 
	}	
	public DMarquee setHtmlHSpace(final String hspace) { 
		setHtmlAttribute(EHtmlAttr.hspace, hspace) ; 
		return this ; 
	}	
	public DMarquee setHtmlHSpace(final int hspace) { 
		setHtmlHSpace(String.valueOf(hspace)) ; 
		return this ; 
	}
	
	/**
	 * Returns the language in which the script is written
	 */ 
	public String getHtmlLanguage() { 
		return getHtmlAttribute(EHtmlAttr.language) ;
	}	
	public DMarquee setHtmlLanguage(final String language) { 
		setHtmlAttribute(EHtmlAttr.language, language) ;
		return this ;
	}
	
	/** 
	 * Returns the number of times a marquee will play
	 */ 
	public String getHtmlLoop() { 
		return getHtmlAttribute(EHtmlAttr.loop) ; 
	}	
	public DMarquee setHtmlLoop(final String loop) { 
		setHtmlAttribute(EHtmlAttr.loop, loop) ; 
		return this ; 
	}	
	public DMarquee setHtmlLoop(final int loop) { 
		setHtmlLoop(String.valueOf(loop)) ; 
		return this ; 
	}
	
	/**
	 * Returns the number of pixels the text scrolls between each subsequent
	 * drawing of the marquee
	 */ 
	public String getHtmlScrollAmount() { 
		return getHtmlAttribute(EHtmlAttr.scrollamount) ; 
	}	
	public DMarquee setHtmlScrollAmount(final String scrollamount) { 
		setHtmlAttribute(EHtmlAttr.scrollamount, scrollamount) ; 
		return this ; 
	}	
	public DMarquee setHtmlScrollAmount(final int scrollamount) { 
		setHtmlScrollAmount(String.valueOf(scrollamount)) ; 
		return this ; 
	}
	
	/**
	 * Returns the speed of the marquee scroll, in milliseconds
	 */
	public String getHtmlScrollDelay() { 
		return getHtmlAttribute(EHtmlAttr.scrolldelay) ; 
	}	
	public DMarquee setHtmlScrollDelay(final String scrolldelay) { 
		setHtmlAttribute(EHtmlAttr.scrolldelay, scrolldelay) ; 
		return this ; 
	}	
	public DMarquee setHtmlScrollDelay(final int scrolldelay) { 
		setHtmlScrollDelay(String.valueOf(scrolldelay)) ; 
		return this ; 
	}
	
	/**
	 * Returns whether the position of the marquee is calculated using 
	 * scrollDelay and scrollAmount properties and the actual time elapsed from 
	 * the last clock tick
	 */ 
	public String getHtmlTrueSpeed() {
		return getHtmlAttribute(EHtmlAttr.truespeed) ;
	}	
	public DMarquee setHtmlTrueSpeed(final String truespeed) { 
		setHtmlAttribute(EHtmlAttr.truespeed, truespeed) ; 
		return this ; 
	}	
	public DMarquee setHtmlTrueSpeed(final boolean truespeed) { 
		setHtmlTrueSpeed(String.valueOf(truespeed)) ; 
		return this ; 
	}
	
	/** 
	 * Returns whether the element can be selected
	 */ 
	public String getHtmlUnselectable() { 
		return getHtmlAttribute(EHtmlAttr.unselectable) ;
	}	
	public DMarquee setHtmlUnselectable(final String unselectable) { 
		setHtmlAttribute(EHtmlAttr.unselectable, unselectable) ; 
		return this ;
	}
	
	/**
	 * Return the vertical margin for the object
	 */ 
	public String getHtmlVSpace() { 
		return getHtmlAttribute(EHtmlAttr.vspace) ; 
	}	
	public DMarquee setHtmlVSpace(final String vspace) { 
		setHtmlAttribute(EHtmlAttr.vspace, vspace) ;
		return this ;
	}	
	public DMarquee setHtmlVSpace(final int vspace) { 
		setHtmlVSpace(String.valueOf(vspace)) ; 
		return this ; 
	}
	
	/** 
	 * Returns the width of the object
	 */ 
	public String getHtmlWidth() { 
		return getHtmlAttribute(EHtmlAttr.width) ; 
	}	
	public DMarquee setHtmlWidth(final String width) { 
		setHtmlAttribute(EHtmlAttr.width, width) ; 
		return this ; 	
	}	
	public DMarquee setHtmlWidth(final int width) { 
		setHtmlWidth(String.valueOf(width)) ; 
		return this ; 
	}
	
	// Events
	public DMarquee setHtmlOnActivate(final String script) {
		setInlineEvent(EHtmlAttr.onactivate, script, EventType.ACTIVATE);
		return this ; 
	}
	
	public DMarquee setHtmlOnAfterUpdate(final String script) {
		setInlineEvent(EHtmlAttr.onafterupdate, script, EventType.AFTERUPDATE);
		return this ; 
	}
	
	public DMarquee setHtmlOnBeforeActivate(final String script) {
		setInlineEvent(EHtmlAttr.onbeforeactivate, script, EventType.BEFOREACTIVATE);
		return this ; 
	}
	
	public DMarquee setHtmlOnBeforeCut(final String script) {
		setInlineEvent(EHtmlAttr.onbeforecut, script, EventType.BEFORECUT);
		return this ; 
	}
	
	public DMarquee setHtmlOnBeforeDeactivate(final String script) {
		setInlineEvent(EHtmlAttr.onbeforedeactivate, script, EventType.BEFOREDEACTIVATE);
		return this ; 
	}
	
	public DMarquee setHtmlOnBeforeEditFocus(final String script) {
		setInlineEvent(EHtmlAttr.onbeforeeditfocus, script, EventType.BEFOREEDITFOCUS);
		return this ; 
	}
	
	public DMarquee setHtmlOnBeforePaste(final String script) {
		setInlineEvent(EHtmlAttr.onbeforepaste, script, EventType.BEFOREPASTE);
		return this ; 
	}
	
	public DMarquee setHtmlOnBeforeUpdate(final String script) {
		setInlineEvent(EHtmlAttr.onbeforeupdate, script, EventType.BEFOREUPDATE);
		return this ; 
	}
	
	public DMarquee setHtmlOnBounce(final String script) {
		setInlineEvent(EHtmlAttr.onbounce, script, EventType.BOUNCE);
		return this ; 
	}
	
	public DMarquee setHtmlOnControlSelect(final String script) {
		setInlineEvent(EHtmlAttr.oncontrolselect, script, EventType.CONTROLSELECT);
		return this ; 
	}
	
	public DMarquee setHtmlOnCut(final String script) {
		setInlineEvent(EHtmlAttr.oncut, script, EventType.CUT);
		return this ; 
	}
	
	public DMarquee setHtmlOnDeactivate(final String script) {
		setInlineEvent(EHtmlAttr.ondeactivate, script, EventType.DEACTIVATE);
		return this ; 
	}
	
	@Override
	public DMarquee setHtmlOnDoubleClick(final String script) {
		super.setHtmlOnDoubleClick(script) ;
		return this ; 
	}
	
	public DMarquee setHtmlOnErrorUpdate(final String script) {
		setInlineEvent(EHtmlAttr.onerrorupdate, script, EventType.ERRORUPDATE);
		return this ; 
	}
	
	public DMarquee setHtmlOnFilterChange(final String script) {
		setInlineEvent(EHtmlAttr.onfilterchange, script, EventType.FILTERCHANGE);
		return this ; 
	}
	
	public DMarquee setHtmlOnFinish(final String script) {
		setInlineEvent(EHtmlAttr.onfinish, script, EventType.FINISH);
		return this ; 
	}
	
	public DMarquee setHtmlOnFocusIn(final String script) {
		setInlineEvent(EHtmlAttr.onfocusin, script, EventType.FOCUSIN);
		return this ; 
	}
	
	public DMarquee setHtmlOnFocusOut(final String script) {
		setInlineEvent(EHtmlAttr.onfocusout, script, EventType.FOCUSOUT);
		return this ; 
	}
	
	public DMarquee setHtmlOnHelp(final String script) {
		setInlineEvent(EHtmlAttr.onhelp, script, EventType.HELP);
		return this ; 
	}
	
	public DMarquee setHtmlOnLoseCapture(final String script) { 
		setInlineEvent(EHtmlAttr.onlosecapture, script, EventType.LOSECAPTURE);
		return this ; 
	}
	
	public DMarquee setHtmlOnMouseEnter(final String script) {
		setInlineEvent(EHtmlAttr.onmouseenter, script, EventType.MOUSEENTER);
		return this ; 
	}
	
	public DMarquee setHtmlOnMouseLeave(final String script) {
		setInlineEvent(EHtmlAttr.onmouseleave, script, EventType.MOUSELEAVE);
		return this ; 
	}
	
	public DMarquee setHtmlOnMove(final String script) {
		setInlineEvent(EHtmlAttr.onmove, script, EventType.MOVE);
		return this ; 
	}
	
	public DMarquee setHtmlOnMoveEnd(final String script) {
		setInlineEvent(EHtmlAttr.onmoveend, script, EventType.MOVEEND);
		return this ; 
	}
	
	public DMarquee setHtmlOnMoveStart(final String script) {
		setInlineEvent(EHtmlAttr.onmovestart, script, EventType.MOVESTART);
		return this ; 
	}
	
	public DMarquee setHtmlOnPaste(final String script) {
		setInlineEvent(EHtmlAttr.onpaste, script, EventType.PASTE);
		return this ; 
	}
	
	public DMarquee setHtmlOnPropertyChange(final String script) { 
		setInlineEvent(EHtmlAttr.onpropertychange, script, EventType.PROPERTYCHANGE);
		return this ; 
	}
	
	public DMarquee setHtmlOnResize(final String script) {
		setInlineEvent(EHtmlAttr.onresize, script, EventType.RESIZE);
		return this ; 
	}
	
	public DMarquee setHtmlOnResizeEnd(final String script) {
		setInlineEvent(EHtmlAttr.onresizeend, script, EventType.RESIZEEND);
		return this ; 
	}
	
	public DMarquee setHtmlOnResizeStart(final String script) {
		setInlineEvent(EHtmlAttr.onresizestart, script, EventType.RESIZESTART);
		return this ; 
	}
	
	public DMarquee setHtmlOnSelectStart(final String script) {
		setInlineEvent(EHtmlAttr.onselectstart, script, EventType.SELECTSTART);
		return this ; 
	}
	
	public DMarquee setHtmlOnStart(final String script) {
		setInlineEvent(EHtmlAttr.onstart, script, EventType.START);
		return this ; 
	}
	
	public DMarquee setHtmlOnTimeError(final String script) {
		setInlineEvent(EHtmlAttr.ontimeerror, script, EventType.TIMEERROR);
		return this ; 
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt("accesskey", getHtmlAccessKey()) + 
		Z.fmt("atomicselection", getHtmlAtomicSelection()) +
		Z.fmt("behavior", getHtmlBehavior()) +
		Z.fmt("bgcolor", getHtmlBgColor()) +
		Z.fmt("contenteditable", getHtmlContentEditable()) +
		Z.fmt("datafld", getHtmlDataFld()) +
		Z.fmt("dataformatas", getHtmlDataFormatAs()) +
		Z.fmt("datasrc", getHtmlDataSrc()) +
		Z.fmt("direction", getHtmlDirection()) +
		Z.fmt("height", getHtmlHeight()) +
		Z.fmt("hidefocus", getHtmlHideFocus()) +
		Z.fmt("hspace", getHtmlHSpace()) +
		Z.fmt("language", getHtmlLanguage()) +
		Z.fmt("loop", getHtmlLoop()) +
		Z.fmt("scrollamount", getHtmlScrollAmount()) +
		Z.fmt("scrolldelay", getHtmlScrollDelay()) +
		Z.fmt("tabindex", getHtmlTabIndex()) +
		Z.fmt("truespeed", getHtmlTrueSpeed()) +
		Z.fmt("unselectable", getHtmlUnselectable()) +
		Z.fmt("vspace", getHtmlVSpace()) +
		Z.fmt("width", getHtmlWidth()) ;
	}
	
	//
	// Extensions
	//
	public DMarquee setHtmlExtTextValue(final String value) {
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
	public DMarquee add(final DNode newChild) throws DOMException {
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
	public DMarquee add(final String value) throws DOMException {
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
	public DMarquee addRaw(final String value) throws DOMException {
		super.addRaw(value) ;
		return this ;
	}
	
	/**
	 * This double dispatch approach provides the control point for the node
	 * to have customized behavior.
	 */
	@Override
	public DMarquee dsfAccept(final IDNodeVisitor visitor) {
		super.dsfAccept(visitor) ;
		return this;
	}
	
	/**
	 * Broadcasts the event to any registered IDsfEventListner's.
	 * The listeners are broadcast to in the order they were maintained.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DMarquee dsfBroadcast(final DsfEvent event) // must not be null
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
	public DMarquee setDsfRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
		super.setDsfRelationshipVerifier(relationshipVerifier) ;
		return this;
	}
	
	@Override
	public DMarquee cloned() {
		return (DMarquee)super.cloned() ;
	}
	
    /**
     * set namespace for this node.
     * update the nodename based on the given namespace
     * @param namespace
     * @return
     */
    @Override
    public DMarquee setDsfNamespace(DNamespace namespace){
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
	public DMarquee setHtmlAccessKey(final String accessKey) {
		super.setHtmlAccessKey(accessKey) ;
		return this ;
	}
		
	/**
	 * set class name, overwrite current class(es)
	 */
	@Override
	public DMarquee setHtmlClassName(final String className) {
		super.setHtmlClassName(className) ;
		return this ;
	}
	@Override
	public DMarquee setHtmlClassName(final CssClassConstant ccc) {
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
	public DMarquee setHtmlContentEditable(final String editable) {
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
	public DMarquee setHtmlContextMenu(final String contextMenu) {
		super.setHtmlContextMenu(contextMenu) ;
		return this ;
	}
	
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	@Override
	public DMarquee setHtmlDir(final String dir) {
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
	public DMarquee setHtmlDraggable(final String draggable) {  // true, false, auto
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
	public DMarquee setHtmlDraggable(final boolean draggable) {  // true, false
		super.setHtmlDraggable(draggable) ;
		return this ;
	}
	
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DMarquee setHtmlHidden(final String hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DMarquee setHtmlHidden(final boolean hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	@Override
	public DMarquee setHtmlId(String id) {
		super.setHtmlId(id) ;
		return this ;
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */	
	@Override
	public DMarquee setHtmlId(CssIdConstant id) {
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
	public DMarquee setHtmlItem(final String item) {
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
	public DMarquee setHtmlItemProp(final String itemProp) {
		super.setHtmlItemProp(itemProp) ;
		return this ;
	}	
	
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	@Override
	public DMarquee setHtmlLang(final String lang) {
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
	public DMarquee setHtmlSpellCheck(final String spellCheck) {
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
	public DMarquee setHtmlSpellCheck(final boolean spellCheck) {
		super.setHtmlSpellCheck(spellCheck) ;
		return this ;
	}	

	@Override
	public DMarquee setHtmlStyleAsString(final String styleString) {
		super.setHtmlStyleAsString(styleString) ;
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 */
	@Override
	public DMarquee setHtmlStyle(final ICssStyleDeclaration style) {
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
	public DMarquee setHtmlSubject(final String subject) {
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
	public DMarquee setHtmlTabIndex(final String tabIndex) {  // HTML 5.0
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
	public DMarquee setHtmlTabIndex(final int tabIndex) {  // HTML 5.0
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
	public DMarquee setHtmlTitle(final String title) {
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
	public DMarquee setHtmlOnAbort(final String script) {
		super.setHtmlOnAbort(script) ;
		return this ;
	}
	
	/**
	 * The onblur event occurs when an object loses focus.
	 * not supported on BODY
	 */
	@Override
	public DMarquee setHtmlOnBlur(final String onBlur) {
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
	public DMarquee setHtmlOnCanPlay(final String script) {
		super.setHtmlOnCanPlay(script) ;
		return this ;
	}
	
	/**
	 *  The user agent estimates that if playback were to be started now, the 
	 *  media resource could be rendered at the current playback rate all the way 
	 *  to its end without having to stop for further buffering. 
	 */
	@Override
	public DMarquee setHtmlOnCanPlayThrough(final String script) {
		super.setHtmlOnCanPlayThrough(script) ;
		return this ;
	}
	
	// onchange
	@Override
	public DMarquee setHtmlOnChange(final String script) {
		super.setHtmlOnChange(script) ;
		return this ;
	}
	
	// onclick
	@Override
	public DMarquee setHtmlOnClick(final String script) {
		super.setHtmlOnClick(script) ;
		return this ;
	}

	// oncontextmenu
	@Override
	public DMarquee setHtmlOnContextMenu(final String script) {
		super.setHtmlOnContextMenu(script) ;
		return this ;
	}
	
	// ondblclick
	@Override
	public DMarquee setHtmlOnDblClick(final String script) {
		super.setHtmlOnDblClick(script) ;
		return this ;
	}
	
	// ondrag
	@Override
	public DMarquee setHtmlOnDrag(final String script) {
		super.setHtmlOnDrag(script) ;
		return this ;
	}
	
	// ondragend
	@Override
	public DMarquee setHtmlOnDragEnd(final String script) {
		super.setHtmlOnDragEnd(script) ;
		return this ;
	}
	
	// ondragenter
	@Override
	public DMarquee setHtmlOnDragEnter(final String script) {
		super.setHtmlOnDragEnter(script) ;
		return this ;
	}
	
	// ondragleave
	@Override
	public DMarquee setHtmlOnDragLeave(final String script) {
		super.setHtmlOnDragLeave(script) ;
		return this ;
	}
	
	// ondragover
	@Override
	public DMarquee setHtmlOnDragOver(final String script) {
		super.setHtmlOnDragOver(script) ;
		return this ;
	}
	
	// ondragstart
	@Override
	public DMarquee setHtmlOnDragStart(final String script) {
		super.setHtmlOnDragStart(script) ;
		return this ;
	}
	
	// ondrop
	@Override
	public DMarquee setHtmlOnDrop(final String script) {
		super.setHtmlOnDrop(script) ;
		return this ;
	}
	
	// ondurationchange
	@Override
	public DMarquee setHtmlOnDurationChange(final String script) {
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
	public DMarquee setHtmlOnEmptied(final String script) {
		super.setHtmlOnEmptied(script) ;
		return this ;
	}
	
	/**
	 * Playback has stopped because the end of the media resource was reached. 
	 */
	@Override
	public DMarquee setHtmlOnEnded(final String script) {
		super.setHtmlOnEnded(script) ;
		return this ;
	}
	
	/**
	 * An error occurs while fetching the media data. 
	 * not supported on BODY
	 */
	@Override
	public DMarquee setHtmlOnError(final String script) {
		super.setHtmlOnError(script) ;
		return this ;
	}

	/**
	 * onfocus - not supported on BODY
	 */
	@Override
	public DMarquee setHtmlOnFocus(final String script) {
		super.setHtmlOnFocus(script) ;
		return this ;
	}
	
	/**
	 * onformchange
	 */
	@Override
	public DMarquee setHtmlOnFormChange(final String script) {
		super.setHtmlOnFormChange(script) ;
		return this ;
	}
	
	/**
	 * onforminput
	 */
	@Override
	public DMarquee setHtmlOnFormInput(final String script) {
		super.setHtmlOnFormInput(script) ;
		return this ;
	}
	
	/**
	 * oninput
	 */
	@Override
	public DMarquee setHtmlOnInput(final String script) {
		super.setHtmlOnInput(script) ;
		return this ;
	}
	
	/**
	 * oninvalid
	 */
	@Override
	public DMarquee setHtmlOnInvalid(final String script) {
		super.setHtmlOnInvalid(script) ;
		return this ;
	}
	
	/**
	 * onkeydown
	 */
	@Override
	public DMarquee setHtmlOnKeyDown(final String script) {
		super.setHtmlOnKeyDown(script) ;
		return this ;
	}

	/**
	 * onkeypress
	 */
	@Override
	public DMarquee setHtmlOnKeyPress(final String script) {
		super.setHtmlOnKeyPress(script) ;
		return this ;
	}
	
	/**
	 * onkeyup
	 */
	@Override
    public DMarquee setHtmlOnKeyUp(final String script) {
    	super.setHtmlOnKeyUp(script);
    	return this ;
	}
    
	/**
	 * onload
	 */
	@Override
    public DMarquee setHtmlOnLoad(final String script) {
    	super.setHtmlOnLoad(script) ;
    	return this ;
	}
    
	/**
	 * onloadeddata
	 */
	@Override
    public DMarquee setHtmlOnLoadedData(final String script) {
    	super.setHtmlOnLoadedData(script) ;
    	return this ;
	}   
    
	/**
	 * onloadedmetadata
	 */
	@Override
    public DMarquee setHtmlOnLoadedMetadata(final String script) {
    	super.setHtmlOnLoadedMetadata(script) ;
    	return this ;
	}  
	
	/**
	 * onloadstart
	 */
	@Override
    public DMarquee setHtmlOnLoadStart(final String script) {
    	super.setHtmlOnLoadStart(script) ;
    	return this ;
	} 
    
	/**
	 * onmousedown
	 */
	@Override
	public DMarquee setHtmlOnMouseDown(final String script){
		super.setHtmlOnMouseDown(script) ;
		return this ;
	}
	
	/**
	 * onmousemove
	 */
	@Override
	public DMarquee setHtmlOnMouseMove(final String script) {
		super.setHtmlOnMouseMove(script) ;
		return this ;
	}
	
	/**
	 * onmouseout
	 */
	@Override
	public DMarquee setHtmlOnMouseOut(final String script) {
		super.setHtmlOnMouseOut(script) ;
		return this ;
	}
	
	/**
	 * onmouseover
	 */
	@Override
	public DMarquee setHtmlOnMouseOver(final String script) {
		super.setHtmlOnMouseOver(script) ;
		return this ;
	}
	
	/**
	 * onmouseup
	 */
	@Override
	public DMarquee setHtmlOnMouseUp(final String script) {
		super.setHtmlOnMouseUp(script) ;
		return this ;
	}
    
	/**
	 * onmousewheel
	 */
	@Override
	public DMarquee setHtmlOnMouseWheel(final String script) {
		super.setHtmlOnMouseWheel(script) ;
		return this ;
	}
    
	/**
	 * onpause
	 */
	@Override
	public DMarquee setHtmlOnPause(final String script) {
		super.setHtmlOnPause(script) ;
		return this ;
	}  
    
	/**
	 * onplay
	 */
	@Override
	public DMarquee setHtmlOnPlay(final String script) {
		super.setHtmlOnPlay(script) ;
		return this ;
	}
    
	/**
	 * onplaying
	 */
	@Override
	public DMarquee setHtmlOnPlaying(final String script) {
		super.setHtmlOnPlaying(script) ;
		return this ;
	}
	
	/**
	 * onprogress
	 */
	@Override
	public DMarquee setHtmlOnProgress(final String script) {
		super.setHtmlOnProgress(script) ;
		return this ;
	}
    
	/**
	 * onratechange
	 */
	@Override
	public DMarquee setHtmlOnRateChange(final String script) {
		super.setHtmlOnRateChange(script) ;
		return this ;
	}
    
	/**
	 * onreadystatechange
	 */
	@Override
	public DMarquee setHtmlOnReadyStateChange(final String script) {
		super.setHtmlOnReadyStateChange(script) ;
		return this ;
	}

	/**
	 * onscroll
	 */
	@Override
	public DMarquee setHtmlOnScroll(final String script) {
		super.setHtmlOnScroll(script) ;
		return this ;
	}
	
	/**
	 * onseeked
	 */
	@Override
	public DMarquee setHtmlOnSeeked(final String script) {
		super.setHtmlOnSeeked(script) ;
		return this ;
	}
    
	/**
	 * onseeking
	 */
	@Override
	public DMarquee setHtmlOnSeeking(final String script) {
		super.setHtmlOnSeeking(script) ;
		return this ;
	}
	
	/**
	 * onselect
	 */
	@Override
	public DMarquee setHtmlOnSelect(final String script) {
		super.setHtmlOnSelect(script) ;
		return this ;
	}
	
	/**
	 * onshow
	 */
	@Override
	public DMarquee setHtmlOnShow(final String script) {
		super.setHtmlOnShow(script) ;
		return this ;
	}
	
	/**
	 * onstalled
	 */
	@Override
	public DMarquee setHtmlOnStalled(final String script) {
		super.setHtmlOnStalled(script) ;
		return this ;
	}
	
	/**
	 * onsubmit
	 */
	@Override
	public DMarquee setHtmlOnSubmit(final String script) {
		super.setHtmlOnSubmit(script) ;
		return this ;
	}
	
	/**
	 * onsuspend
	 */
	@Override
	public DMarquee setHtmlOnSuspend(final String script) {
		super.setHtmlOnSuspend(script) ;
		return this ;
	}
	
	/**
	 * ontimeupdate
	 */
	@Override
	public DMarquee setHtmlOnTimeUpdate(final String script) {
		super.setHtmlOnTimeUpdate(script) ;
		return this ;
	}
	
	/**
	 * onvolumechange
	 */
	@Override
	public DMarquee setHtmlOnVolumeChange(final String script) {
		super.setHtmlOnVolumeChange(script) ;
		return this ;
	}
	
	/**
	 * onwaiting
	 */
	@Override
	public DMarquee setHtmlOnWaiting(final String script) {
		super.setHtmlOnWaiting(script) ;
		return this ;
	}
	
	//
	// Framework - Event Wiring
	//
	@Override
	public DMarquee add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		super.add(eventType, handler) ;
		return this ;
	}
	
	@Override
	public DMarquee add(
		final EventType eventType, 
		final IJsFunc func)
	{
		super.add(eventType, func) ;
		return this ;
	}
	
	@Override
	public DMarquee add(
		final EventType eventType, 
		final String jsText)
	{
		super.add(eventType, jsText) ;
		return this ;
	}
	
//	@Override
//	public DMarquee add(final IDomActiveListener listener){
//		super.add(listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DMarquee add(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.add(eventType, listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DMarquee removeListener(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.removeListener(eventType, listener) ;
//		return this ;
//	}
	
	//
	// Helpers
	//
	@Override
	public DMarquee addBr() {
		super.addBr() ;
		return this ;
	}
	
	@Override
	public DMarquee addBr(final int howMany){
		super.addBr(howMany) ;
		return this ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	@Override
	public DMarquee addHtmlClassName(final String className) {
		super.addHtmlClassName(className) ;
		return this ;
	}
	
	@Override
	public DMarquee addHtmlClassName(final CssClassConstant ccc) {
		super.addHtmlClassName(ccc) ;
		return this ;
	}
	
	@Override
	public DMarquee jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
}


