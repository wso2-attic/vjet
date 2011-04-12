/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.util.xml.rt;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 
 */
public class XmlReaderHelper {

	/** Namespaces feature id (http://xml.org/sax/features/namespaces). */
	static final String NAMESPACES_FEATURE_ID =
		"http://xml.org/sax/features/namespaces";

	/** Namespace prefixes feature id (http://xml.org/sax/features/namespace-prefixes). */
	static final String NAMESPACE_PREFIXES_FEATURE_ID =
		"http://xml.org/sax/features/namespace-prefixes";

	/** Validation feature id (http://xml.org/sax/features/validation). */
	static final String VALIDATION_FEATURE_ID =
		"http://xml.org/sax/features/validation";

	/** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
	static final String SCHEMA_VALIDATION_FEATURE_ID =
		"http://apache.org/xml/features/validation/schema";

	/** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
	static final String SCHEMA_FULL_CHECKING_FEATURE_ID =
		"http://apache.org/xml/features/validation/schema-full-checking";

	/** Dynamic validation feature id (http://apache.org/xml/features/validation/dynamic). */
	static final String DYNAMIC_VALIDATION_FEATURE_ID =
		"http://apache.org/xml/features/validation/dynamic";
	static final String NO_NAMESPACE_SCHEMA_LOCATION_ID =
		"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";

	static final String DEFAULT_PARSER_NAME =
		"org.apache.xerces.parsers.SAXParser";
	
	/**
	 * Simple ThreadLocal-based XMLReader instances.
	 */
	private static final ThreadLocal<XMLReader> s_tl = new ThreadLocal<XMLReader>();

	/** This will create an XML read that will set the parser parameters that
	 * are necessary for parsing an email XML.  It will also set which schema
	 * to validate against.
	 */
	public static XMLReader createXmlReader() throws SAXException {
		final XMLReader xmlReader =
			XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);

