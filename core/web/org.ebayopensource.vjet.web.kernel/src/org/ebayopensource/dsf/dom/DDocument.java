/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import org.ebayopensource.dsf.common.binding.IValueBinding;
import org.ebayopensource.dsf.common.binding.SimpleValueBinding;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.node.IDNodeList;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.dom.support.DsfDomLevelNotSupportedException;
import org.ebayopensource.dsf.dom.support.DsfDomNotSupportedRuntimeException;
import org.ebayopensource.dsf.common.Z;

/**
 * The <code>Document</code> interface represents the entire HTML or XML 
 * document. Conceptually, it is the root of the document tree, and provides 
 * the primary access to the document's data.
 * <p>Since elements, text nodes, comments, processing instructions, etc. 
 * cannot exist outside the context of a <code>Document</code>, the 
 * <code>Document</code> interface also contains the factory methods needed 
 * to create these objects. The <code>Node</code> objects created have a 
 * <code>ownerDocument</code> attribute which associates them with the 
 * <code>Document</code> within whose context they were created.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 */
public class DDocument extends DNode implements Document {

	private static final long serialVersionUID = 1L;
	
	private String m_xmlVersion;
	private boolean m_bDsfXml11Version;
	
	private Map<String, DElement> m_idToElement = new HashMap<String, DElement>();
	//private Map<DElement, String> m_elementToId = new HashMap<DElement, String>();
	
	//DNamespace object directly added to this document
	private Set<DNamespace> m_dsfNamespaceDeclarations; 

	private DNamespace m_dsfDocumentURI; 

	private String m_xmlEncoding;

    private String m_inputEncoding; // m_xmlActualEncoding;
    
    private DOMImplementation m_implementation ;
    
// We hold onto this directly to ease the cost of looking up the document
// element in a child list.  While this is not very expensive, a number of
// our routines (like Rendering) will ask this question many 1000's of times.
//    private DElement m_documentElement ;
    
    static final List<String> ENCODEINGS = new ArrayList<String>(4);
    static final List<String> XML_VERSIONS = new ArrayList<String>(2);
    static {
    	ENCODEINGS.add("utf-8");
    	ENCODEINGS.add("utf-16");
    	ENCODEINGS.add("iso-8859-1");
    	ENCODEINGS.add("cp1252");    
    	
    	XML_VERSIONS.add("1.0");
    	XML_VERSIONS.add("1.1");
    }
    
	//
	// Constructor(s) 
	//
	public DDocument() {
		this(null);
	}

	public DDocument(final DDocumentType docType) {
//		m_docType = docType;
		setDsfOwnerDocument(this);
		if (docType != null) {
			docType.setDsfOwnerDocument(this) ;
			add(docType) ;
		}
	}

	//
	// Overrides from DNode
	//	
	public DDocument add(final DNamespace ns) {
		getDsfNamespaceDeclarations().add(ns) ;
		return this ;
	}	
	
	@Override
	public DDocument add(DNode node) {
		super.add(node) ;
		return this ;
	}
	
	@Override
	public Node appendChild(final Node newChild) throws DOMException {
		if (newChild == null) {
			throw new DOMException(
				DOMException.VALIDATION_ERR, 
				"Document add(...) expects a non-null");
		}
		
		final Class<?> newChildsClass = newChild.getClass();
		
		if (DocumentFragment.class.isAssignableFrom(newChildsClass)) {
			DocumentFragment frag = (DocumentFragment)newChild ;
			
			// Check if there are any children to add BEFORE other assertions
			if (!frag.hasChildNodes()) return this ; // empty so a no-op
			
			if (getDocumentElement() != null) {
				throw new DOMException(
					DOMException.VALIDATION_ERR, "Document already has a document element");				
			}
			
// TODO: What a fragment from another document?  Should we allows its children
// into this new document?  
			if (frag.getOwnerDocument() != null
				&& !(frag.getOwnerDocument() == this))
			{
				throw new DOMException(
					DOMException.VALIDATION_ERR, "The added fragment is from another document");					
			}
			
			NodeList children = frag.getChildNodes() ;
			int size = children.getLength() ;
			if (size > 1) {
				throw new DOMException(
					DOMException.VALIDATION_ERR, 
					"Documents only support a single root node.  The added fragment has more than 1 child.");									
			}
			
			Node child = children.item(0) ;
			super.appendChild(child) ;

			return newChild ;
		}
		
		if (Element.class.isAssignableFrom(newChildsClass)
			|| Comment.class.isAssignableFrom(newChildsClass)
			|| ProcessingInstruction.class.isAssignableFrom(newChildsClass))
		{
			super.appendChild(newChild);			
			return this ;
		}
		
		if (DElement.class.isAssignableFrom(newChildsClass)) {
			if (getDocumentElement() != null) {
				throw new DOMException(
					DOMException.VALIDATION_ERR, "Document already has a document element");				
			}
		}
		
		if (DocumentType.class.isAssignableFrom(newChildsClass)) {
			if (getDoctype() != null) {
				throw new DOMException(
					DOMException.VALIDATION_ERR, "Document already has a doc type");
			}
			DDocumentType docType = (DDocumentType)newChild ;
			getDsfChildNodes().add(0, docType) ;
			docType.setParent(this) ;
			docType.setDsfOwnerDocument(this);
			return this ;
		}
		
		super.appendChild(newChild);
		return this;
		
		/*
		 * Following exception is needed but commenting out since some of the app code using it wrong way
		 * For example, v3trading/CheckoutApplicationV4/src/com/ebay/darwin/app/checkout/page/constructpaycheckout/ConstructPayCheckout.java
		 * is adding DRawString to DDocument which is wrong
		 */
//		throw new DOMException(
//			DOMException.VALIDATION_ERR, 
//			"A DDocuments children can be DDocumentType(0..1), DElement(0..1), DProcessingInstruction(0..n), DComment(0..n)") ;
	}
	
	//
	// Satisfy Document
	//
	/**
	 * The Document Type Declaration (see <code>DocumentType</code>) 
	 * associated with this document. For XML documents without a document 
	 * type declaration this returns <code>null</code>. For HTML documents, 
	 * a <code>DocumentType</code> object may be returned, independently of 
	 * the presence or absence of document type declaration in the HTML 
	 * document.
	 * <br>This provides direct access to the <code>DocumentType</code> node, 
	 * child node of this <code>Document</code>. This node can be set at 
	 * document creation time and later changed through the use of child 
	 * nodes manipulation methods, such as <code>Node.insertBefore</code>, 
	 * or <code>Node.replaceChild</code>. Note, however, that while some 
	 * implementations may instantiate different types of 
	 * <code>Document</code> objects supporting additional features than the 
	 * "Core", such as 'HTML' [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
	 * , based on the <code>DocumentType</code> specified at creation time, 
	 * changing it afterwards is very unlikely to result in a change of the 
	 * features supported.
	 * @version DOM Level 3
	 */
	public DDocumentType getDoctype() {
//		return m_docType;
		Node firstChild = getFirstChild() ;
		if (firstChild == null) return null ;
		if (firstChild instanceof DDocumentType) return (DDocumentType) firstChild;
		return null ;
	}

//	void setDocumentType(DDocumentType docType) {
//		m_docType = docType ;
//	}
	
	/**
	 * The <code>DOMImplementation</code> object that handles this document. A 
	 * DOM application may use objects from multiple implementations.  Answer
	 * null if no implementation is associated with this document
	 */
	public DOMImplementation getImplementation() {
		return m_implementation ;
	}
	
	/**
	 * Framework use only
	 */
	protected DDocument setDOMImplementation(final DOMImplementation impl) {
		m_implementation = impl ;
		return this ;
	}

	/**
	 * This is a convenience attribute that allows direct access to the child 
	 * node that is the document element of the document.  If no such Element
	 * child answer null.
	 */
	public Element getDocumentElement() {
		if (!hasChildNodes()) {
			return null ;
		}
		final IDNodeList kids = getDsfChildNodes() ;
		final int len = kids.getLength() ;
		for(int i = 0; i < len; i++) {
			DNode node = kids.get(i) ;
			if (node instanceof Element) return (Element)node ;
		}
//		for(DNode node: getDsfChildNodes()) {
//			if (node instanceof Element) return (Element)node ;
//		}
		return null ;
	}
	public DElement dsfGetDocumentElement() {
		return (DElement)getDocumentElement() ;
	}

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
	 * @return A new Element object with the nodeName attribute 
	 * 	set to tagName, and localName, prefix, and namespaceURI set to null.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
	 *   name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 */
	public Element createElement(final String tagName) throws DOMException {				
		return new DElement(this, tagName);
	}
	public <T> T element(final String tagName) {
		return (T) createElement(tagName) ;
	}
	
