/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Js12RegExpTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




/**
 * Js12RegExpTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Js12RegExpTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 118, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 123, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 133, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 138, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 143, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 148, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 185, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 190, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 200, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 205, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 210, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 215, 0));
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 2268,
                0));
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 2379,
                0));
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 2402,
                0));
    }

    @Test
    @Ignore("Property issue")
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testJs12RegExpTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "Js12RegExpTests.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
