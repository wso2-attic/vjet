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

public class ForProblemProcessor extends ProblemProcessor {


	private static final String FOR_STATEMENT = "ForStatement";
	private static final String STATEMENT = ") Statement";

	@Override
	public boolean accept(CategorizedProblem problem) {
		return (problem.getID() == IProblem.ParsingErrorInsertToComplete) && containsArguments(problem, STATEMENT,FOR_STATEMENT);
	}

	@Override
	public char[] process(CategorizedProblem problem, char[] source) {
		String s = String.valueOf(source);
		int index = s.lastIndexOf(")", problem.getSourceStart());
		if (index != -1 && problem.getSourceEnd()+1 < source.length && 
				Character.isWhitespace(source[index+1])) {
			source[index+1]=';';
		}
		return source;
	}

}
