/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node.visitor;

import org.ebayopensource.dsf.common.enums.BaseEnum;

/**
 * Set of Enums used to indicate how the component graph traversals should
 * proceed (or not) after one of the visitation methods was invoked.
 * 
 * @see ComponentVisitor
 * @see ComponentHandler
 * @see ComponentHandlingStrategy
 * @see BreadthFirstTraversal
 * @see DepthFirstTraversal
 * @see PreOrderTraversal
 */
public class DNodeVisitStatus extends BaseEnum {

	private static final long serialVersionUID = 1L;

	private DNodeVisitStatus(final int id, final String name) {
		super(id, name);
	}

	/**
	 * Continue traversing as normal
	 */
	public static final DNodeVisitStatus CONTINUE = 
		new DNodeVisitStatus(0, "CONTINUE");

	/**
	 * Abort the current node, but continue traversal of its children
	 */
	public static final DNodeVisitStatus ABORT_CURRENT_NODE = 
		new DNodeVisitStatus(1, "ABORT_CURRENT_NODE");

	/**
	 * Abort the current node and don't traverse any of its children
	 */
	public static final DNodeVisitStatus ABORT_SUBTREE = 
		new DNodeVisitStatus(2, "ABORT_SUBTREE");

	/**
	 * Will visit the current node, but not traverse its children
	 */
	public static final DNodeVisitStatus STOP_SUBTREE_TRAVERSAL = 
		new DNodeVisitStatus(3, "STOP_SUBTREE_TRAVERSAL");	
}
