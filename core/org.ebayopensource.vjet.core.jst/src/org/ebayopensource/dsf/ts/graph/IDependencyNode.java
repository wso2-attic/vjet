/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.graph;

import java.util.Map;

public interface IDependencyNode<E> {
	
	/**
	 * Answer the name of this node
	 * @return String
	 */
	String getName();
	
	/**
	 * Answer the entity this node is for
	 * @return E
	 */
	E getEntity();
	
	/**
	 * Answer what this node depends on
	 * @return Map<String,IDependencyNode<E>> empty if none
	 */
	Map<String,? extends IDependencyNode<E>> getDependencies();
	
	/**
	 * Answer what depends on this
	 * @return Map<String,IDependencyNode<E>> empty if none
	 */
	Map<String,? extends IDependencyNode<E>> getDependents();
}
