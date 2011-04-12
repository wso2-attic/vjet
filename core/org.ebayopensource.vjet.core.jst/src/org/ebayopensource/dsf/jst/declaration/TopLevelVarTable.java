/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;

public class TopLevelVarTable extends VarTable {

	private static final long serialVersionUID = 1L;

	private List<IJstType> m_includedTypes = new ArrayList<IJstType>();
	
	public TopLevelVarTable addIncludedType(IJstType type) {
		m_includedTypes.add(type);
		return this;
	}
	
	@Override
	public IJstNode getVarNode(final String name){
		IJstNode node = super.getVarNode(name);
		if (node != null) {
			return node;
		}
		for (IJstType t : m_includedTypes) {
			node = t.getInitBlock().getVarTable().getVarNode(name);
			if (node != null) {
				return node;
			}
		}
		return null;
	}
	
	public IJstNode getSelfVarNode(final String name){
		IJstNode node = super.getVarNode(name);
		if (node != null) {
			return node;
		}
		return null;
	}
	
	public Map<String, IJstNode> getSelfVarNodes() {
		return super.getVarNodes();
	}
	
	public Map<String, IJstNode> getVarNodes() {
		Map<String, IJstNode> merged = new LinkedHashMap<String, IJstNode>();
		merged.putAll(super.getVarNodes());
		for (IJstType t : m_includedTypes) {
			merged.putAll(t.getInitBlock().getVarTable().getVarNodes());
		}
		return merged;
	}
	
	public List<VarTable> getLinkedVarTables(){
		final List<VarTable> linkedVarTables = new LinkedList<VarTable>();
		for (IJstType t : m_includedTypes) {
			linkedVarTables.add(t.getInitBlock().getVarTable());
		}
		return linkedVarTables;
	}

	public IJstType getVarType(final String name){
		IJstType varType = super.getVarType(name);
		if (varType != null) {
			return varType;
		}
		for (IJstType t : m_includedTypes) {
			varType = t.getInitBlock().getVarTable().getVarType(name);
			if (varType != null) {
				return varType;
			}
		}
		return null;
	} 
		
	@Override
	public Set<String> getVars(){
		Set<String> merged = new LinkedHashSet<String>();
		merged.addAll(super.getVars());
		for (IJstType t : m_includedTypes) {
			merged.addAll(t.getInitBlock().getVarTable().getVars());
		}
		return merged;
	}
	
	@Override
	public Map<String,IJstType> getVarTypes(){
		Map<String, IJstType> merged = new LinkedHashMap<String, IJstType>();
		merged.putAll(super.getVarTypes());
		for (IJstType t : m_includedTypes) {
			merged.putAll(t.getInitBlock().getVarTable().getVarTypes());
		}
		return merged;
	}
}
