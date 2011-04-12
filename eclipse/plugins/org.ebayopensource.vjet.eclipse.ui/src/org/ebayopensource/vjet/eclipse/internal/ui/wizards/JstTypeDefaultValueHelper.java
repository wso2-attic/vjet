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
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import org.ebayopensource.dsf.jst.IJstType;

/**
 * helper class regarding for jst type default values
 * 
 * 
 *
 */
class JstTypeDefaultValueHelper {

	/**
	 * helper class
	 */
	private JstTypeDefaultValueHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * get default value in string format
	 * 
	 * @return
	 */
	public static String getDefaultValue(IJstType jstType) {
		//fix bug 3861
		if (jstType == null)
			return null;
		
		String aliasName = jstType.getAlias();
		if ("void".equals(aliasName))
			return null;
		
		return "null";
	}
}
