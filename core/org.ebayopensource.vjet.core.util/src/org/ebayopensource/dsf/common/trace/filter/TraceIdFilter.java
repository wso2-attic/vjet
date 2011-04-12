/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.filter;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.config.FilterKey;
import org.ebayopensource.dsf.common.trace.event.TraceEvent;
import org.ebayopensource.dsf.common.trace.event.TraceId;

public class TraceIdFilter implements ITraceEventFilter {
	
	private FilterKey m_filterKey;
	private Mode m_mode = Mode.ALL;
	private List<TraceId> m_ids = new ArrayList<TraceId>(2);
	
//	private static final TraceConfigMgr s_configBean = 
//		TraceConfigMgr.getInstance();
	
	//
	// Constructor
	//
	public TraceIdFilter(FilterId id) {
		
		if (id == null){
			DsfExceptionHelper.chuck("id is null");
		}
		
		m_filterKey = new FilterKey(this.getClass(), id);
	}
	
	public TraceIdFilter(FilterId id, Mode mode) {
		
		this(id);
		if (mode != null){
			m_mode = mode;
		}
	}
	
	//
	// Satisfy ITraceFilter
	//
	public FilterId getId(){
		return m_filterKey.getId();
	}
	
	public boolean isInterested(TraceEvent event){
		if (event == null){
			return false;
		}
		
		if (m_mode == Mode.ALL){
			return true;
		}
		else if (m_mode == Mode.INCLUDE){
			return m_ids.contains(event.getId());
		}
		else {
			return !m_ids.contains(event.getId());
		}
	}
	
	//
	// API
	//
	public Mode getMode(){
		return m_mode;
	}
	
	//
	// Inner
	//
	public static enum Mode {
		ALL,
		INCLUDE,
		EXCLUDE,
	}
}
