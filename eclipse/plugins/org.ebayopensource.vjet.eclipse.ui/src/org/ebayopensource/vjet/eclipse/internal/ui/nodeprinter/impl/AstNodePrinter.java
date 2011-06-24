/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl;

import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.view.properties.AstNodesPropertySourceAdapter;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;

/**
 * Node printer for ast node.
 * 
 *  Ouyang
 * 
 */
public class AstNodePrinter implements INodePrinter {

	private INodePrinter m_printer;

	public AstNodePrinter(INodePrinter printer) {
		if (printer == null) {
			throw new IllegalArgumentException();
		}
		this.m_printer = printer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyNames(java.lang.Object)
	 */
	@Override
	public String[] getPropertyNames(Object node) {
		return m_printer.getPropertyNames(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyValuies(java.lang.Object)
	 */
	@Override
	public Object[] getPropertyValuies(Object node) {
		Object[] propertyValues = m_printer.getPropertyValuies(node);
		// check ast node array
		for (int i = 0; i < propertyValues.length; i++) {
			if (propertyValues[i] instanceof IASTNode[]) {
				propertyValues[i] = new AstNodesPropertySourceAdapter(
						(IASTNode[]) propertyValues[i]);
			}
		}
		return propertyValues;
	}

}
