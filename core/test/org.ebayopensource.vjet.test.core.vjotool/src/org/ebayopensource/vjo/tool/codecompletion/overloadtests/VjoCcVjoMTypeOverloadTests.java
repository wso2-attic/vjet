/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.overloadtests;




import java.util.Arrays;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcVjoMTypeOverloadTests extends VjoCcBaseTest{
	
	private VjoCcOverloadUtil overloadUtil;
	
	@Before
	public void setUp() throws Exception {
		overloadUtil = new VjoCcOverloadUtil();
	}
	@Test
	public void testBaseNonStatOverloadProposal(){
		// JS Type name
		String js = "engine.overload.MBase";
		
		// Function to test for overloading
		String funcName = "mpubCompute";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("mbase.", jstType);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	@Test
	public void testBaseStatOverloadProposal(){
		
		String js = "engine.overload.MBase";
		String funcName = "mpubStaticCompute";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("this.", jstType);
		
		// null to be included in case of no argument methods
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	@Test
	public void testChildNeedsCTypeOverloadProposal(){
		// JS Type name
		String js = "engine.overload.MChild";
		
		// Function to test for overloading
		String funcName = "pubCompute";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("var pubVar = cbase.", jstType);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	@Test
	public void testChildNeedsETypeOverloadProposal(){
		// JS Type name
		String js = "engine.overload.MChild";
		
		// Function to test for overloading
		String funcName = "pubCompute";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("ebase.", jstType);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}

}
