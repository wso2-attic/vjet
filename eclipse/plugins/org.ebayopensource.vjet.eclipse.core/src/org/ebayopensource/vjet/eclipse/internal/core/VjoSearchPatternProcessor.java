/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.core;

import org.eclipse.dltk.mod.core.ISearchPatternProcessor;

public class VjoSearchPatternProcessor implements ISearchPatternProcessor {

	public char[] extractDeclaringTypeQualification(String patternString) {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] extractDeclaringTypeSimpleName(String patternString) {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] extractSelector(String patternString) {
		// TODO Auto-generated method stub
		return null;
	}

	public String extractTypeChars(String patternString) {
		return patternString;
	}

	public char[] extractTypeQualification(String patternString) {
		return patternString.toCharArray();
	}

	public String getDelimiterReplacementString() {
		// TODO Auto-generated method stub
		return null;
	}
}
