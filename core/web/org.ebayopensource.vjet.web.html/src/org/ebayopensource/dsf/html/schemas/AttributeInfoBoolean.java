/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** this is used to represent attribute information for HTML boolean types.
 * For example, "disabled='disabled'", "checked='checked'".
 * I have selected a type of enumeration because it is a closed set.
 * TODO: Is this a good data types???
 * I do not believe that there can be a default because this would make it
 * as good as fixed.
 * I also believe that it must always be implied because required would, in
 * essense, make it fixed.
 */
public class AttributeInfoBoolean implements IAttributeInfo {

	private final String m_name;

	public AttributeInfoBoolean(final String name) {
		m_name = name;
	}
	public AttributeDefault getAttrDefault() {
		return AttributeDefault.IMPLIED;
	}

	public AttributeDataType getDataType() {
		return AttributeDataType.ENUMERATION;
	}

	public String getDefaultValue() {
		return null;
	}

	public String getName() {
		return m_name;
	}
}
