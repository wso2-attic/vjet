/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: ArrayType1Tester.java, May 7, 2009, 6:34:28 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.typecheck.array;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




/**
 * For test the in array types in Equality Operator.
 * 
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//@Category( { P3, FAST, UNIT })
//@Description("Test assign value to array children's type mismatch")
public class ArrayType3Tester extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        // FIXME find correct problem id
        expectProblems.clear();
        expectProblems.add(createNewProblem(TypeProbIds.TypeMismatch, 18, 0));
        expectProblems.add(createNewProblem(MethodProbIds.ShouldReturnValue,
                17, 0));
    }

    @Test
    @Ignore
    //@Category( { P3, FAST, UNIT })
    //@Description("Test assign value to array children's type mismatch")
    public void testCompartibleType2() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "ArrayType3Tester.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
