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

/**
 * Configuration for tracers. Thread-safe after constructed.
 */
public final class TracerConfig {
	
	private String m_scope;
	private List<FilterKey> m_filters =
		new ArrayList<FilterKey>(1);			
	private List<ListenerKey> m_listeners = 	
		new ArrayList<ListenerKey>(1);	
	
	//
	// Constructor
	//
	public TracerConfig(final String scope){
		if (scope == null){
			DsfExceptionHelper.chuck("scope is null");
		}
		m_scope = scope;
	}
	
	//
	// API
	//
	public String getScope(){
		return m_scope;
	}
	
	public synchronized void addFilter(final FilterKey filterKey){
		if (filterKey == null || m_filters.contains(filterKey)){
			return;
		}
		m_filters.add(filterKey);
	}
	
	public synchronized boolean removeFilter(final FilterKey filterKey){
		return m_filters.remove(filterKey);
	}
	
	public synchronized void removeAllFilters(){
		m_filters.clear();
	}
	
	public synchronized List<FilterKey> getFilters(){
		return Collections.unmodifiableList(m_filters);
	}
	
	public synchronized void addListener(final ListenerKey listener){
		if (listener == null || m_listeners.contains(listener)){
			return;
		}
		m_listeners.add(listener);
	}
	
	public synchronized boolean removeListener(final ListenerKey listener){
		return m_listeners.remove(listener);
	}
	
	public synchronized void removeAllListeners(){
		m_listeners.clear();
	}
	
	public synchronized List<ListenerKey> getListeners(){
		return Collections.unmodifiableList(m_listeners);
	}
}
