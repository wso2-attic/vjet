/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.config;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.introspect.ITraceObjectIntrospector;
import org.ebayopensource.dsf.common.Z;

public final class IntrospectorKey {

	public static final String DELIMITER = ":";
	
	private Class<? extends ITraceObjectIntrospector> m_type;
	private Class m_targetType;
	
	//
	// Constructor
	//
	public IntrospectorKey(final Class<? extends ITraceObjectIntrospector> type, final Class targetType){
		if (type == null){
			DsfExceptionHelper.chuck("type is null");
		}
		if (type == ITraceObjectIntrospector.class){
			DsfExceptionHelper.chuck("type is an interface");
		}
		if (targetType == null){
			DsfExceptionHelper.chuck("targetType is null");
		}
		m_type = type;
		m_targetType = targetType;
	}
	
	//
	// API
	//
	public Class<? extends ITraceObjectIntrospector> getType(){
		return m_type;
	}
	
	public Class getTargetType(){
		return m_targetType;
	}
	
	@SuppressWarnings("unchecked")
	public static IntrospectorKey parse(final String value){
		
		if (value == null || value.length() == 0){
			return null;
		}
		
		int index = value.indexOf(DELIMITER);
		if (index < 0){
			return null;
		}
		
		String typeName = value.substring(0, index).trim();
		String targetName = value.substring(index+1).trim();
		if (typeName.length() == 0 || targetName.length() == 0){
			return null;
		}
		
		Class<? extends ITraceObjectIntrospector> inspectorType;
		try {
			inspectorType = (Class<? extends ITraceObjectIntrospector>)Class.forName(typeName);
		} 
		catch (ClassNotFoundException e) {
			return null;
		} 
		
		if (inspectorType == ITraceObjectIntrospector.class){
			return null;
		}
		
		Class targetType;
		try {
			targetType = Class.forName(targetName);
		} 
		catch (ClassNotFoundException e) {
			return null;
		} 
		
		return new IntrospectorKey(inspectorType, targetType);
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || obj.getClass() != IntrospectorKey.class){
			return false;
		}
		
		IntrospectorKey that = (IntrospectorKey)obj;
		return m_type == that.m_type && m_targetType == that.m_targetType;
	}
	
	@Override
	public int hashCode(){
		return m_type.hashCode() + m_targetType.hashCode();
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("type", m_type);
		z.format("targetType", m_targetType);
		
		return z.toString();
	}
}
