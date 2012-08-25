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

import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value = "DsfPrebuild", subModuleId = "JsToJava")
public class GlobalsTranslatorTest extends BaseTest {

	// @Test
	public void testConstructor() {
	}


	@Test
	//@Category( { P1, FAST, UNIT })
	//@Description("Test global vars declaration and its value and type."
//			+ "Also check the method arguments in JstType")
	public void testGlobalVars() throws Exception {

		CompilationUnitDeclaration ast = prepareAst(
				"globalsTest1.txt", null);
		assertNotNull(ast);

		TranslateConfig cfg = new TranslateConfig();
		cfg.setAllowPartialJST(true);
		TranslateCtx translateCtx = new TranslateCtx(cfg);

		translateCtx.setAST(ast);

		IJstType jstType = SyntaxTreeFactory2.createJST(ast, translateCtx);
		assertNotNull(jstType);
		// global props
		validateGlobal(jstType, "foo", "String");
		validateGlobal(jstType, "bar", "Number");
		
		// global functions
		validateGlobal(jstType, "func", "void");
		
//		JstArg arg1 = args.get(0);
//		assertEquals("arg1", arg1.getName());
//		assertNotNull(arg1.getSource());
//
//		JstArg arg2 = args.get(1);
//		assertEquals("arg2", arg2.getName());
//		assertNotNull(arg2.getSource());
//
//		IJstMethod method = jstType.getMethod("doIt");
//		assertNotNull(method);
//
//		assertEquals("doIt", method.getName().getName());
//
//		JstBlock block = method.getBlock();
//		assertNotNull(block);
//
//		List<IStmt> stmts = block.getStmts();
//		assertEquals(2, stmts.size());
	}

	@Test
	//@Category( { P1, FAST, UNIT })
	//@Description("Test global vars declaration and its value and type."
//			+ "Also check the method arguments in JstType")
	public void testGlobalVarNoDefAndPromote() throws Exception {
		
		/**
		 * vjo.ctype('a.b.c.other.GlobalPromote') //< public
		.globals({
			$ : vjo.DEFONLY, //<< type::Type::prop
			Jq : vjo.DEFONLY //<< type::Type
		})
		.endType();
		
		**/
		
		CompilationUnitDeclaration ast = prepareAst(
				"globalsTest2.txt", null);
		assertNotNull(ast);

		TranslateConfig cfg = new TranslateConfig();
		cfg.setAllowPartialJST(true);
		TranslateCtx translateCtx = new TranslateCtx(cfg);

		translateCtx.setAST(ast);

		IJstType jstType = SyntaxTreeFactory2.createJST(ast, translateCtx);
		assertNotNull(jstType);
		
		
		validateGlobal(jstType, "Foo1", "Object");
		validateGlobalMapping(jstType, "Foo2", "Number::valueOf", true);
//		validateGlobalMapping(jstType, "Foo3", "type::Number");
		validateGlobalMapping(jstType, "Foo4", "Number::NaN", false);
//		validateGlobalMapping(jstType, "Foo5", "type::Number");
		
		
		
	}
	
	private IJstGlobalVar validateGlobalMapping(IJstType jstType, String name,
			String typeMapping, boolean isMethod) {
		IJstGlobalVar gvar = jstType.getGlobalVar(name);
				
		assertNotNull("missing global var " + name, gvar);
		
		IJstType type = gvar.getType();
		assertNotNull(type);
		
		return gvar;		
	}


	private void validateGlobal(IJstType jstType, String globalName, String globalType) {
		IJstGlobalVar globalVar = jstType.getGlobalVar(globalName);
		assertNotNull("missing global var " + globalName, globalVar);

		IJstType type = globalVar.getType();
		assertNotNull(type);
		assertEquals(globalType, type.getSimpleName());
		
		if(globalVar.isFunc()){
			globalVar.getFunction().getRtnType().getSimpleName().equals(globalType);
		}		
	}
}
