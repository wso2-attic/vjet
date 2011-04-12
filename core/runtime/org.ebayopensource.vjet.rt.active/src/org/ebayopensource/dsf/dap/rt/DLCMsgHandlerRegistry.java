/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DLCMsgHandlerRegistry {

	private Map<String, IDLCMsgHandler> m_handlers = new HashMap<String, IDLCMsgHandler>();

	public DLCMsgHandlerRegistry() {
	}

	public void add(IDLCMsgHandler handler) {
		if(handler != null){
			m_handlers.put(handler.getNameSpace(), handler);
		}
	}

	public IDLCMsgHandler get(String namespace) {
		return m_handlers.get(namespace);
	}

	public Collection<String> getNamespaces() {
		return Collections.unmodifiableCollection(m_handlers.keySet());
	}

	public Collection<? extends IDLCMsgHandler> getHandlers() {
		return Collections.unmodifiableCollection(m_handlers.values());
	}

}
