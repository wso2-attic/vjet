/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.runtime.tests.metatype;
import static com.ebay.junitnexgen.category.Category.Groups.FF;
import static com.ebay.junitnexgen.category.Category.Groups.IE;
import static com.ebay.junitnexgen.category.Category.Groups.P1;
import static com.ebay.junitnexgen.category.Category.Groups.P2;

import org.junit.Test;

import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Module;
import org.ebayopensource.vjo.runtime.tests.BaseTestClass;

public class MethodsTests extends BaseTestClass {
	private static final String METHODS_TEST_VJO = "org.ebayopensource.vjo.runtime.tests.metatype.jstests.MethodsTests";

	@Test
	@Category( {P1,IE })
	@Module("VjoRuntimeTests")
	public void testMethodsTests_MSIE() throws Exception {
		runJsTest(METHODS_TEST_VJO, BrowserType.IE_8);
	}

	@Test
	@Category( {P2,FF })
	@Module("VjoRuntimeTests")
	public void testMethodsTests_FIREFOX() throws Exception {
		runJsTest(METHODS_TEST_VJO, BrowserType.FIREFOX_3);
	}
//
//	@Test
//	@Category( {P2,OPERA })
//	public void methodsTests_OPERA() throws Exception {
//		runJsTest(METHODS_TEST_VJO, BrowserType.OPERA_9);
//	}
//
//	@Test
//	@Category( {P2,SAFARI })
//	public void methodsTests_SAFARI() throws Exception {
//		runJsTest(METHODS_TEST_VJO, BrowserType.SAFARI_3);
//	}

}
