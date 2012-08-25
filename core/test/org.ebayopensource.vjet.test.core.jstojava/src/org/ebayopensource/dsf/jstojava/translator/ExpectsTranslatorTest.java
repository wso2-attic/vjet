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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class ExpectsTranslatorTest extends BaseTest{

	@Test //@Description("Partial Mtype JS file should produce meaningful JSTType. " +
//	"Type name and package should be proper along with expects for itype")
	public void testExpects() {
		CompilationUnitDeclaration ast = prepareAst("expectsTranslatorTestFile.js.txt",
				null);

		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		assertTrue(jstType.isMixin());
		
		assertEquals("com.ebay.vjet", jstType.getPackage().getName());
		assertEquals("M", jstType.getSimpleName());
		
		List<? extends IJstType> expects = jstType.getExpects();
		assertNotNull(expects);
		assertEquals(1, expects.size());
		
		assertEquals("com.ebay.vjet", expects.get(0).getPackage().getName());
		assertEquals("I", expects.get(0).getSimpleName());
	}
}
