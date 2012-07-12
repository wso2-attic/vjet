/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.graph;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.Z;

/**
 * A node in dependency graph. It holds on the nodes this node
 * depends and the node that depend on this node.
 *
 * @param <E> the entity this node represents
 */
public class DependencyNode<E> implements IDependencyNode<E> {

	private final String m_name;
	private final E m_entity;
	
	private Map<String,DependencyNode<E>> m_dependencies;
	
	private Map<String,DependencyNode<E>> m_dependents;
	
	private DependencyGraph<E> m_graph;
	
	//
	// Constructor
	//
	public DependencyNode(final String name, final E entity, DependencyGraph<E> graph){
		assert name != null : "name cannot be null";
		assert entity != null : "entity cannot be null";
		//assert graph != null : "graph cannot be null"; 
		//graph can be null for unresolved types as they are global to type space, not to the graph
		m_name = name;
		m_entity = entity;
		m_graph = graph;
	}
	
	//
	// Satisfying IDependencyNode
	//
	/**
	 * @see IDependencyNode#getName()
	 */
	public String getName(){
		return m_name;
	}
	
	/**
	 * @see IDependencyNode#getEntity()
	 */
	public E getEntity(){
		return m_entity;
	}
		
	/**
	 * @see IDependencyNode#getDependencies()
	 */
	public synchronized Map<String,DependencyNode<E>> getDependencies(){
		if (m_dependencies == null || m_dependencies.isEmpty()){
			return Collections.emptyMap();
		}
		else {
			return Collections.unmodifiableMap(m_dependencies);
		}
	}

	/**
	 * @see IDependencyNode#getDependents()
	 */
	public synchronized Map<String,DependencyNode<E>> getDependents(){
		if (m_dependents == null || m_dependents.isEmpty()){
			return Collections.emptyMap();
		}
		else {
			return Collections.unmodifiableMap(m_dependents);
		}
	}
	
	//
	// API
	//
	public synchronized void addDependency(DependencyNode<E> node){
		assert node != null : "node cannot be null";
		if (m_dependencies == null){
			m_dependencies = new LinkedHashMap<String,DependencyNode<E>>();
		}
		m_dependencies.put(node.getName(), node);
	}
	
	public synchronized void removeDependency(DependencyNode<E> node){
		assert node != null : "node cannot be null";
		if (m_dependencies != null){
			m_dependencies.remove(node.getName());
		}
	}
	
	public synchronized void clearAllDependencies() {
		if (m_dependencies != null) {
			m_dependencies.clear();
		}
	}
	
	public synchronized void addDependent(DependencyNode<E> node){
		assert node != null : "node cannot be null";
		if (m_dependents == null){
			m_dependents = new LinkedHashMap<String,DependencyNode<E>>();
		}
		m_dependents.put(node.getName(), node);
	}
	
	public synchronized void removeDependent(DependencyNode<E> node){
		assert node != null : "node cannot be null";
		if (m_dependents != null){
			m_dependents.remove(node.getName());
		}
	}
	
	public DependencyGraph<E> getGraph(){
		return m_graph;
	}
	
	public DependencyGraph<E> setGraph(DependencyGraph<E> graph){
		return m_graph = graph;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("Node: " + m_name);
		if (m_dependencies != null){
			z.format("   Dependencies:");
			for (String key: getDependencies().keySet()){
				z.format("   - " + key);
			}
		}
		if (m_dependents != null){
			z.format("   Dependents:");
			for (String key: getDependents().keySet()){
				z.format("   - " + key);
			}
		}
		return z.toString();
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || !IDependencyNode.class.isAssignableFrom(o.getClass())){
			return false;
		}
		DependencyNode that = (DependencyNode)o;
		if (getName() == null && that.getName() != null){
			return false;
		}
		return getName().equals(that.getName());
	}
	
	@Override
	public int hashCode(){
		String name = getName();
		return name == null ? 0 : name.hashCode();
	}
}
