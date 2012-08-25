/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: DeclaredCtypeCorrect3Tester.java, May 31, 2009, 10:01:50 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;




/**
 * Declared ctype correct include inits
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class DeclaredItypeSeq2Tester extends VjoValidationBaseTester {

    @Before
    public void setUp(){
        expectProblems.clear();
//        bugfix by roy, early fail on undefined method, no subsequent error
        expectProblems.add(createNewProblem(MethodProbIds.UndefinedMethod, 5, 0));
//        expectProblems.add(createNewProblem(VjoSyntaxProbIds.IncorrectVjoSyntax, 5, 0));
//        expectProblems.add(createNewProblem(VjoSyntaxProbIds.TypeUnknownNotInTypeSpace, 5, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test declared itype with unnomal sequence ")
    public void testCTypes() {
 
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "syntax.declare.itype.","ITypeExample5.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
