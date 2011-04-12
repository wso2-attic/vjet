/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.event.TraceType;

public final class TraceTypeMapping implements Cloneable {
	
	public static final TraceTypeMapping DEFAULT = new TraceTypeMapping();
	
	static {
		DEFAULT.setMapping(TraceType.ENTER_METHOD, Level.FINE);
		DEFAULT.setMapping(TraceType.EXIT_METHOD, Level.FINE);
//		DEFAULT.setMapping(TraceType.MSG, Level.FINE);
		
		DEFAULT.setMapping(TraceType.OBJECT_TYPE, Level.FINER);
		DEFAULT.setMapping(TraceType.OBJECT_STATE, Level.FINER);
		
//		DEFAULT.setMapping(TraceType.START_CALL, Level.FINER);
//		DEFAULT.setMapping(TraceType.END_CALL, Level.FINER);
//		
//		DEFAULT.setMapping(TraceType.START_LOOP, Level.FINEST);
//		DEFAULT.setMapping(TraceType.END_LOOP, Level.FINEST);
	}

	private final Map<TraceType,Level> m_levelMap = new HashMap<TraceType,Level>(10);
	
	@Override
	public TraceTypeMapping clone(){
		TraceTypeMapping copy = new TraceTypeMapping();
		
		for (Map.Entry<TraceType,Level> entry: m_levelMap.entrySet()){
			copy.setMapping(entry.getKey(), entry.getValue());
		}
		
		return copy;
	}
	
	//
	// API
	//
	public void setMapping(final TraceType type, final Level level){
		
		if (type == null){
			DsfExceptionHelper.chuck("type is null");
		}
		if (level == null){
			DsfExceptionHelper.chuck("level is null");
		}
		m_levelMap.put(type, level);
	}
	
	public Level getLevel(final TraceType type){
		
		if (type == null){
			DsfExceptionHelper.chuck("type is null");
		}
		
		return m_levelMap.get(type);
	}
}
