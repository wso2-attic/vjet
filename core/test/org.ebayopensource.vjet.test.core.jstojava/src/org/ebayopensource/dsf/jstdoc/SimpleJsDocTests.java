/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstdoc;




import static junit.framework.Assert.assertTrue;

import java.net.URL;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.junit.Test;


import org.ebayopensource.dsf.common.resource.ResourceUtil;

public class SimpleJsDocTests {

	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("AST Recovery tests with and without errors")
	public void testDocs1() throws Exception {
		
		
		String postiveCaseJs = "multipledocmixed" + ".js";
		URL goodCaseFile = ResourceUtil.getResource(JsDocTests.class, postiveCaseJs);
		
		String goodCase = VjoParser.getContent(goodCaseFile);
		
	
		TranslateCtx positiveCtx = new TranslateCtx();
		assertTrue(positiveCtx.getErrorReporter().getErrors().size()==0);
		assertTrue(positiveCtx.getErrorReporter().getWarnings().size()==0);
	
		
		IJstType posJST = new VjoParser().parse("TEST", goodCaseFile.getFile(), goodCase, false).getType();
	
		assertTrue(posJST.getMethod("doIt").getDoc().getComment().trim().contains("doIt Js doc"));
		assertTrue(posJST.getMethod("foobar").getDoc().getComment().trim().contains("doIt2 Js doc"));
		
		
		
	

	
	
	}
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("AST Recovery tests with and without errors")
	public void testDocsNestedTypes() throws Exception {
		
		
		String postiveCaseJs = "nested" + ".js";
		URL goodCaseFile = ResourceUtil.getResource(JsDocTests.class, postiveCaseJs);
		
		String goodCase = VjoParser.getContent(goodCaseFile);
		
		
		TranslateCtx positiveCtx = new TranslateCtx();
		assertTrue(positiveCtx.getErrorReporter().getErrors().size()==0);
		assertTrue(positiveCtx.getErrorReporter().getWarnings().size()==0);
		
		
		IJstType posJST = new VjoParser().parse("TEST", goodCaseFile.getFile(), goodCase, false).getType();

		
		
		
		assertTrue(posJST.getEmbededType("A").getDoc().getComment().trim().contains("A nested type"));
		assertTrue(posJST.getEmbededType("A").getEmbededType("AA").getDoc().getComment().trim().contains("AA nested type"));
	
		
		
		
		
		
		
	}
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("AST Recovery tests with and without errors")
	public void testDocsGlobals() throws Exception {
		
		
		String postiveCaseJs = "globalstest" + ".js";
		URL goodCaseFile = ResourceUtil.getResource(JsDocTests.class, postiveCaseJs);
		
		String goodCase = VjoParser.getContent(goodCaseFile);
		
		
		TranslateCtx positiveCtx = new TranslateCtx();
		assertTrue(positiveCtx.getErrorReporter().getErrors().size()==0);
		assertTrue(positiveCtx.getErrorReporter().getWarnings().size()==0);
		
		
		IJstType posJST = new VjoParser().parse("TEST", goodCaseFile.getFile(), goodCase, false).getType();
		
		
		
		
		assertTrue(posJST.getGlobalVar("$").getProperty().getDoc().getComment().trim().contains("$ entry point doc"));
		assertTrue(posJST.getGlobalVar("jQuery").getProperty().getDoc().getComment().trim().contains("jquery property"));
		
		
		
		assertTrue(posJST.getGlobalVar("myJQueryGlobalProp").getProperty().getDoc().getComment().trim().contains("JQuery property for testing"));
		assertTrue(posJST.getGlobalVar("myJQueryGlobalFunc").getFunction().getDoc().getComment().trim().contains("JQuery function for testing"));
		
		
		
		
		
		
		
	}
	
	
	
		
		

	
}
