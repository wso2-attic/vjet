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
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcVjoTypeBugsTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	
	@Test //Bug2757
	public void testProposalsWithEndType() {
		String js = "BugJsFiles.Bug2757";
		String[] names = new String[] {"inherits", "satisfies", "inits", 
				"endType", "mixin", "props", "protos", "needs"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("public\r\n.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5953
	public void testVjoSysoutSyserrProposals() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"sysout", "syserr"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastBeforePositionInFile("syserr.print", jstType)+1;
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5953
	public void testVjoSysoutPrintln() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"println", "print"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("vjo.sysout.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5953
	public void testVjoSyserrPrintln() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"println", "print"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("vjo.syserr.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug484
	public void testNeedsAliasProposal() {
		String js = "BugJsFiles.Bug484";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2", 
				"RADIUS", "staticProp"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("this.vj$.myAlias.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug484
	public void testNeedsAliasProposal1() {
		String js = "BugJsFiles.Bug484";
		String[] names = new String[] {"validProp1", "compute"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("v.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug484
	public void testNeedsAliasProposal2() {
		String js = "BugJsFiles.Bug484extn";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2", 
				"RADIUS", "staticProp"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("this.vj$.myAlias.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug484
	public void testNeedsAliasProposalExtn3() {
		String js = "BugJsFiles.Bug484extn";
		String[] names = new String[] {"validProp1", "compute"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("vv.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5007
	public void testETypeProposals() {
		String js = "BugJsFiles.WeekDaysEType";
		String[] names = new String[] {"wkday", "dispName", "weekday", 
				"displayName", "isWeekday", "getDisplayName"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstBeforePositionInFile("this.displayName", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5007
	public void testETypeProposals1() {
		String js = "BugJsFiles.WeekDaysEType";
		String[] names = new String[] {"weekday", "displayName", "isWeekday", 
				"getDisplayName"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstBeforePositionInFile("displayName = dispName", jstType) + 1;
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5007
	public void testETypeProposals2() {
		String js = "BugJsFiles.WeekDaysEType";
		String[] names = new String[] {"staticProp", "staticProp1", "staticFunc"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("this.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug3960
	public void testVjoProposal() {
		String js = "BugJsFiles.Bug3960";
		String[] names = new String[] {"inherits", "satisfies", "inits", 
				"endType", "mixin", "props", "protos", "needs"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug3960
	public void testVjoProposal1() {
		String js = "BugJsFiles.Bug3960extn";
		String[] names = new String[] {"inherits", "satisfies", "inits", 
				"endType", "mixin", "props", "protos", "needs"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5776
	public void testTypeProposal() {
		String js = "BugJsFiles.Bug4073";
		String[] names = new String[] {"Bug4073"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("constructs : function(){", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5776
	public void testETypeProposal() {
		String js = "BugJsFiles.WeekDaysEType";
		String[] names = new String[] {"needs", "satisfies", "props", 
				"protos", "values", "endType"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstBeforePositionInFile("props({", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4750
	public void testInitsProposal() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2", 
				"RADIUS", "staticProp"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("inits( function() {", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug6097
	public void testInitsProposal1() {
		String js = "BugJsFiles.GenericCtype";
		String [] excludes = new String[] {"ctype", "mtype", "itype", "otype", 
				"etype", "NEEDS_IMPL", "needs", "mixin", "protos", "props", 
				"satisfies", "expects", "endType", "inits"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("inits( function() {", jstType);
		
		excludeProposals(jstType, position, excludes);
	}
	
	@Test //535
	public void testNeedsProposals() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"GenericCtypeExtn", "WeekDaysEType"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".needs('BugJsFiles.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //650
	public void testNeedsProposals1() {
		String js = "engine.javaone.AnimalKingdom";
		String[] names = new String[] {"engine.javaone"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".needs(['engine.java", jstType);
		
		checkProposals(jstType, position, names);
		names = new String[] {"AnimalLab", "Cat", 
				"IMate", "Liger", "Lion", "Tiger"};
		position = lastPositionInFile(".needs(['engine.javaone.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //6135
	public void testNeedsProposals2() {
		String js = "engine.javaone.AnimalKingdom";
		String[] names = new String[] {"AnimalKingdom"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".needs(['engine.java", jstType);
		
		excludeProposals(jstType, position, names);
	}
	
	@Test //6188
	public void testITypePropsFuncProposals() {
		String js = "BugJsFiles.Bug6188";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("testFunc", jstType);
		
		noProposals(jstType, position);
	}
	
	@Test //6188
	public void testITypePropsFuncProposals1() {
		String js = "BugJsFiles.Bug6188Extn";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("test", jstType);
		
		functionProposals(jstType, position);
	}
	
	@Test //6188
	public void testITypePropsFuncProposals2() {
		String js = "BugJsFiles.Bug6188Extn";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("func1 ", jstType);
		
		functionProposals(jstType, position);
	}
	
	@Test //6188
	public void testITypePropsFuncProposals3() {
		String js = "BugJsFiles.Bug6188Extn1";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("func1", jstType);
		
		functionProposals(jstType, position);
	}
	
	@Test //6188
	public void testITypePropsFuncProposals4() {
		String js = "BugJsFiles.Bug6188Extn1";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("func2", jstType);
		
		functionProposals(jstType, position);
	}
	
	@Test //6245
	public void testOrdinalProposals() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"ordinal", "name"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("var ordinalVal = this.vj$.WeekDaysEType.SUN.", jstType);
		
		checkProposals(jstType, position, names);
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
	
	public void noProposals(IJstType jstType, int position){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(),content, position);
		List<String> strList = getStringProposals(propList);
		
		Assert.assertTrue(" No proposals should have been shown. " 
				+ "Got these proposals : " + strList, strList.size() == 0);
	}
	
	public void functionProposals(IJstType jstType, int position){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		String advisor = "org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcFunctionGenProposalAdvisor";
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(),content, position);
		Assert.assertTrue(" Only one proposals should have been shown. " 
				+ "Got these proposals : " + propList, propList.size() == 1);
		Assert.assertTrue(" Got wrong advisor, Should have been " +
				"VjoCcFunctionGenProposalAdvisor, but got : " + 
				propList.get(0).getAdvisor(), 
				propList.get(0).getAdvisor().equalsIgnoreCase(advisor));		
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
