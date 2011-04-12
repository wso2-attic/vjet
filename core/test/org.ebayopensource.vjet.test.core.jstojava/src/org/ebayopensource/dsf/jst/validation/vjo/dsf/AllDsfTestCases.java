/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllDsfTestCases.java, Jun 19, 2009, 1:52:50 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.dsf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 * Oct.14 failedtestcase: 0
 */

@RunWith(Suite.class)
@SuiteClasses({
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.AnchorTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.AreaTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.BaseFontTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.BaseTest.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.BodyTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.ButtonTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.DlistTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.DocTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.HasFeatureTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.HTMLAnchorElementTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.HTMLAppletElementTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.HTMLAreaElementTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.HTMLBaseElementTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.HTMLBaseFontElementTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.html.level2.HTMLBodyElementTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.W3cDomLevel1CoreTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.dom.XMLDomTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.BigNumberTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.CommentTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.ConcatTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.EmptyTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.ForTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.Inner2Tests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.Inner3Tests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.InnerTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.Parens2Tests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.RegExTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.format.UnicodeTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.ArrayWrapperTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.BaseTest.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.BaseTestEcma2.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.BaseTestEcma3.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2ExceptionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2ExpressionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2extensionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2FunctionObjectsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2instanceofTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2LexicalConventionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2RegExpTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2StatementsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma2StringTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3ArrayTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3DateTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3ExceptionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3ExecutionContextsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3ExpressionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3ExtensionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3FunctionTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3FunExprTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3LexicalConventionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3NumberFormattingTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3NumberTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3ObjectTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3OperatorsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3RegExpTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3RegressTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3StatementsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3StringTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3UnicodeTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3_1ObjectTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Ecma3_1RegExpTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaArrayTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaBooleanTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaDateTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaExecutionContextsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaExpressionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaExtensionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaFunctionObjectsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaGlobalObjectTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaLexicalConventionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaMathTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaNumberTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaObjectObjectsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaSourceTextTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaStatementsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaStringTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaTypeConversionTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.EcmaTypesTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js12ArrayTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js12FunctionTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js12OperatorTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js12RegExpTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js12RegressTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js12StatementsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js12StringTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js13BooleanTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js13ExtensionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js13InheritTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js13ScriptTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js14EvalTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js14FunctionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15ArrayTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15DateTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15ErrorTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15ExceptionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15ExpressionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15ExtensionsMoreTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15ExtensionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15FunctionTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15GetSetTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15LexicalConventionsTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15ObjectTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15ScopeTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jslang.feature.tests.Js15StringTests.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jst.validation.vjoPro.BugFixes.Bug3944.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jst.validation.vjoPro.BugFixes.Bug3944Extn.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jst.validation.vjoPro.BugFixes.ClassA.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jst.validation.vjoPro.BugFixes.CTypeUtil.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jst.validation.vjoPro.BugFixes.EnumA.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.jst.validation.vjoPro.BugFixes.IA.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.tests.jsast.parser.devtests.MType.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.vjoProlang.feature.tests.MethodNoComment.class,
    org.ebayopensource.dsf.jst.validation.vjo.dsf.vjoProlang.feature.tests.MethodOverloadingTests.class
})

public class AllDsfTestCases {
}
