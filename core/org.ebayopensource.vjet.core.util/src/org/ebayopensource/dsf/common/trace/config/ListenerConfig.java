/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;

public final class ListenerConfig {

	private ListenerKey m_listenerKey;
	private List<FilterKey> m_filterKeys = new ArrayList<FilterKey>(1);						
	private List<HandlerKey> m_handlerKeys = new ArrayList<HandlerKey>(1);				
	
	//
	// Constructor
	//
	public ListenerConfig(final ListenerKey listenerKey){
		if (listenerKey == null){
			DsfExceptionHelper.chuck("listenerKey is null");
		}
		m_listenerKey = listenerKey;
	}
	
	//
	// API
	//
	public ListenerKey getListenerKey(){
		return m_listenerKey;
	}
	
	public synchronized void addFilter(final FilterKey filterKey){
		if (filterKey == null || m_filterKeys.contains(filterKey)){
			return;
		}
		m_filterKeys.add(filterKey);
	}
	
	public synchronized boolean removeFilter(final FilterKey filterKey){
		return m_filterKeys.remove(filterKey);
	}
	
	public synchronized void removeAllFilters(){
		m_filterKeys.clear();
	}
	
	public synchronized List<FilterKey> getFilters(){
		return Collections.unmodifiableList(m_filterKeys);
	}
	
	public synchronized void addHandler(final HandlerKey handlerKey){
		if (handlerKey == null || m_handlerKeys.contains(handlerKey)){
			return;
		}
		m_handlerKeys.add(handlerKey);
	}
	
	public synchronized boolean removeHandler(final HandlerKey handlerKey){
		return m_handlerKeys.remove(handlerKey);
	}
	
	public synchronized void removeAllHandlers(){
		m_handlerKeys.clear();
	}
	
	public synchronized List<HandlerKey> getHandlers(){
		return Collections.unmodifiableList(m_handlerKeys);
	}
}
