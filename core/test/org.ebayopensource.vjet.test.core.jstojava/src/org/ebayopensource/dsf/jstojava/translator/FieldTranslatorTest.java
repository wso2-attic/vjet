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

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class FieldTranslatorTest extends BaseTest {

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test props/protos section property declaration and " +
//			"related JstProperty and access modifiers")
	public void testProcessField() throws Exception {
		CompilationUnitDeclaration ast = prepareAst(
				"fieldTranslatorTest.js.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		
		// non-static field
		assertNotNull(jstType.getInstanceProperties());
		assertEquals(1, JstTypeHelper.getDeclaredProperties(jstType.getInstanceProperties()).size());
		assertNotNull(jstType.getProperty("b"));
		
		IJstProperty jstProperty = jstType.getProperty("b");
		assertEquals("b", jstProperty.getName().getName());
		assertEquals("5", jstProperty.getValue().toSimpleTermText());
		assertEquals("String", jstProperty.getType().getName());
		assertTrue(jstProperty.getModifiers().isPublic());
		
		// static field
		assertNotNull(jstType.getStaticProperties());
		assertEquals(1, JstTypeHelper.getDeclaredProperties(jstType.getStaticProperties()).size());
		assertNotNull(jstType.getProperty("a"));
		
		IJstProperty jstStaticProperty = jstType.getProperty("a");
		assertEquals("a", jstStaticProperty.getName().getName());
		assertEquals("8", jstStaticProperty.getValue().toSimpleTermText());
		assertEquals("int", jstStaticProperty.getType().getName());
		assertTrue(jstStaticProperty.getModifiers().isPrivate());
		
	}
}
