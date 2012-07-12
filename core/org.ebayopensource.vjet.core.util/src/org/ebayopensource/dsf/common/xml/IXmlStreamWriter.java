/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

/** This interface is barrowed from StAX (Streaming API for XML) API.
 * The XMLStreamWriter interface specifies how to write XML.
 *
 * It does not check for well formed on its input.
 *
 * writeCharacters method is required to escape '&', '<', '>'.
 * 
 * For attribute values the writeAttribute method will escape the 
 * above characters plus &quot; to ensure that all character content
 * and attribute values are well formed. 
 *
 * Each NAMESPACE 
 * and ATTRIBUTE must be individually written.
 *
 * If javax.xml.stream.isPrefixDefaulting is set to false it is a fatal error if an element
 * is written with namespace URI that has not been bound to a prefix.
 *
 * If javax.xml.stream.isPrefixDefaulting is set to true the XMLStreamWriter implementation
 * must write a prefix for each unbound URI that it encounters in the
 * current scope.  
 */

public interface IXmlStreamWriter {
	public static final String CDATA_END = "]]>";
	public static final String CDATA_START = "<![CDATA[";
	public static final String COMMENT_END = "-->";
	public static final String COMMENT_START = "<!--";
	
//	boolean isEscapingEnabled() ;
//	void setEscapingEnabled(boolean escapingEnabled) ;
	
	/**
	 * Writes a start tag to the output.
	 * @param tagName tag name of the tag.  Must not be null.
	 * @throws XmlStreamException
	 */
// MrPperf - added for perf
	void writeStartElement(char[] tagNameAsChars) ;
	
	void writeStartElement(String tagName);

//	void writeStartElement(String namespaceURI, String localName)
//			throws XmlStreamException;

//	void writeStartElement(String prefix, String localName,
//			String namespaceURI) throws XmlStreamException;

//	void writeEmptyElement(String namespaceURI, String localName)
//			throws XmlStreamException;

//	void writeEmptyElement(String prefix, String localName,
//			String namespaceURI) throws XmlStreamException;

	/** Write an empty element tag to the output
	 * @param tagName tag name of the tag.  Must not be null.
	 * @throws XmlStreamException
	 */
	void writeEmptyElement(String tagName);

	void writeRaw(String data) throws XmlStreamException;

	/** Write an end tag to the output.  It maintain the necessary
	 * state to close the appropriate element.
	 * @throws XmlStreamException 
	 */
	void writeEndElement();

	/** Closes any opened tags.
	 * @throws XmlStreamException
	 */
	void writeEndDocument();

//	void close() throws XmlStreamException;

	void flush();

	/** Write an attribute to the output stream.  It will properly escape
	 * any characters as necessary.
	 * @param attributeName the local name of the attribute
	 * @param value
	 * @throws XmlStreamException - throws if the current state does not
	 * allow Attribute writing at this point in the stream.
	 */
	void writeAttribute(String attributeName, String value);
// MrPperf - added for faster attribute writing
	void writeAttribute(char[] attributeName, String value) ;

//	void writeAttribute(String prefix, String namespaceURI,
//			String localName, String value) throws XmlStreamException;

//	void writeAttribute(String namespaceURI, String localName,
//			String value) throws XmlStreamException;

//	void writeNamespace(String prefix, String namespaceURI)
//			throws XmlStreamException;

//	void writeDefaultNamespace(String namespaceURI)
//			throws XmlStreamException;

	/** Write an xml comment with the data enclosed.
	 * @param data - text of the comments
	 * @throws XmlStreamException 
	 */
	void writeComment(String data);

//	void writeProcessingInstruction(String target)
//			throws XmlStreamException;

//	void writeProcessingInstruction(String target, String data)
//			throws XmlStreamException;

	/** Write a CData section
	 * @param data - text of the data to be in the CData Section.  Must
	 * not be null!
	 * @throws XmlStreamException 
	 */
	void writeCData(String data);

	/** Write a DTD section.  This string represents the entire
	 * doctypedecl production.
	 * @param dtd - the String of the DTD to be written.
	 * @throws XmlStreamException 
	 */
	void writeDtd(String dtd) throws XmlStreamException;

	/**
	 * Writes an entity reference
	 * @param name the name of the entity
	 * @throws XmlStreamException 
	 */
//	void writeEntityRef(String name) throws XmlStreamException;

	/** Write the XML Declaration. Defaults the XML version to 1.0,
	 * and the encoding to utf-8.
	 * @throws XmlStreamException 
	 */
	void writeStartDocument();

	/** Write the XML Declaration. Defaults the encoding to utf-8.
	 * @param version version of the xml document
	 * @throws XmlStreamException 
	 */
	void writeStartDocument(String version);

	/**Write the XML Declaration.  Note that the encoding parameter does
	 * not set the actual encoding of the underlying output.  That must 
	 * be set when the instance of the XMLStreamWriter is created using the
	 * XMLOutputFactory
	 * @param encoding encoding of the xml declaration
	 * @param version version of the xml document
	 * @throws XmlStreamException 
	 */
	void writeStartDocument(String encoding, String version);

	/** Write text to the output. It will escape '&', '<', '>'.
	 * @param text the value to write
	 * @throws XmlStreamException 
	 */
	void writeCharacters(String text);

	/** Write text to the output.  It will escape '&', '<', '>'.
	 * @param text the value to write
	 * @param start the starting position in the array
	 * @param len the number of characters to write
	 * @throws XmlStreamException 
	 */
	void writeCharacters(char[] text, int start, int len);
	
	void writeRawCharacters(char[] text, int start, int len);

//	public String getPrefix(String uri) throws XmlStreamException;

//	void setPrefix(String prefix, String uri) throws XmlStreamException;

//	void setDefaultNamespace(String uri) throws XmlStreamException;

//	void setNamespaceContext(NamespaceContext context)
//			throws XmlStreamException;

//	public NamespaceContext getNamespaceContext();

//	public Object getProperty(java.lang.String name)
//			throws IllegalArgumentException;

}
