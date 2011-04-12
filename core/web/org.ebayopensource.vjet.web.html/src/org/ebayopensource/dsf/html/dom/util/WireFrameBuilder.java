/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

	import java.util.Vector;

import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DElementConstructor;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.dom.DHtml;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.Html;

	/**
	 * The purpose of this class is to receive sax events and to build
	 * a DOM.
	 */
	public class WireFrameBuilder implements ContentHandler, LexicalHandler {

		/**
		 * The document that is being built.
		 */
		protected DDocument m_document;
		
		protected DDocument m_initialDocument = new DHtmlDocument();
		protected DElement m_rootElement = new DHtml();

		/**
		 * The current node in the document into which elements, text and
		 * other nodes will be inserted. This starts as the document iself
		 * and reflects each element that is currently being parsed.
		 */
		protected DElement m_current;

		/**
		 * A reference to the current locator, this is generally the parser
		 * itself. The locator is used to locate errors and identify the
		 * source locations of elements.
		 */
//		private Locator m_locator;

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
		 * The document is only created the same time as the document element, however, certain
		 * nodes may precede the document element (comment and PI), and they are accumulated
		 * in this vector.
		 */
		protected Vector m_preRootNodes;
		
		/**
		 * An optional Element constructor that, given a tag name, can return a DElement
		 * that is an instance representing that tag name. If  this is not specified,
		 * the constructor for Html tags is used.
		 */
		protected DElementConstructor m_eltConstuctor;

		public void startDocument() throws SAXException {
			if (!m_done) {
				throw new SAXException("HTM001 State error: startDocument fired twice on one builder.");
			}
			m_done = false;
		}

		public void endDocument() throws SAXException {
			if (m_document == null) {
				throw new SAXException("HTM002 State error: document never started or missing document element.");
			}
			if (m_current != null) {
				throw new SAXException("HTM003 State error: document ended before end of document element.");
			}
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
				throw new SAXException("HTM004 Argument 'tagName' is null.");
			}

			// If this is the root element, this is the time to create a new document,
			// because only know we know the document element name and namespace URI.
			// Get the initial document object and root elements from private properties.
			// These default to DHtmlDocument and DHtml to be compatible with Web usage.
			// Use SetDocumentAndRoot method if you want a different document type and
			// root element.
			if (m_document == null) {
				// No need to create the element explicitly.
				m_document = m_initialDocument;
				m_current = m_rootElement;
				m_document.add(m_rootElement);
				if (m_current == null) {
					throw new SAXException("HTM005 State error: Document.getDocumentElement returns null.");
				}
				// Insert nodes (comment and PI) that appear before the root element.
				if (m_preRootNodes != null) {
					for (i = m_preRootNodes.size(); i-- > 0;) {
						m_document.insertBefore(
							(Node) m_preRootNodes.elementAt(i),
							m_current);
					}
					m_preRootNodes = null;
				}

			} 
			else {
				// This is a state error, indicates that document has been parsed in full,
				// or that there are two root elements.
				if (m_current == null) {
					throw new SAXException("HTM006 State error: startElement called after end of document element.");
				}
				// For older cases that might not have setup the element constructor. Default to Html.
				if (m_eltConstuctor == null) {
					m_eltConstuctor = new Html();
				}
				final DElement node = m_eltConstuctor.domCreateDynamicElement(localName);
				m_current.add(node);
				m_current = node;
			}

			// Add the attributes (specified and not-specified) to this element.
			if (atts != null) {
				for (i = 0; i < atts.getLength(); ++i) {
					final String name = atts.getLocalName(i);
					final String value = atts.getValue(i);
					if ("id".equalsIgnoreCase(name)) {
						final DAttr attr = (DAttr)m_document.createAttribute(name);
						attr.setValue(value);
						m_current.setAttributeNode(attr);
						m_current.setIdAttribute(name, true);
					} else {
						m_current.setAttribute(name, value);
					}
				}
			}
		}

		public void endElement(
			final String namespaceURI, final String localName, final String qName)
				throws SAXException
		{
			if (m_current == null) {
				throw new SAXException("HTM007 State error: endElement called with no current node.");
			}
			if (!m_current.getNodeName().equalsIgnoreCase(localName)) {
				throw new SAXException(
					"HTM008 State error: mismatch in closing tag name "
						+ localName
						+ "\n"
						+ localName);
			}

			// Move up to the parent element. When you reach the top (closing the root element).
			// the parent is document and current is null.
			if (m_current.getParentNode().getNodeType() == Node.DOCUMENT_NODE){
				m_current = null;
			} 
			else {
				m_current = (DElement)m_current.getParentNode();
			}
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

		// Squeeze out spaces, returns, new lines and tabs. These are not
		// preserved in wireframes. If desired, they must be added by code.
		// We could use ignorable-white-space if we had a DTD but we don't.
		private void addText(final String text) throws SAXException {
			if (m_current == null) {
				throw new SAXException("HTM009 State error: character data found outside of element.");
			}
			String val = text.trim().replace("\r","").replace("\n", "").replace("\t", "");
			if (val.length() > 0) {
				final Node child = m_current.getLastChild();
				if (child instanceof DText) {
					((DText) child).appendData(text);
				} 
				else {
					m_current.add(text);
				}
			}
		}

		public void processingInstruction(
			final String target, final String instruction) throws SAXException
		{
		}

		// Keep for compatibility with previous HtmlBuilder users
		public DHtmlDocument getHTMLDocument() {
			return (DHtmlDocument)m_document;
		}
		
		public DDocument getDocument() {
			return m_document;
		}
		
		/**
		 * Set alternative Document and root element for the DOM
		 * being constructued.
		 * @param doc - alternate document object.
		 * @param root - alternate root element.
		 */
		public void setDocumentAndRoot(DDocument doc, DElement root) {
			m_initialDocument = doc;
			m_rootElement = root;
		}
		
		/**
		 * Set elementConstructor class used to convert string forms
		 * of tag names into actual Java objects (DBr, DTable, etc.)
		 * @param elt
		 */
		public void setElementConstructor(DElementConstructor elt) {
			m_eltConstuctor = elt;
		}

		public DElement getCurrentElement() {
			return m_current;
		}

		public void setDocumentLocator(final Locator locator) {
//			m_locator = locator;
		}

		////////////////////////////////////////////////////////////////////
		// Additional implementation for ContentHandler interface
		////////////////////////////////////////////////////////////////////
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

		////////////////////////////////////////////////////////////////////
		// LexicalHandler required implementation
		////////////////////////////////////////////////////////////////////
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
	}
