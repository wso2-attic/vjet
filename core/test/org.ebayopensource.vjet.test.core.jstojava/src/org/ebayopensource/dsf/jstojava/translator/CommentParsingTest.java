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
import static org.junit.Assert.fail;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta.DIRECTION;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.dsf.jstojava.parser.comments.JsAttributed;
import org.ebayopensource.dsf.jstojava.parser.comments.JsCommentMeta;
import org.ebayopensource.dsf.jstojava.parser.comments.JsFuncType;
import org.ebayopensource.dsf.jstojava.parser.comments.ParseException;
import org.ebayopensource.dsf.jstojava.parser.comments.VjComment;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value = "DsfPrebuild", subModuleId = "JsToJava")
public class CommentParsingTest extends BaseTest {

	@Test
	//@Description("Test the comments placement and its association with js statements")
	public void testProcessComments() {
		CompilationUnitDeclaration ast = prepareAst("commentTestFile.js.txt",
				null);
		IJstType jstType = SyntaxTreeFactory2
				.createJST(ast, new TranslateCtx());

		IJstProperty sPropOne = jstType.getProperty("sPropOne");
		assertNotNull(sPropOne);
		JstModifiers sPropOne_modifiers = sPropOne.getModifiers();
		assertNotNull(sPropOne_modifiers);
		assertEquals("public", sPropOne_modifiers.getAccessScope());
		assertTrue(sPropOne_modifiers.isStatic());
		assertEquals("String", sPropOne.getType().getSimpleName());

		IJstMethod simpleMethod = jstType.getMethod("simpleMethod");
		assertNotNull(simpleMethod);
		assertTrue(simpleMethod.isPublic());
		assertNotNull(simpleMethod.getArgs());
		assertTrue(simpleMethod.getArgs().size() == 1);
		JstArg constructorArg = simpleMethod.getArgs().get(0);
		assertEquals("float", constructorArg.getType().getSimpleName());

		JstBlock block = simpleMethod.getBlock();
		assertNotNull(block);

		List<IStmt> stmts = block.getStmts();
		assertNotNull(stmts);
		assertEquals(6, stmts.size());

		JstVars var1 = (JstVars) stmts.get(0);
		assertEquals("float", var1.getType().getSimpleName());

		JstVars var2 = (JstVars) stmts.get(1);
		assertEquals("boolean", var2.getType().getSimpleName());

		JstVars var3 = (JstVars) stmts.get(4);
		assertEquals("int", var3.getType().getSimpleName());

//		 IJstGlobalVar gvar = jstType.getGlobalVar("a");
//		 assertNotNull(gvar);
//		 assertNotNull(gvar.getTypeRef());
//				
//				
//		 IJstGlobalVar gvar2 = jstType.getGlobalVar("a");
//		 assertNotNull(gvar2);
//		 assertNotNull(gvar2.getTypeRef());
//		 assertNotNull(gvar.getPropertyRef());

	}

	@Test
	//@Description("Test the comments placement and its association with js statements")
	public void testAttributedTypeComments() {
		JsCommentMeta m;
		m= parseComment("//< Foo:bar");
		checkAttributedComment(m, "BACK", "", "true", "Foo", "bar" );
		m = parseComment("//< a.b.c.Foo:bar");
		checkAttributedComment(m, "BACK", "", "true", "a.b.c.Foo", "bar" );
		m= parseComment("//< a.b.c.Foo::bar; foo text");
		checkAttributedComment(m, "BACK", "", "false", "a.b.c.Foo", "bar" );
		m = parseComment("//< public a.b.c.Foo::bar");
		checkAttributedComment(m, "BACK", "public", "false", "a.b.c.Foo", "bar" );
		m = parseComment("//< public a.b.c.Foo::bar; foo text");

	}
	
	/** 
	 * position check on comment
	 * 0 = direction
	 * 1 = modifier
	 * 2 = isInstance
	 * 3 = type
	 * 4 = attribute
	 * @param m
	 * @param args
	 */

	private void checkAttributedComment(IJsCommentMeta m, String ... args) {
		assertNotNull(m.getDirection());
		JsAttributed attributed = (JsAttributed)m.getTyping();
		assertNotNull(attributed);
		
		assertEquals(args[0], m.getDirection().name());
		assertEquals(args[1], m.getModifiers().getAccessScope());
		assertEquals(Boolean.valueOf(args[2]), attributed.isInstance());
		assertEquals("type doesn't match", args[3], attributed.getAttributor().getTypingToken().image);
		assertTrue(attributed.getName().equals(args[4]));
		
		
	}

	@Test
	//@Description("Test the generics comments")
	public void testMethodsInComments() {
		JsCommentMeta m;
		m =parseComment("//< public String foo(String, int, Number)");
		checkMethodComment(m, "BACK", "public", "foo", "String", "String", "int", "Number");
		m = parseComment("//< public String foo(String a)");
		checkMethodComment(m, "BACK", "public", "foo", "String", "String");
		m = parseComment("//< public final static int foo(String a); foo text");
		checkMethodComment(m, "BACK", "public", "foo", "int", "String");
		m= parseComment("//< public final static void foo(String a)");
		checkMethodComment(m, "BACK", "public", "foo", "void", "String");
		
		
	}
	
	/**
	 * 0 - direction
	 * 1- access
	 * 2 - return type
	 * 3 - name
	 * 4 - Argument type 1
	 * 5-n - Argument type n
	 * @param m
	 * @param args
	 */
	private void checkMethodComment(JsCommentMeta m, String ... args) {
		assertTrue(m.isMethod());
		assertNotNull(m.getDirection());
		
		assertEquals(args[0], m.getDirection().name());
		assertEquals(args[1], m.getModifiers().getAccessScope());
		assertEquals(args[2], m.getName());
		assertEquals("type doesn't match", args[3], m.getTyping().getTypingToken().image);
		
		JsFuncType func = (JsFuncType)m.getTyping();
		for(int i=4; i<args.length; i++){
			assertEquals("type doesn't match", args[i], func.getParams().get(i-4).getType());
		}
	
	}

	@Test
	//@Description("Test the generics comments")
	public void testGenericsInComments() {
		parseComment("//< Foo<E>");
		parseComment("//< GenericOuter<T extends Number>");
		parseComment("//< public Foo<E>");
		parseComment("//< public Foo<E> doIt(E)");
		parseComment("//< public Foo<E> doIt(E, Y)");
		parseComment("//< public Foo<E> doIt(E, Y); foo text");


	}
	@Test
	//@Description("Test the generics comments")
	public void testModifiersInComments() {
		parseComment("//< public");
		parseComment("//< private");
		parseComment("//< protected");
		parseComment("//< public final");
		parseComment("//< private final");
		parseComment("//< protected final");
		parseComment("//< public dynamic");
		
		
	}

	@Test
	//@Description("Test the generics comments")
	public void testNegativeComments() {
		parseComment("//> public abstract final", DIRECTION.FORWARD, true);

	}

	private JsCommentMeta parseComment(String p, DIRECTION direction, boolean expectError) {
		try {
			JsCommentMeta m = VjComment.parse(p);
			assertNotNull(m);
			assertNotNull(m.getModifiers());
			assertTrue("direction is not back", m.getDirection().equals(IJsCommentMeta.DIRECTION.BACK));
			System.out.println(m);
			return m;
		} catch (ParseException e) {
			if(!expectError){
				fail(e.getMessage());
			}
		}
		return null;
	}

	private JsCommentMeta parseComment(String p) {
		return parseComment(p, DIRECTION.BACK ,false);
	}
}
