/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;


public class ValueHelper {
	
	private static final String NULL = "null";
	private static final String COMMA = ",";
	
	/**
	 * Convert an object value to string. For non-array object,
	 * return object's toString(). For array object, concatenate
	 * each element's toString().
	 * @param value Object
	 * @return String
	 */
	public static String toString(Object value){
		
		if (value == null){
			return NULL;
		}
		
		if (!value.getClass().isArray()){
			return value.toString();
		}
		
		StringBuilder rb = new StringBuilder();
		
		Object[] values = (Object[])value;
		for (int i=0; i<values.length; i++){
			if (i > 0){
				rb.append(COMMA);
			}
			if (values[i] == null){
				rb.append(NULL);
			}
			else {
				rb.append(values[i].toString());
			}	
		}
		
		return rb.toString();
	}
}
