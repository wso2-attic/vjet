/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import java.io.StringWriter;
import java.util.List;

import org.ebayopensource.dsf.common.trace.ITraceData;
import org.ebayopensource.dsf.common.trace.TraceAttr;
import org.ebayopensource.dsf.common.trace.event.TraceEvent;
import org.ebayopensource.dsf.common.trace.event.TraceType;
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;
import org.ebayopensource.dsf.common.trace.listener.ListenerId;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.common.xml.XmlStreamWriter;

public class TranslateTraceListener implements ITraceEventListener {
	
	private static final ListenerId ID = 
		new ListenerId(TranslateTraceListener.class.getSimpleName());
	
	private XmlStreamWriter m_xmlWriter;
	
	public TranslateTraceListener(StringWriter writer){
		m_xmlWriter = new XmlStreamWriter(writer,IIndenter.COMPACT);
//		addHandler(new DefaultTraceEventHandler(new HandlerId("TranslateTraceHandler"), m_xmlWriter));
	}

	public TranslateTraceListener(XmlStreamWriter writer){
		m_xmlWriter = writer;
//		addHandler(new DefaultTraceEventHandler(new HandlerId("TranslateTraceHandler"), m_xmlWriter));
	}

	//
	// Satisfy ITraceEventListener
	//
	public ListenerId getId(){
		return ID;
	}
	
	public boolean isApplicable(TraceEvent event) {
		return true;
	}
	
	public void beforeTrace(final TraceEvent event) {
		// No op
	}
	
	public void trace(final TraceEvent event){
		
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
		else {
//			super.trace(event);
		}
	}
	
	public void afterTrace(final TraceEvent event) {
		// No op
	}
	
	public void close(){
		m_xmlWriter.flush();
	}
	
	//
	// Private
	//
	private void startGroup(final TraceEvent event){
		m_xmlWriter.writeStartElement(event.getId().getName());
		writeData(event.getData());
	}
	
	private void endGroup(final TraceEvent event){
		m_xmlWriter.writeEndElement();
	}
	
	private void traceError(final TraceEvent event){
		m_xmlWriter.writeStartElement(event.getType().name());
		writeData(event.getData());
		m_xmlWriter.writeEndElement();
	}
	
	private void traceTime(final TraceEvent event){
		m_xmlWriter.writeStartElement(event.getType().name());
		writeData(event.getData());
		m_xmlWriter.writeEndElement();
	}
	
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
				m_xmlWriter.writeAttribute(attr.getName(), attr.getValue());
			}
			else if (d instanceof TraceErrors){
				TraceErrors t = (TraceErrors)d;
				List<TranslateError> errors = t.getErrors();
				if (errors != null && !errors.isEmpty()){
					for (TranslateError e: errors){
						m_xmlWriter.writeStartElement(e.getLevel().name());
						if (e.getMsgId() != null){
							m_xmlWriter.writeAttribute(ERR_TYPE, e.getMsgId());
						}
						if (e.getMsg() != null){
							m_xmlWriter.writeAttribute(MSG, e.getMsg());
						}
						if (e.getSrcName() != null){
							m_xmlWriter.writeAttribute(SRC_NAME, e.getSrcName());
						}
						if (e.getSrcLineNo() > 0){
							m_xmlWriter.writeAttribute(SRC_LINE_NO, String.valueOf(e.getSrcLineNo()));
						}
						m_xmlWriter.writeEndElement();
					}
				}
			}
			else if (d instanceof TraceTime){
				TraceTime t = (TraceTime)d;
				m_xmlWriter.writeAttribute(START, t.getStartTime());
				m_xmlWriter.writeAttribute(END, t.getEndTime());
				m_xmlWriter.writeAttribute(DURATION, t.getDuration());
			}
		}
	}
}
