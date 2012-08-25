/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.handler;




import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.junit.Test;



/**
 * Test if the Handler can grab the legal advisors
 * 
 */
//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcHandlerTest extends VjoCcBaseTest {
	
	@Test
	public void testCTypeHandler() {
		VjoCcBaseHandlerTestUtil testUtil = new VjoCcHandlerTestUtil();
		testUtil.testTag = "advisor";
		testUtil.sampleJs = "handler/Sample.js";
		testUtil.testJs = "handler.HandlerTest";
		testUtil.xmlFile = "handler/Advisor_Handler_Mapping.xml";
		
		testUtil.testHandlerCases();
	}
	
	@Test
	public void testCTypewithITypeHandler() {
		VjoCcBaseHandlerTestUtil testUtil = new VjoCcHandlerTestUtil();
		testUtil.testTag = "advisor";
		testUtil.sampleJs = "innertypes/CTypeWithITypeTest.js";
		testUtil.testJs = "innertypes.CTypeWithIType";
		testUtil.xmlFile = "handler/CTypeWithITypeHandler.xml";
		
		testUtil.testHandlerCases();
	}
	
	@Test
	public void testCTypewithMTypeHandler() {
		VjoCcBaseHandlerTestUtil testUtil = new VjoCcHandlerTestUtil();
		testUtil.testTag = "advisor";
		testUtil.sampleJs = "innertypes/CTypeWithMTypeTest.js";
		testUtil.testJs = "innertypes.CTypeWithMType";
		testUtil.xmlFile = "handler/CTypeWithMTypeHandler.xml";
		
		testUtil.testHandlerCases();
	}
	
	@Test
	public void testOuterTypeHandler() {
		VjoCcBaseHandlerTestUtil testUtil = new VjoCcHandlerTestUtil();
		testUtil.testTag = "advisor";
		testUtil.sampleJs = "innertypes/OuterTypeTest.js";
		testUtil.testJs = "innertypes.OuterType";
		testUtil.xmlFile = "handler/OuterTypeHandler.xml";
		
		testUtil.testHandlerCases();
	}
	
	@Test
	public void testCTypeComment() {
		VjoCcBaseHandlerTestUtil testUtil = new VjoCcCommentHandlerTestUtil();
		testUtil.testTag = "advisor";
		testUtil.sampleJs = "comment/CommentTypeTest.js";
		testUtil.testJs = "comment.CommentType";
		testUtil.xmlFile = "Handler/CommentProposalMapping.xml";
		
		testUtil.testHandlerCases();
	}

	@Test
	public void testITypeComment() {
		VjoCcBaseHandlerTestUtil testUtil = new VjoCcCommentHandlerTestUtil();
		testUtil.testTag = "advisor";
		testUtil.sampleJs = "comment/ITypeCommentTest.js";
		testUtil.testJs = "comment.ITypeComment";
		testUtil.xmlFile = "Handler/ITypeCommentProposalMapping.xml";
		
		testUtil.testHandlerCases();
	}

	@Test
	public void testInnerTypeComment() {
		VjoCcBaseHandlerTestUtil testUtil = new VjoCcCommentHandlerTestUtil();
		testUtil.testTag = "advisor";
		testUtil.sampleJs = "comment/InnerTypeCommentTest.js";
		testUtil.testJs = "comment.InnerTypeComment";
		testUtil.xmlFile = "Handler/InnerTypeCommentProposalMapping.xml";
		
		testUtil.testHandlerCases();
	}
}
