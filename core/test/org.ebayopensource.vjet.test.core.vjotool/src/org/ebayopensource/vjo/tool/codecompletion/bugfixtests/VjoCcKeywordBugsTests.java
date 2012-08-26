/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.bugfixtests;




import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcKeywordBugsTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	
	@Test //Bug596
	public void testVjoProposal() {
		String js = "BugJsFiles.BuyerTransactionAlert";
		String[] names = new String[] {"vjo"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("this.sId = pId;vj", jstType);

		checkProposals(jstType, position, names);
	}
	
	@Test //Bug596
	public void testThisProposal() {
		IJstType jstType = getJstType(
				CodeCompletionUtil.GROUP_NAME, "BugJsFiles.Bug2323");
		int position = firstPositionInFile("this.", jstType);
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(),content, position);
		Assert.assertTrue(propList.size() > 0);
		
		IJstType jstTypeExtn = getJstType(
				CodeCompletionUtil.GROUP_NAME, "BugJsFiles.Bug2323Extn");
		int positionExtn = firstPositionInFile("this.", jstTypeExtn);
		URL url1 = getSourceUrl(jstTypeExtn.getName(), ".js");
		String content1 = VjoParser.getContent(url1);
		List<IVjoCcProposalData> propListExtn = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstTypeExtn.getName(),content1, positionExtn);
		Assert.assertTrue(propListExtn.size() > 0);
		
		Assert.assertTrue(propListExtn.size() == propList.size());
		Assert.assertTrue(getStringProposals(propList).
				containsAll(getStringProposals(propListExtn)));
		
	}
	
	public void checkProposals(IJstType jstType, int position, String [] names){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
		List<String> strList = getStringProposals(propList);
		
		for (String name : names){
			Assert.assertTrue("proposal : " + name + 
					" is not included in proposal list" + 
					strList, strList.contains(name));
		}
	}
	
	public void excludeProposals(IJstType jstType, int position, String [] names){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(),content, position);
		List<String> strList = getStringProposals(propList);
		
		for (String name : names){
			Assert.assertFalse("proposal : " + name + 
					" should have not been included in proposal list" + 
					strList, strList.contains(name));
		}
	}
	
	private static List<String> getStringProposals(List<IVjoCcProposalData> dataList){
		List<String> propList = new ArrayList<String>();
		for (IVjoCcProposalData data : dataList){
			Object obj = data.getData();
			if (obj instanceof IJstMethod){
				obj = ((IJstMethod) obj).getName().getName();
			} else if (obj instanceof IJstProperty){
				obj = ((IJstProperty) obj).getName().getName();
			} else if (obj instanceof JstArg){
				obj = ((JstArg) obj).getName();
			} else if (obj instanceof IJstType){
				obj = ((IJstType) obj).getSimpleName();
			} else if (obj instanceof JstIdentifier){
				obj = ((JstIdentifier) obj).getName();
			} else if (obj instanceof JstPackage){
				obj = ((JstPackage) obj).getName();
			}
			propList.add((String)obj);
		}
		return propList;
	}
}
