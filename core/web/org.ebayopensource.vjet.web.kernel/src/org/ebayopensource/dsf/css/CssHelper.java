/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css;

class CssHelper {
	private static final String SCOPE_SEPARATOR = "-"; 
	
	static String determineName(final String scope, final String name) {
		String s;
		if (scope != null && scope.trim().length() > 0) {
			s = scope + SCOPE_SEPARATOR + name;
		} 
		else {
			s = name;
		}
		return s;
	}
}
