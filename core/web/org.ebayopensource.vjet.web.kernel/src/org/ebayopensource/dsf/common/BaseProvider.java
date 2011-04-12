/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;

public class BaseProvider<TYPE,ID> implements IProvider<TYPE,ID>{

	private Map<ID,TYPE> m_instances = new LinkedHashMap<ID,TYPE>();
	
	//
	// Satisfy IProvider
	//
	public TYPE get(final ID id){
		return m_instances.get(id);
	}
	
	public Map<ID,TYPE> getAll(){
		return Collections.unmodifiableMap(m_instances);
	}
	
	public boolean remove(final ID id) {
		return m_instances.remove(id) != null ;
	}
	
	public void clear(){
		m_instances.clear();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Ids: ");
		for (ID id: m_instances.keySet()){
			sb.append(id).append(", ");
		}
		
		return sb.toString();
	}
	
	//
	// Protected
	//
	protected BaseProvider<TYPE,ID> add(final ID id, final TYPE obj){
		if (id == null){
			DsfExceptionHelper.chuck("id is null");
		}
		
		if (obj == null){
			DsfExceptionHelper.chuck("obj is null");
		}
		
		if (m_instances.containsKey(id)){
			DsfExceptionHelper.chuck(
				"there is already an object registered with this id:" + id);
		}
		
		m_instances.put(id, obj);
		return this;
	}
}
