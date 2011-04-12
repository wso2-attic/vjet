/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * There is only one instance of ComponentManager per component type
 * 
 */
public abstract class BaseDNodeManager {
			
//	protected abstract IDsfComponent create();

	
// TODO: We should remove this class - we don't expect superclass for comp mgrs
	public ResourceBundle getResourceBundle(String resourceName, Locale locale){
		return ResourceBundle.getBundle(
			this.getClass().getPackage().getName() + "."+ resourceName, locale);		
	}

}
