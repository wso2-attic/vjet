/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Dynamic;
import org.ebayopensource.dsf.jsnative.anno.FactoryFunc;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsArray;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.OverrideFunc;
import org.ebayopensource.dsf.jsnative.anno.OverrideProp;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.ProxyFunc;
import org.ebayopensource.dsf.jsnative.events.DocumentEvent;
import org.w3c.dom.html.HTMLElement;

/**
 * An <code>HTMLDocument</code> is the root of the HTML hierarchy and holds the entire content. 
 * Besides providing access to the hierarchy, it also provides some 
 * convenience methods for accessing certain sets of information from the document.
 *
 */
@Alias("HTMLDocument")
@DOMSupport(DomLevel.ZERO)
@Dynamic
@JsMetatype
public interface HtmlDocument extends Document, DocumentEvent, DocumentRange {
	/**
	 * Returns all elements in the document
	 * Examples:
	 * document.all
	 * document.all[i]
	 * document.all['name']
	 * document.all['id']
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@JsArray(Node.class)
	@FactoryFunc
	@Property NodeList getAll();
	
	/**
	 * Returns the title of a document as specified 
	 * by the <code>TITLE</code> element in the head of the document
	 * @return
	 */
	@DOMSupport(DomLevel.ONE)
	@Property String getTitle();
	
    /**
     * Sets the title of a document
     * @param title
     */
	@DOMSupport(DomLevel.ONE)
	@Property void setTitle(String title);

    /**
     * Returns the URI [IETF RFC 2396] of the page that linked to this page. 
     * The value is an empty string if the user navigated to the page directly 
     * (not through a link, but, for example, via a bookmark).
     * @return
     */
	@DOMSupport(DomLevel.ONE)
	@Property String getReferrer();

    /**
     * Returns the domain name of the server that served the document, 
     * or <code>null</code> if the server cannot be identified by a domain name
     * @return
     */
	@DOMSupport(DomLevel.ONE)
	@Property String getDomain();
	
	/**
	 * Sets the security domain of the document. 
	 */
	@DOMSupport(DomLevel.ONE)
	@Property void setDomain(String domain);

    /**
     * Returns the absolute URI [IETF RFC 2396] of the document
     * @return
     */
	@DOMSupport(DomLevel.ONE)
	@Property String getURL();

    /**
     * Returns the element that contains the content for the document. 
     * In documents with <code>BODY</code> contents, returns the <code>BODY</code> element. 
     * In frameset documents, this returns the outermost <code>FRAMESET</code> element.
     * @return
     */
	@DOMSupport(DomLevel.ONE)
	@Property HtmlBody getBody();
    

    /**
     * Returns a collection of all the <code>IMG</code> elements in a document. 
     * @return
     */
	@DOMSupport(DomLevel.ZERO)
	@JsArray(Image.class)
	@Property HtmlCollection getImages();
	

    @Property(name="implementation")
    HtmlDOMImplementation getHtmlImplementation();
	

    /**
     * Returns a collection of all the <code>OBJECT</code> elements that include applets 
     * and <code>APPLET</code> (deprecated) elements in a document.
     * @return
     */
	@DOMSupport(DomLevel.ZERO)
	@Property HtmlCollection getApplets();

    /**
     * Returns a collection of all <code>AREA</code> elements and 
     * anchor (A) elements in a document with a value for the href attribute.
     * @return
     */
	@DOMSupport(DomLevel.ZERO)
	@Property HtmlCollection getLinks();

    /**
     * Returns a collection of all the forms of a document.
     * @return
     */
	@DOMSupport(DomLevel.ZERO)
	@Property HtmlCollection getForms();

    /**
     * Returns a collection of all the anchor (A) elements 
     * in a document with a value for the name attribute.
     * @return
     */
	@DOMSupport(DomLevel.ZERO)
	@Property HtmlCollection getAnchors();

    /**
     * Returns a collection of all the frame(FRAMES) elements 
     * in a document 
     * @return
     */
	@DOMSupport(DomLevel.ZERO)
	@BrowserSupport({BrowserType.IE_6P})
	@Property HtmlCollection getFrames();

