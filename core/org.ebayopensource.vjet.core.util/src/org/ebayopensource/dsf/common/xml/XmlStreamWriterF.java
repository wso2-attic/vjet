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
import java.util.ArrayList;

/**
 * This is a basic non-thread safe implementation of an XmlStreamWriter.
 *
 * All the variables are protected because this allows for example, an HTML
 * stream writer to manipulate the stack, which is a necessity.
 */
public class XmlStreamWriterF implements IXmlStreamWriter {
	protected static final String AMP_ESCAPE_CHARS = "&amp;" ;
	protected static final String LT_ESCAPE_CHARS = "&lt;" ;
	protected static final String GT_ESCAPE_CHARS = "&gt;" ; 
	protected static final String QUOT_ESCAPE_CHARS = "&quot;" ;
	
	protected static final String SINGLE_SPACE_CHARS = " " ;
	protected static final String DOUBLE_QUOTE_CHARS = "\"" ; 
	protected static final String EQUALS_SIGN_DOUBLE_QUOTE_CHARS = "=\"" ;
	
	protected static final String CDATA_END_CHARS = "]]>" ;
	protected static final String CDATA_START_CHARS = "<![CDATA["; 
	protected static final String COMMENT_END_CHARS = "-->" ; 
	protected static final String COMMENT_START_CHARS = "<!--";
	
	protected static final String SINGLE_LT_CHARS = "<" ;
	protected static final String SINGLE_GT_CHARS = ">" ;
	protected static final String LT_SLASH_CHARS = "</" ;
	protected static final String SLASH_CHARS = "/" ;

	// Array list was chosen for the stack because:
	// - it is not synchronized
	// - Stack set can be set upfront.  Therefore only one memory allocation.
	// The stack is the depth of nesting of the tag structure - 20 is pretty deep
	protected final ArrayList<String> m_tagStack = new ArrayList<String>(20);

	protected final XmlStreamWriterOptions m_options ;
	
	protected boolean m_inStartTag = false;

	//
	// Constructor(s)
	//
	public XmlStreamWriterF() {
		this(new XmlStreamWriterOptions()) ;
	}

	public XmlStreamWriterF(XmlStreamWriterOptions options) {
		if(options == null) {
			throw new RuntimeException("Options can not be null") ;
		}
		m_options = options ;
	}
	
	//
	// API
	//
	public boolean isEscapingEnabled() {
		return m_options.isEscapeEnabled() ;
	}
	
	public void setEscapingEnabled(boolean escapingEnabled) {
		m_options.setEscapeEnabled(escapingEnabled) ;
	}
	
	public XmlStreamWriterOptions getOptions() {
		return m_options ;
	}
	
