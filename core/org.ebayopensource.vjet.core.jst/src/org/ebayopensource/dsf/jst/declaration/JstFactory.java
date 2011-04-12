/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;

public class JstFactory {
	
	private JstCache m_cache = JstCache.getInstance();
	
	//
	// Singleton
	//
	private static JstFactory s_instance = new JstFactory();
	private JstFactory(){}
	public static JstFactory getInstance(){
		return s_instance;
	}
	
	//
	// API
	//
	public synchronized JstType createJstType(boolean cacheIt){
		JstType type = new JstType();
		if (cacheIt){
			m_cache.addType(type);
		}
		return type;
	}
	
	/**
	 * Create JstType instance with given name as full name. Optinally save it in 
	 * JST cache if given cacheIt is true. Please be aware that, if there is already 
	 * an instance of JstType with given name existed in cache and cacheIt is true , 
	 * the existing instance in cache will be replaced with newly created instance.
	 * @param name String
	 * @param cacheIt boolean
	 * @return JstType
	 */
	public synchronized JstType createJstType(final String name, boolean cacheIt){
		JstType type;
		type =  new JstType(name);
		if (cacheIt){
			m_cache.addType(type);
		}
		return type;
	}
	
	public synchronized JstType createJstType(JstPackage pkg, String name, boolean cacheIt){
		JstType type =  new JstType(pkg, name);
		if (cacheIt){
			m_cache.addType(type);
		}
		return type;
	}
	
	@Deprecated
	public JstPackage createPackage(String fullName){
		return new JstPackage(fullName);
	}
	
	@Deprecated
	public JstRefType createJstRefType(final Class refType, final String name, final boolean primitive, final boolean isArray){
		JstRefType type = new JstRefType(refType, name, primitive, isArray);
		
//		String typeName = (refType != null && !isArray) ? refType.getName() : name;// TODO replace this temp fix for array
		m_cache.addRefType(name, type);
		return type;
	}
	
	public JstRefType createJstRefType(final String name, final boolean primitive, final boolean isArray, boolean cacheIt){
		JstRefType type = new JstRefType(name, primitive, isArray);
		if (cacheIt){
			m_cache.addRefType(type);
		}
		return type;
	}
	
	public JstArray createJstArrayType(IJstType elementType, boolean cacheIt){
		if (elementType == null) {
			throw new IllegalArgumentException();
		}
		JstArray type =  createJstArray(elementType);
		if (cacheIt){
			m_cache.addType(type);
		}
		return type;
	}
	
	public IJstRefType createJstTypeRefType(final IJstType type){
		IJstRefType jstTypeRefType = new JstTypeRefType(type);
		m_cache.addTypeRefType(jstTypeRefType);
		return jstTypeRefType;
	}
	
	//
	// Private
	//
	private JstArray createJstArray(IJstType componentType) {
		return new JstArray(componentType);
	}
}
