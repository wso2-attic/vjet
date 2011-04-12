/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** <!ENTITY % LAlign "(top|bottom|left|right)">
 */
public class LegendAlignEnum extends BaseSchemaEnum {

	public static final LegendAlignEnum TOP = new LegendAlignEnum(1, "top");
	public static final LegendAlignEnum BOTTOM = new LegendAlignEnum(3, "bottom");
	public static final LegendAlignEnum LEFT = new LegendAlignEnum(4, "left");
	public static final LegendAlignEnum RIGHT = new LegendAlignEnum(5, "right");

	private LegendAlignEnum(final int id, final String name) {
		super(id, name);
	}
}
