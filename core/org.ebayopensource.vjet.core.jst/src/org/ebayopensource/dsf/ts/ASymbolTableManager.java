/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.TypeSpaceLocker;
import org.ebayopensource.dsf.ts.index.DependencyIndexMap;
import org.ebayopensource.dsf.ts.index.DependencyIndexNode;
import org.ebayopensource.dsf.ts.type.ISymbolName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * this class implements common operations on symbol tables. There are 2 separate symbol tables: methods, properties.
 * FIXME: why keep this class completely generic?
 * 
 * @param <T>
 * @param <D>
 */
public abstract class ASymbolTableManager<T,D> {
		
	private final TypeSpaceLocker m_locker = new TypeSpaceLocker();
	
	protected Map<TypeName,DependencyIndexMap<D>> m_symbolTable = new LinkedHashMap<TypeName,DependencyIndexMap<D>>();
	protected Map<TypeName,DependencyIndexMap<D>> m_removedSymbolTable = new LinkedHashMap<TypeName,DependencyIndexMap<D>>();
	
	
	/**
	 * create new index for the given type.
	 * @param node
	 * @return
	 */
	protected abstract DependencyIndexMap<D> createIndex(T type);
	/**
	 * 
	 * @param symbol
	 * @param index
	 */
	protected abstract void addDanglingReferencesToSymbol(ISymbolName symbol, DependencyIndexNode<D> index);
	/**
	 * 
	 * @param symbol
	 * @param isStatic
	 * @return
	 */
	protected abstract D getSymbolNode(ISymbolName symbol, boolean isStatic);
	
	// these 2 methods could be implemented here if this class was aware of QueryExecutor
	protected abstract Map<? extends ISymbolName,List<D>> getPropertyUsages(D n);
	protected abstract Map<? extends ISymbolName,List<D>> getMethodUsages(D n);
	
	// the 2 methods below would be unnecessary if TypeSpace wasn't completely generic and was at least
	// <T extends IJstType, D extends IJstNode>
	protected abstract String getName(T type);
	protected abstract T getOwnerType(D node);
	
	/**
	 * find type by its name
	 * @param typename
	 * @return  type object or null of not found
	 */
	protected abstract T lookupType(TypeName typename);
	
	//
	// Constructor
	//
	protected ASymbolTableManager(){
	}
	
