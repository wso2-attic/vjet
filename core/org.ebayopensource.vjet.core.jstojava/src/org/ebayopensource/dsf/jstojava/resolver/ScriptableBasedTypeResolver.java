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

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.Scriptable;

public class ScriptableBasedTypeResolver implements ITypeResolver {

	private final String m_groupId;
	private final Context m_cx;
	private final Scriptable m_scope;
	private final Function m_func;
	private final Map<String,String> m_cache = new HashMap<String, String>();
	
	// to help with multiple threads calling this resolver we are caching the result on the first pass.
	/**
	 * 
	 * 
	 * 
	 * @param groupId
	 * @param cx
	 * @param scope
	 * @param func
	 */
	public ScriptableBasedTypeResolver(
		String groupId,
		Context cx,
		Scriptable scope,
		Function func) {
		m_groupId = groupId;
		m_cx = cx;
		m_scope = scope;
		m_func = func;
	}
	
	@Override
	public String getGroupId() {
		return m_groupId;
	}

	@Override
	public String resolve(String[] args) {
		String key = createKey(args);
		String result = m_cache.get(key);

		if(result!=null){
			return result;
		}
		
		Object val = m_func.call(m_cx, m_scope, null, (Object[])args);
		
		if(val!=null){
			result = val.toString();
			m_cache.put(key, result);
		}
		
		return result;
	}
	
	private String createKey(String[] args) {
		StringBuilder b = new StringBuilder();
		for(String a:args){
			b.append(a);
			b.append(",");
		}
		return b.toString();

	}
	
	
}
