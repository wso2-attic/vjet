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
import org.ebayopensource.dsf.common.trace.handler.HandlerId;
import org.ebayopensource.dsf.common.trace.handler.ITraceEventHandler;
import org.ebayopensource.dsf.common.Z;

public final class HandlerKey {

	public static final String DELIMITER = ":";
	
	private Class<? extends ITraceEventHandler> m_type;
	private HandlerId m_id;
	
	//
	// Constructor
	//
	public HandlerKey(final Class<? extends ITraceEventHandler> type, final String id){
		this(type, new HandlerId(id));
	}
	
	public HandlerKey(final Class<? extends ITraceEventHandler> type, final HandlerId id){
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
	public Class<? extends ITraceEventHandler> getType(){
		return m_type;
	}
	
	public HandlerId getId(){
		return m_id;
	}
	
	/**
	 * Parse a string of format "HandlerType:HandlerName" and return a HandlerKey
	 * @param value String
	 * @return HandlerKey
	 */
	@SuppressWarnings("unchecked")
	public static HandlerKey parse(final String value){
		
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
		
		Class<? extends ITraceEventHandler> handlerType;
		try {
			handlerType = (Class<? extends ITraceEventHandler>)Class.forName(typeName);
		} 
		catch (ClassNotFoundException e) {
			return null;
		} 
		
		if (handlerType == ITraceEventHandler.class){
			return null;
		}
		
		return new HandlerKey(handlerType, id);
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != HandlerKey.class){
			return false;
		}
		
		HandlerKey that = (HandlerKey)obj;
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
