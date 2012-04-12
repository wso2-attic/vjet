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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.util.IdentityHashSet;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.TypeSpaceLocker;
import org.ebayopensource.dsf.ts.graph.DependencyNode;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.group.GroupDependencyNode;
import org.ebayopensource.dsf.ts.group.GroupSymbolMapTable;
import org.ebayopensource.dsf.ts.group.GroupSymbolMapTable.GlobalSymbolMapEntry;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.dsf.ts.index.DependencyIndexNode;
import org.ebayopensource.dsf.ts.method.MethodIndex;
import org.ebayopensource.dsf.ts.method.MethodName;
import org.ebayopensource.dsf.ts.property.PropertyIndex;
import org.ebayopensource.dsf.ts.property.PropertyName;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * Implementation of <code>ITypeSpace<T,D></code>
 * 
 * FIXME 
 * - does this have to be completely generic? why not make it aware of IJstType and IJstNode?
 * - how/why the resonsibilities of this class differ from JstTypeSpaceMgr?
 * 
 * @see ITypeSpace<T,D>
 * 
 * 
 */
public class TypeSpace<T,D> implements ITypeSpace<T,D> {

	private Map<String,Group<T>> m_groups = new LinkedHashMap<String,Group<T>>();
	private Map<Group<T>, GroupSymbolMapTable<T, D>> m_groupSymbolMap = new LinkedHashMap<Group<T>, GroupSymbolMapTable<T, D>>();

	private ASymbolTableManager<T,D> m_propertySymbolTable;
	private ASymbolTableManager<T,D> m_methodSymbolTable;
	
	private Map<PropertyName,DependencyIndexNode<D>> m_unresolvedPtyDependents = 
		new LinkedHashMap<PropertyName,DependencyIndexNode<D>>();
	
	private Map<MethodName,DependencyIndexNode<D>> m_unresolvedMtdDependents = 
		new LinkedHashMap<MethodName,DependencyIndexNode<D>>();
	
	// the map of nodes for which no group was found to own them
	private final Map<String,DependencyNode<T>> m_unresolvedNodes = 
		new LinkedHashMap<String,DependencyNode<T>>();
	
	// store type package names and types within each package
	//
	private Map<String, Map<TypeName, T>> m_packages = new LinkedHashMap<String, Map<TypeName, T>>();

	// map of group dependency node to hold group dependencies
	//
	private Map<String, GroupDependencyNode<T>> m_groupDependency = new LinkedHashMap<String, GroupDependencyNode<T>>();
	
	private final TypeSpaceLocker m_locker;
	
	// temp tables to hold globle names before the groups are created
	//
	
	// group global types
	private Map<String, List<GlobalNameEntry>> m_globalTypeNameTbl = new LinkedHashMap<String, List<GlobalNameEntry>>(); 
	
	// group global members
	private Map<String, List<GlobalNameEntry>> m_globalMemberNameTbl = new LinkedHashMap<String, List<GlobalNameEntry>>();
	
	// group global all type mamber	
	private Map<String, List<GlobalNameEntry>> m_globalAllTypeMemberNameTbl = new LinkedHashMap<String, List<GlobalNameEntry>>();
	
	// group global global type members	only
	private Map<String, List<GlobalNameEntry>> m_globalVarTypeMemberNameTbl = new LinkedHashMap<String, List<GlobalNameEntry>>();
	
	private static enum GlobalNameType { TYPE, MEMBER, ALLMEMBER, GLOBALSONLY };
	
	private static class GlobalNameEntry {
		private String m_shortName;
		private String m_longName;
		private TypeName m_typeName;
		
		GlobalNameEntry(String shortName, String longName) {
			m_shortName = shortName;
			m_longName = longName;
		}
		
		GlobalNameEntry(TypeName typeName) {
			m_typeName = typeName;
		}		
	}
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 */
	public TypeSpace(){
		m_locker = new TypeSpaceLocker();
	}
	
	public void setMethodSymbolTableManager(ASymbolTableManager<T,D> stmgr) {
		m_methodSymbolTable = stmgr;
	}
	
	public void setPropertySymbolTableManager(ASymbolTableManager<T,D> stmgr) {
		m_propertySymbolTable = stmgr;
	}
	
	public ASymbolTableManager<T,D> getMethodSymbolTableManager() {
		return m_methodSymbolTable;
	}
	
	public ASymbolTableManager<T,D> getPropertySymbolTableManager() {
		return m_propertySymbolTable;
	}
	
