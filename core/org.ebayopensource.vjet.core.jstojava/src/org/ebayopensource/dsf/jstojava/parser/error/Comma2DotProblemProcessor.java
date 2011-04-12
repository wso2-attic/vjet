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

public class Comma2DotProblemProcessor extends ProblemProcessor {

	public boolean accept(CategorizedProblem problem) {
		return problem.getID() == IProblem.ParsingError;
	}

	public char[] process(CategorizedProblem problem, char[] source) {

		boolean start = false;

//		if (containsArguments(problem, ":", "in")) {
//			String literal = getLiteral(problem.getSourceStart() - 1, source);
//			String astString = ast.toString();
//			String joinedLiteral = getJoinedLiteral(literal,astString);
//		}

		if (source[problem.getSourceStart()] == ',') {
			start = processCommaProblem(problem, source, start);
		}
		
		if (containsArguments(problem, "}",";")){
			source[problem.getSourceEnd()-1]=';';
		}

		return source;
	}

//	private String getJoinedLiteral(String literal, String astString) {
//		//int index = astString.indexOf(str)
//		return null;
//	}

//	private String getLiteral(int sourceStart, char[] source) {
//
//		StringBuffer buffer = new StringBuffer();
//		boolean start = false;
//
//		for (int i = sourceStart; i >= 0; i--) {
//			
//			char c = source[i];
//
//			if (!Character.isWhitespace(c)) {
//				start = true;
//			}
//
//			if (start && Character.isWhitespace(c)) {
//				break;
//			}
//
//			if (start) {
//				buffer.insert(0, c);
//			}
//
//		}
//
//		return buffer.toString();
//	}

	private boolean processCommaProblem(CategorizedProblem problem,
			char[] source, boolean start) {
			//Case:
			//abc:""//int\r\nab, here should not apply this processor
			int slashCount = 0;
		for (int i = problem.getSourceStart() + 1; i < source.length; i++) {
			char c = source[i];
			if (!Character.isWhitespace(c)) {
				start = true;
			}
			
			if (start && Character.isWhitespace(c)) {
				source[i] = ':';
				break;
			} 
			else {
				if (c == '/') {
					slashCount++;
				} else {
					slashCount = 0;
				}
				if (slashCount == 2) {
					//have comment after problem point, jump to the next line
					i = getNextLineIndex(source, i);
					start = false;
					slashCount = 0;
				}
			}
		}
		return start;
	}

private int getNextLineIndex(char[] source, int startPoint) {
	int i = startPoint + 1;
	for (; i < source.length - 1; i++) {
		char c = source[i];
		char c1 = source[i+1];
		if (c == '\r' && c1 == '\n') {
			return i+2;
		}
	}
	return startPoint;
}

}
