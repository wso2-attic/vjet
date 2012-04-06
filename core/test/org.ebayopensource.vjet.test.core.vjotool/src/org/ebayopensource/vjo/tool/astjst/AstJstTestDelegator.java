/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.astjst;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.vjo.tool.astjst.AstJstTestUtil.AstJstBean;
import org.ebayopensource.vjo.tool.astjst.AstJstTestUtil.AstJstInput;
import org.ebayopensource.vjo.tool.astjst.TestInputUtil.JxPathInput;
import org.ebayopensource.vjo.tool.astjst.TestInputUtil.TestInputData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;

public class AstJstTestDelegator {
	
	public void testAll() throws URISyntaxException {
		// Get the data from xml file
		TestInputUtil util = new TestInputUtil();
		List<File> fileInputList = util.getXmlDataFileList();
		
		for (File file : fileInputList) {
//			util.setFile(file);
			List<TestInputData> dataList = util.getAllTestInputData();
			for (TestInputData data : dataList) {
				testNodeList(data);
			}
		}
	}
	
	public void testAll(String xmlFile) throws URISyntaxException {
		// Get the data from xml file
		TestInputUtil util = new TestInputUtil();
		URL u = util.getXmlDataFile(xmlFile);
		util.setURL(u);
		List<TestInputData> dataList = util.getAllTestInputData();
		for (TestInputData data : dataList) {
			testNodeList(data);
		}
	}
	
	public void testAll(String xmlFile, int testCase) throws URISyntaxException {
		// Get the data from xml file
		TestInputUtil util = new TestInputUtil();
		URL u = util.getXmlDataFile(xmlFile);
		util.setURL(u);
		TestInputData data = util.getTestInputData(testCase);
		testNodeList(data);
	}
	
	
	public List<IJstNode> testNodeList(TestInputData data) throws URISyntaxException {
		
		List<IJstNode> testNodes = null;
		AstJstTestUtil astUtil = new AstJstTestUtil();
		
		// Create and load (into type-space) the test file template
		URL testFile = astUtil.getTestFile(data);
		if(testFile == null){
			return new ArrayList<IJstNode>();
		}
		if (testFile.getFile().contains("SKIPIT")){
			return new ArrayList<IJstNode>();
		}

		// get Ast and Jst info
		
		//IJstParseController control = CodeCompletionUtil.getJstParseController();
		IJstParseController control = VjoCcBaseTest.getJstParseController();
		AstJstBean bean = astUtil.getAstJstBean(testFile, control);
		control.resolve(bean.getJst());
		
		List<JxPathInput> xpathList = data.getJXPathInput();
		for (JxPathInput jxPathInput : xpathList) {
//			System.out.println("Js File Name:: "+testFile.getName());
//			System.out.println("XPath::\t"+jxPathInput.pathname);

			// consume the xpath style input
			List<AstJstInput> inputList = astUtil.getAstJstInput(jxPathInput.getPathName());
			
			// get all the expected nodes
			testNodes = astUtil.getExpectedNodes(bean.getJst(),inputList);
			
			// Put assertions
			astUtil.testAssertions(data.testNumber,testNodes, bean.getJst(), jxPathInput);
			
			// test custom assertions
			astUtil.testCustomAssertions(testNodes, bean.getJst(), jxPathInput.getClassName());
//			System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::");
		}
		
		return testNodes;
	}

	
}