    /**
     * This mutable string attribute denotes persistent state information that (1) 
     * is associated with the current frame or document and (2) is composed of 
     * information described by the cookies non-terminal of [IETF RFC 2965], 
     * Section 4.2.2.
     * If no persistent state information is available for the current frame or 
     * document document, then this property's value is an empty string.
     * When this attribute is read, all cookies are returned as a single string, 
     * with each cookie's name-value pair concatenated into a list of name-value pairs, 
     * each list item being separated by a ';' (semicolon).
     * @return
     */
	@DOMSupport(DomLevel.ONE)
	@Property String getCookie();
    
    /**
     * When this attribute is set, the value it is set to should be a string 
     * that adheres to the cookie non-terminal of [IETF RFC 2965]; that is, 
     * it should be a single name-value pair followed by zero or more cookie 
     * attribute values. If no domain attribute is specified, then the domain 
     * attribute for the new value defaults to the host portion of an 
     * absolute URI [IETF RFC 2396] of the current frame or document. 
     * If no path attribute is specified, then the path attribute for the new value 
     * defaults to the absolute path portion of the URI [IETF RFC 2396] 
     * of the current frame or document. If no max-age attribute is specified, 
     * then the max-age attribute for the new value defaults to a user agent 
     * defined value. If a cookie with the specified name is 
     * already associated with the current frame or document, then the new value 
     * as well as the new attributes replace the old value and attributes. 
     * If a max-age attribute of 0 is specified for the new value, 
     * then any existing cookies of the specified name are removed from 
     * the cookie storage. 
     * @param cookie
     */
	@DOMSupport(DomLevel.ONE)
	@Property void setCookie(String cookie);
	
