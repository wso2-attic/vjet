/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.AExclude;
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
import org.ebayopensource.dsf.jsnative.anno.JstMultiReturn;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.ProxyFunc;

@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface Element extends Node {
	
	/**
	 * The name of the element. If <code>Node.localName</code> is different 
	 * from <code>null</code>, this attribute is a qualified name. For 
	 * example, in: 
	 * <pre> &lt;elementExample id="demo"&gt; ... 
	 * &lt;/elementExample&gt; , </pre>
	 *  <code>tagName</code> has the value 
	 * <code>"elementExample"</code>. Note that this is case-preserving in 
	 * XML, as are all of the operations of the DOM. The HTML DOM returns 
	 * the <code>tagName</code> of an HTML element in the canonical 
	 * uppercase form, regardless of the case in the source HTML document.
	 */
	@Property String getTagName();
	
	/**
	 *  The type information associated with this element. 
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Property TypeInfo getSchemaTypeInfo();

	@ProxyFunc("getAttribute")Object __getAttribute(Object sAttrName, Object iFlags,Object notreq1,Object notreq2, Object notreq3); 
	
	/**
	 * Retrieves an attribute value by name.
	 * @param name The name of the attribute to retrieve.
	 * @return The <code>Attr</code> value as a string, or the empty string 
	 *   if that attribute does not have a specified or default value.
	 */

	@JstMultiReturn({String.class, int.class, boolean.class})
	@OverLoadFunc String getAttribute(String sAttrName);


	@JstMultiReturn({String.class, int.class, boolean.class})
	@OverLoadFunc Object getAttribute(String sAttrName, int iFlags);



	/**
	 * Adds a new attribute. If an attribute with that name is already present 
	 * in the element, its value is changed to be that of the value 
	 * parameter. This value is a simple string; it is not parsed as it is 
	 * being set. So any markup (such as syntax to be recognized as an 
	 * entity reference) is treated as literal text, and needs to be 
	 * appropriately escaped by the implementation when it is written out. 
	 * In order to assign an attribute value that contains entity 
	 * references, the user must create an <code>Attr</code> node plus any 
	 * <code>Text</code> and <code>EntityReference</code> nodes, build the 
	 * appropriate subtree, and use <code>setAttributeNode</code> to assign 
	 * it as the value of an attribute.
	 * <br>To set an attribute with a qualified name and namespace URI, use 
	 * the <code>setAttributeNS</code> method.
	 * @param name The name of the attribute to create or alter.
	 * @param value Value to set in string form.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
	 *   name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   
	 *   The W3C spec mentions this is the correct api:
	 *   @Function void setAttribute(String name, String value);
	 *   
	 *   The reality in JavaScript is that value is not always correct
	 *   the value that is passed in can be an object where the object
	 *   is converted to a string via the Object.toString method internally.
	 *   
	 */
	
	@ProxyFunc("setAttribute")void __setAttribute(Object name, Object value,Object notreq1,Object notreq2, Object notreq3);
	 
	@OverLoadFunc void setAttribute(String name, String value);
	@OverLoadFunc void setAttribute(String name, Object value);

	/**
	 * Removes an attribute by name. If a default value for the removed 
	 * attribute is defined in the DTD, a new attribute immediately appears 
	 * with the default value as well as the corresponding namespace URI, 
	 * local name, and prefix when applicable. The implementation may handle 
	 * default values from other schemas similarly but applications should 
	 * use <code>Document.normalizeDocument()</code> to guarantee this 
	 * information is up-to-date.
	 * <br>If no attribute with this name is found, this method has no effect.
	 * <br>To remove an attribute by local name and namespace URI, use the 
	 * <code>removeAttributeNS</code> method.
	 * @param name The name of the attribute to remove.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 */
	@Function void removeAttribute(String name);
    
	/**
	 * Returns a <code>NodeList</code> of all descendant <code>Elements</code> 
	 * with a given tag name, in document order.
	 * @param name The name of the tag to match on. The special value "*" 
	 *   matches all tags.
	 * @return A list of matching <code>Element</code> nodes.
	 */
	@FactoryFunc
	@JsArray(Element.class)
	@Function NodeList getElementsByTagName(String name);
	
    @BrowserSupport({BrowserType.NONE})
	@ARename(name = "getElementsByTagName")
	@JstExclude
	@Function NodeList byTag(String name);

    
    
	/**
	 * Retrieves an attribute node by name.
	 * @param name
	 * @return Attr 
	 */
	@Function Attr getAttributeNode(String name);

	/**
	 * Adds a new attribute node. 
	 * @param newAttr Attr
	 * @return Attr If the newAttr attribute replaces an existing attribute, 
	 * the replaced Attr node is returned, otherwise null is returned.
	 */
	@Function Attr setAttributeNode(Attr newAttr);

	/**
	 * Removes the specified attribute node
	 * @param oldAttr Attr
	 * @return The Attr node that was removed.
	 */
	@Function Attr removeAttributeNode(Attr oldAttr);
    
	/**
	 * Returns <code>true</code> when an attribute with a given name is 
	 * specified on this element or has a default value, <code>false</code> 
	 * otherwise.
	 * @param name The name of the attribute to look for.
	 * @return <code>true</code> if an attribute with the given name is 
	 *   specified on this element or has a default value, <code>false</code>
	 *    otherwise.
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Function boolean hasAttribute(String name);

	/**
	 * Retrieves an attribute value by local name and namespace URI.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value <code>null</code> as the 
	 * <code>namespaceURI</code> parameter for methods if they wish to have 
	 * no namespace.
	 * @param namespaceURI The namespace URI of the attribute to retrieve.
	 * @param localName The local name of the attribute to retrieve.
	 * @return The <code>Attr</code> value as a string, or the empty string 
	 *   if that attribute does not have a specified or default value.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature <code>"XML"</code> and the language exposed 
	 *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO) 
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Function String getAttributeNS(String namespaceURI, String localName);

	/**
	 * Adds a new attribute. If an attribute with the same local name and 
	 * namespace URI is already present on the element, its prefix is 
	 * changed to be the prefix part of the <code>qualifiedName</code>, and 
	 * its value is changed to be the <code>value</code> parameter. This 
	 * value is a simple string; it is not parsed as it is being set. So any 
	 * markup (such as syntax to be recognized as an entity reference) is 
	 * treated as literal text, and needs to be appropriately escaped by the 
	 * implementation when it is written out. In order to assign an 
	 * attribute value that contains entity references, the user must create 
	 * an <code>Attr</code> node plus any <code>Text</code> and 
	 * <code>EntityReference</code> nodes, build the appropriate subtree, 
	 * and use <code>setAttributeNodeNS</code> or 
	 * <code>setAttributeNode</code> to assign it as the value of an 
	 * attribute.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value <code>null</code> as the 
	 * <code>namespaceURI</code> parameter for methods if they wish to have 
	 * no namespace.
	 * @param namespaceURI The namespace URI of the attribute to create or 
	 *   alter.
	 * @param qualifiedName The qualified name of the attribute to create or 
	 *   alter.
	 * @param value The value to set in string form.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified qualified name is not 
	 *   an XML name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is 
	 *   malformed per the Namespaces in XML specification, if the 
	 *   <code>qualifiedName</code> has a prefix and the 
	 *   <code>namespaceURI</code> is <code>null</code>, if the 
	 *   <code>qualifiedName</code> has a prefix that is "xml" and the 
	 *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
	 *   http://www.w3.org/XML/1998/namespace</a>", if the <code>qualifiedName</code> or its prefix is "xmlns" and the 
	 *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if the <code>namespaceURI</code> is "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>" and neither the <code>qualifiedName</code> nor its prefix is "xmlns".
	 *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature <code>"XML"</code> and the language exposed 
	 *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Function void setAttributeNS(String namespaceURI, String qualifiedName, String value);

	/**
	 * Removes an attribute by local name and namespace URI. If a default 
	 * value for the removed attribute is defined in the DTD, a new 
	 * attribute immediately appears with the default value as well as the 
	 * corresponding namespace URI, local name, and prefix when applicable. 
	 * The implementation may handle default values from other schemas 
	 * similarly but applications should use 
	 * <code>Document.normalizeDocument()</code> to guarantee this 
	 * information is up-to-date.
	 * <br>If no attribute with this local name and namespace URI is found, 
	 * this method has no effect.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value <code>null</code> as the 
	 * <code>namespaceURI</code> parameter for methods if they wish to have 
	 * no namespace.
	 * @param namespaceURI The namespace URI of the attribute to remove.
	 * @param localName The local name of the attribute to remove.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature <code>"XML"</code> and the language exposed 
	 *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.THREE)
	@Function void removeAttributeNS(String namespaceURI, String localName);

	/**
	 * Retrieves an <code>Attr</code> node by local name and namespace URI.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value <code>null</code> as the 
	 * <code>namespaceURI</code> parameter for methods if they wish to have 
	 * no namespace.
	 * @param namespaceURI The namespace URI of the attribute to retrieve.
	 * @param localName The local name of the attribute to retrieve.
	 * @return The <code>Attr</code> node with the specified attribute local 
	 *   name and namespace URI or <code>null</code> if there is no such 
	 *   attribute.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature <code>"XML"</code> and the language exposed 
	 *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO) 
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Function Attr getAttributeNodeNS(String namespaceURI, String localName);

	/**
	 * Adds a new attribute. If an attribute with that local name and that 
	 * namespace URI is already present in the element, it is replaced by 
	 * the new one. Replacing an attribute node by itself has no effect.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value <code>null</code> as the 
	 * <code>namespaceURI</code> parameter for methods if they wish to have 
	 * no namespace.
	 * @param newAttr The <code>Attr</code> node to add to the attribute list.
	 * @return If the <code>newAttr</code> attribute replaces an existing 
	 *   attribute with the same local name and namespace URI, the replaced 
	 *   <code>Attr</code> node is returned, otherwise <code>null</code> is 
	 *   returned.
	 * @exception DOMException
	 *   WRONG_DOCUMENT_ERR: Raised if <code>newAttr</code> was created from a 
	 *   different document than the one that created the element.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>INUSE_ATTRIBUTE_ERR: Raised if <code>newAttr</code> is already an 
	 *   attribute of another <code>Element</code> object. The DOM user must 
	 *   explicitly clone <code>Attr</code> nodes to re-use them in other 
	 *   elements.
	 *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature <code>"XML"</code> and the language exposed 
	 *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO)
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Function Attr setAttributeNodeNS(Attr newAttr);

	/**
	 * Returns a <code>NodeList</code> of all the descendant 
	 * <code>Elements</code> with a given local name and namespace URI in 
	 * document order.
	 * @param namespaceURI The namespace URI of the elements to match on. The 
	 *   special value "*" matches all namespaces.
	 * @param localName The local name of the elements to match on. The 
	 *   special value "*" matches all local names.
	 * @return A new <code>NodeList</code> object containing all the matched 
	 *   <code>Elements</code>.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature <code>"XML"</code> and the language exposed 
	 *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO) 
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@JsArray(Element.class)
	@FactoryFunc
	@Function NodeList getElementsByTagNameNS(String namespaceURI, String localName);

    @BrowserSupport({BrowserType.NONE})
	@ARename(name = "getElementsByTagNameNS")
	@AExclude
	@JstExclude
	@Function NodeList byTagNS(String namespaceURI, String localName);
	/**
	 * Returns <code>true</code> when an attribute with a given local name and 
	 * namespace URI is specified on this element or has a default value, 
	 * <code>false</code> otherwise.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value <code>null</code> as the 
	 * <code>namespaceURI</code> parameter for methods if they wish to have 
	 * no namespace.
	 * @param namespaceURI The namespace URI of the attribute to look for.
	 * @param localName The local name of the attribute to look for.
	 * @return <code>true</code> if an attribute with the given local name 
	 *   and namespace URI is specified or has a default value on this 
	 *   element, <code>false</code> otherwise.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: May be raised if the implementation does not 
	 *   support the feature <code>"XML"</code> and the language exposed 
	 *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]). 
	 * @since DOM Level 2
	 */
	@DOMSupport(DomLevel.TWO) 
	@BrowserSupport({BrowserType.FIREFOX_2P, BrowserType.OPERA_9P, BrowserType.SAFARI_3P})
	@Function boolean hasAttributeNS(String namespaceURI, String localName);
	
	/**
	 *  If the parameter <code>isId</code> is <code>true</code>, this method 
	 * declares the specified attribute to be a user-determined ID attribute
	 * . This affects the value of <code>Attr.isId</code> and the behavior 
	 * of <code>Document.getElementById</code>, but does not change any 
	 * schema that may be in use, in particular this does not affect the 
	 * <code>Attr.schemaTypeInfo</code> of the specified <code>Attr</code> 
	 * node. Use the value <code>false</code> for the parameter 
	 * <code>isId</code> to undeclare an attribute for being a 
	 * user-determined ID attribute. 
	 * <br> To specify an attribute by local name and namespace URI, use the 
	 * <code>setIdAttributeNS</code> method. 
	 * @param name The name of the attribute.
	 * @param isId Whether the attribute is a of type ID.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>NOT_FOUND_ERR: Raised if the specified node is not an attribute 
	 *   of this element.
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Function void setIdAttribute(String name, boolean isId);

	/**
	 *  If the parameter <code>isId</code> is <code>true</code>, this method 
	 * declares the specified attribute to be a user-determined ID attribute
	 * . This affects the value of <code>Attr.isId</code> and the behavior 
	 * of <code>Document.getElementById</code>, but does not change any 
	 * schema that may be in use, in particular this does not affect the 
	 * <code>Attr.schemaTypeInfo</code> of the specified <code>Attr</code> 
	 * node. Use the value <code>false</code> for the parameter 
	 * <code>isId</code> to undeclare an attribute for being a 
	 * user-determined ID attribute. 
	 * @param namespaceURI The namespace URI of the attribute.
	 * @param localName The local name of the attribute.
	 * @param isId Whether the attribute is a of type ID.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>NOT_FOUND_ERR: Raised if the specified node is not an attribute 
	 *   of this element.
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Function void setIdAttributeNS(String namespaceURI, String localName, boolean isId);

	/**
	 *  If the parameter <code>isId</code> is <code>true</code>, this method 
	 * declares the specified attribute to be a user-determined ID attribute
	 * . This affects the value of <code>Attr.isId</code> and the behavior 
	 * of <code>Document.getElementById</code>, but does not change any 
	 * schema that may be in use, in particular this does not affect the 
	 * <code>Attr.schemaTypeInfo</code> of the specified <code>Attr</code> 
	 * node. Use the value <code>false</code> for the parameter 
	 * <code>isId</code> to undeclare an attribute for being a 
	 * user-determined ID attribute. 
	 * @param idAttr The attribute node.
	 * @param isId Whether the attribute is a of type ID.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>NOT_FOUND_ERR: Raised if the specified node is not an attribute 
	 *   of this element.
	 * @since DOM Level 3
	 */
	@DOMSupport(DomLevel.THREE) @BrowserSupport({BrowserType.UNDEFINED})
	@Function void setIdAttributeNode(Attr idAttr, boolean isId);
}
