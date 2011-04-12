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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.node.DNodeId;
import org.ebayopensource.dsf.dom.DNode;

/**
 * This is a default implementation of IDsfNodeProvider.  DNodes are managed
 * using a Map<DNodeId, DNode> semantic.  Added nodes must not be null and are
 * expected to answer a non-null for node.getNodeId().  The map is not ordered.
 * The getAll() : List<DNode> method returns a new instance containing all the
 * nodes that were added.  There are no "maintenance" API's on this type.
 */
public class DefaultDsfNodeProvider implements IDsfNodeProvider {
	
	private Map<DNodeId, DNode> m_nodes = 
		new HashMap<DNodeId, DNode>();
	
	//
	// Satisfy IComponentProvider
	//
	public DNode add(final DNode node) 
		throws DsfNodeRegistrationException {
			
		if (node == null) {
			chuck("component is null.") ;
		}

		m_nodes.put(node.getNodeId(), node);
		
		return node;
	}
		
	public DNode get(final DNodeId id){
		return m_nodes.get(id);
	}
	
	public List<DNode> getAll(){
		return new ArrayList<DNode>(m_nodes.values());
	}
	
	// 
	// Protected
	//
	protected void chuck(final String msg){
		throw new DsfRuntimeException(msg);
	}
}