		return xmlReader;
	}
	
	/**
	 * Returns a ThreadLocal-based XMLReader instance for simple parsing
	 * purposes.
	 * <p>
	 * Note that the returned instance has all states cleared when it is 
	 * returned from this method.  For example, the content handler, the error
	 * handler, the DTD handler, the entity resolver, and any properties or 
	 * features that may have been set will be cleared and restored to the 
	 * defaults.  Therefore, one must not call this method while the instance is
	 * still being used within the thread.  It will have a consequence of 
	 * clearing all states immediately, and you may run into unexpected errors.
	 * 
	 * @deprecated This method is deprecated in favor of the version that
	 * takes a ThreadLocal as an input argument. As this shares a global
	 * threadlocal for a given thread, this increases the risk of corrupting its
	 * states if calls to this method are nested.
	 */
	public static XMLReader getSimpleThreadLocalXmlReader() throws SAXException {
		return getSimpleThreadLocalXmlReader(s_tl);
	}
	
	/**
	 * Returns a ThreadLocal-based XMLReader instance for simple parsing
	 * purposes. It uses the threadlocal instance that is passed into the method
	 * as the storage. Thus, its storage and use are isolated from other uses of
	 * this method within the same thread.
	 * <p>
	 * For a given type of use, the recommended pattern is to use a static final
	 * threadlocal member variable. For example,
	 * <pre>
	 * public class MyClass {
	 *     private static final ThreadLocal&lt;XMLReader&gt; s_tl = new ThreadLocal&lt;XMLReader&gt;();
	 *     private final XMLReader m_reader;
	 * 
	 *     public MyClass() {
	 *         m_reader = XmlReaderHelper.getSimpleThreadLocalXmlReader(s_tl);
	 *     }
	 * }
	 * </pre>
	 * <p>
	 * Note that the returned instance has all states cleared when it is 
	 * returned from this method.  For example, the content handler, the error
	 * handler, the DTD handler, the entity resolver, and any properties or 
	 * features that may have been set will be cleared and restored to the 
	 * defaults.  Therefore, one must not call this method while the instance is
	 * still being used within the thread.  It will have a consequence of 
	 * clearing all states immediately, and you may run into unexpected errors. 
	 */
	public static XMLReader getSimpleThreadLocalXmlReader(ThreadLocal<XMLReader> tl) throws SAXException {
		ReaderDelegate reader = (ReaderDelegate)tl.get();
		if (reader == null) {
			reader = new ReaderDelegate(XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME));
			tl.set(reader);
		} else { // reused: clear the state before giving it
			reader.clearAll();
		}
		return reader;
	}

	/** This sets the XML parser parameters.
	 */	
	public static void setDefaultParams(
		final XMLReader xmlReader,
		final boolean validateXml)
			throws SAXException
	{

		/** Default namespaces support (true). */
		final boolean DEFAULT_NAMESPACES = true;

		/** Default namespace prefixes (false). */
		final boolean DEFAULT_NAMESPACE_PREFIXES = false;

		/** Default validation support (false). */
		final boolean DEFAULT_VALIDATION = true;

		/** Default Schema validation support (false). */
		final boolean DEFAULT_SCHEMA_VALIDATION = true;

		/** Default Schema full checking support (false). */
		final boolean DEFAULT_SCHEMA_FULL_CHECKING = true;

		/** Default dynamic validation support (false). */
		final boolean DEFAULT_DYNAMIC_VALIDATION = true;

		boolean namespaces = DEFAULT_NAMESPACES;
		boolean namespacePrefixes = DEFAULT_NAMESPACE_PREFIXES;
		boolean validation = validateXml?DEFAULT_VALIDATION:false;
		boolean schemaValidation = validateXml?DEFAULT_SCHEMA_VALIDATION:false;
		boolean schemaFullChecking = validateXml?DEFAULT_SCHEMA_FULL_CHECKING:false;
		boolean dynamicValidation = validateXml?DEFAULT_DYNAMIC_VALIDATION:false;

		xmlReader.setFeature(NAMESPACES_FEATURE_ID, namespaces);
		xmlReader.setFeature(NAMESPACE_PREFIXES_FEATURE_ID,	namespacePrefixes);

		xmlReader.setFeature(VALIDATION_FEATURE_ID, validation);
		xmlReader.setFeature(SCHEMA_VALIDATION_FEATURE_ID, schemaValidation);

		xmlReader.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID,
			schemaFullChecking);

		xmlReader.setFeature(DYNAMIC_VALIDATION_FEATURE_ID, dynamicValidation);
	}

	public static void setDefaultNamespaceSchemaLocation(
		final XMLReader xmlReader,
		final URL urlToSchema)
			throws SAXException
	{
		xmlReader.setProperty( NO_NAMESPACE_SCHEMA_LOCATION_ID,
			urlToSchema.toExternalForm() );
	}

	public static void parse(final XMLReader xmlReader, final URL url)
		throws IOException, SAXException
	{
		final InputStream inputStream = url.openStream();
		try {
			final InputSource inputSource = new InputSource(inputStream);
			xmlReader.parse(inputSource);
		} finally {
			inputStream.close();
		}

	}
	
	private static class ReaderDelegate implements XMLReader {
		private final XMLReader m_wrapped;
		
		// setter intercepts
		private boolean m_entityResolverSet;
		private boolean m_errorHandlerSet;
		private Map<String,Boolean> m_features;
		private Map<String,Object> m_properties;
		
		public ReaderDelegate(XMLReader wrapped) {
			m_wrapped = wrapped;
		}
		
		void clearAll() throws SAXException {
			m_wrapped.setContentHandler(null);
			m_wrapped.setDTDHandler(null);
			if (m_entityResolverSet) {
				m_wrapped.setEntityResolver(null);
				m_entityResolverSet = false;
			}
			if (m_errorHandlerSet) {
				m_wrapped.setErrorHandler(null);
				m_errorHandlerSet = false;
			}
			
			if (m_features != null) {
				for (Map.Entry<String,Boolean> entry : m_features.entrySet()) {
					m_wrapped.setFeature(entry.getKey(), entry.getValue());
				}
				m_features = null;
			}
			
			if (m_properties != null) {
				for (Map.Entry<String,Object> entry : m_properties.entrySet()) {
					m_wrapped.setProperty(entry.getKey(), entry.getValue());
				}
				m_properties = null;
			}
		}

		public void setFeature(String s, boolean flag)
				throws SAXNotRecognizedException, SAXNotSupportedException {
			// store the original feature before delegating
			if (m_features == null) {
				m_features = new HashMap<String,Boolean>();
			}
			if (!m_features.containsKey(s)) {
				m_features.put(s, m_wrapped.getFeature(s));
			}
			m_wrapped.setFeature(s, flag);
		}

		public void setProperty(String s, Object obj)
				throws SAXNotRecognizedException, SAXNotSupportedException {
			// store the original property (which may be null) before delegating
			if (m_properties == null) {
				m_properties = new HashMap<String,Object>();
			}
			if (!m_properties.containsKey(s)) {
				m_properties.put(s, m_wrapped.getProperty(s));
			}
			m_wrapped.setProperty(s, obj);
		}

		public ContentHandler getContentHandler() {
			return m_wrapped.getContentHandler();
		}

		public DTDHandler getDTDHandler() {
			return m_wrapped.getDTDHandler();
		}

		public EntityResolver getEntityResolver() {
			return m_wrapped.getEntityResolver();
		}

		public ErrorHandler getErrorHandler() {
			return m_wrapped.getErrorHandler();
		}

		public boolean getFeature(String s) throws SAXNotRecognizedException,
				SAXNotSupportedException {
			return m_wrapped.getFeature(s);
		}

		public Object getProperty(String s) throws SAXNotRecognizedException,
				SAXNotSupportedException {
			return m_wrapped.getProperty(s);
		}

		public void parse(InputSource inputsource) throws IOException,
				SAXException {
			m_wrapped.parse(inputsource);
		}

		public void parse(String s) throws IOException, SAXException {
			m_wrapped.parse(s);
		}

		public void setContentHandler(ContentHandler contenthandler) {
			m_wrapped.setContentHandler(contenthandler);
		}

		public void setDTDHandler(DTDHandler dtdhandler) {
			m_wrapped.setDTDHandler(dtdhandler);
		}

		public void setEntityResolver(EntityResolver entityresolver) {
			// record the fact the entity resolver was set
			m_entityResolverSet = true;
			m_wrapped.setEntityResolver(entityresolver);
		}

		public void setErrorHandler(ErrorHandler errorhandler) {
			// record the fact the error handler was set
			m_errorHandlerSet = true;
			m_wrapped.setErrorHandler(errorhandler);
		}
	}
}
