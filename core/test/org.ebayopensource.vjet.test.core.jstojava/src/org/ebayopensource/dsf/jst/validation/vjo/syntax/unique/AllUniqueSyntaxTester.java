/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllSyntaxTester.java, May 11, 2009, 6:47:45 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.unique;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
    SyntaxUniqueCorrect1Tester.class,
    SyntaxUniqueCorrect2Tester.class,
    SyntaxVarUnqiue1Tester.class,
    SyntaxVarUnqiue2Tester.class,
    SyntaxVarUnqiue3Tester.class,
    SyntaxClassUnqiue1Tester.class,
    SyntaxClassUnqiue2Tester.class,
    SyntaxMetUnqiue1Tester.class,
    SyntaxMetUnqiue2Tester.class,
    SyntaxArgUnqiue1Tester.class,
    SyntaxArgUnqiue2Tester.class,
    JavaKeyWord1Tester.class,
    JavaKeyWord2Tester.class,
    JSKeyWord1Tester.class,
    JSKeyWord2Tester.class,
    VjoKeyWord1Tester.class,
    VjoKeyWord2Tester.class
})

/**
 * All unique tester
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class AllUniqueSyntaxTester {
}

