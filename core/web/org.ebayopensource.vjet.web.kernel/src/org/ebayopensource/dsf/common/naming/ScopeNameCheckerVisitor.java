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

class ScopeNameCheckerVisitor extends BaseNameCheckerVisitor{
	
	ScopeNameCheckerVisitor(
		final DNode child,
		final DNode enclosingScope)
	{
		super(child, enclosingScope);
	}
	
	public DNodeVisitStatus preVisit(final DNode component)
		throws AbortDNodeTraversalException {
		if (component == m_child) {
			// ignore the root node because we already checked it.
			return DNodeVisitStatus.CONTINUE;				
		}
		
		if (!component.hasDsfName()) {
			return DNodeVisitStatus.CONTINUE;
		}
		
		final String scopeName = component.getDsfName().getScopeName();
		if (scopeName == null) {
			return DNodeVisitStatus.CONTINUE;
		}
		
		// we have a scope name, so we need to check it.
		final ParentScopes scopes = new ParentScopes();
		scopes.appendScope(new DElementId(scopeName).toString());
		
		final DNode foundConflict =
			NameBasedDNodeFinder.get(scopes, null, m_enclosingScope);
		if (foundConflict != null) {
			throw new DsfInvalidNameException(
				"can't add child with scope '" + scopeName +
				"' because the scope is already used in parent\n\n");
		}
		
		// we don't need to traverse this subtree any more
		// because we don't need to check its children scopes.
		return DNodeVisitStatus.ABORT_SUBTREE;
	}
}
