/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Ecma3OperatorsTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.junit.Before;
import org.junit.Test;




/**
 * Ecma3OperatorsTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Ecma3OperatorsTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 60, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 60, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 190, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 195, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 200, 0));
//        expectProblems
//        		.add(createNewProblem(FieldProbIds.UndefinedField, 296, 0));
//        expectProblems
//        		.add(createNewProblem(FieldProbIds.UndefinedField, 297, 0));
        // bugfix by roy, redeclared local with same type, tolerated
        // expectProblems.add(createNewProblem(VarProbIds.RedefinedLocal, 205,
        // 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 303, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testEcma3OperatorsTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "Ecma3OperatorsTests.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
