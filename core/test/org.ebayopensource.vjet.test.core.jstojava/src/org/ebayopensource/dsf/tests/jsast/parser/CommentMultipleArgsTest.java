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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
 * Comment (forward, backward) tests for methods with multiple args.
 * 
 * 
 *
 */
@RunWith(value=Parameterized.class)
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class CommentMultipleArgsTest implements ICommentConstants {
	
	private static final int NUM_METHODS = 17;
	
	private IJstType jstType = null;
	
	private String fileName = null; 
	private boolean isStatic = false;
	private String returnType = null;
	
	private void verifyArg(JstArg arg, String name, List<String> typeList,
					       boolean isOptional, boolean isVariable) {
		assertEquals(name, arg.getName());
		
		List<? extends IJstType> jstList = arg.getTypes();
		assertEquals(jstList.size(), typeList.size());
		
		for (IJstType j : jstList) {
			assertTrue(typeList.contains(j.getName()));
		}
		
		assertTrue(!isOptional || !isVariable); //both can't be true
		
		assertEquals(isOptional,arg.isOptional());
		assertEquals(isVariable,arg.isVariable());		
	}
	
	@Parameters
	public static Collection data() {
		return Arrays.asList( new Object[][] {
				//input file, isStatic, return type 
				{FOLDER+"/CommentMultipleArgsPropsCType.vjo", true, "void"},
				{FOLDER+"/CommentMultipleArgsProtosCType.vjo", false, "void"},
		});
	}

	public CommentMultipleArgsTest(String fileName, boolean isStatic, 
								   String returnType) {
		this.fileName = fileName;
		this.isStatic = isStatic;
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
	//@Description("Verifies methods with multiple args in comments")
	public void verifyMethods() {
		
		IJstMethod cons = jstType.getConstructor();
		assertTrue(cons == null | cons instanceof JstDefaultConstructor);
		
		List<? extends IJstMethod> methodMap = null;
		if (isStatic) {
			methodMap = JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods());

			assertTrue(methodMap.size() > 0);
			assertEquals(NUM_METHODS, methodMap.size());	
			
			//assertFalse(jstType.hasInstanceMethods());
			assertEquals(0, JstTypeHelper.getDeclaredMethods(jstType.getInstanceMethods()).size());
		} else {
			//assertFalse(jstType.hasStaticMethods());
			assertEquals(0, JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods()).size());
			
			methodMap = JstTypeHelper.getDeclaredMethods(jstType.getInstanceMethods());
			assertTrue(methodMap.size() > 0);
			assertEquals(NUM_METHODS, jstType.getInstanceMethods().size());	
		}
		
		int ii = 0;
		for (IJstMethod jm : methodMap) {
			assertEquals(PREFIX_METHOD + ii, jm.getName().toString());
			ii++;
		}

	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with multiple args of itself in comments")
	public void verifyMultipleArgItself() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"0");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(1, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("boolean");		
		verifyArg(jal.get(0),PREFIX_ARG+"0", typeList0, false, false);
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with multiple args of itself as wrapper in comments")
	public void verifyMultipleArgItselfAsWrapper() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"1");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(1, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("Character");		
		verifyArg(jal.get(0),PREFIX_ARG+"0", typeList0, false, false);
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with multiple args of itself twice in comments")
	public void verifyMultipleArgItselfTwice() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"2");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(1, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("short");		
		verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0, false, false);
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with multiple args of itself twice as wrapper in comments")
	public void verifyMultipleArgItselfTwiceAsWrapper() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"3");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(1, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("Integer");		
		verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0, false, false);
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with multiple args of itself twice " +
//			"as primitive and as wrapper in comments")
	public void verifyMultipleArgItselfAndAsWrapper() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"4");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(1, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("int");	
		typeList0.add("Integer");
		verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0, false, false);
	}

	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with args followed by multiple args")
	public void verifyArgPlusMultipleArg() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"5");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(2, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("boolean");
		verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0, false, false);
		
		List<String> typeList1 = new ArrayList<String>();
		typeList1.add("char");
		typeList1.add("short");
		verifyArg(jal.get(1), PREFIX_ARG+"1", typeList1, false, false);
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with multiple args followed by args")
	public void verifyMultipleArgPlusArg() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"6");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(2, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("int");
		typeList0.add("long");		
		verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0, false, false);
		
		List<String> typeList1 = new ArrayList<String>();
		typeList1.add("float");
		verifyArg(jal.get(1), PREFIX_ARG+"1", typeList1, false, false);
	}

	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with 2 multiple args")
	public void verifyTwoMultipleArgs() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"7");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(2, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("double");
		typeList0.add("String");
		verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0, false, false);
		
		List<String> typeList1 = new ArrayList<String>();
		typeList1.add("java.util.Date");
		typeList1.add("Boolean");
		verifyArg(jal.get(1), PREFIX_ARG+"1", typeList1, false, false);
	}	

	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with multiple args followed by variable arg")
	public void verifyMultipleArgPlusVariableArg() {
		IJstMethod foo12 = jstType.getMethod(PREFIX_METHOD+"12");
		assertTrue(foo12.isDispatcher());
		
		List<String> booleanList = new ArrayList<String>();
		booleanList.add("boolean");
		
		List<String> charList = new ArrayList<String>();
		charList.add("char");
		
		List<List<String>> typeList0 = new ArrayList<List<String>>();
		typeList0.add(booleanList);
		typeList0.add(charList);
		
		for(IJstMethod jMethod: foo12.getOverloaded()){
			String access = jMethod.getModifiers().getAccessScope();
			assertEquals(ACCESS, access);
			
			JstModifiers jm = jMethod.getModifiers();
			assertEquals(isStatic, jm.isStatic());
			assertFalse(jm.isFinal());
			
			IJstType jrt = jMethod.getRtnType();
			assertEquals(returnType, jrt.getSimpleName());
			
			List<JstArg> jal = jMethod.getArgs();
			assertEquals(2, jal.size());
			
			
			verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0.remove(0), false, false);
			
			List<String> typeList1 = new ArrayList<String>();
			typeList1.add("short");
			verifyArg(jal.get(1), VARIABLE_ARG, typeList1, false, true);
		}
	}	
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with 3 multiple args")
	public void verifyThreeMultipleArgs() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"13");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(3, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("int");
		typeList0.add("long");
		typeList0.add("float");
		verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0, false, false);
		
		List<String> typeList1 = new ArrayList<String>();
		typeList1.add("double");
		typeList1.add("String");
		verifyArg(jal.get(1), PREFIX_ARG+"1", typeList1, false, false);
		
		List<String> typeList2 = new ArrayList<String>();
		typeList2.add("java.util.Date");
		verifyArg(jal.get(2), PREFIX_ARG+"2", typeList2, false, false);
	}	
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with 2 multiple args as wrapper")
	public void verifyTwoMultipleArgsAsWrappers() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"14");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(2, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("boolean");
		typeList0.add("int");
		verifyArg(jal.get(0), PREFIX_ARG+"0", typeList0, false, false);
		
		List<String> typeList1 = new ArrayList<String>();
		typeList1.add("Integer");
		typeList1.add("Boolean");
		verifyArg(jal.get(1), PREFIX_ARG+"1", typeList1, false, false);
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with multiple args with primitives and wrappers")
	public void verifyMultipleArgWithBothPrimitivesAndWrappers() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"15");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(1, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("boolean");
		typeList0.add("char");
		typeList0.add("short");
		typeList0.add("int");
		typeList0.add("long");
		typeList0.add("float");
		typeList0.add("double");
		typeList0.add("String");
		typeList0.add("java.util.Date");
		typeList0.add("Boolean");
		typeList0.add("Character");
		typeList0.add("Short");
		typeList0.add("Integer");
		typeList0.add("Long");
		typeList0.add("Float");
		typeList0.add("Double");
		verifyArg(jal.get(0),PREFIX_ARG+"0", typeList0, false, false);
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods with 2 multiple args with primitives and wrappers")
	public void verifyTwoMultipleArgWithBothPrimitivesAndWrappers() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"16");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jrt = jMethod.getRtnType();
		assertEquals(returnType, jrt.getSimpleName());
		
		List<JstArg> jal = jMethod.getArgs();
		assertEquals(2, jal.size());
		
		List<String> typeList0 = new ArrayList<String>();
		typeList0.add("boolean");
		typeList0.add("char");
		typeList0.add("short");
		typeList0.add("int");
		typeList0.add("long");
		typeList0.add("float");
		typeList0.add("double");
		typeList0.add("String");
		typeList0.add("java.util.Date");
		typeList0.add("Boolean");
		typeList0.add("Character");
		typeList0.add("Short");
		typeList0.add("Integer");
		typeList0.add("Long");
		typeList0.add("Float");
		typeList0.add("Double");
		verifyArg(jal.get(0),PREFIX_ARG+"0", typeList0, false, false);
		
		List<String> typeList1 = new ArrayList<String>();
		typeList1.add("boolean");
		typeList1.add("char");
		typeList1.add("short");
		typeList1.add("int");
		typeList1.add("long");
		typeList1.add("float");
		typeList1.add("double");
		typeList1.add("String");
		typeList1.add("java.util.Date");
		typeList1.add("Boolean");
		typeList1.add("Character");
		typeList1.add("Short");
		typeList1.add("Integer");
		typeList1.add("Long");
		typeList1.add("Float");
		typeList1.add("Double");
		verifyArg(jal.get(1),PREFIX_ARG+"1", typeList1, false, false);
	}
	
	
	@After
	public void tearDownJst() throws Exception {
		jstType = null;

	}

}
