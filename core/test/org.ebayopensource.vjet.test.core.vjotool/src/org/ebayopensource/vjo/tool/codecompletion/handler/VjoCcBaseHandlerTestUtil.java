/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.handler;

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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcHandler;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.eclipse.core.runtime.FileLocator;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class VjoCcBaseHandlerTestUtil extends VjoCcBaseTest {

	private static final String	PATTERN_POSITION	= "<<\\d+>>";

	public class HandlerResults {
		public List<String>	actualList		= new ArrayList<String>();
		public List<String>	expectedList	= new ArrayList<String>();
		public int			position;

		public HandlerResults(String[] actualAdv, List<String> expectedList,
				int position) {
			for (String s : actualAdv) {
				actualList.add(s);
			}
			this.expectedList = expectedList;
			this.position = position;
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Results for Position : " + position);
			sb.append("\nExpected List of Advisors :\n");
			for (String s : expectedList) {
				sb.append("\t" + s + "\n");
			}
			sb.append("\nActual List of Advisors returned from Test :\n");
			for (String s : actualList) {
				sb.append("\t " + s + "\n");
			}

			return sb.toString();
		}
	}

	public VjoCcCtx							ctx			= getEmptyContext();
	public String							sampleJs	= "";
	public String							testJs		= "";
	public String							testTag		= "";
	public String							xmlFile		= "";
	protected VjoCcEngine					engine;
	protected String						typeStr;
	protected Map<Integer, List<String>>	xmlMapping	= new HashMap<Integer, List<String>>();
	private TypeName						calledName;
	private IJstType						calledType;
	private IVjoCcHandler					handler;
	private Pattern							p;
	private List<HandlerResults>			results;

	public void testHandlerCases() {
		initialize();
		URL url = this.getClass().getClassLoader().getResource(sampleJs);
		String contents = VjoParser.getContent(url);
		if(calledType!=null){
			contents = contents.replace(sampleJs.substring(
				sampleJs.indexOf("/") + 1, sampleJs.indexOf(".js")), calledType
				.getSimpleName());
		}
		Map<Integer, List<String>> positions = filterPositions(contents);
		testHandlerForPositions(positions);
		if (results.size() > 0) {
			Assert.fail(printResults());
		}
	}

	protected VjoCcCtx createCcContext(int testPosition, int position,
			String str) {
		return engine.genCcContext(CodeCompletionUtil.GROUP_NAME, testJs,
				str, position);
	}

	protected abstract IVjoCcHandler createHandler();

	protected void populateXmlMap(Map<Integer, List<String>> mapping,
			Node node, Integer position) {
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

	protected void processNode(Node node) {
		Integer position = getPosition(node);
		populateXmlMap(xmlMapping, node, position);
	}

	protected Integer getPosition(Node node) {
		Node namedItem = node.getAttributes().getNamedItem("position");
		if (namedItem == null) {
			return Integer.valueOf(-1);
		}
		return Integer.valueOf(namedItem.getNodeValue());
	}

	private Map<Integer, List<String>> filterPositions(String content) {
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();

		Matcher m = p.matcher(content);
		boolean b = m.find();
		while (b) {
			populatePositionMap(map, content, m);
			b = m.find();
		}

		return map;
	}

	private void findAdvisors(int testPosition, int position, String str) {
		VjoCcCtx ctx = createCcContext(testPosition, position, str);
		String[] proposals = handler.handle(ctx);
		ValidateAdvisors(testPosition, proposals);
	}

	private void getPositionAdvisorMapping() {
		try {
			URL url = VjoCcHandlerTest.class.getClassLoader().getResource(
					xmlFile);
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
				Node node = list.item(i);
				String status = node.getAttributes().getNamedItem("status")
						.getNodeValue();
				if (status.equalsIgnoreCase("pass"))
					processNode(node);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void initialize() {
		calledName = new TypeName(CodeCompletionUtil.GROUP_NAME, testJs);
		calledType = getJstType(calledName);
		URL url = getSourceUrl(testJs, ".js");
		typeStr = VjoParser.getContent(url);
		engine = new VjoCcEngine((JstParseController) CodeCompletionUtil
				.getJstParseController());
		handler = createHandler();
		p = Pattern.compile(PATTERN_POSITION);
		results = new ArrayList<HandlerResults>();
		getPositionAdvisorMapping();
	}

	private void populatePositionMap(Map<Integer, List<String>> map,
			String content, Matcher m) {
		int start = m.start();
		int end = m.end();
		String grp = m.group();

		String actual = content.substring(0, start).replaceAll(
				PATTERN_POSITION, "")
				+ grp + content.substring(end).replaceAll(PATTERN_POSITION, "");

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

	private String printResults() {
		StringBuffer sb = new StringBuffer();
		System.err.println("Following are the mismatches for handler tests");
		System.err
				.println("-------------------------------------------------------");
		for (HandlerResults info : results) {
			sb.append(info.toString());
			sb.append("\n");
			System.err.println(info.toString());
		}
		System.err
				.println("-------------------------------------------------------");
		return sb.toString();
	}

	private void testHandlerForPositions(Map<Integer, List<String>> map) {
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
					if (xmlMapping.get(position) == null) {
						continue;
					}
					findAdvisors(position, start, newStr);
				}
			}
		}
	}

	private void ValidateAdvisors(Integer position, String[] actualList) {
		if (xmlMapping.get(position) == null) {
			return;
		}
		List<String> expectedList = xmlMapping.get(position);
		if (expectedList.size() == actualList.length) {
			for (String s : actualList) {
				if (!expectedList.contains(s)) {
					results.add(new HandlerResults(actualList, expectedList,
							position.intValue()));
					break;
				}
			}
		} else {
			results.add(new HandlerResults(actualList, expectedList, position
					.intValue()));
		}
	}

}
