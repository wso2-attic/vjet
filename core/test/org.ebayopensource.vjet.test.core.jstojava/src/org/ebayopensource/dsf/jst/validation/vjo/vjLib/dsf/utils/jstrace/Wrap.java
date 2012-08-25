/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Wrap.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.jstrace;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Wrap.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Wrap extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 9, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 10, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 11, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 12, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 12, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 13, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 14, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 15, 0));
        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 16, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 22, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 27, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 53, 0));
//        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 67,
//                0));
//        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 76,
//                0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test Vjo vj lib project, To validate false positive ")
    public void testWrap() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER,
                "vjoPro.dsf.utils.jstrace.", "Wrap.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
