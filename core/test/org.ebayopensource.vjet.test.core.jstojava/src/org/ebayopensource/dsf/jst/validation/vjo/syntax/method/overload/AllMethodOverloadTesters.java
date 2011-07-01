/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllMethodOverloadTesters.java, May 19, 2009, 7:33:30 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload;

import org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload.construct.ConstructorOverload1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload.construct.ConstructorOverloadCorrect1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload.construct.ConstructorOverloadCorrect2Tester;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    MethodOverload1Tester.class,
    MethodOverload2Tester.class,
    MethodOverloadCorrect1Tester.class,
    MethodOverloadCorrect2Tester.class,
    
    ConstructorOverloadCorrect1Tester.class,
    ConstructorOverloadCorrect2Tester.class,
    ConstructorOverload1Tester.class,
    MethodArgumentsTester.class
   
})

/**
 * All method overload testers
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class AllMethodOverloadTesters {

}
