/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.vjoPro.samples.generics;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;



//@Category( { P3, FAST, UNIT })
public class GenericsExample1 extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("To test VjoPro project false positive")
    public void testGenericsExample1() {
    	expectProblems.add(createNewProblem(FieldProbIds.StaticReferenceToNonStaticType, 6, 0));
    	
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "vjoPro.samples.generics.", "GenericSample.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
