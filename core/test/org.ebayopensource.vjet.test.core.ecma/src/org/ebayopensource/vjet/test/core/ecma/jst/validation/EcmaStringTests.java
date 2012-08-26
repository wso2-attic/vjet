/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: EcmaStringTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
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
 * EcmaStringTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class EcmaStringTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 528, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 851, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 6109, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 6592, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 7076, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 11216, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 11216, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 11568, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 11568, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 11722, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 11904, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 11904, 0));
//        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 12011, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 12119, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 12195, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 12199, 0));
//        expectProblems.add(createNewProblem(
//                MethodProbIds.WrongNumberOfArguments, 12097, 0));
//        expectProblems.add(createNewProblem(
//                MethodProbIds.WrongNumberOfArguments, 12099, 0));
//        expectProblems.add(createNewProblem(
//                MethodProbIds.WrongNumberOfArguments, 12101, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testEcmaStringTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "EcmaStringTests.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
