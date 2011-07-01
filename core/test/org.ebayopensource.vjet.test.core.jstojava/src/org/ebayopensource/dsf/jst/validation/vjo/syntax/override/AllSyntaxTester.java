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
package org.ebayopensource.dsf.jst.validation.vjo.syntax.override;

import org.ebayopensource.dsf.jst.validation.vjo.syntax.override.construct.SyntaxConstructOverride1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.override.construct.SyntaxConstructOverride2Tester;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
    SyntaxConstructOverride1Tester.class,
    SyntaxConstructOverride2Tester.class,
    SyntaxOverride1Tester.class,
    SyntaxOverride2Tester.class,
    SyntaxOverrideCorrectTester.class
    
})
/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class AllSyntaxTester {

}
