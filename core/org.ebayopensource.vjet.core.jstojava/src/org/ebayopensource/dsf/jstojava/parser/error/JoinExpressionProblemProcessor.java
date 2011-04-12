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

public class JoinExpressionProblemProcessor extends ProblemProcessor{

	private static final String ERROR_CODE = "233";
	private static final char DOT_COMMA = ';';
	private static final char SPACE = ' ';
	private static final char END_BRACKET = ')';

	public boolean accept(CategorizedProblem problem) {
		String s =  problem.toString();
		return s.contains(ERROR_CODE);
	}

	public char[] process(CategorizedProblem problem, char[] source) {
		
		int startIndex = problem.getSourceStart();
		int endIndex = problem.getSourceEnd();
		boolean start = false;
		
		for (int i = startIndex; i < endIndex; i++) {
			
			char c = source[i];
			
			if (c==END_BRACKET){
				start = true;
			}
			
			if (start && c==SPACE){
				source[i]=DOT_COMMA;
				break;
			}
		}
		return source;
	}

}
