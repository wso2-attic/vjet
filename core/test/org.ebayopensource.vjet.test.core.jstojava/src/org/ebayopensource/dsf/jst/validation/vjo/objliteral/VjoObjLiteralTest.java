/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo.objliteral;





import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Test;




//@Category( { P1, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class VjoObjLiteralTest extends VjoValidationBaseTester {

    List<VjoSemanticProblem> actualProblems = null;

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test create customed object")
    public void testObjectLiteral() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 20, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ParameterMismatch, 22, 0));
        
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.objliteral.",
                "ObjLiteral.js", this.getClass());
        
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    //@Category( { P2, FAST, UNIT })
    //@Description("Test create customed object")
    public void testObjectLiteralInternal() throws Exception {
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.IncompatibleTypesInEqualityOperator, 6, 0));
        
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.objliteral.",
                "ObjLiteralInternal.js", this.getClass());
        
        assertProblemEquals(expectProblems, actualProblems);
    }
    
    @Test
    //@Category( { P2, FAST, UNIT })
    //@Description("Test create customed object")
    public void testObjectLiteralAttributed() throws Exception {
        expectProblems.clear();
        
        actualProblems = getVjoSemanticProblem(
                "org.ebayopensource.dsf.jst.validation.vjo.objliteral.",
                "ObjLiteralWithAttributedTypes.js", this.getClass());
        
        assertProblemEquals(expectProblems, actualProblems);
    }
}
