/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class GroupSymbolMapTable<T, D> {

	public static class GlobalSymbolMapEntry<D> {
		private String m_name;
		private String m_ownerTypeName; //could also be type name for direct type mapping
		private D m_jstNode;
		private boolean m_isDirectTypeMapping = false;

		GlobalSymbolMapEntry(String name, String ownerTypeName, D node) {
			m_name = name;
			m_ownerTypeName = ownerTypeName;
			m_jstNode = node;
		}

		public String getName() {
			return m_name;
		}
		
		public D getNode() {
			return m_jstNode;
		}
		
		boolean isDirectTypeMapping() {
			return m_isDirectTypeMapping;
		}
		
		GlobalSymbolMapEntry<D> setDirectTypeMapping(boolean set) {
			m_isDirectTypeMapping = set;
			return this;
		}
				
		String getOwnerTypeName() {
			return m_ownerTypeName;
		}

		void setNode(D node) {
			m_jstNode = node;
		}
	}

	private Group<T> m_group; // parent group	
	private Map<String, GlobalSymbolMapEntry<D>> m_globalVarSymbolTable;
	private Map<String, Set<String>> m_ownerTypeAndGlobalVarMapping; //for efficient deletion
	private Map<String, Map<String, GlobalSymbolMapEntry<D>>> m_extendedGlobalVars;
	private Map<String, String> m_extensionPointMapping; //for efficient deletion of global extension

	// global types whose methods and properties are added to the global symbol table
	private Set<String> m_globalTypeNameList;

	public GroupSymbolMapTable(Group<T> group) {
		m_globalVarSymbolTable = new LinkedHashMap<String, GlobalSymbolMapEntry<D>>();
		m_ownerTypeAndGlobalVarMapping = new HashMap<String, Set<String>>();
		m_extendedGlobalVars = new LinkedHashMap<String, Map<String, GlobalSymbolMapEntry<D>>>();
		m_extensionPointMapping = new HashMap<String, String>();
		m_globalTypeNameList = new LinkedHashSet<String>();
		m_group = group;
	}

	public synchronized void addGlobalType(String type) {
		m_globalTypeNameList.add(type);
	}

	public synchronized void addGlobal(String varName, String typeName) {
		addGlobal(varName, typeName, false);
	}

	public synchronized void addGlobal(String varName, String typeName, boolean directTypeMapping) {
		GlobalSymbolMapEntry<D> entry = addGlobal(varName, typeName, null);
		entry.setDirectTypeMapping(directTypeMapping);
	}
	
	public synchronized GlobalSymbolMapEntry<D> addGlobal(String varName, String typeName, D node) {
		GlobalSymbolMapEntry<D> entry = null;
		if (node instanceof IJstGlobalVar) {			
			String scope = ((IJstGlobalVar)node).getScopeForGlobal();
			if (scope != null) { //extended global var 
				Map<String, GlobalSymbolMapEntry<D>> map = m_extendedGlobalVars.get(scope);
				if (map == null) {
					map = new LinkedHashMap<String, GlobalSymbolMapEntry<D>>();
					m_extendedGlobalVars.put(scope, map);
					m_extensionPointMapping.put(typeName, scope);
				}
				else {
					entry = map.get(varName);
				}
				if (entry == null) {
					entry = new GlobalSymbolMapEntry<D>(varName, typeName, node);
					map.put(varName, entry);
				}
				return entry;
			}
		}
		entry = m_globalVarSymbolTable.get(varName);
		if (entry == null) {
			entry = new GlobalSymbolMapEntry<D>(varName, typeName, node);
			m_globalVarSymbolTable.put(varName, entry);
			Set<String> varsFromType = m_ownerTypeAndGlobalVarMapping.get(typeName);
			if (varsFromType == null) {
				varsFromType = new HashSet<String>();
				m_ownerTypeAndGlobalVarMapping.put(typeName, varsFromType);
			}
			varsFromType.add(varName);
		} else {
			//report error???
		}
		return entry;
	}

	public List<GlobalSymbolMapEntry<D>> getAllGlobalTypes() {
		List<GlobalSymbolMapEntry<D>> list = new ArrayList<GlobalSymbolMapEntry<D>>();

		for (GlobalSymbolMapEntry<D> entry : m_globalVarSymbolTable.values()) {
			
			if (entry.getNode() != null && entry.getNode() instanceof IJstType){
				D node = entry.getNode();	
				if (node == null) {
					T type = m_group.getEntity(entry.getOwnerTypeName());	
					if (type != null) {
						entry.setNode((D)type);
						list.add(entry);
					}
				}
				else {
					list.add(entry);
				}
			}
		}

		return list;
	}

	public T getGlobalType(String typeName) {

		GlobalSymbolMapEntry<D> entry = m_globalVarSymbolTable.get(typeName);

		if (entry == null || !entry.isDirectTypeMapping()) {
			return null;
		}

		D node = entry.getNode();
		if (node == null) {
			node = findGlobalNode(entry);
		}
		
		return (T)node;
	}
	
	public List<D> getAllGlobalVars() {
		List<D> list = new ArrayList<D>();
		
		for (GlobalSymbolMapEntry<D> entry : m_globalVarSymbolTable.values()) {
			
			D node =  entry.getNode();			
			if(node == null){
				node = findGlobalNode(entry);
			}
			
			if (node != null) {
				list.add(node);
			}
		}		
		return list;
	}

	public D getGlobalVar(String varName) {
		
		GlobalSymbolMapEntry<D> entry = m_globalVarSymbolTable.get(varName);		
		if (entry == null) {
			return null;
		}
				
		D node =  entry.getNode();		
		if(node == null){
			node = findGlobalNode(entry);
		}
		return node;		
	}
	
	public boolean hasExtension(String globalVarName) {
		return m_extendedGlobalVars.containsKey(globalVarName);
	}
	
	public List<D> getExtensions(String globalVarName) {
		Map<String, GlobalSymbolMapEntry<D>> extensionMap = 
			m_extendedGlobalVars.get(globalVarName);
		if (extensionMap == null) {
			return null;
		}
		List<D> extensions = new ArrayList<D>(extensionMap.size());
		for (GlobalSymbolMapEntry<D> entry : extensionMap.values()) {
			D node =  entry.getNode();		
			if(node == null){
				node = findGlobalNode(entry);
			}
			if (node != null) {
				extensions.add(node);
			}
		}
		return extensions;
	}

	private D findGlobalNode(GlobalSymbolMapEntry<D> entry) {	
		String typeName = entry.getOwnerTypeName();
		IJstType type = (IJstType) m_group.getEntity(typeName);	
		if (type != null) {
			D glbvar = null;
			if (entry.isDirectTypeMapping()) {
				glbvar = (D)type;
				entry.setNode(glbvar);
				return glbvar;
			}
			
			glbvar = (D)type.getGlobalVar(entry.getName());			
			if (glbvar != null) {
				entry.setNode(glbvar);
				return glbvar;			
			}
		}		
		return null;
	}
	
	
	public D getGlobalMethod(String methodName) {

		GlobalSymbolMapEntry<D> entry = m_globalVarSymbolTable.get(methodName);

		if (entry == null) {
			return null;
		}

		D method = entry.getNode();
		if (method == null){
			method = findGlobalNode(entry);
		}
		
		if(!(method instanceof IJstMethod)){
			return null;
		}
		
		return method;
	}

	public synchronized void promoteGlobalTypeMembers() {

		if (m_globalTypeNameList.isEmpty()) {
			return;
		}

		List<JstType> typeList = new ArrayList<JstType>();
		Set<String> unresolvedGlobalTypeList = new HashSet<String>();

		for (String typeName : m_globalTypeNameList) {
			JstType type = (JstType) m_group.getEntity(typeName);

			if (type != null) {
				typeList.add(type);
			} else {
				unresolvedGlobalTypeList.add(typeName);
			}
		}

		promoteAllMembers(typeList);
		m_globalTypeNameList.clear();
		m_globalTypeNameList = unresolvedGlobalTypeList;
	}

	synchronized private void promoteAllMembers(List<JstType> typeList) {		
		for (JstType jstType : typeList) {
			String typeName = jstType.getName();
			for (IJstProperty pty : jstType.getProperties()) {
				addGlobal(pty.getName().getName(), typeName, (D)pty);
			}
			for (IJstMethod method : jstType.getMethods()) {
				addGlobal(method.getName().getName(), typeName, (D)method);
			}
		}
	}

	public synchronized void removeGlobalVarsFromType(String typeName) {
		Set<String> varsFromType = m_ownerTypeAndGlobalVarMapping.remove(typeName);
		if (varsFromType != null) {
			for (String varName : varsFromType) {
				m_globalVarSymbolTable.remove(varName);
			}
			return;
		}
		String extensionPoint = m_extensionPointMapping.remove(typeName);
		if (extensionPoint != null) {
			Map<String, GlobalSymbolMapEntry<D>> extendedMap
				= m_extendedGlobalVars.get(extensionPoint);
			List<String> keysToRemove = new ArrayList<String>();
			
			for (Map.Entry<String, GlobalSymbolMapEntry<D>> entry : extendedMap.entrySet()){			
				if(typeName.equals(entry.getValue().getOwnerTypeName())){
					keysToRemove.add(entry.getKey());
				}
			}	
			
			for(String extended: keysToRemove){
				extendedMap.remove(extended);
			}
			if (extendedMap.isEmpty()) {
				m_extendedGlobalVars.remove(extensionPoint);
			}
		}
	}
}
