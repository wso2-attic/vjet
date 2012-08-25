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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.RegexpLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class ExpressionTranslatorTest extends BaseTest {

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test Object Creation Expression in vjet js file." +
//			" Js name, package and expressions should be proper in jst type.")
	public void testObjectCreationExpression() {
		CompilationUnitDeclaration ast = prepareAst("objectCreationExprTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		List stmts = jstType.getMethod("doIt").getBlock().getStmts();
		
		// expressions
		assertTrue(stmts.get(0) instanceof JstVars);
		JstVars var = (JstVars) stmts.get(0);
		assertNotNull(var);
		assertNotNull(var.getInitializer());
		assertEquals(1, var.getAssignments().size());
		AssignExpr varInitializer =  var.getAssignments().get(0);
		assertNotNull(varInitializer);
		assertEquals("re=new RegExp(\"w+\")", varInitializer.toExprText());
		assertEquals("re", varInitializer.getLHS().toLHSText());
		assertEquals("new RegExp(\"w+\")", varInitializer.getExpr().toExprText());
		assertTrue(varInitializer.getExpr() instanceof ObjCreationExpr);
		
		// ObjectCreationExpression
		ObjCreationExpr objCrtnExpr = (ObjCreationExpr) varInitializer.getExpr();
		assertEquals("RegExp", objCrtnExpr.getInvocationExpr().getMethodIdentifier().toExprText());
		assertNotNull(objCrtnExpr.getInvocationExpr().getArgs());
		assertEquals(1, objCrtnExpr.getInvocationExpr().getArgs().size());
		assertEquals("\"w+\"", objCrtnExpr.getInvocationExpr().getArgs().get(0).toExprText());	
	}
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test Simple Literal Expression in vjet js file." +
//			" Js name, package and expressions should be proper in jst type.")
	public void testSimpleLiteralExpression(){
		CompilationUnitDeclaration ast = prepareAst("simpleLiteralTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		List stmts = jstType.getMethod("doIt").getBlock().getStmts();
		
		assertTrue(stmts.get(0) instanceof JstVars);
		JstVars var = (JstVars)stmts.get(0);
		assertNotNull(var);
		assertNotNull(var.getInitializer());
		assertEquals(1, var.getAssignments().size());
		AssignExpr varInitializer =  var.getAssignments().get(0);
		assertNotNull(varInitializer);
		assertEquals("re=/\\w+/", varInitializer.toExprText());
		assertEquals("re", varInitializer.getLHS().toLHSText());
		assertEquals("/\\w+/", varInitializer.getExpr().toExprText());
		assertTrue(varInitializer.getExpr() instanceof RegexpLiteral);
		
		// SimpleLiteral
		RegexpLiteral smplLitrlExpr = (RegexpLiteral) varInitializer.getExpr();
		assertEquals("/\\w+/", smplLitrlExpr.toValueText());
		
	}	
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test Postfix Expression in vjet js file." +
//			" Js name, package and expressions should be proper in jst type.")
	public void testPostFixExpression(){
		
		CompilationUnitDeclaration ast = prepareAst("postFixExprTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		List stmts = jstType.getMethod("doIt").getBlock().getStmts();
		
		// PostFixExpression
		assertTrue(stmts.get(0) instanceof PostfixExpr);
		PostfixExpr postFixExpr = (PostfixExpr) stmts.get(0);
		assertEquals("i--", postFixExpr.toString());
		assertEquals("i", postFixExpr.getIdentifier().toExprText());		
	
	}
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test chaining Expression in vjet js file. LHS and RHS expressions should be proper." +
//			" Js name, package and all parts of chaing expressions should be proper in jst type.")
	public void testProcessExpression2() {
		
		CompilationUnitDeclaration ast = prepareAst("expressionTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		List<IStmt> stmts = jstType.getMethod("doIt").getBlock().getStmts();
		
		// other expressions
		IStmt expr = stmts.get(0);
		System.out.println(expr);
		
		assertNotNull(expr);
		
		assertTrue(expr instanceof JstVars);
		List<AssignExpr> inits = ((JstVars)expr).getAssignments();
		assertEquals(1, inits.size());
		AssignExpr init = inits.get(0);
		
		assertEquals("expr", init.getLHS().toString());
		
		IExpr rhs = init.getExpr();
		assertTrue(rhs instanceof InfixExpr);
		InfixExpr iExpr = (InfixExpr) rhs;
		// assertEquals(input, iExpr.toExprText());

		assertNotNull(iExpr.getLeft());
		assertEquals("a+b/c+get().myName.name.method(a,b+7)", iExpr.getLeft().toExprText());
		assertTrue(iExpr.getLeft() instanceof InfixExpr);
		InfixExpr l = (InfixExpr) iExpr.getLeft();
		assertNotNull(l.getLeft());
		assertEquals("a+b/c", l.getLeft().toExprText());
		assertTrue(l.getLeft() instanceof InfixExpr);
		InfixExpr ll = (InfixExpr) l.getLeft();

		assertNotNull(ll.getLeft());
		assertTrue(ll.getLeft() instanceof JstIdentifier);
		JstIdentifier idenA = (JstIdentifier) ll.getLeft();
		assertEquals("a", idenA.getName());
		assertNull(idenA.getQualifier());
		assertNotNull(idenA.getSource());

		assertNotNull(ll.getRight());
		assertTrue(ll.getRight() instanceof InfixExpr);
		InfixExpr llr = (InfixExpr) ll.getRight();
		assertEquals("b/c", llr.toExprText());

		assertNotNull(llr.getLeft());
		assertTrue(llr.getLeft() instanceof JstIdentifier);
		JstIdentifier idenB = (JstIdentifier) llr.getLeft();
		assertEquals("b", idenB.getName());
		assertNotNull(idenB.getSource());
		assertNull(idenB.getQualifier());

		assertNotNull(llr.getRight());
		assertTrue(llr.getRight() instanceof JstIdentifier);
		JstIdentifier idenC = (JstIdentifier) llr.getRight();
		assertEquals("c", idenC.getName());
		assertNotNull(idenC.getSource());
		assertNull(idenC.getQualifier());

		assertNotNull(l.getRight());
		assertTrue(l.getRight() instanceof MtdInvocationExpr);
		MtdInvocationExpr mtd = (MtdInvocationExpr) l.getRight();
		assertNotNull(mtd.getArgs());
		List<IExpr> args = mtd.getArgs();
		assertEquals(2, args.size());

		assertTrue(args.get(0) instanceof JstIdentifier);
		JstIdentifier arg1 = (JstIdentifier) args.get(0);
		assertEquals("a", arg1.getName());
		assertNotNull(arg1.getSource());
		assertNull(arg1.getQualifier());

		assertTrue(args.get(1) instanceof InfixExpr);
		InfixExpr arg2 = (InfixExpr) args.get(1);

		assertNotNull(arg2.getLeft());
		assertTrue(arg2.getLeft() instanceof JstIdentifier);
		JstIdentifier argB = (JstIdentifier) arg2.getLeft();
		assertEquals("b", argB.toExprText());
		assertNotNull(argB.getSource());
		assertNull(argB.getQualifier());

		assertNotNull(arg2.getRight());
		assertTrue(arg2.getRight() instanceof SimpleLiteral);
		SimpleLiteral arg7 = (SimpleLiteral) arg2.getRight();
		assertEquals("7", arg7.toExprText());
		assertNotNull(arg7.getSource());

		JstIdentifier mtdName = (JstIdentifier)mtd.getMethodIdentifier();
		assertNotNull(mtdName);
		assertEquals("method", mtdName.getName());
		assertNotNull(mtdName.getSource());
		assertNotNull(mtd.getQualifyExpr());
		assertTrue(mtd.getQualifyExpr() instanceof FieldAccessExpr);
		FieldAccessExpr qualifier = (FieldAccessExpr) mtd.getQualifyExpr();
		assertNotNull(qualifier.getName());
		assertEquals("name", qualifier.getName().getName());
		assertNotNull(qualifier.getExpr());
		assertTrue(qualifier.getExpr() instanceof FieldAccessExpr);
		FieldAccessExpr qualifier1 = (FieldAccessExpr) qualifier.getExpr();
		assertNotNull(qualifier1.getSource());
		assertNotNull(qualifier1.getName());
		assertEquals("myName", qualifier1.getName().getName());
		assertNotNull(qualifier1.getExpr());
		assertTrue(qualifier1.getExpr() instanceof MtdInvocationExpr);
		MtdInvocationExpr qualifier2 = (MtdInvocationExpr) qualifier1.getExpr();
		assertNotNull(qualifier2.getMethodIdentifier().toExprText());
		assertEquals("get", qualifier2.getMethodIdentifier().toExprText());
		assertNotNull(qualifier2.getArgs());
		assertTrue(qualifier2.getArgs().isEmpty());

		assertNotNull(iExpr.getRight());
		assertTrue(iExpr.getRight() instanceof ParenthesizedExpr);
		InfixExpr r = (InfixExpr) ((ParenthesizedExpr)iExpr.getRight())
			.getExpression();
		assertEquals("s/f", r.toExprText());

		assertNotNull(r.getLeft());
		assertTrue(r.getLeft() instanceof JstIdentifier);
		JstIdentifier rl = (JstIdentifier) r.getLeft();
		assertNotNull(rl.getSource());
		assertEquals("s", rl.getName());
		assertNull(rl.getQualifier());

		assertNotNull(r.getRight());
		assertTrue(r.getRight() instanceof JstIdentifier);
		JstIdentifier rr = (JstIdentifier) r.getRight();
		assertNotNull(rr.getSource());
		assertEquals("f", rr.getName());
		assertNull(rr.getQualifier());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test Postfix Expression in vjet js file." +
//			" Js name, package and all parts of postfix expressions should be proper in jst type.")
	public void testPostFixExpression2() {
		CompilationUnitDeclaration ast = prepareAst("postFixExprTranslatorTestFile2.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		List stmts = jstType.getMethod("doIt").getBlock().getStmts();
		assertNotNull(stmts.get(0));
		assertTrue(stmts.get(0) instanceof PostfixExpr);
		
		PostfixExpr pExpr = (PostfixExpr) stmts.get(0);
		
		assertNotNull(pExpr.getIdentifier());
		assertTrue(pExpr.getIdentifier() instanceof FieldAccessExpr);
		FieldAccessExpr ident = (FieldAccessExpr) pExpr.getIdentifier();
		assertNotNull(ident.getName());
		assertEquals("d", ident.getName().getName());
		assertNotNull(ident.getSource());
		assertNotNull(ident.getExpr());
		assertTrue(ident.getExpr() instanceof FieldAccessExpr);

		FieldAccessExpr ident2 = (FieldAccessExpr) ident.getExpr();
		assertNotNull(ident2.getName());
		assertEquals("g", ident2.getName().getName());
		assertNotNull(ident2.getSource());
		assertNotNull(ident2.getExpr());
		assertTrue(ident2.getExpr() instanceof JstIdentifier);

		JstIdentifier ident3 = (JstIdentifier) ident2.getExpr();
		assertEquals("i", ident3.getName());
		assertNotNull(ident3.getSource());
		assertNull(ident3.getQualifier());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test Postfix Expression in vjet js file." +
//			" Js name, package and all parts of postfix expressions should be proper in jst type.")
	public void testPrefixExpression() {
		CompilationUnitDeclaration ast = prepareAst("prefixExprTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		List stmts = jstType.getMethod("doIt").getBlock().getStmts();
		assertNotNull(stmts.get(0));
		assertTrue(stmts.get(0) instanceof PrefixExpr);
		
		PrefixExpr pExpr = (PrefixExpr) stmts.get(0);
		assertNotNull(pExpr);
		
		assertNotNull(pExpr.getIdentifier());
		assertTrue(pExpr.getIdentifier() instanceof JstIdentifier);
		JstIdentifier ident = (JstIdentifier) pExpr.getIdentifier();
		assertEquals("i", ident.getName());
		assertNotNull(ident.getSource());
		assertNull(ident.getQualifier());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test Method Invocation Expression in vjet js file. LHS and RHS expressions should be proper." +
//			" Js name, package and all parts of chaing expressions should be proper in jst type.")
	public void testMtdInvocationExpr() {
		CompilationUnitDeclaration ast = prepareAst("MtdInvocationExprTranslatorTest.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		List stmts = jstType.getMethod("doIt").getBlock().getStmts();
		assertNotNull(stmts.get(0));
		assertTrue(stmts.get(0) instanceof MtdInvocationExpr);

		MtdInvocationExpr mtd = (MtdInvocationExpr) stmts.get(0);

		assertNotNull(mtd.getArgs());
		assertEquals(1, mtd.getArgs().size());
		assertTrue(mtd.getArgs().get(0) instanceof SimpleLiteral);
		SimpleLiteral lit = (SimpleLiteral) mtd.getArgs().get(0);
		assertEquals("\"s.j.gg\"", lit.toString());

		assertNotNull(mtd.getMethodIdentifier().toExprText());
		assertEquals("getName", mtd.getMethodIdentifier().toExprText());

		assertNotNull(mtd.getQualifyExpr());
		assertTrue(mtd.getQualifyExpr() instanceof ObjCreationExpr);
		ObjCreationExpr obj = (ObjCreationExpr) mtd.getQualifyExpr();
		MtdInvocationExpr invocationExpr = obj.getInvocationExpr();
		assertNotNull(invocationExpr);
		assertNotNull(invocationExpr.getMethodIdentifier());
		assertEquals("Test", invocationExpr.getMethodIdentifier().toExprText());
	}
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test chaining Expression in vjet js file." +
//			" Js name, package and Array declaration JstIdentifier should be proper.")
	public void testArrayAccessExpression() {
		CompilationUnitDeclaration ast = prepareAst("arrayReferenceTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		List stmts = jstType.getMethod("doIt").getBlock().getStmts();
		assertNotNull(stmts.get(0));
		assertTrue(stmts.get(0) instanceof JstVars);
		JstVars jstVars = (JstVars) stmts.get(0);
		AssignExpr jstInitializer = jstVars.getAssignments().get(0);
		assertNotNull(jstInitializer);
		assertNotNull(jstInitializer.getExpr());
		assertTrue(jstInitializer.getExpr() instanceof ArrayAccessExpr);
				

		ArrayAccessExpr aae = (ArrayAccessExpr)jstInitializer.getExpr();
		assertNotNull(aae.getSource());
		
		
		assertNotNull(aae.getExpr());		
		assertTrue(aae.getExpr() instanceof JstIdentifier);
		JstIdentifier ident = (JstIdentifier) aae.getExpr();
		assertEquals("array", ident.getName());
		assertNull(ident.getQualifier());
		assertNotNull(ident.getSource());		
		
		assertNotNull(aae.getIndex());
		assertTrue(aae.getIndex() instanceof SimpleLiteral);
		SimpleLiteral sl = (SimpleLiteral) aae.getIndex();
		assertEquals("0", sl.toExprText());
	}
}
