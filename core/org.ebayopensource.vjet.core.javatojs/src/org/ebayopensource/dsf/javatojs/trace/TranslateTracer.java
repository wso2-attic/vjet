/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.ITraceData;
import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.config.FilterKey;
import org.ebayopensource.dsf.common.trace.config.ListenerKey;
import org.ebayopensource.dsf.common.trace.event.TraceEvent;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.common.trace.event.TraceType;
import org.ebayopensource.dsf.common.trace.filter.ITraceEventFilter;
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;
import org.ebayopensource.dsf.common.tracer.NoOpTracer;

public class TranslateTracer implements ITranslateTracer {
	
	public static final ITranslateTracer NO_OP = new NoOp();
	
	private String m_scope;
	
	private final List<ITraceEventFilter> m_filters = 
		new ArrayList<ITraceEventFilter>(5);
	
	private final List<ITraceEventListener> m_listeners = 
		new ArrayList<ITraceEventListener>(5);
	
//	private static TraceConfigMgr s_configBean;
	
	public void startGroup(TraceId id, TraceAttr...attrs){
		trace(id, TranslateTraceType.START_GROUP, this, (ITraceData[])attrs);
	}
	
	public void startGroup(TraceId id, TraceTime timer, TraceAttr...attrs){
		timer.start();
		trace(id, TranslateTraceType.START_GROUP, this, (ITraceData[])attrs);
	}
	
	public void endGroup(TraceId id){
		trace(id, TranslateTraceType.END_GROUP, this);
	}
	
	public void endGroup(TraceId id, List<TranslateError> errors){
		traceError(id, errors);
		endGroup(id);
	}
	
	public void endGroup(TraceId id, List<TranslateError> errors, TraceTime timer){
		timer.end();
		traceError(id, errors);
		traceTime(id, timer);
		endGroup(id);
	}
	
	public void traceTime(TraceId id, TraceTime timer){
		if (timer == null){
			return;
		}
		trace(id, TranslateTraceType.TIME, this, timer);
	}
	
	public void traceError(TraceId id, List<TranslateError> errors){
		if (errors == null || errors.isEmpty()){
			return;
		}
		trace(id, TranslateTraceType.ERRORS, this, new TraceErrors(errors));
	}
	
	public void traceError(TraceId id, TranslateError error){
		if (error == null){
			return;
		}
		List<TranslateError> errors = new ArrayList<TranslateError>(1);
		errors.add(error);
		trace(id, TranslateTraceType.ERRORS, this, new TraceErrors(errors));
	}
	
	//
	// Satisfy ITracer
	//
	public boolean isEnabled(){
		return true;
	}
	
	public String getScope(){
		return m_scope;
	}

	public void close(){
		for (ITraceEventListener listener: m_listeners){
			listener.close();
		}
	}
	
	public void traceEnterMethod(TraceId id, Object caller, Object ... args){
		trace(id, TraceType.ENTER_METHOD, caller, args);
	}
	
	public void traceExitMethod(TraceId id, Object caller, Object ... args){
		trace(id, TraceType.EXIT_METHOD, caller, null, args);
	}
	
	public void traceObjectType(TraceId id, Object caller, Object target){
		trace(id, TraceType.OBJECT_TYPE, caller, target);
	}
	
	public void traceObjectState(TraceId id, Object caller, Object target){
		trace(id, TraceType.OBJECT_STATE, caller, target);
	}

	public void traceDataModel(TraceId id, Object caller, Object target){
		trace(id, TraceType.DATAMODEL, caller, target);
	}
	
	public void traceMsg(TraceId id, Object caller, String msg){
		trace(id, TraceType.MSG, caller, msg);
	}
	
	public void traceNV(TraceId id, Object caller, String name, String value){
		trace(id, TraceType.NV, caller, name, value);
	}
	
