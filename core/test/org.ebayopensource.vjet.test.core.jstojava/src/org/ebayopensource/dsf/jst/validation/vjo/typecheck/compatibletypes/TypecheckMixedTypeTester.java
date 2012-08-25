/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: TypecheckCompartibleType1Tester.java, May 7, 2009, 6:34:28 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * For test the in compatible types in Equality Operator.
 * 
 * Should validate 1. var x = 10; x="String"; 2. var x; //< Public int X =
 * "String"; 3. var x; //< Pulic int x = 'String'; 4. var x2 = 10; x2 = 20.323;
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class TypecheckMixedTypeTester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        // REMOVED since mixed types could be ok here boolean could be assigned to boolean+String mixed type
//        expectProblems.add(createNewProblem(
//                TypeProbIds.IncompatibleTypesInEqualityOperator, 13, 0));
//        expectProblems.add(createNewProblem(
//                TypeProbIds.IncompatibleTypesInEqualityOperator, 14, 0));
//        expectProblems.add(createNewProblem(
//                TypeProbIds.IncompatibleTypesInEqualityOperator, 27, 0));
//        expectProblems.add(createNewProblem(
//                TypeProbIds.IncompatibleTypesInEqualityOperator, 28, 0));
//        expectProblems.add(createNewProblem(
//                TypeProbIds.IncompatibleTypesInEqualityOperator, 36, 0));
//        expectProblems.add(createNewProblem(
//                TypeProbIds.IncompatibleTypesInEqualityOperator, 37, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test incomatible situation between functions assignment")
    public void testCompartibleType1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "typecheck.compartible.", "TypecheckMixedType.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
