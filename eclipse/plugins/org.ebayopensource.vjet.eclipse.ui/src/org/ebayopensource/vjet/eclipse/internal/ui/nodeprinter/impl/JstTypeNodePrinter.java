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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter;

/**
 * 
 *
 */
public class JstTypeNodePrinter implements INodePrinter {

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyNames(Object node)
	 */
	public String[] getPropertyNames(Object node) {
		return new String[] {"object_id", "name", "local_name", "package", "group"};
	}

	public Object[] getPropertyValuies(Object node) {
		String objectID = String.valueOf(node.hashCode());
		IJstType jstType = (IJstType)node;
		
		String name = jstType.getName();
		String localName = jstType.getAlias();
		
		if (jstType.getPackage() == null)
			return new Object[] {name, localName, null, null};
		
		String packageName = jstType.getPackage().getName();
		String groupName = jstType.getPackage().getGroupName();
		return new Object[] {objectID, name, localName, packageName, groupName};
	}

}
