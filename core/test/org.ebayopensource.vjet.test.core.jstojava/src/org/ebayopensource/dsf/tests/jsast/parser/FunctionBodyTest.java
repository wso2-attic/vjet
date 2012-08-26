/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;




import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * 
 * 
 *
 */
@RunWith(value=Parameterized.class)
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class FunctionBodyTest implements ICommentConstants {
	
	private IJstType jstType = null;
	
	private String fileName = null; 
	
	@Parameters
	public static Collection data() {
		return Arrays.asList( new Object[][] {
				{FOLDER+"/FunctionBody.vjo"},
		});
	}

	public FunctionBodyTest(String fileName) {
		this.fileName = fileName;
	}
	
	@Before
	public void setUpJst() throws Exception {
		// get file
		IResourceResolver jstLibResolver = org.ebayopensource.dsf.jstojava.test.utils.JstLibResolver.getInstance()
				.setSdkEnvironment(new org.ebayopensource.dsf.jstojava.test.utils.VJetSdkEnvironment(new String[0], "DefaultSdk"));

		LibManager.getInstance().setResourceResolver(jstLibResolver);
		URL url = ResourceUtil.getResource(this.getClass(),fileName);
		jstType = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, url).getType();
	}
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("Generates vjo and verifies function body.")
	public void verifyFunctionBody() {
		VjoGenerator generator = new VjoGenerator(new GeneratorCtx(CodeStyle.PRETTY));
		generator.writeVjo(jstType);  
		System.out.println("\n===== Vjo =====\n" + generator.getGeneratedText()); // KEEPME
		
//		List<IStmt> iList = jstType.getInitializers(false);
//		System.out.println("1" + iList);
//		assertEquals(0,iList.size());
//		
//		List<IStmt> siList = jstType.getStaticInitializers();
//		System.out.println("2" + siList);
//		//assertEquals(0,siList.size());
//		
//		List<IStmt> iiList = jstType.getInstanceInitializers();
//		System.out.println("3" + iiList);
//		assertEquals(0,iiList.size());

	}
	@After
	public void tearDownJst() throws Exception {
		jstType = null;

	}

}
