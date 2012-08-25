/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.needs;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoNeedsTests extends VjoValidationBaseTester {

	@Test
    //@Category( { P1, FAST, UNIT })
    //@Description("test false positive active needs required")
    public void testActiveNeedsNotTruelyRequired() throws Exception {
        expectProblems.clear();
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.needs.", "NeedsTest.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
	
	@Test
    //@Category( { P1, FAST, UNIT })
    //@Description("test active needs required")
    public void testActiveNeedsRequired() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.InactiveNeedsInUse, 1,
                0));
        expectProblems.add(createNewProblem(
                FieldProbIds.UndefinedField, 10, 0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.needs.", "NeedsTestError.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
	
	@Test
    //@Category( { P2, FAST, UNIT })
    //@Description("test active needs required")
    public void testActiveNeedsRequiredInConstruction() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.InactiveNeedsInUse, 3,
                0));
        final List<VjoSemanticProblem> actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.needs.", "ActiveNeededInConstruction.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }
    
}
