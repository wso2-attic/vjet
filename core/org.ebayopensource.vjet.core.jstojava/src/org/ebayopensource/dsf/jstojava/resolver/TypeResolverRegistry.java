/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry of function-return-type resolvers at Lib/Group level from meta bootstrap.
 */
public class TypeResolverRegistry {
	
	private static final TypeResolverRegistry s_instance = new TypeResolverRegistry();
	private Map<String, List<ITypeResolver>> m_resolvers = new HashMap<String, List<ITypeResolver>>();
	
	public static TypeResolverRegistry getInstance() {
		return s_instance;
	}
	
	public TypeResolverRegistry addResolver(String key, ITypeResolver resolver) {
		List<ITypeResolver> resolverList = m_resolvers.get(key);
		if (resolverList == null) {
			resolverList = new ArrayList<ITypeResolver>(1);
			m_resolvers.put(key, resolverList);
		}
		resolverList.add(resolver);
		return this;
	}
	
	public String resolve(String key, String[] args) {
		List<ITypeResolver> resolverList = m_resolvers.get(key);
		if (resolverList == null) {
			return null;
		}
		for (int i = 0; i < resolverList.size(); i++) {
			ITypeResolver resolver = resolverList.get(i);
			String typeName = resolver.resolve(args);
			if (typeName != null) {
				return typeName;
			}
		}
		return null;
	}
	
	public boolean hasResolver(String key) {
		return m_resolvers.containsKey(key);
	}
	
	public void clear(String groupId) {
		for (List<ITypeResolver> resolverList : m_resolvers.values()) {
			for (int i = resolverList.size() - 1; i >=0; i--) {
				ITypeResolver resolver = resolverList.get(i);
				if (groupId.endsWith(resolver.getGroupId())) {
					resolverList.remove(resolver);
				}
			}
		}
	}
	
	public void clearAll() {
		m_resolvers.clear();
	}
}
