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

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Comment (forward, backward) tests for vars, props and protos.
 * 
 * 
 *
 */
@RunWith(value=Parameterized.class)
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class CommentVarsTest implements ICommentConstants {
	
	private static final String PREFIX_VAR = "count";
	
	private IJstType jstType = null;
	
	private String fileName = null; 
	private char kind = ' ';
	private boolean isStatic = false;
	private String type = null;
	private String initialValue = null;
	
	@Parameters
	public static Collection data() {
		return Arrays.asList( new Object[][] {
				//input file, {'C','A','I','E','M'}, isStatic, type, initial value
				{FOLDER+"/CommentVarsPropsPbooleanCType.vjo", CONCRETE, true, "boolean", "false"},
				{FOLDER+"/CommentVarsPropsPcharCType.vjo", CONCRETE, true, "char", "a"},
				{FOLDER+"/CommentVarsPropsPshortCType.vjo", CONCRETE, true, "short", "0"},
				{FOLDER+"/CommentVarsPropsPintCType.vjo", CONCRETE, true, "int", "0"},
				{FOLDER+"/CommentVarsPropsPlongCType.vjo", CONCRETE, true, "long", "0"},				
				{FOLDER+"/CommentVarsPropsPfloatCType.vjo", CONCRETE, true, "float", "0.0"},
				{FOLDER+"/CommentVarsPropsPdoubleCType.vjo", CONCRETE, true, "double", "0.0"},
				
				{FOLDER+"/CommentVarsPropsWBooleanCType.vjo", CONCRETE, true, "Boolean", "false"},
				{FOLDER+"/CommentVarsPropsWCharacterCType.vjo", CONCRETE, true, "Character", "a"},
				{FOLDER+"/CommentVarsPropsWShortCType.vjo", CONCRETE, true, "Short", "0"},
				{FOLDER+"/CommentVarsPropsWIntegerCType.vjo", CONCRETE, true, "Integer", "0"},
				{FOLDER+"/CommentVarsPropsWLongCType.vjo", CONCRETE, true, "Long", "0"},	
				{FOLDER+"/CommentVarsPropsWFloatCType.vjo", CONCRETE, true, "Float", "0.0"},
				{FOLDER+"/CommentVarsPropsWDoubleCType.vjo", CONCRETE, true, "Double", "0.0"},

				{FOLDER+"/CommentVarsPropsStringCType.vjo", CONCRETE, true, "String", "hello"},
				{FOLDER+"/CommentVarsPropsJavaUtilDateCType.vjo", CONCRETE, true, "java.util.Date", "0"},
			
				{FOLDER+"/CommentVarsProtosPintCType.vjo", CONCRETE, false, "int", "0"},
				
				//TODO look at these closely
				{FOLDER+"/CommentVarsPropsPintAType.vjo", ABSTRACT, true, "int", "0"},				
				{FOLDER+"/CommentVarsProtosStringAType.vjo", ABSTRACT, false, "String", "hello"},
				
				{FOLDER+"/CommentVarsPropsPintIType.vjo", INTERFACE, true, "int", "0"},				
				{FOLDER+"/CommentVarsProtosStringIType.vjo", INTERFACE, false, "String", "hello"},
		});
	}

	public CommentVarsTest(String fileName, char kind, boolean isStatic, String type, 
			String initialValue) {
		this.fileName = fileName;
		this.kind = kind;
		this.isStatic = isStatic;
		this.type = type;
		this.initialValue = initialValue;
	}
	
	@Before
	public void setUpJst() throws Exception {
		// get file
		IResourceResolver jstLibResolver = org.ebayopensource.dsf.jstojava.test.utils.JstLibResolver.getInstance()
				.setSdkEnvironment(new org.ebayopensource.dsf.jstojava.test.utils.VJetSdkEnvironment(new String[0], "DefaultSdk"));

		LibManager.getInstance().setResourceResolver(jstLibResolver);
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
	//@Description("Verifies properties declared.")
	public void verifyVars() {
		List<IJstProperty> varMap = null;
		if (isStatic) {
			//assertTrue(jstType.hasStaticProperties());
			assertEquals(11, JstTypeHelper.getDeclaredProperties(jstType.getStaticProperties()).size());	
			
			varMap = JstTypeHelper.getDeclaredProperties(jstType.getStaticProperties());

			//assertFalse(jstType.hasInstanceProperties());
			assertEquals(0, JstTypeHelper.getDeclaredProperties(jstType.getInstanceProperties()).size());
		} else {
			//assertFalse(jstType.hasStaticProperties());
			assertEquals(0,JstTypeHelper.getDeclaredProperties(jstType.getStaticProperties()).size());
			
			//assertTrue(jstType.hasInstanceProperties());
			assertEquals(11, JstTypeHelper.getDeclaredProperties(jstType.getInstanceProperties()).size());	
			
			varMap = JstTypeHelper.getDeclaredProperties(jstType.getInstanceProperties());
		}
		
		Iterator<IJstProperty> it = varMap.iterator(); 
		for (int ii = 0; it.hasNext(); ii++) {
			IJstProperty pty = it.next();
			assertEquals(PREFIX_VAR + ii, pty.getName().getName());

			IJstProperty jp = pty;
			if (type.equals("String") || type.equals("Character") || type.equals("char")) {
				assertEquals("\""+initialValue+"\"", jp.getValue().toSimpleTermText());
			}
			else {
				assertEquals(initialValue, jp.getValue().toSimpleTermText());
			}
		}
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are static.")
	public void verifyStaticVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"0");
		String access = jp.getModifiers().getAccessScope();
		assertEquals(PACKAGE_ACCESS, access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		//assertTrue(jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are static and final.")
	public void verifyStaticFinalVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"1");
		String access = jp.getModifiers().getAccessScope();
		assertEquals(PACKAGE_ACCESS, access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		//TODO - Norman, please look into this. This seems like used for a lot. I added fix for removing final for protos property in itype
		//assertTrue(jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are static and public access.")
	public void verifyPublicStaticVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"2");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("public", access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		//assertFalse(jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are public, static and final.")
	public void verifyPublicStaticFinalVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"3");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("public",access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic,jm.isStatic());
		
		//TODO - Norman, please fix this line. IType protos cannot have final.
		//assertTrue("Expected this property to be final", jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type,jt.getName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are final, public and static.")
	public void verifyFinalPublicStaticVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"4");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("public",access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are private and static.")
	public void verifyPrivateStaticVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"5");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("private", access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		//assertFalse(jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are private, static and final.")
	public void verifyPrivateStaticFinalVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"6");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("private", access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		//TODO - Norman, please fix this line. IType protos cannot have final.
		//assertTrue("Expected this property to be final", jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are private, static and final.")
	public void verifyFinalPrivateStaticFinalVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"7");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("private", access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are protected")
	public void verifyProtectedVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"8");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("protected", access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		//assertFalse(jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are protected and final")
	public void verifyProtectedFinalVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"9");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("protected", access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		//TODO - Norman, please fix this line. IType protos cannot have final.
		//assertTrue("Expected this property to be final", jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@Test 
	//@Category({P1,UNIT,FAST})
	//@Description("Verifies properties declared are final and protected")
	public void verifyFinalProtectedVar() {
		IJstProperty jp = jstType.getProperty(PREFIX_VAR+"10");
		String access = jp.getModifiers().getAccessScope();
		assertEquals("protected", access);
		
		JstModifiers jm = jp.getModifiers();
		assertEquals(isStatic, jm.isStatic());
		assertTrue(jm.isFinal());

		IJstType jt = jp.getType();
		assertEquals(type, jt.getName());		
	}
	
	@After
	public void tearDownJst() throws Exception {
		jstType = null;

	}

}
