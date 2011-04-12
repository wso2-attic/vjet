/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.error;

import org.ebayopensource.dsf.jstojava.parser.ProblemProcessor;
import org.eclipse.mod.wst.jsdt.core.compiler.CategorizedProblem;

public class ErrorTokenProblemProcessor extends ProblemProcessor {

	private static final String ERROR_CODE = "233";
	private static final char SPACE = ' ';
	private static final char COMMA = ',';

	public char[] process(CategorizedProblem problem, char[] source) {

		int index = problem.getSourceStart();
		if (source[index] == COMMA) {
			source[index] = SPACE;
		}
		return source;
	}

	public boolean accept(CategorizedProblem problem) {
		String s = problem.toString();
		return s.contains(ERROR_CODE);
	}

}
