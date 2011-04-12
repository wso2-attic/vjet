/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

/** From iframe:
 * scrolling   (yes|no|auto)  auto      -- scrollbar or none --
 */
public class ScrollingEnum extends BaseSchemaEnum {

	public static final ScrollingEnum YES = new ScrollingEnum(1, "yes");
	public static final ScrollingEnum NO = new ScrollingEnum(2, "no");
	public static final ScrollingEnum AUTO = new ScrollingEnum(3, "auto");

	private ScrollingEnum(final int id, final String name) {
		super(id, name);
	}
}
