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
package org.ebayopensource.vjet.eclipse.internal.ui.view.properties;

import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * bean style property source adapter
 * 
 * 
 *
 */
public class DefaultBeanPropertySourceAdapter implements IPropertySource {

	private INodePrinter nodePrinter;
	private IPropertyDescriptor[] propertyDescriptors;
	
	private Object[] propertyValues;
	
	/**
	 * @param astNode
	 * @param nodePrinter
	 */
	public DefaultBeanPropertySourceAdapter(Object node, INodePrinter nodePrinter) {
		this.nodePrinter = nodePrinter;
		this.propertyValues = nodePrinter.getPropertyValuies(node);
		
		String[] propertyNames = this.nodePrinter.getPropertyNames(node);
		this.propertyDescriptors = new PropertyDescriptor[propertyNames.length];
		for (int i = 0; i < propertyNames.length; i++) {
			this.propertyDescriptors[i] = new PropertyDescriptor(propertyNames[i], propertyNames[i]);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	public Object getEditableValue() {
		return this;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return this.propertyDescriptors;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id) {
		for (int i = 0; i < this.propertyDescriptors.length; i++) {
			if (this.propertyDescriptors[i].getId().equals(id))
				return this.propertyValues[i];
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
	 */
	public void resetPropertyValue(Object id) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	public void setPropertyValue(Object id, Object value) {
	}

}