	public DElement dsfCreateElement(final String tagName) throws DOMException {				
		return (DElement)createElement(tagName) ;
	}

	/**
	 * Creates an empty <code>DocumentFragment</code> object.
	 * @return A new <code>DocumentFragment</code>.
	 */
	public DocumentFragment createDocumentFragment() {
		return new DDocumentFragment(this);
	}
	public DDocumentFragment dsfCreateDocumentFragment() {
		return (DDocumentFragment)createDocumentFragment() ;
	}

	/**
	 * Creates a <code>Text</code> node given the specified string.
	 * @param data The data for the node.
	 * @return The new <code>Text</code> object.
	 */
	public Text createTextNode(final String data) {
		return new DText(this, data);
	}
	public DText dsfCreateTextNode(final String data) {
		return (DText)createTextNode(data) ;
	}

	/**
	 * Creates a <code>Comment</code> node given the specified string.
	 * @param data The data for the node.
	 * @return The new <code>Comment</code> object.
	 */
	public Comment createComment(final String data) {
		return new DComment(this, data);
	}
	public DComment dsfCreateComment(final String data) {
		return (DComment)createComment(data) ;
	}

	/**
	 * Creates a <code>CDATASection</code> node whose value is the specified 
	 * string.
	 * @param data The data for the <code>CDATASection</code> contents.
	 * @return The new <code>CDATASection</code> object.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
	 */
	public CDATASection createCDATASection(final String data) throws DOMException {
		return new DCDATASection(this, data);
	}
	public DCDATASection dsfCreateCDATASection(final String data) throws DOMException {
		return (DCDATASection)createCDATASection(data) ;
	}

	/**
	 * Creates a <code>ProcessingInstruction</code> node given the specified 
	 * name and data strings.
	 * @param target The target part of the processing instruction.Unlike 
	 *   <code>Document.createElementNS</code> or 
	 *   <code>Document.createAttributeNS</code>, no namespace well-formed 
	 *   checking is done on the target name. Applications should invoke 
	 *   <code>Document.normalizeDocument()</code> with the parameter "
	 *   namespaces" set to <code>true</code> in order to ensure that the 
	 *   target name is namespace well-formed. 
	 * @param data The data for the node.
	 * @return The new <code>ProcessingInstruction</code> object.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified target is not an XML 
	 *   name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 *   <br>NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
	 */
	public ProcessingInstruction createProcessingInstruction(
		final String target, final String data) throws DOMException
	{
		if (target == null) {
			chuck("The target for a processing instruction must not be null") ;
		}
		if (data == null) {
			chuck("The data for a processing instruction must not be null") ;
		}
		
		DProcessingInstruction pi = new DProcessingInstruction(this, target, data);
		appendChild(pi) ;
		
		return pi ;
	}
	public DProcessingInstruction dsfCreateProcessingInstruction(
		final String target, final String data) throws DOMException
	{
		return (DProcessingInstruction)createProcessingInstruction(target, data);
	}

	/**
	 * Creates an <code>Attr</code> of the given name. Note that the 
	 * <code>Attr</code> instance can then be set on an <code>Element</code> 
	 * using the <code>setAttributeNode</code> method. 
	 * <br>To create an attribute with a qualified name and namespace URI, use 
	 * the <code>createAttributeNS</code> method.
	 * @param name The name of the attribute.
	 * @return A new <code>Attr</code> object with the <code>nodeName</code> 
	 *   attribute set to <code>name</code>, and <code>localName</code>, 
	 *   <code>prefix</code>, and <code>namespaceURI</code> set to 
	 *   <code>null</code>. The value of the attribute is the empty string.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
	 *   name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 */
	public Attr createAttribute(final String name) throws DOMException {
		return new DAttr(this, name);
	}
	public DAttr dsfCreateAttribute(final String name) throws DOMException {
		return (DAttr)createAttribute(name);
	}
	
	public Attr createAttribute(
		final String name, final String value) throws DOMException
	{
		return new DAttr(this, name, value) ;
	}
	public DAttr dsfCreateAttribute(
		final String name, final String value) throws DOMException
	{
		return (DAttr)createAttribute(name, value) ;
	}

	/**
	 * Creates an <code>EntityReference</code> object. In addition, if the 
	 * referenced entity is known, the child list of the 
	 * <code>EntityReference</code> node is made the same as that of the 
	 * corresponding <code>Entity</code> node.
	 * <p ><b>Note:</b> If any descendant of the <code>Entity</code> node has 
	 * an unbound namespace prefix, the corresponding descendant of the 
	 * created <code>EntityReference</code> node is also unbound; (its 
	 * <code>namespaceURI</code> is <code>null</code>). The DOM Level 2 and 
	 * 3 do not support any mechanism to resolve namespace prefixes in this 
	 * case.
	 * @param name The name of the entity to reference.Unlike 
	 *   <code>Document.createElementNS</code> or 
	 *   <code>Document.createAttributeNS</code>, no namespace well-formed 
	 *   checking is done on the entity name. Applications should invoke 
	 *   <code>Document.normalizeDocument()</code> with the parameter "
	 *   namespaces" set to <code>true</code> in order to ensure that the 
	 *   entity name is namespace well-formed. 
	 * @return The new <code>EntityReference</code> object.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML 
	 *   name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 *   <br>NOT_SUPPORTED_ERR: Raised if this document is an HTML document.
	 */
	public EntityReference createEntityReference(final String name)
		throws DOMException
	{
		return new DEntityReference(this, name);
	}
	public DEntityReference dsfCreateEntityReference(final String name)
		throws DOMException
	{
		return (DEntityReference)createEntityReference(name);
	}

	/**
	 * This attribute returns the text content of this node and its 
	 * descendants. When it is defined to be <code>null</code>, setting it 
	 * has no effect. On setting, any possible children this node may have 
	 * are removed and, if it the new string is not empty or 
	 * <code>null</code>, replaced by a single <code>Text</code> node 
	 * containing the string this attribute is set to. 
	 * <br> On getting, no serialization is performed, the returned string 
	 * does not contain any markup. No whitespace normalization is performed 
	 * and the returned string does not contain the white spaces in element 
	 * content (see the attribute 
	 * <code>Text.isElementContentWhitespace</code>). Similarly, on setting, 
	 * no parsing is performed either, the input string is taken as pure 
	 * textual content. 
	 * <br>The string returned is made of the text content of this node 
	 * depending on its type, as defined below: 
	 * <table border='1' cellpadding='3'>
	 * <tr>
	 * <th>Node type</th>
	 * <th>Content</th>
	 * </tr>
	 * <tr>
	 * <td valign='top' rowspan='1' colspan='1'>
	 * ELEMENT_NODE, ATTRIBUTE_NODE, ENTITY_NODE, ENTITY_REFERENCE_NODE, 
	 * DOCUMENT_FRAGMENT_NODE</td>
	 * <td valign='top' rowspan='1' colspan='1'>concatenation of the <code>textContent</code> 
	 * attribute value of every child node, excluding COMMENT_NODE and 
	 * PROCESSING_INSTRUCTION_NODE nodes. This is the empty string if the 
	 * node has no children.</td>
	 * </tr>
	 * <tr>
	 * <td valign='top' rowspan='1' colspan='1'>TEXT_NODE, CDATA_SECTION_NODE, COMMENT_NODE, 
	 * PROCESSING_INSTRUCTION_NODE</td>
	 * <td valign='top' rowspan='1' colspan='1'><code>nodeValue</code></td>
	 * </tr>
	 * <tr>
	 * <td valign='top' rowspan='1' colspan='1'>DOCUMENT_NODE, 
	 * DOCUMENT_TYPE_NODE, NOTATION_NODE</td>
	 * <td valign='top' rowspan='1' colspan='1'><em>null</em></td>
	 * </tr>
	 * </table>
	 * @exception DOMException
	 *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than 
	 *   fit in a <code>DOMString</code> variable on the implementation 
	 *   platform.
	 *
	 * @since DOM Level 3
	 */
	@Override
	public String getTextContent() throws DOMException {
		return null ;
	}
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
	public NodeList getElementsByTagName(final String tagName) {
		final Element root = this.getDocumentElement();
		if (root == null) {
			return new DNodeList(this, 0);
		}

		return root.getElementsByTagName(tagName);
	}

