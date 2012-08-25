/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Handlers.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Handlers.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Handlers extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod,
                72, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 3, 0));
//        expectProblems.add(createNewProblem(
//                MethodProbIds.WrongNumberOfArguments, 28, 0));
//        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 27, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                6, 0));
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                8, 0));
//        not reporting this problem as the type is unknown in the 1st place
//        expectProblems
//                .add(createNewProblem(TypeProbIds.typeun, 1, 0));
        // We should not report error if we cannot determine the return type
        // expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 45,
        // 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Vjo vj lib project, To validate false positive ")
    public void testHandlers() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER, "vjoPro.dsf.utils.",
                "Handlers.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
