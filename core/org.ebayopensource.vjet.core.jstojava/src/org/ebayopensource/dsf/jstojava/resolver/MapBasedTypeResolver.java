/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.resolver;

import java.util.HashMap;
import java.util.Map;

public class MapBasedTypeResolver implements ITypeResolver {

	private final String m_groupId;
	private final Map<String, String> m_typeMapping = new HashMap<String, String>();
	
	public MapBasedTypeResolver(String groupId) {
		m_groupId = groupId;
	}
	
	@Override
	public String[] getGroupIds() {
		return new String[]{m_groupId};
	}

	@Override
	public String resolve(String[] args) {
		if (args == null || args.length == 0) {
			return null;
		}
		String val = getStrValue(args[0]);
		return (val == null) ? null : m_typeMapping.get(val);
	}
	
	public MapBasedTypeResolver addMapping(String lookupKey, String typeName) {
		m_typeMapping.put(lookupKey, typeName);
		return this;
	}
	
	private static String getStrValue(String quottedValue) {
		if ((quottedValue.startsWith("\"") && quottedValue.endsWith("\"")) ||
			(quottedValue.startsWith("'") && quottedValue.endsWith("'"))) {
			return quottedValue.substring(1, quottedValue.length()-1);
		}
		return null;
	}
}
