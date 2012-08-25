/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.bugfixes;




import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoBugFixes extends VjoValidationBaseTester {

   

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test or statement with null")
    public void testBugOrStmt() throws Exception {
    	 List<VjoSemanticProblem> actualProblems = new ArrayList();
    	 expectProblems.clear();
    	 expectProblems.add(createNewProblem(FieldProbIds.FieldInitializationDependsOnUnintializedTypes,
                 5, 0));
        actualProblems = getVjoSemanticProblem(
                "vjoPro.", "Test.js", this
                        .getClass());
        assertProblemEquals(expectProblems, actualProblems);
    }

    
    //@Category( { P1, FAST, UNIT })
    //@Description("Test or statement with null")
    public void testBugGeneric() throws Exception {
    	List<VjoSemanticProblem> actualProblems = new ArrayList();
//    	actualProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 20, 0));
//    	actualProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 21, 0));
//    	
    	expectProblems.clear();
    	actualProblems = getVjoSemanticProblem(
    			"vjoPro.", "TestGeneric.js", this
    			.getClass());
    	assertProblemEquals(expectProblems, actualProblems);
    }
    
    
  
}
