/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* Created on Dec 19, 2005 */
package org.ebayopensource.dsf.html.schemas;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;


public class UserDataFactory {

	public static ISchema create(final ISchema schema) {
		final Map<HtmlTypeEnum,IElementInfo> map =
			new HashMap<HtmlTypeEnum,IElementInfo>();
		for (final IElementInfo old:schema) {
			final ElementInfoAndData newElement = new ElementInfoAndData(old);
			map.put(newElement.getType(), newElement);
		}
		final ISchema userSchema = new SchemaImp(map);
		return userSchema;
	}
}
