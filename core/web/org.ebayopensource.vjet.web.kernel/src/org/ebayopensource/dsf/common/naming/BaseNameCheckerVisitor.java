/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

import org.ebayopensource.dsf.common.node.visitor.AbortDNodeTraversalException;
import org.ebayopensource.dsf.common.node.visitor.DNodeVisitStatus;
import org.ebayopensource.dsf.common.node.visitor.IDNodeHandlingStrategy;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.common.node.visitor.PreOrderDNodeTraversal;
import org.ebayopensource.dsf.dom.DNode;

abstract class BaseNameCheckerVisitor implements IDNodeVisitor
{
	private final IDNodeHandlingStrategy m_strategy =
		new PreOrderDNodeTraversal();
	protected final DNode m_child;
	protected final DNode m_enclosingScope;
	
	BaseNameCheckerVisitor(
		final DNode child,
		final DNode enclosingScope)
	{
		m_child = child;
		m_enclosingScope = enclosingScope;
	}
	
	public abstract DNodeVisitStatus preVisit(final DNode component)
		throws AbortDNodeTraversalException;
	
	public DNodeVisitStatus visit(final DNode component) {
		return DNodeVisitStatus.CONTINUE;
	}
	
	public DNodeVisitStatus postVisit(final DNode component) {
		return DNodeVisitStatus.CONTINUE;			
	}
	
	public IDNodeHandlingStrategy getStrategy() {
		return m_strategy;
	}
}
