/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.BlockTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.ast2jst.TranslatorFactory;
import org.eclipse.mod.wst.jsdt.core.ast.IObjectLiteralField;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Block;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FunctionExpression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.IfStatement;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ObjectLiteral;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class BlockTranslatorTest extends BaseTest {

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Tests JstBlock statement. JstBlock statement should contain MtdInvocationExpr & BreakStmt")
	// depends on StatementTranslator.toBreakStmt(BreakStatement, JstType,
	// IJstNode)
	public void testProcessField() throws Exception {
		CompilationUnitDeclaration ast = prepareAst(
				"blockTranslatorTest.js.txt", null);
		assertNotNull(ast);
		MessageSend ms = (MessageSend) ast.getStatements()[0];
		ObjectLiteral objLit = (ObjectLiteral) ms.getArguments()[0];
		IObjectLiteralField objectLiteralField = objLit.getFields()[0];
		FunctionExpression initializer = (FunctionExpression) objectLiteralField
				.getInitializer();
		MethodDeclaration methodDeclaration = initializer
				.getMethodDeclaration();
		IfStatement ifStatement = (IfStatement) methodDeclaration
				.getStatements()[0];
		Block thenStatement = (Block) ifStatement.thenStatement;

		TranslateConfig cfg = new TranslateConfig();
		cfg.setAllowPartialJST(true);
		TranslateCtx translateCtx = new TranslateCtx(cfg);
		translateCtx.setAST(ast);

		BlockTranslator blockTranslator = (BlockTranslator) TranslatorFactory
				.getTranslator(thenStatement, translateCtx);
		blockTranslator.setParent(new IfStmt().getBody());
		JstBlock jstBlock = blockTranslator.translate(thenStatement);

		assertEquals(thenStatement.statements.length, jstBlock.getStmts()
				.size());
		assertTrue(jstBlock.getStmts().get(0) instanceof MtdInvocationExpr);
		assertTrue(jstBlock.getStmts().get(1) instanceof BreakStmt);

	}
}
