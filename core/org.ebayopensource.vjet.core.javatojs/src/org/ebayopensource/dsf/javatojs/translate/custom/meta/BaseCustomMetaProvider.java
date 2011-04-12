/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * Base implementation for ICustomMetaProvider
 * Please note that this class is NOT THREAD-SAFE.
 */
public abstract class BaseCustomMetaProvider implements ICustomMetaProvider {

	private Map<String,CustomType> m_customTypes = 
		new HashMap<String,CustomType>();
	
	private Map<String,IPrivilegedProcessor> m_processors = 
		new HashMap<String,IPrivilegedProcessor>();
	
	private Map<String,IPrivilegedProcessor> m_ctrProcessors = 
		new HashMap<String,IPrivilegedProcessor>();
	
	private Map<String,Map<String,IPrivilegedProcessor>> m_mtdProcessors = 
		new HashMap<String,Map<String,IPrivilegedProcessor>>();
	
	private Map<String,Map<String,IPrivilegedProcessor>> m_fldProcessors = 
		new HashMap<String,Map<String,IPrivilegedProcessor>>();
	
	//
	// Satisfy ICustomMetaProvider
	//
	/**
	 * @see ICustomMetaProvider#getCustomType(String)
	 */
	public CustomType getCustomType(final String javaTypeName){
		return m_customTypes.get(javaTypeName);
	}
	
	/**
	 * @see ICustomMetaProvider#getPrivilegedTypeProcessor(String)
	 */
	public IPrivilegedProcessor getPrivilegedTypeProcessor(String typeName){
		return m_processors.get(typeName);
	}
	
	/**
	 * @see ICustomMetaProvider#getPrivilegedConstructorProcessor(String)
	 */
	public IPrivilegedProcessor getPrivilegedConstructorProcessor(String typeName){
		return m_ctrProcessors.get(typeName);
	}
	
	/**
	 * @see ICustomMetaProvider#getPrivilegedMethodProcessor(String, String)
	 */
	public IPrivilegedProcessor getPrivilegedMethodProcessor(String typeName, String mtdName){
		Map<String,IPrivilegedProcessor> processors = m_mtdProcessors.get(typeName);
		if (processors != null){
			return processors.get(mtdName);
		}
		return null;
	}
	
	/**
	 * @see ICustomMetaProvider#getPrivilegedFieldProcessor(String, String)
	 */
	public IPrivilegedProcessor getPrivilegedFieldProcessor(String typeName, String fldName){
		Map<String,IPrivilegedProcessor> processors = m_fldProcessors.get(typeName);
		if (processors != null){
			return processors.get(fldName);
		}
		return null;
	}
	
	//
	// API
	//
	/**
	 * Add custom meta for java type with given name
	 * @param typeName String
	 * @paramcType CustomType
	 */
	public void addCustomType(final String typeName, final CustomType cType){
		assert typeName != null : "typeName is null";
		assert cType != null : "cType is null";
		m_customTypes.put(typeName, cType);
	}
	
	/**
	 * Add custom processor for java type with given name
	 * @param typeName String
	 * @param processor IPrivilegedProcessor
	 */
	public void addPrivilegedTypeProcessor(
			final String typeName, final IPrivilegedProcessor processor){
		assert typeName != null : "typeName is null";
		assert processor != null : "processor is null";
		m_processors.put(typeName, processor);
	}
	
	/**
	 * Add custom processor for java type with given name
	 * @param typeName String
	 * @param processor IPrivilegedProcessor
	 */
	public void addPrivilegedConstructorProcessor(
			final String typeName, final IPrivilegedProcessor processor){
		assert typeName != null : "typeName is null";
		assert processor != null : "processor is null";
		m_ctrProcessors.put(typeName, processor);
	}
	
	/**
	 * Add custom processor for method with given java type name and method name
	 * @param typeName String
	 * @param mtdName
	 * @param processor IPrivilegedProcessor
	 */
	public void addPrivilegedMethodProcessor(
			final String typeName, final String mtdName, final IPrivilegedProcessor processor){
		assert typeName != null : "typeName is null";
		assert mtdName != null : "mtdName is null";
		assert processor != null : "processor is null";
		Map<String,IPrivilegedProcessor> mtdProcessors = m_mtdProcessors.get(typeName);
		if (mtdProcessors == null){
			mtdProcessors = new HashMap<String,IPrivilegedProcessor>();
			m_mtdProcessors.put(typeName, mtdProcessors);
		}
		mtdProcessors.put(mtdName, processor);
	}
	
	/**
	 * Add custom processor for field with given java type name and field name
	 * @param typeName String
	 * @param fieldName
	 * @param processor IPrivilegedProcessor
	 */
	public void addPrivilegedFieldProcessor(
			final String typeName, final String fieldName, final IPrivilegedProcessor processor){
		assert typeName != null : "typeName is null";
		assert fieldName != null : "fieldName is null";
		assert processor != null : "processor is null";
		Map<String,IPrivilegedProcessor> fieldProcessors = m_fldProcessors.get(typeName);
		if (fieldProcessors == null){
			fieldProcessors = new HashMap<String,IPrivilegedProcessor>();
			m_fldProcessors.put(typeName, fieldProcessors);
		}
		fieldProcessors.put(fieldName, processor);
	}
}
