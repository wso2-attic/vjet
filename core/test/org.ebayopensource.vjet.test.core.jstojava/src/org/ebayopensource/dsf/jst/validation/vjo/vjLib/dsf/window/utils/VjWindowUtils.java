/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjWindowUtils.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.window.utils;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




/**
 * VjWindowUtils.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjWindowUtils extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 266, 0));
        expectProblems.add(createNewProblem(
                FieldProbIds.NonStaticFieldFromStaticInvocation, 34, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 59, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 154, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 265, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 62, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 110, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 139, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 124, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 125, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 298, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 169, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 202, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 90, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 281, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 324, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 248, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 246, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 59, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 204, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 184, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 16, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 110, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 124, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 169, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 186, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 265, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 263, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 153, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 281, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 61, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 138, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 139, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 298, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 204, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 246, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 41, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 14, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 282, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 186, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 90, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 63, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 248, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 325, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 67, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 263, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 153, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 66, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 20, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 138, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 109, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 91, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 36, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 168, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 325, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 246, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 265, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 249, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 297, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 205, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 63, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 187, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 125, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 39, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 282, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 15, 0));
        expectProblems.add(createNewProblem(
                FieldProbIds.NonStaticFieldFromStaticInvocation, 42, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 263, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 204, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 91, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 266, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 249, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 168, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 154, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 186, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 205, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 326, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 297, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 248, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 65, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 109, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 18, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 187, 0));
    }

    @Test
    @Ignore
    // ML Should change JS files
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Vjo vj lib project, To validate false positive ")
    public void testVjWindowUtils() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER,
                "vjoPro.dsf.window.utils.", "VjWindowUtils.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
