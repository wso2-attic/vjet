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
import static org.junit.Assert.assertNull;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
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
 * Comment (forward, backward) tests for constructors
 * 
 * 
 *
 */
@RunWith(value=Parameterized.class)
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class CommentConstructorsWithArgsTest implements ICommentConstants {
	
	private IJstType jstType = null;
	
	private String fileName = null; 
	private int testNumber = -1;
	private String type = null;
	
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
				//input file, test number, arg type	
				{FOLDER+"/CommentConstructorNoArgsCType.vjo", -1, "void"},
				{FOLDER+"/CommentConstructorWithArgsPintCType00.vjo", 0, "int"},				
				{FOLDER+"/CommentConstructorWithArgsPintCType01.vjo", 1, "int"},
				{FOLDER+"/CommentConstructorWithArgsPintCType02.vjo", 2, "int"},
				{FOLDER+"/CommentConstructorWithArgsPintCType03.vjo", 3, "int"},
				{FOLDER+"/CommentConstructorWithArgsPintCType04.vjo", 4, "int"},
				{FOLDER+"/CommentConstructorWithArgsPintCType05.vjo", 5, "int"},
				{FOLDER+"/CommentConstructorWithArgsPintCType06.vjo", 6, "int"},
				{FOLDER+"/CommentConstructorWithArgsPintCType07.vjo", 7, "int"},
		});
	}

	public CommentConstructorsWithArgsTest(String fileName, int testNumber, 
			String type) {
		this.fileName = fileName;
		this.testNumber = testNumber;
		this.type = type;
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
	//@Description("Verifies constructors declared")
	public void verifyConstructor() {
		
		IJstMethod constructor = jstType.getConstructor();
		assertNotNull(constructor);
		
		assertFalse(JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods()).size() > 0);
		assertFalse(JstTypeHelper.getDeclaredProperties(jstType.getStaticProperties()).size() > 0);
		assertFalse(JstTypeHelper.getDeclaredMethods(jstType.getInstanceMethods()).size() > 0);
		assertFalse(JstTypeHelper.getDeclaredProperties(jstType.getInstanceProperties()).size() > 0);
		
		assertEquals(0,JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods()).size());
		assertEquals(0,JstTypeHelper.getDeclaredProperties(jstType.getStaticProperties()).size());
		assertEquals(0,JstTypeHelper.getDeclaredMethods(jstType.getInstanceMethods()).size());
		assertEquals(0,JstTypeHelper.getDeclaredProperties(jstType.getInstanceProperties()).size());
	
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies constructors with no args declared")
	public void verifyNoArgsConstructor() {
		if (testNumber == -1) {
			IJstMethod constructor = jstType.getConstructor();
			String access = ((JstMethod)constructor).getAccessScope();
			assertEquals(PACKAGE_ACCESS, access);

			assertFalse(constructor.isStatic());
			assertFalse(constructor.isFinal());
			
			IJstType jrt = constructor.getRtnType();
			assertNull(jrt);
		
			List<JstArg> jal = constructor.getArgs();
			assertEquals(0, jal.size());
		}
	
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies constructors with 1 args declared")
	public void verifyOneArgConstructor() {
		if (testNumber == 0) {
			IJstMethod constructor = jstType.getConstructor();
			String access = ((JstMethod)constructor).getAccessScope();
			assertEquals(PACKAGE_ACCESS, access);

			assertFalse(constructor.isStatic());
			assertFalse(constructor.isFinal());
			
			IJstType jrt = constructor.getRtnType();
			assertNull(jrt);
		
			List<JstArg> jal = constructor.getArgs();
			assertEquals(1, jal.size());
			
			verifyArg(jal.get(0), PREFIX_ARG+"0", type, false, false);
			
		}
	
	}
	
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies constructors with 1 variable args declared")
	public void verifyOneVariableArgConstructor() {
		if (testNumber == 2) {
			IJstMethod constructor = jstType.getConstructor();
			String access = ((JstMethod)constructor).getAccessScope();
			assertEquals(PACKAGE_ACCESS, access);

			assertFalse(constructor.isStatic());
			assertFalse(constructor.isFinal());
			
			IJstType jrt = constructor.getRtnType();
			assertNull(jrt);
		
			List<JstArg> jal = constructor.getArgs();
			assertEquals(1, jal.size());
			
			verifyArg(jal.get(0), VARIABLE_ARG, type, false, true);
		}
		
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies constructors with 2 args declared")
	public void verifyTwoArgsConstructor() {
		if (testNumber == 3) {
			IJstMethod constructor = jstType.getConstructor();
			String access =((JstMethod)constructor).getAccessScope();
			assertEquals(PACKAGE_ACCESS, access);

			assertFalse(constructor.isStatic());
			assertFalse(constructor.isFinal());
			
			IJstType jrt = constructor.getRtnType();
			assertNull(jrt);
		
			List<JstArg> jal = constructor.getArgs();
			assertEquals(2, jal.size());
			
			verifyArg(jal.get(0), PREFIX_ARG+"0", type, false, false);
			verifyArg(jal.get(1), PREFIX_ARG+"1", type, false, false);
		}

	}

	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies constructors with 1 args and 1 variable args declared")
	public void verifyArgPlusVariableArgConstructor() {
		if (testNumber == 5) {
			IJstMethod constructor = jstType.getConstructor();
			String access = ((JstMethod)constructor).getAccessScope();
			assertEquals(PACKAGE_ACCESS, access);

			assertFalse(constructor.isStatic());
			assertFalse(constructor.isFinal());
			
			IJstType jrt = constructor.getRtnType();
			assertNull(jrt);
		
			List<JstArg> jal = constructor.getArgs();
			assertEquals(2, jal.size());
			
			verifyArg(jal.get(0), PREFIX_ARG+"0", type, false, false);
			verifyArg(jal.get(1), VARIABLE_ARG, type, false, true);
		}
	}

	
	//TODO code for multiple args in constructors go here
	
	@After
	public void tearDownJst() throws Exception {
		jstType = null;

	}

}
