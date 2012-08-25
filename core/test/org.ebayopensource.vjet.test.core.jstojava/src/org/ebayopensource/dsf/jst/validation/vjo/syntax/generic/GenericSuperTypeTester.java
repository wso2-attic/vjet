/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: GenericSuperType.java, Mar 2, 2010, 9:58:19 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.generic;





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
public class GenericSuperTypeTester  extends VjoValidationBaseTester {
    @Before
    public void setUp() {
        expectProblems.clear();
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes1() {
//        expectProblems.add(createNewProblem(problemId, 2, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType1.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes2() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes3() {
        expectProblems.clear();
//      expectProblems.add(createNewProblem(problemId, 2, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType3.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes4() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType4.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes5() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 8, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType5.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes6() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(NOTSUREPROBLEID, 4, 0));
//        expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 8, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType6.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes7() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 8, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType7.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes8() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(MethodProbIds.WrongNumberOfArguments, 8, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType8.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes9() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType9.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes10() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.OverrideSuperMethodWithReducedVisibility, 7, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType10.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes11() {
        expectProblems.clear();
//        removed by huzhou@ebay.com, this isn't an error in the js source at all
//        expectProblems.add(createNewProblem(MethodProbIds.OverrideSuperMethodWithReducedVisibility, 7, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType11.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes12() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 13, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType12.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes13() {
        expectProblems.clear();
        //by huzhou@ebay.com to match TypeCheckUtil's change done by Eric
        //@see #isAssignable Modified by Eric.Ma 20100408
        //Object type and JstTypeWithArgs equivalence logic modification
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 13, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType13.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes14() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType14.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes15() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType15.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes16() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType16.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes17() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType17.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes18() {
        expectProblems.clear();
        //added by huzhou@ebay.com, as the String type doesn't match parameter type
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 18, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType18.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes19() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType19.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes20() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType20.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes21() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType21.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes22() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType22.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes23() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType23.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes24() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType24.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
