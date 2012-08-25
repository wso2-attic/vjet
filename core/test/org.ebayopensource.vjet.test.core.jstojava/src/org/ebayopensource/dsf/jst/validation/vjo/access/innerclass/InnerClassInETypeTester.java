/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: InnerClassInATypeTester.java, Nov 2, 2009, 6:42:56 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.access.innerclass;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
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
public class InnerClassInETypeTester extends VjoValidationBaseTester {
    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.MTypeAsInnerType, 7, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.OTypeAsInnerType, 11, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.MTypeAsInnerType, 22, 0));
        expectProblems.add(createNewProblem(VjoSyntaxProbIds.OTypeAsInnerType, 26, 0));
    }

    @Test
    //@Category( { P1, FAST, UNIT })
    //@Description("Test all Xtype as inner type in EType")
    public void testAccesVisible1() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                "access.innerClass.","InnerclassInEType.js", this
                        .getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
