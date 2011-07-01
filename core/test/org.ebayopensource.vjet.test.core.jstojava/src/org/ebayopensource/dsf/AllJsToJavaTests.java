/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf;

import java.io.File;
import java.net.URL;

import org.ebayopensource.dsf.ast.recovery.RecoveryTests;
import org.ebayopensource.dsf.cml.vjetv.AllHeadLessTests;
import org.ebayopensource.dsf.comment.parser.BootstrapCommentTest;
import org.ebayopensource.dsf.comment.parser.VjCommentParserTest;
import org.ebayopensource.dsf.jst.validation.vjo.AllVjoValidationTests;
import org.ebayopensource.dsf.jstdoc.AllJsDocTests;
import org.ebayopensource.dsf.jstojava.codegen.CodeGenJsrDiff;
import org.ebayopensource.dsf.jstojava.translator.AllTranslatorsTests;
import org.ebayopensource.dsf.tests.jsast.parser.AllJs2JstTests;
import org.ebayopensource.dsf.tests.jsast.parser.AllJsParsingTests;
import org.ebayopensource.dsf.vjolang.feature.tests.AllVjoFeatureTests;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AllTranslatorsTests.class,
	AllJs2JstTests.class,
	CodeGenJsrDiff.class,
//	AllEcmaLangTests.class,
//	JsLangParsingTests.class,
	AllJsParsingTests.class,
	AllVjoFeatureTests.class,
	RecoveryTests.class,
    AllVjoValidationTests.class,
    BootstrapCommentTest.class,
    VjCommentParserTest.class,
    AllHeadLessTests.class,
    AllJsDocTests.class
//    JsResourceOptimizationTests.class
})
public class AllJsToJavaTests {
	@BeforeClass
	public static void appendSourcePath(){
		URL u2 = AllJsToJavaTests.class.getClassLoader().getResource(
				AllJsToJavaTests.class.getName().replace('.', '/')+".class");
		if(u2.getFile().contains(".jar")){
			String path = u2.getFile();
			if (path.startsWith("file:/")){
				path = path.substring("file:/".length());
			}
			String jarPath = path.substring(0, path.indexOf(".jar")+".jar".length());
			System.setProperty("java.source.path", System.getProperty("java.source.path")+File.pathSeparator+jarPath);
		}
	}
}
