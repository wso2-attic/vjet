/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllStaticTesters.java, May 25, 2009, 8:26:02 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.access.staticMember;

import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.fatherPackage.StaticFatherAccessDefaultTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.fatherPackage.StaticFatherAccessPrivateTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.fatherPackage.StaticFatherAccessProtectedTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.fatherPackage.StaticFatherAccessPublicTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.sameLevel.StaticSLAccessDefaultTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.sameLevel.StaticSLAccessPrivateTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.sameLevel.StaticSLAccessProtectedTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.sameLevel.StaticSLAccessPublicTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.samePackage.StaticSPAccessDefaultTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.samePackage.StaticSPAccessPrivateTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.samePackage.StaticSPAccessProtectedTester;
import org.ebayopensource.dsf.jst.validation.vjo.access.staticMember.samePackage.StaticSPAccessPublicTester;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All static member tester
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
@RunWith(Suite.class)
@SuiteClasses({
    StaticFatherAccessDefaultTester.class,
    StaticFatherAccessPrivateTester.class,
    StaticFatherAccessProtectedTester.class,
    StaticFatherAccessPublicTester.class,
    
    StaticSLAccessDefaultTester.class,
    StaticSLAccessPrivateTester.class,
    StaticSLAccessProtectedTester.class,
    StaticSLAccessPublicTester.class,
    
    StaticSPAccessDefaultTester.class,
    StaticSPAccessPrivateTester.class,
    StaticSPAccessProtectedTester.class,
    StaticSPAccessPublicTester.class
})
public class AllStaticTesters {

}
