/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;





import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.core.runtime.FileLocator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


//@Category({P1,FAST,UNIT})
public class VjoCcEngineTestUtil extends VjoCcBaseTest {
	public VjoCcCtx ctx = getEmptyContext();
	private TypeName calledName;
	private IJstType calledType;
	private String typeStr;
	private VjoCcEngine engine;
	private Pattern p;
	private List<ProposalResults> results;
	private Map<Integer, List<String>> xmlMapping = 
		new HashMap<Integer, List<String>>();
	public String sampleJs = "";
	public String testJs = "";
	public String xmlFile = "";
	public String testTag = "proposal";
	
	public void testCcProposals(){
		if(!testJs.equals("")){
			initialize();
		}
		URL url = this.getClass().getClassLoader().getResource(sampleJs);
		String contents = VjoParser.getContent(url);
		contents = contents.replace(sampleJs.substring(sampleJs.lastIndexOf("/")+1, 
				sampleJs.indexOf(".js")), calledType.getSimpleName());
		Map<Integer, List<String>> map = filterPositions(contents);
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
			System.out.println("processing position: " + position);
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
		if (typeStr.equals(str)) {
//			System.out.println(" Great!! Both strings are equal, position = "
//					+ position + " and test position = " + testPosition);
		}
		List<IVjoCcProposalData> propList = new ArrayList<IVjoCcProposalData>();
		try {
			propList = engine.complete(
					CodeCompletionUtil.GROUP_NAME,testJs, str, position);
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
		for (IVjoCcProposalData data : actualList){
			Object obj = data.getData();
			if (obj instanceof IJstNode) {
				IJstType type = ((IJstNode)obj).getOwnerType();
				if (type != null && (type.getName().equals("Object") || type.getName().equals("vjo.Object"))) {
					continue;
				}
			}
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
				obj = ((JstVars)obj).getInitializer().getAssignments().get(0).getLHS().toLHSText();
			}
			if (!propList.contains((String)obj)) {
				propList.add((String)obj);
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
//			for (String s : propList) {
//				if (!expectedList.contains(s)) {
//					results.add(new ProposalResults(propList, expectedList,
//							position.intValue()));
//					break;
//				}
//			}
		} else {
			results.add(new ProposalResults(propList, expectedList, position
					.intValue()));
		}
	}

	private void getPositionAdvisorMapping() {
		try {
			System.out.println("loading xml file: "+ xmlFile);
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
				Node caseNode = list.item(i);
				String status = "fail";
				
				if (caseNode != null && caseNode.getAttributes() != null){
					Node namedItem = caseNode.getAttributes().getNamedItem("status");
					//If the test status is not set in xml for any case element,
					//it will be considered as pass
					if (namedItem != null && !namedItem.getNodeValue().trim().equals("")){
						status = namedItem.getNodeValue();
					} else {
						status = "pass";
					}
				}
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
			if (n.getNodeName().startsWith(testTag)) {
				list.add(n.getFirstChild().getNodeValue());
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			VjoCcEngineTestUtil test = new VjoCcEngineTestUtil();
//			URL url = VjoCcEngineTestUtil.class.getClassLoader().getResource(test.sampleJs);
//			test.getFileContents(url.getFile());
//			Map<Integer, List<String>> map = test.filterPositions(test
//					.getFileContents(url.getFile()));
//			test.testHandlerForPositions(map);
//			if (test.results.size() > 0) {
//				new AssertionFailedException(test.printResults());
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}