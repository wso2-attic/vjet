/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.inherits;




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
public class VjoInheritsTest extends VjoValidationBaseTester {
    List<VjoSemanticProblem> actualProblems = null;

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test unused private methods ")
    public void testInherits() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod,
                14, 0));
        expectProblems.add(createNewProblem(TypeProbIds.ClassBetterStartsWithCapitalLetter,
                1, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.inherits.", "parent.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test inherits visbility invoving field and method ")
    public void testInherits2() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(FieldProbIds.NotVisibleField, 16, 0));
        expectProblems.add(createNewProblem(MethodProbIds.NotVisibleMethod, 21,
                0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.RedundantImport,
                2, 0));
//        expectProblems.add(createNewProblem(TypeProbIds.ObjectMustBeClass, 14,
//                0));
//        expectProblems.add(createNewProblem(TypeProbIds.ObjectMustBeClass, 15,
//                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.inherits.", "Child.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test unused active needs ")
    public void testInherits3() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.inherits.", "School.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("No validation error for typespace in square brackets in inherits section")
    public void testInherits4() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.inherits.", "Child1.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
