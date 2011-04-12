/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.event;

import org.ebayopensource.dsf.common.Id;

public class TraceId extends Id {

	private static final long serialVersionUID = 1L;
	
	//
	// Constructor(s)
	//
	public TraceId(final String name) {
		super(getNextEnumSequence(), name, false) ;
	}
	
	public TraceId(final int id, final String name) {
		super(id, name, true);
	}
}
