package org.ebayopensource.vjet.test.core.ecma.jst.validation;
///*******************************************************************************
// * Copyright (c) 2005-2011 eBay Inc.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *
// *******************************************************************************/
///* 
// * $Id: Js12ArrayTests.java.java, Jun 21, 2009, 12:20:41 AM, liama. Exp$:
// * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
// * This software program and documentation are copyrighted by Ebay
// * Technologies.
// */
//package org.ebayopensource.dsf.jst.validation;
//
//
//
//
//import java.util.List;
//
//import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
//import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
//import org.junit.Before;
//import org.junit.Test;
//
//
//
//
///**
// * Js12ArrayTests.java
// * 
// * @author <a href="mailto:liama@ebay.com">liama</a>
// * @since JDK 1.5
// */
////@Category( { P3, FAST, UNIT })
////@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
//public class Js12ArrayTests extends VjoValidationBaseTester {
//
//    @Before
//    public void setUp() {
//        expectProblems.clear();
//        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 229, 0));
//        expectProblems.add(createNewProblem(VarProbIds.LooseVarDecl, 345, 0));
//    }
//
//    @Test
//    //@Category( { P3, FAST, UNIT })
//    //@Description("Test DSF project, To validate false positive ")
//    public void testJs12ArrayTests() {
//        List<VjoSemanticProblem> problems = getVjoSemanticProblem(
//                null,"dsf.jslang.feature.tests.", "Js12ArrayTests.js", this
//                        .getClass(),"test",true);
//        assertProblemEquals(expectProblems, problems);
//    }
//}
