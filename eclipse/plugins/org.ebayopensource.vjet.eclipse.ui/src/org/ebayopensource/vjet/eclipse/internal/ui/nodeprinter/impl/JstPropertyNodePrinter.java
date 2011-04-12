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

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter;

/**
 * 
 *
 */
public class JstPropertyNodePrinter implements INodePrinter {

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyNames(Object node)
	 */
	public String[] getPropertyNames(Object node) {
		return new String[] {"object_id", "name"};
	}

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyValuies(java.lang.Object)
	 */
	public Object[] getPropertyValuies(Object node) {
		String objectID = String.valueOf(node.hashCode());
		IJstProperty jstProperty = (IJstProperty)node;
		
		String name = jstProperty.getName().getName();
		return new Object[] {objectID, name};
	}

}
