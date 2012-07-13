/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.xml.XmlEncoder;

/**
 * Default implementation for IDsfTracer
 */
public class DefaultTracer implements IDsfTracer {
	
	private List<ITraceWriter> m_writers = new ArrayList<ITraceWriter>(1);
	private int m_traceDepth = -1;
	private boolean m_traceEnabled = false;
	private ListOrderedMap m_stackLabels = new ListOrderedMap();
	
	private static final String DOT = ".";
	
	//
	// Constructor
	//
	public DefaultTracer(){
		reset();	
	}
	
	//
	// Satisfying IDsfTracer
	//
	public void enableTrace(final boolean enable){
		m_traceEnabled = enable;
	}
	
	public boolean isEnabled(){
		return m_traceEnabled;
	}
	
	public IDsfTracer addWriter(final ITraceWriter handler){
		if (handler == null){
			DsfExceptionHelper.chuck("handler is null");
		}
		m_writers.add(handler);
		return this;
	}
	
	public void enterMethod(final Object caller){
		
		if (!m_traceEnabled){
			return;
		}
		
		if (caller == null){
			return;
		}
		
		final Throwable t = new Throwable();
		t.fillInStackTrace();
		
		final String clsName = getClassName(caller);
		final String methodName = getMethodName(caller, t);
		
		push(clsName + DOT + methodName);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleEnterMethod(m_traceDepth, clsName, methodName);
		}}) ;
	}
	public void enterMethod(final Object caller, final String msg){
		
		if (!m_traceEnabled){
			return;
		}
		
		if (caller == null){
			return;
		}
		
		final Throwable t = new Throwable();
		t.fillInStackTrace();
		
		final String clsName = getClassName(caller);
		final String methodName = getMethodName(caller, t);
		
		push(clsName + DOT + methodName);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleEnterMethod(m_traceDepth, clsName, methodName, msg);
		}}) ;
	}
	
	public void exitMethod(final Object caller){
		
		if (!m_traceEnabled){
			return;
		}
		
		if (caller == null){
			return;
		}
		
		final Throwable t = new Throwable();
		t.fillInStackTrace();
		
		final String clsName = getClassName(caller);
		final String methodName = getMethodName(caller, t);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleExitMethod(m_traceDepth, clsName, methodName);
		}}) ;
		
		pop(clsName + DOT + methodName);
	}
	
	public void exitMethod(final Object caller, final ExitStatus status){
		
		if (!m_traceEnabled){
			return;
		}
		
		if (caller == null){
			return;
		}
		
		final Throwable t = new Throwable();
		t.fillInStackTrace();
		
		final String clsName = getClassName(caller);
		final String methodName = getMethodName(caller, t);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleExitMethod(m_traceDepth, clsName, methodName, status);
		}}) ;
		
		pop(clsName + DOT + methodName);
	}
	public void exitMethod(final Object caller, final String msg){
		
		if (!m_traceEnabled){
			return;
		}
		
		if (caller == null){
			return;
		}
		
		final Throwable t = new Throwable();
		t.fillInStackTrace();
		
		final String clsName = getClassName(caller);
		final String methodName = getMethodName(caller, t);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleExitMethod(m_traceDepth, clsName, methodName, msg);
		}}) ;
		
		pop(clsName + DOT + methodName);
	}
	public void exitMethod(
		final Object caller, final ExitStatus status, final String msg)
	{	
		if (!m_traceEnabled){
			return;
		}
		
		if (caller == null){
			return;
		}
		
		final Throwable t = new Throwable();
		t.fillInStackTrace();
		
		final String clsName = getClassName(caller);
		final String methodName = getMethodName(caller, t);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleExitMethod(m_traceDepth, clsName, methodName, status, msg);
		}}) ;

		pop(clsName + DOT + methodName);
	}
	
	public void startCall(final Object callee, final String method){
		
		if (!m_traceEnabled){
			return;
		}
		
		if (callee == null){
			return;
		}
		
		final String clsName = callee.getClass().getName();
		
		push(clsName + DOT + method);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleStartCall(m_traceDepth, clsName, method);
		}}) ;
	}
	
	public void startCall(
		final Object callee, final String method, final String msg)
	{	
		if (!m_traceEnabled){
			return;
		}
		
		if (callee == null){
			return;
		}
		
		final String clsName = callee.getClass().getName();
		
		push(clsName + DOT + method);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleStartCall(m_traceDepth, clsName, method, msg);
		}}) ;
	}
	
	public void endCall(
		final Object callee, final String method, final String msg)
	{
		if (!m_traceEnabled){
			return;
		}
		
		if (callee == null){
			return;
		}
		
		final String clsName = callee.getClass().getName();
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleEndCall(m_traceDepth, clsName, method, msg);
		}}) ;
		
		pop(clsName + DOT + method);
	}
	
	public void endCall(final Object callee, final String method){		
		if (!m_traceEnabled){
			return;
		}
		
		if (callee == null){
			return;
		}
		
		final String clsName = callee.getClass().getName();
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleEndCall(m_traceDepth, clsName, method);
		}}) ;
		
		pop(clsName + DOT + method);
	}
	
	public void startLoop(final String group){
		if (!m_traceEnabled){
			return;
		}
		
		push(group);
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleStartLoop(m_traceDepth, group);
		}}) ;
	}
	
	public void loopStep(final String msg){
		if (!m_traceEnabled){
			return;
		}
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleLoopStep(m_traceDepth, msg);
		}}) ;
	}
	
	public void endLoop(final String group){
		if (!m_traceEnabled){
			return;
		}
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleEndLoop(m_traceDepth, group);
		}}) ;
		
		pop(group);
	}
	
	public void msg(final String msg){
		
		if (!m_traceEnabled){
			return;
		}
		
		dispatch(new WriterInvoker() { public void process(ITraceWriter w) { 
			w.handleMsg(m_traceDepth, msg);
		}}) ;
	}
	
	public void reset(){
		m_traceDepth = -1;
		m_writers.clear();
		
		TraceConfig cfg = TraceConfig.getInstance();
		m_writers = new ArrayList<ITraceWriter>(cfg.getWriters());
		m_traceEnabled = cfg.isEnabled();
		if (m_traceEnabled && m_writers.size() == 0){
			m_writers.add(new ConsoleTraceWriter());
		}
	}
	
	//
	// Private
	//
	private String getClassName(final Object caller) {
		String name = caller.getClass().getName();
		int start = name.lastIndexOf('.');
		if (start != -1) {
			name = name.substring(start+1);
		}
		return name;
	}
	
	private String getMethodName(final Object caller, final Throwable t){
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		t.printStackTrace(new PrintStream(stream));

		Class cls = caller.getClass();
		String clsName = cls.getName();
		String stackTrace = stream.toString();
		int index = stackTrace.indexOf(clsName);
		while (index < 1){
			cls = cls.getSuperclass();
			if (cls == null){
				return "NotFound";
			}
			clsName = cls.getName();
			index = stackTrace.indexOf(clsName);;
		}
		final int start = stackTrace.indexOf(clsName) + clsName.length() + 1;
		final int end = stackTrace.indexOf("(", start);
		return XmlEncoder.encode(stackTrace.substring(start, end));
	}
	
	private void push(final String label){
		if (label == null || label.trim().length() == 0){
			DsfExceptionHelper.chuck("label is null");
		}
		m_traceDepth++;
		m_stackLabels.put(Integer.valueOf(m_traceDepth), label);
	}
	
	private void pop(final String label){
		if (m_stackLabels.get(m_stackLabels.lastKey()).equals(label)){
			m_traceDepth--;
			m_stackLabels.remove(m_stackLabels.lastKey());
		}
		else if (m_traceDepth > 0){
			m_traceDepth--;
			m_stackLabels.remove(m_stackLabels.lastKey());
			pop(label);
		}
	}
	
	private void dispatch(final WriterInvoker wi) {
		final Iterator itr = m_writers.iterator() ;
		while(itr.hasNext()) {
			ITraceWriter w = (ITraceWriter)itr.next() ;
			wi.process(w) ;
		}
	}
	
	//
	// Helper classes
	//
	static interface WriterInvoker {
		void process(ITraceWriter writer) ;
	}
}
