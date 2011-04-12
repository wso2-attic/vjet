/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import static org.junit.Assert.assertFalse;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.eclipse.mod.wst.jsdt.core.compiler.CategorizedProblem;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

public abstract class BaseTest {

	CompilationUnitDeclaration prepareAst(String filename, String input) {
		// get absolute file path
		if (input == null && filename != null) {
			URL resource = getClass().getResource(filename);
			filename = resource.getPath();
			input = VjoParser.getContent(resource);
		}
		CompilationUnitDeclaration ast = SyntaxTreeFactory2.createAST(null,
				input != null ? input.toCharArray() : null, filename, null);
		if (ast.compilationResult().hasProblems()) {
			CategorizedProblem[] problems = ast.compilationResult()
					.getProblems();
			for (CategorizedProblem cp : problems) {
				System.err.println(cp.toString());
			}
			assertFalse("Can't parse " + filename + ": " + ast.compilationResult(), ast.compilationResult().hasProblems());
		}
		return ast;
	}

	protected static IJstMethod getMethodByName(List <? extends IJstMethod> methods,
			String name) {
		for (IJstMethod method : methods) {
			if (name.equals(method.getName().getName())) {
				return method;
			}
		}
		return null;
	}

	protected static IJstProperty getPropertyByName(
			Collection<IJstProperty> properties, String name) {
		for (IJstProperty property : properties) {
			if (name.equals(property.getName().getName())) {
				return property;
			}
		}
		return null;
	}
}