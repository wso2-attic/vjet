/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** <!ENTITY % IAlign "(top|middle|bottom|left|right)" -- center? -->
 */
public class IAlignEnum extends BaseSchemaEnum {

	public static final IAlignEnum TOP = new IAlignEnum(1, "top");
	public static final IAlignEnum MIDDLE = new IAlignEnum(2, "middle");
	public static final IAlignEnum BOTTOM = new IAlignEnum(3, "bottom");
	public static final IAlignEnum LEFT = new IAlignEnum(4, "left");
	public static final IAlignEnum RIGHT = new IAlignEnum(5, "right");

	private IAlignEnum(final int id, final String name) {
		super(id, name);
	}
}
