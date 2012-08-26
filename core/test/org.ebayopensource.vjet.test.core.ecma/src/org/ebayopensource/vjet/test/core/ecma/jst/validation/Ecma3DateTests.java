/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Ecma3DateTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.junit.Before;
import org.junit.Test;




/**
 * Ecma3DateTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Ecma3DateTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        // bugfix by roy, Function type doesn't have toDateString, toTimeString,
        // toDateTimeString
        // added expected problems. this.now is a Function instead of its return
        // type as Date
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 334,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 349,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 442,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 637,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 650,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 650,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 725,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 737,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 835,
                0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 925, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 926, 0));
        
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 200, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 204, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 208, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 212, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 216, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    // @Ignore
    public void testEcma3DateTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "Ecma3DateTests.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
