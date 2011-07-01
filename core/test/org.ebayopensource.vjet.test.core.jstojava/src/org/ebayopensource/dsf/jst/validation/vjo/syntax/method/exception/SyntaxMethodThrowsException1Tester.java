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
package org.ebayopensource.dsf.jst.validation.vjo.syntax.method.exception;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test the abstract method will be impl in sub classes
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class SyntaxMethodThrowsException1Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
    }

    @Test
    @Ignore
    public void testInterfaceType1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.exception/", "MtdThrowsException1.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    @Ignore
    public void testThrow2() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 9, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 11, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 14, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 15, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 16, 0));
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 17, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 13, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 14, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 15, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 16, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 17, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 18, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 19, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.exception/", "MtdThrowsException2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    public void testThrow3() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.exception/", "MtdThrowsException3.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    public void testThrow4() {
        expectProblems.clear();
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.exception/", "MtdThrowsException3.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
