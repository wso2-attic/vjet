/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.common.Z;

/**
 * A node in index map. It holds on the dependents of the entity
 * this node represents.
 *
 * @param <E,D> E is the type of entity this node represents and D is the type of dependents
 */
public class DependencyIndexNode<D> implements IDependencyIndexNode<D> {

	private final String m_name;
	private List<D> m_dependents;
	
	//
	// Constructor
	//
	public DependencyIndexNode(final String name){
		assert name != null : "name cannot be null";
		m_name = name;
	}
	
	//
	// Satisfying IIndexNode
	//
	/**
	 * @see IDependencyIndexNode#getName()
	 */
	public String getName(){
		return m_name;
	}
	
	/**
	 * @see IDependencyIndexNode#getDependents()
	 */
	public synchronized List<D> getDependents(){
		if (m_dependents == null || m_dependents.isEmpty()){
			return Collections.emptyList();
		}
		else {
			return Collections.unmodifiableList(m_dependents);
		}
	}
	
	//
	// API
	//
	public void addDependent(D dependent){
		if (dependent == null){
			return;
		}
		synchronized (this){
			if (m_dependents == null){
				m_dependents = new ArrayList<D>();
			}
			m_dependents.add(dependent);
		}
	}
	
	public void addDependents(List<D> dependents){
		if (dependents == null || dependents.isEmpty()){
			return;
		}
		synchronized (this){
			if (m_dependents == null){
				m_dependents = new ArrayList<D>();
			}
			m_dependents.addAll(dependents);
		}
	}
	
	public void removeDependent(D dependent){
		if (dependent == null || m_dependents == null){
			return;
		}
		synchronized (this){
			m_dependents.remove(dependent);
		}
	}
	/**
	 * remove all matching dependents
	 * @param dependents
	 */
	public void removeDependents(List<D> dependents){
		if (dependents == null || m_dependents == null){
			return;
		}
		synchronized (this){
			for (D d: dependents){
				m_dependents.remove(d);
			}
		}
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("Node: " + m_name);
		if (m_dependents != null){
			z.format("   Dependents:");
			for (D d: m_dependents){
				z.format("   - " + d.toString());
			}
		}
		return z.toString();
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null || o.getClass() != this.getClass()){
			return false;
		}
		DependencyIndexNode that = (DependencyIndexNode)o;
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
