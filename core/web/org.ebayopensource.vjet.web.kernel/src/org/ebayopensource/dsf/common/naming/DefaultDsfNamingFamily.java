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

import org.ebayopensource.dsf.common.Z;
/**
 * Checks whether the given name is valid for use. This is the default verifier
 * which implements <code>IDsfNamingFamily</code>. The html id is invalid, if
 * it is:
 * <li><code>null</code> object
 * <li> empty string
 * <li> contains illegal character - '.',':','_','$', and '#'
 * 
 * @see IDsfNamingFamily 
 */
public class DefaultDsfNamingFamily implements IDsfNamingFamily, Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_SCOPE_SEPARATOR = ":";
	public static final String DEFAULT_LOCAL_NAME_SEPARATOR = "#";
	
	private static Set<String> s_validSeparators = createValidSeparators();
	
	private final String m_scopeSeparator;
	private final String m_localNameSeparator;

	private static DefaultDsfNamingFamily s_instance =
		new DefaultDsfNamingFamily(
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
	protected DefaultDsfNamingFamily(
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
					"Illegal naming char: " + name.charAt(i) + "from name = " + name);
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
			case ' ' :
				return true;
		    default :
		    	return false;
		}
	}

//	public DsfScopedNameAssembler getAssembler() {
//		return s_defaultAssembler;
//	}
//	public DsfScopedNameAssembler getAssembler(
//		String scopeSeparator,
//		String localNameSeparator)
//	{
//		if (scopeSeparator == null) {
//			scopeSeparator = DEFAULT_SCOPE_SEPARATOR;
//		}
//		if (localNameSeparator == null) {
//			localNameSeparator = DEFAULT_LOCAL_NAME_SEPARATOR;
//		}
//		if (DEFAULT_SCOPE_SEPARATOR.equals(scopeSeparator) &&
//			DEFAULT_LOCAL_NAME_SEPARATOR.equals(localNameSeparator))
//		{
//			return getAssembler();
//		}
//		return new DefaultScopedNameAssembler(
//			scopeSeparator, localNameSeparator);
//	}
//
//	public DsfNamingVerifier getVerifier() {
//		return DefaultNamingVerifier.getInstance();
//	}
	
//	public boolean isValidSeparator(String separator) {
//		return s_validSeparators.contains(separator);
//	}
	
//	public static class DefaultScopedNameAssembler 
//		implements DsfScopedNameAssembler
//	{
//		private final String m_scopeSeparator;
//		private final String m_localNameSeparator;
//
//		public DefaultScopedNameAssembler(
//			final String scopeSeparator,
//			final String localNameSeparator)
//		{
//			if (!s_validSeparators.contains(scopeSeparator)) {
//				final String m = "invalid scope separator '"+scopeSeparator+"'";
//				throw new DsfInvalidNameException(m);
//			}
//			if (!s_validSeparators.contains(localNameSeparator)) {
//				final String m = "invalid local name separator '" +
//					localNameSeparator + "'";
//				throw new DsfInvalidNameException(m);
//			}
//			m_scopeSeparator = scopeSeparator;
//			m_localNameSeparator = localNameSeparator;			
//		}
//
//		public String getScopePath(final ParentScopes scopes) {
//			final RopeBuffer rb = new RopeBuffer();
//			final Iterator itr = scopes.getAncestorScopes();
//			while (itr.hasNext()) {
//				final String scope = (String)itr.next();
//				rb.append(scope);
//				if (itr.hasNext()) {
//					rb.append(m_scopeSeparator);	
//				}
//			}
//			return rb.toString();
//		}
//
//		public ParentScopes getParentScopes(final String scopePath) {			
//			ParentScopes scopes = new ParentScopes();
//			if (scopePath == null || scopePath.length() == 0) {
//				return scopes;
//			}
//			int startIndex = 0;
//			while (startIndex < scopePath.length()) {
//				int endIndex = scopePath.indexOf(m_scopeSeparator, startIndex);
//				if (endIndex == -1)	{
//					endIndex = scopePath.length();						
//				}
//				scopes.appendScope(scopePath.substring(startIndex, endIndex));
//				startIndex = endIndex + m_scopeSeparator.length();					
//			}
//			return scopes;
//		}
//
//		public String getFullName(final DsfName dsfName) {
//			final String localName = dsfName.getLocalName();
//			final String scopeName = dsfName.getScopeName();
//			if (localName == null && scopeName == null) {
//				return null;
//			}
//			final ParentScopes scopes = dsfName.getParentScopes();
//			if (!scopes.hasScopedParent() && scopeName == null) {
//				return null;
//			}
//			if (scopes.size() > 0) {
//				final String scopePath = getScopePath(scopes);				
//				if (scopes.hasScopedParent() && localName != null) {
//					return scopePath + m_localNameSeparator + localName;			
//				}
//				return scopePath + m_scopeSeparator + scopeName;
//			}
//			return scopeName;
//		}
//
//		public String getScopeSeparator() {
//			return m_scopeSeparator;
//		}
//		
//		public String getLocalNameSeparator() {
//			return m_localNameSeparator;
//		}		
//	}
	
//	public static class DefaultNamingVerifier implements DsfNamingVerifier {
//		
//		private static DsfNamingVerifier s_instance = 
//			new DefaultNamingVerifier();
//		
//		public static DsfNamingVerifier getInstance() {
//			return s_instance;
//		}
//		protected DefaultNamingVerifier() {
//			// allow subclasses.
//			// force getInstance() for instances of this class.
//		}
//		public DsfNamingVerifier.Status verifyFacetName(final String name) {
//			return verifyDsfName(name);
//		}
//		
//		public DsfNamingVerifier.Status verifyAttributeName(final String name) {
//			return verifyDsfName(name);
//		}
//
//		/** This should verify the local name.
//		 */
//		public DsfNamingVerifier.Status verifyDsfName(final String name) {
//			
//			if (name == null || name.length() == 0) {
//				return createFailedNamingVerifierStatus(
//					"name must not be empty");
//			}
//			
//			for (int i = 0; i < name.length(); i++) {
//				if (s_unqualifiedChars.contains(
//					String.valueOf(name.charAt(i)))) {
//					return createFailedNamingVerifierStatus(
//						"Illegal naming char: " + name.charAt(i));
//				}
//			}
//			
//			return DsfNamingVerifier.STATUS_OK ;
//		}
//
//		//
//		// Private
//		//
//		private DsfNamingVerifier.Status createFailedNamingVerifierStatus(
//			final String message)
//		{
//			return new DsfNamingVerifier.Status() {
//				public boolean isOK() {
//					return false ;
//				}
//				
//				public String getErrorMessage() {
//					return message ;
//				}
//			} ;
//		}
//	}
	
	public String toString() {
		Z z = new Z() ;
		z.format("DEFAULT_SCOPE_SEPARATOR", DEFAULT_SCOPE_SEPARATOR) ;
		z.format("DEFAULT_LOCAL_NAME_SEPARATOR", DEFAULT_LOCAL_NAME_SEPARATOR) ;
		z.format("valid separators", s_validSeparators) ;
		z.format("scope separator", m_scopeSeparator) ;
		z.format("local name separator",  m_localNameSeparator) ;
		return z.toString() ;
	}
}
