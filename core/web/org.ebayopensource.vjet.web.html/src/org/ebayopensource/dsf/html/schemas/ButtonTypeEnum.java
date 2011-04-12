/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** type        (button|submit|reset) submit -- for use as form button --
 */
public class ButtonTypeEnum extends BaseSchemaEnum {

	public static final ButtonTypeEnum BUTTON = new ButtonTypeEnum(1, "button");
	public static final ButtonTypeEnum SUBMIT = new ButtonTypeEnum(2, "submit");
	public static final ButtonTypeEnum RESET = new ButtonTypeEnum(3, "reset");

	private ButtonTypeEnum(final int id, final String name) {
		super(id, name);
	}
}
