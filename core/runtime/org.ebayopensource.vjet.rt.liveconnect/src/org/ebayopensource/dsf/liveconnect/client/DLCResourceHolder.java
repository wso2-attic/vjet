/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect.client;

import java.util.HashMap;
import java.util.Map;

public class DLCResourceHolder {

	private Map<String, IDLCResourceProvider> m_providers =
		new HashMap<String, IDLCResourceProvider>();
	private Map<String, DLCHttpResource> m_cache =
		new HashMap<String, DLCHttpResource>();

	public DLCResourceHolder() {
	}

	public void addProvider(IDLCResourceProvider provider) {
		this.m_providers.put(provider.getBaseUrl(), provider);
	}

	public DLCHttpResource getResource(String url) {
		DLCHttpResource resource = m_cache.get(url);
		if (resource == null) {
			int ind = url.lastIndexOf('/');
			if (ind == -1)
				throw new IllegalArgumentException("Malformed url: " + url);
			if (ind == url.length() - 1)
				throw new IllegalArgumentException(
						"Listing directory are forbidden: " + url);
			String baseUrl = url.substring(0, ind);
			String name = url.substring(ind + 1);
			synchronized (m_cache) {
				resource = m_cache.get(url);
				if (resource == null) {
					IDLCResourceProvider provider = m_providers.get(baseUrl);
					if (provider != null) {
						resource = provider.getResource(name);
						m_cache.put(url, resource);
					}
				}
			}
		}
		return resource;
	}

}
