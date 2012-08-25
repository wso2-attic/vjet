/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: ValidationB4233.java, Jul 30, 2009, 6:44:36 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.bugs;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P1, FAST, UNIT })
public class ValidationB4233 extends VjoValidationBaseTester{
    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 6, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 31, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 3, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 19, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 7, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 35, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 5, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 27, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 11, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 4, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.InvalidIdentifier, 23, 0));
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 3, 0));
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 4, 0));
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 5, 0));
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 6, 0));
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 7, 0));
        expectProblems.add(createNewProblem(MethodProbIds.OverlyVisibleMethod, 1, 0));
        expectProblems.add(createNewProblem(TypeProbIds.ClassBetterStartsWithCapitalLetter, 1, 0));
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test invalid identifier for field")
    public void testBugfix() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem("bugs.b4233/",
                "b4233A.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
