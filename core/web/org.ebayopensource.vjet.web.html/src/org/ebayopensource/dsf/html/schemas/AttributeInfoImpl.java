/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

public class AttributeInfoImpl implements IAttributeInfo {
	private final String m_name;
	final AttributeDataType m_type;
	final AttributeDefault m_attrDefault;
	private final String m_defaultValue;
	public AttributeInfoImpl(final String name) {
		this(name, AttributeDataType.CDATA, AttributeDefault.IMPLIED, null);
	}
	public AttributeInfoImpl(final String name, final AttributeDataType type) {
		this(name, type, AttributeDefault.IMPLIED, null);
	}
	public AttributeInfoImpl(
		final String name,
		final AttributeDataType type,
		final AttributeDefault attrDefault,
		final String defaultValue)
	{
		m_name = name;
		m_type = type;
		m_attrDefault = attrDefault;
		m_defaultValue = defaultValue;
	}
	public String getName() {
		return m_name;
	}
	public String getDefaultValue() {
		return m_defaultValue;
	}
	public AttributeDefault getAttrDefault() {
		return m_attrDefault;
	}
	public AttributeDataType getDataType() {
		return m_type;
	}
}
