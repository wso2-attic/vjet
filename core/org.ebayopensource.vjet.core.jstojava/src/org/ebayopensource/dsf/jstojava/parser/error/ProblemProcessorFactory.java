/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.error;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jstojava.parser.ProblemProcessor;
import org.eclipse.mod.wst.jsdt.core.compiler.CategorizedProblem;

public class ProblemProcessorFactory {

	private static List<ProblemProcessor> processors = new ArrayList<ProblemProcessor>();

	static {
		processors.add(new SimpleNameProblemProcessor());
		processors.add(new ForProblemProcessor());
		processors.add(new MissCommaProblemProcessor());
		processors.add(new MissSemiColProblemProcessor());
		processors.add(new Dot2SemiColProblemProcessor());
		processors.add(new ErrorTokenProblemProcessor());
		processors.add(new LiteralFieldProcessor());
		processors.add(new JoinExpressionProblemProcessor());
		processors.add(new Comma2DotProblemProcessor());
		processors.add(new Question2SpaceProblemProcessor());
		processors.add(new ObjectLiteralProblemProcessor());		
		processors.add(new TryProblemProcessor());
		processors.add(new TryBracketProblemProcessor());
		processors.add(new IfProblemProcessor());
	}

	public static ProblemProcessor getProcessor(CategorizedProblem problem) {

		ProblemProcessor problemProcessor = null;

		for (ProblemProcessor processor : processors) {
			if (processor.accept(problem)) {
				problemProcessor = processor;
				break;
			}
		}

		if (problemProcessor == null) {
			problemProcessor = new EmptyProblemProcessor();
		}

		return problemProcessor;
	}

}
