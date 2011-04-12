/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.rtnflow;
import com.ebay.junitnexgen.category.ModuleInfo;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P1;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;

@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoRtnFlowTest extends VjoValidationBaseTester {
    List<VjoSemanticProblem> actualProblems = null;

    @Test
    @Category( { P1, FAST, UNIT })
    @Description("Test vjo.needs_impl;")
    public void testRtnFlow() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue,
                23, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue,
                30, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.rtnflow.", "RtnFlow.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    public void testNoRtn() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.rtnflow.", "NoRtn.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    public void testUnreachable() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UnreachableStmt, 7, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 14,
                0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 33,
                0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.rtnflow.", "Unreachable.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
