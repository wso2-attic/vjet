/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public class AttributeInfoMapImpl implements IAttributeInfoMap {

	private final Map<String,IAttributeInfo> m_attributeMap;
	public AttributeInfoMapImpl(){
		m_attributeMap = new HashMap<String,IAttributeInfo>();
	}
	public IAttributeInfo get(final String attributeName) {
		return m_attributeMap.get(attributeName);
	}

	public void put(final IAttributeInfo attributeInfo) {
		m_attributeMap.put(attributeInfo.getName(), attributeInfo);
	}
	
	public Iterator<IAttributeInfo> iterator() {
		return m_attributeMap.values().iterator();
	}
	public static IAttributeInfoMap createUnmodifiable(
		final IAttributeInfoMap map)
	{
		return new IAttributeInfoMap(){
			public IAttributeInfo get(String attributeName) {
				return map.get(attributeName);
			}
			public void put(IAttributeInfo attributeInfo) {
				throw new DsfRuntimeException("can't modify unmodifiable map");
			}
			public Iterator<IAttributeInfo> iterator() {
				return map.iterator();
			}
		};
	}
}
