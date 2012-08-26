/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: EcmaExecutionContextsTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.junit.Before;
import org.junit.Test;




/**
 * EcmaExecutionContextsTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class EcmaExecutionContextsTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(
//                MethodProbIds.WrongNumberOfArguments, 75, 0));
//        expectProblems.add(createNewProblem(
//                MethodProbIds.WrongNumberOfArguments, 1629, 0));
//        expectProblems.add(createNewProblem(
//                MethodProbIds.WrongNumberOfArguments, 1678, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 300, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 378, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 451, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 525, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 604, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 686, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 830, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 910, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 1179, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 1312, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testEcmaExecutionContextsTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "EcmaExecutionContextsTests.js",
                this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
