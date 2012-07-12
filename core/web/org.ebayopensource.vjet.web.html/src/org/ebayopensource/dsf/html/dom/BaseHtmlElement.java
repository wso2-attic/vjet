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
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.common.DsfVerifierConfig;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.statistics.DarwinStatisticsCtxHelper;
import org.ebayopensource.dsf.css.CssClassConstant;
import org.ebayopensource.dsf.css.CssIdConstant;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.css.dom.impl.DCssStyleDeclaration;
import org.ebayopensource.dsf.dom.Associator;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.support.Jif;
import org.ebayopensource.dsf.html.ctx.HtmlCtx;
import org.ebayopensource.dsf.html.events.EventHandlerAttacher;
import org.ebayopensource.dsf.html.events.EventHandlerContainer;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.common.Z;

/**
* Base class for most of the elements.
* <code>http://www.w3.org/TR/REC-html40/index/elements.html</code>
* 
*/
public abstract class BaseHtmlElement extends DElement {

	private static final long serialVersionUID = 3833188025499792690L;
	
	/** "ltr" */
	public static final String DIR_LTR = "ltr" ;
	/** "rtl" */
	public static final String DIR_RTL = "rtl" ;
	
	//
	// Constructor(s)
	//
	protected BaseHtmlElement(final HtmlTypeEnum type){
		this(null, type);
	}
	
	protected BaseHtmlElement(final DHtmlDocument doc, final HtmlTypeEnum type) {
		super(doc, type.getName());
		if (type != htmlType()){
			throw new DsfRuntimeException(
				"type is not consistent with htmlType() mehtod.");
		}
	}
	
	public NodeList getElementsByTagName(final HtmlTypeEnum htmlType) {
		return getElementsByTagName(htmlType.getName());
	}
	
	@Override
	public NodeList getElementsByTagName(final String tagName) {
		return super.getElementsByTagName(tagName.toLowerCase()) ;
	}
	
	//
	// Framework
	//
	abstract public HtmlTypeEnum htmlType();

	//
	// Spec API
	//
	/**
	 * The accesskey attribute's value is used by the user agent as a guide for 
	 * creating a keyboard shortcut that activates or focuses the element.
	 */
	public String getHtmlAccessKey() {
		return getHtmlAttribute(EHtmlAttr.accesskey);
	}
	/**
	 * The accesskey attribute's value is used by the user agent as a guide for 
	 * creating a keyboard shortcut that activates or focuses the element.
	 */
	public BaseHtmlElement setHtmlAccessKey(final String accesskey) {
		setHtmlAttribute(EHtmlAttr.accesskey, accesskey);
		return this ;
	}
	
	/**
	 * returns class or many classes, space delimited
	 */
	public String getHtmlClassName() {
		return getHtmlAttribute(EHtmlAttr._class);
	}	
	/**
	 * set class name, overwrite current class(es)
	 */
	public BaseHtmlElement setHtmlClassName(final String className) {
		setHtmlAttribute(EHtmlAttr._class, className);
		return this ;
	}	
	public BaseHtmlElement setHtmlClassName(final CssClassConstant ccc) {
		DarwinStatisticsCtxHelper.countCssStatistics(ccc.getCssrName());
		return setHtmlClassName(ccc.getName()) ;
	}

