/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.ex;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoExTest extends VjoValidationBaseTester {
    List<VjoSemanticProblem> actualProblems = null;

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test vjo.needs_impl;")
    public void testIValidation() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "IValidation.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter mismatch ")
    public void testEx1() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 9,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 8,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 32,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "ValidationEx1.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P4, FAST, UNIT })
    //@Description("Test not visbility methods ")
    public void testEx2() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.NotVisibleMethod, 15,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.NotVisibleMethod, 14,
                0));
        expectProblems.add(createNewProblem(FieldProbIds.FinalFieldAssignment,
                8, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "ValidationEx2.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P4, FAST, UNIT })
    //@Description("Test needs other exist valid type ")
    public void testEx3() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "ValidationEx3.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P4, FAST, UNIT })
    //@Description("Test undefined methods ")
    public void testEx4() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(FieldProbIds.AmbiguousField, 13, 0));
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 1, 0));
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 1, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 21,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "VallidationEx4.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test abstract ctype constructors initailize ")
    public void testEx5() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "ValidationEx5.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P4, FAST, UNIT })
    //@Description("Test class path is not correct ")
    public void testEx6() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.IsClassPathCorrect, 1,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "ValidationEx6.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test unused active needs ")
    public void testEx7() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "ValidationEx7.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test invoked type exist in type space but not import ")
    public void testEx8() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 3, 0));
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.TypeUnknownMissingImport, 7, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.ex.", "ValidationEx8.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test not exist package situation ")
    public void testBadPackage() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.IsClassPathCorrect, 1,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.clz.", "BadPackage.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
