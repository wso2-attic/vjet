/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.util.ArrayList;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.node.IDNodeList;

class DNodeList extends ArrayList<DNode> implements IDNodeList {
	private static final long serialVersionUID = -8189254909742472498L;
	private final DNode m_owner ;
	
	public DNodeList(final DNode owner, final int initialSize) {
		super(initialSize) ;
		m_owner = owner ;
	}
	
	//
	// Satisfy INodeList
	//
	public DNode item(final int index) {
		return get(index) ;
	}
	
	public int getLength() {
		return size() ;
	}

	public DNode getOwner() {
		return m_owner;
	}
	
	//
	// Need to do some tweaks to safely implement the List<DNode> operation
	//
	public boolean add(DNode child) {
		m_owner.appendChild(child) ;
		return true ;
	}
	
	void privateAdd(DNode child) {
		super.add(child) ;
	}
	void privateRemove(DNode child) {
		super.remove(child) ;
	}
	
	public boolean remove(Object child) {
		if (child == null) {
			return false ;
		}
		if (! (child instanceof DNode)) {
			throw new DsfRuntimeException(
				"remove(Object) signature expects DNode argument") ;
		}
		return m_owner.removeChild((DNode)child) != null ;
	}
	
	public void clear() {
		if (! m_owner.hasChildNodes()) {
			return ;
		}
		while (this.size() > 0) {
			final DNode child = get(this.size()-1);
			m_owner.removeChild(child);
		}
		super.clear();
	}
	void privateClear() {
		super.clear() ;
	}
}
