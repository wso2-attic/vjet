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

import java.util.Collection;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class ProtosTranslatorTest extends BaseTest {

	//@Test
	public void testConstructor() {
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test the different assignment expressions and methods in protos section")
	public void testProcessProtos() throws Exception {
		// prepare data
		CompilationUnitDeclaration ast = prepareAst(
				"protosTranslatorTestFile.txt", null);

		// process function
		TranslateConfig cfg = new TranslateConfig();
		cfg.setAllowPartialJST(true);
		TranslateCtx ctx = new TranslateCtx(cfg);
		ctx.setAST(ast);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);

		JstMethod constructor = (JstMethod)jstType.getConstructor();
		assertNotNull(constructor);
		assertEquals("constructs", constructor.getName().getName());

		JstBlock cBlock = constructor.getBlock();
		assertNotNull(cBlock);
		List<IStmt> stmts = cBlock.getStmts();
		assertNotNull(stmts);
		assertEquals(1, stmts.size());

		assertTrue(stmts.get(0) instanceof MtdInvocationExpr);
		MtdInvocationExpr mtd = (MtdInvocationExpr) stmts.get(0);
		assertEquals("alert", mtd.getMethodIdentifier().toExprText());
		List<IExpr> mtdArgs = mtd.getArgs();
		assertNotNull(mtdArgs);
		assertEquals(1, mtdArgs.size());
		assertTrue(mtdArgs.get(0) instanceof SimpleLiteral);
		SimpleLiteral literal = (SimpleLiteral) mtdArgs.get(0);
		assertTrue(literal.toSimpleTermText().equals("\"contructing\""));

		assertNotNull(constructor.getName().getSource());
		assertEquals("", constructor.getAccessScope());

		Collection<IJstProperty> protos = JstTypeHelper.getDeclaredProperties(jstType.getInstanceProperties());
		assertNotNull(protos);
		assertEquals(2, protos.size());

		IJstProperty prop1 = jstType.getProperty("propOne");
		assertNotNull(prop1);
		assertEquals("propOne", prop1.getName().getName());
		assertEquals("\"Default\"", prop1.getValue().toSimpleTermText());
		assertNotNull(prop1.getSource());
		assertNotNull(prop1.getType());
		assertEquals("", prop1.getModifiers().getAccessScope());

		IJstProperty prop2 = jstType.getProperty("propTwo");
		assertNotNull(prop2);
		assertEquals("propTwo", prop2.getName().getName());
		assertEquals("23", prop2.getValue().toSimpleTermText());
		assertNotNull(prop2.getSource());
		assertNotNull(prop2.getType());
		assertEquals("", prop2.getModifiers().getAccessScope());
	}
	
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test the different arithmatic and assignment expressions in protos section")
	public void testProtos() throws Exception {
		// prepare data
		CompilationUnitDeclaration ast = prepareAst(
				"protosWithExpTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		List <IJstProperty> instanceProps = JstTypeHelper.getDeclaredProperties(jstType.getInstanceProperties());
		assertEquals(17, instanceProps.size());		
		
		IJstProperty prop1 = getPropertyByName(instanceProps, "a");
		
		prop1 = getPropertyByName(instanceProps, "b");
		assertEquals("a+1", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "c");
		assertEquals("a+b+i", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "d");
		assertEquals("new Date()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "e");
		assertEquals("d.getDay()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "f");
		assertEquals("new Integer(10)+Float(12.2)", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "i");
		//assertEquals("a+1", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "j");
		assertEquals("this.i+1", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "k");
		assertEquals("this.j++", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "l");
		assertEquals("(this.i||1)", prop1.getInitializer().toExprText());		
		
		prop1 = getPropertyByName(instanceProps, "m");
		assertEquals("(1>2)", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "n");
		assertEquals("new Date()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "o");
		assertEquals("new Array()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "p");
		assertEquals("new Object()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "q");
		assertEquals("new Boolean(0)", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "r");
		assertEquals("[]", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(instanceProps, "s");
		//assertEquals("[]", prop1.getInitializer().toExprText());
		
	}

}
