/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;

/**
 * An interface for tools/scripts to set/get mocked-bean properties.
 * The avaliable properties (name and type) can be retrieved from BeanInfo
 * by calling BeanInfo's getPropertyDescriptors method.
 * 
 * All mocked objects will implement this interface in addition to their
 * real bean-style interface.
 */
public interface IMockBean {
	Object getMockValue(String name);
	void setMockValue(String name, Object value);
	Object getMockValue(PropertyDescriptor propertyDesc);
	void setMockValue(PropertyDescriptor propertyDesc, Object value);
	BeanInfo getBeanInfo();
}
