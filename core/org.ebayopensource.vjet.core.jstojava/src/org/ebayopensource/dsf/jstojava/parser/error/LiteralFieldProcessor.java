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

public class LiteralFieldProcessor extends ProblemProcessor {

	private static final String ERROR_BLOCK_NAME = "LiteralField";
	private static final String ERROR_CODE = "231";

	public boolean accept(CategorizedProblem problem) {
		String s = problem.toString();
		return s.contains(ERROR_CODE) && s.contains(ERROR_BLOCK_NAME);
	}

	public char[] process(CategorizedProblem problem, char[] source) {		
		source[problem.getSourceStart()]=' ';
		return source;
	}

}
