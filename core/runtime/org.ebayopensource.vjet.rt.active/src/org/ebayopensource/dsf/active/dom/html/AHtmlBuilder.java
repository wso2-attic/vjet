/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlHtml;

/**
 * The purpose of this class is to receive sax events and to build
 * an Active HTML DOM.
 */
public class AHtmlBuilder implements ContentHandler, LexicalHandler {

	/**
	 * The document that is being built.
	 */
	protected AHtmlDocument m_document;

	/**
	 * The current node in the document into which elements, text and
	 * other nodes will be inserted. This starts as the document iself
	 * and reflects each element that is currently being parsed.
	 */
	protected Stack<HtmlElement> m_current = new Stack<HtmlElement>();

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
	private boolean m_fixDuplicateIds = false;
	private int m_sequence = 0;
	private List<String> m_ids = new ArrayList<String>();

	/**    
	 * The document is only created the same time as the document element, however, certain
	 * nodes may precede the document element (comment and PI), and they are accumulated
	 * in this vector.
	 */
	protected Vector m_preRootNodes;

	private Locator m_locator;
	
	public AHtmlBuilder(AHtmlDocument document) {
		m_document = document;
	}
	
	public AHtmlBuilder() {
		m_document = new AHtmlDocument();
	}

	public void startDocument() throws SAXException {
		if (!m_done) {
			throw new SAXException("startDocument already called.");
		}
		m_done = false;
	}

	public void endDocument() throws SAXException {
		if (getCurrentElement() != null) {
			throw new SAXException("Document ended before end of document element.");
		}
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

		if (getCurrentElement() == null) {
			HtmlHtml html = m_document.createElement(AHtmlType.HTML);
			m_document.appendChild(html);
			m_current.push(html);
		} 
		else {
			final AHtmlElement node = (AHtmlElement) AHtmlFactory.createElement(m_document, localName);
//			if (node instanceof AHtmlBody) {
//				System.out.println();
//			}
			getCurrentElement().appendChild(node);
			m_current.push(node);
		}

		// Add the attributes (specified and not-specified) to this element.
		if (atts != null) {
			for (i = 0; i < atts.getLength(); ++i) {
				final String name = atts.getLocalName(i);
				String value = atts.getValue(i);
				if ("id".equalsIgnoreCase(name) && shouldFixDuplicateIds()) {
					if (m_ids.contains(value)) {
						value = value + "_" + m_sequence++;
					} else {
						m_ids.add(value);
					}
				}
				getCurrentElement().setAttribute(name, value);
			}
		}
	}

	public void endElement(
		final String namespaceURI, final String localName, final String qName)
			throws SAXException
	{
		if (getCurrentElement() == null) {
			throw new SAXException("endElement called with no current node.");
		}
		if (!getCurrentElement().getNodeName().equalsIgnoreCase(localName)) {
			throw new SAXException(
				"mismatch in closing tag name "
					+ localName
					+ "\n"
					+ localName);
		}
		
		// Pop the stack
		m_current.pop();

		// Move up to the parent element. When you reach the top (closing the root element).
		// the parent is document and current is null.
//		if (m_current.getParentNode().getNodeType() == Node.DOCUMENT_NODE){
//			m_current = null;
//		} 
//		else {
//			m_current = (AHtmlElement)m_current.getParentNode();
//		}
	}

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
		if (getCurrentElement() == null) {
			throw new SAXException("character data found outside of element.");
		}
		
		final ANode child = (ANode) getCurrentElement().getLastChild();
		if (child instanceof AText) {
			((AText) child).appendData(text);
		} 
		else {
			getCurrentElement().appendChild(m_document.createTextNode(text));
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

	public HtmlDocument getHtmlDocument() {
		return m_document;
	}

	public HtmlElement getCurrentElement() {
		if (m_current.empty()) {
			return null;
		}
		return m_current.peek();
	}

	public void setDocumentLocator(final Locator locator) {
		m_locator = locator;
	}
	
	public Locator getDocumentLocator() {
		return m_locator;
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
//		if (m_document == null) {
//			return;
//		}
//		m_current.appendChild(
//			m_document.createComment(new String(text, start, length)));
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
