/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

import org.ebayopensource.dsf.dom.support.DNamespace;

/**
 * The purpose of this class is to receive sax events and to build
 * a DOM.
 */


//Comment: DsfQ109_NS - Dsf Q12009 parser namespace enhanc
public class DDOMBuilder implements ContentHandler, LexicalHandler {

	/**
	 * The document that is being built.
	 */
	protected DDocument m_document;

	/**
	 * The current node in the document into which elements, text and
	 * other nodes will be inserted. This starts as the document iself
	 * and reflects each element that is currently being parsed.
	 */
	protected DElement m_current;

	/**
	 * Applies only to whitespace appearing between element tags in element content,
	 * as per the SAX definition, and true by default.
	 */
	private boolean m_ignoreWhitespace = true;

	/**
	 * Indicates whether finished building a document. If so, can start building
	 * another document. Must be initially true to get the first document processed.
	 */
	private boolean m_done = true;
	
	/**
	 * Indicates whether to generate unique element 'id' if duplicates are detected.
	 */
	protected boolean m_fixDuplicateIds = false;
	private int m_sequence = 0;
	private Set<String> m_ids = new HashSet<String>();

	/**    
	 * The document is only created the same time as the document element, however, certain
	 * nodes may precede the document element (comment and PI), and they are accumulated
	 * in this vector.
	 */
	protected Vector m_preRootNodes;	
	
	private boolean m_normalizeNS = false; //true, if build an element with prefixOnly. (DsfQ109_NS)
	protected static final DElementConstructor m_domConstructor = new DOM();

	public void startDocument() throws SAXException {
		if (!m_done) {
			throw new SAXException("startDocument already called.");
		}
		m_document = null;
		m_done = false;
	}

	public void endDocument() throws SAXException {
		if (m_document == null) {
			throw new SAXException("Document never started or missing document element.");
		}
		if (m_current != null) {
			throw new SAXException("Document ended before end of document element.");
		}
		//m_document.setCreatedByDDOMBuilder(true);
		m_current = null;
		m_done = true;
	}

	public void startElement(
		final String namespaceURI,
		final String localName,
		final String qName,
		final Attributes atts) throws SAXException
	{
		int i;

		if (localName == null) {
			throw new SAXException("Tag name is null.");
		}

		// If this is the root element, this is the time to create a new document,
		// because only know we know the document element name and namespace URI.
		if (m_document == null) {
			m_document = new DDocument();
//			m_current = m_document.dsfGetDocumentElement();
			m_current = addElem(m_document, namespaceURI, localName, qName) ; 
			
			if (m_current == null) {
				throw new SAXException("No document element for this document");
			}
			// Insert nodes (comment and PI) that appear before the root element.
			if (m_preRootNodes != null) {
				for (i = m_preRootNodes.size(); i-- > 0;) {
					m_document.insertBefore(
						(Node) m_preRootNodes.elementAt(i), m_current);
				}
				m_preRootNodes = null;
			}
		} 
		else {
			// This is a state error, indicates that document has been parsed in full,
			// or that there are two root elements.
			if (m_current == null) {
				throw new SAXException("startElement called after end of document element.");
			}
			m_current = addElem(m_current, namespaceURI, localName, qName); 
		}
		addAttributesToElement(atts); // add attributes (if any) to m_current
	}

	private DElement addElem(
		final DNode parent, final String namespaceURI, 
		final String localName, final String qName) throws SAXException
	{
// todo: MrP - not sure if this is right logic...
		String name = localName ;
		if ("".equals(localName)) name = qName ;
		
		final DElement node = m_domConstructor.domCreateDynamicElement(name);
		//DsfQ109_NS
		if (namespaceURI != null && namespaceURI.length()> 0){
			if (!m_normalizeNS) {
				m_normalizeNS = true;
			}
			int prefixIdx = qName.indexOf(":");
			if (prefixIdx != -1){
				node.setDsfNamespace(DNamespace.getNamespace(
						qName.substring(0, prefixIdx), namespaceURI));
				
			} else {
				node.setDsfNamespace(DNamespace.getNamespace(null, namespaceURI));
			}
		}
		parent.add(node);
		return node ;
	}
	
