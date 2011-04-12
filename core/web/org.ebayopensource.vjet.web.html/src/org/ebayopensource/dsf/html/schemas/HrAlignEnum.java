/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

public class HrAlignEnum extends BaseSchemaEnum {

	public static final HrAlignEnum LEFT = new HrAlignEnum(1, "left");
	public static final HrAlignEnum CENTER = new HrAlignEnum(2, "center");
	public static final HrAlignEnum RIGHT = new HrAlignEnum(3, "right");

	private HrAlignEnum(final int id, final String name) {
		super(id, name);
	}
}
