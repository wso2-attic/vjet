/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.access.scope.var;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;



public class VarAndArgTest extends VjoValidationBaseTester {
	 @Before
	    public void setUp() {
	        expectProblems.clear();
	    }
	    
	    @Test
	    //@Category( { P1, FAST, UNIT })
	    //@Description("Test arg hiding external var")
	    public void testAccesVisible1() {
	        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
	                "access.scope.var.","VarAndArgTest.js", this
	                        .getClass());
	        assertProblemEquals(expectProblems, problems);
	    }
}
