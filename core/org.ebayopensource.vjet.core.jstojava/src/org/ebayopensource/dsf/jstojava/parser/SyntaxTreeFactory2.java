/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser;

import java.util.Locale;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.error.ProblemProcessorFactory;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.robust.RobustASTTranslator;
import org.eclipse.mod.wst.jsdt.core.compiler.CategorizedProblem;
import org.eclipse.mod.wst.jsdt.internal.compiler.CompilationResult;
import org.eclipse.mod.wst.jsdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.mod.wst.jsdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.mod.wst.jsdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.mod.wst.jsdt.internal.compiler.problem.DefaultProblemFactory;
import org.eclipse.mod.wst.jsdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.mod.wst.jsdt.internal.core.util.CommentRecorderParser;

public class SyntaxTreeFactory2 {

	private static final int TRY_FIXED_PROBLEMS_COUNT = 2;

	public static CompilationUnitDeclaration createAST(Map<?, ?> settings,
			char[] source, String filename, String encoding) {

		CompilerOptions options = new CompilerOptions(settings);
		ProblemReporter problemReporter = new ProblemReporter(
				DefaultErrorHandlingPolicies.exitAfterAllProblems(), options,
				new DefaultProblemFactory(Locale.getDefault()));
		CommentRecorderParser parser = new CommentRecorderParser(
				problemReporter, true);
		// CompletionParser parser = new CompletionParser(problemReporter);
		ICompilationUnit sourceUnit = new CompilationUnit(source, filename,
				encoding);
		CompilationResult compilationUnitResult = new CompilationResult(
				sourceUnit, 0, 0, options.maxProblemsPerUnit);

		CompilationUnitDeclaration ast = parser.parse(sourceUnit,
				compilationUnitResult);

		return ast;
	}
	
	public static AstCompilationResult createASTCompilationResult(Map<?, ?> settings,
			char[] source, String filename, String encoding) {

		CompilerOptions options = new CompilerOptions(settings);
		ProblemReporter problemReporter = new ProblemReporter(
				DefaultErrorHandlingPolicies.exitAfterAllProblems(), options,
				new DefaultProblemFactory(Locale.getDefault()));
		CommentRecorderParser parser = new CommentRecorderParser(
				problemReporter, true);
		ICompilationUnit sourceUnit = new CompilationUnit(source, filename,
				encoding);
		CompilationResult compilationUnitResult = new CompilationResult(
				sourceUnit, 0, 0, options.maxProblemsPerUnit);

		CompilationUnitDeclaration ast = parser.parse(sourceUnit,
				compilationUnitResult);

		return new AstCompilationResult(ast, parser.hasNonFakeTokenInsertionError());
	}

	public static IJstType createJST(CompilationUnitDeclaration ast,
			TranslateCtx tctx) {
		if (ast == null) {
			return null;
		}else{
			tctx.setAST(ast);
		}
		tctx.getCommentCollector().handle(ast,tctx.getErrorReporter(),tctx.getSourceUtil());
		RobustASTTranslator trans = new RobustASTTranslator(tctx);
		tctx.setAST(ast);

		IJstType jstType = null;
		try {
			jstType = trans.translate(ast);
		} finally {
			// TODO -- removing this null need not sure why null is here
//			tctx.setAST(null);
		}
		return jstType;
	}

	public static IJstType createJST(Map<?, ?> settings, char[] source,
			String filename, String encoding, TranslateCtx tctx) {
		CompilationUnitDeclaration ast = createASTCompilationResult
			(settings, source, filename, encoding)
			.getCompilationUnitDeclaration();
		// Add errors to translate context -- making default if AST and JST are
		// done together
		addAstErrorMessagesToCtx(new String(source), ast.compilationResult
				.getAllProblems(), tctx.getErrorReporter());
//		ast = fixedProblems(settings, source, filename, encoding, ast);
		return createJST(ast, tctx);
	}

	protected static void addAstErrorMessagesToCtx(String resource,
			CategorizedProblem[] allProblems, ErrorReporter errorReporter) {
		if (allProblems == null || allProblems.length == 0) {
			return;
		}

		for (CategorizedProblem cp : allProblems) {

			if (cp.isError()) {
				errorReporter.error(cp.getMessage(), resource, cp
						.getSourceStart(), cp.getSourceEnd(), cp
						.getSourceLineNumber(), cp.getSourceStart());
			}
			if (cp.isWarning()) {
				errorReporter.warning(cp.getMessage(), resource, cp
						.getSourceStart(), cp.getSourceEnd(), cp
						.getSourceLineNumber(), cp.getSourceStart());
			}
		}

	}

	public static CompilationUnitDeclaration fixedProblems(Map<?, ?> settings,
			char[] source, String filename, String encoding,
			CompilationUnitDeclaration ast) {

		int count = TRY_FIXED_PROBLEMS_COUNT;
		while (ast.compilationResult.hasErrors() && count != 0) {
			ast = tryFixed(settings, source, filename, encoding, ast);
			count--;
		}

		return ast;
	}

	private static CompilationUnitDeclaration tryFixed(Map<?, ?> settings,
			char[] source, String filename, String encoding,
			CompilationUnitDeclaration ast) {
		char[] s = source;
		CategorizedProblem[] problems;
		problems = ast.compilationResult.getAllProblems();
		for (CategorizedProblem problem : problems) {
			ProblemProcessor processor;
			processor = ProblemProcessorFactory.getProcessor(problem);
			processor.setAst(ast);
			s = processor.process(problem, s);
		}
		ast = createASTCompilationResult(settings, s, filename, encoding)
			.getCompilationUnitDeclaration();
		return ast;
	}
}