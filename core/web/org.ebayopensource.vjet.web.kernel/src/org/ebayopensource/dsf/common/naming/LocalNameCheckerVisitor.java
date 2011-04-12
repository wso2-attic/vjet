/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.naming;

import org.ebayopensource.dsf.common.node.NameBasedDNodeFinder;
import org.ebayopensource.dsf.common.node.visitor.AbortDNodeTraversalException;
import org.ebayopensource.dsf.common.node.visitor.DNodeVisitStatus;
import org.ebayopensource.dsf.dom.DNode;

class LocalNameCheckerVisitor extends BaseNameCheckerVisitor{
	LocalNameCheckerVisitor(
		final DNode child,
		final DNode enclosingScope)
	{
		super(child, enclosingScope);
	}
	/** two factors determine, whether or not we traverse farther with
	 * the particular subtree:
	 * 1. shouldExportLocalNames()
	 * 2. whether the component has a scope name.
	 * We still need to check all component's local name to make sure
	 * that it is valid.
	 */
	public DNodeVisitStatus preVisit(final DNode node)
		throws AbortDNodeTraversalException
	{
		if (node == m_child) {
			// ignore the root node because we already checked it.
			return DNodeVisitStatus.CONTINUE;				
		}
		DNodeVisitStatus status;
		// the export local names determines whether or not the
		// subtree should be traversed.
		if (node.isDsfExportingLocalNames()) {
			status = DNodeVisitStatus.CONTINUE;
		} 
		else {
			status = DNodeVisitStatus.ABORT_SUBTREE;				
		}
		
		if (!node.hasDsfName()) {
			return status; // nothing to check
		}
		
		if (node.getDsfName().getScopeName() != null) {
			// if the current nocde has scope, then we don't want to
			// traverse the subtree.
			status = DNodeVisitStatus.ABORT_SUBTREE;
		}
		
		final String localName = node.getDsfName().getLocalName();
		if (localName == null) {
			return status;
		}
		
		// we have a local name, so we need to check it.
		final DNode foundConflict = NameBasedDNodeFinder.getByLocalName(
			localName, m_enclosingScope);
		if (foundConflict != null) {
			throw new DsfInvalidNameException(
				"can't add child ("+ node.getClass() +")  with local name '" 
				+ localName + "' because the scope is already used in parent");
		}
		
		return status;
	}
}
