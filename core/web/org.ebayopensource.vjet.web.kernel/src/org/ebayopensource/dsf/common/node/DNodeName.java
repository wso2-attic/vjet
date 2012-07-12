/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;

import org.ebayopensource.dsf.common.DsfVerifierConfig;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.naming.DsfInvalidNameException;
import org.ebayopensource.dsf.common.naming.IDsfName;
import org.ebayopensource.dsf.common.naming.IDsfNamingFamily;
import org.ebayopensource.dsf.common.naming.NameChecker;
import org.ebayopensource.dsf.common.naming.NameStatusCheck;
import org.ebayopensource.dsf.common.naming.ParentScopes;
import org.ebayopensource.dsf.common.naming.ResolvedNamePath;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.common.Z;

public class DNodeName implements IDsfName {
	private static final long serialVersionUID = -4458564866139801250L;
	
	private String m_localName = null;
	private String m_scopeName = null;
	private final DNode m_node;

	//
	// Constructor(s)
	//
	public DNodeName(final DNode node) {
		if (node == null) {
			chuck("node can't be null");
		}
		m_node = node;
	}
	
	//
	// Satisfy IDsfName
	//
	public String getLocalName() {
		return m_localName;
	}
	
	public void setLocalName(final String localName) {
		if (m_localName != null && m_localName.equals(localName)) {
			return ; // nothing to do
		}
		
//		 MrPperf - honor name validation config
		final DsfVerifierConfig config = DsfVerifierConfig.getInstance();	
		final boolean verifyNaming = config.isVerifyNaming() ;
		if (verifyNaming) {
//			final NameStatusCheck status = DsfCtx.ctx().getContainer()
//				.getDsfNamingFamily().verifyLocalName(localName);
			final NameStatusCheck status 
				= m_node.getDsfNamingFamily().verifyLocalName(localName);
			if (status.isOk() == false) {
				throw new DsfInvalidNameException(status.getErrorMessage()) ;
			}
		
			assertLocalNameIsNotInUse(localName);
		}
		
		if (m_localName == null) {
			m_localName = localName;
			return;
		}
		
		final DNode parent = m_node.getDsfParentNode();
// MrPperf - Don't force facet creation unless needed
		if (parent != null) {
			if (parent.hasDsfFacets()) {
				if (parent.getDsfFacets().get(m_localName) == m_node) {
					chuck("can't override localName for facet");
				}
			}
		}
		m_localName = localName;				
	}
	
	public String getScopeName() {
		return m_scopeName;
	}
	
	public String getFullName() {
		return getFullName(m_node.getDsfNamingFamily());
	}
	
	public String getFullName(final IDsfNamingFamily namingFamily) {
		if (m_localName == null && m_scopeName == null) {
			return null;
		}
		final ParentScopes scopes = getParentScopes();
		if (m_localName == null) {
			if (m_scopeName != null) {
				scopes.appendScope(m_scopeName);
			}
		}
		final ResolvedNamePath rpn =new ResolvedNamePath(scopes,getLocalName());
		final String fullname = namingFamily.compose(rpn);
		return fullname;
	}

	public void setScopeName(final String localScopeName) {
// MrPperf - honor name validation config
		final DsfVerifierConfig config = DsfVerifierConfig.getInstance();	
		final boolean verifyNaming = config.isVerifyNaming() ;
		if (verifyNaming) {
//			final NameStatusCheck status = DsfCtx.ctx().getContainer()
//				.getDsfNamingFamily().verifyLocalName(localScopeName);
			final NameStatusCheck status = 
				m_node.getDsfNamingFamily().verifyLocalName(localScopeName);			
			if (status.isOk() == false) {
				throw new DsfInvalidNameException(status.getErrorMessage()) ;
			}
		}
		
		if (localScopeName.equals(m_scopeName)) {
			return ;
		}
		if (verifyNaming) {
			assertScopeNameNotInUse(localScopeName);
		}
		m_scopeName = localScopeName;
	}
	
	public ParentScopes getParentScopes() {
		final ParentScopes scopes = new ParentScopes();	
		final DNode parent = m_node.getDsfParentNode();
		if (parent == null) {
			return scopes;
		}
		if (parent.getDsfName().getScopeName() != null) {
			scopes.setHasScopedParent(true);			
		}
		collectScope(scopes);
		return scopes;
	}
	
	//
	// Overrides from Object
	//
	public String toString() {
		Z z = new Z() ;
		z.format("local name", m_localName) ;
		z.format("local scope name", m_scopeName);
		z.format("node name", m_node.getNodeName()) ;
		z.format("node value", m_node.getNodeValue()) ;
		return z.toString() ;
	}
	
	//
	// Protected
	//
	protected void collectScope(final ParentScopes scopes) {
		DNode parent = m_node.getDsfParentNode();
		while (parent != null) {
			DNodeName dsfName = (DNodeName)parent.getDsfName();
			String scopeName = dsfName.getScopeName();
			if (scopeName != null) {
				scopes.addParentToCurrentScope(scopeName);				
			}
			parent = parent.getDsfParentNode();
		}				
	}
	
	protected void assertScopeNameNotInUse(final String scopeName) {
		if (!DsfVerifierConfig.getInstance().isVerifyNaming()) {
			return;
		}
		final DNode parent = m_node.getDsfParentNode();
		if (parent == null) {
			return ;
		}
		final DNode enclosingScope = NameChecker.getEnclosingScoped(parent);
		final ParentScopes scopes = new ParentScopes();
		scopes.appendScope(scopeName);
		final DNode component 
			= NameBasedDNodeFinder.get(scopes, null, enclosingScope);
		if (component == null) {
			return ;
		}
		throw new DsfInvalidNameException(scopeName +
			" has conflict with existing component");
	}
	
	//
	// Private
	//
	private void assertLocalNameIsNotInUse(final String localName) {
		if (!DsfVerifierConfig.getInstance().isVerifyNaming()) {
			return;
		}
		final DNode parent = m_node.getDsfParentNode();
		if (parent == null) {
			return ;
		}
		final DNode enclosingScope
			= NameChecker.getEnclosingScoped(parent);
		final DNode component
			= NameBasedDNodeFinder.getByLocalName(localName, enclosingScope);
		if (component == null) {
			return ;
		}
		throw new DsfInvalidNameException(
			"parent has child or facet " +"with local name '"+ localName + "'");
	}
	
	private void chuck(final String msg) {
		throw new DsfRuntimeException(msg) ;
	}
}
