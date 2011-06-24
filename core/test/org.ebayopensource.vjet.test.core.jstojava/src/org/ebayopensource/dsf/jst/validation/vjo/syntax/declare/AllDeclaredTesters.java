/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllDeclaredTesters.java, May 25, 2009, 11:21:53 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.declare;

import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype2Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype3Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype4Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype5Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype6Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtypeCorrect1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtypeCorrect2Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtypeCorrect3Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtypeCorrect4Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype2Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype3Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype4Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype5Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype6Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtypeCorrect1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.AnonDeclaredCorrect1;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.Anonymous1;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.Anonymous2;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.Anonymous3;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.Anonymous4;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.NestedClientTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.NestedSameNameTester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.NestedTypesCorrect1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.NestedTypesCorrect2Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItype1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItype2Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItype3Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItype4Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItypeCorrect1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItypeSeq1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItypeSeq2Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype.DeclaredMtype1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype.DeclaredMtype2Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype.DeclaredMtype3Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype.DeclaredMtypeCorrect1Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype.DeclaredMtypeCorrect2Tester;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.otype.CorrectOtype;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All declared testers
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */

@RunWith(Suite.class)
@SuiteClasses({
    CorrectOtype.class,
       
    //InnerType
//    AccessOuterType.class,
    AnonDeclaredCorrect1.class,
    Anonymous1.class,
    Anonymous2.class,
    Anonymous3.class,
    Anonymous4.class,
//    NestedTypesIType.class,
    NestedTypesCorrect1Tester.class,
    NestedTypesCorrect2Tester.class,
    NestedClientTester.class,
    NestedSameNameTester.class,
//    NestedTypesIType.class,
//    AccessOuterType.class,
    
    // CTYPE
    DeclaredCtype1Tester.class,
    DeclaredCtype2Tester.class,
    DeclaredCtype3Tester.class,
    DeclaredCtype4Tester.class,
    DeclaredCtype5Tester.class,
    DeclaredCtype6Tester.class,
    DeclaredCtypeCorrect1Tester.class,
    DeclaredCtypeCorrect2Tester.class,
    DeclaredCtypeCorrect3Tester.class,
    DeclaredCtypeCorrect4Tester.class,
    
    // ITYPE
    DeclaredItype1Tester.class,
    DeclaredItype2Tester.class,
    DeclaredItype3Tester.class,
    DeclaredItype4Tester.class,
    DeclaredItypeCorrect1Tester.class,
    DeclaredItypeSeq1Tester.class,
    DeclaredItypeSeq2Tester.class,
    
    // ETYPE
    DeclaredEtype1Tester.class,
    DeclaredEtype2Tester.class,
    DeclaredEtype3Tester.class,
    DeclaredEtype4Tester.class,
    DeclaredEtype5Tester.class,
    DeclaredEtype6Tester.class,
    DeclaredEtypeCorrect1Tester.class,
    
    // MTYPE
    DeclaredMtype1Tester.class,
    DeclaredMtype2Tester.class,
    DeclaredMtype3Tester.class,
    DeclaredMtypeCorrect1Tester.class,
    DeclaredMtypeCorrect2Tester.class
    
    
})
public class AllDeclaredTesters {

}
