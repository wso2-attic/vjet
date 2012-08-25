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
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
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
public class CommentMethodsNoArgsTest implements ICommentConstants {
	
	private static final int NUM_METHODS = 11;
	
	private IJstType jstType = null;
	
	private String fileName = null; 
	private char kind = ' ';
	private boolean isStatic = false;
	private String type = null;
	
	@Parameters
	public static Collection data() {
		return Arrays.asList( new Object[][] {
				//input file, isStatic, type, initial value 
				{FOLDER+"/CommentMethodsNoArgsPropsPintCType.vjo", CONCRETE, true, "int"},
				
				{FOLDER+"/CommentMethodsNoArgsProtosStringCType.vjo", CONCRETE, false, "String"},
				
				//TODO look at these closely
				{FOLDER+"/CommentMethodsNoArgsPropsPintAType.vjo", ABSTRACT, true, "int"},
				{FOLDER+"/CommentMethodsNoArgsProtosStringAType.vjo", ABSTRACT, false, "String"},
				
				{FOLDER+"/CommentMethodsNoArgsPropsPintIType.vjo", INTERFACE, true, "int"},
				{FOLDER+"/CommentMethodsNoArgsProtosStringIType.vjo", INTERFACE, false, "String"},
		});
	}

	public CommentMethodsNoArgsTest(String fileName, char kind, boolean isStatic, String type) {
		this.fileName = fileName;
		this.kind = kind;
		this.isStatic = isStatic;
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
	//@Description("Verifies type. Ctype/Atype/Itype/Etype/Mtype.")
	public void verifyKind() {
		switch (kind) {
		case 'c': 
			assertFalse(jstType.getModifiers().isAbstract());
			assertFalse(jstType.isInterface());
			break;
		case 'a':
			assertTrue(jstType.getModifiers().isAbstract());
			assertFalse(jstType.isInterface());
			break;
		case 'i':
			assertFalse(jstType.getModifiers().isAbstract());
			assertTrue(jstType.isInterface());
			break;
		case 'e': fail("Enum not yet implemented");
			break;
		case 'm': fail("Mixin not yet implemented");
			break;
		default:
			fail("Invalid: " + kind);
		}		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods declared - Number of methods(instance/static), and method name")
	public void verifyMethods() {
		
		IJstMethod cons = jstType.getConstructor();
		assertTrue(cons == null | cons instanceof JstDefaultConstructor);
		
		List<? extends IJstMethod> methodMap = null;
		if (isStatic) {
			methodMap = JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods());
			assertTrue(methodMap.size() > 0);
			assertEquals(NUM_METHODS, methodMap.size());	
			
			assertFalse(JstTypeHelper.getDeclaredMethods(jstType.getInstanceMethods()).size() > 0);
			assertEquals(0, jstType.getInstanceMethods().size());
		} else {
			//assertFalse(JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods()).size() > 0);
			assertEquals(0, JstTypeHelper.getDeclaredMethods(jstType.getStaticMethods()).size());
			
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
	//@Description("Verifies methods are static")
	public void verifyStaticVar() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"0");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(PACKAGE_ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());
		
		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are static and final")
	public void verifyStaticFinalMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"1");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals(PACKAGE_ACCESS, access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are public and static")
	public void verifyPublicStaticMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"2");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("public", access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are public, static and final")
	public void verifyPublicStaticFinalMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"3");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("public", access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are final, public and static")
	public void verifyFinalPublicStaticMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"4");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("public", access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are private and static")
	public void verifyPrivateStaticMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"5");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("private", access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are private, static and final")
	public void verifyPrivateStaticFinalMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"6");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("private", access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are final, private and static")
	public void verifyFinalPrivateStaticFinalMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"7");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("private",access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are protected")
	public void verifyProtectedMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"8");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("protected", access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertFalse(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are protected and final")
	public void verifyProtectedFinalMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"9");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("protected", access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies methods are final and protected")
	public void verifyFinalProtectedMethod() {
		IJstMethod jMethod = jstType.getMethod(PREFIX_METHOD+"10");
		String access = jMethod.getModifiers().getAccessScope();
		assertEquals("protected", access);
		
		JstModifiers jm = jMethod.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jMethod.getRtnType();
		assertEquals(type, jt.getSimpleName());		
	}
	
	@After
	public void tearDownJst() throws Exception {
		jstType = null;

	}

}
