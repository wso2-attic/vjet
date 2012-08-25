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

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
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
public class IncompleteFunctionTest implements ICommentConstants {
	
	private IScriptUnit jstUnit = null;
	
	private String fileName = null; 
	
	@Parameters
	public static Collection<?> data() {
		return Arrays.asList( new Object[][] {
				{FOLDER+"/IncompleteFunction.vjo"},
		});
	}

	public IncompleteFunctionTest(String fileName) {
		this.fileName = fileName;
	}
	
	@Before
	public void setUpJst() throws Exception {
		// get file
		URL url = ResourceUtil.getResource(this.getClass(),fileName);
		jstUnit = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, url);
	}
	
	@Test
	//@Category({P1, UNIT, FAST})
	//@Description("verify syntax problems")
	public void verifyFunctionBody() {
		assertEquals(3, jstUnit.getProblems().size());
	}
	
	@After
	public void tearDownJst() throws Exception {
		jstUnit = null;
	}

}
