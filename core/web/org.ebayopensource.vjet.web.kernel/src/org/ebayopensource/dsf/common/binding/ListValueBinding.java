/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.binding;

import java.util.List;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public class ListValueBinding<T> 
	extends BaseValueBinding<T> implements IListValueBinding<T>
{
	private static final long serialVersionUID = 1L;
	private List<T> m_data ;
	private int m_index ;
	
	//
	// Constructor(s)
	//
	public ListValueBinding(
		final List<T> list, final int indexKey, final Class<T> valueType)
	{
		super(valueType) ;
		setList(list) ;
		setIndex(indexKey) ;
	}

	//
	// Satify IMapValueBinding
	//
	public void setList(final List<T> list) { 
		assertNotNull(list, "Target List must not be null") ;
		m_data = list ;
	}
	
	public List<T> getList() {
		return m_data ;
	}
	
	public void setIndex(final int index) {
		if (index < 0) {
			chuck("List index must be a positive value") ;
		}
		m_index = index ;
	}
	
	public int getIndex() {
		return m_index ;
	}
	
	//
	// Satisfy DsfBinding
	//
	public T getValue() {		
		assertState() ; // make sure target is set		
		return getList().get(getIndex()) ;
	}
	
	public void setValue(final T value) {	
		assertState() ; // make sure target is set		
		getList().set(getIndex(), value) ;
	}
	
	//
	// Framework
	//
	protected final void assertState() {
		if (m_data == null) {
			throw new DsfRuntimeException("List target must not be null") ;
		}
	}
	
	//
	// Overrides from Object
	//
	public String toString() {
		return "" + m_index + " on: " + m_data ; 
	}
}

