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

public class Question2SpaceProblemProcessor extends ProblemProcessor{

	public boolean accept(CategorizedProblem problem) {
		String s = problem.toString();
		return s.contains("231") && s.contains("?");
	}

	public char[] process(CategorizedProblem problem, char[] source) {
		for (int i = problem.getSourceStart(); i < source.length; i++) {
			char c = source[i];
			if (Character.isWhitespace(c)){
				source[i] = ',';
				break;
			}
		}
		return source;
	}

}
