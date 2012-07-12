/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * This is a basic non-thread safe implementation of an XmlStreamWriter.
 *
 * All the variables are protected because this allows for example, an HTML
 * stream writer to manipulate the stack, which is a necessity.
 * 
 */
public class XmlStreamWriter implements IXmlStreamWriter {
	/*
	 * It is much more efficient to write(char[]) than to write("&amp;").  The
	 * reason is that write(String s) will result in System.arrayCopy(...) or other
	 * methods that result in a copy of the String's contents being created
	 * and thus being a junk object.  Other possibility is s.charAt(index) lookups
	 * that go thru extra index bounds checks before actually going to do the low-level
	 * char[] index lookup.  
	 * 
	 * We also don't want to do write(char) if possible since this often results 
	 * in extra overhead of internal locking or even worse creation of a single 
	 * value char[] to be processed by commonmethods.
	 */
	protected static final char[] AMP_ESCAPE_CHARS = {'&', 'a', 'm', 'p', ';'} ;
	protected static final char[] LT_ESCAPE_CHARS = {'&', 'l', 't', ';'} ;
	protected static final char[] GT_ESCAPE_CHARS = {'&', 'g', 't', ';'} ;
	protected static final char[] QUOT_ESCAPE_CHARS = {'&', 'q', 'u', 'o', 't', ';'} ;
	
	protected static final char[] SINGLE_SPACE_CHARS = {' '} ;
	protected static final char[] DOUBLE_QUOTE_CHARS = {'"'} ;
	protected static final char[] EQUALS_SIGN_DOUBLE_QUOTE_CHARS = {'=', '"'} ;
	
	protected static final char[] CDATA_END_CHARS = {']', ']', '>'};
	protected static final char[] CDATA_START_CHARS = {'<', '!', '[', 'C', 'D', 'A', 'T', 'A', '['};
	protected static final char[] COMMENT_END_CHARS = {'-', '-', '>'};
	protected static final char[] COMMENT_START_CHARS = {'<', '!', '-', '-'};
	
	protected static final char[] SINGLE_LT_CHARS = {'<'} ;
	protected static final char[] SINGLE_GT_CHARS = {'>'} ;
	protected static final char[] LT_SLASH_CHARS = {'<', '/'} ;
	protected static final char [] SLASH_CHARS = {'/'};

	// Array list was chosen for the stack because:
	// - it is not synchronized
	// - Stack set can be set upfront.  Therefore only one memory allocation.
// MrPperf -- change Stack type to char[] 
	protected final ArrayList<String> m_tagStack = new ArrayList<String>(20);

	protected final Writer m_writer;
	protected final IIndenter m_indenter;
	protected boolean m_inStartTag = false;

	//
	// Constructor(s)
	//
	public XmlStreamWriter(final Writer writer) {
		this(writer, new IIndenter.Pretty());
	}
	
	public XmlStreamWriter(final Writer writer, final IIndenter indenter) {
		m_writer = writer;
		m_indenter = indenter;		
	}
			
