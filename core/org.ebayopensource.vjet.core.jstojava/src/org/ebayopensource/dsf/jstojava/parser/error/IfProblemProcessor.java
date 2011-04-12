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

public class IfProblemProcessor extends ProblemProcessor {

	private static final String BLOCK_STATEMENT = "BlockStatement";
	private static final String ERROR_CODE_240 = "240";
	private static final String ERROR_CODE_231 = "231";
	private static final char LEFT_BRACKET = '{';

	@Override
	public boolean accept(CategorizedProblem problem) {
		String s = problem.toString();
		boolean containsErrorCode = s.contains(ERROR_CODE_231) || s.contains(ERROR_CODE_240);
		return s.contains(BLOCK_STATEMENT) && containsErrorCode;
	}

	@Override
	public char[] process(CategorizedProblem problem, char[] source) {
		String s = String.valueOf(source);
		int index = s.lastIndexOf("(this", problem.getSourceStart());
		if (index != -1) {
			int i = s.indexOf(')', index);
			if (i != -1 && Character.isWhitespace(source[i + 1])) {
				source[i + 1] = LEFT_BRACKET;
			}
		}
		return source;
	}

}
