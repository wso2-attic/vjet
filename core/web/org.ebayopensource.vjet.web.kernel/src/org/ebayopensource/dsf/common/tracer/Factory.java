/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.tracer;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.config.FilterKey;
import org.ebayopensource.dsf.common.trace.config.HandlerKey;
import org.ebayopensource.dsf.common.trace.config.IntrospectorKey;
import org.ebayopensource.dsf.common.trace.config.ListenerKey;
import org.ebayopensource.dsf.common.trace.filter.FilterId;
import org.ebayopensource.dsf.common.trace.filter.ITraceEventFilter;
import org.ebayopensource.dsf.common.trace.handler.HandlerId;
import org.ebayopensource.dsf.common.trace.handler.ITraceEventHandler;
import org.ebayopensource.dsf.common.trace.handler.TraceFileHandler;
import org.ebayopensource.dsf.common.trace.introspect.ITraceObjectIntrospector;
import org.ebayopensource.dsf.common.trace.listener.ITraceEventListener;
import org.ebayopensource.dsf.common.trace.listener.ListenerId;

public class Factory {
	
	public static ITraceEventListener createListener(final ListenerKey key){
		
		if (key == null){
			DsfExceptionHelper.chuck("key is null");
		}
		
		Class<? extends ITraceEventListener> type = key.getType();
		ITraceEventListener listener = null;

		Constructor<? extends ITraceEventListener> c = null;
		try {
			c = type.getConstructor(ListenerId.class);
		} 
		catch (SecurityException e) {
			DsfExceptionHelper.chuck(
				"SecurityException when getting constructor for: " + type.getName());
		} 
		catch (NoSuchMethodException e) {
			DsfExceptionHelper.chuck(
				"NoSuchMethodException when getting constructor for: " + type.getName());
		}
		
		try {
			listener = c.newInstance(key.getId());
		} 
		catch (IllegalArgumentException e) {
			DsfExceptionHelper.chuck(
				"IllegalArgumentException when instantiate: " + type.getName());
		} 
		catch (InstantiationException e) {
			DsfExceptionHelper.chuck(
				"InstantiationException when instantiate: " + type.getName());
		} 
		catch (IllegalAccessException e) {
			DsfExceptionHelper.chuck(
				"IllegalAccessException when instantiate: " + type.getName());
		} 
		catch (InvocationTargetException e) {
			DsfExceptionHelper.chuck(
				"InvocationTargetException when instantiate: " + type.getName());
		}

		return listener;
	}
	
	public static ITraceEventFilter createFilter(final FilterKey key){
		
		if (key == null){
			DsfExceptionHelper.chuck("key is null");
		}
		
		Class<? extends ITraceEventFilter> type = key.getType();
		ITraceEventFilter filter = null;

		Constructor<? extends ITraceEventFilter> c = null;
		try {
			c = type.getConstructor(FilterId.class);
		} 
		catch (SecurityException e) {
			DsfExceptionHelper.chuck(
				"SecurityException when getting constructor for: " + type.getName());
		} 
		catch (NoSuchMethodException e) {
			DsfExceptionHelper.chuck(
				"NoSuchMethodException when getting constructor for: " + type.getName());
		}
		
		try {
			filter = c.newInstance(key.getId());
		} 
		catch (IllegalArgumentException e) {
			DsfExceptionHelper.chuck(
				"IllegalArgumentException when instantiate: " + type.getName());
		} 
		catch (InstantiationException e) {
			DsfExceptionHelper.chuck(
				"InstantiationException when instantiate: " + type.getName());
		} 
		catch (IllegalAccessException e) {
			DsfExceptionHelper.chuck(
				"IllegalAccessException when instantiate: " + type.getName());
		} 
		catch (InvocationTargetException e) {
			DsfExceptionHelper.chuck(
				"InvocationTargetException when instantiate: " + type.getName());
		}

		return filter;
	}
	
	public static ITraceEventHandler createHandler(final HandlerKey key){
		
		if (key == null){
			DsfExceptionHelper.chuck("key is null");
		}
		
		Class<? extends ITraceEventHandler> type = key.getType();
		ITraceEventHandler handler = null;

		Constructor<? extends ITraceEventHandler> c = null;
		try {
			c = type.getConstructor(HandlerId.class);
		} 
		catch (SecurityException e) {
			DsfExceptionHelper.chuck(
				"SecurityException when getting constructor for: " + type.getName());
		} 
		catch (NoSuchMethodException e) {
			DsfExceptionHelper.chuck(
				"NoSuchMethodException when getting constructor for: " + type.getName());
		}
		
		try {
			handler = c.newInstance(key.getId());
		} 
		catch (IllegalArgumentException e) {
			DsfExceptionHelper.chuck(
				"IllegalArgumentException when instantiate: " + type.getName());
		} 
		catch (InstantiationException e) {
			DsfExceptionHelper.chuck(
				"InstantiationException when instantiate: " + type.getName());
		} 
		catch (IllegalAccessException e) {
			DsfExceptionHelper.chuck(
				"IllegalAccessException when instantiate: " + type.getName());
		} 
		catch (InvocationTargetException e) {
			DsfExceptionHelper.chuck(
				"InvocationTargetException when instantiate: " + type.getName());
		}

		return handler;
	}
	
	public static ITraceObjectIntrospector createInspector(final IntrospectorKey key){
		
		if (key == null){
			DsfExceptionHelper.chuck("key is null");
		}
		
		Class<? extends ITraceObjectIntrospector> type = key.getType();
		
		ITraceObjectIntrospector inspector = null;

		try {
			inspector = type.newInstance();
		} 
		catch (IllegalAccessException e) {
			DsfExceptionHelper.chuck(
				"IllegalAccessException when instantiate: " + type.getName());
		} 
		catch (InstantiationException e) {
			DsfExceptionHelper.chuck(
				"InstantiationException when instantiate: " + type.getName());
		}

		return inspector;
	}
	
	private static final String FILE_PREFIX = "v4trace";
	private static final String FILE_EXTENSION = ".xml";
	private static final int FILE_MAX_SIZE = 10000000;
	private static final int FILE_MAX_COUT = 10;
	
	private static List<String> s_initializedLoggers = new ArrayList<String>(5);
	
	public static Logger createDefaultLogger(final String scope, Formatter formatter){
		return createDefaultLogger(scope, formatter, FILE_MAX_COUT);
	}
	
	public static Logger createDefaultLogger(final String scope, Formatter formatter, int maxFileCount){

		String fileName = FILE_PREFIX + FILE_EXTENSION;

		Logger logger = Logger.getLogger(scope);
		synchronized (Factory.class){
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
			fileHandler.setFilter(new TraceFilter());
			logger.addHandler(fileHandler);
		} 
		catch (SecurityException e) {
			// TODO Auto-generated catch block
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		synchronized (Factory.class){
			s_initializedLoggers.add(scope);
		}
		
		return logger;
	}

	/**
	 * A log filter class to accept trace logs
	 */
	static class TraceFilter implements Filter {
		/**
		 * Check if a given log record should be published.
		 * @param record  a LogRecord
		 * @return true if the log record should be published.
		 */
		public boolean isLoggable(LogRecord record) {
			if (TraceManager.class.getName().equals(record.getLoggerName())) {
				return true;
			}
			else return false;
		}
	}
}
