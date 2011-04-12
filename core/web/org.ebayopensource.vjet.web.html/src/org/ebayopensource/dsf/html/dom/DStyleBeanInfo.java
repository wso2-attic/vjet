/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class DStyleBeanInfo extends SimpleBeanInfo {

	private final static Class myClz = DStyle.class;
	private final PropertyDescriptor[] m_list;
	
	public DStyleBeanInfo() {
		try {
			final PropertyDescriptor styleRules = 
				new PropertyDescriptor("styleRules", myClz);
			styleRules.setPropertyEditorClass(DStyleRulesPropertyEditor.class);
												
			m_list = new PropertyDescriptor[] {styleRules,
				new PropertyDescriptor("htmlMedia", myClz),
				new PropertyDescriptor("htmlType", myClz)};
		}
		catch (IntrospectionException iexErr) {
			throw new Error(iexErr.toString());
		}
	}
	
	public PropertyDescriptor[] getPropertyDescriptors() {
		return m_list;
	}
	
	public BeanDescriptor getBeanDescriptor() {
		final BeanDescriptor desc = new BeanDescriptor(myClz);
		desc.setShortDescription("A configurable DStyle");
		desc.setDisplayName("HTML Style");
		return desc;
	}
}
