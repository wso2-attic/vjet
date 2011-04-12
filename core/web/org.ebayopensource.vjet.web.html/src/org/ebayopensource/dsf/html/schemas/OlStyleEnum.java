/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** represents type attribute of ol:
 * <!ENTITY % OLStyle "CDATA"      -- constrained to: "(1|a|A|i|I)" -->
 */
public class OlStyleEnum extends BaseSchemaEnum {

	public static final OlStyleEnum NUMBER = new OlStyleEnum(1, "1");
	public static final OlStyleEnum ALPHA_LOWER = new OlStyleEnum(2, "a");
	public static final OlStyleEnum ALPHA_UPPER = new OlStyleEnum(3, "A");
	public static final OlStyleEnum ROMAN_LOWER = new OlStyleEnum(4, "i");
	public static final OlStyleEnum ROMAN_UPPER = new OlStyleEnum(5, "I");

	private OlStyleEnum(final int id, final String name) {
		super(id, name);
	}
}
