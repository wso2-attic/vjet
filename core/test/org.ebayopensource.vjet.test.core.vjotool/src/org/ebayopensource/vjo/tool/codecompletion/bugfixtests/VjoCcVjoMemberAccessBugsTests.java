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
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcVjoMemberAccessBugsTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	@Test //Bug2157
	public void testArgumentProposal() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"arg", "arguments"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstBeforePositionInFile("vjo.sysout.println", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5676
	public void testCaseInsensitiveProposal() {
		String js = "BugJsFiles.Bug5676";
		String[] names = new String[] {"STRINGPROP1"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("str", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4747
	public void testWindowPropMethod() {
		String js = "BugJsFiles.GenericCtype";
		IJstType window = getJstType(JstTypeSpaceMgr.JS_BROWSER_GRP, "Window");
		List<String> strList = getSuggestions(window, false);
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("var win = window.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
	}
	
	@Test //Bug5674
	public void testPrivateMemberAccess() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"staticFunc", "validProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("main : function(){", jstType);
		
		excludeProposals(jstType, position, names);
	}
	
	@Test //Bug5674
	public void testNonPrivateMemberAccess() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2", 
				"RADIUS", "staticProp"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("main : function(){", jstType);

		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5674
	public void testNonPrivateMemberAccess1() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validProp1", "compute"};
		String[] exclude = new String[] {"validProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("v.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test //Bug2299
	public void testPropertyType() {
		String js = "BugJsFiles.GenericCtype";
		String name = "undefined";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("undefi", jstType);
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(),content, position);
		List<String> strList = getStringProposals(propList);
		Assert.assertTrue(strList.size() == 1);
		Assert.assertTrue(strList.get(0).equals(name));
	}
	
//	@Test //Bug2673
//	public void testETypeReferences() {
//		String js = "BugJsFiles.GenericCtype";
//		String[] names = new String[] {"staticProp", "staticProp1", 
//				"staticFunc", "FRI", "MON", "SAT", "SUN", "THU", "TUE", "WED"};
//		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
//		int position = lastPositionInFile("this.vj$.WeekDaysEType.", jstType);
//
//		checkProposals(jstType, position, names);
//	}
	
	@Test //Bug2673
	public void testLocalVarAccess() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"localVar1", "localVar2", "localVar3"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("localVar2, localVar3;", jstType);

		checkProposals(jstType, position, names);
	}
	
	@Test //Bug6006
	public void testPropsAlertProposals() {
		String js = "BugJsFiles.Bug6006";
		IJstType string = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
		List<String> strList = getSuggestions(string, false);
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("alert(this.validstaticProp2.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
	}
	
	public void checkProposals(IJstType jstType, int position, String [] names){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		
		VjoCcEngine engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
		
	
		CodeCompletionUtils.printProposal(propList);
		
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
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
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
			} else if (obj instanceof JstVars){
				obj = ((JstVars) obj).getAssignments().get(0).getLHS().toLHSText();
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
