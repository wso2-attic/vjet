/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.beans.PropertyDescriptor;

import org.ebayopensource.dsf.common.binding.IValueBinding;

/** This is an interface that an object that is holding on to a set of
 * properties is supposed to implement.  For all intensive purposes only
 * DNode implements this interface at this time. 
 */
interface IPropertyHolder {
	PropertyDescriptor getPropertyDescriptor(String propertyName);
	
	IValueBinding getIntrinsicPropertyValueBinding(String propertyName);
	
	DNode setDsfIntrinsicPropertyValueBinding(
		String propertyName, IValueBinding binding);
	
	void assertAttributeRelationship(
		IPropertyHolder parent, 
		String attributeName,
		Object attribute);
}
