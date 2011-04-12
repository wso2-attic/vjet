/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf;

import org.ebayopensource.dsf.common.trace.event.TraceId;

public class DsfTraceId extends TraceId {
	
	public static final DsfTraceId LIFE_CYCLE = new DsfTraceId(1, "LifeCycle");
	
	public static final DsfTraceId PIPELINE = new DsfTraceId(2, "Pipeline");
	
	public static final DsfTraceId SERVICE_PHASES = new DsfTraceId(3, "ServicePhases");
	public static final DsfTraceId SERVICE_HANDLERS = new DsfTraceId(4, "ServiceHandlers");

	public static final DsfTraceId DATAMODEL = new DsfTraceId(5, "DataModel");
	
	private static final long serialVersionUID = 1L;
	
	//
	// Constructor(s)
	//
	public DsfTraceId(final int id, final String name) {
		super(id, name);
	}
}
