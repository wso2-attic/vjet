/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.common.node.DNodeId;
import org.ebayopensource.dsf.dom.DNode;

public class NodeTraceHelper {
	
	/**
	 * Convert a component list to an component id list. If component list is
	 * null or empty, returns Collections.emptyList()  
	 * @param nodes List<IDsfComponent>
	 * @return List<DsfComponentId> 
	 */
	public static List<DNodeId> getIds(final List<? extends DNode> nodes){
		if (nodes == null || nodes.isEmpty()){
			return Collections.emptyList();
		}
		
		final List<DNodeId> ids = new ArrayList<DNodeId>(nodes.size());
		final Iterator<? extends DNode> itr = nodes.iterator();
		while (itr.hasNext()){
			ids.add(itr.next().getNodeId());
		}
		
		return ids;
	}
}