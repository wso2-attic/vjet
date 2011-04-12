/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.tracer;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.common.trace.config.TraceConfigMgr;
import org.ebayopensource.dsf.common.trace.handler.DefaultTraceEventHandler;
import org.ebayopensource.dsf.common.trace.handler.HandlerId;
import org.ebayopensource.dsf.common.trace.handler.TraceFormatter;
import org.ebayopensource.dsf.common.trace.introspect.DefaultTraceIntrospector;

/**
 * Trace manager is responsible for the following
 * <li>Manage global on/off of tracing</li>
 * <li>Answer the tracer for a given scope</li>
 * <li>Provide default constructs for sharing</li>
 * 
 * Please be aware that:
 * <li>Global switch can be set per request or via shared config. Former has 
 * higher precedence</li>
 * <li>Tracers can be enable/disable per scope. If nothing has been set for a scope, 
 * it'll lookup parents' setting recursively.</li>
 * <li>Tracers are created and returned based on tracer config. If there is no config 
 * for a scope, the nearest ancestor's tracer will be returned, no matter it's enabled
 * or not.</li>
 */
public final class TraceManager {
	
	public static final String SCOPE_ROOT = "";

	private Boolean m_isTraceOn = null;
	private Map<String,DsfTracer> m_tracers = new HashMap<String,DsfTracer>(1);
	
	private Logger m_defaultLogger;
	private Formatter m_defaultFormatter;
	private Writer m_defaultWriter;
	private DefaultTraceEventHandler m_defaultHandler;
	private DefaultTraceIntrospector m_defaultIntrospector;
	
	private static TraceConfigMgr s_configBean;
	private Boolean m_isVerboseOn = null;
	//
	// API
	//
	/**
	 * Turn on/off the tracing for all. 
	 * It has higher precedence than TraceConfigMgr.
	 * @param on boolean
	 */
	public void setTraceOn(boolean on){
		m_isTraceOn = Boolean.valueOf(on);
	}
	
	/**
	 * Answer whether trace is turned on. 
	 * It first checks the setting in this class which is per request.
	 * If no setting in this class, it returns value from shared config bean.
	 * @return boolean
	 */
	public boolean isTraceOn(){
		if (m_isTraceOn != null){
			return m_isTraceOn.booleanValue();
		}
		else {
			return getConfigBean().isTraceOn();
		}
	}
	
	/**
	 * Answer whether trace is enabled for the given callingClass.
	 * It'll check whether the setting exists for the corresponding scope
	 * and return true or false accordingly.
	 * If setting is not found for the corresponding scope, it'll check 
	 * parent scope recursively until it find one.
	 * If none is found, return false.
	 * @param callingClass Class
	 * @return boolean
	 */
	public boolean isEnabled(final Class callingClass){
		if (!isTraceOn() || callingClass == null){
			return false;
		}
		return isEnabled(getScope(callingClass));
	}
	
	/**
	 * Answer whether trace is enabled for the given scope.
	 * It'll check whether the setting exists for the given scope
	 * and return true or false accordingly.
	 * If setting is not found for the given scope, it'll check 
	 * parent scope recursively until it find one.
	 * If none is found, return false.
	 * @param callingClass Class
	 * @return boolean
	 */
	public boolean isEnabled(final String scope){
		
		if (!isTraceOn() || scope == null){
			return false;
		}
		
		List<String> enabledScopes = getConfigBean().getEnabledScopes();
		if (enabledScopes.isEmpty()){
			return false;
		}
		
		if (enabledScopes.contains(scope)){
			return true;
		}
		
		List<String> disabledScopes = getConfigBean().getDisabledScopes();
		if (disabledScopes.contains(scope)){
			return false;
		}
		
		return isEnabled(getParentScope(scope));
	}
	
	/**
	 * Answer the tracer for the given callingClass. It'll return the nearest tracer
	 * on the ancestor chain that is enabled. It'll stop lookup and return NoOpTracer
	 * if it hits any ancestor that is explicitly disabled.
	 * @param callingClass Class
	 * @return ITracer
	 * @exception DsfRuntimeException if callingClass is null
	 */
	public ITracer getTracer(final Class callingClass) {
		if (callingClass == null){
			DsfExceptionHelper.chuck("callingClass is null");
		}
		if (!isTraceOn()){
			return NoOpTracer.NO_OP_TRACER;
		}
		return getTracer(getScope(callingClass));
	}
	
