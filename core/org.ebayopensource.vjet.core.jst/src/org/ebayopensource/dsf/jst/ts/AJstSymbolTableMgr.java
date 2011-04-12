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
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.ASymbolTableManager;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.graph.DependencyNode;
import org.ebayopensource.dsf.ts.index.DependencyIndexNode;
import org.ebayopensource.dsf.ts.method.MethodName;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.ts.type.ISymbolName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * this class could be merged to SymbolTableManager if that was aware of JST types.
 *
 * @param <T>
 */
abstract class AJstSymbolTableMgr<T extends ISymbolName> extends ASymbolTableManager<IJstType,IJstNode> {	
	protected final JstQueryExecutor m_qe;
	protected final TypeSpace<IJstType,IJstNode> m_ts;
	
	//
	// Constructor
	//
	protected AJstSymbolTableMgr(TypeSpace<IJstType,IJstNode> ts, JstQueryExecutor executor){
		assert ts != null : "ts cannot be null";
		m_ts = ts;
		m_qe = executor;
	}
	
	/**
	 * for all symbols declared within the given type -
	 * return the map for each (type-declared) symbol to list of external JST nodes that use the symbol.
	 * @param type
	 * @return
	 */
	abstract Map<T,List<IJstNode>> findSymbolUsagesWithinType(IJstType type);

	@Override
	protected IJstType lookupType(TypeName typeName) {
		// FIXME: lookup type may not succeed during initialization-sepcific order
		IJstType type = m_ts.getType(typeName);
		
		if (type == null) { // search unresolved type in type space
			DependencyNode<IJstType> node = (DependencyNode<IJstType>)m_ts.getUnresolvedNodes().get(typeName.typeName());
			
			type = node.getEntity();
		}
		
		return type;
	}

	
	/**
	 * Perform symbol ref index actions to handle the AddType event.
	 * add dependencies introduced by this type.
	 * 
	 * This method populates symbol's ref index, therefore it cannot rely on the existing indexes for the type.
	 * 
	 * TODO: clarify the use case:
	 * A. this is called during the initial load, when all indexes are initially empty, but JST trees
	 * are fully populated. In this case we DON'T look into unresolved nodes index.
	 * B. this is called during normal operation. In this case, since this is a new type, if there are
	 * any references to the type's symbols, they would be unresolved. So we ONLY look into unresoved index.
	 * 
	 * FIXME: there should be clearer separation in the code what is the master source of data, 
	 * and data flows per each use case.
	 * @param type IJstType
	 */
	void processTypeAdded(TypeName typeName, final IJstType type){
		if (type == null){
			return;
		}	
		
		fixIndexMap(typeName, type); // fix the index map of this type first
		
		Map<T,List<IJstNode>> map = findSymbolUsagesWithinType(type);
		addReferencesUsedByType(type, map);
	}
		
	/**
	 * Perform symbol ref index actions to handle RemoveType event.
	 * Remove dependencies from dependent lists of index nodes' dependent list.
	 * @param typeName TypeName
	 */
	void processTypeRemoved(final TypeName typeName){		
		// Remove index of this type
		removeIndex(typeName);
		
		// Remove dependency on other properties and methods
		removeReferencesToType(typeName, m_ts.getAllDependencies(typeName));
	}
	
		
	@Override
	protected Map<? extends ISymbolName, List<IJstNode>> getMethodUsages(IJstNode n) {
		return m_qe.findMethodUsagesWithinNode(n);
	}

	@Override
	protected Map<? extends ISymbolName, List<IJstNode>> getPropertyUsages(IJstNode n) {
		return m_qe.findPropertyUsagesWithinNode(n);
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
	protected void removePropertyDependencies(final Map<? extends ISymbolName,List<IJstNode>> usage_list) {
		m_ts.getPropertySymbolTableManager().remove_dependencies(usage_list);
		removeUnresolvedPtyIndexNodeDependencies(usage_list);		
	}
	
	@Override
	protected void removeMethodDependencies(final Map<? extends ISymbolName,List<IJstNode>> usage_list) {
		m_ts.getMethodSymbolTableManager().remove_dependencies(usage_list);
		removeUnresolvedMtdIndexNodeDependencies(usage_list);
	}
	
	protected void removeUnresolvedPtyIndexNodeDependencies(final Map<? extends ISymbolName,List<IJstNode>> usage_list) {
		if (usage_list == null){
			return;
		}

		// for every symbol in the usage list, remove OUR syntax node dependent from the index
		for (Entry<? extends ISymbolName,List<IJstNode>> entry: usage_list.entrySet()){
			ISymbolName symbol = entry.getKey();
			// the type that declares the symbol that we refer
			TypeName typeName = new TypeName(symbol.getGroupName(),symbol.getOwnerTypeName());
			
			DependencyIndexNode<IJstNode> node = m_ts.getUnresolvedIndexNode(new PropertyName(typeName, symbol.getLocalName()));
			
			if (node != null) {
				node.removeDependents(entry.getValue());
			}			
		}
	}

	protected void removeUnresolvedMtdIndexNodeDependencies(final Map<? extends ISymbolName,List<IJstNode>> usage_list) {
		if (usage_list == null){
			return;
		}

		// for every symbol in the usage list, remove OUR syntax node dependent from the index
		for (Entry<? extends ISymbolName,List<IJstNode>> entry: usage_list.entrySet()){
			ISymbolName symbol = entry.getKey();
			// the type that declares the symbol that we refer
			TypeName typeName = new TypeName(symbol.getGroupName(),symbol.getOwnerTypeName());
			
			DependencyIndexNode<IJstNode> node = m_ts.getUnresolvedIndexNode(new MethodName(typeName, symbol.getLocalName()));
			
			if (node != null) {
				node.removeDependents(entry.getValue());
			}			
		}
	}
	
}
