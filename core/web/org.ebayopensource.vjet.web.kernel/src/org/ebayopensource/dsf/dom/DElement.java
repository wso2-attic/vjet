/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.beans.PropertyDescriptor;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

import org.ebayopensource.dsf.common.DsfVerifierConfig;
import org.ebayopensource.dsf.common.binding.IValueBinding;
import org.ebayopensource.dsf.common.binding.SimpleValueBinding;
import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.naming.IDsfNamingFamily;
import org.ebayopensource.dsf.common.node.IAttributeMap;
import org.ebayopensource.dsf.common.node.IDNodeRelationshipVerifier;
import org.ebayopensource.dsf.common.node.visitor.IDNodeHandlingStrategy;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.dom.support.DsfDomLevelNotSupportedException;
import org.ebayopensource.dsf.dom.support.DsfDomNotSupportedRuntimeException;
import org.ebayopensource.dsf.dom.support.NSNameVerifier;
import org.ebayopensource.dsf.dom.support.XmlVerifier;
import org.ebayopensource.dsf.common.Z;

public class DElement extends DNode implements Element {

	private static final long serialVersionUID = 1L;

// MrPperf - we need to make this lazy creation --
	protected AttributeMap m_attributes ;

	//
	// Constructor(s)
	//
	/** DOM Level 1: create an element using givne tagName
	 * @param tagName - The name of the element type to instantiate
	 * Create a new DElement object with the nodeName set to tagName, 
	 * and localName, prefix, and namespaceURI set to null. 
	 * @exception DOMException
	 */
	public DElement(final String unqualifiedName) {
		this((DDocument)null, unqualifiedName);		
	}
	public DElement(final String namespaceUri, final String possibleQualifiedName) {
		this(
			DomUtil.getNamespace(namespaceUri, possibleQualifiedName), 
			DomUtil.getUnqualifedName(possibleQualifiedName)
		) ;
	}

	public DElement(final DNamespace namespace, final String unqualifiedName) {
		this(null, namespace, unqualifiedName) ;
	}

	public DElement(final DDocument document, final String unqualifiedName) {
		super(document, unqualifiedName);		
		if (DsfVerifierConfig.getInstance().isVerifyNaming()) {
			if (XmlVerifier.isXMLName(unqualifiedName, isDsfXml11Version()) == false) {
				DErrUtil.elementCharError(unqualifiedName, null, null);
			}	
		}
	}	

	//
	// Satisfy Element
	//
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
	public String getTagName() {
		return m_nodeName ;
	}

	/**
	 * Retrieves an attribute value by enum.
	 * @param attrEnum The enum of the attribute to retrieve.
	 * @return The <code>Attr</code> value as a string, or the empty string 
	 *   if that attribute does not have a specified or default value.
	 * @see EHtmlAttr
	 */
	public String getAttribute(final Enum<?> attrEnum) {
		if (attrEnum == null) {
			chuck("Attr enum must not be null") ;
		}
		return getAttribute(attrEnum.name()) ;
	}
	
	/**
	 * Retrieves an attribute value by name.
	 * @param name The name of the attribute to retrieve.
	 * @return The <code>Attr</code> value as a string, or the empty string 
	 *   if that attribute does not have a specified or default value.
	 */
	public String getAttribute(final String name) {
		if (name == null) {
			chuck("Attribute name must not be null") ;
		}
// MrPperf -- don't create attributes unless we need them
		if (m_attributes == null) return "" ;
		
		final DAttr attr = m_attributes.getAttributeNode(name);
		if (attr == null) {
			return "";
		}
		return attr.getValue();
	}

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
	 */
	public DElement setDsfAttribute(
		final String name, final String value) throws DOMException
	{
		setAttribute(name, value) ;
		return this ;
	}
	public DElement setDsfAttribute(
		final String name, final boolean value) throws DOMException
	{
		setAttribute(name, Boolean.toString(value)) ;
		return this ;
	}
	public DElement setDsfAttribute(
		final String name, final int value) throws DOMException
	{
		setAttribute(name, Integer.toString(value)) ;
		return this ;
	}
	public DElement setDsfAttribute(
		final String name, final long value) throws DOMException
	{
		setAttribute(name, Long.toString(value)) ;
		return this ;
	}
	public DElement setDsfAttribute(
		final String name, final double value) throws DOMException
	{
		setAttribute(name, Double.toString(value)) ;
		return this ;
	}
	
	
	public void setAttribute(
		final String name, final String value) throws DOMException
	{
		if (name == null) {
			throw new DsfRuntimeException("Attribute name must not be null") ;
		}
// MrPperf - we're adding a value so we should materialize the attrs
		DAttr attr = getDsfAttributes().getAttr(name);
		if (attr == null) {
			attr = new DAttr(name, value);
			m_attributes.put(attr);
		}
		attr.setValue(value);
	}
	
