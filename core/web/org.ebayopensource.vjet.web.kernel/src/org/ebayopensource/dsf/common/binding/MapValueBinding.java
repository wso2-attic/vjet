/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.binding;

import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public class MapValueBinding<K, T> 
	extends BaseValueBinding<T> implements IMapValueBinding<K,T>
{
	private static final long serialVersionUID = 1L;
	private Map<K, T> m_data ;
	private K m_key ;
	
	//
	// Constructor(s)
	//
	public MapValueBinding(
		final Map<K, T> map, final K key, final Class<T> valueType)
	{
		super(valueType) ;
		setMap(map) ;
		setKey(key) ;	
	}

	//
	// Satify IMapValueBinding
	//	
	public void setMap(final Map<K, T> map) { 
		assertNotNull(map, "Binding Map target must not be null") ;
		m_data = map ;
	}
	public Map<K, T> getMap() {
		return m_data ;
	}
	
	public void setKey(final K key) {
		// Maps can support a null key so we allow it
		m_key = key ;
	}
	public K getKey() {
		return m_key ;
	}

	//
	// Satisfy DsfBinding
	//
	public T getValue() {
		assertState() ; // make sure we have non-nulls for map and key		
		return getMap().get(getKey()) ;
	}
	
	public void setValue(final T value) {		
		assertState() ; // make sure we have non-nulls for map and key
		getMap().put(getKey(), value) ;
	}
	
	//
	// Framework
	//
	protected final void assertState() {
		if (m_data == null) {
			throw new DsfRuntimeException("Map target must not be null") ;
		}
	}
	
	//
	// Overrides from Object
	//
	public String toString() {
		return m_key + " on: " + m_data ;
	}
}
