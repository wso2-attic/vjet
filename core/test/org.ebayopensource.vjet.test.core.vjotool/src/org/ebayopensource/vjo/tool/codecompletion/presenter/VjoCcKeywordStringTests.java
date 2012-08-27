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

import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcKeywordStringTests  extends VjoCcBaseTest {
	
	private MockVjoCcPresenter presenter = new MockVjoCcPresenter();
	
	@Test
	public void testNeedsReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "needs";
		String expected = name + "('" + getCursorPos() +"')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
//	@Test
//	@Ignore
	// needsLib not supported
//	public void testNeedsLibReplaceString() {
//		String typeName = "presenter.FunctionPosition";
//		String name = "needsLib";
//		String expected = name + "('" + getCursorPos() +"')";
//		testKeywordsReplacementString(name, expected, typeName);
//	}
	
//	@Test
//	@Ignore
	// vjo.type is no longer supported removing test case
//	public void testVjoTypeReplaceString() {
//		String typeName = "presenter.FunctionPosition";
//		String name = "type";
//		String expected = name + "('" + getCursorPos() +"')";
//		testKeywordsReplacementString(name, expected, typeName);
//	}
	
	@Test
	public void testCTypeReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "ctype";
		String expected = name + "('" + typeName + "')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testITypeReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "itype";
		String expected = name + "('" + typeName + "')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testETypeReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "etype";
		String expected = name + "('" + typeName + "')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testMTypeReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "mtype";
		String expected = name + "('" + typeName + "')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testOTypeReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "otype";
		String expected = name + "('" + typeName + "')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testInheritsReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "inherits";
		String expected = name + "('" + getCursorPos() +"')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testSatisfiesReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "satisfies";
		String expected = name + "('" + getCursorPos() +"')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testExpectsReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "expects";
		String expected = name + "('" + getCursorPos() +"')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testMixinReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "mixin";
		String expected = name + "('" + getCursorPos() +"')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testProtosReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "protos";
		String expected = name + "({" + getLineSeparator() + "\t" + getCursorPos()
			+ getLineSeparator()+ "})";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testPropsReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "props";
		String expected = name + "({" +getLineSeparator() + "\t" + getCursorPos()
			+ getLineSeparator()+ "})";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testDefsReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "defs";
		String expected = name + "({" +getLineSeparator() + "\t" + getCursorPos()
			+ getLineSeparator()+ "})";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testInitsReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "inits";
		String expected = name + "(function(){" + getLineSeparator() + "\t" 
			+ getCursorPos() + getLineSeparator()+ "})";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testFunctionReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "function";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testValuesReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "values";
		String expected = name + "('" + getCursorPos() +"')";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testWhileReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "while";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testDoReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "do";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testForReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "for";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testThisReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "this";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testWindowReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "window";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testDocumentReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "document";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testReturnReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "return";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testVarReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "var";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testNewReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "new";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testIfReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "if";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testElseReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "else";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testContinueReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "continue";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testBreakReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "break";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testSwitchReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "switch";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testDefaultReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "default";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testInstanceOfReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "instanceof";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testTypeOfReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "typeof";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testBaseReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "base";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testVJDollarReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "vj$";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testSysoutReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "sysout";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testSyserrReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "syserr";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testPrintlnReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "println";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testPrintReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "print";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testPrintStackTraceReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "printStackTrace";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testEndTypeReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "endType";
		String expected = name + "();";
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	@Test
	public void testInnerEndTypeReplaceString() {
		String typeName = "presenter.TestInnerCompletion";
		String name = "endType";
		String expected = name + "()";
		String actual = CodeCompletionUtils.getKeywordReplaceString(name, typeName, false, "\t");
		assertTrue("Get wrong replaceString, Expected " + expected + 
				", Actual: " + actual, expected.equals(actual));
	}
	
	@Test
	public void testCaseReplaceString() {
		String typeName = "presenter.FunctionPosition";
		String name = "case";
		String expected = name;
		testKeywordsReplacementString(name, expected, typeName);
	}
	
	private void testKeywordsReplacementString(String name, String expected, String typeName){
		String replaceString = presenter.getKeywordReplaceString(name, typeName, false, "\t");
		assertTrue("Get wrong replaceString, Expected " + expected + 
				", Actual: " + replaceString, expected.equals(replaceString));
	}
	
	private String getLineSeparator(){
		return presenter.getLineSeperator();
	}
	
	private String getCursorPos(){
		return CodeCompletionUtils.CURSOR_POSITION_TOKEN;
	}
}
