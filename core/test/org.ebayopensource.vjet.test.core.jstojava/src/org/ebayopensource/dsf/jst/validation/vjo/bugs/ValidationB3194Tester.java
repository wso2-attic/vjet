/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: SyntaxOverride1Tester.java, May 11, 2009, 6:47:12 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.bugs;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




/**
 * Bug 2192
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
public class ValidationB3194Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        //ML should change the inner class can't use $ problem id
        expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 3,
                0));
    }

    @Test
    @Ignore
    //@Category( { P3, FAST, UNIT })
    //@Description("Test variable name start with _")
    public void testBugfix() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem("bugs.b3194/",
                "Bug3194.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
