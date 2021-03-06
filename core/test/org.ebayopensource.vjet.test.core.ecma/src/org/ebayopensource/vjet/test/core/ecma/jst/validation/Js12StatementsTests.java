/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: Js12StatementsTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;




import org.ebayopensource.dsf.jsgen.shared.ids.MethodProbIds;
import org.junit.Before;
import org.junit.Test;




/**
 * Js12StatementsTests.java
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
//@Category( { P3, FAST, UNIT })
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class Js12StatementsTests extends VjoValidationBaseTester {

    @Before
    public void setUp() {
        expectProblems.clear();
    }

    @Test
    //@Category( { P3, FAST, UNIT })
    //@Description("Test DSF project, To validate false positive ")
    public void testJs12StatementsTests() {
    	expectProblems.add(createNewProblem(
                MethodProbIds.UnreachableStmt, 438, 0));
    	expectProblems.add(createNewProblem(
                MethodProbIds.UnreachableStmt, 466, 0));
    	
        assertProblemEquals(expectProblems, getVjoSemanticProblem(
                "dsf.jslang.feature.tests.", "Js12StatementsTests.js", this
                .getClass()));
    }
}