	/**
	 * Add attributes to element at the end of startElement() process
	 * @param atts
	 */
	protected void addAttributesToElement(final Attributes atts){
		// Add the attributes (specified and not-specified) to this element.
		if (atts == null) return ;
		for (int i = 0; i < atts.getLength(); ++i) {
			final String name = atts.getLocalName(i);
			String value = atts.getValue(i);
			if ("id".equalsIgnoreCase(name)) {
				final DAttr attr = (DAttr)m_document.createAttribute(name);
				if (shouldFixDuplicateIds()) {
					//use while loop for this test case
					//<DIV id=D_0 /> <DIV id=D_1 /> <DIV id=D_2 /><DIV id=D />
					while (m_ids.contains(value)){
						value = value + "_" + m_sequence++;							
					} 
					m_ids.add(value);						
				}
				attr.setValue(value);
				m_current.setAttributeNode(attr);
				m_current.setIdAttribute(name, true);
			} 
			else {
				m_current.setAttribute(name, value);
			}
		}
	}
	
	
	public void endElement(
		final String namespaceURI, final String localName, final String qName)
			throws SAXException
	{
		if (m_current == null) {
			throw new SAXException("endElement called with no current node.");
		}
		
		if (m_current.getNamespaceURI() == null) {
			if (!m_current.getNodeName().equalsIgnoreCase(localName)) {
				throw new SAXException(
					"mismatch in closing tag name "
						+ localName
						+ "\n"
						+ m_current.getNodeName());
			}
		} else {
			if (!m_current.getNodeName().equalsIgnoreCase(qName)) {
				throw new SAXException(
					"mismatch in closing tag name "
						+ qName
						+ "\n"
						+ m_current.getNodeName());
			}
		}

		endElementInternal();
		//m_current.setCreatedByDDOMBuilder(true);
		// Move up to the parent element. When you reach the top (closing the root element).
		// the parent is document and current is null.
		if (m_current.getParentNode().getNodeType() == Node.DOCUMENT_NODE){
			m_current = null;
		} 
		else {
			m_current = (DElement)m_current.getParentNode();
		}
	}

	/**
	 * handle additional logic before moving upt to the parent element.
	 */
	protected void endElementInternal() {}
	
	public void characters(final String text) throws SAXException {
		addText(text);
	}

	public void characters(
		final char[] text, final int start, final int length)
			throws SAXException
	{
		addText(new String(text, start, length));
	}

	public void ignorableWhitespace(
		final char[] text, final int start, final int length)
			throws SAXException
	{
		if (!m_ignoreWhitespace) {
			addText(new String(text, start, length));
		}
	}

	private void addText(final String text) throws SAXException {
		if (m_current == null) {
			throw new SAXException("character data found outside of element.");
		}
		
		final Node child = m_current.getLastChild();
		if (child instanceof DText) {
			((DText) child).appendData(text);
		} 
		else {
			m_current.add(text);
		}
	}

	public void processingInstruction(
		final String target, final String instruction) throws SAXException
	{
		// Processing instruction may appear before the document element (in fact, before the
		// document has been created, or after the document element has been closed.
//		if (m_current == null && m_document == null) {
//			if (m_preRootNodes == null)
//				m_preRootNodes = new Vector();
//			m_preRootNodes.addElement(
//				new ProcessingInstructionImpl(null, target, instruction));
//		} else if (m_current == null && m_document != null)
//			m_document.appendChild(
//				new ProcessingInstructionImpl(m_document, target, instruction));
//		else
//			m_current.appendChild(
//				new ProcessingInstructionImpl(m_document, target, instruction));
	}

	public DDocument getDDocument() {
//		if (m_normalizeNS) {
//			HtmlNSHelper.normalizeNS(m_document, true);
//		}
		return m_document;
	}

	public DElement getCurrentElement() {
		return m_current;
	}

	public void setDocumentLocator(final Locator locator) {
//		m_locator = locator;
	}

	//
	// Satisfy ContentHandler
	//
	public void startPrefixMapping(
		final String prefix, final String uti) throws SAXException
	{
		// empty on purpose
	}

	public void endPrefixMapping(final String prefix) throws SAXException {
		// empty on purpose
	}

	public void skippedEntity(final String name) throws SAXException {
		// empty on purpose
	}

	// 
	// Satisfy LexicalHandler
	//
	public void comment(
		final char[] text, final int start, final int length)
			throws SAXException
	{
		if (m_document == null) {
			return;
		}
		m_current.appendChild(
			m_document.createComment(new String(text, start, length)));
	}

	public void endCDATA() throws SAXException {
		// empty on purpose
	}
	
	public void endDTD() throws SAXException {
		// empty on purpose
	}
	
	public void endEntity(final String name) throws SAXException {
		// empty on purpose
	}
	
	public void startCDATA() throws SAXException {
		// empty on purpose
	}
	
	public void startDTD(
		final String name, final String publicId, final String systemId)
			throws SAXException
	{
		// empty on purpose
	}
	
	public void startEntity(final String name) throws SAXException {
		// empty on purpose
	}

	public boolean shouldFixDuplicateIds() {
		return m_fixDuplicateIds;
	}

	public void setFixDuplicateIds(boolean value) {
		m_fixDuplicateIds = value;
	}
}

