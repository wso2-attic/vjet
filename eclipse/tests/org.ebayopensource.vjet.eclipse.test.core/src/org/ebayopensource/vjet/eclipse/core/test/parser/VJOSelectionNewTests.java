/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.VjoSelectionEngine;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

public class VJOSelectionNewTests extends AbstractSelectionModelTests {

	private static final String EDITOR_ID = "org.ebayopensource.vjet.ui.VjetJsEditor";

	public void testLocalVarForNative() throws ModelException {
		simpleTest("var <cursor>date = new Date(); //< Date", "JSSourceField:date");
	}
//	public void testLocalVarComment() throws ModelException {
//		simpleTest("var outerType = new this.vj$.MyTest() ;   /<cursor>/< MyTest", null);
//	}
	
	public void testStaticProperty() throws ModelException {
		simpleTest("ax = ax + this.vj$.MyTest.<cursor>myProp;", "JSSourceField:myProp");
	}
	
	public void testNativeTypeProperty() throws ModelException {
		simpleTest("var c = this.foo(x).<cursor>length;", "JSSourceField:length");
	}
	
	public void testType() throws ModelException {
		simpleTest("this.vj$.<cursor>MyTest.abc();", "VjoSourceType:MyTest");
	}
	
	public void testReturnValue() throws ModelException {
		simpleTest("return <cursor>a;", "JSSourceField:a");
	}
	
	public void testMethod() throws ModelException {
		simpleTest("<cursor>abc : function(){", "JSSourceMethod:abc");
	}
	
	public void testProperty() throws ModelException {
		simpleTest("<cursor>myProp: 234, //<int", "JSSourceField:myProp");
	}
	
	public void testLocalVardef() throws ModelException {
		simpleTest("<cursor>a = new this.vj$.MyTest();", "JSSourceField:a");
	}
	
	public void testLocalVar() throws ModelException {
		simpleTest("<cursor>a.values();", "JSSourceField:a");
	}
	
	public void testType2() throws ModelException {
		simpleTest("var a = new this.vj$.<cursor>MyTest();", "VjoSourceType:MyTest");
	}

	public void testTypedef() throws ModelException {
		simpleTest("vjo.ctype('selection.<cursor>MyTest')", "VjoSourceType:MyTest");
	}

	public void testVjo() throws ModelException {
		simpleTest("<cursor>vjo.syserr.print('a');", "VjoSourceType:vjo");
	}
	
	public void testVjoSyserr() throws ModelException {
        simpleTest("vjo.<cursor>syserr.print('a');", "JSSourceField:syserr");
	}
	
	public void testVjoSyserrPrint() throws ModelException {
		simpleTest("vjo.syserr.<cursor>print('a');", "JSSourceMethod:print");
	}

	public void testBug3740() throws ModelException {
		simpleTest("window.<cursor>alert('hi');", "JSSourceMethod:alert");
	} 
	
	public void testBug3742() throws ModelException {
		simpleTest("var d = new <cursor>Date() //< Date", "VjoSourceType:Date");
	}
	
	public void testBug3743() throws ModelException {
		simpleTest("return <cursor>s;", "JSSourceField:s");
	}
	
	public void testBug5831() throws ModelException {
		simpleTest("//> public void main(<cursor>String... arguments)", "VjoSourceType:String");
	}
	
	public void testBug6248a() throws ModelException {
        simpleTest("vjo.<cursor>sysout.println(arr[i]);", "JSSourceField:sysout");
	}
	
	public void testBug6248b() throws ModelException {
		simpleTest("vjo.sysout.<cursor>println(arr[i]);", "JSSourceMethod:println");
	}
	
	public void testBug6248c() throws ModelException {
		simpleTest("vjo.<cursor>syserr.print(arr[i]);", "JSSourceField:syserr");
	}
	
	public void testBug6248d() throws ModelException {
		simpleTest("vjo.syserr.<cursor>print(arr[i]);", "JSSourceMethod:print");
	}
	
	public void testBug3535() throws ModelException {
		simpleTest("//> public <cursor>MyTest bug3535()", "VjoSourceType:MyTest");
	}
	
	public void testBug3744() throws ModelException {
		simpleTest("//> public void bug3744(<cursor>String s)", "VjoSourceType:String");
	}
	