	public DElement setDsfAttribute(
		final Enum<?> attrEnum, final String value) throws DOMException
	{
		setAttribute(attrEnum, value) ;
		return this ;
	}
	public DElement setDsfAttribute(
		final Enum<?> attrNameEnum, final Enum<?> attrValueEnum) throws DOMException
	{
		setAttribute(attrNameEnum, attrValueEnum.name()) ;
		return this ;
	}
	public DElement setDsfAttribute(
		final String name, final Enum<?> attrValueEnum) throws DOMException
	{
		if (attrValueEnum == null) {
			chuck("Attr value enum must not be null") ;
		}
		setAttribute(name, attrValueEnum.name()) ;
		return this ;
	}
	public void setAttribute(
		final Enum<?> attrEnum, final String value) throws DOMException
	{
		if (attrEnum == null) {
			chuck("Attr name enum must not be null") ;
		}
		setAttribute(attrEnum.name(), value) ;
	}


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
	public void removeAttribute(final String name) throws DOMException {
		if (name == null) {
			throw new DsfRuntimeException("attribute name must not be null") ;
		}
//		 MrPperf -- don't create attributes unless we need them
// I needed to copy some of this defensive code from the attribute map to
// keep our current exception semantics
		final PropertyDescriptor pd = this.getPropertyDescriptor(name);
		if (pd != null) {
			throw new DsfRuntimeException("Can not remove an intrinsic property") ;
		}
		
		if (m_attributes == null) return ;
		m_attributes.remove(name);
	}
	
	/**
	 * Remove the attribute for the passed in enum
	 * @param attrEnum enum of the attribute to remove.  Must not be null.
	 * @throws DOMException
	 */
	public void removeAttribute(final Enum<?> attrEnum) throws DOMException {
		if (attrEnum == null) {
			chuck("Attr enum must not be null") ;
		}
		removeAttribute(attrEnum.name()) ;
	}

	/**
	 * Retrieves an attribute node by name.
	 * <br>To retrieve an attribute node by qualified name and namespace URI, 
	 * use the <code>getAttributeNodeNS</code> method.
	 * @param name The name (<code>nodeName</code>) o f the attribute to 
	 *   retrieve.
	 * @return The <code>Attr</code> node with the specified name (
	 *   <code>nodeName</code>) or <code>null</code> if there is no such 
	 *   attribute.
	 */
	public Attr getAttributeNode(final String name) {
//		 MrPperf -- don't create attributes unless we need them
		if (m_attributes == null) {
			return null ;
		}
		return m_attributes.getAttributeNode(name);
	}

	/**
	 * Adds a new attribute node. If an attribute with that name (
	 * <code>nodeName</code>) is already present in the element, it is 
	 * replaced by the new one. Replacing an attribute node by itself has no 
	 * effect.
	 * <br>To add a new attribute node with a qualified name and namespace 
	 * URI, use the <code>setAttributeNodeNS</code> method.
	 * @param newAttr The <code>Attr</code> node to add to the attribute list.
	 * @return If the <code>newAttr</code> attribute replaces an existing 
	 *   attribute, the replaced <code>Attr</code> node is returned, 
	 *   otherwise <code>null</code> is returned.
	 * @exception DOMException
	 *   WRONG_DOCUMENT_ERR: Raised if <code>newAttr</code> was created from a 
	 *   different document than the one that created the element.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>INUSE_ATTRIBUTE_ERR: Raised if <code>newAttr</code> is already an 
	 *   attribute of another <code>Element</code> object. The DOM user must 
	 *   explicitly clone <code>Attr</code> nodes to re-use them in other 
	 *   elements.
	 */
	public Attr setAttributeNode(final Attr newAttr) throws DOMException {
		if (newAttr == null) {
			throw new DsfRuntimeException("null attribute not allowed");
		}
// MrPperf - need to force creation of map
		final DAttr existing 
			= getDsfAttributes().getAttr(newAttr.getName());
		final DAttr attr = (DAttr)newAttr;
		attr.setOwnerElement(this);
		m_attributes.put(attr);
		if (existing != null) {
			existing.setOwnerElement(null);
		}
		return existing;
	}

