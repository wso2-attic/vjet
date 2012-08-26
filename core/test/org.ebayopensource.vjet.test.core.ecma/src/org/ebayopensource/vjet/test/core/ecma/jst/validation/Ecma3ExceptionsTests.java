/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Ecma3ExceptionsTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
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
 * Ecma3ExceptionsTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Ecma3ExceptionsTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 849, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 863, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 629, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 630, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 635, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 792, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 793, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 794, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 799, 0));
        
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 653, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 660, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 669, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 806, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 813, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 822, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testEcma3ExceptionsTests() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "Ecma3ExceptionsTests.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
