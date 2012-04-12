/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jslang.ts.tests;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.ASymbolTableManager;
import org.ebayopensource.dsf.ts.index.DependencyIndexMap;
import org.ebayopensource.dsf.ts.index.DependencyIndexNode;
import org.ebayopensource.dsf.ts.method.MethodIndex;
import org.ebayopensource.dsf.ts.property.PropertyIndex;
import org.ebayopensource.dsf.ts.type.ISymbolName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * implements symbol table manager for tests where JstTy[eSpaceManager is not used
 *
 */
public class TestSymbolTableManager extends ASymbolTableManager<IJstType,IJstNode>  {
	boolean m_for_methods;
	
	protected TestSymbolTableManager(boolean for_methods) {
		m_for_methods = for_methods;
	}

	@Override
	protected Map<? extends ISymbolName, List<IJstNode>> getMethodUsages(IJstNode n) {
		return Collections.emptyMap();
	}

	@Override
	protected Map<? extends ISymbolName, List<IJstNode>> getPropertyUsages(IJstNode n) {
		return Collections.emptyMap();
	}

	@Override
	protected void addDanglingReferencesToSymbol(ISymbolName symbol, DependencyIndexNode<IJstNode> index) {
	}

	@Override
	protected DependencyIndexMap<IJstNode> createIndex(IJstType type) {
		return m_for_methods?
				new MethodIndex<IJstType,IJstNode>(type) :
				new PropertyIndex<IJstType,IJstNode>(type);					
	}


	@Override
	protected String getName(IJstType type) {
		return type.getName();
	}

	@Override
	protected IJstType getOwnerType(IJstNode node) {
		return node.getOwnerType();
	}

	@Override
	protected IJstNode getSymbolNode(ISymbolName symbol, boolean isStatic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IJstType lookupType(TypeName typename) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected boolean isSymbolInType(IJstType type, String symbol) {
		return true;
	}

	protected void removePropertyDependencies(final Map<? extends ISymbolName,List<IJstNode>> usage_list) {
		
	}
	
	protected void removeMethodDependencies(final Map<? extends ISymbolName,List<IJstNode>> usage_list) {
		
	}

}
