/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.tracer;

import java.util.List;

import org.ebayopensource.dsf.common.trace.ITraceData;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.common.trace.event.TraceType;
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;

/**
 * Defines common interface for tracing.
 */
public interface ITracer {
	
	boolean isEnabled();
	
	String getScope();
	
	/**
	 * Trace enter method with given id. 
	 * @param id TraceId
	 * @param caller Object
	 * @param args Object ...
	 */
	void traceEnterMethod(TraceId id, Object caller, Object ... args);
	
	/**
	 * Trace exit method with given id. 
	 * @param id TraceId
	 * @param caller Object
	 * @param args Object ...
	 */
	void traceExitMethod(TraceId id, Object caller, Object ... args);
	
	/**
	 * Trace type of given target object
	 * @param id TraceId
	 * @param caller Object
	 * @param target Object
	 */
	void traceObjectType(TraceId id, Object caller, Object target);
	
	/**
	 * Trace state of given target object
	 * @param id TraceId
	 * @param caller Object
	 * @param target Object
	 */
	void traceObjectState(TraceId id, Object caller, Object target);
	
	/**
	 * Trace state of javabean target object
	 * @param id TraceId
	 * @param caller Object
	 * @param target a java bean object 
	 */
	void traceDataModel(TraceId id, Object caller, Object target);
	
	/**
	 * Trace msg
	 * @param id TraceId
	 * @param caller Object
	 * @param msg String
	 */
	void traceMsg(TraceId id, Object caller, String msg);
	
	/**
	 * Trace name/value
	 * @param id TraceId
	 * @param caller Object
	 * @param name String
	 * @param value String
	 */
	void traceNV(TraceId id, Object caller, String name, String value);
	
	/**
	 * Trace with given id and type
	 * @param id TraceId
	 * @param type TraceType
	 * @param caller Object
	 * @param args Object ...
	 */
	void trace(TraceId id, TraceType type, Object caller, Object ... args);
	
	/**
	 * Trace with given id, type, name and attributes
	 * @param id TraceId
	 * @param type TraceType
	 * @param caller Object
	 * @param data ITraceData ...
	 */
	void trace(TraceId id, TraceType type, Object caller, ITraceData ... data);
	
	void addListener(final ITraceEventListener listener);
	
	boolean removeListener(final ITraceEventListener listener);
	
	List<ITraceEventListener> getListeners();
	
	void close();
}
