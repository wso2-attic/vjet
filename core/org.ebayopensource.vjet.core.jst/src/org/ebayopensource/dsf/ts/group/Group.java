/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.graph.DependencyGraph;
import org.ebayopensource.dsf.ts.graph.DependencyNode;
import org.ebayopensource.dsf.ts.graph.IDependencyCollector;
import org.ebayopensource.dsf.common.Z;

/**
 * Default implementation of <code>IGroup</code>.
 *
 * @param <E>
 */
public class Group<E> implements IGroup<E> {

	private final String m_name;
	private DependencyGraph<E> m_graph;
	private List<IGroup<E>> m_directGroupDependency = new CopyOnWriteArrayList<IGroup<E>>();
	private TypeSpace<E,?> m_typeSpace;
	
	// entities is the map of nodes that this Group owns.
	private final Map<String,E> m_entities;
	
	// user objects associated with the entities stored in group
	private final Map<String,Object> m_userObjects;
	private Map<String, E> m_aliasTypeNames;
	
	public static final String DEFAULT_GRP_NAME = "DefaultGroup";	
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param name String
	 * @param builder IDependencyBuilder<E>
	 * @param typeSpace TypeSpace<E>
	 */
	public Group(final String name, final IDependencyCollector<E> builder){
		assert name != null : "name cannot be null";
		assert builder != null : "builder cannot be null";
		m_name = name;
		m_graph = new DependencyGraph<E>(builder, this);
		m_entities = new LinkedHashMap<String,E>();
		m_userObjects = new LinkedHashMap<String,Object>();
	}
	
	//
	// Satisfying ITypeGroup
	//
	/**
	 * @see IGroup#getName()
	 */
	@Override
	public String getName(){
		return m_name;
	}
	
	/**
	 * @see IGroup#getEntity(String)
	 */
	@Override
	public synchronized E getEntity(final String name){
		return getEntities().get(name);
	}
	
	/**
	 * @see IGroup#getEntities()
	 */
	@Override
	public Map<String,E> getEntities(){
		return Collections.unmodifiableMap(m_entities);
	}
	
	/**
	 * @see IGroup#getGraph()
	 */
	@Override
	public DependencyGraph<E> getGraph(){
		return m_graph;
	}
	
	/**
	 * @see IGroup#getGroupDependency()
	 */
	@Override
	public List<IGroup<E>> getGroupDependency() {
		List<IGroup<E>> all = new ArrayList<IGroup<E>>();
		collectDependency(this, all);
		return Collections.unmodifiableList(all);
	}
	
	/**
	 * @see IGroup#getDirectGroupDependency()
	 */
	@Override
	public List<IGroup<E>> getDirectGroupDependency() {
		return Collections.unmodifiableList(m_directGroupDependency);
	}
	
	/**
	 * @see IGroup#isDependOn(IGroup<E>)
	 */
	@Override
	public boolean isDependOn(final IGroup<E> group) {
		return isDependOn(this, group, new ArrayList<IGroup<E>>());
	}
	
	/**
	 * @see IGroup#isDirectlyDependOn(IGroup<E>)
	 */
	@Override
	public boolean isDirectlyDependOn(final IGroup<E> group) {
		if (group == null){
			return false;
		}
		if (m_directGroupDependency.isEmpty()){
			return false;
		}
		return m_directGroupDependency.contains(group);
	}
	
	/**
	 * @see IGroup#addGroupDependency(IGroup)
	 */
	@Override
	public Group<E> addGroupDependency(final IGroup<E> group){
		if (group != null && !m_directGroupDependency.contains(group)){
			m_directGroupDependency.add(group);
		}
		return this;
	}
	
	/**
	 * @see IGroup#removeGroupDependency(IGroup)
	 */
	@Override
	public Group<E> removeGroupDependency(final IGroup<?> group){
		if (group != null){
			m_directGroupDependency.remove(group);
		}
		return this;
	}
	
	//
	// API
	//
	/**
	 * Support creating group from within the graph
	 * @param graph DependencyGraph<E>
	 */
	public void setGraph(DependencyGraph<E> graph){
		m_graph = graph;
	}
	
	/**
	 * Add given type to the group and associated dependency graph
	 * @param name String
	 * @param entity E
	 */
	public boolean addEntity(final String name, final E entity){
		return addEntity(name, entity, true);
	}
	
	public boolean setUserObject(final String name, final Object userObj){
		synchronized (this){
			m_userObjects.put(name, userObj);
		}
		return true;
	}
	
	public synchronized Object getUserObject(final String name){
		return m_userObjects.get(name);
	}
	
	public void setGroupNameInJstType(E type) {
		
		if (type instanceof JstType) {
			JstType jstType = (JstType)type;
			JstPackage jstPackage = jstType.getPackage();
			String grpName = getName();
		
			if (jstPackage == null && grpName != null) {			
				jstPackage = new JstPackage();
				jstPackage.setGroupName(grpName);
				((JstType)type).setPackage(jstPackage);
			}
			else if (jstPackage != null) {
				if (jstPackage.getGroupName() == null || !jstPackage.getGroupName().equals(grpName)) {
					jstPackage.setGroupName(grpName);
				}
			}	
		}
	}
	
