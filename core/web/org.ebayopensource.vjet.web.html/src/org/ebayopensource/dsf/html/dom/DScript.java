/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import java.util.Iterator;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.common.Z;
import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.common.node.IAttributeMap;
import org.ebayopensource.dsf.common.node.IDNodeRelationshipVerifier;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.css.CssClassConstant;
import org.ebayopensource.dsf.css.CssIdConstant;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DDocumentType;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.html.dom.util.INodeEmitter;
import org.ebayopensource.dsf.html.dom.util.IRawSaxHandler;
import org.ebayopensource.dsf.html.dom.util.ISelfRender;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;

import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
/**
* http://www.w3.org/TR/REC-html40/interact/scripts.html#edef-SCRIPT
*/
public class DScript extends BaseOrigNonAttrs implements ISelfRender
//	implements IDRepeatableHeadElement, IDSpecial, IDStrict, INoStandardAttributes,
//		/* HTML 5.0 */ IMetadataContent
{
	private static final long serialVersionUID = 3832626162072498224L;
	private static final DScript s_script = new DScript() ;
	
	/** "text/css" */
	public static final String TYPE_TEXT_CSS = "text/css" ;
	/** "text/javascript" */
	public static final String TYPE_TEXT_JAVASCRIPT = "text/javascript" ;
	/** "text/vbscript" */
	public static final String TYPE_TEXT_VBSCRIPT = "text/vbscript" ;
	
	//Document location for this script enum
	/** value = 0 */
	public static final int DOCUMENT_LOCATION_HEAD = 0;
	/** value = 1 */
	public static final int DOCUMENT_LOCATION_BODY = 1;
	
	@Deprecated
	private int m_documentLocation;
	
	//
	// Constructor(s)
	//
	public DScript() {
		super(null, HtmlTypeEnum.SCRIPT);
		m_documentLocation = 0 ; // HEAD
	}
	
	public DScript(final DHtmlDocument doc) {
		super(doc, HtmlTypeEnum.SCRIPT);
	}
	
//	public DScript(final String jif) {
//		this() ;
//		jif(jif) ;
//	}
	
	// 
	// Extension Constructor(s)
	//
	public DScript(final String text) {
		this() ;
		setHtmlText(text) ;
	}

	public DScript(final String text, final String type) {
		this() ;
		setHtmlText(text) ;
		setHtmlType(type) ;
	}

    public DScript(final Class anchorClass, final String resourceName) {
        this() ;
        setHtmlType("text/javascript") ;
        setHtmlSrc(anchorClass.getResource(resourceName).toExternalForm()) ;
    }
    
	//
	// Framework
	//
    @Override
	public HtmlTypeEnum htmlType() {
		return HtmlTypeEnum.SCRIPT ;
	}

    //
    // Satisfy ISelfRender
    //
	public boolean render(
		final IRawSaxHandler rawSaxHandler,
		final IXmlStreamWriter xmlStreamWriter,
		final INodeEmitter nodeEmitter)
	{
		DDocument doc = getDsfOwnerDocument() ;
		if (doc == null || doc.getDoctype() == null) {
			rawSaxHandler.startElement(this);
			writeKids(xmlStreamWriter, nodeEmitter) ;
			rawSaxHandler.endElement(this) ;
			return true ;
		}
		
		DDocumentType doctype = doc.getDoctype() ;
		
		// for XML, SVG, XHTML, etc... we need to CDATA any scripting code
		boolean addCDATA = scriptRequiresCDATA(doctype) ;
		
		// if CDATA and text/javascript use JS comment for older browser support
		boolean isJavaScript = javascript() ;

		rawSaxHandler.startElement(this);
		
		if (addCDATA) {
			if (isJavaScript) xmlStreamWriter.writeRaw("//") ;
			xmlStreamWriter.writeRaw("<![CDATA[") ;
		}
		
		writeKids(xmlStreamWriter, nodeEmitter) ;
	
		if (addCDATA) {
			if (isJavaScript) xmlStreamWriter.writeRaw("//") ;
			xmlStreamWriter.writeRaw("]]>") ;
		}
		
		rawSaxHandler.endElement(this) ;
	
		return true ;
	}

	private void writeKids(final IXmlStreamWriter xmlStreamWriter,
			final INodeEmitter nodeEmitter) {
		if (this.hasChildNodes()) {
			NodeList kids = this.getChildNodes() ;
			int len = kids.getLength();
			for(int i = 0; i < len; i++) {
				Node kid = kids.item(i) ;
				nodeEmitter.genEvents(kid, xmlStreamWriter) ;
			}
		}
	}
	
	private void writeMyself(
		final IRawSaxHandler rawSaxHandler, 
		final IXmlStreamWriter xmlStreamWriter)
	{
//		rawSaxHandler.startElement(this);
//		writeAttributes(xmlStreamWriter) ;
		rawSaxHandler.startElement(this);
	}
	
	private void writeAttributes(final IXmlStreamWriter xmlStreamWriter) {
		if (!hasAttributes()) {
			return;
		}
		final IAttributeMap attrs = getDsfAttributes();
		final Iterator<String> itr = attrs.keySet().iterator();
		while (itr.hasNext()) {
			final String key = itr.next();
			Object value = attrs.get(key);
			xmlStreamWriter.writeAttribute(key,
					value == null ? "" : value.toString());
		}
	}
	
	private boolean scriptRequiresCDATA(final DDocumentType doctype) {
		if (doctype == null) return false ;
//		String sysId = doctype.getSystemId() ;
		String pubId = doctype.getPublicId() ;
//		String prefix = doctype.getPrefix() ;
//		String baseUri = doctype.getBaseURI();
//		String localName = doctype.getLocalName();
//		String name = doctype.getName();
//		String textContent = doctype.getTextContent() ;
		
		if (pubId.contains("-//W3C//DTD XHTML")) return true ;
		if (pubId.contains("-//W3C//DTD XML")) return true ;
		if (pubId.contains("-//W3C//DTD SVG")) return true ;
		if (pubId.contains("-//W3C//DTD MathML")) return true ;
		
		return false ;
	}
	
	private boolean javascript() {
// TODO: MrP - can't use JS comments in XML formed docs!
		return false ; 
//		return "text/javascript".equalsIgnoreCase(getHtmlType()) ;
	}
	
/**
 * @see JsSlotLocation for location of JS slots.
 * @return
 */
	@Deprecated
	public int getDocumentLocation() {
		return m_documentLocation ;
	}
	
	@Deprecated
	public void setDocumentLocation(final int location) {
		m_documentLocation = location ;
	}
		
	//
	// HTML Attributes
	//	
	public String getHtmlText() {
		final StringBuilder text = new StringBuilder();

		// Find the Text nodes contained within this element and return their
		// concatenated value. Required to go around comments, entities, etc.
		Node child = getFirstChild();
		while (child != null) {
			if (child instanceof DText) {
				text.append(((DText) child).getData());
			}
			child = child.getNextSibling();
		}
		return text.toString();
	}

	public final DScript setHtmlText(final String text) {
		// Delete all the nodes and replace them with a single Text node.
		// This is the only approach that can handle comments and other nodes.
		
		// adding optimizer
//		JsResourceOptimizer optimizer = new JsResourceOptimizer(text,ResourceTypes.JAVASCRIPT, text);
//		if(optimizer.optimize())
//		{
//			text = optimizer.getOptimizedCode();
//		}
		Node child = getFirstChild();
		while (child != null) {
			final Node next = child.getNextSibling();
			removeChild(child);
			child = next;
		}
		add(text); // all children have been removed
		return this;
	}

//	public String getHtmlEvent() {
//		return getDomAttribute("event");
//	}
//
//	public DScript setHtmlEvent(String event) {
//		setAttribute("event", event);
//		return this ;
//	}

	public String getHtmlCharset() {
		return getHtmlAttribute(EHtmlAttr.charset);
	}
	public DScript setHtmlCharset(final String charset) {
		setHtmlAttribute(EHtmlAttr.charset, charset);
		return this ;
	}

	public final boolean getHtmlDefer() {
		return getHtmlAttributeExists(EHtmlAttr.defer);
	}
	public DScript setHtmlDefer(final String defer) {
		return setHtmlDefer(toBoolean("defer", defer)) ;
	}	
	public DScript setHtmlDefer(final boolean defer) {
		setHtmlAttribute(EHtmlAttr.defer, defer);
		return this ;
	}

	public String getHtmlSrc() {
		return getHtmlAttribute(EHtmlAttr.src);
	}
	public DScript setHtmlSrc(final String src) {
		setHtmlAttribute(EHtmlAttr.src, src);
		return this ;
	}

	public String getHtmlType() {
		return getHtmlAttribute(EHtmlAttr.type);
	}
	public final DScript setHtmlType(final String type) {
		setHtmlAttribute(EHtmlAttr.type, type);
		return this ;
	}
	
	public final boolean getHtmlAsync() {
		return getHtmlAttributeExists(EHtmlAttr.async);
	}
	public DScript setHtmlAsync(final String async) {
		return setHtmlAsync(toBoolean("async", async)) ;
	}	
	public DScript setHtmlAsync(final boolean async) {
		setHtmlAttribute(EHtmlAttr.async, async);
		return this ;
	}
	
	/**
	 * @deprecated - use getHtmlLang() instead
	 * @see - getHtmlLang()
	 */
	public String getHtmlLanguage() {
		return getHtmlAttribute(EHtmlAttr.language);
	}
	/**
	 * @deprecated - use setHtmlLang() instead
	 * @see - setHtmlLang()
	 */
	public DScript setHtmlLanguage(final String language) {
		setHtmlAttribute(EHtmlAttr.language, language);
		return this ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return super.toString() +
		Z.fmt("charset", getHtmlCharset()) + 
		Z.fmt("defer", "" + getHtmlDefer()) +
		Z.fmt("language", getHtmlLanguage()) +
		Z.fmt("src", "" + getHtmlSrc()) +
		Z.fmt("text", "" + getHtmlText()) +
		Z.fmt("type", getHtmlType()) +
		Z.fmt("async", getHtmlAsync()) ;
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
	public DScript add(final DNode newChild) throws DOMException {
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
	public DScript add(final String value) throws DOMException {
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
	public DScript addRaw(final String value) throws DOMException {
		super.addRaw(value) ;
		return this ;
	}
	
	/**
	 * This double dispatch approach provides the control point for the node
	 * to have customized behavior.
	 */
	@Override
	public DScript dsfAccept(final IDNodeVisitor visitor) {
		super.dsfAccept(visitor) ;
		return this;
	}
	
	/**
	 * Broadcasts the event to any registered IDsfEventListner's.
	 * The listeners are broadcast to in the order they were maintained.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DScript dsfBroadcast(final DsfEvent event) // must not be null
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
	public DScript setDsfRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
		super.setDsfRelationshipVerifier(relationshipVerifier) ;
		return this;
	}
	
	@Override
	public DScript cloned() {
		return (DScript)super.cloned() ;
	}
	
    /**
     * set namespace for this node.
     * update the nodename based on the given namespace
     * @param namespace
     * @return
     */
    @Override
    public DScript setDsfNamespace(DNamespace namespace){
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
	public DScript setHtmlAccessKey(final String accessKey) {
		super.setHtmlAccessKey(accessKey) ;
		return this ;
	}
		
	/**
	 * set class name, overwrite current class(es)
	 */
	@Override
	public DScript setHtmlClassName(final String className) {
		super.setHtmlClassName(className) ;
		return this ;
	}
	@Override
	public DScript setHtmlClassName(final CssClassConstant ccc) {
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
	public DScript setHtmlContentEditable(final String editable) {
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
	public DScript setHtmlContextMenu(final String contextMenu) {
		super.setHtmlContextMenu(contextMenu) ;
		return this ;
	}
	
	/**
	 * The dir attribute specifies the element's text directionality. The attribute 
	 * is an enumerated attribute with the keyword ltr mapping to the state ltr, 
	 * and the keyword rtl  mapping to the state rtl. The attribute has no defaults.
	 */
	@Override
	public DScript setHtmlDir(final String dir) {
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
	public DScript setHtmlDraggable(final String draggable) {  // true, false, auto
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
	public DScript setHtmlDraggable(final boolean draggable) {  // true, false
		super.setHtmlDraggable(draggable) ;
		return this ;
	}
	
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DScript setHtmlHidden(final String hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	/** 
	 * This attribute is a boolean attribute. When specified on an element, it 
	 * indicates that the element is not yet, or is no longer, relevant. User 
	 * agents should not render elements that have the hidden attribute specified.
	 */
	@Override
	public DScript setHtmlHidden(final boolean hidden) {
		super.setHtmlHidden(hidden);
		return this ;
	}
	
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */
	@Override
	public DScript setHtmlId(String id) {
		super.setHtmlId(id) ;
		return this ;
	}
	/**
	 * The id attribute represents its element's unique identifier. The value must 
	 * be unique in the element's home subtree and must contain at least one character. 
	 * The value must not contain any space characters
	 */	
	@Override
	public DScript setHtmlId(CssIdConstant id) {
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
	public DScript setHtmlItem(final String item) {
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
	public DScript setHtmlItemProp(final String itemProp) {
		super.setHtmlItemProp(itemProp) ;
		return this ;
	}	
	
	/**
	 * The lang attribute (in no namespace) specifies the primary language for 
	 * the element's contents and for any of the element's attributes that contain 
	 * text. Its value must be a valid BCP 47 language code, or the empty string.
	 */
	@Override
	public DScript setHtmlLang(final String lang) {
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
	public DScript setHtmlSpellCheck(final String spellCheck) {
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
	public DScript setHtmlSpellCheck(final boolean spellCheck) {
		super.setHtmlSpellCheck(spellCheck) ;
		return this ;
	}	

	@Override
	public DScript setHtmlStyleAsString(final String styleString) {
		super.setHtmlStyleAsString(styleString) ;
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 */
	@Override
	public DScript setHtmlStyle(final ICssStyleDeclaration style) {
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
	public DScript setHtmlSubject(final String subject) {
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
	public DScript setHtmlTabIndex(final String tabIndex) {  // HTML 5.0
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
	public DScript setHtmlTabIndex(final int tabIndex) {  // HTML 5.0
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
	public DScript setHtmlTitle(final String title) {
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
	public DScript setHtmlOnAbort(final String script) {
		super.setHtmlOnAbort(script) ;
		return this ;
	}
	
	/**
	 * The onblur event occurs when an object loses focus.
	 * not supported on BODY
	 */
	@Override
	public DScript setHtmlOnBlur(final String onBlur) {
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
	public DScript setHtmlOnCanPlay(final String script) {
		super.setHtmlOnCanPlay(script) ;
		return this ;
	}
	
	/**
	 *  The user agent estimates that if playback were to be started now, the 
	 *  media resource could be rendered at the current playback rate all the way 
	 *  to its end without having to stop for further buffering. 
	 */
	@Override
	public DScript setHtmlOnCanPlayThrough(final String script) {
		super.setHtmlOnCanPlayThrough(script) ;
		return this ;
	}
	
	// onchange
	@Override
	public DScript setHtmlOnChange(final String script) {
		super.setHtmlOnChange(script) ;
		return this ;
	}
	
	// onclick
	@Override
	public DScript setHtmlOnClick(final String script) {
		super.setHtmlOnClick(script) ;
		return this ;
	}

	// oncontextmenu
	@Override
	public DScript setHtmlOnContextMenu(final String script) {
		super.setHtmlOnContextMenu(script) ;
		return this ;
	}
	
	// ondblclick
	@Override
	public DScript setHtmlOnDblClick(final String script) {
		super.setHtmlOnDblClick(script) ;
		return this ;
	}
	
	// ondrag
	@Override
	public DScript setHtmlOnDrag(final String script) {
		super.setHtmlOnDrag(script) ;
		return this ;
	}
	
	// ondragend
	@Override
	public DScript setHtmlOnDragEnd(final String script) {
		super.setHtmlOnDragEnd(script) ;
		return this ;
	}
	
	// ondragenter
	@Override
	public DScript setHtmlOnDragEnter(final String script) {
		super.setHtmlOnDragEnter(script) ;
		return this ;
	}
	
	// ondragleave
	@Override
	public DScript setHtmlOnDragLeave(final String script) {
		super.setHtmlOnDragLeave(script) ;
		return this ;
	}
	
	// ondragover
	@Override
	public DScript setHtmlOnDragOver(final String script) {
		super.setHtmlOnDragOver(script) ;
		return this ;
	}
	
	// ondragstart
	@Override
	public DScript setHtmlOnDragStart(final String script) {
		super.setHtmlOnDragStart(script) ;
		return this ;
	}
	
	// ondrop
	@Override
	public DScript setHtmlOnDrop(final String script) {
		super.setHtmlOnDrop(script) ;
		return this ;
	}
	
	// ondurationchange
	@Override
	public DScript setHtmlOnDurationChange(final String script) {
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
	public DScript setHtmlOnEmptied(final String script) {
		super.setHtmlOnEmptied(script) ;
		return this ;
	}
	
	/**
	 * Playback has stopped because the end of the media resource was reached. 
	 */
	@Override
	public DScript setHtmlOnEnded(final String script) {
		super.setHtmlOnEnded(script) ;
		return this ;
	}
	
	/**
	 * An error occurs while fetching the media data. 
	 * not supported on BODY
	 */
	@Override
	public DScript setHtmlOnError(final String script) {
		super.setHtmlOnError(script) ;
		return this ;
	}

	/**
	 * onfocus - not supported on BODY
	 */
	@Override
	public DScript setHtmlOnFocus(final String script) {
		super.setHtmlOnFocus(script) ;
		return this ;
	}
	
	/**
	 * onformchange
	 */
	@Override
	public DScript setHtmlOnFormChange(final String script) {
		super.setHtmlOnFormChange(script) ;
		return this ;
	}
	
	/**
	 * onforminput
	 */
	@Override
	public DScript setHtmlOnFormInput(final String script) {
		super.setHtmlOnFormInput(script) ;
		return this ;
	}
	
	/**
	 * oninput
	 */
	@Override
	public DScript setHtmlOnInput(final String script) {
		super.setHtmlOnInput(script) ;
		return this ;
	}
	
	/**
	 * oninvalid
	 */
	@Override
	public DScript setHtmlOnInvalid(final String script) {
		super.setHtmlOnInvalid(script) ;
		return this ;
	}
	
	/**
	 * onkeydown
	 */
	@Override
	public DScript setHtmlOnKeyDown(final String script) {
		super.setHtmlOnKeyDown(script) ;
		return this ;
	}

	/**
	 * onkeypress
	 */
	@Override
	public DScript setHtmlOnKeyPress(final String script) {
		super.setHtmlOnKeyPress(script) ;
		return this ;
	}
	
	/**
	 * onkeyup
	 */
	@Override
    public DScript setHtmlOnKeyUp(final String script) {
    	super.setHtmlOnKeyUp(script);
    	return this ;
	}
    
	/**
	 * onload
	 */
	@Override
    public DScript setHtmlOnLoad(final String script) {
    	super.setHtmlOnLoad(script) ;
    	return this ;
	}
    
	/**
	 * onloadeddata
	 */
	@Override
    public DScript setHtmlOnLoadedData(final String script) {
    	super.setHtmlOnLoadedData(script) ;
    	return this ;
	}   
    
	/**
	 * onloadedmetadata
	 */
	@Override
    public DScript setHtmlOnLoadedMetadata(final String script) {
    	super.setHtmlOnLoadedMetadata(script) ;
    	return this ;
	}  
	
	/**
	 * onloadstart
	 */
	@Override
    public DScript setHtmlOnLoadStart(final String script) {
    	super.setHtmlOnLoadStart(script) ;
    	return this ;
	} 
    
	/**
	 * onmousedown
	 */
	@Override
	public DScript setHtmlOnMouseDown(final String script){
		super.setHtmlOnMouseDown(script) ;
		return this ;
	}
	
	/**
	 * onmousemove
	 */
	@Override
	public DScript setHtmlOnMouseMove(final String script) {
		super.setHtmlOnMouseMove(script) ;
		return this ;
	}
	
	/**
	 * onmouseout
	 */
	@Override
	public DScript setHtmlOnMouseOut(final String script) {
		super.setHtmlOnMouseOut(script) ;
		return this ;
	}
	
	/**
	 * onmouseover
	 */
	@Override
	public DScript setHtmlOnMouseOver(final String script) {
		super.setHtmlOnMouseOver(script) ;
		return this ;
	}
	
	/**
	 * onmouseup
	 */
	@Override
	public DScript setHtmlOnMouseUp(final String script) {
		super.setHtmlOnMouseUp(script) ;
		return this ;
	}
    
	/**
	 * onmousewheel
	 */
	@Override
	public DScript setHtmlOnMouseWheel(final String script) {
		super.setHtmlOnMouseWheel(script) ;
		return this ;
	}
    
	/**
	 * onpause
	 */
	@Override
	public DScript setHtmlOnPause(final String script) {
		super.setHtmlOnPause(script) ;
		return this ;
	}  
    
	/**
	 * onplay
	 */
	@Override
	public DScript setHtmlOnPlay(final String script) {
		super.setHtmlOnPlay(script) ;
		return this ;
	}
    
	/**
	 * onplaying
	 */
	@Override
	public DScript setHtmlOnPlaying(final String script) {
		super.setHtmlOnPlaying(script) ;
		return this ;
	}
	
	/**
	 * onprogress
	 */
	@Override
	public DScript setHtmlOnProgress(final String script) {
		super.setHtmlOnProgress(script) ;
		return this ;
	}
    
	/**
	 * onratechange
	 */
	@Override
	public DScript setHtmlOnRateChange(final String script) {
		super.setHtmlOnRateChange(script) ;
		return this ;
	}
    
	/**
	 * onreadystatechange
	 */
	@Override
	public DScript setHtmlOnReadyStateChange(final String script) {
		super.setHtmlOnReadyStateChange(script) ;
		return this ;
	}

	/**
	 * onscroll
	 */
	@Override
	public DScript setHtmlOnScroll(final String script) {
		super.setHtmlOnScroll(script) ;
		return this ;
	}
	
	/**
	 * onseeked
	 */
	@Override
	public DScript setHtmlOnSeeked(final String script) {
		super.setHtmlOnSeeked(script) ;
		return this ;
	}
    
	/**
	 * onseeking
	 */
	@Override
	public DScript setHtmlOnSeeking(final String script) {
		super.setHtmlOnSeeking(script) ;
		return this ;
	}
	
	/**
	 * onselect
	 */
	@Override
	public DScript setHtmlOnSelect(final String script) {
		super.setHtmlOnSelect(script) ;
		return this ;
	}
	
	/**
	 * onshow
	 */
	@Override
	public DScript setHtmlOnShow(final String script) {
		super.setHtmlOnShow(script) ;
		return this ;
	}
	
	/**
	 * onstalled
	 */
	@Override
	public DScript setHtmlOnStalled(final String script) {
		super.setHtmlOnStalled(script) ;
		return this ;
	}
	
	/**
	 * onsubmit
	 */
	@Override
	public DScript setHtmlOnSubmit(final String script) {
		super.setHtmlOnSubmit(script) ;
		return this ;
	}
	
	/**
	 * onsuspend
	 */
	@Override
	public DScript setHtmlOnSuspend(final String script) {
		super.setHtmlOnSuspend(script) ;
		return this ;
	}
	
	/**
	 * ontimeupdate
	 */
	@Override
	public DScript setHtmlOnTimeUpdate(final String script) {
		super.setHtmlOnTimeUpdate(script) ;
		return this ;
	}
	
	/**
	 * onvolumechange
	 */
	@Override
	public DScript setHtmlOnVolumeChange(final String script) {
		super.setHtmlOnVolumeChange(script) ;
		return this ;
	}
	
	/**
	 * onwaiting
	 */
	@Override
	public DScript setHtmlOnWaiting(final String script) {
		super.setHtmlOnWaiting(script) ;
		return this ;
	}
	
	//
	// Framework - Event Wiring
	//
	@Override
	public DScript add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		super.add(eventType, handler) ;
		return this ;
	}
	
	@Override
	public DScript add(
		final EventType eventType, 
		final IJsFunc func)
	{
		super.add(eventType, func) ;
		return this ;
	}
	
	@Override
	public DScript add(
		final EventType eventType, 
		final String jsText)
	{
		super.add(eventType, jsText) ;
		return this ;
	}
	
/*	@Override
	public DScript add(final IDomActiveListener listener){
		super.add(listener) ;
		return this ;
	}
	
	@Override
	public DScript add(
		final EventType eventType, final IDomActiveListener listener)
	{
		super.add(eventType, listener) ;
		return this ;
	}
	
	@Override
	public DScript removeListener(
		final EventType eventType, final IDomActiveListener listener)
	{
		super.removeListener(eventType, listener) ;
		return this ;
	}*/
	
	//
	// Helpers
	//
	@Override
	public DScript addBr() {
		super.addBr() ;
		return this ;
	}
	
	@Override
	public DScript addBr(final int howMany){
		super.addBr(howMany) ;
		return this ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	@Override
	public DScript addHtmlClassName(final String className) {
		super.addHtmlClassName(className) ;
		return this ;
	}
	
	@Override
	public DScript addHtmlClassName(final CssClassConstant ccc) {
		super.addHtmlClassName(ccc) ;
		return this ;
	}
	
	@Override
	public DScript jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Child Hooks (none)
	//
}
