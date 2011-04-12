/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** This is for the clear attribute of the br element; it only seems to be in
 * the 4.01 transitional spec.
 * clear       (left|all|right|none) none -- control of text flow --
 */
public class ClearEnum extends BaseSchemaEnum {

	public static final ClearEnum LEFT = new ClearEnum(1, "left");
	public static final ClearEnum ALL = new ClearEnum(2, "all");
	public static final ClearEnum RIGHT = new ClearEnum(3, "right");
	public static final ClearEnum NONE = new ClearEnum(4, "none");

	private ClearEnum(final int id, final String name) {
		super(id, name);
	}
}
