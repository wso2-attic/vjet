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

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.Attr;
import org.ebayopensource.dsf.jsnative.Element;
import org.ebayopensource.dsf.jsnative.NamedNodeMap;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.TypeInfo;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AElement extends ANode implements Element{
	
	private static final long serialVersionUID = 1L;
	
	private NamedNodeMap m_attributes = new ANamedNodeMap(Node.ATTRIBUTE_NODE);

	protected static final String JS_BODY_PATH = "document.getElementById('body')";
	
	protected static final String JS_OFFSET_PARENT_PATH = "DLC_getPath({0}.offsetParent)";
	
	public AElement(ADocument doc, DElement element) {
		super(doc, element);
		populateScriptable(AElement.class, BrowserType.NONE);
	}
	
	protected AElement(AHtmlDocument doc, DElement element) {
		super(doc, element);
		populateScriptable(AElement.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	@Override
	public Object __getAttribute(Object sAttrName, Object iFlags, Object notreq1,
			Object notreq2, Object notreq3) {
		if(sAttrName instanceof String && iFlags instanceof Integer){
			return getAttribute((String)sAttrName, (Integer)iFlags);
		}
		else{
			return getAttribute((String)sAttrName);
		}
		
	}
	
	public String getAttribute(String name) {
		return getDElement().getAttribute(name);
	}

	public String getAttribute(String name, int iFlags) {
		// TODO iFlags is ignored right now
		return getDElement().getAttribute(name);
	}

	
	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		return getDElement().getAttributeNS(namespaceURI, localName);
	}

	@Override
	public NamedNodeMap getAttributes() {
		return m_attributes;
	}

	public Attr getAttributeNode(String name) {
		Attr aAttr = (Attr) m_attributes.getNamedItem(name);
		if (aAttr == null) {
			return null;
		}
		return aAttr;
	}


	public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
		DAttr attr = (DAttr) getDElement().getAttributeNodeNS(namespaceURI, localName);
		if (attr == null) {
			return null;
		}
		AAttr aAttr = new AAttr((AHtmlDocument) getOwnerDocument(), attr);
		aAttr.setOwnerElement(this);
		return aAttr;
	}

	public NodeList byTag(String name) {
		return getElementsByTagName(name) ;
	}
	public NodeList getElementsByTagName(String name) {
		final ANodeList answer = new ANodeList();
		getElementsByTagName(name, answer);
		return answer;
	}

	public NodeList byTagNS(String namespaceURI, String localName) {
		return getElementsByTagNameNS(namespaceURI, localName) ;
	}
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		final ANodeList answer = new ANodeList();
		getElementByTagNameNS(localName, namespaceURI, answer);
		return answer;
	}

	public String getTagName() {
		return getDElement().getTagName();
	}


	public void removeAttribute(String name) throws DOMException {
		try {
			if (m_attributes.getNamedItem(name) != null) {
				m_attributes.removeNamedItem(name);
			}
			getDElement().removeAttribute(name);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}


	
	
	public void __setAttribute(Object name, Object value,Object notreq1,Object notreq2, Object notreq3) throws DOMException{
		if(name instanceof String && value instanceof String){
			setAttribute((String)name, (String)value);
		}
		else{
			setAttribute((String)name, value);
		}
	}
	
	public void setAttribute(String name, Object value) throws DOMException {
		try {
			// TODO test to string here
			getDElement().setAttribute(name, value.toString());
			AAttr aAttr = new AAttr((AHtmlDocument) getOwnerDocument(), 
					(DAttr) getDElement().getAttributeNode(name));
			aAttr.setOwnerElement(this);
			m_attributes.setNamedItem(aAttr);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public void setAttribute(String name, String value) throws DOMException {
		try {
			getDElement().setAttribute(name, value);
			AAttr aAttr = new AAttr((AHtmlDocument) getOwnerDocument(), 
					(DAttr) getDElement().getAttributeNode(name));
			aAttr.setOwnerElement(this);
			m_attributes.setNamedItem(aAttr);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public boolean hasAttribute(String name) {
		return getDElement().hasAttribute(name);
	}

	public boolean hasAttributeNS(String namespaceURI, String localName) {
		return getDElement().hasAttributeNS(namespaceURI, localName);
	}

	public void removeAttributeNS(String namespaceURI, String localName) {
		try {
			getDElement().removeAttributeNS(namespaceURI, localName);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public Attr removeAttributeNode(Attr oldAttr) {
		if (oldAttr == null) {
			throw new ADOMException(new DOMException(DOMException.NOT_FOUND_ERR,
				"oldAttr is null"));
		}
		AAttr aAttr = null;
		if (m_attributes.getNamedItem(oldAttr.getName()) != null) {
			aAttr = (AAttr) m_attributes.removeNamedItem(oldAttr.getName());
		}
		
		if (aAttr == null) {
			throw new ADOMException(new DOMException(DOMException.NOT_FOUND_ERR,
					"Did not find a node named: " + oldAttr.getName()));
		}
		try {
			getDElement().removeAttributeNode((DAttr)aAttr.getDNode());
			aAttr.setOwnerElement(null);
			return aAttr;
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) {
		try {
			getDElement().setAttributeNS(namespaceURI, qualifiedName, value);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public Attr setAttributeNode(Attr newAttr) {
		if (newAttr == null) {
			return null;
		}
		AAttr aAttr = (AAttr) newAttr;
		aAttr.setOwnerElement(this);
		aAttr.setOwnerDocument((ADocument)super.getOwnerDocument());
		m_attributes.setNamedItem(aAttr);
		DAttr attr = (DAttr) getDElement().setAttributeNode((DAttr) aAttr.getDNode());
		if (attr == null) {
			return null;
		}
		AAttr replacedAttr = (AAttr) m_attributes.getNamedItem(attr.getName());
		if (replacedAttr == null) {
			return null;
		}
		replacedAttr.setOwnerElement(null);
		replacedAttr.setOwnerDocument(null);
		return aAttr;
	}

	public Attr setAttributeNodeNS(Attr newAttr) {
		if (newAttr == null) {
			return null;
		}
		AAttr aAttr = (AAttr) newAttr;
		DAttr attr = (DAttr) getDElement().setAttributeNodeNS((DAttr) aAttr.getDNode());
		if (attr == null) {
			return null;
		}
		aAttr = new AAttr((AHtmlDocument) getOwnerDocument(), attr);
		aAttr.setOwnerElement(this);
		return aAttr;
	}

	public void setIdAttribute(String name, boolean isId) throws DOMException {
		try {
			getDElement().setIdAttribute(name, isId);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}
	
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) {
//		TODO: complete implementation when DDOM implements this.
		throw new ADOMException(
			new DOMException(DOMException.NOT_SUPPORTED_ERR, 
					"setIdAttributeNS not supported"));
//		getDElement().setIdAttributeNS(namespaceURI, localName, isId);
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId) {
		final AAttr aAttr = (AAttr)idAttr;
		try {
			getDElement().setIdAttributeNode(aAttr.getDAttr(), isId);
		} catch (DOMException e) {
			throw new ADOMException(e);
		}
	}

	public short compareDocumentPosition(Node other) {
//		TODO: complete implementation when DDOM implements this.
		throw new ADOMException(
			new DOMException(DOMException.NOT_SUPPORTED_ERR, 
					"compareDocumentPosition not supported"));
//		return getDElement().compareDocumentPosition(((ANode)other).getDNode());
	}

	public String getBaseURI() {
		return getDElement().getBaseURI();
	}

	public Object getFeature(String feature, String version) {
//		TODO: complete implementation when DDOM implements this.
		throw new ADOMException(
			new DOMException(DOMException.NOT_SUPPORTED_ERR, 
					"getFeature not supported"));
//		return getDElement().getFeature(feature, version);
	}

	public String getTextContent() {
		// concatenation of the textContent attribute value of every child node, 
		// excluding COMMENT_NODE and PROCESSING_INSTRUCTION_NODE nodes. 
		// This is the empty string if the node has no children.
		if (m_childNodes == null) return "";
		StringBuilder sb = new StringBuilder();
		Iterator<ANode> iter = m_childNodes.iterator();
		while (iter.hasNext()) {
			ANode child = iter.next();
			sb.append(child.getTextContent());
		}
		return sb.toString();
	}
	
	@Override
	public String getNodeName() {
		// same as Element.tagName
		return getTagName();
	}

	@Override
	public Node cloneNode(boolean deep) {
		AElement copy =  (AElement) super.cloneNode(deep);
		if (m_attributes != null) {
			copy.m_attributes = ((ANamedNodeMap) m_attributes).cloneMap();
		}
		return copy;
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return getDElement().isDefaultNamespace(namespaceURI);
	}

	public boolean isEqualNode(Node node) {
		return getDElement().isEqualNode(((ANode)node).getDNode());
	}

	public boolean isSameNode(Node other) {
		return getDElement().isSameNode(((ANode)other).getDNode());
	}

	public String lookupNamespaceURI(String specifiedPrefix) {
		return getDElement().lookupNamespaceURI(specifiedPrefix);
	}

	public String lookupPrefix(String namespaceURI) {
		return getDElement().lookupPrefix(namespaceURI);
	}
	
	public TypeInfo getSchemaTypeInfo() {
		return null;
	}
	
	void getElementsByName(final String elementName, final ANodeList answer) {
		if (m_childNodes == null) return;
		Iterator<ANode> iter = m_childNodes.iterator();
		while (iter.hasNext()) {
			ANode childx = iter.next();
			if (childx instanceof AElement) {
				final AElement child = (AElement) childx;
				String value = child.getAttribute("name");
				if (elementName.equals(value)) {
					answer.add(child);
				}
				// *** RECURSION ***
				child.getElementsByName(elementName, answer);
			}
		}	
		
	}
	
	void getElementsByTagName(
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
	
	private DElement getDElement() {
		return (DElement) getDNode();
	}

	void setHtmlAttribute(final EHtmlAttr attr, final boolean value) {
		setHtmlAttribute(attr, String.valueOf(value));
	}
	
	void setHtmlAttribute(final EHtmlAttr attr, final int value) {
		setHtmlAttribute(attr, String.valueOf(value));
	}
	
	void setHtmlAttribute(final EHtmlAttr attr, final String value) {
		setAttribute(attr.getAttributeName(), value);
	}

	protected int getIntBindingValue(EHtmlAttr attr) {
		String value = getBindingValue(attr);
		if (value == null || value.length() == 0) {
			return 0;
		}
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	protected String getBindingValue(EHtmlAttr attr) {
		return null;
	}

	String getHtmlAttribute(final EHtmlAttr attr) {
		return getAttribute(attr.getAttributeName());
	}

	private IBrowserBinding getBrowserBinding() {
		AHtmlDocument doc = (AHtmlDocument)getOwnerDocument();
		if (doc == null) {
			return null;
		}
		return doc.getBrowserBinding();
	}

	@Override
	public ADocument getOwnerDocument() {
		return (ADocument) super.getOwnerDocument();
	}

	private BaseHtmlElement getElement() {
		return (BaseHtmlElement) getDNode();
	}



}
