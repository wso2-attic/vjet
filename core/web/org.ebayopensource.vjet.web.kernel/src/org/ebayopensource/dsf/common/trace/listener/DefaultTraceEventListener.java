/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.common.trace.config.FilterKey;
import org.ebayopensource.dsf.common.trace.config.HandlerKey;
import org.ebayopensource.dsf.common.trace.config.ListenerConfig;
import org.ebayopensource.dsf.common.trace.config.ListenerKey;
import org.ebayopensource.dsf.common.trace.config.TraceConfigMgr;
import org.ebayopensource.dsf.common.trace.event.TraceEvent;
import org.ebayopensource.dsf.common.trace.filter.ITraceEventFilter;
import org.ebayopensource.dsf.common.trace.handler.ITraceEventHandler;
import org.ebayopensource.dsf.common.tracer.Factory;

/**
 * DefaultTraceEventListener is default listener provided by infrastructure.
 * Filters and handlers can be added via config or directly added to each instance.
 * 
 * Please be aware that:
 * <li>Configured filters and handlers will be added during construction time and
 * same applies to all instances. 
 * <li>If there is no handler configured, default shared handler from TraceManager will be added.
 * <li>Filters and handlers can be added to individual instances after construction.
 * <li>Duplicated filters or handlers will be ignored w/o error msg.
 */
public class DefaultTraceEventListener implements ITraceEventListener {
	
	private ListenerKey m_listenerKey;
	
	private final List<ITraceEventFilter> m_filters = 
		new ArrayList<ITraceEventFilter>(1);
	
	private List<ITraceEventHandler> m_handlers =
		new ArrayList<ITraceEventHandler>(1);

	private static final TraceConfigMgr s_configBean = TraceConfigMgr.getInstance();

	//
	// Constructor
	//
	public DefaultTraceEventListener(final ListenerId id) { 
		
		if (id == null){
			DsfExceptionHelper.chuck("id is null");
		}
		m_listenerKey = new ListenerKey(getClass(), id);
		
		// Config
		ListenerConfig config = s_configBean.getListenerConfig(m_listenerKey);
		if (config != null){
			addFilters(config);
			addHandlers(config);
		}
		
		// Default if none configured
		if (m_handlers.isEmpty()){
			m_handlers.add(TraceCtx.ctx().getTraceManager().getDefaultHandler());
		}
	}
	
	protected DefaultTraceEventListener(){
	}
	
	//
	// Satisfy ITraceEventListener
	//
	public ListenerId getId(){
		return m_listenerKey.getId();
	}
	
	public boolean isApplicable(TraceEvent event) {
		return true;
	}
	
	public void beforeTrace(final TraceEvent event) {	
		// TODO
	}
	
	public void trace(final TraceEvent event){
		
		for (ITraceEventFilter filter: m_filters){
			if (!filter.isInterested(event)){
				return;
			}
		}
		
		for (ITraceEventHandler handler: m_handlers){
			handler.handle(event);
		}
	}
	
	public void afterTrace(final TraceEvent event) {
		// TODO
	}
	
	public void close(){
		for (ITraceEventHandler handler: m_handlers){
			handler.close();
		}
	}
	
	//
	// API
	//
	public ListenerKey getKey(){
		return m_listenerKey;
	}
	
	public boolean hasFilter(final ITraceEventFilter filter){
		if (filter == null){
			return false;
		}
		for (ITraceEventFilter f: m_filters){
			if (f.getId().equals(filter.getId()) 
				&& f.getClass() == filter.getClass()){
				return true;
			}
		}
		
		return false;
	}
	
	public void addFilter(final ITraceEventFilter filter){
		if (filter == null){
			DsfExceptionHelper.chuck("filter is null");
		}
		if (!hasFilter(filter)){
			m_filters.add(filter);
		}
	}
	
	public List<ITraceEventFilter> getFilters(){
		if (m_filters.isEmpty()){
			return Collections.emptyList();
		}
		else {
			return Collections.unmodifiableList(m_filters);
		}
	}
	
	public void removeFilter(final ITraceEventFilter filter){
		m_filters.remove(filter);
	}
	
	public void removeAllFilters(){
		m_filters.clear();
	}
	
	public boolean hasHandler(final ITraceEventHandler handler){
		if (handler == null){
			return false;
		}
		for (ITraceEventHandler h: m_handlers){
			if (h.getId().equals(handler.getId()) 
				&& h.getClass() == handler.getClass()){
				return true;
			}
		}
		
		return false;
	}
	
	public void addHandler(final ITraceEventHandler handler){
		if (handler == null){
			DsfExceptionHelper.chuck("handler is null");
		}
		if (!hasHandler(handler)){
			m_handlers.add(handler);
		}
	}
	
	public List<ITraceEventHandler> getHandlers(){
		if (m_handlers.isEmpty()){
			return Collections.emptyList();
		}
		else {
			return Collections.unmodifiableList(m_handlers);
		}
	}
	
	public void removeHandler(final ITraceEventHandler handler){
		m_handlers.remove(handler);
	}
	
	public void removeAllHandlers(){
		m_handlers.clear();
	}

	//
	// Private
	//
	private void addFilters(final ListenerConfig config){
		if (config == null){
			return;
		}
		
		List<FilterKey> filterKeys = config.getFilters();
		ITraceEventFilter filter;
		if (!filterKeys.isEmpty()){
			for (FilterKey key: filterKeys){
				filter = Factory.createFilter(key);
				m_filters.add(filter);
			}
		}
	}
	
	private void addHandlers(final ListenerConfig config){
		if (config == null){
			return;
		}
		
		List<HandlerKey> handlerKeys = config.getHandlers();
		ITraceEventHandler handler;
		if (!handlerKeys.isEmpty()){
			for (HandlerKey key: handlerKeys){
				handler = Factory.createHandler(key);
				m_handlers.add(handler);
			}
		}
	}
}
