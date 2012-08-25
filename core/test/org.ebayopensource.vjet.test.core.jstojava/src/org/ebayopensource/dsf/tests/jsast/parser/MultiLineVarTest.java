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

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Tests multi-line var test
 * 
 * 
 *
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class MultiLineVarTest implements ICommentConstants {
	
	private JstType jstType = null;
	
	private String fileName = FOLDER + "/MultiLineVar.vjo";
	
	@Before
	public void setUpJst() throws Exception {
		// get file
		URL simple1 = ResourceUtil.getResource(this.getClass(),fileName);
		jstType = (JstType)new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1);
	}
	
	@Test @Ignore
	//@Category({P2,UNIT,FAST})
	//@Description("Test single line value")
	public void verifySingleLineVar() {
		IJstProperty prop = jstType.getInstanceProperty("foo");
		assertEquals("'This is wholly on a single line'", 
					 prop.getValue().toString());
	}
	
	@Test @Ignore
	//@Category({P2,UNIT,FAST})
	//@Description("Test multiple line value")
	public void verifyMultiLineVar() {
		IJstProperty prop = jstType.getInstanceProperty("bar");
		assertEquals("'This is on multiple lines'", 
				 prop.getValue().toString());
	}
	
	@After
	public void tearDownJst() throws Exception {
		jstType = null;

	}

}
