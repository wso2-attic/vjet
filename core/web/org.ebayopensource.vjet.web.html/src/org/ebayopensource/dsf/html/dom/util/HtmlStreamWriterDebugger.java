/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

import org.ebayopensource.dsf.common.xml.XmlStreamWriterDebugger;


public class HtmlStreamWriterDebugger 
	extends XmlStreamWriterDebugger implements IHtmlStreamWriter
{
	private final IHtmlStreamWriter m_xmlStreamWriter;
	
	//
	// Constructor(s)
	//
	public HtmlStreamWriterDebugger(final IHtmlStreamWriter streamWriter) {
		super(streamWriter);
		m_xmlStreamWriter = streamWriter;
	}
	
	//
	// API
	//
	public void ignoreCurrentEndTag() {
		m_xmlStreamWriter.ignoreCurrentEndTag();
		out("ignoreCurrentEndTag()");
	}
	
	private void out(final String message) {
		System.out.println(message); // KEEPME
	}
}
