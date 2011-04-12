/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import java.io.PrintStream;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class Sax2PrinterDelegate extends Sax2Delegate {
	private PrintStream m_stream = System.out;

	public void setStream(final PrintStream stream) {
		m_stream = stream;
	}
	////////////////////////////////////////////////////////
	// content handler routines {
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		m_stream.println("characters(" + new String(arg0, arg1, arg2) + ")");
		super.characters(arg0, arg1, arg2);
	}
	@Override
	public void endDocument() throws SAXException {
		m_stream.println("endDocument()");
		super.endDocument();
	}
	@Override
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		m_stream.println("endElement(" + arg0 + "," + arg1 + "," + arg2 + ")");
		super.endElement(arg0, arg1, arg2);
	}
	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		m_stream.println("endPrefixMapping(" + arg0 + ")");
		super.endPrefixMapping(arg0);
	}
	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		m_stream.println("ignorableWhitespace(" + new String(arg0, arg1, arg2) + ")");
		super.ignorableWhitespace(arg0, arg1, arg2);
	}
	@Override
	public void processingInstruction(String arg0, String arg1) throws SAXException {
		m_stream.println("processingInstruction(" + arg0 + "," + arg1 + ")");
		super.processingInstruction(arg0, arg1);
	}
	@Override
	public void setDocumentLocator(Locator arg0) {
		m_stream.println("setDocumentLocator(" + arg0 + ")");
		super.setDocumentLocator(arg0);
	}
	@Override
	public void skippedEntity(String arg0) throws SAXException {
		m_stream.println("skippedEntity(" + arg0 + ")");
		super.skippedEntity(arg0);
	}
	@Override
	public void startDocument() throws SAXException {
		m_stream.println("startDocument()");
		super.startDocument();
	}
	@Override
	public void startElement(
		final String arg0,
		final String arg1,
		final String arg2,
		final Attributes attributes)
			throws SAXException
	{
		m_stream.print("startElement(" + arg0 + "," + arg1 + "," + arg2 + ")");
		if (attributes != null) {
			for (int i=0; i < attributes.getLength(); i++) {
				final String key = attributes.getQName(i);
				final String value = attributes.getValue(i);
				m_stream.print(" " + key + "='" + value + "'");
			}
		}
		m_stream.println();
		super.startElement(arg0, arg1, arg2, attributes);
	}
	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		m_stream.println("startPrefixMapping(" + arg0 + "," + arg1 + ")");
		super.startPrefixMapping(arg0, arg1);
	}
	// content handler routines }
	////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////
	// Lexical handlers {
	@Override
	public void comment(char[] arg0, int arg1, int arg2) throws SAXException {
		m_stream.println("comment(" + new String(arg0, arg1, arg2) + ")");
		super.comment(arg0, arg1, arg2);
	}
	@Override
	public void endCDATA() throws SAXException {
		m_stream.println("endCDATA()");
		super.endCDATA();
	}
	@Override
	public void endDTD() throws SAXException {
		m_stream.println("endDTD()");
		super.endDTD();
	}
	@Override
	public void endEntity(String arg0) throws SAXException {
		m_stream.println("endEntity(" + arg0 + ")");
		super.endEntity(arg0);
	}
	@Override
	public void startCDATA() throws SAXException {
		m_stream.println("startCDATA()");
		super.startCDATA();
	}
	@Override
	public void startDTD(String arg0, String arg1, String arg2) throws SAXException {
		m_stream.println("startDTD(" + arg0 + "," + arg1 + "," + arg2 + ")");
		super.startDTD(arg0, arg1, arg2);
	}
	@Override
	public void startEntity(String arg0) throws SAXException {
		m_stream.println("startEntity(" + arg0 + ")");
		super.startEntity(arg0);
	}
	// Lexical handlers }
	////////////////////////////////////////////////////////

}
