/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import java.util.Iterator;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

/** This is used to define a schema.  Right now we only need a typed map
 * for each element.
 */
public interface ISchema extends  Iterable<IElementInfo> {
	public IElementInfo getElementInfo(final HtmlTypeEnum type);
	public Iterator<IElementInfo> iterator();
}
