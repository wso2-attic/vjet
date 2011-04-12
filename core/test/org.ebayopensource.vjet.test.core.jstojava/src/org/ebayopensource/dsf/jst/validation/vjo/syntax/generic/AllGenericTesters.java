/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllGenericTesters.java, Jun 23, 2009, 7:31:57 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.generic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({
    DeclaredGeneric1Tester.class,
    DeclaredGeneric2Tester.class,
    DeclaredGeneric3Tester.class,
    DeclaredGeneric4Tester.class,
    DeclaredGeneric5Tester.class,
    DeclaredGeneric6Tester.class,
    GenericBugFixTester.class,
    GenericImplTester.class,
    GenericNestedTypeTester.class,
    GenericSuperTypeTester.class,
    GenericConstructorTester.class
})
/**
 * All generic tester
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */

public class AllGenericTesters {
    
}
