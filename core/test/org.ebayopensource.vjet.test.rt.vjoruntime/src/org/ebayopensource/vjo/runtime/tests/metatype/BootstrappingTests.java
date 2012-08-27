/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.runtime.tests.metatype;





import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.vjo.runtime.tests.BaseTestClass;
import org.junit.Test;



public class BootstrappingTests extends BaseTestClass {
	private static final String FIELDS_TEST_VJO = "org.ebayopensource.vjo.runtime.tests.metatype.jstests.BootstrappingTests";

	@Test

	public void testBootstrappingTests_MSIE() throws Exception {
		runJsTest(FIELDS_TEST_VJO, BrowserType.IE_8);
	}

	@Test
	public void testBootstrappingTests_FIREFOX() throws Exception {
		runJsTest(FIELDS_TEST_VJO, BrowserType.FIREFOX_3);
	}

//	@Test
//	//@Category( { P2, OPERA })
//	public void bootstrappingTests_OPERA() throws Exception {
//		runJsTest(FIELDS_TEST_VJO, BrowserType.OPERA_9);
//	}
//
//	@Test
//	//@Category( { P2, SAFARI })
//	public void bootstrappingTests_SAFARI() throws Exception {
//		runJsTest(FIELDS_TEST_VJO, BrowserType.SAFARI_3);
//	}
}
