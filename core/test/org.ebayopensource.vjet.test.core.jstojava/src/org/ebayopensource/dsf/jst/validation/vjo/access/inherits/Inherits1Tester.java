/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: InheritsCorrect1Tester.java, May 25, 2009, 9:38:30 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.access.inherits;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Inherits Correct 1 tester
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
public class Inherits1Tester extends VjoValidationBaseTester {


    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.NotVisibleMethod, 33, 0));
        expectProblems.add(createNewProblem(FieldProbIds.NotVisibleField, 28, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod, 13, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod, 18, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test invoking method from extends type")
    public void testAccesVisible1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "access.inherits.","Employee1.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