	/**
	 * Removes the specified attribute node. If a default value for the 
	 * removed <code>Attr</code> node is defined in the DTD, a new node 
	 * immediately appears with the default value as well as the 
	 * corresponding namespace URI, local name, and prefix when applicable. 
	 * The implementation may handle default values from other schemas 
	 * similarly but applications should use 
	 * <code>Document.normalizeDocument()</code> to guarantee this 
	 * information is up-to-date.
	 * @param oldAttr The <code>Attr</code> node to remove from the attribute 
	 *   list.
	 * @return The <code>Attr</code> node that was removed.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>NOT_FOUND_ERR: Raised if <code>oldAttr</code> is not an attribute 
	 *   of the element.
	 */
	public Attr removeAttributeNode(final Attr oldAttr) throws DOMException {
		if (oldAttr == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"null attribute node is not valid and cannot be found");
		}
// MrPperf -
		if (m_attributes == null) {
			return null ;
		}
		return (Attr) m_attributes.removeNamedItem(oldAttr.getName());
	}

	/**
	 * Returns a <code>NodeList</code> of all descendant <code>Elements</code> 
	 * with a given tag name, in document order.  XML tag names ARE case-sensitive.
	 * @param name The name of the tag to match on. The special value "*" 
	 *   matches all tags.
	 * @return A list of matching <code>Element</code> nodes.
	 */
	public NodeList getElementsByTagName(final String name) {
		final DNodeList answer = new DNodeList(this, 5);
		getElementsByTagName(name, answer);
		return answer;
	}

	protected void getElementsByTagName(
		final String name, final DNodeList answer)
	{		
		final boolean matchesAll = "*".equals(name);
		// preorder traversal
// MrPperf - don't create children unless we need them
		if (m_childNodes == null) return ;
		
// MrPperf -- don't use iterator, loop directly over nodes
		int size = m_childNodes.size();
		for (int i = 0; i < size; i++) {
			DNode childx = m_childNodes.get(i);
			if (childx instanceof DElement) {
				final DElement child = (DElement) childx;
				String childName = child.getNodeName();
				if (matchesAll || childName.equals(name)) {					
					answer.privateAdd(child);
				}
				// *** RECURSION ***
				child.getElementsByTagName(name, answer);
			}
		}		
	}

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
	public String getAttributeNS(
		final String namespaceURI, final String localName) throws DOMException
	{
		throw new DsfDomNotSupportedRuntimeException("getAttributeNS(...)");
	}

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
	public void setAttributeNS(
		final String namespaceURI, 
		final String qualifiedName,
		final String value) throws DOMException 
	{
		throw new DsfDomNotSupportedRuntimeException("setAttributeNS(...)");
	}

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
	public void removeAttributeNS(
		final String namespaceURI, final String localName) throws DOMException 
	{
		throw new DsfDomNotSupportedRuntimeException("removeAttributeNS(...)");
	}

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
	public Attr getAttributeNodeNS(
		final String namespaceURI, final String localName) throws DOMException
	{
		throw new DsfDomNotSupportedRuntimeException("getAttributeNodeNS(...)");
	}

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
	public Attr setAttributeNodeNS(final Attr newAttr) throws DOMException {
		throw new DsfDomNotSupportedRuntimeException("setAttributeNodeNS(...)");
	}

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
	public NodeList getElementsByTagNameNS(
		final String namespaceURI, final String localName)throws DOMException
	{
		final DNodeList answer = new DNodeList(this, 5);
		getElementByTagNameNS(localName, namespaceURI, answer);
		return answer;
	}
	
	/**
	 * Returns a <code>NodeList</code> of all the <code>Elements</code> with a 
	 * given namespace and localName in the order in which they are encountered 
	 * in a preorder traversal of the Document tree. 
	 * @param namespace
	 * @param localName
	 * @return
	 */
	public NodeList dsfGetElementsByTagNameNS(
		final DNamespace namespace, final String localName)throws DOMException
	{
		final DNodeList answer = new DNodeList(this, 5);
		getElementByTagNameNS(localName, namespace, answer);
		return answer;
	}
	
	private void getElementByTagNameNS(final String name, final String nsUri, final DNodeList answer){
		if (m_childNodes == null) return ;
		if (nsUri == null ) {
			getElementsByTagName(name, answer);
			return;
		} 
		
		if (name == null) {
			return;
		}
		final boolean matchesAllName = "*".equals(name);
		final boolean matchesAllUri = "*".equals(nsUri);
		
	    // DOM2: Namespace logic.
		for (DNode childx : m_childNodes) {
			if (childx.getNodeType() == Node.ELEMENT_NODE) {
			    if (matchesAllName) {
					if ( matchesAllUri) {
						answer.privateAdd(childx);
					} else {					  
					    if ( nsUri.equals(childx.getNamespaceURI())) {
					    	answer.privateAdd(childx);
					    }
					}
			    } else {			    	
					if (childx.getLocalName() != null && childx.getLocalName().equals(name)) {
					    if (matchesAllUri) {
					    	answer.privateAdd(childx);
					    } else {
							if (nsUri.equals(childx.getNamespaceURI())) {
								answer.privateAdd(childx);
							}
					    }
					}
			    }
			    //*** RECURSION ***
				((DElement)childx).getElementByTagNameNS(name, nsUri, answer);
			}
		}
	}
	private void getElementByTagNameNS(
		final String name, final DNamespace ns, final DNodeList answer)
	{
		if (m_childNodes == null) return ;
		if (ns == null ) {
			getElementsByTagName(name, answer);
			return;
		} 		
		if (name == null) {
			return;
		}
		final boolean matchesAllName = "*".equals(name);			
	    // DOM2: Namespace logic.
// MrP - perf - loop over children directly, do create an iterator
		for (DNode childx : m_childNodes) {
			if (childx.getNodeType() == Node.ELEMENT_NODE) {
			    if (matchesAllName && ns == childx.getDsfNamespace()) {
				   	answer.privateAdd(childx);				   					
			    } else {			    	
					if (childx.getLocalName() != null 
							&& childx.getLocalName().equals(name)
							&& ns == childx.getDsfNamespace()) 
					{
					    answer.privateAdd(childx);					    
					}
			    }
			    //*** RECURSION ***
				((DElement)childx).getElementByTagNameNS(name, ns, answer);
			}
		}
	}
	
	
	/**
	 * Testing
	 * @param namespaceURI
	 * @param localName
	 * @param traversal
	 * @return
	 * @throws DOMException
	 */
	public NodeList getElementsByTagNameNS(
		final String namespaceURI, 
		final String localName, 
		final IDNodeHandlingStrategy traversal )throws DOMException
	{
	      //return new DDeepNodeListImpl(this, namespaceURI, localName);	      
		final DNodeList answer = new DNodeList(this, 5);
	
		GetElementByNSQuery qry = new GetElementByNSQuery(answer, namespaceURI, localName);		
		
		//Breadth First Traversal -- returned order of node is different from the order in document 
		//BreadthFirstDNodeTraversal breadthTraversal = new BreadthFirstDNodeTraversal() ;
		
		//DepthFirstTraversal -- return order is the same as the order in document
		
		qry.setStrategy(traversal) ;
		// snippet.breadthFirst.end
		dsfAccept(qry) ;
		//System.out.println("DepthFirst visited nodes = " + qry.m_count);
		return answer;		
	}

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
	public boolean hasAttribute(final String name) {
		if (name == null) {
			chuck("Attribute name must not be null") ;
		}
// MrPperf -
		if (m_attributes == null) {
			return false ;
		}
		return m_attributes.containsKey(name);
	}
	
	/**
	 * Answer if the attribute exists
	 * @param attrEnum enum of the attribute to lookup.  Must not be null.
	 * @return true if the attribute exists
	 */
	public boolean hasAttribute(final Enum<?> attrEnum) {
		if (attrEnum == null) {
			chuck("Attr enum must not be null") ;
		}
		return hasAttribute(attrEnum.name()) ;
	}

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
	public boolean hasAttributeNS(
		final String namespaceURI, final String localName) throws DOMException
	{
		throw new DsfDomNotSupportedRuntimeException("hasAttributeNS(...)");
	}

	/**
	 *  The type information associated with this element. 
	 * @since DOM Level 3
	 */
	public TypeInfo getSchemaTypeInfo() {
		throw new DsfDomLevelNotSupportedException(3);
	}

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
	public void setIdAttribute(
		final String name, final boolean isId) throws DOMException
	{
		if (name == null){
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"attribute with null name");
		}
