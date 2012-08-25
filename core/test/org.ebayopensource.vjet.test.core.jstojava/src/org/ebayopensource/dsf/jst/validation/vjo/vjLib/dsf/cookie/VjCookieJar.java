/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjCookieJar.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.cookie;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




/**
 * VjCookieJar.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjCookieJar extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 671, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 665, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 3, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue,
                626, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 672, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 671, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 671, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 522, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 533, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 536, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 404, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 669, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 243, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 409, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 649, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                225, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 237, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 218, 0));
        // expectProblems.add(createNewProblem(VarProbIds.UndefinedName,536,
        // 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 380, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                316, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 191, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 668, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 662, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 646, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 646, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 657, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 646, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 628, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 667, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 383, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 70, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 102, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
                308, 0));
        // expectProblems.add(createNewProblem(VarProbIds.UndefinedName,102,
        // 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 666, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 565, 0));
        // expectProblems.add(createNewProblem(VarProbIds.UndefinedName,671,
        // 0));

    }

    @Test
    @Ignore("need to look into jearly")
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Vjo vj lib project, To validate false positive ")
    public void testVjCookieJar() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER, "vjoPro.dsf.cookie.",
                "VjCookieJar.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
