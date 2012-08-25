/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: UriBuilder.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils;




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
 * UriBuilder.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class UriBuilder extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 244, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.RedefinedLocal, 185, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 215, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 79,
                0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInConditionalOperator, 215, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 189,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 285,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 188,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 188,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 189,
                0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 145, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 165, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 217, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 189,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 214, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 63, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 96, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 215,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 242, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 243, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 144, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInConditionalOperator, 163, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 163,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 304, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 304, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 299, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 162,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 188,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 159, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 305, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 305, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 307, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 307, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 214,
                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 308, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 308, 0));
    }

    @Test
    @Ignore
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Vjo vj lib project, To validate false positive ")
    public void testUriBuilder() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER, "vjoPro.dsf.utils.",
                "UriBuilder.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
