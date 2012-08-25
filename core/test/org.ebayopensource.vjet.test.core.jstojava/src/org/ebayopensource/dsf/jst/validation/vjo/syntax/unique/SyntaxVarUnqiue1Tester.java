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
package org.ebayopensource.dsf.jst.validation.vjo.syntax.unique;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Override for bigger visibility
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P1, FAST, UNIT })
public class SyntaxVarUnqiue1Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod,
                28, 0));
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 5, 0));
        expectProblems
                .add(createNewProblem(FieldProbIds.UndefinedField, 18, 0));
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test variable unique")
    public void testOverrideType1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.unique/", "Person2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
