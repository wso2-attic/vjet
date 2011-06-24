/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.astjst;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestInputUtil {

	String xmlFile = "astjst/JstTestInput.xml";
//	private String testFile;
//	private int[] testNos;
//	private File file;
	private URL url;
//	public TestInputUtil(String xmlFile, String testFile, int[] testNos){
//		this.xmlFile = xmlFile;
//		this.testFile = testFile;
//		this.testNos = testNos;
//	}

	public List<TestInputData> getAllTestInputData() {
		List<TestInputData> list = new ArrayList<TestInputData>();
		int totalTests = getTestInputSize();
		for (int i = 1; i <= totalTests; i++) {
			list.add(getTestInputData(i));
		}
		return list;
	}

	public TestInputData getTestInputData(int number) {
		TestInputData data = null;
		try {
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			Document doc = getDocument();
			String query = "/testcases/testcase[@number='" + number + "']/";
			String type = "";
			String section = "";
			String fileName = "";

			Field field;
			String statement = (String) xpath.evaluate(query + "statement",
					doc, XPathConstants.STRING);

			List<JxPathInput> jxPathList = new ArrayList<JxPathInput>();
			List<BindingInput> bindingList;

			if (!statement.equals("")) {
				type = (String) xpath.evaluate(query + "type", doc,
						XPathConstants.STRING);
				section = (String) xpath.evaluate(query + "section", doc,
						XPathConstants.STRING);

			} else {
				fileName = (String) xpath.evaluate(query + "file", doc,
						XPathConstants.STRING);
				fileName = fileName.replace('.','/');
				fileName = fileName.replace('\\','/');
			}

			NodeList jxPathNodeList = (NodeList) xpath.evaluate(query
					+ "jxpath", doc, XPathConstants.NODESET);

			// Iterate jxpath nodes
			for (int i = 0; i < jxPathNodeList.getLength(); i++) {
				NodeList jxPathChildNodeList = jxPathNodeList.item(i)
						.getChildNodes();
				JxPathInput pathInput1 = new JxPathInput();
				bindingList = new ArrayList<BindingInput>();
				// iterate nodes inside jxpath
				for (int j = 0; j < jxPathChildNodeList.getLength(); j++) {
					BindingInput bindings = new BindingInput();
					if (jxPathChildNodeList.item(j).getNodeType() == Node.ELEMENT_NODE) {
						if (jxPathChildNodeList.item(j).getNodeName().equals("node")) {
							bindings.setPosition(jxPathChildNodeList.item(j).
									getAttributes().item(0).getTextContent().trim());
							if(jxPathChildNodeList.item(j).
									getAttributes().item(1)!=null){
								bindings.setStatus(jxPathChildNodeList.item(j).
									getAttributes().item(1).getTextContent().trim());
							}else{
								bindings.setStatus("pass");
							}
							NodeList bindingNodes = jxPathChildNodeList.item(j).getChildNodes();
							// Iterate binding
							for (int k = 0; k < bindingNodes.getLength(); k++) {
								if (bindingNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
									field = bindings.getClass().getDeclaredField(
											bindingNodes.item(k).getNodeName());
									field.set(bindings, bindingNodes.item(k).getTextContent().trim());
								}
							}
							bindingList.add(bindings);
						} else {
							field = pathInput1.getClass().getDeclaredField(
									jxPathChildNodeList.item(j).getNodeName());
							field.set(pathInput1, jxPathChildNodeList.item(j)
									.getTextContent().trim());
						}
					}
				}
				pathInput1.setBindingInfo(bindingList);
				jxPathList.add(pathInput1);
			}
			data = new TestInputData(number, statement.trim(), jxPathList, type
					.trim(), section.trim(), fileName.trim());
//			System.out.println(data.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

	private Document getDocument() {
		Document doc = null;
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			doc = builder.parse(url.openStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return doc;
	}

	private int getTestInputSize() {
		int nodeCount = 0;
		try {
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			XPathExpression expr = xpath.compile("/testcases/*");
			Object result = expr
					.evaluate(getDocument(), XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			nodeCount = nodes.getLength();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return nodeCount;
	}
	
	public void setURL(URL url){
		this.url = url;
	}

//	public File getXmlDataFile(String xmlFile) {
//		File testFile;
//		URL u = JavaSourceLocator.getInstance().getSourceUrl(this.getClass());
//		String fileLoc = u.getFile().substring(0, u.getFile().indexOf("src/"));
//		fileLoc = fileLoc + CodeCompletionUtil.ARTIFACT_FOLDER + "/" + xmlFile;
//		testFile = new File(fileLoc);
//
//		return testFile;
//	}
	
	public URL getXmlDataFile(String xmlFile) {
		return this.getClass().getResource(xmlFile);
	}
	
	public List<File> getXmlDataFileList() {
		URL u = JavaSourceLocator.getInstance().getSourceUrl(this.getClass());
		int index = u.getFile().indexOf("src/");
		if (index < 0){
			index = u.getFile().indexOf("org/");
		}
		String fileLoc = u.getFile().substring(0, index);
		fileLoc = fileLoc + CodeCompletionUtil.ARTIFACT_FOLDER + "/astjst/";
		List<File> listOfFiles = getXmlFilteredFiles(fileLoc);
		return listOfFiles;
	}
	
	private List<File> getXmlFilteredFiles(String fileLoc){
		List<File> xmlFileList = new ArrayList<File>();
		try
        {
            File f = new File(fileLoc);
            boolean flag =  f.isDirectory();
            if(flag)
            {
                File fs[] = f.listFiles();
                for(int i=0;i<fs.length;i++)
                {
                    if(!fs[i].isDirectory())
                    {
                        String filename = fs[i].getName();
                        if(filename.endsWith("xml"))
                        	xmlFileList.add(new File(fileLoc+"/"+filename));

                    }
                }   
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return xmlFileList;
	}

	public class TestInputData {
		int testNumber;
		String statement;
		List<JxPathInput> jXPathInput;
		String type;
		String section;
		String fileName;

		public TestInputData(int testNumber, String statement,
				List<JxPathInput> jXPathInput, String type, String section,
				String fileName) {
			super();
			this.testNumber = testNumber;
			this.statement = statement;
			this.jXPathInput = jXPathInput;
			this.type = type;
			this.section = section;
			this.fileName = fileName;
		}

		public int getTestNumber() {
			return testNumber;
		}

		public String getStatement() {
			return statement;
		}

		public String getType() {
			return type;
		}

		public String getSection() {
			return section;
		}

		public List<JxPathInput> getJXPathInput() {
			return jXPathInput;
		}

		public String getFileName() {
			return fileName;
		}

		public String toString() {
			return "testnumber: " + testNumber + "; " + "type: " + type + "; "
					+ "section: " + section + "; " + "statement: " + statement
					+ "; " + "file: " + fileName + "; " + "xPathinputData: "
					+ jXPathInput + "; ";
		}

	}

	public class JxPathInput {
		String pathname;
		String classname;
		String nodecount;
		List<BindingInput> bindingInfo;

		public String getClassName() {
			return classname;
		}

		public String getPathName() {
			return pathname;
		}

		public int getTotalCount() {
			return Integer.parseInt(nodecount);
		}

		public List<BindingInput> getBindingInfo() {
			return bindingInfo;
		}

		public void setBindingInfo(List<BindingInput> bindingInfo) {
			this.bindingInfo = bindingInfo;
		}
	}

	public class BindingInput {
		String bindingnode;
		String bindingclass;
		String bindingtype;
		String bindingparent;
		String position;
		String status;

		public String getBindNode() {
			return bindingnode;
		}

		public String getBindType() {
			return bindingtype;
		}

		public String getBindParent() {
			return bindingparent;
		}

		public int getPosition() {
			return Integer.parseInt(position);
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getBindingclass() {
			return bindingclass;
		}
	}

}
