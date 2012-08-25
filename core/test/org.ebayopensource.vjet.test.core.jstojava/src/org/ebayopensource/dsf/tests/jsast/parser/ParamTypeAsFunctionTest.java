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
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
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
public class ParamTypeAsFunctionTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/ParamTypeAsFunction.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		File resource= new File(ResourceUtil.getResource(ParamTypeAsFunctionTest.class,
				                fileName).getFile());
		URL url = ResourceUtil.getResource(BadCommentTest.class,fileName);
		String fileAsString = JsPreGenHelper.url2String(url);
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
		
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies function type as a parameter type")
	public void verifyParamTypeAsFunction() {
		//testing single function parameter
		IJstMethod bar = jstType.getStaticMethod("bar");
		assertNotNull(bar.getArgs());
		assertTrue(bar.getArgs().size() > 0);
		
		final JstArg paramAsFunc = bar.getArgs().get(0);
		assertNotNull(paramAsFunc);
		verifyParamType(paramAsFunc, "void", "int");
		
		//testing single function not in the 1st argument position
		IJstMethod foo = jstType.getStaticMethod("foo");
		assertNotNull(foo.getArgs());
		assertTrue(foo.getArgs().size() > 1);
		
		final JstArg param2AsFunc = foo.getArgs().get(1);
		assertNotNull(param2AsFunc);
		verifyParamType(param2AsFunc, "void", "int");
		
		//testing multiple function parameters
		IJstMethod fun = jstType.getStaticMethod("fun");
		assertNotNull(foo.getArgs());
		assertTrue(foo.getArgs().size() > 1);
		
		final JstArg param3AsFunc = fun.getArgs().get(0);
		assertNotNull(param3AsFunc);
		verifyParamType(param3AsFunc, "int", "String");
		
		final JstArg param4AsFunc = fun.getArgs().get(1);
		assertNotNull(param4AsFunc);
		verifyParamType(param4AsFunc, "void", "int");
		
		IJstMethod bad = jstType.getStaticMethod("bad");
		assertNotNull(bad.getArgs());
		assertEquals(1, bad.getArgs().size());
		assertNotNull(bad.getArgs().get(0));
//		verifyParamType(bad.getArgs().get(0), "int", "String");
		
		boolean assignmentFound = false;
		for(IStmt stmt: bad.getBlock().getStmts()){
			if(stmt instanceof JstVars){
				final JstVars vars = (JstVars)stmt;
				assertNotNull(vars.getType());
				
				if("i".equals(vars.getAssignments().get(0).getLHS().toLHSText())){
					assertEquals("int", vars.getType().getName());
					assignmentFound = true;
				}
			}
		}
		assertTrue(assignmentFound);
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}

	private void verifyParamType(final JstArg paramAsFunc, final String retTypeName, final String paramTypeName) {
		final IJstType paramType = paramAsFunc.getType();
		assertNotNull(paramType);
		assertEquals("Function", paramType.getName());
		assertEquals(JstFuncType.class, paramType.getClass());
		final JstFuncType funcType = (JstFuncType)paramType;
		assertNotNull(funcType.getFunction());
		assertEquals(retTypeName, funcType.getFunction().getRtnType().getName());
		assertEquals(paramTypeName, funcType.getFunction().getArgs().get(0).getType().getName());
	}
	
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;
	}
}
