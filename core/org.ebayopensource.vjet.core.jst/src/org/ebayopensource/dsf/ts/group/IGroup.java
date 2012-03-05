/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.group;

import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.graph.IDependencyGraph;

/**
 * An abstraction of project or library.
 * 
 * @param <E>
 *            The type of the entities the group contains
 */
public interface IGroup<E> {

	/**
	 * Answer the name of the group
	 * 
	 * @return String
	 */
	String getName();

	/**
	 * Answer the entity with given name.
	 * 
	 * @param name
	 *            String
	 * @return E
	 */
	E getEntity(final String name);

	/**
	 * Answer a map of entities in this group. The map is key by type name.
	 * 
	 * @return Map<String,E>
	 */
	Map<String, E> getEntities();

	/**
	 * Answer the type dependency graph of this group
	 * 
	 * @return IDependencyGraph<E>
	 */
	IDependencyGraph<E> getGraph();
	
	/**
	 * Answer an unmodifiable list of groups this group directly depends on
	 * @return List<IGroup<E>>
	 */
	List<IGroup<E>> getDirectGroupDependency();
	
	/**
	 * Answer an unmodifiable list of groups this group depends on, directly or indirectly
	 * @return List<IGroup<E>>
	 */
	List<IGroup<E>> getGroupDependency();
	
	/**
	 * Answer whether this group depends on given group directly or indirectly
	 * @param group IGroup<E>
	 * @return boolean
	 */
	boolean isDependOn(IGroup<E> group);
	
	/**
	 * Answer whether this group depends on given group directly
	 * @param group IGroup<E>
	 * @return boolean
	 */
	boolean isDirectlyDependOn(IGroup<E> group);
	
	/**
	 * Add direct dependency on given group 
	 * @param group IGroup<?>
	 * @return Group<E>
	 */
	Group<E> addGroupDependency(final IGroup<E> group);
	
	/**
	 * Remove direct dependency on given group
	 * @param group IGroup<?>
	 * @return Group<E>
	 */
	Group<E> removeGroupDependency(final IGroup<?> group);

	/**
	 * Answer if the group is read only or read/write
	 * 
	 * @return boolean true if read only
	 */
	boolean isReadOnly();

	Map<? extends String, E> getAliasTypeNames();
}
