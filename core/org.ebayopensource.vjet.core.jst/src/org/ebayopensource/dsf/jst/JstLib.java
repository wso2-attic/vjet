/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import org.ebayopensource.dsf.jst.declaration.JstType;

public final class JstLib implements IJstLib {

	private String m_name;
	private Map<String,JstType> m_types = new HashMap<String,JstType>();
	private Map<String,IJstLib> m_libs = new HashMap<String,IJstLib>();
	private Manifest m_manifest = null;
	
	//
	// Constructor
	//
	public JstLib(String name){
		assert name != null : "name cannot be null";
		m_name = name;
	}
	
	public JstLib(String name, Manifest mf){
		assert name != null : "name cannot be null";
		m_name = name;
		m_manifest = mf;
	}

	//
	// Satisfy IJstLib
	//
	public String getName() {
		return m_name;
	}

	public JstType getType(String typeName, boolean recursive){
		JstType type = m_types.get(typeName);
		if (type == null && recursive){
			for (IJstLib lib: m_libs.values()){
				type = lib.getType(typeName, recursive);
				if (type != null){
					return type;
				}
			}
		}
		return type;
	}
	
	public IJstLib getLib(String libName, boolean recursive){
		IJstLib lib = m_libs.get(libName);
		if (lib == null && recursive){
			for (IJstLib l: m_libs.values()){
				lib = l.getLib(libName, recursive);
				if (lib != null){
					return lib;
				}
			}
		}
		return lib;
	}
	
	public List<JstType> getAllTypes(boolean recursive){
		List<JstType> list = new ArrayList<JstType>();
		list.addAll(m_types.values());
		if (recursive){
			for (IJstLib lib: m_libs.values()){
				list.addAll(lib.getAllTypes(recursive));
			}
		}
		return list;
	}
	
	public List<IJstLib> getAllLibs(boolean recursive){
		List<IJstLib> list = new ArrayList<IJstLib>();
		list.addAll(m_libs.values());
		if (recursive){
			for (IJstLib lib: m_libs.values()){
				list.addAll(lib.getAllLibs(recursive));
			}
		}
		return list;
	}
	
	//
	// API
	//
	public JstLib addType(JstType type){
		assert type != null : "type cannot be null";
//		System.out.println("++ Adding type - " + type.getName());
		m_types.put(type.getName(), type);
//		JstCache.getInstance().addType(type);
		return this;
	}
	
	public JstLib addLib(IJstLib lib){
		assert lib != null : "lib cannot be null";
		m_libs.put(lib.getName(), lib);
		return this;
	}

	public JstLib addTypes(List<IJstType> jstTypes) {
		assert jstTypes != null : "type cannot be null";
		for (IJstType type : jstTypes) {
			addType((JstType) type);
		}
		return this;
	}
	
	public Manifest getManifest() {
		return m_manifest;
	}
}
