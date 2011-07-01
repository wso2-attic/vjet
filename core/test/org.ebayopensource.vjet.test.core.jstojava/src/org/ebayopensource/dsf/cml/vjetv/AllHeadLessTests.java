/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllHeadLessTests.java, Jan 29, 2010, 1:19:30 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.cml.vjetv;

import org.ebayopensource.dsf.cml.vjetv.core.ArgumentParserTester;
import org.ebayopensource.dsf.cml.vjetv.util.ClassLoaderUtilTester;
import org.ebayopensource.dsf.cml.vjetv.util.FileOperatorTester;
import org.ebayopensource.dsf.jslang.feature.bugtests.JsParserTestCases;
import org.ebayopensource.dsf.jslang.ts.tests.JsLangTypeSpaceTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
@RunWith(Suite.class)
@SuiteClasses({
    ClassLoaderUtilTester.class,
    VjetvBugFixTests.class,
    FileOperatorTester.class,
    ArgumentParserTester.class,
    JsParserTestCases.class,
    JsLangTypeSpaceTests.class
})
public class AllHeadLessTests {

}
