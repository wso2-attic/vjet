/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P2;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileReader;

import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstAttributedType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mozilla.mod.javascript.Kit;

import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;
import com.ebay.junitnexgen.category.ModuleInfo;
import com.ebay.kernel.resource.ResourceUtil;

/**
 * Tests if explicit static in .props is ok
 * 
 * 
 *
 */
@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class GlobalVarByAttributedTypeSemanticErrorTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/GlobalVarByAttributedTypeSemanticError.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		File resource= new File(ResourceUtil.getResource(GlobalVarByAttributedTypeSemanticErrorTest.class,
				                fileName).getFile());
//		URL url = ResourceUtil.getResource(BadCommentTest.class,fileName);
		String fileAsString = Kit.readReader(new FileReader(resource));;
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
		
	@Test
	@Category({P2,UNIT,FAST})
	@Description("Verifies local variables as attributed type with semantic error")
	public void verifyLocalVariableByAttributedTypeError() {
		validateGlobalVar(jstType.getGlobalVar("funLocal"));
		
		validateGlobalVar(jstType.getGlobalVar("objLocal"));
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}
	
	private void validateGlobalVar(final IJstGlobalVar intGlobalVar) {
		assertNotNull(intGlobalVar);

		final IJstType type = intGlobalVar.getType();
		assertNotNull(type);
		assertEquals(JstAttributedType.class, type.getClass());
		
		final JstAttributedType attributedType = (JstAttributedType)type;
		assertNotNull(attributedType.getAttributorType());
		assertNotNull(attributedType.getAttributeName());
	}
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;
	}
}
