/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests;

import org.ebayopensource.dsf.javatojs.tests.cli.Java2VjoTests;
import org.ebayopensource.dsf.javatojs.tests.data.control.AllControlTests;
import org.ebayopensource.dsf.javatojs.tests.data.custom.AllCustomTranslationTests;
import org.ebayopensource.dsf.javatojs.tests.data.custom.meta.AllCustomMetaTests;
import org.ebayopensource.dsf.javatojs.tests.generics.GenericsTest;
import org.ebayopensource.vjet.test.util.JstLibResolver;
import org.ebayopensource.vjet.test.util.VJetSdkEnvironment;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	JsrGeneratorTests.class,
	StructureSyntaxTests.class,
//	DapTranslationTests.class,
	VjoDataTypeTests.class,
	AllCustomTranslationTests.class,
	MultiPassTests.class,
	ErrorReporterTests.class,
	DependencyTests.class,
	FilterTests.class,
	PreBuildTests.class, 
//	BuildTests.class,	// keep after PreBuildTests
	VjoPrebuildAntTest.class,
	Java2VjoTests.class,
//	AllTypeSpaceTests.class,
	GenericsTest.class,
	AllControlTests.class,
	AllCustomMetaTests.class
//	NativeArrayJ2JTests.class
	})
public class AllJavaToJsTests {

	@BeforeClass
	public static void setup() {
		IResourceResolver jstLibResolver = JstLibResolver
				.getInstance()
				.setSdkEnvironment(
						new VJetSdkEnvironment(
								new String[0], "DefaultSdk"));

		LibManager.getInstance().setResourceResolver(jstLibResolver);
	}
	
}
