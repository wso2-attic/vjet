/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: TypeCase1Tester.java, Sep 25, 2009, 2:34:38 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.typeCast;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Type cast declaration
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P1, FAST, UNIT })
public class TypeCast2Tester extends VjoValidationBaseTester {
    @Before
    public void setUp(){
        expectProblems.clear();
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter type cast")
    public void testIfstatement1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.typeCast.",
                "ParTypeCast.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter type cast")
    public void testCastWithEmpty() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.RedefinedLocal, 13, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.typeCast.",
                "GlobalCast.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
