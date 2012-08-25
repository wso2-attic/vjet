/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: TypecheckCompartibleType2Tester.java, May 7, 2009, 6:34:28 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * test1 : undefined //< Public String
 * 
 * //> Public int test2 : null
 * 
 * 
 * //> Number a() a:function(){ this.test1 = 20 + "HA"; return test1; },
 * 
 * 
 * //> Number a1() a1:function(){ test1 = 20 + "HA"; return test1; },
 * 
 * 
 * b:function(){ //< public void b() test2 = 30; test2++; test2 = test2 + 2.32; },
 * 
 * //> public void b1() b1:function(){ this.test2 = 30; this.test2++; this.test2 =
 * this.test2 + 2.32; },
 * 
 * b2:function(){ //< public void b2() this.test2 = 30; this.test2++;
 * this.test2 = test2 + 2.32; },
 * 
 * c:function(){ //< public void c() test2 = 30; test2 = test2 + "HA"; },
 * 
 * c1:function(){ //< public void c1() this.test2 = 30; this.test2 = this.test2 +
 * "HA"; },
 * 
 * c2:function(){ //< public void c2() this.test2 = 30; this.test2 = test2 +
 * "HA"; }
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
public class TypecheckCompartibleType4Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 47, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 15, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 22, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 57, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.IncompatibleTypesInEqualityOperator, 52, 0));
        expectProblems.add(createNewProblem(
                TypeProbIds.ClassBetterStartsWithCapitalLetter, 3, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test incomatible situation betwween string and int.Number")
    public void testCompartibleType4() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "typecheck.compartible.", "typecheckCompartibleType4.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
