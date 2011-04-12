/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

public class DirEnum extends BaseSchemaEnum {

	public static final DirEnum LTR = new DirEnum(1, "ltr");
	public static final DirEnum RTL = new DirEnum(2, "rtl");

	private DirEnum(final int id, final String name) {
		super(id, name);
	}
}
