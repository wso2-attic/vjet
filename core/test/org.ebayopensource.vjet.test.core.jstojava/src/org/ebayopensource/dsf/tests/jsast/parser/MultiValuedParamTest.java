/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Tests if explicit static in .props is ok
 * 
 * 
 *
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class MultiValuedParamTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/MultiValuedParam.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		File resource= new File(ResourceUtil.getResource(MultiValuedParamTest.class,
				                fileName).getFile());
		URL url = ResourceUtil.getResource(BadCommentTest.class,fileName);
		String fileAsString = JsPreGenHelper.url2String(url);
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
		
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies local function overloading supported")
	public void verifyMethodOverloadingWithMultiValueParameters() {
		final IJstMethod bar = jstType.getStaticMethod("bar");
		verifyFunctionSignature(bar);
		
		assertTrue(bar.getBlock().getStmts().size() > 0);
		for(IStmt stmt: bar.getBlock().getStmts()){
			if(stmt instanceof JstVars){
				for(AssignExpr assignment: ((JstVars)stmt).getAssignments()){
					assertNotNull(assignment);
					assertNotNull(assignment.getLHS());
					if(assignment.getLHS() instanceof JstIdentifier){
						final JstIdentifier identifier = (JstIdentifier)assignment.getLHS();
						assertNotNull(identifier);
						final IJstNode jstBinding = identifier.getJstBinding();
						assertNotNull(jstBinding);
						assertTrue(jstBinding instanceof IJstMethod);
						final IJstMethod localFunction = (IJstMethod)jstBinding;
						verifyFunctionSignature(localFunction);
					}
				}
			}
		}
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}

	private void verifyFunctionSignature(IJstMethod bar) {
		assertNotNull(bar);
		assertTrue(bar.isDispatcher());
		assertNotNull(bar.getOverloaded());
		assertEquals(2, bar.getOverloaded().size());
		assertNotNull(bar.getOverloaded().get(0));
		assertNotNull(bar.getOverloaded().get(1));
		assertEquals(1, bar.getOverloaded().get(0).getArgs().size());
		assertEquals(1, bar.getOverloaded().get(1).getArgs().size());
		assertEquals("String", bar.getOverloaded().get(0).getArgs().get(0).getType().getSimpleName());
	}
	
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies local function overloading supported")
	public void verifyMethodOverloadingWithMultiValueParametersFoo() {
		final IJstMethod foo = jstType.getStaticMethod("foo");
		verifyMoreFunctionSignature(foo);
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}
	
	private void verifyMoreFunctionSignature(IJstMethod foo) {
		assertNotNull(foo);
		assertTrue(foo.isDispatcher());
		assertNotNull(foo.getOverloaded());
		assertEquals(4, foo.getOverloaded().size());
		assertNotNull(foo.getOverloaded().get(0));
		assertNotNull(foo.getOverloaded().get(1));
		assertNotNull(foo.getOverloaded().get(2));
		assertNotNull(foo.getOverloaded().get(3));
		assertEquals(2, foo.getOverloaded().get(0).getArgs().size());
		assertEquals(2, foo.getOverloaded().get(1).getArgs().size());
		assertEquals(2, foo.getOverloaded().get(2).getArgs().size());
		assertEquals(2, foo.getOverloaded().get(3).getArgs().size());
		
//		assertEquals("String", foo.getOverloaded().get(0).getArgs().get(0).getType().getSimpleName());
	}
	
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies local function overloading supported optional + multi value")
	public void verifyMethodOverloadingWithMultiValueParametersFun() {
		final IJstMethod fun = jstType.getStaticMethod("fun");
		verifyCombinedFunctionSignature(fun);
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}
	
	private void verifyCombinedFunctionSignature(IJstMethod fun) {
		assertNotNull(fun);
		assertTrue(fun.isDispatcher());
		assertNotNull(fun.getOverloaded());
		assertEquals(4, fun.getOverloaded().size());
		assertNotNull(fun.getOverloaded().get(0));
		assertNotNull(fun.getOverloaded().get(1));
		assertNotNull(fun.getOverloaded().get(2));
		assertNotNull(fun.getOverloaded().get(3));
		assertEquals(1, fun.getOverloaded().get(0).getArgs().size());
		assertEquals(2, fun.getOverloaded().get(1).getArgs().size());
		assertEquals(2, fun.getOverloaded().get(2).getArgs().size());
		assertEquals(2, fun.getOverloaded().get(3).getArgs().size());
		
//		assertEquals("String", foo.getOverloaded().get(0).getArgs().get(0).getType().getSimpleName());
	}
	
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies local function overloading supported optional + multi value")
	public void verifyMethodOverloadingWithMultiValueParametersMore() {
		final IJstMethod fun = jstType.getStaticMethod("more");
		verifyCombinedMoreFunctionSignature(fun);
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}
	
	private void verifyCombinedMoreFunctionSignature(IJstMethod more) {
		assertNotNull(more);
		assertTrue(more.isDispatcher());
		assertNotNull(more.getOverloaded());
		assertEquals(7, more.getOverloaded().size());
		for(int i = 0; i < 7; i++){
			final int numberOfArgs = i == 0 ? 0 :
					i < 3 ? 1 : 2;
			assertEquals(numberOfArgs, more.getOverloaded().get(i).getArgs().size());
		}
	}
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;
	}
}
