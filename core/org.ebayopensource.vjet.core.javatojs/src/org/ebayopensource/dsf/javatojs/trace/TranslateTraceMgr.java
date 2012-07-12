/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ebayopensource.dsf.common.trace.handler.TraceFileHandler;
import org.ebayopensource.dsf.common.trace.handler.TraceFormatter;
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.common.xml.XmlStreamWriter;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;

public class TranslateTraceMgr {
	
	private TranslateCtx m_ctx;
	private ITranslateTracer m_tracer;
	private List<ITraceEventListener> m_listeners = new ArrayList<ITraceEventListener>();
	private XmlStreamWriter m_writer = new XmlStreamWriter(new StringWriter(),IIndenter.COMPACT);
	private static Logger s_logger;
	
	//
	// COnstructor
	//
	public TranslateTraceMgr(TranslateCtx ctx){
		m_ctx = ctx;
		
	}
	
	//
	// API
	//
	public TranslateTraceMgr addTraceListener(ITraceEventListener listener){
		if (listener == null || m_listeners.contains(listener)){
			return this;
		}
		m_listeners.add(listener);
		return this;
	}
	
	public void setTracer(ITranslateTracer tracer){
		m_tracer = tracer;
	}
	
	public ITranslateTracer getTracer(){
		if (m_ctx.isTraceEnabled()){
			return getTracer(true);
		}
		else {
			return TranslateTracer.NO_OP;
		}
	}
	
	public ITranslateTracer getTracer(StringWriter writer){
		if (m_ctx.isTraceEnabled()){
			return createTracer(new XmlStreamWriter(writer));
		}
		else {
			return TranslateTracer.NO_OP;
		}
	}
	
	public TraceTime getTimer(){
		if (m_ctx.isTraceEnabled()){
			return new TraceTime();
		}
		else {
			return TraceTime.NO_OP_TIMER;
		}
	}
	
	public void close(){
//		System.out.println("TranslateTraceMgr::close()");
		if (m_tracer == null){
			return;
		}
		
		m_writer.writeEndElement();
		
		m_tracer.close();

		String xml = m_writer.toString();
//		System.out.println("TranslateTraceMgr: xml=" + xml);
		getLogger().log(Level.INFO, xml);
		for (Handler h: getLogger().getHandlers()){
			try {
				h.close();
			} catch (SecurityException e) {
				e.printStackTrace(); //KEEPME
			}
		}
		
		m_tracer = null;
//		System.out.println("TranslateTraceMgr::close() done");
	}
	
	public void append(final String trace){
		m_writer.writeRaw(trace);
	}
	
	//
	// Private
	//
	private ITranslateTracer getTracer(boolean create){
		if (m_tracer == null && create){
			m_tracer = createTracer(m_writer);
			m_writer.writeStartElement("JavaToJst");
		}
		return m_tracer;
	}
	
	private ITranslateTracer createTracer(XmlStreamWriter writer){
		ITranslateTracer tracer = new TranslateTracer();
		tracer.addListener(new TranslateTraceListener(writer));
		for (ITraceEventListener l: m_listeners){
			tracer.addListener(l);
		}
		return tracer;
	}
	
	public Logger getLogger(){
		if (s_logger == null){
			try {
				s_logger = createDefaultLogger(
						"org.ebayopensource.dsf.common.tracer.TraceManager", 
						new TraceFormatter(),
						10);
			} catch (Throwable t) {
				t.printStackTrace();	//KEEPME
			}
		}
		return s_logger;
	}
	
	private static final String FILE_PREFIX = "v4trace";
	private static final String FILE_EXTENSION = ".xml";
	private static final int FILE_MAX_SIZE = 10000000;
	private static final int FILE_MAX_COUT = 10;
	private static List<String> s_initializedLoggers = new ArrayList<String>(5);
	
	private static Logger createDefaultLogger(final String scope, Formatter formatter, int maxFileCount){

		String fileName = FILE_PREFIX + FILE_EXTENSION;

		Logger logger = Logger.getLogger(scope);
		synchronized (TranslateTraceMgr.class){
			if (s_initializedLoggers.contains(scope)){
				return logger;
			}
		}
		
		// Initialize
		logger.setLevel(Level.INFO);
		
		for (Handler h: logger.getHandlers()){
			if (h instanceof ConsoleHandler){
				logger.removeHandler(h);
			}
		}
		
		try {
			Handler fileHandler = new TraceFileHandler(
				fileName, 
				FILE_MAX_SIZE, 
				maxFileCount, 
				true);
			
			fileHandler.setFormatter(formatter);
			fileHandler.setLevel(Level.ALL);
//			fileHandler.setFilter(new TraceFilter());
			logger.addHandler(fileHandler);
		} 
		catch (SecurityException e) {
			// TODO Auto-generated catch block
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		synchronized (TranslateTraceMgr.class){
			s_initializedLoggers.add(scope);
		}
		
		return logger;
	}
}
