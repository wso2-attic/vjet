/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser;

import java.util.Arrays;
import java.util.List;

import org.eclipse.mod.wst.jsdt.core.compiler.CategorizedProblem;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

public abstract class ProblemProcessor {

	protected CompilationUnitDeclaration ast;

	public CompilationUnitDeclaration getAst() {
		return ast;
	}

	public void setAst(CompilationUnitDeclaration ast) {
		this.ast = ast;
	}

	public abstract char[] process(CategorizedProblem problem, char[] source);

	public abstract boolean accept(CategorizedProblem problem);

	protected boolean containsArguments(CategorizedProblem problem, String... s) {
		List<String> list = Arrays.asList(problem.getArguments());
		boolean contains = true;
		for (int i = 0; i < s.length; i++) {
			String string = s[i];
			contains = list.contains(string);
			if (!contains) {
				break;
			}
		}
		return contains;
	}

}
