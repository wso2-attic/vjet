/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.util.CollectionHelper;
import org.ebayopensource.dsf.common.Z;

/**
 * Default implementation of IDependencyGraph<E>.
 * The graph can be internal to a <code>Group<E></code>
 * or standalone. It can also be unidirectional or bidirectional.
 * <P/>
 * Please note that, the map of dependency nodes contains
 * nodes for all entities added to this graph. If this graph 
 * has a owner group which is part of a type space, then the 
 * dependency node map also contain nodes for other entities in the 
 * type space. Nodes for any unresolved entities can be found in
 * the un-resolved node map.
 * <P/>
 * There should be only one dependency node for each entity in one type space.
 * 
 * TODO: add listener in type space for any add/update/delete on entities
 *
 * @param <E>
 * 
 * TODO: consider an option of <E extends IDependency> and put the dependency maintenance inside entity nodes,
 * instead of maintaining wrapper objects DependencyNode. 
 * TODO: add getGroup() method inside entity. Will eliminate expensive lookups.
 */
public class DependencyGraph<E> implements IDependencyGraph<E> {
	
	// entities is the map of nodes that this Graph/Group owns.
	// FIXME: this should not really be here. This map of entities really belong to Group (it is owned by the group)
	// FIX: Moved to Group
	//private final Map<String,E> m_entities;

	// The map of nodes that exists for the purposes of name-based lookups.
	// the nodes themselves have additional dependent-dependency links.
	// the nodes are wrapper objects for entity objects.
	// the entity objects may or may not belong to the same group (belong or not to m_entities).
	private final Map<String,DependencyNode<E>> m_nodes = 
		new LinkedHashMap<String,DependencyNode<E>>();
	
	// the map of nodes for which no group was found to own them
	// FIXME: why cannot this be kept in a global list of unaffiliated entities, and having E.getGroup() return null?
	// there is a global lookup in getNode() anyways.
	// FIX: Moved to TypeSpace
	//private final Map<String,DependencyNode<E>> m_unresolvedNodes = 
		//new LinkedHashMap<String,DependencyNode<E>>();
	
	// the helper object that will tell us all dependencies for any given entity.
	// this is essencially used to bootstrap all depenendencies.
	private final IDependencyCollector<E> m_collector;
	
	// the backreference to the group that holds this graph.
	private final Group<E> m_group;
	
	private Direction m_direction = Direction.BOTH;
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param builder IDependencyBuilder<E> required.
	 */
	public DependencyGraph(final IDependencyCollector<E> builder){
		this(null, builder, null);
	}
	
	/**
	 * Constructor
	 * @param entities Map<String,E> optional
	 * @param builder IDependencyBuilder<E> required.
	 */
	public DependencyGraph(Map<String,E> entities, final IDependencyCollector<E> builder){
		this(entities, builder, null);
	}
	
	/**
	 * Constructor
	 * @param builder IDependencyBuilder<E> required.
	 * @param group Group<E>
	 */
	public DependencyGraph(final IDependencyCollector<E> builder, final Group<E> group){
		this(null, builder, group);
	}
	
	/**
	 * Constructor
	 * @param entities Map<String,E> optional
	 * @param builder IDependencyBuilder<E> required.
	 * @param group Group<E>
	 */
	public DependencyGraph(Map<String,E> entities, final IDependencyCollector<E> builder, final Group<E> group){
		assert builder != null : "builder cannot be null";
		
		if (group == null) { 
			//create default group
			m_group = new Group<E>(Group.DEFAULT_GRP_NAME, builder);
			m_group.setTypeSpace(new TypeSpace()); // create empty type space
			m_group.setGraph(this);
		}
		else {
			m_group = group;
		}		
		
		m_collector = builder;
		
		// add DependencyNodes wrapping the input entities	
		m_group.addEntities(entities, true);		
	}
	
	//
	// Satisfying IDependencyGraph
	//
	/**
	 * @see IDependencyGraph#getEntity(String)
	 */
	public E getEntity(final String name){
		
		return m_group.getEntity(name);
	}
	
