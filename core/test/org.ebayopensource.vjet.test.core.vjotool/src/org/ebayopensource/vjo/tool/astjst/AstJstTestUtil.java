/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.astjst;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import junit.framework.Assert;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.JstProxyIdentifier;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.vjo.tool.astjst.TestInputUtil.BindingInput;
import org.ebayopensource.vjo.tool.astjst.TestInputUtil.JxPathInput;
import org.ebayopensource.vjo.tool.astjst.TestInputUtil.TestInputData;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

import org.ebayopensource.dsf.common.FileUtils;

public class AstJstTestUtil {
	
	public AstJstBean getAstJstBean(URL testFile,IJstParseController control) throws URISyntaxException {
		String content;
		AstJstBean bean = null;
		try {
			content = FileUtils.readStream(testFile.openStream());
			TranslateCtx ctx = new TranslateCtx();
			IJstType jst = control.parseAndResolve(CodeCompletionUtil.GROUP_NAME,
					testFile.getFile(), content).getType();
//			IJstType jst = SyntaxTreeFactory2.createJST(null, content
//					.toCharArray(), testFile.getName(), null, ctx);
			bean = new AstJstBean(ctx, jst, ctx.getAST());
			bean.setContent(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<AstJstInput> getAstJstInput(String input){
//		System.out.println("INPUT: " + input);
		List<AstJstInput> list = new ArrayList<AstJstInput>();
		String[] str = input.split("/");
		String pre = "";
		for(int i = 0; i<str.length; i++){
			if (str[i].trim().equals("")){
				pre = "//";
				i = i + 1;
			} else if (str[i].trim().equals("*")){
				pre = "*";
				i = i + 1;
			} else if (i > 0){
				pre = "/";
			} 
			
			String element = str[i];
//			System.out.println("ELEMENT: " + element);
			/*if (element.equals("*")){
				list.add(new AstJstInput("*", null, null, pre));
			} else */
			if (element.indexOf("[") > 0) {
				boolean isLastElement = (i == str.length-1) ? true : false;
				addToInputListVersion2(isLastElement, element, list, pre);
			} else if (i != str.length-1){ //not last
				list.add(new AstJstInput(element, null, 1, pre));
			} else { //last
				list.add(new AstJstInput(element, "*", -1, pre));
			}
		}
		
		return list;
	}
	
	public List<IJstNode> getExpectedNodes(IJstNode node,List<AstJstInput> inputList) {
        List<IJstNode> nodeList = null;
        //JstAstXPathTraverseVisitor visitor = new JstAstXPathTraverseVisitor(inputList);
        JstXpathTraversal visitor = new JstXpathTraversal(inputList);
        node.accept(visitor);
        nodeList = visitor.getFoundNodes();             
        return nodeList;
    }
	
//	public File getFile(String fileName){
//		File testFile;
//		URL u = JavaSourceLocator.getInstance().getSourceUrl(this.getClass());
//		String fileLoc = u.getFile().substring(0, u.getFile().indexOf("src/"));
//		fileLoc = fileLoc + CodeCompletionUtil.ARTIFACT_FOLDER + "/" 
//			+ fileName +".js";
//		testFile = new File(fileLoc);
//		return testFile;
//	}
	
	public URL getFile(String fileName){
		return this.getClass().getResource("/"+fileName+".js");
	}
	
//	public List<String> getXPath(File file){
//		List<String> xpathList = new ArrayList<String>();
//		JstAstXPathVisitor xpathVisitor = new JstAstXPathVisitor();
//		AstJstBean bean = getAstJstBean(file);
//		IJstNode node = bean.getJst();
//		node.accept(xpathVisitor);
//		xpathList = xpathVisitor.getFoundNodes();
//		return xpathList;
//	}


	public void testAssertions(int testNumber, List<IJstNode> nodeList, IJstType rootNode, JxPathInput jxPathInput){
		Assert.assertEquals(jxPathInput.getPathName() + ": ", jxPathInput.getTotalCount(), nodeList.size());
		
		for(BindingInput bindInfo : jxPathInput.getBindingInfo()){
			int nodePosition =  bindInfo.getPosition()-1;
			IJstNode n = nodeList.get(nodePosition);
			if(n.getSource() != null){
//				IJstNode n1 = JstUtil.getLeafNode(rootNode, n.getSource().getStartOffSet(), 
//						n.getSource().getEndOffSet());
				//Assert.assertTrue(nodeList.contains(n1));
				Assert.assertTrue(n.getRootNode().equals(rootNode));
			}
			if (!bindInfo.getBindNode().equals("")){
				if ((n instanceof JstIdentifier) &&
						bindInfo.getStatus().equals("pass")){
					assertJstIdentifiers(testNumber,(JstIdentifier)n,bindInfo);
				}
				else if(bindInfo.getStatus().equals("pass")){
					assertType(n, bindInfo);
				}
			}
		}
	}
	
	public void testAssertions(List<IJstNode> nodeList, IJstType rootNode){
		for( IJstNode n : nodeList){
			IJstNode n1 = JstUtil.getLeafNode(rootNode, n.getSource().getStartOffSet(), 
					n.getSource().getEndOffSet());
			Assert.assertTrue(nodeList.contains(n1));
			Assert.assertTrue(n.getRootNode().equals(rootNode));
		}
	}
	
	public void testCustomAssertions(List<IJstNode> testNodes, IJstType rootNode, String className){
		try {
			if (className != null && !className.equals("")){
				Class<?> clazz = Class.forName(className);
				Method m = clazz.getMethod("validate", List.class, IJstType.class);
				m.invoke(clazz.newInstance(), testNodes, rootNode);
			}
		} catch (Exception e) {
			System.err.println("Provided custom assertion class does " +
					"not exists : " + className.toString());
		}
	}
	
	public void testAstContents(AstJstBean bean) {
		if (bean.getAst().toString().equals(bean.getContent())) {

		} else {
			String ast = trimAndFlatString(bean.getAst().toString());
			String contents = trimAndFlatString(bean.getContent());
			if (!ast.equals(contents)) {
				new AssertionFailedException("Ast string is not same as "
						+ "source string. Source # \n" + bean.getContent()
						+ "\nAnd Ast.toString # \n" + bean.getAst().toString());
			}
		}
	}
	
	public URL getTestFile(TestInputData data){
		URL url = null;
		if(data.getFileName().equals(null) || data.getFileName().equals("")){
			TemplateCreationUtil util = new TemplateCreationUtil();
			util.setDeleteOnExit(true);
//			try {
//				TypeSections sec = TypeSections.valueOf(data.getSection());
//				url = util.createAndLoadType(data.getStatement(), Types
//						.valueOf(data.getType()), sec);
//			} catch (IllegalArgumentException ill){
//				try {
//					StatementSections sec = StatementSections.valueOf(data.getSection());
//					url = util.createAndLoadType(data.getStatement(), Types
//							.valueOf(data.getType()), sec);
//				} catch (IllegalArgumentException ill2){
//					System.err.println("The Type section is not proper. skipping it for now");
//				}
//			}
		}else{
			AstJstTestUtil util = new AstJstTestUtil();
			url = util.getFile(data.getFileName());
		}
		return url;
	}
	
	private boolean isNumber(String str){
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex){
			return false;
		}
	}
	
	private void printLog(IJstNode n){
		System.out.println("BindNode::\t"+getNodeName(n));
		System.out.println("BindType::\t"+getTypeList(n));
		System.out.println("BindClass::\t"+n.getClass().getSimpleName());
		System.out.println("BindParent::\t"+getTypeList(getParentType(n)));
		System.out.println("==");
	}
	
	
	private void assertJstIdentifiers(int testNumber, JstIdentifier n, BindingInput bindInfo){
		if(n.getParentNode() instanceof JstProxyIdentifier){
			n = (JstProxyIdentifier)n.getParentNode();
		}
		IJstNode bNode = n.getJstBinding();
		if(bNode instanceof ILHS
				&& getParentType(bNode) instanceof AssignExpr
				&& getParentType(bNode).getParentNode() instanceof JstVars){
			bNode = getParentType(bNode).getParentNode();
		}
		Assert.assertNotNull(bindInfo);
		Assert.assertTrue("test # "+ testNumber+" failed, Binding for node:"+n.toString()+" for position "+
			bindInfo.getPosition()+" is null", bNode != null);
		
		printLog(bNode);
		Assert.assertEquals(bindInfo.getBindNode(), getNodeName(bNode));
		
		if (bindInfo.getBindType()!=null && 
				!bindInfo.getBindType().equals("")) {
			List<String> typeList = getTypeList(bNode);
			Assert.assertNotNull(typeList);
			List<String> expected = getExpTypeList(bindInfo.getBindType());
			Assert.assertTrue(n.getName() + ":: actualType: " + typeList + " ; expected: " + expected,
				typeList.containsAll(expected));
		}
		if (bindInfo.getBindParent()!=null && 
				!bindInfo.getBindParent().equals("")) {
			List<String> typeList = getTypeList(getParentType(bNode));
			Assert.assertNotNull(typeList);
			Assert.assertEquals("bindInfofailed = " + bindInfo.getBindNode(),typeList.get(0).toString(),bindInfo.getBindParent().toString());
		}
		if (bindInfo.getBindingclass()!=null && 
				!bindInfo.getBindingclass().equals("")) {
			Assert.assertEquals(bindInfo.getBindingclass(), 
					bNode.getClass().getSimpleName());
		}
		
	}

	private IJstNode getParentType(IJstNode bNode) {
		IJstNode parent = bNode.getParentNode();
		if(bNode instanceof IJstMethod 
				&& parent instanceof IJstMethod
				&& ((IJstMethod)parent).getOverloaded().contains(bNode)){
			return parent.getParentNode();
		}
		return parent;
	}
	
	private List<String> getExpTypeList(String bindType){
		String[] types = bindType.split(",");
		List<String> typeList = new ArrayList<String>();
		typeList = Arrays.asList(types);
		return typeList;
	}
	
//	private void assertJstLiteral(IJstNode n, BindingInput bindInfo){
//		Assert.assertEquals(bindInfo.getBindNode(), 
//				getNodeName(n));
//		if (!bindInfo.getBindType().equals(""))
//			Assert.assertEquals(bindInfo.getBindType(), 
//					((JstLiteral)n).getResultType().getSimpleName());
//	}
	
	private void assertType(IJstNode node, BindingInput bindInfo){
		Assert.assertEquals(bindInfo.getBindNode(), 
				getNodeName(node));
//		System.out.println("BindNode::\t"+getNodeName(node));
//		System.out.println("BindType::\t"+getTypeList(node));
//		System.out.println("==");
		if(bindInfo.getBindType()!=null &&
				!bindInfo.getBindType().equals("")){
			List<String> expectedType = Arrays.asList(bindInfo.getBindType().split(","));
			List<String> actualType = getTypeList(node);
			Assert.assertTrue(node.toString() + ":: expected: " + expectedType + " ; actualType: " + actualType,
				expectedType.containsAll(actualType) &&
				actualType.containsAll(expectedType));	
		}
	}
	
	private List<String> getTypeList(IJstNode node){
		List<String> actualType = new ArrayList<String>();
		try {
			JstAstInfoVisitor visitor = new JstAstInfoVisitor();
			Method method = JstAstInfoVisitor.class.getMethod("visit", node
					.getClass());
			method.invoke(visitor, node);
			actualType = visitor.getType();
		} catch (Exception e) {}
		
		return actualType;
	}
	
	/*
	 * foo returns true
	 * 'foo' returns true
	 * "foo" returns true
	 * "foo returns false
	 * foo' returns false
	 * "foo' returns false
	 * 'foo" returns false
	 */
	private boolean isQuotesMatch(final String str) {
		//if both end characters are not quotes, then return true
		if (str.charAt(0) != '\'' && str.charAt(0) != '\"' &&
			str.charAt(str.length()-1) != '\'' && str.charAt(str.length()-1) != '\"') {
			return true;
		}
		else {
			if ((str.charAt(0) == '\'') && (str.charAt(str.length()-1) == '\'')
				||
				(str.charAt(0) == '\"') && (str.charAt(str.length()-1) == '\"')) {
				return true;
			}
			else return false;
		}
	}
	
	private void addToInputListVersion2(boolean isLastElement, String element, List<AstJstInput> list, String pre) {
		String nodeOfElement = element.substring(0,element.indexOf("["));
		String bracketList = element;
		
		String valueOfName = null;
		String valueOfPosition = null;
		
		int openingBracketIndex = -1;				
		while ((openingBracketIndex = bracketList.indexOf("[")) >= 0) {
			int closingBracketIndex = bracketList.indexOf("]");				
			if (closingBracketIndex < 0) {
				System.err.println("Unmatched [ for: " + bracketList);
				System.exit(-1);
			}
			
			String attr = bracketList.substring(openingBracketIndex+1,
					   closingBracketIndex);					

//			System.out.println("Attr:" + attr);
			StringTokenizer equalList = new StringTokenizer(attr,"=", true);
			if (equalList.countTokens() != 3) {
				System.err.println("Need 3 tokens");
				System.exit(-1);
			}
			
			String key = equalList.nextToken().trim();
			String equal = equalList.nextToken().trim();
			String value = equalList.nextToken().trim();
			assert equalList.hasMoreTokens() == false : "err already printed";
		
			if (key.equals("@Name")) {
				if (value.equals("")) {
					System.err.println("@Name value can not be null");
					System.exit(-1);
				}
				
				if (!isQuotesMatch(value)) {
					System.err.println("Unmatched quotes");
					System.exit(-1);
				}

				if (value.charAt(0) == '\'' || value.charAt(0) == '\"') {
					//trim quotes at end of string
					value = value.substring(1,value.length()-1);
				}
//				System.out.println("Name is " + value);		
				
				if (valueOfName != null) {
					System.err.println("Can't have more than 1 @Name");
					System.exit(-1);
				}
				
				valueOfName = value;
			} else if (key.equals("@Position")) {	

				if (value.equals("")) {
					System.err.println("@Position value can not be null");
					System.exit(-1);
				}
				
				if (!isQuotesMatch(value)) {
					System.err.println("Unmatched quotes");
					System.exit(-1);
				}
				
				if (value.charAt(0) == '\'' || value.charAt(0) == '\"') {
					//trim quotes at end of string
					value = value.substring(1,value.length()-1);
				}
				
				if (!isNumber(value)) {
					System.err.println(value + " is not an int");
					System.exit(-1);
				}
//				System.out.println("Position is " + value);
				if (valueOfPosition != null) {
					System.err.println("Can't have more than 1 @Position");
					System.exit(-1);
				}
				valueOfPosition = value;
			} else {
				System.err.println("Must be @Name or @Position: " + key);
				System.exit(-1);
			}	
			if (!equal.equals("=")) {
				System.err.println("= is missing");
				System.exit(-1);
			}
			
			//move to next bracket
			bracketList = bracketList.substring(closingBracketIndex+1);			
		} //while
		
		if (isLastElement) { //not last
			if (valueOfName == null) {
				valueOfName = "*";
			}
			
			if (valueOfPosition == null) {
				valueOfPosition = "-1"; //it's really a "*"
			}
			
		} else { //last
			if (valueOfPosition == null) {
				valueOfPosition = "1";
			}
		}
		
		Integer numberOfPosition = Integer.parseInt(valueOfPosition);
//		System.out.println("Norman: " + isLastElement + "," + nodeOfElement + "," + valueOfName + "," + numberOfPosition);
		list.add(new AstJstInput(nodeOfElement,valueOfName,numberOfPosition, pre));
	}
	
	private String trimAndFlatString(String str) {
		str = str.replace("\n", "");
		str = str.replace("\r", "");
		str = str.replace("\t", "");
		str.trim();
		return str;
	}
	
	public static String getNodeName(IJstNode node) {
		String value = null;
		try {
			JstAstInfoVisitor visitor = new JstAstInfoVisitor();
			Class<?> argType = node.getClass();
			if (node instanceof IJstRefType) {
				argType = IJstRefType.class;
			}
			Method method = JstAstInfoVisitor.class.getMethod("visit", argType);
			method.invoke(visitor, node);
			value = visitor.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public class AstJstBean {
		TranslateCtx ctx;
		IJstType jst;
		CompilationUnitDeclaration ast;
		String content;

		public AstJstBean(TranslateCtx ctx, IJstType jst,
				CompilationUnitDeclaration ast) {
			this.ctx = ctx;
			this.jst = jst;
			this.ast = ast;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public TranslateCtx getCtx() {
			return ctx;
		}

		public IJstType getJst() {
			return jst;
		}

		public CompilationUnitDeclaration getAst() {
			return ast;
		}
	}

	public class AstJstInput {
		private String node; // Node Type (JstType,JstMethod,JstVariable...)
		private String nodeName; // Method or Variable name
		private Integer nodePosition; // Index of Method or variable
		private String nodePrefix; // Node prefix
		
		public AstJstInput(String node, String nodeName, Integer nodePosition, String nodePrefix) {
			super();
			this.node = node;
			this.nodeName = nodeName;
			this.nodePosition = nodePosition;
			this.nodePrefix = nodePrefix;
		}
		public String getNode() {
			return node;
		}
		public String getNodeName() {
			return nodeName;
		}
		public Integer getNodePosition() {
			return nodePosition;
		}
		public void setNodePosition(Integer nodePosition){
			this.nodePosition = nodePosition;
		}
		public String getNodePrefix(){
			return nodePrefix;
		}
		public void setNodePrefix(String nodePrefix){
			this.nodePrefix = nodePrefix;
		}
	}

}
