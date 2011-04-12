/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node.visitor;

import org.ebayopensource.dsf.common.phase.PhaseId;
import org.ebayopensource.dsf.dom.DNode;
/**
 * Defines the strategy on how to traverse the DOM tree. There are some classic 
 * traversal pattern implementations that are provided:
 * <li>Pre-oreder - <code>PreOrderDNodeTraversal</code>
 * <li>Post-order (Depth first) - <code>DepthFirstDNodeTraversal</code>
 * <li>In-order (Breadth first) - <code>BreadthFirstDNodeTraversal</code>
 * And you can also use <code>DefaultDNodeHandlingStrategy</code> which is using
 * depth first as the default pattern.
 * 
 * @see PreOrderDNodeTraversal
 * @see DepthFirstDNodeTraversal
 * @see BreadthFirstDNodeTraversal
 * @see DefaultDNodeHandlingStrategy
 */
public interface IDNodeHandlingStrategy {
	/**
	 * Indicates that in which phase the <code>handle()</code> operation is 
	 * applicable. If returns <code>PhaseId.ANY_PHASE</code>, it means the
	 * operation is applicable in any phase.
	 * 
	 * @return
	 * 		 the <code>PhaseId</code> object indicates which phase is applicable
	 *       for handling
	 */
	PhaseId getApplicablePhaseId();
	
	/**
	 * Defines the traversal strategy. The <code>visitor</code> defines the 
	 * standard opertions when accessing a node on the DOM tree.
	 *  
	 * @param component
	 *        the root node of the whole DOM tree to be traversed
	 * @param visitor
	 *        the <code>visitor</code> used to handle each accessed node 
	 * @see IDNodeVisitor
	 */
	void handle(
		DNode component,
		IDNodeVisitor visitor);

	Object clone() throws CloneNotSupportedException;

}
