/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;

import java.util.regex.Pattern;

public final class RegExErrorFilter implements ErrorFilter {
	
	public static final String MATCH_ALL = ".*";
	public static final String MATCH_DOT_ANY = "\\..*";
	
	private Pattern m_pattern;
	
	//
	// Constructor(s)
	//
	
	/**
	 * Create an instance that will answer true if an ErrorObjects' ErrorId
	 * full name matches the regex.
	 * 
	 * @param regex Regular expression to match the ErrorObjects' ErrorId 
	 * full name.  May be null in which case matches will always return false.
	 */
	public RegExErrorFilter(String regex) {
		m_pattern = Pattern.compile(regex);
	}

	//
	// Satisfy ErrorFilter
	//
	/**
	 * Answers true if the ErrorId string in the supplied ErrorObject matches
	 * the regular expression String this instance was created with.  If the
	 * regex String was null and/or the passed in ErrorObject was null we will
	 * answer false.
	 * 
	 * @param errorObject ErrorObject to match.  Can be null.
	 */
	public boolean matches(ErrorObject errorObject) {
		if (m_pattern == null || errorObject == null) {
			return false;
		}
		
		return m_pattern.matcher(errorObject.getId().getFullName()).matches();
	}

	//
	// API
	//
	public Pattern getPattern() {
		return m_pattern ;
	}
}
