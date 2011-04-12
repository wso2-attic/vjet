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

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public class ElementInfo implements IElementInfo {

	private final HtmlTypeEnum m_type;
	private final boolean m_requireStartTag;
	private final boolean m_requireEndTag;
	private final IContentModel m_contentModel;
	private final IAttributeInfoMap m_attributeInfo;

	public ElementInfo(
		final HtmlTypeEnum type,
		final IAttributeInfoMap attributeInfoMap,
		final IContentModel contentModel)
	{
		this(type, attributeInfoMap, contentModel, true, true);
	}
	public ElementInfo(
		final HtmlTypeEnum type,
		final IAttributeInfoMap attributeInfoMap,
		final IContentModel contentModel,
		final boolean requireStartTag,
		final boolean requireEndTag)
	{
		m_type = type;
		m_attributeInfo = AttributeInfoMapImpl.createUnmodifiable(attributeInfoMap);
		m_requireStartTag = requireStartTag;
		m_requireEndTag = requireEndTag;
		m_contentModel = contentModel;
	}

	public HtmlTypeEnum getType() {
		return m_type;
	}
	public boolean requireEndTag() {
		return m_requireEndTag;
	}
	public boolean requireStartTag() {
		return m_requireStartTag;
	}
	public IAttributeInfo getAttributeInfo(final String name) {
		return m_attributeInfo.get(name);
	}
	public IContentModel getContentModel() {
		return m_contentModel;
	}
	public Iterator<IAttributeInfo> iterator() {
		return m_attributeInfo.iterator();
	}
}
