/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: CorrectOtype.java, Jul 27, 2009, 1:48:27 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.otype;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
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
public class CorrectOtype extends VjoValidationBaseTester {
    @Before
    public void setUp(){
        expectProblems.clear();
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test declared mtype with unexpect block")
    public void testOTypes() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.otype.","Deotype.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test declared mtype with unexpect block")
    public void testOTypes1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.otype.","Defbug1.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test declared mtype with unexpect block")
    public void testOTypes2() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 9, 0));
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.otype.","OTypeTarget.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
