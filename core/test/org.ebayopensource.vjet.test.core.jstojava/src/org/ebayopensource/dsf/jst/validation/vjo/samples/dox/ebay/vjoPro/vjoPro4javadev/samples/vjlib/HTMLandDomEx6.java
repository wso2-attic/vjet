/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: HTMLandDomEx6.java.java, July 27, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.samples.dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib;




import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;




/**
 * HTMLandDomEx6.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class HTMLandDomEx6 extends VjoValidationBaseTester {

	 @BeforeClass
     public static void beforeClass(){
//     	JstCache.getInstance().clear();
//     	LibManager.getInstance().clear();
     }
	
    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 15, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 13, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 6, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 8, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 7, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 14, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 16, 0));
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test VJO Sample project, To validate false positive ")
    public void testHTMLandDomEx6() {
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER,
                "dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.",
                "HTMLandDomEx6.js", this.getClass());
        assertProblemEquals(expectProblems, problems);
    }
}