	public boolean addEntity(final String name, final E entity, final boolean buildDependency) {
		synchronized (this){
			m_entities.put(name,entity);
		}
		
		setGroupNameInJstType(entity);
		setAliasTypeNameInGroup(entity);
		DependencyNode<E> node = null;
		if (m_typeSpace != null) {
			node = m_typeSpace.getUnresolvedNodes().get(name);
		}
		if (node != null){
			synchronized (this){
				m_typeSpace.removeUnresolvedNode(name);
			}
			
			// unresolved node may have null graph
			if (node.getGraph() == null) {
				node.setGraph(m_graph);
			}
			
			// clear this type's direct dependency nodes as they may be obsolete
			node.clearAllDependencies();
			
			m_graph.addNode(name, node);
		}
		
		if (buildDependency) {
			m_graph.addToGraph(name, entity);
		}
		return true;
	}
	
	private void setAliasTypeNameInGroup(E entity) {
		if(entity instanceof JstType){
			JstType type = (JstType)entity;
			if(type.getAliasTypeName()!=null){
			    addAliasTypeName(type.getAliasTypeName(),entity);
			}
		}
		
	}

	private void addAliasTypeName(String aliasTypeName, E type) {
		if(m_aliasTypeNames==null){
			m_aliasTypeNames = new HashMap<String, E>();
		}
		m_aliasTypeNames.put(aliasTypeName,type);
		
	}
	

	/**
	 * Add given entities to the group and associated dependency graph
	 * @param entities Map<String,E>
	 */
	public void addEntities(final Map<String,? extends E> entities){
		addEntities(entities, true);
	}
	
	public void addEntities(final Map<String,? extends E> entities, final boolean buildDependency){
		if (entities == null || entities.isEmpty()){
			return;
		}
		synchronized (this){
			for (Entry<String,? extends E> entry: entities.entrySet()){
				addEntity(entry.getKey(), entry.getValue(), buildDependency);
			}
		}
	}
	
	/**
	 * Update internals to use the new name of the entity.
	 * @param oldName String
	 * @param newName String
	 */
	public void renameEntity(final String oldName, final String newName){
		final E entity = getEntity(oldName);
		final Object userObj = getUserObject(oldName);
		if (entity == null){
			return;
		}
		
		synchronized (this){
			m_entities.remove(oldName);
			m_entities.put(newName, entity);
			
			if (userObj != null) {
				m_userObjects.remove(oldName);
				m_userObjects.put(newName, userObj);
			}
		}
		
		getGraph().renameNode(oldName, newName);
	}
	
	/**
	 * Add given type to the group and associated dependency graph
	 * @param name String
	 */
	public void removeEntity(final String name){
		final E entity = getEntity(name);
		final Object userObj = getUserObject(name);
		if (entity == null){
			return;
		}
		synchronized (this){
			m_entities.remove(name);
			
			if (userObj != null) {
				m_userObjects.remove(name);
			}
		}
		
		getGraph().removeDependencyNode(name);
	}
	
	/**
	 * Put this group into given type space
	 * @param typeSpace TypeSpace
	 */
	public void setTypeSpace(final TypeSpace<E, ?> typeSpace){
		m_typeSpace = typeSpace;
	}
	
	/**
	 * Answer the type space this group belongs to
	 * @return TypeSpace
	 */
	public TypeSpace<E, ?> getTypeSpace(){
		return m_typeSpace;
	}
	
	public boolean isReadOnly() {
		return false;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("Group: ", m_name);
		z.format(m_graph.toString());
		return z.toString();
	}
	
	//
	// Private
	//
	private void collectDependency(final IGroup<E> group, final List<IGroup<E>> all){
		for (IGroup<E> g: group.getDirectGroupDependency()){
			if (g == this || all.contains(g)){
				continue;
			}
			all.add(g);
			collectDependency(g, all);
		}
	}
	
	private boolean isDependOn(final IGroup<E> fromGroup, final IGroup<E> toGroup, final List<IGroup<E>> alreadyVisited) {
		if (fromGroup == null){
			throw new IllegalArgumentException("fromGroup is null");
		}
		if (toGroup == null){
			return false;
		}
		//added by huzhou@ebay.com for fromGroup == toGroup
		if (fromGroup.equals(toGroup) || fromGroup.isDirectlyDependOn(toGroup)){
			return true;
		}
		alreadyVisited.add(this);
		for (IGroup<E> g: fromGroup.getDirectGroupDependency()){
			if (alreadyVisited.contains(g)){
				continue;
			}
			if (isDependOn(g, toGroup, alreadyVisited)){
				return true;
			}
		}
		return false;
	}

	public Map<String, E> getAliasTypeNames() {
		if(m_aliasTypeNames==null){
			return Collections.EMPTY_MAP;
		}
		return m_aliasTypeNames;
	}
}
