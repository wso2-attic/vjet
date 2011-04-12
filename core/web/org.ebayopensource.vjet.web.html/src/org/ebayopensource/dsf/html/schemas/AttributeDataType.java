/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;


public class AttributeDataType {
	public static final AttributeDataType CDATA = new AttributeDataType("CDATA");
	public static final AttributeDataType NMTOKEN = new AttributeDataType("NMTOKEN");
	public static final AttributeDataType NMTOKENS = new AttributeDataType("NMTOKENS");
	public static final AttributeDataType ENUMERATION =	new AttributeDataType("ENUMERATION");
	public static final AttributeDataType ENTITY = new AttributeDataType("ENTITY");
	public static final AttributeDataType ENTITIES = new AttributeDataType("ENTITIES");
	public static final AttributeDataType ID = new AttributeDataType("ID");
	public static final AttributeDataType IDREF = new AttributeDataType("IDREF");
	public static final AttributeDataType IDREFS = new AttributeDataType("IDREFS");
	public static final AttributeDataType NAME = new AttributeDataType("NAME");
	public static final AttributeDataType NOTATION = new AttributeDataType("NOTATION");
	public static final AttributeDataType NUMBER = new AttributeDataType("NUMBER");

	private final String m_typeValue;
	private AttributeDataType(final String typeValue) {
		m_typeValue = typeValue;
	}
	public String getValue() {
		return m_typeValue;
	}
}
