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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.listener.DefaultTraceEventListener;
import org.ebayopensource.dsf.common.trace.listener.ListenerId;
import org.ebayopensource.dsf.common.tracer.TraceManager;


/**
 * Please follow the following formats for each config key/value.
 * 1. Global switch:
 *    key: 		PTY_ENABLE_TRACE
 *    value: 	"true"|"false"
 *    
 * 2. Enable/disable tracer per scope
 *    key: 		PTY_PREFIX_ENABLE + [scope]
 *    value:	"true"|"false"|""	(empty value will leave the node as unconfigured)
 *    
 * 3a. Add Filters to tracers
 *    key: 		PTY_PREFIX_ADD_FILTER_TO_TRACER + [scope]
 *    value: 	FilterClass:Name
 *    
 * 3b. Remove Filters to tracers
 *    key: 		PTY_PREFIX_REMOVE_FILTER_FROM_TRACER + [scope]
 *    value: 	FilterClass:Name
 *    
 * 4a. Add Listeners to tracers
 *    key: 		PTY_PREFIX_ADD_LISTENER + [scope]
 *    value: 	ListenerClass:Name
 *    
 * 4b. Remove Listeners to tracers
 *    key: 		PTY_PREFIX_REMOVE_LISTENER + [scope]
 *    value: 	ListenerClass:Name
 *   
 * 5a. Add Filters to listeners
 *    key: 		PTY_PREFIX_ADD_FILTER_TO_LISTENER + [listenerClass:name]
 *    value: 	FilterClass:Name
 *    
 * 5b. Remove Filters to listeners
 *    key: 		PTY_PREFIX_REMOVE_FILTER_FROM_LISTENER + [listenerClass:name]
 *    value: 	FilterClass:Name
 *    
 * 6a. Add handlers to listeners
 *    key: 		PTY_PREFIX_ADD_HANDLER + [listenerClass:name]
 *    value: 	HandlerClass:Name
 *    
 * 6b. Remove handlers to listeners
 *    key: 		PTY_PREFIX_REMOVE_HANDLER + [listenerClass:name]
 *    value: 	HandlerClass:Name
 *
 */
public final class TraceConfigMgr {
	
	public final static String ID = "org.ebayopensource.dsf.common.trace.config";
	public final static String ALIAS = "v4trace";
	public final static String GROUP = "v4.dsf";
	public final static String DESC = "v4 trace config";
	
	// Global
	public final static String PTY_ENABLE_TRACE = "EnableTrace";
	public final static String PTY_ENABLE_VERBOSE = "EnableVerbose";
	// Per scope
	public final static String PTY_PREFIX_ENABLE = "Enable_";
	public final static String PTY_PREFIX_ADD_FILTER_TO_TRACER = "AddFilterToTracer_";
	public final static String PTY_PREFIX_REMOVE_FILTER_FROM_TRACER = "RemoveFilterFromTracer_";
	public final static String PTY_PREFIX_ADD_LISTENER = "AddListener_";
	public final static String PTY_PREFIX_REMOVE_LISTENER = "RemoveListener_";
	
	// Per listener
	public final static String PTY_PREFIX_ADD_FILTER_TO_LISTENER = "AddFilterToListener_";
	public final static String PTY_PREFIX_REMOVE_FILTER_FROM_LISTENER = "RemoveFilterFromListener_";
	public final static String PTY_PREFIX_ADD_HANDLER = "AddHandler_";
	public final static String PTY_PREFIX_REMOVE_HANDLER = "RemoveHandler_";
	
	public final static String DELIMITER = ":";
	
	public final static String TRUE = "true";
	public final static String FALSE = "false";

	
	private boolean m_isTraceOn;
	private boolean m_isVerboseOn;
	private List<String> m_enabledScopes = new ArrayList<String>(1);	
	private List<String> m_disabledScopes = new ArrayList<String>(1);
	
	private Map<String,TracerConfig> m_tracerConfigs = 
		new HashMap<String,TracerConfig>(1);	
	
