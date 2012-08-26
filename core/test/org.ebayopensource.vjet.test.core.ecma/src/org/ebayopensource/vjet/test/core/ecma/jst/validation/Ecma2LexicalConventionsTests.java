/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Ecma2LexicalConventionsTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.junit.Before;
import org.junit.Test;




/**
 * Ecma2LexicalConventionsTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Ecma2LexicalConventionsTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 46, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 72, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 77, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 82, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 87, 0));
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 113, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testEcma2LexicalConventionsTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "Ecma2LexicalConventionsTests.js",
                this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
