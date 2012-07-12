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
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.common.Z;

/**
<a href='http://dev.w3.org/html5/spec/Overview.html#the-section-element'>W3C time</a>
<p>
The time element represents a precise date and/or a time in the proleptic 
Gregorian calendar. [GREGORIAN]
<p>
This element is intended as a way to encode modern dates and times in a 
machine-readable way so that user agents can offer to add them to the user's 
calendar. For example, adding birthday reminders or scheduling events.
<p>
The time element can be used to encode dates, for example in Microformats. 
The following shows a hypothetical way of encoding an event using a variant on 
hCalendar that uses the time element:
<p>
<div class="vevent">
 <a class="url" href="http://www.web2con.com/">http://www.web2con.com/</a>
  <span class="summary">Web 2.0 Conference</span>:
  <time class="dtstart" datetime="2007-10-05">October 5</time> -
  <time class="dtend" datetime="2007-10-20">19</time>,
  at the <span class="location">Argent Hotel, San Francisco, CA</span>
 </div>
 */
public class DTime extends BaseHtml50Element {
	private static final long serialVersionUID = 1L;

	//
	// Constructor(s)
	//
	public DTime() {
		super(HtmlTypeEnum.TIME);
	}
	
	public DTime(final DHtmlDocument doc) {
		super(doc, HtmlTypeEnum.TIME);
	}
	
//	public DTime(final String jif) {
//		this() ;
//		jif(jif) ;
//	}

	public DTime(final String dateTime) {
		this() ;
		setHtmlDateTime(dateTime) ;
	}
	
	public DTime(BaseHtmlElement... elems) {
		this() ;
		add(elems) ;
	}
	
	//
	// Framework
	//
	@Override
	public HtmlTypeEnum htmlType() {
		return HtmlTypeEnum.TIME ;
	}
	
	//
	// HTML Attributes
	//	

