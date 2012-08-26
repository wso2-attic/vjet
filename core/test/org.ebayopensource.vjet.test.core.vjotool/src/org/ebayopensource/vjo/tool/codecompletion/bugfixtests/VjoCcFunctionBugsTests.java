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
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;




//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcFunctionBugsTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	@Test
	//@Description("Bug 2192 : Only arguments defined in actual function should proposed.")
	public void testActualArugmentProposals() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"string", "object", "date"};
		String[] exclude = new String[] {"str", "obj", "d"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("func1 : function(string,object,date){", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	/**
	 * TODO Put the proper logic to check the function arguments
	 */
	@Test //Bug4918
	public void testOverloadedProposalsProtos() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"compute", "compute", "compute"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastBeforePositionInFile("vjo.sysout.println", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	/**
	 * TODO Put the proper logic to check the function arguments
	 */
//	@Test //Bug4918
//	@Ignore
//	public void testOverloadedProposalsProps() {
//		String js = "presenter.FunctionPosition";
//		String[] names = new String[] {"compute", "compute", "compute"};
//		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
//		int position = firstBeforePositionInFile("this.staticFunc1(", jstType);
//		
//		checkProposals(jstType, position, names);
//	}
	
	@Test //Bug5029
	public void testFQNAccessCTypeProps() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2", 
				"RADIUS", "staticProp", "staticFunc"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("BugJsFiles.GenericCtype.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5029
	public void testFQNAccessCTypeProtos() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2", 
				"RADIUS", "staticProp", "staticFunc"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastBeforePositionInFile("BugJsFiles.GenericCtype.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5029
	public void testFQNAccessETypeProps() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("BugJsFiles.WeekDaysEType.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5029
	public void testFQNAccessETypeProtos() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("BugJsFiles.WeekDaysEType.", jstType);
		
		checkProposals(jstType, position, names);
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
			} else if (obj instanceof JstVars){
				obj = ((JstVars) obj).getAssignments().get(0).getLHS().toLHSText();
			}
			propList.add((String)obj);
		}
		return propList;
	}
}
