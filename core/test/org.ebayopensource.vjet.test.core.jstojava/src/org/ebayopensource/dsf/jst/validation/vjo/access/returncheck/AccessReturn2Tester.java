/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AccessReturnCorrect1Tester.java, May 26, 2009, 7:58:31 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.access.returncheck;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Access return correct 1 tester
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P1, FAST, UNIT })
public class AccessReturn2Tester extends VjoValidationBaseTester {
    
    //Bug exist
    @Before
    public void setUp() {
        expectProblems.clear();
        //Should change can't execute statement, ML change can't execute problem ID
        expectProblems.add(createNewProblem(MethodProbIds.UnreachableStmt, 8, 0));
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test return statement")
    public void testAccesVisible1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "access.returnStatement.","AccessReturn2Tester.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
