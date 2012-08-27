/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.presenter;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstComletionOnMessageSend;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtxForTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcPresenterTests extends VjoCcBaseTest {
	private MockVjoCcPresenter presenter = new MockVjoCcPresenter();
	
	/**
	 * Test inner type's replace string
	 */
	@Test
	public void testReplaceStringForInnerType() {
		IJstType type = super.getJstType(LibManager.VJO_SELF_DESCRIBED , "vjo.Console");
		assertNotNull("Can not find JstType: Console", type);
		String replaceString = presenter.getSimpleTypeName(type);
		assertTrue("Get wrong replaceString for inner type, should be 'vjo.syserr', but now is '" + replaceString + "'" , "Console".equals(replaceString));
	}
	
	@Test //Bug5737
	public void testStaticMethodCalledInStaticMethod() {
		String js = "nonStaticPropAdvisor.ProtosAdvisorTest1";
		String expected = "this.testFunction()";
		VjoCcCtxForTest vjoCcCtx = getEmptyContext();
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		vjoCcCtx.setCompletion(new JstComletionOnMessageSend(null));
		vjoCcCtx.setActingType(jstType);
		IJstMethod method = jstType.getMethod("testFunction");
		String s = presenter.getMethodProposalReplaceStr(false, method, vjoCcCtx);
		assertTrue("Correct result should be: " + expected + " , but the result is: " + s, s.equals(expected) );
	}
	@Test //Bug5741, 5737
	public void testStaticPropertyCalledInStaticMethod() {
		String js = "nonStaticPropAdvisor.ProtosAdvisorTest1";
		String expected = "this.property1";
		VjoCcCtxForTest vjoCcCtx = getEmptyContext();
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		vjoCcCtx.setCompletion(new JstComletionOnMessageSend(null));
		vjoCcCtx.setActingType(jstType);
		IJstProperty property = jstType.getProperty("property1");
		String s = presenter.getPropertyProposalReplaceStr(false, property, vjoCcCtx);
		assertTrue("Correct result should be: " + expected + " , but the result is: " + s, s.equals(expected) );
	}
	
	@Test //Bug 5883
	public void testReplaceStringForOverrideProposal() {
		IJstType jstType = getJstType(LibManager.VJO_SELF_DESCRIBED, "vjo.Object");
		IJstMethod method = jstType.getMethod("equals");
		String s = presenter.getReplaceStringForOverrideProposal(method, "\t");
		assertTrue("Replace string should contains keyword 'return' " , s.contains("return") );
	}
	
	@Test //Bug 5883
	 //TODO need get confirmation on how to deal with default method which was overloaded from SJC team
	public void testReplaceStrOverrideProposals(){
		String expected = "//>public String staticFunc1(String s1, int n1)" + 
			getLineSeparator() + "staticFunc1 : function(s1, n1){" + 
			getLineSeparator() + "\t"+ getCursorPos() +"return this.base.staticFunc1(s1, n1);" + getLineSeparator() + "}";
		String typeName = "presenter.FunctionPosition";
		IJstType type = super.getJstType(CodeCompletionUtil.GROUP_NAME, typeName);
		assertNotNull("Can not find JstType: presenter.FunctionPosition", type);
		String replaceString = presenter.getReplaceStringForOverrideProposal(type.getMethod("staticFunc1"), "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test //Bug 6301
	public void testReplaceStrOverrideProposals1(){
		String expected = "//>private final void staticFunc2(Array a1, Date d1)" + 
			getLineSeparator() + "staticFunc2 : function(a1, d1){" + 
			getLineSeparator() + "\t"+ getCursorPos() +"this.base.staticFunc2(a1, d1);" + getLineSeparator() + "}";
		String typeName = "presenter.FunctionPosition";
		IJstType type = super.getJstType(CodeCompletionUtil.GROUP_NAME, typeName);
		assertNotNull("Can not find JstType: presenter.FunctionPosition", type);
		String replaceString = presenter.getReplaceStringForOverrideProposal(type.getMethod("staticFunc2"), "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test //Bug 6301
	public void testReplaceStrOverrideProposals2(){
		String expected = "//>public void instanceFunc1()" + 
			getLineSeparator() + "instanceFunc1 : function(){" + 
			getLineSeparator() + "\t"+ getCursorPos() +"this.base.instanceFunc1();" + getLineSeparator() + "}";
		String typeName = "presenter.FunctionPosition";
		IJstType type = super.getJstType(CodeCompletionUtil.GROUP_NAME, typeName);
		assertNotNull("Can not find JstType: presenter.FunctionPosition", type);
		String replaceString = presenter.getReplaceStringForOverrideProposal(type.getMethod("instanceFunc1"), "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test //Bug 5883
	public void testReplaceStrOverrideProposals3(){
		String expected = "//>private final String instanceFunc2()" + 
			getLineSeparator() + "instanceFunc2 : function(){" + 
			getLineSeparator() + "\t"+ getCursorPos() +"return this.base.instanceFunc2();" + getLineSeparator() + "}";
		String typeName = "presenter.FunctionPosition";
		IJstType type = super.getJstType(CodeCompletionUtil.GROUP_NAME, typeName);
		assertNotNull("Can not find JstType: presenter.FunctionPosition", type);
		String replaceString = presenter.getReplaceStringForOverrideProposal(type.getMethod("instanceFunc2"), "\t");
		assertTrue("Get wrong replaceString, Expected " + expected
				+ ", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	@Test //Bug 6240
	public void testReplaceStrOverrideProposals4(){
		String expected = "//>public void func1()" + 
			getLineSeparator() + "func1 : function(){" + 
			getLineSeparator() + "\t"+ getCursorPos() + getLineSeparator() + "}";
		String typeName = "BugJsFiles.Bug6554";
		IJstType type = super.getJstType(CodeCompletionUtil.GROUP_NAME, typeName);
		assertNotNull("Can not find JstType: presenter.FunctionPosition", type);
		String replaceString = presenter.getReplaceStringForOverrideProposal(type.getMethod("func1"), "\t");
		assertEquals(expected, replaceString);
	}
	
	@Test //Bug 6240
	public void testReplaceStrOverrideProposals5(){
		String expected = "//>public String func1(String str)" + 
			getLineSeparator() + "func1 : function(str){" + 
			getLineSeparator() + "\t"+ getCursorPos() +"return null;" + getLineSeparator() + "}";
		String typeName = "BugJsFiles.Bug6240";
		IJstType type = super.getJstType(CodeCompletionUtil.GROUP_NAME, typeName);
		assertNotNull("Can not find JstType: presenter.FunctionPosition", type);
		for (IJstMethod m : JstTypeHelper.getSignatureMethods(type)) {
			String replaceString = presenter.getReplaceStringForOverrideProposal(m, "\t");
			assertEquals(expected, replaceString);
			break;
		}
	}
	
	private String getLineSeparator(){
		return presenter.getLineSeperator();
	}
	
	private String getCursorPos(){
		return CodeCompletionUtils.CURSOR_POSITION_TOKEN;
	}
}
