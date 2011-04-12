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

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.index.DependencyIndexMap;
import org.ebayopensource.dsf.ts.index.DependencyIndexNode;
import org.ebayopensource.dsf.ts.method.MethodIndex;
import org.ebayopensource.dsf.ts.method.MethodName;
import org.ebayopensource.dsf.ts.type.ISymbolName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * MethodIndexMgr manages method related changes to ensure type space integrity
 * 
 * 
 */
final class MethodIndexMgr extends AJstSymbolTableMgr<MethodName> {	
	//
	// Constructor
	//
	MethodIndexMgr(TypeSpace<IJstType,IJstNode> ts, JstQueryExecutor executor){
		super(ts, executor);
	}
	
	@Override
	protected Map<MethodName,List<IJstNode>> findSymbolUsagesWithinType(IJstType type) {
		//return m_qe.buildReferencesForTypeMethods(type);
		return m_qe.findMethodUsagesWithinNode(type);
	}
	
	@Override
	protected DependencyIndexMap<IJstNode> createIndex(IJstType type) {
		return new MethodIndex<IJstType,IJstNode>(type);
	}
	
	
	@Override
	protected void addDanglingReferencesToSymbol(ISymbolName symbol, DependencyIndexNode<IJstNode> indexnode) {
		m_ts.addToUnresolvedIndexNode((MethodName)symbol, indexnode);	
	}
	
	@Override
	protected IJstNode getSymbolNode(ISymbolName symbol, boolean isStatic) {
		TypeName typeName = new TypeName(symbol.getGroupName(),symbol.getOwnerTypeName());
		IJstType type = m_ts.getType(typeName);
		if (type == null){
			throw new RuntimeException("cannot find type for type:" + type);
		}
		return type.getMethod(symbol.getLocalName(), isStatic, false);
	}
	
	@Override
	protected boolean isSymbolInType(IJstType type, String symbol) {
		if (type.getMethod(symbol) != null) {
			return true;
		}
		else {
			IJstMethod constructor = type.getConstructor();
			if (constructor != null && constructor.getName().getName().equals(symbol)) {
				return true; // symbol is constructor
			}
			else {
				return false;
			}
		}
	}
		
	//
	// Package protected
	//
	/**
	 * Remove entry for method with given name from method index for given type.
	 */
	void removeMethod(final MethodName mtdName, boolean isStatic){
		removeSymbol(mtdName, isStatic);
	}

	void addMethod(MethodName mtdName, IJstMethod mtd) {
		TypeName typeName = mtdName.typeName();
		IJstType type = m_ts.getType(typeName);
		if (type == null){
			throw new RuntimeException("cannot find type for type:" + typeName);
		}
		
		// add method index from unresolved mtd dependencts
		DependencyIndexMap<IJstNode> index = getTypeIndex(typeName, true, true);
		DependencyIndexNode<IJstNode> node = m_ts.getUnresolvedIndexNode(mtdName);
		
		if (index != null && node != null) {
			index.addEntity(node);
		}
		
		Map<? extends ISymbolName, List<IJstNode>> methodUsageMap = getMethodUsages(mtd);
		addReferencesUsedByType(type, methodUsageMap);
		
		Map<? extends ISymbolName, List<IJstNode>> propertyUsageMap = getPropertyUsages(mtd);
		m_ts.getPropertySymbolTableManager().addReferencesUsedByType(type, propertyUsageMap);
	}

	void modifyMethod(MethodName mtdName, boolean isStatic, IJstMethod method) {
		removeSymbol(mtdName, isStatic); // remove it first
		addMethod(mtdName, method); // add the modified method
	}

}
