/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** method      (GET|POST)     GET       -- HTTP method used to submit the form--
 */
public class FormMethodEnum extends BaseSchemaEnum {

	public static final FormMethodEnum GET = new FormMethodEnum(1, "get");
	public static final FormMethodEnum POST = new FormMethodEnum(2, "post");

	private FormMethodEnum(final int id, final String name) {
		super(id, name);
	}
}
