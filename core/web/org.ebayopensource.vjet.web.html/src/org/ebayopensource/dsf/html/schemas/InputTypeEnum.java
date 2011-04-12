/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** <!ENTITY % InputType
  "(TEXT | PASSWORD | CHECKBOX |
    RADIO | SUBMIT | RESET |
    FILE | HIDDEN | IMAGE | BUTTON)"
   >
  <!ENTITY % InputType
  "(text | password | checkbox |
    radio | submit | reset |
    file | hidden | image | button)"
   >
*/
public class InputTypeEnum extends BaseSchemaEnum {

	public static final InputTypeEnum TEXT = new InputTypeEnum(1, "text");
	public static final InputTypeEnum PASSWORD=new InputTypeEnum(2, "password");
	public static final InputTypeEnum CHECKBOX=new InputTypeEnum(3, "checkbox");
	public static final InputTypeEnum RADIO = new InputTypeEnum(4, "radio");
	public static final InputTypeEnum SUBMIT = new InputTypeEnum(5, "submit");
	public static final InputTypeEnum RESET = new InputTypeEnum(6, "reset");
	public static final InputTypeEnum FILE = new InputTypeEnum(7, "file");
	public static final InputTypeEnum HIDDEN = new InputTypeEnum(8, "hidden");
	public static final InputTypeEnum IMAGE = new InputTypeEnum(9, "image");
	public static final InputTypeEnum BUTTON = new InputTypeEnum(10, "button");

	private InputTypeEnum(final int id, final String name) {
		super(id, name);
	}
}