	/**
	 * Imports a node from another document to this document, without altering 
	 * or removing the source node from the original document; this method 
	 * creates a new copy of the source node. The returned node has no 
	 * parent; (<code>parentNode</code> is <code>null</code>).
	 * <br>For all nodes, importing a node creates a node object owned by the 
	 * importing document, with attribute values identical to the source 
	 * node's <code>nodeName</code> and <code>nodeType</code>, plus the 
	 * attributes related to namespaces (<code>prefix</code>, 
	 * <code>localName</code>, and <code>namespaceURI</code>). As in the 
	 * <code>cloneNode</code> operation, the source node is not altered. 
	 * User data associated to the imported node is not carried over. 
	 * However, if any <code>UserDataHandlers</code> has been specified 
	 * along with the associated data these handlers will be called with the 
	 * appropriate parameters before this method returns.
	 * <br>Additional information is copied as appropriate to the 
	 * <code>nodeType</code>, attempting to mirror the behavior expected if 
	 * a fragment of XML or HTML source was copied from one document to 
	 * another, recognizing that the two documents may have different DTDs 
	 * in the XML case. The following list describes the specifics for each 
	 * type of node. 
	 * <dl>
	 * <dt>ATTRIBUTE_NODE</dt>
	 * <dd>The <code>ownerElement</code> attribute 
	 * is set to <code>null</code> and the <code>specified</code> flag is 
	 * set to <code>true</code> on the generated <code>Attr</code>. The 
	 * descendants of the source <code>Attr</code> are recursively imported 
	 * and the resulting nodes reassembled to form the corresponding subtree.
	 * Note that the <code>deep</code> parameter has no effect on 
	 * <code>Attr</code> nodes; they always carry their children with them 
	 * when imported.</dd>
	 * <dt>DOCUMENT_FRAGMENT_NODE</dt>
	 * <dd>If the <code>deep</code> option 
	 * was set to <code>true</code>, the descendants of the source 
	 * <code>DocumentFragment</code> are recursively imported and the 
	 * resulting nodes reassembled under the imported 
	 * <code>DocumentFragment</code> to form the corresponding subtree. 
	 * Otherwise, this simply generates an empty 
	 * <code>DocumentFragment</code>.</dd>
	 * <dt>DOCUMENT_NODE</dt>
	 * <dd><code>Document</code> 
	 * nodes cannot be imported.</dd>
	 * <dt>DOCUMENT_TYPE_NODE</dt>
	 * <dd><code>DocumentType</code> 
	 * nodes cannot be imported.</dd>
	 * <dt>ELEMENT_NODE</dt>
	 * <dd><em>Specified</em> attribute nodes of the source element are imported, and the generated 
	 * <code>Attr</code> nodes are attached to the generated 
	 * <code>Element</code>. Default attributes are <em>not</em> copied, though if the document being imported into defines default 
	 * attributes for this element name, those are assigned. If the 
	 * <code>importNode</code> <code>deep</code> parameter was set to 
	 * <code>true</code>, the descendants of the source element are 
	 * recursively imported and the resulting nodes reassembled to form the 
	 * corresponding subtree.</dd>
	 * <dt>ENTITY_NODE</dt>
	 * <dd><code>Entity</code> nodes can be 
	 * imported, however in the current release of the DOM the 
	 * <code>DocumentType</code> is readonly. Ability to add these imported 
	 * nodes to a <code>DocumentType</code> will be considered for addition 
	 * to a future release of the DOM.On import, the <code>publicId</code>, 
	 * <code>systemId</code>, and <code>notationName</code> attributes are 
	 * copied. If a <code>deep</code> import is requested, the descendants 
	 * of the the source <code>Entity</code> are recursively imported and 
	 * the resulting nodes reassembled to form the corresponding subtree.</dd>
	 * <dt>
	 * ENTITY_REFERENCE_NODE</dt>
	 * <dd>Only the <code>EntityReference</code> itself is 
	 * copied, even if a <code>deep</code> import is requested, since the 
	 * source and destination documents might have defined the entity 
	 * differently. If the document being imported into provides a 
	 * definition for this entity name, its value is assigned.</dd>
	 * <dt>NOTATION_NODE</dt>
	 * <dd>
	 * <code>Notation</code> nodes can be imported, however in the current 
	 * release of the DOM the <code>DocumentType</code> is readonly. Ability 
	 * to add these imported nodes to a <code>DocumentType</code> will be 
	 * considered for addition to a future release of the DOM.On import, the 
	 * <code>publicId</code> and <code>systemId</code> attributes are copied.
	 * Note that the <code>deep</code> parameter has no effect on this type 
	 * of nodes since they cannot have any children.</dd>
	 * <dt>
	 * PROCESSING_INSTRUCTION_NODE</dt>
	 * <dd>The imported node copies its 
	 * <code>target</code> and <code>data</code> values from those of the 
	 * source node.Note that the <code>deep</code> parameter has no effect 
	 * on this type of nodes since they cannot have any children.</dd>
	 * <dt>TEXT_NODE, 
	 * CDATA_SECTION_NODE, COMMENT_NODE</dt>
	 * <dd>These three types of nodes inheriting 
	 * from <code>CharacterData</code> copy their <code>data</code> and 
	 * <code>length</code> attributes from those of the source node.Note 
	 * that the <code>deep</code> parameter has no effect on these types of 
	 * nodes since they cannot have any children.</dd>
	 * </dl> 
	 * @param importedNode The node to import.
	 * @param deep If <code>true</code>, recursively import the subtree under 
	 *   the specified node; if <code>false</code>, import only the node 
	 *   itself, as explained above. This has no effect on nodes that cannot 
	 *   have any children, and on <code>Attr</code>, and 
	 *   <code>EntityReference</code> nodes.
	 * @return The imported node that belongs to this <code>Document</code>.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: Raised if the type of node being imported is not 
	 *   supported.
	 *   <br>INVALID_CHARACTER_ERR: Raised if one of the imported names is not 
	 *   an XML name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute. This may happen when 
	 *   importing an XML 1.1 [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>] element 
	 *   into an XML 1.0 document, for instance.
	 * @since DOM Level 2
	 */
	public Node importNode(
		final Node source, final boolean deep) throws DOMException
	{
		return importNode((DNode)source, deep, true, null);
	}
	public DNode dsfImportNode(
		final Node source, final boolean deep) throws DOMException
	{
		return (DNode)importNode(source, deep);
	}
	
	/**
	 * Creates an element of the given qualified name and namespace URI. <br>
	 * Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML
	 * Namespaces</a>] , applications must use the value <code>null</code> as
	 * the namespaceURI parameter for methods if they wish to have no namespace.
	 * 
	 * @param namespaceURI
	 *            The namespace URI of the element to create.
	 * @param qualifiedName
	 *            The qualified name of the element type to instantiate.
	 * @return A new Element object with the following
	 *         Attribute  			Value
	 *         Node.nodeName 		qualifiedName
	 *         Node.namespaceURI 	namespaceURI
	 *         Node.prefix 			prefix, extracted from qualifiedName, or null if there is no prefix
	 *         Node.localName 		local name, extracted from qualifiedName
	 *         Element.tagName 		qualifiedName
	 *         
	 * @exception DOMException
	 *                INVALID_CHARACTER_ERR: Raised if the specified qualified name contains an illegal 
	 *                character, per the XML 1.0 specification .
	 *                NAMESPACE_ERR: Raised if the qualifiedName is malformed per the Namespaces in XML 
	 *                specification, if the qualifiedName has a prefix and the namespaceURI is null, 
	 *                or if the qualifiedName has a prefix that is "xml" and the namespaceURI 
	 *                is different from " http://www.w3.org/XML/1998/namespace" .
	 *                NOT_SUPPORTED_ERR: Always thrown if the current document does not support 
	 *                the "XML" feature, since namespaces were defined by XML.
	 * @since DOM Level 2
	 */
	public Element createElementNS(
		final String namespaceURI, final String qualifiedName) throws DOMException
	{
		return new DElement(this, namespaceURI, qualifiedName);	
	}
	public DElement dsfCreateElementNS(
		final String namespaceURI, final String qualifiedName) throws DOMException
	{
		return (DElement)createElementNS(namespaceURI, qualifiedName);	
	}
	
