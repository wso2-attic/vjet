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
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.NodePrinterFactory;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * IPropertySource adapter factory for ast node
 * 
 * 
 *
 */
public class AstNodePropertiesAdapterFactory implements IAdapterFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		INodePrinter nodePrinter = NodePrinterFactory.getNodePrinter(adaptableObject);
		if (nodePrinter == null)
			return null;
		
		
		if (adaptableObject instanceof IASTNode)
			return new AstNodePropertySourceAdapter((IASTNode)adaptableObject, nodePrinter);
		
		return new DefaultBeanPropertySourceAdapter(adaptableObject, nodePrinter);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] {IPropertySource.class};
	}

}
