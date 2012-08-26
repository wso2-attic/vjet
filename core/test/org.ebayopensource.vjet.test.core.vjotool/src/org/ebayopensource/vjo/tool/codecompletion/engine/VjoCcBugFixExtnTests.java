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
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcBugFixExtnTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	

	@Test
	// Bug2603
	public void testNumber() {
		String js = "BugJsFiles.Bug2603";
		String[] names = new String[] { "Number" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("new N", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug2481
	public void testNeedsInherits() {
		String js = "BugJsFiles.Bug2481";
		String[] names = new String[] { "endType", "inits", "mixin", "options",
				"props", "protos", "satisfies" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".inherits(\"\").", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug2667
	// itypes can not have mixins or satisfies
	public void testIType() {
		String js = "BugJsFiles.Bug2667";
		String[] names = new String[] { "endType", "inherits", "inits",
				"needs", "props", "protos" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("Bug2667').", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug4898
	public void testVJDollar() {
		String js = "BugJsFiles.Bug4898";
		String[] names = new String[] { "Call1", "Bug4898" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("new this.vj$.", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug1983
	public void testInherits() {
		String js = "BugJsFiles.Bug1983";
		String[] names = new String[] { "endType", "inits", "protos", "options" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug3930a
	public void testThis() {
		String js = "BugJsFiles.Bug3930";
		String[] names = new String[0];

		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("this.", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug3930b
	public void testThisVJDollar() {
		String js = "BugJsFiles.Bug3930";
		String[] names = new String[0];

		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = firstPositionInFile("this.vj", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug4671a
	public void testITypeA() {
		String js = "BugJsFiles.Bug4671a";
		String[] names = new String[] { "endType", "inits", "options", "props" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("})\r\n.", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug4671b
	public void testITypeB() {
		String js = "BugJsFiles.Bug4671b";
		String[] names = new String[] { "endType", "inits", "options", "protos" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("})\r\n.", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug5076
	public void testJsOutsideCType() {
		String js = "BugJsFiles.Bug5076";
		String[] names = new String[] { "endType", "inherits", "inits",
				"mixin", "needs", "props", "protos", "satisfies" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastBeforePositionInFile("\r\n.endType();", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug5007
	public void testETypeProtos() {
		String js = "BugJsFiles.Bug5076";
		String[] names = new String[] { "endType", "inherits", "inits",
				"mixin", "needs", "props", "protos", "satisfies" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastBeforePositionInFile("\r\n.endType();", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug4935
	public void testDefs() {
		String js = "BugJsFiles.Bug4935";
		String[] names = new String[] { "defs" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastBeforePositionInFile(".defs", jstType);

		checkProposals(jstType, position, names);
	}

	@Test
	// Bug5677
	//In property initialization, only keyword and global method can be used
	public void testAdvisorForPropertyAssign() {
		String js = "BugJsFiles.Bug5677";
		String[] names = new String[0];
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("staticProp3 : ", jstType);

		checkProposals(jstType, position, names);
	}
	
	@Test
	//@Description("Code proposal for extra dot after instance variable")
	public void testBug6352() {
		String js = "BugJsFiles.Bug6352";
		String[] names = new String[] { "iFunc" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("base.", jstType);

		checkProposals(jstType, position, names);
	}
	
	@Test
	//@Description("Code proposal for sections in partial etype. Called CompletionsFileteredRobustTranslator.getAllowedTokens where changes are done.")
	public void testBug6474() {
		String js = "BugJsFiles.Bug6474";
		String[] names = new String[] { "inits","options" };
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile(".", jstType)-1;

		checkProposals(jstType, position, names);
	}
	
	// ***************

	// @Test //Bug4683
	// public void testMixinProposalAfterPkg() {
	// String js = "nonStaticPropAdvisor.ProtosAdvisorTest1";
	// String[] names = new String[] {"ProtosAdvisorMType"};
	// IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
	// int position = lastPositionInFile("mixin('nonStaticPropAdvisor.",
	// jstType);
	//		
	// checkProposals(jstType, position, names);
	// }
	//	
	// @Test //Bug4681
	// public void testMixinEmptyBracket() {
	// String js = "BugJsFiles.Bug4681";
	// String[] names = new String[] {};
	// IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
	// int position = lastPositionInFile("mixin(", jstType);
	//		
	// checkProposals(jstType, position, names);
	// }
	//	
	// @Test //Bug4712
	// public void testMixinEmptySquareBracket() {
	// String js = "BugJsFiles.Bug4712";
	// String[] names = new String[] {"BugJsFiles", "ETypeJs", "Handler",
	// "Integration", "aliasProposal", "constructorAdvisor", "engine",
	// "nonStaticPropAdvisor", "overrideAdvisor", "packageProposalAdvisor",
	// "parent", "staticPropAdvisor", "variableAdvisor", "ProtosAdvisorMType",
	// "ProtosAdvisorMType", "StaticPropAdvisorMType", "StaticPropAdvisorMType",
	// "TestMType"};
	// IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
	// int position = lastPositionInFile("mixin(['", jstType);
	//		
	// checkProposals(jstType, position, names);
	// }
	//	
	// @Test //Bug4639
	// public void testSingleQuoteNeedsProposals() {
	// String js = "BugJsFiles.Bug4639";
	// String[] names = new String[] {"BugJsFiles", "Bug4639",
	// "Bug4639Extn", "Bug4712", "Bug4681"};
	// IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
	// int position = lastPositionInFile(".needs('Bug", jstType);
	//		
	// checkProposals(jstType, position, names);
	// }
	//	
	// @Test //Bug4639
	// public void testSingleQuoteNeedsProposalsExtn() {
	// String js = "BugJsFiles.Bug4639Extn";
	// String[] names = new String[] {"BugJsFiles", "BugJsFiles", "ETypeJs",
	// "Handler", "aliasProposal", "constructorAdvisor", "engine",
	// "nonStaticPropAdvisor", "overrideAdvisor", "packageProposalAdvisor",
	// "parent", "staticPropAdvisor", "variableAdvisor"};
	// IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
	// int position = lastPositionInFile(".needs('", jstType);
	//		
	// checkProposals(jstType, position, names);
	// }
	//	
	// @Test //Bug4189
	// public void testBug4189() {
	// String js = "BugJsFiles.Bug4189";
	// String[] names = new String[] {};
	// IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
	// int position = firstPositionInFile("}", jstType);
	//		
	// checkProposals(jstType, position, names);
	// }
	//	
	// @Test //Bug4933
	// public void testStringFunctions() {
	// String js = "Integration.StringTest";
	// IJstType string = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, "String");
	// List<String> strList = getSuggestions(string);
	// strList.add("toString");
	// String[] names = new String[] {};
	// IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
	// int position = lastPositionInFile("s.", jstType);
	//		
	// checkProposals(jstType, position, strList.toArray(names));
	// }
	//	
	// @Test //Bug4073
	// @Ignore
	// public void testBug4073() {
	// String js = "aliasProposal.AliasTest1";
	// String[] names = new String[] {"MyAlias1", "StaticPropAdvisorTest",
	// "StaticPropAdvisorTest1"};
	// IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
	// int position = lastPositionInFile("constructs : function(){", jstType);
	//		
	// List<IVjoCcProposalData> propList = engine.complete(
	// CodeCompletionUtil.GROUP_NAME,jstType.getName(),
	// jstType.getSource().getBinding().toText(), position);
	// List<String> strList = getStringProposals(propList);
	//		
	// for (String name : names){
	// Assert.assertTrue("proposal : " + name +
	// " is not included in proposal list" +
	// strList, strList.contains(name));
	//			
	// Assert.assertTrue("Multiple packages shown for package : " + name
	// + "\n Proposals : " + strList,
	// strList.indexOf(name) == strList.lastIndexOf(name));
	// }
	// }

	public void checkProposals(IJstType jstType, int position, String[] names) {
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME, jstType.getName(), content, position);
		List<String> strList = getStringProposals(propList);

		for (String name : names) {
			Assert.assertTrue("proposal : " + name
					+ " is not included in proposal list" + strList, strList
					.contains(name));
		}
	}
	
	private static List<String> getStringProposals(
			List<IVjoCcProposalData> dataList) {
		List<String> propList = new ArrayList<String>();
		for (IVjoCcProposalData data : dataList) {
			Object obj = data.getData();
			if (obj instanceof IJstMethod) {
				obj = ((IJstMethod) obj).getName().getName();
			} else if (obj instanceof IJstProperty) {
				obj = ((IJstProperty) obj).getName().getName();
			} else if (obj instanceof JstArg) {
				obj = ((JstArg) obj).getName();
			} else if (obj instanceof IJstType) {
				obj = ((IJstType) obj).getSimpleName();
			} else if (obj instanceof JstIdentifier) {
				obj = ((JstIdentifier) obj).getName();
			} else if (obj instanceof JstPackage) {
				obj = ((JstPackage) obj).getName();
			}
			propList.add((String) obj);
		}
		return propList;
	}
}
