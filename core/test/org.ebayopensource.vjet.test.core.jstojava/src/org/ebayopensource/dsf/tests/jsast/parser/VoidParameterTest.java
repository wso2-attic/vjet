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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.ebayopensource.af.common.error.ErrorArgsInterface;
import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.af.common.error.ErrorObject;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Tests if parameter of type void logs error
 * 
 * 
 *
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VoidParameterTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/VoidParameter.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		File resource= new File(ResourceUtil.getResource(VoidParameterTest.class,
				                fileName).getFile());
		URL url = ResourceUtil.getResource(BadCommentTest.class,fileName);
		String fileAsString = JsPreGenHelper.url2String(url);
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
	
	@Test @Ignore
	//@Category({P3,UNIT,FAST})
	//@Description("Error in case parameter type is void")
	public void verifyVoidParameterLogsError() {
		IJstMethod method = jstType.getInstanceMethod("foo");
		
		List<JstArg> argList = method.getArgs();
		assertEquals(1, argList.size());
		
		JstArg ja = argList.get(0);
		IJstType jt = ja.getType();
		assertEquals("void", jt.getName());
		
		ErrorReporter er = ctx.getErrorReporter();
		assertTrue(er.hasErrors());
		
		ErrorList el = er.getErrors();
		ErrorObject eo = el.get(0);
		ErrorArgsInterface eai = eo.getParameters();
		String errorMessage = eai.getValueByName("message");
			
		//System.out.println("EM: " + errorMessage);
		
		//assertTrue(errorMessage.startsWith("xxx"));


	}
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;

	}

}
