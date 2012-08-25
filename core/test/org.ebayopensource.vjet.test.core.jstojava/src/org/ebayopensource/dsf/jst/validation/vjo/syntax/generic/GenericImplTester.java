/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: GenericImplTester.java, Mar 4, 2010, 10:40:12 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.generic;





import java.util.List;

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
public class GenericImplTester extends VjoValidationBaseTester {
    @Before
    public void setUp() {
        expectProblems.clear();
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testImpl1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.satisfies.", "Implement1.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testImpl2() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.satisfies.", "Implement2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testImpl3() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.satisfies.", "Implement3.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testImpl4() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.satisfies.", "Implement4.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testImpl5() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.satisfies.", "Implement5.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
}
