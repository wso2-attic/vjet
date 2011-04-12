/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.listener.DefaultTraceEventListener;
import org.ebayopensource.dsf.common.trace.listener.ListenerId;
import org.ebayopensource.dsf.common.tracer.TraceManager;
import com.ebay.kernel.bean.configuration.BaseConfigBean;
import com.ebay.kernel.bean.configuration.BeanConfigCategoryInfo;
import com.ebay.kernel.bean.configuration.BeanPropertyInfo;
import com.ebay.kernel.bean.configuration.ConfigCategoryCreateException;
import com.ebay.kernel.bean.configuration.DynamicConfigBean;
import com.ebay.kernel.bean.configuration.PropertyChangeHistory;

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
public final class TraceConfigMgr implements PropertyChangeListener {
	
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

	private DynamicConfigBean m_configBean;
	
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
		initConfigBean();
		reset();
	}
	public static TraceConfigMgr getInstance() {
		return s_instance;
	}

	//
	// Satisfy PropertyChangeListener
	//
	public synchronized void propertyChange(PropertyChangeEvent evt) {
		
		BeanPropertyInfo info = BaseConfigBean.getBeanPropertyInfo(evt);
		
		final String name = info.getName();
		if (name == null){
			return;
		}
		
		final String value = (String)evt.getNewValue();
		final PropertyChangeHistory changeHistory = 
			m_configBean.createChangeHistory(evt);
		changeHistory.updateStart();
		
		if (name.equals(PTY_ENABLE_TRACE)) {
			if (TRUE.equalsIgnoreCase(value)) {
				m_isTraceOn = true;
				changeHistory.updateSuccess();
			} else if (FALSE.equalsIgnoreCase(value)) {
				m_isTraceOn = false;
				changeHistory.updateSuccess();
			} else {
				changeHistory.updateFailed(
					PTY_ENABLE_TRACE + ": invalid value - " + value);
			}
		}else if(name.equals(PTY_ENABLE_VERBOSE)){
			if (TRUE.equalsIgnoreCase(value)) {
				m_isVerboseOn = true;
				changeHistory.updateSuccess();
			} else if (FALSE.equalsIgnoreCase(value)) {
				m_isVerboseOn = false;
				changeHistory.updateSuccess();
			} else {
				changeHistory.updateFailed(
				PTY_ENABLE_VERBOSE + ": invalid value - " + value);
			}
		}
		else if (name.startsWith(PTY_PREFIX_ENABLE)){
			final String scope = name.substring(PTY_PREFIX_ENABLE.length());
			if (value.trim().length() == 0){
				if (m_enabledScopes.contains(scope)){
					m_enabledScopes.remove(scope);
				}
				if (m_disabledScopes.contains(scope)){
					m_disabledScopes.remove(scope);
				}
			}
			else if (TRUE.equalsIgnoreCase(value)) {
				if (!m_enabledScopes.contains(scope)){
					m_enabledScopes.add(scope);
					if (m_disabledScopes.contains(scope)){
						m_disabledScopes.remove(scope);
					}
					changeHistory.updateSuccess();
				}
			} else if (FALSE.equalsIgnoreCase(value)) {
				if (!m_disabledScopes.contains(scope)){
					m_disabledScopes.add(scope);
					if (m_enabledScopes.contains(scope)){
						m_enabledScopes.remove(scope);
					}
					changeHistory.updateSuccess();
				}
			} else {
				changeHistory.updateFailed(
					PTY_PREFIX_ENABLE + ": invalid value - " + value);
			}
		}
		else if (name.startsWith(PTY_PREFIX_ADD_FILTER_TO_TRACER)){
			
			final String scope = name.substring(PTY_PREFIX_ADD_FILTER_TO_TRACER.length());
			final FilterKey filterKey = FilterKey.parse(value);
			if (filterKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_ADD_FILTER_TO_TRACER + ": invalid filter - " + value);
			}
			else {
				TracerConfig tracerConfig = getTracerConfig(scope, true);
				tracerConfig.addFilter(filterKey);
				changeHistory.updateSuccess();
			}
		}
		else if (name.startsWith(PTY_PREFIX_REMOVE_FILTER_FROM_TRACER)){
			final String scope = name.substring(PTY_PREFIX_REMOVE_FILTER_FROM_TRACER.length());
			final FilterKey filterKey = FilterKey.parse(value);
			if (filterKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_REMOVE_FILTER_FROM_TRACER + ": invalid filter - " + value);
			}
			else {
				TracerConfig tracerConfig = getTracerConfig(scope);
				if (tracerConfig != null){
					tracerConfig.removeFilter(filterKey);
					changeHistory.updateSuccess();
				}
			}
		}
		else if (name.startsWith(PTY_PREFIX_ADD_LISTENER)){
			final String scope = name.substring(PTY_PREFIX_ADD_LISTENER.length());
			final ListenerKey listenerKey = ListenerKey.parse(value);
			if (listenerKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_ADD_LISTENER + ": invalid value - " + value);
			}
			else {
				TracerConfig tracerConfig = getTracerConfig(scope, true);
				tracerConfig.addListener(listenerKey);
				changeHistory.updateSuccess();
			}
		}
		else if (name.startsWith(PTY_PREFIX_REMOVE_LISTENER)){
			final String scope = name.substring(PTY_PREFIX_REMOVE_LISTENER.length());
			final ListenerKey listenerKey = ListenerKey.parse(value);
			if (listenerKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_REMOVE_LISTENER + ": invalid value - " + value);
			}
			else {
				TracerConfig tracerConfig = getTracerConfig(scope);
				if (tracerConfig != null){
					tracerConfig.removeListener(listenerKey);
					changeHistory.updateSuccess();
				}
			}
		}
		else if (name.startsWith(PTY_PREFIX_ADD_FILTER_TO_LISTENER)){
			
			final String listener = name.substring(PTY_PREFIX_ADD_FILTER_TO_LISTENER.length());
			final ListenerKey listenerKey = ListenerKey.parse(listener);
			final FilterKey filterKey = FilterKey.parse(value);
			if (listenerKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_ADD_FILTER_TO_LISTENER + ": invalid listener - " + listener);
			}
			else if (filterKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_ADD_FILTER_TO_LISTENER + ": invalid filter - " + value);
			}
			else {
				ListenerConfig listenerConfig = getListenerConfig(listenerKey, true);
				listenerConfig.addFilter(filterKey);
				changeHistory.updateSuccess();
			}
		}
		else if (name.startsWith(PTY_PREFIX_REMOVE_FILTER_FROM_LISTENER)){
			final String listener = name.substring(PTY_PREFIX_REMOVE_FILTER_FROM_LISTENER.length());
			final ListenerKey listenerKey = ListenerKey.parse(listener);
			final FilterKey filterKey = FilterKey.parse(value);
			if (listenerKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_REMOVE_FILTER_FROM_LISTENER + ": invalid listener - " + listener);
			}
			else if (filterKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_REMOVE_FILTER_FROM_LISTENER + ": invalid filter - " + value);
			}
			else {
				ListenerConfig listenerConfig = getListenerConfig(listenerKey);
				if (listenerConfig != null){
					listenerConfig.removeFilter(filterKey);
					changeHistory.updateSuccess();
				}
			}
		}
		else if (name.startsWith(PTY_PREFIX_ADD_HANDLER)){
			
			final String listener = name.substring(PTY_PREFIX_ADD_HANDLER.length());
			final ListenerKey listenerKey = ListenerKey.parse(listener);
			final HandlerKey handlerKey = HandlerKey.parse(value);
			if (listenerKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_ADD_HANDLER + ": invalid listener - " + listener);
			}
			else if (handlerKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_ADD_HANDLER + ": invalid handler - " + value);
			}
			else {
				ListenerConfig listenerConfig = getListenerConfig(listenerKey, true);
				listenerConfig.addHandler(handlerKey);
				changeHistory.updateSuccess();
			}
		}
		else if (name.startsWith(PTY_PREFIX_REMOVE_HANDLER)){
			final String listener = name.substring(PTY_PREFIX_REMOVE_HANDLER.length());
			final ListenerKey listenerKey = ListenerKey.parse(listener);
			final HandlerKey handlerKey = HandlerKey.parse(value);
			if (listenerKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_REMOVE_HANDLER + ": invalid listener - " + listener);
			}
			else if (handlerKey == null){
				changeHistory.updateFailed(
					PTY_PREFIX_REMOVE_HANDLER + ": invalid handler - " + value);
			}
			else {
				ListenerConfig listenerConfig = getListenerConfig(listenerKey);
				if (listenerConfig != null){
					listenerConfig.removeHandler(handlerKey);
					changeHistory.updateSuccess();
				}
			}
		}

		m_configBean.addChangeHistory(changeHistory);
	}
	
	//
	// API
	//
	public DynamicConfigBean getBean(){
		return m_configBean;
	}
	
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
	
	//
	// Private
	//
	private void initConfigBean() {
		
		BeanConfigCategoryInfo category = null;
		try {
			category = BeanConfigCategoryInfo.createBeanConfigCategoryInfo(
				ID, ALIAS, GROUP, false, false, null, DESC, true);
		} 
		catch (ConfigCategoryCreateException e) {
			DsfExceptionHelper.chuck("Failed to create config category for " + ID);
			return;
		}
		
		m_configBean = new DynamicConfigBean(category);
		m_configBean.setExternalMutable(); 
		m_configBean.addPropertyChangeListener(this);
	}
}
