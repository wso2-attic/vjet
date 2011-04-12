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


public interface IAttributeInfoAndData extends IAttributeInfo {
	public Object getData();
	public void setData(final Object o);
}
