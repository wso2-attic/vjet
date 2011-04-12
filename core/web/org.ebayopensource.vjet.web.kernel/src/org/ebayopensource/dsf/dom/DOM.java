/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.ebayopensource.dsf.dom.support.DNamespace;

public class DOM extends DElementConstructor {	
	public static void main(String[] args) {
		DOM dom = new DOM() ;
		Object txt = dom.domCreateDynamicElement("text") ;
		DText text = (DText)txt ;
		System.out.println(text) ;
	}
	/**
	 * Holds names and classes of BML element types. When an element with a
	 * particular tag name is created, the matching {@link java.lang.Class}
	 * is used to create the element object. For example, &lt;<TEASER>&gt; matches
	 * BTeaser. This static table is shared across  BSF documents.
	 */
	private static HashMap<String,Constructor<?> > s_elementTypes;

	/**
	 * Signature used to locate constructor of HTML element classes. This
	 * static array is shared across all HTML documents.
	 *
	 * @see #createElement
	 */
	private static final Class<?>[] s_elemClassSig = new Class[] {};
	private static final Object[] EMPTY_ARG = new Object[] {};

	static {
		populateElementTypes();
	}
	
	//
	// API
	//
	@Override
	public DElement domCreateDynamicElement(
		final String namespaceUri, final String possibleQualifiedName)
	{
		final DNamespace ns = DomUtil.getNamespace(namespaceUri, possibleQualifiedName);
		final String localName = DomUtil.getUnqualifedName(possibleQualifiedName) ;
		return domCreateDynamicElement(ns, localName) ;
	}
	/**
	 * Answers the correct BML Element for the tagName.  The tagName is put
	 * in connonical form internally so case does not matter.  If no tag name
	 * match, a DElement instance with the tag name is returned.
	 * @return Specific DXyz type for the tag.  If not an HTML tag, then returns
	 * a DElement of that tag name
	 * @param inTagName Will throw IllegalStateException if null or empty String
	 */
	@Override
	public DElement domCreateDynamicElement(final String unqualifiedName) {
		// XML "IS" case-sensitive so leave name as is
		final String tagName = unqualifiedName ; //.toLowerCase(Locale.ENGLISH);
		final Constructor<?> cnst = s_elementTypes.get(tagName);
		if (cnst != null) {
			// Get the constructor for the element. The signature specifies an
			// owner document and a tag name. Use the constructor to instantiate
			// a new object and return it.
			try {
				return (DElement) cnst.newInstance(EMPTY_ARG);
			} 
			catch (Exception except) {
				final Throwable thrw;

				if (except instanceof InvocationTargetException) {
					thrw = ((InvocationTargetException)except).getTargetException();
				} 
				else {
					thrw = except;
				}

				throw new IllegalStateException(
					"HTM15 Tag '"
					+ tagName
					+ "' associated with an Element class that failed to construct.\n"
					+ tagName
					+ " with following message: "
					+ thrw.getMessage(),
					except);
			}
		}
		return new DElement(tagName);
	}
	
	@Override
	public DElement domCreateDynamicElement(
		final DNamespace namespace, final String nonQualifiedTagName)
	{
		DElement elem = domCreateDynamicElement(nonQualifiedTagName) ;
		elem.setDsfNamespace(namespace) ;
		return elem ;
	}
	
	@Override
	public DElement domCreateDynamicElement(
		final DDocument owner, final String inTagName)
	{
		DElement e = domCreateDynamicElement(inTagName);
		setOwnerDocument(owner, e);
		return e;
	}
	
	@Override
	public DElement domCreateDynamicElement(
		final DDocument owner, final DNamespace namespace, final String inTagName)
	{
		DElement e = domCreateDynamicElement(namespace, inTagName);
		setOwnerDocument(owner, e);
		return e;
	}

	//
	// Private
	//
	/**
	 * Called by the constructor to populate the element types list (see {@link
	 * #_elementTypesHTML}). Will be called multiple times but populate the list
	 * only the first time. Replacement for static constructor.
	 */
	private synchronized static void populateElementTypes() {
		// This class looks like it is due to some strange
		// (read: inconsistent) JVM bugs.
		// Initially all this code was placed in the static constructor,
		// but that caused some early JVMs (1.1) to go mad, and if a
		// class could not be found (as happened during development),
		// the JVM would die.
		// Bertrand Delacretaz <bdelacretaz@worldcom.ch> pointed out
		// several configurations where HTMLAnchorElementImpl.class
		// failed, forcing me to revert back to Class.forName().

		if (s_elementTypes != null) {
			return;
		}
		s_elementTypes = new HashMap<String,Constructor<?> >(101);
		for (final DOMTypeEnum type:DOMTypeEnum.valueIterable()){
			populateElementType(type);
		}
	}

	private static void populateElementType(final DOMTypeEnum type) {
		// Need to do lowercase since BML has some all uppercase and lowercase tags
		// so we do lowercase as the connonical representation
		final String tagName = type.getName().toLowerCase();
		final Class<?> elemClz = type.getTypeClass();
		try {
			Constructor<?> c = elemClz.getConstructor(s_elemClassSig) ;
			s_elementTypes.put(tagName, c);
		}
		catch (Exception except) {
			throw new RuntimeException(
				"HTM019 OpenXML Error: Could not find proper constructor for "
					+ elemClz.getName()
					+ " for "
					+ tagName,
				except);
		}
	}
}

