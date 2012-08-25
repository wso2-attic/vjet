/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: JavaKeyWord2Tester.java, May 19, 2009, 10:46:53 PM, liama. Exp$
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
 * Java key word testers in vjo syntax
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
//@Description("Test java key word")
public class JavaKeyWord2Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        // ML should change the var problem id
        for (int i = 0; i < GenerateJavaKeyWordHelper.javaKeyWord.length; i++) {
            expectProblems.add(createNewProblem(
                    VjoSyntaxProbIds.IncorrectVjoSyntax, 5 + i, 0));
        }
    }

    @Test
    @Ignore
    //@Category( { P3, FAST, UNIT })
    //@Description("Test java key word")
    // quick bug 5880
    public void testOverrideType1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.unique/", "javaKeyWord2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
