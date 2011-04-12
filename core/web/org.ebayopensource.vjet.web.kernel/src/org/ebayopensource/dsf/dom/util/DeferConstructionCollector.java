/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.common.container.DsfNodeInstantiationException;
import org.ebayopensource.dsf.common.container.IDsfNodeInstantiationValidator;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.dom.util.IDeferConstruction;

public class DeferConstructionCollector
	implements IDsfNodeInstantiationValidator
{
	private List<IDeferConstruction> m_nodes;
	
	public void validate(DNode node)
		throws DsfNodeInstantiationException
	{
		if (node instanceof IDeferConstruction) {
			getList().add((IDeferConstruction)node);
		}
	}
	
	public boolean hasDeferredNode() {
		if (m_nodes == null) {
			return false;
		}
		return m_nodes.size() > 0;
	}

	private List<IDeferConstruction> getList() {
		if (m_nodes == null) {
			m_nodes = new ArrayList<IDeferConstruction>(2);
		}
		return m_nodes;
	}
	
	public void reset() {
		if (m_nodes != null) {
			m_nodes.clear();
		}
	}
	
	public IDeferConstruction removeFirst() {
		if (!hasDeferredNode()) {
			return null;
		}
		return m_nodes.remove(0);
	}
	
	public void remove(IDeferConstruction o) {
		if (!hasDeferredNode()) {
			return;
		}
		m_nodes.remove(o);
	}
}