	// datetime
	public String getHtmlDateTime() {
		return getHtmlAttribute(EHtmlAttr.datetime) ;
	}
	public DTime setHtmlDateTime(final String datetime) {
		setHtmlAttribute(EHtmlAttr.datetime, datetime) ;
		return this ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString()
			+ Z.fmt(EHtmlAttr.datetime.getAttributeName(), "" + getHtmlDateTime()) ;	
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
	public DTime add(final DNode newChild) throws DOMException {
		super.add(newChild) ;
		return this ;
	}
	
	@Override
	public DTime add(BaseHtmlElement... elems) throws DOMException {
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
	public DTime add(final String value) throws DOMException {
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
	public DTime addRaw(final String value) throws DOMException {
		super.addRaw(value) ;
		return this ;
	}
	
	/**
	 * This double dispatch approach provides the control point for the node
	 * to have customized behavior.
	 */
	@Override
	public DTime dsfAccept(final IDNodeVisitor visitor) {
		super.dsfAccept(visitor) ;
		return this;
	}
	
	/**
	 * Broadcasts the event to any registered IDsfEventListner's.
	 * The listeners are broadcast to in the order they were maintained.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DTime dsfBroadcast(final DsfEvent event) // must not be null
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
	public DTime setDsfRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
		super.setDsfRelationshipVerifier(relationshipVerifier) ;
		return this;
	}
	
	@Override
	public DTime cloned() {
		return (DTime)super.cloned() ;
	}
	
    /**
     * set namespace for this node.
     * update the nodename based on the given namespace
     * @param namespace
     * @return
     */
    @Override
    public DTime setDsfNamespace(DNamespace namespace){
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
	public DTime setHtmlAccessKey(final String accessKey) {
		super.setHtmlAccessKey(accessKey) ;
		return this ;
	}
		
	/**
	 * set class name, overwrite current class(es)
	 */
	@Override
	public DTime setHtmlClassName(final String className) {
		super.setHtmlClassName(className) ;
		return this ;
	}
	@Override
	public DTime setHtmlClassName(final CssClassConstant ccc) {
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
	public DTime setHtmlContentEditable(final String editable) {
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
	public DTime setHtmlContextMenu(final String contextMenu) {
		super.setHtmlContextMenu(contextMenu) ;
		return this ;
	}
	
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	@Override
	public DTime setHtmlDir(final String dir) {
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
	public DTime setHtmlDraggable(final String draggable) {  // true, false, auto
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
	public DTime setHtmlDraggable(final boolean draggable) {  // true, false
		super.setHtmlDraggable(draggable) ;
		return this ;
	}
	
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DTime setHtmlHidden(final String hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DTime setHtmlHidden(final boolean hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	@Override
	public DTime setHtmlId(String id) {
		super.setHtmlId(id) ;
		return this ;
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */	
	@Override
	public DTime setHtmlId(CssIdConstant id) {
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
	public DTime setHtmlItem(final String item) {
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
	public DTime setHtmlItemProp(final String itemProp) {
		super.setHtmlItemProp(itemProp) ;
		return this ;
	}	
	
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	@Override
	public DTime setHtmlLang(final String lang) {
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
	public DTime setHtmlSpellCheck(final String spellCheck) {
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
	public DTime setHtmlSpellCheck(final boolean spellCheck) {
		super.setHtmlSpellCheck(spellCheck) ;
		return this ;
	}	

	@Override
	public DTime setHtmlStyleAsString(final String styleString) {
		super.setHtmlStyleAsString(styleString) ;
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 */
	@Override
	public DTime setHtmlStyle(final ICssStyleDeclaration style) {
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
	public DTime setHtmlSubject(final String subject) {
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
	public DTime setHtmlTabIndex(final String tabIndex) {  // HTML 5.0
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
	public DTime setHtmlTabIndex(final int tabIndex) {  // HTML 5.0
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
	public DTime setHtmlTitle(final String title) {
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
	public DTime setHtmlOnAbort(final String script) {
		super.setHtmlOnAbort(script) ;
		return this ;
	}
	
	/**
	 * The onblur event occurs when an object loses focus.
	 * not supported on BODY
	 */
	@Override
	public DTime setHtmlOnBlur(final String onBlur) {
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
	public DTime setHtmlOnCanPlay(final String script) {
		super.setHtmlOnCanPlay(script) ;
		return this ;
	}
	
	/**
	 *  The user agent estimates that if playback were to be started now, the 
	 *  media resource could be rendered at the current playback rate all the way 
	 *  to its end without having to stop for further buffering. 
	 */
	@Override
	public DTime setHtmlOnCanPlayThrough(final String script) {
		super.setHtmlOnCanPlayThrough(script) ;
		return this ;
	}
	
	// onchange
	@Override
	public DTime setHtmlOnChange(final String script) {
		super.setHtmlOnChange(script) ;
		return this ;
	}
	
	// onclick
	@Override
	public DTime setHtmlOnClick(final String script) {
		super.setHtmlOnClick(script) ;
		return this ;
	}

	// oncontextmenu
	@Override
	public DTime setHtmlOnContextMenu(final String script) {
		super.setHtmlOnContextMenu(script) ;
		return this ;
	}
	
	// ondblclick
	@Override
	public DTime setHtmlOnDblClick(final String script) {
		super.setHtmlOnDblClick(script) ;
		return this ;
	}
	
	// ondrag
	@Override
	public DTime setHtmlOnDrag(final String script) {
		super.setHtmlOnDrag(script) ;
		return this ;
	}
	
	// ondragend
	@Override
	public DTime setHtmlOnDragEnd(final String script) {
		super.setHtmlOnDragEnd(script) ;
		return this ;
	}
	
	// ondragenter
	@Override
	public DTime setHtmlOnDragEnter(final String script) {
		super.setHtmlOnDragEnter(script) ;
		return this ;
	}
	
	// ondragleave
	@Override
	public DTime setHtmlOnDragLeave(final String script) {
		super.setHtmlOnDragLeave(script) ;
		return this ;
	}
	
	// ondragover
	@Override
	public DTime setHtmlOnDragOver(final String script) {
		super.setHtmlOnDragOver(script) ;
		return this ;
	}
	
	// ondragstart
	@Override
	public DTime setHtmlOnDragStart(final String script) {
		super.setHtmlOnDragStart(script) ;
		return this ;
	}
	
	// ondrop
	@Override
	public DTime setHtmlOnDrop(final String script) {
		super.setHtmlOnDrop(script) ;
		return this ;
	}
	
	// ondurationchange
	@Override
	public DTime setHtmlOnDurationChange(final String script) {
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
	public DTime setHtmlOnEmptied(final String script) {
		super.setHtmlOnEmptied(script) ;
		return this ;
	}
	
	/**
	 * Playback has stopped because the end of the media resource was reached. 
	 */
	@Override
	public DTime setHtmlOnEnded(final String script) {
		super.setHtmlOnEnded(script) ;
		return this ;
	}
	
	/**
	 * An error occurs while fetching the media data. 
	 * not supported on BODY
	 */
	@Override
	public DTime setHtmlOnError(final String script) {
		super.setHtmlOnError(script) ;
		return this ;
	}

	/**
	 * onfocus - not supported on BODY
	 */
	@Override
	public DTime setHtmlOnFocus(final String script) {
		super.setHtmlOnFocus(script) ;
		return this ;
	}
	
	/**
	 * onformchange
	 */
	@Override
	public DTime setHtmlOnFormChange(final String script) {
		super.setHtmlOnFormChange(script) ;
		return this ;
	}
	
	/**
	 * onforminput
	 */
	@Override
	public DTime setHtmlOnFormInput(final String script) {
		super.setHtmlOnFormInput(script) ;
		return this ;
	}
	
	/**
	 * oninput
	 */
	@Override
	public DTime setHtmlOnInput(final String script) {
		super.setHtmlOnInput(script) ;
		return this ;
	}
	
	/**
	 * oninvalid
	 */
	@Override
	public DTime setHtmlOnInvalid(final String script) {
		super.setHtmlOnInvalid(script) ;
		return this ;
	}
	
	/**
	 * onkeydown
	 */
	@Override
	public DTime setHtmlOnKeyDown(final String script) {
		super.setHtmlOnKeyDown(script) ;
		return this ;
	}

	/**
	 * onkeypress
	 */
	@Override
	public DTime setHtmlOnKeyPress(final String script) {
		super.setHtmlOnKeyPress(script) ;
		return this ;
	}
	
	/**
	 * onkeyup
	 */
	@Override
    public DTime setHtmlOnKeyUp(final String script) {
    	super.setHtmlOnKeyUp(script);
    	return this ;
	}
    
	/**
	 * onload
	 */
	@Override
    public DTime setHtmlOnLoad(final String script) {
    	super.setHtmlOnLoad(script) ;
    	return this ;
	}
    
	/**
	 * onloadeddata
	 */
	@Override
    public DTime setHtmlOnLoadedData(final String script) {
    	super.setHtmlOnLoadedData(script) ;
    	return this ;
	}   
    
	/**
	 * onloadedmetadata
	 */
	@Override
    public DTime setHtmlOnLoadedMetadata(final String script) {
    	super.setHtmlOnLoadedMetadata(script) ;
    	return this ;
	}  
	
	/**
	 * onloadstart
	 */
	@Override
    public DTime setHtmlOnLoadStart(final String script) {
    	super.setHtmlOnLoadStart(script) ;
    	return this ;
	} 
    
	/**
	 * onmousedown
	 */
	@Override
	public DTime setHtmlOnMouseDown(final String script){
		super.setHtmlOnMouseDown(script) ;
		return this ;
	}
	
	/**
	 * onmousemove
	 */
	@Override
	public DTime setHtmlOnMouseMove(final String script) {
		super.setHtmlOnMouseMove(script) ;
		return this ;
	}
	
	/**
	 * onmouseout
	 */
	@Override
	public DTime setHtmlOnMouseOut(final String script) {
		super.setHtmlOnMouseOut(script) ;
		return this ;
	}
	
	/**
	 * onmouseover
	 */
	@Override
	public DTime setHtmlOnMouseOver(final String script) {
		super.setHtmlOnMouseOver(script) ;
		return this ;
	}
	
	/**
	 * onmouseup
	 */
	@Override
	public DTime setHtmlOnMouseUp(final String script) {
		super.setHtmlOnMouseUp(script) ;
		return this ;
	}
    
	/**
	 * onmousewheel
	 */
	@Override
	public DTime setHtmlOnMouseWheel(final String script) {
		super.setHtmlOnMouseWheel(script) ;
		return this ;
	}
    
	/**
	 * onpause
	 */
	@Override
	public DTime setHtmlOnPause(final String script) {
		super.setHtmlOnPause(script) ;
		return this ;
	}  
    
	/**
	 * onplay
	 */
	@Override
	public DTime setHtmlOnPlay(final String script) {
		super.setHtmlOnPlay(script) ;
		return this ;
	}
    
	/**
	 * onplaying
	 */
	@Override
	public DTime setHtmlOnPlaying(final String script) {
		super.setHtmlOnPlaying(script) ;
		return this ;
	}
	
	/**
	 * onprogress
	 */
	@Override
	public DTime setHtmlOnProgress(final String script) {
		super.setHtmlOnProgress(script) ;
		return this ;
	}
    
	/**
	 * onratechange
	 */
	@Override
	public DTime setHtmlOnRateChange(final String script) {
		super.setHtmlOnRateChange(script) ;
		return this ;
	}
    
	/**
	 * onreadystatechange
	 */
	@Override
	public DTime setHtmlOnReadyStateChange(final String script) {
		super.setHtmlOnReadyStateChange(script) ;
		return this ;
	}

	/**
	 * onscroll
	 */
	@Override
	public DTime setHtmlOnScroll(final String script) {
		super.setHtmlOnScroll(script) ;
		return this ;
	}
	
	/**
	 * onseeked
	 */
	@Override
	public DTime setHtmlOnSeeked(final String script) {
		super.setHtmlOnSeeked(script) ;
		return this ;
	}
    
	/**
	 * onseeking
	 */
	@Override
	public DTime setHtmlOnSeeking(final String script) {
		super.setHtmlOnSeeking(script) ;
		return this ;
	}
	
	/**
	 * onselect
	 */
	@Override
	public DTime setHtmlOnSelect(final String script) {
		super.setHtmlOnSelect(script) ;
		return this ;
	}
	
	/**
	 * onshow
	 */
	@Override
	public DTime setHtmlOnShow(final String script) {
		super.setHtmlOnShow(script) ;
		return this ;
	}
	
	/**
	 * onstalled
	 */
	@Override
	public DTime setHtmlOnStalled(final String script) {
		super.setHtmlOnStalled(script) ;
		return this ;
	}
	
	/**
	 * onsubmit
	 */
	@Override
	public DTime setHtmlOnSubmit(final String script) {
		super.setHtmlOnSubmit(script) ;
		return this ;
	}
	
	/**
	 * onsuspend
	 */
	@Override
	public DTime setHtmlOnSuspend(final String script) {
		super.setHtmlOnSuspend(script) ;
		return this ;
	}
	
	/**
	 * ontimeupdate
	 */
	@Override
	public DTime setHtmlOnTimeUpdate(final String script) {
		super.setHtmlOnTimeUpdate(script) ;
		return this ;
	}
	
	/**
	 * onvolumechange
	 */
	@Override
	public DTime setHtmlOnVolumeChange(final String script) {
		super.setHtmlOnVolumeChange(script) ;
		return this ;
	}
	
	/**
	 * onwaiting
	 */
	@Override
	public DTime setHtmlOnWaiting(final String script) {
		super.setHtmlOnWaiting(script) ;
		return this ;
	}
	
	//
	// Framework - Event Wiring
	//
	@Override
	public DTime add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		super.add(eventType, handler) ;
		return this ;
	}
	
	@Override
	public DTime add(
		final EventType eventType, 
		final IJsFunc func)
	{
		super.add(eventType, func) ;
		return this ;
	}
	
	@Override
	public DTime add(
		final EventType eventType, 
		final String jsText)
	{
		super.add(eventType, jsText) ;
		return this ;
	}
	
//	@Override
//	public DTime add(final IDomActiveListener listener){
//		super.add(listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DTime add(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.add(eventType, listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DTime removeListener(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.removeListener(eventType, listener) ;
//		return this ;
//	}
	
	//
	// Helpers
	//
	@Override
	public DTime addBr() {
		super.addBr() ;
		return this ;
	}
	
	@Override
	public DTime addBr(final int howMany){
		super.addBr(howMany) ;
		return this ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	@Override
	public DTime addHtmlClassName(final String className) {
		super.addHtmlClassName(className) ;
		return this ;
	}
	
	@Override
	public DTime addHtmlClassName(final CssClassConstant ccc) {
		super.addHtmlClassName(ccc) ;
		return this ;
	}
	
	@Override
	public DTime jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Child Hooks (Phrasing Content)
	//
	public DA _a() {
		 return _a(-1) ;
	}
	public DTime _a_() {
		_a() ;
		return this ;
	}
	public DA _a(final int count) {
		 return (DA)getOrCreate(DA.class, count) ;
	}
	public DTime _a_(final int count) {
		_a(count) ;
		return this ;
	}
	public DA _a(final String jif) {
		return _a().jif(jif) ;
	}
	public DTime _a_(final String jif) {
		_a().jif(jif) ;
		return this ;
	}
	public DA _aText(final String value) {
		return _a().setHtmlExtTextValue(value) ;
	}
	public DTime _aText_(final String textValue) {
		_aText(textValue) ;
		return this ;
	}
	public DA _a(final String value, final String url) {
		return _a().setHtmlExtTextValue(value).setHtmlHref(url) ;
	}
	public DTime _a_(final String value, final String url) {
		_a(value, url) ;
		return this ;
	}

	public DAbbr _abbr() {
		 return _abbr(-1) ;
	}
	public DTime _abbr_() {
		_abbr() ;
		return this ;
	}
	public DAbbr _abbr(final int count) {
		 return (DAbbr)getOrCreate(DAbbr.class, count) ;
	}
	public DTime _abbr_(final int count) {
		 _abbr(count) ;
		 return this ;
	}
	public DAbbr _abbr(final String jif) {
		return _abbr().jif(jif) ;
	}
	public DTime _abbr_(final String jif) {
		_abbr(jif) ;
		return this ;
	}
	public DAbbr _abbrText(final String textValue) {
		return _abbr().setHtmlExtTextValue(textValue) ;
	}
	public DTime _abbrText_(final String textValue) {
		_abbrText(textValue) ;
		return this ;
	}
	public DAbbr _abbr(BaseHtmlElement... elements) {
		final DAbbr answer = _abbr() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _abbr_(BaseHtmlElement... elements) {
		_abbr(elements) ;
		return this ;
	}

	public DArea _area() {
		 return _area(-1) ;
	}
	public DTime _area_() {
		 _area(-1) ;
		 return this ;
	}
	public DArea _area(final int count) {
		 return (DArea)getOrCreate(DArea.class, count) ;
	}
	public DTime _area_(final int count) {
		 _area(count) ;
		 return this;
	}
	public DArea _area(final String jif) {
		return _area().jif(jif) ;
	}
	public DTime _area_(final String jif) {
		_area(jif) ;
		return this ;
	}

	public DAudio _audio() {
		 return _audio(-1) ;
	}
	public DTime _audio_() {
		_audio() ;
		return this ;
	}
	public DAudio _audio(final int count) {
		 return (DAudio)getOrCreate(DAudio.class, count) ;
	}
	public DTime _audio_(final int count) {
		_audio(count) ;
		return this ;
	}
	public DAudio _audio(final String jif) {
		return _audio().jif(jif) ;
	}
	public DTime _audio_(final String jif) {
		_audio(jif) ;
		return this ;
	}

	public DB _b() {
		 return _b(-1) ;
	}
	public DTime _b_() {
		_b() ;
		return this ;
	}
	public DB _b(final int count) {
		 return (DB)getOrCreate(DB.class, count) ;
	}
	public DTime _b_(final int count) {
		_b(count) ;
		return this ;
	}
	public DB _b(final String jif) {
		return _b().jif(jif) ;
	}
	public DTime _b_(final String jif) {
		_b(jif) ;
		return this ;
	}
	public DB _bText(final String textValue) {
		return _b().setHtmlExtTextValue(textValue) ;
	}
	public DTime _bText_(final String textValue) {
		_bText(textValue) ;
		return this ;
	}
	public DB _b(BaseHtmlElement... elements) {
		final DB answer = _b() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _b_(BaseHtmlElement... elements) {
		_b(elements) ;
		return this ;
	}

	public DBdo _bdo() {
		 return _bdo(-1) ;
	}
	public DTime _bdo_() {
		_bdo() ;
		return this ;
	}
	public DBdo _bdo(final int count) {
		 return (DBdo)getOrCreate(DBdo.class, count) ;
	}
	public DTime _bdo_(final int count) {
		_bdo(count) ;
		return this ;
	}
	public DBdo _bdo(final String jif) {
		return _bdo().jif(jif);
	}
	public DTime _bdo_(final String jif) {
		_bdo(jif) ;
		return this ;
	}
	public DBdo _bdo(BaseHtmlElement... elements) {
		final DBdo answer = _bdo() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _bdo_(BaseHtmlElement... elements) {
		_bdo(elements) ;
		return this ;
	}

	public DBr _br() {
		 return _br(-1) ;
	}
	public DTime _br_() {
		_br() ;
		return this ;
	}
	public DBr _br(final int count) {
		 return (DBr)getOrCreate(DBr.class, count) ;
	}
	public DTime _br_(final int count) {
		 _br(count) ;
		 return this ;
	}
	public DBr _br(final String jif) {
		return _br().jif(jif) ;
	}
	public DTime _br_(final String jif) {
		_br(jif) ;
		return this ;
	}

	public DButton _button() {
		 return _button(-1) ;
	}
	public DTime _button_() {
		_button() ;
		return this ;
	}
	public DButton _button(final int count) {
		 return (DButton)getOrCreate(DButton.class, count) ;
	}
	public DTime _button_(final int count) {
		_button(count) ;
		return this ;
	}
	public DButton _button(final String jif) {
		return _button().jif(jif) ;
	}
	public DTime _button_(final String jif) {
		_button(jif) ;
		return this ;
	}
	public DButton _buttonText(final String textValue) {
		return _button().setHtmlExtTextValue(textValue) ;
	}
	public DTime _buttonText_(final String textValue) {
		_buttonText(textValue) ;
		return this ;
	}
	public DButton _button(BaseHtmlElement... elements) {
		final DButton answer = _button() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _button_(BaseHtmlElement... elements) {
		_button(elements) ;
		return this ;
	}

	public DCanvas _canvas() {
		 return _canvas(-1) ;
	}
	public DTime _canvas_() {
		_canvas() ;
		return this ;
	}
	public DCanvas _canvas(final int count) {
		 return (DCanvas)getOrCreate(DCanvas.class, count) ;
	}
	public DTime _canvas_(final int count) {
		_canvas(count) ;
		return this ;
	}
	public DCanvas _canvas(final String jif) {
		return _canvas().jif(jif) ;
	}
	public DTime _canvas_(final String jif) {
		_canvas(jif) ;
		return this ;
	}
	

	public DCite _cite() {
		 return _cite(-1) ;
	}
	public DTime _cite_() {
		_cite() ;
		return this ;
	}
	public DCite _cite(final int count) {
		 return (DCite)getOrCreate(DCite.class, count) ;
	}
	public DTime _cite_(final int count) {
		 _cite(count) ;
		 return this ;
	}
	public DCite _cite(final String jif) {
		return _cite().jif(jif) ;
	}
	public DTime _cite_(final String jif) {
		_cite(jif) ;
		return this;
	}
	public DCite _citeText(final String textValue) {
		return _cite().setHtmlExtTextValue(textValue) ;
	}
	public DTime _citeText_(final String textValue) {
		_citeText(textValue) ;
		return this ;
	}
	public DCite _cite(BaseHtmlElement... elements) {
		final DCite answer = _cite() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _cite_(BaseHtmlElement... elements) {
		_button(elements) ;
		return this ;
	}

	public DCode _code() {
		 return _code(-1) ;
	}
	public DTime _code_() {
		_code() ;
		return this ;
	}
	public DCode _code(final int count) {
		 return (DCode)getOrCreate(DCode.class, count) ;
	}
	public DTime _code_(final int count) {
		_code(count) ;
		return this ;
	}
	public DCode _code(final String jif) {
		return _code().jif(jif) ;
	}
	public DTime _code_(final String jif) {
		_code(jif) ;
		return this ;
	}
	public DCode _codeText(final String textValue) {
		return _code().setHtmlExtTextValue(textValue) ;
	}
	public DTime _codeText_(final String textValue) {
		_codeText(textValue) ;
		return this;
	}
	public DCode _code(BaseHtmlElement... elements) {
		final DCode answer = _code() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _code_(BaseHtmlElement... elements) {
		_code(elements) ;
		return this ;
	}

	public DCommand _command() {
		 return _command(-1) ;
	}
	public DTime _command_() {
		_command() ;
		return this ;
	}
	public DCommand _command(final int count) {
		 return (DCommand)getOrCreate(DCommand.class, count) ;
	}
	public DTime _command_(final int count) {
		_command(count) ;
		return this ;
	}
	public DCommand _command(final String jif) {
		return _command().jif(jif) ;
	}
	public DTime _command_(final String jif) {
		_command(jif) ;
		return this ;
	}

	public DDataList _datalist() {
		 return _datalist(-1) ;
	}
	public DTime _datalist_() {
		_datalist() ;
		return this ;
	}
	public DDataList _datalist(final int count) {
		 return (DDataList)getOrCreate(DDataList.class, count) ;
	}
	public DTime _datalist_(final int count) {
		_datalist(count) ;
		return this ;
	}
	public DDataList _datalist(final String jif) {
		return _datalist().jif(jif) ;
	}
	public DTime _datalist_(final String jif) {
		_datalist(jif) ;
		return this ;
	}
	public DDataList _datalist(BaseHtmlElement... elements) {
		final DDataList answer = _datalist() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _datalist_(BaseHtmlElement... elements) {
		_datalist(elements) ;
		return this ;
	}

	public DDel _del() {
		 return _del(-1) ;
	}
	public DTime _del_() {
		 _del() ;
		 return this ;
	}
	public DDel _del(final int count) {
		 return (DDel)getOrCreate(DDel.class, count) ;
	}
	public DTime _del_(final int count) {
		_del(count) ;
		return this ;
	}
	public DDel _del(final String jif) {
		return _del().jif(jif) ;
	}
	public DTime _del_(final String jif) {
		_del(jif) ;
		return this ;
	}
	public DDel _delText(final String textValue) {
		return _del().setHtmlExtTextValue(textValue) ;
	}
	public DTime _delText_(final String textValue) {
		_delText(textValue) ;
		return this ;
	}

	public DDfn _dfn() {
		 return _dfn(-1) ;
	}
	public DTime _dfn_() {
		_dfn() ;
		return this ;
	}
	public DDfn _dfn(final int count) {
		 return (DDfn)getOrCreate(DDfn.class, count) ;
	}
	public DTime _dfn_(final int count) {
		_dfn(count) ;
		return this ;
	}
	public DDfn _dfn(final String jif) {
		return _dfn().jif(jif) ;
	}
	public DTime _dfn_(final String jif) {
		_dfn().jif(jif) ;
		return this ;
	}
	public DDfn _dfnText(final String textValue) {
		return _dfn().setHtmlExtTextValue(textValue) ;
	}
	public DTime _dfnText_(final String textValue) {
		_dfnText(textValue) ;
		return this ;
	}
	public DDfn _dfn(BaseHtmlElement... elements) {
		final DDfn answer = _dfn() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _dfn_(BaseHtmlElement... elements) {
		_dfn(elements) ;
		return this ;
	}

	public DEm _em() {
		 return _em(-1) ;
	}
	public DTime _em_() {
		_em() ;
		return this ;
	}
	public DEm _em(final int count) {
		 return (DEm)getOrCreate(DEm.class, count) ;
	}
	public DTime _em_(final int count) {
		_em(count) ;
		return this ;
	}
	public DEm _em(final String jif) {
		return _em().jif(jif) ;
	}
	public DTime _em_(final String jif) {
		_em(jif) ;
		return this ;
	}
	public DEm _emText(final String textValue) {
		return _em().setHtmlExtTextValue(textValue) ;
	}
	public DTime _emText_(final String textValue) {
		_emText(textValue) ;
		return this ;
	}
	public DEm _em(BaseHtmlElement... elements) {
		final DEm answer = _em() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _em_(BaseHtmlElement... elements) {
		_em(elements) ;
		return this ;
	}

	public DEmbed _embed() {
		 return _embed(-1) ;
	}
	public DTime _embed_() {
		_embed() ;
		return this ;
	}
	public DEmbed _embed(final int count) {
		 return (DEmbed)getOrCreate(DEmbed.class, count) ;
	}
	public DTime _embed_(final int count) {
		_embed(count) ;
		return this ;
	}
	public DEmbed _embed(final String jif) {
		return _embed().jif(jif) ;
	}
	public DTime _embed_(final String jif) {
		_embed(jif) ;
		return this ;
	}

	public DI _i() {
		 return _i(-1) ;
	}
	public DTime _i_() {
		_i() ;
		return this ;
	}
	public DI _i(final int count) {
		 return (DI)getOrCreate(DI.class, count) ;
	}
	public DTime _i_(final int count) {
		_i(count) ;
		return this ;
	}
	public DI _i(final String jif) {
		return _i().jif(jif) ;
	}
	public DTime _i_(final String jif) {
		_i(jif) ;
		return this ;
	}
	public DI _iText(final String textValue) {
		return _i().setHtmlExtTextValue(textValue) ;
	}
	public DTime _iText_(final String textValue) {
		_iText(textValue) ;
		return this ;
	}
	public DI _i(BaseHtmlElement... elements) {
		final DI answer = _i() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _i_(BaseHtmlElement... elements) {
		_i(elements) ;
		return this ;
	}

	public DIFrame _iframe() {
		 return _iframe(-1) ;
	}
	public DTime _iframe_() {
		_iframe() ;
		return this ;
	}
	public DIFrame _iframe(final int count) {
		 return (DIFrame)getOrCreate(DIFrame.class, count) ;
	}
	public DTime _iframe_(final int count) {
		_iframe(count) ;
		return this ;
	}
	public DIFrame _iframe(final String jif) {
		return _iframe().jif(jif) ;
	}
	public DTime _iframe_(final String jif) {
		_iframe(jif) ;
		return this ;
	}

	public DImg _img() {
		 return _img(-1) ;
	}
	public DTime _img_() {
		_img() ;
		return this ;
	}
	public DImg _img(final int count) {
		 return (DImg)getOrCreate(DImg.class, count) ;
	}
	public DTime _img_(final int count) {
		_img(count) ;
		return this ;
	}
	public DImg _img(final String jif) {
		return _img().jif(jif) ;
	}
	public DTime _img_(final String jif) {
		_img(jif) ;
		return this ;
	}
	public DImg _img(final String srcUrl, final String alt) {
		return _img().setHtmlSrc(srcUrl).setHtmlAlt(alt) ;
	}
	public DTime _img_(final String srcUrl, final String alt) {
		_img(srcUrl, alt) ;
		return this ;
	}

	public DInput _input() {
		 return _input(-1) ;
	}
	public DTime _input_() {
		_input() ;
		return this ;
	}
	public DInput _input(final int count) {
		 return (DInput)getOrCreate(DInput.class, count) ;
	}
	public DTime _input_(final int count) {
		_input(count) ;
		return this ;
	}
	public DInput _input(final String jif) {
		return _input().jif(jif) ;
	}
	public DTime _input_(final String jif) {
		_input(jif) ;
		return this ;
	}
	public DInput _inputValue(final String value) {
		return _input().setHtmlValue(value) ;
	}
	public DTime _inputValue_(final String value) {
		_inputValue(value) ;
		return this ;
	}

	public DIns _ins() {
		 return _ins(-1) ;
	}
	public DTime _ins_() {
		_ins() ;
		return this ;
	}
	public DIns _ins(final int count) {
		 return (DIns)getOrCreate(DIns.class, count) ;
	}
	public DTime _ins_(final int count) {
		_ins(count) ;
		return this ;
	}
	public DIns _ins(final String jif) {
		return _ins().jif(jif) ;
	}
	public DTime _ins_(final String jif) {
		_ins(jif) ;
		return this ;
	}
	public DIns _insText(final String textValue) {
		return _ins().setHtmlExtTextValue(textValue) ;
	}
	public DTime _insText_(final String textValue) {
		_insText(textValue) ;
		return this ;
	}

	public DKbd _kbd() {
		 return _kbd(-1) ;
	}
	public DTime _kbd_() {
		_kbd() ;
		return this ;
	}
	public DKbd _kbd(final int count) {
		 return (DKbd)getOrCreate(DKbd.class, count) ;
	}
	public DTime _kbd_(final int count) {
		_kbd(count) ;
		return this ;
	}
	public DKbd _kbd(final String jif) {
		return _kbd().jif(jif) ;
	}
	public DTime _kbd_(final String jif) {
		_kbd(jif) ;
		return this ;
	}
	public DKbd _kbdText(final String textValue) {
		return _kbd().setHtmlExtTextValue(textValue) ;
	}
	public DTime _kbdText_(final String textValue) {
		_kbdText(textValue) ;
		return this ;
	}
	public DKbd _kbd(BaseHtmlElement... elements) {
		final DKbd answer = _kbd() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _kbd_(BaseHtmlElement... elements) {
		_kbd(elements) ;
		return this ;
	}

	public DKeyGen _keygen() {
		 return _keygen(-1) ;
	}
	public DTime _keygen_() {
		_keygen() ;
		return this ;
	}
	public DKeyGen _keygen(final int count) {
		 return (DKeyGen)getOrCreate(DKeyGen.class, count) ;
	}
	public DTime _keygen_(final int count) {
		_keygen(count) ;
		return this ;
	}
	public DKeyGen _keygen(final String jif) {
		return _keygen().jif(jif) ;
	}
	public DTime _keygen_(final String jif) {
		_keygen(jif) ;
		return this ;
	}

	public DLabel _label() {
		 return _label(-1) ;
	}
	public DTime _label_() {
		_label() ;
		return this ;
	}
	public DLabel _label(final int count) {
		 return (DLabel)getOrCreate(DLabel.class, count) ;
	}
	public DTime _label_(final int count) {
		_label(count) ;
		return this ;
	}
	public DLabel _label(final String jif) {
		return _label().jif(jif) ;
	}
	public DTime _label_(final String jif) {
		_label(jif) ;
		return this ;
	}
	public DLabel _labelText(final String textValue) {
		return _label().setHtmlExtTextValue(textValue) ;
	}
	public DTime _labelText_(final String textValue) {
		_labelText(textValue) ;
		return this;
	}
	public DLabel _label(BaseHtmlElement... elements) {
		final DLabel answer = _label() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _label_(BaseHtmlElement... elements) {
		_label(elements) ;
		return this ;
	}

	public DMap _map() {
		 return _map(-1) ;
	}
	public DTime _map_() {
		_map() ;
		return this ;
	}
	public DMap _map(final int count) {
		 return (DMap)getOrCreate(DMap.class, count) ;
	}
	public DTime _map_(final int count) {
		_map(count) ;
		return this ;
	}
	public DMap _map(final String jif) {
		return _map().jif(jif) ;
	}
	public DTime _map_(final String jif) {
		_map(jif) ;
		return this ;
	}
	public DMap _map(BaseHtmlElement... elements) {
		final DMap answer = _map() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _map_(BaseHtmlElement... elements) {
		_map(elements) ;
		return this ;
	}

	public DMark _mark() {
		 return _mark(-1) ;
	}
	public DTime _mark_() {
		_mark() ;
		return this ;
	}
	public DMark _mark(final int count) {
		 return (DMark)getOrCreate(DMark.class, count) ;
	}
	public DTime _mark_(final int count) {
		_mark(count) ;
		return this ;
	}
	public DMark _mark(final String jif) {
		return _mark().jif(jif) ;
	}
	public DTime _mark_(final String jif) {
		_mark(jif) ;
		return this ;
	}
	public DMark _mark(BaseHtmlElement... elements) {
		final DMark answer = _mark() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _mark_(BaseHtmlElement... elements) {
		_mark(elements) ;
		return this ;
	}

// MATH!!!

	public DMeter _meter() {
		 return _meter(-1) ;
	}
	public DTime _meter_() {
		_meter() ;
		return this ;
	}
	public DMeter _meter(final int count) {
		 return (DMeter)getOrCreate(DMeter.class, count) ;
	}
	public DTime _meter_(final int count) {
		_meter(count) ;
		return this ;
	}
	public DMeter _meter(final String jif) {
		return _meter().jif(jif) ;
	}
	public DTime _meter_(final String jif) {
		_meter(jif) ;
		return this ;
	}
	public DMeter _meter(final String min, final String max) {
		return _meter().setHtmlMin(min).setHtmlMax(max) ;
	}
	public DTime _meter_(final String min, final String max) {
		_meter(min, max) ;
		return this ;
	}
	public DMeter _meter(final String min, final String max, final String value) {
		return _meter(min, max).setHtmlValue(value) ;
	}
	public DTime _meter_(final String min, final String max, final String value) {
		_meter(min, max, value) ;
		return this ;
	}
	public DMeter _meter(final double min, final double max) {
		return _meter().setHtmlMin(min).setHtmlMax(max) ;
	}
	public DTime _meter_(final double min, final double max) {
		_meter(min, max) ;
		return this ;
	}
	public DMeter _meter(final double min, final double max, final double value) {
		return _meter(min, max).setHtmlValue(value) ;
	}
	public DTime _meter_(final double min, final double max, final double value) {
		_meter(min, max, value) ;
		return this ;
	}
	public DMeter _meter(BaseHtmlElement... elements) {
		final DMeter answer = _meter() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _meter_(BaseHtmlElement... elements) {
		_meter(elements) ;
		return this ;
	}

	public DNoScript _noscript() {
		 return _noscript(-1) ;
	}
	public DTime _noscript_() {
		_noscript() ;
		return this ;
	}
	public DNoScript _noscript(final int count) {
		 return (DNoScript)getOrCreate(DNoScript.class, count) ;
	}
	public DTime _noscript_(final int count) {
		_noscript(count) ;
		return this ;
	}
	public DNoScript _noscript(final String jif) {
		return _noscript().jif(jif) ;
	}
	public DTime _noscript_(final String jif) {
		_noscript(jif) ;
		return this ;
	}

	public DObject _object() {
		 return _object(-1) ;
	}
	public DTime _object_() {
		_object() ;
		return this ;
	}
	public DObject _object(final int count) {
		 return (DObject)getOrCreate(DObject.class, count) ;
	}
	public DTime _object_(final int count) {
		_object(count) ;
		return this ;
	}
	public DObject _object(final String jif) {
		return _object().jif(jif) ;
	}
	public DTime _object_(final String jif) {
		_object(jif) ;
		return this ;
	}
	public DObject _object(DParam... elements) {
		final DObject answer = _object() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _object_(DParam... elements) {
		_object(elements) ;
		return this ;
	}

	public DOutput _output() {
		 return _output(-1) ;
	}
	public DTime _output_() {
		_output() ;
		return this ;
	}
	public DOutput _output(final int count) {
		 return (DOutput)getOrCreate(DOutput.class, count) ;
	}
	public DTime _output_(final int count) {
		_output(count) ;
		return this ;
	}
	public DOutput _output(final String jif) {
		return _output().jif(jif) ;
	}
	public DTime _output_(final String jif) {
		_output(jif) ;
		return this ;
	}
	public DOutput _output(BaseHtmlElement... elements) {
		final DOutput answer = _output() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _output_(BaseHtmlElement... elements) {
		_output(elements) ;
		return this ;
	}

	public DProgress _progress() {
		 return _progress(-1) ;
	}
	public DTime _progress_() {
		_progress() ;
		return this ;
	}
	public DProgress _progress(final int count) {
		 return (DProgress)getOrCreate(DProgress.class, count) ;
	}
	public DTime _progress_(final int count) {
		_progress(count) ;
		return this ;
	}
	public DProgress _progress(final String jif) {
		return _progress().jif(jif) ;
	}
	public DTime _progress_(final String jif) {
		_progress(jif) ;
		return this ;
	}
	public DProgress _progress(final double max, final double value) {
		return _progress().setHtmlMax(max).setHtmlValue(value) ;
	}
	public DTime _progress_(final double max, final double value) {
		_progress(max, value) ;
		return this ;
	}
	public DProgress _progress(final String max, final String value) {
		return _progress().setHtmlMax(max).setHtmlValue(value) ;
	}
	public DTime _progress_(final String max, final String value) {
		_progress(max, value) ;
		return this ;
	}
	public DProgress _progress(BaseHtmlElement... elements) {
		final DProgress answer = _progress() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _progress_(BaseHtmlElement... elements) {
		_progress(elements) ;
		return this ;
	}

	public DQ _q() {
		 return _q(-1) ;
	}
	public DTime _q_() {
		_q();
		return this ;
	}
	public DQ _q(final int count) {
		 return (DQ)getOrCreate(DQ.class, count) ;
	}
	public DTime _q_(final int count) {
		_q(count) ;
		return this ;
	}
	public DQ _q(final String jif) {
		return _q().jif(jif) ;
	}
	public DTime _q_(final String jif) {
		_q(jif) ;
		return this ;
	}
	public DQ _qText(final String textValue) {
		return _q().setHtmlExtTextValue(textValue) ;
	}
	public DTime _qText_(final String textValue) {
		_qText(textValue) ;
		return this ;
	}
	public DQ _q(BaseHtmlElement... elements) {
		final DQ answer = _q() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _q_(BaseHtmlElement... elements) {
		_q(elements) ;
		return this ;
	}

	public DRuby _ruby() {
		 return _ruby(-1) ;
	}
	public DTime _ruby_() {
		_ruby() ;
		return this ;
	}
	public DRuby _ruby(final int count) {
		 return (DRuby)getOrCreate(DRuby.class, count) ;
	}
	public DTime _ruby_(final int count) {
		_ruby(count) ;
		return this ;
	}
	public DRuby _ruby(final String jif) {
		return _ruby().jif(jif) ;
	}
	public DTime _ruby_(final String jif) {
		_ruby(jif) ;
		return this ;
	}
	public DRuby _ruby(BaseHtmlElement... elements) {
		final DRuby answer = _ruby() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _ruby_(BaseHtmlElement... elements) {
		_ruby(elements) ;
		return this ;
	}

	public DSamp _samp() {
		 return _samp(-1) ;
	}
	public DTime _samp_() {
		_samp() ;
		return this ;
	}
	public DSamp _samp(final int count) {
		 return (DSamp)getOrCreate(DSamp.class, count) ;
	}
	public DTime _samp_(final int count) {
		_samp(count) ;
		return this ;
	}
	public DSamp _samp(final String jif) {
		return _samp().jif(jif) ;
	}
	public DTime _samp_(final String jif) {
		_samp(jif) ;
		return this ;
	}
	public DSamp _sampText(final String textValue) {
		return _samp().setHtmlExtTextValue(textValue) ;
	}
	public DTime _sampText_(final String textValue) {
		_sampText(textValue) ;
		return this ;
	}
	public DSamp _samp(BaseHtmlElement... elements) {
		final DSamp answer = _samp() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _samp_(BaseHtmlElement... elements) {
		_samp(elements) ;
		return this ;
	}

	public DScript _script() {
		 return _script(-1) ;
	}
	public DTime _script_() {
		_script();
		return this ;
	}
	public DScript _script(final int count) {
		 return (DScript)getOrCreate(DScript.class, count) ;
	}
	public DTime _script_(final int count) {
		_script(count) ;
		return this ;
	}
	public DScript _script(final String jif) {
		return _script().jif(jif) ;
	}
	public DTime _script_(final String jif) {
		_script(jif) ;
		return this ;
	}
	public DScript _scriptText(final String text) {
		return _script().setHtmlText(text) ;
	}
	public DTime _scriptText_(final String text) {
		_scriptText(text) ;
		return this ;
	}
	public DScript _scriptText(final String text, final String type) {
		return _scriptText(text).setHtmlType(type) ;
	}
	public DTime _scriptText_(final String text, final String type) {
		_scriptText(text, type) ;
		return this ;
	}


	public DSelect _select() {
		 return _select(-1) ;
	}
	public DTime _select_() {
		_select();
		return this ;
	}
	public DSelect _select(final int count) {
		 return (DSelect)getOrCreate(DSelect.class, count) ;
	}
	public DTime _select_(final int count) {
		_select(count);
		return this ;
	}
	public DSelect _select(final String jif) {
		return _select().jif(jif) ;
	}
	public DTime _select_(final String jif) {
		_select(jif) ;
		return this ;
	}
	public DSelect _select(BaseHtmlElement... elements) {
		final DSelect answer = _select() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _select_(BaseHtmlElement... elements) {
		_select(elements) ;
		return this ;
	}

	public DSmall _small() {
		 return _small(-1) ;
	}
	public DTime _small_() {
		_small() ;
		return this ;
	}
	public DSmall _small(final int count) {
		 return (DSmall)getOrCreate(DSmall.class, count) ;
	}
	public DTime _small_(final int count) {
		_small(count) ;
		return this ;
	}
	public DSmall _small(final String jif) {
		return _small().jif(jif) ;
	}
	public DTime _small_(final String jif) {
		_small(jif) ;
		return this ;
	}
	public DSmall _smallText(final String textValue) {
		return _small().setHtmlExtTextValue(textValue) ;
	}
	public DTime _smallText_(final String textValue) {
		_smallText(textValue) ;
		return this ;
	}
	public DSmall _small(BaseHtmlElement... elements) {
		final DSmall answer = _small() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _small_(BaseHtmlElement... elements) {
		_small(elements) ;
		return this ;
	}

	public DSpan _span() {
		 return _span(-1) ;
	}
	public DTime _span_() {
		_span() ;
		return this ;
	}
	public DSpan _span(final int count) {
		 return (DSpan)getOrCreate(DSpan.class, count) ;
	}
	public DTime _span_(final int count) {
		_span(count) ;
		return this ;
	}
	public DSpan _span(final String jif) {
		return _span().jif(jif) ;
	}
	public DTime _span_(final String jif) {
		_span(jif) ;
		return this ;
	}
	public DSpan _span(BaseHtmlElement... elements) {
		final DSpan answer = _span() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _span_(BaseHtmlElement... elements) {
		_span(elements) ;
		return this ;
	}

	public DStrong _strong() {
		 return _strong(-1) ;
	}
	public DTime _strong_() {
		_strong() ;
		return this ;
	}
	public DStrong _strong(final int count) {
		 return (DStrong)getOrCreate(DStrong.class, count) ;
	}
	public DTime _strong_(final int count) {
		_strong(count) ;
		return this ;
	}
	public DStrong _strong(final String jif) {
		return _strong().jif(jif);
	}
	public DTime _strong_(final String jif) {
		_strong(jif) ;
		return this ;
	}
	public DStrong _strongText(final String textValue) {
		return _strong().setHtmlExtTextValue(textValue) ;
	}
	public DTime _strongText_(final String textValue) {
		_strongText(textValue) ;
		return this ;
	}
	public DStrong _strong(BaseHtmlElement... elements) {
		final DStrong answer = _strong() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _strong_(BaseHtmlElement... elements) {
		_strong(elements) ;
		return this ;
	}

	public DSub _sub() {
		 return _sub(-1) ;
	}
	public DTime _sub_() {
		_sub();
		return this ;
	}
	public DSub _sub(final int count) {
		 return (DSub)getOrCreate(DSub.class, count) ;
	}
	public DTime _sub_(final int count) {
		_sub(count) ;
		return this ;
	}
	public DSub _sub(final String jif) {
		return _sub().jif(jif) ;
	}
	public DTime _sub_(final String jif) {
		_sub(jif) ;
		return this ;
	}
	public DSub _subText(final String textValue) {
		return _sub().setHtmlExtTextValue(textValue) ;
	}
	public DTime _subText_(final String textValue) {
		_subText(textValue) ;
		return this ;
	}
	public DSub _sub(BaseHtmlElement... elements) {
		final DSub answer = _sub() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _sub_(BaseHtmlElement... elements) {
		_sub(elements) ;
		return this ;
	}

	public DSup _sup() {
		 return _sup(-1) ;
	}
	public DTime _sup_() {
		_sup(); 
		return this ;
	}
	public DSup _sup(final int count) {
		 return (DSup)getOrCreate(DSup.class, count) ;
	}
	public DTime _sup_(final int count) {
		_sup(count) ;
		return this ;
	}
	public DSup _sup(final String jif) {
		return _sup().jif(jif) ;
	}
	public DTime _sup_(final String jif) {
		_sup(jif) ;
		return this ;
	}
	public DSup _supText(final String textValue) {
		return _sup().setHtmlExtTextValue(textValue) ;
	}
	public DTime _supText_(final String textValue) {
		_supText(textValue) ;
		return this ;
	}
	public DSup _sup(BaseHtmlElement... elements) {
		final DSup answer = _sup() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _sup_(BaseHtmlElement... elements) {
		_sup(elements) ;
		return this ;
	}
	

// SVG !!!

	public DTextArea _textarea() {
		 return _textarea(-1) ;
	}
	public DTime _textarea_() {
		_textarea() ;
		return this ;
	}
	public DTextArea _textarea(final int count) {
		 return (DTextArea)getOrCreate(DTextArea.class, count) ;
	}
	public DTime _textarea_(final int count) {
		_textarea(count);
		return this ;
	}
	public DTextArea _textarea(final String jif) {
		return _textarea().jif(jif) ;
	}
	public DTime _textarea_(final String jif) {
		_textarea(jif) ;
		return this ;
	}

	public DTime _time() {
		 return _time(-1) ;
	}
	public DTime _time_() {
		_time();
		return this;
	}
	public DTime _time(final int count) {
		 return (DTime)getOrCreate(DTime.class, count) ;
	}
	public DTime _time_(final int count) {
		_time(count) ;
		return this ;
	}
	public DTime _time(final String jif) {
		return _time().jif(jif) ;
	}
	public DTime _time_(final String jif) {
		_time(jif) ;
		return this ;
	}
	public DTime _timeDateTime(final String dateTime) {
		return _time().setHtmlDateTime(dateTime) ;
	}
	public DTime _timeDateTime_(final String dateTime) {
		_timeDateTime(dateTime);
		return this ;
	}
	public DTime _time(BaseHtmlElement... elements) {
		final DTime answer = _time() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _time_(BaseHtmlElement... elements) {
		_time(elements) ;
		return this ;
	}

	public DVar _var() {
		 return _var(-1) ;
	}
	public DTime _var_() {
		_var() ;
		return this ;
	}
	public DVar _var(final int count) {
		 return (DVar)getOrCreate(DVar.class, count) ;
	}
	public DTime _var_(final int count) {
		_var(count) ;
		return this ;
	}
	public DVar _var(final String jif) {
		return _var().jif(jif) ;
	}
	public DTime _var_(final String jif) {
		_var(jif) ;
		return this ;
	}
	public DVar _varText(final String textValue) {
		return _var().setHtmlExtTextValue(textValue) ;
	}
	public DTime _varText_(final String textValue) {
		_varText(textValue) ;
		return this ;
	}
	public DVar _var(BaseHtmlElement... elements) {
		final DVar answer = _var() ;
		answer.add(elements) ;
		return answer ;
	}
	public DTime _var_(BaseHtmlElement... elements) {
		_var(elements) ;
		return this ;
	}

	public DVideo _video() {
		 return _video(-1) ;
	}
	public DTime _video_() {
		_video();
		return this ;
	}
	public DVideo _video(final int count) {
		 return (DVideo)getOrCreate(DVideo.class, count) ;
	}
	public DTime _video_(final int count) {
		_video(count) ;
		return this ;
	}
	public DVideo _video(final String jif) {
		return _video().jif(jif) ;
	}
	public DTime _video_(final String jif) {
		_video(jif) ;
		return this ;
	}

	public DWbr _wbr() {
		 return _wbr(-1) ;
	}
	public DTime _wbr_() {
		_wbr() ;
		return this ;
	}
	public DWbr _wbr(final int count) {
		 return (DWbr)getOrCreate(DWbr.class, count) ;
	}
	public DTime _wbr_(final int count) {
		_wbr(count);
		return this ;
	}
	public DWbr _wbr(final String jif) {
		return _wbr().jif(jif) ;
	}
	public DTime _wbr_(final String jif) {
		_wbr(jif) ;
		return this ;
	}
}

