/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.typecheck;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoTypeCheckTest extends VjoValidationBaseTester {

    List<VjoSemanticProblem> actualProblems = null;

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test undefined method which start with ${} ")
    public void testTypeCheck() throws Exception {
        expectProblems.clear();
        // $() method is undefined, error missing beforehand
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedFunction, 6,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.typecheck.", "TypeCheck.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test assign invalid type and method not return value ")
    public void testTypeCheck2() throws Exception {
        expectProblems.clear();
        // line number updated after test case changes
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 8, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue, 6,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.typecheck.", "TypeCheck2.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test assign invalid type ")
    public void testTypeCheck3() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 5, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.typecheck.", "TypeCheck3.js",
                this.getClass());
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test assign invalid type ")
    public void testTypeCheck4() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 5, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.typecheck.", "TypeCheck4.js",
                this.getClass());
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test assign invalid type ")
    public void testTypeCheck5() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 8, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.typecheck.", "TypeCheck5.js",
                this.getClass());
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Warning to display if type name and file name not match")
    public void testTypeCheck6() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.IsClassPathCorrect, 1,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.typecheck.", "TypeCheck6.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
