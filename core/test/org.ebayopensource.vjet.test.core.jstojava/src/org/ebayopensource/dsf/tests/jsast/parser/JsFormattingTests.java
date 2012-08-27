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

import java.io.IOException;
import java.net.URL;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjet.test.util.TestHelper;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Ignore;
import org.junit.Test;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class JsFormattingTests {
	@Test
	@org.junit.Ignore
	//@Category({P2,UNIT,FAST})
	//@Description("Test generated js matches gold file.")
	public void testFormatting() throws IOException{
//		File jsFile = new File(
//				ResourceUtil.getResource(this.getClass(), "FormattingTest1.js.txt").getFile());
		
//		FileInputStream fis = new FileInputStream(jsFile);
//		String goldString = FileUtils.readStream(fis);
		URL jsFile = ResourceUtil.getResource(this.getClass(), "FormattingTest1.js.txt");
		String goldString = JsPreGenHelper.url2String(jsFile);
		IJstType type = (IJstType)new VjoParser().addLib(
				LibManager.getInstance().getJsNativeGlobalLib()).parse(null, jsFile);
		
		GeneratorCtx ctx = new GeneratorCtx(CodeStyle.PRETTY);
		ctx.getConfig().setAddCodeGenAnnotation(false);
		VjoGenerator writer = new VjoGenerator(ctx);
        writer.writeVjo(type);
        String generatedVjo = writer.getGeneratedText();
        
		assertEquals(goldString, generatedVjo);	
	  }
	
	@Test

	@Ignore("jsdoc comments are not working correctly")
	//@Category({P2,UNIT,FAST})
	//@Description("Test generated js matches gold file.")
	public void testFormattingComments() throws IOException{
//		File jsFile = new File(
//				ResourceUtil.getResource(this.getClass(), "FormattingTest2.js.txt").getFile());
//		
//		FileInputStream fis = new FileInputStream(jsFile);
//		String goldString = FileUtils.readStream(fis);
		URL jsFile = ResourceUtil.getResource(this.getClass(), "FormattingTest2.js.txt");
		String goldString = JsPreGenHelper.url2String(jsFile);
		IJstType type = new VjoParser().addLib(
				LibManager.getInstance().getJsNativeGlobalLib()).parse(null, jsFile).getType();
		
		GeneratorCtx ctx = new GeneratorCtx(CodeStyle.PRETTY);
		ctx.setNewline(TestHelper.NEWLINE);
		ctx.getConfig().setAddCodeGenAnnotation(true);
		VjoGenerator writer = new VjoGenerator(ctx);
		writer.setNewline(TestHelper.NEWLINE);
        writer.writeVjo(type);
        String generatedVjo = writer.getGeneratedText();
        
		assertEquals(goldString, generatedVjo);	
	  }
}
