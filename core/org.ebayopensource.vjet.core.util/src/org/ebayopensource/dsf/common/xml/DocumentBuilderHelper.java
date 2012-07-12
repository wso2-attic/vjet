/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author msomani
 *
 */
public class DocumentBuilderHelper {
	private static ThreadLocal<WrappedDocumentBuilderFactory> s_tl_factory = new ThreadLocal<WrappedDocumentBuilderFactory>() {
		protected WrappedDocumentBuilderFactory initialValue() {
            return new WrappedDocumentBuilderFactory(DocumentBuilderFactory.newInstance());
        }
	};
	
	//TODO, allow sets after appropriate changes.  
	private static final boolean allowsSets = false;
	
	private static ThreadLocal<WrappedDocumentBuilder> s_tl_builder = new ThreadLocal<WrappedDocumentBuilder>();
	
	/**
	 * Returns a cached (thread local) document builder factory.  Multiple calls across different method calls
	 * would return cached object to minimize the cost of creation as factory object can be an expensive 
	 * object to create.  
	 * 
	 * @return {@link DocumentBuilderFactory}
	 */
	public static DocumentBuilderFactory getDocumentBuilderFactory() {
		return s_tl_factory.get();
	}
	
	/**
	 * Returns a cached (thread local) document builder.  Multiple calls across different method calls
	 * would return cached object to minimize the cost of creation as document builder can be an expensive 
	 * object to create.  
	 * 
	 * @return {@link DocumentBuilder}
	 * @throws ParserConfigurationException
	 */
	public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		return getDocumentBuilder(getDocumentBuilderFactory());
	}
	
	/**
	 * Returns a cached (thread local) document builder.  Multiple calls across different method calls
	 * would return cached object to minimize the cost of creation as document builder can be an expensive 
	 * object to create.  
	 * 
	 * @param factory The factory using which the document builder is to be created.  
	 * @return {@link DocumentBuilder}
	 * @throws ParserConfigurationException
	 */
	public static DocumentBuilder getDocumentBuilder(DocumentBuilderFactory factory) 
			throws ParserConfigurationException {
		WrappedDocumentBuilder builder = s_tl_builder.get();
		if (builder == null) {
			builder = new WrappedDocumentBuilder(factory.newDocumentBuilder());
			s_tl_builder.set(builder);
		}
		return builder;
	}
	
	private static void checkSets() {
		if (!allowsSets) {
			throw new UnsupportedOperationException();
		}
	}
	
	private static class WrappedDocumentBuilderFactory extends DocumentBuilderFactory {
		private DocumentBuilderFactory m_factory;
		
		private WrappedDocumentBuilderFactory(DocumentBuilderFactory factory) {
			this.m_factory = factory;
		}

		public Object getAttribute(String name) throws IllegalArgumentException {
			return m_factory.getAttribute(name);
		}

		public boolean getFeature(String name)
				throws ParserConfigurationException {
			return m_factory.getFeature(name);
		}

		public Schema getSchema() {
			return m_factory.getSchema();
		}

		public boolean isCoalescing() {
			return m_factory.isCoalescing();
		}

		public boolean isExpandEntityReferences() {
			return m_factory.isExpandEntityReferences();
		}

		public boolean isIgnoringComments() {
			return m_factory.isIgnoringComments();
		}

		public boolean isIgnoringElementContentWhitespace() {
			return m_factory.isIgnoringElementContentWhitespace();
		}

		public boolean isNamespaceAware() {
			return m_factory.isNamespaceAware();
		}

		public boolean isValidating() {
			return m_factory.isValidating();
		}

		public boolean isXIncludeAware() {
			return m_factory.isXIncludeAware();
		}

		public DocumentBuilder newDocumentBuilder()
				throws ParserConfigurationException {
			return m_factory.newDocumentBuilder();
		}

		public void setAttribute(String name, Object value)
				throws IllegalArgumentException {
			checkSets();
			m_factory.setAttribute(name, value);
		}

		public void setCoalescing(boolean coalescing) {
			checkSets();
			m_factory.setCoalescing(coalescing);
		}

		public void setExpandEntityReferences(boolean expandEntityRef) {
			checkSets();
			m_factory.setExpandEntityReferences(expandEntityRef);
		}

		public void setFeature(String name, boolean value)
				throws ParserConfigurationException {
			checkSets();
			m_factory.setFeature(name, value);
		}

		public void setIgnoringComments(boolean ignoreComments) {
			checkSets();
			m_factory.setIgnoringComments(ignoreComments);
		}

		public void setIgnoringElementContentWhitespace(boolean whitespace) {
			checkSets();
			m_factory.setIgnoringElementContentWhitespace(whitespace);
		}

		public void setNamespaceAware(boolean awareness) {
			checkSets();
			m_factory.setNamespaceAware(awareness);
		}

		public void setSchema(Schema schema) {
			checkSets();
			m_factory.setSchema(schema);
		}

		public void setValidating(boolean validating) {
			checkSets();
			m_factory.setValidating(validating);
		}

		public void setXIncludeAware(boolean state) {
			checkSets();
			m_factory.setXIncludeAware(state);
		}
	}
	
	private static class WrappedDocumentBuilder extends DocumentBuilder {
		private DocumentBuilder m_builder;
		
		private WrappedDocumentBuilder(DocumentBuilder builder) {
			this.m_builder = builder;
		}

		public DOMImplementation getDOMImplementation() {
			return m_builder.getDOMImplementation();
		}

		public Schema getSchema() {
			return m_builder.getSchema();
		}

		public boolean isNamespaceAware() {
			return m_builder.isNamespaceAware();
		}

		public boolean isValidating() {
			return m_builder.isValidating();
		}

		public boolean isXIncludeAware() {
			return m_builder.isXIncludeAware();
		}

		public Document newDocument() {
			return m_builder.newDocument();
		}

		public Document parse(File f) throws SAXException, IOException {
			return m_builder.parse(f);
		}

		public Document parse(InputSource is) throws SAXException, IOException {
			return m_builder.parse(is);
		}

		public Document parse(InputStream is, String systemId)
				throws SAXException, IOException {
			return m_builder.parse(is, systemId);
		}

		public Document parse(InputStream is) throws SAXException, IOException {
			return m_builder.parse(is);
		}

		public Document parse(String uri) throws SAXException, IOException {
			return m_builder.parse(uri);
		}

		public void reset() {
			m_builder.reset();
		}

		public void setEntityResolver(EntityResolver er) {
			checkSets();
			m_builder.setEntityResolver(er);
		}

		public void setErrorHandler(ErrorHandler eh) {
			checkSets();
			m_builder.setErrorHandler(eh);
		}

	}

}