	/**
	 * @see IDependencyGraph#getEntities()
	 */
	public Map<String,E> getEntities(){
		return m_group.getEntities();
	}
	
	/**
	 * @see IDependencyGraph#getDirectDependencies(String, boolean)
	 */
	public List<E> getDirectDependencies(final String name, boolean internalOnly){
		
		DependencyNode<E> node = getNode(name);
		if (node == null){
			return Collections.emptyList();
		}
		
		List<E> list = new ArrayList<E>();
		E dEntity;
		for (DependencyNode<E> d: node.getDependencies().values()){
			dEntity = d.getEntity();
			if (!internalOnly || getEntities().containsValue(dEntity)){
				list.add(dEntity);
			}
		}
		
		return list;
	}
	
	/**
	 * @see IDependencyGraph#getIndirectDependencies(String, boolean)
	 */
	public List<E> getIndirectDependencies(final String name, boolean internalOnly){
		
		DependencyNode<E> node = getNode(name);
		if (node == null){
			return Collections.emptyList();
		}
		
		List<E> list = new ArrayList<E>();
		List<DependencyNode<E>> visited = new ArrayList<DependencyNode<E>>();
		visited.add(node);
		collectDependency(node, list, visited, 0, internalOnly);
		
		return list;
	}
	
	/**
	 * @see IDependencyGraph#getAllDependencies(String, boolean)
	 */
	public List<E> getAllDependencies(final String name, boolean internalOnly){
		
		DependencyNode<E> node = getNode(name);
		if (node == null){
			return Collections.emptyList();
		}
		
		return CollectionHelper.merge(
				getDirectDependencies(name, internalOnly), 
				getIndirectDependencies(name, internalOnly));
	}

	/**
	 * @see IDependencyGraph#getDirectDependents(String, boolean)
	 */
	public List<E> getDirectDependents(final String name, boolean internalOnly){
		
		DependencyNode<E> node = getNode(name);
		if (node == null){
			return Collections.emptyList();
		}
		
		List<E> list = new ArrayList<E>();
		E dEntity;
		for (DependencyNode<E> d: node.getDependents().values()){
			dEntity = d.getEntity();
			if (!internalOnly || getEntities().containsValue(dEntity)){
				list.add(dEntity);
			}
		}
		
		return list;
	}
	
	/**
	 * @see IDependencyGraph#getIndirectDependents(String, boolean)
	 */
	public List<E> getIndirectDependents(final String name, boolean internalOnly){
		
		DependencyNode<E> node = getNode(name);
		if (node == null){
			return Collections.emptyList();
		}
		
		List<E> list = new ArrayList<E>();
		List<DependencyNode<E>> visited = new ArrayList<DependencyNode<E>>();
		visited.add(node);
		collectDependents(node, list, visited, 0, internalOnly);
		
		return list;
	}
	
	/**
	 * @see IDependencyGraph#getAllDependents(String, boolean)
	 */
	public List<E> getAllDependents(final String name, boolean internalOnly){
		
		DependencyNode<E> node = getNode(name);
		if (node == null){
			return Collections.emptyList();
		}
		
		return CollectionHelper.merge(
				getDirectDependents(name, internalOnly), 
				getIndirectDependents(name, internalOnly));
	}
	
	//
	// API
	//
	/**
	 * Set dependency direction this graph should build.
	 * Default is both directions
	 * @param direction Direction
	 */
	public void setDirection(final Direction direction){
		m_direction = direction;
	}
	
	/**
	 * Add given entity to the dependency graph
	 * @param name String
	 * @param entity T
	 */
	public boolean addEntity(final String name, final E entity){
		
		return m_group.addEntity(name, entity, true);
	}
	
	/**
	 * Add given entities to the dependency graph
	 * @param entities Map<String,E>
	 */
	public synchronized void addEntities(final Map<String,? extends E> entities){
		
		m_group.addEntities(entities, true);
	}
	
	/**
	 * Update internals to use the new name of the entity
	 * @param oldName String
	 * @param newName String
	 */
	public void renameEntity(final String oldName, final String newName){
	
		m_group.renameEntity(oldName, newName);
	}
	
