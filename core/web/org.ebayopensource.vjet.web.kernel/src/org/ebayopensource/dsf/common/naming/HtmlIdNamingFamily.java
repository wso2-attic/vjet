/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Checks whether the given html id is valid for use. The html id is invalid, if
 * it is:
 * <li><code>null</code> object
 * <li> empty string
 * <li> contains illegal character - '.',':','_','$','#' and '-'
 * 
 * @see IDsfNamingFamily 
 */
public class HtmlIdNamingFamily
	implements IDsfNamingFamily, Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_SCOPE_SEPARATOR = "-";
	public static final String DEFAULT_LOCAL_NAME_SEPARATOR = "-";
	
	private static Set<String> s_validSeparators = createValidSeparators();
	
	private final String m_scopeSeparator;
	private final String m_localNameSeparator;

	private static HtmlIdNamingFamily s_instance =
		new HtmlIdNamingFamily(
			DEFAULT_SCOPE_SEPARATOR, DEFAULT_LOCAL_NAME_SEPARATOR);

//	private static DsfScopedNameAssembler s_defaultAssembler=
//		new DefaultScopedNameAssembler(
//			DEFAULT_SCOPE_SEPARATOR, DEFAULT_LOCAL_NAME_SEPARATOR);

	private static Set<String> createValidSeparators() {
		final Set<String> set = new HashSet<String>();
		set.add(".");
		set.add(":");
		set.add("_");
		set.add("$");
		set.add("#");
		set.add("-");
		return set;
	}

	public static IDsfNamingFamily getInstance() {
		return s_instance;
	}

	/** Constructor for DefaultDsfNamingFamily.
	 * 
	 *allow subclasses.
	 *force getInstance() for instances of this class.
	 */
	protected HtmlIdNamingFamily(
		final String scopeSeparator,
		final String localNameSeparator)
	{

		if (!s_validSeparators.contains(scopeSeparator)) {
			final String m = "invalid scope separator '"+scopeSeparator+"'";
			throw new DsfInvalidNameException(m);
		}
		if (!s_validSeparators.contains(localNameSeparator)) {
			final String m = "invalid local name separator '" +
				localNameSeparator + "'";
			throw new DsfInvalidNameException(m);
		}
		m_scopeSeparator = scopeSeparator;
		m_localNameSeparator = localNameSeparator;			

	}
	public String compose(final ResolvedNamePath resolvedNamePath) {
		final StringBuilder buffer = new StringBuilder();
		final ParentScopes scopes = resolvedNamePath.getScopes();
		if (scopes != null) {
			final Iterator<String> iter = scopes.iterator();
			while (iter.hasNext()) {
				final String name = (String)iter.next();
				buffer.append(name);
				if (iter.hasNext()) {
					buffer.append(m_scopeSeparator);
				}
			}
		}
		final String localName = resolvedNamePath.getLocalName();
		if (localName != null) {
			buffer.append(m_localNameSeparator);
			buffer.append(localName);
		}
		if (buffer.length() == 0) {
			return null;
		}
		return buffer.toString();
	}
	
	public ResolvedNamePath decomposeName(final String fullyQualifiedName) {
		if (fullyQualifiedName == null || fullyQualifiedName.length() == 0) {
			return ResolvedNamePath.EMPTY;
		}
		final ParentScopes scopes = new ParentScopes();
		if (fullyQualifiedName.startsWith(m_localNameSeparator)) {
			final String localName =
				fullyQualifiedName.substring(m_localNameSeparator.length());
			return new ResolvedNamePath(scopes, localName);
		}
		int index = 0;
		if (fullyQualifiedName.startsWith(m_scopeSeparator)) {
			index++;
		}
		while (index < fullyQualifiedName.length()) {
			final int sepIndex =
				fullyQualifiedName.indexOf(m_scopeSeparator, index);
			if (sepIndex == -1) {
				break;					
			}
			if (index == sepIndex) {
				throw new DsfInvalidNameException("empty scope name");
			}
			final String scope = fullyQualifiedName.substring(index, sepIndex);
			scopes.appendScope(scope);
			index = sepIndex + m_scopeSeparator.length();					
		}
		
		final String localName;
		if (index >= fullyQualifiedName.length()) {
			localName = null;
		} else {
			final int sepIndex =
				fullyQualifiedName.indexOf(m_localNameSeparator, index);
			if (sepIndex == -1) {
				if (index == 0) {
					localName = fullyQualifiedName;
				} else {
					final String scope = fullyQualifiedName.substring(index); 
					scopes.appendScope(scope);
					localName = null;
				}
			} else {
				if (sepIndex == index) {
					// we don't want an empty string
					throw new DsfInvalidNameException("empty scope name");
				}
				final String scope=
					fullyQualifiedName.substring(index,sepIndex);
				scopes.appendScope(scope);
				localName = fullyQualifiedName.substring(
					sepIndex + m_localNameSeparator.length());				
			}
		}
		if (scopes.size() <= 0 && localName == null) {
			return ResolvedNamePath.EMPTY;
		}
		return new ResolvedNamePath(scopes,localName);
	}
	
	public NameStatusCheck verifyLocalName(final String localName) {
		return verifyName(localName);
	}
	
	public NameStatusCheck verifyScopeName(final String scopeName) {
		return verifyName(scopeName);
	}

	protected NameStatusCheck verifyName(final String name) {
		if (name == null) {
			return new NameStatusCheck(false,
				"name must not be null");
		}
		
		int size = name.length();
		if (size == 0) {
			return new NameStatusCheck(false,
				"name must not be empty");
		}
		
		for (int i = 0; i < size; i++) {
			if (isUnqualifiedChar(name.charAt(i))) {
				return new NameStatusCheck(false,
					"Illegal naming char: " + name.charAt(i) + " from name = " + name);
			}
		}
		return NameStatusCheck.OK ;		
	}
	
	private boolean isUnqualifiedChar(final char c) {
		switch (c) {
			case '.' :
			case ':' :
			case '_' :
			case '$' :
			case '#' :
			case '-' :
			case ' ' :
				return true;
		    default :
		    	return false;
		}
	}

}
