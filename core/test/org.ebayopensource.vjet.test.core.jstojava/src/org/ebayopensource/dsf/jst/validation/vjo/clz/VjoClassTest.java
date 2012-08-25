/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.clz;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoClassTest extends VjoValidationBaseTester {

    List<VjoSemanticProblem> actualProblems = null;

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test initilize itself")
    public void testClz() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.clz.", "ClassA.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test undefined function")
    public void testInherits() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                16, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                27, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedFunction,
                38, 0));
        expectProblems
                .add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.clz.", "ClassTest1.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);

    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test inner class visibility")
    public void testInnerClass() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.clz.", "InnerClass.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
