/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** <!ENTITY % align "align (left|center|right|justify)  #IMPLIED"
                   -- default is left for ltr paragraphs, right for rtl --
  >
 */
public class TextAlignEnum extends BaseSchemaEnum {

	public static final TextAlignEnum LEFT = new TextAlignEnum(1, "left");
	public static final TextAlignEnum CENTER = new TextAlignEnum(2, "center");
	public static final TextAlignEnum RIGHT = new TextAlignEnum(3, "right");
	public static final TextAlignEnum JUSTIFY = new TextAlignEnum(4, "justify");

	private TextAlignEnum(final int id, final String name) {
		super(id, name);
	}
}
