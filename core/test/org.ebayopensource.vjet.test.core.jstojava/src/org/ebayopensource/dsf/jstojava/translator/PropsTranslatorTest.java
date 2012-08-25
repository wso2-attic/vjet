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

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class PropsTranslatorTest extends BaseTest {

	//@Test
	public void testConstructor() {
	}

	//@Test 
	//@Category({P1, FAST, UNIT})
	//@Description("Test the different assignment expressions and methods in props section")
	public void testProcessProps() throws Exception {
		// prepare data
		CompilationUnitDeclaration ast = prepareAst(
				"propsTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		List <? extends IJstMethod> staticMethods = jstType.getStaticMethods();
		IJstMethod jstMethod = getMethodByName(staticMethods, "doIt");
		assertNotNull(jstMethod);
		assertTrue(jstMethod.isStatic());
		assertEquals("doIt", jstMethod.getName().getName());
		assertNotNull(jstMethod.getBlock());
		assertNotNull(jstMethod.getName().getSource());
		// TODO assertEquals("private", jstMethod.getAccessScope()); default
		// Access Scope is private ??

		List<JstArg> args = jstMethod.getArgs();
		assertNotNull(args);
		assertEquals(1, args.size());
		JstArg jstArg = args.get(0);
		assertEquals("arg", jstArg.getName());
		assertNotNull(jstArg.getType());
		assertNotNull(jstArg.getSource());

		Collection<IJstProperty> staticProperties = jstType
				.getStaticProperties();
		assertNotNull(staticProperties);
		assertEquals(2, staticProperties.size());

		IJstProperty prop1 = getPropertyByName(staticProperties, "sPropOne");
		assertNotNull(prop1);
		assertEquals("sPropOne", prop1.getName().getName());
		assertEquals("\"Static Default\"", prop1.getValue().toSimpleTermText());
		assertNotNull(prop1.getSource());
		assertNotNull(prop1.getType());
		// TODO assertEquals("private", prop1.getAccessScope()); default Access
		// Scope is private ??

		IJstProperty prop2 = getPropertyByName(staticProperties, "sPropTwo");
		assertNotNull(prop2);
		assertEquals("sPropTwo", prop2.getName().getName());
		assertEquals("42", prop2.getValue().toSimpleTermText());
		assertNotNull(prop2.getSource());
		assertNotNull(prop2.getType());
		// TODO assertEquals("private", prop2.getAccessScope()); default Access
		// Scope is private ??
	}
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test the different arithmatic and assignment expressions in props section")
	public void testProps() throws Exception {
		// prepare data
		CompilationUnitDeclaration ast = prepareAst(
				"propsWithExpTranslatorTestFile.txt", null);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, new TranslateCtx());

		List <IJstProperty> staticProps = JstTypeHelper.getDeclaredProperties(jstType.getStaticProperties());
		assertEquals(17, staticProps.size());		
		
		IJstProperty prop1 = getPropertyByName(staticProps, "a");
		
		prop1 = getPropertyByName(staticProps, "b");
		assertEquals("a+1", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "c");
		assertEquals("a+b+i", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "d");
		assertEquals("new Date()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "e");
		assertEquals("d.getDay()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "f");
		assertEquals("new Integer(10)+Float(12.2)", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "i");
		//assertEquals("a+1", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "j");
		assertEquals("this.i+1", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "k");
		assertEquals("this.j++", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "l");
		assertEquals("(this.i||1)", prop1.getInitializer().toExprText());		
		
		prop1 = getPropertyByName(staticProps, "m");
		assertEquals("(1>2)", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "n");
		assertEquals("new Date()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "o");
		assertEquals("new Array()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "p");
		assertEquals("new Object()", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "q");
		assertEquals("new Boolean(0)", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "r");
		assertEquals("[]", prop1.getInitializer().toExprText());
		
		prop1 = getPropertyByName(staticProps, "s");
		//assertEquals("[]", prop1.getInitializer().toExprText());
		
	}
}