	public void trace(TraceId id, TraceType type, Object caller, Object ... args){
		
		try {
			TraceEvent event = new TraceEvent(id, type, caller, args);
			
			beforeTrace(event);
			trace(event);
			afterTrace(event);
		}
		catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	public void trace(TraceId id, TraceType type, Object caller, ITraceData ... data){
		try {
			TraceEvent event = new TraceEvent(id, type, caller, data);
			
			beforeTrace(event);
			trace(event);
			afterTrace(event);
		}
		catch(Throwable t){
			t.printStackTrace();
		}
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
		return Collections.unmodifiableList(m_filters);
	}
	
	public boolean removeFilter(final ITraceEventFilter filter){
		if (filter == null){
			return false;
		}
		
		return m_filters.remove(filter);
	}
	
	public void removeAllFilters(){
		m_filters.clear();
	}
	
	public boolean hasListener(final ITraceEventListener listener){
		if (listener == null){
			return false;
		}
		for (ITraceEventListener l: m_listeners){
			if (l.getId().equals(listener.getId()) 
				&& l.getClass() == listener.getClass()){
				return true;
			}
		}
		
		return false;
	}
	
	public void addListener(final ITraceEventListener listener){

		if (listener == null){
			DsfExceptionHelper.chuck("listener is null");
		}
		
		if (!hasListener(listener)){
			m_listeners.add(listener);
		}
	}
	
	public List<ITraceEventListener> getListeners(){
		return Collections.unmodifiableList(m_listeners);
	}
	
	public boolean removeListener(final ITraceEventListener listener){
		if (listener == null){
			return false;
		}
		
		return m_listeners.remove(listener);
	}
	
	public void removeAllListeners(){
		m_listeners.clear();
	}
	
	//
	// Private
	//
	private List<FilterKey> getFilterKeys(final String scope){
//		if (scope == null){
			return Collections.emptyList();
//		}
//		
//		TracerConfig config = getConfigBean().getTracerConfig(scope);
//		if (config != null){
//			return config.getFilters();
//		}
//		else {
//			return getFilterKeys(TraceManager.getParentScope(scope));	
//		}
	}
	
	private List<ListenerKey> getListenerKeys(final String scope){
//		if (scope == null){
			return Collections.emptyList();
//		}
//		
//		TracerConfig config = getConfigBean().getTracerConfig(scope);
//		if (config != null){
//			return config.getListeners();
//		}
//		else {
//			return getListenerKeys(TraceManager.getParentScope(scope));
//		}
	}
	
	private void beforeTrace(final TraceEvent event) {
		for (ITraceEventListener listener : m_listeners) {
			if (listener.isApplicable(event)) {
				listener.beforeTrace(event);
			}
		}
	}
	
	private void trace(final TraceEvent event) {
		for (ITraceEventListener listener : m_listeners) {
			if (listener.isApplicable(event)) {
				listener.trace(event);
			}
		}
	}
	
	private void afterTrace(final TraceEvent event) {		
		for (ITraceEventListener listener : m_listeners) {
			if (listener.isApplicable(event)) {
				listener.afterTrace(event);
			}
		}
	}

//	private static TraceConfigMgr getConfigBean() {
//		if (s_configBean == null) {
//			s_configBean = TraceConfigMgr.getInstance();
//		}
//		return s_configBean;
//	}
	
	public static class NoOp extends NoOpTracer implements ITranslateTracer {
		
		private NoOp(){}
		
		public void startGroup(TraceId id, TraceAttr...attrs){
			// No op
		}
		
		public void startGroup(TraceId id, TraceTime timer, TraceAttr...attrs){
			// No op
		}
		
		public void endGroup(TraceId id){
			// No op
		}
		
		public void endGroup(TraceId id, List<TranslateError> errors){
			// No op
		}
		
		public void endGroup(TraceId id, List<TranslateError> errors, TraceTime timer){
			// No op
		}
		
		public void traceError(TraceId id, List<TranslateError> errors){
			// No op;
		}
		
		public void traceError(TraceId id, TranslateError error){
			// No op;
		}
		
		public void traceTime(TraceId id, TraceTime timer){
			// No op;
		}
	}
}
