/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.graph;

import java.util.List;
import java.util.Map;

public interface IDependencyGraph<E> {
	
	/**
	 * Answer the entity with given name. 
	 * @param name String
	 * @return E
	 */
	E getEntity(final String name);
	
	/**
	 * Answer the map of entities in this graph
	 * @return Map<String,E>
	 */
	Map<String,E> getEntities();

	/**
	 * Answer the list of entities the entity with given name directly depends on
	 * @param name String
	 * @param internalOnly boolean true - exclude external type
	 * @return List<E>
	 */
	List<E> getDirectDependencies(String name, boolean internalOnly);
	
	/**
	 * Answer the list of entities the entity with given name indirectly depends on
	 * @param name String
	 * @param internalOnly boolean true - exclude external type
	 * @return List<E>
	 */
	List<E> getIndirectDependencies(String name, boolean internalOnly);
	
	/**
	 * Answer the list of all entities the entity with given name depends on
	 * @param name String
	 * @param internalOnly boolean true - exclude external type
	 * @return List<E>
	 */
	List<E> getAllDependencies(String name, boolean internalOnly);
	
	/**
	 * Answer the list of entities that directly depend on the type with given name
	 * @param name String
	 * @param internalOnly boolean true - exclude external type
	 * @return List<E>
	 */
	List<E> getDirectDependents(String name, boolean internalOnly);
	
	/**
	 * Answer the list of entities that indirectly depend on the type with given name
	 * @param name String
	 * @param internalOnly boolean true - exclude external type
	 * @return List<E>
	 */
	List<E> getIndirectDependents(String name, boolean internalOnly);
	
	/**
	 * Answer the list of all entities that depend on the type with given name
	 * @param name String
	 * @param internalOnly boolean true - exclude external type
	 * @return List<E>
	 */
	List<E> getAllDependents(String name, boolean internalOnly);
}
