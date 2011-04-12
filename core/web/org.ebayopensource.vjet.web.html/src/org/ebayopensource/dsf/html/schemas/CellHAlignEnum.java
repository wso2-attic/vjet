/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** "align      (left|center|right|justify|char) #IMPLIED
 */
public class CellHAlignEnum extends BaseSchemaEnum {

	public static final CellHAlignEnum LEFT = new CellHAlignEnum(1, "left");
	public static final CellHAlignEnum CENTER = new CellHAlignEnum(2, "center");
	public static final CellHAlignEnum RIGHT = new CellHAlignEnum(3, "right");
	public static final CellHAlignEnum JUSTIFY=new CellHAlignEnum(4, "justify");
	public static final CellHAlignEnum CHAR = new CellHAlignEnum(5, "char");

	private CellHAlignEnum(final int id, final String name) {
		super(id, name);
	}
}
