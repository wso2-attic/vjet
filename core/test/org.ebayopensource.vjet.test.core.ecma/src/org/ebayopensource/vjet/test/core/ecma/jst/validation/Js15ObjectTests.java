/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Js15ObjectTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.junit.Before;
import org.junit.Test;




/**
 * Js15ObjectTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Js15ObjectTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 35, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 37, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 126, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 127, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 131, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 132, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 133, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 159, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 170, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 376, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 390, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 404, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 419, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 432, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 511, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 549, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 385, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 387, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 389, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 390, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 391, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 392, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 399, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 401, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 403, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 404, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 405, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 406, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 413, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 415, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 417, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 428, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 434, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 515, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 767, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 782, 0));
        
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testJs15ObjectTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "Js15ObjectTests.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
