/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** "valign     (top|middle|bottom|baseline) #IMPLIED"
 */
public class CellVAlignEnum extends BaseSchemaEnum {

	public static final CellVAlignEnum TOP = new CellVAlignEnum(1, "top");
	public static final CellVAlignEnum MIDDLE = new CellVAlignEnum(2, "middle");
	public static final CellVAlignEnum BOTTOM = new CellVAlignEnum(3, "bottom");
	public static final CellVAlignEnum BASELINE=new CellVAlignEnum(4, "baseline");

	private CellVAlignEnum(final int id, final String name) {
		super(id, name);
	}
}
