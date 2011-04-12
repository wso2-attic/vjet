/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger;

import java.util.HashMap;
import java.util.Map;


public class ObjectIdMapper {
	
	private long m_idIndex = 0;
	private Map<Long, Object> m_objMap = new HashMap<Long, Object>();
	
	public synchronized long getId(Object obj) {
		long id = m_idIndex++;
		m_objMap.put(id, obj);
		return id;
	}
	
	public Object getObject(long id) {
		return m_objMap.get(id);
	}
	
	public void clear() {
		m_objMap.clear();
	}
}
