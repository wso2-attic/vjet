/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.global.vjoType;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 *
 */
public class VjoMixinTester extends VjoValidationBaseTester {
	@Before
    public void setUp(){
        expectProblems.clear();
    }

    @Test
    public void testIfstatement1() {
    	expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 6, 0));
    	expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 13, 0));
    	
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.global.vjoType/",
                "VjoMixin.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
