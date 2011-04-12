/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import java.util.HashMap;
import java.util.Map;

public class InputParamValueProviderAdapter implements IInputParamValueProvider {

	public String getInputParamValue(final String key) {
		return null;
	}
	
	public String getInputParamValue(
		final String key, final String defaultValue)
	{
		final String value = getInputParamValue(key);
		return value != null ? value : defaultValue;
	}
	
	public String[] getInputParamValues(final String key) {
		return null;
	}
	
	public String[] getInputParamValues(
		final String key, final String[] defaultValues)
	{
		final String[] values = getInputParamValues(key);
		return values != null ? values : defaultValues;
	}
	
	public Map<String,String[]> getInputParamValues(){
		return new HashMap<String,String[]>();
	}
}
