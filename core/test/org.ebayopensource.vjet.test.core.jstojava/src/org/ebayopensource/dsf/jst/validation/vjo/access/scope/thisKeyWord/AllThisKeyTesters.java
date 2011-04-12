/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllScopeTesters.java, May 25, 2009, 12:48:03 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All this key word testers
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */

@RunWith(Suite.class)
@SuiteClasses({
    CorrectThisWord10Tester.class,
    CorrectThisWord1Tester.class,
    CorrectThisWord2Tester.class,
    CorrectThisWord3Tester.class,
    CorrectThisWord4Tester.class,
    CorrectThisWord5Tester.class,
    CorrectThisWord6Tester.class,
    CorrectThisWord7Tester.class,
    CorrectThisWord8Tester.class,
    CorrectThisWord9Tester.class,
    ThisBaseWord1Tester.class,
    ThisWord1Tester.class,
    ThisWord2Tester.class,
    ThisWord3Tester.class
})
public class AllThisKeyTesters {

}
