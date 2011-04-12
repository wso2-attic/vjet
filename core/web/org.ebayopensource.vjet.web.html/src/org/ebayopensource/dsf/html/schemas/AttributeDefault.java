/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** This expresses the existence contrant on the attribute:
 * implied, required, fixed.  The name follows the name described in Orielly's
 * XML in a nutshell; the name does not make a lot of sense.
 */
public class AttributeDefault {

	public static final AttributeDefault IMPLIED =
		new AttributeDefault("IMPLIED");
	public static final AttributeDefault REQUIRED =
		new AttributeDefault("REQUIRED");
	public static final AttributeDefault FIXED = new AttributeDefault("FIXED");

	private final String m_typeValue;
	private AttributeDefault(final String typeValue) {
		m_typeValue = typeValue;
	}
	public String getTypeValue() {
		return m_typeValue;
	}

}
