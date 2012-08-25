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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P5, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class NeedsTranslatorTest extends BaseTest {

	//@Test
	public void testConstructor() {
	}

	@Test //@Category({P5, FAST, UNIT})
	//@Description("Test Ctype needs section. check the package and type information."
//			+ " This test is old test and tests older style which is not supported anymore")
	public void testProcessNeeds() throws Exception {
		CompilationUnitDeclaration ast = prepareAst("needsTransTestFile.txt",
				null);

		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);

		List<? extends IJstType> imports = jstType.getImports();
		assertNotNull(imports);
		assertEquals(1, imports.size());

		assertTrue(jstType.getImport("Z") instanceof JstType);
		IJstTypeReference importedTypeRef = jstType.getImportRef("Z");
		assertNotNull(importedTypeRef);
		IJstType importType = importedTypeRef.getReferencedType();
		assertFalse(importType.isEnum());
		// assertFalse(importType.isPrimitive());
		assertEquals("Z", importType.getSimpleName());
		assertEquals("a.b.c.Z", importType.getName());
		assertNotNull(importedTypeRef.getSource());
		assertNotNull(importType.getPackage());
		assertEquals("a.b.c", importType.getPackage().getName());
	}

}
