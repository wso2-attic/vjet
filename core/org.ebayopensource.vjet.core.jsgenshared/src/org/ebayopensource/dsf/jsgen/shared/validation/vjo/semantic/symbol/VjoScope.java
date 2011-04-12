/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;

/**
 * <p>
 * 	vjo scope is simply a chain of scope qualified nodes (JstType or JstMethod)
 * 	each node in the chain adds a depth to the scope chain which affects the symbol lookup and creation (latest)
 * 
 * 	vjo scope implementation contains a list of types and a list of nodes(type + method)
 * </p>
 * 
 *
 */
public class VjoScope{
	private boolean m_static;
	private List<IJstType> m_typeScopeNodes = new ArrayList<IJstType>();
	private List<IJstNode> m_scopeNodes = new ArrayList<IJstNode>();
	
	public boolean isStatic(){
		return m_static;
	}
	
	public List<IJstNode> getScopeNodes(){
		return m_scopeNodes;
	}
	
	public List<IJstType> getTypeScopeNodes(){
		return m_typeScopeNodes;
	}
	
	public void setStatic(boolean isStatic){
		m_static = isStatic;
	}
	
	public void addScopeNode(IJstNode node){
		m_scopeNodes.add(node);
	}
	
	public void addTypeNode(IJstType type){
		m_typeScopeNodes.add(type);
	}
	
	public void removeScopeNode(IJstNode node){
		m_scopeNodes.remove(node);
	}
	
	public void removeTypeNode(IJstType type){
		m_typeScopeNodes.remove(type);
	}
	
	public IJstNode getClosestScopeNode(){
		if(m_scopeNodes.size() <= 0){
			return null;
		}
		return m_scopeNodes.get(m_scopeNodes.size() - 1);
	}
	
	public boolean isWithinScope(IJstNode node){
		return m_scopeNodes.contains(node);
	}
	
	public IJstType getClosestTypeScopeNode(){
		if(m_typeScopeNodes.size() <= 0){
			return null;
		}
		return m_typeScopeNodes.get(m_typeScopeNodes.size() - 1);
	}
}