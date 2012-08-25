/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.arrayaccess;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoArrayAccessTest extends VjoValidationBaseTester {

    List<VjoSemanticProblem> actualProblems = null;

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test accessbility for array")
    public void testArrayAccess() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 5, 0));
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.arrayaccess.",
                "Arrayaccess.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test invoking array's accessbility ")
    public void testArrayParamType() throws Exception {
        expectProblems.clear();
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.arrayaccess.",
                "ArrayParamType.js", this.getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    // syntax error remove
    
//    @Test
//    //@Category( { P1, FAST, UNIT })
//    //@Description("Test accessbility ")
////    @Ignore("Syntax error")
//    public void testBadArray() throws Exception {
//        expectProblems.clear();
//        actualProblems = getVjoSemanticProblem(
//                "org.ebayopensource.dsf.jst.validation.vjo.arrayaccess.", "Badarray.js",
//                this.getClass());
//        assertProblemEquals(expectProblems, actualProblems);
//    }
}
