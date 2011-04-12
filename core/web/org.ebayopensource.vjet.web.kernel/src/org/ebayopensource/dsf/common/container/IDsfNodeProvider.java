/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.container;

import java.util.List;

import org.ebayopensource.dsf.common.node.DNodeId;
import org.ebayopensource.dsf.dom.DNode;

public interface IDsfNodeProvider {
	/**
	 * provides hook to cache the component and/or wrap the component.
	 * Throws DsfNodeInstantionException if the component can not be created.
	 * Throws DsfNodeRegistrationException if the component can not be registered.
	 * <p>
	 * A DsfRuntimeException should be thrown if the component is null.
	 */
	DNode add(DNode node) throws DsfNodeRegistrationException;
		
	/**
	 * Answer the component with given id.
	 * @param id DsfComponentId
	 * @return IDsfComponent
	 */
	DNode get(DNodeId id);	
	
	/**
	 * Answer a copied list of components in this provider. 
	 * @return List<IDsfComponent>
	 */
	List<DNode> getAll();		
}
