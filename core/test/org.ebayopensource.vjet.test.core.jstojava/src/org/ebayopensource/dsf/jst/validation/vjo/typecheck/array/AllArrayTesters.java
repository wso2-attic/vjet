/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllArrayTesters.java, May 7, 2009, 6:34:28 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.typecheck.array;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
    CorrectArray1Tester.class,
    CorrectArray2Tester.class,
    CorrectArray3Tester.class,
    CorrectArray4Tester.class,
    ArrayType1Tester.class,
    ArrayType2Tester.class,
    ArrayType3Tester.class,
    ArrayCorrectIDType1Tester.class,
    ArrayIDType1Tester.class,
    MulArray1Tester.class,
    MulArray2Tester.class,
    MulArray3Tester.class
})

/**
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class AllArrayTesters {
}