	private Map<ListenerKey,ListenerConfig> m_listenerConfigs =
		new HashMap<ListenerKey,ListenerConfig>(1);					
	
	//
	// Singleton
	//
	private static TraceConfigMgr s_instance = new TraceConfigMgr();
	private TraceConfigMgr() {
		reset();
	}
	public static TraceConfigMgr getInstance() {
		return s_instance;
	}

	//
	// Satisfy PropertyChangeListener
	//

	//
	// API
	//

	
	public void setTraceOn(boolean isTraceOn){
		m_isTraceOn = isTraceOn;
	}
	
	public boolean isVerboseOn(){
		return m_isVerboseOn;
	}

	public void setVerboseOn(boolean isVerboseOn){
		m_isVerboseOn = isVerboseOn;
	}
	
	public boolean isTraceOn(){
		return m_isTraceOn;
	}

	public synchronized void setEnabled(final Class callingClass, Boolean enable){
		if (callingClass == null){
			return;
		}
		setEnabled(TraceManager.getScope(callingClass), enable);
	}
	
	public synchronized void setEnabled(final String scope, Boolean enable){
		
		if (scope == null){
			return;
		}
		
		if (Boolean.TRUE.equals(enable)) {
			if (!m_enabledScopes.contains(scope)){
				m_enabledScopes.add(scope);
				if (m_disabledScopes.contains(scope)){
					m_disabledScopes.remove(scope);
				}
			}
		} 
		else if (Boolean.FALSE.equals(enable)) {
			if (!m_disabledScopes.contains(scope)){
				m_disabledScopes.add(scope);
				if (m_enabledScopes.contains(scope)){
					m_enabledScopes.remove(scope);
				}
			}
		} 
		else {
			m_enabledScopes.remove(scope);
			m_disabledScopes.remove(scope);
		}
	}
	
	public synchronized List<String> getEnabledScopes(){
		return Collections.unmodifiableList(m_enabledScopes);
	}
	
	public synchronized List<String> getDisabledScopes(){
		return Collections.unmodifiableList(m_disabledScopes);
	}
	
	public synchronized TracerConfig getTracerConfig(final String scope){
		return getTracerConfig(scope, false);
	}
	
	public synchronized TracerConfig getTracerConfig(final String scope, boolean create){
		if (scope == null){
			DsfExceptionHelper.chuck("scope is null");
		}
		TracerConfig config = m_tracerConfigs.get(scope);
		if (config == null && create){
			config = new TracerConfig(scope);
			m_tracerConfigs.put(scope, config);
		}
		return config;
	}
	
	public synchronized void removeTracerConfig(final String scope){
		m_tracerConfigs.remove(scope);
	}
	
	public synchronized ListenerConfig getListenerConfig(final ListenerKey listenerKey){
		return getListenerConfig(listenerKey, false);
	}
	
	public synchronized ListenerConfig getListenerConfig(
			final ListenerKey listenerKey, boolean create){
		
		if (listenerKey == null){
			DsfExceptionHelper.chuck("listenerKey is null");
		}
		ListenerConfig config = m_listenerConfigs.get(listenerKey);
		if (config == null && create){
			config = new ListenerConfig(listenerKey);
			m_listenerConfigs.put(listenerKey, config);
		}
		return config;
	}
	
	public synchronized void removeListenerConfig(final ListenerKey listenerKey){
		m_listenerConfigs.remove(listenerKey);
	}
	
	public void reset(){
		m_isTraceOn = false;
		m_isVerboseOn = false;
		m_enabledScopes.clear();	
		m_disabledScopes.clear();
		m_tracerConfigs.clear();	
		m_listenerConfigs.clear();		
		
		// Init
		m_enabledScopes.add(TraceManager.SCOPE_ROOT);
		getTracerConfig(TraceManager.SCOPE_ROOT, true).addListener(
			new ListenerKey(DefaultTraceEventListener.class, 
				new ListenerId("DefaultListenerForRoot")));
	}
	


}
