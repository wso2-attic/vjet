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

public class Dot2SemiColProblemProcessor extends ProblemProcessor {


	@Override
	public boolean accept(CategorizedProblem problem) {
		return (problem.getID() == IProblem.ParsingError) && containsArguments(problem, ".",";");
	}

	@Override
	public char[] process(CategorizedProblem problem, char[] source) {
		if (problem.getSourceEnd()+1 < source.length && 
				Character.isWhitespace(source[problem.getSourceEnd()+1])) {
			source[problem.getSourceEnd()+1]=';';
		}
		return source;
	}

}
