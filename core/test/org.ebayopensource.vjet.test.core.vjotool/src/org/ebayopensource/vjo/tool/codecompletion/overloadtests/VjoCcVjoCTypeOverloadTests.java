/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.overloadtests;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngineTestUtil;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.core.runtime.FileLocator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcVjoCTypeOverloadTests extends VjoCcBaseTest{
	
	private VjoCcOverloadUtil overloadUtil;
	
	
	public VjoCcCtx ctx;
	private TypeName calledName;
	private IJstType calledType;
	private String typeStr;
	private Pattern p;
	private List<ProposalResults> results;
	private Map<Integer, List<String>> xmlMapping = 
		new HashMap<Integer, List<String>>();
	private String sampleJs = "engine/overload/CBaseTest.js";
	private String testJs = "engine.overload.CBase";
	public String xmlFile = "engine/overload/Overload_Proposals.xml";
	private VjoCcEngine engine;
	
	

	
	@Before
	public void setUp() throws Exception {
		ctx = getEmptyContext();
		overloadUtil = new VjoCcOverloadUtil();
	}
	
	
	@Test
	public void testCcProposals(){
		initialize();
		URL url = this.getClass().getClassLoader().getResource(sampleJs);
		if(url.getProtocol().contains("bundleresource")){
			try {
				url = FileLocator.resolve(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<Integer, List<String>> map = filterPositions(getFileContents(url
				.getFile()));
		testCodeCompletionProposals(map);
		if (results.size() > 0) {
			throw new AssertionFailedException(printResults());
		}
	}

	private void initialize() {
		calledName = new TypeName(CodeCompletionUtil.GROUP_NAME, testJs);
		calledType = getJstType(calledName);
		URL url = getSourceUrl(testJs, ".js");
		typeStr = VjoParser.getContent(url);
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
		p = Pattern.compile("<<\\d+>>");
		results = new ArrayList<ProposalResults>();
		getPositionAdvisorMapping();
	}
	
	private void testCodeCompletionProposals(Map<Integer, List<String>> map) {
		Iterator<Integer> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Integer position = iter.next();
			List<String> list = map.get(position);
			for (String content : list) {
				Matcher m = p.matcher(content);
				if (m.find()) {
					int start = m.start();
					int end = m.end();
					String newStr = content.substring(0, start)
							+ content.substring(end);
					findProposals(position, start, newStr);
				}
			}
		}
	}

	private void findProposals(int testPosition, int position, String str) {
		List<IVjoCcProposalData> propList = new ArrayList<IVjoCcProposalData>();
		try {
			propList = engine.complete(
					CodeCompletionUtil.GROUP_NAME,testJs, typeStr, position);
			if (propList.size() > 0){
//				System.out.println(propList);
			} else {
//				System.out.println("No proposals");
			}
		} catch (Exception ex){
			System.out.println("Exception occured." + ex.getLocalizedMessage());
		}
		
		ValidateProposals(testPosition, propList);
	}

	private void ValidateProposals(Integer position, List<IVjoCcProposalData> actualList) {
		if(xmlMapping.get(position) == null){
			return;
		}
		List<String> expectedList = xmlMapping.get(position);
		List<String> propList = new ArrayList<String>();
		String args = "";
		
		for (IVjoCcProposalData data : actualList){
			Object obj = data.getData();
			if (obj instanceof IJstMethod){
				IJstMethod m = (IJstMethod) obj;
				//if(m.getName().getName().equalsIgnoreCase("pubCompute")){
					List<JstArg> argList = m.getArgs();
					args += m.getName().getName() + "(";
					if (argList.size() ==  0){
						args += ")";
						propList.add(args);
						args = "";
					}
					else{
						for (JstArg arg : argList){
							IJstType type = arg.getType();
							String name = arg.getName();
							args += type.getSimpleName() + " " + name +", ";
						}
						args = args.substring(0, args.length()-2);
						args += ")";
						propList.add(args);
						args = "";
					}
				//}
			}
			
		}
		if (propList.size() >= expectedList.size()) {
			for (String s : expectedList) {
				if (!propList.contains(s)) {
					results.add(new ProposalResults(propList, expectedList,
							position.intValue()));
					break;
				}
			}
		} else {
			results.add(new ProposalResults(propList, expectedList, position
					.intValue()));
		}
	}
	
	private void getPositionAdvisorMapping() {
		try {
			URL url = VjoCcEngineTestUtil.class.getClassLoader().getResource(xmlFile);
			if(url.getProtocol().contains("bundleresource")){
				url = FileLocator.resolve(url);
			}
			File file = new File(url.getFile());
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);

			NodeList list = doc.getDocumentElement().getElementsByTagName(
					"case"); // Case tag list

			for (int i = 0; i < list.getLength(); i++) {
				String status = list.item(i).getAttributes()
						.getNamedItem("status").getNodeValue();
				if (status.equalsIgnoreCase("pass"))
					populateXmlMap(xmlMapping, list.item(i));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void populateXmlMap(Map<Integer, List<String>> mapping, Node node) {
		Integer position = Integer.valueOf(node.getAttributes().getNamedItem(
				"position").getNodeValue());
		List<String> list = new ArrayList<String>();

		NodeList clist = node.getChildNodes(); // Advisors
		for (int j = 0; j < clist.getLength(); j++) {
			Node n = clist.item(j);
			if (n.getNodeName().startsWith("proposal")) {
				ProposalData data = new ProposalData(
						n.getAttributes().getNamedItem("name").getNodeValue(),
						n.getAttributes().getNamedItem("description").getNodeValue(),
						n.getFirstChild().getNodeValue());
				
				if (n.getAttributes().getNamedItem("prefix") != null
						&& n.getAttributes().getNamedItem("prefix").
						getNodeValue().equals("yes"))
					data.setPrefix(true);
				
				list.add(data.getProposal());
			}
		}
		if (mapping.containsKey(position)) {
			mapping.get(position).addAll(list);
		} else {
			mapping.put(position, list);
		}
	}

	private Map<Integer, List<String>> filterPositions(String content) {
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();

		Matcher m = p.matcher(content);
		boolean b = m.find();
		int count = 0;
		while (b) {
			count = count + 1;
			populatePositionMap(map, content, m);
			b = m.find();

		}

		return map;
	}

	private void populatePositionMap(Map<Integer, List<String>> map,
			String content, Matcher m) {
		int start = m.start();
		int end = m.end();
		String grp = m.group();

		String actual = content.substring(0, start).replaceAll("<<\\d+>>", "")
				+ grp + content.substring(end).replaceAll("<<\\d+>>", "");

		Integer testPosition = Integer.valueOf(grp.replace("<<", "").replace(
				">>", ""));

		if (map.containsKey(testPosition)) {
			List<String> list = map.get(testPosition);
			list.add(actual);
		} else {
			List<String> list = new ArrayList<String>();
			list.add(actual);
			map.put(testPosition, list);
		}
	}

	private String getFileContents(String file) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader inputStream = new BufferedReader(
					new FileReader(file));
			String inLine = "";
			while ((inLine = inputStream.readLine()) != null) {
				sb.append(inLine);
				sb.append("\r\n");
			}
			inputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String jsContent = sb.toString().replace(
				sampleJs.substring(sampleJs.lastIndexOf("/")+1, sampleJs.indexOf(".js")),
				calledType.getSimpleName());
//		System.out.println(jsContent);
		return jsContent;
	}

	public class ProposalResults {
		public List<String> actualList = new ArrayList<String>();
		public List<String> expectedList = new ArrayList<String>();
		public int position;

		private ProposalResults(List<String> actual, List<String> expected,
				int position) {
			this.actualList = actual;
			this.expectedList = expected;
			this.position = position;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Resutls for Position : " + position);
			sb.append("\nExpected List of proposals :");
			for (String s : expectedList) {
				sb.append(" " + s);
			}
			sb.append("\nActual List of proposals returned from Test :");
			for (String s : actualList) {
				sb.append(" " + s);
			}

			return sb.toString();
		}
	}
	
	public class ProposalData {
		String name = "";
		String desc = "";
		String proposal = "";
		boolean prefix = false;
		public ProposalData(String name, String desc,
				String proposal) {
			this.name = name;
			this.desc = desc;
			this.proposal = proposal;
		}
		public String getName() {
			return name;
		}
		public String getDesc() {
			return desc;
		}
		public String getProposal() {
			return proposal;
		}
		public boolean isPrefix(){
			return prefix;
		}
		public void setPrefix(boolean flag){
			this.prefix = flag;
		}
	}

	private String printResults() {
		StringBuffer sb = new StringBuffer();
		System.err.println("Following are the mismatches for handler tests");
		System.err
				.println("-------------------------------------------------------");
		for (ProposalResults info : results) {
			sb.append(info.toString());
			sb.append("\n");
			System.err.println(info.toString());
		}
		System.err
				.println("-------------------------------------------------------");
		return sb.toString();
	}
	
	@Test
	public void testBaseNonStatOverloadProposal(){
		// JS Type name
		String js = "engine.overload.CBase";
		String js1 = "engine.overload.Child";
		// Function to test for overloading
		String funcName = "pubCompute";
		String mixPubFunc = "mixCompute";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		IJstType jstType1 = getJstType(CodeCompletionUtil.GROUP_NAME, js1);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("var pubComp = base.", jstType);
		int position1 = lastPositionInFile("var pubVar = base.", jstType1);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		String[] expectArgs1 = {"int i","int i, String s",
		"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		VjoCcOverloadUtil.Proposals propMix = overloadUtil.new Proposals(jstType1,position1,mixPubFunc);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
		
		List<String> actualMixList = overloadUtil.getActMethParamList(propMix);
		List<String> expectedList1 = Arrays.asList(expectArgs1);
		
		overloadUtil.checkProposals(expectedList1,actualMixList);
	}
	
	@Test
	public void testBaseStatOverloadProposal(){
		
		String js = "engine.overload.CBase";
		String funcName = "pubStaticCompute";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("var pubStatComp = this.", jstType);
		
		// null to be included in case of no argument methods
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	
	@Test
	public void testChildInheritOverloadProposal(){
		// JS Type name
		String js = "engine.overload.Child";
		
		// Function to test for overloading
		String funcName = "pubCompute";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("var pubVar = base.", jstType);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	@Test
	public void testChildInheritStatOverloadProposal(){
		
		String js = "engine.overload.Child";
		String funcName = "pubStaticCompute";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("var pubStatVar = this.", jstType);
		
		// null to be included in case of no argument methods
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	@Test
	public void testChildInheritATypeOverloadProposal(){
		
		String js = "engine.overload.ChildInheritsABase";
		String funcName1 = "abstFunc1";
		String funcName2 = "nonAbstFunc1";
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		int position = lastPositionInFile("abase.", jstType);
		
		// null to be included in case of no argument methods
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop1 = overloadUtil.new Proposals(jstType,position,funcName1);
		VjoCcOverloadUtil.Proposals prop2 = overloadUtil.new Proposals(jstType,position,funcName2);
		
		List<String> actualList1 = overloadUtil.getActMethParamList(prop1);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList1);
		
		List<String> actualList2 = overloadUtil.getActMethParamList(prop2);
		
		overloadUtil.checkProposals(expectedList,actualList2);
	}
	
	@Test
	public void testChildNeedsCTypeOverloadProposal(){
		// JS Type name
		String js = "engine.overload.ChildNeeds";
		
		// Function to test for overloading
		String funcName = "pubCompute";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("var cpubVar = cbase.", jstType);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	@Test
	public void testChildNeedsETypeOverloadProposal(){
		// JS Type name
		String js = "engine.overload.ChildNeeds";
		
		// Function to test for overloading
		String funcName = "pubCompute";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("ebase.", jstType);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	@Test
	public void testChildSatisfyITypeOverloadProposal(){
		// JS Type name
		String js = "engine.overload.Child";
		
		// Function to test for overloading
		String funcName = "func";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("ichild.", jstType);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	
	@Test
	public void testChildMixinMTypeOverloadProposal(){
		// JS Type name
		String js = "engine.overload.Child";
		
		// Function to test for overloading
		String funcName = "mpubCompute";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
		
		// Position where proposal to be displayed
		int position = lastPositionInFile("mchild.", jstType);
		
		// list of expected argument list. include null in case of no argument expected
		String[] expectArgs = {null,"int i","int i, String s",
				"int i, String s, Date d"};
		
		VjoCcOverloadUtil.Proposals prop = overloadUtil.new Proposals(jstType,position,funcName);
		
		List<String> actualList = overloadUtil.getActMethParamList(prop);
		List<String> expectedList = Arrays.asList(expectArgs);
		
		overloadUtil.checkProposals(expectedList,actualList);
	}
	

}
