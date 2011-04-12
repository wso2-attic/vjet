/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.index.DependencyIndexMap;
import org.ebayopensource.dsf.ts.index.DependencyIndexNode;
import org.ebayopensource.dsf.ts.property.PropertyIndex;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.ts.type.ISymbolName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * PropertyIndexMgr manages property related changes to ensure type space integrity
 * 
 * 
 */
final class PropertyIndexMgr extends AJstSymbolTableMgr<PropertyName>  {
	
	//
	// Constructor
	//

	PropertyIndexMgr(TypeSpace<IJstType,IJstNode> ts, JstQueryExecutor executor){
		super(ts, executor);
	}
	
	@Override
	protected Map<PropertyName,List<IJstNode>> findSymbolUsagesWithinType(IJstType type) {
		//return m_qe.buildReferencesForTypeProperties(type);
		return m_qe.findPropertyUsagesWithinNode(type);
	}
	
	@Override
	protected DependencyIndexMap<IJstNode> createIndex(IJstType type) {
		return new PropertyIndex<IJstType,IJstNode>(type);
	}
	
	@Override
	protected void addDanglingReferencesToSymbol(ISymbolName symbol, DependencyIndexNode<IJstNode> indexnode) {
		m_ts.addToUnresolvedIndexNode((PropertyName)symbol, indexnode);	
	}	
	
	@Override
	protected IJstNode getSymbolNode(ISymbolName symbol, boolean isStatic) {
		TypeName typeName = new TypeName(symbol.getGroupName(),symbol.getOwnerTypeName());
		IJstType type = m_ts.getType(typeName);
		if (type == null){
			throw new RuntimeException("cannot find type for type:" + type);
		}
		return type.getProperty(symbol.getLocalName(), isStatic, false);
	}
	
	@Override
	protected boolean isSymbolInType(IJstType type, String symbol) {
		if (type.getProperty(symbol) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	protected Map<? extends ISymbolName, List<IJstNode>> getMethodUsages(IJstNode n) {
		if (n instanceof IJstProperty) {
			IJstProperty pty = (IJstProperty)n;
			IJstNode initializer = pty.getInitializer();
			if (initializer != null)
				n = initializer;
		}
		
		return m_qe.findMethodUsagesWithinNode(n);
	}

	@Override
	protected Map<? extends ISymbolName, List<IJstNode>> getPropertyUsages(IJstNode n) {
		if (n instanceof IJstProperty) {
			IJstProperty pty = (IJstProperty)n;
			IJstNode initializer = pty.getInitializer();
			if (initializer != null)
				n = initializer;
		}
		
		return m_qe.findPropertyUsagesWithinNode(n);
	}
		
	//
	// Package protected
	//
	/**
	 * Remove entry for property with given name from property index for given type.
	 * If this method is called before property is removed from IJstType, the dependent
	 * lists of other properties and methods this property depends on will also be updated.
	 * @param typeName String
	 * @param ptyName String
	 */
	void removeProperty(final PropertyName ptyName, boolean isStatic){
		removeSymbol(ptyName, isStatic);
	}

	void addProperty(PropertyName ptyName, boolean isStatic, IJstProperty pty) {
		TypeName typeName = ptyName.typeName();
		IJstType type = m_ts.getType(typeName);
		if (type == null){
			throw new RuntimeException("cannot find type for type:" + typeName);
		}
		
		// add method index from unresolved mtd dependencts
		DependencyIndexMap<IJstNode> index = getTypeIndex(typeName, true, true);
		DependencyIndexNode<IJstNode> node = m_ts.getUnresolvedIndexNode(ptyName);
		
		if (index != null && node != null) {
			index.addEntity(node);
		}	
		
		Map<? extends ISymbolName, List<IJstNode>> propertyUsageMap = getPropertyUsages(pty);
		addReferencesUsedByType(type, propertyUsageMap);
		
		Map<? extends ISymbolName, List<IJstNode>> methodUsageMap = getMethodUsages(pty);
		m_ts.getMethodSymbolTableManager().addReferencesUsedByType(type, methodUsageMap);
	}
	
}
