/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoJstUtilTests {
	private IJstType type;

	@Before
	public void setUp() throws IOException {
//		File file = new File(ResourceUtil.getResource(getClass(),
//				"JstUtilTest.js.txt").getFile());
		URL file = ResourceUtil.getResource(VjoJstTests.class, "JstUtilTest.js.txt");
		type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, file).getType();
	}

	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test leaf node from JstUtil for ctype")
	public void testCtype() {
		BaseJstNode node = JstUtil.getLeafNode(type, 23, 23);
		assertNotNull(node);
		assertTrue(node instanceof JstType);
		assertEquals("vjo.samples.OuterType", ((JstType)node).getName());
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test leaf node from JstUtil for inner type")
	public void testInnerType() {
		BaseJstNode node = JstUtil.getLeafNode(type, 60, 74);
		assertNotNull(node);
		assertTrue(node instanceof JstType);
		assertEquals("vjo.samples.OuterType.StaticInnerType", ((JstType)node).getName());
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test leaf node from JstUtil for static prop in inner type")
	public void testInnerProp() {
		BaseJstNode node = JstUtil.getLeafNode(type, 119, 119);
		assertNotNull(node);
		assertTrue(node instanceof JstName);
		assertEquals("innerStaticP", ((JstName)node).getName());
		
		List<BaseJstNode> nodes = JstUtil.getAllNodes(type, 119, 119);
		assertTrue(nodes.size() == 2);
		assertTrue(nodes.get(0) instanceof JstProperty);
		assertTrue(nodes.get(1) instanceof JstName);
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test leaf node from JstUtil for static function in inner type")
	public void testInnerFunc() {
		BaseJstNode node = JstUtil.getLeafNode(type, 181, 181);
		assertNotNull(node);
		assertTrue(node instanceof JstBlock);
		assertTrue(((JstBlock)node).getParentNode() instanceof JstMethod);
		JstMethod mtd = (JstMethod) ((JstBlock)node).getParentNode();
		assertEquals("innerStaticFunc", mtd.getName().getName());
		
		
		List<BaseJstNode> nodes = JstUtil.getAllNodes(type, 181, 181);
		assertTrue(nodes.size() == 2);
		assertTrue(nodes.get(0) instanceof JstMethod);
		assertTrue(nodes.get(1) instanceof JstBlock);
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test overloaded constructors")
	public void testOverLoadedConst() {
		
		//Get dispatcher method constructor
		List<BaseJstNode> nodes = JstUtil.getAllNodes(type, 619, 619);
		int noOfConst = 0;
		for (BaseJstNode node : nodes) {
			if (node instanceof JstConstructor) noOfConst++;
		}
		assertEquals(1, noOfConst);
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test overloaded methods")
	public void testOverLoadedMethod() {
		List<BaseJstNode> nodes = JstUtil.getAllNodes(type, 781, 781);
		assertTrue(nodes.size() == 2);
		assertTrue(nodes.get(0) instanceof JstMethod);
		assertTrue(nodes.get(1) instanceof JstBlock);
	}
	
	@Test
	//@Category({P1,UNIT,FAST})
	//@Description("Test arguments in overloaded methods")
	public void testArgs() {
		BaseJstNode node = JstUtil.getLeafNode(type, 796, 796);
		assertNotNull(node);
		assertTrue(node instanceof JstArg);
		int sourceStart = node.getSource().getStartOffSet();
		int sourceEnd = node.getSource().getEndOffSet();
		assertTrue(sourceStart <= 796 && sourceEnd >= 796);
		
		// Test overloaded argument types
		node = JstUtil.getLeafNode(type, 747, 747, true);
		assertNotNull(node);
		assertTrue(node instanceof JstTypeReference);
		assertTrue(node.getParentNode() instanceof JstArg);
		assertTrue(node.getParentNode().getParentNode() instanceof JstMethod);
		JstMethod method = (JstMethod) node.getParentNode().getParentNode();
		assertFalse(method.isDispatcher());
		assertTrue(method.getParentNode() instanceof JstMethod);
		method = (JstMethod) method.getParentNode();
		assertTrue(method.isDispatcher());
	}


	@After
	public void tearDown() {
	}
}
