/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public class XhtmlElementInfo extends ElementInfo {
	public XhtmlElementInfo(
		final HtmlTypeEnum type,
		final IAttributeInfoMap attributeInfoMap,
		final IContentModel contentModel)
	{
		super(type, attributeInfoMap, contentModel, true, true);
	}
}
