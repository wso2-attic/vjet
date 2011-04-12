/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * Provides the abstraction of an ordered collection of DOM implementations, 
 * without defining or constraining how this collection is implemented. The items 
 * in the DOMImplementationList are accessible via an integral index, starting 
 * from 0.
 */
public class DDOMImplementationList 
	implements DOMImplementationList, Iterable<DOMImplementation>
{
	private List<DOMImplementation> m_impls = new ArrayList<DOMImplementation>();
	
	//
	// Constructor(s)
	//
	/**
	 * Add the specified varags set of impls to this list.  An exception is
	 * thrown for any null args.
	 */
	public DDOMImplementationList(DOMImplementation... impls) {
		for(DOMImplementation impl: impls) {
			add(impl) ;
		}
	}
	
	/**
	 * Add the specified set of impls from the passed in collection to this
	 * list.  The collection should not contain any nulls and if so an exception
	 * will be thrown.
	 */
	public DDOMImplementationList(final Collection<DOMImplementation> impls) {
		for(DOMImplementation impl: impls) {
			add(impl) ;
		}		
	}
	
	//
	// Satisfy DOMImplementationList
	//
	/**
	 * Answer the number of implementations in this list.
	 */
	public int getLength() {
		return m_impls.size();
	}

	/**
	 * Answer the implementation at the specified index.  The list is 0-based.
	 * If the index would be out of bounds based on the current length, a null
	 * is returned.
	 */
	public DOMImplementation item(int index) {
		try {
			return m_impls.get(index) ;
		}
		catch(IndexOutOfBoundsException e) {
			return null ;
		}
	}

	//
	// Satisfy Iterable
	//
	public Iterator<DOMImplementation> iterator() {
		return m_impls.listIterator() ;
	}
	
	//
	// API
	//
	public void add(final DOMImplementation impl) {
		if (impl == null) {
			chuck("Cannot add a null implementation") ;
		}
		m_impls.add(impl) ;
	}
	
	public void add(final DOMImplementationList impls) {
		final int len = impls.getLength() ;
		for(int i = 0; i < len; i++) {
			add(impls.item(i)) ;
		}
	}
	
	//
	// Private
	//
	private void chuck(final String msg) {
		throw new DsfRuntimeException(msg) ;
	}
}
