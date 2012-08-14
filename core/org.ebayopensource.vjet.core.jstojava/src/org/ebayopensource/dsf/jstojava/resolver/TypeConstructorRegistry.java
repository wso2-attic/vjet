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
 * Registry of function-return-type resolvers at Lib/Group level from meta
 * bootstrap.
 */
public class TypeConstructorRegistry {

	private static final TypeConstructorRegistry s_instance = new TypeConstructorRegistry();
	private Map<String, List<ITypeConstructorResolver>> m_resolvers = new HashMap<String, List<ITypeConstructorResolver>>();

	public static TypeConstructorRegistry getInstance() {
		return s_instance;
	}

	public TypeConstructorRegistry addResolver(String key,
			ITypeConstructorResolver resolver) {
		List<ITypeConstructorResolver> resolverList = m_resolvers.get(key);
		if (resolverList == null) {
			resolverList = new ArrayList<ITypeConstructorResolver>(1);
			m_resolvers.put(key, resolverList);
		}
		resolverList.add(resolver);
		return this;
	}

	public void resolve(String key, ITypeConstructContext constrCtx) {
		List<ITypeConstructorResolver> resolverList = m_resolvers.get(key);
		if (resolverList == null) {
			return;
		}

		for (int i = 0; i < resolverList.size(); i++) {
			ITypeConstructorResolver resolver = resolverList.get(i);
			if (constrCtx.getExprClass()
					.equals(resolver.getExprClass())) {
				resolver.resolve(constrCtx);
				return;
			}
		}
	}

	public boolean hasResolver(String key) {
		return m_resolvers.containsKey(key);
	}

	public void clear(String groupId) {
		for (List<ITypeConstructorResolver> resolverList : m_resolvers.values()) {
			for (int i = resolverList.size() - 1; i >= 0; i--) {
				ITypeConstructorResolver resolver = resolverList.get(i);
				for(String grp: resolver.getGroupIds()){
					if (groupId.endsWith(grp)) {
						resolverList.remove(resolver);
					}
				}
				
			}
		}
	}

	public void clearAll() {
		m_resolvers.clear();
	}
}
