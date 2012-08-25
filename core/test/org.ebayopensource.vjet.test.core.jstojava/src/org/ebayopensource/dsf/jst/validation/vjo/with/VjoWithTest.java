/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.with;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoWithTest extends VjoValidationBaseTester {

    List<VjoSemanticProblem> actualProblems = null;

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test with function")
    public void testWith() throws Exception {
        expectProblems.clear();
        // bugfix by roy, with statement supresses all errors in it now as
        // agreed with Justin
        // expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod,
        // 16, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.with_.", "With.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test nested with delcaration")
    public void testNestedWith() throws Exception {
        expectProblems.clear();
        // bugfix by roy, line number updated as test js resource was updated
        expectProblems.add(createNewProblem(
                VjoSyntaxProbIds.NestedWithDiscouraged, 7, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.with_.", "NestedWith.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test nested with delcaration")
    public void testReturenWith() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 13, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 20, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.with_.", "With2.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
}
