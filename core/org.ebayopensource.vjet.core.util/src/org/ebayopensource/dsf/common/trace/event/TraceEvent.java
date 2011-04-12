/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.event;

import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.common.trace.ITraceData;
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;

public class TraceEvent extends DsfEvent<Object, ITraceEventListener> {
	
	private static final long serialVersionUID = 1L;

	private TraceType m_type;
	private TraceId m_id;
	private ITraceData[] m_data;
	
	@Deprecated
	private Object[] m_args;
	
	//
	// API
	//
	@Deprecated
	public TraceEvent(
			final TraceId id, 
			final TraceType type, 
			final Object source, 
			final Object... args)
	{	
		super(source, ITraceEventListener.class);
		m_type = type;
		m_id = id;
		m_args = args;
	}
	
	public TraceEvent(
			final TraceId id, 
			final TraceType type, 
			final Object source, 
			final ITraceData... data)
	{	
		super(source, ITraceEventListener.class);
		m_type = type;
		m_id = id;
		m_data = data;
	}
	
	public TraceType getType(){
		return m_type;
	}
	
	public TraceId getId(){
		return m_id;
	}
	
	@Deprecated
	public Object[] getArgs(){
		return m_args;
	}
	
	public ITraceData[] getData(){
		return m_data;
	}

	//
	// Satisfy abstract requirement from DsfEvent
	//
	public boolean dispatch(ITraceEventListener listener) 
		throws AbortDsfEventProcessingException
	{
		// empty on purpose
		return true;
	}
}
