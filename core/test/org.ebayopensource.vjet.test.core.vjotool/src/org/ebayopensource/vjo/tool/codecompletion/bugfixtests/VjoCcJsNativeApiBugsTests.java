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
public class VjoCcJsNativeApiBugsTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	
	@Test //Bug3107
	public void testNodeListProposals() {
		String js = "BugJsFiles.GenericCtype";
		IJstType nodeList = getJstType(JstTypeSpaceMgr.JS_BROWSER_GRP, "NodeList");
		IJstType objectList = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Object");
		List<String> strList = getSuggestions(nodeList, false);
		strList.addAll(getSuggestions(objectList, false));
		String[] names = new String[] {};
		String[] exclude = new String [] {"if","for","try","catch","switch"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("nodelistproposal.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
		excludeProposals(jstType, position, exclude);
	}
	
	@Test //Bug3107
	public void testNodeProposals() {
		String js = "BugJsFiles.GenericCtype";
		IJstType nodeList = getJstType(JstTypeSpaceMgr.JS_BROWSER_GRP, "Node");
		IJstType objectList = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Object");
		List<String> strList = getSuggestions(nodeList, false);
		strList.addAll(getSuggestions(objectList, false));
		String[] names = new String[] {};
		String[] exclude = new String [] {"if","for","try","catch","switch"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("nodeproposal.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
		excludeProposals(jstType, position, exclude);
	}
	
	@Test //Bug3134
	public void testWindowProposals() {
		String js = "BugJsFiles.GenericCtype";
		IJstType window = getJstType(JstTypeSpaceMgr.JS_BROWSER_GRP, "Window");
		IJstType objectList = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Object");
		List<String> strList = getSuggestions(window, false);
		String[] exclude = new String [] {"if","for","try","catch","switch"};
		strList.addAll(getSuggestions(objectList, false));
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("window.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
		excludeProposals(jstType, position, exclude);
	}
	
	@Test //Bug3134
	public void testDocumentProposals() {
		String js = "BugJsFiles.GenericCtype";
		IJstType document = getJstType(JstTypeSpaceMgr.JS_BROWSER_GRP, "HTMLDocument");
		IJstType objectList = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Object");
		List<String> strList = getSuggestions(document, false);
		strList.addAll(getSuggestions(objectList, false));
		String[] names = new String[] {};
		String[] exclude = new String [] {"if","for","try","catch","switch"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("document.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
		excludeProposals(jstType, position, exclude);
	}
	
	@Test //Bug5078
	public void testMathProposals() {
		String js = "BugJsFiles.GenericCtype";
		IJstType math = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Math");
		IJstType objectList = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Object");
		List<String> strList = getSuggestions(math, true);
		String[] names = new String[] {};
		String[] exclude = new String [] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("Math.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
		strList.clear();
		strList.addAll(getSuggestions(objectList, true));
		excludeProposals(jstType, position, strList.toArray(exclude));
	}
	
	@Test //Bug2600
	public void testArrayProposals() {
		String js = "partials.ArrayFunc";
		IJstType array = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Array");
		IJstType objectList = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Object");
		List<String> strList = getSuggestions(array, false);
		strList.addAll(getSuggestions(objectList, false));
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("a.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
	}
	
	@Test //Bug7628
	//@Description("Test if the Function native api has a default constructor")
	public void testFunctionConstructors() {
		IJstType func = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Function");
		IJstMethod method = func.getConstructor();
		boolean found = false;
		for (IJstMethod m : method.getOverloaded()){
			if (m.getArgs().size() == 0){
				found = true;
				break;
			}
		}
		Assert.assertTrue("Did not found the default constructor in Function" +
				" API", found);
	}
	
	public void checkProposals(IJstType jstType, int position, String [] names){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(),content, position);
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
	
	private List<String> getSuggestions(IJstType type, boolean isStatic) {
		List<String> sugg = new ArrayList<String>();
		for(IJstMethod m:type.getMethods(isStatic)){
			if (!(m.getOriginalName().startsWith("_")
					|| m.getOriginalName().startsWith("__")))
				sugg.add(m.getOriginalName());
		}
		for(IJstProperty p:type.getProperties(isStatic)){
			if (!(p.getName().getName().startsWith("_")
					|| p.getName().getName().startsWith("__")))
				sugg.add(p.getName().getName());
		}
		return sugg;
	}

}
