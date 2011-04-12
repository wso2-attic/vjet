/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class Sax2Delegate implements ContentHandler, LexicalHandler{

	private ContentHandler m_contentHandler;
	private LexicalHandler m_lexicalHandler;

	public void setContentHandler(final ContentHandler contentHandler) {
		this.m_contentHandler = contentHandler;
	}
	public void setLexicalHandler(final LexicalHandler lexicalHandler) {
		this.m_lexicalHandler = lexicalHandler;
	}

	////////////////////////////////////////////////////////
	// content handler routines {
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.characters(arg0, arg1, arg2);
		}
	}

	public void endDocument() throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.endDocument();
		}
	}

	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.endElement(arg0, arg1, arg2);
		}
	}

	public void endPrefixMapping(String arg0) throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.endPrefixMapping(arg0);
		}
	}

	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.ignorableWhitespace(arg0, arg1, arg2);
		}
	}

	public void processingInstruction(String arg0, String arg1) throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.processingInstruction(arg0, arg1);
		}
	}

	public void setDocumentLocator(Locator arg0) {
		if (m_contentHandler != null) {
			m_contentHandler.setDocumentLocator(arg0);
		}
	}

	public void skippedEntity(String arg0) throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.skippedEntity(arg0);
		}
	}

	public void startDocument() throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.startDocument();
		}
	}

	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.startElement(arg0, arg1, arg2, arg3);
		}
	}

	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		if (m_contentHandler != null) {
			m_contentHandler.startPrefixMapping(arg0, arg1);
		}
	}
	// content handler routines }
	////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////
	// Lexical handlers {
	public void comment(char[] arg0, int arg1, int arg2) throws SAXException {
		if (m_lexicalHandler != null) {
			m_lexicalHandler.comment(arg0, arg1, arg2);
		}
	}

	public void endCDATA() throws SAXException {
		if (m_lexicalHandler != null) {
			m_lexicalHandler.endCDATA();
		}
	}

	public void endDTD() throws SAXException {
		if (m_lexicalHandler != null) {
			m_lexicalHandler.endDTD();
		}
	}

	public void endEntity(String arg0) throws SAXException {
		if (m_lexicalHandler != null) {
			m_lexicalHandler.endEntity(arg0);
		}
	}

	public void startCDATA() throws SAXException {
		if (m_lexicalHandler != null) {
			m_lexicalHandler.startCDATA();
		}
	}

	public void startDTD(String arg0, String arg1, String arg2) throws SAXException {
		if (m_lexicalHandler != null) {
			m_lexicalHandler.startDTD(arg0, arg1, arg2);
		}
	}

	public void startEntity(String arg0) throws SAXException {
		if (m_lexicalHandler != null) {
			m_lexicalHandler.startEntity(arg0);
		}
	}
	// Lexical handlers }
	////////////////////////////////////////////////////////
}
