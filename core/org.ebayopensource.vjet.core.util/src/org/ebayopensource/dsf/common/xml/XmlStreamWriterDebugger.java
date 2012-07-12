/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

public class XmlStreamWriterDebugger implements IXmlStreamWriter {
	private final IXmlStreamWriter m_xmlStreamWriter;
	public XmlStreamWriterDebugger(final IXmlStreamWriter xmlStreamWriter) {
		m_xmlStreamWriter = xmlStreamWriter;
	}
	
//	public boolean isEscapingEnabled() {
//		return m_xmlStreamWriter.isEscapingEnabled() ;
//	}
//	
//	public void setEscapingEnabled(boolean escapingEnabled) {
//		m_xmlStreamWriter.setEscapingEnabled(escapingEnabled) ;
//		out("escaping enabled: " + escapingEnabled) ;
//	}
	
	public void flush() {
		m_xmlStreamWriter.flush();
		out("flush()");
	}

// MrPperf -
	public void writeAttribute(final char[] attributeName, final String value) {
		m_xmlStreamWriter.writeAttribute(attributeName, value);
		out("writeAttribute(" + new String(attributeName) + ", " + value + ")");
	}
	
	public void writeAttribute(final String attributeName, final String value) {
		m_xmlStreamWriter.writeAttribute(attributeName, value);
		out("writeAttribute(" + attributeName + ", " + value + ")");
	}

	public void writeCData(final String data) {
		m_xmlStreamWriter.writeCData(data);
		out("writeCData(" + data + ")");
	}

	public void writeRaw(String data) throws XmlStreamException {
		m_xmlStreamWriter.writeRaw(data);
		out("writeRaw(" + data + ")");
	}

	public void writeCharacters(final char[] text, int start, int len) {
		m_xmlStreamWriter.writeCharacters(text, start, len);
		out("writeCharacters(" + new String(text, start, len) + ")");
	}

	public void writeCharacters(final String text) {
		m_xmlStreamWriter.writeCharacters(text);
		out("writeCharacters(" + text + ")");
	}

	public void writeComment(final String data) {
		m_xmlStreamWriter.writeComment(data);
		out("writeComment(" + data + ")");
	}
	public void writeDtd(String dtd) throws XmlStreamException {
		m_xmlStreamWriter.writeDtd(dtd);
		out("writeDtd(" + dtd + ")");
	}
	public void writeEmptyElement(final String tagName) {
		m_xmlStreamWriter.writeEmptyElement(tagName);
		out("writeEmptyElement(" + tagName + ")");
	}

	public void writeEndDocument() {
		m_xmlStreamWriter.writeEndDocument();
		out("writeEndDocument()");
	}

	public void writeEndElement() {
		m_xmlStreamWriter.writeEndElement();
		out("writeEndElement()");
	}

	public void writeStartDocument() {
		m_xmlStreamWriter.writeStartDocument();
		out("writeStartDocument()");
	}

	public void writeStartDocument(final String encoding, final String version){
		m_xmlStreamWriter.writeStartDocument(encoding, version);
		out("writeStartDocument(" + encoding + version + ")");
	}

	public void writeStartDocument(final String version) {
		m_xmlStreamWriter.writeStartDocument(version);
		out("writeStartDocument(" + version + ")");
	}

// MrPperf - added method
	public void writeStartElement(final char[] tagNameAsChars) {
		m_xmlStreamWriter.writeStartElement(tagNameAsChars);
		out("writeStartElement(" + new String(tagNameAsChars) + ")");
	}
	
	public void writeStartElement(final String tagName) {
//		writeStartElement(tagName.toCharArray()) ;
		m_xmlStreamWriter.writeStartElement(tagName);
		out("writeStartElement(" + tagName + ")");
	}
	
	public void writeRawCharacters(char[] text, int start, int len) {
		m_xmlStreamWriter.writeRawCharacters(text, start, len);
		out("writeRawCharacters(" + new String(text, start, len) + ")");
	}

	private void out(final String message) {
		System.out.println(message);
	}
}
