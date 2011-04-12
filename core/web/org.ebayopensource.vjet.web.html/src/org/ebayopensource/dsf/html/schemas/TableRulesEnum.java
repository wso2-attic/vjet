/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** <!ENTITY % TRules "(none | groups | rows | cols | all)">
 */
public class TableRulesEnum extends BaseSchemaEnum {

	public static final TableRulesEnum NONE = new TableRulesEnum(1, "none");
	public static final TableRulesEnum GROUPS = new TableRulesEnum(2, "groups");
	public static final TableRulesEnum ROWS = new TableRulesEnum(3, "rows");
	public static final TableRulesEnum COLS = new TableRulesEnum(4, "cols");
	public static final TableRulesEnum ALL = new TableRulesEnum(5, "all");

	private TableRulesEnum(final int id, final String name) {
		super(id, name);
	}
}
