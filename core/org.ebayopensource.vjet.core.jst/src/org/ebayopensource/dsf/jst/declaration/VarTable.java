/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;

import org.ebayopensource.dsf.common.Z;

public class VarTable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String,IJstType> m_varTypes;
	//>using at vjet side
	private Map<String, IJstNode> m_varNodes;
	private Map<String, JstType> m_localTypes;
//	
//	private static class VarTableEntry implements Serializable {
//		private static final long serialVersionUID = 1L;
//		private final IJstNode m_varNode;
//		private boolean m_visibleFromSameScope;
//		
//		private VarTableEntry(final IJstNode varNode,
//				final boolean visibleFromSameScope){
//			m_varNode = varNode;
//			m_visibleFromSameScope = visibleFromSameScope;
//		}
//		
//		public IJstNode getVarNode(){
//			return m_varNode;
//		}
//		
//		public boolean isVisibleFromSameScope(){
//			return m_visibleFromSameScope;
//		}
//		
//		public void setVisibleFromSameScope(final boolean visible){
//			m_visibleFromSameScope = visible;
//		}
//	}
//	
	public static interface IVarTableEntryViewer {
		public IJstNode view(final IJstNode varNode, final boolean visibleFromSameScope);
	}
	
	public static IVarTableEntryViewer SAME_SCOPE_VIEWER = new IVarTableEntryViewer(){
		@Override
		public IJstNode view(IJstNode varNode, boolean visibleFromSameScope) {
			return visibleFromSameScope ? varNode : null;
		}
	};
	
//	public static IVarTableEntryViewer INNER_SCOPE_VIEWER = new IVarTableEntryViewer(){
//		@Override
//		public IJstNode view(IJstNode varNode, boolean visibleFromSameScope) {
//			return varNode;
//		}
//	};
	
//	private void addVarNode(final String name, final IJstNode node){
//		addVarNode(name, node, true);
//	}
//	
	public void addVarNode(final String name, final IJstNode node/*, final boolean visibleFromSameScope*/){
		if(m_varNodes == null){
			m_varNodes = new HashMap<String, IJstNode>(8);
		}
		m_varNodes.put(name, node/*new VarTableEntry(node, visibleFromSameScope)*/);
	}
//	
//	public void visitVarNode(final String name){
//		if(m_varNodes != null){
//			final VarTableEntry varEntry = m_varNodes.get(name);
//			if(varEntry != null && !varEntry.isVisibleFromSameScope()){
//				varEntry.setVisibleFromSameScope(true);
//			}
//		}
//	}
	
	public IJstNode getVarNode(final String name){
//		return getVarNode(name, SAME_SCOPE_VIEWER);
		return m_varNodes != null ? m_varNodes.get(name) : null;
	}
	
//	public IJstNode getVarNode(final String name, final IVarTableEntryViewer viewer){
//		final VarTableEntry entry = m_varNodes != null ? m_varNodes.get(name) : null;
//		return entry == null ? null : viewer.view(entry.getVarNode(), entry.isVisibleFromSameScope()); 
//	}
	
	public Map<String, IJstNode> getVarNodes(){
		return getVarNodes(SAME_SCOPE_VIEWER);
	}
	
	public Map<String, IJstNode> getVarNodes(final IVarTableEntryViewer viewer){
		if(m_varNodes == null){
			return Collections.emptyMap();
		}
		Map<String, IJstNode> varNodes = new HashMap<String, IJstNode>(m_varNodes.size());
		for(Map.Entry<String, IJstNode> varEntry : m_varNodes.entrySet()){
			final IJstNode value = varEntry.getValue();
			varNodes.put(varEntry.getKey(), value/* != null ? viewer.view(value.getVarNode(), value.isVisibleFromSameScope()) : null*/);
		}
		return varNodes;
	}
	
	public void addVarType(final String name, final IJstType type){
		if (m_varTypes == null){
			m_varTypes = new LinkedHashMap<String,IJstType>(2);
		}
		m_varTypes.put(name, type);
	}
	
	public IJstType getVarType(final String name){
		return m_varTypes != null ? m_varTypes.get(name) : null;
	} 
	
	public void addLocalType(final String name, final JstType type){
		if (m_localTypes == null){
			m_localTypes = new LinkedHashMap<String,JstType>(2);
		}
		m_localTypes.put(name, type);
	}
	
	public boolean hasLocalType(final String name){
		return m_localTypes != null ? m_localTypes.containsKey(name) : false;
	} 
	
	public JstType getLocalType(final String name){
		return m_localTypes != null ? m_localTypes.get(name) : null;
	} 
	
	/**
	 * returns unmodifiable set of variable names
	 * @return
	 */
	public Set<String> getVars(){
		if(m_varTypes==null){
			return null;
		}
		return Collections.unmodifiableSet(m_varTypes.keySet());
	}
	
	/**
	 * returns unmodifiable set of variable types
	 * @return
	 */
	public Map<String,IJstType> getVarTypes(){
		if(m_varTypes==null){
			return null;
		}
		return Collections.unmodifiableMap(m_varTypes);
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		if (m_varTypes != null && m_varTypes.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (Entry<String,IJstType> entry: m_varTypes.entrySet()){
				sb.append("\n\t").append(entry.getKey()).append(" ").append(entry.getValue().getName());
			}
			z.format("m_varTypes", sb.toString());
		}
		return z.toString();
	}
}