	/**
	 * 
	 * @param namespace
	 * @param localName
	 * @return A new Element object with the following
	 *         Node.nodeName 		localName with prefix extracted from namespace
	 *         Node.namespace	 	dsfNamespace
	 *         Node.prefix 			prefix, extracted from namespace, or null if there is no prefix
	 *         Node.localName 		local name, 
	 *         Element.tagName 		localName with prefix extracted from namespace
	 * @throws DOMException
	 */
	public Element createElementNS(
		DNamespace namespace, String localName) throws DOMException
	{
		return new DElement(this, namespace, localName);	
	}
	public DElement dsfCreateElementNS(
		DNamespace namespace, String localName) throws DOMException
	{
		return (DElement)createElementNS(namespace, localName);	
	}

	/**
	 * Creates an attribute of the given qualified name and namespace URI.
	 * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 * , applications must use the value <code>null</code> as the 
	 * <code>namespaceURI</code> parameter for methods if they wish to have 
	 * no namespace.
	 * @param namespaceURI The namespace URI of the attribute to create.
	 * @param qualifiedName The qualified name of the attribute to 
	 *   instantiate.
	 * @return A new <code>Attr</code> object with the following attributes:
	 * 	Attribute  				Value
	 *  Node.nodeName 			qualifiedName
	 *  Node.namespaceURI 		namespaceURI
	 *  Node.prefix 			prefix, extracted from qualifiedName, or null if there is no prefix
	 *  Node.localName 			local name, extracted from qualifiedName
	 *  Attr.name 				qualifiedName
	 *  Node.nodeValue 			the empty string
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified 
	 *   <code>qualifiedName</code> is not an XML name according to the XML 
	 *   version in use specified in the <code>Document.xmlVersion</code> 
	 *   attribute.
	 *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is a 
	 *   malformed qualified name, if the <code>qualifiedName</code> has a 
	 *   prefix and the <code>namespaceURI</code> is <code>null</code>, if 
	 *   the <code>qualifiedName</code> has a prefix that is "xml" and the 
	 *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
	 *   http://www.w3.org/XML/1998/namespace</a>", if the <code>qualifiedName</code> or its prefix is "xmlns" and the 
	 *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if the <code>namespaceURI</code> is "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>" and neither the <code>qualifiedName</code> nor its prefix is "xmlns".
	 *   <br>NOT_SUPPORTED_ERR: Always thrown if the current document does not 
	 *   support the <code>"XML"</code> feature, since namespaces were 
	 *   defined by XML.
	 * @since DOM Level 2
	 */
	public Attr createAttributeNS(String namespaceURI, String qualifiedName)
		throws DOMException
	{
		throw new DsfDomNotSupportedRuntimeException(
			"createAttributeNS(String namespaceURI, String qualifiedName)");
	}

	/**
	 * Returns a <code>NodeList</code> of all the <code>Elements</code> with a 
	 * given local name and namespace URI in the order in which they are encountered 
	 * in a preorder traversal of the Document tree. 
	 * @param namespaceURI The namespace URI of the elements to match on. The 
	 *   special value <code>"*"</code> matches all namespaces.
	 * @param localName The local name of the elements to match on. The 
	 *   special value "*" matches all local names.
	 * @return A new <code>NodeList</code> object containing all the matched 
	 *   <code>Elements</code>.
	 * @since DOM Level 2
	 */
	public NodeList getElementsByTagNameNS(final String namespaceURI, final String localName) {
		final Element root = this.getDocumentElement();
		if (root == null) {
			return new DNodeList(this, 0);
		}

		return root.getElementsByTagNameNS(namespaceURI, localName);
	}

	/**
	 * Returns a <code>NodeList</code> of all the <code>Elements</code> with a 
	 * given namespace and localName in the order in which they are encountered 
	 * in a preorder traversal of the Document tree. 
	 * @param namespace
	 * @param localName
	 * @return
	 */
	public NodeList dsfGetElementsByTagNameNS(final DNamespace namespace, final String localName) {
		final DElement root = (DElement)this.getDocumentElement();
		if (root == null) {
			return new DNodeList(this, 0);
		}

		return root.dsfGetElementsByTagNameNS(namespace, localName);
	}
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
	public Element getElementById(final String elementId) {
		//    	throw new DsfDomNotSupportedRuntimeException(
		//			"getElementById(String elementId)") ;
		final DElement element = m_idToElement.get(elementId);
		return element;
	}
	public DElement dsfGetElementById(final String elementId) {
		return (DElement)getElementById(elementId) ;
	}
	
	/**
	 * An attribute specifying the encoding used for this document at the time 
	 * of the parsing. This is <code>null</code> when it is not known, such 
	 * as when the <code>Document</code> was created in memory.
	 * @since DOM Level 3
	 */
	public String getInputEncoding() {
		return m_inputEncoding;
	}

	/**
	 * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the encoding of this document. This is <code>null</code> when 
	 * unspecified or when it is not known, such as when the 
	 * <code>Document</code> was created in memory.
	 * @since DOM Level 3
	 */
	public String getXmlEncoding() {
		return m_xmlEncoding;
	}

