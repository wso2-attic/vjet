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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstComletionOnMessageSend;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Ast2JstTest extends BaseTest {

	@Test //@Category({P2,FAST, UNIT})
	//@Description("Partial JS file should produce meaningful JSTType. " +
//			"Type name and package should be proper")
	public void testType() {
		CompilationUnitDeclaration ast = prepareAst("ast2jstTestType.js.txt",
				null);
		TranslateCtx translateCtx = new TranslateCtx();
		IJstType type = SyntaxTreeFactory2.createJST(ast, translateCtx);
		assertEquals("Type name is invalid", "MyClass", type.getSimpleName());
		assertEquals("Package name is invalid", "vjo.darwin.app", type
				.getPackage().getName());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Vjo needs defined in older style should not blow up JstType. " +
//			"JstType should have imports information")
	public void testNeeds() {
		CompilationUnitDeclaration ast = prepareAst("ast2jstTestNeeds.js.txt",
				null);
		TranslateCtx translateCtx = new TranslateCtx();
		IJstType type = SyntaxTreeFactory2.createJST(ast, translateCtx);
		assertEquals("Wrong imports number", 3, type.getImports().size());
		assertNotNull("Cant get import", type.getImport("MyClass1"));
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with satisfies should translate to meaningful JstType." +
//			" itypes in satisfies should be present in JstType")
	public void testSatisfies() {
		CompilationUnitDeclaration ast = prepareAst(
				"ast2jstTestSatisfies.js.txt", null);
		TranslateCtx translateCtx = new TranslateCtx();
		IJstType type = SyntaxTreeFactory2.createJST(ast, translateCtx);
		List<? extends IJstType> satisfiers = type.getSatisfies();
		// assertEquals("Wrong interfaces number", 2, satisfiers.size());
		List<String> names = new ArrayList<String>(2);
		names.add("p3.ITest");
		names.add("p0.IBase");
		int i = 0;
		for (IJstType satisfier : satisfiers) {
			assertEquals("Interface name is invalid", names.get(i), satisfier
					.getName());
			i++;
		}
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with inherits should translate to meaningful JstType." +
//			" ctypes in inherits should be present in JstType")
	public void testInherits() {
		CompilationUnitDeclaration ast = prepareAst(
				"ast2jstTestInherits.js.txt", null);
		TranslateCtx translateCtx = new TranslateCtx();
		IJstType type = SyntaxTreeFactory2.createJST(ast, translateCtx);
		List<? extends IJstType> superTypes = type.getExtends();
		assertEquals("Wrong super types number", 1, superTypes.size());
		assertEquals("Interface name is invalid", "p2.Base", superTypes.get(0)
				.getName());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with inits should translate to meaningful JstType." +
//			" JstType should have proper inits section")
	public void testInits() {
		CompilationUnitDeclaration ast = prepareAst("ast2jstTestInits.js.txt",
				null);
		TranslateCtx translateCtx = new TranslateCtx();
		IJstType type = SyntaxTreeFactory2.createJST(ast, translateCtx);
		List<IStmt> inits = type.getStaticInitializers();
		assertEquals(1, inits.size());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with fields should translate to meaningful JstType." +
//			" JstType should have proper fields")
	public void testFields() {
		CompilationUnitDeclaration ast = prepareAst("ast2jstTestFields.js.txt",
				null);
		TranslateCtx translateCtx = new TranslateCtx();
		IJstType type = SyntaxTreeFactory2.createJST(ast, translateCtx);
		IJstProperty k = type.getProperty("k");
		assertNotNull("Can't get field", k);
		assertTrue(k.getModifiers().isStatic());
		assertTrue(k.getModifiers().isPublic());
		IJstProperty f = type.getProperty("f");
		assertNotNull("Can't get field", f);
		assertFalse(f.getModifiers().isStatic());
		assertFalse(f.getModifiers().isFinal());
		assertTrue(k.getModifiers().isPublic());
		IJstProperty n = type.getProperty("n");
		assertNotNull("Can't get field", n);
		assertFalse(n.getModifiers().isStatic());
		assertFalse(n.getModifiers().isFinal());
		assertTrue(k.getModifiers().isPublic());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with methods should translate to meaningful JstType." +
//			" JstType should have proper methods information")
	public void testMethod() {

		CompilationUnitDeclaration ast = prepareAst("ast2jst.js.txt", null);
		IJstType jstType = SyntaxTreeFactory2
				.createJST(ast, new TranslateCtx());

		assertNotNull(jstType);
		IJstMethod jstMethod = jstType.getMethod("foo");
		assertTrue("Not must be null", jstMethod != null);
		assertEquals("foo", jstMethod.getName().getName());

		IJstType refType = jstMethod.getRtnType();
		assertTrue("Not must be null", refType != null);
		assertEquals("String", refType.getSimpleName());

		IJstProperty jstProperty = jstType.getProperty("f");
		assertTrue("Not must be null", jstProperty != null);
		assertTrue("5".equals(jstProperty.getValue().toSimpleTermText()));

		assertTrue("Not must be null", jstType.getConstructor() != null);

		jstMethod = jstType.getMethod("bar");
		assertTrue("Not must be null", jstMethod != null);
		assertEquals("bar", jstMethod.getName().getName());
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with singleton pattern should translate to " +
//			"meaningful JstType. JstType should have MtdInvocationExpr")
	public void testCallMethodCompletion() {

		CompilationUnitDeclaration ast = prepareAst("ast2jstMethod.js.txt",
				null);
		TranslateCtx ctx = new TranslateCtx();
		int pos = findPositionInFile("ast2jstMethod.js.txt", "foo()");
		ctx.setCompletionPos(pos);
		//ctx.setCompletionPos(280);
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, ctx);

		assertNotNull(jstType);
		IJstMethod jstMethod = jstType.getMethod("foo");
		assertTrue("Not must be null", jstMethod != null);
		assertEquals("foo", jstMethod.getName().getName());

//		List<? extends IJstNode> list = jstType.getChildren();
//		assertContains(list, JstCompletionOnSingleNameReference.class);

		// foo(<cursor>);
		//ctx.setCompletionPos(284);
		ctx.setCompletionPos(pos+"foo(".length());
		ctx.setCreatedCompletion(false);
		jstType = SyntaxTreeFactory2.createJST(ast, ctx);

		List<JstCompletion> list = ctx.getJstErrors();
		System.out.println("Ast2JstTest: "+list);
		assertContains(list, JstComletionOnMessageSend.class);

		// foo()<cursor>;
		//ctx.setCompletionPos(285);
		ctx.setCompletionPos(pos+"foo()".length());
		ctx.setCreatedCompletion(false);
		jstType = SyntaxTreeFactory2.createJST(ast, ctx);

		list = ctx.getJstErrors();
		assertContains(list, JstComletionOnMessageSend.class);

	}

	private void assertContains(List<? extends JstCompletion> list, Class class1) {
		boolean contains = false;
		for (JstCompletion jstNode : list) {
			if (jstNode.getClass().equals(class1)) {
				contains = true;
				break;
			}
		}
		assertTrue("Must be contains object type " + class1.getName(), contains);
	}

	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with inits should translate to meaningful JstType.")
	public void testResolveErrors() throws IOException {

		IJstType jstType = createJstType("ast2jstProblemMethod.js.txt");			
		assertEquals("test", jstType.getSimpleName());
	}

	private IJstType createJstType(String name) throws IOException {
		InputStream stream = getClass().getResourceAsStream(name);
		int len = stream.available();
		byte[] buff = new byte[len];
		stream.read(buff);		
		String s = new String(buff);
		
		TranslateCtx tctx = new TranslateCtx();
		IJstType jstType = SyntaxTreeFactory2.createJST(null, s.toCharArray(),
				"memory", null, tctx);
		return jstType;
	}
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with inits should translate to meaningful JstType." +
//			" JstType should be able resolve  and bind the alias JstIdentifier")
	public void testAlias() throws IOException{
		IJstType type = createJstType("ast2jsttestAlias.js.txt");
		
		String name= type.getName();
		assertEquals("a.b.c.MyType", name);
		
		IJstType importType = type.getImport("MyTest");		
		assertNotNull(importType);
		 
	}
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with inits should translate to meaningful JstType." +
//			" JstType should have info about inner ctype")
	public void testInnerClass() throws IOException{
		IJstType type = createJstType("ast2jstTestInnerClass.js.txt");
		IJstType jstType = type.getEmbededType("MyInnerClass");		
		assertNotNull(jstType);
		IJstMethod method = jstType.getMethod("doIt");
		assertNotNull(method);
	}
	
	public int findPositionInFile(String filename, String keyword){
		String content = "";
		try {
			InputStream in = getClass().getResourceAsStream(filename);
			
			while(in.available() != 0)
				content += (char)in.read();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return content.indexOf(keyword);
	}
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Partail JS file with inits should translate to meaningful JstType." +
//			" JstType should not blow up if endType is not last statement in js")
	public void testEndType() throws IOException {			
		IJstType type = createJstType("ast2jstTestEndType.js.txt");
		IJstMethod method = type.getMethod("staticDoIt");
		assertNotNull(method);
		method = type.getMethod("instanceDoIt");
		assertNotNull(method);
		// JST will contain all sections even when they are 
		// the wrong order in this case protos appears after endtype
		
	}
	
//	@Test //@Category({P1, FAST, UNIT})
//	//@Description("Partail JS file with inits should translate to meaningful JstType.")
////	@Ignore
//	public void testTry() throws IOException {
//		IJstType type = createJstType("ast2jstTestTry.js.txt");
//		IJstMethod method = type.getMethod("testMethod");
//		assertNotNull(method);
//	}

}
