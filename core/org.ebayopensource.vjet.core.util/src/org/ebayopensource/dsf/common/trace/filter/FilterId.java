/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.filter;

import org.ebayopensource.dsf.common.Id;

public class FilterId extends Id {
	
	private static final long serialVersionUID = 1L;

	public FilterId(final String name) {
		super(getNextEnumSequence(), name, false) ;
	}
	
	public FilterId(final int enumId, final String name) {
		super(enumId, name, true) ;
	}
}
