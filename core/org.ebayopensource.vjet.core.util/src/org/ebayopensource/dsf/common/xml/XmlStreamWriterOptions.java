/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

import java.io.StringWriter;
import java.io.Writer;


public class XmlStreamWriterOptions {
	private Writer m_writer ;
	private IIndenter m_indenter = IIndenter.COMPACT;
	private boolean m_escapeEnabled = false ;

	//
	// Constructor(s) 
	//
	public XmlStreamWriterOptions() {
		// empty on purpose
	}
	
	public XmlStreamWriterOptions(
		final Writer writer, final IIndenter indenter, final boolean escapeEnabled)
	{
		m_writer = writer ;
		m_indenter = indenter ;
		m_escapeEnabled = escapeEnabled ;
	}
	
	//
	// API
	//
	public Writer getWriter() {
		if (m_writer == null) {
			m_writer = new StringWriter() ;
		}
		return m_writer ;
	}
	public XmlStreamWriterOptions setWriter(final Writer writer) {
		m_writer = writer ;
		return this ;
	}
	
	public IIndenter getIndenter() {
		return m_indenter ;
	}
	public XmlStreamWriterOptions setIndenter(final IIndenter indenter) {
		m_indenter = indenter ;
		return this ;
	}
	
	public boolean isEscapeEnabled() {
		return m_escapeEnabled ;
	}
	
	public XmlStreamWriterOptions setEscapeEnabled(final boolean escapeEnabled) {
		m_escapeEnabled = escapeEnabled ;
		return this ;
	}
	
}
