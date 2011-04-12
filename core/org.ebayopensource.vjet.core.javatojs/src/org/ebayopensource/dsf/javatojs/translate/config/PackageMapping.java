/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Temporary holder
 * Assumption:
 * 1. Unique mapping (if reverse is required)
 * 2. Prefix only
 */
public class PackageMapping {

	private Map<String,String> m_pkgMap = new HashMap<String,String>();

	public PackageMapping() {
	}
	
	public void add(final String from, final String to){
		m_pkgMap.put(from, to);
	}
	
	public String mapTo(final String fromPkg){
		if (fromPkg == null){
			return null;
		}
		String to = m_pkgMap.get(fromPkg);
		if (to != null){
			return to;
		}
		String from;
		for (Map.Entry entry: m_pkgMap.entrySet()){
			from = entry.getKey().toString();
			if (fromPkg.startsWith(from)){
				return entry.getValue().toString() + fromPkg.substring(from.length());
			}
		}
		return fromPkg;
	}
	
	public String mapFrom(final String toPkg){
		if (toPkg == null){
			return null;
		}
		String to;
		for (Map.Entry entry: m_pkgMap.entrySet()){
			to = entry.getValue().toString();
			if (toPkg.startsWith(to)){
				return entry.getKey().toString() + toPkg.substring(to.length());
			}
		}
		return toPkg;
	}
}
