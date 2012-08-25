/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: MixinType4Tester.java, May 22, 2009, 12:12:13 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.mixin;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
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
//@Category( { P3, FAST, UNIT })
public class MixinType4Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 6, 0));
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 20, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 13, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 27, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test mixin function for ctype with confilict field and method situation")
    public void testIfstatement1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.mixintype/", "PersonConflit.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test mixin function for ctype with confilict field and method situation")
    public void testMixinConflicts() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.mixintype/", "EmployeeConflictsPerson.js", this.getClass());
        expectProblems.clear();
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 5, 0));
        expectProblems.add(createNewProblem(FieldProbIds.AmbiguousField, 13, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 8, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 16, 0));
        //bugfix by roy, mtype reference error changed to undefined field
        expectProblems.add(createNewProblem(FieldProbIds.UndefinedField, 22, 0));
        
        assertProblemEquals(expectProblems, problems);
    }
}
