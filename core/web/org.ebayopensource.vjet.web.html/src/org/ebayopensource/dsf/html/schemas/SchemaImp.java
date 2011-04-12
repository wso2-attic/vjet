/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import java.util.Iterator;
import java.util.Map;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public class SchemaImp implements ISchema, Iterable<IElementInfo> {
	// the map is only kept around to make the iterator work easily because
	// the array might have null entries for some of the elements.
	private final Map<HtmlTypeEnum,IElementInfo> m_elementMap;
	private final IElementInfo[] m_elementInfo;
	private static IElementInfo[] createElementInfoArray(
		final Map<HtmlTypeEnum,IElementInfo> elementMap)
	{
		final int size = HtmlTypeEnum.size();
		final IElementInfo[] array = new IElementInfo[size];
		
		for (final HtmlTypeEnum htmlTypeEnum:HtmlTypeEnum.valueIterable()) {
			final IElementInfo elementInfo = elementMap.get(htmlTypeEnum);
			
			// populate the static array with the index and element info so we can
			// look it up much faster during emission
			array[htmlTypeEnum.getId()] = elementInfo ;
		}
		return array;
	}
	public SchemaImp(final Map<HtmlTypeEnum,IElementInfo> elementMap){
		m_elementMap = elementMap;
		m_elementInfo = createElementInfoArray(elementMap);
	}
	public IElementInfo getElementInfo(final HtmlTypeEnum type){
		if (type == null) {
			return null;
		}
		final IElementInfo elementInfo = m_elementInfo[type.getId()];
		return elementInfo;
	}
	public Iterator<IElementInfo> iterator() {
		return m_elementMap.values().iterator();
	}
}
