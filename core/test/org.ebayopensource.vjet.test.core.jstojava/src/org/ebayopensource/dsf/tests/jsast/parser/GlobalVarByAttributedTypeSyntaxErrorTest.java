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
import java.io.FileReader;
import java.net.URL;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mozilla.mod.javascript.Kit;



import org.ebayopensource.dsf.common.resource.ResourceUtil;
import org.eclipse.core.runtime.FileLocator;


/**
 * Tests if explicit static in .props is ok
 * 
 * 
 *
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class GlobalVarByAttributedTypeSyntaxErrorTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/GlobalVarByAttributedTypeSyntaxError.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		URL url = ResourceUtil.getResource(GlobalVarByAttributedTypeSyntaxErrorTest.class,
				                fileName);
		if(url.getProtocol().contains("bundleresource")){
			url = FileLocator.resolve(url);
		}
		File resource= new File(url.getFile());
		String fileAsString = Kit.readReader(new FileReader(resource));
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
		
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies local variables as attributed type with syntax error")
	public void verifyLocalVariableByAttributedTypeError() {
		
		ErrorReporter er = ctx.getErrorReporter();
		assertTrue(er.hasErrors());
		
		assertEquals(2, er.getErrors().size());
	}
	
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;
	}
}