	protected DependencyIndexMap<D> addIndex(final TypeName typeName, final DependencyIndexMap<D> index){
		if (index == null || typeName == null){
			return null;
		}
		try {
			m_locker.lockExclusive();
			DependencyIndexMap<D> index1 = m_symbolTable.get(typeName);
			if (index1 != null){
				if (index1 == index){
					return index1;
				}
				else {
					throw new RuntimeException("Index of same type already exists:" + typeName);
				}
			}
			m_symbolTable.put(typeName, index);
			return index;
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	protected DependencyIndexMap<D> removeIndex(final TypeName typeName){
		if (typeName == null){
			return null;
		}
		try {
			m_locker.lockExclusive();

			DependencyIndexMap<D> index = m_symbolTable.remove(typeName);
			
			if (index != null) {
			
				remove_references(typeName, index);
			
				// save the removed index for later Add or Modify event
				m_removedSymbolTable.put(typeName, index);
			}
			
			return index;			
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	protected DependencyIndexMap<D> getIndex(TypeName typeName) {
		return getTypeIndex(typeName, false, false);		
	}
	
	protected abstract boolean isSymbolInType(IJstType type, String symbol); // overridden in property and method index manager
	
	protected void fixIndexMap(TypeName typeName, IJstType type) {
		if (typeName == null || type == null){
			return;
		}
		
		DependencyIndexMap<D> index = getTypeIndex(typeName, true, true);
		
		if (index != null) {
			ArrayList<String> symbolNameRemoveList = new ArrayList<String>();
			
			for (String symbol : index.getMap().keySet()) {
				if (!isSymbolInType(type, symbol)) {
					symbolNameRemoveList.add(symbol); // symbol not found in JstType, remove it from index map
				}
			}
			synchronized (this) {
				for (String symbol : symbolNameRemoveList) {
					index.removeEntity(symbol);
				}
			}
		}
		
	}
	
	protected DependencyIndexMap<D> getTypeIndex(TypeName typeName, boolean useRemoved, boolean create_index){
		if (typeName == null){
			return null;
		}
		DependencyIndexMap<D> index = null;
		try {
			m_locker.lockExclusive();
			
			index = m_symbolTable.get(typeName);
			
			if (index == null && useRemoved) {
				index = m_removedSymbolTable.get(typeName);
				
				if (index != null && create_index) { // move out of removed symbol table and put into symbol table
					m_removedSymbolTable.remove(typeName);
					m_symbolTable.put(typeName, index);					
				}
			}
		
			if (index == null && create_index) {
				
				index = m_symbolTable.get(typeName);
				if (index == null) {
					T type = lookupType(typeName);
					if (type == null) {					
						throw new RuntimeException("cannot find type:" + typeName);						
					}
					index = createIndex(type);
					m_symbolTable.put(typeName, index);
				}
			}
		}
		finally {
			m_locker.releaseExclusive();
		}
		
		return index;
	}
	
	protected void renameType(final TypeName oldName, final String newName){
		if (oldName == null || newName == null){
			return;
		}
		try {
			m_locker.lockExclusive();
				
			// Update key in PropertyIndex 
			TypeName newTypeName = new TypeName(oldName.groupName(), newName);

			m_symbolTable.put(newTypeName, m_symbolTable.remove(oldName));
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	public List<D> getDependents(ISymbolName symbol) {
		if (symbol == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			TypeName typeName = new TypeName(symbol.getGroupName(),symbol.getOwnerTypeName());
			DependencyIndexMap<D> index = m_symbolTable.get(typeName);
			if (index == null){
				return Collections.emptyList();
			}
			return index.getDependents(symbol.getLocalName());
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	

	/**
	 * Remove entry for property/method with given name from method index for given type.
	 * @param symbol ISymbolName property/method symbol to remove
	 */
	protected void removeSymbol(final ISymbolName symbol, boolean isStatic) {
		
		if (symbol == null){
			return;
		}
		
		// this is the type that declares the symbol
		TypeName declaringTypeName = new TypeName(symbol.getGroupName(),symbol.getOwnerTypeName());
		
		// get the symbol table for the owner type
		DependencyIndexMap<D> declaringTypeIndex = getTypeIndex(declaringTypeName, true, false);
		if (declaringTypeIndex == null){
			throw new RuntimeException("cannot find symbol index for type:" + declaringTypeName);
		}
		
		// 1. remove the symbol from the declaring type ref index
		// get the dependents that this symbol uses. These are ALL (everywhere) nodes that refer to the symbol.
		DependencyIndexNode<D> nodes_referring_us = declaringTypeIndex.removeEntity(symbol.getLocalName());
		
		// 2. backup the dependents to the "unresolved" list. 
		// The unresolved list will contain all JST nodes that now have dangling reference to the symbol.
		// Note: lists are kept separately for property/method.
		addDanglingReferencesToSymbol(symbol, nodes_referring_us);
				
		// 3. For ALL our dependencies (whom we refer to), remove us from their dependents.	
		D node = getSymbolNode(symbol, isStatic); // will return JstMethod or JstProperty object
		
		//IndentedPrintStream ps = new IndentedPrintStream(System.out);
		///JstDepthFirstTraversal.accept((IJstNode)node, new JstPrettyPrintVisitor(System.out));
		
		// Only the references that our body uses. 
		if (node != null){
			// traverse the syntax body of the given property/method (the definition)
			removePropertyDependencies(getPropertyUsages(node));
			
			removeMethodDependencies(getMethodUsages(node));
		}	
	}
	
	/**
	 * Remove dependencies from usage lists the given symbol.
	 * The usage_list contains the list of properties/methods invoked/used from withing a block of code
	 * (body of method). 
	 * FIXME: Property would always yeild empty usage list - property does not have a body and initializers
	 * are not possible in JS?
	 * @param usage_list Map<ISymbolName,List<IJstNode>> - the syntax nodes that refer to other symbols
	 */
	protected abstract void removePropertyDependencies(final Map<? extends ISymbolName,List<D>> usage_list);
	
	protected abstract void removeMethodDependencies(final Map<? extends ISymbolName,List<D>> usage_list);
	
	public void remove_dependencies(final Map<? extends ISymbolName,List<D>> usage_list) {
		
		if (usage_list == null){
			return;
		}

		// for every symbol in the usage list, remove OUR syntax node dependent from the index
		for (Entry<? extends ISymbolName,List<D>> entry: usage_list.entrySet()){
			ISymbolName symbol = entry.getKey();
			// the type that declares the symbol that we refer
			TypeName typeName = new TypeName(symbol.getGroupName(),symbol.getOwnerTypeName());

			DependencyIndexMap<D> index = getTypeIndex(typeName, true, false);
			if (index == null){
				continue;
			}
			index.removeDependents(symbol.getLocalName(), entry.getValue());
		}
	}
	/**
	 * this is formerly the MethodIndexMgr.addMethodDependents() and PropertyIndexMgr.addPropertyDependencies();
	 * 
	 * Called as part of AddType event.
	 * @param type - the type being added
	 * @param usages - the list (per each declared symbol) of dependents JST nodes that refer to that symbol
	 */
	public void addReferencesUsedByType(final T type, Map<? extends ISymbolName,List<D>> usages){		
		if (type == null){
			return;
		}

		// per each symbol, declared by the type
		for(Entry<? extends ISymbolName,List<D>> entry: usages.entrySet()){
			ISymbolName symbol = entry.getKey();
			TypeName symbolsTypeName = new TypeName(symbol.getGroupName(),symbol.getOwnerTypeName());

			// get (create if necessary) the index for the symbol's owner type (the type that declared the symbol)
			DependencyIndexMap<D> index = getTypeIndex(symbolsTypeName, true, true);
			
			// in the type-specific symbol ref index, add dependent JST nodes to the property/method symbol
			// symbol - is one of a property/method declared in the type
			// the list - is list of JST nodes that refer to that symbol (are dependents)
			index.addDependents(symbol.getLocalName(), entry.getValue());
		}
	}

	/**
	 * Called as part of RemoveType event
	 * @param typeName - the type name
	 * @param from_types types (IJstType) that this type depends on
	 */
	protected void removeReferencesToType(final TypeName typeName, List<T> from_types){

		for (T bType: from_types){
			String groupName = typeName.groupName();
			if (bType instanceof IJstType) {
				IJstType jstType = (IJstType)bType;
				
				if (jstType.getPackage() != null)
					groupName = jstType.getPackage().getGroupName(); 
			}
			TypeName tName = new TypeName(groupName, getName(bType));
			DependencyIndexMap<D> another_type_index = getTypeIndex(tName, true, false);
			if (another_type_index == null){
				continue;
			}
			remove_references(typeName, another_type_index);
		}
	}
	
	/**
	 * remove symbol table entries that refer to the given type
	 * @param typeName - the type referenced to be removed
	 * @param another_type_index - the ref index
	 */
	private void remove_references(final TypeName typeName, DependencyIndexMap<D> another_type_index) {
		// iterate over the symbol ref index
		for (DependencyIndexNode<D> symbolNode: another_type_index.getMap().values()){
			ArrayList<D> toRemove = new ArrayList<D>();
			// iterate over the nodes that refer to symbolNode symbol
			for (D n: symbolNode.getDependents()){
				// if declared by the type in question, collect the node
				T aType = getOwnerType(n);
				if (aType != null && typeName.typeName().equals(getName(aType))){
					toRemove.add(n);
				}
			}
			for (D n: toRemove){
				another_type_index.removeDependent(symbolNode.getName(), n);
			}
		}
	}
}
