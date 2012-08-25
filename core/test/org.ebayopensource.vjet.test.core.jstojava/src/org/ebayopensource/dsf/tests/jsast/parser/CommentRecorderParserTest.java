/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;




import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.dsf.jstojava.translator.TranslateConfig;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.impl.CompilerOptions;
import org.junit.Test;




//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class CommentRecorderParserTest {

	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Compiles js file without errors")
	public void test1() throws Exception {
		CompilationUnitDeclaration ast = SyntaxTreeFactory2.createAST(null,
				null, "src/org.ebayopensource.dsf/tests/jsast/parser/simplevjo.js.txt",
				null);
		if (ast.compilationResult().hasProblems()) {
			System.err.println(ast.compilationResult().getProblems()[0]
					.toString());
			assertNotNull(null);
		}
		TranslateConfig cfg = new TranslateConfig();
		cfg.setAllowPartialJST(true);
		TranslateCtx translateCtx = new TranslateCtx(cfg);
		SyntaxTreeFactory2.createJST(ast, translateCtx);
	}

	protected Map getCompilerOptions() {
		Map defaultOptions = new HashMap<Object, Object>();
		defaultOptions.put(CompilerOptions.OPTION_LocalVariableAttribute,
				CompilerOptions.GENERATE);
		defaultOptions.put(CompilerOptions.OPTION_ReportUnusedPrivateMember,
				CompilerOptions.WARNING);
//		defaultOptions.put(CompilerOptions.OPTION_ReportUnusedImport,
//				CompilerOptions.WARNING);
		defaultOptions.put(CompilerOptions.OPTION_ReportLocalVariableHiding,
				CompilerOptions.WARNING);
		defaultOptions.put(CompilerOptions.OPTION_ReportFieldHiding,
				CompilerOptions.WARNING);
		defaultOptions
				.put(
						CompilerOptions.OPTION_ReportPossibleAccidentalBooleanAssignment,
						CompilerOptions.WARNING);
		defaultOptions.put(CompilerOptions.OPTION_ReportWrongNumberOfArguments,
				CompilerOptions.WARNING);
		defaultOptions.put(CompilerOptions.OPTION_PreserveUnusedLocal,
				CompilerOptions.PRESERVE);
		defaultOptions.put(CompilerOptions.OPTION_ReportUnnecessaryElse,
				CompilerOptions.WARNING);
		defaultOptions.put(CompilerOptions.OPTION_Unresolved_Field,
				CompilerOptions.ERROR);
		defaultOptions.put(CompilerOptions.OPTION_Unresolved_Method,
				CompilerOptions.ERROR);
		defaultOptions.put(CompilerOptions.OPTION_Unresolved_Type,
				CompilerOptions.ERROR);
		return defaultOptions;
	}

}
