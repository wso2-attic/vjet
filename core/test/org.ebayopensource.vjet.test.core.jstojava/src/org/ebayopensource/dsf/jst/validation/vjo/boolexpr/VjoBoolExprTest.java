/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.boolexpr;
import com.ebay.junitnexgen.category.ModuleInfo;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P1;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;

@Category( { P1, FAST, UNIT })
@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoBoolExprTest extends VjoValidationBaseTester {

    List<VjoSemanticProblem> actualProblems = null;

    @Test
    @Category( { P1, FAST, UNIT })
    @Description("Test  boolean expression ")
    public void testBoolExpr() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.boolexpr.", "BoolExpr.js",
                this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    @Category( { P1, FAST, UNIT })
    @Description("Test native boolean expression ")
    public void testNativeBoolExpr() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.boolexpr.",
                "NativeBoolExpr.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    @Category( { P1, FAST, UNIT })
    @Description("Test native boolean expression ")
    public void testNativeBoolExpr2() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.boolexpr.",
                "NativeBoolExpr2.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
