/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dom.DDOMImplementationRegistry;
import org.ebayopensource.dsf.html.dom.util.HtmlBuilder;
import org.ebayopensource.dsf.html.sax.HtmlSaxParser;
import org.ebayopensource.dsf.common.FileUtils;

public class DHtmlDocumentBuilder extends DocumentBuilder {

	//
	// Constructor(s)
	//
	public DHtmlDocumentBuilder() {
		
	}
	
	//
	// Helper(s)
	//
	public static DHtmlDocument getDocument(final String html) {
		return getDocument(new StringBufferInputStream(html)) ;
	}
	public static DHtmlDocument getDocument(
		final Class<?> anchor, final String resourceHtml)
	{
		return getDocument(FileUtils.getResourceString(anchor, resourceHtml)) ;
	}
	public static DHtmlDocument getDocument(final InputStream inputStream) {
		DHtmlDocumentBuilder builder = new DHtmlDocumentBuilder() ;
		try {	
			return (DHtmlDocument)builder.parse(inputStream) ;
		}
		catch(Exception e) {
			throw new DsfRuntimeException(e) ;
		}
	}
	
	//
	// Satisfy abstract methods from DocumentBuilder
	//
	@Override
	public DOMImplementation getDOMImplementation() {
		final DDOMImplementationRegistry reg = DDOMImplementationRegistry.newInstance() ;
		final DOMImplementation impl = reg.getDOMImplementation("html") ;
		return impl ;
	}
	
	@Override
	public Document newDocument() {
		return new DHtmlDocument() ;
	}

	@Override
	public Document parse(final InputSource is) throws SAXException, IOException {
		try {
			final HtmlSaxParser parser = new HtmlSaxParser();
			final HtmlBuilder builder = new HtmlBuilder();
			parser.setContentHandler(builder);
			parser.parse(is) ;
			return builder.getHTMLDocument() ; 
		}
		catch(Exception e) {
			throw new DsfRuntimeException(e) ;
		}
	}

	@Override
	public boolean isNamespaceAware() {
		return false;
	}

	@Override
	public boolean isValidating() {
		return false;
	}

	@Override
	public void setEntityResolver(final EntityResolver er) {
		// Not supported for now
	}

	@Override
	public void setErrorHandler(final ErrorHandler eh) {
		// Not supported for now
	}
}
