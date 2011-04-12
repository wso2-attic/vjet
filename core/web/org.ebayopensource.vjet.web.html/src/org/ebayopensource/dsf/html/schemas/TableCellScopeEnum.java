/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** <!ENTITY % Scope "(row|col|rowgroup|colgroup)">
 *  <!ENTITY % Scope "(row|col|rowgroup|colgroup)">
 *
 */
public class TableCellScopeEnum extends BaseSchemaEnum {

	public static final TableCellScopeEnum ROW = new TableCellScopeEnum(1, "rows");
	public static final TableCellScopeEnum COL = new TableCellScopeEnum(2, "cols");
	public static final TableCellScopeEnum ROW_GROUP = new TableCellScopeEnum(3, "rowgroup");
	public static final TableCellScopeEnum COLS_GROUP = new TableCellScopeEnum(4, "colgroup");

	private TableCellScopeEnum(final int id, final String name) {
		super(id, name);
	}
}