	public void writeAttribute(
		final char[] key, final String value) throws XmlStreamException
	{
		try {
			m_options.getWriter().write(SINGLE_SPACE_CHARS) ;
			m_options.getWriter().write(key);
			m_options.getWriter().write(EQUALS_SIGN_DOUBLE_QUOTE_CHARS) ;
			handleValueSubset(value, 0, value.length(), true) ;
			m_options.getWriter().write(DOUBLE_QUOTE_CHARS) ;
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
	
	public void writeAttribute(final String key, final String value)
		throws XmlStreamException
	{
		try {
			m_options.getWriter().write(SINGLE_SPACE_CHARS) ; 
			m_options.getWriter().write(key);
			m_options.getWriter().write(EQUALS_SIGN_DOUBLE_QUOTE_CHARS) ;
			handleValueSubset(value, 0, value.length(), true);	
			m_options.getWriter().write(DOUBLE_QUOTE_CHARS) ;
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}

	private void handleEscapedValue(
		final String value, final int offset, final int count, 
		final String alreadyEscapedValue, 
		final int remainingCount,
		final boolean escapeQuotes) throws IOException
	{
		// Write the next section upto the char needed escaping
		m_options.getWriter().write(value, offset, count) ;
		
		// Write the already escaped values out
		// caller sees '&' and sends "&amp;"
		m_options.getWriter().write(alreadyEscapedValue);
		
		// *** RECURSION ***
		// We basically recurse on processing what's left 
		handleValueSubset(
			value, offset + count + 1, remainingCount - count - 1, 
			escapeQuotes) ;
	}
	
	// *** RECURSION ***
	private void handleValueSubset(
		final String value, final int offset, final int count,
		final boolean escapeQuotes // this is NOT the same as m_escapeEnabled
		) throws IOException
	{
		// If were not escaping at all, we don't worry about the extra escape
		// check for quotes
		if (! isEscapingEnabled()) {
			m_options.getWriter().write(value, offset, count);
			return ; 
		}

		// *** EXIT FROM RECURSION ***
		if (offset >= value.length()) return ;
		
		final int size = offset + count ;
		int okCharCount = 0 ;
		
		for (int i = offset; i < size; i++) {
			final char c = value.charAt(i) ;
			switch (c) {
				case '&' :
					// *** RECURSION ***
					handleEscapedValue(value, offset, okCharCount, 
							AMP_ESCAPE_CHARS, count, escapeQuotes) ;
					return ;
					
				case '<' :
					// *** RECURSION ***
					handleEscapedValue(value, offset, okCharCount, 
							LT_ESCAPE_CHARS, count, escapeQuotes) ;
					return ;
					
				case '>' :
					// *** RECURSION ***
					handleEscapedValue(value, offset, okCharCount, 
							GT_ESCAPE_CHARS, count, escapeQuotes) ;
					return ;
					
				case '\"' :
					if (escapeQuotes) {
						// *** RECURSION ***
						handleEscapedValue(value, offset, okCharCount, 
							QUOT_ESCAPE_CHARS, count, escapeQuotes) ;
						return ;
					}
					// expected fall thru
					
				default:
					// keep looping
					okCharCount++ ;
			}
		}
		
		//no special chars - write entire char[] is more efficient
		m_options.getWriter().write(value, offset, count);
	}
	
	public void writeCData(final String data) throws XmlStreamException {
		try {
			closeStartTagIfNecessary();
			m_options.getWriter().write(CDATA_START_CHARS);
			m_options.getWriter().write(data);
			m_options.getWriter().write(CDATA_END_CHARS);
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}

	public void writeRaw(final String data) throws XmlStreamException {
		try {
			closeStartTagIfNecessary();
			m_options.getWriter().write(data);
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
		closeStartTagIfNecessary() ;
		try {
			boolean escapeQuotes = false ;
			handleValueSubset(text, 0, text.length(), escapeQuotes) ;
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
						m_options.getWriter().write(AMP_ESCAPE_CHARS) ;
						break;
					case '<' :
						m_options.getWriter().write(LT_ESCAPE_CHARS) ;
						break;
					case '>' :
						m_options.getWriter().write(GT_ESCAPE_CHARS) ;
						break;
					default :
						m_options.getWriter().write(c);
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
			m_options.getWriter().write(COMMENT_START_CHARS) ;
			m_options.getWriter().write(data);
			m_options.getWriter().write(COMMENT_END_CHARS) ;
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
			m_options.getWriter().flush();
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
		final String tagName = m_tagStack.remove(m_tagStack.size()-1);
		try {
			if (m_inStartTag) {
				m_options.getWriter().write(SLASH_CHARS);
				writeClosingStartTag();		
			} else {
				m_options.getIndenter().indent(m_options.getWriter(), m_tagStack.size());
				m_options.getWriter().write(LT_SLASH_CHARS) ;
				m_options.getWriter().write(tagName);
				m_options.getWriter().write(SINGLE_GT_CHARS) ;		
			}
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}

	public void writeStartElement(final char[] tagNameAsChars) throws XmlStreamException {
		writeStartElement(new String(tagNameAsChars)) ;
	}
	
	public void writeStartElement(final String tagName) throws XmlStreamException {
		try {
			closeStartTagIfNecessary();
			m_options.getIndenter().indent(m_options.getWriter(), m_tagStack.size());
			m_options.getWriter().write(SINGLE_LT_CHARS) ;
			m_options.getWriter().write(tagName);
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
			m_options.getWriter().write(SINGLE_GT_CHARS) ;
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
		m_inStartTag = false;
	}
	
	public void flush() throws XmlStreamException {
		try {
			m_options.getWriter().flush();
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
	
	protected String getCurrentTagName() {
		return m_tagStack.get(m_tagStack.size()-1);
	}
	
	public void writeRawCharacters(char[] text, int start, int len) {
		try {
            closeStartTagIfNecessary();
            m_options.getWriter().write(text, start, len);
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
}
