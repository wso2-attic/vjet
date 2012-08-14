/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstProxyType;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.jst.ts.util.MethodDependencyVisitor;
import org.ebayopensource.dsf.jst.ts.util.PropertyDependencyVisitor;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.graph.DependencyNode;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.group.GroupDependencyNode;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.dsf.ts.method.MethodIndex;
import org.ebayopensource.dsf.ts.method.MethodName;
import org.ebayopensource.dsf.ts.property.PropertyIndex;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.ts.type.ISymbolName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * Execute queries based on type space or computed on the fly by tree traversal.
 * 
 * 
 */
public class JstQueryExecutor {
	
	private final TypeSpace<IJstType,IJstNode> m_ts;
	private final TypeSpaceLocker m_locker;
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param ts JstTypeSpace
	 */
	public JstQueryExecutor(final TypeSpace<IJstType,IJstNode> ts){
		if (ts == null){
			throw new AssertionError("ts cannot be null");
		}
		m_ts = ts;
		m_locker = m_ts.getLocker();
	}
	
	//
	// API
	//
	/**
	 * Answer the type with given name. Return null if not found.
	 * @param typeName TypeName
	 * @return IJstType
	 */
	public IJstType findType(final TypeName typeName){
		try {
			//m_locker.lockShared();
			return m_ts.getType(typeName);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer the type with given name visible to the given fromGroup. 
	 * Return null if not found.
	 * @param typeName TypeName
	 * @param fromGroup IGroup<IJstType>
	 * @return IJstType
	 */
	public IJstType findType(final TypeName typeName, final IGroup<IJstType> fromGroup){
		try {
			//m_locker.lockShared();
			return m_ts.getVisibleType(typeName, fromGroup);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * find all types in type space keyed by (group name, type name)
	 * @return
	 */
	public Map<TypeName, IJstType> findAllTypes() {
		
		return m_ts.getTypes();
	}
	
	/**
	 * find all package names in type space
	 * @return
	 */
	public List<String> findAllPackages() {
		
		return m_ts.getPackages();
	}
	
	/**
	 * find all types in the package
	 * @param packageName
	 * @return
	 */
	public Map<TypeName, IJstType> findAllTypesInPackage(String packageName) {
		
		return m_ts.getTypes(packageName);
	}
	
	/**
	 * find all JstType that depend on the given package
	 * @param packageName
	 * @return
	 */
	public List<IJstType> findPackageDependents(String packageName) {
		
		return m_ts.getPackageDependents(packageName);
	}
	
	/**
	 * find a list of groups depending on the input group
	 * @return
	 */
	public List<Group<IJstType>> findGroupDependents(final String groupName) {
		GroupDependencyNode<IJstType> node = m_ts.getGroupDependencyNode(groupName);
		
		List<Group<IJstType>> listGrp = new ArrayList<Group<IJstType>>();
		
		if (node != null) {
			for (GroupDependencyNode<IJstType> dependent : node.getDependents().values()) {
				listGrp.add(dependent.getGroup());
			}
		}
		
		return listGrp;
	}
	
	/**
	 * find a list of groups the input group is depending on
	 * @return
	 */
	public List<Group<IJstType>> findGroupDependencies(final String groupName) {
		GroupDependencyNode<IJstType> node = m_ts.getGroupDependencyNode(groupName);
		
		List<Group<IJstType>> listGrp = new ArrayList<Group<IJstType>>();
		
		if (node != null) {
			for (GroupDependencyNode<IJstType> dependency : node.getDependencies().values()) {
				listGrp.add(dependency.getGroup());
			}
		}
		
		return listGrp;
	}
	
	public IJstType findGlobalType(final String groupName, final String typeName) {
		return m_ts.getGlobalType(groupName, typeName);		
	}
	
	public IJstNode findGlobalVar(final String groupName, final String ptyName, final boolean recursive) {
		IJstNode var = m_ts.getGlobalVar(groupName, ptyName);
		if (var != null) {
			return var;
		}
		if (!recursive) {
			return null;
		}
		IGroup<IJstType> group = m_ts.getGroup(groupName);
		if(group==null){
			return null;
		}
		for (IGroup<IJstType> depGrp : group.getGroupDependency()) {
			var = m_ts.getGlobalVar(depGrp.getName(), ptyName);
			if (var != null) {
				return var;
			}
		}
		return null;
	}
	
	public List<IJstNode> getAllGlobalVars() {
		return m_ts.getAllGlobalVars();
	}
	
	public List<IJstNode> getAllGroupScopedGlobalVars(String groupName) {	
		return m_ts.getAllVisibleGlobals(m_ts.getGroup(groupName));
	}
	
	
	public List<IJstNode> getAllGlobalVars(String groupName) {	
		return m_ts.getAllGlobalVars(groupName);
	}

	/**
	 * Answer a list of types that need the given type name
	 * @param typeName TypeName
	 * @return List<IJstType>
	 */
	public List<IJstType> findNeeded(final TypeName typeName){
		try {
			m_locker.lockShared();
			if (typeName == null){
				return Collections.emptyList();
			}
			return m_ts.getDirectDependents(typeName);
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of types that directly extend the type with given name
	 * @param typeName TypeName
	 * @return List<IJstType>
	 */
	public List<IJstType> findSubTypes(final TypeName typeName){
		try {
			m_locker.lockShared();
			IJstType type = m_ts.getType(typeName);
			if (type == null){
				return Collections.emptyList();
			}
			List<IJstType> dependents = m_ts.getDirectDependents(typeName);
			if (dependents.isEmpty()){
				return Collections.emptyList();
			}
			List<IJstType> subTypes = new ArrayList<IJstType>();
			for (IJstType dType: dependents){
				if (checkExtendsOrSatisfies(dType.getExtends(), type)) {
					subTypes.add(dType);
				}
			}
			return subTypes;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of types that are direct or indirect sub types or direct dependents of the type with given name.
	 * When the type with typeName is changed, all direct dependents and all indirect sub types need to be resolved binding
	 * again as they may refer to the old mtd/pty of the changed type.  
	 * 
	 * @param typeName TypeName
	 * @return List<IJstType>
	 */
	public List<IJstType> findAllDependentTypes(final TypeName typeName){
		try {
			m_locker.lockShared();
			
			IJstType type = m_ts.getType(typeName);
			
			return collectAllSubTypes(type, true);			
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of types that directly or indirectly extend the type with given name
	 * @param IJstType type
	 * @return List<IJstType>
	 */
	public List<IJstType> findAllSubTypes(final TypeName typeName) {
		try {
			m_locker.lockShared();
		
			IJstType type = m_ts.getType(typeName);
			
			return collectAllSubTypes(type, false);
		}
		finally{
			m_locker.releaseShared();
		}		
	}
	
	// recursively collect all sub types and their sub types of the input type
	//	if includeDependents is true, it also include direct dependents of each sub type 
	//
	private List<IJstType> collectAllSubTypes(final IJstType type, boolean includeDependents) {
		String groupName = "";
		
		if (type.getPackage() != null) {
			groupName = type.getPackage().getGroupName();
		}
		
		String typeName = type.getName();
		
		List<IJstType> dependents = m_ts.getDirectDependents(new TypeName(groupName, typeName));
		
		if (dependents.isEmpty()){
			return Collections.emptyList();
		}
		
		List<IJstType> subTypes = new ArrayList<IJstType>();
		
		for (IJstType dType: dependents){	
		
			if (checkExtendsOrSatisfies(dType.getExtends(), type) || 
				checkExtendsOrSatisfies(dType.getSatisfies(), type)) {
				
				subTypes.add(dType); // add direct sub type
				subTypes.addAll(collectAllSubTypes(dType, includeDependents)); // add all indirect sub types
			}
			else if (includeDependents) {
				subTypes.add(dType);
			}
		}
		return subTypes;
	}
	
	// check if the input type is in the extends or satisfies type list
	//
	private boolean checkExtendsOrSatisfies(List<? extends IJstType> extendsOrSatisfies, IJstType type) {
		
		for (IJstType parentType : extendsOrSatisfies) {
			if (parentType instanceof JstProxyType) {
				parentType = ((JstProxyType)parentType).getType();
			}
			
			if (parentType == type) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Answer a list of types that directly satisfy the type with given name
	 * @param typeName String
	 * @return List<IJstType>
	 */
	public List<IJstType> findSatisfiers(final TypeName typeName){
		try {
			m_locker.lockShared();
			final IJstType type = m_ts.getType(typeName);
			if (type == null || !type.isInterface()){
				return Collections.emptyList();
			}
			List<IJstType> dependents = m_ts.getDirectDependents(typeName);
			if (dependents.isEmpty()){
				return Collections.emptyList();
			}
			List<IJstType> satisfiers = new ArrayList<IJstType>();
			for (IJstType dType: dependents){
				if (checkExtendsOrSatisfies(dType.getSatisfies(),type)){
					satisfiers.add(dType);
				}
			}
			return satisfiers;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of types that are not found in the type space but have dependents
	 * in the type space.
	 * @return List<String>
	 */
	public List<TypeName> findMissingTypes(){
		try {
			m_locker.lockShared();
			List<TypeName> list = new ArrayList<TypeName>();
	
			for (Entry<String,DependencyNode<IJstType>> entry: m_ts.getUnresolvedNodes().entrySet()){
				DependencyNode<IJstType> node = entry.getValue();
				String groupName=null;
				if (node.getEntity().getPackage() != null) {
					groupName = node.getEntity().getPackage().getGroupName();
				}
				// TODO  Added null check for now need to find why this happens
				// found with FindingDomain code
				if(entry!=null && entry.getKey()!=null){
					list.add(new TypeName(groupName, entry.getKey()));
				}
			}

			return list;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a map of types that have unresolved dependency. 
	 * @return Map<String,List<IJstType>> The map key is name of unresolved type, 
	 * value is a list of dependent types
	 */
	public Map<TypeName,List<IJstType>> findUnresolvedDependents(){
		try {
			m_locker.lockShared();
			Map<TypeName,List<IJstType>> map = new LinkedHashMap<TypeName,List<IJstType>>();
			
			for (Entry<String,DependencyNode<IJstType>> entry: m_ts.getUnresolvedNodes().entrySet()){
				List<IJstType> list = map.get(entry.getKey());
				if (list == null){
					list = new ArrayList<IJstType>();
					DependencyNode<IJstType> node = entry.getValue();
					String groupName=null;
					if (node.getEntity().getPackage() != null) {
						groupName = node.getEntity().getPackage().getGroupName();
					}
					map.put(new TypeName(groupName, entry.getKey()), list);
				}
				for (DependencyNode<IJstType> dNode: entry.getValue().getDependents().values()){
					list.add(dNode.getEntity());
				}
			}
			
			return map;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Traverse the JST tree (representing a file/portion of source file) and find out all properties used and where they are used from.
	 * The resulting map will group by property all recognized property 
	 * usages expressions. 
	 * The properties listed would be all (this type+external types). 
	 * The locations listed will be the local inside the jstNode.
	 * The dependency is computed on the fly.
	 * @param jstNode IJstNode the root of the (sub)tree. Normally this would be a JstType variable.
	 * @return Map<PropertyName,List<IJstNode>> map (keyed by global property names) of JST nodes representing locations where usage happens
	 */
	public Map<PropertyName,List<IJstNode>> findPropertyUsagesWithinNode(final IJstNode jstNode){
		try {
			m_locker.lockShared();
			if (jstNode == null){
				return Collections.emptyMap();
			}
			
			PropertyDependencyVisitor visitor = new PropertyDependencyVisitor();
			visitor.setTypeSpace(m_ts);
			
			JstDepthFirstTraversal.accept(jstNode, visitor);
			
			return visitor.getPropertyDependencies();
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of jstNode that depends on the property with given property name.
	 * @param name PropertyName
	 * @return List<IJstNode>
	 */
	public List<IJstNode> findPropertyDependentNodes(final PropertyName name){
		try {
			m_locker.lockShared();
			if (name == null){
				return Collections.emptyList();
			}
			
			PropertyIndex<IJstType,IJstNode> index = m_ts.getPropertyIndex(name.typeName());
			if (index == null){
				return Collections.emptyList();
			}
			
			List<IJstNode> list = new ArrayList<IJstNode>();
			for (IJstNode dNode: index.getDependents(name.propertyName())){
				if (!list.contains(dNode)){
					list.add(dNode);
				}
			}
			
			return list;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of types that depend on property with given property name
	 * @param name PropertyName
	 * @return List<IJstType>
	 */
	public List<IJstType> findPropertyDependentTypes(final PropertyName name){
		try {
			m_locker.lockShared();
			if (name == null){
				return Collections.emptyList();
			}
			
			PropertyIndex<IJstType,IJstNode> index = m_ts.getPropertyIndex(name.typeName());
			if (index == null){
				return Collections.emptyList();
			}
			
			List<IJstType> list = new ArrayList<IJstType>();
			IJstType ownerType;
			for (IJstNode dNode: index.getDependents(name.propertyName())){
				ownerType = dNode.getOwnerType();
				if (ownerType != null && !list.contains(ownerType)){
					list.add(ownerType);
				}
			}
			
			return list;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of unsolved properties in the type with given name
	 * @param typeName TypeName
	 * @return List<PropertyName>
	 */
	public List<PropertyName> findUnresolvedProperties(final TypeName typeName){
		try {
			m_locker.lockShared();
			if (typeName == null){
				return Collections.emptyList();
			}
			
			List<PropertyName> list = new ArrayList<PropertyName>();
			for (Entry<PropertyName,List<IJstNode>> entry: m_ts.getUnresolvedPropertyDependents().entrySet()){
				for (IJstNode dNode: entry.getValue()){
					if (typeName.typeName().equals(dNode.getOwnerType().getName())){
						list.add(entry.getKey());
						break;
					}
				}
			}
			
			return list;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Traverse the JST tree (representing a file/portion of source file) and find out all methods used and where they are used from.
	 * The resulting map will group by method all recognized method 
	 * invocation expressions. 
	 * The methods listed would be all (this type+external types). 
	 * The locations listed will be the local inside the jstNode.
	 * The dependency is computed on the fly.
	 * @param jstNode IJstNode the root of the (sub)tree. Normally this would be a JstType variable.
	 * @return Map<MethodName,List<IJstNode>> map (keyed by global method names) of JST nodes representing locations where invocation happens
	 */
	public Map<MethodName,List<IJstNode>> findMethodUsagesWithinNode(final IJstNode jstNode){
		try {
			m_locker.lockShared();
			if (jstNode == null){
				return Collections.emptyMap();
			}
			
			MethodDependencyVisitor visitor = new MethodDependencyVisitor();
			visitor.setTypeSpace(m_ts);
			
			JstDepthFirstTraversal.accept(jstNode, visitor);
			
			return visitor.getMethodDependencies();
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of jstNode that depends on the method with given method name.
	 * @param name MethodName
	 * @return List<IJstNode>
	 */
	public List<IJstNode> findMethodDependentNodes(final MethodName name){
		try {
			m_locker.lockShared();
			if (name == null){
				return Collections.emptyList();
			}
			
			MethodIndex<IJstType,IJstNode> index = m_ts.getMethodIndex(name.typeName());
			if (index == null){
				return Collections.emptyList();
			}
			
			List<IJstNode> list = new ArrayList<IJstNode>();
			for (IJstNode dNode: index.getDependents(name.methodName())){
				if (!list.contains(dNode)){
					list.add(dNode);
				}
			}
			
			return list;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of types that depend on method with given method name
	 * @param name MethodName
	 * @return List<IJstType>
	 */
	public List<IJstType> findMethodDependentTypes(final MethodName name){
		try {
			m_locker.lockShared();
			if (name == null){
				return Collections.emptyList();
			}
			
			MethodIndex<IJstType,IJstNode> index = m_ts.getMethodIndex(name.typeName());
			if (index == null){
				return Collections.emptyList();
			}
			
			List<IJstType> list = new ArrayList<IJstType>();
			IJstType ownerType;
			for (IJstNode dNode: index.getDependents(name.methodName())){
				ownerType = dNode.getOwnerType();
				if (ownerType != null && !list.contains(ownerType)){
					list.add(ownerType);
				}
			}
			
			return list;
		}
		finally{
			m_locker.releaseShared();
		}
	}
	
	/**
	 * Answer a list of unsolved methods in the type with given name
	 * @param typeName TypeName
	 * @return List<MethodName>
	 */
	public List<MethodName> findUnresolvedMethods(final TypeName typeName){
		try {
			m_locker.lockShared();
			if (typeName == null){
				return Collections.emptyList();
			}
			
			List<MethodName> list = new ArrayList<MethodName>();
			for (Entry<MethodName,List<IJstNode>> entry: m_ts.getUnresolvedMethodDependents().entrySet()){
				for (IJstNode dNode: entry.getValue()){
					if (typeName.typeName().equals(dNode.getOwnerType().getName())){
						list.add(entry.getKey());
						break;
					}
				}
			}
			
			return list;
		}
		finally{
			m_locker.releaseShared();
		}
	}

	/**
	 * compute the index for the type (cannot rely on indexes as they may not exist yet).
	 * the map per OUR type's declared symbols referenced by EXTERNAL (this+other type) nodes.
	 * @param type
	 * @return
	 */
	public Map<MethodName, List<IJstNode>> buildReferencesForTypeMethods(IJstType type) {
		Map<MethodName, List<IJstNode>> usages = new HashMap<MethodName, List<IJstNode>>();
		build_references_for_type_symbols(type, usages, true);
		return usages;
	}
	

	/**
	 * compute the index for the type (cannot rely on indexes as they may not exist yet).
	 * the map per OUR type's declared symbols referenced by EXTERNAL (this+other type) nodes.
	 * @param type
	 * @return
	 */
	public Map<PropertyName, List<IJstNode>> buildReferencesForTypeProperties(IJstType type) {
		Map<PropertyName, List<IJstNode>> usages = new HashMap<PropertyName, List<IJstNode>>();
		build_references_for_type_symbols(type, usages, false);
		return usages;
	}
	
	/**
	 * FIXME: why have separate but identical classes MethodName and PropertyName??  It just leads to bad code below. 
	 * @param <T>
	 * @param type
	 * @param map
	 * @param for_methods
	 */
	@SuppressWarnings("unchecked")
	private <T extends ISymbolName> void build_references_for_type_symbols(IJstType type, Map<T, List<IJstNode>> map, boolean for_methods) {
		
		// for all types in typespace, including the type in question
		for (IJstType t: m_ts.enumerateTypes()) {
			
			// find out which properties/methods the type t uses and by which syntactic node
			Map<T, List<IJstNode>> usages = (Map<T, List<IJstNode>>)(for_methods?
					findMethodUsagesWithinNode(t) :
					findPropertyUsagesWithinNode(t));

			for(Entry<? extends ISymbolName,List<IJstNode>> entry: usages.entrySet()){
				ISymbolName symbol = entry.getKey(); // the symbol being referenced

				if (!symbol.getOwnerTypeName().equals(type.getName())) { 
					// only interesed in symbols declared by the type in arg
					continue;
				}
				List<IJstNode> l = map.get(symbol);
				if (l == null) {
					l = new ArrayList<IJstNode>();
					map.put((T)symbol, l);
				}
				l.addAll(entry.getValue());
			}
		}
	}
	
	public boolean hasGlobalExtension(String globalVarName) {
		return m_ts.hasGlobalExtension(globalVarName);
	}
	
	public List<IJstNode> getGlobalExtensions(String globalVarName) {
		return m_ts.getGlobalExtensions(globalVarName);
	}

	public Map<String, IJstType> findAllVisibleAliasNames(String groupName) {
		Group<IJstType> fromGroup = m_ts.getGroup(groupName);
		return m_ts.getAllVisibleAliasNames(fromGroup);
	}
}
