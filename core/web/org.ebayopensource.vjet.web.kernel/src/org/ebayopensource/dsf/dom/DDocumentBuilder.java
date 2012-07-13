/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.FileUtils;

public class DDocumentBuilder extends DocumentBuilder {

	//
	// Constructor(s)
	//
	public DDocumentBuilder() {
		
	}
	
	//
	// Helper(s)
	//
	public static DDocument getDocument(final String xml) {
		return getDocument(new StringBufferInputStream(xml)) ;
	}
	public static DDocument getDocument(
		final Class<?> anchor, final String resourceXml)
	{
		return getDocument(FileUtils.getResourceString(anchor, resourceXml)) ;
	}
	public static DDocument getDocument(final InputStream inputStream) {
		DDocumentBuilder builder = new DDocumentBuilder() ;
		try {	
			return (DDocument)builder.parse(inputStream) ;
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
		final DOMImplementation impl = reg.getDOMImplementation("xml") ;
		return impl ;
	}
	
	@Override
	public Document newDocument() {
		return new DDocument() ;
	}

	@Override
	public Document parse(final InputSource is) throws SAXException, IOException {
		try {
			final DDOMBuilder ddomBuilder = new DDOMBuilder() ;
			
			// We need to create a wrapper/adpater sou our DDOMBuilder can
			// work with the general purpose SAX processor
			final DDomBuilder_DefaultHandler builder 
				= new DDomBuilder_DefaultHandler(ddomBuilder) ;
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
//			factory.setValidating(true);
			SAXParser parser = factory.newSAXParser();
		
			parser.parse(is, builder) ;
			return ddomBuilder.getDDocument() ;
		}
		catch(Exception e) {
			throw new DsfRuntimeException(e) ;
		}
	}
	
////	@Override
//	public Document parse2(final InputSource is) throws SAXException, IOException {
//		try {
//			final HtmlSaxParser parser = new HtmlSaxParser();
//			final DDOMBuilder builder = new DDOMBuilder();
//			parser.setContentHandler(builder);
//			parser.parse(is) ;
//			return builder.getDDocument() ;
//		}
//		catch(Exception e) {
//			throw new DsfRuntimeException(e) ;
//		}
//	}

	@Override
	public boolean isNamespaceAware() {
		return true;
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
