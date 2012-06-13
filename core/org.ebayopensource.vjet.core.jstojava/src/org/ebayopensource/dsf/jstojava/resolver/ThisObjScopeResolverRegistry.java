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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry of 'this' scope resolvers.
 */
public class ThisObjScopeResolverRegistry {

	private static final ThisObjScopeResolverRegistry s_instance = new ThisObjScopeResolverRegistry();
	private Map<String, List<IThisObjScopeResolver>> m_resolvers = new HashMap<String, List<IThisObjScopeResolver>>();

	public static ThisObjScopeResolverRegistry getInstance() {
		return s_instance;
	}

	public ThisObjScopeResolverRegistry addResolver(String key,
			IThisObjScopeResolver resolver) {
		List<IThisObjScopeResolver> resolverList = m_resolvers.get(key);
		if (resolverList == null) {
			resolverList = new ArrayList<IThisObjScopeResolver>(1);
			m_resolvers.put(key, resolverList);
		}
		resolverList.add(resolver);
		return this;
	}

	public void resolve(String key, IThisScopeContext context) {
		Collection<List<IThisObjScopeResolver>> resolverList = m_resolvers.values();
		for (List<IThisObjScopeResolver> list : resolverList) {
			for (IThisObjScopeResolver iThisObjScopeResolver : list) {
				iThisObjScopeResolver.resolve(context);
			}
		}
		
//		if (resolverList == null) {
//			return;
//		}
//		for (int i = 0; i < resolverList.size(); i++) {
//			IThisObjScopeResolver resolver = resolverList.get(i);
//			resolver.resolve(context);
//		}
	}

	public boolean hasResolver(String key) {
		return m_resolvers.containsKey(key);
	}

	public void clear(String groupId) {
		for (List<IThisObjScopeResolver> resolverList : m_resolvers.values()) {
			for (int i = resolverList.size() - 1; i >= 0; i--) {
				IThisObjScopeResolver resolver = resolverList.get(i);
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
