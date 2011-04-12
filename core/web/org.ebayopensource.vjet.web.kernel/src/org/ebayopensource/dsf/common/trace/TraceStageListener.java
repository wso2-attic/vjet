/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import org.ebayopensource.dsf.DsfTraceId;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.common.trace.event.TraceType;
import org.ebayopensource.dsf.common.tracer.ITracer;
import org.ebayopensource.kernel.stage.StageListenerAdapter;
import org.ebayopensource.kernel.stage.StagePreExecutionEvent;

public class TraceStageListener extends StageListenerAdapter {

	public static final TraceId TRACE_ID = DsfTraceId.LIFE_CYCLE;
	private final ITracer m_tracer;
	
	public TraceStageListener(){
		m_tracer = TraceCtx.ctx().getTracer(getClass());
	}
	
	@Override
	public void beforeStage(final StagePreExecutionEvent event) {	
		m_tracer.trace(TRACE_ID, TraceType.OBJECT_TYPE, this, event.getSource());
	}
}
