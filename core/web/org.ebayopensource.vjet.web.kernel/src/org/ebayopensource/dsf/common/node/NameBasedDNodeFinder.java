/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.naming.IDsfName;
import org.ebayopensource.dsf.common.naming.IDsfNamingFamily;
import org.ebayopensource.dsf.common.naming.ParentScopes;
import org.ebayopensource.dsf.common.naming.ResolvedNamePath;
import org.ebayopensource.dsf.common.node.visitor.AbortDNodeTraversalException;
import org.ebayopensource.dsf.common.node.visitor.DNodeVisitStatus;
import org.ebayopensource.dsf.common.node.visitor.IDNodeHandlingStrategy;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.common.node.visitor.PreOrderDNodeTraversal;
import org.ebayopensource.dsf.dom.DNode;

public class NameBasedDNodeFinder {
	

	/** This will find the component given the relative path.  It
	 * basically calls the naming family to decompose the relativePath
	 * into a ResolvedNamePath and then it calls findComponent() with the
	 * resolved name path.  This is basicaly a convenience method.
	 * @param relativePath - relative path of a sub component.  The path
	 * can include both scopes and local name; e.g. "a:b#c".  See naming
	 * family decompose.
	 * @param component
	 * @return
	 */
	public static DNode get(
		final String relativePath,
		final DNode component)
	{
		//component.setDsfNamingFamily(DefaultDsfNamingFamily.getInstance());
		
		
		final IDsfNamingFamily nf = component.getDsfNamingFamily();
		//final IDsfNamingFamily nf = component.setDsfNamingFamily(DefaultDsfNamingFamily.getInstance()).getDsfNamingFamily();
		final ResolvedNamePath rnp = nf.decomposeName(relativePath);
		return get(rnp, component);
	}
	
	public static DNode get(
		final ResolvedNamePath resolvedNamePath,
		final DNode component)
	{
		final ParentScopes scopes = resolvedNamePath.getScopes();
		final String localName = resolvedNamePath.getLocalName();
		return get(scopes, localName, component);
	}
	
	public static DNode get(
		final ParentScopes scopes,
		final String localName,
		final DNode component)
	{	
		if ((scopes == null || scopes.size() <= 0) && localName != null) {
			return getByLocalName(localName, component);
		}
		
		final NameFinder finder = new NameFinder(scopes, localName);
		try {
			component.dsfAccept(finder);
		}
		catch (AbortDNodeTraversalException e) {
			//do nothing
		}
		return finder.getMatchedComponent();
	}
	
	public static DNode getByLocalName(
		final String localName,
		final DNode component)
	{
		final LocalNameFinder finder =
			new LocalNameFinder(localName, component);
		try {
			component.dsfAccept(finder);
		} catch (AbortDNodeTraversalException e) {
			//do nothing
		}
		return finder.getMatchedComponent();
	}

	private static class LocalNameFinder implements IDNodeVisitor {
		private final IDNodeHandlingStrategy m_strategy =
			new PreOrderDNodeTraversal();
		private final DNode m_rootComponent; 
		private final String m_localName;
		private DNode m_matchedComponent = null;
		
		LocalNameFinder(final String localName,final DNode rootComponent) {
			if (localName == null || localName.length() == 0) {
				throw new DsfRuntimeException("must supply local name");
			}
			m_localName = localName;
			m_rootComponent = rootComponent;
		}
		
		DNode getMatchedComponent() {
			return m_matchedComponent;
		}
		
		public DNodeVisitStatus preVisit(final DNode component)
			throws AbortDNodeTraversalException {
			if (!component.hasDsfName()) {
				if (component.isDsfExportingLocalNames()) {
					return DNodeVisitStatus.CONTINUE;
				} 
					if (component == m_rootComponent) {
						return DNodeVisitStatus.CONTINUE;
					} 
				return DNodeVisitStatus.ABORT_SUBTREE;
			}
			
			final IDsfName dsfName = component.getDsfName();
			if (m_localName.equals(dsfName.getLocalName())) {
				m_matchedComponent = component;
//				return ComponentVisitStatus.STOP_SUBTREE_TRAVERSAL;
				throw new AbortDNodeTraversalException("matched");
			}
			
			if (dsfName.getScopeName() == null || component == m_rootComponent){
				return DNodeVisitStatus.CONTINUE;				
			}
			
			return DNodeVisitStatus.ABORT_SUBTREE;				
		}
		
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

	private static class NameFinder implements IDNodeVisitor {
		
		private final IDNodeHandlingStrategy m_strategy =
			new PreOrderDNodeTraversal();
		private final ParentScopes m_scopes;
		private int m_scopeIndex = 0;
		private final String m_localName;
		private DNode m_matchedComponent = null;
		
		NameFinder(ParentScopes scopes, String localName) {
			m_scopes = scopes;
			m_localName = localName;
		}
		DNode getMatchedComponent() {
			return m_matchedComponent;
		}
		
		public DNodeVisitStatus preVisit(final DNode component)
			throws AbortDNodeTraversalException {
				
			if (m_scopeIndex >= m_scopes.size()) {
				m_matchedComponent = null;
				throw new AbortDNodeTraversalException("nomatch");								
			}
			
			if (!component.hasDsfName()) {
				return DNodeVisitStatus.CONTINUE;								
			}
			
			final String componentScopeName =
				component.getDsfName().getScopeName();
			if (componentScopeName == null) {
				return DNodeVisitStatus.CONTINUE;				
			}
			
			final String scopeName = m_scopes.get(m_scopeIndex);
			if (scopeName.equals(componentScopeName)) {
				return handlePartialScopeMatch(component);				
			}
			
			return DNodeVisitStatus.CONTINUE;
		}
		
		private DNodeVisitStatus handlePartialScopeMatch(
			final DNode component) throws AbortDNodeTraversalException
		{
			m_scopeIndex++; // point to next scope index
			if (m_scopeIndex < m_scopes.size()) {
				// more scope to match, so continue
				return DNodeVisitStatus.CONTINUE;				
			}

			// this indicates that we have an exact match of the scopes.
			// Lets see how the local names match

			if (m_localName == null) {
				// no local name to match, so search is done
				m_matchedComponent = component;
//				return ComponentVisitStatus.STOP_SUBTREE_TRAVERSAL;
				throw new AbortDNodeTraversalException("matched");
			}
			m_matchedComponent = getByLocalName(m_localName, component);
			if (m_matchedComponent == null)	{
				throw new AbortDNodeTraversalException(
					"localName is not matched");
			}																
//			return ComponentVisitStatus.STOP_SUBTREE_TRAVERSAL;
			throw new AbortDNodeTraversalException("matched");
		}
		
		public DNodeVisitStatus visit(final DNode component)
			throws AbortDNodeTraversalException
		{
			return DNodeVisitStatus.CONTINUE;
		}
		
		public DNodeVisitStatus postVisit(final DNode component)
			throws AbortDNodeTraversalException
		{			
			if (m_matchedComponent == component) {
				m_matchedComponent = null;				
				throw new AbortDNodeTraversalException("ScopePath not matched");				
			}
			return DNodeVisitStatus.CONTINUE;			
		}
		
		public IDNodeHandlingStrategy getStrategy() {
			return m_strategy;
		}
	}
}
