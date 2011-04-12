/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node.visitor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.ebayopensource.dsf.common.node.IDNodeList;
import org.ebayopensource.dsf.common.phase.PhaseId;
import org.ebayopensource.dsf.dom.DNode;
/**
 * Provides a in-order (breath-first) traversal.
 * 
 * @see IDNodeHandlingStrategy
 */
public class BreadthFirstDNodeTraversal implements IDNodeHandlingStrategy {
		
	private PhaseId m_phaseId = PhaseId.ANY_PHASE;
	private List<DNode> m_kidsToTraverse = 
		new LinkedList<DNode>();
	
	public PhaseId getApplicablePhaseId() {
		return m_phaseId;
	}
	
	public void setApplicablePhaseId(final PhaseId phaseId) {
		m_phaseId = phaseId;
	}
		
	public Object clone() throws CloneNotSupportedException {
		final BreadthFirstDNodeTraversal copy =
			new BreadthFirstDNodeTraversal();
		copy.m_phaseId = m_phaseId;
		return copy;
	}
	
	public void handle(
		final DNode current,
		final IDNodeVisitor visitor) 
	{
		final int numKidsToTraverse = m_kidsToTraverse.size();
		DNodeVisitStatus status = visitor.preVisit(current);
		boolean stopTraversal = 
			(status == DNodeVisitStatus.STOP_SUBTREE_TRAVERSAL);
	
		if (status != DNodeVisitStatus.ABORT_CURRENT_NODE ||
			status != DNodeVisitStatus.ABORT_SUBTREE) {
		
			status = visitor.visit(current);
		}
		if (!stopTraversal &&
			status != DNodeVisitStatus.ABORT_SUBTREE &&
			status != DNodeVisitStatus.STOP_SUBTREE_TRAVERSAL) {
		
			collectChildren(current);
		}
		if (status != DNodeVisitStatus.ABORT_CURRENT_NODE ||
			status != DNodeVisitStatus.ABORT_SUBTREE) {
		
			visitor.postVisit(current);
		}
										
		if (numKidsToTraverse == 0) {
			//start with next level if it is root node
			startNextLevel(visitor);			
		}
	}
	
//	protected void collectChildren(final DNode component) {	
//		if (!component.hasChildNodes() && !component.hasDsfFacets()) {
//			return;
//		}
//		final Iterator<DNode> kids = component.getFacetsAndChildrenItr();		
//		while (kids.hasNext()) {
//			m_kidsToTraverse.add(kids.next());
//		}
//	}
	
	protected void collectChildren(final DNode node) {	
		if (node.hasDsfFacets()) {
			// MrP - perf - see if we can get values without creating iterator
			Iterator<DNode> itr = node.getDsfFacets().iterator() ;
			while(itr.hasNext()) {
				m_kidsToTraverse.add(itr.next());
			}
		}
		
		if (node.hasChildNodes()) {
			final IDNodeList children = node.getDsfChildNodes() ;
			final int len = children.getLength();
			for(int i = 0; i < len; i++) {
				m_kidsToTraverse.add(children.get(i)) ;
			}
		}
	}

	private void startNextLevel(final IDNodeVisitor visitor) {
		final int numKids = m_kidsToTraverse.size();
		for (int i=0; i < numKids; i++) {
			DNode child = m_kidsToTraverse.remove(0);
			child.dsfAccept(visitor);
		}
		if (m_kidsToTraverse.size() > 0) {
			startNextLevel(visitor);
		}
	}
}
