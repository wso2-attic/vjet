/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** <!ENTITY % TAlign "(left|center|right)">
 */
public class TableAlignEnum extends BaseSchemaEnum {

	public static final TableAlignEnum LEFT = new TableAlignEnum(1, "left");
	public static final TableAlignEnum CENTER = new TableAlignEnum(2, "center");
	public static final TableAlignEnum RIGHT = new TableAlignEnum(3, "right");

	private TableAlignEnum(final int id, final String name) {
		super(id, name);
	}
}
