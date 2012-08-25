/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: DeclaredCtypeCorrect1Tester.java, May 25, 2009, 11:50:55 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



/**
 * Declared ctype correct 1
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
public class DeclaredMtype3Tester extends VjoValidationBaseTester {

    @Before
    public void setUp(){
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod,2, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    public void testMTypes() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.mtype.","DeclaredMixinType3.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P3, FAST, UNIT })
    public void testMTypeExpects() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.mtype.","DeclaredMixinType5.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P3, FAST, UNIT })
    public void testMTypeExpectNeeds() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.UnusedActiveNeeds, 1, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.mtype.", "DeclaredMixinType6.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    public void testMTypeMixinSelf() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.MixinedTypeShouldNotBeItself, 1, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.mtype.", "CTypeMixinCType6.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    public void testMTypeDoubleMixin() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.RedundantImport, 3, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.mtype.", "DeclaredMixinType7.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P3, FAST, UNIT })
    public void testMTypeExpectsEType() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.ExpectsMustBeMtypeOrItype, 2, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.mtype.", "DeclaredMixinType8.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Ignore
    //@Category( { P3, FAST, UNIT })
    public void testMTypeComprisedObject() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.mtype.", "DeclaredMixinType9.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
