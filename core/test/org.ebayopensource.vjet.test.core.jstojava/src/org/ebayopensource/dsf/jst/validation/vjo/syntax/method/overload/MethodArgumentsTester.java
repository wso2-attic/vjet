/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class MethodArgumentsTester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 6, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 7, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 12, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 13, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 25, 0));
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test args assign to different variable type")
    public void testMethodArgs() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.methodOverload/", "Methodarguments.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }

}
