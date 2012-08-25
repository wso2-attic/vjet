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
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class TypeTranslatorTest extends BaseTest {

	//@Test
	public void testConstructor() {
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test incomplete VJET itype should parse non-null jst. " +
//			"Also it should have bare minimum information baout vjet type")
	public void testProcessIType() {
		CompilationUnitDeclaration ast = prepareAst(
				"iTypeTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		assertTrue(jstType.isInterface());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test incomplete VJET etype should parse non-null jst. " +
//			"Also it should have bare minimum information baout vjet type")
	public void testProcessEType() {
		CompilationUnitDeclaration ast = prepareAst(
				"eTypeTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		assertNotNull(jstType);
		assertTrue(jstType.isEnum());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test VJET mtype should parse non-null jst. " +
//			"Also it should have required statements and expressions in jst type")
	public void testProcessMType() {
		CompilationUnitDeclaration ast = prepareAst(
				"mTypeTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		assertNotNull(jstType);
		assertTrue(jstType.isMixin());
	}
	
	@Test //@Category({P5, FAST, UNIT})
	//@Description("Test incomplete VJET atype should parse non-null jst. " +
//			"Also it should have bare minimum information baout vjet type")
	public void testProcessAType() {
		CompilationUnitDeclaration ast = prepareAst(
				"aTypeTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		assertNotNull(jstType);
		assertTrue(jstType.getModifiers().isAbstract());
	}
	
	@Test //@Category({P5, FAST, UNIT})
	//@Description("Test incomplete VJET atype should parse non-null jst. " +
//			"Also it should have bare minimum information baout vjet type")
	public void testProcessFType() {
		CompilationUnitDeclaration ast = prepareAst(
				"fTypeTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		assertNotNull(jstType);
		assertTrue("ftype yet parsed correctly", jstType.isFType());
		assertNotNull(jstType.getSimpleName());
	}

	@Test //@Category({P5, FAST, UNIT})
	//@Description("Test incomplete VJET atype should parse non-null jst. " +
//			"Also it should have bare minimum information baout vjet type")
	public void testProcessType1() {
		CompilationUnitDeclaration ast = prepareAst(
				"typeTranslatorTestFile.js.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		List<? extends IJstMethod> staticMethods = jstType.getStaticMethods();
		assertNotNull(staticMethods);
		assertEquals(2, JstTypeHelper.getDeclaredMethods(staticMethods).size());
		IJstMethod jstMethod = getMethodByName(staticMethods, "getRate");
		assertNotNull(jstMethod);
		IJstMethod jstMethod2 = getMethodByName(staticMethods, "getDiscount");
		assertNotNull(jstMethod2);
		assertNotNull(jstType.getMethod("chase"));
	}

}
