/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import java.util.Map;


public interface IInputParamValueProvider {

	String getInputParamValue(String key);
	String getInputParamValue(String key, String defaultValue);
	
	String[] getInputParamValues(String key);
	String[] getInputParamValues(String key, String[] defaultValues);	
	
	/**
	 * Answer the map of input param values. Return empty map if there is not any.
	 * @return Map<String,String[]>
	 */
	Map<String,String[]> getInputParamValues();
}
