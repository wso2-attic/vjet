/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: MixinType2Tester.java, May 21, 2009, 1:49:32 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.mixin;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Mix in typ2 tester
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
public class MixinType2Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 6, 0));
        expectProblems.add(createNewProblem(MethodProbIds.AmbiguousMethod, 17, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod, 6, 0));
        expectProblems.add(createNewProblem(MethodProbIds.UnusedPrivateMethod, 17, 0));
        
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test mixin function for ctype")
    public void testIfstatement1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.mixintype/", "Employee2.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
    
}
