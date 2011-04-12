/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** from iframe
 * frameborder (1|0)          1         -- request frame borders? --
 *
 */
public class FrameBorder extends BaseSchemaEnum {

	public static final FrameBorder ONE = new FrameBorder(1, "1");
	public static final FrameBorder ZERO = new FrameBorder(2, "0");

	private FrameBorder(final int id, final String name) {
		super(id, name);
	}
}
