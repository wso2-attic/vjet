/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.DocumentType;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.common.xml.XmlStreamException;
import org.ebayopensource.dsf.common.xml.XmlStreamWriter;
import org.ebayopensource.dsf.dom.DDocumentType;
import org.ebayopensource.dsf.html.HtmlWriterCtx;
import org.ebayopensource.dsf.html.dom.DHtmlDocType;


public class HtmlStreamWriter extends XmlStreamWriter
	implements IHtmlStreamWriter
{
	private final char[][] m_notEscapeTagsChar; 
	private final String[] m_notEscapeTags;
	
	final static private String DOC_START = "<?";
	final static private String XML_10 = "xml version=\"1.0";
	final static private String ENCODE = "\" encoding=\"";
	final static private String DOC_END = "\"?>\r\n";   //new line feed is added here, see XmlStreamWriter.writeDtd() 
	final static private String XML_11 = "<?xml version=\"1.1";
	
	static private String s_defaultDocTypeString ; 
	
	static {
		final DHtmlDocType tempDocType =
			DHtmlDocType.createDocType(null, DHtmlDocType.HTML_STRICT);
		StringWriter w = new StringWriter(100) ;
		tempDocType.write(w);
		s_defaultDocTypeString = w.toString();
	}
	
	//
	// Constructor(s)
	//
	public HtmlStreamWriter(final Writer writer) {
		this(writer, new IIndenter.Pretty());
	}
	
	public HtmlStreamWriter(final Writer writer, final IIndenter indenter) {
		this(new HtmlWriterCtx(writer, indenter));		
	}
	
	public HtmlStreamWriter(final HtmlWriterCtx options) {
		super(options.getWriter(), options.getIndenter());
		m_notEscapeTagsChar = options.getNotEscapeTags();
		
		final int len = m_notEscapeTagsChar.length ;
		m_notEscapeTags = new String[len] ;
		for(int i = 0; i < m_notEscapeTagsChar.length; i++) {
			m_notEscapeTags[i] = new String(m_notEscapeTagsChar[i]) ;
		}
	}
	
	//
	// API
	//
	public void ignoreCurrentEndTag() {
		final int size = m_tagStack.size();
		super.m_tagStack.remove(size-1);
		closeStartTagIfNecessary();
	}
	
	public void writeStartDocument(final DocumentType doctype) {
		if(doctype == null) {
			//defaults to HTML strict - only need to create the String once..
			try {
				m_writer.write(s_defaultDocTypeString) ;
			}
			catch(IOException e) {
				throw new DsfRuntimeException(e) ;
			}
		} 
		else {
			((DDocumentType)doctype).write(m_writer);
		}
	}
	
	@Override
	public void writeStartDocument(final String encoding, final String version)
		throws XmlStreamException 
	{
		if (encoding == null && version == null){
			return;
		}
		final StringBuilder tmp = new StringBuilder();
		tmp.append(DOC_START);
		if (version != null) {
			tmp.append(version.equals("1.0")? XML_10 : XML_11);
		}
		if (encoding != null){		
			tmp.append(ENCODE).append(encoding);
		}
		tmp.append(DOC_END);
	
		writeRaw(tmp.toString());
		//indenter didn't do the job right
		//m_indenter.indent(m_writer, 0);				
	}
	
	@Override
	public void writeStartDocument(final String version)
		throws XmlStreamException 
	{
		writeStartDocument(null, version);
	}

	@Override
	public void writeCharacters(final char[] text, final int start, final int len)
		throws XmlStreamException
	{
		if (shouldSkipEscaping()) {
			try {
				closeStartTagIfNecessary();
				m_writer.write(text, start, len);
			} 
			catch (IOException e) {
				throw new XmlStreamException(e);
			}
		} 
		else {
			super.writeCharacters(text, start, len);
		}
	}
	
	@Override
	public void writeCharacters(final String text) throws XmlStreamException {
		if (shouldSkipEscaping()) {
			writeRaw(text);
		} 
		else {
			super.writeCharacters(text);
		}
	}
	
	private boolean shouldSkipEscaping() {
		if (m_tagStack.size() <= 0) {
			return false;
		}
		
// MrPperf - using char[] now
		final String currentTagName = getCurrentTagName();
		// Do NOT use compact for() here since it creates a iterator underneath
		for(int i = 0; i < m_notEscapeTags.length; i++) {
// MrPperf - switch to Arrays.equals() vs equalsIgnoreCase() when we had a String type
//			if (Arrays.equals(m_notEscapeTags[i], currentTagName)) {
			if (currentTagName.equals(m_notEscapeTags[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void writeEndElement() throws XmlStreamException {
//		 MrPperf - stack is now of type char[]
		final String tagName = m_tagStack.remove(m_tagStack.size()-1);
		try {
			closeStartTagIfNecessary();
			m_indenter.indent(m_writer, m_tagStack.size());
			m_writer.write(LT_SLASH_CHARS) ; // MrPperf - ("</");
			m_writer.write(tagName);
			m_writer.write(SINGLE_GT_CHARS) ; // MrPperf - ('>');
		} 
		catch (IOException e) {
			throw new XmlStreamException(e);
		}
	}
}
