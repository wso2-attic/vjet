/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: HTMLandDomEx5.java.java, July 27, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.samples.dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib;
import com.ebay.junitnexgen.category.ModuleInfo;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P3;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.ebayopensource.dsf.jsgen.shared.ids.TypeProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;
import org.ebayopensource.vjo.lib.LibManager;

/**
 * HTMLandDomEx5.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
@Category( { P3, FAST, UNIT })
@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class HTMLandDomEx5 extends VjoValidationBaseTester {

	 @BeforeClass
     public static void beforeClass(){
//     	JstCache.getInstance().clear();
//     	LibManager.getInstance().clear();
     }
	
    @Before
    public void setUp() {
        expectProblems.clear();
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 17, 0));
        expectProblems.add(createNewProblem(VarProbIds.UndefinedName, 25, 0));
    }

    @Test
    @Category( { P3, FAST, UNIT })
    @Description("Test VJO Sample project, To validate false positive ")
    public void testHTMLandDomEx5() {
    	
    	// error switches depending on if Element is loaded in TS
    	// if Element is not loaded get undefined name
    	// if Element is loaded get inactive needs in use
    	
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
                VjoValidationBaseTester.VJLIB_FOLDER,
                "dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.",
                "HTMLandDomEx5.js", this.getClass(), "GRP", false);
        assertProblemEquals(expectProblems, problems);
    }
}
