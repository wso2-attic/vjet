/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.filter;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.config.FilterKey;
import org.ebayopensource.dsf.common.trace.event.TraceEvent;

public class TraceTypeFilter implements ITraceEventFilter {
	
	private FilterKey m_filterKey;
	private TraceTypeMapping m_typeMapping = TraceTypeMapping.DEFAULT;
	
//	private static final TraceConfigMgr s_configBean = 
//		TraceConfigMgr.getInstance();
	
	//
	// Constructor
	//
	public TraceTypeFilter(FilterId id) {
		
		if (id == null){
			DsfExceptionHelper.chuck("id is null");
		}
		
		m_filterKey = new FilterKey(this.getClass(), id);
	}
	
	//
	// Satisfy ITraceFilter
	//
	public FilterId getId(){
		return m_filterKey.getId();
	}
	
	public boolean isInterested(TraceEvent event){
		return true;
	}
	
	//
	// API
	//
	public TraceTypeMapping getTypeMapping(){
		return m_typeMapping;
	}
}
