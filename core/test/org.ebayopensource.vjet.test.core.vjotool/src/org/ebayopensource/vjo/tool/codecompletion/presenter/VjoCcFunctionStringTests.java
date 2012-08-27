/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.presenter;


import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Ignore;
import org.junit.Test;




//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcFunctionStringTests extends VjoCcBaseTest {
	
	private MockVjoCcPresenter presenter = new MockVjoCcPresenter();
	
	@Test
	public void testPublicFunctionString(){
		int identifier = JstModifiers.PUBLIC;
		String name = "testFunction";
		String expected = "//>public void " + name + "() " + getLineSeparator()
			+ name + " : function(){" + getLineSeparator() + "\t" + 
			getCursorPos()+ getLineSeparator() + "}";
		testFunctionString(name, expected, identifier);
	}
	
	@Test
	public void testProtectedFunctionString(){
		int identifier = JstModifiers.PROTECTED;
		String name = "testFunction";
		String expected = "//>protected void " + name + "() " + getLineSeparator()
			+ name + " : function(){" + getLineSeparator() + "\t" + 
			getCursorPos()+ getLineSeparator() + "}";
		testFunctionString(name, expected, identifier);
	}
	
	@Test
	public void testPrivateFunctionString(){
		int identifier = JstModifiers.PRIVATE;
		String name = "testFunction";
		String expected = "//>private void " + name + "() " + getLineSeparator()
			+ name + " : function(){" + getLineSeparator() + "\t" + 
			getCursorPos()+ getLineSeparator() + "}";
		testFunctionString(name, expected, identifier);
	}
	
	@Test
	public void testFinalFunctionString(){
		int identifier = JstModifiers.FINAL;
		String name = "testFunction";
		String expected = "//>final void " + name + "() " + getLineSeparator()
			+ name + " : function(){" + getLineSeparator() + "\t" + 
			getCursorPos()+ getLineSeparator() + "}";
		testFunctionString(name, expected, identifier);
	}
	
	@Test
	public void testPublicConstructsString(){
		String modifier = "public";
		String expected = "//>public constructs()" + getLineSeparator()
			+ "constructs : function(){" + getLineSeparator() + "\t" + 
			getCursorPos()+ getLineSeparator() + "}";
		String replaceString = presenter.getConstructorString(modifier, "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testPrivateConstructsString(){
		String modifier = "private";
		String expected = "//>private constructs()" + getLineSeparator()
			+ "constructs : function(){" + getLineSeparator() + "\t" + 
			getCursorPos()+ getLineSeparator() + "}";
		String replaceString = presenter.getConstructorString(modifier, "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFunctionOverRideStr(){
		String method = "instanceFunc1";
		String expected = "//>public void instanceFunc1()" + getLineSeparator()
			+ "instanceFunc1 : function(){" + getLineSeparator() + "\t" + 
			getCursorPos() + "this.base.instanceFunc1();" + getLineSeparator() + "}";
		testOverRideStr(expected, method);
	}
	
	@Test
	public void testFunctionOverRideStr1(){
		String method = "instanceFunc2";
		String expected = "//>private final String instanceFunc2()" + getLineSeparator()
			+ "instanceFunc2 : function(){" + getLineSeparator() + "\t" + 
			getCursorPos() + "return this.base.instanceFunc2();" + getLineSeparator() + "}";
		testOverRideStr(expected, method);
	}
	
//	@Test
//	@Ignore //TODO need get confirmation on how to deal with default method which was overloaded from SJC team
//	public void testFunctionOverRideStr2(){
//		String method = "staticFunc1";
//		String expected = "//>public String staticFunc1(String s1, int n1)" + getLineSeparator()
//			+ "staticFunc1 : function( s1,  n1){" + getLineSeparator() + "\t" + 
//			getCursorPos() + "return this.base.staticFunc1(s1, n1);" + getLineSeparator() + "}";
//		testOverRideStr(expected, method);
//	}
	
	@Test
//	@Ignore //TODO need get confirmation on how to deal with default method which was overloaded from SJC team
	public void testFullMethodString(){
		String method = "staticFunc1";
		String expected = "staticFunc1(String s1, int n1) String - FunctionPosition";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getFullMethodString(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullMethodString1(){
		String method = "staticFunc2";
		String expected = "staticFunc2(Array a1, Date d1) void - FunctionPosition";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getFullMethodString(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullMethodString2(){
		String method = "instanceFunc1";
		String expected = "instanceFunc1() void - FunctionPosition";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getFullMethodString(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullMethodString3(){
		String method = "instanceFunc2";
		String expected = "instanceFunc2() String - FunctionPosition";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getFullMethodString(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
//	@Ignore //TODO need get confirmation on how to deal with default method which was overloaded from SJC team
	public void testFullMethodStringForOverride(){
		String method = "staticFunc1";
		String js = "presenter.FunctionPosition";
		String expected = "staticFunc1(String s1, int n1) String - " +
			"Override method in '" + js + "'";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.
			getMethodStringForOverrideProposal(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	//@Description("Tests for NPE if the method does not have return type")
	public void testMethodStrNoReturn(){
		String method = "func1";
		String js = "BugJsFiles.Bug6554";
		String expected = "func1() void - " +
			"Implement method in '" + js + "'";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.
			getMethodStringForOverrideProposal(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullMethodStringForOverride1(){
		String method = "staticFunc2";
		String js = "presenter.FunctionPosition";
		String expected = "staticFunc2(Array a1, Date d1) void - " +
			"Override method in '" + js + "'";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.
			getMethodStringForOverrideProposal(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullMethodStringForOverride2(){
		String method = "instanceFunc1";
		String js = "presenter.FunctionPosition";
		String expected = "instanceFunc1() void - " +
			"Override method in '" + js + "'";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.
			getMethodStringForOverrideProposal(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullMethodStringForOverride3(){
		String method = "instanceFunc2";
		String js = "presenter.FunctionPosition";
		String expected = "instanceFunc2() String - " +
			"Override method in '" + js + "'";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.
			getMethodStringForOverrideProposal(type.getMethod(method));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullPropertyString(){
		String prop = "validStaticProp1";
		String expected = "validStaticProp1 int - FunctionPosition";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getPropertyString(type.getProperty(prop));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullPropertyString1(){
		String prop = "validstaticProp2";
		String expected = "validstaticProp2 String - FunctionPosition";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getPropertyString(type.getProperty(prop));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullPropertyString2(){
		String prop = "validProp1";
		String expected = "validProp1 int - FunctionPosition";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getPropertyString(type.getProperty(prop));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullPropertyString3(){
		String prop = "validProp2";
		String expected = "validProp2 String - FunctionPosition";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getPropertyString(type.getProperty(prop));
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testFullTypeDisplayString(){
		String expected = "FunctionPosition - presenter";
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		String replaceString = CodeCompletionUtils.getTypeDispalyString(type);
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test
	public void testInterfaceFunctionString(){
		String expected = "//>public void func1() " + getLineSeparator() + 
				"func1 : vjo.NEEDS_IMPL";
		String replaceString = presenter.getFunctionString(JstModifiers.PUBLIC,
				"func1", true, "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	private void testOverRideStr(String expected, String method){
		String js = "presenter.FunctionPosition";
		IJstType type = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		Assert.assertNotNull("Can not find JstType: presenter.FunctionPosition", type);
		String replaceString = 
			presenter.getReplaceStringForOverrideProposal(type.getMethod(method), "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}

	private void testFunctionString(String name, String expected, int identifier){
		String replaceString = presenter.getFunctionString(identifier, name, false, "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	private String getLineSeparator(){
		return presenter.getLineSeperator();
	}
	
	private String getCursorPos(){
		return CodeCompletionUtils.CURSOR_POSITION_TOKEN;
	}
}