	public void renameNode(final String oldName, final String newName) {
		DependencyNode<E> node = getNodes().get(oldName);
		if (node != null){
			synchronized (this){
				m_nodes.remove(oldName);
				m_nodes.put(newName, node);
			}
		}
	}
	
	/**
	 * Add dependency from entity A to entity B
	 * @param entityNameA String
	 * @param entityNameB String
	 */
	public void addEntityDependency(final String entityNameA, final String entityNameB){
		DependencyNode<E> aNode = getNode(entityNameA);
		if (aNode == null){
			throw new RuntimeException("entity not found for:" + entityNameA);
		}
		DependencyNode<E> bNode = getNode(entityNameB);
		if (bNode == null){
			throw new RuntimeException("entity not found for:" + entityNameB);
		}
		addDependency(aNode, bNode);
	}
	
	/**
	 * Remove dependency from entity A to entity B
	 * @param entityNameA String
	 * @param entityNameB String
	 */
	public void removeEntityDependency(final String entityNameA, final String entityNameB){
		DependencyNode<E> aNode = getNode(entityNameA);
		if (aNode == null){
			throw new RuntimeException("entity not found for:" + entityNameA);
		}
		DependencyNode<E> bNode = getNode(entityNameB);
		if (bNode == null){
			throw new RuntimeException("entity not found for:" + entityNameB);
		}
		removeDependency(aNode, bNode);
	}
	
	/**
	 * Remove given entity from the dependency graph.
	 * @param name String
	 * @param entity T
	 */
	public void removeEntity(final String name){
		
		m_group.removeEntity(name);
	}
	
	public void removeDependencyNode(final String name) {
		DependencyNode<E> node = getNodes().get(name);
		if (node != null){
			synchronized (this){
				m_nodes.remove(name);
			}
			
			m_group.getTypeSpace().addUnresolvedNode(node);
			
			// Remove this type from the dependent list of all it's dependency nodes
			for (DependencyNode<E> d: node.getDependencies().values()){
				d.removeDependent(node);
			}
		}

		
	}
	
	/**
	 * Answer the map of unresolved dependency nodes that are not found
	 * in containg group and type space.
	 * @return Map<String,DependencyNode<E>>
	 */
	public synchronized Map<String,DependencyNode<E>> getUnresolvedNodes(){
		
		return Collections.unmodifiableMap(m_group.getTypeSpace().getUnresolvedNodes());

	}
	
	/**
	 * Answer the group this graph is created for.
	 * @return Group<E> could be not if there is no associated group
	 */
	public Group<E> getGroup(){
		return m_group;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		Collection<DependencyNode<E>> nodes = getNodes().values();
		
		z.format("Types: ");
		for (String name: m_group.getEntities().keySet()){
			z.format(" * " + name);
		}
		
		for (DependencyNode<E> node: nodes){
			z.format(node.toString());
		}
		
		return z.toString();
	}
	
	public void addToGraph(final String name, final E entity){		
		Map<String,E> entity_dependencies = m_collector.getDependency(entity);
		
		DependencyNode<E> node = getNode(name, entity, true);
		for (Entry<String,E> entry: entity_dependencies.entrySet()){
			DependencyNode<E> dependency_node = getNode(entry.getKey(), entry.getValue(), true);
			addDependency(node, dependency_node);
		}
	}
	
	public void addImplicitDependency(final String name, final E entity, final String dependencyGrpName, 
										final String dependencyName, final E dependency) {
		
		DependencyNode<E> node = getNode(name, entity, true);
		
		Group<E> group = m_group.getTypeSpace().getGroup(dependencyGrpName);
		
		DependencyNode<E> dependencyNode = group.getGraph().getNode(dependencyName, dependency, true);
		
		addDependency(node, dependencyNode);
	}
	
	//
	// Private
	//
	
	private synchronized Map<String,DependencyNode<E>> getNodes(){
		return Collections.unmodifiableMap(m_nodes);
	}
	
	private DependencyNode<E> getNode(final String name){
		return getNode(name, getEntity(name), false);
	}
	