	/**
	 * Answer the tracer for the given scope. It'll return the nearest tracer
	 * on the ancestor chain that is enabled. It'll stop lookup and return NoOpTracer
	 * if it hits any ancestor that is explicitly disabled.
	 * @param callingClass Class
	 * @param scope String
	 * @return ITracer
	 * @exception DsfRuntimeException if scope is null
	 */
	public ITracer getTracer(final String scope){ 
		
		if (scope == null){
			DsfExceptionHelper.chuck("scope is null");
		}
		
		if (!isTraceOn() || !isEnabled(scope)){
			return NoOpTracer.NO_OP_TRACER;
		}
		
		return findTracer(scope);
	}
	
	public Logger getDefaultLogger(){
		if (m_defaultLogger == null){
//			m_defaultLogger = Factory.createDefaultLogger(SCOPE_ROOT, getDefaultFormatter());
			m_defaultLogger = Factory.createDefaultLogger(
					this.getClass().getName(), getDefaultFormatter());
		}
		return m_defaultLogger;
	}
	
	public Formatter getDefaultFormatter(){
		if (m_defaultFormatter == null){
			m_defaultFormatter = new TraceFormatter();
		}
		return m_defaultFormatter;
	}
	
	public DefaultTraceEventHandler getDefaultHandler(){
		if (m_defaultHandler == null){
			m_defaultHandler = new DefaultTraceEventHandler(new HandlerId("Default"));
		}
		return m_defaultHandler;
	}
	
	public Writer getDefaultWriter(){
		if (m_defaultWriter == null){
			m_defaultWriter = new StringWriter();
		}
		return m_defaultWriter;
	}
	
	public DefaultTraceIntrospector getDefaultIntrospector(){
		if (m_defaultIntrospector == null){
			m_defaultIntrospector = new DefaultTraceIntrospector();
		}
		return m_defaultIntrospector;
	}
	
	/**
	 * Close all the tracers and flush out the content in defaultWriter 
	 * via default logger.
	 */
	public void close(){
		
		if (!isTraceOn()){
			return;
		}
		
		for (DsfTracer tracer: m_tracers.values()){
			tracer.close();
		}
		
		if (m_defaultWriter != null){
			Logger logger = TraceCtx.ctx().getTraceManager().getDefaultLogger();
			
			logger.log(Level.INFO, m_defaultWriter.toString());
			for (Handler h: logger.getHandlers()){
				h.close();
			}
		}
	}
	
	public static String getParentScope(final String childScope){
		
		if (childScope == null){
			return null;
		}
		
		if (childScope.equals(SCOPE_ROOT)){
			return null;
		}
		
		int idx = childScope.lastIndexOf(".");
		if (idx < 0){
			return SCOPE_ROOT;
		}
		
		return childScope.substring(0, idx);
	}
	
	public static String getScope(Class callingClass){
		
		if (callingClass == null){
			DsfExceptionHelper.chuck("callingClass is null");
		}

		if (callingClass.getPackage() != null) {
			return callingClass.getPackage().getName();
		} else {
			return callingClass.getName();
		}
	}
	
	//
	// Private
	//
	private DsfTracer addTracer(final String scope){
		if (scope == null){
			DsfExceptionHelper.chuck("scope is null");
		}
		DsfTracer tracer = new DsfTracer(scope);
		m_tracers.put(scope, tracer);
		return tracer;
	}
	
	private DsfTracer findTracer(final String scope){
		
		if (scope == null){
			DsfExceptionHelper.chuck("scope is null");
		}
		
		DsfTracer tracer = m_tracers.get(scope);
		if (tracer != null){
			return tracer;
		}
		
		if (hasScopeConfig(scope) || SCOPE_ROOT.equals(scope)){
			return addTracer(scope);
		}

		return findTracer(getParentScope(scope));
	}
	
	private boolean hasScopeConfig(final String scope){
		if (scope == null){
			DsfExceptionHelper.chuck("scope is null");
		}
		return getConfigBean().getTracerConfig(scope) != null;
	}
	
	private static TraceConfigMgr getConfigBean() {
		if (s_configBean == null) {
			s_configBean = TraceConfigMgr.getInstance();
		}
		return s_configBean;
	}

	public boolean isVerboseOn() {
		if (m_isVerboseOn != null){
			return m_isVerboseOn.booleanValue();
		}
		else {
			return getConfigBean().isVerboseOn();
		}
	}

	public void setVerboseOn(boolean verboseOn) {
		m_isVerboseOn = Boolean.valueOf(verboseOn);
	}
}
