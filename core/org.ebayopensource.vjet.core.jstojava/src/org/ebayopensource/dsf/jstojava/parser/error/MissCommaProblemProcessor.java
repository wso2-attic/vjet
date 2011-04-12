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

public class MissCommaProblemProcessor extends ProblemProcessor {

	public boolean accept(CategorizedProblem problem) {
		if(problem.getID() == IProblem.ParsingError) {
			String message = problem.getMessage();
			if (message!= null && message.endsWith(", expected")){
				return true;
			}
		}
		return false;
	}

	public char[] process(CategorizedProblem problem, char[] source) {

		if (source[problem.getSourceStart()] == '.'
				&& containsArguments(problem, ".", ","))
			if (Character.isWhitespace(source[problem.getSourceStart() + 1])) {
				source[problem.getSourceStart() + 1] = ',';
			} else if (source[problem.getSourceStart() + 1] == '/') {
				source[problem.getSourceStart() + 1] = ',';
				if (problem.getSourceStart() + 3 < source.length
						&& source[problem.getSourceStart() + 2] == '/') {
					source[problem.getSourceStart() + 3] = '/';
				}
			}
		return source;
	}

}
