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

import org.ebayopensource.dsf.common.trace.ITraceData;
import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceEvent;
import org.ebayopensource.dsf.common.trace.event.TraceId;
import org.ebayopensource.dsf.common.trace.event.TraceType;
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;
import org.ebayopensource.dsf.common.trace.listener.ListenerId;

public class TranslateConsoleListener implements ITraceEventListener {
	
	private static final ListenerId ID = 
		new ListenerId(TranslateConsoleListener.class.getSimpleName());
	
	/**
	 * Answer the id of this listener
	 * @return ListenerId
	 */
	public ListenerId getId(){
		return ID;
	}

	/**
	 * Answer whether this listener is interested in events with
	 * given type and id.
	 * @param id TraceEventId
	 * @param type TraceEventType
	 */
	public boolean isApplicable(TraceEvent event){
		return true;
	}

	/**
	 * Called before trace event. 
	 * @param event TraceEvent. 
	 * @exception DsfRuntimeException if event is null
	 */	
	public void beforeTrace(TraceEvent event){
		
	}
	
	/**
	 * Called to trace event. 
	 * @param event TraceEvent. 
	 * @exception DsfRuntimeException if event is null
	 */	
	public void trace(TraceEvent event){
		if (event == null){
			return;
		}
		
		TraceType type = event.getType();
		if (type == TranslateTraceType.START_GROUP){
			startGroup(event);
		}
		else if (type == TranslateTraceType.END_GROUP){
			endGroup(event);
		}
		else if (type == TranslateTraceType.ERRORS){
			traceError(event);
		}
		else if (type == TranslateTraceType.TIME){
			traceTime(event);
		}
	}

	/**
	 * Called after trace event. 
	 * @param event TraceEvent. 
	 * @exception DsfRuntimeException if event is null
	 */	
	public void afterTrace(TraceEvent event){
		
	}

	public void close(){
		
	}
	
	//
	// Private
	//
	//
	// Private
	//
	private void startGroup(final TraceEvent event){
		TraceId id = event.getId();
		if ("DependencyPhase".equals(id.getName()) 
				|| "DeclarationPhase".equals(id.getName())
				|| "ImplementationPhase".equals(id.getName())){
			out("***** " + id.getName() + " *****");
		}
		else if ("Task".equals(id.getName())){
			TraceAttr attr;
			ITraceData d = event.getData()[0];
			if (d instanceof TraceAttr){
				attr = (TraceAttr)d;
				out("Task: " + attr.getValue());
			}
		}
	}
	
	private void endGroup(final TraceEvent event){
	}
	
	private void traceError(final TraceEvent event){
		writeData(event.getData());
	}
	
	private void traceTime(final TraceEvent event){
	}
	
	private static final String TAB = "    ";
	private static final String NEWLINE = "\n";
	private static final String START = "start";
	private static final String END = "end";
	private static final String DURATION = "duration";
	private static final String ERR_SEVERITY = "severity";
	private static final String ERR_TYPE = "type";
	private static final String SRC_NAME = "srcName";
	private static final String SRC_LINE_NO = "srcLineNo";
	private static final String MSG = "msg";
	private void writeData(final ITraceData[] data){
		if (data == null || data.length == 0){
			return;
		}
		
		TraceAttr attr;
		for (ITraceData d: data){
			if (d instanceof TraceAttr){
				attr = (TraceAttr)d;
				out(attr.getName() + "=" + attr.getValue() + ";");
			}
			else if (d instanceof TraceErrors){
				TraceErrors t = (TraceErrors)d;
				List<TranslateError> errors = t.getErrors();
				if (errors != null && !errors.isEmpty()){
					StringBuffer sb;
					for (TranslateError e: errors){
						sb = new StringBuffer();
						sb.append(TAB + e.getLevel().name() + ": ");
						if (e.getMsgId() != null){
							sb.append(e.getMsgId() + "; ");
						}
						if (e.getMsg() != null){
							sb.append(e.getMsg() + "; ");
						}
						if (e.getSrcName() != null){
							sb.append(e.getSrcName() + "; ");
						}
						if (e.getSrcLineNo() > 0){
							sb.append(String.valueOf(e.getSrcLineNo()) + ";");
						}
						out(sb.toString());
					}
				}
			}
		}
	}
	
	private void out(String s) {
		System.out.println("Trace==> " + s);	// KEEPME
	}
}
