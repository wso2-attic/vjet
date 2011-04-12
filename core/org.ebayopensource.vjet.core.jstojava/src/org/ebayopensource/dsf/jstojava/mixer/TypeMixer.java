/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.mixer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeMixer {
	private Map<String, List<String>> m_maps = new HashMap<String, List<String>>();
	private final String m_groupId;
	
	public TypeMixer(String groupId) {
		m_groupId = groupId;
	}
	
	public String getGroupId() {
		return m_groupId;
	}
	
	public List<String> getMixInTypes(String targetType) {
		return m_maps.get(targetType);
	}
	
	public TypeMixer addExtendedType(String targetType, String mixinType) {
		List<String> li = m_maps.get(targetType);
		if (li == null) {
			li = new ArrayList<String>(1);
			m_maps.put(targetType, li);
		}
		li.add(mixinType);
		return this;
	}
	
	public Set<String> getExtendedTypes() {
		return m_maps.keySet();
	}
}