	/**
	 * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, whether this document is standalone. This is <code>false</code> when 
	 * unspecified.
	 * <p ><b>Note:</b>  No verification is done on the value when setting 
	 * this attribute. Applications should use 
	 * <code>Document.normalizeDocument()</code> with the "validate" 
	 * parameter to verify if the value matches the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-rmd'>validity 
	 * constraint for standalone document declaration</a> as defined in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. 
	 * @since DOM Level 3
	 */
	public boolean getXmlStandalone() {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 * An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, whether this document is standalone. This is <code>false</code> when 
	 * unspecified.
	 * <p ><b>Note:</b>  No verification is done on the value when setting 
	 * this attribute. Applications should use 
	 * <code>Document.normalizeDocument()</code> with the "validate" 
	 * parameter to verify if the value matches the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-rmd'>validity 
	 * constraint for standalone document declaration</a> as defined in [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. 
	 * @exception DOMException
	 *    NOT_SUPPORTED_ERR: Raised if this document does not support the 
	 *   "XML" feature. 
	 * @since DOM Level 3
	 */
	public void setXmlStandalone(final boolean xmlStandalone) throws DOMException {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 *  An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the version number of this document. If there is no declaration and if 
	 * this document supports the "XML" feature, the value is 
	 * <code>"1.0"</code>. If this document does not support the "XML" 
	 * feature, the value is always <code>null</code>. Changing this 
	 * attribute will affect methods that check for invalid characters in 
	 * XML names. Application should invoke 
	 * <code>Document.normalizeDocument()</code> in order to check for 
	 * invalid characters in the <code>Node</code>s that are already part of 
	 * this <code>Document</code>. 
	 * <br> DOM applications may use the 
	 * <code>DOMImplementation.hasFeature(feature, version)</code> method 
	 * with parameter values "XMLVersion" and "1.0" (respectively) to 
	 * determine if an implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. DOM 
	 * applications may use the same method with parameter values 
	 * "XMLVersion" and "1.1" (respectively) to determine if an 
	 * implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]. In both 
	 * cases, in order to support XML, an implementation must also support 
	 * the "XML" feature defined in this specification. <code>Document</code>
	 *  objects supporting a version of the "XMLVersion" feature must not 
	 * raise a <code>NOT_SUPPORTED_ERR</code> exception for the same version 
	 * number when using <code>Document.xmlVersion</code>. 
	 * @since DOM Level 3
	 */
	public String getXmlVersion() {
		return m_xmlVersion==null?"1.0":m_xmlVersion ;
	}

	/**
	 *  An attribute specifying, as part of the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#NT-XMLDecl'>XML declaration</a>, the version number of this document. If there is no declaration and if 
	 * this document supports the "XML" feature, the value is 
	 * <code>"1.0"</code>. If this document does not support the "XML" 
	 * feature, the value is always <code>null</code>. Changing this 
	 * attribute will affect methods that check for invalid characters in 
	 * XML names. Application should invoke 
	 * <code>Document.normalizeDocument()</code> in order to check for 
	 * invalid characters in the <code>Node</code>s that are already part of 
	 * this <code>Document</code>. 
	 * <br> DOM applications may use the 
	 * <code>DOMImplementation.hasFeature(feature, version)</code> method 
	 * with parameter values "XMLVersion" and "1.0" (respectively) to 
	 * determine if an implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. DOM 
	 * applications may use the same method with parameter values 
	 * "XMLVersion" and "1.1" (respectively) to determine if an 
	 * implementation supports [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]. In both 
	 * cases, in order to support XML, an implementation must also support 
	 * the "XML" feature defined in this specification. <code>Document</code>
	 *  objects supporting a version of the "XMLVersion" feature must not 
	 * raise a <code>NOT_SUPPORTED_ERR</code> exception for the same version 
	 * number when using <code>Document.xmlVersion</code>. 
	 * @exception DOMException
	 *    NOT_SUPPORTED_ERR: Raised if the version is set to a value that is 
	 *   not supported by this <code>Document</code> or if this document 
	 *   does not support the "XML" feature. 
	 * @since DOM Level 3
	 */
	public void setXmlVersion(final String xmlVersion) throws DOMException {
		
		if(xmlVersion != null && (XML_VERSIONS.indexOf(xmlVersion) != -1))
		{
			//we need to change the flag value only --
			// when the version set is different than already set.
			//if(!getXmlVersion().equals(xmlVersion)){
				//xmlVersionChanged = true ;
				//change the normalization value back to false
				//setNormalized(false);
				m_xmlVersion = xmlVersion;
			//}
		} else{
			//NOT_SUPPORTED_ERR: Raised if the vesion is set to a value that is not supported by
			//this document
			//we dont support any other XML version			
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "unknown xml version " + xmlVersion);
		}
		if(m_xmlVersion.equals("1.1")){
			m_bDsfXml11Version = true;
		} else{
			m_bDsfXml11Version = false;
		}
	}

	/**
	 * An attribute specifying whether error checking is enforced or not. When 
	 * set to <code>false</code>, the implementation is free to not test 
	 * every possible error case normally defined on DOM operations, and not 
	 * raise any <code>DOMException</code> on DOM operations or report 
	 * errors while using <code>Document.normalizeDocument()</code>. In case 
	 * of error, the behavior is undefined. This attribute is 
	 * <code>true</code> by default.
	 * @since DOM Level 3
	 */
	public boolean getStrictErrorChecking() {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 * An attribute specifying whether error checking is enforced or not. When 
	 * set to <code>false</code>, the implementation is free to not test 
	 * every possible error case normally defined on DOM operations, and not 
	 * raise any <code>DOMException</code> on DOM operations or report 
	 * errors while using <code>Document.normalizeDocument()</code>. In case 
	 * of error, the behavior is undefined. This attribute is 
	 * <code>true</code> by default.
	 * @since DOM Level 3
	 */
	public void setStrictErrorChecking(final boolean strictErrorChecking) {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 *  The location of the document or <code>null</code> if undefined or if 
	 * the <code>Document</code> was created using 
	 * <code>DOMImplementation.createDocument</code>. No lexical checking is 
	 * performed when setting this attribute; this could result in a 
	 * <code>null</code> value returned when using <code>Node.baseURI</code>
	 * . 
	 * <br> Beware that when the <code>Document</code> supports the feature 
	 * 'HTML' [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
	 * , the href attribute of the HTML BASE element takes precedence over 
	 * this attribute when computing <code>Node.baseURI</code>. 
	 * @since DOM Level 3
	 */
	public String getDocumentURI() {
		return m_dsfDocumentURI!=null?m_dsfDocumentURI.getNamespaceKey():null;
	}

	  /**
     * get document uri DNamespace reference
     * @return
     */
    public DNamespace getDsfDocumentURI() {
        return m_dsfDocumentURI;
    }
	/**
	 *  The location of the document or <code>null</code> if undefined or if 
	 * the <code>Document</code> was created using 
	 * <code>DOMImplementation.createDocument</code>. No lexical checking is 
	 * performed when setting this attribute; this could result in a 
	 * <code>null</code> value returned when using <code>Node.baseURI</code>
	 * . 
	 * <br> Beware that when the <code>Document</code> supports the feature 
	 * 'HTML' [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
	 * , the href attribute of the HTML BASE element takes precedence over 
	 * this attribute when computing <code>Node.baseURI</code>. 
	 * @since DOM Level 3
	 */
	public void setDocumentURI(final String documentURI) {			
		if (documentURI ==null){
			throw new DOMException(DOMException.NAMESPACE_ERR, "namespaceUri is null.");
		}
		
		m_dsfDocumentURI = DNamespace.getNamespace(null, documentURI);
	}
	
	public DDocument setDsfDocumentURI(final DNamespace namespace) {		
		m_dsfDocumentURI = namespace;
		return this;
	}

	/**
	 *  Attempts to adopt a node from another document to this document. If 
	 * supported, it changes the <code>ownerDocument</code> of the source 
	 * node, its children, as well as the attached attribute nodes if there 
	 * are any. If the source node has a parent it is first removed from the 
	 * child list of its parent. This effectively allows moving a subtree 
	 * from one document to another (unlike <code>importNode()</code> which 
	 * create a copy of the source node instead of moving it). When it 
	 * fails, applications should use <code>Document.importNode()</code> 
	 * instead. Note that if the adopted node is already part of this 
	 * document (i.e. the source and target document are the same), this 
	 * method still has the effect of removing the source node from the 
	 * child list of its parent, if any. The following list describes the 
	 * specifics for each type of node. 
	 * ATTRIBUTE_NODE
	 *     The ownerElement attribute is set to null and the specified flag is set to true on the adopted Attr.
	 *     The descendants of the source Attr are recursively adopted.
	 * DOCUMENT_FRAGMENT_NODE
	 *     The descendants of the source node are recursively adopted.
	 * DOCUMENT_NODE
	 *     Document nodes cannot be adopted.
	 * DOCUMENT_TYPE_NODE
	 *     DocumentType nodes cannot be adopted.
	 * ELEMENT_NODE
	 *     Specified attribute nodes of the source element are adopted. Default attributes
	 *      are discarded, though if the document being adopted into defines default attributes 
	 *      for this element name, those are assigned. The descendants of the source element are recursively adopted.
	 * ENTITY_NODE
	 *     Entity nodes cannot be adopted.
	 * ENTITY_REFERENCE_NODE
	 *     Only the EntityReference node itself is adopted, the descendants are discarded, 
	 *     since the source and destination documents might have defined the entity differently. 
	 *     If the document being imported into provides a definition for this entity name, its value is assigned.
	 * NOTATION_NODE
	 *     Notation nodes cannot be adopted.
	 * PROCESSING_INSTRUCTION_NODE, TEXT_NODE, CDATA_SECTION_NODE, COMMENT_NODE
	 *     These nodes can all be adopted. No specifics. 
	 * <p ><b>Note:</b>  Since it does not create new nodes unlike the 
	 * <code>Document.importNode()</code> method, this method does not raise 
	 * an <code>INVALID_CHARACTER_ERR</code> exception, and applications 
	 * should use the <code>Document.normalizeDocument()</code> method to 
	 * check if an imported name is not an XML name according to the XML 
	 * version in use. 
	 * @param source The node to move into this document.
	 * @return The adopted node, or <code>null</code> if this operation 
	 *   fails, such as when the source node comes from a different 
	 *   implementation.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: Raised if the source node is of type 
	 *   <code>DOCUMENT</code>, <code>DOCUMENT_TYPE</code>.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised when the source node is 
	 *   readonly.
	 * @since DOM Level 3
	 */
	public Node adoptNode(final Node source) throws DOMException {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 *  The configuration used when <code>Document.normalizeDocument()</code> 
	 * is invoked. 
	 * @since DOM Level 3
	 */
	public DOMConfiguration getDomConfig() {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 *  This method acts as if the document was going through a save and load 
	 * cycle, putting the document in a "normal" form. As a consequence, 
	 * this method updates the replacement tree of 
	 * <code>EntityReference</code> nodes and normalizes <code>Text</code> 
	 * nodes, as defined in the method <code>Node.normalize()</code>. 
	 * <br> Otherwise, the actual result depends on the features being set on 
	 * the <code>Document.domConfig</code> object and governing what 
	 * operations actually take place. Noticeably this method could also 
	 * make the document namespace well-formed according to the algorithm 
	 * described in , check the character normalization, remove the 
	 * <code>CDATASection</code> nodes, etc. See 
	 * <code>DOMConfiguration</code> for details. 
	 * <pre>// Keep in the document 
	 * the information defined // in the XML Information Set (Java example) 
	 * DOMConfiguration docConfig = myDocument.getDomConfig(); 
	 * docConfig.setParameter("infoset", Boolean.TRUE); 
	 * myDocument.normalizeDocument();</pre>
	 * 
	 * <br>Mutation events, when supported, are generated to reflect the 
	 * changes occurring on the document.
	 * <br> If errors occur during the invocation of this method, such as an 
	 * attempt to update a read-only node or a <code>Node.nodeName</code> 
	 * contains an invalid character according to the XML version in use, 
	 * errors or warnings (<code>DOMError.SEVERITY_ERROR</code> or 
	 * <code>DOMError.SEVERITY_WARNING</code>) will be reported using the 
	 * <code>DOMErrorHandler</code> object associated with the "error-handler
	 * " parameter. Note this method might also report fatal errors (
	 * <code>DOMError.SEVERITY_FATAL_ERROR</code>) if an implementation 
	 * cannot recover from an error. 
	 * @since DOM Level 3
	 */
	public void normalizeDocument() {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 * Rename an existing node of type <code>ELEMENT_NODE</code> or 
	 * <code>ATTRIBUTE_NODE</code>.
	 * <br>When possible this simply changes the name of the given node, 
	 * otherwise this creates a new node with the specified name and 
	 * replaces the existing node with the new node as described below.
	 * <br>If simply changing the name of the given node is not possible, the 
	 * following operations are performed: a new node is created, any 
	 * registered event listener is registered on the new node, any user 
	 * data attached to the old node is removed from that node, the old node 
	 * is removed from its parent if it has one, the children are moved to 
	 * the new node, if the renamed node is an <code>Element</code> its 
	 * attributes are moved to the new node, the new node is inserted at the 
	 * position the old node used to have in its parent's child nodes list 
	 * if it has one, the user data that was attached to the old node is 
	 * attached to the new node.
	 * <br>When the node being renamed is an <code>Element</code> only the 
	 * specified attributes are moved, default attributes originated from 
	 * the DTD are updated according to the new element name. In addition, 
	 * the implementation may update default attributes from other schemas. 
	 * Applications should use <code>Document.normalizeDocument()</code> to 
	 * guarantee these attributes are up-to-date.
	 * <br>When the node being renamed is an <code>Attr</code> that is 
	 * attached to an <code>Element</code>, the node is first removed from 
	 * the <code>Element</code> attributes map. Then, once renamed, either 
	 * by modifying the existing node or creating a new one as described 
	 * above, it is put back.
	 * <br>In addition,
	 * <ul>
	 * <li> a user data event <code>NODE_RENAMED</code> is fired, 
	 * </li>
	 * <li> 
	 * when the implementation supports the feature "MutationNameEvents", 
	 * each mutation operation involved in this method fires the appropriate 
	 * event, and in the end the event {
	 * <code>http://www.w3.org/2001/xml-events</code>, 
	 * <code>DOMElementNameChanged</code>} or {
	 * <code>http://www.w3.org/2001/xml-events</code>, 
	 * <code>DOMAttributeNameChanged</code>} is fired. 
	 * </li>
	 * </ul>
	 * @param n The node to rename.
	 * @param namespaceURI The new namespace URI.
	 * @param qualifiedName The new qualified name.
	 * @return The renamed node. This is either the specified node or the new 
	 *   node that was created to replace the specified node.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: Raised when the type of the specified node is 
	 *   neither <code>ELEMENT_NODE</code> nor <code>ATTRIBUTE_NODE</code>, 
	 *   or if the implementation does not support the renaming of the 
	 *   document element.
	 *   <br>INVALID_CHARACTER_ERR: Raised if the new qualified name is not an 
	 *   XML name according to the XML version in use specified in the 
	 *   <code>Document.xmlVersion</code> attribute.
	 *   <br>WRONG_DOCUMENT_ERR: Raised when the specified node was created 
	 *   from a different document than this document.
	 *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is a 
	 *   malformed qualified name, if the <code>qualifiedName</code> has a 
	 *   prefix and the <code>namespaceURI</code> is <code>null</code>, or 
	 *   if the <code>qualifiedName</code> has a prefix that is "xml" and 
	 *   the <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
	 *   http://www.w3.org/XML/1998/namespace</a>" [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 *   . Also raised, when the node being renamed is an attribute, if the 
	 *   <code>qualifiedName</code>, or its prefix, is "xmlns" and the 
	 *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>".
	 * @since DOM Level 3
	 */
	public Node renameNode(final Node n, final String namespaceURI, final String name)
		throws DOMException 
	{
	   throw new DsfDomLevelNotSupportedException(3);
	}

	//	@Override
	public DocumentType createDocumentType(
		final String qualifiedName,
		final String publicId,
		final String systemId)
	{
		DDocumentType dt = new DDocumentType(this, qualifiedName, publicId, systemId);
//		add(dt) ;
		return dt ;
	}
	public DDocumentType dsfCreateDocumentType(
		final String qualifiedName,
		final String publicId,
		final String systemId)
	{
		return (DDocumentType)createDocumentType(qualifiedName, publicId, systemId);
	}

	//	@Override
	//	public Attr createAttributeNS(
	//		String namespaceURI,
	//		String qualifiedName,
	//		String localpart)
	//	{
	//		throw new DsfDomNotSupportedRuntimeException("name spaces not supported");
	//	}
	public Entity createEntity(final String name) {
		return new DEntity(this, name);
	}
	public DEntity dsfCreateEntity(final String name) {
		return (DEntity)createEntity(name) ;
	}

	public Notation createNotation(final String name) {
		return new DNotation(this, name);
	}
	public DNotation dsfCreateNotation(final String name) {
		return (DNotation)createNotation(name) ;
	}

	//	@Override
	//	public Node appendChild(Node newChild) throws DOMException {
	//		checkChildNode(newChild);
	//		return super.appendChild(newChild);
	//	}
	//
	//	@Override
	//	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
	//		checkChildNode(newChild);
	//		return super.insertBefore(newChild, refChild);
	//	}
	@Override
	protected void checkChildForAdd(final Node newChild) {
		super.checkChildForAdd(newChild);
		if (newChild instanceof DocumentType) {
			if (getDoctype() != null) {
				throw new DOMException(
					DOMException.HIERARCHY_REQUEST_ERR,
					"HIERARCHY_REQUEST_ERR: Document already has a DocumentType.");
			}
		} 
		else if (newChild instanceof Element) {
			if (getDocumentElement() != null) {
				throw new DOMException(
					DOMException.HIERARCHY_REQUEST_ERR,
					"HIERARCHY_REQUEST_ERR: Document already has a Document Element.");
			}
		}
	}

	@Override
	protected void postChildAdd(final DNode node) {
		super.postChildAdd(node);
//		if (node instanceof DocumentType) {
//			final DDocumentType o = (DDocumentType) node;
////			m_docType = o;
//		} 
//		else if (node instanceof Element) {
//			final DElement o = (DElement) node;
////			m_documentElement = o;
//		}
	}

	@Override
	public Node removeChild(final Node oldChild) throws DOMException {
		if (oldChild == null) {
			throw new DOMException(
				DOMException.VALIDATION_ERR, "removeChild() does not expect a null") ;
		}
		
		// Need to check for DocumentType since it is NOT a normal child
		// and would fail on the super.removeChild(oldChild)
//		if (oldChild instanceof DocumentType) {
//			m_docType = null ;
//			return oldChild ;
//		}
		
		final Node result = super.removeChild(oldChild);
		if (result == null) {
			return result;
		}
//		if (oldChild instanceof Element) {
//			m_documentElement = null;
//		}
		return result;
	}

	@Override
	public final short getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	void putIdentifier(final String id, final DElement element) {
		final DElement old = m_idToElement.get(id);
		//go to schema
		//if (getDsfRelationshipVerifier().enforceUniqueElementId()) {
			if (old != null && old != element) {
				throw new DOMException(DOMException.VALIDATION_ERR,
					"duplicate ID of " + id);
			}
		//}
		m_idToElement.put(id, element);
		//m_elementToId.put(element, id);
	}

	void removeIdentifier(final String id) {
		m_idToElement.remove(id);
//		if (element != null) {
//			m_elementToId.remove(element);
//		}
	}
	
	void removeIdentifiedElement(final DNode node) {
		if (node instanceof DElement) {
			//String id = m_elementToId.remove(node);
			String id = ((DElement)node).getAttribute("id");
			if (id != null) {
				m_idToElement.remove(id);
			}
		}
	}

	protected void cloneNode(final DDocument copyDoc) {
		// FIXME: please note that this algorithm does not take into account an
		// element that might have two attributes marked as ID attributes.
		final Map<DElement, String> elementToId =
			new HashMap<DElement,String>();
		for (final Map.Entry<String,DElement> entry:m_idToElement.entrySet()) {
			elementToId.put(entry.getValue(), entry.getKey());
		}
// MrPperf - don't create children if not needed ;
		if (m_childNodes != null) {
			for (final DNode kid:m_childNodes){
				final DNode importedNode = (DNode)
					copyDoc.importNode(kid, true, true, elementToId);
				
//				// doctypes are already parents from the importNode(...)
//				if (! (importedNode instanceof DocumentType)) { 
					copyDoc.add(importedNode);
//				}
			}
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		final DDocument copyDoc = new DDocument();
		cloneNode(copyDoc);
		return copyDoc;
	}
	/**
	 * Overloaded implementation of DOM's importNode method. This method
	 * provides the core functionality for the public importNode and cloneNode
	 * methods.
	 * 
	 * The reversedIdentifiers parameter is provided for cloneNode to preserve
	 * the document's identifiers. The Hashtable has Elements as the keys and
	 * their identifiers as the values. When an element is being imported, a
	 * check is done for an associated identifier. If one exists, the identifier
	 * is registered with the new, imported element. If reversedIdentifiers is
	 * null, the parameter is not applied.
	 */
	private Node importNode(
		final DNode source,
		boolean deep,
		final boolean cloningDoc,
		final Map<DElement, String> elementToId)
			throws DOMException
	{
		DNode newnode = null;
//		Hashtable userData = null;

		// Sigh. This doesn't work; too many nodes have private data that
		// would have to be manually tweaked. May be able to add local
		// shortcuts to each nodetype. Consider ?????
		// if(source instanceof NodeImpl &&
		// !(source instanceof DocumentImpl))
		// {
		// // Can't clone DocumentImpl since it invokes us...
		// newnode=(NodeImpl)source.cloneNode(false);
		// newnode.ownerDocument=this;
		// }
		// else
//		if (source instanceof NodeX)
//			userData = ((NodeX) source).getUserDataRecord();
		final int type = source.getNodeType();
		switch (type) {
		case ELEMENT_NODE: {
			newnode = importElement((DElement)source,cloningDoc,elementToId);
			break;
		}

		case ATTRIBUTE_NODE: {
//			final boolean domLevel20 = source.getOwnerDocument()
//			.getImplementation().hasFeature("XML", "2.0");
			final boolean domLevel20 = true;

			if (domLevel20) {
				if (source.getLocalName() == null) {
					newnode = (DAttr)createAttribute(source.getNodeName());
				} 
				else {
					newnode = (DAttr)createAttributeNS(source.getNamespaceURI(),
						source.getNodeName());
				}
			} 
			else {
				newnode = (DAttr)createAttribute(source.getNodeName());
			}
			// if source is an AttrImpl from this very same implementation
			// avoid creating the child nodes if possible
//			if (source instanceof DAttr) {
//				DAttr attr = (DAttr) source;
//				if (attr.hasStringValue()) {
//					DAttr newattr = (DAttr) newnode;
//					newattr.setValue(attr.getValue());
//					deep = false;
//				} else {
//					deep = true;
//				}
//			} else {
				// According to the DOM spec the kids carry the value.
				// However, there are non compliant implementations out
				// there that fail to do so. To avoid ending up with no
				// value at all, in this case we simply copy the text value
				// directly.
				if (source.getFirstChild() == null) {
					final DAttr sourceAttr = (DAttr)source;
					final DAttr newAttr = (DAttr)newnode;
					final IValueBinding<?> binding = sourceAttr.getValueBinding();
					if (binding == null) {
						newnode.setNodeValue(source.getNodeValue());
					} else {
						final IValueBinding<Object> newBinding = 
							new SimpleValueBinding<Object>(Object.class, binding.getValue());
						newAttr.setValueBinding(newBinding);
					}
					//CE container uses child-first mode to load dom api which hides dom api from Java 5
					//so org.w3c.dom.Attr.isId causes the method not found exception
					((DAttr)newnode).setId(((DAttr)source).isId());
					deep = false;
				}
				else {
					deep = true;
				}
//			}
			break;
		}

		case TEXT_NODE: {
			newnode = (DText)createTextNode(source.getNodeValue());
			break;
		}

		case CDATA_SECTION_NODE: {
			newnode = (DCDATASection)createCDATASection(source.getNodeValue());
			break;
		}

		case ENTITY_REFERENCE_NODE: {
			newnode = (DEntityReference)createEntityReference(source.getNodeName());
			// the subtree is created according to this doc by the method
			// above, so avoid carrying over original subtree
			deep = false;
			break;
		}

		case ENTITY_NODE: {
			Entity srcentity =(Entity) source;
			DEntity newentity = new DEntity(this, source.getNodeName());
			newentity.setPublicId(srcentity.getPublicId());
			newentity.setSystemId(srcentity.getSystemId());
			newentity.setNotationName(srcentity.getNotationName());
			// Kids carry additional value,
			// allow deep import temporarily
//			newentity.isReadOnly(false);
			newnode = newentity;
			break;
		}

		case PROCESSING_INSTRUCTION_NODE: {
			newnode = (DProcessingInstruction)createProcessingInstruction(
				source.getNodeName(), source.getNodeValue());
			break;
		}

		case COMMENT_NODE: {
			newnode = (DComment)createComment(source.getNodeValue());
			break;
		}

		case DOCUMENT_TYPE_NODE: {
			// unless this is used as part of cloning a Document
			// forbid it for the sake of being compliant to the DOM spec
			if (!cloningDoc) {
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
					"this is not supported");
			}
			DocumentType srcdoctype = (DocumentType) source;
			DDocumentType newdoctype = (DDocumentType)createDocumentType(
				srcdoctype.getNodeName(), 
				srcdoctype.getPublicId(), 
				srcdoctype.getSystemId());
			// Values are on NamedNodeMaps
			NamedNodeMap smap = srcdoctype.getEntities();
			NamedNodeMap tmap = newdoctype.getEntities();
			if (smap != null) {
				for (int i = 0; i < smap.getLength(); i++) {
					final DNode nodeToImport = (DNode)smap.item(i);
					tmap.setNamedItem(
						importNode(nodeToImport,true,true,elementToId));
				}
			}
			smap = srcdoctype.getNotations();
			tmap = newdoctype.getNotations();
			if (smap != null) {
				for (int i = 0; i < smap.getLength(); i++) {
					final DNode nodeToImport = (DNode)smap.item(i);
					tmap.setNamedItem(
						importNode(nodeToImport,true,true,elementToId));
				}
			}

			// NOTE: At this time, the DOM definition of DocumentType
			// doesn't cover Elements and their Attributes. domimpl's
			// extentions in that area will not be preserved, even if
			// copying from domimpl to domimpl. We could special-case
			// that here. Arguably we should. Consider. ?????
			newnode = newdoctype;
			break;
		}

		case DOCUMENT_FRAGMENT_NODE: {
			newnode = (DDocumentFragment)createDocumentFragment();
			// No name, kids carry value
			break;
		}

		case NOTATION_NODE: {
			Notation srcnotation = (Notation) source;
			DNotation newnotation = (DNotation) createNotation(source
				.getNodeName());
			newnotation.setPublicId(srcnotation.getPublicId());
			newnotation.setSystemId(srcnotation.getSystemId());
			// Kids carry additional value
			newnode = newnotation;
			// No name, no value
			break;
		}
		case DOCUMENT_NODE: // Can't import document nodes
		default: { // Unknown node type
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
				"this is not supported");
		}
		}

//		if (userData != null)
//			callUserDataHandlers(source, newnode,
//				UserDataHandler.NODE_IMPORTED, userData);

		// If deep, replicate and attach the kids.
		if (deep) {
// MrPperf - don't create children unless we need to
			if (source.m_childNodes != null) {
				for (DNode srckid:source.m_childNodes){
					newnode.appendChild(
						importNode(srckid, true, cloningDoc, elementToId));
				}
			}
		}
//		if (newnode.getNodeType() == Node.ENTITY_NODE) {
//			((NodeX) newnode).setReadOnly(true, true);
//		}
		return newnode;

	} // importNode(Node,boolean,boolean,Hashtable):Node
	protected DElement createElementForImporting(final DElement source) {
//		final boolean domLevel20 = source.getOwnerDocument()
//		.getImplementation().hasFeature("XML", "2.0");
		final boolean domLevel20 = true;
		if (source.getClass() != DElement.class) {
			return createBastardElement(source);
		}
		// Create element according to namespace support/qualification.
		if (!domLevel20 || source.getLocalName() == null) {
			return (DElement)createElement(source.getNodeName());
		}
		return (DElement)createElementNS(source.getNamespaceURI(),source.getNodeName());
	}
	protected DElement createBastardElement(final DElement source) {
		final Class<?> type = source.getClass();
		final DElement newElement;
		try {
			newElement = (DElement)type.newInstance();
		} 
		catch (IllegalAccessException e) {
			throw new DsfRuntimeException("unable to create instance of " +
				type.getName(), e);
		} 
		catch (InstantiationException e) {
			throw new DsfRuntimeException("unable to create instance of " +
				type.getName(), e);			
		}
		newElement.m_nodeName = source.getNodeName();
		newElement.setDsfOwnerDocument(this);
		if (source.getLocalName() != null) {
			throw new DsfDomNotSupportedRuntimeException(
				"name spaces not supported");
		}
		return newElement;
	}
	protected DElement importElement(
		final DElement source,
		final boolean cloningDoc,
		final Map<DElement, String> reversedIdentifiers)
	{
//		final boolean domLevel20 = source.getOwnerDocument()
//		.getImplementation().hasFeature("XML", "2.0");
		final boolean domLevel20 = true;
		final DElement newElement = createElementForImporting(source);

		// Copy element's attributes, if any.
//		NamedNodeMap sourceAttrs = source.getAttributes();
//		if (sourceAttrs != null) {
//			int length = sourceAttrs.getLength();
//			for (int index = 0; index < length; index++) {
//				final DAttr attr = (DAttr) sourceAttrs.item(index);
//
//				// NOTE: this methods is used for both importingNode
//				// and cloning the document node. In case of the
//				// clonning default attributes should be copied.
//				// But for importNode defaults should be ignored.
//				if (attr.getSpecified() || cloningDoc) {
//					final DAttr newAttr = (DAttr) importNode(attr, true,
//						cloningDoc, reversedIdentifiers);
//
//					// Attach attribute according to namespace
//					// support/qualification.
//					if (!domLevel20 || attr.getLocalName() == null)
//						newElement.setAttributeNode(newAttr);
//					else
//						newElement.setAttributeNodeNS(newAttr);
//				}
//			}
//		}
// MrPperf -- avoid creating attrs unless needed
		if (source.hasAttributes()) {
			final AttributeMap attrs = source.m_attributes;
	//		for (Map.Entry<String, IValueManager> entry:attrs.entrySet()){
			for (Map.Entry<String, DAttr> entry:attrs.attrEntrySet()){
	//			final IValueManager<?> vm = (IValueManager)entry.getValue();
	//			final Object value = vm.getValue();
	//			if (value instanceof DAttr) {
	//				final DAttr attr = (DAttr)value;
					final DAttr attr = entry.getValue();
					if (attr.getSpecified() || cloningDoc) {
						final DAttr newAttr = (DAttr) importNode(attr, true,
							cloningDoc, reversedIdentifiers);
		
						// Attach attribute according to namespace
						// support/qualification.
						if (!domLevel20 || attr.getLocalName() == null) {
							newElement.setAttributeNode(newAttr);
						} else {
							newElement.setAttributeNodeNS(newAttr);
						}
					}
	//			} else {
	//				newElement.getDsfAttributes().put(entry.getKey(), value);
	//			}
			}
		}
		// Register element identifier.
		if (reversedIdentifiers != null) {
			// Does element have an associated identifier?
			String elementId = reversedIdentifiers.get(source);
			if (elementId != null) {
				m_idToElement.put(elementId, newElement);
			}
		}

		return newElement;
	}
	
	@Override
	public DDocument jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		Z z = new Z() ;
		z.format("docType", getDoctype()) ;
		z.format("documentElement", getDocumentElement() == null 
			? null 
			: getDocumentElement().getTagName()) ;
		z.format("length", this.getLength() ) ;
		z.format("ids", m_idToElement.keySet()) ;
		return z.toString() ;
	}
	
//	/**
//	 * Add namespace declaration to this document
//	 * @param declaration
//	 */
//	public DDocument dsfAddNamespaceDeclaration(DNamespace declaration) {		
//		if (declaration!=null){			
//			//System.out.println(declaration.getPrefix() + " " + declaration.getUri());
//			if (m_dsfNamespaceDeclarations == null) {
//				m_dsfNamespaceDeclarations = new ArrayList<DNamespace>();
//			}
//	
//			if (!dsfContainsNamespaceDeclaration(declaration)) {
//				m_dsfNamespaceDeclarations.add(declaration) ;
//			}
//		}
//		return this ;
//	}
	
	/**
	 * return the namespaces declared for this document
	 * @return
	 */
	public Set<DNamespace> getDsfNamespaceDeclarations() {
		if (m_dsfNamespaceDeclarations == null) {
			m_dsfNamespaceDeclarations = new LinkedHashSet<DNamespace>(3) ;
		}		
		return m_dsfNamespaceDeclarations ;
	}

//	/**
//	 * remove a namespace declaration from document
//	 * @param declaration
//	 * @return
//	 */
//	public boolean dsfRemoveDeclaredNamespace(DNamespace declaration) {
//		if (m_dsfNamespaceDeclarations == null) {
//			return false ;
//		}		
//		return m_dsfNamespaceDeclarations.remove(declaration);
//	}
//	
//	/**
//	 * check if this document contains the namespace declaration
//	 * @param declaration
//	 * @return
//	 */
//	public boolean dsfContainsNamespaceDeclaration(DNamespace declaration) {
//		if (declaration==m_dsfDocumentURI 
//				|| (m_dsfNamespaceDeclarations != null &&  m_dsfNamespaceDeclarations.indexOf(declaration)!=-1))
//		{
//			return true;
//		}
//		
//		return false;
//	}
	
	@Override
	public String getBaseURI() {
        return getDocumentURI();
    }
	
	@Override
	public DNamespace getDsfBaseURI() {
        return getDsfDocumentURI();
    }
	
    /**
     * DOM Internal
     * (Was a DOM L3 Core WD public interface method setActualEncoding )
     *
     * An attribute specifying the actual encoding of this document. This is
     * <code>null</code> otherwise.
     * <br> This attribute represents the property [character encoding scheme]
     * defined in .
     */
	public void setInputEncoding(final String value) {
		if (value != null && ENCODEINGS.indexOf(value.toLowerCase()) != -1) {
			m_inputEncoding = value;
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "unsupported InputEncoding " + value);
		}	
	}

    /**
     * DOM Internal
     * (Was a DOM L3 Core WD public interface method setXMLEncoding )
     *
     * An attribute specifying, as part of the XML declaration,
     * the encoding of this document. This is null when unspecified.
     */
	public void setXmlEncoding(final String value) {     
		if (value != null && ENCODEINGS.indexOf(value.toLowerCase()) != -1) {
			m_xmlEncoding = value;
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "unsupported XmlEncoding " + value);
		}
    } 
    
    @Override
    public boolean isDsfXml11Version(){
    	return m_bDsfXml11Version;
    }
        
    /**
     * Return namespace based on given specifiedPrefix
     * if specifiedPrefix is null, return documentUri.
     */
    @Override
    public DNamespace dsfLookupNamespaceURI(final String specifiedPrefix) {
    	if (specifiedPrefix==null){
    		return m_dsfDocumentURI;
    	}
    	if (m_dsfNamespaceDeclarations==null){
    		return null;
    	}
    	for (DNamespace ns: m_dsfNamespaceDeclarations){
    		if (specifiedPrefix.equals(ns.getPrefix())){
    			return ns;
    		}
    	}
    	return null;
    } 
}
