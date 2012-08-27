/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;




import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
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
public class ExplicitStaticTest implements ICommentConstants {
	
	private static final String fileName = FOLDER + "/ExplicitStatic.vjo";
	
	private static IJstType jstType = null;
	private static TranslateCtx ctx = null;
	
	@BeforeClass
	public static void setUpJst() throws Exception {
		VjoParser p = new VjoParser();
		ctx = new TranslateCtx();

		// get file
		URL url = ResourceUtil.getResource(ExplicitStaticTest.class,
				                fileName);
		if(url.getProtocol().startsWith("bundleresource")){
			url = FileLocator.resolve(url);
		}
		File resource= new File(url.getFile());
		String fileAsString =Kit.readReader(new FileReader(resource));
		jstType = p.parse(fileName, resource.getAbsolutePath(), fileAsString, ctx).getType();
		assertNotNull(jstType);
	}
	
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies variables explicitly declared static")
	public void verifyExplicitStaticVar() {
		IJstProperty prop = jstType.getStaticProperty("foo");
		JstModifiers jm = prop.getModifiers();
		assertTrue(jm.isStatic());
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}
	
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies functions explicitly declared static")
	public void verifyExplicitStaticFunction() {
		IJstMethod prop = jstType.getStaticMethod("bar");
		JstModifiers jm = prop.getModifiers();
		assertTrue(jm.isStatic());
		
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());
	}
	
	@Test
	//@Category({P2,UNIT,FAST})
	//@Description("Verifies no error in case of explicitly declaration of static")
	public void verifyNoErrors() {
		ErrorReporter er = ctx.getErrorReporter();
		assertFalse(er.hasErrors());

	}
	
	@AfterClass
	public static void tearDownJst() throws Exception {
		ctx = null;
		jstType = null;

	}

}
