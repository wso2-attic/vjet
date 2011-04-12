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

public class TryBracketProblemProcessor extends ProblemProcessor {

	private static final String ERROR_CODE = "236";
	private static final String MISPLACED = "misplaced";
	private static final char SPACE = ' ';
	private static final char CLOSE_BRACKET = '}';

	@Override
	public boolean accept(CategorizedProblem problem) {
		String s = problem.toString();
		return s.contains(MISPLACED) && s.contains(ERROR_CODE);
	}

	@Override
	public char[] process(CategorizedProblem problem, char[] source) {
		int index = problem.getSourceEnd();
		if (source[index] == CLOSE_BRACKET) {
			source[index] = SPACE;
		}
		return source;
	}

}
