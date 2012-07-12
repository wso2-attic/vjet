/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.DsfVerifierConfig;

public final class DNamespace implements Serializable {
	/**
	 * W3C, namespace NCNameChar
	 */
	public static final String NS_NAME_CHAR = ":";

	private static final long serialVersionUID = 1L;

	private static Map<String, DNamespace> s_nsKeyToNamespaces;

	// private static Map<String, DNamespace> s_prefixToNamespaces;
	/**
	 * W3C, xmlns by definition bound to the namespace name
	 * http://www.w3.org/2000/xmlns/
	 */
	public final static String XMLNS_URI = "http://www.w3.org/2000/xmlns/";

	/**
	 * W3C, Xml is by definition bound to the namespace name
	 * http://www.w3.org/XML/1998/namespace.
	 */
	public final static String XML_URI = "http://www.w3.org/XML/1998/namespace";

	/**
	 * W3C, xml MAY, but need not, be declared, and MUST NOT be bound to any
	 * other namespace name. Other prefixes MUST NOT be bound to its namespace
	 * name, and it MUST NOT be declared as the default namespace
	 */
	public final static String XML_PREFIX = "xml";

	/**
	 * W3C, namespace DefaultAttName, is used only to declare namespace
	 * bindings. It MUST NOT be declared . Other prefixes MUST NOT be bound to
	 * this namespace name, and it MUST NOT be declared as the default
	 * namespace. Element names MUST NOT have the prefix xmlns.
	 */
	public final static String XMLNS_PREFIX = "xmlns";

	public static final DNamespace NO_NAMESPACE = new DNamespace("", "");

	public static final DNamespace XML_NAMESPACE = new DNamespace(XML_PREFIX,
			XML_URI);

	final private String m_prefix;

	final private String m_uri;

	private int hashCode = 0;

	// ??? How do we "share" this with all the various domains ???
	static {
		s_nsKeyToNamespaces = new HashMap<String, DNamespace>();
		s_nsKeyToNamespaces.put("&", NO_NAMESPACE);
		s_nsKeyToNamespaces.put("xml&http://www.w3.org/XML/1998/namespace",
				XML_NAMESPACE);
		// s_prefixToNamespaces = new HashMap<String, DNamespace>();
		// s_prefixToNamespaces.put(XML_NAMESPACE.getPrefix(), XML_NAMESPACE);
	}

	//
	// Constructor(s)
	//
	private DNamespace(final String prefix, final String uri) {
		m_prefix = prefix;
		m_uri = uri;
	}

	//
	// Lookup
	//
	/**
	 * Answer a Namespace for the empty String prefix with the specified URI. If
	 * such a Namespace did not exist, create it, register it and return it.
	 * 
	 * The URI should not be null or empty String
	 */
	public static DNamespace getNamespace(final String namespaceKey) {
		return getNamespace(null, namespaceKey);
	}

	/**
	 * Answer a Namespace for the specified prefix with the specified URI. If
	 * such a Namespace did not exist, create it, register it and return it.
	 * 
	 * The prefix and URI should not be null or empty String
	 */
	public static DNamespace getNamespace(
		final String prefix, final String namespaceKey)
	{
		checkNamespace(namespaceKey, prefix);
		final String nsKey = namespaceKeyFor(prefix, namespaceKey);
		DNamespace namespace = s_nsKeyToNamespaces.get(nsKey);
		if (namespace != null) {
			return namespace;
		}
		namespace = new DNamespace(prefix, namespaceKey);
		s_nsKeyToNamespaces.put(nsKey, namespace);
		// s_prefixToNamespaces.put(prefix, namespace);
		return namespace;
	}

	//
	// API
	//
	/**
	 * Answers the prefix for this Namespace. The prefix may be the empty
	 * String.
	 */
	public String getPrefix() {
		return m_prefix;
	}

	/**
	 * Ansswers the URI for this Namespace. The URI is never null or empty
	 * String.
	 */
	public String getNamespaceKey() {
		return m_uri;
	}

	//
	// Override(s) from Object
	//

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("[Namespace: prefix: \"");
		buf.append(m_prefix);
		buf.append("\" uri: \"");
		buf.append(m_uri);
		buf.append("\"]");
		return buf.toString();
	}

	/**
	 * As DNamespace instances are cached, equality is is determined by
	 * comparing object references
	 */
	@Override
	public boolean equals(final Object obj) {
		return this == obj;
	}

	//
	// Private
	//
	private static String namespaceKeyFor(final String prefix, final String uri){
		StringBuilder buf = new StringBuilder();
		if (prefix != null) {
			buf.append(prefix);
		}
		buf.append('&');
		buf.append(uri);
		return buf.toString();
	}

	/**
	 * Verify if a given namespaceURI and prefix are valid namespace.
	 * 
	 * @param namespaceKey
	 * @param prefix
	 */
	public static void checkNamespace(String namespaceKey, String prefix) {
		String reformated_nsUri = namespaceKey;
		if (namespaceKey != null) {
			reformated_nsUri = namespaceKey.trim().length() == 0 ? null
					: namespaceKey;

		}
		NSNameVerifier.verifyNSUriName(reformated_nsUri);
		if (DsfVerifierConfig.getInstance().isVerifyNaming()) {
			if (prefix == null) {
				// done
				return;
			}
			NSNameVerifier.verifyNSPrefix(prefix, reformated_nsUri);
		}
	}

	/**
	 * Compute hashCode. As DNamespace is a immutable class the hasCode is
	 * cached and calculated once.
	 * 
	 * @return the Objects hashcode.
	 */
	@Override
	public int hashCode() {
		if (hashCode == 0) {
			int hc = 1;
			hc = 31 * hc + (m_prefix == null ? 0 : m_prefix.hashCode());
			hc = 31 * hc + (m_uri == null ? 0 : m_uri.hashCode());
			hashCode = hc;
		}
		return hashCode;
	}
}
