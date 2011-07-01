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
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * AST node property source implementation
 * 
 * 
 * 
 */
public class AstNodePropertySourceAdapter implements IPropertySource {
	private INodePrinter nodePrinter;
	private IPropertyDescriptor[] propertyDescriptors;

	private Object[] propertyValues;
	private IASTNode node;

	/**
	 * @param astNode
	 * @param nodePrinter
	 */
	public AstNodePropertySourceAdapter(IASTNode astNode,
			INodePrinter nodePrinter) {
		this.node = astNode;
		this.nodePrinter = nodePrinter;
		this.propertyValues = nodePrinter.getPropertyValuies(astNode);

		String[] propertyNames = this.nodePrinter.getPropertyNames(astNode);
		this.propertyDescriptors = new PropertyDescriptor[propertyNames.length];
		for (int i = 0; i < propertyNames.length; i++) {
			this.propertyDescriptors[i] = new PropertyDescriptor(
					propertyNames[i], propertyNames[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	public Object getEditableValue() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return this.propertyDescriptors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id) {
		for (int i = 0; i < this.propertyDescriptors.length; i++) {
			if (this.propertyDescriptors[i].getId().equals(id)) {
				Object value = this.propertyValues[i];
				return value;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
	 */
	public boolean isPropertySet(Object id) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
	 */
	public void resetPropertyValue(Object id) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void setPropertyValue(Object id, Object value) {
	}

	//add by patrick
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.node.getClass().getName();
	}

}
