/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: EqualsSample1.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjoPro.samples;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.validation.vjo.VjoValidationBaseTester;
import org.junit.Before;
import org.junit.Test;



/**
 * EqualsSample1.java
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */

//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
 public class EqualsSample1 extends VjoValidationBaseTester {

    @Before
    public void setUp(){
        expectProblems.clear();
    }   

     @Test
     public void testEqualsSample1(){
        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
        "vjoPro.samples.",
        "EqualsSample1.js",
            this.getClass());
         assertProblemEquals(expectProblems, problems);
        }
    }
