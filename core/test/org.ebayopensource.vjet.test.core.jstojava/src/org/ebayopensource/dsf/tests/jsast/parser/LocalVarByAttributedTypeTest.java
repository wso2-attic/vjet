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

import org.ebayopensource.dsf.jst.IInferred;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstAttributedType;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
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
public class LocalVarByAttributedTypeTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/LocalVarByAttributedType.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		File resource= new File(ResourceUtil.getResource(LocalVarByAttributedTypeTest.class,
				                fileName).getFile());
		URL url = ResourceUtil.getResource(BadCommentTest.class,fileName);
		String fileAsString = JsPreGenHelper.url2String(url);
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
		
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies local variables declared as attributed type")
	public void verifyLocalVariableByAttributedType() {
		IJstMethod bar = jstType.getStaticMethod("bar");
		for(IStmt stmt: bar.getBlock().getStmts()){
			if(stmt instanceof JstVars){
				final IJstType varType = ((JstVars)stmt).getType();
				assertNotNull(varType);
				assertFalse(varType instanceof IInferred);
				assertTrue(varType instanceof JstAttributedType);
				assertNotNull(((JstAttributedType)varType).getAttributorType());
				assertNotNull(((JstAttributedType)varType).getAttributeName());
			}
		}
		
		IJstMethod foo = jstType.getStaticMethod("foo");
		for(IStmt stmt: foo.getBlock().getStmts()){
			if(stmt instanceof JstVars){
				final IJstType varType = ((JstVars)stmt).getType();
				assertNotNull(varType);
				assertFalse(varType instanceof IInferred);
				assertTrue(varType instanceof JstArray);
				final JstArray arrayType = (JstArray)varType;
				assertEquals(JstAttributedType.class, arrayType.getComponentType().getClass());
				assertNotNull(((JstAttributedType)arrayType.getComponentType()).getAttributorType());
				assertNotNull(((JstAttributedType)arrayType.getComponentType()).getAttributeName());
			}
		}
		
		IJstMethod fun = jstType.getStaticMethod("fun");
		for(IStmt stmt: fun.getBlock().getStmts()){
			if(stmt instanceof JstVars){
				final IJstType varType = ((JstVars)stmt).getType();
				assertNotNull(varType);
				assertFalse(varType instanceof IInferred);
				assertTrue(varType instanceof JstTypeWithArgs);
				final JstTypeWithArgs withArgsType = (JstTypeWithArgs)varType;
				assertEquals(JstAttributedType.class, withArgsType.getArgType().getClass());
				assertNotNull(((JstAttributedType)withArgsType.getArgType()).getAttributorType());
				assertNotNull(((JstAttributedType)withArgsType.getArgType()).getAttributeName());
			}
		}
		
		IJstMethod bla = jstType.getStaticMethod("bla");
		for(IStmt stmt: bla.getBlock().getStmts()){
			if(stmt instanceof JstVars){
				final IJstType varType = ((JstVars)stmt).getType();
				assertNotNull(varType);
				assertFalse(varType instanceof IInferred);
				assertTrue(varType instanceof JstFuncType);
				final JstFuncType withArgsType = (JstFuncType)varType;
				final IJstMethod function = withArgsType.getFunction();
				assertNotNull(function);
				assertEquals(JstAttributedType.class, function.getRtnType().getClass());
				assertNotNull(((JstAttributedType)function.getRtnType()).getAttributorType());
				assertNotNull(((JstAttributedType)function.getRtnType()).getAttributeName());
				for(JstArg arg: function.getArgs()){
					assertNotNull(arg);
					assertEquals(JstAttributedType.class, arg.getType().getClass());
					assertNotNull(((JstAttributedType)arg.getType()).getAttributorType());
					assertNotNull(((JstAttributedType)arg.getType()).getAttributeName());
				}
			}
		}
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}
	
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;
	}
}
