/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.presenter;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstComletionOnMessageSend;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtxForTest;
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
public class VjoCcMethodPropReplaceStrTests extends VjoCcBaseTest {
	
	private MockVjoCcPresenter presenter = new MockVjoCcPresenter();
	
	public VjoCcCtx ctx;
	private TypeName calledName;
	private IJstType calledType;
	private String typeStr;
	private Pattern p;
	private List<ProposalResults> results;
	private Map<Integer, List<ProposalData>> xmlMapping = 
		new HashMap<Integer, List<ProposalData>>();
	private String sampleJs = "presenter/FunctionPositionTest.js";
	private String testJs = "presenter.FunctionPosition";
	private String xmlFile = "presenter/FunctionPositionData.xml";
	
	

	@Before
	public void setUp() throws Exception {
		ctx = getEmptyContext();
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
				int protosPos = content.indexOf("protos");
				int initsPos = content.indexOf("inits");
				Matcher m = p.matcher(content);
				if (m.find()) {
					int start = m.start();
					int end = m.end();
					String newStr = content.substring(0, start)
							+ content.substring(end);
					findProposals(position, start, newStr, (protosPos > end | initsPos < end));
				}
			}
		}
	}

	private void findProposals(int testPosition, int position, String str, boolean inStatic) {
		if (typeStr.equals(str.trim())) {
//			System.out.println(" Great!! Both strings are equal, position = "
//					+ position + " and test position = " + testPosition);
		}
		List<ProposalData> dataList = xmlMapping.get(testPosition);
		if (dataList != null)
			for (ProposalData data : dataList){
				String expected = data.getProposal();
				String actual = "";
				if (data.getType().equalsIgnoreCase("property")){
					VjoCcCtxForTest ctx = getEmptyContext();
					ctx.setCompletion(new JstComletionOnMessageSend(null));
					ctx.setActingType(calledType);
					ctx.setInStatic(inStatic);
					ctx.setOffset(position);
					ctx.setPositionType(getPositionType(data.getDesc()));
					if (data.isPrefix())
						ctx.setHasNoPrifix(false);
					
					IJstProperty property = calledType.getProperty(data.getName());
					actual = presenter.getPropertyProposalReplaceStr(false, property, ctx);
					if (!actual.equals(expected)){
						results.add(new ProposalResults(actual, expected, testPosition));
					}
				} else if (data.getType().equalsIgnoreCase("function")){
					VjoCcCtxForTest ctx = getEmptyContext();
					ctx.setCompletion(new JstComletionOnMessageSend(null));
					ctx.setActingType(calledType);
					ctx.setInStatic(inStatic);
					ctx.setOffset(position);
					ctx.setPositionType(getPositionType(data.getDesc()));
					if (data.isPrefix())
						ctx.setHasNoPrifix(false);
					
					IJstMethod method = calledType.getMethod(data.getName());
					if (method.isDispatcher()) {
						boolean founcMatch = false;
						for (IJstMethod mtd : method.getOverloaded()) {
							actual = presenter.getMethodProposalReplaceStr(false, mtd, ctx);
							if (actual.equals(expected)){
								founcMatch = true;
								break;
							} 
						}
						if (!founcMatch){
							results.add(new ProposalResults(actual, expected, testPosition));
						}
					} else {
						actual = presenter.getMethodProposalReplaceStr(false, method, ctx);
						if (!actual.equals(expected)){
							results.add(new ProposalResults(actual, expected, testPosition));
						}
					}
				}
			}
	}
	
	private int getPositionType(String str){
		if (str.equalsIgnoreCase("POSITION_UNKNOWN"))
			return VjoCcCtx.POSITION_UNKNOWN;
		if (str.equalsIgnoreCase("POSITION_AFTER_THIS"))
			return VjoCcCtx.POSITION_AFTER_THIS;
		if (str.equalsIgnoreCase("POSITION_AFTER_THISVJO"))
			return VjoCcCtx.POSITION_AFTER_THISVJO;
		if (str.equalsIgnoreCase("POSITION_AFTER_THISVJOTYPE"))
			return VjoCcCtx.POSITION_AFTER_THISVJOTYPE;
		return VjoCcCtx.POSITION_UNKNOWN;
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

	private void populateXmlMap(Map<Integer, List<ProposalData>> mapping, Node node) {
		Integer position = Integer.valueOf(node.getAttributes().getNamedItem(
				"position").getNodeValue());
		List<ProposalData> list = new ArrayList<ProposalData>();

		NodeList clist = node.getChildNodes(); // Advisors
		for (int j = 0; j < clist.getLength(); j++) {
			Node n = clist.item(j);
			if (n.getNodeName().startsWith("proposal")) {
				ProposalData data = new ProposalData(
						n.getAttributes().getNamedItem("name").getNodeValue(),
						n.getAttributes().getNamedItem("type").getNodeValue(),
						n.getAttributes().getNamedItem("description").getNodeValue(),
						n.getFirstChild().getNodeValue());
				
				if (n.getAttributes().getNamedItem("prefix") != null
						&& n.getAttributes().getNamedItem("prefix").
						getNodeValue().equals("yes"))
					data.setPrefix(true);
				
				list.add(data);
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
		public String actual = "";
		public String expected = "";
		public int position;
		private ProposalResults(String actual, String expected,	int position) {
			this.actual = actual;
			this.expected = expected;
			this.position = position;
		}
		public String toString() {
			return "Replacement string is wrong for position : " +  position +
				". Expected = " + expected + " , and actual = " + actual;
		}
	}
	
	public class ProposalData {
		String name = "";
		String type = "";
		String desc = "";
		String proposal = "";
		boolean prefix = false;
		public ProposalData(String name, String type, String desc,
				String proposal) {
			this.name = name;
			this.type = type;
			this.desc = desc;
			this.proposal = proposal;
		}
		public String getName() {
			return name;
		}
		public String getType() {
			return type;
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
}
