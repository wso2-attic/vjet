/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter;

/**
 * 
 *
 */
public class BeanStyleNodePrinter implements INodePrinter {

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyNames()
	 */
	public String[] getPropertyNames(Object node) {
		List<String> propertyNames = new ArrayList<String>();
		propertyNames.add("object_id");
		
		try {
			Class nodeClass = node.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(nodeClass);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				propertyNames.add(propertyDescriptors[i].getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return propertyNames.toArray(new String[propertyNames.size()]);
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyValuies(java.lang.Object)
	 */
	public Object[] getPropertyValuies(Object node) {
		List propertyValues = new ArrayList();
		propertyValues.add(node.hashCode());
		
		try {
			Class nodeClass = node.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(nodeClass);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				if (propertyDescriptors[i].getReadMethod() != null) {
					try {
						Object value = propertyDescriptors[i].getReadMethod().invoke(node);
						propertyValues.add(value);
					} catch (Exception e) {
						propertyValues.add(null);
					}
				}
				else
					propertyValues.add(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return propertyValues.toArray();
	}

}