	/**
	 * The contenteditable  attribute is an enumerated attribute whose keywords 
	 * are the empty string, true, and false. The empty string and the true keyword 
	 * map to the true state. The false keyword maps to the false state. In 
	 * addition, there is a third state, the inherit state, which is the missing 
	 * value default (and the invalid value default).
	 */
	public String getHtmlContentEditable() {
		return getHtmlAttribute(EHtmlAttr.contentEditable);
	}
	/**
	 * The contenteditable  attribute is an enumerated attribute whose keywords 
	 * are the empty string, true, and false. The empty string and the true keyword 
	 * map to the true state. The false keyword maps to the false state. In 
	 * addition, there is a third state, the inherit state, which is the missing 
	 * value default (and the invalid value default).
	 */
	public BaseHtmlElement setHtmlContentEditable(final String editable) {
		setHtmlAttribute(EHtmlAttr.contentEditable, editable);
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
	public String getHtmlContextMenu() {
		return getHtmlAttribute(EHtmlAttr.contextMenu);
	}
	/**
	 * The contextmenu  attribute gives the element's context menu. The value 
	 * must be the ID of a menu element in the DOM. If the node that would be 
	 * obtained by the invoking the getElementById() method using the attribute's 
	 * value as the only argument is null or not a menu element, then the element 
	 * has no assigned context menu. Otherwise, the element's assigned context 
	 * menu is the element so identified.
	 */
	public BaseHtmlElement setHtmlContextMenu(final String contextMenu) {
		setHtmlAttribute(EHtmlAttr.contextMenu, contextMenu);
		return this ;
	}
	
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	public String getHtmlDir() {
		return getHtmlAttribute(EHtmlAttr.dir);
	}
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	public BaseHtmlElement setHtmlDir(final String dir) {
		setHtmlAttribute(EHtmlAttr.dir, dir);
		return this ;
	}
	
	/**
	 * The draggable attribute is an enumerated attribute. It has three states. 
	 * The first state is true and it has the keyword true. The second state is 
	 * false and it has the keyword false. The third state is auto; it has no 
	 * keywords but it is the missing value default.
	 */
	public String getHtmlDraggable() {
		return getHtmlAttribute(EHtmlAttr.draggable);
	}
	/**
	 * The draggable attribute is an enumerated attribute. It has three states. 
	 * The first state is true and it has the keyword true. The second state is 
	 * false and it has the keyword false. The third state is auto; it has no 
	 * keywords but it is the missing value default.
	 */
	public BaseHtmlElement setHtmlDraggable(final String draggable) {  // true, false, auto
		setHtmlAttribute(EHtmlAttr.draggable, draggable);
		return this ;
	}
	/**
	 * The draggable attribute is an enumerated attribute. It has three states. 
	 * The first state is true and it has the keyword true. The second state is 
	 * false and it has the keyword false. The third state is auto; it has no 
	 * keywords but it is the missing value default.
	 */
	public BaseHtmlElement setHtmlDraggable(final boolean draggable) {  // true, false
		return setHtmlDraggable(Boolean.toString(draggable)) ;
	}
	
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	public String getHtmlHidden() {
		return getHtmlAttribute(EHtmlAttr.hidden);
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	public BaseHtmlElement setHtmlHidden(final String hidden) {
		setHtmlAttribute(EHtmlAttr.hidden, hidden);
		return this ;
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	public BaseHtmlElement setHtmlHidden(final boolean hidden) {
		setHtmlAttribute(EHtmlAttr.hidden, hidden);
		return this ;
	}
	
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	public String getHtmlId() {
		return getHtmlAttribute(EHtmlAttr.id);
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	public BaseHtmlElement setHtmlId(String id) {
		setHtmlAttribute(EHtmlAttr.id, id);
		return this ;
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */	
	public BaseHtmlElement setHtmlId(CssIdConstant id) {
		return setHtmlId(id.getName()) ;
	}
	
	/**
	 * An element with the item attribute specified creates a new item, a group 
	 * of name-value pairs.  The attribute, if specified, must have a value that 
	 * is an unordered set of unique space-separated tokens representing the 
	 * types (if any) of the item.
	 */
	public String getHtmlItem() {
		return getHtmlAttribute(EHtmlAttr.item);
	}
	/**
	 * An element with the item attribute specified creates a new item, a group 
	 * of name-value pairs.  The attribute, if specified, must have a value that 
	 * is an unordered set of unique space-separated tokens representing the 
	 * types (if any) of the item.
	 */
	public BaseHtmlElement setHtmlItem(final String item) {
		setHtmlAttribute(EHtmlAttr.item, item);
		return this ;
	}
	
	/**
	 * An element with the itemprop  attribute specified adds one or more name-value 
	 * pairs to its corresponding item. The itemprop attribute, if specified, must 
	 * have a value that is an unordered set of unique space-separated tokens 
	 * representing the names of the name-value pairs that it adds. The attribute's 
	 * value must have at least one token.
	 */
	public String getHtmlItemProp() {
		return getHtmlAttribute(EHtmlAttr.itemprop);
	}
	/**
	 * An element with the itemprop  attribute specified adds one or more name-value 
	 * pairs to its corresponding item. The itemprop attribute, if specified, must 
	 * have a value that is an unordered set of unique space-separated tokens 
	 * representing the names of the name-value pairs that it adds. The attribute's 
	 * value must have at least one token.
	 */
	public BaseHtmlElement setHtmlItemProp(final String itemprop) {
		setHtmlAttribute(EHtmlAttr.itemprop, itemprop);
		return this ;
	}	
	
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	public String getHtmlLang() {
		return getHtmlAttribute(EHtmlAttr.lang);
	}
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	public BaseHtmlElement setHtmlLang(final String lang) {
		setHtmlAttribute(EHtmlAttr.lang, lang);
		return this ;
	}
	
	/**
	 * The spellcheck  attribute is an enumerated attribute whose keywords are 
	 * the empty string, true and false. The empty string and the true keyword 
	 * map to the true state. The false keyword maps to the false state. In 
	 * addition, there is a third state, the default state, which is the missing 
	 * value default (and the invalid value default).
	 */
	public String getHtmlSpellCheck() {
		return getHtmlAttribute(EHtmlAttr.spellcheck);
	}
	/**
	 * The spellcheck  attribute is an enumerated attribute whose keywords are 
	 * the empty string, true and false. The empty string and the true keyword 
	 * map to the true state. The false keyword maps to the false state. In 
	 * addition, there is a third state, the default state, which is the missing 
	 * value default (and the invalid value default).
	 */
	public BaseHtmlElement setHtmlSpellCheck(final String spellcheck) {
		setHtmlAttribute(EHtmlAttr.spellcheck, spellcheck);
		return this ;
	}
	/**
	 * The spellcheck  attribute is an enumerated attribute whose keywords are 
	 * the empty string, true and false. The empty string and the true keyword 
	 * map to the true state. The false keyword maps to the false state. In 
	 * addition, there is a third state, the default state, which is the missing 
	 * value default (and the invalid value default).
	 */
	public BaseHtmlElement setHtmlSpellCheck(final boolean spellcheck) {
		setHtmlAttribute(EHtmlAttr.spellcheck, spellcheck);
		return this ;
	}	
	
	/** get the inline style.
	 * This is not a cheap operation, it creates several objects.
	 * TODO: revist string/object usage.
	 * @return DCssStyleDeclarationImpl - if not set, null will be returned. 
	 */
	public ICssStyleDeclaration getHtmlStyle() {
		final String styleString = getHtmlAttribute(EHtmlAttr.style);
		if (styleString == null || "".equals(styleString)) {
			return null;
		}
		final ICssStyleDeclaration style = new DCssStyleDeclaration(null);
		style.setCssText("{" + styleString + "}");
		return style;
	}

	/** get the inline style.
	 * This is not a cheap operation, it creates several objects.
	 * TODO: revist string/object usage.
	 * @return String - if not set, null will be returned. 
	 */
	public String getHtmlStyleAsString() {
		return getHtmlAttribute(EHtmlAttr.style);
	}
	/** Set the style.
	 * This will create several objects during the call.
	 * @param style
	 * TODO: revist string/object usage.
	 * @return
	 */
	public BaseHtmlElement setHtmlStyleAsString(final String styleString) {
		if (styleString == null) {
// MrPperf - don't create attributes unless we need them
			if (hasAttributes()) {
				getDsfAttributes().remove(EHtmlAttr.style.getAttributeName());
			}
			return this;
		}
		
		if (DsfVerifierConfig.getInstance().isVerifyNaming()) {
			// TODO: why can't we just parse/generate the stuff between '{' and '}'
			final String bastardizedStyleString = "{" + styleString + "}";
			final ICssStyleDeclaration style = new DCssStyleDeclaration(null);
			// This will actually parse/verify the style string!
			style.setCssText(bastardizedStyleString);
		}
		
		setStyleInternal(styleString);
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 * @param style
	 * TODO: revist string/object usage.
	 * @return
	 */
	public BaseHtmlElement setHtmlStyle(final ICssStyleDeclaration style) {
		if (style == null) {
// MrPperf - don't create attributes unless we need to
			if (hasAttributes()) {
				getDsfAttributes().remove(EHtmlAttr.style.getAttributeName());
			}
			return this;
		}
		final String styleString = style.getCssText();
		// TODO: why can't we just parse/generate the stuff between '{' and '}'
		final String realText = styleString.substring(1, styleString.length()-1);
		setStyleInternal(realText);
		return this;
	}	
	
	/**
	 * The subject  attribute may be specified on any HTML element to associate 
	 * the element with an element with an item attribute. If the subject 
	 * attribute is specified, the attribute's value must be the ID of an element 
	 * with an item attribute, in the same Document as the element with the 
	 * subject attribute.
	 */
	public String getHtmlSubject() {
		return getHtmlAttribute(EHtmlAttr.subject);
	}
	/**
	 * The subject  attribute may be specified on any HTML element to associate 
	 * the element with an element with an item attribute. If the subject 
	 * attribute is specified, the attribute's value must be the ID of an element 
	 * with an item attribute, in the same Document as the element with the 
	 * subject attribute.
	 */
	public BaseHtmlElement setHtmlSubject(final String subject) {
		setHtmlAttribute(EHtmlAttr.subject, subject);
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
	public int getHtmlTabIndex() {
		return this.getHtmlAttributeInteger(EHtmlAttr.tabindex);
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
	public BaseHtmlElement setHtmlTabIndex(final String tabindex) {  // HTML 5.0
		setHtmlAttribute(EHtmlAttr.tabindex, tabindex);
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
	public BaseHtmlElement setHtmlTabIndex(final int tabindex) {  // HTML 5.0
		setHtmlAttribute(EHtmlAttr.tabindex, tabindex);
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
	public String getHtmlTitle() {
		return getHtmlAttribute(EHtmlAttr.title);
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
	public BaseHtmlElement setHtmlTitle(final String title) {
		setHtmlAttribute(EHtmlAttr.title, title);
		return this ;
	}
	
	//
	// HTML 5.0 API - Events
	//
	// onabort
	public String getHtmlOnAbort() {
		return getHtmlAttribute(EHtmlAttr.onabort);		
	}
	public BaseHtmlElement setHtmlOnAbort(final String script) {
		setInlineEvent(EHtmlAttr.onabort,script,EventType.ABORT);
		return this ;
	}
	
	// onblur - not supported on BODY
	public String getHtmlOnBlur() {
		return getHtmlAttribute(EHtmlAttr.onblur);		
	}
	public BaseHtmlElement setHtmlOnBlur(final String onblur) {
		setInlineEvent(EHtmlAttr.onblur,onblur,EventType.BLUR);
		return this ;
	}
	
	// oncanplay
	public String getHtmlOnCanPlay() {
		return getHtmlAttribute(EHtmlAttr.oncanplay);		
	}
	public BaseHtmlElement setHtmlOnCanPlay(final String script) {
		setInlineEvent(EHtmlAttr.oncanplay,script,EventType.CANPLAY);
		return this ;
	}
	
	// oncanplaythrough
	public String getHtmlOnCanPlayThrough() {
		return getHtmlAttribute(EHtmlAttr.oncanplaythrough);		
	}
	public BaseHtmlElement setHtmlOnCanPlayThrough(final String script) {
		setInlineEvent(EHtmlAttr.oncanplaythrough,script,EventType.CANPLAYTHROUGH);
		return this ;
	}
	
	// onchange
	public String getHtmlOnChange() {
		return getHtmlAttribute(EHtmlAttr.onchange);		
	}
	public BaseHtmlElement setHtmlOnChange(final String script) {
		setInlineEvent(EHtmlAttr.onchange,script,EventType.CHANGE);
		return this ;
	}
	
	// onclick
	public String getHtmlOnClick() {
		return getHtmlAttribute(EHtmlAttr.onclick);		
	}
	public BaseHtmlElement setHtmlOnClick(final String script) {
		setInlineEvent(EHtmlAttr.onclick,script,EventType.CLICK);
		return this ;
	}

	// oncontextmenu
	public String getHtmlOnContextMenu() {
		return getHtmlAttribute(EHtmlAttr.oncontextmenu);		
	}
	public BaseHtmlElement setHtmlOnContextMenu(final String script) {
		setInlineEvent(EHtmlAttr.oncontextmenu,script,EventType.CONTEXTMENU);
		return this ;
	}
	
	// ondblclick
	public String getHtmlOnDblClick() {
		return getHtmlAttribute(EHtmlAttr.ondblclick);		
	}
	public BaseHtmlElement setHtmlOnDblClick(final String script) {
		setInlineEvent(EHtmlAttr.ondblclick,script,EventType.DBLCLICK);
		return this ;
	}
	
	// ondoubleclick - same as ondblclick ; was already in use with this name...
	/**
	 * @deprecated - use getHtmlOnDblClick()
	 * @see getHtmlDblClick()
	 */
	public String getHtmlOnDoubleClick() {
		return getHtmlAttribute(EHtmlAttr.ondblclick);
	}
	// ondoubleclick - same as ondblclick ; was already in use with this name...
	/**
	 * @deprecated - use setHtmlOnDblClick()
	 * @see setHtmlDblClick()
	 */
	public BaseHtmlElement setHtmlOnDoubleClick(final String script) {
		setInlineEvent(EHtmlAttr.ondblclick,script,EventType.DBLCLICK);
		return this ;
	}
	
	// ondrag
	public String getHtmlOnDrag() {
		return getHtmlAttribute(EHtmlAttr.ondrag);		
	}
	public BaseHtmlElement setHtmlOnDrag(final String script) {
		setInlineEvent(EHtmlAttr.ondrag,script,EventType.DRAG);
		return this ;
	}
	
	// ondragend
	public String getHtmlOnDragEnd() {
		return getHtmlAttribute(EHtmlAttr.ondragend);		
	}
	public BaseHtmlElement setHtmlOnDragEnd(final String script) {
		setInlineEvent(EHtmlAttr.ondragend,script,EventType.DRAGEND);
		return this ;
	}
	
	// ondragenter
	public String getHtmlOnDragEnter() {
		return getHtmlAttribute(EHtmlAttr.ondragenter);		
	}
	public BaseHtmlElement setHtmlOnDragEnter(final String script) {
		setInlineEvent(EHtmlAttr.ondragenter,script,EventType.DRAGENTER);
		return this ;
	}
	
	// ondragleave
	public String getHtmlOnDragLeave() {
		return getHtmlAttribute(EHtmlAttr.ondragleave);		
	}
	public BaseHtmlElement setHtmlOnDragLeave(final String script) {
		setInlineEvent(EHtmlAttr.ondragleave,script,EventType.DRAGLEAVE);
		return this ;
	}
	
	// ondragover
	public String getHtmlOnDragOver() {
		return getHtmlAttribute(EHtmlAttr.ondragover);		
	}
	public BaseHtmlElement setHtmlOnDragOver(final String script) {
		setInlineEvent(EHtmlAttr.ondragover,script,EventType.DRAGOVER);
		return this ;
	}
	
	// ondragstart
	public String getHtmlOnDragStart() {
		return getHtmlAttribute(EHtmlAttr.ondragstart);		
	}
	public BaseHtmlElement setHtmlOnDragStart(final String script) {
		setInlineEvent(EHtmlAttr.ondragstart,script,EventType.DRAGSTART);
		return this ;
	}
	
	// ondrop
	public String getHtmlOnDrop() {
		return getHtmlAttribute(EHtmlAttr.ondrop);		
	}
	public BaseHtmlElement setHtmlOnDrop(final String script) {
		setInlineEvent(EHtmlAttr.ondrop,script,EventType.DROP);
		return this ;
	}
	
	// ondurationchange
	public String getHtmlOnDurationChange() {
		return getHtmlAttribute(EHtmlAttr.ondurationchange);		
	}
	public BaseHtmlElement setHtmlOnDurationChange(final String script) {
		setInlineEvent(EHtmlAttr.ondurationchange,script,EventType.DURATIONCHANGE);
		return this ;
	}
	
	// onemptied
	public String getHtmlOnEmptied() {
		return getHtmlAttribute(EHtmlAttr.onemptied);		
	}
	public BaseHtmlElement setHtmlOnEmptied(final String script) {
		setInlineEvent(EHtmlAttr.onemptied,script,EventType.EMPTIED);
		return this ;
	}
	
	// onended
	public String getHtmlOnEnded() {
		return getHtmlAttribute(EHtmlAttr.onended);		
	}
	public BaseHtmlElement setHtmlOnEnded(final String script) {
		setInlineEvent(EHtmlAttr.onended,script,EventType.ENDED);
		return this ;
	}
	
	// onerror - not supported on BODY
	public String getHtmlOnError() {
		return getHtmlAttribute(EHtmlAttr.onerror);		
	}
	public BaseHtmlElement setHtmlOnError(final String script) {
		setInlineEvent(EHtmlAttr.onerror,script,EventType.ERROR);
		return this ;
	}

	// onfocus - not supported on BODY
	public String getHtmlOnFocus() {
		return getHtmlAttribute(EHtmlAttr.onfocus);		
	}
	public BaseHtmlElement setHtmlOnFocus(final String script) {
		setInlineEvent(EHtmlAttr.onfocus,script,EventType.FOCUS);
		return this ;
	}
	
	// onformchange
	public String getHtmlOnFormChange() {
		return getHtmlAttribute(EHtmlAttr.onformchange);		
	}
	public BaseHtmlElement setHtmlOnFormChange(final String script) {
		setInlineEvent(EHtmlAttr.onformchange,script,EventType.FORMCHANGE);
		return this ;
	}
	
	// onforminput
	public String getHtmlOnFormInput() {
		return getHtmlAttribute(EHtmlAttr.onforminput);		
	}
	public BaseHtmlElement setHtmlOnFormInput(final String script) {
		setInlineEvent(EHtmlAttr.onforminput,script,EventType.FORMINPUT);
		return this ;
	}
	
	// oninput
	public String getHtmlOnInput() {
		return getHtmlAttribute(EHtmlAttr.oninput);		
	}
	public BaseHtmlElement setHtmlOnInput(final String script) {
		setInlineEvent(EHtmlAttr.oninput,script,EventType.INPUT);
		return this ;
	}
	
	// oninvalid
	public String getHtmlOnInvalid() {
		return getHtmlAttribute(EHtmlAttr.oninvalid);		
	}
	public BaseHtmlElement setHtmlOnInvalid(final String script) {
		setInlineEvent(EHtmlAttr.oninvalid,script,EventType.INVALID);
		return this ;
	}
	
	// onkeydown
	public String getHtmlOnKeyDown() {
		return getHtmlAttribute(EHtmlAttr.onkeydown);
	}
	public BaseHtmlElement setHtmlOnKeyDown(final String script) {
		setInlineEvent(EHtmlAttr.onkeydown,script,EventType.KEYDOWN);
		return this ;
	}

	// onkeypress
	public String getHtmlOnKeyPress() {
		return getHtmlAttribute(EHtmlAttr.onkeypress);
	}
	public BaseHtmlElement setHtmlOnKeyPress(final String script) {
		setInlineEvent(EHtmlAttr.onkeypress,script,EventType.KEYPRESS);
		return this ;
	}
	
	// onkeyup
	public String getHtmlOnKeyUp() {
		return getHtmlAttribute(EHtmlAttr.onkeyup);
	}
    public BaseHtmlElement setHtmlOnKeyUp(final String script) {
    	setInlineEvent(EHtmlAttr.onkeyup,script,EventType.KEYUP);
    	return this ;
	}
    
    // onload
	public String getHtmlOnLoad() {
		return getHtmlAttribute(EHtmlAttr.onload);
	}
    public BaseHtmlElement setHtmlOnLoad(final String script) {
    	setInlineEvent(EHtmlAttr.onload,script,EventType.LOAD);
    	return this ;
	}
    
    // onloadeddata
	public String getHtmlOnLoadedData() {
		return getHtmlAttribute(EHtmlAttr.onloadeddata);
	}
    public BaseHtmlElement setHtmlOnLoadedData(final String script) {
    	setInlineEvent(EHtmlAttr.onloadeddata,script,EventType.LOADEDDATA);
    	return this ;
	}   
    
    // onloadedmetadata
	public String getHtmlOnLoadedMetadata() {
		return getHtmlAttribute(EHtmlAttr.onloadedmetadata);
	}
    public BaseHtmlElement setHtmlOnLoadedMetadata(final String script) {
    	setInlineEvent(EHtmlAttr.onloadedmetadata,script,EventType.LOADEDMETADATA);
    	return this ;
	}  
	
    // onloadstart
	public String getHtmlOnLoadStart() {
		return getHtmlAttribute(EHtmlAttr.onloadstart);
	}
    public BaseHtmlElement setHtmlOnLoadStart(final String script) {
    	setInlineEvent(EHtmlAttr.onloadstart,script,EventType.LOADSTART);
    	return this ;
	} 
    
    // onmousedown
	public String getHtmlOnMouseDown(){
		return getHtmlAttribute(EHtmlAttr.onmousedown);		
	}
	public BaseHtmlElement setHtmlOnMouseDown(final String script){
		setInlineEvent(EHtmlAttr.onmousedown,script,EventType.MOUSEDOWN);
		return this ;
	}
	
	// onmousemove
	public String getHtmlOnMouseMove() {
		return getHtmlAttribute(EHtmlAttr.onmousemove);
	}
	public BaseHtmlElement setHtmlOnMouseMove(final String script) {
		setInlineEvent(EHtmlAttr.onmousemove,script,EventType.MOUSEMOVE);
		return this ;
	}
	
	// onmouseout
	public String getHtmlOnMouseOut() {
		return getHtmlAttribute(EHtmlAttr.onmouseout);
	}
	public BaseHtmlElement setHtmlOnMouseOut(final String script) {
		setInlineEvent(EHtmlAttr.onmouseout,script,EventType.MOUSEOUT);
		return this ;
	}
	
	// onmouseover
	public String getHtmlOnMouseOver() {
		return getHtmlAttribute(EHtmlAttr.onmouseover);		
	}
	public BaseHtmlElement setHtmlOnMouseOver(final String script) {
		setInlineEvent(EHtmlAttr.onmouseover,script,EventType.MOUSEOVER);
		return this ;
	}
	
	// onmouseup
	public String getHtmlOnMouseUp() {
		return getHtmlAttribute(EHtmlAttr.onmouseup);		
	}
	public BaseHtmlElement setHtmlOnMouseUp(final String script) {
		setInlineEvent(EHtmlAttr.onmouseup,script,EventType.MOUSEUP);
		return this ;
	}
    
	// onmousewheel
	public String getHtmlOnMouseWheel() {
		return getHtmlAttribute(EHtmlAttr.onmousewheel);		
	}
	public BaseHtmlElement setHtmlOnMouseWheel(final String script) {
		setInlineEvent(EHtmlAttr.onmousewheel,script,EventType.MOUSEWHEEL);
		return this ;
	}
    
	// onpause
	public String getHtmlOnPause() {
		return getHtmlAttribute(EHtmlAttr.onpause);		
	}
	public BaseHtmlElement setHtmlOnPause(final String script) {
		setInlineEvent(EHtmlAttr.onpause,script,EventType.PAUSE);
		return this ;
	}  
    
	// onplay
	public String getHtmlOnPlay() {
		return getHtmlAttribute(EHtmlAttr.onplay);		
	}
	public BaseHtmlElement setHtmlOnPlay(final String script) {
		setInlineEvent(EHtmlAttr.onplay,script,EventType.PLAY);
		return this ;
	}
    
	// onplaying
	public String getHtmlOnPlaying() {
		return getHtmlAttribute(EHtmlAttr.onplaying);		
	}
	public BaseHtmlElement setHtmlOnPlaying(final String script) {
		setInlineEvent(EHtmlAttr.onplaying,script,EventType.PLAYING);
		return this ;
	}
	
	// onprogress
	public String getHtmlOnProgress() {
		return getHtmlAttribute(EHtmlAttr.onprogress);		
	}
	public BaseHtmlElement setHtmlOnProgress(final String script) {
		setInlineEvent(EHtmlAttr.onprogress,script,EventType.PROGRESS);
		return this ;
	}
    
	// onratechange
	public String getHtmlOnRateChange() {
		return getHtmlAttribute(EHtmlAttr.onratechange);		
	}
	public BaseHtmlElement setHtmlOnRateChange(final String script) {
		setInlineEvent(EHtmlAttr.onratechange,script,EventType.RATECHANGE);
		return this ;
	}
    
	// onreadystatechange
	public String getHtmlOnReadyStateChange() {
		return getHtmlAttribute(EHtmlAttr.onreadystatechange);		
	}
	public BaseHtmlElement setHtmlOnReadyStateChange(final String script) {
		setInlineEvent(EHtmlAttr.onreadystatechange,script,EventType.READYSTATECHANGE);
		return this ;
	}
	
	// onscroll
	public String getHtmlOnScroll() {
		return getHtmlAttribute(EHtmlAttr.onscroll);		
	}
	public BaseHtmlElement setHtmlOnScroll(final String script) {
		setInlineEvent(EHtmlAttr.onscroll,script,EventType.SCROLL);
		return this ;
	}
	
	// onseeked
	public String getHtmlOnSeeked() {
		return getHtmlAttribute(EHtmlAttr.onseeked);		
	}
	public BaseHtmlElement setHtmlOnSeeked(final String script) {
		setInlineEvent(EHtmlAttr.onseeked,script,EventType.SEEKED);
		return this ;
	}
    
	// onseeking
	public String getHtmlOnSeeking() {
		return getHtmlAttribute(EHtmlAttr.onseeking);		
	}
	public BaseHtmlElement setHtmlOnSeeking(final String script) {
		setInlineEvent(EHtmlAttr.onseeking,script,EventType.SEEKING);
		return this ;
	}
	
	// onselect
	public String getHtmlOnSelect() {
		return getHtmlAttribute(EHtmlAttr.onselect);		
	}
	public BaseHtmlElement setHtmlOnSelect(final String script) {
		setInlineEvent(EHtmlAttr.onselect,script,EventType.SELECT);
		return this ;
	}
	
	// onshow
	public String getHtmlOnShow() {
		return getHtmlAttribute(EHtmlAttr.onshow);		
	}
	public BaseHtmlElement setHtmlOnShow(final String script) {
		setInlineEvent(EHtmlAttr.onshow,script,EventType.SHOW);
		return this ;
	}
	
	// onstalled
	public String getHtmlOnStalled() {
		return getHtmlAttribute(EHtmlAttr.onstalled);		
	}
	public BaseHtmlElement setHtmlOnStalled(final String script) {
		setInlineEvent(EHtmlAttr.onstalled,script,EventType.STALLED);
		return this ;
	}
	
	// onsubmit
	public String getHtmlOnSubmit() {
		return getHtmlAttribute(EHtmlAttr.onsubmit);		
	}
	public BaseHtmlElement setHtmlOnSubmit(final String script) {
		setInlineEvent(EHtmlAttr.onsubmit,script,EventType.SUBMIT);
		return this ;
	}
	
	// onsuspend
	public String getHtmlOnSuspend() {
		return getHtmlAttribute(EHtmlAttr.onsuspend);		
	}
	public BaseHtmlElement setHtmlOnSuspend(final String script) {
		setInlineEvent(EHtmlAttr.onsuspend,script,EventType.SUSPEND);
		return this ;
	}
	
	// ontimeupdate
	public String getHtmlOnTimeUpdate() {
		return getHtmlAttribute(EHtmlAttr.ontimeupdate);		
	}
	public BaseHtmlElement setHtmlOnTimeUpdate(final String script) {
		setInlineEvent(EHtmlAttr.ontimeupdate,script,EventType.TIMEUPDATE);
		return this ;
	}
	
	// onvolumechange
	public String getHtmlOnVolumeChange() {
		return getHtmlAttribute(EHtmlAttr.onvolumechange);		
	}
	public BaseHtmlElement setHtmlOnVolumeChange(final String script) {
		setInlineEvent(EHtmlAttr.onvolumechange,script,EventType.VOLUMECHANGE);
		return this ;
	}
	
	// onwaiting
	public String getHtmlOnWaiting() {
		return getHtmlAttribute(EHtmlAttr.onwaiting);		
	}
	public BaseHtmlElement setHtmlOnWaiting(final String script) {
		setInlineEvent(EHtmlAttr.onwaiting,script,EventType.WAITING);
		return this ;
	}
	
	//
	// Framework - Event Wiring
	//
	public BaseHtmlElement add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
// MrP - this will get done in the EventHandlerAttacher.add(...)
//		if (getHtmlId().equals("")) {
//			setHtmlId(DsfCtx.ctx().ids().nextHtmlId()); 
//		}
		EventHandlerAttacher.add(this, eventType, handler) ;
		return this ;
	}
	
	public BaseHtmlElement add(final EventType eventType, final IJsFunc func){
// MrP - this will get done in the EventHandlerAttacher.add(...)
//		if (getHtmlId().equals("")) {
//			setHtmlId(DsfCtx.ctx().ids().nextHtmlId()); 
//		}
		EventHandlerAttacher.add(this, eventType, func) ;
		return this ;
	}
	
	public BaseHtmlElement add(final EventType eventType, final String jsText){
		EventHandlerAttacher.add(this, eventType, jsText) ;
		return this ;
	}
	
	public boolean hasEventHandlers() {
		return getEventHandlerContainer().getNumOfHandlers(this) > 0 ? true : false;
	}
	
	public boolean removeEventHandler(final ISimpleJsEventHandler handler) {
		return getEventHandlerContainer().removeHandler(this, handler);
	}
	
//	public BaseHtmlElement add(final IDapEventListener listener){
//		DapCtx.ctx().getEventListenerRegistry().addListener(this, listener) ;
//		return this ;
//	}
//	
//	public BaseHtmlElement add(
//		final EventType eventType, final IDapEventListener listener)
//	{
//		DapCtx.ctx().getEventListenerRegistry().addListener(this, eventType, listener) ;
//		return this ;
//	}
//	
//	public BaseHtmlElement removeListener(
//		final EventType eventType, final IDapEventListener listener)
//	{
//		DapCtx.ctx().getEventListenerRegistry().remove(this, eventType, listener) ;
//		return this ;
//	}
	
	//
	// Helpers
	//
	public BaseHtmlElement addBr() {
		final DBr br = new DBr() ;
		add(br) ;
		return this ;
	}
	
	public BaseHtmlElement addBr(final int howMany){
		for(int i = 0; i < howMany; i++) {
			addBr() ;
		}
		return this ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	public BaseHtmlElement addHtmlClassName(final String className) {
		if (hasHtmlAttribute(EHtmlAttr._class)) {
			setHtmlAttribute(EHtmlAttr._class, getHtmlAttribute(EHtmlAttr._class) + " " + className);
		}
		else {
			setHtmlAttribute(EHtmlAttr._class, className);
		}
		return this ;
	}
	
	public BaseHtmlElement addHtmlClassName(final CssClassConstant ccc) {
		DarwinStatisticsCtxHelper.countCssStatistics(ccc.getCssrName());
		return addHtmlClassName(ccc.getName()) ;
	}

	@Override
	public BaseHtmlElement jif(final String jif) { 
		Jif.jif(this, jif, true, "Html", "Dsf") ; 
		return this ;
	}
	
	public BaseHtmlElement add(BaseHtmlElement... elems) throws DOMException {
		for(BaseHtmlElement elem: elems) add(elem) ;
		return this ;
	}
	
	@Override
	public String toString() {
		return super.toString()
			+ Z.fmt(EHtmlAttr.dir.getAttributeName(), getHtmlDir())
			+ Z.fmt(EHtmlAttr.lang.getAttributeName(), getHtmlLang())
			+ Z.fmt(EHtmlAttr._class.getAttributeName(), getHtmlClassName())
			+ Z.fmt(EHtmlAttr.id.getAttributeName(), getHtmlId())
			+ Z.fmt(EHtmlAttr.style.getAttributeName(), getHtmlStyleAsString())		
			+ Z.fmt(EHtmlAttr.title.getAttributeName(), getHtmlTitle()) ;	
	}	

	//
	// Helper class(es)
	//
	static class DomAssociator extends Associator {
		protected static void setNameAsCharIndex(final DAttr attr, final int index) {
			Associator.setNameAsCharIndex(attr, index) ;
		}
		
		protected static int getNameAsCharIndex(final DAttr attr) {
			return Associator.getNameAsCharIndex(attr) ;
		}
		
		protected static DAttr attributeMapPut(
			final DElement element, final DAttr attr)
		{
			return Associator.attributeMapPut(element, attr) ;
		}
	}
	
	//
	// HTML Attributes support
	//

	/**
	 * Translates an attribute value into an integer value. Returns the integer 
	 * value or zero if the attribute is not a valid numeric string.
	 * <p>
	 * Note: It parses the string value on each call.
	 * 
	 * @param value The value of the attribute
	 * @return The integer value, or zero if not a valid numeric string
	 */
	int getHtmlAttributeInteger(final EHtmlAttr attr) {
		try {
			final String value = getHtmlAttribute(attr) ;
			return Integer.parseInt(value);
		} 
		catch (NumberFormatException except) {
			return 0;
		}
	}

	/**
	 * Translates an attribute value into a boolean value. If the attribute has 
	 * an associated value (even an empty string), it is set and true is 
	 * returned. If the attribute does not exist, false is returend.
	 * 
	 * @param value The value of the attribute
	 * @return True or false depending on whether the attribute has been set
	 */
	boolean getHtmlAttributeExists(final EHtmlAttr attr) {
		return hasAttribute(attr.getAttributeName());
	}
	
	boolean hasHtmlAttribute(final EHtmlAttr attr) {
		return super.hasAttribute(attr.getAttributeName()) ;
	}
	
	String getHtmlAttribute(final EHtmlAttr attr) {
		return super.getAttribute(attr.getAttributeName());
	}
	
	public void setHtmlAttribute(final EHtmlAttr attr, final String value) {
		setAttributeInternal(attr.getAttributeName(), value, attr.ordinal()) ;
	}
	
	void setHtmlAttribute(final EHtmlAttr attr, final int value) {
		setHtmlAttribute(attr, String.valueOf(value)) ;
	}
	
	void setHtmlAttribute(final EHtmlAttr attr, final double value) {
		setHtmlAttribute(attr, String.valueOf(value)) ;
	}
	
	void setHtmlAttribute(final EHtmlAttr attr, final boolean value) {
		if (value) {
			setAttribute(attr.getAttributeName(), attr.getAttributeName());
		} 
		else {
			getDsfAttributes().remove(attr.getAttributeName());
		}		
	}
	
	/*
	 * Optimized for new entries vs. looking up old entry and replacing value
	 * @param name
	 * @param value
	 * @param nameAsCharsIndex
	 */
	private void setAttributeInternal(
		final String name, final String value, final int nameAsCharsIndex)
	{
		final DAttr attr = new DAttr(name, value); 
		getAttributes() ; // make sure lazy attrs map is created
		// This is a reach-in to avoid some useless overhead checks
		final DAttr previousAttr = DomAssociator.attributeMapPut(this, attr);
		if (previousAttr == null) {
			DomAssociator.setNameAsCharIndex(attr, nameAsCharsIndex) ;
			attr.setValue(value);
			return ;
		}
		
		// We have a previous attr that has been displaced and needs a new
		// value and to be put back in the map.  It's parenting is still
		// ok since all the attributeMapPut(...) did was replace it in the
		// attributes map.
		previousAttr.setValue(value) ;
		DomAssociator.attributeMapPut(this, previousAttr);		
	}
		
	//
	// Misc
	//

	void setInlineEvent(EHtmlAttr attr, String script, EventType eventType){
		if(HtmlCtx.ctx().isDisableInlineHandler()){
			HtmlCtx.ctx().getInlineEventHandlerContainer().add(this, eventType, script);
		}
		else {
			setHtmlAttribute(attr, script);
		}
	}	
	
	private EventHandlerContainer getEventHandlerContainer() {
		return HtmlCtx.ctx().getEventHandlerContainer() ;
	}
	
	private final void setStyleInternal(final String style) {
		setHtmlAttribute(EHtmlAttr.style, style);
	}
	
	/**
	 * Capitalizes a one-off attribute value before it is returned. 
	 * <p>
	 * For example, the align values "LEFT" and "left" will both return as 
	 * "Left".
	 * 
	 * @param value The value of the attribute
	 * @return The capitalized value
	 */
	String capitalize(final String value) {
// MrPperf -- we shouldn't be messing with this value...
		return value ;
/*
		if (value == null) {
			return null;
		}
		
		char[] chars;
		int i;

		// Convert string to charactares. Convert the first one to upper case,
		// the other characters to lower case, and return the converted string.
		chars = value.toCharArray();
		if (chars.length > 0) {
			chars[0] = Character.toUpperCase(chars[0]);
			for (i = 1; i < chars.length; ++i) {
				chars[i] = Character.toLowerCase(chars[i]);
			}
			return String.valueOf(chars);
		}
		return value;
*/
	}

	/**
	 * Capitalizes a one-off attribute value before it is returned. For example, 
	 * the align values "LEFT" and "left" will both return as "Left".
	 * 
	 * @param name The name of the attribute
	 * @return The capitalized value
	 */
	String getCapitalized(final String name) {
// MrPperf -- we shouldn't be messing with these values
		return name ;
/*
		char[] chars;
		int i;

		final String value = getDomAttribute(name);
		if (value != null) {
			// Convert string to charactares. Convert the first one to upper case,
			// the other characters to lower case, and return the converted string.
			chars = value.toCharArray();
			if (chars.length > 0) {
				chars[0] = Character.toUpperCase(chars[0]);
				for (i = 1; i < chars.length; ++i) {
					chars[i] = Character.toLowerCase(chars[i]);
				}
				return String.valueOf(chars);
			}
		}
		return value;
*/
	}
}
