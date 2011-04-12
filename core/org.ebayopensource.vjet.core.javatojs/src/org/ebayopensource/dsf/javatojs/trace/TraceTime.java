/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ebayopensource.dsf.common.trace.ITraceData;

public class TraceTime implements ITraceData {
	
	static final TraceTime NO_OP_TIMER = new NoOpTimer();
	
	private static String TIME_FORMAT = "HH:mm:ss:SSS";
	private static String NULL = "null";
	
	private Date m_start;
	private Date m_end;
	
	TraceTime(){}
	
	public void start(){
		m_start = new Date();
	}
	
	public void end(){
		m_end = new Date();
	}
	
	public String getStartTime(){
		if (m_start == null){
			return NULL;
		}
		return formatTime(m_start);
	}
	
	public String getEndTime(){
		if (m_end == null){
			return NULL;
		}
		return formatTime(m_end);
	}
	
	public String getDuration(){
		if (m_end == null || m_start == null){
			return NULL;
		}
		return String.valueOf(m_end.getTime() - m_start.getTime());
	}
	
	private static String formatTime(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
		return formatter.format(date);
	}
	
	//
	// Inner
	//
	private static class NoOpTimer extends TraceTime {
		public void start(){
			// No Op
		}
		
		public void end(){
			// No Op
		}
		
		public String getStartTime(){
			return null;
		}
		
		public String getEndTime(){
			return null;
		}
		
		public String getDuration(){
			return null;
		}
	}
}
