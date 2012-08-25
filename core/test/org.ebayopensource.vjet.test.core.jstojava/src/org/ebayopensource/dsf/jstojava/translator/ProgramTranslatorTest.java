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

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class ProgramTranslatorTest extends BaseTest {

	//@Test
	public void testConstructor() {
	}

	/**
	 * Generic test. Asserting that jstType is creating correctly
	 */
	@Test //@Category({P5, FAST, UNIT})
	//@Description("Generic test. Asserting that jstType is creating correctly")
	public void testProcessUnit1() {
		CompilationUnitDeclaration ast = prepareAst(
				"programTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);

		List<? extends IJstType> imports = jstType.getImports();
		assertNotNull(imports);
		assertEquals(2, imports.size());
		assertNotNull(jstType.getImport("U"));
		assertNotNull(jstType.getImport("Z"));

		assertEquals("org.ebayopensource.dsf.tests.jsast.parser.simplevjo", jstType
				.getName());
		assertEquals("simplevjo", jstType.getSimpleName());

	}

	@Test //@Category({P1, FAST, UNIT})
	public void testProcessUnit2() {
		IJstType jstType = SyntaxTreeFactory2.createJST(null, null);
		assertNull(jstType);
	}
}
