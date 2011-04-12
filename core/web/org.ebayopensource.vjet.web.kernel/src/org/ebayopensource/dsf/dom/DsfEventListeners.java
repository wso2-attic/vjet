/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.util.LinkedHashSet;

import org.ebayopensource.dsf.common.event.IDsfEventListener;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.node.IDsfEventListeners;

class DsfEventListeners 
	extends LinkedHashSet<IDsfEventListener> implements IDsfEventListeners
{
	private static final long serialVersionUID = 5986120122098617169L;

	DsfEventListeners(final int initialSize) {
		super(initialSize) ;
	}
	
	@Override
	public boolean add(IDsfEventListener listener) {
		if (listener == null) {
			throw new DsfRuntimeException("Listener must not be null") ;
	
		}
		return super.add(listener) ;
	}
}
