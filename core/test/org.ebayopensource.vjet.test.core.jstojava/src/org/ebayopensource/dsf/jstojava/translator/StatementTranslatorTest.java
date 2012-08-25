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

import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.ContinueStmt;
import org.ebayopensource.dsf.jst.stmt.DoStmt;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class StatementTranslatorTest extends BaseTest {

	private static final String NULL_STATEMENT = "The statement is null";

	private static final String IS_NOT_INSTANCE_OF = "Returned statement is not instance of ";

	private static final String WRONG_CONDITION = "Conditions in ast and jst statements are different";

	private static final String WRONG_INITIALIZER_NAME = "Initializer name in ast and jst statements are different";

	private static final String WRONG_INITIALIZER_VALUE = "Initializer value in ast and jst statements are different";

	private static final String WRONG_UPDATER = "Updater in ast and jst statements are different";

	private static final String WRONG_SOURCE_START = "Source start in ast and jst statements are different";

	private static final String WRONG_SOURCE_END = "Source end in ast and jst statements are different";

	private static final String WRONG_BODY = "The body of statements in ast and jst are different";

	private static final String NULL_THEN_BODY = "The body of then statement is null";

	private static final String EMPTY_THEN_BODY = "The body of then statement is empty";

	private static final String EMPTY_ELSE_BLOCK_BODY = "The body of else block is empty";

	private static final String WRONG_SELECTOR = "Selector in ast and jst statements are different";

	private static final String WRONG_VAR_NAME = "Var names are different in ast and jst";

	private static final String WRONG_RETURN_EXPRESSION = "return expression value in ast and jst statements are different";

	private static final String WRONG_EXCEPTION_NAME = "Exception name in ast and jst statements are different";

	private static final String WRONG_EXPRESSION = "Iterated objects in ast and jst statements are different";

	private static final String NO_SOURCE = "Jst statement has no source";

	private static final String FILE_STATEMENT_TRANSLATOR_TEST = "statementTranslatorTestFile.txt";

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test the for loop statement including boolean condition and the loop block statement")
	public void testForStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("forStatement.js.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());
		assertTrue(IS_NOT_INSTANCE_OF + "ForStmt",
				statements.get(0) instanceof ForStmt);

		ForStmt forStmt = (ForStmt) statements.get(0);

		// condition
		assertNotNull(forStmt.getCondition());
		assertEquals("i<9", forStmt.getCondition().toString());

		// updaters
		List<IExpr> forStmtUpdaters = forStmt.getUpdaters();
		assertNotNull(forStmtUpdaters);
		assertEquals(1, forStmtUpdaters.size());
		assertEquals("i--", forStmtUpdaters.get(0).toExprText());

		// initializers
		List<AssignExpr> initializers = forStmt.getInitializers()
				.getAssignments();
		assertNotNull(initializers);
		assertEquals(1, initializers.size());
		assertEquals("i=0", initializers.get(0).toExprText());

		// body
		JstBlock forStmtBody = forStmt.getBody();
		assertNotNull(forStmtBody);
		assertEquals("alert(param1,param2);", forStmtBody.getStmts().get(0)
				.toStmtText());

		// source
		assertNotNull(NO_SOURCE, forStmt.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test the for-in loop statement including boolean condition and the loop block statement")
	public void testForInStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("forInStatement.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertTrue(IS_NOT_INSTANCE_OF + "ForInStmt",
				statements.get(0) instanceof ForInStmt);

		ForInStmt forInStmt = (ForInStmt) statements.get(0);

		// expressions
		IExpr forInExpr = forInStmt.getExpr();
		assertNotNull(forInExpr);

		// body
		JstBlock forInStmtBody = forInStmt.getBody();
		assertNotNull(forInStmtBody);
		assertEquals("alert();", forInStmtBody.getStmts().get(0)
				.toStmtText());

		// source
		assertNotNull(NO_SOURCE, forInStmt.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test the if conditional statement including boolean condition and the conditional block")
	public void testIfStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("ifStatement.js.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertTrue(IS_NOT_INSTANCE_OF + "IfStmt",
				statements.get(0) instanceof IfStmt);

		IfStmt ifStmt = (IfStmt) statements.get(0);
		assertNotNull(ifStmt);

		// if condition
		BoolExpr ifCondition = (BoolExpr)ifStmt.getCondition();
		assertNotNull(ifCondition);
		assertEquals("number>0", ifCondition.toBoolExprText());
		assertEquals("number", ifCondition.getLeft().toExprText());
		assertEquals("0", ifCondition.getRight().toExprText());

		// if body
		JstBlock ifBlock = ifStmt.getBody();
		assertNotNull(ifBlock);
		assertNotNull(ifBlock.getStmts());
		assertEquals(1, ifBlock.getStmts().size());

		assertTrue(IS_NOT_INSTANCE_OF + "MtdInvocationExpr", ifBlock.getStmts().get(0) instanceof MtdInvocationExpr);
		MtdInvocationExpr ifBlockStmt = (MtdInvocationExpr) ifBlock.getStmts().get(0);
		assertNotNull(ifBlockStmt);
		assertEquals("alert", ifBlockStmt.getMethodIdentifier().toExprText());
		assertNotNull(ifBlockStmt.getArgs());
		assertEquals(1, ifBlockStmt.getArgs().size());
		assertEquals("\"Number is a positive integer\"", ifBlockStmt.getArgs()
				.get(0).toExprText());

		// else statements
		assertEquals(null, ifStmt.getElseBlock(false));
		assertNotNull(ifStmt.getElseIfBlock(false));
		assertEquals(1, ifStmt.getElseIfBlock(false).getStmts().size());

		// else if statement
		IfStmt ifElseStmt = (IfStmt)ifStmt.getElseIfBlock(false).getStmts().get(0);
		assertNotNull(ifElseStmt);

		// else if condition
		BoolExpr ifElseCondition = (BoolExpr)ifElseStmt.getCondition();
		assertNotNull(ifElseCondition);
		assertEquals("number<0", ifElseCondition.toBoolExprText());
		assertEquals("number", ifElseCondition.getLeft().toExprText());
		assertEquals("0", ifElseCondition.getRight().toExprText());

		// else if body
		JstBlock ifElseBlock = ifElseStmt.getBody();
		assertNotNull(ifElseBlock);
		assertNotNull(ifElseBlock.getStmts());
		assertEquals(1, ifElseBlock.getStmts().size());

		assertTrue(IS_NOT_INSTANCE_OF + "MtdInvocationExpr", ifElseBlock
				.getStmts().get(0) instanceof MtdInvocationExpr);
		MtdInvocationExpr ifElseBlockStmt = (MtdInvocationExpr) ifElseBlock
				.getStmts().get(0);
		assertNotNull(ifElseBlockStmt);
		assertEquals("alert", ifElseBlockStmt.getMethodIdentifier().toExprText());
		assertNotNull(ifElseBlockStmt.getArgs());
		assertEquals(1, ifElseBlockStmt.getArgs().size());
		assertEquals("\"Number is a negative integer\"", ifElseBlockStmt
				.getArgs().get(0).toExprText());

		// else statement
		JstBlock elseStmt = ifElseStmt.getElseBlock(false);
		assertNotNull(elseStmt);
		assertNotNull(elseStmt.getStmts());
		assertEquals(1, elseStmt.getStmts().size());

		assertTrue(IS_NOT_INSTANCE_OF + "MtdInvocationExpr", elseStmt.getStmts().get(0) instanceof MtdInvocationExpr);
		MtdInvocationExpr elseBlockStmt = (MtdInvocationExpr) elseStmt
				.getStmts().get(0);
		assertNotNull(elseBlockStmt);
		assertEquals("alert", elseBlockStmt.getMethodIdentifier().toExprText());
		assertNotNull(elseBlockStmt.getArgs());
		assertEquals(1, elseBlockStmt.getArgs().size());
		assertEquals("\"Number is 0\"", elseBlockStmt.getArgs().get(0)
				.toExprText());

		// source
		assertNotNull(NO_SOURCE, ifStmt.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test the switch conditional statement including boolean condition and the conditional block")
	public void testSwitchStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("switchStatement.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertTrue(IS_NOT_INSTANCE_OF + "SwitchStmt",
				statements.get(0) instanceof SwitchStmt);

		SwitchStmt switchStatement = (SwitchStmt) statements.get(0);
		assertNotNull(switchStatement);

		// expression
		assertTrue(IS_NOT_INSTANCE_OF + "MtdInvocationExpr", switchStatement
				.getExpr() instanceof MtdInvocationExpr);
		MtdInvocationExpr switchExpr = (MtdInvocationExpr) switchStatement
				.getExpr();
		assertNotNull(switchExpr);
		assertEquals("doIt(3)", switchExpr.toExprText());
		assertEquals("doIt", switchExpr.getMethodIdentifier().toExprText());
		assertNotNull(switchExpr.getArgs());
		assertEquals(1, switchExpr.getArgs().size());
		assertEquals("3", switchExpr.getArgs().get(0).toExprText());

		// switch body
		JstBlock switchBody = switchStatement.getBody();
		assertNotNull(switchBody);
		assertNotNull(switchBody.getStmts());
		assertEquals(6, switchBody.getStmts().size());

		// case statements
		List<IStmt> switchStmts = switchBody.getStmts();

		assertTrue(IS_NOT_INSTANCE_OF + "CaseStmt",
				switchStmts.get(0) instanceof CaseStmt);
		assertEquals("case 5:", ((CaseStmt) switchStmts.get(0)).toStmtText());
		assertEquals("5", ((CaseStmt) switchStmts.get(0)).getExpr()
				.toExprText());

		assertTrue(IS_NOT_INSTANCE_OF + "AssignExpr",
				switchStmts.get(1) instanceof AssignExpr);
		assertEquals("x=x+5", ((AssignExpr) switchStmts.get(1)).toExprText());
		assertEquals("x", ((AssignExpr) switchStmts.get(1)).getLHS()
				.toLHSText());
		assertEquals("=", ((AssignExpr) switchStmts.get(1)).getOprator()
				.toString());
		assertEquals("x+5", ((AssignExpr) switchStmts.get(1)).getExpr()
				.toExprText());

		assertTrue(IS_NOT_INSTANCE_OF + "CaseStmt",
				switchStmts.get(2) instanceof CaseStmt);
		assertEquals("case 10:", ((CaseStmt) switchStmts.get(2)).toStmtText());
		assertEquals("10", ((CaseStmt) switchStmts.get(2)).getExpr()
				.toExprText());

		assertTrue(IS_NOT_INSTANCE_OF + "AssignExpr",
				switchStmts.get(3) instanceof AssignExpr);
		assertEquals("x=x+10", ((AssignExpr) switchStmts.get(3)).toExprText());
		assertEquals("x", ((AssignExpr) switchStmts.get(3)).getLHS()
				.toLHSText());
		assertEquals("=", ((AssignExpr) switchStmts.get(3)).getOprator()
				.toString());
		assertEquals("x+10", ((AssignExpr) switchStmts.get(3)).getExpr()
				.toExprText());

		assertTrue(IS_NOT_INSTANCE_OF + "CaseStmt",
				switchStmts.get(4) instanceof CaseStmt);
		assertEquals("case 15:", ((CaseStmt) switchStmts.get(4)).toStmtText());
		assertEquals("15", ((CaseStmt) switchStmts.get(4)).getExpr()
				.toExprText());

		assertTrue(IS_NOT_INSTANCE_OF + "AssignExpr",
				switchStmts.get(5) instanceof AssignExpr);
		assertEquals("x=x+15", ((AssignExpr) switchStmts.get(5)).toExprText());
		assertEquals("x", ((AssignExpr) switchStmts.get(5)).getLHS()
				.toLHSText());
		assertEquals("=", ((AssignExpr) switchStmts.get(5)).getOprator()
				.toString());
		assertEquals("x+15", ((AssignExpr) switchStmts.get(5)).getExpr()
				.toExprText());

		// source
		assertNotNull(NO_SOURCE, switchStatement.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test the while loop statement including boolean condition and the loop block")
	public void testWhileStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("whileStatement.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());
		assertTrue(IS_NOT_INSTANCE_OF + "WhileStmt",
				statements.get(0) instanceof WhileStmt);

		WhileStmt whileStatement = (WhileStmt) statements.get(0);
		assertNotNull(whileStatement);

		BoolExpr whileCondition = (BoolExpr)whileStatement.getCondition();
		assertNotNull(whileCondition);
		assertEquals("k<5", whileCondition.toBoolExprText());
		assertEquals("k", whileCondition.getLeft().toExprText());
		assertEquals("5", whileCondition.getRight().toExprText());

		JstBlock whileBody = whileStatement.getBody();
		assertNotNull(whileBody);

		List<IStmt> whileStmts = whileBody.getStmts();
		assertNotNull(whileStmts);
		assertEquals(1, whileStmts.size());
		assertTrue(IS_NOT_INSTANCE_OF + "MtdInvocationExpr",
				whileStmts.get(0) instanceof MtdInvocationExpr);

		MtdInvocationExpr whileStmt = (MtdInvocationExpr) whileStmts.get(0);
		assertNotNull(whileStmt);
		assertEquals("alert(\"jst\");", whileStmt.toStmtText());
		assertEquals("alert", whileStmt.getMethodIdentifier().toExprText());
		assertNotNull(whileStmt.getArgs());
		assertEquals(1, whileStmt.getArgs().size());
		assertEquals("\"jst\"", whileStmt.getArgs().get(0).toExprText());

		// source
		assertNotNull(NO_SOURCE, whileStatement.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test do-while loop statement including boolean condition and loop block")
	public void testDoWhileStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("doWhileStatement.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());
		assertTrue(IS_NOT_INSTANCE_OF + "DoStmt",
				statements.get(0) instanceof DoStmt);

		DoStmt doWhileStatement = (DoStmt) statements.get(0);
		assertNotNull(doWhileStatement);

		// condition
		BoolExpr doWhileCondition = (BoolExpr)doWhileStatement.getCondition();
		assertNotNull(doWhileCondition);
		assertEquals("x<5", doWhileCondition.toBoolExprText());
		assertEquals("x", doWhileCondition.getLeft().toExprText());
		assertEquals("5", doWhileCondition.getRight().toExprText());

		// do statement
		JstBlock doWhileBlock = doWhileStatement.getBody();
		assertNotNull(doWhileBlock);
		assertNotNull(doWhileBlock.getStmts());
		assertEquals(1, doWhileBlock.getStmts().size());

		assertTrue(IS_NOT_INSTANCE_OF + "MtdInvocationExpr", doWhileBlock
				.getStmts().get(0) instanceof MtdInvocationExpr);
		MtdInvocationExpr doWhileStmt = (MtdInvocationExpr) doWhileBlock
				.getStmts().get(0);
		assertNotNull(doWhileStmt);
		assertEquals("alert();", doWhileStmt.toStmtText());
		assertEquals("alert", doWhileStmt.getMethodIdentifier().toExprText());
		assertNotNull(doWhileStmt.getArgs());
		assertEquals(0, doWhileStmt.getArgs().size());

		// source
		assertNotNull(NO_SOURCE, doWhileStatement.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test assignment expression in construcuts statement")
	public void testAssigment() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("assigment.js.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());
		assertTrue(IS_NOT_INSTANCE_OF + "JstVars",
				statements.get(0) instanceof JstVars);

		JstVars jstStatement = (JstVars) statements.get(0);
		AssignExpr jstInitializer = jstStatement.getAssignments().get(0);
		assertNotNull(jstInitializer);
		assertEquals("a", jstInitializer.getLHS().toLHSText());
		assertEquals("5", jstInitializer.getExpr().toExprText());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test return statement")
	public void testReturnStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("returnStatement.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());
		assertTrue(IS_NOT_INSTANCE_OF + "RtnStmt",
				statements.get(0) instanceof RtnStmt);

		RtnStmt rtnStatement = (RtnStmt) statements.get(0);
		assertNotNull(rtnStatement);
		assertEquals("\"Hello, have a nice day!\"", rtnStatement
				.getExpression().toExprText());

		// source
		assertNotNull(NO_SOURCE, rtnStatement.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test throws statement in js file")
	public void testThrowStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("throwStatement.js.txt",
				null);
		TranslateCtx translateCtx = new TranslateCtx();
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, translateCtx);

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());
		assertTrue(IS_NOT_INSTANCE_OF + "ThrowStmt",
				statements.get(0) instanceof ThrowStmt);

		ThrowStmt throwStatement = (ThrowStmt) statements.get(0);
		assertNotNull(throwStatement);
		assertEquals("\"Error\"", throwStatement.getExpression().toExprText());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test continue statement in loop")
	public void testContinueStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("continueStatement.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());

		IStmt firstStmt = statements.get(0);// do - while
		assertTrue(IS_NOT_INSTANCE_OF + "BlockStmt",
				firstStmt instanceof BlockStmt);

		jstBlock = ((BlockStmt) firstStmt).getBody();// loop body

		statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());

		firstStmt = statements.get(0);
		assertTrue(IS_NOT_INSTANCE_OF + "ContinueStmt",
				firstStmt instanceof ContinueStmt);

		ContinueStmt continueStmt = (ContinueStmt) firstStmt;
		assertNotNull(continueStmt);

		// source
		assertNotNull(NO_SOURCE, continueStmt.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test break statement in loop")
	public void testBreakStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("breakStatement.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());

		IStmt firstStmt = statements.get(0);// do - while
		assertTrue(IS_NOT_INSTANCE_OF + "BlockStmt",
				firstStmt instanceof BlockStmt);

		jstBlock = ((BlockStmt) firstStmt).getBody();// loop body

		statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());

		firstStmt = statements.get(0);
		assertTrue(IS_NOT_INSTANCE_OF + "ContinueStmt",
				firstStmt instanceof BreakStmt);

		BreakStmt breakStmt = (BreakStmt) firstStmt;
		assertNotNull(breakStmt);

		// source
		assertNotNull(NO_SOURCE, breakStmt.getSource());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test try statement in js file")
	public void testTryStatement() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("tryStatement.js.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());
		assertTrue(IS_NOT_INSTANCE_OF + "TryStmt",
				statements.get(0) instanceof TryStmt);

		// try statement
		TryStmt tryStatement = (TryStmt) statements.get(0);
		assertNotNull(tryStatement);

		// try block
		JstBlock tryBlock = tryStatement.getBody();
		assertNotNull(tryBlock);
		assertNotNull(tryBlock.getStmts());
		assertEquals(1, tryBlock.getStmts().size());
		assertTrue(IS_NOT_INSTANCE_OF + "MtdInvocationExpr", tryBlock.getStmts().get(0) instanceof MtdInvocationExpr);

		MtdInvocationExpr tryBlockStmt = (MtdInvocationExpr) tryBlock.getStmts().get(0);
		assertEquals("alert", tryBlockStmt.getMethodIdentifier().toExprText());
		assertNotNull(tryBlockStmt.getArgs());
		assertEquals(1, tryBlockStmt.getArgs().size());
		assertTrue(IS_NOT_INSTANCE_OF + "SimpleLiteral", tryBlockStmt.getArgs()
				.get(0) instanceof SimpleLiteral);
		assertEquals("\"OK\"", ((SimpleLiteral) tryBlockStmt.getArgs().get(0))
				.toSimpleTermText());

		// catch block
		assertNotNull(tryStatement.getCatchBlock(false));
		assertEquals(1, tryStatement.getCatchBlock(false).getStmts().size());

		IStmt catchStmt = tryStatement.getCatchBlock(false).getStmts().get(0);
		assertNotNull(catchStmt);
		assertEquals("err", ((CatchStmt)catchStmt).getException().getName());

		JstBlock catchBlock = ((CatchStmt)catchStmt).getBody();
		assertNotNull(catchBlock);
		assertNotNull(catchBlock.getStmts());
		assertEquals(1, catchBlock.getStmts().size());
		assertTrue(IS_NOT_INSTANCE_OF + "MtdInvocationExpr", catchBlock
				.getStmts().get(0) instanceof MtdInvocationExpr);

		MtdInvocationExpr catchBlockStmt = (MtdInvocationExpr) catchBlock
				.getStmts().get(0);
		assertEquals("alert", catchBlockStmt.getMethodIdentifier().toExprText());
		assertNotNull(catchBlockStmt.getArgs());
		assertEquals(1, catchBlockStmt.getArgs().size());
		assertTrue(IS_NOT_INSTANCE_OF + "SimpleLiteral", catchBlockStmt
				.getArgs().get(0) instanceof SimpleLiteral);
		assertEquals("\"FAIL\"", ((SimpleLiteral) catchBlockStmt.getArgs().get(
				0)).toSimpleTermText());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test local declaration in constructs in vjet js file")
	public void testLocalDeclaration() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("localDeclaration.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		IJstMethod jstMethod = jstType.getConstructor();
		assertNotNull(jstMethod);

		JstBlock jstBlock = jstMethod.getBlock();
		assertNotNull(jstBlock);

		List<IStmt> statements = jstBlock.getStmts();
		assertNotNull(NULL_STATEMENT, statements);
		assertEquals(1, statements.size());
		assertTrue(IS_NOT_INSTANCE_OF + "JstVars",
				statements.get(0) instanceof JstVars);

		JstVars jstStatement = (JstVars) statements.get(0);
		assertNotNull(jstStatement);
		assertNotNull(jstStatement.getInitializer());
		assertEquals(1, jstStatement.getAssignments().size());
		assertTrue(jstStatement.getAssignments().get(0) instanceof AssignExpr);
		assertEquals("a", jstStatement.getAssignments().get(0).getLHS()
				.toLHSText());
		assertEquals(null, jstStatement.getAssignments().get(0)
				.getExpr());

	}

	// @Test
	// public void testProccesExpression() throws Exception {
	// String input ="var a,p = (this.prototype = new parent())";
	// CompilationUnitDeclaration ast = prepareAst("mockFile",input);
	// JstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
	//		
	// JstMethod jstMethod = jstType.getConstructor();
	// assertNotNull(jstMethod);
	//		
	// JstBlock jstBlock = jstMethod.getBlock(false);
	// assertNotNull(jstBlock);
	//		
	// List<IStmt> statements = jstBlock.getStmts(false);
	// assertNotNull(NULL_STATEMENT, statements);
	// assertEquals(1, statements.size());
	// assertTrue(statements.get(0) instanceof JstVars);
	//		
	// JstVars vars = (JstVars)statements.get(0);
	// List<JstInitializer> initsList = vars.getInitializers();
	// JstInitializer init1 = initsList.get(0);
	// assertNull(init1.getExpression());
	// assertEquals("a", init1.getLHS().toString());
	//		
	// JstInitializer init2 = initsList.get(1);
	// assertEquals("this.prototype=new
	// parent()",init2.getExpression().toString());
	// assertEquals("p", init2.getLHS().toString());
	// }
}
