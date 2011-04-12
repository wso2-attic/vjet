/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import java.util.List;

import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.common.tracer.ITracer;

public interface ITranslateTracer extends ITracer {
	
	public void startGroup(TraceId id, TraceAttr...args);
	
	public void startGroup(TraceId id, TraceTime timer, TraceAttr...args);
	
	public void endGroup(TraceId id);
	
	public void endGroup(TraceId id, List<TranslateError> errors);
	
	public void endGroup(TraceId id, List<TranslateError> errors, TraceTime timer);
	
	public void traceError(TraceId id,List<TranslateError> errors);
	
	public void traceError(TraceId id,TranslateError error);
	
	public void traceTime(TraceId id, TraceTime timer);
}
