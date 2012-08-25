/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: MethodOverload1Tester.java, May 19, 2009, 7:48:49 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
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
public class MethodOverload1Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.ClassBetterStartsWithCapitalLetter, 1, 0));
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test overload with different args")
    public void testInterfaceType1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.methodOverload/", "methodOverload2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test overload with different modifier")
    public void testOverloadModifiers() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.ClassBetterStartsWithCapitalLetter, 1, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.methodOverload/", "methodOverload5.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test overload with different modifier")
    public void testOverloadModifiers2() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.ClassBetterStartsWithCapitalLetter, 1, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.methodOverload/", "methodOverload5.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test overload with different modifier")
    public void methodOverloadOpt1() {
    	expectProblems.clear();
    	expectProblems.add(createNewProblem(
    			MethodProbIds.ParameterMismatch, 11, 0));
    	List<VjoSemanticProblem> problems = getVjoSemanticProblem(
    			"syntax.methodOverload/", "MethodOverloadOpt1.js", this.getClass());
    	assertProblemEquals(expectProblems, problems);
    }
}
