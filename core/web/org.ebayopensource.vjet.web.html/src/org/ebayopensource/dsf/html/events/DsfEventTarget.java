/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.events;

import org.ebayopensource.dsf.dom.DElement;

public class DsfEventTarget {
	private String m_id;
	private DElement m_elem;
	private IDomType m_type;
	
	//
	// Constructor(s)
	//
	public DsfEventTarget() {
		// empty on purpose
	}

	//
	// API
	//
	public String getId() {
		return m_id;
	}
	public void setId(final String id) {
		m_id = id;
	}
	
	public DElement getElem() {
		return m_elem;
	}
	public void setElem(final DElement elem) {
		m_elem = elem;
	}

	public IDomType getType() {
		return m_type;
	}
	public void setType(final IDomType type) {
		m_type = type;
	}
}