// MrPperf - don't create attributes unless we need to - force lazy
		if (m_attributes == null){
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"no attribute found with name " + name);
		}
		
		final DAttr attr = getDsfAttributes().getAttr(name);
		if (attr == null){
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"no attribute found with name " + name);
		}
		
		final String id = attr.getNodeValue();

		if (getDsfOwnerDocument() != null) {
			if (isId) {
				getDsfOwnerDocument().putIdentifier(id, this);
			} 
			else {
				getDsfOwnerDocument().removeIdentifier(id);
			}
		}
		attr.setId(isId);
	}

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
	public void setIdAttributeNS(
		final String namespaceURI, final String localName,
		final boolean isId) throws DOMException
	{
		throw new DsfDomLevelNotSupportedException(3);
	}

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
	
	public void setIdAttributeNode(
		final Attr attr, final boolean isId)throws DOMException
	{
		if (attr.getOwnerElement() != this) {
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"An attempt is made to reference a node in a context where " +
				"it does not exist.");
		}
		final DAttr idAttr = (DAttr)attr;
		if (idAttr.isId() == isId) {
			return ; //document is already updated
		}
		final String id = idAttr.getNodeValue();
		if (isId) {
			getDsfOwnerDocument().putIdentifier(id, this);
		} 
		else {
			getDsfOwnerDocument().removeIdentifier(id);
		}
		idAttr.setId(isId);
	}

	@Override
	public final short getNodeType() {
		return Node.ELEMENT_NODE;
	}

	//
	// DNode Overrides
	//
	@Override
	public DElement add(final DNode node) {
		super.add(node) ;
		return this ;
	}
	
	@Override
	public DElement add(final String text) {
		super.add(text) ;
		return this ;
	}
	
	@Override 
	public DElement addRaw(final String text) {
		super.addRaw(text) ;
		return this ;
	}
	
