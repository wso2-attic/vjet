/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
API from DefaultHandler...

characters(char[], int, int) 
endDocument()
endElement(String, String, String)
endPrefixMapping(String)
ignorableWhitespace(char[], int, int)
processingInstruction(String, String)
setDocumentLocator(Locator)
skippedEntity(String)
startDocument()
startElement(STring, String, String, Attributes)
startPrefixMapping(String, String)


Missing from DDomBuilder

error(SAXParseException)
fatalError(SAXParseException)
warning(SAXParseException)
notationDecl(String, String, String)
resolveEntity(String, String)
unparsedEntityDecl(String, String, String, String)

 */
public class DDomBuilder_DefaultHandler extends DefaultHandler{
	private final DDOMBuilder m_builder ;
	
	//
	// Constructor(s)
	//
	public DDomBuilder_DefaultHandler(final DDOMBuilder builder) {
		m_builder = builder ;
	}
	
	//
	// API
	//
	public DDOMBuilder getBuilder() {
		return m_builder ;
	}
	
	//
	// Passthrus
	//
	@Override
	public void characters(char[] text, int start, int length) {
		try {
			getBuilder().characters(text, start, length) ;
		}
		catch(SAXException e) { err(e) ; }
	}
	@Override
	public void endDocument() {
		try {
			getBuilder().endDocument() ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	@Override
	public void endElement(String a, String b, String c) {
		try {
			getBuilder().endElement(a, b, c) ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	@Override
	public void endPrefixMapping(String a) {
		try {
			getBuilder().endPrefixMapping(a) ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	@Override
	public void ignorableWhitespace(char[] a, int b, int c) {
		try {
			getBuilder().ignorableWhitespace(a, b, c) ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	@Override 
	public void processingInstruction(String a, String b) {
		try {
			getBuilder().processingInstruction(a, b) ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	@Override
	public void setDocumentLocator(Locator locator) {
		getBuilder().setDocumentLocator(locator) ;
	}
	@Override
	public void skippedEntity(String a) {
		try {
			getBuilder().skippedEntity(a) ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	@Override
	public void startDocument() {
		try {
			getBuilder().startDocument() ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	@Override
	public void startElement(String a, String b, String c, Attributes d) {
		try {
			getBuilder().startElement(a, b, c, d) ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	@Override
	public void startPrefixMapping(String a, String b) {
		try {
			getBuilder().startPrefixMapping(a, b) ;
		}
		catch(SAXException e) { err(e) ; } ;
	}
	
	//
	// Missing from DDOMBuilder...
	//
//	error(SAXParseException)
//	fatalError(SAXParseException)
//	warning(SAXParseException)
//	notationDecl(String, String, String)
//	resolveEntity(String, String)
//	unparsedEntityDecl(String, String, String, String)

	//
	// Private
	//
	private void err(SAXException e) {
		SAXParseException spe = new SAXParseException(
			e.getMessage(), null, e) ;
		try { 
			error(spe) ;
		}
		catch(SAXException se) {
			throw new RuntimeException(se) ;
		}
	}
}
