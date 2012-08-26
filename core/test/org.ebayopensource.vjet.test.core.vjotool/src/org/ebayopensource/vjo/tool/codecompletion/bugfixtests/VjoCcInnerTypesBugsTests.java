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
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
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
public class VjoCcInnerTypesBugsTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	
	@Test //Bug4704
	public void testInnerType() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"NEEDS_IMPL", "ctype", "itype", "mtype", "etype"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("d:vjo.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4704
	public void testInnerType1() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"vj$", "base", "d", "innerProp"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastBeforePositionInFile("innerProp.big()", jstType)+1;
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4704
	public void testInnerType2() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"this", "d", "innerProp"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastBeforePositionInFile("this.innerProp.big()", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug6015
	public void testInnerType3() {
		String js = "BugJsFiles.GenericCtype";
		IJstType str = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
		List<String> strList = getInstanceSuggestions(str);
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("this.innerProp.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
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
			} else if (obj instanceof JstVars){
				obj = ((JstVars) obj).getAssignments().get(0).getLHS().toLHSText();
			}
			propList.add((String)obj);
		}
		return propList;
	}
	
	private List<String> getInstanceSuggestions(IJstType type) {
		List<String> sugg = new ArrayList<String>();
		for(IJstMethod m:type.getMethods()){
			if (!(m.getOriginalName().startsWith("_")
					|| m.getOriginalName().startsWith("__")
					|| m.isStatic()))
				sugg.add(m.getOriginalName());
		}
		for(IJstProperty p:type.getProperties()){
			if (!(p.getName().getName().startsWith("_")
					|| p.getName().getName().startsWith("__")
					|| p.isStatic()))
				sugg.add(p.getName().getName());
		}
		return sugg;
	}
}
