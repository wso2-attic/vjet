/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.container;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.common.DsfVerifierConfig;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.naming.DefaultDsfNamingFamily;
import org.ebayopensource.dsf.common.naming.HtmlIdNamingFamily;
import org.ebayopensource.dsf.common.naming.IDsfNamingFamily;
import org.ebayopensource.dsf.common.node.DefaultDNodeRelationshipVerifier;
import org.ebayopensource.dsf.common.node.IDNodeRelationshipVerifier;
import org.ebayopensource.dsf.dom.DNode;

/**
 * A DsfContainer is the implied Container that all DNode's belong too.
 * The association of Container and nodes is done transparently by the 
 * Dsf Framework.
 * 
 * A single instance of the DsfContainer is associated with each thread the
 * DsfCtx is associated with.  The DsfCtx is the way nodes and
 * application code gain access to the DsfContainer.
 * 
 * The Containers main job is to broker the creation of Nodes and hold onto
 * default instances of various Dsf objects used by Nodes. 
 */
public final class DsfNodeContainer {
	
	private IDsfNodeProvider m_nodeProvider = 
		NoOpDsfNodeProvider.getInstance();	
			
	private IDsfNamingFamily m_namingFamily =
		HtmlIdNamingFamily.getInstance() ;
//		DefaultDsfNamingFamily.getInstance();
				
	private IDNodeRelationshipVerifier m_relationshipVerifier =
		DefaultDNodeRelationshipVerifier.getInstance();
		
	private List<IDsfNodeInstantiationValidator> m_nodeInstantiationValidators = 
		new ArrayList<IDsfNodeInstantiationValidator>();		
	
	private static final DsfVerifierConfig s_dsfVerifierConfig =
			DsfVerifierConfig.getInstance();
	
	//
	// Constructor(s)
	//	
	public DsfNodeContainer() {	
		// empty on purpose						
	}
	
	public DsfNodeContainer(
		final IDsfNodeProvider nodeProvider,
		final IDsfNamingFamily namingFamily,
		final IDNodeRelationshipVerifier relationshipVerifier) {
		
		if (nodeProvider != null) {
			setNodeProvider(nodeProvider);			
		}
		if (namingFamily != null) {
			setDsfNamingFamily(namingFamily);			
		}
		if (relationshipVerifier != null) {
			setNodeRelationshipVerifier(relationshipVerifier);			
		}		
	}	

	
	//
	// API
	//	
	public IDsfNodeProvider getNodeProvider() {
		return m_nodeProvider;
	}

	public void setNodeProvider(final IDsfNodeProvider provider) {
		if (provider == null) {
			chuck("Node provider can't be null");			
		}
		m_nodeProvider = provider;
	}	
	
	public IDsfNamingFamily getDsfNamingFamily() {
		return m_namingFamily;
	}

	public void setDsfNamingFamily(final IDsfNamingFamily namingFamily) {
		if (namingFamily == null) {
			chuck("DsfNamingFamily can't be null");			
		}		
		m_namingFamily = namingFamily;
	}
	
	public IDNodeRelationshipVerifier getNodeRelationshipVerifier() {
		return m_relationshipVerifier;
	}

	public void setNodeRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
		if (relationshipVerifier == null) {
			chuck("ComponentRelationshipVerifier must not be null");			
		}		
		m_relationshipVerifier = relationshipVerifier;
	}
	
	/**
	 * If the validator is null a DsfRuntimeException is thrown.
	 * The validators are maintained in an ordered list
	 * @param validator
	 * @return
	 */
	public boolean addNodeInstantiationValidator
		(IDsfNodeInstantiationValidator validator)
	{
		if (validator == null) {
			chuck("Validator must not be null") ;
		}
		if (!m_nodeInstantiationValidators.contains(validator)){
			return m_nodeInstantiationValidators.add(validator);
		}
		return false;
	}
	
	public boolean removeNodeInstantiationValidator
		(IDsfNodeInstantiationValidator validator)
	{
		return m_nodeInstantiationValidators.remove(validator);
	}
	
	public void checkNodeInstantiation(final DNode node) {
		if (node == null) {
			chuck("Node to have its instantiation checked must not be null") ;
		}
// TODO: MrP - for some reason this causes the Cssr ref generation to fail!!!
// believe that this is a shared class instance bug with the VM
		//12-05-08, tested enable it, ice build, unified is green.
		if (!s_dsfVerifierConfig.isVerifyInstantiation()) {
			return ;
		}
		// Don't use shorthand for() - don't need overhead of Iterator
		int size = m_nodeInstantiationValidators.size();
		for (int i = 0; i < size; i++) {
			m_nodeInstantiationValidators.get(i).validate(node);
		}
	}
	
	public void reset() {
		m_nodeProvider = NoOpDsfNodeProvider.getInstance();				
		m_namingFamily = DefaultDsfNamingFamily.getInstance();			
		m_relationshipVerifier = 
			DefaultDNodeRelationshipVerifier.getInstance();
		m_nodeInstantiationValidators.clear();		
	}

	//
	// Framework
	//	
	protected void chuck(final String message) {
		throw new DsfRuntimeException(message) ;
	}
}