	/**
	 * Returns a Location object, which contains information about the URL of
	 * the document. 
	 * @return Location object
	 */
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.IE_6P})
	@Property Location getLocation();
	
	/**
	 * Set a new URL to load
	 * @param location URL string
	 */
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.IE_6P})
	@Property void setLocation(String url);
	
	// Functions
	
	/**
	 * Returns the element in the document at the given index.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@OverLoadFunc Node all(int index);
	
	/**
	 * Returns all elements in the document with matching id or matching name
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@JsArray(Node.class)
	@FactoryFunc
	@OverLoadFunc NodeList all(String id);
	
	/**
	 * Returns the element in the document with matching id or matching name
	 * given the subIndex.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@OverLoadFunc Node all(String id, int subIndex);

    /**
     * Open a document stream for writing. 
     * If a document exists in the target, this method clears it. 
     */
	@DOMSupport(DomLevel.ONE)
	@Function void open();

    /**
     * Closes a document stream opened by open() and forces rendering.
     */
	@DOMSupport(DomLevel.ONE)
	@Function void close();

    /**
     * Write a string of text to a document stream opened by open(). 
     * Note that the function will produce a document which is not necessarily
     * driven by a DTD and therefore might be produce an invalid result in the 
     * context of the document. 
     * @param text
     * 
     */
	@DOMSupport(DomLevel.ONE)
	@Function void write(Object text);

    /**
     * Write a string of text followed by a newline character to a document 
     * stream opened by open(). Note that the function will produce a document 
     * which is not necessarily driven by a DTD and therefore might be produce 
     * an invalid result in the context of the document 
     * @param text
     */
	@DOMSupport(DomLevel.ONE)
	@Function void writeln(Object text);

    /**
     * With [HTML 4.01] documents, this method returns the (possibly empty) 
     * collection of elements whose name value is given by elementName. 
     * In [XHTML 1.0] documents, this methods only return the (possibly empty) collection 
     * of form controls with matching name. 
     * This method is case sensitive. 
     * @param elementName The name attribute value for an element.
     * @return <code>NodeList</code> The matching elements.
     */
	@DOMSupport(DomLevel.ONE)
	@JsArray(HTMLElement.class)
	@FactoryFunc
	@Function NodeList getElementsByName(String elementName);
	
	@BrowserSupport({BrowserType.NONE})
	@JstExclude
	@ARename(name = "getElementsByName")
	@Function NodeList byName(String elementName) ;
	
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.IE_6P})
	@OverLoadFunc boolean execCommand(String command);
	
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.IE_6P})
	@OverLoadFunc boolean execCommand(String command,boolean userInterface);
	
	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.IE_6P})
	@OverLoadFunc boolean execCommand(String command,boolean userInterface, Object value);

	@DOMSupport(DomLevel.ONE)
	@BrowserSupport({BrowserType.IE_6P})
	@Property Selection getSelection();
	
	@BrowserSupport({BrowserType.IE_6P})
	@Property Window getParentWindow();
	
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P, BrowserType.SAFARI_3P})
	@Property Window getDefaultView();
	
	@JsArray(CssStyleSheet.class)
	@Property StyleSheetList getStyleSheets();
	
	
	/**
     * Only for Rhino support
     * @param type
     * @return
     */
    @Function Object valueOf(String type);
    
	/**
	 * The get the unencoded url 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getURLUnencoded();
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_9P})
	@FactoryFunc
	@Function void addEventListener(
			String type, 
			Object listener, 
			boolean useCapture);

	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@FactoryFunc
	@Function void attachEvent(
			String type, 
			Object listener);
	
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getReadyState();
	
	

	
	/**
	 * Returns the onkeydown event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onkeydown.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeydown")
	Object getOnKeyDown();
	
	/**
	 * Sets the onkeydown event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onkeydown.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeydown")
	void setOnKeyDown(Object functionRef);
	
	/**
	 * Returns the onkeypress event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onkeypress.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeypress")
	Object getOnKeyPress();
	
	/**
	 * Sets the onkeypress event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onkeypress.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeypress")
	void setOnKeyPress(Object functionRef);
	
	/**
	 * Returns the onkeyup event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onkeyup.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeyup")
	Object getOnKeyUp();
	
	/**
	 * Sets the onkeyup event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onkeyup.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onkeyup")
	void setOnKeyUp(Object functionRef);
	
	/**
	 * Returns the onclick event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onclick")
	Object getOnClick();
	
	/**
	 * Sets the onclick event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onclick")
	void setOnClick(Object functionRef);
	
	/**
	 * Returns the ondblclick event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_ondblclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="ondblclick")
	Object getOnDblClick();
	
	/**
	 * Sets the ondblclick event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_ondblclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="ondblclick")
	void setOnDblClick(Object functionRef);
	
	/**
	 * Returns the onmousedown event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmousedown.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousedown")
	Object getOnMouseDown();
	
	/**
	 * Sets the onmousedown event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmousedown.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousedown")
	void setOnMouseDown(Object functionRef);
	
	/**
	 * Returns the onmousemove event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmousemove.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousemove")
	Object getOnMouseMove();
	
	/**
	 * Sets the onmousemove event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmousemove.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmousemove")
	void setOnMouseMove(Object functionRef);
	
	/**
	 * Returns the onmouseout event handler code on the current element.
	 * @see http://www.w3schools.com/jsref/jsref_onmouseout.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseout")
	Object getOnMouseOut();
	
	/**
	 * Sets the onmouseout event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmouseout.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseout")
	void setOnMouseOut(Object functionRef);
	
	/**
	 * Returns the onmouseover event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmouseover.asp 
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseover")
	Object getOnMouseOver();
	
	/**
	 * Sets the onmouseover event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmouseover.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseover")
	void setOnMouseOver(Object functionRef);
	
	/**
	 * Returns the onmouseup event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onmouseup.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseup")
	Object getOnMouseUp();
	
	/**
	 * Sets the onmouseup event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onmouseup.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@Property(name="onmouseup")
	void setOnMouseUp(Object functionRef);

	/**
	 * Returns the onclick event handler code on the current element. 
	 * @see http://www.w3schools.com/jsref/jsref_onclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@BrowserSupport({BrowserType.IE_6P})
	@Property(name="onreadystatechange")
	Object getOnReadyStateChange();
	
	/**
	 * Sets the onclick event handler code on the current element. 
	 * @param functionRef
	 * @see http://www.w3schools.com/jsref/jsref_onclick.asp
	 */
	@DOMSupport(DomLevel.ZERO)
	@BrowserSupport({BrowserType.IE_6P})
	@Property(name="onreadystatechange")
	void setOnReadyStateChange(Object functionRef);
	
	/**
	 * Indicates whether the document is rendered in Quirks mode or Strict mode.
	 * @return string containing "BackCompat" for Quirks mode or 
	 * "CSS1Compat" for Strict mode.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property String getCompatMode();
	
	/**
	 * Creates a style sheet for the document. 
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@OverLoadFunc void createStyleSheet();
	
	/**
	 * Creates a style sheet for the document. 
	 * @param url A String that specifies how to add the style sheet to the document. 
	 * If a file name is specified for the URL, the style information is added
	 * as a link object. If the URL contains style information, 
	 * it is added to the style object.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@OverLoadFunc void createStyleSheet(String url);
	
	/**
	 * Creates a style sheet for the document. 
	 * @param url A String that specifies how to add the style sheet to the document. 
	 * If a file name is specified for the URL, the style information is added
	 * as a link object. If the URL contains style information, 
	 * it is added to the style object.
	 * @param index Integer that specifies the index that indicates where the new style sheet
	 * is inserted in the styleSheets collection. 
	 * The default is to insert the new style sheet at the end of 
	 * the collection.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@OverLoadFunc void createStyleSheet(String url, int index);
	
	@ProxyFunc("createStyleSheet") void __createStyleSheet(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5);
	
	@DOMSupport(DomLevel.TWO)
	@Function Range createRange();
	
	/**
	 * Creates an element of the type specified. Note that the instance 
	 * returned implements the <code>HtmlElement</code> interface, so attributes 
	 * can be specified directly on the returned object.
	 * <br>In addition, if there are known attributes with default values, 
	 * <code>Attr</code> nodes representing them are automatically created 
	 * and attached to the element.
	 * <br>To create an element with a qualified name and namespace URI, use 
	 * the <code>createElementNS</code> method.
	 * @param tagName The name of the element type to instantiate. For XML, 
	 *   this is case-sensitive, otherwise it depends on the 
	 *   case-sensitivity of the markup language in use. In that case, the 
	 *   name is mapped to the canonical form of that markup by the DOM 
	 *   implementation.
	 * @return A new <code>HtmlElement</code> object with the 
	 *   <code>nodeName</code> attribute set to <code>tagName</code>, and 
	 *   <code>localName</code>, <code>prefix</code>, and 
	 *   <code>namespaceURI</code> set to <code>null</code>.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
	 *   name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 */
	@FactoryFunc
	@OverrideFunc HtmlElement createElement(String tagName);
	
	

	/**
	 * Returns the <code>HtmlElement</code> that has an ID attribute with the 
	 * given value. If no such element exists, this returns <code>null</code>
	 * . If more than one element has an ID attribute with that value, what 
	 * is returned is undefined. 
	 * <br> The DOM implementation is expected to use the attribute 
	 * <code>Attr.isId</code> to determine if an attribute is of type ID. 
	 * <p ><b>Note:</b> Attributes with the name "ID" or "id" are not of type 
	 * ID unless so defined.
	 * @param elementId The unique <code>id</code> value for an element.
	 * @return The matching element or <code>null</code> if there is none.
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.THREE)
	@OverrideFunc HtmlElement getElementById(String elementId);
    
	@BrowserSupport({BrowserType.NONE})
	@JstExclude
    @ARename(name = "getElementById")
    @OverrideFunc HtmlElement byId(String elementId);
    
	/**
	 * This is a convenience attribute that allows direct access to the child 
	 * node that is the document element of the document.
	 */
    @OverrideProp HtmlElement getDocumentElement();

}
