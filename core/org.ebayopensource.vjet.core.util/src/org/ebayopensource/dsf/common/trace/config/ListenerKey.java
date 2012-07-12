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
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;
import org.ebayopensource.dsf.common.trace.listener.ListenerId;
import org.ebayopensource.dsf.common.Z;

public final class ListenerKey {
	
	public static final String DELIMITER = ":";
	
	private Class<? extends ITraceEventListener> m_type;
	private ListenerId m_id;
	
	//
	// Constructor
	//
	public ListenerKey(final Class<? extends ITraceEventListener> type, final String id){
		this(type, new ListenerId(id));
	}
	
	public ListenerKey(final Class<? extends ITraceEventListener> type, final ListenerId id){
		if (type == null){
			DsfExceptionHelper.chuck("type is null");
		}
		if (type == ITraceEventListener.class){
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
	public Class<? extends ITraceEventListener> getType(){
		return m_type;
	}
	
	public ListenerId getId(){
		return m_id;
	}
	
	@SuppressWarnings("unchecked")
	public static ListenerKey parse(final String value){
		
		if (value == null || value.length() == 0){
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
		
		Class<? extends ITraceEventListener> listenerType;
		try {
			listenerType = (Class<? extends ITraceEventListener>)Class.forName(typeName);
		} 
		catch (ClassNotFoundException e) {
			return null;
		} 
		
		if (listenerType == ITraceEventListener.class){
			return null;
		}
		
		return new ListenerKey(listenerType, id);
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != ListenerKey.class){
			return false;
		}
		
		ListenerKey that = (ListenerKey)obj;
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
