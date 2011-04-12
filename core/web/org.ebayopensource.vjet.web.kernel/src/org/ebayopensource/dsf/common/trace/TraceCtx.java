/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.tracer.ITracer;
import org.ebayopensource.dsf.common.tracer.TraceManager;

public class TraceCtx extends BaseSubCtx {

	private IDsfTracer m_tracer;	
	private IDsfInstrumenter m_instrumenter;
	private ContentTracker m_contentTracker;
	
	// New 
	private TraceManager m_traceManager;
	
	/**
	 * Gets a context assocaited with current thread
	 */
	public static TraceCtx ctx() {
		TraceCtx context = CtxAssociator.getCtx();
		if (context == null) {
			context = new TraceCtx();
			setCtx(context);
		}
		return context;
	}
	
	/**
	 * Sets the context to be associated with this thread.  The context
	 * can be null.  
	 */
	public static void setCtx(final TraceCtx context) {
		CtxAssociator.setCtx(context) ;
	}
	
	public void reset() {
		if (m_tracer != null){
			m_tracer.reset();
		}
		m_traceManager = null;
		resetInstrumenter();
		resetContentTracker();
	}
	
	public void setTracer(final IDsfTracer tracer){
		if (tracer == null){
			throw new DsfRuntimeException("tracer is null");
		}
		m_tracer = tracer;
	}
	
	public IDsfTracer getTracer(){
		if (m_tracer == null){
			m_tracer = new DefaultTracer();
		}
		return m_tracer;
	}
	
	public TraceManager getTraceManager(){
		if (m_traceManager == null){
			m_traceManager = new TraceManager();
		}
		return m_traceManager;
	}
	
	public ITracer getTracer(String scope){
		return getTraceManager().getTracer(scope);
	}
	
	public ITracer getTracer(Class callingClass){
		return getTraceManager().getTracer(callingClass);
	}
	
	public IDsfInstrumenter getInstrumenter() {
		return m_instrumenter;
	}
	
	public void setInstrumenter(final IDsfInstrumenter instrumenter) {
		if (instrumenter == null) {
			throw new DsfRuntimeException("instrumenter is nulll");
		}
		m_instrumenter = instrumenter;
	}
	
	public boolean haveInstrumenter() {
		if (m_instrumenter == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean haveContentTracker() {
		if (m_contentTracker == null) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public ContentTracker getContentTracker() {
		return m_contentTracker;
	}
	
	public void setContentTracker(final ContentTracker contentTracker) {
		if (contentTracker == null) {
			throw new DsfRuntimeException("contentTracker is nulll");
		}
		m_contentTracker = contentTracker;		
	}
	
	public void resetContentTracker() {
		if (m_contentTracker != null) {
			m_contentTracker = null;
		}
		
	}
	
	public void resetInstrumenter () {
		if (m_instrumenter != null) {
			m_instrumenter.resetInstrumenter();
			m_instrumenter = null;
		}
	}
	
	private static class CtxAssociator extends ContextHelper {
		private static final String CTX_NAME = TraceCtx.class.getSimpleName();
		protected static TraceCtx getCtx() {
			return (TraceCtx)getSubCtx(DsfCtx.ctx(), CTX_NAME);
		}
		
		protected static void setCtx(final TraceCtx ctx) {
			setSubCtx(DsfCtx.ctx(), CTX_NAME, ctx);
		}
	}

}
