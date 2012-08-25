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





import java.util.Collection;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
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
//@ModuleInfo(value = "DsfPrebuild", subModuleId = "JsToJava")
//@Category( { P1, FAST, UNIT })
public class GenericBugFixTester extends VjoValidationBaseTester {
    @Before
    public void setUp() {
        expectProblems.clear();
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
        expectProblems.add(createNewProblem(
                MethodProbIds.WrongNumberOfArguments, 8, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType5.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes6() {
        expectProblems.clear();
        //by huzhou@ebay.com, using method based parameter type now for this test case
//        expectProblems.add(createNewProblem(FieldProbIds.StaticReferenceToNonStaticType
//                , 5, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType6.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes6A() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownMissingImport, 14, 0));
        expectProblems.add(createNewProblem(FieldProbIds.StaticReferenceToNonStaticType, 4, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType6A.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes6B() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownMissingImport, 14, 0));
        expectProblems.add(createNewProblem(FieldProbIds.StaticReferenceToNonStaticType, 4, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownMissingImport, 9, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType6B.js", this.getClass());
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
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 13, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType13.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes18() {
        expectProblems.clear();
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

    @Test
    @Ignore//Bug here should fix
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes25() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType25.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes26() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType26.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes27() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 12, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType27.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes28() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType28.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes29() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType29.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes30() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 12, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType30.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes31() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 12, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType31.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes32() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 13, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType32.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testSuperTypes33() {
        expectProblems.clear();
        assertProblemEquals(expectProblems, getVjoSemanticProblem(
                "syntax.generic.superType.", "SubType33.js", this.getClass()));
    }
    
    @Test
    @Ignore //Bug exist here
    //@Category( { P1, FAST, UNIT })
    //@Description("Test parameter as generic")
    public void testOTypeDef() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.otype.", "Deotype.js", this.getClass());
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
        
        final Collection<Object> c = null;
        sth(c);
    }
    
    
    
    public void sth(Collection<? super Collection<?>> c){
    	
    }
}
