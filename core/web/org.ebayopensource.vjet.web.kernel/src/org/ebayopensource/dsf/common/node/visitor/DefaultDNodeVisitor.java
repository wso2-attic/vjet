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
 * The default node visitor that implements <code>IDNodeVisitor</code>. It it 
 * does nothing and only returns <code>DNodeVisitStatus.CONTINUE</code> in each
 * visit method. This class can be extended to only override the specific visit 
 * method(s) that are of interest.
 * <p>
 * The strategy returned by default is what is returned from 
 * <code>DefaultComponentHandlingStrategy.getInstance()</code>
 * 
 * @see IDNodeVisitor
 */
public class DefaultDNodeVisitor implements IDNodeVisitor {
	protected IDNodeHandlingStrategy m_handlingStrategy 
		= DefaultDNodeHandlingStrategy.getInstance() ;
	
	/**
	 * Subclasses should override and/or super as needed.
	 */
	public DNodeVisitStatus preVisit(final DNode component)
		throws AbortDNodeTraversalException
	{
		return DNodeVisitStatus.CONTINUE ;	
	}

	/**
	 * Subclasses should override and/or super as needed.
	 */	
	public DNodeVisitStatus visit(final DNode component)
		throws AbortDNodeTraversalException
	{
		return DNodeVisitStatus.CONTINUE ;			
	}

	/**
	 * Subclasses should override and/or super as needed.
	 */	
	public DNodeVisitStatus postVisit(final DNode component)
		throws AbortDNodeTraversalException 
	{
		return DNodeVisitStatus.CONTINUE ;			
	}

	/**
	 * Subclasses should override and/or super as needed.
	 */	
	public IDNodeHandlingStrategy getStrategy() {
		return m_handlingStrategy ;
	}
	
	/**
	 * Registers custom node handling strategy.
	 */
	public void setStrategy(final IDNodeHandlingStrategy strategy) {
		m_handlingStrategy = strategy ;
	}
}
