/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;

import org.ebayopensource.dsf.common.Id;

public class DNodeId extends Id {
	private static final long serialVersionUID = -557407384701568538L;

	static {
		Initializer.init();
	}
	
	//
	// Constructor(s)
	//
	public DNodeId() {
		this(nextDefaultName()) ;	
	}

	public DNodeId(final String name) {
		this(getNextEnumSequence(), name, false) ;
	}
	
	public DNodeId(final int enumId, final String name) {
		this(enumId, name, true) ;
	}
	
	public DNodeId(final int enumId, final String name, final boolean register) {
		super(enumId, name, register) ;
	}
}