	//
	// Satisfying ITypeSpace
	//
	/**
	 * @see ITypeSpace#getType(TypeName)
	 */
	public T getType(final TypeName typeName){
		if (typeName == null){
			return null;
		}
		try {
			//m_locker.lockShared();
		
			Group<T> group = getGroup(typeName.groupName());
			if (group == null){
				return null;
			}
			return group.getEntity(typeName.typeName());
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getType(String)
	 */
	public List<T> getType(final String typeName){
		if (typeName == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			T type;
			List<T> list = null;
			for (Group<T> g: m_groups.values()){
				type = g.getEntity(typeName);
				if (type != null){
					if (list == null){
						list = new ArrayList<T>();
					}
					list.add(type);
				}
			}
			if (list == null){
				return Collections.emptyList();
			}
			return list;
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getVisibleGlobal(String, IGroup)
	 */

	public D getVisibleGlobal(final String global, IGroup<T> fromGroup){
		if (global == null || fromGroup ==null){
			return null;
		}

		GroupSymbolMapTable<T, D> map = m_groupSymbolMap.get(fromGroup);
		map.promoteGlobalTypeMembers();
		
		try {
			//m_locker.lockShared();
			if(fromGroup==null){
				return null;
			}
			Group<T> ownerGroup = getGroup(fromGroup.getName());
			if (ownerGroup == null){
				return null;
			}
			if (fromGroup != null && !fromGroup.isDependOn(ownerGroup)){
				return null;
			}
			D globalVar =  m_groupSymbolMap.get(ownerGroup).getGlobalVar(global);
			if(globalVar!=null){
				return globalVar;
			}
			for(IGroup<T> g : ownerGroup.getDirectGroupDependency()){
				map = m_groupSymbolMap.get(g);
				if(map!=null){
					map.promoteGlobalTypeMembers();
					globalVar = map.getGlobalVar(global);
					if(globalVar!=null){
						return globalVar;
					}
				}
			}
			
			 return null;
		}
		finally{
			//m_locker.releaseShared();
		}
	}

	public List<D> getAllVisibleGlobals(IGroup<T> fromGroup){
	
		try {
			//m_locker.lockShared();
			if(fromGroup==null){
				return null;
			}
			
			List<D> globals = new ArrayList<D>();
			Group<T> ownerGroup = getGroup(fromGroup.getName());
			if (ownerGroup == null){
				return null;
			}
			
			globals.addAll(m_groupSymbolMap.get(ownerGroup).getAllGlobalVars());
			for(IGroup<T> g : ownerGroup.getDirectGroupDependency()){
				GroupSymbolMapTable<T, D> groupSymbolMapTable = m_groupSymbolMap.get(g);
				if(groupSymbolMapTable!=null){
					globals.addAll(groupSymbolMapTable.getAllGlobalVars());
				}
			}
			
			return globals;
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	/**
	 * @see ITypeSpace#getVisibleType(TypeName, IGroup)
	 */
	@Override
	public T getVisibleType(final TypeName typeName, IGroup<T> fromGroup){
		if (typeName == null){
			return null;
		}
		try {
			//m_locker.lockShared();
		
			Group<T> ownerGroup = getGroup(typeName.groupName());
			if (ownerGroup == null){
				return null;
			}
			if (fromGroup != null && !fromGroup.isDependOn(ownerGroup)){
				return null;
			}
			return ownerGroup.getEntity(typeName.typeName());
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getVisibleType(String, IGroup)
	 */
	@Override
	public List<T> getVisibleType(final String typeName, IGroup<T> fromGroup){
		if (typeName == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			T type;
			List<T> list = null;
			for (Group<T> g: m_groups.values()){
				type = g.getEntity(typeName);
				if (type != null){
					if (fromGroup != null && !fromGroup.isDependOn(g)){
						continue;
					}
					if (list == null){
						list = new ArrayList<T>();
					}
					list.add(type);
				}
			}
			if (list == null){
				return Collections.emptyList();
			}
			return list;
		}
		finally{
			//m_locker.releaseShared();
		}
	}

	/**
	 * @see ITypeSpace#isTypeVisible(TypeName, IGroup)
	 */
	@Override
	public boolean isTypeVisible(TypeName typeName, IGroup<T> fromGroup) {
		
		if (fromGroup == null){
			throw new RuntimeException("fromGroup is null");
		}
		
		if (typeName == null){
			return false;
		}

		return fromGroup.isDependOn(getGroup(getType(typeName)));
	}	
	
	/**
	 * @see ITypeSpace#isTypeVisible(String, IGroup)
	 */
	@Override
	public boolean isTypeVisible(String typeName, IGroup<T> fromGroup) {
		
		if (fromGroup == null){
			throw new RuntimeException("fromGroup is null");
		}
		
		if (typeName == null){
			return false;
		}
		
		List<T> types = getType(typeName);
		if (types == null || types.isEmpty()){
			return false;
		}

		for (T type: types){
			if (fromGroup.isDependOn(getGroup(type))){
				return true;
			}
		}
		
		return false;
	}	
	
	/**
	 * @see ITypeSpace#getVisibleTypes(IGroup)
	 */
	@Override
	public List<T> getVisibleTypes(final IGroup<T> fromGroup) {
		if (fromGroup == null){
			return Collections.emptyList();
		}
		List<T> list = new ArrayList<T>();
		list.addAll(fromGroup.getEntities().values());
		for (IGroup<T> g: fromGroup.getGroupDependency()){
			list.addAll(g.getEntities().values());
		}
		return list;
	}	
	
	
	/**
	 * @see ITypeSpace#getVisibleTypes(IGroup)
	 */
	@Override
	public Map<TypeName,T> getVisibleTypesMap(final IGroup<T> fromGroup) {
		if (fromGroup == null){
			return Collections.EMPTY_MAP;
		}
		Map<TypeName,T> map = new LinkedHashMap<TypeName,T>();
		
		for (Entry<String,T> entry: fromGroup.getGraph().getEntities().entrySet()){
			map.put(new TypeName(fromGroup.getName(),entry.getKey()), entry.getValue());
		}
		
		String gName;
		String tName;
		for (IGroup<T> g: fromGroup.getGroupDependency()){
				gName = g.getName();
				for (Entry<String,T> entry: g.getGraph().getEntities().entrySet()){
					tName = entry.getKey();
					map.put(new TypeName(gName,tName), entry.getValue());
				}
			}
		
		return map;
	}	
	
	/**
	 * @see ITypeSpace#getTypes()
	 */
	public Map<TypeName,T> getTypes(){
		try {
			//m_locker.lockShared();
			Map<TypeName,T> map = new LinkedHashMap<TypeName,T>();
			String gName;
			String tName;
			for (Group<T> g: m_groups.values()){
				gName = g.getName();
				for (Entry<String,T> entry: g.getGraph().getEntities().entrySet()){
					tName = entry.getKey();
					map.put(new TypeName(gName,tName), entry.getValue());
				}
			}
			return map;
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getUserObject(final TypeName typeName)
	 */
	public Object getUserObject(final TypeName typeName) {
		if (typeName == null){
			return null;
		}
		try {
			//m_locker.lockShared();
		
			Group<T> group = getGroup(typeName.groupName());
			if (group == null){
				return null;
			}
			return group.getUserObject(typeName.typeName());
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#setUserObject(final TypeName typeName, Object userObj)
	 */
	public boolean setUserObject(final TypeName typeName, Object userObj) {
		if (typeName == null){
			return false;
		}
		try {
			m_locker.lockExclusive();
		
			Group<T> group = getGroup(typeName.groupName());
			if (group == null){
				return false;
			}
			return group.setUserObject(typeName.typeName(), userObj);
		}
		finally{
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * @see ITypeSpace#getGroup(String)
	 */
	public Group<T> getGroup(String groupName){
		if (groupName == null){
			return null;
		}
		try {
			//m_locker.lockShared();
			return m_groups.get(groupName);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	public GroupDependencyNode<T> getGroupDependencyNode(String groupName) {
		if (groupName == null){
			return null;
		}
		try {
			//m_locker.lockShared();
			GroupDependencyNode<T> node = m_groupDependency.get(groupName);
			
			if (node == null) {
				node = new GroupDependencyNode<T>(groupName);
				m_groupDependency.put(groupName, node);
			}
			
			return node;
		}
		finally{
			//m_locker.releaseShared();
		}
		
	}
	
	/**
	 * This is a global lookup operation. 
	 * Searches all known groups (i.e. Eclipse projects) for the given type name.
	 * @see ITypeSpace#getGroup(T)
	 */
	public Group<T> getGroup(final T type){
		if (type == null){
			return null;
		}
		try {
			//m_locker.lockShared();
			for (Group<T> g: m_groups.values()){
				// FIXME: performance penalty - linear search within values
				if (g.getEntities().containsValue(type)){
					return g;
				}
			}
			return null;
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * This is an optimized version than the getGroup(T) which does a global search
	 * It searches all the dependency groups of the input group name
	 */
	public Group<T> getDependencyGroup(final String groupName, final T type) {
		if (type == null){
			return null;
		}
		try {
			//m_locker.lockShared();
			GroupDependencyNode<T> node = getGroupDependencyNode(groupName);
			
			for (GroupDependencyNode<T> d: node.getDependencies().values()) {
				Group<T> group = d.getGroup();
				if (group != null && group.getEntities().containsValue(type)) {
					return group;
				}
			}
			return null;
		}
		finally{
			//m_locker.releaseShared();
		}
		
	}	
	
	
	/**
	 * @see ITypeSpace#getGroups()
	 */
	public Map<String,Group<T>> getGroups(){
		try {
			//m_locker.lockShared();
			return Collections.unmodifiableMap(m_groups);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getDirectDependencies(TypeName)
	 */
	public List<T> getDirectDependencies(final TypeName typeName){
		if (typeName == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			Group<T> g = getGroup(typeName.groupName());
			if (g == null){
				return Collections.emptyList();
			}
			return g.getGraph().getDirectDependencies(typeName.typeName(), false);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getIndirectDependencies(TypeName)
	 */
	public List<T> getIndirectDependencies(final TypeName typeName){
		if (typeName == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			Group<T> g = getGroup(typeName.groupName());
			if (g == null){
				return Collections.emptyList();
			}
			return g.getGraph().getIndirectDependencies(typeName.typeName(), false);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * returns list of types (IJstType nodes only) that this type depends on
	 * @see ITypeSpace#getAllDependencies(TypeName)
	 */
	public List<T> getAllDependencies(final TypeName typeName){
		if (typeName == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			Group<T> g = getGroup(typeName.groupName());
			if (g == null){
				return Collections.emptyList();
			}
			return g.getGraph().getAllDependencies(typeName.typeName(), false);
		}
		finally{
			//m_locker.releaseShared();
		}
	}

	/**
	 * @see ITypeSpace#getDirectDependents(TypeName)
	 */
	public List<T> getDirectDependents(final TypeName typeName){
		if (typeName == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			Group<T> g = getGroup(typeName.groupName());
			if (g == null){
				return Collections.emptyList();
			}
			return g.getGraph().getDirectDependents(typeName.typeName(), false);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getIndirectDependents(TypeName)
	 */
	public List<T> getIndirectDependents(final TypeName typeName){
		if (typeName == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			Group<T> g = getGroup(typeName.groupName());
			if (g == null){
				return Collections.emptyList();
			}
			return g.getGraph().getIndirectDependents(typeName.typeName(), false);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * returns list of types (IJstType nodes only) that depend on this type
	 * @see ITypeSpace#getAllDependents(TypeName)
	 */
	public List<T> getAllDependents(final TypeName typeName){
		if (typeName == null){
			return Collections.emptyList();
		}
		try {
			//m_locker.lockShared();
			Group<T> g = getGroup(typeName.groupName());
			if (g == null){
				return Collections.emptyList();
			}
			return g.getGraph().getAllDependents(typeName.typeName(), false);
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getPropertyIndex(TypeName)
	 */
	public PropertyIndex<T,D> getPropertyIndex(TypeName typeName){
		return (PropertyIndex<T,D>)m_propertySymbolTable.getIndex(typeName);
	}
	
	/**
	 * @see ITypeSpace#getPropertyDependents(PropertyName)
	 */
	public List<D> getPropertyDependents(PropertyName ptyName){
		return m_propertySymbolTable.getDependents(ptyName);
	}
	
	/**
	 * @see ITypeSpace#getUnresolvedPropertyDependents()
	 */
	public Map<PropertyName,List<D>> getUnresolvedPropertyDependents(){
		try {
			//m_locker.lockShared();
			Map<PropertyName,List<D>> map = new HashMap<PropertyName,List<D>>();
			for (Entry<PropertyName,DependencyIndexNode<D>> entry: m_unresolvedPtyDependents.entrySet()){
				map.put(entry.getKey(), entry.getValue().getDependents());
			}
			return map;
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	/**
	 * @see ITypeSpace#getMethodIndex(TypeName)
	 */
	public MethodIndex<T,D> getMethodIndex(TypeName typeName){
		return (MethodIndex<T,D>)m_methodSymbolTable.getIndex(typeName);
	}
	
	/**
	 * @see ITypeSpace#getMethodDependents(MethodName)
	 */
	public List<D> getMethodDependents(MethodName mtdName){
		return m_methodSymbolTable.getDependents(mtdName);
	}
	
	/**
	 * @see ITypeSpace#getUnresolvedMethodDependents()
	 */
	public Map<MethodName,List<D>> getUnresolvedMethodDependents(){
		try {
			//m_locker.lockShared();
			Map<MethodName,List<D>> map = new HashMap<MethodName,List<D>>();
			for (Entry<MethodName,DependencyIndexNode<D>> entry: m_unresolvedMtdDependents.entrySet()){
				map.put(entry.getKey(), entry.getValue().getDependents());
			}
			return map;
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	//
	// API
	//
	/**
	 * Answer the locker for this type space
	 */
	public TypeSpaceLocker getLocker(){
		return m_locker;
	}
	
	/**
	 * Simply add given group to the type space. No update on dependency graphs and indexes.
	 * @param group Group<T>
	 * @return TypeSpace<T,D>
	 * @exception RuntimeException if different group with same name already exists.
	 */
	public TypeSpace<T,D> addGroup(final Group<T> group){
		if (group == null || group.getName() == null){
			return this;
		}
		try {
			m_locker.lockExclusive();
			final String groupName = group.getName();
			if (m_groups.containsKey(groupName)){
				if (m_groups.get(groupName) == group){
					return this;
				}
				else {
					throw new RuntimeException("Group with same name already exists:" + groupName);
				}
			}
			m_groups.put(groupName, group);
			m_groupSymbolMap.put(group, new GroupSymbolMapTable<T, D>(group));
			group.setTypeSpace(this);
			
			// set group dependency node to refer to this group
			GroupDependencyNode<T> node = getGroupDependencyNode(groupName);			
			node.setGroup(group);
			return this;
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * Simply remove group with given name from the type space. No update on type dependency graphs and indexes.
	 * Update group dependency to remove this group from the dependent list of all it's dependency groups
	 * @param groupName String
	 * @return TypeSpace<T,D>
	 */
	public TypeSpace<T,D> removeGroup(final String groupName){
		if (groupName == null){
			return this;
		}
		try {
			m_locker.lockExclusive();
			Group<T> group = m_groups.remove(groupName);
			m_groupSymbolMap.remove(group);
			
			GroupDependencyNode<T> node = getGroupDependencyNode(groupName);
			
			node.setGroup(null); // don't refer to the deleted group
			
			// Remove this node from the dependent list of all it's dependency nodes
			for (GroupDependencyNode<T> d: node.getDependencies().values()){
				d.removeDependent(node);
			}

			return this;
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	public List<String> getPackages() {
		try {
			//m_locker.lockShared();
			List<String> listPackages = new ArrayList<String>();
		
			for (String packageName : m_packages.keySet()){
				listPackages.add(packageName);
			}
			return listPackages;
		}
		finally{
			//m_locker.releaseShared();
		}
	}
	
	public Map<TypeName, T> getTypes(String packageName) {
			
		return m_packages.get(packageName);
	}
		
	
	public List<T> getPackageDependents(String packageName) {
		
		Map <TypeName, T> types = m_packages.get(packageName);
		List<T> dependents = new ArrayList<T>();
		Map<T, T> uniqueDependents = new LinkedHashMap<T, T>();
		
		for (TypeName typeName : types.keySet()) {			
			List<T> typeDependents = getDirectDependents(typeName);
			
			if (typeDependents != null) {
				for (T type : typeDependents) {
					uniqueDependents.put(type, type);
				}
			}
			
			// put current type in the package in the dependent list as well
			//
			T currType = types.get(typeName);
			uniqueDependents.put(currType, currType);
		}
		
		for (T type : uniqueDependents.keySet()) {
			dependents.add(type);
		}
		
		return dependents;
	}
	
	public void addTypeToPackage(String packageName, TypeName typeName, T type) {
		try {
			m_locker.lockExclusive();
			
			Map<TypeName, T> mapTypes = m_packages.get(packageName);
			
			if (mapTypes == null) {
				mapTypes = new LinkedHashMap<TypeName, T>();
				m_packages.put(packageName, mapTypes);
			}

			mapTypes.put(typeName, type);
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	public void removeTypeFromPackage(String packageName, TypeName typeName) {
		try {
			m_locker.lockExclusive();
			
			Map<TypeName, T> mapTypes = m_packages.get(packageName);
			
			if (mapTypes != null) {
				mapTypes.remove(typeName);
				if (mapTypes.isEmpty()) {
					m_packages.remove(packageName);
				}
			}
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * Rename type with given old name to given new name. Internal keys will
	 * be updated accordingly.
	 * @param oldName TypeName
	 * @param newName String
	 * @return T
	 * @exception RuntimeException if type or group is not found 
	 */
	public T renameType(final TypeName oldName, final String newName){
		if (oldName == null || newName == null){
			return null;
		}
		try {
			m_locker.lockExclusive();
			// Update name keys in group/graph
			Group<T> group = getGroup(oldName.groupName());
			if (group == null){
				throw new RuntimeException("cannot find group for type:" + oldName);
			}
			T type = group.getEntity(oldName.typeName());
			if (type == null){
				throw new RuntimeException("cannot find type:" + oldName);
			}
			group.renameEntity(oldName.typeName(), newName);
			
			// Update key in PropertyIndex 
			m_propertySymbolTable.renameType(oldName, newName);
			
			// Update key in MethodIndex
			m_methodSymbolTable.renameType(oldName, newName);
			return type;
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * Simply add property index of given type to the type space. Dependency is not updated.
	 * @param typeName TypeName
	 * @param index PropertyIndex<T,D>
	 * @return TypeSpace<T,D>
	 * @exception RuntimeException if different index for same type name already exists.
	 */
	public TypeSpace<T,D> addPropertyIndex(final TypeName typeName, final PropertyIndex<T,D> index){
		m_propertySymbolTable.addIndex(typeName, index);
		return this;
	}
	
	/**
	 * Rename property with given old name to given new name. Internal keys will
	 * be updated accordingly.
	 * @param oldName PropertyName
	 * @param newName String
	 * @return TypeSpace<T,D>
	 * @exception RuntimeException if index of given type is not found 
	 */
	public void renameProperty(final PropertyName oldName, final String newName){
		if (oldName == null || newName == null){
			return;
		}
		try {
			m_locker.lockExclusive();
			PropertyIndex<T,D> index = getPropertyIndex(oldName.typeName());
			if (index == null){
				throw new RuntimeException("cannot find property index for type:" + oldName.typeName());
			}
			index.renameEntity(oldName.propertyName(), newName);
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * Simply remove index for given type from type space. Dependency is not updated.
	 * @param typeName TypeName
	 * @return TypeSpace<T,D>
	 */
	public TypeSpace<T,D> removePropertyIndex(final TypeName typeName){
		m_propertySymbolTable.removeIndex(typeName);
		return this;
	}
	
	/**
	 * Simply add method index of given type to the type space. Dependency is not updated.
	 * @param typeName TypeName
	 * @param index MethodIndex<T,D>
	 * @return TypeSpace<T,D>
	 * @exception RuntimeException if different index for same type name already exists.
	 */
	public TypeSpace<T,D> addMethodIndex(final TypeName typeName, final MethodIndex<T,D> index){
		m_methodSymbolTable.addIndex(typeName, index);
		return this;
	}
	
	/**
	 * Rename method with given old name to given new name. Internal keys will
	 * be updated accordingly.
	 * @param oldName String
	 * @param newName String
	 * @return TypeSpace<T,D>
	 * @exception RuntimeException if index of given type is not found 
	 */
	public void renameMethod(final MethodName oldName, final String newName){
		if (oldName == null || newName == null){
			return;
		}
		try {
			m_locker.lockExclusive();
			MethodIndex<T,D> index = getMethodIndex(oldName.typeName());
			if (index == null){
				throw new RuntimeException("cannot find method index for type:" + oldName.typeName());
			}
			index.renameEntity(oldName.methodName(), newName);
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * Simply remove method index for given type from type space. Dependency is not updated.
	 * @param typeName TypeName
	 * @return TypeSpace<T,D>
	 */
	public TypeSpace<T,D> removeMethodIndex(final TypeName typeName){
		m_methodSymbolTable.removeIndex(typeName);
		return this;
	}
	
	/**
	 * Add property index node to the unresolved node map if the node still has dependents
	 * @param pty PropertyName
	 * @param indexNode DependencyIndexNode<D>
	 * @return TypeSpace<T,D>
	 */
	public TypeSpace<T,D> addToUnresolvedIndexNode(PropertyName pty, DependencyIndexNode<D> indexNode){
		if (pty == null || indexNode == null){
			return this;
		}
		try {
			m_locker.lockExclusive();
			if (!indexNode.getDependents().isEmpty()){
				m_unresolvedPtyDependents.put(pty, indexNode);
			}
			return this;
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * Add method index node to the unresolved node map if the node still has dependents
	 * @param mtd MethodName
	 * @param indexNode DependencyIndexNode<D>
	 * @return TypeSpace<T,D>
	 */
	public TypeSpace<T,D> addToUnresolvedIndexNode(MethodName mtd, DependencyIndexNode<D> indexNode){
		if (mtd == null || indexNode == null){
			return this;
		}
		try {
			m_locker.lockExclusive();
			if (!indexNode.getDependents().isEmpty()){
				m_unresolvedMtdDependents.put(mtd, indexNode);
			}
			return this;
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	public DependencyIndexNode<D> getUnresolvedIndexNode(MethodName mtd) {
		if (mtd == null){
			return null;
		}
		
		return m_unresolvedMtdDependents.get(mtd);
	}
	
	public DependencyIndexNode<D> getUnresolvedIndexNode(PropertyName pty) {
		if (pty == null){
			return null;
		}
		
		return m_unresolvedPtyDependents.get(pty);
	}
	
	/**
	 * Answer the map of unresolved dependency nodes that are not found
	 * in containg group and type space.
	 * @return Map<String,DependencyNode<T>>
	 */
	public synchronized Map<String,DependencyNode<T>> getUnresolvedNodes(){
		return Collections.unmodifiableMap(m_unresolvedNodes);
	}
	
	public synchronized void removeUnresolvedNode(String name) {
		m_unresolvedNodes.remove(name);
	}
	
	public synchronized void addUnresolvedNode(DependencyNode<T> node){
		m_unresolvedNodes.put(node.getName(), node);
	}

	/**
	 * @see ITypeSpace<T,D>.enumerateTypes();
	 */
	public Iterable<T> enumerateTypes() {
		IdentityHashSet<T> retval = new IdentityHashSet<T>();
		try {
			m_locker.lockExclusive();
			for (Group<T> g: m_groups.values()){
				retval.addAll(g.getEntities().values());
			}
		}
		finally {
			m_locker.releaseExclusive();
		}
		return retval;
	}
	
	private void addToTempGroupGlobalNameTbl(Map<String, List<GlobalNameEntry>> table, String groupName, String shortName, String longName) {
		
		List<GlobalNameEntry> listOfNames = table.get(groupName);
		Map<String,GlobalNameEntry> listOfNamesSet = new LinkedHashMap<String,GlobalNameEntry>();
		
		
		
		if (listOfNames == null) {
			listOfNames = new ArrayList<GlobalNameEntry>();
			table.put(groupName, listOfNames);
		}
		
		GlobalNameEntry globalNameEntry = new GlobalNameEntry(shortName, longName);
		listOfNames.add(globalNameEntry);	
		listOfNamesSet.put(shortName, globalNameEntry);
	}
	
	private void addToTempGroupGlobalVarTypeTable(Map<String, List<GlobalNameEntry>> table, TypeName typeName) {
		
		List<GlobalNameEntry> listOfNames = table.get(typeName.groupName());
		
		if (listOfNames == null) {
			listOfNames = new ArrayList<GlobalNameEntry>();
			table.put(typeName.groupName(), listOfNames);
		}
		
		listOfNames.add(new GlobalNameEntry(typeName));		
	}
	
	
	private void addToTempGroupGlobalNameTbl(Map<String, List<GlobalNameEntry>> table, TypeName typeName) {
		
		List<GlobalNameEntry> listOfNames = table.get(typeName.groupName());
		
		if (listOfNames == null) {
			listOfNames = new ArrayList<GlobalNameEntry>();
			table.put(typeName.groupName(), listOfNames);
		}
		
		listOfNames.add(new GlobalNameEntry(typeName));		
	}
	
	private void addTempGlobalNameToGroup(Group<T> group, Map<String, List<GlobalNameEntry>> nameTable, GlobalNameType type) {
				
		if (group != null) {
			List<GlobalNameEntry> listNames = nameTable.get(group.getName());
			
			if (listNames != null) {
				GroupSymbolMapTable<T, D> groupSymbolTable = m_groupSymbolMap.get(group);
				for (GlobalNameEntry entry : listNames) {
					switch (type) {
					case TYPE:
						groupSymbolTable.addGlobal(entry.m_shortName, entry.m_longName, true);
						break;
					case MEMBER:
						groupSymbolTable.addGlobal(entry.m_shortName, extractTypeName(entry.m_longName));
						break;
					case ALLMEMBER:
						groupSymbolTable.addGlobalType(entry.m_typeName.typeName());
						break;
					default:
						break;
					}					
				}
				
				// Don't clear the global name list so that they can be promoted later if the group is deleted
				//
				//listNames.clear();
				//nameTable.remove(group.getName());
			}
		}			
	}
	
	private synchronized void addAllTempGlobalNameToGroup(Group<T> group) {
			
		addTempGlobalNameToGroup(group, m_globalTypeNameTbl, GlobalNameType.TYPE);
		addTempGlobalNameToGroup(group, m_globalMemberNameTbl, GlobalNameType.MEMBER);
		addTempGlobalNameToGroup(group, m_globalAllTypeMemberNameTbl, GlobalNameType.ALLMEMBER);
		addTempGlobalNameToGroup(group, m_globalVarTypeMemberNameTbl, GlobalNameType.GLOBALSONLY);
		
	}
	
	public void addToGlobalSymbolMap(String groupName, String varName, String typeName, D node) {
		Group<T> group = getGroup(groupName);
		
		try {
			m_locker.lockExclusive();
			
			if (group != null) {
				m_groupSymbolMap.get(group).addGlobal(varName, typeName, node);
			}
		}
		finally {
			m_locker.releaseExclusive();
		}
	}

	/**
	 * @see ITypeSpace<T,D>.addToGlobalTypeSymbolMap(groupName, globalTypeName, fullyQualifiedTypeName)
	 */
	public void addToGlobalTypeSymbolMap(String groupName, String globalTypeName, String fullyQualifiedTypeName) {
		Group<T> group = getGroup(groupName);
		
		try {
			m_locker.lockExclusive();
			
			if (group != null) {
				m_groupSymbolMap.get(group).addGlobal(globalTypeName, fullyQualifiedTypeName, true);
			}
			else { 
				// add to temp group global type name table
				addToTempGroupGlobalNameTbl(m_globalTypeNameTbl, groupName, globalTypeName, fullyQualifiedTypeName);		
			}
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * @see ITypeSpace<T,D>.addToGlobalMemberSymbolMap(groupName, globalMethodName, fullyQualifiedMethodName)
	 */
	public void addToGlobalMemberSymbolMap(String groupName, String globalMemberName, String fullyQualifiedMethodName) {
		Group<T> group = getGroup(groupName);
		
		try {
			m_locker.lockExclusive();
			
			if (group != null) {
				m_groupSymbolMap.get(group).addGlobal(globalMemberName, extractTypeName(fullyQualifiedMethodName));
			}	
			else {
				// add to temp group global method name table
				addToTempGroupGlobalNameTbl(m_globalMemberNameTbl, groupName, globalMemberName, fullyQualifiedMethodName);
			}
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	/**
	 * @see ITypeSpace<T,D>.addAllGlobalTypeMembers(typeName)
	 */
	public void addAllGlobalTypeMembers(TypeName typeName) {
		Group<T> group = getGroup(typeName.groupName());
		
		try {
			m_locker.lockExclusive();
			
			if (group != null) {
				m_groupSymbolMap.get(group).addGlobalType(typeName.typeName());
			}
			else {
				// add to temp group all type member name table
				addToTempGroupGlobalNameTbl(m_globalAllTypeMemberNameTbl, typeName);
			}
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
		
	public List<GlobalSymbolMapEntry<D>> getAllGlobalTypes(String groupName) {		
		if (groupName == null || groupName.length() == 0) {
			return getAllGlobalTypes();
		}		
		Group<T> group = getGroup(groupName);		
		if (group != null) {
			addAllTempGlobalNameToGroup(group); // move all global names to each group
			return getAllGlobalTypes(group);
		}				
		return Collections.emptyList();
	}
	
	public List<GlobalSymbolMapEntry<D>> getAllGlobalTypes() {
		List<GlobalSymbolMapEntry<D>> list = new ArrayList<GlobalSymbolMapEntry<D>>();
		
		for (Group<T> group: m_groups.values()) {
			addAllTempGlobalNameToGroup(group);
			list.addAll(getAllGlobalTypes(group));
		}		
		return list;
	}
	
	public T getGlobalType(String groupName, String typeName) {		
		if (groupName == null || groupName.length() == 0) {
			return getGlobalType(typeName);
		}		
		Group<T> group = getGroup(groupName);		
		if (group != null) {
			addAllTempGlobalNameToGroup(group); // move all global names to each group
			return (T)getGlobalType(group, typeName);
		}				
		return null;
	}
	
	public T getGlobalType(String typeName) {		
		for (Group<T> group: m_groups.values()) {			
			addAllTempGlobalNameToGroup(group);			
			T type = (T)getGlobalType(group, typeName);			
			if (type != null) {
				return type;
			}
		}			
		return null;
	}
	
	public D getGlobalMethod(String groupName, String methodName) {		
		if (groupName == null || groupName.length() == 0) {
			return getGlobalMethod(methodName);
		}		
		Group<T> group = getGroup(groupName);		
		if (group != null) {
			addAllTempGlobalNameToGroup(group); // move all global names to each group
			return (D)getGlobalMethod(group, methodName);
		}				
		return null;
	}
	
	public D getGlobalMethod(String methodName) {		
		for (Group<T> group: m_groups.values()) {			
			addAllTempGlobalNameToGroup(group);			
			D method = (D)getGlobalMethod(group, methodName);			
			if (method != null) {
				return method;
			}
		}			
		return null;
	}
	
	public List<D> getAllGlobalVars(String groupName) {
		if (groupName == null || groupName.length() == 0) {
			return getAllGlobalVars();
		}		
		Group<T> group = getGroup(groupName);		
		if (group != null) {
			addAllTempGlobalNameToGroup(group); // move all global names to each group
			return (List<D>)getAllGlobalVars(group);
		}				
		return Collections.emptyList();
	}
	
	public List<D> getAllGlobalVars() {		
		List<D> list = new ArrayList<D>();		
		for (Group<T> group: m_groups.values()){
			addAllTempGlobalNameToGroup(group);
			list.addAll((List<D>)getAllGlobalVars(group));
		}		
		return list;
	}
	
	public D getGlobalVar(String groupName, String varName) {
		if (groupName == null || groupName.length() == 0) {
			return getGlobalVar(varName);
		}		
		Group<T> group = getGroup(groupName);		
		if (group != null) {
			addAllTempGlobalNameToGroup(group); // move all global names to each group
			return getGlobalVar(group, varName);
		}				
		return null;
	}
	
	public D getGlobalVar(String propertyName) {		
		for (Group<T> group: m_groups.values()) {		
			addAllTempGlobalNameToGroup(group);			
			D property = getGlobalVar(group, propertyName);			
			if (property != null) {
				return property;
			}
		}			
		return null;
	}

	@Override
	public void removeGlobalsFromType(String groupName, String typeName) {
		Group<T> group = getGroup(groupName);		
		try {
			m_locker.lockExclusive();			
			if (group != null) {
				removeTypesGlobals(group, typeName);
			}
		}
		finally {
			m_locker.releaseExclusive();
		}
	}
	
	@Override
	public boolean hasGlobalExtension(String globalVarName) {
		for (GroupSymbolMapTable<T, D> map: m_groupSymbolMap.values()) {
			if (map.hasExtension(globalVarName)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<D> getGlobalExtensions(String globalVarName) {
		List<D> extensions = new ArrayList<D>();
		for (GroupSymbolMapTable<T, D> map: m_groupSymbolMap.values()) {
			if (map.hasExtension(globalVarName)) {
				extensions.addAll(map.getExtensions(globalVarName));
			}
		}
		return extensions;
	}
	
	private List<GlobalSymbolMapEntry<D>> getAllGlobalTypes(Group<T> group) {
		GroupSymbolMapTable<T, D> map = m_groupSymbolMap.get(group);
		map.promoteGlobalTypeMembers();
		return map.getAllGlobalTypes();
	}

	
	private T getGlobalType(Group<T> group, String typeName) {
		GroupSymbolMapTable<T, D> map = m_groupSymbolMap.get(group);
		map.promoteGlobalTypeMembers();
		return map.getGlobalType(typeName);
	}
	
	private D getGlobalMethod(Group<T> group, String methodName) {
		GroupSymbolMapTable<T, D> map = m_groupSymbolMap.get(group);
		map.promoteGlobalTypeMembers();
		return map.getGlobalMethod(methodName);
	}
	
	private List<D> getAllGlobalVars(Group<T> group) {
		GroupSymbolMapTable<T, D> map = m_groupSymbolMap.get(group);
		map.promoteGlobalTypeMembers();
		return map.getAllGlobalVars();
	}
	
	private D getGlobalVar(Group<T> group, String propertyName) {
		GroupSymbolMapTable<T, D> map = m_groupSymbolMap.get(group);
		map.promoteGlobalTypeMembers();
		return m_groupSymbolMap.get(group).getGlobalVar(propertyName);
	}
	
	private void removeTypesGlobals(Group<T> group, String typeName) {
		m_groupSymbolMap.get(group).removeGlobalVarsFromType(typeName);
	}
	
	private static String extractTypeName(String fullyQualifiedName) {
		int idx = fullyQualifiedName.lastIndexOf(".");
		if (idx > 0) {
			return fullyQualifiedName.substring(0, idx);
		}
		return fullyQualifiedName;
	}


	@Override
	public Map<String, T> getAllVisibleAliasNames(IGroup<T> fromGroup) {
		// TODO Auto-generated method stub
		if (fromGroup == null){
			return Collections.EMPTY_MAP;
		}
		Map<String,T> map = new HashMap<String,T>();
		
		map.putAll(fromGroup.getAliasTypeNames());
		for (IGroup<T> g: fromGroup.getGroupDependency()){
			map.putAll(g.getAliasTypeNames());
		}
		return map;
	}
}
