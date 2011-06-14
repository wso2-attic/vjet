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
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.FactoryFunc;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsArray;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * The Document interface represents the entire HTML or XML document. 
 * Conceptually, it is the root of the document tree, and provides the primary access to the document's data.
 * <br>
 * See http://www.w3.org/TR/DOM-Level-2-Core/core.html#i-Document
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface Document extends Node {
	

	/**
	 * This is a convenience attribute that allows direct access to the child 
	 * node that is the document element of the document.
	 */
	@Property Element getDocumentElement();
	
	/**
	 *  An attribute specifying the encoding used for this document at the time of the parsing. 
	 *  This is null when it is not known, such as when the Document was created in memory.
	 * @return
	 * @since DOM Level 3
	 */
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@DOMSupport(DomLevel.THREE)
	@Property String getInputEncoding();

	/**
	 * An attribute specifying, as part of the XML declaration, 
	 * the encoding of this document. This is null when unspecified or when it is not known, 
	 * such as when the Document was created in memory.
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@Property String getXmlEncoding();

	/**
	 * An attribute specifying, as part of the XML declaration, whether this document is standalone. 
	 * This is false when unspecified.
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@Property boolean getXmlStandalone();
	
	/**
	 * An attribute specifying, as part of the XML declaration, whether this document is standalone.
	 * @param xmlStandalone
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@Property void setXmlStandalone(boolean xmlStandalone);

	/**
	 * An attribute specifying, as part of the XML declaration, the version number of this document. 
	 * If there is no declaration and if this document supports the "XML" feature, the value is "1.0". 
	 * If this document does not support the "XML" feature, the value is always null. 
	 * Changing this attribute will affect methods that check for invalid characters in XML names. 
	 * Application should invoke Document.normalizeDocument() in order to check for invalid characters 
	 * in the Nodes that are already part of this Document. 
	 * DOM applications may use the DOMImplementation.hasFeature(feature, version) method with 
	 * parameter values "XMLVersion" and "1.0" (respectively) to determine if an implementation 
	 * supports [XML 1.0]. DOM applications may use the same method with parameter values "XMLVersion" 
	 * and "1.1" (respectively) to determine if an implementation supports [XML 1.1]. 
	 * In both cases, in order to support XML, an implementation must also support the "XML" feature 
	 * defined in this specification. Document objects supporting a version of the "XMLVersion" feature 
	 * must not raise a NOT_SUPPORTED_ERR exception for the same version number when using 
	 * Document.xmlVersion. 
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@Property String getXmlVersion();
	
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@Property void setXmlVersion(String xmlVersion);

	/**
	 * An attribute specifying whether error checking is enforced or not. 
	 * When set to false, the implementation is free to not test every possible error case normally 
	 * defined on DOM operations, and not raise any DOMException on DOM operations or report errors 
	 * while using Document.normalizeDocument(). In case of error, the behavior is undefined. 
	 * This attribute is true by default.
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@Property boolean getStrictErrorChecking();
	
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@Property void setStrictErrorChecking(boolean strictErrorChecking);

	/**
	 * The location of the document or null if undefined or if the Document was created using 
	 * DOMImplementation.createDocument. No lexical checking is performed when setting this attribute; 
	 * this could result in a null value returned when using Node.baseURI. 
	 * Beware that when the Document supports the feature "HTML" [DOM Level 2 HTML], 
	 * the href attribute of the HTML BASE element takes precedence over this attribute 
	 * when computing Node.baseURI. 
	 * @return
	 * @since DOM Level 3
	 */
	@Property String getDocumentURI();
	@DOMSupport( DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P})
	@Property void setDocumentURI(String documentURI);
    
	/**
	 * The configuration used when Document.normalizeDocument() is invoked. 
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport(BrowserType.UNDEFINED)
	@Property DOMConfiguration getDomConfig();
	
	   /**
     * The Document Type Declaration (see DocumentType) associated with this document. 
     * For XML documents without a document type declaration this returns null. For HTML documents, 
     * a DocumentType object may be returned, independently of the presence or absence of document type 
     * declaration in the HTML document. This provides direct access to the DocumentType node, 
     * child node of this Document. This node can be set at document creation time and later changed 
     * through the use of child nodes manipulation methods, such as Node.insertBefore, 
     * or Node.replaceChild. Note, however, that while some implementations may instantiate 
     * different types of Document objects supporting additional features than the "Core", 
     * such as "HTML" [DOM Level 2 HTML], based on the DocumentType specified at creation time, 
     * changing it afterwards is very unlikely to result in a change of the features supported.
     * @return
     * @since DOM Level 3
     */
    @DOMSupport(DomLevel.THREE)
    @Property DocumentType getDoctype();
    
	/**
	 * The DOMImplementation object that handles this document. 
	 * A DOM application may use objects from multiple implementations.
	 */
    @Property DOMImplementation getImplementation();

	/**
	 * Creates an element of the type specified. Note that the instance 
	 * returned implements the <code>Element</code> interface, so attributes 
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
	 * @return A new <code>Element</code> object with the 
	 *   <code>nodeName</code> attribute set to <code>tagName</code>, and 
	 *   <code>localName</code>, <code>prefix</code>, and 
	 *   <code>namespaceURI</code> set to <code>null</code>.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
	 *   name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 */
    @FactoryFunc
	@Function Element createElement(String tagName);

	/**
	 * Creates a <code>Text</code> node given the specified string.
	 * @param data The data for the node.
	 * @return The new <code>Text</code> object.
	 */
	@Function Text createTextNode(String data);

	/**
	 * Returns a <code>NodeList</code> of all the <code>Elements</code> in 
	 * document order with a given tag name and are contained in the 
	 * document.
	 * @param tagname  The name of the tag to match on. The special value "*" 
	 *   matches all tags. For XML, the <code>tagname</code> parameter is 
	 *   case-sensitive, otherwise it depends on the case-sensitivity of the 
	 *   markup language in use. 
	 * @return A new <code>NodeList</code> object containing all the matched 
	 *   <code>Elements</code>.
	 */
	@FactoryFunc
    @JsArray(Node.class)
	@Function NodeList getElementsByTagName(String tagname);
	
    @BrowserSupport({BrowserType.NONE})
    @ARename(name = "getElementsByTagName")
    @JstExclude
    @Function NodeList byTag(String tagName);

	/**
	 * Imports a node from another document to this document, without altering 
	 * or removing the source node from the original document; this method 
	 * creates a new copy of the source node. The returned node has no 
	 * parent; (<code>parentNode</code> is <code>null</code>).
	 */
    @DOMSupport(DomLevel.TWO)
    @BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P})
    @Function Node importNode(Node importedNode, boolean deep);

	/**
	 * Returns the <code>Element</code> that has an ID attribute with the 
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
    @DOMSupport(DomLevel.TWO)
    @Function Element getElementById(String elementId);
    
    @BrowserSupport({BrowserType.NONE})
    @ARename(name = "getElementById")
    @JstExclude
    @Function Element byId(String elementId);

    
	/**
	 * Creates an Attr of the given name. Note that the Attr instance can then be set on an Element 
	 * using the setAttributeNode method. To create an attribute with a qualified name and namespace URI, 
	 * use the createAttributeNS method.
	 * @param name
	 * @return
	 */
    @Function Attr createAttribute(String name);
    
	/**
	 * Creates an element of the given qualified name and namespace URI. Per [XML Namespaces], 
	 * applications must use the value null as the namespaceURI parameter for methods 
	 * if they wish to have no namespace.
	 * @param namespaceURI
	 * @param qualifiedName
	 * @return
	 * @since DOM Level 2
	 */
    @DOMSupport(DomLevel.TWO)
    @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P})
    @Function Element createElementNS(String namespaceURI, String qualifiedName);
    
	/**
	 * Creates an attribute of the given qualified name and namespace URI. Per [XML Namespaces], 
	 * applications must use the value null as the namespaceURI parameter for methods 
	 * if they wish to have no namespace.
	 * @param namespaceURI
	 * @param qualifiedName
	 * @return
	 * @since DOM Level 2
	 */
    @DOMSupport(DomLevel.TWO)
    @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P})
	@Function Attr createAttributeNS(String namespaceURI, String qualifiedName);

	/**
	 * Returns a NodeList of all the Elements with a given local name and namespace URI in document order.
	 * @param namespaceURI
	 * @param localName
	 * @return
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO)
    @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.IE_9P})
    @JsArray(Node.class)
    @FactoryFunc
	@Function NodeList getElementsByTagNameNS(String namespaceURI, String localName);
    
    @BrowserSupport({BrowserType.NONE})
    @ARename(name = "getElementsByTagNameNS")
    @JstExclude
    @Function NodeList byTagNS(String namespaceURI, String localName);
    
	/**
	 * Creates an EntityReference object. In addition, if the referenced entity is known, 
	 * the child list of the EntityReference node is made the same as that of the corresponding Entity node.
	 * @param name
	 * @return
	 */
	@DOMSupport(DomLevel.ONE)
    @BrowserSupport({BrowserType.FIREFOX_2P})
	@Function EntityReference createEntityReference(String name);
    
	/**
	 * Creates a Comment node given the specified string.
	 * @param data
	 * @return
	 */
	@Function Comment createComment(String data);

	/**
	 * Creates a CDATASection node whose value is the specified string.
	 * @param data
	 * @return
	 */
	@DOMSupport(DomLevel.ONE)
    @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P})
	@Function CDATASection createCDATASection(String data);

	/**
	 * Creates a ProcessingInstruction node given the specified name and data strings.
	 * @param target
	 * @param data
	 * @return
	 */

	@DOMSupport(DomLevel.ONE)
    @BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P})
	@Function ProcessingInstruction createProcessingInstruction(String target, String data);
    
	/**
	 * Creates an empty DocumentFragment object. 
	 * @return
	 */
	@Function DocumentFragment createDocumentFragment();

	/**
	 * Attempts to adopt a node from another document to this document. 
	 * If supported, it changes the ownerDocument of the source node, its children, 
	 * as well as the attached attribute nodes if there are any. 
	 * If the source node has a parent it is first removed from the child list of its parent. 
	 * This effectively allows moving a subtree from one document to another (unlike importNode() 
	 * which create a copy of the source node instead of moving it). 
	 * When it fails, applications should use Document.importNode() instead. 
	 * Note that if the adopted node is already part of this document (i.e. the source and 
	 * target document are the same), this method still has the effect of removing the source node 
	 * from the child list of its parent, if any. 
	 * The following list describes the specifics for each type of node. 
	 * @param source
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P})
	@Function Node adoptNode(Node source);

	/**
	 * This method acts as if the document was going through a save and load cycle, 
	 * putting the document in a "normal" form. As a consequence, this method updates the 
	 * replacement tree of EntityReference nodes and normalizes Text nodes, as defined in 
	 * the method Node.normalize(). Otherwise, the actual result depends on the features being set 
	 * on the Document.domConfig object and governing what operations actually take place. 
	 * Noticeably this method could also make the document namespace well-formed according to 
	 * the algorithm described in Namespace Normalization, check the character normalization, 
	 * remove the CDATASection nodes, etc. See DOMConfiguration for details. 
	 * @since DOM Level 3
	 *
	 */
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@DOMSupport(DomLevel.THREE)
	@Function void normalizeDocument();

	/**
	 * Rename an existing node of type ELEMENT_NODE or ATTRIBUTE_NODE. When possible this simply changes 
	 * the name of the given node, otherwise this creates a new node with the specified name 
	 * and replaces the existing node with the new node as described below. If simply changing the name of
	 * the given node is not possible, the following operations are performed: a new node is created, 
	 * any registered event listener is registered on the new node, any user data attached to the old node 
	 * is removed from that node, the old node is removed from its parent if it has one, the children 
	 * are moved to the new node, if the renamed node is an Element its attributes are moved to the 
	 * new node, the new node is inserted at the position the old node used to have in its parent's 
	 * child nodes list if it has one, the user data that was attached to the old node is attached to 
	 * the new node.
	 * When the node being renamed is an Element only the specified attributes are moved, 
	 * default attributes originated from the DTD are updated according to the new element name. 
	 * In addition, the implementation may update default attributes from other schemas. 
	 * Applications should use Document.normalizeDocument() to guarantee these attributes are up-to-date. 
	 * When the node being renamed is an Attr that is attached to an Element, the node is first removed 
	 * from the Element attributes map. Then, once renamed, either by modifying the existing node or 
	 * creating a new one as described above, it is put back. In addition,
	 * <ul>
	 * <li>a user data event NODE_RENAMED is fired,</li>
	 * <li>when the implementation supports the feature "MutationNameEvents", 
	 * each mutation operation involved in this method fires the appropriate event, 
	 * and in the end the event {http://www.w3.org/2001/xml-events, DOMElementNameChanged} or 
	 * {http://www.w3.org/2001/xml-events, DOMAttributeNameChanged} is fired.</li>
	 * </ul>
	 * @param n
	 * @param namespaceURI
	 * @param qualifiedName
	 * @return
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE)
	@BrowserSupport({BrowserType.FIREFOX_2P})
	@Function Node renameNode(Node n, 
                           String namespaceURI, 
                           String qualifiedName);

	/**
	 * The get the last modified date & time of the document. 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getLastModified();

	/**
	 * The get the document background color 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getBgColor();

	/**
	 * The get the active link color 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getAlinkColor();

	/**
	 * The get the visited link color 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getVlinkColor();

	/**
	 * The get the  link color 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getLinkColor();

	/**
	 * The get the protocol 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getProtocol();

	/**
	 * The get the cookie 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getCookie();

	/**
	 * The get the document fg color 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getFgColor();

	/**
	 * The get the state of the document 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getReadyState();

	/**
	 * The get the security of the document 
	 * 
	 */
	@DOMSupport(DomLevel.ONE)
    @Property String getSecurity();
	
	@DOMSupport(DomLevel.ONE)
    @Property void setBgColor(String value);
	
	@DOMSupport(DomLevel.ONE)
    @Property void setFgColor(String value);
	
	@DOMSupport(DomLevel.ONE)
    @Property void setLinkColor(String value);
}
