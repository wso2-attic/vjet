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
import org.eclipse.mod.wst.jsdt.core.compiler.IProblem;

public class SimpleNameProblemProcessor extends ProblemProcessor {

	private static final String THIS_DOT = "this.";
	private static final String SIMPLE_NAME = "SimpleName";

	@Override
	public boolean accept(CategorizedProblem problem) {
		return (problem.getID() == IProblem.ParsingErrorReplaceTokens)
				&& containsArguments(problem, SIMPLE_NAME);
	}

	@Override
	public char[] process(CategorizedProblem problem, char[] source) {
		String s = String.valueOf(source);
		int index = s.lastIndexOf(THIS_DOT);
		if (index != -1 && source[problem.getSourceStart()] == ')'
				&& problem.getSourceStart() + 1 < source.length)
			if (Character.isWhitespace(source[problem.getSourceStart() + 1])) {
				source[problem.getSourceStart() + 1] = ';';
			} else if (source[problem.getSourceStart() + 1] == '/') {
				source[problem.getSourceStart() + 1] = ';';
				//Jack:"fun()//xxx" to "fun();//xx"
				if (problem.getSourceStart() + 3 < source.length
						&& source[problem.getSourceStart() + 2] == '/') {
					source[problem.getSourceStart() + 3] = '/';
				}
			}
		return source;
	}

}
