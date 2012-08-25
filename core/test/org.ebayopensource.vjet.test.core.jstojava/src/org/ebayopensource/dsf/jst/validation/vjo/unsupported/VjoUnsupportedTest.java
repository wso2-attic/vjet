/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.unsupported;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Ignore;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoUnsupportedTest extends VjoValidationBaseTester {

    List<VjoSemanticProblem> actualProblems = null;

    @Test
    @Ignore("Syntax error")
    //@Category( { P1, FAST, UNIT })
    //@Description("Test no type given")
    public void testNoTypeGiven() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 6, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue, 4,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.unsupported.", "NoType.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test event binding")
    public void testEventBind() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.unsupported.", "Eventbind.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test dynamic proerty")
    public void testDynamicProperty() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.unsupported.",
                "Dynamicproperty.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    @Ignore("Syntax error")
    public void testThrowAndCatch() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.unsupported.",
                "ThrowCatch.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    @Ignore("Syntax error")
    public void testMissArgument() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 8, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.unsupported.",
                "ThrowCatch.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
