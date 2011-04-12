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

import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter;

/**
 * common node printer for all IJstStmt sub types
 * 
 * 
 *
 */
public class JstStmtCommonNodePrinter implements INodePrinter{
	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyNames(Object node)
	 */
	public String[] getPropertyNames(Object node) {
		// TODO Auto-generated method stub
		return new String[] {"object_id", "text"};
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.INodePrinter#getPropertyValuies(java.lang.Object)
	 */
	public Object[] getPropertyValuies(Object node) {
		String objectID = String.valueOf(node.hashCode());
		IStmt stmt = (IStmt)node;
		
		String text = stmt.toStmtText();
		return new Object[] {objectID, text};
	}
	
}
