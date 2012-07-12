/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.config;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.filter.FilterId;
import org.ebayopensource.dsf.common.trace.filter.ITraceEventFilter;
import org.ebayopensource.dsf.common.Z;

public final class FilterKey {
	public static final String DELIMITER = ":";
	
	private Class<? extends ITraceEventFilter> m_type;
	private FilterId m_id;
	
	//
	// Constructor
	//
	public FilterKey(final Class<? extends ITraceEventFilter> type, final String id){
		this(type, new FilterId(id));
	}
	
	public FilterKey(final Class<? extends ITraceEventFilter> type, final FilterId id){
		if (type == null){
			DsfExceptionHelper.chuck("type is null");
		}
		if (type.isInterface()){
			DsfExceptionHelper.chuck("type is an interface");
		}
		if (id == null){
			DsfExceptionHelper.chuck("id is null");
		}
		m_type = type;
		m_id = id;
	}
	
	//
	// API
	//
	public Class<? extends ITraceEventFilter> getType(){
		return m_type;
	}
	
	public FilterId getId(){
		return m_id;
	}
	
	/**
	 * Parse a string of format "FilterType:FilterName" and return a FilterKey
	 * @param value String
	 * @return FilterKey
	 */
	@SuppressWarnings("unchecked")
	public static FilterKey parse(final String value){
		
		if (value == null || value.trim().length() == 0){
			return null;
		}
		
		int index = value.indexOf(DELIMITER);
		if (index < 0){
			return null;
		}
		
		String typeName = value.substring(0, index).trim();
		String id = value.substring(index+1).trim();
		if (typeName.length() == 0 || id.length() == 0){
			return null;
		}
		
		
		Class<? extends ITraceEventFilter> filterType;
		try {
			filterType = (Class<? extends ITraceEventFilter>)Class.forName(typeName);
		} 
		catch (ClassNotFoundException e) {
			return null;
		} 
		
		if (filterType == ITraceEventFilter.class){
			return null;
		}
		
		return new FilterKey(filterType, id);
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != FilterKey.class){
			return false;
		}
		
		FilterKey that = (FilterKey)obj;
		return m_type == that.m_type && m_id.getName().equals(that.m_id.getName());
	}
	
	@Override
	public int hashCode(){
		return m_type.hashCode() + m_id.getName().hashCode();
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("type", m_type);
		z.format("id", m_id.getName());
		
		return z.toString();
	}
}
