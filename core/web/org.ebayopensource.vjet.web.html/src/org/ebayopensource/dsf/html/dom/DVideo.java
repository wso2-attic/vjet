/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import java.util.Collection;

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
<a href='http://dev.w3.org/html5/spec/Overview.html#video'>W3C video</a>
<p>
A video element represents a video or movie.
<p>
Content may be provided inside the video element. User agents should not show 
this content to the user; it is intended for older Web browsers which do not 
support video, so that legacy video plugins can be tried, or to show text to the 
users of these older browser informing them of how to access the video contents.
 */
public class DVideo extends BaseHtml50Element 
//	implements /* HTML 5.0 */ IEmbeddedContent
{
	private static final long serialVersionUID = -4186319562242113949L;

	//
	// Constructor(s)
	//
	public DVideo() {
		super(HtmlTypeEnum.VIDEO);
	}
	
	public DVideo(final DHtmlDocument doc) {
		super(doc, HtmlTypeEnum.VIDEO);
	}
	
	public DVideo(final String jif) {
		this() ;
		jif(jif) ;
	}

	public DVideo(final int height, final int width) {
		this() ;
		setHtmlHeight(height) ;
		setHtmlWidth(width) ;
	}
	
	public DVideo(final String height, final String width) {
		this() ;
		setHtmlHeight(height) ;
		setHtmlWidth(width) ;
	}
	
	public DVideo(BaseHtmlElement... elems) {
		this() ;
		add(elems) ;
	}
	
	//
	// Framework
	//
	@Override
	public HtmlTypeEnum htmlType() {
		return HtmlTypeEnum.VIDEO ;
	}
	
	//
	// HTML Attributes
	//
	// autoplay
	public boolean getHtmlAutoPlay() {
		return hasHtmlAttribute(EHtmlAttr.autoplay) ;
	}
	public DVideo setHtmlAutoPlay(final boolean autoPlay) {
		setHtmlAttribute(EHtmlAttr.autoplay, autoPlay) ;
		return this ;
	}
	public DVideo setHtmlAutoPlay(final String autoPlay) {
		setHtmlAttribute(EHtmlAttr.autoplay, autoPlay) ;
		return this ;
	}
	
	// autobuffer
	public boolean getHtmlAutoBuffer() {
		return hasHtmlAttribute(EHtmlAttr.autobuffer) ;
	}
	public DVideo setHtmlAutoBuffer(final boolean autobuffer) {
		setHtmlAttribute(EHtmlAttr.autobuffer, autobuffer) ;
		return this ;
	}
	public DVideo setHtmlAutoBuffer(final String autobuffer) {
		setHtmlAttribute(EHtmlAttr.autobuffer, autobuffer) ;
		return this ;
	}
	
	// loop
	public boolean getHtmlLoop() {
		return hasHtmlAttribute(EHtmlAttr.loop) ;
	}
	public DVideo setHtmlLoop(final boolean loop) {
		setHtmlAttribute(EHtmlAttr.loop, loop) ;
		return this ;
	}
	public DVideo setHtmlLoop(final String loop) {
		setHtmlAttribute(EHtmlAttr.loop, loop) ;
		return this ;
	}
	
	// controls
	public boolean getHtmlControls() {
		return hasHtmlAttribute(EHtmlAttr.controls) ;
	}
	public DVideo setHtmlControls(final boolean controls) {
		setHtmlAttribute(EHtmlAttr.controls, controls) ;
		return this ;
	}
	public DVideo setHtmlControls(final String controls) {
		setHtmlAttribute(EHtmlAttr.controls, controls) ;
		return this ;
	}
	
//	// end
//	public String getHtmlEnd() {
//		return getHtmlAttribute(EHtmlAttr.end);
//	}
//	public DVideo setHtmlEnd(final String end) {
//		setHtmlAttribute(EHtmlAttr.end, end);
//		return this ;
//	}
//	public DVideo setHtmlEnd(final int end) {
//		setHtmlAttribute(EHtmlAttr.end, end);
//		return this ;
//	}
	
	// height
	public String getHtmlHeight() {
		return getHtmlAttribute(EHtmlAttr.height);
	}
	public DVideo setHtmlHeight(final String height) {
		setHtmlAttribute(EHtmlAttr.height, height);
		return this ;
	}
	public DVideo setHtmlHeight(final int height) {
		setHtmlAttribute(EHtmlAttr.height, height);
		return this ;
	}
	
//	// loopEnd
//	public String getHtmlLoopEnd() {
//		return getHtmlAttribute(EHtmlAttr.loopend);
//	}
//	public DVideo setHtmlLoopEnd(final String loopend) {
//		setHtmlAttribute(EHtmlAttr.loopend, loopend);
//		return this ;
//	}
//	public DVideo setHtmlLoopEnd(final int loopend) {
//		setHtmlAttribute(EHtmlAttr.loopend, loopend);
//		return this ;
//	}
	
//	// loopStart
//	public String getHtmlLoopStart() {
//		return getHtmlAttribute(EHtmlAttr.loopstart);
//	}
//	public DVideo setHtmlLoopStart(final String loopstart) {
//		setHtmlAttribute(EHtmlAttr.loopstart, loopstart);
//		return this ;
//	}
//	public DVideo setHtmlLoopStart(final int loopstart) {
//		setHtmlAttribute(EHtmlAttr.loopstart, loopstart);
//		return this ;
//	}
	
//	// playCount
//	public String getHtmlPlayCount() {
//		return getHtmlAttribute(EHtmlAttr.playcount);
//	}
//	public DVideo setHtmlPlayCount(final String playcount) {
//		setHtmlAttribute(EHtmlAttr.playcount, playcount);
//		return this ;
//	}
//	public DVideo setHtmlPlayCount(final int playcount) {
//		setHtmlAttribute(EHtmlAttr.playcount, playcount);
//		return this ;
//	}
	
	// poster
	public String getHtmlPoster() {
		return getHtmlAttribute(EHtmlAttr.poster);
	}
	public DVideo setHtmlPoster(final String posterUrl) {
		setHtmlAttribute(EHtmlAttr.poster, posterUrl);
		return this ;
	}
	
	// src
	public String getHtmlSrc() {
		return getHtmlAttribute(EHtmlAttr.src);
	}
	public DVideo setHtmlSrc(final String srcUrl) {
		setHtmlAttribute(EHtmlAttr.src, srcUrl);
		return this ;
	}
	
//	// start
//	public String getHtmlStart() {
//		return getHtmlAttribute(EHtmlAttr.start);
//	}
//	public DVideo setHtmlStart(final String start) {
//		setHtmlAttribute(EHtmlAttr.start, start);
//		return this ;
//	}
//	public DVideo setHtmlStart(final int start) {
//		setHtmlAttribute(EHtmlAttr.start, start);
//		return this ;
//	}
	
	// width
	public String getHtmlWidth() {
		return getHtmlAttribute(EHtmlAttr.width);
	}
	public DVideo setHtmlWidth(final String width) {
		setHtmlAttribute(EHtmlAttr.width, width);
		return this ;
	}
	public DVideo setHtmlWidth(final int width) {
		setHtmlAttribute(EHtmlAttr.width, width);
		return this ;
	}

	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString()
			+ Z.fmt(EHtmlAttr.autoplay.getAttributeName(), "" + getHtmlAutoPlay())
			+ Z.fmt(EHtmlAttr.autobuffer.getAttributeName(), "" + getHtmlAutoBuffer())
			+ Z.fmt(EHtmlAttr.loop.getAttributeName(), "" + getHtmlLoop())
			+ Z.fmt(EHtmlAttr.controls.getAttributeName(), "" + getHtmlControls())
//			+ Z.fmt(EHtmlAttr.end.getAttributeName(), "" + getHtmlEnd())
			+ Z.fmt(EHtmlAttr.height.getAttributeName(), "" + getHtmlHeight())
//			+ Z.fmt(EHtmlAttr.loopend.getAttributeName(), "" + getHtmlLoopEnd())
//			+ Z.fmt(EHtmlAttr.loopstart.getAttributeName(), "" + getHtmlLoopStart())
//			+ Z.fmt(EHtmlAttr.playcount.getAttributeName(), "" + getHtmlPlayCount())
			+ Z.fmt(EHtmlAttr.poster.getAttributeName(), "" + getHtmlPoster())
			+ Z.fmt(EHtmlAttr.src.getAttributeName(), "" + getHtmlSrc())
//			+ Z.fmt(EHtmlAttr.start.getAttributeName(), "" + getHtmlStart())
			+ Z.fmt(EHtmlAttr.width.getAttributeName(), "" + getHtmlWidth()) ;
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
	public DVideo add(final DNode newChild) throws DOMException {
		super.add(newChild) ;
		return this ;
	}
	
	@Override
	public DVideo add(BaseHtmlElement... elems) throws DOMException {
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
	public DVideo add(final String value) throws DOMException {
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
	public DVideo addRaw(final String value) throws DOMException {
		super.addRaw(value) ;
		return this ;
	}
	
	/**
	 * This double dispatch approach provides the control point for the node
	 * to have customized behavior.
	 */
	@Override
	public DVideo dsfAccept(final IDNodeVisitor visitor) {
		super.dsfAccept(visitor) ;
		return this;
	}
	
	/**
	 * Broadcasts the event to any registered IDsfEventListner's.
	 * The listeners are broadcast to in the order they were maintained.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DVideo dsfBroadcast(final DsfEvent event) // must not be null
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
	public DVideo setDsfRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
		super.setDsfRelationshipVerifier(relationshipVerifier) ;
		return this;
	}
	
	@Override
	public DVideo cloned() {
		return (DVideo)super.cloned() ;
	}
	
    /**
     * set namespace for this node.
     * update the nodename based on the given namespace
     * @param namespace
     * @return
     */
    @Override
    public DVideo setDsfNamespace(DNamespace namespace){
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
	public DVideo setHtmlAccessKey(final String accessKey) {
		super.setHtmlAccessKey(accessKey) ;
		return this ;
	}
		
	/**
	 * set class name, overwrite current class(es)
	 */
	@Override
	public DVideo setHtmlClassName(final String className) {
		super.setHtmlClassName(className) ;
		return this ;
	}
	@Override
	public DVideo setHtmlClassName(final CssClassConstant ccc) {
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
	public DVideo setHtmlContentEditable(final String editable) {
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
	public DVideo setHtmlContextMenu(final String contextMenu) {
		super.setHtmlContextMenu(contextMenu) ;
		return this ;
	}
	
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	@Override
	public DVideo setHtmlDir(final String dir) {
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
	public DVideo setHtmlDraggable(final String draggable) {  // true, false, auto
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
	public DVideo setHtmlDraggable(final boolean draggable) {  // true, false
		super.setHtmlDraggable(draggable) ;
		return this ;
	}
	
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DVideo setHtmlHidden(final String hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DVideo setHtmlHidden(final boolean hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	@Override
	public DVideo setHtmlId(String id) {
		super.setHtmlId(id) ;
		return this ;
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */	
	@Override
	public DVideo setHtmlId(CssIdConstant id) {
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
	public DVideo setHtmlItem(final String item) {
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
	public DVideo setHtmlItemProp(final String itemProp) {
		super.setHtmlItemProp(itemProp) ;
		return this ;
	}	
	
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	@Override
	public DVideo setHtmlLang(final String lang) {
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
	public DVideo setHtmlSpellCheck(final String spellCheck) {
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
	public DVideo setHtmlSpellCheck(final boolean spellCheck) {
		super.setHtmlSpellCheck(spellCheck) ;
		return this ;
	}	

	@Override
	public DVideo setHtmlStyleAsString(final String styleString) {
		super.setHtmlStyleAsString(styleString) ;
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 */
	@Override
	public DVideo setHtmlStyle(final ICssStyleDeclaration style) {
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
	public DVideo setHtmlSubject(final String subject) {
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
	public DVideo setHtmlTabIndex(final String tabIndex) {  // HTML 5.0
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
	public DVideo setHtmlTabIndex(final int tabIndex) {  // HTML 5.0
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
	public DVideo setHtmlTitle(final String title) {
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
	public DVideo setHtmlOnAbort(final String script) {
		super.setHtmlOnAbort(script) ;
		return this ;
	}
	
	/**
	 * The onblur event occurs when an object loses focus.
	 * not supported on BODY
	 */
	@Override
	public DVideo setHtmlOnBlur(final String onBlur) {
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
	public DVideo setHtmlOnCanPlay(final String script) {
		super.setHtmlOnCanPlay(script) ;
		return this ;
	}
	
	/**
	 *  The user agent estimates that if playback were to be started now, the 
	 *  media resource could be rendered at the current playback rate all the way 
	 *  to its end without having to stop for further buffering. 
	 */
	@Override
	public DVideo setHtmlOnCanPlayThrough(final String script) {
		super.setHtmlOnCanPlayThrough(script) ;
		return this ;
	}
	
	// onchange
	@Override
	public DVideo setHtmlOnChange(final String script) {
		super.setHtmlOnChange(script) ;
		return this ;
	}
	
	// onclick
	@Override
	public DVideo setHtmlOnClick(final String script) {
		super.setHtmlOnClick(script) ;
		return this ;
	}

	// oncontextmenu
	@Override
	public DVideo setHtmlOnContextMenu(final String script) {
		super.setHtmlOnContextMenu(script) ;
		return this ;
	}
	
	// ondblclick
	@Override
	public DVideo setHtmlOnDblClick(final String script) {
		super.setHtmlOnDblClick(script) ;
		return this ;
	}
	
	// ondrag
	@Override
	public DVideo setHtmlOnDrag(final String script) {
		super.setHtmlOnDrag(script) ;
		return this ;
	}
	
	// ondragend
	@Override
	public DVideo setHtmlOnDragEnd(final String script) {
		super.setHtmlOnDragEnd(script) ;
		return this ;
	}
	
	// ondragenter
	@Override
	public DVideo setHtmlOnDragEnter(final String script) {
		super.setHtmlOnDragEnter(script) ;
		return this ;
	}
	
	// ondragleave
	@Override
	public DVideo setHtmlOnDragLeave(final String script) {
		super.setHtmlOnDragLeave(script) ;
		return this ;
	}
	
	// ondragover
	@Override
	public DVideo setHtmlOnDragOver(final String script) {
		super.setHtmlOnDragOver(script) ;
		return this ;
	}
	
	// ondragstart
	@Override
	public DVideo setHtmlOnDragStart(final String script) {
		super.setHtmlOnDragStart(script) ;
		return this ;
	}
	
	// ondrop
	@Override
	public DVideo setHtmlOnDrop(final String script) {
		super.setHtmlOnDrop(script) ;
		return this ;
	}
	
	// ondurationchange
	@Override
	public DVideo setHtmlOnDurationChange(final String script) {
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
	public DVideo setHtmlOnEmptied(final String script) {
		super.setHtmlOnEmptied(script) ;
		return this ;
	}
	
	/**
	 * Playback has stopped because the end of the media resource was reached. 
	 */
	@Override
	public DVideo setHtmlOnEnded(final String script) {
		super.setHtmlOnEnded(script) ;
		return this ;
	}
	
	/**
	 * An error occurs while fetching the media data. 
	 * not supported on BODY
	 */
	@Override
	public DVideo setHtmlOnError(final String script) {
		super.setHtmlOnError(script) ;
		return this ;
	}

	/**
	 * onfocus - not supported on BODY
	 */
	@Override
	public DVideo setHtmlOnFocus(final String script) {
		super.setHtmlOnFocus(script) ;
		return this ;
	}
	
	/**
	 * onformchange
	 */
	@Override
	public DVideo setHtmlOnFormChange(final String script) {
		super.setHtmlOnFormChange(script) ;
		return this ;
	}
	
	/**
	 * onforminput
	 */
	@Override
	public DVideo setHtmlOnFormInput(final String script) {
		super.setHtmlOnFormInput(script) ;
		return this ;
	}
	
	/**
	 * oninput
	 */
	@Override
	public DVideo setHtmlOnInput(final String script) {
		super.setHtmlOnInput(script) ;
		return this ;
	}
	
	/**
	 * oninvalid
	 */
	@Override
	public DVideo setHtmlOnInvalid(final String script) {
		super.setHtmlOnInvalid(script) ;
		return this ;
	}
	
	/**
	 * onkeydown
	 */
	@Override
	public DVideo setHtmlOnKeyDown(final String script) {
		super.setHtmlOnKeyDown(script) ;
		return this ;
	}

	/**
	 * onkeypress
	 */
	@Override
	public DVideo setHtmlOnKeyPress(final String script) {
		super.setHtmlOnKeyPress(script) ;
		return this ;
	}
	
	/**
	 * onkeyup
	 */
	@Override
    public DVideo setHtmlOnKeyUp(final String script) {
    	super.setHtmlOnKeyUp(script);
    	return this ;
	}
    
	/**
	 * onload
	 */
	@Override
    public DVideo setHtmlOnLoad(final String script) {
    	super.setHtmlOnLoad(script) ;
    	return this ;
	}
    
	/**
	 * onloadeddata
	 */
	@Override
    public DVideo setHtmlOnLoadedData(final String script) {
    	super.setHtmlOnLoadedData(script) ;
    	return this ;
	}   
    
	/**
	 * onloadedmetadata
	 */
	@Override
    public DVideo setHtmlOnLoadedMetadata(final String script) {
    	super.setHtmlOnLoadedMetadata(script) ;
    	return this ;
	}  
	
	/**
	 * onloadstart
	 */
	@Override
    public DVideo setHtmlOnLoadStart(final String script) {
    	super.setHtmlOnLoadStart(script) ;
    	return this ;
	} 
    
	/**
	 * onmousedown
	 */
	@Override
	public DVideo setHtmlOnMouseDown(final String script){
		super.setHtmlOnMouseDown(script) ;
		return this ;
	}
	
	/**
	 * onmousemove
	 */
	@Override
	public DVideo setHtmlOnMouseMove(final String script) {
		super.setHtmlOnMouseMove(script) ;
		return this ;
	}
	
	/**
	 * onmouseout
	 */
	@Override
	public DVideo setHtmlOnMouseOut(final String script) {
		super.setHtmlOnMouseOut(script) ;
		return this ;
	}
	
	/**
	 * onmouseover
	 */
	@Override
	public DVideo setHtmlOnMouseOver(final String script) {
		super.setHtmlOnMouseOver(script) ;
		return this ;
	}
	
	/**
	 * onmouseup
	 */
	@Override
	public DVideo setHtmlOnMouseUp(final String script) {
		super.setHtmlOnMouseUp(script) ;
		return this ;
	}
    
	/**
	 * onmousewheel
	 */
	@Override
	public DVideo setHtmlOnMouseWheel(final String script) {
		super.setHtmlOnMouseWheel(script) ;
		return this ;
	}
    
	/**
	 * onpause
	 */
	@Override
	public DVideo setHtmlOnPause(final String script) {
		super.setHtmlOnPause(script) ;
		return this ;
	}  
    
	/**
	 * onplay
	 */
	@Override
	public DVideo setHtmlOnPlay(final String script) {
		super.setHtmlOnPlay(script) ;
		return this ;
	}
    
	/**
	 * onplaying
	 */
	@Override
	public DVideo setHtmlOnPlaying(final String script) {
		super.setHtmlOnPlaying(script) ;
		return this ;
	}
	
	/**
	 * onprogress
	 */
	@Override
	public DVideo setHtmlOnProgress(final String script) {
		super.setHtmlOnProgress(script) ;
		return this ;
	}
    
	/**
	 * onratechange
	 */
	@Override
	public DVideo setHtmlOnRateChange(final String script) {
		super.setHtmlOnRateChange(script) ;
		return this ;
	}
    
	/**
	 * onreadystatechange
	 */
	@Override
	public DVideo setHtmlOnReadyStateChange(final String script) {
		super.setHtmlOnReadyStateChange(script) ;
		return this ;
	}

	/**
	 * onscroll
	 */
	@Override
	public DVideo setHtmlOnScroll(final String script) {
		super.setHtmlOnScroll(script) ;
		return this ;
	}
	
	/**
	 * onseeked
	 */
	@Override
	public DVideo setHtmlOnSeeked(final String script) {
		super.setHtmlOnSeeked(script) ;
		return this ;
	}
    
	/**
	 * onseeking
	 */
	@Override
	public DVideo setHtmlOnSeeking(final String script) {
		super.setHtmlOnSeeking(script) ;
		return this ;
	}
	
	/**
	 * onselect
	 */
	@Override
	public DVideo setHtmlOnSelect(final String script) {
		super.setHtmlOnSelect(script) ;
		return this ;
	}
	
	/**
	 * onshow
	 */
	@Override
	public DVideo setHtmlOnShow(final String script) {
		super.setHtmlOnShow(script) ;
		return this ;
	}
	
	/**
	 * onstalled
	 */
	@Override
	public DVideo setHtmlOnStalled(final String script) {
		super.setHtmlOnStalled(script) ;
		return this ;
	}
	
	/**
	 * onsubmit
	 */
	@Override
	public DVideo setHtmlOnSubmit(final String script) {
		super.setHtmlOnSubmit(script) ;
		return this ;
	}
	
	/**
	 * onsuspend
	 */
	@Override
	public DVideo setHtmlOnSuspend(final String script) {
		super.setHtmlOnSuspend(script) ;
		return this ;
	}
	
	/**
	 * ontimeupdate
	 */
	@Override
	public DVideo setHtmlOnTimeUpdate(final String script) {
		super.setHtmlOnTimeUpdate(script) ;
		return this ;
	}
	
	/**
	 * onvolumechange
	 */
	@Override
	public DVideo setHtmlOnVolumeChange(final String script) {
		super.setHtmlOnVolumeChange(script) ;
		return this ;
	}
	
	/**
	 * onwaiting
	 */
	@Override
	public DVideo setHtmlOnWaiting(final String script) {
		super.setHtmlOnWaiting(script) ;
		return this ;
	}
	
	//
	// Framework - Event Wiring
	//
	@Override
	public DVideo add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		super.add(eventType, handler) ;
		return this ;
	}
	
	@Override
	public DVideo add(
		final EventType eventType, 
		final IJsFunc func)
	{
		super.add(eventType, func) ;
		return this ;
	}
	
	@Override
	public DVideo add(
		final EventType eventType, 
		final String jsText)
	{
		super.add(eventType, jsText) ;
		return this ;
	}
	
//	@Override
//	public DVideo add(final IDomActiveListener listener){
//		super.add(listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DVideo add(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.add(eventType, listener) ;
//		return this ;
//	}
//	
//	@Override
//	public DVideo removeListener(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.removeListener(eventType, listener) ;
//		return this ;
//	}
	
	//
	// Helpers
	//
	@Override
	public DVideo addBr() {
		super.addBr() ;
		return this ;
	}
	
	@Override
	public DVideo addBr(final int howMany){
		super.addBr(howMany) ;
		return this ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	@Override
	public DVideo addHtmlClassName(final String className) {
		super.addHtmlClassName(className) ;
		return this ;
	}
	
	@Override
	public DVideo addHtmlClassName(final CssClassConstant ccc) {
		super.addHtmlClassName(ccc) ;
		return this ;
	}
	
	@Override
	public DVideo jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Child Hooks (Transparent = Flow-Content/Phrasing-Content)
	//
	public DA _a() {
		 return _a(-1) ;
	}
	public DVideo _a_() {
		_a() ;
		return this ;
	}
	public DA _a(final int count) {
		 return (DA)getOrCreate(DA.class, count) ;
	}
	public DVideo _a_(final int count) {
		_a(count) ;
		return this ;
	}
	public DA _a(final String jif) {
		return _a().jif(jif) ;
	}
	public DVideo _a_(final String jif) {
		_a().jif(jif) ;
		return this ;
	}
	public DA _aText(final String value) {
		return _a().setHtmlExtTextValue(value) ;
	}
	public DVideo _aText_(final String textValue) {
		_aText(textValue) ;
		return this ;
	}
	public DA _a(final String value, final String url) {
		return _a().setHtmlExtTextValue(value).setHtmlHref(url) ;
	}
	public DVideo _a_(final String value, final String url) {
		_a(value, url) ;
		return this ;
	}

	public DAbbr _abbr() {
		 return _abbr(-1) ;
	}
	public DVideo _abbr_() {
		_abbr() ;
		return this ;
	}
	public DAbbr _abbr(final int count) {
		 return (DAbbr)getOrCreate(DAbbr.class, count) ;
	}
	public DVideo _abbr_(final int count) {
		 _abbr(count) ;
		 return this ;
	}
	public DAbbr _abbr(final String jif) {
		return _abbr().jif(jif) ;
	}
	public DVideo _abbr_(final String jif) {
		_abbr(jif) ;
		return this ;
	}
	public DAbbr _abbrText(final String textValue) {
		return _abbr().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _abbrText_(final String textValue) {
		_abbrText(textValue) ;
		return this ;
	}
	public DAbbr _abbr(BaseHtmlElement... elements) {
		final DAbbr answer = _abbr() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _abbr_(BaseHtmlElement... elements) {
		_abbr(elements) ;
		return this ;
	}

	public DAddress _address() {
		 return _address(-1) ;
	}
	public DVideo _address_() {
		_address() ;
		return this ;
	}
	public DAddress _address(final int count) {
		 return (DAddress)getOrCreate(DAddress.class, count) ;
	}
	public DVideo _address_(final int count) {
		 _address(count) ;
		 return this ;
	}
	public DAddress _address(final String jif) {
		return _address().jif(jif) ;
	}
	public DVideo _address_(final String jif) {
		_address(jif) ;
		return this ;
	}
	public DAddress _address(BaseHtmlElement... elements) {
		final DAddress answer = _address() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _address_(BaseHtmlElement... elements) {
		_address(elements) ;
		return this ;
	}

	public DArea _area() {
		 return _area(-1) ;
	}
	public DVideo _area_() {
		 _area(-1) ;
		 return this ;
	}
	public DArea _area(final int count) {
		 return (DArea)getOrCreate(DArea.class, count) ;
	}
	public DVideo _area_(final int count) {
		 _area(count) ;
		 return this;
	}
	public DArea _area(final String jif) {
		return _area().jif(jif) ;
	}
	public DVideo _area_(final String jif) {
		_area(jif) ;
		return this ;
	}

	public DArticle _article() {
		 return _article(-1) ;
	}
	public DVideo _article_() {
		 _article() ;
		 return this ;
	}
	public DArticle _article(final int count) {
		 return (DArticle)getOrCreate(DArticle.class, count) ;
	}
	public DVideo _article_(final int count) {
		_article(count) ;
		return this ;
	}
	public DArticle _article(final String jif) {
		return _article().jif(jif) ;
	}
	public DVideo _article_(final String jif) {
		_article(jif) ;
		return this ;
	}
	public DArticle _articleText(final String textValue) {
		return _article().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _articleText_(final String textValue) {
		_articleText(textValue) ;
		return this ;
	}
	public DArticle _article(BaseHtmlElement... elements) {
		final DArticle answer = _article() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _article_(BaseHtmlElement... elements) {
		_article(elements) ;
		return this ;
	}

	public DAside _aside() {
		 return _aside(-1) ;
	}
	public DVideo _aside_() {
		_aside() ;
		return this ;
	}
	public DAside _aside(final int count) {
		 return (DAside)getOrCreate(DAside.class, count) ;
	}
	public DVideo _aside_(final int count) {
		 _aside(count) ;
		 return this ;
	}
	public DAside _aside(final String jif) {
		return _aside().jif(jif) ;
	}
	public DVideo _aside_(final String jif) {
		_aside(jif) ;
		return this ;
	}
	public DAside _asideText(final String textValue) {
		return _aside().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _asideText_(final String textValue) {
		_asideText(textValue) ;
		return this ;
	}
	public DAside _aside(BaseHtmlElement... elements) {
		final DAside answer = _aside() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _aside_(BaseHtmlElement... elements) {
		_aside(elements) ;
		return this ;
	}

	public DAudio _audio() {
		 return _audio(-1) ;
	}
	public DVideo _audio_() {
		_audio() ;
		return this ;
	}
	public DAudio _audio(final int count) {
		 return (DAudio)getOrCreate(DAudio.class, count) ;
	}
	public DVideo _audio_(final int count) {
		_audio(count) ;
		return this ;
	}
	public DAudio _audio(final String jif) {
		return _audio().jif(jif) ;
	}
	public DVideo _audio_(final String jif) {
		_audio(jif) ;
		return this ;
	}

	public DB _b() {
		 return _b(-1) ;
	}
	public DVideo _b_() {
		_b() ;
		return this ;
	}
	public DB _b(final int count) {
		 return (DB)getOrCreate(DB.class, count) ;
	}
	public DVideo _b_(final int count) {
		_b(count) ;
		return this ;
	}
	public DB _b(final String jif) {
		return _b().jif(jif) ;
	}
	public DVideo _b_(final String jif) {
		_b(jif) ;
		return this ;
	}
	public DB _bText(final String textValue) {
		return _b().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _bText_(final String textValue) {
		_bText(textValue) ;
		return this ;
	}
	public DB _b(BaseHtmlElement... elements) {
		final DB answer = _b() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _b_(BaseHtmlElement... elements) {
		_b(elements) ;
		return this ;
	}

	public DBdo _bdo() {
		 return _bdo(-1) ;
	}
	public DVideo _bdo_() {
		_bdo() ;
		return this ;
	}
	public DBdo _bdo(final int count) {
		 return (DBdo)getOrCreate(DBdo.class, count) ;
	}
	public DVideo _bdo_(final int count) {
		_bdo(count) ;
		return this ;
	}
	public DBdo _bdo(final String jif) {
		return _bdo().jif(jif);
	}
	public DVideo _bdo_(final String jif) {
		_bdo(jif) ;
		return this ;
	}
	public DBdo _bdo(BaseHtmlElement... elements) {
		final DBdo answer = _bdo() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _bdo_(BaseHtmlElement... elements) {
		_bdo(elements) ;
		return this ;
	}

	public DBlockQuote _blockquote() {
		 return _blockquote(-1) ;
	}
	public DVideo _blockquote_() {
		_blockquote() ;
		return this ;
	}
	public DBlockQuote _blockquote(final int count) {
		 return (DBlockQuote)getOrCreate(DBlockQuote.class, count) ;
	}
	public DVideo _blockquote_(final int count) {
		_blockquote(count) ;
		return this ;
	}
	public DBlockQuote _blockquote(final String jif) {
		return _blockquote().jif(jif) ;
	}
	public DVideo _blockquote_(final String jif) {
		_blockquote(jif) ;
		return this ;
	}
	public DBlockQuote _blockquoteText(final String textValue) {
		return _blockquote().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _blockquoteText_(final String textValue) {
		_blockquoteText(textValue) ;
		return this ;
	}
	public DBlockQuote _blockquote(BaseHtmlElement... elements) {
		final DBlockQuote answer = _blockquote() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _blockquote_(BaseHtmlElement... elements) {
		_blockquote(elements) ;
		return this ;
	}

	public DBr _br() {
		 return _br(-1) ;
	}
	public DVideo _br_() {
		_br() ;
		return this ;
	}
	public DBr _br(final int count) {
		 return (DBr)getOrCreate(DBr.class, count) ;
	}
	public DVideo _br_(final int count) {
		 _br(count) ;
		 return this ;
	}
	public DBr _br(final String jif) {
		return _br().jif(jif) ;
	}
	public DVideo _br_(final String jif) {
		_br(jif) ;
		return this ;
	}

	public DButton _button() {
		 return _button(-1) ;
	}
	public DVideo _button_() {
		_button() ;
		return this ;
	}
	public DButton _button(final int count) {
		 return (DButton)getOrCreate(DButton.class, count) ;
	}
	public DVideo _button_(final int count) {
		_button(count) ;
		return this ;
	}
	public DButton _button(final String jif) {
		return _button().jif(jif) ;
	}
	public DVideo _button_(final String jif) {
		_button(jif) ;
		return this ;
	}
	public DButton _buttonText(final String textValue) {
		return _button().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _buttonText_(final String textValue) {
		_buttonText(textValue) ;
		return this ;
	}
	public DButton _button(BaseHtmlElement... elements) {
		final DButton answer = _button() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _button_(BaseHtmlElement... elements) {
		_button(elements) ;
		return this ;
	}

	public DCanvas _canvas() {
		 return _canvas(-1) ;
	}
	public DVideo _canvas_() {
		_canvas() ;
		return this ;
	}
	public DCanvas _canvas(final int count) {
		 return (DCanvas)getOrCreate(DCanvas.class, count) ;
	}
	public DVideo _canvas_(final int count) {
		_canvas(count) ;
		return this ;
	}
	public DCanvas _canvas(final String jif) {
		return _canvas().jif(jif) ;
	}
	public DVideo _canvas_(final String jif) {
		_canvas(jif) ;
		return this ;
	}
	

	public DCite _cite() {
		 return _cite(-1) ;
	}
	public DVideo _cite_() {
		_cite() ;
		return this ;
	}
	public DCite _cite(final int count) {
		 return (DCite)getOrCreate(DCite.class, count) ;
	}
	public DVideo _cite_(final int count) {
		 _cite(count) ;
		 return this ;
	}
	public DCite _cite(final String jif) {
		return _cite().jif(jif) ;
	}
	public DVideo _cite_(final String jif) {
		_cite(jif) ;
		return this;
	}
	public DCite _citeText(final String textValue) {
		return _cite().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _citeText_(final String textValue) {
		_citeText(textValue) ;
		return this ;
	}
	public DCite _cite(BaseHtmlElement... elements) {
		final DCite answer = _cite() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _cite_(BaseHtmlElement... elements) {
		_button(elements) ;
		return this ;
	}

	public DCode _code() {
		 return _code(-1) ;
	}
	public DVideo _code_() {
		_code() ;
		return this ;
	}
	public DCode _code(final int count) {
		 return (DCode)getOrCreate(DCode.class, count) ;
	}
	public DVideo _code_(final int count) {
		_code(count) ;
		return this ;
	}
	public DCode _code(final String jif) {
		return _code().jif(jif) ;
	}
	public DVideo _code_(final String jif) {
		_code(jif) ;
		return this ;
	}
	public DCode _codeText(final String textValue) {
		return _code().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _codeText_(final String textValue) {
		_codeText(textValue) ;
		return this;
	}
	public DCode _code(BaseHtmlElement... elements) {
		final DCode answer = _code() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _code_(BaseHtmlElement... elements) {
		_code(elements) ;
		return this ;
	}

	public DCommand _command() {
		 return _command(-1) ;
	}
	public DVideo _command_() {
		_command() ;
		return this ;
	}
	public DCommand _command(final int count) {
		 return (DCommand)getOrCreate(DCommand.class, count) ;
	}
	public DVideo _command_(final int count) {
		_command(count) ;
		return this ;
	}
	public DCommand _command(final String jif) {
		return _command().jif(jif) ;
	}
	public DVideo _command_(final String jif) {
		_command(jif) ;
		return this ;
	}

	public DDataList _datalist() {
		 return _datalist(-1) ;
	}
	public DVideo _datalist_() {
		_datalist() ;
		return this ;
	}
	public DDataList _datalist(final int count) {
		 return (DDataList)getOrCreate(DDataList.class, count) ;
	}
	public DVideo _datalist_(final int count) {
		_datalist(count) ;
		return this ;
	}
	public DDataList _datalist(final String jif) {
		return _datalist().jif(jif) ;
	}
	public DVideo _datalist_(final String jif) {
		_datalist(jif) ;
		return this ;
	}
	public DDataList _datalist(BaseHtmlElement... elements) {
		final DDataList answer = _datalist() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _datalist_(BaseHtmlElement... elements) {
		_datalist(elements) ;
		return this ;
	}

	public DDel _del() {
		 return _del(-1) ;
	}
	public DVideo _del_() {
		 _del() ;
		 return this ;
	}
	public DDel _del(final int count) {
		 return (DDel)getOrCreate(DDel.class, count) ;
	}
	public DVideo _del_(final int count) {
		_del(count) ;
		return this ;
	}
	public DDel _del(final String jif) {
		return _del().jif(jif) ;
	}
	public DVideo _del_(final String jif) {
		_del(jif) ;
		return this ;
	}
	public DDel _delText(final String textValue) {
		return _del().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _delText_(final String textValue) {
		_delText(textValue) ;
		return this ;
	}
	
	public DDetails _details() {
		 return _details(-1) ;
	}
	public DVideo _details_() {
		_details() ;
		return this ;
	}
	public DDetails _details(final int count) {
		 return (DDetails)getOrCreate(DDetails.class, count) ;
	}
	public DVideo _details_(final int count) {
		_details(count) ;
		return this ;
	}
	public DDetails _details(final String jif) {
		return _details().jif(jif) ;
	}
	public DVideo _details_(final String jif) {
		_details(jif) ;
		return this ;
	}
	public DDetails _details(BaseHtmlElement... elements) {
		final DDetails answer = _details() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _details_(BaseHtmlElement... elements) {
		_details(elements) ;
		return this ;
	}

	public DDfn _dfn() {
		 return _dfn(-1) ;
	}
	public DVideo _dfn_() {
		_dfn() ;
		return this ;
	}
	public DDfn _dfn(final int count) {
		 return (DDfn)getOrCreate(DDfn.class, count) ;
	}
	public DVideo _dfn_(final int count) {
		_dfn(count) ;
		return this ;
	}
	public DDfn _dfn(final String jif) {
		return _dfn().jif(jif) ;
	}
	public DVideo _dfn_(final String jif) {
		_dfn().jif(jif) ;
		return this ;
	}
	public DDfn _dfnText(final String textValue) {
		return _dfn().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _dfnText_(final String textValue) {
		_dfnText(textValue) ;
		return this ;
	}
	public DDfn _dfn(BaseHtmlElement... elements) {
		final DDfn answer = _dfn() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _dfn_(BaseHtmlElement... elements) {
		_dfn(elements) ;
		return this ;
	}

	public DDiv _div() {
		 return _div(-1) ;
	}
	public DVideo _div_() {
		_div() ;
		return this ;
	}
	public DDiv _div(final int count) {
		 return (DDiv)getOrCreate(DDiv.class, count) ;
	}
	public DVideo _div_(final int count) {
		_div(count) ;
		return this ;
	}
	public DDiv _div(final String jif) {
		return _div().jif(jif) ;
	}
	public DVideo _div_(final String jif) {
		_div(jif) ;
		return this ;
	}
	public DDiv _div(BaseHtmlElement... elements) {
		final DDiv answer = _div() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _div_(BaseHtmlElement... elements) {
		_div(elements) ;
		return this ;
	}

	public DDl _dl() {
		 return _dl(-1) ;
	}
	public DVideo _dl_() {
		_dl() ;
		return this ;
	}
	public DDl _dl(final int count) {
		 return (DDl)getOrCreate(DDl.class, count) ;
	}
	public DVideo _dl_(final int count) {
		_dl(count) ;
		return this ;
	}
	public DDl _dl(final String jif) {
		return _dl().jif(jif) ;
	}
	public DVideo _dl_(final String jif) {
		_dl().jif(jif) ;
		return this ;
	}
	public DDl _dl(BaseHtmlElement... elements) {
		final DDl answer = _dl() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _dl_(BaseHtmlElement... elements) {
		_dl(elements) ;
		return this ;
	}

	public DEm _em() {
		 return _em(-1) ;
	}
	public DVideo _em_() {
		_em() ;
		return this ;
	}
	public DEm _em(final int count) {
		 return (DEm)getOrCreate(DEm.class, count) ;
	}
	public DVideo _em_(final int count) {
		_em(count) ;
		return this ;
	}
	public DEm _em(final String jif) {
		return _em().jif(jif) ;
	}
	public DVideo _em_(final String jif) {
		_em(jif) ;
		return this ;
	}
	public DEm _emText(final String textValue) {
		return _em().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _emText_(final String textValue) {
		_emText(textValue) ;
		return this ;
	}
	public DEm _em(BaseHtmlElement... elements) {
		final DEm answer = _em() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _em_(BaseHtmlElement... elements) {
		_em(elements) ;
		return this ;
	}

	public DEmbed _embed() {
		 return _embed(-1) ;
	}
	public DVideo _embed_() {
		_embed() ;
		return this ;
	}
	public DEmbed _embed(final int count) {
		 return (DEmbed)getOrCreate(DEmbed.class, count) ;
	}
	public DVideo _embed_(final int count) {
		_embed(count) ;
		return this ;
	}
	public DEmbed _embed(final String jif) {
		return _embed().jif(jif) ;
	}
	public DVideo _embed_(final String jif) {
		_embed(jif) ;
		return this ;
	}

	public DFieldSet _fieldset() {
		 return _fieldset(-1) ;
	}
	public DVideo _fieldset_() {
		_fieldset() ;
		return this ;
	}
	public DFieldSet _fieldset(final int count) {
		 return (DFieldSet)getOrCreate(DFieldSet.class, count) ;
	}
	public DVideo _fieldset_(final int count) {
		_fieldset(count) ;
		return this ;
	}
	public DFieldSet _fieldset(final String jif) {
		return _fieldset().jif(jif) ;
	}
	public DVideo _fieldset_(final String jif) {
		_fieldset(jif) ;
		return this ;
	}
	public DFieldSet _fieldsetLegend(final String legend) {
		return _fieldset().add(new DLegend(legend)) ;
	}
	public DVideo _fieldsetLegend_(final String legend) {
		_fieldsetLegend(legend) ;
		return this ;
	}
	public DFieldSet _fieldset(BaseHtmlElement... elements) {
		final DFieldSet answer = _fieldset() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _fieldset_(BaseHtmlElement... elements) {
		_fieldset(elements) ;
		return this ;
	}

	public DFigure _figure() {
		 return _figure(-1) ;
	}
	public DVideo _figure_() {
		_figure() ;
		return this ;
	}
	public DFigure _figure(final int count) {
		 return (DFigure)getOrCreate(DFigure.class, count) ;
	}
	public DVideo _figure_(final int count) {
		_figure(count) ;
		return this ;
	}
	public DFigure _figure(final String jif) {
		return _figure().jif(jif) ;
	}
	public DVideo _figure_(final String jif) {
		_figure(jif) ;
		return this ;
	}
	public DFigure _figure(BaseHtmlElement... elements) {
		final DFigure answer = _figure() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _figure_(BaseHtmlElement... elements) {
		_figure(elements) ;
		return this ;
	}
		
	public DFooter _footer() {
		 return _footer(-1) ;
	}
	public DVideo _footer_() {
		_footer() ;
		return this ;
	}
	public DFooter _footer(final int count) {
		 return (DFooter)getOrCreate(DFooter.class, count) ;
	}
	public DVideo _footer_(final int count) {
		_footer(count) ;
		return this ;
	}
	public DFooter _footer(final String jif) {
		return _footer().jif(jif) ;
	}
	public DVideo _footer_(final String jif) {
		_footer(jif) ;
		return this ;
	}
	public DFooter _footerText(final String textValue) {
		return _footer().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _footerText_(final String textValue) {
		_footerText(textValue) ;
		return this ;
	}
	public DFooter _footer(BaseHtmlElement... elements) {
		final DFooter answer = _footer() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _footer_(BaseHtmlElement... elements) {
		_footer(elements) ;
		return this ;
	}

	public DForm _form() {
		 return _form(-1) ;
	}
	public DVideo _form_() {
		_form() ;
		return this ;
	}
	public DForm _form(final int count) {
		 return (DForm)getOrCreate(DForm.class, count) ;
	}
	public DVideo _form_(final int count) {
		_form(count) ;
		return this ;
	}
	public DForm _form(final String jif) {
		return _form().jif(jif) ;
	}
	public DVideo _form_(final String jif) {
		_form(jif) ;
		return this ;
	}
	public DForm _form(BaseHtmlElement... elements) {
		final DForm answer = _form() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _form_(BaseHtmlElement... elements) {
		_form(elements) ;
		return this ;
	}

	public DH1 _h1() {
		 return _h1(-1) ;
	}
	public DVideo _h1_() {
		_h1() ;
		return this ;
	}
	public DH1 _h1(final int count) {
		 return (DH1)getOrCreate(DH1.class, count) ;
	}
	public DVideo _h1_(final int count) {
		_h1(count);
		return this ;
	}
	public DH1 _h1(final String jif) {
		return _h1().jif(jif) ;
	}
	public DVideo _h1_(final String jif) {
		_h1(jif) ;
		return this ;
	}
	public DH1 _h1Text(final String textValue) {
		return _h1().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _h1Text_(final String textValue) {
		_h1Text(textValue) ;
		return this ;
	}
	public DH1 _h1(BaseHtmlElement... elements) {
		final DH1 answer = _h1() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _h1_(BaseHtmlElement... elements) {
		_h1(elements) ;
		return this ;
	}

	public DH2 _h2() {
		 return _h2(-1) ;
	}
	public DVideo _h2_() {
		_h2() ;
		return this ;
	}
	public DH2 _h2(final int count) {
		 return (DH2)getOrCreate(DH2.class, count) ;
	}
	public DVideo _h2_(final int count) {
		_h2(count);
		return this ;
	}
	public DH2 _h2(final String jif) {
		return _h2().jif(jif) ;
	}
	public DVideo _h2_(final String jif) {
		_h2(jif) ;
		return this ;
	}
	public DH2 _h2Text(final String textValue) {
		return _h2().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _h2Text_(final String textValue) {
		_h2Text(textValue) ;
		return this ;
	}
	public DH2 _h2(BaseHtmlElement... elements) {
		final DH2 answer = _h2() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _h2_(BaseHtmlElement... elements) {
		_h2(elements) ;
		return this ;
	}

	public DH3 _h3() {
		 return _h3(-1) ;
	}
	public DVideo _h3_() {
		_h3() ;
		return this ;
	}
	public DH3 _h3(final int count) {
		 return (DH3)getOrCreate(DH3.class, count) ;
	}
	public DVideo _h3_(final int count) {
		_h3(count);
		return this ;
	}
	public DH3 _h3(final String jif) {
		return _h3().jif(jif) ;
	}
	public DVideo _h3_(final String jif) {
		_h3(jif) ;
		return this ;
	}
	public DH3 _h3Text(final String textValue) {
		return _h3().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _h3Text_(final String textValue) {
		_h3Text(textValue) ;
		return this ;
	}
	public DH3 _h3(BaseHtmlElement... elements) {
		final DH3 answer = _h3() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _h3_(BaseHtmlElement... elements) {
		_button(elements) ;
		return this ;
	}
	
	public DH4 _h4() {
		 return _h4(-1) ;
	}
	public DVideo _h4_() {
		_h4() ;
		return this ;
	}
	public DH4 _h4(final int count) {
		 return (DH4)getOrCreate(DH4.class, count) ;
	}
	public DVideo _h4_(final int count) {
		_h4(count);
		return this ;
	}
	public DH4 _h4(final String jif) {
		return _h4().jif(jif) ;
	}
	public DVideo _h4_(final String jif) {
		_h4(jif) ;
		return this ;
	}
	public DH4 _h4Text(final String textValue) {
		return _h4().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _h4Text_(final String textValue) {
		_h4Text(textValue) ;
		return this ;
	}
	public DH4 _h4(BaseHtmlElement... elements) {
		final DH4 answer = _h4() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _h4_(BaseHtmlElement... elements) {
		_h4(elements) ;
		return this ;
	}
	
	public DH5 _h5() {
		 return _h5(-1) ;
	}
	public DVideo _h5_() {
		_h5() ;
		return this ;
	}
	public DH5 _h5(final int count) {
		 return (DH5)getOrCreate(DH5.class, count) ;
	}
	public DVideo _h5_(final int count) {
		_h5(count);
		return this ;
	}
	public DH5 _h5(final String jif) {
		return _h5().jif(jif) ;
	}
	public DVideo _h5_(final String jif) {
		_h5(jif) ;
		return this ;
	}
	public DH5 _h5Text(final String textValue) {
		return _h5().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _h5Text_(final String textValue) {
		_h5Text(textValue) ;
		return this ;
	}
	public DH5 _h5(BaseHtmlElement... elements) {
		final DH5 answer = _h5() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _h5_(BaseHtmlElement... elements) {
		_h5(elements) ;
		return this ;
	}
	
	public DH6 _h6() {
		 return _h6(-1) ;
	}
	public DVideo _h6_() {
		_h6() ;
		return this ;
	}
	public DH6 _h6(final int count) {
		 return (DH6)getOrCreate(DH6.class, count) ;
	}
	public DVideo _h6_(final int count) {
		_h6(count);
		return this ;
	}
	public DH6 _h6(final String jif) {
		return _h6().jif(jif) ;
	}
	public DVideo _h6_(final String jif) {
		_h6(jif) ;
		return this ;
	}
	public DH6 _h6Text(final String textValue) {
		return _h6().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _h6Text_(final String textValue) {
		_h6Text(textValue) ;
		return this ;
	}
	public DH6 _h6(BaseHtmlElement... elements) {
		final DH6 answer = _h6() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _h6_(BaseHtmlElement... elements) {
		_h6(elements) ;
		return this ;
	}
	
	public DHeader _header() {
		 return _header(-1) ;
	}
	public DVideo _header_() {
		_header() ;
		return this ;
	}
	public DHeader _header(final int count) {
		 return (DHeader)getOrCreate(DHeader.class, count) ;
	}
	public DVideo _header_(final int count) {
		_header(count) ;
		return this ;
	}
	public DHeader _header(final String jif) {
		return _header().jif(jif) ;
	}
	public DVideo _header_(final String jif) {
		_header(jif) ;
		return this ;
	}
	public DHeader _header(BaseHtmlElement... elements) {
		final DHeader answer = _header() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _header_(BaseHtmlElement... elements) {
		_header(elements) ;
		return this ;
	}

	public DHGroup _hgroup() {
		 return _hgroup(-1) ;
	}
	public DVideo _hgroup_() {
		_hgroup() ;
		return this ;
	}
	public DHGroup _hgroup(final int count) {
		 return (DHGroup)getOrCreate(DHGroup.class, count) ;
	}
	public DVideo _hgroup_(final int count) {
		_hgroup(count) ;
		return this ;
	}
	public DHGroup _hgroup(final String jif) {
		return _hgroup().jif(jif) ;
	}
	public DVideo _hgroup_(final String jif) {
		_hgroup(jif) ;
		return this ;
	}
	public DHGroup _hgroup(BaseHtmlElement... elements) {
		final DHGroup answer = _hgroup() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _hgroup_(BaseHtmlElement... elements) {
		_hgroup(elements) ;
		return this ;
	}

	public DHr _hr() {
		 return _hr(-1) ;
	}
	public DVideo _hr_() {
		_hr() ;
		return this ;
	}
	public DHr _hr(final int count) {
		 return (DHr)getOrCreate(DHr.class, count) ;
	}
	public DVideo _hr_(final int count) {
		_hr(count) ;
		return this ;
	}
	public DHr _hr(final String jif) {
		return _hr().jif(jif) ;
	}
	public DVideo _hr_(final String jif) {
		_hr(jif) ;
		return this ;
	}

	public DI _i() {
		 return _i(-1) ;
	}
	public DVideo _i_() {
		_i() ;
		return this ;
	}
	public DI _i(final int count) {
		 return (DI)getOrCreate(DI.class, count) ;
	}
	public DVideo _i_(final int count) {
		_i(count) ;
		return this ;
	}
	public DI _i(final String jif) {
		return _i().jif(jif) ;
	}
	public DVideo _i_(final String jif) {
		_i(jif) ;
		return this ;
	}
	public DI _iText(final String textValue) {
		return _i().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _iText_(final String textValue) {
		_iText(textValue) ;
		return this ;
	}
	public DI _i(BaseHtmlElement... elements) {
		final DI answer = _i() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _i_(BaseHtmlElement... elements) {
		_i(elements) ;
		return this ;
	}

	public DIFrame _iframe() {
		 return _iframe(-1) ;
	}
	public DVideo _iframe_() {
		_iframe() ;
		return this ;
	}
	public DIFrame _iframe(final int count) {
		 return (DIFrame)getOrCreate(DIFrame.class, count) ;
	}
	public DVideo _iframe_(final int count) {
		_iframe(count) ;
		return this ;
	}
	public DIFrame _iframe(final String jif) {
		return _iframe().jif(jif) ;
	}
	public DVideo _iframe_(final String jif) {
		_iframe(jif) ;
		return this ;
	}

	public DImg _img() {
		 return _img(-1) ;
	}
	public DVideo _img_() {
		_img() ;
		return this ;
	}
	public DImg _img(final int count) {
		 return (DImg)getOrCreate(DImg.class, count) ;
	}
	public DVideo _img_(final int count) {
		_img(count) ;
		return this ;
	}
	public DImg _img(final String jif) {
		return _img().jif(jif) ;
	}
	public DVideo _img_(final String jif) {
		_img(jif) ;
		return this ;
	}
	public DImg _img(final String srcUrl, final String alt) {
		return _img().setHtmlSrc(srcUrl).setHtmlAlt(alt) ;
	}
	public DVideo _img_(final String srcUrl, final String alt) {
		_img(srcUrl, alt) ;
		return this ;
	}

	public DInput _input() {
		 return _input(-1) ;
	}
	public DVideo _input_() {
		_input() ;
		return this ;
	}
	public DInput _input(final int count) {
		 return (DInput)getOrCreate(DInput.class, count) ;
	}
	public DVideo _input_(final int count) {
		_input(count) ;
		return this ;
	}
	public DInput _input(final String jif) {
		return _input().jif(jif) ;
	}
	public DVideo _input_(final String jif) {
		_input(jif) ;
		return this ;
	}
	public DInput _inputValue(final String value) {
		return _input().setHtmlValue(value) ;
	}
	public DVideo _inputValue_(final String value) {
		_inputValue(value) ;
		return this ;
	}

	public DIns _ins() {
		 return _ins(-1) ;
	}
	public DVideo _ins_() {
		_ins() ;
		return this ;
	}
	public DIns _ins(final int count) {
		 return (DIns)getOrCreate(DIns.class, count) ;
	}
	public DVideo _ins_(final int count) {
		_ins(count) ;
		return this ;
	}
	public DIns _ins(final String jif) {
		return _ins().jif(jif) ;
	}
	public DVideo _ins_(final String jif) {
		_ins(jif) ;
		return this ;
	}
	public DIns _insText(final String textValue) {
		return _ins().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _insText_(final String textValue) {
		_insText(textValue) ;
		return this ;
	}

	public DKbd _kbd() {
		 return _kbd(-1) ;
	}
	public DVideo _kbd_() {
		_kbd() ;
		return this ;
	}
	public DKbd _kbd(final int count) {
		 return (DKbd)getOrCreate(DKbd.class, count) ;
	}
	public DVideo _kbd_(final int count) {
		_kbd(count) ;
		return this ;
	}
	public DKbd _kbd(final String jif) {
		return _kbd().jif(jif) ;
	}
	public DVideo _kbd_(final String jif) {
		_kbd(jif) ;
		return this ;
	}
	public DKbd _kbdText(final String textValue) {
		return _kbd().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _kbdText_(final String textValue) {
		_kbdText(textValue) ;
		return this ;
	}
	public DKbd _kbd(BaseHtmlElement... elements) {
		final DKbd answer = _kbd() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _kbd_(BaseHtmlElement... elements) {
		_kbd(elements) ;
		return this ;
	}

	public DKeyGen _keygen() {
		 return _keygen(-1) ;
	}
	public DVideo _keygen_() {
		_keygen() ;
		return this ;
	}
	public DKeyGen _keygen(final int count) {
		 return (DKeyGen)getOrCreate(DKeyGen.class, count) ;
	}
	public DVideo _keygen_(final int count) {
		_keygen(count) ;
		return this ;
	}
	public DKeyGen _keygen(final String jif) {
		return _keygen().jif(jif) ;
	}
	public DVideo _keygen_(final String jif) {
		_keygen(jif) ;
		return this ;
	}

	public DLabel _label() {
		 return _label(-1) ;
	}
	public DVideo _label_() {
		_label() ;
		return this ;
	}
	public DLabel _label(final int count) {
		 return (DLabel)getOrCreate(DLabel.class, count) ;
	}
	public DVideo _label_(final int count) {
		_label(count) ;
		return this ;
	}
	public DLabel _label(final String jif) {
		return _label().jif(jif) ;
	}
	public DVideo _label_(final String jif) {
		_label(jif) ;
		return this ;
	}
	public DLabel _labelText(final String textValue) {
		return _label().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _labelText_(final String textValue) {
		_labelText(textValue) ;
		return this;
	}
	public DLabel _label(BaseHtmlElement... elements) {
		final DLabel answer = _label() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _label_(BaseHtmlElement... elements) {
		_label(elements) ;
		return this ;
	}

	public DMap _map() {
		 return _map(-1) ;
	}
	public DVideo _map_() {
		_map() ;
		return this ;
	}
	public DMap _map(final int count) {
		 return (DMap)getOrCreate(DMap.class, count) ;
	}
	public DVideo _map_(final int count) {
		_map(count) ;
		return this ;
	}
	public DMap _map(final String jif) {
		return _map().jif(jif) ;
	}
	public DVideo _map_(final String jif) {
		_map(jif) ;
		return this ;
	}
	public DMap _map(BaseHtmlElement... elements) {
		final DMap answer = _map() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _map_(BaseHtmlElement... elements) {
		_map(elements) ;
		return this ;
	}

	public DMark _mark() {
		 return _mark(-1) ;
	}
	public DVideo _mark_() {
		_mark() ;
		return this ;
	}
	public DMark _mark(final int count) {
		 return (DMark)getOrCreate(DMark.class, count) ;
	}
	public DVideo _mark_(final int count) {
		_mark(count) ;
		return this ;
	}
	public DMark _mark(final String jif) {
		return _mark().jif(jif) ;
	}
	public DVideo _mark_(final String jif) {
		_mark(jif) ;
		return this ;
	}
	public DMark _mark(BaseHtmlElement... elements) {
		final DMark answer = _mark() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _mark_(BaseHtmlElement... elements) {
		_mark(elements) ;
		return this ;
	}

	public DMenu _menu() {
		 return _menu(-1) ;
	}
	public DVideo _menu_() {
		_menu() ;
		return this ;
	}
	public DMenu _menu(final int count) {
		 return (DMenu)getOrCreate(DMenu.class, count) ;
	}
	public DVideo _menu_(final int count) {
		_menu(count) ;
		return this ;
	}
	public DMenu _menu(final String jif) {
		return _menu().jif(jif) ;
	}
	public DVideo _menu_(final String jif) {
		_menu(jif) ;
		return this ;
	}
	public DMenu _menu(BaseHtmlElement... elements) {
		final DMenu answer = _menu() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _menu_(BaseHtmlElement... elements) {
		_menu(elements) ;
		return this ;
	}

	public DMeter _meter() {
		 return _meter(-1) ;
	}
	public DVideo _meter_() {
		_meter() ;
		return this ;
	}
	public DMeter _meter(final int count) {
		 return (DMeter)getOrCreate(DMeter.class, count) ;
	}
	public DVideo _meter_(final int count) {
		_meter(count) ;
		return this ;
	}
	public DMeter _meter(final String jif) {
		return _meter().jif(jif) ;
	}
	public DVideo _meter_(final String jif) {
		_meter(jif) ;
		return this ;
	}
	public DMeter _meter(final String min, final String max) {
		return _meter().setHtmlMin(min).setHtmlMax(max) ;
	}
	public DVideo _meter_(final String min, final String max) {
		_meter(min, max) ;
		return this ;
	}
	public DMeter _meter(final String min, final String max, final String 

value) {
		return _meter(min, max).setHtmlValue(value) ;
	}
	public DVideo _meter_(final String min, final String max, final String value) 

{
		_meter(min, max, value) ;
		return this ;
	}
	public DMeter _meter(final double min, final double max) {
		return _meter().setHtmlMin(min).setHtmlMax(max) ;
	}
	public DVideo _meter_(final double min, final double max) {
		_meter(min, max) ;
		return this ;
	}
	public DMeter _meter(final double min, final double max, final double 

value) {
		return _meter(min, max).setHtmlValue(value) ;
	}
	public DVideo _meter_(final double min, final double max, final double value) 

{
		_meter(min, max, value) ;
		return this ;
	}
	public DMeter _meter(BaseHtmlElement... elements) {
		final DMeter answer = _meter() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _meter_(BaseHtmlElement... elements) {
		_meter(elements) ;
		return this ;
	}

	public DNav _nav() {
		 return _nav(-1) ;
	}
	public DVideo _nav_() {
		_nav() ;
		return this ;
	}
	public DNav _nav(final int count) {
		 return (DNav)getOrCreate(DNav.class, count) ;
	}
	public DVideo _nav_(final int count) {
		_nav(count) ;
		return this ;
	}
	public DNav _nav(final String jif) {
		return _nav().jif(jif) ;
	}
	public DVideo _nav_(final String jif) {
		_nav(jif) ;
		return this ;
	}
	public DNav _nav(BaseHtmlElement... elements) {
		final DNav answer = _nav() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _nav_(BaseHtmlElement... elements) {
		_nav(elements) ;
		return this ;
	}

	public DNoScript _noscript() {
		 return _noscript(-1) ;
	}
	public DVideo _noscript_() {
		_noscript() ;
		return this ;
	}
	public DNoScript _noscript(final int count) {
		 return (DNoScript)getOrCreate(DNoScript.class, count) ;
	}
	public DVideo _noscript_(final int count) {
		_noscript(count) ;
		return this ;
	}
	public DNoScript _noscript(final String jif) {
		return _noscript().jif(jif) ;
	}
	public DVideo _noscript_(final String jif) {
		_noscript(jif) ;
		return this ;
	}

	public DObject _object() {
		 return _object(-1) ;
	}
	public DVideo _object_() {
		_object() ;
		return this ;
	}
	public DObject _object(final int count) {
		 return (DObject)getOrCreate(DObject.class, count) ;
	}
	public DVideo _object_(final int count) {
		_object(count) ;
		return this ;
	}
	public DObject _object(final String jif) {
		return _object().jif(jif) ;
	}
	public DVideo _object_(final String jif) {
		_object(jif) ;
		return this ;
	}
	public DObject _object(DParam... elements) {
		final DObject answer = _object() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _object_(DParam... elements) {
		_object(elements) ;
		return this ;
	}

	public DOl _ol() {
		 return _ol(-1) ;
	}
	public DVideo _ol_() {
		_ol() ;
		return this ;
	}
	public DOl _ol(final int count) {
		 return (DOl)getOrCreate(DOl.class, count) ;
	}
	public DVideo _ol_(final int count) {
		_ol(count) ;
		return this ;
	}
	public DOl _ol(final String jif) {
		return _ol().jif(jif) ;
	}
	public DVideo _ol_(final String jif) {
		_ol(jif) ;
		return this ;
	}
	public DOl _ol(final Collection<Object> collection) {
		return _ol(collection, "1" /* arabic - 1, 2, 3, ...*/) ;
	}
	public DVideo _ol_(final Collection<Object> collection) {
		_ol(collection) ;
		return this ;
	}
	public DOl _ol(final Collection<Object> collection, final String htmlType) {
		DOl ol = _ol() ;
		for (Object listElement : collection) {
			final DLi li = new DLi(listElement.toString()) ;
			if (htmlType != null) {
				li.setHtmlType(htmlType);
			}
			ol.add(li) ;
		}	
		return ol ;
	}
	public DVideo _ol_(final Collection<Object> collection, final String 

htmlType) {
		_ol(collection, htmlType) ;
		return this ;
	}
	public DOl _ol(DLi... elements) {
		final DOl answer = _ol() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _ol_(DLi... elements) {
		_ol(elements) ;
		return this ;
	}

	public DOutput _output() {
		 return _output(-1) ;
	}
	public DVideo _output_() {
		_output() ;
		return this ;
	}
	public DOutput _output(final int count) {
		 return (DOutput)getOrCreate(DOutput.class, count) ;
	}
	public DVideo _output_(final int count) {
		_output(count) ;
		return this ;
	}
	public DOutput _output(final String jif) {
		return _output().jif(jif) ;
	}
	public DVideo _output_(final String jif) {
		_output(jif) ;
		return this ;
	}
	public DOutput _output(BaseHtmlElement... elements) {
		final DOutput answer = _output() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _output_(BaseHtmlElement... elements) {
		_output(elements) ;
		return this ;
	}

	public DP _p() {
		 return _p(-1) ;
	}
	public DVideo _p_() {
		_p() ;
		return this ;
	}
	public DP _p(final int count) {
		 return (DP)getOrCreate(DP.class, count) ;
	}
	public DVideo _p_(final int count) {
		_p(count) ;
		return this ;
	}
	public DP _p(final String jif) {
		return _p().jif(jif) ;
	}
	public DVideo _p_(final String jif) {
		_p(jif) ;
		return this ;
	}
	public DP _pText(final String textValue) {
		return _p().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _pText_(final String textValue) {
		_pText(textValue) ;
		return this ;
	}
	public DP _p(BaseHtmlElement... elements) {
		final DP answer = _p() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _p_(BaseHtmlElement... elements) {
		_p(elements) ;
		return this ;
	}

	public DPre _pre() {
		 return _pre(-1) ;
	}
	public DVideo _pre_() {
		_pre() ;
		return this ;
	}
	public DPre _pre(final int count) {
		 return (DPre)getOrCreate(DPre.class, count) ;
	}
	public DVideo _pre_(final int count) {
		_pre(count) ;
		return this ;
	}
	public DPre _pre(final String jif) {
		return _pre().jif(jif) ;
	}
	public DVideo _pre_(final String jif) {
		_pre(jif) ;
		return this ;
	}
	public DPre _preText(final String textValue) {
		return _pre().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _preText_(final String textValue) {
		_preText(textValue) ;
		return this ;
	}
	public DPre _pre(BaseHtmlElement... elements) {
		final DPre answer = _pre() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _pre_(BaseHtmlElement... elements) {
		_pre(elements) ;
		return this ;
	}

	public DProgress _progress() {
		 return _progress(-1) ;
	}
	public DVideo _progress_() {
		_progress() ;
		return this ;
	}
	public DProgress _progress(final int count) {
		 return (DProgress)getOrCreate(DProgress.class, count) ;
	}
	public DVideo _progress_(final int count) {
		_progress(count) ;
		return this ;
	}
	public DProgress _progress(final String jif) {
		return _progress().jif(jif) ;
	}
	public DVideo _progress_(final String jif) {
		_progress(jif) ;
		return this ;
	}
	public DProgress _progress(final double max, final double value) {
		return _progress().setHtmlMax(max).setHtmlValue(value) ;
	}
	public DVideo _progress_(final double max, final double value) {
		_progress(max, value) ;
		return this ;
	}
	public DProgress _progress(final String max, final String value) {
		return _progress().setHtmlMax(max).setHtmlValue(value) ;
	}
	public DVideo _progress_(final String max, final String value) {
		_progress(max, value) ;
		return this ;
	}
	public DProgress _progress(BaseHtmlElement... elements) {
		final DProgress answer = _progress() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _progress_(BaseHtmlElement... elements) {
		_progress(elements) ;
		return this ;
	}

	public DQ _q() {
		 return _q(-1) ;
	}
	public DVideo _q_() {
		_q();
		return this ;
	}
	public DQ _q(final int count) {
		 return (DQ)getOrCreate(DQ.class, count) ;
	}
	public DVideo _q_(final int count) {
		_q(count) ;
		return this ;
	}
	public DQ _q(final String jif) {
		return _q().jif(jif) ;
	}
	public DVideo _q_(final String jif) {
		_q(jif) ;
		return this ;
	}
	public DQ _qText(final String textValue) {
		return _q().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _qText_(final String textValue) {
		_qText(textValue) ;
		return this ;
	}
	public DQ _q(BaseHtmlElement... elements) {
		final DQ answer = _q() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _q_(BaseHtmlElement... elements) {
		_q(elements) ;
		return this ;
	}

	public DRuby _ruby() {
		 return _ruby(-1) ;
	}
	public DVideo _ruby_() {
		_ruby() ;
		return this ;
	}
	public DRuby _ruby(final int count) {
		 return (DRuby)getOrCreate(DRuby.class, count) ;
	}
	public DVideo _ruby_(final int count) {
		_ruby(count) ;
		return this ;
	}
	public DRuby _ruby(final String jif) {
		return _ruby().jif(jif) ;
	}
	public DVideo _ruby_(final String jif) {
		_ruby(jif) ;
		return this ;
	}
	public DRuby _ruby(BaseHtmlElement... elements) {
		final DRuby answer = _ruby() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _ruby_(BaseHtmlElement... elements) {
		_ruby(elements) ;
		return this ;
	}

	public DSamp _samp() {
		 return _samp(-1) ;
	}
	public DVideo _samp_() {
		_samp() ;
		return this ;
	}
	public DSamp _samp(final int count) {
		 return (DSamp)getOrCreate(DSamp.class, count) ;
	}
	public DVideo _samp_(final int count) {
		_samp(count) ;
		return this ;
	}
	public DSamp _samp(final String jif) {
		return _samp().jif(jif) ;
	}
	public DVideo _samp_(final String jif) {
		_samp(jif) ;
		return this ;
	}
	public DSamp _sampText(final String textValue) {
		return _samp().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _sampText_(final String textValue) {
		_sampText(textValue) ;
		return this ;
	}
	public DSamp _samp(BaseHtmlElement... elements) {
		final DSamp answer = _samp() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _samp_(BaseHtmlElement... elements) {
		_samp(elements) ;
		return this ;
	}

	public DScript _script() {
		 return _script(-1) ;
	}
	public DVideo _script_() {
		_script();
		return this ;
	}
	public DScript _script(final int count) {
		 return (DScript)getOrCreate(DScript.class, count) ;
	}
	public DVideo _script_(final int count) {
		_script(count) ;
		return this ;
	}
	public DScript _script(final String jif) {
		return _script().jif(jif) ;
	}
	public DVideo _script_(final String jif) {
		_script(jif) ;
		return this ;
	}
	public DScript _scriptText(final String text) {
		return _script().setHtmlText(text) ;
	}
	public DVideo _scriptText_(final String text) {
		_scriptText(text) ;
		return this ;
	}
	public DScript _scriptText(final String text, final String type) {
		return _scriptText(text).setHtmlType(type) ;
	}
	public DVideo _scriptText_(final String text, final String type) {
		_scriptText(text, type) ;
		return this ;
	}

	public DSection _section() {
		 return _section(-1) ;
	}
	public DVideo _section_() {
		_section() ;
		return this ;
	}
	public DSection _section(final int count) {
		 return (DSection)getOrCreate(DSection.class, count) ;
	}
	public DVideo _section_(final int count) {
		_section(count) ;
		return this ;
	}
	public DSection _section(final String jif) {
		return _section().jif(jif) ;
	}
	public DVideo _section_(final String jif) {
		_section(jif) ;
		return this ;
	}
	public DSection _section(BaseHtmlElement... elements) {
		final DSection answer = _section() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _section_(BaseHtmlElement... elements) {
		_section(elements) ;
		return this ;
	}
	

	public DSelect _select() {
		 return _select(-1) ;
	}
	public DVideo _select_() {
		_select();
		return this ;
	}
	public DSelect _select(final int count) {
		 return (DSelect)getOrCreate(DSelect.class, count) ;
	}
	public DVideo _select_(final int count) {
		_select(count);
		return this ;
	}
	public DSelect _select(final String jif) {
		return _select().jif(jif) ;
	}
	public DVideo _select_(final String jif) {
		_select(jif) ;
		return this ;
	}
	public DSelect _select(BaseHtmlElement... elements) {
		final DSelect answer = _select() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _select_(BaseHtmlElement... elements) {
		_select(elements) ;
		return this ;
	}

	public DSmall _small() {
		 return _small(-1) ;
	}
	public DVideo _small_() {
		_small() ;
		return this ;
	}
	public DSmall _small(final int count) {
		 return (DSmall)getOrCreate(DSmall.class, count) ;
	}
	public DVideo _small_(final int count) {
		_small(count) ;
		return this ;
	}
	public DSmall _small(final String jif) {
		return _small().jif(jif) ;
	}
	public DVideo _small_(final String jif) {
		_small(jif) ;
		return this ;
	}
	public DSmall _smallText(final String textValue) {
		return _small().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _smallText_(final String textValue) {
		_smallText(textValue) ;
		return this ;
	}
	public DSmall _small(BaseHtmlElement... elements) {
		final DSmall answer = _small() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _small_(BaseHtmlElement... elements) {
		_small(elements) ;
		return this ;
	}

	public DSpan _span() {
		 return _span(-1) ;
	}
	public DVideo _span_() {
		_span() ;
		return this ;
	}
	public DSpan _span(final int count) {
		 return (DSpan)getOrCreate(DSpan.class, count) ;
	}
	public DVideo _span_(final int count) {
		_span(count) ;
		return this ;
	}
	public DSpan _span(final String jif) {
		return _span().jif(jif) ;
	}
	public DVideo _span_(final String jif) {
		_span(jif) ;
		return this ;
	}
	public DSpan _span(BaseHtmlElement... elements) {
		final DSpan answer = _span() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _span_(BaseHtmlElement... elements) {
		_span(elements) ;
		return this ;
	}

	public DStrong _strong() {
		 return _strong(-1) ;
	}
	public DVideo _strong_() {
		_strong() ;
		return this ;
	}
	public DStrong _strong(final int count) {
		 return (DStrong)getOrCreate(DStrong.class, count) ;
	}
	public DVideo _strong_(final int count) {
		_strong(count) ;
		return this ;
	}
	public DStrong _strong(final String jif) {
		return _strong().jif(jif);
	}
	public DVideo _strong_(final String jif) {
		_strong(jif) ;
		return this ;
	}
	public DStrong _strongText(final String textValue) {
		return _strong().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _strongText_(final String textValue) {
		_strongText(textValue) ;
		return this ;
	}
	public DStrong _strong(BaseHtmlElement... elements) {
		final DStrong answer = _strong() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _strong_(BaseHtmlElement... elements) {
		_strong(elements) ;
		return this ;
	}

	public DStyle _style() {
		 return _style(-1) ;
	}
	public DVideo _style_() {
		_style() ;
		return this ;
	}
	public DStyle _style(final int count) {
		 return (DStyle)getOrCreate(DStyle.class, count) ;
	}
	public DVideo _style_(final int count) {
		_style(count) ;
		return this ;
	}
	public DStyle _style(final String jif) {
		return _style().jif(jif) ;
	}
	public DVideo _style_(final String jif) {
		_style(jif) ;
		return this ;
	}
	public DStyle _styleText(final String styleText) {
		return _style().add(styleText) ;
	}
	public DVideo _styleText_(final String styleText) {
		_styleText(styleText) ;
		return this ;
	}

	public DSub _sub() {
		 return _sub(-1) ;
	}
	public DVideo _sub_() {
		_sub();
		return this ;
	}
	public DSub _sub(final int count) {
		 return (DSub)getOrCreate(DSub.class, count) ;
	}
	public DVideo _sub_(final int count) {
		_sub(count) ;
		return this ;
	}
	public DSub _sub(final String jif) {
		return _sub().jif(jif) ;
	}
	public DVideo _sub_(final String jif) {
		_sub(jif) ;
		return this ;
	}
	public DSub _subText(final String textValue) {
		return _sub().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _subText_(final String textValue) {
		_subText(textValue) ;
		return this ;
	}
	public DSub _sub(BaseHtmlElement... elements) {
		final DSub answer = _sub() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _sub_(BaseHtmlElement... elements) {
		_sub(elements) ;
		return this ;
	}

	public DSup _sup() {
		 return _sup(-1) ;
	}
	public DVideo _sup_() {
		_sup(); 
		return this ;
	}
	public DSup _sup(final int count) {
		 return (DSup)getOrCreate(DSup.class, count) ;
	}
	public DVideo _sup_(final int count) {
		_sup(count) ;
		return this ;
	}
	public DSup _sup(final String jif) {
		return _sup().jif(jif) ;
	}
	public DVideo _sup_(final String jif) {
		_sup(jif) ;
		return this ;
	}
	public DSup _supText(final String textValue) {
		return _sup().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _supText_(final String textValue) {
		_supText(textValue) ;
		return this ;
	}
	public DSup _sup(BaseHtmlElement... elements) {
		final DSup answer = _sup() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _sup_(BaseHtmlElement... elements) {
		_sup(elements) ;
		return this ;
	}
	
	public DTable _table() {
		 return _table(-1) ;
	}
	public DVideo _table_() {
		_table() ;
		return this ;
	}
	public DTable _table(final int count) {
		 return (DTable)getOrCreate(DTable.class, count) ;
	}
	public DVideo _table_(final int count) {
		_table(count);
		return this ;
	}
	public DTable _table(final String jif) {
		return _table().jif(jif) ;
	}
	public DVideo _table_(final String jif) {
		_table(jif) ;
		return this ;
	}
	public DTable _table(DTr... rows) {
		final DTable table = _table() ;
		for(DTr row: rows) table.add(row) ;
		return table ;
	}
	public DVideo _table_(DTr... rows) {
		_table(rows) ;
		return this ;
	}
	public DTable _table(BaseHtmlElement... elements) {
		final DTable answer = _table() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _table_(BaseHtmlElement... elements) {
		_table(elements) ;
		return this ;
	}

	public DTextArea _textarea() {
		 return _textarea(-1) ;
	}
	public DVideo _textarea_() {
		_textarea() ;
		return this ;
	}
	public DTextArea _textarea(final int count) {
		 return (DTextArea)getOrCreate(DTextArea.class, count) ;
	}
	public DVideo _textarea_(final int count) {
		_textarea(count);
		return this ;
	}
	public DTextArea _textarea(final String jif) {
		return _textarea().jif(jif) ;
	}
	public DVideo _textarea_(final String jif) {
		_textarea(jif) ;
		return this ;
	}

	public DTime _time() {
		 return _time(-1) ;
	}
	public DVideo _time_() {
		_time();
		return this;
	}
	public DTime _time(final int count) {
		 return (DTime)getOrCreate(DTime.class, count) ;
	}
	public DVideo _time_(final int count) {
		_time(count) ;
		return this ;
	}
	public DTime _time(final String jif) {
		return _time().jif(jif) ;
	}
	public DVideo _time_(final String jif) {
		_time(jif) ;
		return this ;
	}
	public DTime _timeDateTime(final String dateTime) {
		return _time().setHtmlDateTime(dateTime) ;
	}
	public DVideo _timeDateTime_(final String dateTime) {
		_timeDateTime(dateTime);
		return this ;
	}
	public DTime _time(BaseHtmlElement... elements) {
		final DTime answer = _time() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _time_(BaseHtmlElement... elements) {
		_time(elements) ;
		return this ;
	}

	public DUl _ul() {
		 return _ul(-1) ;
	}
	public DVideo _ul_() {
		_ul() ;
		return this ;
	}
	public DUl _ul(final int count) {
		 return (DUl)getOrCreate(DUl.class, count) ;
	}
	public DVideo _ul_(final int count) {
		_ul(count) ;
		return this ;
	}
	public DUl _ul(final Collection<Object> collection) {
		return _ul(collection, "circle") ;
	}
	public DVideo _ul_(final Collection<Object> collection) {
		_ul(collection) ;
		return this ;
	}
	public DUl _ul(final Collection<Object> collection, final String htmlType) 

{
		DUl ul = _ul() ;
		for (Object listElement : collection) {
			final DLi li = new DLi(listElement.toString()) ;
			if (htmlType != null) {
				li.setHtmlType(htmlType);
			}
			ul.add(li) ;
		}	
		return ul ;
	}
	public DVideo _ul_(final Collection<Object> collection, final String htmlType) {
		_ul(collection, htmlType) ;
		return this ;
	}
	public DUl _ul(DLi... elements) {
		final DUl answer = _ul() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _ul_(DLi... elements) {
		_ul(elements) ;
		return this ;
	}

	public DVar _var() {
		 return _var(-1) ;
	}
	public DVideo _var_() {
		_var() ;
		return this ;
	}
	public DVar _var(final int count) {
		 return (DVar)getOrCreate(DVar.class, count) ;
	}
	public DVideo _var_(final int count) {
		_var(count) ;
		return this ;
	}
	public DVar _var(final String jif) {
		return _var().jif(jif) ;
	}
	public DVideo _var_(final String jif) {
		_var(jif) ;
		return this ;
	}
	public DVar _varText(final String textValue) {
		return _var().setHtmlExtTextValue(textValue) ;
	}
	public DVideo _varText_(final String textValue) {
		_varText(textValue) ;
		return this ;
	}
	public DVar _var(BaseHtmlElement... elements) {
		final DVar answer = _var() ;
		answer.add(elements) ;
		return answer ;
	}
	public DVideo _var_(BaseHtmlElement... elements) {
		_var(elements) ;
		return this ;
	}

	public DVideo _video() {
		 return _video(-1) ;
	}
	public DVideo _video_() {
		_video();
		return this ;
	}
	public DVideo _video(final int count) {
		 return (DVideo)getOrCreate(DVideo.class, count) ;
	}
	public DVideo _video_(final int count) {
		_video(count) ;
		return this ;
	}
	public DVideo _video(final String jif) {
		return _video().jif(jif) ;
	}
	public DVideo _video_(final String jif) {
		_video(jif) ;
		return this ;
	}

	public DWbr _wbr() {
		 return _wbr(-1) ;
	}
	public DVideo _wbr_() {
		_wbr() ;
		return this ;
	}
	public DWbr _wbr(final int count) {
		 return (DWbr)getOrCreate(DWbr.class, count) ;
	}
	public DVideo _wbr_(final int count) {
		_wbr(count);
		return this ;
	}
	public DWbr _wbr(final String jif) {
		return _wbr().jif(jif) ;
	}
	public DVideo _wbr_(final String jif) {
		_wbr(jif) ;
		return this ;
	}
	
	//
	// Extended child hooks
	//
	public DSource _source() {
		 return _source(-1) ;
	}
	public DVideo _source_() {
		_source() ;
		return this ;
	}
	public DSource _source(final int count) {
		 return (DSource)getOrCreate(DSource.class, count) ;
	}
	public DVideo _source_(final int count) {
		_source(count);
		return this ;
	}
	public DSource _source(final String jif) {
		return _source().jif(jif) ;
	}
	public DVideo _source_(final String jif) {
		_source(jif) ;
		return this ;
	}	
}

