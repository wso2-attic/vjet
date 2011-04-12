/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom.impl;

public abstract class DCssBaseColor {
    protected static boolean isPercentage(final String value) {
    	return (value.charAt(value.length() - 1) == '%');
    }
    
	protected static void appendPercentage(final StringBuilder sb, String value) {
   		value = value.substring(0, value.length() - 1);
   		int i = Integer.parseInt(value);
   		if(i < 0) {
   			i = 0;
   		} 
   		else if(i > 100) {
   			i = 100;
   		}
   		sb.append(i).append('%');
    }
}
