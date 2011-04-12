/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** <!ENTITY % TFrame "(void|above|below|hsides|lhs|rhs|vsides|box|border)">
 */
public class TableFrameEnum extends BaseSchemaEnum {

	public static final TableFrameEnum VOID = new TableFrameEnum(1, "void");
	public static final TableFrameEnum ABOVE=new TableFrameEnum(2, "above");
	public static final TableFrameEnum BELOW=new TableFrameEnum(3, "below");
	public static final TableFrameEnum HSIDES = new TableFrameEnum(4, "hsides");
	public static final TableFrameEnum LHS = new TableFrameEnum(5, "lhs");
	public static final TableFrameEnum RHS = new TableFrameEnum(6, "rhs");
	public static final TableFrameEnum VSIDES = new TableFrameEnum(7, "vsides");
	public static final TableFrameEnum BOX = new TableFrameEnum(8, "box");
	public static final TableFrameEnum BORDER = new TableFrameEnum(9, "border");

	private TableFrameEnum(final int id, final String name) {
		super(id, name);
	}
}