	// lookup the node that wraps the given entity, and if necessary, create a new wrapper node.
	private DependencyNode<E> getNode(final String name, final E entity, boolean create){
		// FIXME: here we do full scan (search map by value)
		Map<String,E> entities = m_group.getEntities();
		
		if (entity != null && entities.containsValue(entity)){  // we own this entity
			DependencyNode<E> node = getNodes().get(name);
			if (node == null && create){
				node = new DependencyNode<E>(name, entity, this);
				addNode(name, node);
			}
			return node;
		}
		
		// we don't own this entity in current group, search dependency groups
		//
		Group<E> group = m_group.getTypeSpace().getDependencyGroup(m_group.getName(), entity);
		
		// FIXME here, we do global lookup via X full scans (search maps by value, in a loop)
		if (group == null) {
			group = m_group.getTypeSpace().getGroup(entity);
		}
		
		if (group != null){
			// FIXME here, we do another full scan
			return group.getGraph().getNode(name, entity, create);
		}
		
		// we couldn't do global search and this is not our entity.
		// add it to unresolved.
		DependencyNode<E> node = null;

		node = (DependencyNode<E>)m_group.getTypeSpace().getUnresolvedNodes().get(name);
		if (node == null && create) {
			node = new DependencyNode<E>(name, entity, null);
			m_group.getTypeSpace().addUnresolvedNode(node);
		}
		
		return node;
	}
	
	/**
	 * Add dependency from node A to node B
	 * @param aNode IDependencyNode<E> node A
	 * @param bNode IDependencyNode<E> node B
	 */
	private void addDependency(final DependencyNode<E> aNode, final DependencyNode<E> bNode){
		
		if (aNode == null){
			throw new RuntimeException("aNode is null");
		}
		
		if (bNode == null){
			throw new RuntimeException("bNode is null");
		}
		
		if (m_direction != Direction.DEPENDENT_ONLY){
			aNode.addDependency(bNode);
		}
		
		if (m_direction != Direction.DEPENDENCY_ONLY){
			bNode.addDependent(aNode);
		}
	}
	
	/**
	 * Remove dependency from node A to node B
	 * @param aNode IDependencyNode<E> node A
	 * @param bNode IDependencyNode<E> node B
	 */
	private void removeDependency(final DependencyNode<E> aNode, final DependencyNode<E> bNode){
		
		if (aNode == null){
			throw new RuntimeException("aNode is null");
		}
		
		if (bNode == null){
			throw new RuntimeException("bNode is null");
		}
		
		if (m_direction != Direction.DEPENDENT_ONLY){
			aNode.removeDependency(bNode);
		}
		
		if (m_direction != Direction.DEPENDENCY_ONLY){
			bNode.removeDependent(aNode);
		}
	}
	
	public synchronized void addNode(String name, DependencyNode<E> node) {
		m_nodes.put(name, node);
	}
	
	private void collectDependency(DependencyNode<E> node, 
			List<E> collector, List<DependencyNode<E>> visited, int level, boolean internalOnly){

		E dEntity;
		for (DependencyNode<E> d: node.getDependencies().values()){
			if (visited.contains(d)){
				continue;
			}
			if (level > 0){
				dEntity = d.getEntity();
				if (!internalOnly || getEntities().containsValue(dEntity)){
					collector.add(dEntity);
				}
			}
			visited.add(d);
			collectDependency(d, collector, visited, level+1, internalOnly);
		}
	}
	
	private void collectDependents(DependencyNode<E> node, 
			List<E> collector, List<DependencyNode<E>> visited, int level, boolean internalOnly){

		E dEntity;
		for (DependencyNode<E> d: node.getDependents().values()){
			if (visited.contains(d)){
				continue;
			}
			if (level > 0){
				dEntity = d.getEntity();
				if (!internalOnly || getEntities().containsValue(dEntity)){
					collector.add(dEntity);
				}
			}
			visited.add(d);
			collectDependents(d, collector, visited, level+1, internalOnly);
		}
	}
	
	//
	// Inner
	//
	public static enum Direction {
		DEPENDENCY_ONLY,
		DEPENDENT_ONLY,
		BOTH
	}
}
