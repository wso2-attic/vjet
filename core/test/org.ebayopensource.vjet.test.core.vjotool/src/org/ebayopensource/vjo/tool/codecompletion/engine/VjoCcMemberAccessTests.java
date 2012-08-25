/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;




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
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Before;
import org.junit.Test;




//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcMemberAccessTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	
	@Test
	public void testInheritedMemAccsProps() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"GenericCtypeExtn", "GenericCtype"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("var v = new this.vj$.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test
	public void testInheritedMemAccsProps1() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2",
				"RADIUS", "staticProp"};
		String[] exclude = new String[] {"staticFunc", "validProp1", 
				"validProp2", "compute"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("main : function(){", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProps2() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validProp1", "compute"};
		String[] exclude = new String[] {"validProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("v.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProps3() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validProp1", "compute"};
		String[] exclude = new String[] {"validProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("genericCtype.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProps4() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2",
				"RADIUS", "staticProp"};
		String[] exclude = new String[] {"staticFunc", "validProp1", 
				"validProp2", "compute"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("this.vj$.GenericCtype.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProps5() {
		String js = "BugJsFiles.GenericCtypeExtn";
		IJstType string = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
		List<String> strList = getSuggestions(string);
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("this.vj$.GenericCtype.staticProp.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
	}
	
	@Test
	public void testInheritedMemAccsProps6() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2",
				"RADIUS", "staticProp"};
		String[] exclude = new String[] {"staticFunc", "validProp1", 
				"validProp2", "compute"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("var str = this.vj$.GenericCtype.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProps7() {
		String js = "BugJsFiles.GenericCtypeExtn";
		IJstType string = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
		List<String> strList = getSuggestions(string);
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("var str = this.vj$.GenericCtype.staticProp.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
	}
	
	@Test
	public void testInheritedMemAccsProtos1() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2",
				"RADIUS", "staticProp", "validProp1", "compute"};
		String[] exclude = new String[] {"staticFunc", "validProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("constructs:function(){", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProtos2() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validProp1", "compute"};
		String[] exclude = new String[] {"validProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("v1.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProtos3() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validProp1", "compute"};
		String[] exclude = new String[] {"validProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("genericCtype1.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProtos4() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2",
				"RADIUS", "staticProp"};
		String[] exclude = new String[] {"staticFunc", "validProp1", 
				"validProp2", "compute"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("this.vj$.GenericCtype.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProtos5() {
		String js = "BugJsFiles.GenericCtypeExtn";
		IJstType string = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
		List<String> strList = getSuggestions(string);
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("this.vj$.GenericCtype.staticProp.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
	}
	
	@Test
	public void testInheritedMemAccsProtos6() {
		String js = "BugJsFiles.GenericCtypeExtn";
		String[] names = new String[] {"validStaticProp1", "validstaticProp2",
				"RADIUS", "staticProp"};
		String[] exclude = new String[] {"staticFunc", "validProp1", 
				"validProp2", "compute"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("var str1 = this.vj$.GenericCtype.", jstType);
		
		checkProposals(jstType, position, names);
		excludeProposals(jstType, position, exclude);
	}
	
	@Test
	public void testInheritedMemAccsProtos7() {
		String js = "BugJsFiles.GenericCtypeExtn";
		IJstType string = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
		List<String> strList = getSuggestions(string);
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("var str1 = this.vj$.GenericCtype.staticProp.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
	}
	
	@Test
	//@Description("Code proposal for protected modifier")
	public void testInheritedMemAccsProtos8() {
		String js = "BugJsFiles.Bug6242";
		String[] names = {"staticProp1","staticProp2","staticProp4"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("var j = this.vj$.Bug6242_Super.", jstType);
		
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
	
	private List<String> getSuggestions(IJstType type) {
		List<String> sugg = new ArrayList<String>();
		for(IJstMethod m:type.getMethods(false)){
			if (!(m.getOriginalName().startsWith("_")
					|| m.getOriginalName().startsWith("__")))
				sugg.add(m.getOriginalName());
		}
		for(IJstProperty p:type.getProperties(false)){
			if (!(p.getName().getName().startsWith("_")
					|| p.getName().getName().startsWith("__")))
				sugg.add(p.getName().getName());
		}
		return sugg;
	}
}
