/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;


import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Before;
import org.junit.Test;




//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcBugfixTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	
	
	@Test //Bug4683
	public void testMixinProposalAfterPkg() {
		String js = "nonStaticPropAdvisor.ProtosAdvisorTest1";
		String[] names = new String[] {"nonStaticPropAdvisor.ProtosAdvisorMType"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("mixin('nonStaticPropAdvisor.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4681
	public void testMixinEmptyBracket() {
		String js = "BugJsFiles.Bug4681";
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("mixin(", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4712
	public void testMixinEmptySquareBracket() {
		String js = "BugJsFiles.Bug4712";
		String[] names = new String[] {"BugJsFiles", "ETypeJs", "handler", 
				"Integration", "aliasProposal", "constructorAdvisor", "engine", 
				"nonStaticPropAdvisor", "overrideAdvisor", "packageProposalAdvisor", 
				"parent", "staticPropAdvisor", "variableAdvisor", "ProtosAdvisorMType", 
				"ProtosAdvisorMType", "StaticPropAdvisorMType", "StaticPropAdvisorMType", 
				"TestMType"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("mixin(['", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4639
	public void testSingleQuoteNeedsProposals() {
		String js = "BugJsFiles.Bug4639";
		String[] names = new String[] {"BugJsFiles", 
				"Bug4639Extn", "Bug4712", "Bug4681"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".needs('Bug", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug8457
	//@Description("Test code proposal for empty square brackets in needs section")
	public void testNeedsEmptySquareBracket() {
		String js = "BugJsFiles.NeedsProposal";
		String[] names = new String[] {"BugJsFiles", 
				"Bug4639Extn", "Bug4712", "Bug4681"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".needs(['", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4639
	public void testSingleQuoteNeedsProposalsExtn() {
		String js = "BugJsFiles.Bug4639Extn";
		String[] names = new String[] {"BugJsFiles", "BugJsFiles", "ETypeJs", 
				"handler", "aliasProposal", "constructorAdvisor", "engine", 
				"nonStaticPropAdvisor", "overrideAdvisor", "packageProposalAdvisor", 
				"parent", "staticPropAdvisor", "variableAdvisor"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".needs('", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4189
	public void testBug4189() {
		String js = "BugJsFiles.Bug4189";
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("}", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug4933
	public void testStringFunctions() {
		String js = "Integration.StringTest";
		IJstType string = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
		List<String> strList = getSuggestions(string);
		strList.add("toString");
		String[] names = new String[] {};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("s.", jstType);
		
		checkProposals(jstType, position, strList.toArray(names));
	}
	
	@Test //Bug4073
	public void testBug4073() {
		String js = "BugJsFiles.Bug4073";
		String[] names = new String[] {"staticPropAdvisor.StaticPropAdvisorTest", 
				"staticPropAdvisor.StaticPropAdvisorTest1", "sAlias1", "sAlias2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("constructs : function(){s", jstType);
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
		List<String> strList = getStringProposals(propList);
		
		for (String name : names){
			Assert.assertTrue("proposal : " + name + 
					" is not included in proposal list" + 
					strList, strList.contains(name));
			
			Assert.assertTrue("Multiple packages shown for package : " + name
					+ "\n Proposals : " + strList, 
					strList.indexOf(name) == strList.lastIndexOf(name));
		}
	}
	
//	@Test //Bug5348
//	@Ignore("determine issue with _defineGetter__")
//	public void testBug5348() {
//		String js = "BugJsFiles.GenericCtype";
//		IJstType date = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Date");
//		List<String> strList = getSuggestions(date);
//		strList.addAll(getSuggestions(getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Object")));
//		String[] names = new String[] {};
//		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
//		int position = lastPositionInFile("date.", jstType);
//		
//		checkProposals(jstType, position, strList.toArray(names));
//	}
	
//	@Test //Bug5502
//	@Ignore("determine issue with _defineGetter__")
//	public void testBug5502() {
//		String js = "BugJsFiles.GenericCtype";
//		IJstType date = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
//		List<String> strList = getSuggestions(date);
//		strList.addAll(getSuggestions(getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "Object")));
//		String[] names = new String[] {};
//		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
//		int position = lastPositionInFile("this.validProp2.", jstType);
//		
//		checkProposals(jstType, position, strList.toArray(names));
//	}
	
	@Test //Bug5502
	public void testBug5502_1() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"validProp1","validProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("this.vj$.GenericCtype.validstaticProp2.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug5432
	public void testBug5432() {
		String js = "BugJsFiles.GenericCtype";
		String[] names = new String[] {"RADIUS","validStaticProp1","validstaticProp2"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("return this.vj$.GenericCtype.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	@Test //Bug7895
	//@Description("Code proposal for variables after . and before semicolon")
	public void testBug7895() {
		String js = "BugJsFiles.Bug7895";
		String[] names = new String[] {"valueOf","getAttribute","eval"};
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("var y = x.", jstType);
		
		checkProposals(jstType, position, names);
	}
	
	public void checkProposals(IJstType jstType, int position, String [] names){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
		List<String> strList = getStringProposals(propList);
		List<String> simpleStrList = getStringProposals(propList, true);
		
		for (String name : names){
			Assert.assertTrue("proposal : " + name + 
					" is not included in proposal list" + 
					strList, strList.contains(name) || simpleStrList.contains(name));
		}
	}
	
	private static List<String> getStringProposals(List<IVjoCcProposalData> dataList){
		return getStringProposals(dataList, false);
	}
	private static List<String> getStringProposals(List<IVjoCcProposalData> dataList, boolean simpleName){
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
				if (simpleName) {
					obj = ((IJstType) obj).getSimpleName();
				} else {
					obj = ((IJstType) obj).getName();
				}
			} else if (obj instanceof JstIdentifier){
				obj = ((JstIdentifier) obj).getName();
			} else if (obj instanceof JstPackage){
				obj = ((JstPackage) obj).getName();
			}
			propList.add((String)obj);
		}
		return propList;
	}
	
	private List<String> getSuggestions(IJstType type) {
		List<String> sugg = new ArrayList<String>();
		for(IJstMethod m:type.getMethods(false)){
				sugg.add(m.getOriginalName());
		}
		for(IJstProperty p:type.getProperties(false)){
				sugg.add(p.getName().getName());
		}
		return sugg;
	}
	
	/**
	 * See bug 4400
	 * @param type
	 * @return
	 */
	@Test
	public void testProposalAfterEndtype() {

		String js = "aliasProposal.AliasTest1";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("endType()", jstType);
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
		assertTrue("Should not propose when cursor after endType()", propList.isEmpty());
		
		position = position + 1;
		
		propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
		assertTrue("Should not propose when cursor after endType();", propList.isEmpty());
		
	}
	
	/**
	 * Test the offset of mtdInvocationExpr from ScriptUnit
	 * Bug5663
	 */
	@Test
	public void testOffset() {
		String js = "aliasProposal.AliasTest1";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("endT", jstType);
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		VjoCcCtx propList = engine.genCcContext(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
		MtdInvocationExpr mtd = propList.getSMtdInvo();
		JstSource source = mtd.getSource();
		int offset = source.getEndOffSet();
		assertTrue("The MtdInvocationExpr's source (0 - " + offset +") should contain the cursor position:" + position , offset > position);
	}
	
	/**
	 * Test the offset of mtdInvocationExpr from ScriptUnit
	 * Bug5673
	 */
	@Test
	public void testTokenForThis() {
		String js = "aliasProposal.AliasTest1";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("endT", jstType);
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		VjoCcCtx propList = engine.genCcContext(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(), content, position);
		MtdInvocationExpr mtd = propList.getSMtdInvo();
		JstSource source = mtd.getSource();
		int offset = source.getEndOffSet();
		assertTrue("The MtdInvocationExpr's source (0 - " + offset +") should contain the cursor position:" + position , offset > position);
	}
}
