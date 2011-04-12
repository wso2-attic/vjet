/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

public interface IAttributeInfo {
	public String getName();
	public String getDefaultValue();
	public AttributeDefault getAttrDefault();
	public AttributeDataType getDataType();
//	/** indicates whether the value is valid.  Please note that ID's and
//	 * similar attribute types must look at the whole DOM for uniqueness.
//	 * ID uniqueness within the whole DOM will not be implemented in this.
//	 */
//	public boolean isValid(String value);
}
