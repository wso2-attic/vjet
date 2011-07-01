/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: TempVjoValidationTests.java, May 7, 2009, 6:34:28 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo;

import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.validation.vjo.access.AllAccessTesters;
import org.ebayopensource.dsf.jst.validation.vjo.bugs.AllBugsSyntaxTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.AllDeclaredTesters;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.dowhile.AllVjoDowhileTesters;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.generic.AllGenericTesters;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.ifstatement.AllIfstatementTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.label.LabelTests;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.method.AllMethodSyntaxTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.mixin.AllMixinTypeTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.needs.AllNeedsTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.override.AllSyntaxTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.paramtypes.AllParamtypesTesters;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.typeCast.AllTypeCastTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.AllUniqueSyntaxTester;
import org.ebayopensource.dsf.jst.validation.vjo.typecheck.array.AllArrayTesters;
import org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes.AllCompatibleTesters;
import org.ebayopensource.dsf.jst.validation.vjo.typecheck.or.OrTypeCheckTester;
import org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.AllReturnTypeTester;
import org.ebayopensource.dsf.jst.validation.vjo.typecheck.typeName.AllTypeNameTester;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Temp vjo validation testers
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
@RunWith(Suite.class)
@SuiteClasses({
    
	//Added by eric 2009.10.08
	AllCompatibleTesters.class,
	AllParamtypesTesters.class,
	AllReturnTypeTester.class,
	AllArrayTesters.class,
	AllGenericTesters.class,
	AllMethodSyntaxTester.class,
	AllSyntaxTester.class,
	AllUniqueSyntaxTester.class,
	AllBugsSyntaxTester.class,
	AllNeedsTester.class,
	AllMixinTypeTester.class,
	AllIfstatementTester.class,
	AllVjoDowhileTesters.class,
	AllDeclaredTesters.class,
	AllAccessTesters.class,
	AllTypeCastTester.class,
	AllTypeNameTester.class,
	LabelTests.class,
//	DefaultJstValidationTests.class,
	OrTypeCheckTester.class
	//End added
})

public class TempVjoValidationTests {
	 @BeforeClass
     public static void beforeClass(){
//     	JstCache.getInstance().clear();
//     	LibManager.getInstance().clear();
     }
}
