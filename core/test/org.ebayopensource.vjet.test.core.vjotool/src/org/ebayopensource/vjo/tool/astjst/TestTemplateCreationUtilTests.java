/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.astjst;






import org.junit.Ignore;
import org.junit.Test;




//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class TestTemplateCreationUtilTests {
	
//	private Map<String, List<String>> xpathMap;
	
//	public void intitializeMap(){
//		xpathMap = new HashMap<String, List<String>>();
//		List<String> fileList = getFileLists();
//		Iterator<String> iter = fileList.iterator();
//		List<String> xpathList = new ArrayList<String>();
//		AstJstTestUtil util = new AstJstTestUtil();
//		
//		while(iter.hasNext()){
//			String fileStr = iter.next();
//			File file = util.getFile(fileStr);
//			xpathList = util.getXPath(file);
//			xpathMap.put(fileStr, xpathList);
//		}
//	}
//	
//	private List<String> getFileLists(){
//		String[] fileArr = {"Test1"};
//		return Arrays.asList(fileArr);
//	}
//	@Test
//	public void testCreateXpaths(){
//		intitializeMap();
//	}
	
	@Test
	@Ignore("Tests all xml files at once. We should not use this test." +
			" But this test shoudl remain as it might be useful later on.")
	public void ignoredTestAllTests() throws Exception {
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll();
	}
	
	@Test
	//@Description("Test Comment Spec")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testCommentSpec() throws Exception {
		String xmlFileTest = "/astjst/CommentSpec.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test etype")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testETypeTest() throws Exception {
		String xmlFileTest = "/astjst/ETypeTest.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Common test scenerios")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testJstTestInput() throws Exception {
		String xmlFileTest = "/astjst/JstTestInput.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test otype")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testOTypeTest() throws Exception {
		String xmlFileTest = "/astjst/OTypeTest.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test inner types")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testInnerTest() throws Exception {
		String xmlFileTest = "/astjst/InnerTest.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test statements")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testStatementsTest() throws Exception {
		String xmlFileTest = "/astjst/StatementsTest.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test param types")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testParamTypeTest() throws Exception {
		String xmlFileTest = "/astjst/ParamTypeTest.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test ECMA Native")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testECMANativeTest() throws Exception {
		String xmlFileTest = "/astjst/ECMANativeTests.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test ECMA statements")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testECMAStatements() throws Exception {
		String xmlFileTest = "/astjst/ECMAStatementsTests.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);			
	}
	
	@Test
	//@Description("Test ECMA operators")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testECMAOperators() throws Exception {
		String xmlFileTest = "/astjst/ECMAOperatorsTests.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);		
	}
	
	@Test
	//@Description("Test ECMA Expressions")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testECMAExpressions() throws Exception {
		String xmlFileTest = "/astjst/ECMAExpressionsTests.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);		
	}
	
	@Test
	//@Description("Test common scenarios")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testTestInput() throws Exception {
		String xmlFileTest = "/astjst/TestInput.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test Native props")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testJsNativeTypesProps() throws Exception {
		String xmlFileTest = "/astjst/JsNativePropsTests.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test Native protos")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testJsNativeTypesProtos() throws Exception {
		String xmlFileTest = "/astjst/JsNativeProtosTests.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("Test global methods")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	@Ignore
	public void ignoredTestGlobalMethods() throws Exception {
		String xmlFileTest = "/astjst/GlobalMethodsTests.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test //8148
	//@Description("Test parital")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testBug8148() throws Exception {
		String xmlFileTest = "/partials/PartialBugs.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test //8009
	//@Description("Variables used in annonymous function")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testBug8009() throws Exception {
		String xmlFileTest = "/astjst/BugFixes.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test //7625
	//@Description("JstIdentifier for LabelStatement")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testBug7625() throws Exception {
		String xmlFileTest = "/astjst/BugFixes.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("To test single file, single test")
	//@Category({P5,UNIT,FAST,NOJARRUN})
	public void testSingleFileSingleTest() throws Exception {
		int testCase = 1;
		String xmlFileTest = "/astjst/JstTestInput.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest, testCase);
	}
	
	@Test
	//@Description("Test JST")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testBugFixes() throws Exception {
		String xmlFileTest = "/BugJsFiles/JSTTestFile.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
	@Test
	//@Description("test vjo.Class APIs")
	//@Category({P3,UNIT,FAST,NOJARRUN})
	public void testVjoClassAPI() throws Exception {
		String xmlFileTest = "/astjst/VjoClassTest.xml";
		AstJstTestDelegator util1 = new AstJstTestDelegator();
		util1.testAll(xmlFileTest);
	}
	
//	private static void testgraph(){
//		Map<ActingType, List<RefInfo>> map = new HashMap<ActingType, List<RefInfo>>();
//		
//		addToMap(map, "H", Types.CTYPE, new RefInfo(TypeRef.NEEDS,"E",Types.CTYPE), 
//				new RefInfo(TypeRef.NEEDS,"F",Types.CTYPE), 
//				new RefInfo(TypeRef.SATISFIES,"G",Types.ITYPE));
//		
//		addToMap(map, "G", Types.ITYPE, new RefInfo(TypeRef.INHERITS,"I",Types.ITYPE));
//		
//		addToMap(map, "F", Types.CTYPE, new RefInfo(TypeRef.INHERITS,"C",Types.CTYPE));
//		
//		addToMap(map, "E", Types.CTYPE, new RefInfo(TypeRef.NEEDS,"J",Types.ATYPE), 
//				new RefInfo(TypeRef.INHERITS,"B",Types.CTYPE));
//		
//		addToMap(map, "C", Types.CTYPE, new RefInfo(TypeRef.INHERITS,"A",Types.CTYPE), 
//				new RefInfo(TypeRef.SATISFIES,"I",Types.ITYPE));
//		
//		addToMap(map, "B", Types.CTYPE, new RefInfo(TypeRef.INHERITS,"A",Types.CTYPE), 
//				new RefInfo(TypeRef.SATISFIES,"I",Types.ITYPE));
//	}
//
//	private static void addToMap(Map<ActingType, List<RefInfo>> map, 
//			String name, Types type, RefInfo... refInfo) {
//		ActingType actInfo = new ActingType(name, type);
//		List<RefInfo> list = new ArrayList<RefInfo>();
//		for (RefInfo info : refInfo){
//			list.add(info);
//		}
//		map.put(actInfo, list);
//	}
}
