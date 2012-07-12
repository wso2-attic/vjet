/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.index;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.Z;

/**
* Default implementation of <code>IIndexMap<E,D></code>.
* This class enumerates all objects in the dependency graph and provides name-based lookup.
* The data objects are wrapped with DependencyIndexNode, which contains the list of dependents.
* FIXME: how this is different from DependencyFraph and why do we need complete duplication here?
*
* @param <E,D>
*/
public class DependencyIndexMap<D> implements IDependencyIndexMap<D> {
	
	private final Map<String,DependencyIndexNode<D>> m_nodes = 
		new LinkedHashMap<String,DependencyIndexNode<D>>();

	//
	// Satisfying IIndexMap
	//
	/**
	 * @see IDependencyIndexMap#getDependents(String)
	 */
	public List<D> getDependents(final String name){
		
		DependencyIndexNode<D> node = getNodes().get(name);
		if (node == null){
			return Collections.emptyList();
		}
		return node.getDependents();
	}
	
	//
	// API
	//
	public void addDependent(final String name, final D dependent){
		if (name == null || dependent == null){
			return;
		}
		synchronized(this){
			DependencyIndexNode<D> node = m_nodes.get(name);
			if (node == null){
				node = new DependencyIndexNode<D>(name);
				m_nodes.put(name, node);
			}
			node.addDependent(dependent);
		}
	}
	
	public void addDependents(final String name, final List<D> dependents){
		if (name == null || dependents == null){
			return;
		}
		synchronized(this){
			DependencyIndexNode<D> node = m_nodes.get(name);
			if (node == null){
				node = new DependencyIndexNode<D>(name);
				m_nodes.put(name, node);
//System.out.println("$$$ Creating new node for symbol "+name);				
			}
			node.addDependents(dependents);
		}
	}
	
	public void renameEntity(final String oldName, final String newName){
		if (oldName == null){
			throw new RuntimeException("oldName is null");
		}
		if (newName == null){
			throw new RuntimeException("newName is null");
		}
		synchronized(this){
			m_nodes.put(newName, m_nodes.remove(oldName));
		}
	}
	
	public DependencyIndexNode<D> removeEntity(final String name){
		if (name == null){
			return null;
		}
		synchronized(this){
			return m_nodes.remove(name);
		}
	}
	
	public void addEntity(DependencyIndexNode<D> node) {
		if (node == null)
			return;
		
		synchronized(this){
			m_nodes.put(node.getName(), node);
		}
	}
	
	public void removeDependent(final String name, final D dependent){
		if (name == null || dependent == null){
			return;
		}
		DependencyIndexNode<D> node = getNodes().get(name);
		if (node != null){
			node.removeDependent(dependent);
		}
	}
	
	public void removeDependents(final String name, final List<D> dependents){
		if (name == null || dependents == null || dependents.size() == 0){
			return;
		}
		DependencyIndexNode<D> node = getNodes().get(name);
		if (node != null){
			node.removeDependents(dependents);
		}
	}
	
	public int size(){
		return getNodes().size();
	}
	
	public Map<String,DependencyIndexNode<D>> getMap(){
		return Collections.unmodifiableMap(m_nodes);
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		Map<String,DependencyIndexNode<D>> nodes = getNodes();
		
		z.format("Types: ");
		for (String name: nodes.keySet()){
			z.format(" * " + name+ "=["+nodes.get(name)+"]");
		}
		
		return z.toString();
	}
	
	//
	// Private
	//
	private synchronized Map<String,DependencyIndexNode<D>> getNodes(){
		return Collections.unmodifiableMap(m_nodes);
	}
}
