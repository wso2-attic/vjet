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
 * Provides a default traversal as post-order (depth-first) pattern.
 * 
 * @see IDNodeHandlingStrategy
 * @see DepthFirstDNodeTraversal
 */
public class DefaultDNodeHandlingStrategy 
	implements IDNodeHandlingStrategy {
		
	private final DepthFirstDNodeTraversal m_strategy;
	
	private static DefaultDNodeHandlingStrategy s_instance
		= new DefaultDNodeHandlingStrategy();
		
	private DefaultDNodeHandlingStrategy() {
		m_strategy = new DepthFirstDNodeTraversal();
		m_strategy.setApplicablePhaseId(PhaseId.ANY_PHASE);
	}
	
	public Object clone() throws CloneNotSupportedException {
		// since one is supposed to call getInstance() and it always returns
		// the same instance, returning "this" will do the same thing.
		return this;
	}
	
	public static DefaultDNodeHandlingStrategy getInstance() {
		return s_instance;
	}
	
	public PhaseId getApplicablePhaseId() {
		return m_strategy.getApplicablePhaseId();
	}
	
	public void handle(final DNode component, final IDNodeVisitor visitor) {
		m_strategy.handle(component, visitor);
	}
}
