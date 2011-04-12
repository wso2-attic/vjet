/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** valuetype   (DATA|REF|OBJECT) DATA   -- How to interpret value --
 * valuetype   (data|ref|object) "data"
 *
 */
public class ParamValueTypeEnum extends BaseSchemaEnum {

	public static final ParamValueTypeEnum DATA = new ParamValueTypeEnum(1, "data");
	public static final ParamValueTypeEnum REF = new ParamValueTypeEnum(2, "ref");
	public static final ParamValueTypeEnum OBJECT = new ParamValueTypeEnum(3, "object");

	private ParamValueTypeEnum(final int id, final String name) {
		super(id, name);
	}
}
