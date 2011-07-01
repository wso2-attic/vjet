/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: GlobalTester.java, Sep 10, 2009, 11:54:09 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.global;

import org.ebayopensource.dsf.jst.validation.vjo.syntax.global.globalMethods.GlobalMethods;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.global.globalType.GlobalError;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.global.globalType.GlobalType;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.global.vjoType.VjoAssertTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.global.vjoType.VjoForEachTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.global.vjoType.VjoMixinTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.global.vjoType.VjoType;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    GlobalMethods.class,
    GlobalError.class,
    GlobalType.class,
    VjoType.class,
    VjoMixinTester.class,
    VjoForEachTester.class,
    VjoAssertTester.class
})
/**
 * Global Tester
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class GlobalTester {

}
