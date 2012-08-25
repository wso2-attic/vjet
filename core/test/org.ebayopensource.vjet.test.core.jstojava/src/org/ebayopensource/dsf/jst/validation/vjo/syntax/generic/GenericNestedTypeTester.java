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
import org.junit.Ignore;
import org.junit.Test;




/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P1, FAST, UNIT })
public class GenericNestedTypeTester  extends VjoValidationBaseTester {
    @Before
    public void setUp() {
        expectProblems.clear();
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType1.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes2() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(NOTSUREPROBLEMID, 2, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes3() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(NOTSUREPROBLEMID, 2, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType3.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes4() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(NOTSUREPROBLEMID, 2, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType4.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes5() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType5.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Ignore
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes6() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType6.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes7() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 10, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType7.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes9() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 16, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType9.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes10() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType10.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Ignore
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes11() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType11.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Ignore
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes12() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(NOTSUREPROBLEMID, lineNumber, colNumber))
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType12.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes13() {
        expectProblems.clear();
//        expectProblems.add(createNewProblem(NOTSUREPROBLEMID, lineNumber, colNumber))
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType13.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes14() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType14.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testNestedTypes15() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 17, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.nested.", "NestedType15.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
