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
package org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter;



/**
 * 
 *
 */
public interface INodePrinter {
	
	/**
	 * get property name list
	 * 
	 * @param node
	 * @return
	 */
	public String[] getPropertyNames(Object node);
	
	/**
	 * get property value list
	 * 
	 * @param node
	 * @return
	 */
	public Object[] getPropertyValuies(Object node);
}
