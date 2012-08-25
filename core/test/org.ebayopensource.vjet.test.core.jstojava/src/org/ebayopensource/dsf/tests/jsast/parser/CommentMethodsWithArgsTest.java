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
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstDefaultConstructor;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Comment (forward, backward) tests for methods with no args, props and protos.
 * 
 * 
 *
 */
@RunWith(value=Parameterized.class)
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class CommentMethodsWithArgsTest implements ICommentConstants {
	
	private static final int NUM_METHODS = 8;
	
	private IJstType jstType = null;
	
	private String fileName = null; 
	private boolean isStatic = false;
	private String type = null;
	private String returnType = null;
	
	private void verifyArg(JstArg arg, String name, String type,
			boolean isOptional, boolean isVariable) {
		assertEquals(name, arg.getName());
		
		IJstType jt = arg.getType();
		assertEquals(type, jt.getName());
		
		assertEquals(isOptional,arg.isOptional());
		assertEquals(isVariable,arg.isVariable());		
	}
	
	@Parameters
	public static Collection data() {
		return Arrays.asList( new Object[][] {
				//input file, isStatic, arg type, return type 
				{FOLDER+"/CommentMethodsWithArgsPropsPintCType.vjo", true, "int", "void"},
				{FOLDER+"/CommentMethodsWithArgsProtosStringCType.vjo", false, "String", "void"},
		});
	}

	public CommentMethodsWithArgsTest(String fileName, boolean isStatic, 
			String type, String returnType) {
		this.fileName = fileName;
		this.isStatic = isStatic;
		this.type = type;
		this.returnType = returnType;
	}
	
	@Before
	public void setUpJst() throws Exception {
		// get file
		URL simple1 = ResourceUtil.getResource(this.getClass(),fileName);
		jstType = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1).getType();
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with args declared - Number of methods(instance/static), and method name")
	public void verifyMethods() {
		
		IJstMethod cons = jstType.getConstructor();
		assertTrue(cons == null | cons instanceof JstDefaultConstructor);
		
		List<? extends IJstMethod> methodMap = null;
		if (isStatic) {
			methodMap = JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods());
			assertTrue(methodMap.size() > 0);
			assertEquals(NUM_METHODS, methodMap.size());	
			
			//assertFalse(JstTypeHelper.getDeclaredMethods(jstType.getInstanceMethods()).size() > 0);
			assertEquals(0, JstTypeHelper.getDeclaredMethods(jstType.getInstanceMethods()).size());
		} else {
			//assertFalse(jstType.hasStaticMethods());
			assertEquals(0,JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods()).size());
			
			methodMap = JstTypeHelper.getDeclaredMethods(jstType.getInstanceMethods());
			assertTrue(methodMap.size() > 0);
			assertEquals(NUM_METHODS, methodMap.size());	
		}
		
		Iterator<? extends IJstMethod> it = methodMap.iterator(); 
		for (int ii = 0; it.hasNext(); ii++) {
			IJstMethod mtd = it.next();
			assertEquals(PREFIX_METHOD + ii, mtd.getName().getName());
		}
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with 1 args declared - " +
//			"AccessScope, static, return type and arguments")
	public void verifyOneArgMethods() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"0");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(PACKAGE_ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(1, jal.size());
		
		verifyArg(jal.get(0),PREFIX_ARG+"0", type, false, false);
	}
	
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with 1 variable args declared - " +
//			"AccessScope, static, return type and arguments")
	public void verifyOneVariableArgMethods() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"2");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(PACKAGE_ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(1, jal.size());
		
		verifyArg(jal.get(0), VARIABLE_ARG, type, false, true);
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with 2 args declared - " +
//			"AccessScope, static, return type and arguments")
	public void verifyTwoArgsMethods() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"3");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(PACKAGE_ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(2, jal.size());
		
		verifyArg(jal.get(0), PREFIX_ARG+"0", type, false, false);
		verifyArg(jal.get(1), PREFIX_ARG+"1", type, false, false);
	}

	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with 1 args and 1 variable arg declared - " +
//			"AccessScope, static, return type and arguments")
	public void verifyArgPlusVariableArgMethods() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"5");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(PACKAGE_ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(2, jal.size());
		
		verifyArg(jal.get(0), PREFIX_ARG+"0", type, false, false);
		verifyArg(jal.get(1), VARIABLE_ARG, type, false, true);
	}
	
	@After
	public void tearDownJst() throws Exception {
		jstType = null;

	}

}
