/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjoKeyWord1Tester.java, May 20, 2009, 8:01:36 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.unique;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




/**
 * For identify the vjo key word
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
public class VjoKeyWord1Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        for (int i = 0; i < GenerateVjoKeyWordHelper.vjoKeyWord.length; i++) {
            expectProblems.add(createNewProblem(
                    VjoSyntaxProbIds.IncorrectVjoSyntax, 3 + i, 0));
        }
    }

    @Test
    @Ignore
    //@Category( { P3, FAST, UNIT })
    //@Description("Test vjo keyword unique")
    public void testOverrideType1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.unique/", "VjoKeyWord1.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
