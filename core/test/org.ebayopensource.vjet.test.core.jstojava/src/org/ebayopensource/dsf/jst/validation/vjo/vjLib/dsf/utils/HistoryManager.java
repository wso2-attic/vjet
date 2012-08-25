/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: HistoryManager.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




/**
 * HistoryManager.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class HistoryManager extends VjoValidationBaseTester {

	 @BeforeClass
     public static void beforeClass(){
     	JstCache.getInstance().clear();
     	LibManager.getInstance().clear();
     }
	
    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 2, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 3, 0));
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                11, 0));
        expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                12, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 24, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 27, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 28, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 28, 0));
//        expectProblems.add(createNewProblem(TypeProbIds.ObjectMustBeClass, 29,
//                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 32, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 33, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 34, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 34, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 34, 0));
//        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
//                35, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 36, 0));
//        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
//                40, 0));
//        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch,
//                39, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 49, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 52, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 52, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 58, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 65, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 75, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 79, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 83, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 86, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 93, 0));
//        expectProblems.add(createNewProblem(TypeProbIds.ObjectMustBeClass, 93,
//                0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 94, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.VoidMethodReturnsValue, 99, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.VoidMethodReturnsValue, 100, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 99, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.VoidMethodReturnsValue, 105, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.VoidMethodReturnsValue, 106, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 118, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 132, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 148, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 151, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod,
                151, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 152, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 155, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 161, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 164, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 165, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 165,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 167,
                0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Vjo vj lib project, To validate false positive ")
    public void testHistoryManager() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER, "vjoPro.dsf.utils.",
                "HistoryManager.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
