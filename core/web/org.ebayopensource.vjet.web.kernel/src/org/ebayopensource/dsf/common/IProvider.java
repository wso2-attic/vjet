/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common;

import java.util.Map;

public interface IProvider<TYPE,ID> {
	
	public TYPE get(final ID id);
	
	public Map<ID,TYPE> getAll();
	
	public boolean remove(final ID id);
	
	public void clear();
}