//	@Override
//	public DElement addDsfListener(IDsfEventListener listener) {
//		super.addDsfListener(listener) ;
//		return this ;
//	}
	
	@Override
	public DElement dsfAccept(final IDNodeVisitor visitor) {
		super.dsfAccept(visitor) ;
		return this ;
	}
	
	@Override
	public DElement dsfBroadcast(final DsfEvent event) 
		throws AbortDsfEventProcessingException 
	{
		super.dsfBroadcast(event) ;
		return this ;
	}

	@Override
	public DElement setDsfExportingLocalNames(final boolean export) {
		super.setDsfExportingLocalNames(export) ;
		return this ;
	}
	
	@Override
	public DElement setDsfNamingFamily(final IDsfNamingFamily family) {
		super.setDsfNamingFamily(family) ;
		return this ;
	}
	
	@Override
	public DElement setDsfRelationshipVerifier(final IDNodeRelationshipVerifier verifier) {
		super.setDsfRelationshipVerifier(verifier) ;
		return this ;
	}
	
	@Override
	public DElement dsfDiscard() {
// MrPperf --
		if(m_attributes != null) {
			m_attributes.clear();
		}
		m_attributes = null;
		
		super.dsfDiscard();
		return this ;
	}
	
	@Override
	public DElement cloneInternal(final boolean deep) throws CloneNotSupportedException {
		DElement copy = (DElement) super.cloneInternal(deep);
// MrPperf - avoid creating attributes unless needed
		if (m_attributes == null) return copy ;		
		copy.m_attributes = createAttributeMap(m_attributes.size());
		for (Map.Entry<String, DAttr> entry : m_attributes.attrEntrySet()) {
			final DAttr attr = entry.getValue();
			copy.m_attributes.put(attr.getNodeName(), attr.getNodeValue());
			final IValueBinding<?> binding = attr.getValueBinding();
			if (binding != null) {
				IValueBinding<Object> newBinding = 
					new SimpleValueBinding<Object>(Object.class, binding.getValue());
				copy.m_attributes.setValueBinding(entry.getKey(), newBinding);
			}
		}
		return copy;
	}
	
	@Override
	public DElement jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		Z z = new Z();
