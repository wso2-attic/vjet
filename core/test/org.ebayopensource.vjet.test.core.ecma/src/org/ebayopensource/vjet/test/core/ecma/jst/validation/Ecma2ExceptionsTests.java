/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Ecma2ExceptionsTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.junit.Before;
import org.junit.Test;




/**
 * Ecma2ExceptionsTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Ecma2ExceptionsTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 310, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 311, 0));
//        treated as an implicity constructor call
//        expectProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
//                313, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 347, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 348, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 383, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 384, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 387, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 419, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 420, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 456, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 457, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 499, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 500, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 545, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 546, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 581, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 582, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 620, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 621, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 648, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 649, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 669, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 671, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 672, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 678, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 761, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 809, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 984, 0));

        // Line 1361 result = (void 0).valueOf(); should be clear with SDC
//        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod,
//                1361, 0));
//        this constructor call fixed
//        expectProblems.add(createNewProblem(TypeProbIds.ObjectMustBeClass,
//                1538, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 2133, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4167, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4168, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4211, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4212, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4213, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4214, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4265, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4312, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4359, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4360, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 4854, 0));
        expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator,
        		4855, 0));
        
        //for new Math error added, no constructor is defined for Math type
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod,
        		352, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod,
        		900, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testEcma2ExceptionsTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "Ecma2ExceptionsTests.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
