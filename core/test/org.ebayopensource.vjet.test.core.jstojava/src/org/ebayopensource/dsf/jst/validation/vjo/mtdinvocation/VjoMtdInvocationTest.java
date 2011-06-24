/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation;
import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P1;
import static com.ebay.junitnexgen.category.Category.Groups.P2;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;

import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;
import com.ebay.junitnexgen.category.ModuleInfo;

@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoMtdInvocationTest extends VjoValidationBaseTester {

    List<VjoSemanticProblem> actualProblems = null;

    @Test
    @Category( { P1, FAST, UNIT })
    @Description("Test undefined method and asssign static fiel to nonstatic one")
    public void testMethodInvocation() throws Exception {
        expectProblems.clear();
        expectProblems
                .add(createNewProblem(MethodProbIds.UndefinedMethod, 5, 0));
        expectProblems.add(createNewProblem(
                FieldProbIds.NonStaticAccessToStaticField, 24, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 29, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.",
                "Mtdinvocation.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    @Category( {P2, FAST, UNIT })
    @Description("Test local method declaration and invocation")
    public void testLocalMethodInvocation() throws Exception {
        expectProblems.clear();
        expectProblems
        	.add(createNewProblem(MethodProbIds.ParameterMismatch, 6, 0));
        expectProblems
    		.add(createNewProblem(MethodProbIds.ParameterMismatch, 13, 0));
        expectProblems
			.add(createNewProblem(MethodProbIds.ParameterMismatch, 21, 0));
        expectProblems
			.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 24, 0));
        
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.",
                "LocalMtdinvocation.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    @Category( {P2, FAST, UNIT })
    @Description("Test local method declaration and invocation")
    public void testOverloadMethodInvocation() throws Exception {
        expectProblems.clear();
        
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.",
                "OverloadMtdinvocation.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
