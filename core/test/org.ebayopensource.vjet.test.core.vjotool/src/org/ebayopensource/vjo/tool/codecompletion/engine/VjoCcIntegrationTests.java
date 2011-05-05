/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;
import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P1;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.net.URL;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.ModuleInfo;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;

@Category({P1,FAST,UNIT})
@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcIntegrationTests extends VjoCcBaseTest {
	
	private VjoCcEngine engine;
	
	@BeforeClass
	protected void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	

	@Test
	public void testBasicProposals() {
		IJstType calledType = getJstType(CodeCompletionUtil.GROUP_NAME,
				"nonStaticPropAdvisor.ProtosAdvisorTest1");
		int i = firstBeforePositionInFile("alert", calledType);
		URL url = getSourceUrl(calledType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propoList = engine.complete(CodeCompletionUtil.GROUP_NAME, 
				"nonStaticPropAdvisor.ProtosAdvisorTest1", content, i-5);
	}
	
	@Test
	public void testBasicProposals1() {
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, "nonStaticPropAdvisor.ProtosAdvisorTest1");
		
		int i = firstBeforePositionInFile("alert", jstType);
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(CodeCompletionUtil.GROUP_NAME, 
				"nonStaticPropAdvisor.ProtosAdvisorTest1", content, i);
		
//		System.out.println(propList);
		
	}
	
	@Test
	@Ignore("this test was not running due to NPE in framework")
	public void testIShoppingProposals() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/IShoppingTest.js";
		testUtil.testJs = "engine.IShopping";
		testUtil.xmlFile = "engine/Shopping_TestData.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	public void testShoppingCategoryProposals() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/ShoppingCategoryTest.js";
		testUtil.testJs = "engine.ShoppingCategory";
		testUtil.xmlFile = "engine/Shopping_TestData.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	public void testShoppingItemProposals() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/ShoppingItemTest.js";
		testUtil.testJs = "engine.ShoppingItem";
		testUtil.xmlFile = "engine/Shopping_TestData.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	@Ignore
	public void testGenericShoppingProposals() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/GenericShoppingTest.js";
		testUtil.testJs = "engine.GenericShopping";
		testUtil.xmlFile = "engine/Shopping_TestData.xml";
		
		testUtil.testCcProposals();
	}
	
	@Test
	@Ignore("this test was not running due to NPE in framework")
	public void testShoppingProposals() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/ShoppingTest.js";
		testUtil.testJs = "engine.Shopping";
		testUtil.xmlFile = "engine/Shopping_TestData.xml";
		
		testUtil.testCcProposals();
	}

	@Test
	@Ignore //TODO huzhou@ebay.com find the source back
	public void testLocalVarAsFunctionProposals() {
		VjoCcEngineTestUtil testUtil = new VjoCcEngineTestUtil();
		testUtil.sampleJs = "engine/LocalVarAsFunctionTest.js";
		testUtil.testJs = "engine.LocalVarAsFunction";
		testUtil.xmlFile = "engine/LocalVarAsFunction_TestData.xml";
		
		testUtil.testCcProposals();
	}
}
