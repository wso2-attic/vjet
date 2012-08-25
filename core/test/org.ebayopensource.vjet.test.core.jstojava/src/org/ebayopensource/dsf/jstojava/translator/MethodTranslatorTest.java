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

import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class MethodTranslatorTest extends BaseTest {

	//@Test
	public void testConstructor() {
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test method declaration and its JstBlock." +
//			"Also check the method arguments in JstType")
	public void testProcessProps() throws Exception {

		CompilationUnitDeclaration ast = prepareAst(
				"methodTranslatorTestFile.txt", null);
		assertNotNull(ast);

		TranslateConfig cfg = new TranslateConfig();
		cfg.setAllowPartialJST(true);
		TranslateCtx translateCtx = new TranslateCtx(cfg);
		
		translateCtx.setAST(ast);

		IJstType jstType = SyntaxTreeFactory2.createJST(ast, translateCtx);
		assertNotNull(jstType);

		IJstMethod jstMethod = jstType.getMethod("doIt");
		assertNotNull(jstMethod);

		List<JstArg> args = jstMethod.getArgs();
		assertNotNull(args);
		assertEquals(2, args.size());

		JstArg arg1 = args.get(0);
		assertEquals("arg1", arg1.getName());
		assertNotNull(arg1.getSource());

		JstArg arg2 = args.get(1);
		assertEquals("arg2", arg2.getName());
		assertNotNull(arg2.getSource());

		IJstMethod method = jstType.getMethod("doIt");
		assertNotNull(method);
		
		assertEquals("doIt", method.getName().getName());

		JstBlock block = method.getBlock();
		assertNotNull(block);

		List<IStmt> stmts = block.getStmts();
		assertEquals(2, stmts.size());
	}
}
