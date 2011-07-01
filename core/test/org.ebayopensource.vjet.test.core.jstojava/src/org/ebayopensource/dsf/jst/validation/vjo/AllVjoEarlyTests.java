/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo;

import org.ebayopensource.dsf.jst.validation.vjo.BugFixes.VjoValidationBugFixMoreTests;
import org.ebayopensource.dsf.jst.validation.vjo.BugFixes.VjoValidationBugFixTests;
import org.ebayopensource.dsf.jst.validation.vjo.accessbility.VjoAccessbilityTest;
import org.ebayopensource.dsf.jst.validation.vjo.arrayaccess.VjoArrayAccessTest;
import org.ebayopensource.dsf.jst.validation.vjo.arraycreation.VjoArrayCreationTest;
import org.ebayopensource.dsf.jst.validation.vjo.attributed.AttributedTypeAsFunctionParamTest;
import org.ebayopensource.dsf.jst.validation.vjo.attributed.GlobalVarAsAttributedTypeTest;
import org.ebayopensource.dsf.jst.validation.vjo.attributed.LocalVarAsAttributedTypeTest;
import org.ebayopensource.dsf.jst.validation.vjo.boolexpr.VjoBoolExprTest;
import org.ebayopensource.dsf.jst.validation.vjo.clz.VjoClassTest;
import org.ebayopensource.dsf.jst.validation.vjo.ex.VjoExTest;
import org.ebayopensource.dsf.jst.validation.vjo.globals.extension.GlobalsExtensionTest;
import org.ebayopensource.dsf.jst.validation.vjo.inherits.VjoInheritsTest;
import org.ebayopensource.dsf.jst.validation.vjo.jsnative.VjoJsNativeTest;
import org.ebayopensource.dsf.jst.validation.vjo.jstarrayinitializer.VjoJstArrayInitializerTest;
import org.ebayopensource.dsf.jst.validation.vjo.jstinitializer.VjoJstInitializerTest;
import org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.VjoChainOfFunctionInvocationTest;
import org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.VjoMtdInvocationTest;
import org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.VjoParamTypeAsFunctionInferenceTest;
import org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.VjoParamTypeAsFunctionTest;
import org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.VjoReturnTypeAsFunctionTest;
import org.ebayopensource.dsf.jst.validation.vjo.needs.VjoNeedsTests;
import org.ebayopensource.dsf.jst.validation.vjo.objcreation.VjoObjCreationTest;
import org.ebayopensource.dsf.jst.validation.vjo.objliteral.VjoObjLiteralTest;
import org.ebayopensource.dsf.jst.validation.vjo.overloaded.VjoOverloadedTest;
import org.ebayopensource.dsf.jst.validation.vjo.overriden.VjoOverrideTest;
import org.ebayopensource.dsf.jst.validation.vjo.rtnflow.VjoRtnFlowTest;
import org.ebayopensource.dsf.jst.validation.vjo.syntax.global.GlobalTester;
import org.ebayopensource.dsf.jst.validation.vjo.typecheck.VjoTypeCheckTest;
import org.ebayopensource.dsf.jst.validation.vjo.unsupported.VjoUnsupportedTest;
import org.ebayopensource.dsf.jst.validation.vjo.vjNS.VjoVjNsTest;
import org.ebayopensource.dsf.jst.validation.vjo.with.VjoWithTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	VjoValidationTest.class,
	GlobalTester.class,
	VjoAccessbilityTest.class,
	VjoTypeCheckTest.class,
	VjoMtdInvocationTest.class,
	VjoArrayAccessTest.class,
	VjoArrayCreationTest.class,
	VjoJstArrayInitializerTest.class,
	VjoJstInitializerTest.class,
	VjoObjCreationTest.class,
	VjoJsNativeTest.class,
	VjoVjNsTest.class,
	VjoInheritsTest.class,
	VjoWithTest.class,
	VjoOverloadedTest.class,
	VjoBoolExprTest.class,
	VjoExTest.class,
	VjoRtnFlowTest.class,
	VjoOverrideTest.class,
	VjoClassTest.class,
	VjoUnsupportedTest.class,
	VjoValidationBugFixMoreTests.class,
	VjoValidationBugFixTests.class,
	TempVjoValidationTests.class,
	LocalVarAsAttributedTypeTest.class,
	GlobalVarAsAttributedTypeTest.class,
	AttributedTypeAsFunctionParamTest.class,
	GlobalsExtensionTest.class,
	VjoChainOfFunctionInvocationTest.class,
	VjoReturnTypeAsFunctionTest.class,
	VjoParamTypeAsFunctionTest.class,
	VjoParamTypeAsFunctionInferenceTest.class,
	VjoObjLiteralTest.class,
	VjoNeedsTests.class
})


public class AllVjoEarlyTests {

}
