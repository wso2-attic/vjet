/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.client.ActiveObject;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DDocumentFragment;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.jsnative.Document;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.NamedNodeMap;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.UserDataHandler;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class ANode extends ActiveObject implements Node, Cloneable {

	private static final long serialVersionUID = 1L;
	private DNode m_dNode;
	private ADocument m_ownerDocument;
	private ANode m_parentNode;
	protected ANodeList m_childNodes;
	protected transient Map<String, Object> m_userData;
	
	ANode(AHtmlDocument doc, DNode node) {
		m_dNode = node;
		m_ownerDocument = doc;
		populateScriptable(ANode.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	public ANode(ADocument doc, DNode node) {
		m_dNode = node;
		m_ownerDocument = doc;
		populateScriptable(ANode.class, BrowserType.NONE);
	}
	
//	public NamedNodeMap getAttributes() {
//		return m_dNode.getAttributes();
//	}
	
	public Node getParentNode() {
		return m_parentNode;
	}
	
	public Node appendChild(Node newChild) throws DOMException {
		try {
			appendChild((ANode) newChild, true);
			return newChild;
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	// VJIT extension
	public Node add(Node newChild) throws DOMException {
		return appendChild(newChild) ;
	}
	
	public Node addt(String text) throws DOMException {
		DText dsfText = new DText(text) ;
		AText newChild = new AText(null, dsfText) ;
		appendChild(newChild) ;
		return newChild ;
	}
	
	public NodeList getChildNodes() {
		if (m_childNodes == null) {
			m_childNodes = createChildNodes() ;
		}
		return m_childNodes;
	}
	
	public String getNodeName() {
		if(m_dNode.getNodeName()!=null && m_dNode.getNodeType() == Node.ELEMENT_NODE){
			return m_dNode.getNodeName().toUpperCase();
		}else if(m_dNode.getNodeType() == Node.DOCUMENT_NODE){
			return "#document";
		} else if (m_dNode.getNodeType() == Node.TEXT_NODE){
			return "#text";
		} else if (m_dNode.getNodeType() == Node.COMMENT_NODE){
			return "#comment";
		} else if (m_dNode.getNodeType() == Node.DOCUMENT_FRAGMENT_NODE){
			return "#document-fragment";
		} else if (m_dNode.getNodeType() == Node.ATTRIBUTE_NODE){
			return ((AAttr)this).getName();
		}else{
			return m_dNode.getNodeName();
		}
	}

	public short getNodeType() {
		return m_dNode.getNodeType();
	}

	public String getNodeValue() throws DOMException {
		try {
			return m_dNode.getNodeValue();
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public Object getUserData(String key) {
		if (m_userData == null) {
			return null;
		}
		return m_userData.get(key);
	}

	public boolean hasChildNodes() {
		return (m_childNodes == null) ? false : m_childNodes.size() > 0;
	}
	
	public void setNodeValue(String nodeValue) throws DOMException {
		try {
			m_dNode.setNodeValue(nodeValue);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (m_userData == null) {
			m_userData = new LinkedHashMap<String, Object>(3) ;
		}
		//TODO store handles and implement dispatch logic
		return m_userData.put(key, data) ;
	}
	
	public NamedNodeMap getAttributes() {
		return null;
	}
	
	@Override
	public String toString() {
		return getNodeName();
	}
	
	public Node cloneNode() {
		return internalClone(false);
	}
	
	public Node cloneNode(boolean deep) {
		return internalClone(deep);
	}
	
	public Node getFirstChild() {
		if (m_childNodes == null) {
			return null;
		}
		
		if (m_childNodes.isEmpty()) {
			return null;
		}

		return m_childNodes.get(0);
	}

	public Node getLastChild() {
		if (m_childNodes == null) {
			return null ;
		}
		
		if (m_childNodes.isEmpty()) {
			return null;
		}

		return m_childNodes.get(m_childNodes.getLength() - 1);
	}
	
	public Node getNextSibling() {
		if (m_parentNode == null) {
			return null;
		}
		if (m_parentNode.m_childNodes == null) {
			return null ;
		}
		
		final int index = m_parentNode.m_childNodes.indexOf(this);
		if (index < 0 || index+1 >= m_parentNode.m_childNodes.size()) {
			return null;
		}
		final ANode node = m_parentNode.m_childNodes.get(index + 1);
		return node;		
	}
	
	public Node getPreviousSibling() {
		if (m_parentNode == null) {
			return null;
		}
		if (m_parentNode.m_childNodes == null) {
			return null ;
		}
		
		final int index = m_parentNode.m_childNodes.indexOf(this);
		if (index <= 0) {
			return null;
		}
		final ANode node = m_parentNode.m_childNodes.get(index - 1);
		return node;
	}
	
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		try {
			insertBefore((ANode) newChild, (ANode) refChild, true);
			return newChild;
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public Node removeChild(Node oldChild) throws DOMException {
		try {
			removeChild((ANode) oldChild, true);
			return oldChild;
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		try {
			replaceChildInternal((ANode)newChild, (ANode)oldChild);
			return oldChild;
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public Document getOwnerDocument() {
		return (Document) m_ownerDocument;
	}
	
	public boolean hasAttributes() {
		return m_dNode.hasAttributes();
	}
	
	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		}
		else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}
	
	public String getLocalName() {
		return m_dNode.getLocalName();
	}
	
	public String getNamespaceURI() {
		return m_dNode.getNamespaceURI();
	}

	public String getPrefix() {
		return m_dNode.getPrefix();
	}
	
	public void setPrefix(String prefix) {
		m_dNode.setPrefix(prefix);
	}

	public Object getFeature(String feature, String version) {
		return m_dNode.getFeature(feature, version);
	}

	public String getTextContent() throws DOMException {
		// Default value for DOCUMENT_NODE, DOCUMENT_TYPE_NODE, NOTATION_NODE nodes
		// Other nodes should override this method
		return null;
	}


	public boolean isDefaultNamespace(String namespaceURI) {
		return m_dNode.isDefaultNamespace(namespaceURI);
	}

	public boolean isEqualNode(Node node) {
		return m_dNode.isEqualNode(((ANode)node).getDNode());
	}

	public boolean isSameNode(Node other) {
		return m_dNode.isSameNode(((ANode)other).getDNode());
	}

	public String lookupNamespaceURI(String prefix) {
		return m_dNode.lookupNamespaceURI(prefix);
	}

	public String lookupPrefix(String namespaceURI) {
		return m_dNode.lookupPrefix(namespaceURI);
	}
	
	public short compareDocumentPosition(Node other) {
		return m_dNode.compareDocumentPosition(((ANode)other).getDNode());
	}

	public String getBaseURI() {
		return m_dNode.getBaseURI();
	}

	public void setTextContent(String textContent) {
		// On setting, any possible children this node may have are removed and, 
		// if it the new string is not empty or null, replaced by a single Text node 
		// containing the string this attribute is set to. 
		if (textContent == null) {
			return;
		}
		
		// remove all children
		if (m_childNodes != null) {
			m_childNodes.clear();
		}
		
		//if (textContent == "") {
		if (textContent.length()==0) {
			return;
		}
		final DText text = new DText(textContent);
		m_dNode.add(text);
		this.appendChild(new AText((AHtmlDocument) m_ownerDocument, text));
	}
	
	public boolean isSupported(String feature, String version) {
		return m_dNode.isSupported(feature, version);
	}

	public void normalize() {
		//TODO complete implementation when normalize is supported by DNode
		m_dNode.normalize();
	}
	
	public String getInnerText() {
		return getTextContent();
	}

	public void setInnerText(String innerText) {
		setTextContent(innerText);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public Object getDefaultValue(Class typeHint) {
		if (typeHint == null) {
			return "Node";
		}
        return super.getDefaultValue(this, typeHint);
    }
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return internalClone(true);
	}
	
	ANode internalClone(boolean deep) {
		ANode copy = null;
		DNode copyDnode = (DNode) m_dNode.cloneNode(false);
		if (this instanceof AHtmlElement) {
			copy = (ANode) AHtmlFactory.createElement(
			getNodeName().toLowerCase(),
			(HtmlDocument) m_ownerDocument,  copyDnode);
		} else if (this instanceof AHtmlDocument) {
			copy = new AHtmlDocument((DHtmlDocument)copyDnode, 
					((AHtmlDocument)this).getBrowserType());
		} else if (this instanceof AAttr) {
			copy = new AAttr((AHtmlDocument)m_ownerDocument, 
					(DAttr)copyDnode);
		} else if (this instanceof AText) {
			copy = new AText((AHtmlDocument)m_ownerDocument, 
					(DText)copyDnode);
		} else if (this instanceof ADocumentFragment) {
			copy = new ADocumentFragment((AHtmlDocument)m_ownerDocument,(DDocumentFragment)copyDnode);
		} else {
			return null;
		}
		
		copy.m_ownerDocument = m_ownerDocument;
		copy.m_parentNode = null;
		
		if (deep) {
			if (m_childNodes != null) {
				copy.m_childNodes = createChildNodes();
				Iterator<ANode> iter = m_childNodes.iterator();
				while (iter.hasNext()) {
					ANode kid = iter.next();
					ANode kidCopy;
					kidCopy = (ANode)kid.cloneNode(deep);
					copy.appendChild(kidCopy);
				}
			}
		} else {
			copy.m_childNodes = null ;
		}
		return copy;
	}

	void removeChild(ANode oldChild, boolean deep) throws DOMException {
		if (oldChild == null) {
			throw new ADOMException(new DOMException(DOMException.NOT_FOUND_ERR,
				"null node is not a child of this node"));
		}
		if (oldChild.m_parentNode != this) {
			throw new ADOMException(new DOMException(DOMException.NOT_FOUND_ERR,
				"node is not a child of this node"));
		}
		if (deep) {
			m_dNode.removeChild(oldChild.getDNode());
		}
		getChildNodes(); // force child node creation
		m_childNodes.remove(oldChild);
		oldChild.m_parentNode = null;
		if (m_ownerDocument != null) {
			m_ownerDocument.removeIdentifiedElement(oldChild);
		}
	}



	DNode getDNode() {
		return m_dNode;
	}
	
	void setOwnerDocument(ADocument doc) {
		if (m_ownerDocument == doc) {
			return;
		}
		if (m_ownerDocument != null) {
			m_ownerDocument.removeIdentifiedElement(this);
		}
		m_ownerDocument = doc;
		if (m_childNodes != null) {
			for (int i = 0; i < m_childNodes.getLength(); i++) {
				((ANode)m_childNodes.item(i)).setOwnerDocument(doc);
			}
		}
	}
	
	void removeAllChildren() {
		if (m_childNodes == null) {
			return;
		}
		for (int i = m_childNodes.getLength() - 1; i >= 0; i--) {
			removeChild(m_childNodes.item(i));
		}
	}
	
	void postChildAdd(final ANode child) {
		child.m_parentNode = this;
		if (child.m_ownerDocument == null) {
			child.m_ownerDocument = m_ownerDocument;
		} 
		else if (child.m_ownerDocument != this) {
			if (child.m_ownerDocument != m_ownerDocument) {
				throw new ADOMException(new DOMException(DOMException.WRONG_DOCUMENT_ERR,
					"node belongs to a different document"));
			}
		}
	}
	
	void appendChild(ANode newChild, boolean deep) throws DOMException {
		if (newChild == null) {
			throw new ADOMException(new DOMException(DOMException.VALIDATION_ERR,"Child is null"));
		}
		try {
			eraseParent(newChild, (ANode) newChild.getParentNode());
			if (deep) {
				m_dNode.appendChild(newChild.getDNode());
			}			
			((ANodeList)getChildNodes()).add(newChild);
			postChildAdd(newChild);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	void insertBefore(ANode newChild, ANode refChild, boolean deep) throws DOMException {
		if (newChild == null) {
			throw new ADOMException(new DOMException(DOMException.VALIDATION_ERR,"Child is null"));
		}
		try {
		if (deep) {
			m_dNode.insertBefore(
				newChild.getDNode(), refChild == null ? null : refChild.getDNode());
		}
		insertBeforeInternal(newChild, refChild);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}	
	private void eraseParent(final ANode child, final ANode parent) {
		if (parent == null) {
			return;
		}
		parent.removeChild(child);
		return;
	}
	
	private ANodeList createChildNodes() {
		return new ANodeList();
	}
	
	private void insertBeforeInternal(final ANode newChild, final ANode refChild)
		throws DOMException {
		
		if (refChild == null) {
			// in general appendChild is simpler, faster and less error prone.
			appendChild(newChild);
		}
		
		if (newChild == refChild) {
			return ; // nothing to do
		}
		
		if (newChild.getParentNode() == this) {
			final int alreadyInIndex = (m_childNodes == null)
				? -1 : m_childNodes.indexOf(newChild);
			if (alreadyInIndex >= 0) {
				// optimzied remove, should not need to check or de-parent
				m_childNodes.remove(alreadyInIndex);
			} else {
				throw new ADOMException(new DOMException(DOMException.VALIDATION_ERR,
					"child has this parent, but cannot find it as a child"));
			}
		} 
		else {
			// optimized - to only check for truly new children
			if (newChild.getParentNode() != null) {
				newChild.getParentNode().removeChild(newChild);
			}
		}
		
		getChildNodes(); // force child nodes creation
		if (refChild == null) {
			m_childNodes.add(newChild);
		} else {
			final int index = m_childNodes.indexOf(refChild);
			if (index < 0) {
				throw new ADOMException(new DOMException(DOMException.NOT_FOUND_ERR,
					"rerence node not found"));
			}
			m_childNodes.add(index, newChild);
		}
		postChildAdd(newChild);
	}
	
	private void replaceChildInternal(
			final ANode newChild, final ANode oldChild) throws DOMException
	{
		if (newChild == null) {
			throw new ADOMException(new DOMException(DOMException.VALIDATION_ERR,
				"new child cannot be null"));			
		}
		
		final int index = (m_childNodes == null)
			? -1 : m_childNodes.indexOf(oldChild);
		if (index < 0) {
			throw new ADOMException(new DOMException(DOMException.NOT_FOUND_ERR,
				"reference node not found"));
		}
		
		removeChild(oldChild);
	
		getChildNodes() ; // force child nodes creation
		
		if (index >= m_childNodes.size()) {
			appendChild(newChild);
		} 
		else {
			insertBefore(newChild, m_childNodes.get(index));
		}
	}
}
