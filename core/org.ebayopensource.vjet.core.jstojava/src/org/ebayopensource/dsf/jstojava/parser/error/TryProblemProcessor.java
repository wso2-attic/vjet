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

/**
 * 
 * Replace first symbol [t]ty to space ' '.
 * 
 * 
 *
 */
public class TryProblemProcessor extends ProblemProcessor {

	private static final String FINALLY = "Finally";
	private static final String BLOCK_STATEMENTS = "BlockStatements";
	private static final String ERROR_CODE = "240";
	private static final char SPACE = ' ';

	@Override
	public boolean accept(CategorizedProblem problem) {
		String s = problem.toString();
		return s.contains(BLOCK_STATEMENTS) && s.contains(ERROR_CODE)
				&& s.contains(FINALLY);
	}

	@Override
	public char[] process(CategorizedProblem problem, char[] source) {
		String s = String.valueOf(source);
		int index = s.lastIndexOf("try", problem.getSourceStart());
		if (index != -1) {
			source[index] = SPACE;
		}
		return source;
	}

}
