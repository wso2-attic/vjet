/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DCDATASection;
import org.ebayopensource.dsf.dom.DComment;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DDocumentFragment;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DEntityReference;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.DProcessingInstruction;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.jsnative.Attr;
import org.ebayopensource.dsf.jsnative.CDATASection;
import org.ebayopensource.dsf.jsnative.Comment;
import org.ebayopensource.dsf.jsnative.DOMConfiguration;
import org.ebayopensource.dsf.jsnative.DOMImplementation;
import org.ebayopensource.dsf.jsnative.Document;
import org.ebayopensource.dsf.jsnative.DocumentFragment;
import org.ebayopensource.dsf.jsnative.DocumentType;
import org.ebayopensource.dsf.jsnative.Element;
import org.ebayopensource.dsf.jsnative.EntityReference;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.ProcessingInstruction;
import org.ebayopensource.dsf.jsnative.Text;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class ADocument extends ANode implements Document {

	private static final long serialVersionUID = 1L;

	private Map<String, AHtmlElement> m_idToElement = new HashMap<String, AHtmlElement>();
	private Map<AHtmlElement, String> m_elementToId = new HashMap<AHtmlElement, String>();
	private DOMImplementation m_implementation = new ADOMImplementation();

	public ADocument(DDocument doc, final BrowserType browserType) {
		super(null, doc);
		setOwnerDocument(this);
		populateScriptable(ADocument.class, browserType);
	}
	
	public Element getDocumentElement() {
		NodeList nodes = getChildNodes();
		if (nodes.getLength() == 0) return null;
		for(int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) return (Element) node ;
		}
		return null ;
	}
	
	public Element createElement(String tagName) throws DOMException {
		try {
			return AHtmlFactory.createElement((AHtmlDocument) this, tagName);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public Text createTextNode(String data) {
		return new AText(this, (DText) getDDocument().createTextNode(data));
	}
	
	public Element byId(final String elementId) {
		return getElementById(elementId) ;
	}
	public Element getElementById(final String elementId) {
		final AHtmlElement element = m_idToElement.get(elementId);
		return element;
	}

	public NodeList byTag(final String tagName) {
		return getElementsByTagName(tagName) ;
	}
	public NodeList getElementsByTagName(final String tagName) {
		final ANodeList answer = new ANodeList();
		getElementsByTagName(tagName, answer);
		return answer;
	}
	
	private void getElementsByTagName(
		final String name, final ANodeList answer)
	{
		
		final boolean matchesAll = "*".equals(name);
		if (m_childNodes == null) return;
		Iterator<ANode> iter = m_childNodes.iterator();
		while (iter.hasNext()) {
			ANode childx = iter.next();
			if (childx instanceof AElement) {
				final AElement child = (AElement) childx;
				String childName = child.getNodeName();
				if (matchesAll || childName.equalsIgnoreCase(name)) {
					answer.add(child);
				}
				// *** RECURSION ***
				child.getElementsByTagName(name, answer);
			}
		}		
	}
	
	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		try {
			DNode dNode = 
				(DNode) getDDocument().importNode(((ANode)importedNode).getDNode(), deep);
			Node newNode = AHtmlFactory.createElement((AHtmlDocument)this, dNode);
			return newNode;
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public Node adoptNode(Node source) throws DOMException {
		try {
			DNode dNode = 
				(DNode) getDDocument().adoptNode(((ANode)source).getDNode());
			Node newNode = AHtmlFactory.createElement((AHtmlDocument)this, dNode);
			return newNode;
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public Attr createAttribute(String name) throws DOMException {
		if (name == null || name.length() == 0) {
			throw new ADOMException(
				new DOMException(DOMException.INVALID_CHARACTER_ERR, 
					"name is null or empty"));
		}
		try {
			return new AAttr((AHtmlDocument) this, (DAttr) getDDocument().createAttribute(name));
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public Attr createAttributeNS(String namespaceURI, String qualifiedName)
			throws DOMException {
		try {
			return new AAttr((AHtmlDocument) this, 
					(DAttr) getDDocument().createAttributeNS(namespaceURI, qualifiedName));
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public CDATASection createCDATASection(String data) throws DOMException {
		try {
			return new ACDATASection((AHtmlDocument) this, 
					(DCDATASection) getDDocument().createCDATASection(data));
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public Comment createComment(String data) {
		return new AComment((AHtmlDocument) this, 
				(DComment) getDDocument().createComment(data));
	}

	public DocumentFragment createDocumentFragment() {
		return new ADocumentFragment((AHtmlDocument) this,
				(DDocumentFragment) getDDocument().createDocumentFragment());
	}

	public Element createElementNS(String namespaceURI, String qualifiedName)
			throws DOMException {
		return new AElement((AHtmlDocument) this,
			(DElement) getDDocument().createElementNS(namespaceURI, qualifiedName));
	}

	public EntityReference createEntityReference(String name)
			throws DOMException {
		if (name == null || name.length() == 0) {
			throw new ADOMException(new DOMException(DOMException.INVALID_CHARACTER_ERR, "name is null or empty"));
		}
		return new AEntityReference((AHtmlDocument) this,
			(DEntityReference) getDDocument().createEntityReference(name));
	}

	public ProcessingInstruction createProcessingInstruction(String target,
			String data) throws DOMException {
		try {
			return new  AProcessingInstruction((AHtmlDocument) this,
				(DProcessingInstruction) getDDocument().createProcessingInstruction(
						target, data));
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public DocumentType getDoctype() {
		Node firstChild = getFirstChild();
		if (firstChild == null) {
			return null;
		}
		if (firstChild instanceof ADocumentType) {
			return (ADocumentType) firstChild;
		}
		return null;
	}

	public String getDocumentURI() {
		return getDDocument().getDocumentURI();
	}

	public DOMConfiguration getDomConfig() {
		// TODO: remove after this is implemented by DDOM
//		throw new ADOMException(
//			new DOMException(DOMException.NOT_SUPPORTED_ERR, "getDomConfig not supported"));
//		// return getDDocument().getDomConfig();
		return null;
	}

	public NodeList byTagNS(String namespaceURI, String localName) {
		return getElementsByTagNameNS(namespaceURI, localName) ;
	}
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		final ANodeList answer = new ANodeList();
		getElementByTagNameNS(localName, namespaceURI, answer);
		return answer;
	}
	
	void getElementByTagNameNS(final String name, final String nsUri, final ANodeList answer){
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
		for (ANode childx : m_childNodes) {
			if (childx instanceof AElement) {
			    if (matchesAllName) {
					if (matchesAllUri) {
						answer.add(childx);
					} else {					  
					    if (nsUri.equals(childx.getNamespaceURI())) {
					    	answer.add(childx);
					    }
					}
			    } else {			    	
					if (childx.getLocalName() != null && childx.getLocalName().equals(name)) {
					    if (matchesAllUri) {
					    	answer.add(childx);
					    } else {
							if (nsUri.equals(childx.getNamespaceURI())) {
								answer.add(childx);
							}
					    }
					}
			    }
			    //*** RECURSION ***
				((AElement) childx).getElementByTagNameNS(name, nsUri, answer);
			}
		}
	}

	public DOMImplementation getImplementation() {
		return m_implementation;
	}

	public String getInputEncoding() {
		return getDDocument().getInputEncoding();
	}

	public boolean getStrictErrorChecking() {
//		return getDDocument().getStrictErrorChecking();
		return false;
	}

	public String getXmlEncoding() {
		return getDDocument().getXmlEncoding();
	}

	public boolean getXmlStandalone() {
//		return getDDocument().getXmlStandalone();
		return false;
	}

	public String getXmlVersion() {
		return getDDocument().getXmlVersion();
	}

	public void normalizeDocument() {
		getDDocument().normalizeDocument();
	}

	public Node renameNode(Node n, String namespaceURI, String qualifiedName)
			throws DOMException {
		//TODO: remove when this is implmented by DDOM
		throw new ADOMException(
				new DOMException(DOMException.NOT_SUPPORTED_ERR, "renameNode not supported"));
	}

	public void setDocumentURI(String documentURI) {
		try {
			getDDocument().setDocumentURI(documentURI);
		}
		catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public void setStrictErrorChecking(boolean strictErrorChecking) {
		try {
			getDDocument().setStrictErrorChecking(strictErrorChecking);
		}
		catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		try {
			getDDocument().setXmlStandalone(xmlStandalone);
		}
		catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public void setXmlVersion(String xmlVersion) throws DOMException {
		try {
			getDDocument().setXmlVersion(xmlVersion);
		}
		catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
//	@Override
//	public String getNodeName() {
//		return "#document";
//	}
	
	void putIdentifier(final String id, final AHtmlElement element) {
		final AHtmlElement old = m_idToElement.get(id);
		if (old != null && old != element) {
			throw new ADOMException(new DOMException(DOMException.VALIDATION_ERR,
				"duplicate ID of " + id));
		}
		m_idToElement.put(id, element);
		m_elementToId.put(element, id);
	}

	void removeIdentifier(final String id) {
		AHtmlElement element = m_idToElement.remove(id);
		if (element != null) {
			m_elementToId.remove(element);
		}
	}
	
	void removeIdentifiedElement(final ANode node) {
		if (node instanceof AElement) {
			String id = m_elementToId.remove(node);
			if (id != null) {
				m_idToElement.remove(id);
			}
			NodeList children = node.getChildNodes();
			if (children != null) {
				for (int i = 0, l = children.getLength(); i < l; i++) {
					Node child = children.item(i);
					if (child instanceof ANode) {
						removeIdentifiedElement((ANode) child);
					}
				}
			}
		}
	}
	
	private DDocument getDDocument() {
		return (DDocument) getDNode();
	}

	public String getLastModified() {
		return null;
	}

	public String getBgColor() {
		return null;
	}

	public String getAlinkColor() {
		return null;
	}

	public String getLinkColor() {
		return null;
	}

	public String getVlinkColor() {
		return null;
	}

	public String getProtocol() {
		return null;
	}

	public String getCookie() {
		return null;
	}

	public String getFgColor() {
		return null;
	}

	public String getReadyState() {
		return null;
	}

	public String getSecurity() {
		return null;
	}

	public void setBgColor(String value) {
	}

	public void setFgColor(String value) {
	}

	public void setLinkColor(String value) {
	}
}
