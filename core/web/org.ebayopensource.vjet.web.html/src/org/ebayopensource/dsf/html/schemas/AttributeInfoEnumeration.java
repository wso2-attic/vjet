/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.enums.BaseEnum;

public class AttributeInfoEnumeration implements IAttributeInfo {
	private final String m_name;
	final Class m_enumType;
	final AttributeDefault m_attrDefault;
	private final BaseSchemaEnum m_defaultValue;
	public AttributeInfoEnumeration(
		final String name,
		final Class enumType)
	{
		this(name, enumType, AttributeDefault.IMPLIED, null);
	}
	public AttributeInfoEnumeration(
		final String name,
		final Class enumType,
		final AttributeDefault attrDefault,
		final BaseSchemaEnum defaultValue)
	{
		if (!BaseEnum.class.isAssignableFrom(enumType)) {
			throw new DsfRuntimeException("class must be an enum, not " +
				enumType.getName());
		}
		m_name = name;
		m_enumType = enumType;
		m_attrDefault = attrDefault;
		m_defaultValue = defaultValue;
	}
	public String getName() {
		return m_name;
	}
	public String getDefaultValue() {
		return m_defaultValue.getName();
	}
	public BaseSchemaEnum getDefaultEnum() {
		return m_defaultValue;
	}
	public AttributeDefault getAttrDefault() {
		return m_attrDefault;
	}
	public AttributeDataType getDataType() {
		return AttributeDataType.ENUMERATION;
	}
	public Class getEnumType() {
		return m_enumType;
	}
}
