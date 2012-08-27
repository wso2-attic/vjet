/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.runtime.tests.section.globals;



import static org.junit.Assert.fail;

import org.ebayopensource.dsf.jsnative.anno.BrowserType;

import org.ebayopensource.vjo.runtime.tests.BaseTestClass;
import org.junit.Test;

public class GlobalsTests extends BaseTestClass {
	private static final String NATIVETYPES_TEST_VJO = "org.ebayopensource.vjo.runtime.tests.section.globals.Globals";

 
	@Test
	//@Category( { P2, FF })
//	@Module("VjoRuntimeTests")
	public void testSimpleGlobalsTests_FIREFOX() throws Exception {
		runJsTest(NATIVETYPES_TEST_VJO, BrowserType.FIREFOX_3);
	}
	
	@Test
	//@Category( { P2, FF })
//	@Module("VjoRuntimeTests")
	public void testAttemptToClobberGlobalTests_FIREFOX() throws Exception {
		RuntimeException exp = runJsTestNoAssert("org.ebayopensource.vjo.runtime.tests.section.globals.ClobberGlobal", BrowserType.FIREFOX_3);
		if(exp==null){
			fail("expect that there is a javascript exception for globals overriding window or document");
		}
	}
	
	@Test
	//@Category( { P2, FF })
//	@Module("VjoRuntimeTests")
	public void testDontPromoteToGlobalTests_FIREFOX() throws Exception {
		runJsTest("org.ebayopensource.vjo.runtime.tests.section.globals.DontPromoteToGlobal", 
				BrowserType.FIREFOX_3);
		
	}
	
	
	@Test
	//@Category( { P2, FF })
//	@Module("VjoRuntimeTests")

	public void testPromotingExistingType_FIREFOX() throws Exception {
		runJsTest("org.ebayopensource.vjo.runtime.tests.section.globals.PromoteExistingType", 
				BrowserType.FIREFOX_3);
		
	}
	
	
	@Test
	//@Category( { P2, FF })
//	@Module("VjoRuntimeTests")

	public void testSimpleInheritanceTests_FIREFOX() throws Exception {
		runJsTest("org.ebayopensource.vjo.runtime.tests.section.globals.TypeB", 
				BrowserType.FIREFOX_3);
	}


}
