/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.container;

import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.common.node.DNodeId;
import org.ebayopensource.dsf.dom.DNode;

public class NoOpDsfNodeProvider implements IDsfNodeProvider {
	private static IDsfNodeProvider s_instance = new NoOpDsfNodeProvider();
	
	public static IDsfNodeProvider getInstance() {
		return s_instance;
	}
	
	public DNode add(final DNode node) {
		return node;	
	}

	public DNode get(final DNodeId id) {
		return null;
	}
	
	public List<DNode> getAll(){
		return Collections.emptyList();
	}
}
