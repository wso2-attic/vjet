/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: ContinueStatement.java, Sep 24, 2009, 8:08:25 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.continueBreak;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;

/**
 * Continue break statement
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class ContinueStatement2 extends VjoValidationBaseTester {

    @Before
    public void setUp(){
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.ClassBetterStartsWithCapitalLetter, 1, 0));
    }

    @Test
    public void testIfstatement1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.continuebreak.",
                "continuebreak2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
