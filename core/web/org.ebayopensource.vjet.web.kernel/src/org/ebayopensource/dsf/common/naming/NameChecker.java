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
import org.ebayopensource.dsf.dom.DNode;

public class NameChecker {
	public static void assertNamesUnique(
		final DNode parent,
		final DNode child)
	{
		if (!child.hasDsfName()) {
			return ;
		}
		assertLocalNameUnique(parent, child);
		assertScopeNameUnique(parent, child);
	}
	
	public static void assertLocalNameUnique(
		final DNode parent,
		final DNode child)
	{
		if (!child.hasDsfName()) {
			return ;
		}
		final IDsfName name = child.getDsfName();
		if (name.getLocalName() == null) {
			return ;
		}
		final DNode component=NameBasedDNodeFinder.getByLocalName(
			name.getLocalName(), parent);
		if (component != null) {
			throw new DsfInvalidNameException(
				"local name '" + name.getLocalName()+"' is already in use"); 
		}		
	}
	/** If assertion fails, this will throw appropriate exception.
	 * @param child
	 */
	public static void assertScopeNameUnique(
		final DNode parent,
		final DNode child)
	{
		if (!child.hasDsfName()) {
			return ;
		}
		final IDsfName name = child.getDsfName();
		if (name.getScopeName() == null) {
			return ;
		}
		final DNode enclosingScope = getEnclosingScoped(parent);
		final ParentScopes scopes = new ParentScopes();
		scopes.appendScope(new DElementId(name.getScopeName()).toString());
		final DNode component =
			NameBasedDNodeFinder.get(scopes, null, enclosingScope);
		if (component != null) {
			throw new DsfInvalidNameException(
				"scope name '" + name.getScopeName() + "' is already in use");
		}
	}
	public static void assertChildrenNamesUnique(
		final DNode parent,
		final DNode child)
	{
		if (child.hasDsfName()) {
			final IDsfName name = child.getDsfName();
			if (name.getScopeName() != null) {
				return ;
			}
		}
		if (!child.isDsfExportingLocalNames()) {
			return ;
		}
		final DNode enclosingScope = getEnclosingScoped(parent);
		final ScopeNameCheckerVisitor scopeVisitor =
			new ScopeNameCheckerVisitor(child, enclosingScope);
		try {
			child.dsfAccept(scopeVisitor);
		} catch (AbortDNodeTraversalException e) {
			//do nothing, assume everything must be OK.
		}
		final LocalNameCheckerVisitor localVisitor =
			new LocalNameCheckerVisitor(child, enclosingScope);
		try {
			child.dsfAccept(localVisitor);
		} catch (AbortDNodeTraversalException e) {
			//do nothing, assume everything must be OK.
		}
	}

	/** gets the component that is the closes scoped parent.
	 * If this component and none of its parents have a scope name,
	 * it returns the root of the tree.
	 * @return
	 */
	public static DNode getEnclosingScoped(final DNode parent) {
		DNode component = parent;
		for(; component.getDsfParentNode() != null; component = component.getDsfParentNode()) {
			if (component.hasDsfName()) {
				// use hasDsfName to prevent object creation.
				final IDsfName name = component.getDsfName();
				if (name.getScopeName() != null) {
					return component;
				}
			}
		}
		return component; // not found
	}

}