//		z.format("element tag name", m_tagName);
		z.format("element attrs", getElementAttrs()) ;
		return super.toString() + z.toString() ;
	}

	private String getElementAttrs() {
		String answer = "{ " ;
// MrPperf -
		if (m_attributes != null) {
			final Set<Map.Entry<String, DAttr>> entries = m_attributes.attrEntrySet() ;
			final Iterator<Map.Entry<String, DAttr>> itr = entries.iterator() ;
			while(itr.hasNext()) {
				Map.Entry<String, DAttr> entry = itr.next() ;
				String attrName = entry.getKey() ;
				DAttr attr = entry.getValue() ;
				String x = "{" + attrName + ", " + attr.getValue() + "}" ;
				if (itr.hasNext()) x += ", " ;
				answer += x ;
			}
		}
		return answer += " }" ;
	}
	
	protected boolean toBoolean(final String name, final String value) {
		return name.equals(value);
	}

	protected int toInt(final String label, final String value) {
		try {
			return Integer.parseInt(value);
		} 
		catch (NumberFormatException e) {
			throw new DsfRuntimeException("Unable to convert " + label
				+ " value: " + value + " to int", e);
		}
	}

//	protected Object getDynamicAttribute(String key) {
//		final AttributeMap map = (AttributeMap) getDsfAttributes();
//		return map.getInternalDynamicProperty(key);
//	}
//
//	protected Object putDynamicAttribute(String key, Object value) {
//		final AttributeMap map = (AttributeMap) getDsfAttributes();
//		return map.putInternalDynamicProperty(key, value);
//	}
//	@Override
//	public DNode setDsfAttribute(String name, Object value) {
////		if (value instanceof String) {
////			setAttribute(name, (String)value);
////		} else {
//			super.setDsfAttribute(name, value);
////		}
//		return this;
//	}
	/**
	 * A <code>NamedNodeMap</code> containing the attributes of this node (if 
	 * it is an <code>Element</code>) or <code>null</code> otherwise.
	 */
	@Override
	public NamedNodeMap getAttributes() {
// MrPperf
		if (m_attributes == null) {
			m_attributes = createAttributeMap(4);
		}
		return m_attributes;
	}
	
	/** 
	 * Answer the NamedNodeMap for this instance.  If the map has already
	 * been created, the initialSize argument is a no-op and the existing
	 * map is returned.  If this is the first time the attributes map is
	 * asked for, it is presized with the initialSize argument.  You can
	 * use this in cases where you know you are going to create a large number
	 * of attributes and pre-sizing this collection will save some extra
	 * grow operations.  The default attributes map size is 4.
	 * @param initialSize
	 * @return
	 */
	public NamedNodeMap getAttributes(final int initialSize) {
		if (m_attributes == null) {
			if (initialSize < 0) {
				chuck("The initialSize must not be negative") ;
			}
			createAttributeMap(initialSize);
		}
		
		return m_attributes ;
	}
	
	/**
	 * Returns whether this node (if it is an element) has any attributes.
	 * @return Returns <code>true</code> if this node has any attributes, 
	 *   <code>false</code> otherwise.
	 *
	 * @since DOM Level 2
	 */
	@Override
	public boolean hasAttributes() {
// MrPperf -
		if (m_attributes == null) return false ;
		return !m_attributes.isEmpty();
	}
	@Override
	public IAttributeMap getDsfAttributes() {
// MrPperf 
		return (IAttributeMap)getAttributes() ;
//		return m_attributes;
	}
	
	public IAttributeMap getDsfAttributes(final int initialSize) {
		return (IAttributeMap)getAttributes(initialSize) ;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		final DElement copy = cloneInternal(true) ;
//		copy.m_attributes = new AttributeMap(this, this, m_attributes.size());
////		for (Map.Entry<String, IValueManager> entry:m_attributes.entrySet()) {
//		for (Map.Entry<String, DAttr> entry : m_attributes.entrySet()) {
////			final IValueManager<Object> valueMgr = new ManagedValue<Object>(Object.class);
////			valueMgr.setValue(((IValueManager<?>)entry.getValue()).getValue());
//			final DAttr attr = entry.getValue();
//			final IValueManager vm = attr.getValueManager();
//			if (vm == null) {
//				copy.m_attributes.put(attr.getNodeName(), attr.getNodeValue());
//			} else {
//				final Object value = vm.getValue();
//				copy.m_attributes.put(entry.getKey(), value);
//			}
//		}
		return copy;
	}

	protected AttributeMap createAttributeMap(final int initialSize) {
		return new AttributeMap(this, this, initialSize) ;
	}

	@Override
	public String getPrefix() {
		return m_prefix;
	}
	@Override
	public String getNamespaceURI() {		
		return m_dsfNamespace!=null?m_dsfNamespace.getNamespaceKey():null ;
	}
	       
    @Override
    protected boolean isEqualAttrs(Node arg){
        if (hasAttributes() != arg.hasAttributes()) {
            return false;
        }
        //now both attr are the same for has
        if (!hasAttributes()) {
        	return true;
        }
        
        NamedNodeMap map1 = getAttributes();
        NamedNodeMap map2 = arg.getAttributes();
        int len = map1.getLength();
        if (len != map2.getLength()) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            Node n1 = map1.item(i);
            if (n1.getLocalName() == null) {
            	// DSF DOM attribute doesn't support namespace
                Node n2 = map2.getNamedItem(n1.getNodeName());
                if (n2 == null || ! n1.isEqualNode(n2)) {
                    return false;
                }
            }
            //NS for attribute is not enabled
            else {
                Node n2 = map2.getNamedItemNS(n1.getNamespaceURI(),
                                              n1.getLocalName());
                if (n2 == null || ! n1.isEqualNode(n2)) {
                    return false;
                }
            }
        }        
        return true;
    }
      
	/**
	 * DOM2: Constructor for Namespace implementation. it is meant to be used by
	 * namespace aware applications. Simple applications that don't use namespace 
	 * can use DElement(final DDocument document, final String tagName)
	 * Should avoid mising DOM1 and DOM2 methods at the same time.
	 * 
	 * @param document - Document obj, owner of element to be created. optional. 
	 * @param namespaceURI
	 * @param tagName
	 * 
	 * @exception DOMException
	 */
	DElement(final DDocument document, final String namespaceURI, final String tagName) 
	{
		this(document, tagName);				
		setNamespace(namespaceURI, tagName);		
	}
	
	/**
	 * DOM2: Constructor for Namespace implementation. it is meant to be used by
	 * namespace aware applications. Simple applications that don't use namespace 
	 * can use DElement(final DDocument document, final String tagName)

	 * Dsf version of construction of DElement with namespace 
	 * @param document
	 * @param namespace
	 * @param localName
	 */
	public DElement(
		final DDocument document, 
		final DNamespace namespace, 
		final String localName)
	{
		this(document, localName);		
       
	    if (DsfVerifierConfig.getInstance().isVerifyNaming()) {
	       NSNameVerifier.verifyNSLocalName(m_nodeName, null);
	    }		
		setDsfNamespace(namespace);
	}
	
	//construction time, supre already verified name, skip it
	private void setNamespace(final String namespaceURI, final String tagName) {	
        int colon1, colon2 ;
	    colon1 = m_nodeName.indexOf(DNamespace.NS_NAME_CHAR);
	    colon2 = m_nodeName.lastIndexOf(DNamespace.NS_NAME_CHAR);

        // it is an error for NCName to have more than one DNamespace.NS_NAME_CHAR
        // check if it is valid QName [Namespace in XML production 6]
        // :camera , nikon:camera:minolta, camera:
	    if (DsfVerifierConfig.getInstance().isVerifyNaming()) {
	        if (colon1 == 0 || colon1 == m_nodeName.length() - 1 || colon2 != colon1) {	        	 
	        	DErrUtil.elementNSError(m_nodeName, null, null);
	        }
	    }
		if (colon1 < 0) { //no prefix		
			if (DsfVerifierConfig.getInstance().isVerifyNaming()) {								
				NSNameVerifier.verifyNSLocalName( m_nodeName, namespaceURI);
			}
			if (namespaceURI != null) {
				setNamespaceInternal(DNamespace.getNamespace(null, namespaceURI), false);
			}
		} else {//there is a prefix			
			String prefix = m_nodeName.substring(0, colon1);
		    m_localName = m_nodeName.substring(colon2 + 1);
		   		  
		    setNamespaceInternal(DNamespace.getNamespace(prefix, namespaceURI), false);
		}
	}	

	@Override	
	public String getLocalName() {
		return m_localName;
	}		
	
    @Override
    public String getBaseURI() {
    	DNamespace ns = (m_ownerDocument != null) ? 
    			(m_ownerDocument).getDsfDocumentURI() : null ;
    	return ns==null?null:ns.getNamespaceKey();
    }
    
 
    @Override
    public DNamespace getDsfBaseURI() { 
    	//Absolute base URI is computed according to XML Base (http://www.w3.org/TR/xmlbase/#granularity)
    	//1.  the base URI specified by an xml:base attribute on the element, if one exists
       	// Attr, not supported
    	
    	//2.the base URI of the element's parent element within the document or external entity,
        //if one exists
    	
    	DNode rootN = getRootElement();
    	if (rootN != null && rootN.getDsfNamespace() != null){
    		return rootN.getDsfNamespace();
    	}
    	
    	//3. the base URI of the document entity or external entity containing the element
    	return (m_ownerDocument != null) 
    		? (m_ownerDocument).getDsfDocumentURI() : null ;   	
    }   
    
    /**
     * set namespace for this node.
     * update the nodename based on the given namespace
     * @param namespace
     * @return
     */
    @Override
    public DElement setDsfNamespace(final DNamespace namespace){
    	return setNamespaceInternal(namespace, true);
    }
   
    private DElement setNamespaceInternal(
    	final DNamespace namespace, final boolean bNoneNSPrefixReset)
    {
    	if (namespace == null) {    		
    		if (m_dsfNamespace == null){    			
				return this;
    		} 
    		//namespace is null and current node already has namespace
    		m_prefix = null;
    		m_nodeName = m_localName;
    		m_dsfNamespace = null;
    		return this;
		} 
    	// namespace is not null and current node has not namespace
    	if (m_dsfNamespace == null ){
    		m_prefix =  namespace.getPrefix();    
    		if (bNoneNSPrefixReset){
    			if (m_prefix != null){
    				m_localName = m_nodeName;
    				m_nodeName = m_prefix + DNamespace.NS_NAME_CHAR + m_localName;
    			}
    		}    		
    	} else {
    		if (m_prefix == null) {
    			m_localName = m_nodeName;
    		}
    		m_prefix = namespace.getPrefix();
	    	// namespace is not null and current node already has namespace
	    	if (m_prefix != null){	    		
	    		m_nodeName =m_prefix +DNamespace.NS_NAME_CHAR + m_localName;
	    	} else {
	    		m_nodeName = m_localName; //remove prefix
	    	}
    	}
    	m_dsfNamespace = namespace;    	
		return this ;
    }   
    
    @Override
    public DNamespace getDsfNamespace(){
    	return m_dsfNamespace;
    }
    
	/**
	 * Answer a Node instance of type T.  The result of this will be a 
	 * set of children upto count in size .  If we do a getOrCreate(X, 3)
	 * and there are no X-type children, they are created and the count one
	 * is returned.  If only a partial set upto count is available, again,
	 * the remainder are created and the one at count is returned.
	 * <p>
	 * Note that the "count" is NOT the index the child is in the children list
	 * Count is 1-based and NOT 0-based like an index would be and thus this
	 * furthers to enforce the differentiation.
	 * <p>
	 * If the count is 0 or negative we simply create a new instance.  This
	 * supports the cases where you are programmatically driving the instance
	 * creation and don't want to be burdened with trying to keep/find a counter.
	 */
	protected <T extends Class> Node getOrCreate(final T clz, final int count) {
		if (count <= 0) return create(clz) ;
		
		int found = 0 ;
		if (hasChildNodes()) {
			final NodeList kids = getChildNodes() ;
			final int len = kids.getLength() ;
			for(int i = 0; i < len; i++) {
				Node node = kids.item(i) ;
				if (node.getClass() == clz) {
					found++ ;
					if (found == count) return node ;
				}
			}
		}

		// we need to create N new nodes of type T
		final int numOfNodesToCreate = count - found ;
		for(int i = 1; i <= numOfNodesToCreate; i++) {
			Node node = create(clz) ;
			if (i == numOfNodesToCreate) return node ;
		}
		
		throw new RuntimeException("Create node logic failed") ;
	}
	
	private Node create(final Class<?> clz) {
		try {
			final Node node = (Node)clz.newInstance() ;
			appendChild(node) ;
			return node ;
		}
		catch(InstantiationException e) {
			throw new RuntimeException(e) ;
		}
		catch(IllegalAccessException e) {
			throw new RuntimeException(e) ;
		}		
	}
	
	protected <T extends Class> Node getOrCreate(T clz) {
		if (hasChildNodes()) {
			final NodeList kids = getChildNodes() ;
			final int len = kids.getLength() ;
			for(int i = 0; i < len; i++) {
				Node node = kids.item(i) ;
				if (node.getClass() == clz) {
					return node ;
				}
			}
		}

		return create(clz) ;
	}
	
}
