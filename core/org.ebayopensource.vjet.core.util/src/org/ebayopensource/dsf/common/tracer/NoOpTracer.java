/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.tracer;

import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.common.trace.ITraceData;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.common.trace.event.TraceType;
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;


public class NoOpTracer implements ITracer {
	
	public static final ITracer NO_OP_TRACER = new NoOpTracer();
	private static final String NULL = "null";
	
	//
	// Constructors
	//
	protected NoOpTracer(){
	}
	
	//
	// Satisfy ITracer
	//
	public boolean isEnabled(){
		return false;
	}
	
	public String getScope(){
		return NULL;
	}
	
	public void traceEnterMethod(TraceId id, Object caller, Object ... args){
		// No op
	}
	
	public void traceExitMethod(TraceId id, Object caller, Object ... args){
		// No op
	}
	
	public void traceObjectType(TraceId id, Object caller, Object target){
		// No op
	}
	
	public void traceObjectState(TraceId id, Object caller, Object target){
		// No op
	}

	public void traceDataModel(TraceId id, Object caller, Object target){
		// No op
	}

	public void traceMsg(TraceId id, Object caller, String msg){
		// No op
	}
	
	public void traceNV(TraceId id, Object caller, String name, String value){
		// No op
	}
	
	public void trace(TraceId id, TraceType type, Object caller, Object ... args){
		// No op
	}
	
	public void trace(TraceId id, TraceType type, Object caller, ITraceData ... args){
		// No op
	}
	
	public void addListener(final ITraceEventListener listener){
		// No op
	}
	
	public List<ITraceEventListener> getListeners(){
		return Collections.emptyList();
	}
	
	public boolean removeListener(final ITraceEventListener listener){
		return false;
	}
	
	public void close(){}
}
