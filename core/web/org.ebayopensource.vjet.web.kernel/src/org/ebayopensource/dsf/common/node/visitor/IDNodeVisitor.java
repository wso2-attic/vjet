/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node.visitor;

import org.ebayopensource.dsf.dom.DNode;
/**
 * The node visitor is used to handle the accessed DOM tree. Three visit methods
 * defines in this interface will be invoked by the framework in sequence when a
 * DOM tree passed in. 
 */
public interface IDNodeVisitor {
	/**
	 * The framework will invoke this method prior to calling the
	 * <code>visit()</code> method. The component can not be <code>null</code>
	 * Throwing AbortComponentTraversalException will signal
	 * the framework to abort any further processing on the component.  The
	 * ComponentVisitStatus
	 * @param component
	 *        the component being visited
	 * @return
	 *        The <code>DNodeVisitStatus</code> defining how the remainder 
	 *        of the traversal should countinue or not.
	 * @exception AbortDNodeTraversalException
	 *        will signal the framework to abort any further processing on the 
	 *        component
	 * @see   DNodeVisitStatus
	 * @see   #visit(DNode)
	 */
	DNodeVisitStatus preVisit(DNode component)
		throws AbortDNodeTraversalException;
	
	/**
	 * Be invoked after <code>preVisit()</code>.
	 * 
	 * @param component
	 *        the component being visited
	 * @return
	 *        The <code>DNodeVisitStatus</code> defining how the remainder 
	 *        of the traversal should countinue or not.
	 * @exception AbortDNodeTraversalException
	 *        will signal the framework to abort any further processing on the 
	 *        component
	 * @see   DNodeVisitStatus
	 * @see   #preVisit(DNode)
	 */
	DNodeVisitStatus visit(DNode component)
		throws AbortDNodeTraversalException;
	
	/**
	 * Be invoked after <code>visit()</code>.
	 * 
	 * @param component
	 *        the component being visited
	 * @return
	 *        The <code>DNodeVisitStatus</code> defining how the remainder 
	 *        of the traversal should countinue or not.
	 * @exception AbortDNodeTraversalException
	 *        will signal the framework to abort any further processing on the 
	 *        component
	 * @see   DNodeVisitStatus
	 * @see   #visit(DNode)
	 * @see   #preVisit(DNode)
	 */
	DNodeVisitStatus postVisit(DNode component)
		throws AbortDNodeTraversalException;
	
	/**
	 * Answers the handling strategy for this instance.  The strategy must not
	 * be <code>null</code>
	 */	
	IDNodeHandlingStrategy getStrategy();
}
