/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

public class ShapeEnum extends BaseSchemaEnum {

	public static final ShapeEnum RECT = new ShapeEnum(1, "rect");
	public static final ShapeEnum CIRCLE = new ShapeEnum(2, "circle");
	public static final ShapeEnum POLY = new ShapeEnum(3, "poly");
	public static final ShapeEnum DEFAULT = new ShapeEnum(4, "default");

	private ShapeEnum(final int id, final String name) {
		super(id, name);
	}
}
