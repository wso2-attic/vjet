/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** type attribute for Ul:
 * <!ENTITY % ULStyle "(disc|square|circle)">
 */
public class UlStyleEnum extends BaseSchemaEnum {

	public static final UlStyleEnum DISC = new UlStyleEnum(1, "disc");
	public static final UlStyleEnum SQUARE = new UlStyleEnum(2, "square");
	public static final UlStyleEnum CIRCLE = new UlStyleEnum(3, "circle");

	private UlStyleEnum(final int id, final String name) {
		super(id, name);
	}
}
