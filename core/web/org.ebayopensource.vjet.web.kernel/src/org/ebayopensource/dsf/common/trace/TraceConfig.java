/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.ebayopensource.dsf.common.resource.ResourceUtil;

public class TraceConfig {
	
	private static final String TRACE_PROPERTY = "trace.properties";
	private static final String PTY_ENABLED = "enabled";
	private static final String PTY_WRITERS = "writers";
	
	private static volatile TraceConfig s_instance;
	
	private boolean m_enabled = false;
	private List<ITraceWriter> m_writers = new ArrayList<ITraceWriter>(1);
	
	private TraceConfig(){
		init();
	};
	
	//
	// Singleton
	//
	public static TraceConfig getInstance(){
		if (s_instance != null){
			return s_instance;
		}
		
		synchronized (TraceConfig.class){
			if (s_instance == null){
				s_instance = new TraceConfig();
			}
		}
		return s_instance;
	}
	
	//
	// API
	//
	public boolean isEnabled(){
		return m_enabled;
	}
	
	public List<ITraceWriter> getWriters(){
		return Collections.unmodifiableList(m_writers);
	}
	
	//
	// Private
	//
	private void init(){
		try {
			final URL url = ResourceUtil.getResource(DefaultTracer.class, TRACE_PROPERTY);
			if (url == null) {
				throw new IOException("Error loading config " + TRACE_PROPERTY);
			}
			final InputStream inputStream = url.openStream();	
			Properties props = new Properties();
			props.load(inputStream);
			
			// Enabled or not
			String enabled = props.getProperty(PTY_ENABLED);
			if (enabled != null && enabled.compareToIgnoreCase("true") == 0){
				m_enabled = true;
			}
			
			// Create writers if any
			initializeWriters(parseClassNames(props, PTY_WRITERS));
			
			// Disable if no writers
			if (this.m_writers.size() == 0){
				m_enabled = false;
			}
		}
		catch(Exception e){
		}
	}
	
	// Temp copied from LogManger
	private String[] parseClassNames(
		final Properties props, final String propertyName)
	{	
		String hands = props.getProperty(propertyName);
		if (hands == null) {
			return new String[0];
		}

		hands = hands.trim();

		int ix = 0;
		ArrayList<String> result = new ArrayList<String>();

		while (ix < hands.length()) {
			int end = ix;

			while (end < hands.length()) {
				if (Character.isWhitespace(hands.charAt(end))) {
					break;
				}

				if (hands.charAt(end) == ',') {
					break;
				}

				end++;
			}

			String word = hands.substring(ix, end);
			ix = end + 1;
			word = word.trim();

			if (word.length() == 0) {
				continue;
			}

			result.add(word);
		}

		return result.toArray(new String[result.size()]);
	}
	
	private void initializeWriters(final String[] classNames) {
		for (int i = 0; i < classNames.length; i++) {
			final String className = classNames[i];

			try {
				final Class clz = Class.forName(className);
				final ITraceWriter w = (ITraceWriter)clz.newInstance();
				m_writers.add(w);
			} 
			catch (Exception ex) {
				// TODO?
			}
		}
	}
}
