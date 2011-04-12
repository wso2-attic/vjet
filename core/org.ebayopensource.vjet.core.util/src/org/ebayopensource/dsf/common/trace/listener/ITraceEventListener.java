/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.listener;

import org.ebayopensource.dsf.common.event.IDsfEventListener;
import org.ebayopensource.dsf.common.trace.event.TraceEvent;

public interface ITraceEventListener extends IDsfEventListener {
	
	/**
	 * Answer the id of this listener
	 * @return ListenerId
	 */
	ListenerId getId();

	/**
	 * Answer whether this listener is interested in events with
	 * given type and id.
	 * @param id TraceEventId
	 * @param type TraceEventType
	 */
	boolean isApplicable(TraceEvent event);

	/**
	 * Called before trace event. 
	 * @param event TraceEvent. 
	 * @exception DsfRuntimeException if event is null
	 */	
	void beforeTrace(TraceEvent event);
	
	/**
	 * Called to trace event. 
	 * @param event TraceEvent. 
	 * @exception DsfRuntimeException if event is null
	 */	
	void trace(TraceEvent event);

	/**
	 * Called after trace event. 
	 * @param event TraceEvent. 
	 * @exception DsfRuntimeException if event is null
	 */	
	void afterTrace(TraceEvent event);

	void close();
}
