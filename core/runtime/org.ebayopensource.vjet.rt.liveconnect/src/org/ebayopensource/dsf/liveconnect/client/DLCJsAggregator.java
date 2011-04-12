/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect.client;

import java.util.HashSet;
import java.util.Set;

public class DLCJsAggregator implements IDLCJsProvider {

	private Set<IDLCJsProvider> m_providers = new HashSet<IDLCJsProvider>();

	public DLCJsAggregator() {
	}

	public Set<IDLCJsProvider> getProviders() {
		return this.m_providers;
	}

	public void addProvider(IDLCJsProvider client) {
		this.m_providers.add(client);
	}

	@Override
	public byte[] getClientJs() {
		StringBuffer sb = new StringBuffer(1024);
		for (IDLCJsProvider client : m_providers) {
			sb.append(new String(client.getClientJs()));
			sb.append("\r\n");
		}
		return sb.toString().getBytes();
	}

}