	//
	// API
	//
	public void writeAttribute(
		final char[] key, final String value) throws XmlStreamException
	{
		try {
			m_writer.write(SINGLE_SPACE_CHARS) ; // MrPperf- (' ');
			m_writer.write(key);
			m_writer.write(EQUALS_SIGN_DOUBLE_QUOTE_CHARS) ; // MrpPerf - ("=\"");
			writeEscapedVal(value, true /* escape quotes */);
	//OR writeAttrVal(value) for original 
			m_writer.write(DOUBLE_QUOTE_CHARS) ; // MrPperf- ('"');
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
	
	public void writeAttribute(final String key, final String value)
		throws XmlStreamException
	{
// MrPperf - do we really want to get a copy of the chars?
//		final char[] chars = key.toCharArray() ;
//		writeAttribute(chars, value) ;
		try {
			m_writer.write(SINGLE_SPACE_CHARS) ; // MrPperf- (' ');
			m_writer.write(key);
			m_writer.write(EQUALS_SIGN_DOUBLE_QUOTE_CHARS) ; // MrpPerf - ("=\"");
			writeEscapedVal(value, true /* escape quotes */);
	//OR writeAttrVal(value) for original 
			m_writer.write(DOUBLE_QUOTE_CHARS) ; // MrPperf- ('"');
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
	
	private void writeEscapedVal(
		final String value, final boolean escapeQuotes) throws IOException
	{
//		final char[] chars = value.toCharArray() ; // yes we know this is a copy
//		handleChars(chars, chars.length, escapeQuotes) ;
		handleChars(value, value.length(), escapeQuotes) ;
	}
	
	private void handleChars(
		final String chars,
		final int count,
		final boolean escapeQuotes)
			throws IOException
	{		
		int okCharCount = 0 ;
		int offset = 0;
		for (int i = 0; i < count; i++) {
			final char c = chars.charAt(i) ;
			char[] escapeChars = null;
			switch (c) {
				case '&' :
					escapeChars = AMP_ESCAPE_CHARS ;
					break;
					
				case '<' :
					escapeChars = LT_ESCAPE_CHARS ;
					break;
					
				case '>' :
					escapeChars = GT_ESCAPE_CHARS ;
					break;					
				case '\"' :
					if (escapeQuotes) {
						escapeChars = QUOT_ESCAPE_CHARS ;
						break;
					}
					// expected fall thru					
				default:
					// count chars and keep looping
					okCharCount++ ;
					break;
			}			
			if (escapeChars != null) {
				m_writer.write(chars, offset, okCharCount);
				// Write the escaped chars out
				m_writer.write(escapeChars);
				offset = offset + okCharCount + 1;
				okCharCount = 0;
			} 
		}
		
		// write whatevers left over -- could be entire String if there are
		// no chars to escape.
		m_writer.write(chars, offset, okCharCount);
	}
	// Writing with char[] is more efficient and saves over strings.charAt(i)
	// Note that writer.write(char) is very inefficient since the internal
	// impl will actually create a new char[] for that single char!
	
//	private void handleChars(
//		final char[] chars,
//		final int count,
//		final boolean escapeQuotes)
//			throws IOException
//	{		
//		int okCharCount = 0 ;
//		
//		int offset = 0;
//		for (int i = 0; i < count; i++) {
//			final char c = chars[i] ;
//			char[] escapeChars = null;
//			switch (c) {
//				case '&' :
//					escapeChars = AMP_ESCAPE_CHARS ;
//					break;
//					
//				case '<' :
//					escapeChars = LT_ESCAPE_CHARS ;
//					break;
//					
//				case '>' :
//					escapeChars = GT_ESCAPE_CHARS ;
//					break;					
//				case '\"' :
//					if (escapeQuotes) {
//						escapeChars = QUOT_ESCAPE_CHARS ;
//						break;
//					}
//					// expected fall thru					
//				default:
//					// count chars and keep looping
//					okCharCount++ ;
//					break;
//			}			
//			if (escapeChars != null) {
//				m_writer.write(chars, offset, okCharCount);
//				// Write the escaped chars out
//				m_writer.write(escapeChars);
//				offset = offset + okCharCount + 1;
//				okCharCount = 0;
//			} 
//		}
//		
//		//no special chars - write entire char[] is more efficient
//		m_writer.write(chars, offset, okCharCount);
//	}
	
//	private void writeAttrVal(final String value) throws IOException {
//		int index = -1;
//		int size = value.length();
//		for (int i = 0; i < size; i++) {
//			final char c = value.charAt(i);
//			switch (c) {
//				case '&' :
//				case '<' :
//				case '>' :
//				case '\"' :
//					index = i;
//					i = size;
//					break;
//			}
//		}
//		if (index == -1) { //no special char
//			m_writer.write(value);
//			return;
//		}
//		
//		if (index > 0) {
//			m_writer.write(value.substring(0, index));
//		}
//		for (int i = index; i < size; i++) {
//			final char c = value.charAt(i);
//			switch (c) {
//				case '&' :
//					m_writer.write("&amp;");
//					break;
//				case '<' :
//					m_writer.write("&lt;");
//					break;
//				case '>' :
//					m_writer.write("&gt;");
//					break;
//				case '\"' :
//					m_writer.write("&quot;");
//					break;
//				default :
//					m_writer.write(c);
//			}
//		}
//	}

	public void writeCData(final String data) throws XmlStreamException {
		try {
			closeStartTagIfNecessary();
			m_writer.write(CDATA_START_CHARS);
			m_writer.write(data);
			m_writer.write(CDATA_END_CHARS);
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}

	public void writeRaw(final String data) throws XmlStreamException {
		try {
			closeStartTagIfNecessary();
			m_writer.write(data);
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
	
	public void writeCharacters(final char[] text, final int start, final int len)
		throws XmlStreamException
	{
		// A String is a CharSequence so we use CharSequence as the LCD
		final CharSequence sequence = new CharSequence() {
			public char charAt(final int index) {
				return text[start+index];
			}
			public int length() {
				return len;
			}
			public CharSequence subSequence(final int begin, final int end) {
				throw new RuntimeException("not implemented");
			}			
		};
		writeEscapedForElementBlock(sequence);
	}

	public void writeCharacters(final String text) throws XmlStreamException {
//		writeEscapedForElementBlock(text);	
//	MrPperf - following code added
		closeStartTagIfNecessary() ;
		try {
			writeEscapedVal(text, false /* escape quotes */) ;
		}
		catch(IOException e) {
			throw new RuntimeException(e) ;
		}
	}
	
	// A String is a CharSequence so we use CharSequence as the LCD
	private void writeEscapedForElementBlock(final CharSequence sequence) {
		try {
			closeStartTagIfNecessary();
			
			final int sequenceLength = sequence.length() ;
			for (int i = 0; i < sequenceLength; i++) {
				final char c = sequence.charAt(i);
				switch (c) {
					case '&' :
						m_writer.write(AMP_ESCAPE_CHARS) ; // MrPperf - ("&amp;");
						break;
					case '<' :
						m_writer.write(LT_ESCAPE_CHARS) ; // MrPperf - ("&lt;");
						break;
					case '>' :
						m_writer.write(GT_ESCAPE_CHARS) ; // MrPperf - ("&gt;");
						break;
					default :
						m_writer.write(c);
				}
			}
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}		
	}
	
	public void writeComment(final String data) throws XmlStreamException {
		try {
			closeStartTagIfNecessary();
			m_writer.write(COMMENT_START_CHARS) ; // MrPperf- (COMMENT_START);
			m_writer.write(data);
			m_writer.write(COMMENT_END_CHARS) ; // MrPperf - (COMMENT_END);
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
	
	public void writeDtd(final String dtd) {
		writeRaw(dtd);
		if (!dtd.endsWith("\n")) {
// TODO: investigate whether indenter should do this
			writeRaw("\r\n");
		}
	}
	
	public void writeEmptyElement(final String tagName) throws XmlStreamException {
		throw new RuntimeException("not implemented!");
	}

	public void writeEndDocument() throws XmlStreamException {
		while (m_tagStack.size() > 0) {
			writeEndElement();
		}
		try {
			m_writer.flush();
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}

	public void writeStartDocument() throws XmlStreamException {
		// Empty on purpose
	}

	public void writeStartDocument(final String encoding, final String version)
		throws XmlStreamException 
	{
		// Empty on purpose
	}

	public void writeStartDocument(final String version) throws XmlStreamException {
		// Empty on purpose
	}
	public void writeEndElement() throws XmlStreamException {
// MrPperf - stack is now of type char[]
		final String tagName = m_tagStack.remove(m_tagStack.size()-1);
		try {
			if (m_inStartTag) {
				m_writer.write(SLASH_CHARS);
				writeClosingStartTag();		
			} else {
				m_indenter.indent(m_writer, m_tagStack.size());
				m_writer.write(LT_SLASH_CHARS) ; // MrPperf - ("</");
				m_writer.write(tagName);
				m_writer.write(SINGLE_GT_CHARS) ; // MrPperf - ('>');		
			}
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}

// MrPperf - more efficient to be able to write elements as char[] vs tagName String
	public void writeStartElement(final char[] tagNameAsChars) throws XmlStreamException {
		try {
			closeStartTagIfNecessary();
			m_indenter.indent(m_writer, m_tagStack.size());
			m_writer.write(SINGLE_LT_CHARS) ; // MrPperf - ('<');
			m_writer.write(tagNameAsChars);
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
// MrPPerf -- FIX ME
String tagName = new String(tagNameAsChars) ;
		m_tagStack.add(tagName);
		m_inStartTag = true;		
	}
	
	public void writeStartElement(final String tagName) throws XmlStreamException {
//		writeStartElement(tagName.toCharArray()) ;
		try {
			closeStartTagIfNecessary();
			m_indenter.indent(m_writer, m_tagStack.size());
			m_writer.write(SINGLE_LT_CHARS) ; // MrPperf - ('<');
			m_writer.write(tagName);
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
		m_tagStack.add(tagName);
		m_inStartTag = true;	
	}
	protected void closeStartTagIfNecessary() throws XmlStreamException {
		if (m_inStartTag) {
			writeClosingStartTag();
		}
	}
	private void writeClosingStartTag(){
		try {
			m_writer.write(SINGLE_GT_CHARS) ; // MrPperf - ('>');
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
		m_inStartTag = false;
	}
	
	public void flush() throws XmlStreamException {
		try {
			m_writer.flush();
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
	
// MrPperf - stack is now char[]
	protected String getCurrentTagName() {
		return m_tagStack.get(m_tagStack.size()-1);
	}
	
	public void writeRawCharacters(char[] text, int start, int len) {
		try {
            closeStartTagIfNecessary();
            m_writer.write(text, start, len);
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
		
	}

	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return m_writer.toString() ;
	}
}
