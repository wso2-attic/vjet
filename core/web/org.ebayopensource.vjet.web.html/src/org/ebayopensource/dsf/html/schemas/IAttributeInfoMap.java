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


public interface IAttributeInfoMap extends Iterable<IAttributeInfo>{
	void put(final IAttributeInfo attributeInfo);
	IAttributeInfo get(final String attributeName);
	Iterator<IAttributeInfo> iterator();
//	public interface Iterator {
//		boolean hasNext();
//		AttributeInfo next();
//	}
}