	public void testBug8545() throws ModelException {
		vjoTest("<cursor>constructs_1_0_Long_ovld", "JSSourceMethod:constructs_1_0_Long_ovld");
	}
	
	// add by patrick
//	public void testGlobalProp()throws ModelException{
//		simpleTest("var a = global<cursor>Prop + \'a\';","JSSourceField:globalProp");
//	}
//	
//	public void testGlobalFun()throws ModelException{
//		simpleTest("var a = global<cursor>Fun(\'a\');","JSSourceMethod:globalFun");
//	}
	// end add
	
	protected void simpleTest(String sentence, String expectResult) throws ModelException {
		String js = "selection/MyTest.js";
		
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			String typeName = CodeassistUtils.getClassName((IFile) module
					.getResource());
			IJstType jstType = TypeSpaceMgr.findType(module.getScriptProject()
					.getElementName(), typeName);
			basicTestOnSelect(module, jstType, sentence, expectResult);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}		
	}
	
	protected void vjoTest(String sentence, String expectResult) throws ModelException {
		String js = "vjo/java/lang/Long.js";
		
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixtureVjoLib(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectNameVjo(), "util", new Path(js));

			String typeName = CodeassistUtils.getClassName((IFile) module
					.getResource());
			IJstType jstType = TypeSpaceMgr.findType(module.getScriptProject()
					.getElementName(), typeName);
			basicTestOnSelect(module, jstType, sentence, expectResult);
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}		
	}
	
	protected void basicTestOnSelect(IJSSourceModule module, IJstType type,
			String sentence, String expectResult) throws ModelException {

		int position = getOffset(module, sentence);
		
		assertNotSame("Invalid file content, cant find position", -1, position);

		VjoSelectionEngine c = new VjoSelectionEngine();
		
		IModelElement[] newResults = c.select((ISourceModule) module, position,
				position);
		IModelElement newResult = null;
		
		if (newResults!=null && newResults.length>0) {
			newResult = newResults[0];
			if(newResults.length>1){
				newResult = newResults[1];
			}
			
		}
		
		if (expectResult == null ) {
			assertNull("When cursor is in " + sentence + ", element " + getName(newResult) 
					+ " should not be selected!", newResult);
			return;
		}
		
		assertNotNull("When cursor is in " + sentence + " element " + expectResult
					+ " should be selected!", newResult);
		
        assertEquals(expectResult, getName(newResult));

	}

	private int getOffset(IJSSourceModule module, String sentence)
			throws ModelException {
		int offset = sentence.indexOf("<cursor>");
		assertNotSame("invalid sentence, can't find <cursor>  -- " + sentence,
				-1, offset);

		String realSentence = sentence.replace("<cursor>", "");

		int position = module.getSource().indexOf(realSentence);

		assertNotSame("invalid sentence, can't find in js file" + realSentence,
				-1, position);

		int wholeOffset = position + offset;
		return wholeOffset;
	}

	private String getName(IModelElement element) {
		if (element == null) {
			return null;
		}
		return element.getClass().getSimpleName() + ":"
				+ element.getElementName();
	}

	private String getName(Object node) {
		IJstNode jstNode = (IJstNode) node;
		String simpleName = jstNode.getClass().getSimpleName();

		if (jstNode instanceof IJstType)
			return simpleName + ":" + ((IJstType) jstNode).getName();

		if (jstNode instanceof IJstMethod)
			return simpleName + ":" + ((IJstMethod) jstNode).getName();

		if (jstNode instanceof IJstProperty)
			return simpleName + ":" + ((IJstProperty) jstNode).getName();

		if (jstNode instanceof JstVar)
			return simpleName + ":" + ((JstVar) jstNode).getName();

		if (jstNode instanceof JstIdentifier)
			return simpleName + ":" + ((JstIdentifier) jstNode).getName();

		return simpleName;
	}

	protected void getNode(IJSSourceModule module, int position) {
		VjoSourceModule sourceModule = (VjoSourceModule) module;
		IJstType jstType;
		String typeName = CodeassistUtils.getClassName((IFile) sourceModule
				.getResource());
		jstType = TypeSpaceMgr.findType(sourceModule.getScriptProject()
				.getElementName(), typeName);

	}

}
